package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.BmRoleFuncMapper;
import com.icpay.payment.db.dao.mybatis.mapper.BmRoleInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.BmRoleFunc;
import com.icpay.payment.db.dao.mybatis.model.BmRoleFuncExample;
import com.icpay.payment.db.dao.mybatis.model.BmRoleInfo;
import com.icpay.payment.db.dao.mybatis.model.BmRoleInfoExample;
import com.icpay.payment.db.dao.mybatis.model.BmRoleInfoExample.Criteria;
import com.icpay.payment.db.service.IBmRoleService;

@Service("bmRoleService")
public class BmRoleService extends BaseService implements IBmRoleService {
	
	private static final Logger logger = Logger.getLogger(BmRoleService.class);
	
	/**
	 * 分页查询
	 * @param pager
	 * @param qryParamMap
	 */
	public Pager<BmRoleInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		logger.info("分页查询角色信息开始");

		BmRoleInfoExample example = this.buildRoleInfoExample(qryParamMap);
		BmRoleInfoMapper mapper = getRoleInfoMapper();
		Pager<BmRoleInfo> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		
		logger.info("分页查询角色信息完成");
		return pager;
	}
	
	/**
	 * 根据主键查询
	 * @param roleId
	 * @return
	 */
	@Override
	public BmRoleInfo selectByRoleId(int roleId) {
		return getRoleInfoMapper().selectByPrimaryKey(roleId);
	}
	
	/**
	 * 新增
	 * @param roleInfo
	 * @param funcSet
	 */
	public void add(BmRoleInfo roleInfo) {
		i18ArgIsNull(roleInfo, this.getClass().getSimpleName(), "待新增的角色信息对象为null");
		// 如下字段目前暂不通过页面录入
		logger.info("保存角色信息开始...");
		roleInfo.setDepartment("");
		roleInfo.setGrpNo("");
		roleInfo.setRoleSt(CommonEnums.RecSt.VALID.getCode());
		Date now = new Date();
		roleInfo.setRecCrtTs(now);
		roleInfo.setRecUpdTs(now);
		
		getRoleInfoMapper().insert(roleInfo);
		logger.info("保存角色信息完成...");		
	}
	
	/**
	 * 保存角色权限信息
	 * @param roleId
	 * @param funcSet
	 */
	@Override
	@Transactional
	public void saveRoleFunc(int roleId, Set<String> funcSet) {
		logger.info("保存角色权限信息开始，权限条数:" + funcSet.size());
		
		i18ObjIsNull(funcSet, this.getClass().getSimpleName(), "角色信息的功能权限列表为null");

		BmRoleInfo role = this.selectByRoleId(roleId);
		i18ObjIsNull(role, this.getClass().getSimpleName(), "角色不存在: %s", String.valueOf(roleId));

		
		// 先删除角色已有的权限
		BmRoleFuncMapper mapper = getRoleFuncMapper();
		BmRoleFuncExample example = this.buildRoleFuncExample(roleId);
		mapper.deleteByExample(example);
		
		// 依次添加给该角色分配的权限
		for (String funcCd : funcSet) {
			BmRoleFunc rf = new BmRoleFunc();
			rf.setRoleId(roleId);
			rf.setFuncCd(funcCd);
			rf.setRecCrtTs(new Date());
			mapper.insert(rf);
		}
		logger.info("保存角色权限信息完成，权限个数:" + funcSet.size());
	}
	
	@Override
	@Transactional
	public void insertRoleFunc(int roleId, Set<String> funcSet) {
		
		logger.info("新增角色权限信息开始，权限条数:" + funcSet.size());
		
		i18ObjIsNull(funcSet, this.getClass().getSimpleName(), "角色信息的功能权限列表为null");
		BmRoleInfo role = this.selectByRoleId(roleId);
		i18ObjIsNull(role, this.getClass().getSimpleName(), "角色不存在: %s", String.valueOf(roleId));

		BmRoleFuncMapper mapper = getRoleFuncMapper();
		
		// 依次添加给该角色分配的权限
		for (String funcCd : funcSet) {
			BmRoleFunc rf = new BmRoleFunc();
			rf.setRoleId(roleId);
			rf.setFuncCd(funcCd);
			rf.setRecCrtTs(new Date());
			mapper.insert(rf);
		}
		logger.info("新增角色权限信息完成，权限个数:" + funcSet.size());
	}
	
	@Override
	public List<BmRoleInfo> queryRoleId(String roleNm) {
		BmRoleInfoExample example = new BmRoleInfoExample();
		Criteria c = example.createCriteria();
		c.andRoleNmEqualTo(roleNm);
		return this.getRoleInfoMapper().selectByExample(example);
	}
	
	/**
	 * 修改角色信息
	 * @param roleInfo
	 */
	public void update(BmRoleInfo roleInfo) {
		i18ArgIsNull(roleInfo, this.getClass().getSimpleName(), "待更新的角色信息对象为null");

		BmRoleInfo dbRole = this.selectByRoleId(roleInfo.getRoleId());
		i18ObjIsNull(dbRole, this.getClass().getSimpleName(), "待更新的角色信息不存在");

		logger.info("修改角色信息开始...");
		
		dbRole.setRoleNm(roleInfo.getRoleNm()); // 目前仅允许修改角色名
		dbRole.setRecUpdTs(new Date()); // 更新修改时间戳
		getRoleInfoMapper().updateByPrimaryKey(dbRole);
		
		logger.info("修改角色信息完成...");
	}

	/**
	 * 删除角色信息
	 * @param funcCd
	 * @return
	 */
	public void delete(int roleId) {
		// 先检查到删除的角色信息是否存在
		BmRoleInfo role = this.selectByRoleId(roleId);
		i18ObjIsNull(role, this.getClass().getSimpleName(), "待删除的角色信息不存在");

		logger.info("删除角色信息开始...");
		// 删除角色权限信息
		BmRoleFuncMapper mapper = getRoleFuncMapper();
		BmRoleFuncExample example = this.buildRoleFuncExample(roleId);
		mapper.deleteByExample(example);
		// 删除角色信息
		getRoleInfoMapper().deleteByPrimaryKey(roleId);
		logger.info("删除角色信息完成...");
	}
	
	@Override
	public Set<String> selectRoleFunc(int roleId) {
		Set<String> st = new HashSet<String>();
		BmRoleFuncExample example = this.buildRoleFuncExample(roleId);
		List<BmRoleFunc> lst = this.getRoleFuncMapper().selectByExample(example);
		for (BmRoleFunc rf : lst) {
			st.add(rf.getFuncCd());
		}
		return st;
	}
	
	@Override
	public List<BmRoleInfo> getAllRoleInfo() {
		BmRoleInfoExample example = new BmRoleInfoExample();
		return this.getRoleInfoMapper().selectByExample(example);
	}
	
	protected BmRoleInfoExample buildRoleInfoExample(Map<String, String> qryParamMap) {
		BmRoleInfoExample example = new BmRoleInfoExample();
		Criteria c = example.createCriteria();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			String roleId = qryParamMap.get("roleId");
			if (!StringUtil.isBlank(roleId)) {
				Integer id = Integer.valueOf(qryParamMap.get("roleId"));
				c.andRoleIdEqualTo(id);
			}
			if (qryParamMap.get("roleNm") != null) {
				c.andRoleNmLike("%" + qryParamMap.get("roleNm") + "%");
			}
		}
		c.andRoleStEqualTo(CommonEnums.RecSt.VALID.getCode());
		// 按照模块代码、排序序号、功能代码排序
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}
	
	private BmRoleFuncExample buildRoleFuncExample(int roleId) {
		BmRoleFuncExample example = new BmRoleFuncExample();
		com.icpay.payment.db.dao.mybatis.model.BmRoleFuncExample.Criteria c = example.createCriteria();
		c.andRoleIdEqualTo(roleId);
		return example;
	}
	
	private BmRoleFuncMapper getRoleFuncMapper() {
		return this.getMapper(BmRoleFuncMapper.class);
	}
	
	private BmRoleInfoMapper getRoleInfoMapper() {
		return this.getMapper(BmRoleInfoMapper.class);
	}
}
