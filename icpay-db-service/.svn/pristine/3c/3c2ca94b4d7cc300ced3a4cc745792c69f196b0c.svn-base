package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.AgentFuncInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.AgentFuncInfo;
import com.icpay.payment.db.dao.mybatis.model.AgentFuncInfoExample;
import com.icpay.payment.db.dao.mybatis.model.AgentFuncInfoExample.Criteria;
import com.icpay.payment.db.service.IAgentFuncInfoService;

@Service("agentFuncInfoService")
public class AgentFuncInfoService extends BaseService implements IAgentFuncInfoService {
	
	private static final Logger logger = Logger.getLogger(AgentFuncInfoService.class);
	
	@Override
	public List<AgentFuncInfo> select(Map<String, String> qryParamMap) {
		AgentFuncInfoExample example = buildQryExample(qryParamMap);
		return getMapper().selectByExample(example);
	}
	
	@Override
	public Pager<AgentFuncInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		logger.info("分页查询功能权限信息开始");
		
		AgentFuncInfoExample example = this.getQryExample(qryParamMap);
		Pager<AgentFuncInfo> pager = buildPager(pageNum, pageSize, qryParamMap);
		AgentFuncInfoMapper mapper = getMapper();
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		
		logger.info("分页查询功能权限信息完成");
		return pager;
	}
	
	@Override
	public AgentFuncInfo selectByPrimaryKey(String funcCd) {
		i18StrIsBlank(funcCd, this.getClass().getSimpleName(), "功能码为空");
		return getMapper().selectByPrimaryKey(funcCd);
	}
	
	/**
	 * 新增
	 * @param funcInfo
	 * @return
	 */
	public void add(AgentFuncInfo funcInfo) {
		i18ArgIsNull(funcInfo, this.getClass().getSimpleName(), "待保存的功能权限对象为null");
		String funcCd = funcInfo.getFuncCd();
		i18StrIsBlank(funcCd, this.getClass().getSimpleName(), "功能码为空");
		AgentFuncInfo m = this.selectByPrimaryKey(funcCd);
		i18ObjIsNotNull(m, this.getClass().getSimpleName(), "功能权限信息已存在: %s", funcCd);
		funcInfo.setFuncSt(CommonEnums.RecSt.VALID.getCode());
		funcInfo.setModuleCd(funcInfo.getFuncCd().substring(0, 2));
		funcInfo.setRecCrtTs(new Date());
		
		// 添加权限信息
		getMapper().insert(funcInfo);
	}
	
	/**
	 * 修改
	 * @param funcInfo
	 * @return
	 */
	public void update(AgentFuncInfo funcInfo) {
		i18ArgIsNull(funcInfo, this.getClass().getSimpleName(), "待更新的功能权限对象为null");
		String funcCd = funcInfo.getFuncCd();
		i18StrIsBlank(funcCd, this.getClass().getSimpleName(), "功能码不能为空");
		AgentFuncInfo dbFunc = this.selectByPrimaryKey(funcCd);
		i18ObjIsNull(dbFunc, this.getClass().getSimpleName(), "功能权限信息不存在： %s", funcCd);
		
		// 只能修改功能名称、链接和排序序号
		dbFunc.setFuncNm(funcInfo.getFuncNm());
		dbFunc.setFuncHref(funcInfo.getFuncHref());
		dbFunc.setFuncIdx(funcInfo.getFuncIdx());
		
		getMapper().updateByPrimaryKey(dbFunc);
	}
	
	/**
	 * 删除
	 * @param funcCd
	 * @return
	 */
	public void delete(String funcCd) {
		i18ArgIsBlank(funcCd, this.getClass().getSimpleName(), "功能代码不能为空");
		AgentFuncInfo m = this.selectByPrimaryKey(funcCd);
		i18ObjIsNull(m, this.getClass().getSimpleName(), "待删除的功能权限信息不存在： %s",funcCd);
		getMapper().deleteByPrimaryKey(funcCd);
	}
	
	/**
	 * 获取所有信息
	 * @return
	 */
	@Override
	public List<AgentFuncInfo> getAllAgentFuncInfo() {
		return this.select(null);
	}
	
	@Override
	protected AgentFuncInfoExample buildQryExample(Map<String, String> qryParamMap) {
		AgentFuncInfoExample example = new AgentFuncInfoExample();
		Criteria c = example.createCriteria();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			if (qryParamMap.get("funcCd") != null) {
				String funcCd = qryParamMap.get("funcCd");
				if (!StringUtil.isBlank(funcCd)) {
					c.andFuncCdLike("%" + StringUtil.trim(funcCd) + "%");
				}
			}
			if (qryParamMap.get("funcNm") != null) {
				String funcNm = qryParamMap.get("funcNm");
				if (!StringUtil.isBlank(funcNm)) {
					c.andFuncNmLike("%" + StringUtil.trim(funcNm) + "%");
				}
			}
			if (qryParamMap.get("funcTp") != null) {
				String funcTp = qryParamMap.get("funcTp");
				if (!StringUtil.isBlank(funcTp)) {
					c.andFuncTpEqualTo(funcTp);
				}
			}
		}
		c.andFuncStEqualTo(CommonEnums.RecSt.VALID.getCode());
		
		// 按照模块代码、排序序号、功能代码排序
		example.setOrderByClause("MODULE_CD asc, FUNC_IDX asc, FUNC_CD asc");
		return example;
	}
	
	private AgentFuncInfoMapper getMapper() {
		return this.getMapper(AgentFuncInfoMapper.class);
	}
}
