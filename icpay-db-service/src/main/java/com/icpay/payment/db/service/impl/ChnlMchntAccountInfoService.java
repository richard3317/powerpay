package com.icpay.payment.db.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.mapper.ChnlMchntAccountInfoMapper;
import com.icpay.payment.db.dao.mybatis.mapper.ChnlMchntInfoMapper;
import com.icpay.payment.db.dao.mybatis.mapper.MerAccountExtMapper;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountInfoExample;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountInfoExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountInfoKey;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoExample;
import com.icpay.payment.db.dao.mybatis.model.MerAccountInfo;
import com.icpay.payment.db.dao.mybatis.model.TxnRouting;
import com.icpay.payment.db.dao.mybatis.model.TxnRouting_Mapping;
import com.icpay.payment.db.dao.mybatis.model.modelExt.ChnlAccountInfoSummary;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MerAccountInfoSummary;
import com.icpay.payment.db.service.IChnlMchntAccountInfoService;
import com.icpay.payment.db.service.IMchntAccountService;
import com.icpay.payment.db.service.ITxnRoutingService;

@Service("chnlMchntAccountInfoService")
public class ChnlMchntAccountInfoService extends BaseService implements IChnlMchntAccountInfoService {

    @Override
    public Pager<ChnlMchntAccountInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
        ChnlMchntAccountInfoExample example = this.getQryExample(qryParamMap);
        /*example.setOrderByClause("available_balance desc");*/
        ChnlMchntAccountInfoMapper mapper = getMapper();
        Pager<ChnlMchntAccountInfo> pager = buildPager(pageNum, pageSize, qryParamMap);

        example.setStartNum(pager.getStartNum());
        example.setPageSize(pageSize);
		/*
		ChnlMchntAccountInfo info = new ChnlMchntAccountInfo();
		if(!Utils.isEmpty(qryParamMap.get("chnlId")))
			info.setChnlId(qryParamMap.get("chnlId"));
		if(!Utils.isEmpty(qryParamMap.get("mchntCd")))
			info.setMchntCd(qryParamMap.get("mchntCd"));
		if(!Utils.isEmpty(qryParamMap.get("mchntCnNm")))
			info.setMchntCnNm(qryParamMap.get("mchntCnNm"));
		if(!Utils.isEmpty(qryParamMap.get("state")))
			info.setState(qryParamMap.get("state"));
		info.setStartNum(pager.getStartNum());
		info.setPageSize(pageSize);
		*/
        pager.setTotal(mapper.countByExample(example));
        pager.setResultList(mapper.selectByPage(example));
        return pager;
    }

    /**
     * 获取全部通道信息
     *
     * @return
     */
    @Override
    public List<ChnlMchntAccountInfo> getAllChnlInfo() {
        ChnlMchntAccountInfoExample example = new ChnlMchntAccountInfoExample();
        example.setOrderByClause("chnl_id asc");
        return this.getMapper().selectByExample(example);
    }

    @Override
    public ChnlMchntAccountInfo selectByPrimaryKey(ChnlMchntAccountInfoKey key) {
    	i18StrIsBlank(key.getChnlId(), this.getClass().getSimpleName(), "渠道编号为空");
    	return getMapper().selectByPrimaryKey(key);
    }

    /**
     * 新增
     */
    public void add(ChnlMchntAccountInfo chnlInfo) {
    	i18ArgIsNull(chnlInfo, this.getClass().getSimpleName(), "待保存的渠道信息对象为null");

        String chnlId = chnlInfo.getChnlId();
        i18StrIsBlank(chnlId, this.getClass().getSimpleName(), "渠道码为空");

        ChnlMchntAccountInfoKey key = new ChnlMchntAccountInfoKey();
        key.setChnlId(chnlId);
        key.setMchntCd(chnlInfo.getMchntCd());
        ChnlMchntAccountInfo m = this.selectByPrimaryKey(key);
        i18ObjIsNotNull(m, this.getClass().getSimpleName(), "渠道信息信息已存在: %s", chnlId);

        Date now = new Date();
        chnlInfo.setRecCrtTs(now);
        chnlInfo.setRecUpdTs(now);

        getMapper().insert(chnlInfo);
    }

    /**
     * 修改
     */
    public void update(ChnlMchntAccountInfo chnlInfo) {
    	i18ArgIsNull(chnlInfo, this.getClass().getSimpleName(), "待更新的渠道信息对象为null");
    	getMapper().updateByPrimaryKey(chnlInfo);
    }

    /**
     * 删除
     *
     * @param funcCd
     * @return
     */
    public void delete(ChnlMchntAccountInfoKey key) {
        i18ArgIsBlank(key.getChnlId(), this.getClass().getSimpleName(), "渠道编号不能为空");

        ChnlMchntAccountInfo m = this.selectByPrimaryKey(key);
        i18ObjIsNull(m, this.getClass().getSimpleName(), "待删除的渠道信息信息不存在： %s--%s", key.getChnlId(),key.getMchntCd());

        
        getMapper().deleteByPrimaryKey(key);
    }

    private ChnlMchntAccountInfoMapper getMapper() {
        return this.getMapper(ChnlMchntAccountInfoMapper.class);
    }

    private MerAccountExtMapper getExtMapper() {
        return this.getMapper(MerAccountExtMapper.class);
    }

    @Override
    protected ChnlMchntAccountInfoExample buildQryExample(Map<String, String> qryParamMap) {
        ChnlMchntAccountInfoExample example = new ChnlMchntAccountInfoExample();
        String defaultOrderBy = "available_balance DESC";

        if (qryParamMap != null && !qryParamMap.isEmpty()) {
            Criteria c = example.createCriteria();
            // 查询条件：渠道
            String chnlId = StringUtil.trim(qryParamMap.get("chnlId"));
            if (!StringUtil.isBlank(chnlId)) {
                c.andChnlIdLike("%" + chnlId + "%");
            }
            String currCd = StringUtil.trim(qryParamMap.get("currCd"));
            if (!StringUtil.isBlank(currCd)) {
            	c.andCurrCdLike("%" + currCd + "%");
            }

            List<String> list = new ArrayList<String>();
            // 查询条件：商户代码
            String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
            if (!StringUtil.isBlank(mchntCd)) {

                String[] split = mchntCd.split(",");
                for (String string : split) {
                    list.add(string);
                }
            }

            //String mchntCnNm
            String mchntCnNm = StringUtil.trim(qryParamMap.get("mchntCnNm"));
            if (!StringUtil.isBlank(mchntCnNm)) {
                ChnlMchntInfoMapper mdao = getMapper(ChnlMchntInfoMapper.class);
                ChnlMchntInfoExample mex = new ChnlMchntInfoExample();
                mex.createCriteria().andChnlMchntDescLike("%" + mchntCnNm + "%");
                List<ChnlMchntInfo> mers = mdao.selectByExample(mex);
                if ((mers != null) && mers.size() > 0) {
                    for (ChnlMchntInfo m : mers) {
                        list.add(m.getChnlMchntCd());
                    }

                }
            }

            // 查询条件:状态
            String state = StringUtil.trim(qryParamMap.get("state"));
            if (!StringUtil.isBlank(state)) {
                c.andStateEqualTo(state);
            }
            
         // 查询关联条件: 商户号
         	if(qryParamMap.containsKey("chnlMchntCdAssoc")) {
         		String chnlMchntCdAssoc = StringUtil.trim(qryParamMap.get("chnlMchntCdAssoc"));
         		if (!StringUtil.isBlank(chnlMchntCdAssoc)) {
         			c.andMchntCdLike(chnlMchntCdAssoc +"%");
         		}
         	}

            if (list.size() > 0) {
                System.err.println("最终结果为：" + list);
                c.andMchntCdIn(list);
            }


            // 排序字段
            String timeQryMethod = StringUtil.trim(qryParamMap.get("timeQryMethod"));
			if (BmEnums.QryMethod.ByRealAvailableBalance.getCode().equals(timeQryMethod)) {
				defaultOrderBy = "(case when (available_balance - obligated_balance) > 0 then (available_balance - obligated_balance) ELSE 0 end) DESC";
			} else if (BmEnums.QryMethod.ByAvailableBalance.getCode().equals(timeQryMethod)) {
                defaultOrderBy = "available_balance DESC";
            } else if (BmEnums.QryMethod.ByFrozenBalance.getCode().equals(timeQryMethod)) {
                defaultOrderBy = "frozen_balance DESC";
            } else if (BmEnums.QryMethod.ByFrozenT1Balance.getCode().equals(timeQryMethod)) {
                defaultOrderBy = "frozen_t1_balance DESC";
            } else if (BmEnums.QryMethod.ByDayTxnAmt.getCode().equals(timeQryMethod)) {
                defaultOrderBy = "dayTxnAmt DESC";
            } else if (BmEnums.QryMethod.ByDayWithdrawAmt.getCode().equals(timeQryMethod)) {
                defaultOrderBy = "dayWithdrawAmt DESC";
            } else if (BmEnums.QryMethod.ByDayTxnCount.getCode().equals(timeQryMethod)) {
                defaultOrderBy = "dayTxnCount DESC";
            }
        }

        example.setOrderByClause(defaultOrderBy);
        return example;
    }

    @Override
    public String selectSummaryByChnl(Map<String, String> qryParamMap) {
        ChnlMchntAccountInfoExample example = this.getQryExample(qryParamMap);
//		example.setOrderByClause("available_balance desc");
        String sum = getExtMapper().selectSummaryByChnl(example);
        return sum;
    }

    @Override
    public ChnlAccountInfoSummary selectInfoSummaryByChnl(Map<String, String> qryParamMap) {
        ChnlMchntAccountInfoExample example = this.getQryExample(qryParamMap);
        ChnlAccountInfoSummary sum = getExtMapper().selectInfoSummaryByChnl(example);
        return sum;
    }
}
