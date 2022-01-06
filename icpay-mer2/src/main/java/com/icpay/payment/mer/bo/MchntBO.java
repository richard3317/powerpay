package com.icpay.payment.mer.bo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfoKey;
import com.icpay.payment.db.service.IMchntInfoService;
import com.icpay.payment.db.service.IMchntUserInfoService;

@Component("mchntBO")
public class MchntBO extends BaseBO {

	/**
	 * 获取商户信息
	 * 
	 * @param mchntCd
	 * @return
	 */
	public MchntInfo getMchnt(String mchntCd) {
		AssertUtil.strIsBlank(mchntCd, "mchnt is blank.");

		IMchntInfoService mchntService = this.getDBService(IMchntInfoService.class);
		MchntInfo mchnt = mchntService.selectByPrimaryKey(mchntCd);
		return mchnt;
	}

	/**
	 * 获取商户信息
	 * 
	 * @param mchntCd
	 * @return
	 */
	public MchntUserInfo selectMchntUserInfo(String mchntCd, String userId) {
		AssertUtil.strIsBlank(mchntCd, "mchnt is blank.");
		AssertUtil.strIsBlank(userId, "userId is blank.");

		IMchntUserInfoService svc = this.mchntUserSvc();
		MchntUserInfo rec = svc.selectByPrimaryKey(new MchntUserInfoKey(mchntCd, userId));
		return rec;
	}

	/**
	 * 获取商户的所有用户信息
	 * 
	 * @param mchntCd
	 * @return
	 */
	public List<MchntUserInfo> selectMchntUserList(String mchntCd, String userId, String role) {
		AssertUtil.strIsBlank(mchntCd, "mchnt is blank.");
		IMchntUserInfoService svc = this.mchntUserSvc();
		/*
		 * MchntUserInfoExample example = new MchntUserInfoExample();
		 * example.createCriteria().andMchntCdEqualTo(mchntCd);
		 */
		Map<String, String> map = new HashMap<String, String>();
		map.put("mchntCd", mchntCd);
		map.put("userId", userId);
		map.put("role", role);
		
		List<MchntUserInfo> rec = svc.selectByExample(map);

		return rec;
	}

	public void updateByMchntUserSelective(String mchntCd, String userId, String otpSecret) {
		IMchntUserInfoService svc = this.mchntUserSvc();
		MchntUserInfo record = new MchntUserInfo();
		record.setOtpSecret(otpSecret);
		Map<String, String> map = new HashMap<>();
		map.put("mchntCd", mchntCd);
		map.put("userId", userId);
		/*
		 * MchntUserInfoExample example = new MchntUserInfoExample();
		 * example.createCriteria().andMchntCdEqualTo(mchntCd).andUserIdEqualTo(userId);
		 */
		svc.updateByExampleSelective(record, map);
	}
	/**
	 * 增加商户的用户方法 insertSelective
	 */
	public int insertSelective(MchntUserInfo mchntUserInfo) {
		IMchntUserInfoService svc = this.mchntUserSvc();
		mchntUserInfo.setRecCrtTs(new Date());
		int insertStute  = svc.insertSelective(mchntUserInfo);
		return insertStute;
	}
	
	/**
	 * 修改商户的用户方法 (重置密码)
	 */
	public int updatePwdSelective(MchntUserInfo mchntUserInfo) {
		IMchntUserInfoService svc = this.mchntUserSvc();
		mchntUserInfo.setRecUpdTs(new Date());
		int insertStute=0;
		 insertStute = svc.updateByPrimaryKeySelective(mchntUserInfo);
		if(insertStute>0) {//成功
			return insertStute;
		}else {
			//失败
		return insertStute;
		
		}
	}
	

	/**
	 * 删除商户的用户方法 
	 */
	public int delSelective(MchntUserInfo mchntUserInfo) {
		IMchntUserInfoService svc = this.mchntUserSvc();
		mchntUserInfo.setRecUpdTs(new Date());
		int delStute= svc.deleteByPrimaryKey(mchntUserInfo);
			return delStute;
	}
	

	
	
	

}
