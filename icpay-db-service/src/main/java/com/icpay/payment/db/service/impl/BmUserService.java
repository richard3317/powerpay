package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.BmUserInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.BmUserInfo;
import com.icpay.payment.db.dao.mybatis.model.BmUserInfoExample;
import com.icpay.payment.db.dao.mybatis.model.BmUserInfoExample.Criteria;
import com.icpay.payment.db.service.IBmUserService;

@Service("bmUserService")
public class BmUserService extends BaseService implements IBmUserService  {

	private static final Logger logger = Logger.getLogger(BmUserService.class);
	
	public Pager<BmUserInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		logger.info("分页查询用户信息开始");
		
		BmUserInfoExample example = this.getQryExample(qryParamMap);
		Pager<BmUserInfo> pager = this.buildPager(pageNum, pageSize, qryParamMap);
		BmUserInfoMapper mapper = this.getMapper();
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		
		logger.info("分页查询用户信息完成");
		return pager;
	}
	
	/**
	 * 根据主键查询
	 * @param usrId
	 * @return
	 */
	public BmUserInfo selectByPrimaryKey(String usrId) {
		BmUserInfo usrInfo = this.getMapper().selectByPrimaryKey(usrId);
		i18ObjIsNull(usrInfo, this.getClass().getSimpleName(), "未找到用户信息: %s", usrId);

		return usrInfo;
	}
	
	/**
	 * 新增
	 * @param usrInfo
	 */
	@Transactional
	public void add(BmUserInfo usrInfo) {
		i18ArgIsNull(usrInfo, this.getClass().getSimpleName(), "待新增的用户信息对象为null");

		BmUserInfoMapper mapper = this.getMapper();
		BmUserInfo u = mapper.selectByPrimaryKey(usrInfo.getUsrId());
		i18ObjIsNotNull(u, this.getClass().getSimpleName(), "待新增的用户信息已存在");

		
		logger.info("保存用户信息开始...");
		usrInfo.setDepartment("");
		usrInfo.setUsrSt(CommonEnums.RecSt.VALID.getCode());
		usrInfo.setLastLoginIp("");
		usrInfo.setLastLoginTs("");
		Date now = new Date();
		usrInfo.setRecCrtTs(now);
		usrInfo.setRecUpdTs(now);
		mapper.insert(usrInfo);
		logger.info("保存用户信息完成...");
	}
	
	/**
	 * 修改用户信息
	 * @param usrInfo
	 * @param isUpdatePwd 是否修改密码
	 */
	@Transactional
	public void update(BmUserInfo usrInfo, boolean isUpdatePwd) {
		i18ArgIsNull(usrInfo, this.getClass().getSimpleName(), "待更新的用户信息对象为null");
		BmUserInfo dbUsrInf = this.selectByPrimaryKey(usrInfo.getUsrId());
		i18ObjIsNull(dbUsrInf, this.getClass().getSimpleName(), "待更新的用户信息不存在");

		logger.info("修改用户信息开始...");
		// 将带修改的信息更新至数据库记录中并保存
		BeanUtil.cloneEntity(usrInfo, dbUsrInf, new String[] {
				"roleId", "usrNm", "otpSecret", "phone", "email", "usrSt"
		});
		if (isUpdatePwd) {
			dbUsrInf.setPassword(usrInfo.getPassword());
		}
		dbUsrInf.setRecUpdTs(new Date());
		this.getMapper().updateByPrimaryKey(dbUsrInf);
		logger.info("修改用户信息完成...");
	}
	
	/**
	 * 修改用户登录
	 * @param usrInfo
	 * @param isUpdatePwd 是否修改密码
	 */
	public void updateLoginInfo(BmUserInfo usrInfo) {
		i18ArgIsNull(usrInfo, this.getClass().getSimpleName(), "待更新的用户信息对象为null");
		BmUserInfo dbUsrInf = this.selectByPrimaryKey(usrInfo.getUsrId());
		i18ObjIsNull(dbUsrInf, this.getClass().getSimpleName(), "待更新的用户信息不存在");
		logger.info("修改用户最近登录信息开始...");
		// 将带修改的信息更新至数据库记录中并保存
		dbUsrInf.setLastLoginIp(usrInfo.getLastLoginIp());
		dbUsrInf.setLastLoginTs(usrInfo.getLastLoginTs());
		dbUsrInf.setRecUpdTs(new Date());
		this.getMapper().updateByPrimaryKey(dbUsrInf);
		logger.info("修改用户最近登录信息完成...");
	}
	
	/**
	 * 删除
	 * @param usrId
	 * @return
	 */
	@Transactional
	public void delete(String usrId) {
		BmUserInfo usr = this.selectByPrimaryKey(usrId);
		i18ObjIsNull(usr, this.getClass().getSimpleName(), "待删除的用户信息不存在");
		this.getMapper().deleteByPrimaryKey(usrId);
	}
	
	@Override
	protected BmUserInfoExample buildQryExample(Map<String, String> qryParamMap) {
		BmUserInfoExample example = new BmUserInfoExample();
		Criteria c = example.createCriteria();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			if (!StringUtil.isBlank(qryParamMap.get("usrId"))) {
				c.andUsrIdLike("%" + qryParamMap.get("usrId") + "%");
			}
			String roleId = qryParamMap.get("roleId");
			if (!StringUtil.isBlank(roleId)) {
				c.andRoleIdEqualTo(Integer.valueOf(roleId));
			}
			if (!StringUtil.isBlank(qryParamMap.get("usrNm"))) {
				c.andUsrNmLike("%" + qryParamMap.get("usrNm") + "%");
			}
		}
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}
	
	private BmUserInfoMapper getMapper() {
		return this.getMapper(BmUserInfoMapper.class);
	}
}
