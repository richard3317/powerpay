package com.icpay.payment.db.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.MchntInfoMapper;
import com.icpay.payment.db.dao.mybatis.mapper.MerAccountExtMapper;
import com.icpay.payment.db.dao.mybatis.mapper.MerAccountInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntInfoExample;
import com.icpay.payment.db.dao.mybatis.model.MerAccountInfo;
import com.icpay.payment.db.dao.mybatis.model.MerAccountInfoExample;
import com.icpay.payment.db.dao.mybatis.model.MerAccountInfoExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.MerAccountInfoKey;
import com.icpay.payment.db.dao.mybatis.model.TxnRouting;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MerAccountInfoSummaryAddRealAavailable;
import com.icpay.payment.db.service.IMchntAccountService;
import com.icpay.payment.db.service.IMchntInfoService;
import com.icpay.payment.db.service.ITxnRoutingService;

@Service("mchntAccountService")
public class MchntAccountService extends BaseService implements IMchntAccountService {

	@Override
	public List<MerAccountInfo> select(Map<String, String> qryParamMap) {
		MerAccountInfoExample example = this.getQryExample(qryParamMap);
		return getMapper().selectByExample(example);
	}
	
	@Override
	public List<MerAccountInfo> selectByExample(MerAccountInfoExample example){
		return getMapper().selectByExample(example);
	}

	@Override
	public Pager<MerAccountInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		MerAccountInfoExample example = this.getQryExample(qryParamMap);
		MerAccountInfoMapper mapper = getMapper();
		Pager<MerAccountInfo> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}

	@Override
	public MerAccountInfo selectByPrimaryKey(MerAccountInfoKey key) {
		return getMapper().selectByPrimaryKey(key);
	}

	/**
	 * 新增
	 */
	@Override
	public void add(MerAccountInfo mchntInfo) {
		i18ObjIsNull(mchntInfo, this.getClass().getSimpleName(), "待新增的记录为null");

		AssertUtil.strIsBlank(mchntInfo.getMchntCd(), "mchntCd is blank.");
		mchntInfo.setState(CommonEnums.RecSt.VALID.getCode());
		Date now = new Date();
		mchntInfo.setRecCrtTs(now);
		mchntInfo.setRecUpdTs(now);
		
		this.getMapper().insert(mchntInfo);
	}
	
	/**
	 * 更新
	 */
	@Override
	public void update(MerAccountInfo merAccountInfo) {
		i18ObjIsNull(merAccountInfo, this.getClass().getSimpleName(), "待修改的记录为null");
		MerAccountInfoKey key = new MerAccountInfoKey();
		key.setMchntCd(merAccountInfo.getMchntCd());
		key.setCurrCd(merAccountInfo.getCurrCd());
		MerAccountInfo dbEntity = this.selectByPrimaryKey(key);
		i18ObjIsNull(dbEntity, this.getClass().getSimpleName(), "该会员商户账户信息不存在");

		
		// 更新数据库字段
		BeanUtil.cloneEntity(merAccountInfo, dbEntity, new String[] {
				"availableBalance", "frozenBalance", "state"
		});
		dbEntity.setRecUpdTs(new Date());
		
		// 保存至数据库
		getMapper().updateByPrimaryKey(merAccountInfo);
	}
	
	/**
	 * 删除
	 */
	@Override
	public void delete(MerAccountInfoKey key) {
		AssertUtil.strIsBlank(key.getMchntCd(), "mchntCd is blank.");
		AssertUtil.strIsBlank(key.getCurrCd(), "currCd is blank.");
		getMapper().deleteByPrimaryKey(key);
	}
	
	@Override
	protected MerAccountInfoExample buildQryExample(Map<String, String> qryParamMap) {
		MerAccountInfoExample example = new MerAccountInfoExample();
		String defaultOrderBy = "available_balance DESC";
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件：商户代码
			String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdLike("%" + mchntCd + "%");
			}
			
			// 查询条件：幣別
			String currCd = StringUtil.trim(qryParamMap.get("currCd"));
			if (!StringUtil.isBlank(currCd)) {
				c.andCurrCdEqualTo(currCd);
			}
            List<String> merIds= new ArrayList<>();
			//渠道商户代码
            String chnlMchntCd = StringUtil.trim(qryParamMap.get("chnlMchntCd"));
            if(!StringUtil.isBlank(chnlMchntCd)){
                //先通过渠道商户代码去查找商户代码
                ITxnRoutingService service = new TxnRoutingService();
                Map<String, String> qryParamMap_chnl=new HashMap<>();
                qryParamMap_chnl.put("chnlMchntCd",chnlMchntCd);
                List<TxnRouting> select = service.select(qryParamMap_chnl);
                //前端商户集合
                for (TxnRouting txnRouting_mapping : select) {
                    merIds.add(txnRouting_mapping.getMchntCd());
                }
            }

			// 查询条件:状态
			String state = StringUtil.trim(qryParamMap.get("state"));
			if (!StringUtil.isBlank(state)) {
				c.andStateEqualTo(state);
			}
			
			//String mchntName
			String mchntName = StringUtil.trim(qryParamMap.get("mchntName"));
			if (!StringUtil.isBlank(mchntName)) {
				MchntInfoMapper mdao = getMapper(MchntInfoMapper.class);
				MchntInfoExample mex= new MchntInfoExample();
				//无需找寻商户中文简称
				//MchntInfoExample.Criteria c1 = mex.createCriteria().andMchntCnAbbrLike("%"+mchntName+"%");
				MchntInfoExample.Criteria c2 = mex.createCriteria().andMchntCnNmLike("%"+mchntName+"%");
				List<MchntInfo> mers=mdao.selectByExample(mex);
				if ((mers!=null)&& mers.size()>0) {
					for(MchntInfo m: mers) {
						merIds.add(m.getMchntCd());
					}
				}
			}
			
			//站点ID
            String siteId = StringUtil.trim(qryParamMap.get("siteId"));
            if(!StringUtil.isBlank(siteId)){
                //先通过站点ID去查找商户代码
            	IMchntInfoService service = new MchntInfoService();
                Map<String, String> qryParamMap_siteId = new HashMap<>();
                qryParamMap_siteId.put("siteId", siteId);
                List<MchntInfo> select = service.select(qryParamMap_siteId);
                //前端商户集合
                if ((select!=null)&& select.size()>0) {
                	for (MchntInfo mchntInfo_mapping : select) {
                    	merIds.add(mchntInfo_mapping.getMchntCd());
                    }
                }
            }

			if(merIds.size()>0){
			    c.andMchntCdIn(merIds);
            }
			
			// 排序字段
			String timeQryMethod = StringUtil.trim(qryParamMap.get("timeQryMethod"));
			if (BmEnums.QryMethod.ByRealAvailableBalance.getCode().equals(timeQryMethod)) {
				defaultOrderBy = "(case when (available_balance - obligated_balance) > 0 then (available_balance - obligated_balance) ELSE 0 end) DESC";
			}
			else if (BmEnums.QryMethod.ByAvailableBalance.getCode().equals(timeQryMethod)) {
				defaultOrderBy = "available_balance DESC";
			}
			else if (BmEnums.QryMethod.ByFrozenBalance.getCode().equals(timeQryMethod)) {
				defaultOrderBy = "frozen_balance DESC";
			}
			else if (BmEnums.QryMethod.ByFrozenT1Balance.getCode().equals(timeQryMethod)) {
				defaultOrderBy = "frozen_t1_balance DESC";
			}
			else if (BmEnums.QryMethod.ByDayTxnAmt.getCode().equals(timeQryMethod)) {
				defaultOrderBy = "dayTxnAmt DESC";
			}
			else if (BmEnums.QryMethod.ByDayWithdrawAmt.getCode().equals(timeQryMethod)) {
				defaultOrderBy = "dayWithdrawAmt DESC";
			}
			else if (BmEnums.QryMethod.ByDayTxnCount.getCode().equals(timeQryMethod)) {
				defaultOrderBy = "dayTxnCount DESC";
			}
		}
		example.setOrderByClause(defaultOrderBy);
		return example;
	}
	
	private MerAccountInfoMapper getMapper() {
		return this.getMapper(MerAccountInfoMapper.class);
	}
	
	private MerAccountExtMapper getExtMapper() {
		return this.getMapper(MerAccountExtMapper.class);
	}
	
	@Override
	public String selectSummary(Map<String, String> qryParamMap) {
		MerAccountInfoExample example = this.getQryExample(qryParamMap);
		String sum = getExtMapper().selectSummary(example);
		return sum;
	}
	
	@Override
	public MerAccountInfoSummaryAddRealAavailable selectInfoSummary(Map<String, String> qryParamMap) {
		MerAccountInfoExample example = this.getQryExample(qryParamMap);
		MerAccountInfoSummaryAddRealAavailable sum = getExtMapper().selectInfoSummary(example);
		return sum;
	}
}
