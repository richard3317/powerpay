package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.mapper.AnnouncementMapper;
import com.icpay.payment.db.dao.mybatis.model.Announcement;
import com.icpay.payment.db.dao.mybatis.model.AnnouncementExample;
import com.icpay.payment.db.dao.mybatis.model.AnnouncementExample.Criteria;
import com.icpay.payment.db.service.IAnnouncementService;

/**
 * 编号表
 * @author Cassie
 *
 */
@Service("announcementService")
public class AnnouncementService extends BaseService implements IAnnouncementService{
	

	@Override
	public List<Announcement> select(Map<String, String> qryParamMap) {
		AnnouncementExample qryExample = this.getQryExample(qryParamMap);
        return getMapper().selectByExample(qryExample);
	}

	@Override
	public Pager<Announcement> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		AnnouncementExample example = this.getQryExample(qryParamMap);
        AnnouncementMapper mapper = getMapper();
        Pager<Announcement> pager = buildPager(pageNum, pageSize, qryParamMap);
        if (example != null) {
            example.setStartNum(pager.getStartNum());
            example.setPageSize(pageSize);
        }
        pager.setTotal(mapper.countByExample(example));
        pager.setResultList(mapper.selectByPage(example));
        return pager;
	}


	@Override
	public void add(Announcement entity) {
		// TODO Auto-generated method stub
		//return this.getMapper().insert(entity);
		this.getMapper().insertSelective(entity);
		
	}

	@Override
	public void update(Announcement entity) {
		// TODO Auto-generated method stub
		i18ObjIsNull(entity, this.getClass().getSimpleName(), "待修改的记录为null");
        entity.setRecUpdTs(new Date());
        // 保存至数据库
        AnnouncementExample announcementExample=new AnnouncementExample();
        Criteria createCriteria = announcementExample.createCriteria();
        createCriteria.andAnnouncementIdEqualTo(entity.getAnnouncementId());
        createCriteria.andMchntCdEqualTo(entity.getMchntCd());
        getMapper().updateByExample(entity, announcementExample);
	}

	@Override
	protected AnnouncementExample buildQryExample(Map<String, String> qryParamMap) {
		// TODO Auto-generated method stub
		AnnouncementExample example = new AnnouncementExample();
		Criteria createCriteria = example.createCriteria();
		
		if(qryParamMap!=null) {
			String mchntCd = qryParamMap.get("mchntCd");
			if(mchntCd!=null && !"".equals(mchntCd)) {
				createCriteria.andMchntCdEqualTo(mchntCd);
			}
			
			String mchntCdArr = qryParamMap.get("mchntCdArr");
			if(!Utils.isEmpty(mchntCdArr)) {
				createCriteria.andMchntCdIn(Utils.strSplitToList(mchntCdArr, ",", true));
			}
			
			String announcementState=qryParamMap.get("announcementState");
			if(announcementState!=null &&!"".equals(announcementState)) {
				createCriteria.andAnnouncementStateEqualTo(Integer.valueOf(announcementState));
			}
			String announcementId=qryParamMap.get("announcementId");
			if(announcementId!=null &&!"".equals(announcementId)) {
				createCriteria.andAnnouncementIdEqualTo(Integer.valueOf(announcementId));
			}
		}
		// 排序字段
	    example.setOrderByClause("announcement_id desc");
	    return example;
	}

	
	  private AnnouncementMapper getMapper() {
	        return this.getMapper(AnnouncementMapper.class);
	    }

	@Override
	public void delete(int announcementId) {
		// TODO Auto-generated method stub
	
	}

	private AnnouncementMapper dao() {
		return this.getMapper(AnnouncementMapper.class);
	} 
	
	@Override
	public long countByExample(AnnouncementExample example) {
		// TODO Auto-generated method stub
		return dao().countByExample(example);
	}

	@Override
	public int deleteByExample(Map<String,String> qryMap) {
		AnnouncementExample example=new AnnouncementExample();
		AnnouncementExample.Criteria criteria = example.createCriteria();
		criteria.andAnnouncementIdEqualTo(Integer.valueOf(qryMap.get("contentId")));
		// TODO Auto-generated method stub
		return dao().deleteByExample(example);
	}

	@Override
	public int insert(Announcement record) {
		// TODO Auto-generated method stub
		return dao().insert(record);
	}

	@Override
	public int insertSelective(Announcement record) {
		// TODO Auto-generated method stub
		return dao().insertSelective(record);
	}

	@Override
	public List<Announcement> selectByExample(Map<String, String> map) {
		// TODO Auto-generated method stub
		AnnouncementExample example = this.getQryExample(map);
		return dao().selectByExample(example);
	}

	@Override
	public int updateByExample(Announcement record, AnnouncementExample example) {
		// TODO Auto-generated method stub
		return dao().updateByExample(record, example);
	}
	
}
