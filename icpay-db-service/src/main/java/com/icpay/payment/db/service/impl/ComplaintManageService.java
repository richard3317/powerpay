package com.icpay.payment.db.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.db.dao.mybatis.mapper.ComplaintManageMapper;
import com.icpay.payment.db.dao.mybatis.model.ComplaintManage;
import com.icpay.payment.db.dao.mybatis.model.ComplaintManageExample;
import com.icpay.payment.db.dao.mybatis.model.Content;
import com.icpay.payment.db.dao.mybatis.model.ComplaintManageExample.Criteria;
import com.icpay.payment.db.service.IComplaintManageService;

@Service("complaintManageService")
public class ComplaintManageService extends BaseService implements IComplaintManageService {

	@Override
	public List<ComplaintManage> select(Map<String, String> qryParamMap) {
		// TODO Auto-generated method stub
		ComplaintManageExample qryExample = this.getQryExample(qryParamMap);
		return getMapper(ComplaintManageMapper.class).selectByExample(qryExample);
	}

	@Override
	public Pager<ComplaintManage> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		// TODO Auto-generated method stub
		ComplaintManageExample example = this.getQryExample(qryParamMap);
		ComplaintManageMapper mapper = getMapper();
		Pager<ComplaintManage> pager = buildPager(pageNum, pageSize, qryParamMap);
		if (example != null) {
			example.setStartNum(pager.getStartNum());
			example.setPageSize(pageSize);
		}
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}

	@Override
	public ComplaintManage selectByPrimaryKey(int comId) {
		// TODO Auto-generated method stub
		return this.getMapper().selectByPrimaryKey(comId);
	}

	@Override
	public int add(ComplaintManage entity) {
		// TODO Auto-generated method stub
		AssertUtil.objIsNull(entity, "entity is null.");
		return this.getMapper().insert(entity);
	}

	@Override
	public void update(ComplaintManage entity) {
		// TODO Auto-generated method stub
		i18ObjIsNull(entity, this.getClass().getSimpleName(), "?????????????????????null");
		ComplaintManage dbEntity = this.selectByPrimaryKey(entity.getComId());
		i18ObjIsNull(dbEntity, this.getClass().getSimpleName(), "???????????????????????????");
		// ?????????????????????
		BeanUtil.cloneEntity(entity, dbEntity,
				new String[] { "siteDesc", "complaintType", "chnlId", "complaintRequire", "processState",
						"processResult", "upDealWith", "upFreezeMoney", "upFreezeDiffer", "upReturnMoney",
						"mchntDealWith", "mchntFreezeMoney", "mchntFreezeDiffer", "mchntFreezeReMoney",
						"mchntFreezeCd", "mchntReturnMoney", "complaintor", "complaintorPhone", "accName",
						"dealHistory", "terraceTransId", "mchntTransId", "dealMoney", "complaintCard",
						"bankName", "dealType", "dealDt", "dealTm", "mchntCd", "mchntCnNm", "chnlMchntCd",
						"chnlMchntDesc", "dealProcessResult", "complaintMoney", "isDelete" });
		dbEntity.setRecUpdTs(new Date());
		// ??????????????????
		getMapper().updateByPrimaryKey(dbEntity);

	}

	@Override
	public void delete(int ComplaintManageId) {
		// TODO Auto-generated method stub

	}

	private ComplaintManageMapper getMapper() {
		return this.getMapper(ComplaintManageMapper.class);
	}

	@Override
	protected ComplaintManageExample buildQryExample(Map<String, String> qryParamMap) {
		// TODO Auto-generated method stub
		ComplaintManageExample example = new ComplaintManageExample();
		Criteria createCriteria = example.createCriteria();

		String siteDesc = qryParamMap.get("siteDesc");
		// ????????????
		if (siteDesc != null && !"".equals(siteDesc)) {
			createCriteria.andSiteDescEqualTo(Integer.valueOf(siteDesc));
		}

		//???????????????
        String terraceTransId=qryParamMap.get("terraceTransId");
        if (terraceTransId != null && !"".equals(terraceTransId)) {
            createCriteria.andTerraceTransIdLike("%"+terraceTransId+"%");
        }

		// ????????????
		String mchntCnNm = qryParamMap.get("mchntCnNm");
		if (mchntCnNm != null && !"".equals(mchntCnNm)) {
			mchntCnNm = mchntCnNm.trim();
			createCriteria.andMchntCnNmLike(mchntCnNm);
		}

		// ????????????
		String mchntCd = qryParamMap.get("mchntCd");
		if (mchntCd != null && !"".equals(mchntCd)) {
			mchntCd=mchntCd.trim();
			createCriteria.andMchntCdEqualTo(mchntCd);
		}

		// ????????????
		String chnlId = qryParamMap.get("chnlId");
		if (chnlId != null && !"".equals(chnlId)) {
			createCriteria.andChnlIdEqualTo(chnlId);
		}

		// ??????????????????
		String chnlMchntDesc = qryParamMap.get("chnlMchntDesc");
		if (chnlMchntDesc != null && !"".equals(chnlMchntDesc)) {
			createCriteria.andChnlMchntDescLike(chnlMchntDesc);
		}

		// ??????????????????
		String chnlMchntCd = qryParamMap.get("chnlMchntCd");
		if (chnlMchntCd != null && !"".equals(chnlMchntCd)) {
			createCriteria.andChnlMchntCdLike(chnlMchntCd);
		}

		// ??????????????????
		String upDealWith = qryParamMap.get("upDealWith");
		if (upDealWith != null && !"".equals(upDealWith)) {
			createCriteria.andUpDealWithEqualTo(Integer.valueOf(upDealWith));
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// ??????????????????
		String recCrtTsStart = qryParamMap.get("recCrtTsStart");
		if (recCrtTsStart != null && !"".equals(recCrtTsStart)) {
			try {
				createCriteria.andRecCrtTsGreaterThanOrEqualTo(simpleDateFormat.parse(recCrtTsStart));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String recCrtTsEnd = qryParamMap.get("recCrtTsEnd");
		if (recCrtTsEnd != null && !"".equals(recCrtTsEnd)) {
			try {
				createCriteria.andRecCrtTsLessThanOrEqualTo(simpleDateFormat.parse(recCrtTsEnd));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// ????????????
		String processState = qryParamMap.get("processState");
		if (processState != null && !"".equals(processState)) {
			createCriteria.andProcessStateEqualTo(Integer.valueOf(processState));
		}

		// ????????????
		String processResult = qryParamMap.get("processResult");
		if (processResult != null && !"".equals(processResult)) {
			createCriteria.andProcessResultEqualTo(Integer.valueOf(processResult));
		}

		createCriteria.andIsDeleteEqualTo(0);
		// ????????????
		example.setOrderByClause("rec_crt_ts desc");
		return example;
	}

}
