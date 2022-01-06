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
		
		//如果前端有填寫幣別條件
		if(StringUtil.isNotBlank(qryParamMap.get("currCd"))) {
			//前端的幣別欄位條件
			String currCd =  qryParamMap.get("currCd").toString();
			//條件蒐集的小商編的資金池
			for(MerCashPoolInfo result:list) {
				//取小商編資金池的id去抓幣別
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
	 * 新增
	 */
	public void add(MerCashPoolInfo info) {
		i18ArgIsNull(info, this.getClass().getSimpleName(), "待保存的资金池信息对象为null");
		String poolId = info.getPoolId();
		String mchntCd = info.getMchntCd();
		i18StrIsBlank(poolId, this.getClass().getSimpleName(), "资金池码为空");
		i18StrIsBlank(mchntCd, this.getClass().getSimpleName(), "商户号为空");
		
		MerCashPoolInfoKey key = new MerCashPoolInfoKey();
		key.setMchntCd(mchntCd);
		key.setPoolId(poolId);
		MerCashPoolInfo m = this.selectByPrimaryKey(key);
		i18ObjIsNotNull(m, this.getClass().getSimpleName(), "资金池信息信息已存在: %s", poolId);

		Date now = new Date();
		info.setRecCrtTs(now);
		info.setRecUpdTs(now);
		
		getMapper().insert(info);
	}
	
	/**
	 * 修改
	 */
	public void update(MerCashPoolInfo info, Map<String, String> qryParamMap) {
		i18ArgIsNull(info, this.getClass().getSimpleName(), "待更新的资金池信息对象为null");

		MerCashPoolInfoExample example = this.getQryExample(qryParamMap);
		info.setRecUpdTs(new Date());
		
		getMapper().updateByExampleSelective(info,example);
	}
	
	/**
	 * 删除
	 * @param funcCd
	 * @return
	 */
	public void delete(MerCashPoolInfoKey key) {
		i18ArgIsBlank(key.getMchntCd(), this.getClass().getSimpleName(), "商户号不能为空");
		i18ArgIsBlank(key.getPoolId(), this.getClass().getSimpleName(), "资金池id不能为空");

		MerCashPoolInfo m = this.selectByPrimaryKey(key);
		i18ObjIsNull(m, this.getClass().getSimpleName(), "待删除的资金池信息不存在： %s", key.getPoolId());

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


			
			// 查询条件：商户号
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
			
			// 查询条件：渠道
			String poolId = StringUtil.trim(qryParamMap.get("poolId"));
			if (!StringUtil.isBlank(poolId)) {
				c.andPoolIdEqualTo(poolId);
			}
			
			// 查询条件：
			String state = StringUtil.trim(qryParamMap.get("state"));
			if (!StringUtil.isBlank(state)) {
				c.andStateEqualTo(state);
			}
			
			// 查询条件：
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
