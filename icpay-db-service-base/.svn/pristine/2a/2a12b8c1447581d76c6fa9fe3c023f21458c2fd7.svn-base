package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitPolicy;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitPolicyKey;
import com.icpay.payment.db.dao.mybatis.model.Announcement;
import com.icpay.payment.db.dao.mybatis.model.AnnouncementExample;
import com.icpay.payment.db.dao.mybatis.model.Content;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfoExample;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfoKey;

public interface IAnnouncementService {

	
	public List<Announcement> select(Map<String, String> qryParamMap);
	

	public Pager<Announcement> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	

	public void add(Announcement announcement);
	
	
	public void update(Announcement announcement);
	

	
	public void delete(int announcementId);

	
	
//=====================================================================================	
	

    
    long countByExample(AnnouncementExample example);

  
    int deleteByExample(Map<String,String> qryMap);

    
  //  int deleteByPrimaryKey(Announcement key);

    
    int insert(Announcement record);

    
    int insertSelective(Announcement record);


    List<Announcement> selectByExample(Map<String, String> map);

   
   // Announcement selectByPrimaryKey(Announcement key);

   
   // int updateByExampleSelective(Announcement record, Map<String,String> map);

  
    int updateByExample(Announcement record, AnnouncementExample example);

    
    //int updateByPrimaryKeySelective(Announcement record);

   
   // int updateByPrimaryKey(Announcement record);	
	
	
	
	
	
	
	
	

}
