package com.icpay.payment.db.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.MchntUserInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfoExample;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfoExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfoKey;
import com.icpay.payment.db.service.IMchntUserInfoService;

@Service("mchntUserInfoService")
public class MchntUserInfoService extends BaseService implements IMchntUserInfoService {

	@Override
	public long countByExample(MchntUserInfoExample example) {
		return dao().countByExample(example);
	}

	@Override
	public int deleteByExample(MchntUserInfoExample example) {
		return dao().deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(MchntUserInfoKey key) {
		return dao().deleteByPrimaryKey(key);
	}

	@Override
	public int insert(MchntUserInfo record) {
		return dao().insert(record);
	}

	@Override
	public int insertSelective(MchntUserInfo record) {
		return dao().insertSelective(record);
	}

	/*@Override
	public List<MchntUserInfo> selectByPage(MchntUserInfoExample example) {
		return dao().selectByPage(example);
	}*/

	@Override
	public List<MchntUserInfo> selectByExample(Map<String,String> qryParamMap) {
		/*MchntUserInfoExample example = new MchntUserInfoExample();
		Criteria c = example.createCriteria();
		c.andMchntCdEqualTo(map.get("mchntCd"));
		if(!Utils.isEmpty(map.get("userId"))) {
			c.andUserIdEqualTo(map.get("userId"));
		}
		if(!Utils.isEmpty(map.get("role"))) {
			c.andUserRoleEqualTo(map.get("role"));
		}*/
		MchntUserInfoExample example = this.getQryExample(qryParamMap);
		return dao().selectByExample(example);
	}
	
	@Override
	public Pager<MchntUserInfo> selectByPage(int pageNum, int pageSize,Map<String,String> qryParamMap) {
		MchntUserInfoExample example = this.getQryExample(qryParamMap);
		Pager<MchntUserInfo> pager = buildPager(pageNum, pageSize, qryParamMap);
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);

		pager.setTotal(dao().countByExample(example));
		pager.setResultList(dao().selectByPage(example));
		return pager;
	}

	@Override
	public MchntUserInfo selectByPrimaryKey(MchntUserInfoKey key) {
		return dao().selectByPrimaryKey(key);
	}

	@Override
	public int updateByExampleSelective(MchntUserInfo record, Map<String,String> map) {
		MchntUserInfoExample example = new MchntUserInfoExample();
		example.createCriteria().andMchntCdEqualTo(map.get("mchntCd")).andUserIdEqualTo(map.get("userId"));
		return dao().updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(MchntUserInfo record, MchntUserInfoExample example) {
		return dao().updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(MchntUserInfo record) {
		return dao().updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MchntUserInfo record) {
		return dao().updateByPrimaryKey(record);
	}
	
	private MchntUserInfoMapper dao() {
		return this.getMapper(MchntUserInfoMapper.class);
	}	
	
	@Override
	protected MchntUserInfoExample buildQryExample(Map<String, String> qryParamMap) {
		MchntUserInfoExample example = new MchntUserInfoExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件:商户代码
			String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdEqualTo(mchntCd);
			}
			// 查询条件:用户名
			String userId = StringUtil.trim(qryParamMap.get("userId"));
			if (!StringUtil.isBlank(userId)) {
				c.andUserIdEqualTo(userId);
			}

			// 查询条件:状态
			String userState = StringUtil.trim(qryParamMap.get("userState"));
			if (!StringUtil.isBlank(userState)) {
				c.andUserStateEqualTo(userState);
			}

			// 查询条件：角色
			String userRole = StringUtil.trim(qryParamMap.get("userRole"));
			if (!StringUtil.isBlank(userRole)) {
				c.andUserRoleEqualTo(userRole);
			}

			// 查询条件：OTP单次验证密码
			String otpSecret = StringUtil.trim(qryParamMap.get("otpSecret"));
			if (!StringUtil.isBlank(otpSecret)) {
				c.andOtpSecretEqualTo(otpSecret);
			}
			
		}
		// 排序字段
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}

}
