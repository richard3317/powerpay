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
import com.icpay.payment.db.dao.mybatis.mapper.BmFuncInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.BmFuncInfo;
import com.icpay.payment.db.dao.mybatis.model.BmFuncInfoExample;
import com.icpay.payment.db.dao.mybatis.model.BmFuncInfoExample.Criteria;
import com.icpay.payment.db.service.IBmFuncInfoService;

@Service("bmFuncInfoService")
public class BmFuncInfoService extends BaseService implements IBmFuncInfoService {
	
	private static final Logger logger = Logger.getLogger(BmFuncInfoService.class);
	
	@Override
	public List<BmFuncInfo> select(Map<String, String> qryParamMap) {
		BmFuncInfoExample example = buildQryExample(qryParamMap);
		return getMapper().selectByExample(example);
	}
	
	@Override
	public Pager<BmFuncInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		logger.info("分页查询功能权限信息开始");
		
		BmFuncInfoExample example = this.getQryExample(qryParamMap);
		Pager<BmFuncInfo> pager = buildPager(pageNum, pageSize, qryParamMap);
		BmFuncInfoMapper mapper = getMapper();
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		
		logger.info("分页查询功能权限信息完成");
		return pager;
	}
	
	@Override
	public BmFuncInfo selectByPrimaryKey(String funcCd) {
		i18StrIsBlank(funcCd, this.getClass().getSimpleName(), "功能码为空");
		return getMapper().selectByPrimaryKey(funcCd);
	}
	
	/**
	 * 新增
	 * @param funcInfo
	 * @return
	 */
	public void add(BmFuncInfo funcInfo) {
		i18ArgIsNull(funcInfo, this.getClass().getSimpleName(), "待保存的功能权限对象为null");
		String funcCd = funcInfo.getFuncCd();
		i18StrIsBlank(funcCd, this.getClass().getSimpleName(), "功能码为空");
		BmFuncInfo m = this.selectByPrimaryKey(funcCd);
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
	public void update(BmFuncInfo funcInfo) {
		i18ArgIsNull(funcInfo, this.getClass().getSimpleName(), "待更新的功能权限对象为null");
		String funcCd = funcInfo.getFuncCd();
		i18StrIsBlank(funcCd, this.getClass().getSimpleName(), "功能码不能为空");
		BmFuncInfo dbFunc = this.selectByPrimaryKey(funcCd);
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
		BmFuncInfo m = this.selectByPrimaryKey(funcCd);
		i18ObjIsNull(m, this.getClass().getSimpleName(), "待删除的功能权限信息不存在： %s", funcCd);

		getMapper().deleteByPrimaryKey(funcCd);
	}
	
	/**
	 * 获取所有信息
	 * @return
	 */
	@Override
	public List<BmFuncInfo> getAllBmFuncInfo() {
		return this.select(null);
	}
	
	@Override
	protected BmFuncInfoExample buildQryExample(Map<String, String> qryParamMap) {
		BmFuncInfoExample example = new BmFuncInfoExample();
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
	
	private BmFuncInfoMapper getMapper() {
		return this.getMapper(BmFuncInfoMapper.class);
	}
}
