package com.icpay.payment.bm.bo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.icpay.payment.bm.cache.AgentInfoCache;
import com.icpay.payment.bm.cache.BMConfigCache;
import com.icpay.payment.bm.cache.DataDicCache;
import com.icpay.payment.bm.cache.MchntTpCache;
import com.icpay.payment.bm.cache.RegionInfoCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.MchntStCd;
import com.icpay.payment.common.entity.Monitor;
import com.icpay.payment.common.enums.BusCheckTaskEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.MchntStCdEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.FileLineHandler;
import com.icpay.payment.common.utils.FileUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.RegionInfo;
import com.icpay.payment.db.dao.mybatis.model.TransTypeGroup;
import com.icpay.payment.db.service.IMchntInfoService;
import com.icpay.payment.db.service.ITransTypeGroupService;

@Component("mchntInfoBO")
public class MchntInfoBO extends BaseBO {
	
	private static final Logger logger = Logger.getLogger(MchntInfoBO.class);
	
	@Autowired
	private BusCheckBO busCheckBO;
	
	/**
	 * 批量导入
	 * @param file
	 * @return
	 */
	public Map<String, Object> batchImport(MultipartFile file) {
		logger.info("####商户信息批量导入:" + file.getOriginalFilename() + "####");
		
		// step1: 校验并解析文件名
		logger.info("##检查文件名##");
		String fileName = file.getOriginalFilename();
		if (!fileName.startsWith(BMConstants.MCHNT_IMPORT_FILE_PREFIX)) {
			throw new BizzException("文件名需以如下字符串开头：" + BMConstants.MCHNT_IMPORT_FILE_PREFIX);
		}
		
		// step2: 检查文件是否已存在
		logger.info("##检查文件是否已存在##");
		String fullFileNm = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_MCHNT_IMPORT_FILE_PATH) + fileName;
		File targetFile = new File(fullFileNm);
		if (targetFile.exists()) {
			throw new BizzException("文件名已存在");
		}
		
		// step3: 将文件存放至磁盘
		logger.info("##将文件存放至磁盘##");
		try {
			if (!targetFile.getParentFile().exists()) {
				targetFile.getParentFile().mkdirs();
			}
			file.transferTo(targetFile);
		} catch (Exception e) {
			logger.error("##将文件存放至磁盘失败##", e);
			throw new BizzException("将文件存放至磁盘失败");
		}
		
		// step4: 处理文件，并将处理结果写入回盘文件
		logger.info("##处理文件，并将处理结果写入回盘文件");
		final Map<String, Object> resultMap = new HashMap<String, Object>();
		String importResultFileNm = fullFileNm + "_result";
		resultMap.put(BMConstants.SESSION_KEY_MCHNT_IMPORT_RSLT_FILENM, fileName + "_result");
		resultMap.put("total", 0);
		resultMap.put("succ", 0);
		resultMap.put("fail", 0);
		final List<String> result = new ArrayList<String>();
		final Set<String> existingMchntCnNm = new HashSet<String>();
		ITransTypeGroupService service = DBHessionServiceClient.getService(ITransTypeGroupService.class);
		List<TransTypeGroup> transTpGroupLst = service.select(null);
		final Set<String> transTpGroupIdSet = new HashSet<String>();
		for (TransTypeGroup g : transTpGroupLst) {
			transTpGroupIdSet.add(String.valueOf(g.getSeqId()));
		}
		
		final Set<String> dbExistingMchntCnNm = new HashSet<String>();
		IMchntInfoService mchntService = DBHessionServiceClient.getService(IMchntInfoService.class);
		List<MchntInfo> mchntLst = mchntService.select(null);
		for (MchntInfo m : mchntLst) {
			dbExistingMchntCnNm.add(m.getMchntCnNm());
		}
		
		FileUtil.readFileByLine(targetFile, Constant.GBK, new FileLineHandler() {
			@Override
			public void handleLine(String line) {
				Monitor m = new Monitor();
				String dealResult = processLine(line, existingMchntCnNm, dbExistingMchntCnNm, transTpGroupIdSet);
				result.add(line + "|" + dealResult);
				Integer t = (Integer) resultMap.get("total");
				resultMap.put("total", t + 1);
				if ("处理成功".equals(dealResult)) {
					Integer succ = (Integer) resultMap.get("succ");
					resultMap.put("succ", succ + 1);
				} else {
					Integer fail = (Integer) resultMap.get("fail");
					resultMap.put("fail", fail + 1);
				}
				if (logger.isDebugEnabled()) {
					logger.debug("line[" + (t + 1) + "] process tm:" + m.execTm());
				}
			}
		});
		FileUtil.writeStrsToFile(importResultFileNm, result, Constant.GBK);
		
		return resultMap;
	}

	/**
	 * 逐行处理批量导入文件
	 * @param line
	 * @param existingSn
	 * @return
	 */
	private String processLine(String line, final Set<String> existingMchntCnNm,
											final Set<String> dbExistingMchntCnNm,
											final Set<String> transTpGroupIdSet) {
		try {
			if (StringUtil.isBlank(line)) {
				return "处理失败: 空行.";
			}
			String[] strs = line.split("\\|", -1);
			if (strs.length != 19) {
				return "处理失败: 导入域数个数不足";
			}
			
			// 解析各域
			String mchntTp = strs[0]; // 商户MCC
			String mchntCnNm = strs[1]; // 商户中文名称
			String mchntEnNm = strs[2]; // 商户英文名称
			String mchntCnAbbr = strs[3]; // 商户中文简称
			String mchntEnAbbr = strs[4]; // 商户英文简称
			String agentCd = strs[5]; // 所属代理商
			String tradeType = strs[6]; // 行业类别
			String riskFlag = strs[7]; // 风险标识
			String selfSettle = strs[8]; // 是否自主清算
			String expiredDt = strs[9];  // 失效日期
			String areaCd = strs[10];  // 商户地区码 - 市级
			String mchntAddr = strs[11]; // 详细地址
			String transTypeGroupId = strs[12]; // 交易权限组ID
			String zipCd = strs[13]; // 邮编
			String contactPerson = strs[14]; // 联系人
			String contactPhone = strs[15]; // 联系电话
			String contactMail = strs[16]; // 电子邮箱
			String fax = strs[17]; // 传真
			String comment = strs[18]; // 备注
			
			// 必填项校验
			AssertUtil.strIsBlank(mchntTp, "商户MCC为空");
			AssertUtil.strIsBlank(mchntCnNm, "中文名为空");
			AssertUtil.strIsBlank(mchntEnNm, "英文名为空");
			AssertUtil.strIsBlank(mchntCnAbbr, "中文简称为空");
			AssertUtil.strIsBlank(mchntEnAbbr, "英文简称为空");
			AssertUtil.strIsBlank(agentCd, "所属代理商代码为空");
			AssertUtil.strIsBlank(tradeType, "行业类别为空");
			AssertUtil.strIsBlank(riskFlag, "风险标识为空");
			AssertUtil.strIsBlank(selfSettle, "是否自主清算为空");
			AssertUtil.strIsBlank(expiredDt, "过期日期为空");
			AssertUtil.strIsBlank(areaCd, "地区码为空");
			AssertUtil.strIsBlank(transTypeGroupId, "交易权限组ID为空");
			
			/**
			 * 数据有效性校验
			 */
			if (!MchntTpCache.getMchntTpMap().containsKey(mchntTp)) {
				throw new BizzException("无效商户MCC");
			}
			if (!AgentInfoCache.isAgentExist(agentCd)) {
				throw new BizzException("所属代理商不存在");
			}
			if (tradeType.equals(DataDicCache.getDataDicVal(Constant.DATA_DIC_DATA_TP.TRADE_TYPE, tradeType))) {
				throw new BizzException("行业类别代码无效");
			}
			if (riskFlag.equals(DataDicCache.getDataDicVal(Constant.DATA_DIC_DATA_TP.MER_RISK_FLG, riskFlag))) {
				throw new BizzException("风险标识无效");
			}
			MchntStCdEnums.SelfSettle e = EnumUtil.parseEnumByCode(MchntStCdEnums.SelfSettle.class, selfSettle);
			if (e == null) {
				throw new BizzException("是否自主清算标志无效");
			}
			RegionInfo r = RegionInfoCache.getRegionInfo(areaCd);
			if (r == null) {
				throw new BizzException("请输入合法的6位直辖市或城市地区码");
			}
			if (!CommonEnums.RegionLvl._2.getCode().equals(r.getRegionLvl()) 
					&& !RegionInfoCache.getDirCitySet().contains(areaCd)) {
				throw new BizzException("请输入6位直辖市或城市地区码");
			}
			if (!transTpGroupIdSet.contains(transTypeGroupId)) {
				throw new BizzException("交易权限组不存在");
			}
			if (existingMchntCnNm.contains(mchntCnNm)) {
				return "处理失败: 文件内商户名重复";			
			} else {
				existingMchntCnNm.add(mchntCnNm);
			}
			if (dbExistingMchntCnNm.contains(mchntCnNm)) {
				throw new BizzException("商户已存在:" + mchntCnNm);
			}
			
			// 构造待新增的商户信息
			MchntInfo mchnt = new MchntInfo();
			mchnt.setMchntTp(mchntTp);
			mchnt.setMchntCnNm(mchntCnNm);
			mchnt.setMchntEnNm(mchntEnNm);
			mchnt.setMchntCnAbbr(mchntCnAbbr);
			mchnt.setMchntEnAbbr(mchntEnAbbr);
			mchnt.setAgentCd(agentCd);
			mchnt.setTradeType(tradeType);
			mchnt.setRiskFlag(riskFlag);
			mchnt.setExpiredDt(expiredDt);
			mchnt.setAreaCd(areaCd);
			mchnt.setMchntAddr(mchntAddr);
			mchnt.setTransTypeGroupId(Integer.parseInt(transTypeGroupId));
			mchnt.setZipCd(zipCd);
			mchnt.setContactPerson(contactPerson);
			mchnt.setContactPhone(contactPhone);
			mchnt.setContactMail(contactMail);
			mchnt.setFax(fax);
			mchnt.setComment(comment);
			
			mchnt.setTransType("0000000000000000000000000000000000000000");
			mchnt.setMchntSt(CommonEnums.MchntSt._1.getCode());
			
			MchntStCd stCdObj = new MchntStCd();
			stCdObj.setSelfSettle(e.getCode());
			mchnt.setStCd(stCdObj.toStCd());
			
			
			// 添加新增商户任务
			busCheckBO.newTask(BusCheckTaskEnums.TaskTp._01, this.getSessionUser().getUsrId(), 
					CommonEnums.OpType.ADD, "批量导入商户", mchnt);
		} catch (Exception e) {
			logger.error("处理失败", e);
			if (e instanceof BizzException) {
				return "处理失败:" + e.getMessage();
			} else {
				return "处理失败: 系统错误，请联系管理员";
			}
		}
		return "处理成功";
	}
	
}
