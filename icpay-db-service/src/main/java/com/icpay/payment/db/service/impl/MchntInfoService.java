package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.data.utils.SeqNums;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.PropUtils;
import com.icpay.payment.common.utils.PwdUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.MchntInfoMapper;
import com.icpay.payment.db.dao.mybatis.mapper.MchntUserInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntInfoExample;
import com.icpay.payment.db.dao.mybatis.model.MchntInfoExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfo;
import com.icpay.payment.db.service.IMchntInfoService;

@Service("mchntInfoService")
public class MchntInfoService extends BaseService implements IMchntInfoService {

	private static final Logger logger = Logger.getLogger(MchntInfoService.class);

	private static String INS_CD = "999";
	public static String ADMIN_USER = "admin";  //商户默认管理员
	public static String SU_ROLE = "su";  //商户默认管理员角色
	
	static {
		try {
			INS_CD = "999";
			INS_CD = PropUtils.getProperty("sysCfg.properties", "mer.id.prefix","999");
		} catch (Exception e) {
		}
	}

	@Override
	public List<MchntInfo> select(Map<String, String> qryParamMap) {
		MchntInfoExample example = this.getQryExample(qryParamMap);
		return getMapper().selectByExample(example);
	}

	@Override
	public Pager<MchntInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		MchntInfoExample example = this.getQryExample(qryParamMap);
		MchntInfoMapper mapper = getMapper();
		Pager<MchntInfo> pager = buildPager(pageNum, pageSize, qryParamMap);

		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);

		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}

	@Override
	public MchntInfo selectByPrimaryKey(String mchntCd) {
		return getMapper().selectByPrimaryKey(mchntCd);
	}

	/**
	 * 新增
	 */
	@Override
	public void add(MchntInfo mchntInfo) {
//		AssertUtil.objIsNull(mchntInfo, "待新增的记录为null");
//		AssertUtil.strIsBlank(mchntInfo.getAreaCd(), "areaCd is blank.");
//		AssertUtil.strIsBlank(mchntInfo.getMchntTp(), "mchntTp is blank.");

		MchntInfoMapper mapper = this.getMapper();

		// 商户代码生成规则: 999+地区码前4位+MCC(4位)+4位顺序数
		logger.info("生成商户代码开始");
//		String mchntCdPre = INS_CD + mchntInfo.getAreaCd().substring(0, 4) + mchntInfo.getMchntTp();
//		for (int i = 0; i < 9999; i++) {
//			int k = this.genIntKey(PrimaryKeyTp._03);
//			String mchntCdSuf = StringUtil.leftPad(String.valueOf(k), 4, '0');
//			String mchntCd = mchntCdPre + mchntCdSuf;
//			if (mapper.selectByPrimaryKey(mchntCd) == null) {
//				logger.info("商户代码生成成功:" + mchntCd);
//				mchntInfo.setMchntCd(mchntCd);
//				break;
//			}
//		}
		
		try {
			Long i = (System.currentTimeMillis()/1000/86400);
			String prefix= (INS_CD +"00"+ i.toString()).substring(0, 10);
			String mchntCd = prefix+SeqNums.nextSeq(SeqNums.MER_ID);
			mchntInfo.setMchntCd(mchntCd);
			logger.info(String.format("生成商户代码成功，商户号: %s, %s", mchntCd, mchntInfo.getMchntCnNm()));
		} catch (Exception e) {
			logger.error(String.format("生成商户代码错误，商户: %s", mchntInfo.getMchntCnNm()), e);
		}
		
		i18StrIsBlank(mchntInfo.getMchntCd(), this.getClass().getSimpleName(), "商户号生成失败");

		mchntInfo.setInsCd(INS_CD); // 机构代码暂定为999
		//mchntInfo.setLoginPwd(PwdUtil.computeMD5Pwd(mchntInfo.getMchntCd().substring(5))); // 商户网站登录密码默认为商户号后10位
		mchntInfo.setLoginPwd(PwdUtil.computeMD5Pwd(StringUtil.randomPwd(10))); // 初始化密码，商户网站默认密码
		mchntInfo.setShareCertMchntCd("");
		mchntInfo.setTransType(Constant.MCHNT_DFT_TXN_TYPES);
		mchntInfo.setPayType(Constant.MCHNT_DFT_PAY_TYPES);
		mchntInfo.setTrustDomain("");
		mchntInfo.setMchntSt(CommonEnums.RecSt.VALID.getCode());
		Date now = new Date();
		mchntInfo.setRecCrtTs(now);
		mchntInfo.setRecUpdTs(now);
		mchntInfo.setLastLoginIp("");
		mchntInfo.setLastLoginTs("");

		mapper.insert(mchntInfo);
		//同时生成默认的管理用户
		insertUser(mchntInfo);
	}

	/**
	 * 更新
	 */
	@Override
	public void update(MchntInfo mchntInfo) {
		i18ObjIsNull(mchntInfo, this.getClass().getSimpleName(), "待修改的记录为null");
		MchntInfo dbmchntInfo = this.selectByPrimaryKey(mchntInfo.getMchntCd());
		i18ObjIsNull(dbmchntInfo, this.getClass().getSimpleName(), "该会员商户信息不存在");
		// 更新数据库字段
		BeanUtil.cloneEntity(mchntInfo, dbmchntInfo, new String[] {
				"mchntCnNm", "mchntEnNm", "mchntCnAbbr", "mchntEnAbbr", "mchntAddr", "zipCd", "loginPwd", "agentCd",
				"riskFlag", "tradeType", "contactPerson", "contactPhone", "contactMail", "fax", "transType", "mchntSt", 
				"transTypeGroupId", "expiredDt", "lastOperId", "lastLoginIp", "lastLoginTs","otp_secret_login",
				"opt_secret_admin", "secret_init_state", "allowedReqSrc", "allowedReqSrcWd", "trustDomain", "comment",
				"apiType", "mchntUrlFlag", "notifyUrlPay", "pageReturnUrlPay", "notifyUrlWithdraw", "pageReturnUrlWithdraw"
		});
		dbmchntInfo.setRecUpdTs(new Date());

		// 保存至数据库
		getMapper().updateByPrimaryKey(dbmchntInfo);
	}

	
	/**
	 * 根据商户号更新Google验证
	 */
	@Override
	public void updateSecretInitState(MchntInfo mchntInfo) {
		i18ObjIsNull(mchntInfo, this.getClass().getSimpleName(), "待修改的记录为null");

		MchntInfo dbmchntInfo = this.selectByPrimaryKey(mchntInfo.getMchntCd());
		i18ObjIsNull(dbmchntInfo, this.getClass().getSimpleName(), "该会员商户信息不存在");

		
		/*// 更新数据库字段
		BeanUtil.cloneEntity(mchntInfo, dbmchntInfo, new String[] {
				"otp_secret_login","opt_secret_admin", "secret_init_state"
		});*/
		mchntInfo.setRecUpdTs(new Date());
		
		// 保存至数据库
		getMapper().updateByPrimaryKeySelective(mchntInfo);
	}
	
	public void updateLoginInfo(MchntInfo mchntInfo) {
		i18ObjIsNull(mchntInfo, this.getClass().getSimpleName(), "商户信息为空");

//		AssertUtil.objIsNull(mchntInfo, "待修改的记录为null");
//		MchntInfo dbmchntInfo = this.selectByPrimaryKey(mchntInfo.getMchntCd());
//		AssertUtil.objIsNull(dbmchntInfo, "该会员商户信息不存在");

		/*// 更新数据库字段
		BeanUtil.cloneEntity(mchntInfo, dbmchntInfo, new String[] {
				"otp_secret_login","opt_secret_admin", "secret_init_state"
		});*/
		mchntInfo.setRecUpdTs(new Date());

		// 保存至数据库
		int c = getMapper().updateByPrimaryKeySelective(mchntInfo);
		intIsZero(c, this.getClass().getSimpleName(), "商户 %s 不存在", mchntInfo.getMchntCd());
	}

	/**
	 * 删除
	 */
	@Override
	public void delete(String mchntCd) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		getMapper().deleteByPrimaryKey(mchntCd);
	}

	@Override
	protected MchntInfoExample buildQryExample(Map<String, String> qryParamMap) {
		MchntInfoExample example = new MchntInfoExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件:商户代码
			String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdEqualTo(mchntCd);
			}
			// 查询条件:商户中文名称(模糊匹配)
			String mchntCnNm = StringUtil.trim(qryParamMap.get("mchntCnNm"));
			if (!StringUtil.isBlank(mchntCnNm)) {
				c.andMchntCnNmLike("%" + mchntCnNm + "%");
			}
			// 查询条件:商户中文名称(精确匹配)
			String fullMchntCnNm = StringUtil.trim(qryParamMap.get("fullMchntCnNm"));
			if (!StringUtil.isBlank(fullMchntCnNm)) {
				c.andMchntCnNmEqualTo(fullMchntCnNm);
			}

			// 查询条件:商户英文名称(模糊匹配)
			String mchntEnNm = StringUtil.trim(qryParamMap.get("mchntEnNm"));
			if (!StringUtil.isBlank(mchntEnNm)) {
				c.andMchntEnNmLike("%" + mchntEnNm + "%");
			}

			// 查询条件:商户状态
			String mchntSt = StringUtil.trim(qryParamMap.get("mchntSt"));
			if (!StringUtil.isBlank(mchntSt)) {
				c.andMchntStEqualTo(mchntSt);
			}

			// 查询条件：失效日期截至日期
			String expiredEndDt = StringUtil.trim(qryParamMap.get("expiredEndDt"));
			if (!StringUtil.isBlank(expiredEndDt)) {
				c.andExpiredDtLessThan(expiredEndDt);
			}

			// 查询条件：代理商代码
			String agentCd = StringUtil.trim(qryParamMap.get("agentCd"));
			if (!StringUtil.isBlank(agentCd)) {
				c.andAgentCdEqualTo(agentCd);
			}

			// 查询条件：风险标识
			String riskFlg = StringUtil.trim(qryParamMap.get("riskFlg"));
			if (!StringUtil.isBlank(riskFlg)) {
				c.andRiskFlagEqualTo(riskFlg);
			}

			// 查询条件：行业类别
			String tradeType = StringUtil.trim(qryParamMap.get("tradeType"));
			if (!StringUtil.isBlank(tradeType)) {
				c.andTradeTypeEqualTo(tradeType);
			}

			// 查询条件：OTP单次验证密码-登入用
			String otpSecretLogin = StringUtil.trim(qryParamMap.get("otpSecretLogin"));
			if (!StringUtil.isBlank(otpSecretLogin)) {
				c.andOtpSecretLoginEqualTo(otpSecretLogin);
			}

			// 查询条件：OTP单次验证密码-管理者
			String optSecretAdmin = StringUtil.trim(qryParamMap.get("optSecretAdmin"));
			if (!StringUtil.isBlank(optSecretAdmin)) {
				c.andOptSecretAdminEqualTo(optSecretAdmin);
			}

			// 查询条件：安全设置是否已完成,1=已完成
			String secretInitState = StringUtil.trim(qryParamMap.get("secretInitState"));
			if (!StringUtil.isBlank(secretInitState)) {
				c.andSecretInitStateEqualTo(secretInitState);
			}
			
			// 查询条件：接入方式
			String apiType = StringUtil.trim(qryParamMap.get("apiType"));
			if (!StringUtil.isBlank(apiType)) {
				c.andApiTypeLike("%" + apiType + "%");
			}
			
			// 查询条件：域名
			String trustDomain = StringUtil.trim(qryParamMap.get("trustDomain"));
			if (!StringUtil.isBlank(trustDomain)) {
				c.andTrustDomainLike("%" + trustDomain + "%");
			}
			
			// 查询条件：站点
		    String siteId = StringUtil.trim(qryParamMap.get("siteId"));
		    if (!StringUtil.isBlank(siteId)) {
		        c.andSiteIdEqualTo(siteId);
		    }
			
		}
		// 排序字段
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}

	private MchntInfoMapper getMapper() {
		return this.getMapper(MchntInfoMapper.class);
	}
	
	protected void insertUser(MchntInfo mchntInfo) {
		MchntUserInfoMapper mapper = (MchntUserInfoMapper) this.getMapper(MchntUserInfoMapper.class);
		MchntUserInfo record = new MchntUserInfo();
		record.setMchntCd(mchntInfo.getMchntCd());
		record.setUserId(ADMIN_USER); // 默认的管理员
		record.setLoginPwd(mchntInfo.getLoginPwd());
		record.setPwdUpdTs(DateUtil.now19());
		record.setUserState(CommonEnums.MchntUserSt._0.getCode());
		record.setUserRole(SU_ROLE);
		record.setComment("系统默认管理员账户");
		mapper.insertSelective(record);
	}
	
	/**
	 * 新增后回传商户号
	 */
	@Override
	public String addAndReturnMchntCd(MchntInfo mchntInfo) {
		String mchntCd = "";
		i18ObjIsNull(mchntInfo, this.getClass().getSimpleName(), "待新增的记录为null");

		MchntInfoMapper mapper = this.getMapper();

		// 商户代码生成规则: 999+地区码前4位+MCC(4位)+4位顺序数
		logger.info("生成商户代码开始");
		
		try {
			Long i = (System.currentTimeMillis()/1000/86400);
			String prefix= (INS_CD +"00"+ i.toString()).substring(0, 10);
			mchntCd = prefix+SeqNums.nextSeq(SeqNums.MER_ID);
			mchntInfo.setMchntCd(mchntCd);
			logger.info(String.format("生成商户代码成功，商户号: %s, %s", mchntCd, mchntInfo.getMchntCnNm()));
		} catch (Exception e) {
			logger.error(String.format("生成商户代码错误，商户: %s", mchntInfo.getMchntCnNm()), e);
		}
		
		i18StrIsBlank(mchntInfo.getMchntCd(), this.getClass().getSimpleName(), "商户代码生成失败");

		mchntInfo.setInsCd(INS_CD); // 机构代码暂定为999
		//mchntInfo.setLoginPwd(PwdUtil.computeMD5Pwd(mchntInfo.getMchntCd().substring(5))); // 商户网站登录密码默认为商户号后10位
		mchntInfo.setLoginPwd(PwdUtil.computeMD5Pwd(StringUtil.randomPwd(10))); // 初始化密码，商户网站默认密码
		mchntInfo.setShareCertMchntCd("");
		mchntInfo.setTransType(Constant.MCHNT_DFT_TXN_TYPES);
		mchntInfo.setPayType(Constant.MCHNT_DFT_PAY_TYPES);
		mchntInfo.setMchntSt(CommonEnums.RecSt.VALID.getCode());
		Date now = new Date();
		mchntInfo.setRecCrtTs(now);
		mchntInfo.setRecUpdTs(now);
		mchntInfo.setLastLoginIp("");
		mchntInfo.setLastLoginTs("");

		mapper.insert(mchntInfo);
		//同时生成默认的管理用户
		insertUser(mchntInfo);
		return mchntCd;
	}
}
