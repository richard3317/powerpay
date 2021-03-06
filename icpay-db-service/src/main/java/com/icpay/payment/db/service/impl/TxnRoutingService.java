package com.icpay.payment.db.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.mapper.TxnRoutingMapper;
import com.icpay.payment.db.dao.mybatis.mapper.ExtMapper.CustomExtMapper;
import com.icpay.payment.db.dao.mybatis.mapper.ExtMapper.TxnRoutingExtMapper;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.TxnRouting;
import com.icpay.payment.db.dao.mybatis.model.TxnRoutingExample;
import com.icpay.payment.db.dao.mybatis.model.TxnRoutingExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.TxnRoutingKey;
import com.icpay.payment.db.dao.mybatis.model.TxnRouting_Mapping;
import com.icpay.payment.db.service.IMchntInfoService;
import com.icpay.payment.db.service.ITxnRoutingService;

@Service("txnRoutingService")
public class TxnRoutingService extends BaseService implements ITxnRoutingService {
	
	@Override
	protected String getLogPrefix() {
		return "[TxnRoutingService]";
	}

	@Override
	public int batchUpdate(Map<String, String> qryParamMap, TxnRouting txnRouting) {
		TxnRoutingExample example = this.getQryExample(qryParamMap);
		TxnRoutingMapper mapper = getMapper();
		//List<TxnRouting>  list = mapper.selectByExample(example);
		//if (list==null) return 0;
		//if (list.size()==0) return 0;
		return mapper.updateByExampleSelective(txnRouting, example);
	}
	
	@Override
	public int batchDelete(Map<String, String> qryParamMap) {
		TxnRoutingExample example = this.getQryExample(qryParamMap);
		TxnRoutingMapper mapper = getMapper();
		return mapper.deleteByExample(example);
	}
	
	@Override
	public int batchAdd(String mchntCds, TxnRouting tr) {
		if (mchntCds==null)
			mchntCds = tr.getMchntCd();
		if (Utils.isEmpty(mchntCds)) return 0;
		List<String> mchntCdList = Utils.strSplitToList(mchntCds, "\r\n", true);
		this.debug("[batchAdd] mchnts: %s; add: %s", listToStr(mchntCdList), tr);
		TxnRoutingMapper mapper = getMapper();
		int cnt=0;
		for(String mchntCd: mchntCdList) {
			tr.setMchntCd(mchntCd);
			tr.setRecCrtTs(new Date());
			tr.setRecUpdTs(new Date());
			cnt += mapper.insertSelective(tr);
		}
		return cnt;
	}
	
	private String listToStr(List<String> list) {
		StringBuilder buf = new StringBuilder("[");
		for(String s: list) buf.append(s).append(",");
		buf.append("]");
		return buf.toString();
	}
	
	@Override
	public Pager<TxnRouting_Mapping> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		TxnRoutingExample example = this.getQryExample(qryParamMap);
		TxnRoutingMapper mapper = getMapper();
		CustomExtMapper mapper2=this.getMapper(CustomExtMapper.class);
		
		Pager<TxnRouting_Mapping> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper2.selectByPageByTxnRoutingMapping(example));
		return pager;
	}
	
	@Override
	public List<TxnRouting> select(Map<String, String> qryParamMap) {
		TxnRoutingExample example = this.getQryExample(qryParamMap);
		return this.getMapper().selectByExample(example);
	}
	
	
	@Override
	public TxnRouting selectByPrimaryKey(TxnRoutingKey key) {
		validateKey(key);
		return getMapper().selectByPrimaryKey(key);
	}
	
	@Override
	public List<TxnRoutingKey> selectByMchntCdAndCurrCd(TxnRoutingKey key) {
		return this.getMapper(TxnRoutingExtMapper.class).selectByMchntCdAndCurrCd(key);
	}
	
	/**
	 * ??????
	 */
	public void add(TxnRouting txnRouting) {
		AssertUtil.argIsNull(txnRouting, "txnRouting is null.");
		
		validateKey(txnRouting);
		
		Date now = new Date();
		txnRouting.setRecCrtTs(now);
		txnRouting.setRecUpdTs(now);
		this.getMapper().insert(txnRouting);
	}
	
	/**
	 * ??????
	 */
	public void update(TxnRouting txnRouting) {
		getMapper().updateByPrimaryKey(txnRouting);
	}
	
	/**
	 * ??????
	 * @param funcCd
	 * @return
	 */
	public void delete(TxnRoutingKey key) {
		getMapper().deleteByPrimaryKey(key);
	}
	
	private TxnRoutingMapper getMapper() {
		return this.getMapper(TxnRoutingMapper.class);
	}

	private void validateKey(TxnRoutingKey key) {
		AssertUtil.objIsNull(key, "key is null.");
		AssertUtil.strIsBlank(key.getMchntCd(), "mchntCd is blank.");
		AssertUtil.strIsBlank(key.getIntTransCd(), "intTransCd is blank.");
		//AssertUtil.strIsBlank(key.getPayType(), "payType is blank.");
		//AssertUtil.strIsBlank(key.getTermSnRegex(), "termSnRegex is blank.");
	}
	
	@Override
	protected TxnRoutingExample buildQryExample(Map<String, String> qryParamMap) {
		TxnRoutingExample example = new TxnRoutingExample();
		Criteria c = example.createCriteria();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			 //?????????????????????
			String currCd = StringUtil.trim(qryParamMap.get("currCd"));
			if (!StringUtil.isBlank(currCd)) {
				c.andCurrCdEqualTo(currCd);
			}
			
			String mchntCd = qryParamMap.get("mchntCd");
			if (!StringUtil.isBlank(mchntCd)) {
				List<String> list=new ArrayList<>();
				String[] split = mchntCd.split(",");
				for (String string : split) {
					list.add(string);
				}
				c.andMchntCdIn(list);
			}
			String chnlId = qryParamMap.get("chnlId");
			if (!StringUtil.isBlank(chnlId)) {
				c.andChnlIdEqualTo(chnlId);
			}
			String chnlMchntCd = qryParamMap.get("chnlMchntCd");
			if (!StringUtil.isBlank(chnlMchntCd)) {
				List<String> list=new ArrayList<String>();
				String[] split = chnlMchntCd.split(",");
				for (String string : split) {
					list.add(string);
				}
				c.andChnlMchntCdIn(list);
			}
			String status=qryParamMap.get("status");
			if (!StringUtil.isBlank(status)) {
				c.andStatusEqualTo(status);
			}
			
			String priority=qryParamMap.get("priority");
			if (!StringUtil.isBlank(priority)) {
				c.andPriorityEqualTo(Integer.parseInt(priority));
			}
			
			String intTransCd=qryParamMap.get("intTransCd");
			if (! Utils.isEmpty(intTransCd)) {
				c.andIntTransCdLike(intTransCd+"%");
			}
			else {
				String ifPay=qryParamMap.get("ifPay");
				if("1".equals(ifPay)){//??????
					c.andIntTransCdNotEqualTo("5210");
				}else if("0".equals(ifPay)){//??????
					c.andIntTransCdEqualTo("5210");
				}
			}
			
			// ??????????????????: ?????????
			if(qryParamMap.containsKey("chnlMchntCdAssoc")) {
				String chnlMchntCdAssoc = StringUtil.trim(qryParamMap.get("chnlMchntCdAssoc"));
				if (!StringUtil.isBlank(chnlMchntCdAssoc)) {
					c.andChnlMchntCdLike(chnlMchntCdAssoc +"%");
				}
			}
			
			//??????ID
			List<String> merIds = new ArrayList<>();
            String siteId = StringUtil.trim(qryParamMap.get("siteId"));
            if(!StringUtil.isBlank(siteId)){
                //???????????????ID?????????????????????
            	IMchntInfoService service = new MchntInfoService();
                Map<String, String> qryParamMap_siteId = new HashMap<>();
                qryParamMap_siteId.put("siteId", siteId);
                List<MchntInfo> select = service.select(qryParamMap_siteId);
                //??????????????????
                if ((select!=null)&& select.size()>0) {
                	for (MchntInfo mchntInfo_mapping : select) {
                    	merIds.add(mchntInfo_mapping.getMchntCd());
                    }
                	c.andMchntCdIn(merIds);
                } else {
                	merIds.add("");
                	c.andMchntCdIn(merIds);
                }
            }
		}
		// ??????????????????????????????????????????????????????
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}

}
