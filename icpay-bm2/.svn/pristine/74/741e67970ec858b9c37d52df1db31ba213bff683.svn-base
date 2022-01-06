package com.icpay.payment.bm.bo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.icpay.payment.bm.cache.BMConfigCache;
import com.icpay.payment.bm.cache.ValidationConfCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.Monitor;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EncodeUtils;
import com.icpay.payment.common.utils.EncryptUtil;
import com.icpay.payment.common.utils.FileLineHandler;
import com.icpay.payment.common.utils.FileUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.validate.ValidationHelper;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.TermInfo;
import com.icpay.payment.db.service.ITermInfoService;
import com.icpay.payment.service.IcpayDbmKeyHSM;

@Component("terminalBO")
public class TerminalBO extends BaseBO {
	
	private static final Logger logger = Logger.getLogger(TerminalBO.class);
	
	/**
	 * 批量导入
	 * @param file
	 * @return
	 */
	public Map<String, Object> batchImport(MultipartFile file, final String batNo) {
		logger.info("####终端信息批量导入:" + file.getOriginalFilename() + "####");
		
		// step1: 校验并解析文件名
		logger.info("##检查文件名##");
		String fileName = file.getOriginalFilename();
		if (!fileName.startsWith(BMConstants.TERMINAL_IMPORT_FILE_PREFIX)) {
			throw new BizzException("文件名需以如下字符串开头：" + BMConstants.TERMINAL_IMPORT_FILE_PREFIX);
		}
		
		// step2: 检查文件是否已存在
		logger.info("##检查文件是否已存在##");
		String fullFileNm = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_TERMINAL_IMPORT_FILE_PATH) + fileName;
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
		resultMap.put(BMConstants.SESSION_KEY_TERM_IMPORT_RSLT_FILENM, fileName + "_result");
		resultMap.put("total", 0);
		resultMap.put("succ", 0);
		resultMap.put("fail", 0);
		final List<String> result = new ArrayList<String>();
		final Set<String> existingSn = new HashSet<String>();
		FileUtil.readFileByLine(targetFile, Constant.GBK, new FileLineHandler() {
			@Override
			public void handleLine(String line) {
				Monitor m = new Monitor();
				String dealResult = processLine(line, batNo, existingSn);
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
	 * 批量导出
	 */
	public int export(String exportKey, Map<String, String> qryParamMap) {
		byte[] k = EncodeUtils.hexDecode(exportKey);
		AssertUtil.mapIsEmpty(qryParamMap, "查询条件不能为空");
		
		ITermInfoService termInfoService = DBHessionServiceClient.getService(ITermInfoService.class);
		List<TermInfo> lst = termInfoService.select(qryParamMap);
		String maxExportNum = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_TERMINAL_EXPORT_MAX_SIZE);
		int maxSize = 10000; // 默认最大导出个数为10000条
		if (!StringUtil.isBlank(maxExportNum)) {
			maxSize = Integer.parseInt(maxExportNum);
		}
		if (lst == null || lst.size() == 0) {
			throw new BizzException("需要导出的记录个数为0");
		}
		if (lst.size() > maxSize) {
			throw new BizzException("导出记录个数超过允许的最大值:" + maxSize);
		}
		
		List<String> result = new ArrayList<String>();
		for (TermInfo t : lst) {
			StringBuilder line = new StringBuilder();
			line.append(t.getTermMn() + "|");
			line.append(t.getTermBn() + "|");
			line.append(t.getTermSn() + "|");
			
			// 构造终端主密钥传输字符串
			String tmkCv = null; // 终端主密钥CheckValue
			String takCv = null; // 激活密钥CheckValue
			try {
				//byte[] rowTmk = EncryptUtil.TripleDESDecrypt(Constant.KEY, EncodeUtils.hexDecode(t.getTmk()));
				byte[] rowTmk = IcpayDbmKeyHSM.instance().decrypt(EncodeUtils.hexDecode(t.getTmk()));
				tmkCv = EncodeUtils.hexEncode(EncryptUtil.TripleDESEncrypt(rowTmk, "00000000".getBytes()));
				byte[] exportTmk = EncryptUtil.TripleDESEncrypt(k, rowTmk);
				line.append(EncodeUtils.hexEncode(exportTmk) + "|");
			} catch (Exception e) {
				logger.error("构造终端主密钥传输字符串失败", e);
				throw new BizzException("终端主密钥构造失败");
			}
			
			// 构造激活密钥传输字符串
			try {
				//byte[] rowTak = EncryptUtil.TripleDESDecrypt(Constant.KEY, EncodeUtils.hexDecode(t.getTak()));
				byte[] rowTak = IcpayDbmKeyHSM.instance().decrypt(EncodeUtils.hexDecode(t.getTak()));
				takCv = EncodeUtils.hexEncode(EncryptUtil.TripleDESEncrypt(rowTak, "00000000".getBytes()));
				byte[] exportTak = EncryptUtil.TripleDESEncrypt(k, rowTak);
				line.append(EncodeUtils.hexEncode(exportTak) + "|");
			} catch (Exception e) {
				logger.error("构造终端激活密钥传输字符串失败", e);
				throw new BizzException("终端激活密钥构造失败");
			}
			line.append(tmkCv + "|");
			line.append(takCv);
			result.add(line.toString());
		}
		
		String exportFileNm = "term_export_" + System.currentTimeMillis();
		String exportDir = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_TERMINAL_EXPORT_FILE_PATH);
		String fullFilePath = exportDir + exportFileNm;
		// 将导出结果写入磁盘
		FileUtil.writeStrsToFile(fullFilePath, result, Constant.UTF8);
		this.setSessionAttr(BMConstants.SESSION_KEY_TERM_EXPORT_RSLT_FILENM, exportFileNm);
		
		return lst.size();
	}

	/**
	 * 逐行处理批量导入文件
	 * @param line
	 * @param existingSn
	 * @return
	 */
	private String processLine(String line, final String batNo, final Set<String> existingSn) {
		try {
			if (StringUtil.isBlank(line)) {
				return "处理失败: 空行.";
			}
			String[] strs = line.split("[|]");
			if (strs.length != 3) {
				return "处理失败: 导入域数个数不足";
			}
			String mn = strs[0];
			String bn = strs[1];
			String sn = strs[2];
			// 校验终端型号
			ValidationHelper.validate(mn, 
					ValidationConfCache.getConfig(BMConstants.VALID_CONF_KEY_TERMINFO_TERMMN));
			// 校验终端批次号
			ValidationHelper.validate(bn, 
					ValidationConfCache.getConfig(BMConstants.VALID_CONF_KEY_TERMINFO_TERMBN));
			// 校验终端序列号
			ValidationHelper.validate(sn, 
					ValidationConfCache.getConfig(BMConstants.VALID_CONF_KEY_TERMINFO_TERMSN));
			
			// 校验文件内终端序号是否重复
			if (existingSn.contains(sn)) {
				return "处理失败: 文件内终端序号重复";			
			} else {
				existingSn.add(sn);
			}

			// 校验数据库中是否已存在终端序号
			ITermInfoService termInfoService = DBHessionServiceClient.getService(ITermInfoService.class);
			TermInfo t = termInfoService.selectByTermSn(sn);
			if (t != null) {
				return "处理失败: 终端序号已存在";
			}
			
			// 构造终端信息
			TermInfo termInfo = new TermInfo();
			termInfo.setBatNo(batNo);
			termInfo.setTermMn(mn);
			termInfo.setTermBn(bn);
			termInfo.setTermSn(sn);
			
			// 终端主密钥生成 
			// 1. 随机生成裸终端主密钥
			// 2. 使用存储密钥加密
			// 3. 转成16进制字符串存储
			byte[] rowTmk = EncodeUtils.genRawKey();
			//byte[] encryptTmk = EncryptUtil.TripleDESEncrypt(Constant.KEY, rowTmk);
			byte[] encryptTmk = IcpayDbmKeyHSM.instance().encrypt(rowTmk);
			
			termInfo.setTmk(EncodeUtils.hexEncode(encryptTmk));
			
			
			// 激活密钥生成（一个批次号对应的激活密钥相同） 
			// 1. 先根据批次号查找数据库是否已存在，如果存在，则使用已存在的激活密钥
			// 2. 如果不存在，则随机生成裸激活密钥
			// 3. 使用存储密钥加密
			// 4. 转成16进制字符串存储
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("termBn", bn);
			List<TermInfo> lst = termInfoService.select(paramMap);
			if (lst != null && lst.size() > 0) {
				termInfo.setTak(lst.get(0).getTak());
			} else {
				byte[] rowTak = EncodeUtils.genRawKey();
				//byte[] encryptTak = EncryptUtil.TripleDESEncrypt(Constant.KEY, rowTak);
				byte[] encryptTak = IcpayDbmKeyHSM.instance().encrypt(rowTak);
				termInfo.setTak(EncodeUtils.hexEncode(encryptTak));
			}

			// 设置导入操作员ID
			termInfo.setOperId(this.getSessionUser().getUsrId());
			termInfoService.add(termInfo);
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
