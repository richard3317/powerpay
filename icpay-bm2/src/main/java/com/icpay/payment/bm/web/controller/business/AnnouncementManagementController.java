package com.icpay.payment.bm.web.controller.business;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.icpay.payment.db.dao.mybatis.model.AnnouncementExample.Criteria;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.cache.PageConfCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.db.dao.mybatis.model.Announcement;
import com.icpay.payment.db.dao.mybatis.model.AnnouncementExample;
import com.icpay.payment.db.dao.mybatis.model.Content;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;

import com.icpay.payment.db.service.IAnnouncementService;
import com.icpay.payment.db.service.IContextService;
import com.icpay.payment.db.service.IMchntInfoService;

/**
 * 公告管理的业务逻辑层bm2
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/announcementManagement")
public class AnnouncementManagementController extends BaseController {

    private static final String RESULT_BASE_URI = "announcementManagement";

    private static final Logger logger = Logger.getLogger(AnnouncementManagementController.class);
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");

    private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
        @Override
        public void transfer(Map<String, String> m) {
            // 是否已下线做处理
            String endTime = m.get("endTime");
            String beginTime = m.get("beginTime");
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
            	if(simpleDateFormat.parse(endTime).getTime()>=System.currentTimeMillis()&&simpleDateFormat.parse(beginTime).getTime()<=System.currentTimeMillis()) {
            		m.put("contentStateDesc", "生效中");
				}else if(simpleDateFormat.parse(beginTime).getTime()>System.currentTimeMillis()) {
					m.put("contentStateDesc", "未生效");
            	}else {
            		m.put("contentStateDesc", "已失效");
            	}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   
        }
    };

    @RequestMapping(value = "/manage.do", method = RequestMethod.GET)
    public String manage(Model model) {

        model.addAttribute("today", simpleDateFormat.format(new Date()));
        return super.toManage(model, false, RESULT_BASE_URI);
    }

    @RequestMapping(value = "/backToManage.do", method = RequestMethod.GET)
    public String backToManage(Model model) {
        return super.toManage(model, true, RESULT_BASE_URI);
    }

    @RequestMapping(value = "/qry.do", method = RequestMethod.POST)
    @QryMethod
    public @ResponseBody
    AjaxResult qry(Integer pageNum, Integer pageSize, HttpServletRequest req) throws Exception {
        Map<String, String> qryParamMap = this.getQryParamMap();
        if ("-1".equals(qryParamMap.get("contentState"))) {
            qryParamMap.remove("contentState");
        }
        //System.err.println("当前页为：" + pageNum + "；每页查询条数为：" + pageSize + "查询参数为：" + qryParamMap);
        // 获取模糊查询的参数
        IContextService dbService = this.getDBService(IContextService.class);
        // System.out.println("输出dbService" + dbService);
        Pager<Content> selectByPage = dbService.selectByPage(pageNum, pageSize, qryParamMap);
        return commonBO.buildSuccResp(BMConstants.PAGER_RESULT,
                commonBO.transferPager(selectByPage, BMConstants.PAGE_CONF_CONTENT, ENTITY_TRANSFER));
    }

    /**
     * 查看详情
     *
     * @param model
     * @param seqId
     * @return
     */

    @RequestMapping(value = "/detail.do")
    public String detail(Model model, int contentId) {
        // System.out.println("进来了详情。。。");
        IContextService dbService = this.getDBService(IContextService.class);
        Content entity = dbService.selectByPrimaryKey(contentId);
        AssertUtil.objIsNull(entity, "未找到记录:" + contentId);
        model.addAttribute(BMConstants.DETAIL_CONF_LST, PageConfCache.getDetailConf(BMConstants.PAGE_CONF_CONTENT));
        model.addAttribute(BMConstants.ENTITY_RESULT,
                commonBO.transferEntity(entity, BMConstants.PAGE_CONF_CONTENT, ENTITY_TRANSFER));
        return super.toDetail(model, RESULT_BASE_URI);
    }

    /**
     * 添加公告
     *
     * @param model
     * @return
     */

    @RequestMapping(value = "/add.do", method = RequestMethod.GET)
    public String add(Model model) {
        //System.out.println("进来了添加得方法");
        return super.toAdd(model, RESULT_BASE_URI);
    }

    @RequestMapping(value = "/add/submit.do", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResult addSubmit(String contentText, String contentTitle, String beginTime, String endTime, String type,
                         String contentMchntId) {
        String str = contentMchntId;
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH");
        AssertUtil.strIsBlank(contentText, "contentText is blank.");
        AssertUtil.strIsBlank(beginTime, "beginTime is blank.");
        AssertUtil.strIsBlank(endTime, "endTime is blank.");
        String format = sim.format(System.currentTimeMillis());
        try {
        	if(sim.parse(format).getTime()>sim.parse(beginTime).getTime()) {
				String sre="";
				AssertUtil.strIsBlank(sre, "生效时间不能小于当前时间");
			}
			if(sim.parse(beginTime).getTime()>sim.parse(endTime).getTime()) {
				String sre="";
				AssertUtil.strIsBlank(sre, "失效时间不能小于生效时间");
			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        Content entity = new Content();
        entity.setContentText(contentText);// 添加正文
        if ("2".equals(type)) {//指定商户
            String contentMchntIdStr = contentMchntId.trim().replace("\n", "").trim();
            if (contentMchntIdStr != null && !"".equals(contentMchntIdStr)) {
                String[] strs = contentMchntId.split("\n");
                String finalContentMchntId = "";
                for (String s : strs) {
                    if (s != null && !"".equals(s.trim())) {
                        finalContentMchntId += s.replace("\n", "").trim() + ",";
                    }
                }
                finalContentMchntId=finalContentMchntId.substring(0,finalContentMchntId.lastIndexOf(","));
                entity.setContentMchntId(finalContentMchntId);
            } else {
                AssertUtil.strIsBlank(contentMchntId, "contentMchntId is blank.");
            }
        }else {
        	entity.setContentMchntId("全部商户");
        }
        entity.setContentTitle(contentTitle);
        try {
            if (beginTime != null && !"".equals(beginTime))
                entity.setBeginTime(sim.parse(beginTime));
            if (endTime != null && !"".equals(endTime))
                entity.setEndTime(sim.parse(endTime));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 添加默认值
        entity.setContentIsDelete(0);
        entity.setContentState(0);
        entity.setLastOperId(this.getSessionUsrId());
        // entity.setFailureTime(failureTime);
        // 这里应该需要判断选的是哪一个单选框了 然后在进行赋值

        IContextService dbService = this.getDBService(IContextService.class);
        int add = dbService.add(entity);
        if (add > 0) {
            Integer contentId = dbService.select(null).get(0).getContentId();
            entity.setContentId(contentId);
        }
        // System.err.println("结果为："+add+"对象id为："+entity.getContentId());
        //添加另外一张表(公告编号表   以便于前端的数据  )
        this.addAnnounce(entity, str);


        return commonBO.buildSuccResp();
    }

    /**
     * 修改
     *
     * @param model
     * @param seqId
     * @return
     */

    @RequestMapping(value = "/edit.do", method = RequestMethod.GET)
    public String edit(Model model, int contentId) {
        IContextService dbService = this.getDBService(IContextService.class);
        Content entity = dbService.selectByPrimaryKey(contentId);
        entity.setContentMchntId(entity.getContentMchntId() == null ? null : entity.getContentMchntId().replace(",", "\n"));
        entity.setContentMchntId(entity.getContentMchntId().equals("全部商户") ? null:entity.getContentMchntId());

        //查看是否处于有效期内   
        //如果  要是在有效期内  那么 必须              生效时间大于或登录当前时间     失效时间大于
        if(entity.getBeginTime().getTime()<=System.currentTimeMillis()&&entity.getEndTime().getTime()>=System.currentTimeMillis()) {
            entity.setContentState(3);
        }

        AssertUtil.objIsNull(entity, "未找到记录:" + contentId);
        model.addAttribute(BMConstants.ENTITY_RESULT,
                commonBO.transferEntity(entity, BMConstants.PAGE_CONF_CONTENT, ENTITY_TRANSFER));
        return super.toEdit(model, RESULT_BASE_URI);
    }

    @RequestMapping(value = "/edit/submit.do", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResult editSubmit(String contentId, String contentTitle, String contentText, String beginTime, String endTime,
                          String type, String contentMchntId, String contentState) {
        String str = contentMchntId;
        Content entity = new Content();
        entity.setContentId(Integer.valueOf(contentId));
        entity.setLastOperId(this.getSessionUsrId());
        IContextService dbService = this.getDBService(IContextService.class);
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH");
        AssertUtil.strIsBlank(contentText, "contentText is blank.");
        String format = sim.format(System.currentTimeMillis()); 
        try {
        	Content compareObj = dbService.selectByPrimaryKey(Integer.valueOf(contentId));
        	//首先判断对象是属于生效还是失效   0代表生效，1代表是未生效，2，代表已失效
        	int judge=2;
        	if(compareObj.getBeginTime().getTime()<=System.currentTimeMillis()&&compareObj.getEndTime().getTime()>=System.currentTimeMillis()) {
        		judge=0;
        	}else if(compareObj.getBeginTime().getTime()>System.currentTimeMillis()){
        		judge=1;
        	}
//        	1）公告截止时间不能小于当前时间；
        	if(sim.parse(endTime).getTime()<sim.parse(format).getTime()) {
				String sre="";
				AssertUtil.strIsBlank(sre, "失效时间不能小于当前系统时间");
			}
        	
//        	2）如果公告生效时间还没到，修改公告生效时间不能早于当前时间；（报错信息）
        	if(judge==1&&sim.parse(beginTime).getTime()<sim.parse(format).getTime()) {
				String sre="";
				AssertUtil.strIsBlank(sre, "公告的生效时间不能小于当前系统时间");
			}
        	
        	
//        	3）如果公告生效时间已过，修改公告时间不能早于修改前的时间。
        	if((judge==0) && compareObj.getEndTime().getTime()<sim.parse(format).getTime()) {
        		String sre="";
			
				AssertUtil.strIsBlank(sre, "公告属于生效期间，公告截止时间不能小于当前时间");
        	}

        	//失效时间一定要大于或等于生效时间
            if(sim.parse(endTime).getTime()<sim.parse(beginTime).getTime()) {
                String sre="";
                AssertUtil.strIsBlank(sre, "失效时间不得小于生效时间");
            }
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        entity.setContentText(contentText);// 添加正文
        entity.setContentTitle(contentTitle);
        if ("2".equals(type)) {//指定商户
            String contentMchntIdStr = contentMchntId.trim().replace("\n", "").trim();
            if (contentMchntIdStr != null && !"".equals(contentMchntIdStr)) {
                String[] strs = contentMchntId.split("\n");
                String finalContentMchntId = "";
                for (String s : strs) {
                    if (s != null && !"".equals(s.trim())) {
                    	
                        finalContentMchntId += s.replace("\n", "").trim() + ",";
                    }
                }
                finalContentMchntId=finalContentMchntId.substring(0,finalContentMchntId.lastIndexOf(","));
                entity.setContentMchntId(finalContentMchntId);
            } else {
                AssertUtil.strIsBlank(contentMchntId, "contentMchntId is blank.");
            }
        }else {
        	entity.setContentMchntId("全部商户");
        }


        try {
            if (beginTime != null && !"".equals(beginTime))
                entity.setBeginTime(sim.parse(beginTime));
            if (endTime != null && !"".equals(endTime))
                entity.setEndTime(sim.parse(endTime));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 添加默认值
        entity.setContentIsDelete(0);
        entity.setContentState(0);
        dbService.update(entity);
        logger.info("修改公告信息完成:" + entity.getContentId());


        //修改,先删除第二张表的数据，然后重新添加
        if (contentId != null && !"".equals(contentId)) {
            IAnnouncementService iAnnouncementService = this.getDBService(IAnnouncementService.class);
            Map<String,String> qryMap=new HashMap<>();
            qryMap.put("contentId",contentId);
            iAnnouncementService.deleteByExample(qryMap);
          
        }

        //然后再重新添加
        this.addAnnounce(entity, str);
        return commonBO.buildSuccResp();
    }

    /**
     * 删除公告信息  其实是修改数据库得
     *
     * @param contentId
     * @return
     */

    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResult delete(String contentId) {
        logger.info("删除公告信息开始:" + contentId);
        IContextService dbService = this.getDBService(IContextService.class);
        Content dbEntity = dbService.selectByPrimaryKey(Integer.valueOf(contentId));
        AssertUtil.objIsNull(dbEntity, "未找到记录:" + contentId);
       
      
        dbEntity.setContentIsDelete(1);
        dbService.update(dbEntity);
        if (contentId != null && !"".equals(contentId)) {
            IAnnouncementService iAnnouncementService = this.getDBService(IAnnouncementService.class);
            Map<String,String> qryMap=new HashMap<>();
            qryMap.put("contentId",contentId);
            iAnnouncementService.deleteByExample(qryMap);
        }
 
        return commonBO.buildSuccResp();
    }

    /**
     * 验证添加得商户号在商户表中是否存在
     *
     * @param contentMchntId
     */
    @RequestMapping(value = "/addQuery.do", method = RequestMethod.POST)
    public void addQuery(String contentMchntId, HttpServletResponse response) {
        String[] split = contentMchntId.split("\n");
        String str = "";
        IMchntInfoService iMchntInfoService = this.getDBService(IMchntInfoService.class);
        for (String string : split) {
            MchntInfo mchntInfo = new MchntInfo();
            mchntInfo.setMchntCd(string.trim());
            MchntInfo selectByPrimaryKey = iMchntInfoService.selectByPrimaryKey(string.trim());
            if (selectByPrimaryKey == null) {
                if(!"".equals(string.trim())) {
                    str += string + "	";
                }
            }
        }
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(str);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加第二张表数据
     *
     * @param entity
     * @param contentMchntId
     */
    public void addAnnounce(Content entity, String contentMchntId) {
        //添加另外一张表
        //取出所有得数据
        IMchntInfoService iMchntInfoService = this.getDBService(IMchntInfoService.class);

        Map<String, String> qryParamMap = new HashMap<String, String>();
        qryParamMap.put("mchnt_st", "1");
        List<MchntInfo> mchntInfos = iMchntInfoService.select(qryParamMap);
        IAnnouncementService iAnnouncementService = this.getDBService(IAnnouncementService.class);
        if (entity.getContentMchntId() == null || "".equals(contentMchntId)) {
            for (MchntInfo mchntInfo : mchntInfos) {
                Announcement announcement = new Announcement();
                announcement.setAnnouncementState(0);
                announcement.setMchntCd(mchntInfo.getMchntCd());
                announcement.setAnnouncementId(entity.getContentId());
                iAnnouncementService.add(announcement);
            }
        } else {
            String[] split = contentMchntId.split("\n");
            for (String string : split) {
                for (MchntInfo mchntInfo : mchntInfos) {
                    string = string.trim();
                    if (string != null && !"".equals(string) && string.equals(mchntInfo.getMchntCd())) {
                        Announcement announcement = new Announcement();
                        announcement.setAnnouncementState(0);
                        announcement.setMchntCd(mchntInfo.getMchntCd());
                        announcement.setAnnouncementId(entity.getContentId());
                        iAnnouncementService.add(announcement);
                    }
                }
            }
        }
    }

}
