package com.icpay.payment.db.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.mapper.CashPoolInfoMapper;
import com.icpay.payment.db.dao.mybatis.mapper.MchntInfoMapper;
import com.icpay.payment.db.dao.mybatis.mapper.MerCashPoolInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.CashPoolInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlCashPoolInfoView;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntInfoExample;
import com.icpay.payment.db.dao.mybatis.model.MerCashPoolInfo;
import com.icpay.payment.db.dao.mybatis.model.MerCashPoolInfoExample;
import com.icpay.payment.db.dao.mybatis.model.MerCashPoolInfoExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.MerCashPoolInfoKey;
import com.icpay.payment.db.dao.mybatis.model.TxnRoutingExample;
import com.icpay.payment.db.service.IMerCashPoolInfoService;

@Service("MerCashPoolInfoService")
public class MerCashPoolInfoService extends BaseService implements IMerCashPoolInfoService {
	
	@Override
	public Pager<MerCashPoolInfo> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		MerCashPoolInfoExample example = this.getQryExample(qryParamMap);
		example.setOrderByClause("rec_upd_ts desc");
		MerCashPoolInfoMapper mapper = getMapper();
		Pager<MerCashPoolInfo> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		
		CashPoolInfoMapper CashPoolInfoMapper = this.getMapper(CashPoolInfoMapper.class);
		List<MerCashPoolInfo> resultList = new ArrayList<MerCashPoolInfo>();
		List<MerCashPoolInfo> list = mapper.selectByPage(example);
		
		//?????????????????????????????????
		if(StringUtil.isNotBlank(qryParamMap.get("currCd"))) {
			//???????????????????????????
			String currCd =  qryParamMap.get("currCd").toString();
			//????????????????????????????????????
			for(MerCashPoolInfo result:list) {
				//????????????????????????id????????????
				 CashPoolInfo info = CashPoolInfoMapper.selectByPrimaryKey(result.getPoolId());
				 if(info != null) {
					 if(info.getCurrCd().equals(currCd)) {
						 resultList.add(result);
					 }
				 }
			}
		}else {
			resultList = list;
		}
		
		pager.setResultList(resultList);
		
		
		
		return pager;
	}
	
	
	@Override
	public MerCashPoolInfo selectByPrimaryKey(MerCashPoolInfoKey key) {
		return getMapper().selectByPrimaryKey(key);
	}
	
	/**
	 * ??????
	 */
	public void add(MerCashPoolInfo info) {
		i18ArgIsNull(info, this.getClass().getSimpleName(), "????????????????????????????????????null");
		String poolId = info.getPoolId();
		String mchntCd = info.getMchntCd();
		i18StrIsBlank(poolId, this.getClass().getSimpleName(), "??????????????????");
		i18StrIsBlank(mchntCd, this.getClass().getSimpleName(), "???????????????");
		
		MerCashPoolInfoKey key = new MerCashPoolInfoKey();
		key.setMchntCd(mchntCd);
		key.setPoolId(poolId);
		MerCashPoolInfo m = this.selectByPrimaryKey(key);
		i18ObjIsNotNull(m, this.getClass().getSimpleName(), "??????????????????????????????: %s", poolId);

		Date now = new Date();
		info.setRecCrtTs(now);
		info.setRecUpdTs(now);
		
		getMapper().insert(info);
	}
	
	/**
	 * ??????
	 */
	public void update(MerCashPoolInfo info, Map<String, String> qryParamMap) {
		i18ArgIsNull(info, this.getClass().getSimpleName(), "????????????????????????????????????null");

		MerCashPoolInfoExample example = this.getQryExample(qryParamMap);
		info.setRecUpdTs(new Date());
		
		getMapper().updateByExampleSelective(info,example);
	}
	
	/**
	 * ??????
	 * @param funcCd
	 * @return
	 */
	public void delete(MerCashPoolInfoKey key) {
		i18ArgIsBlank(key.getMchntCd(), this.getClass().getSimpleName(), "?????????????????????");
		i18ArgIsBlank(key.getPoolId(), this.getClass().getSimpleName(), "?????????id????????????");

		MerCashPoolInfo m = this.selectByPrimaryKey(key);
		i18ObjIsNull(m, this.getClass().getSimpleName(), "??????????????????????????????????????? %s", key.getPoolId());

		getMapper().deleteByPrimaryKey(key);
	}
	
	private MerCashPoolInfoMapper getMapper() {
		return this.getMapper(MerCashPoolInfoMapper.class);
	}

	@Override
	protected MerCashPoolInfoExample buildQryExample(Map<String, String> qryParamMap) {
		MerCashPoolInfoExample example = new MerCashPoolInfoExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();


			
			// ????????????????????????
			String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdEqualTo(mchntCd);
			}
			
			//String mchntName
			String mchntName = StringUtil.trim(qryParamMap.get("mchntName"));
			if (!StringUtil.isBlank(mchntName)) {
				MchntInfoMapper mdao = getMapper(MchntInfoMapper.class);
				MchntInfoExample mex= new MchntInfoExample();
				MchntInfoExample.Criteria c1 = mex.createCriteria().andMchntCnAbbrLike("%"+mchntName+"%");
				MchntInfoExample.Criteria c2 = mex.or().andMchntCnNmLike("%"+mchntName+"%");
				List<MchntInfo> mers=mdao.selectByExample(mex);
				if ((mers!=null)&& mers.size()>0) {
					List<String> merIds= new ArrayList<>();
					for(MchntInfo m: mers) {
						merIds.add(m.getMchntCd());
					}
					c.andMchntCdIn(merIds);
				}
			}
			
			// ?????????????????????
			String poolId = StringUtil.trim(qryParamMap.get("poolId"));
			if (!StringUtil.isBlank(poolId)) {
				c.andPoolIdEqualTo(poolId);
			}
			
			// ???????????????
			String state = StringUtil.trim(qryParamMap.get("state"));
			if (!StringUtil.isBlank(state)) {
				c.andStateEqualTo(state);
			}
			
			// ???????????????
			String comment = StringUtil.trim(qryParamMap.get("comment"));
			if (!StringUtil.isBlank(comment)) {
				c.andCommentLike("%" + comment + "%");
			}
		}
		return example;
	}


	@Override
	public List<MerCashPoolInfo> select(String mchntCd) {
		MerCashPoolInfoMapper mapper = getMapper();
		MerCashPoolInfoExample example =  new MerCashPoolInfoExample();
		example.createCriteria().andMchntCdEqualTo(mchntCd);
		return mapper.selectByPage(example);
	}


	@Override
	public List<MerCashPoolInfo> selectByPoolId(String poolId) {
		MerCashPoolInfoMapper mapper = getMapper();
		MerCashPoolInfoExample example =  new MerCashPoolInfoExample();
		example.createCriteria().andPoolIdEqualTo(poolId);
		return mapper.selectByPage(example);
	}


	@Override
	public void updateByPrimaryKey(MerCashPoolInfo info) {
		getMapper().updateByPrimaryKeySelective(info);
	}


	@Override
	public int batchUpdate(Map<String, String> qryParamMap, MerCashPoolInfo merCashPoolInfo) {
		MerCashPoolInfoExample example = this.getQryExample(qryParamMap);
		MerCashPoolInfoMapper mapper = this.getMapper(MerCashPoolInfoMapper.class);
		
		return mapper.updateByExampleSelective(merCashPoolInfo, example);
	}


	@Override
	public int batchDelete(Map<String, String> qryParamMap) {
		MerCashPoolInfoExample example = this.getQryExample(qryParamMap);
		MerCashPoolInfoMapper mapper = this.getMapper(MerCashPoolInfoMapper.class);
		return mapper.deleteByExample(example);
	}


	@Override
	public int batchAdd(List<String> mchntCdList, MerCashPoolInfo merCashPoolInfo) {
		if (mchntCdList.size() ==0) return 0;

		MerCashPoolInfoMapper mapper = this.getMapper(MerCashPoolInfoMapper.class);
		int cnt=0;
		for(String mchntCd: mchntCdList) {
			merCashPoolInfo.setMchntCd(mchntCd);
			merCashPoolInfo.setRecCrtTs(new Date());
			merCashPoolInfo.setRecUpdTs(new Date());
			cnt += mapper.insertSelective(merCashPoolInfo);
		}
		return cnt;
	}


	@Override
	public List<MerCashPoolInfo> select(MerCashPoolInfoExample qryParamMap) {
		MerCashPoolInfoMapper mapper = this.getMapper(MerCashPoolInfoMapper.class);
		return mapper.selectByExample(qryParamMap);
	}
}
