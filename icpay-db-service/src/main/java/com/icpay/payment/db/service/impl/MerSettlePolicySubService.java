package com.icpay.payment.db.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.SettleEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.MerSettleAlgorithmMapper;
import com.icpay.payment.db.dao.mybatis.mapper.MerSettlePolicySubMapper;
import com.icpay.payment.db.dao.mybatis.model.MerSettleAlgorithm;
import com.icpay.payment.db.dao.mybatis.model.MerSettleAlgorithmExample;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicySub;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicySubExample;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicySubExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicySubKey;
import com.icpay.payment.db.dao.mybatis.model.SettleAlgorithm;
import com.icpay.payment.db.dao.mybatis.model.TxnRouting;
import com.icpay.payment.db.service.IMerSettlePolicySubService;
import com.icpay.payment.db.service.ITxnRoutingService;

@Service("merSettlePolicySubService")
public class MerSettlePolicySubService extends BaseService implements IMerSettlePolicySubService {
	private static final String DFT_DAILY_LIMIT = "99999999999999";

	@Override
	public List<MerSettlePolicySub> select(String mchntCd) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		MerSettlePolicySubExample example = new MerSettlePolicySubExample();
		Criteria c = example.createCriteria();
		c.andMchntCdEqualTo(mchntCd);
		return this.getMapper().selectByExample(example);
	}
	
	@Override
	public MerSettlePolicySub selectByPrimaryKey(String mchntCd, String intTransCd,String currCd) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		AssertUtil.strIsBlank(intTransCd, "intTransCd is blank.");
		AssertUtil.strIsBlank(currCd, "currCd is blank.");
		MerSettlePolicySubKey k = new MerSettlePolicySubKey();
		k.setMchntCd(mchntCd);
		k.setIntTransCd(intTransCd);
		k.setCurrCd(currCd);
		return this.getMapper().selectByPrimaryKey(k);
	}
	
	@Override
	public void add(MerSettlePolicySub entity) {
		AssertUtil.objIsNull(entity, "entity is null.");
		Date now = new Date();
		entity.setRecCrtTs(now);
		entity.setRecUpdTs(now);
		this.getMapper().insert(entity);
	}

	@Override
	public void delete(String mchntCd, String intTransCd,String currCd) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		AssertUtil.strIsBlank(intTransCd, "intTransCd is blank.");
		AssertUtil.strIsBlank(currCd, "currCd is blank.");
		MerSettlePolicySubKey k = new MerSettlePolicySubKey();
		k.setMchntCd(mchntCd);
		k.setIntTransCd(intTransCd);
		k.setCurrCd(currCd);
		this.getMapper().deleteByPrimaryKey(k);
	}
	
	@Override
	public int update(MerSettlePolicySub entity) {
		i18ObjIsNull(entity, this.getClass().getSimpleName(), "?????????????????????null.");

		MerSettlePolicySubMapper mapper = this.getMapper();
		MerSettlePolicySub dbEntity = mapper.selectByPrimaryKey(entity);
		i18ObjIsNull(dbEntity, this.getClass().getSimpleName(), "???????????????????????????");

		
		// ?????????????????????
//		BeanUtil.cloneEntity(entity, dbEntity, new String[] {
//				"settleMode", "settleAlgorithm", "comment", "lastOperId"
//		});
		entity.setRecUpdTs(new Date());
		
		return getMapper().updateByPrimaryKey(entity);
	}
	
	@Override
	public int updateByExampleSelective(MerSettlePolicySub entity) {
		i18ObjIsNull(entity, this.getClass().getSimpleName(), "?????????????????????null.");

		MerSettlePolicySubMapper mapper = this.getMapper();
		MerSettlePolicySub dbEntity = mapper.selectByPrimaryKey(entity);
		i18ObjIsNull(dbEntity, this.getClass().getSimpleName(), "???????????????????????????");

		
		// ?????????????????????
//		BeanUtil.cloneEntity(entity, dbEntity, new String[] {
//				"settleMode", "settleAlgorithm", "comment", "lastOperId"
//		});
		entity.setRecUpdTs(new Date());
		
		return getMapper().updateByPrimaryKeySelective(entity);
	}

	private MerSettlePolicySubMapper getMapper() {
		return this.getMapper(MerSettlePolicySubMapper.class);
	}
	
	private MerSettleAlgorithmMapper getViewMapper() {
		return this.getMapper(MerSettleAlgorithmMapper.class);
	}

	/*@Override
	public Pager<SettleAlgorithm> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		MerSettlePolicySubExample example = new MerSettlePolicySubExample();
		example = this.buildQryExample(qryParamMap);
		Criteria c = example.createCriteria();
		if(!Utils.isEmpty(qryParamMap.get("mchntCd")))
			c.andMchntCdEqualTo(qryParamMap.get("mchntCd"));
		
		Pager<SettleAlgorithm> pager = new Pager<SettleAlgorithm>();
		pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(this.getMapper().countByExample(example));
		
		??????list 
		List<SettleAlgorithm> lst = buildAlgorithmList(this.getMapper().selectByPage(example));
		
		pager.setResultList(lst);
		return pager;
	}*/
	@Override
	public Pager<MerSettleAlgorithm> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		MerSettleAlgorithmExample example = new MerSettleAlgorithmExample();
		example = this.buildQryExample(qryParamMap);
		
		Pager<MerSettleAlgorithm> pager = new Pager<MerSettleAlgorithm>();
		pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(this.getViewMapper().countByExample(example));
		
		/*??????list */
		//List<SettleAlgorithm> lst = buildAlgorithmList(this.getViewMapper().selectByPage(example));
		
		//??????????????????????????????????????????????????????????????????(EX:20?????????)
		List<MerSettleAlgorithm> results = this.getViewMapper().selectByPage(example);
		
		List<MerSettleAlgorithm> newResults = new ArrayList<MerSettleAlgorithm>();	
		boolean newResultsFlag = false;
				
		String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
		//????????????????????????
		String transChnlSel = StringUtil.trim(qryParamMap.get("transChnl"));
		String chnlMchntCd = StringUtil.trim(qryParamMap.get("chnlMchntCd"));

		
		List<MerSettleAlgorithm> newResultsforPage = new ArrayList<MerSettleAlgorithm>();
		//????????????????????????????????????
		if(!StringUtil.isBlank(transChnlSel)) {
		
			//????????????????????????????????????????????????
			List<MerSettleAlgorithm> totalresult = this.getViewMapper().selectByExample(example);
			
			newResultsFlag = true; //?????????????????????????????????????????????
			
			Map<String, String> qryParamMap_chnl=new HashMap<>();

	        qryParamMap_chnl.put("chnlId",transChnlSel);
	        //??????????????????
			qryParamMap_chnl.put("status","1");
			
			//???????????????
			if (!StringUtil.isBlank(chnlMchntCd)) {
	            qryParamMap_chnl.put("chnlMchntCd",chnlMchntCd);
			}
			//?????????
			if (!StringUtil.isBlank(mchntCd)) {
	            qryParamMap_chnl.put("mchntCd",mchntCd);
			}
			// ???????????????????????????
			ITxnRoutingService svc = new TxnRoutingService();
			List<TxnRouting> routeList = svc.select(qryParamMap_chnl);
			
			if(routeList.size()>0) {
				
				//????????????????????????????????????????????????????????????????????????????????????????????????
				for (MerSettleAlgorithm result : totalresult) {
					for(TxnRouting route:routeList) {
						if(route.getMchntCd().equals(result.getMchntCd())) {
							if(route.getIntTransCd().equals(result.getIntTransCd())) {
								//?????????????????????
								if(!newResults.contains(result)) {
									newResults.add(result);
								}
							}
						}
					}
				}
				if(newResults.size()>0) {
					//?????????????????????????????????????????????
					newResultsforPage = startPage(newResults, pageNum, pageSize);
				}
				
			}
		}
		//?????????????????????????????????????????????????????????
		if(newResultsFlag) {
			pager.setResultList(newResultsforPage);
			pager.setTotal(newResults.size());
		}else {
			pager.setResultList(results); //???????????????
		}
		
		return pager;
	}
	
	@Override
	public List<MerSettleAlgorithm> selectByMap(Map<String, String> qryParamMap) {
		MerSettleAlgorithmExample example = new MerSettleAlgorithmExample();
		example = this.buildQryExample(qryParamMap);
		return this.getViewMapper().selectByPage(example);
	}
	
	//??????????????????????????????
//	private List<SettleAlgorithm> buildAlgorithmList(List<MerSettlePolicySub> lst) {
//		List<SettleAlgorithm> result = new ArrayList<SettleAlgorithm>();
//		for (MerSettlePolicySub mps : lst) {
//			SettleAlgorithm am = new SettleAlgorithm();
//			am.setMchntCd(mps.getMchntCd());
//			am.setIntTransCd(mps.getIntTransCd());
//			am.setIntTransCdDesc(EnumUtil.translate(SettleEnums.SettleTxnType.class, mps.getIntTransCd(), false));
//			am.setSettleMode(mps.getSettleMode());
//			am.setSettleModeDesc(EnumUtil.translate(SettleEnums.SettleMode.class, mps.getSettleMode(), false));
//			
//			am.setTxT0Percent(mps.getTxT0Percent());
//			
//			String settleAlgorithm=mps.getSettleAlgorithm();
//			Map<String, String> map = JSONObject.parseObject(settleAlgorithm, new TypeReference<Map<String, String>>() {
//			});
//			am.setMinFee(new BigDecimal(map.get("minFee")).divide(new BigDecimal("100")).toString());
//			am.setFixRate(map.get("fixRate"));
//			am.setMaxFee(new BigDecimal(map.get("maxFee")).divide(new BigDecimal("100")).toString());
//			
//			am.setTxDayMax(mps.getTxDayMax());
//			am.setTxAmtMin(mps.getTxAmtMin());
//			am.setTxAmtMax(mps.getTxAmtMax());
//			am.setTxTimeLimit(mps.getTxTimeLimit());
//			am.setTxT0Percent(mps.getTxT0Percent());
//			am.setTxCardDayMax(mps.getTxCardDayMax());
//			am.setComment(mps.getComment());
//			result.add(am);
//		}
//		return result;
//		
//	}
	
	@Override
	protected MerSettleAlgorithmExample buildQryExample(Map<String, String> qryParamMap) {
		MerSettleAlgorithmExample example = new MerSettleAlgorithmExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			com.icpay.payment.db.dao.mybatis.model.MerSettleAlgorithmExample.Criteria c = example.createCriteria();
			com.icpay.payment.db.dao.mybatis.model.MerSettleAlgorithmExample.Criteria c1 = example.createCriteria();
			com.icpay.payment.db.dao.mybatis.model.MerSettleAlgorithmExample.Criteria c2 = example.createCriteria();
			 
			String currCd = StringUtil.trim(qryParamMap.get("currCd"));
			if (!StringUtil.isBlank(currCd)) {
				c.andCurrCdEqualTo(currCd);
				c1.andCurrCdEqualTo(currCd);
				c2.andCurrCdEqualTo(currCd);
			}
			
			// ???????????????????????????
			String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdLike("%" + mchntCd + "%");
				c1.andMchntCdLike("%" + mchntCd + "%");
				c2.andMchntCdLike("%" + mchntCd + "%");
			}
			
			//mchntCnNm
			String mchntCnNm = StringUtil.trim(qryParamMap.get("mchntCnNm"));
			if (!StringUtil.isBlank(mchntCnNm)) {
				c.andMchntCnNmLike("%" + mchntCnNm + "%" );
				c1.andMchntCnNmLike("%" + mchntCnNm + "%" );
				c2.andMchntCnNmLike("%" + mchntCnNm + "%" );

			}
			
			// ???????????????????????????
			String intTransCd = StringUtil.trim(qryParamMap.get("intTransCd"));
			if (!StringUtil.isBlank(intTransCd)) {
				c.andIntTransCdLike( "%" + intTransCd +  "%");
				c1.andIntTransCdLike( "%" + intTransCd +  "%");
				c2.andIntTransCdLike( "%" + intTransCd +  "%");
			}
			
			/*??????*/
			String fixRate = StringUtil.trim(qryParamMap.get("fixRate"));
			if (!StringUtil.isBlank(fixRate)) {
				double fixRate1 = Double.parseDouble(fixRate);
				c.andFixRateEqualTo(fixRate1);
				c1.andFixRateEqualTo(fixRate1);
				c2.andFixRateEqualTo(fixRate1);
			}
			
			//???????????????
			String minFee = StringUtil.trim(qryParamMap.get("minFee"));
			if (!StringUtil.isBlank(minFee)) {
				String minFeeString =new BigDecimal(minFee).multiply(new BigDecimal("100")).toString();
				double minFeeDouble=Double.parseDouble(minFeeString);
				c.andMinFeeEqualTo(minFeeDouble);
				c1.andMinFeeEqualTo(minFeeDouble);
				c2.andMinFeeEqualTo(minFeeDouble);
			}
			
			//??????????????????
			String txAmtMin = StringUtil.trim(qryParamMap.get("txAmtMin"));
			if (!StringUtil.isBlank(txAmtMin)) {
				c.andTxAmtMinEqualTo(new BigDecimal(txAmtMin).multiply(new BigDecimal("100")).toString());
				c1.andTxAmtMinEqualTo(new BigDecimal(txAmtMin).multiply(new BigDecimal("100")).toString());
				c2.andTxAmtMinEqualTo(new BigDecimal(txAmtMin).multiply(new BigDecimal("100")).toString());
			}
			
			//??????????????????
			String txAmtMax = StringUtil.trim(qryParamMap.get("txAmtMax"));
			if (!StringUtil.isBlank(txAmtMax)) {
				c.andTxAmtMaxEqualTo(new BigDecimal(txAmtMax).multiply(new BigDecimal("100")).toString());
				c1.andTxAmtMaxEqualTo(new BigDecimal(txAmtMax).multiply(new BigDecimal("100")).toString());
				c2.andTxAmtMaxEqualTo(new BigDecimal(txAmtMax).multiply(new BigDecimal("100")).toString());
			}
			
			//?????????
			String txT0Percent = StringUtil.trim(qryParamMap.get("txT0Percent"));
			if (!StringUtil.isBlank(txT0Percent)) {
				c.andTxT0PercentEqualTo(txT0Percent);
				c1.andTxT0PercentEqualTo(txT0Percent);
				c2.andTxT0PercentEqualTo(txT0Percent);
			}
			//???????????????
			String keyWordType = StringUtil.trim(qryParamMap.get("keyWordType"));
			if (!StringUtil.isBlank(keyWordType)) {
				// 1:???????????????
				if (keyWordType.equals("1")) {
					c.andMchntCnNmNotLike("%??????%");
					c.andMchntCnNmNotLike("%??????%");
					c.andMchntCnNmNotLike("%??????%");
				// 0:???????????????
				}else if (keyWordType.equals("0")) {
					c.andMchntCnNmLike("%??????%");
					c1.andMchntCnNmLike("%??????%");
					c2.andMchntCnNmLike("%??????%");
					example.or(c1);
					example.or(c2);
				}

			}
		}
		// ????????????
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}

	public String parseAlgorithm(String newFixRate,String newMaxfee,String newMinfee) {
		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("minFee", this.judegIsNullMul100(newMinfee, "0"));
		reqMap.put("fixRate", this.judegIsNull(newFixRate, "0"));
		reqMap.put("maxFee", this.judegIsNullMul100(newMaxfee, DFT_DAILY_LIMIT));
		JSONObject Json = (JSONObject) JSON.toJSON(reqMap);
		return Json.toJSONString();
	}
	
	
	private String judegIsNull(String start, String result) {
		if ((start.trim() == null || "".equals(start.trim()))) {
			start = result;
		}
		return start;
	}
	
	private String judegIsNullMul100(String start, String result) {
		if ((start.trim() == null || "".equals(start.trim()))) {
			start = result;
		}else{
			long bd = new BigDecimal(start).multiply(new BigDecimal("100")).longValue();
			long bd2 = new BigDecimal(result).longValue();
			if(!"0".equals(result)){
				if(bd>=bd2){
					start=String.valueOf(bd2);
				}else{
					start=String.valueOf(bd);
				}	
			}else{
				start=String.valueOf(bd);
			}
		}
		return start;
	}
	
	
	public static List<MerSettleAlgorithm> startPage(List list, Integer pageNum, Integer pageSize) {
        if(list == null){
            return null;
        }
        if(list.size() == 0){
            return null;
        }
 
        Integer count = list.size(); //????????????
        Integer pageCount = 0; //??????
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }
 
        int fromIndex = 0; //????????????
        int toIndex = 0; //????????????
 
        if(pageNum > pageCount){
            pageNum = pageCount;
        }
        if (!pageNum.equals(pageCount)) {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        } else {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
        }
 
        List pageList = list.subList(fromIndex, toIndex);
 
        return pageList;
    }

	//???????????????????????????
	public Pager<MerSettleAlgorithm> selectByPage4Update(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		//??????????????????example
		MerSettleAlgorithmExample example = new MerSettleAlgorithmExample();
		example = this.buildQryExample(qryParamMap);
		
		Pager<MerSettleAlgorithm> pager = new Pager<MerSettleAlgorithm>();
		pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(this.getViewMapper().countByExample(example));
		
		//????????????????????????????????????
		List<MerSettleAlgorithm> totalresult = this.getViewMapper().selectByExample(example);
		
		List<MerSettleAlgorithm> newResults = new ArrayList<MerSettleAlgorithm>();	
		boolean newResultsFlag = false;
		
		String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
		// ?????????????????????
		String transChnlSel = StringUtil.trim(qryParamMap.get("transChnl"));
		String chnlMchntCd = StringUtil.trim(qryParamMap.get("chnlMchntCd"));

		//??????????????????????????????
		if(!StringUtil.isBlank(transChnlSel)) {
			
			newResultsFlag = true; //?????????????????????????????????????????????
			
			Map<String, String> qryParamMap_chnl=new HashMap<>();
			
			qryParamMap_chnl.put("chnlId", transChnlSel);
			// ???????????????
			qryParamMap_chnl.put("status", "1");

			//???????????????
			if (!StringUtil.isBlank(chnlMchntCd)) {
	            qryParamMap_chnl.put("chnlMchntCd",chnlMchntCd);
			}
			//?????????
			if (!StringUtil.isBlank(mchntCd)) {
	            qryParamMap_chnl.put("mchntCd",mchntCd);
			}
			// ???????????????????????????
			ITxnRoutingService svc = new TxnRoutingService();
			List<TxnRouting> routeList = svc.select(qryParamMap_chnl);
			
			if(routeList.size()>0) {
				//???????????????????????????????????????????????????????????????
				for (MerSettleAlgorithm result : totalresult) {
					for(TxnRouting route:routeList) {
						if(route.getMchntCd().equals(result.getMchntCd())) {
							if(route.getIntTransCd().equals(result.getIntTransCd())) {
								//?????????????????????
								if(!newResults.contains(result)) {
									newResults.add(result);
								}
							}
						}
					}
				}
			}

		}
		//?????????????????????????????????????????????
		if(newResultsFlag) {
			//???????????????????????????????????????????????????
			pager.setResultList(newResults);
			pager.setTotal(newResults.size());
		}else {
			pager.setResultList(totalresult);//?????????????????????
		}
		
		return pager;
	}

	@Override
	public List<MerSettlePolicySub> select(String mchntCd, String currCd) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		AssertUtil.strIsBlank(currCd, "currCd is blank.");
		MerSettlePolicySubExample example = new MerSettlePolicySubExample();
		Criteria c = example.createCriteria();
		c.andMchntCdEqualTo(mchntCd).andCurrCdEqualTo(currCd);
		return this.getMapper().selectByExample(example);
	}
	
}
