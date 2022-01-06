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
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.ContentMapper;

import com.icpay.payment.db.dao.mybatis.model.Content;
import com.icpay.payment.db.dao.mybatis.model.ContentExample;
import com.icpay.payment.db.dao.mybatis.model.ContentExample.Criteria;
import com.icpay.payment.db.service.IContextService;


@Service("contextService")
public class ContextService extends BaseService implements IContextService {

    @Override
    public List<Content> select(Map<String, String> qryParamMap) {
        // TODO Auto-generated method stub
        ContentExample qryExample = this.getQryExample(qryParamMap);
        return getMapper(ContentMapper.class).selectByExample(qryExample);
    }

    @Override
    public Pager<Content> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
        // TODO Auto-generated method stub
        ContentExample example = this.getQryExample(qryParamMap);
        ContentMapper mapper = getMapper();
        Pager<Content> pager = buildPager(pageNum, pageSize, qryParamMap);
        if (example != null) {
            example.setStartNum(pager.getStartNum());
            example.setPageSize(pageSize);
        }
        pager.setTotal(mapper.countByExample(example));
        pager.setResultList(mapper.selectByPage(example));
        return pager;

    }


    @Override
    protected ContentExample buildQryExample(Map<String, String> qryParamMap) {
        // TODO Auto-generated method stub
        ContentExample example = new ContentExample();
        if(qryParamMap!=null) {
        Criteria createCriteria = example.createCriteria();
        createCriteria.andContentIsDeleteEqualTo(0);
        //内容编号
        if (qryParamMap.get("contentId") != null && !"".equals(qryParamMap.get("contentId"))) {
            createCriteria.andContentIdEqualTo(Integer.parseInt(qryParamMap.get("contentId")));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");

        //开始时间
        String creatTimeStartStr = qryParamMap.get("creatTimeStart");
        String creatTimeEndStr = qryParamMap.get("creatTimeEnd");

        try {
            if (creatTimeStartStr != null && !"".equals(creatTimeStartStr)) {
                //创建大于或等于
                Date creatTimeStartTime = simpleDateFormat.parse(creatTimeStartStr);
                createCriteria.andRecCrtTsGreaterThanOrEqualTo(creatTimeStartTime);
            }
            if (creatTimeEndStr != null && !"".equals(creatTimeEndStr)) {
                //创建小于或等于
                Date creatTimeEndTime = simpleDateFormat.parse(creatTimeEndStr);
                createCriteria.andRecCrtTsLessThanOrEqualTo(creatTimeEndTime);
                
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //公告状态
        String contentState = qryParamMap.get("contentState");
        if (contentState != null && !"".equals(contentState)) {
            createCriteria.andContentStateEqualTo(Integer.valueOf(contentState));
        }
        }
        // 排序字段
        example.setOrderByClause("content_id desc");
        return example;
    }

    private ContentMapper getMapper() {
        return this.getMapper(ContentMapper.class);
    }

    @Override
    public Content selectByPrimaryKey(int contentId) {
        // TODO Auto-generated method stub
        return this.getMapper().selectByPrimaryKey(contentId);
    }

    @Override
    public int add(Content entity) {
        // TODO Auto-generated method stub
        AssertUtil.objIsNull(entity, "entity is null.");
        return this.getMapper().insert(entity);
    }

    @Override
    public void update(Content entity) {
        // TODO Auto-generated method stub
        i18ObjIsNull(entity, this.getClass().getSimpleName(), "待修改的记录为null");
        Content dbEntity = this.selectByPrimaryKey(entity.getContentId());
        i18ObjIsNull(dbEntity, this.getClass().getSimpleName(), "待修改的记录不存在");
        // 更新数据库字段
        BeanUtil.cloneEntity(entity, dbEntity, new String[]{
        		"contentTitle", "contentText", "contentState", "contentMchntId", "beginTime", "endTime", "contentIsDelete"
        });
        dbEntity.setRecUpdTs(new Date());
        // 保存至数据库
        getMapper().updateByPrimaryKey(dbEntity);
    }

    @Override
    public void delete(int contentId) {
        // TODO Auto-generated method stub
        this.getMapper().deleteByPrimaryKey(contentId);

    }


}
