package com.icpay.payment.bm.web.controller.business;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.icpay.payment.bm.cache.BMConfigCache;
import com.icpay.payment.bm.cache.PageConfCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.ColumnInfo;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.*;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.db.dao.mybatis.model.ComplaintManage;
import com.icpay.payment.db.dao.mybatis.model.TxnLogView;
import com.icpay.payment.db.service.IComplaintManageService;
import com.icpay.payment.db.service.ITxnLogViewService;

/**
 * 投诉管理controller类
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/complaintManage")
public class ComplaintManageController extends BaseController {

    private static final String RESULT_BASE_URI = "complaintManage";

    private static final Logger logger = Logger.getLogger(ComplaintManageController.class);

    private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
        @Override
        public void transfer(Map<String, String> m) {
            // 枚举
            // 站点名称
            String siteDesc = m.get("siteDesc") == null ? "" : m.get("siteDesc");
            m.put("siteDesc", EnumUtil.translate(ComplaintEnums.SiteDesc.class, siteDesc, true));
            // 上游处理类型
            String upDealWith = m.get("upDealWith") == null ? "" : m.get("upDealWith");
            m.put("upDealWith", EnumUtil.translate(ComplaintEnums.UpDealWith.class, upDealWith, true));

            // 处理状态
            String processState = m.get("processState") == null ? "" : m.get("processState");
            m.put("processState", EnumUtil.translate(ComplaintEnums.ProcessState.class, processState, true));

            // 处理结果
            String processResult = m.get("processResult") == null ? "" : m.get("processResult");
            m.put("processResult", EnumUtil.translate(ComplaintEnums.ProcessResult.class, processResult, true));

            // 渠道名称
            String chnlId = m.get("chnlId");
            m.put("chnlId", EnumUtil.translate(TxnEnums.ChnlId.class, chnlId, true));

            // 商户处理类别
            String mchntDealWith = m.get("mchntDealWith") == null ? "" : m.get("mchntDealWith");
            m.put("mchntDealWith", EnumUtil.translate(ComplaintEnums.MchntDealWith.class, mchntDealWith, true));
            
            String mchntCnNm = m.get("mchntCnNm");
            if(mchntCnNm!=null && mchntCnNm.indexOf(",")>-1){
                mchntCnNm=mchntCnNm.substring(0,mchntCnNm.indexOf(","));
            }

            //商户名称
            m.put("mchntCnNm", mchntCnNm);

            String mchntCd = m.get("mchntCd");
            if(mchntCd!=null && mchntCd.indexOf(",")>-1){
                mchntCd=mchntCd.substring(0,mchntCd.indexOf(","));
            }
            //商户编号
            m.put("mchntCd", mchntCd);

            //渠道商编
            String chnlMchntCd = m.get("chnlMchntCd");
            if(chnlMchntCd!=null && chnlMchntCd.indexOf(",")>-1){
                chnlMchntCd=chnlMchntCd.substring(0,chnlMchntCd.indexOf(","));
            }
            //商户编号
            m.put("chnlMchntCd", chnlMchntCd);


            //渠道商户名称
            String chnlMchntDesc = m.get("chnlMchntDesc");
            if(chnlMchntDesc!=null && chnlMchntDesc.indexOf(",")>-1){
                chnlMchntDesc=chnlMchntDesc.substring(0,chnlMchntDesc.indexOf(","));
            }
            //商户编号
            m.put("chnlMchntDesc", chnlMchntDesc);

            // 交易类型
            String intTransCd = m.get("intTransCd");
            m.put("intTransCd", EnumUtil.translate(TxnEnums.TxnType.class, intTransCd, true));

            // 交易金额
            m.put("dealMoney", m.get("dealMoney") == null ? null : m.get("dealMoney"));

            String complaintMoney = m.get("complaintMoney");
            double sum = 0;
            if (complaintMoney != null) {
                String[] splits=complaintMoney.split(",");
                for (String split : splits) {
                    if(split!=null &&!"".equals(split)){
                        sum+=Double.valueOf(split);
                    }
                }
            }
            // 诉求金额
            m.put("complaintMoney", sum+"");
        }
    };

    @RequestMapping(value = "/manage.do", method = RequestMethod.GET)
    public String manage(Model model) {
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

        // 获取模糊查询的参数
        IComplaintManageService dbService = this.getDBService(IComplaintManageService.class);
        Pager<ComplaintManage> selectByPage = dbService.selectByPage(pageNum, pageSize, qryParamMap);
        return commonBO.buildSuccResp(BMConstants.PAGER_RESULT,
                commonBO.transferPager(selectByPage, BMConstants.PAGE_CONF_COMPLAINTMANAGE, ENTITY_TRANSFER));
    }

    /**
     * 查看详情
     *
     * @param model
     * @param seqId
     * @return
     */

    @RequestMapping(value = "/detail.do")
    public String detail(Model model, int id) {
        IComplaintManageService dbService = this.getDBService(IComplaintManageService.class);
        ComplaintManage entity = dbService.selectByPrimaryKey(id);
        AssertUtil.objIsNull(entity, "未找到记录:" + id);

        List<Map<String, String>> mapList = publicChange(entity);
        if (mapList.size() > 1) {
            Map<String, String> mapMoney = mapList.get(mapList.size() - 1);
            model.addAttribute("sumMoney", mapMoney.get("sumMoney"));
            model.addAttribute("sumReturnMoney", mapMoney.get("sumReturnMoney"));

            // 移除
            mapList.remove(mapList.size() - 1);
        }
        model.addAttribute("mapList", mapList);
        // 商户号
        List<String> mchntFreezeCdList = new ArrayList<String>();
        if (entity.getMchntFreezeCd() != null && !"".equals(entity.getMchntFreezeCd())) {
            String[] split = entity.getMchntFreezeCd().split(",");
            for (String string : split) {
                mchntFreezeCdList.add(string);
            }
        }
        model.addAttribute("mchntFreezeCdList", mchntFreezeCdList);
        // 处理结果
        String[] strs = new String[]{"请选择", "全部原退", "部分原退", "线下全退", "线下部分退", "不做退款"};
        List<Map<String, String>> processResultMap = new ArrayList<Map<String, String>>();
        for (int i = -1; i < strs.length-1; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("value", i + "");
            map.put("name", strs[i+1]);
            processResultMap.add(map);
        }
        //历史处理记录
        model.addAttribute("dealHistory", entity.getDealHistory());
        model.addAttribute("processResultMap", processResultMap);
        model.addAttribute(BMConstants.DETAIL_CONF_LST,
                PageConfCache.getDetailConf(BMConstants.PAGE_CONF_COMPLAINTMANAGE));
        model.addAttribute(BMConstants.ENTITY_RESULT,
                commonBO.transferEntity(entity, BMConstants.PAGE_CONF_COMPLAINTMANAGE, ENTITY_TRANSFER));
        return super.toDetail(model, RESULT_BASE_URI);
    }

    /**
     * 添加投诉
     *
     * @param model
     * @return
     */

    @RequestMapping(value = "/add.do", method = RequestMethod.GET)
    public String add(Model model) {
        return super.toAdd(model, RESULT_BASE_URI);
    }

    @RequestMapping(value = "/add/submit.do", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResult addSubmit(ComplaintManage complaintManage,String check_id) {
        IComplaintManageService dbService = this.getDBService(IComplaintManageService.class);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
        String complaintId=simpleDateFormat.format(new Date());
        
        Map<String, String> qry=new HashMap<String, String>();
        //拼接序列号
        List<ComplaintManage> select = dbService.select(qry);
        if(select.size()==0) {
        	complaintId+="001";
        }else {
        	if(select.get(0).getComplaintId().indexOf(complaintId)>-1) {
        		complaintId=(Long.valueOf(select.get(0).getComplaintId())+1)+"";
        	}else {
        		complaintId+="001";
        	}
        }
        
        complaintManage.setComplaintId(complaintId);

        String[] split = complaintManage.getComplaintType().split(",");
        //添加单选框中的值
        if(check_id.equals("1")){
        	complaintManage.setComplaintType(split[0]);
        }else {
        	complaintManage.setComplaintType(split[1]);
        }
        

        // 添加默认值
        complaintManage.setIsDelete(0);

        complaintManage.setProcessResult(-1);

        // 商户号
        complaintManage.setTerraceTransId(delFirst(complaintManage.getTerraceTransId()));
        // 商户订单号
        complaintManage.setMchntTransId(delFirst(complaintManage.getMchntTransId()));
        // 交易金额
        complaintManage.setDealMoney(complaintManage.getDealMoney());
        // 卡号
        complaintManage.setComplaintCard(delFirst(complaintManage.getComplaintCard()));
        // 银行名称
        complaintManage.setBankName(delFirst(complaintManage.getBankName()));
        String dealType = "";
        String[] dealTypes = complaintManage.getDealType().split(",");
        for (String string : dealTypes) {
            dealType += EnumUtil.translate(TxnEnums.TxnType.class, string, true) + ",";
        }
        dealType = dealType.substring(0, dealType.length() - 1);
        // 交易方式
        complaintManage.setDealType(dealType);
        // 交易日期
        complaintManage.setDealDt(delFirst(complaintManage.getDealDt()));
        // 交易时间
        complaintManage.setDealTm(delFirst(complaintManage.getDealTm()));
        // 商户编号
        complaintManage.setMchntCd(delFirst(complaintManage.getMchntCd()));
        // 商户名称
        complaintManage.setMchntCnNm(delFirst(complaintManage.getMchntCnNm()));
        // 渠道商户编号
        complaintManage.setChnlMchntCd(delFirst(complaintManage.getChnlMchntCd()));

        // 渠道商户名称
        complaintManage.setChnlMchntDesc(delFirst(complaintManage.getChnlMchntDesc()));
        // 投诉金额
        complaintManage.setComplaintMoney(delFirst(complaintManage.getComplaintMoney()));
        // 交易金额
        complaintManage.setDealMoney(delFirst(complaintManage.getDealMoney()));

        // 处理结果
        complaintManage.setDealProcessResult(delFirst(complaintManage.getDealProcessResult()));

        dbService.add(complaintManage);
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
    public String edit(Model model, int id) {
        IComplaintManageService dbService = this.getDBService(IComplaintManageService.class);
        ComplaintManage entity = dbService.selectByPrimaryKey(id);
        AssertUtil.objIsNull(entity, "未找到记录:" + id);
        
        String str="普通投诉,人行投诉,现场举报,警方协查,司法冻结,疑似欺诈,疑似博彩,疑似洗钱," +
                "疑似外汇,疑似金融,调单,否认交易,盗卡盗刷,电信诈骗,重置未到账,欺诈交易";
        
        String check_id="1";
        System.err.println("修改之前"+entity);
        
        String compalintType=entity.getComplaintType();
        if(str.indexOf(compalintType)==-1) {
        	check_id="2";
        }
        
        model.addAttribute("check_id", check_id);

        List<Map<String, String>> mapList = publicChange(entity);
        System.err.println("之后");
        if (mapList.size() > 1) {
            Map<String, String> mapMoney = mapList.get(mapList.size() - 1);
            model.addAttribute("sumMoney", mapMoney.get("sumMoney"));
            model.addAttribute("sumReturnMoney", mapMoney.get("sumReturnMoney"));

            // 移除
            mapList.remove(mapList.size() - 1);
        }
        model.addAttribute("mapList", mapList);
        // 商户号
        List<String> mchntFreezeCdList = new ArrayList<String>();
        if (entity.getMchntFreezeCd() != null && !"".equals(entity.getMchntFreezeCd())) {
            String[] split = entity.getMchntFreezeCd().split(",");
            for (String string : split) {
                mchntFreezeCdList.add(string);
            }
        }
        model.addAttribute("mchntFreezeCdList", mchntFreezeCdList);

        // 交易金额 sumMoney
        // 退款金额 sumReturnMoney

        model.addAttribute(BMConstants.ENTITY_RESULT,
                commonBO.transferEntity(entity, BMConstants.PAGE_CONF_COMPLAINTMANAGE, null));
        return super.toEdit(model, RESULT_BASE_URI);
    }

    @RequestMapping(value = "/edit/submit.do", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResult editSubmit(ComplaintManage complaintManage,String check_id) {
        IComplaintManageService dbService = this.getDBService(IComplaintManageService.class);
        
        String[] split = complaintManage.getComplaintType().split(",");
        //添加单选框中的值
        if(check_id.equals("1")){
        	complaintManage.setComplaintType(split[0]);
        }else {
        	complaintManage.setComplaintType(split[1]);
        }
        // 添加默认值
        complaintManage.setIsDelete(0);

        // 商户号
        complaintManage.setTerraceTransId(delFirst(complaintManage.getTerraceTransId()));
        // 商户订单号
        complaintManage.setMchntTransId(delFirst(complaintManage.getMchntTransId()));
        // 交易金额
        complaintManage.setDealMoney(complaintManage.getDealMoney());
        // 卡号
        complaintManage.setComplaintCard(delFirst(complaintManage.getComplaintCard()));
        // 银行名称
        complaintManage.setBankName(delFirst(complaintManage.getBankName()));
        // 交易方式
        complaintManage.setDealType(delFirst(complaintManage.getDealType()));
        // 交易日期
        complaintManage.setDealDt(delFirst(complaintManage.getDealDt()));
        // 交易时间
        complaintManage.setDealTm(delFirst(complaintManage.getDealTm()));
        // 商户编号
        complaintManage.setMchntCd(delFirst(complaintManage.getMchntCd()));
        // 商户名称
        complaintManage.setMchntCnNm(delFirst(complaintManage.getMchntCnNm()));
        // 渠道商户编号
        complaintManage.setChnlMchntCd(delFirst(complaintManage.getChnlMchntCd()));

        // 渠道商户名称
        complaintManage.setChnlMchntDesc(delFirst(complaintManage.getChnlMchntDesc()));
        // 投诉金额
        complaintManage.setComplaintMoney(delFirst(complaintManage.getComplaintMoney()));
        // 交易金额
        complaintManage.setDealMoney(delFirst(complaintManage.getDealMoney()));

        // 处理结果
        complaintManage.setDealProcessResult(delFirst(complaintManage.getDealProcessResult()));
        ComplaintManage complaintManage1 = dbService.selectByPrimaryKey(complaintManage.getComId());
        complaintManage.setDealHistory(complaintManage1.getDealHistory());
        dbService.update(complaintManage);
        return commonBO.buildSuccResp("修改成功");
    }

    @RequestMapping(value = "/edit/dealSubmit.do", method = RequestMethod.POST)
    public void dealSubmit(ComplaintManage complaintManage, HttpServletResponse response) {
        IComplaintManageService dbService = this.getDBService(IComplaintManageService.class);
        ComplaintManage selectByPrimaryKey = dbService.selectByPrimaryKey(complaintManage.getComId());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        selectByPrimaryKey
                .setDealHistory((selectByPrimaryKey.getDealHistory() == null ? "" : selectByPrimaryKey.getDealHistory())
                        + "\n" + "     添加时间：" + simpleDateFormat.format(new Date()) + "     添加人："
                        + this.getSessionUsrId() + "     内容：" + complaintManage.getDealHistory());
        dbService.update(selectByPrimaryKey);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write("1");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除t投诉信息 其实是修改数据库得
     *
     * @param contentId
     * @return
     */

    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResult delete(String id) {
        logger.info("删除t投诉信息开始:" + id);
        IComplaintManageService dbService = this.getDBService(IComplaintManageService.class);
        ComplaintManage dbEntity = dbService.selectByPrimaryKey(Integer.valueOf(id));
        AssertUtil.objIsNull(dbEntity, "未找到记录:" + id);
        dbEntity.setIsDelete(1);
        dbService.update(dbEntity);
        return commonBO.buildSuccResp();
    }

    @RequestMapping(value = "/export.do", method = RequestMethod.GET)
    @QryMethod
    public String export(Model model, HttpServletResponse response) {
        Map<String, String> qryParamMap = this.getQryParamMap();
        if (Integer.valueOf(qryParamMap.get("processState")) == -1) {
            qryParamMap.remove("processState");
        }
        if (Integer.valueOf(qryParamMap.get("processResult")) == -2) {
            qryParamMap.remove("processResult");
        }
        if (Integer.valueOf(qryParamMap.get("upDealWith")) == -1) {
            qryParamMap.remove("upDealWith");
        }
        if (Integer.valueOf(qryParamMap.get("siteDesc")) == -1) {
            qryParamMap.remove("siteDesc");
        }
        this.debug("[export] 导出查询结果, qryParamMap: %s", qryParamMap);
        Map<String, Object> mapSummary = null;
        IComplaintManageService dbService = this.getDBService(IComplaintManageService.class);
        List<ComplaintManage> list = dbService.select(qryParamMap);
        Pager<Map<String, String>> pager = new Pager<Map<String, String>>();
        // 表头
        List<ColumnInfo> columnInfos = new ArrayList<ColumnInfo>();
        ColumnInfo columnInfo = new ColumnInfo();
        columnInfo.setTitle("接诉日期");
        columnInfo.setField("接诉日期");
        ColumnInfo columnInfo1 = new ColumnInfo();
        columnInfo1.setTitle("投诉事件编号");
        columnInfo1.setField("投诉事件编号");
        ColumnInfo columnInfo2 = new ColumnInfo();
        columnInfo2.setTitle("投诉商户站点");
        columnInfo2.setField("投诉商户站点");
        ColumnInfo columnInfo3 = new ColumnInfo();
        columnInfo3.setTitle("投诉类型");
        columnInfo3.setField("投诉类型");
        ColumnInfo columnInfo4 = new ColumnInfo();
        columnInfo4.setTitle("渠道名称");
        columnInfo4.setField("渠道名称"); 
        ColumnInfo columnInfo6 = new ColumnInfo();
        columnInfo6.setTitle("处理状态");
        columnInfo6.setField("处理状态");
        ColumnInfo columnInfo7 = new ColumnInfo();
        columnInfo7.setTitle("处理结果");
        columnInfo7.setField("处理结果");
        ColumnInfo columnInfo8 = new ColumnInfo();
        columnInfo8.setTitle("上游处理类型");
        columnInfo8.setField("上游处理类型");
        ColumnInfo columnInfo9 = new ColumnInfo();
        columnInfo9.setTitle("上游冻结金额");
        columnInfo9.setField("上游冻结金额");
        ColumnInfo columnInfo10 = new ColumnInfo();
        columnInfo10.setTitle("上游冻结差额");
        columnInfo10.setField("上游冻结差额");
        ColumnInfo columnInfo11 = new ColumnInfo();
        columnInfo11.setTitle("上游退还金额");
        columnInfo11.setField("上游退还金额");
        ColumnInfo columnInfo12 = new ColumnInfo();
        columnInfo12.setTitle("威力处理类型");
        columnInfo12.setField("威力处理类型");
        ColumnInfo columnInfo13 = new ColumnInfo();
        columnInfo13.setTitle("冻结商户金额");
        columnInfo13.setField("冻结商户金额");
        ColumnInfo columnInfo14 = new ColumnInfo();
        columnInfo14.setTitle("冻结商户差额");
        columnInfo14.setField("冻结商户差额");
        ColumnInfo columnInfo15 = new ColumnInfo();
        columnInfo15.setTitle("退还商户金额");
        columnInfo15.setField("退还商户金额");
        ColumnInfo columnInfo16 = new ColumnInfo();
        columnInfo16.setTitle("投诉人");
        columnInfo16.setField("投诉人");
        ColumnInfo columnInfo17 = new ColumnInfo();
        columnInfo17.setTitle("投诉人联系方式");
        columnInfo17.setField("投诉人联系方式");
        ColumnInfo columnInfo18 = new ColumnInfo();
        columnInfo18.setTitle("退款卡号信息");
        columnInfo18.setField("退款卡号信息");
        ColumnInfo columnInfo5 = new ColumnInfo();
        columnInfo5.setTitle("备注");
        columnInfo5.setField("备注");
        ColumnInfo columnInfo19 = new ColumnInfo();
        columnInfo19.setTitle("涉诉交易总额");
        columnInfo19.setField("涉诉交易总额");
        ColumnInfo columnInfo20 = new ColumnInfo();
        columnInfo20.setTitle("退款总额");
        columnInfo20.setField("退款总额");
        ColumnInfo columnInfo21 = new ColumnInfo();
        columnInfo21.setTitle("处理记录");
        columnInfo21.setField("处理记录");
        ColumnInfo columnInfo22 = new ColumnInfo();
        columnInfo22.setTitle("平台订单号");
        columnInfo22.setField("平台订单号");
        ColumnInfo columnInfo23 = new ColumnInfo();
        columnInfo23.setTitle("商户订单号");
        columnInfo23.setField("商户订单号");
        ColumnInfo columnInfo24 = new ColumnInfo();
        columnInfo24.setTitle("交易金额");
        columnInfo24.setField("交易金额");
        ColumnInfo columnInfo25 = new ColumnInfo();
        columnInfo25.setTitle("卡号");
        columnInfo25.setField("卡号");
        ColumnInfo columnInfo26 = new ColumnInfo();
        columnInfo26.setTitle("银行名称");
        columnInfo26.setField("银行名称");
        ColumnInfo columnInfo27 = new ColumnInfo();
        columnInfo27.setTitle("交易方式");
        columnInfo27.setField("交易方式");
        ColumnInfo columnInfo28 = new ColumnInfo();
        columnInfo28.setTitle("交易创建日期");
        columnInfo28.setField("交易创建日期");
        ColumnInfo columnInfo29 = new ColumnInfo();
        columnInfo29.setTitle("交易创建时间");
        columnInfo29.setField("交易创建时间");
        ColumnInfo columnInfo30 = new ColumnInfo();
        columnInfo30.setTitle("商户号");
        columnInfo30.setField("商户号");
        ColumnInfo columnInfo31 = new ColumnInfo();
        columnInfo31.setTitle("商户名称");
        columnInfo31.setField("商户名称");
        ColumnInfo columnInfo32 = new ColumnInfo();
        columnInfo32.setTitle("渠道商户编号");
        columnInfo32.setField("渠道商户编号");
        ColumnInfo columnInfo33 = new ColumnInfo();
        columnInfo33.setTitle("渠道商户名称");
        columnInfo33.setField("渠道商户名称");
        ColumnInfo columnInfo34 = new ColumnInfo();
        columnInfo34.setTitle("投诉详情处理结果");
        columnInfo34.setField("投诉详情处理结果");
        ColumnInfo columnInfo35 = new ColumnInfo();
        columnInfo35.setTitle("退款金额");
        columnInfo35.setField("退款金额");

        columnInfos.add(columnInfo);
        columnInfos.add(columnInfo1);
        columnInfos.add(columnInfo2);
        columnInfos.add(columnInfo3);
        columnInfos.add(columnInfo4);
        columnInfos.add(columnInfo6);
        columnInfos.add(columnInfo7);
        columnInfos.add(columnInfo8);
        columnInfos.add(columnInfo9);
        columnInfos.add(columnInfo10);
        columnInfos.add(columnInfo11);
        columnInfos.add(columnInfo12);
        columnInfos.add(columnInfo13);
        columnInfos.add(columnInfo14);
        columnInfos.add(columnInfo15);
        columnInfos.add(columnInfo16);
        columnInfos.add(columnInfo17);
        columnInfos.add(columnInfo18);
        columnInfos.add(columnInfo5);
        columnInfos.add(columnInfo19);
        columnInfos.add(columnInfo20);
        columnInfos.add(columnInfo21);
        columnInfos.add(columnInfo22);
        columnInfos.add(columnInfo23);
        columnInfos.add(columnInfo24);
        columnInfos.add(columnInfo25);
        columnInfos.add(columnInfo26);
        columnInfos.add(columnInfo27);
        columnInfos.add(columnInfo28);
        columnInfos.add(columnInfo29);
        columnInfos.add(columnInfo30);
        columnInfos.add(columnInfo31);
        columnInfos.add(columnInfo32);
        columnInfos.add(columnInfo33);
        columnInfos.add(columnInfo34);
        columnInfos.add(columnInfo35);

        pager.setColumnLst(columnInfos);
        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();

        // SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd
        // HH:mm:ss");
        // 里面的内容
        // 结果数据
        for (ComplaintManage complaintManage : list) {
            Map<String, String> transferEntity = commonBO.transferEntity(complaintManage,
                    BMConstants.PAGE_CONF_COMPLAINTMANAGE, ENTITY_TRANSFER);

            // 接诉日期,投诉事件编号,站点名称

            String[] complaintMoneys = null;
            if (transferEntity.get("complaintMoney") != null) {
                complaintMoneys = transferEntity.get("complaintMoney").split(",");
            }
            String[] dealMoneys = null;
            if (transferEntity.get("dealMoney") != null) {
                dealMoneys = transferEntity.get("dealMoney").split(",");
            }
            double sum_dealMoney = 0;
            for (String string : dealMoneys) {
                if (string != null && !"".equals(string)) {
                    sum_dealMoney += Double.valueOf(string);
                }
            }

            double sum_complaintMoney = 0;
            for (String string : complaintMoneys) {
                if (string != null && !"".equals(string)) {
                    sum_complaintMoney += Double.parseDouble(string);
                }
            }
            List<Map<String, String>> publicChangeList = publicChange(complaintManage);
            if (publicChangeList.size() > 0) {
                publicChangeList.remove(publicChangeList.size() - 1);
                for (Map<String, String> map2 : publicChangeList) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("接诉日期", transferEntity.get("recCrtTs"));
                    map.put("投诉事件编号", transferEntity.get("complaintId"));
                    map.put("投诉商户站点", transferEntity.get("siteDesc"));
                    map.put("涉诉交易总额", sum_dealMoney + "");
                    map.put("退款总额", sum_complaintMoney + "");
                    // 处理记录,平台订单号,商户订单号,交易金额
                    map.put("处理记录", transferEntity.get("dealHistory"));
                    // 投诉类型,;渠道名称,;备注,
                    map.put("投诉类型", transferEntity.get("complaintType"));
                    map.put("渠道名称", transferEntity.get("chnlId"));
                    map.put("备注", transferEntity.get("complaintRequire"));
                    // ;处理状态,;处理结果,;上游处理类型,;上游冻结金额,;上游冻结差额,
                    map.put("处理状态", transferEntity.get("processState"));
                    map.put("处理结果", transferEntity.get("processResult"));
                    map.put("上游处理类型", transferEntity.get("upDealWith"));
                    map.put("上游冻结金额", complaintManage.getUpFreezeMoney() == null ? "0" : complaintManage.getUpFreezeMoney() + "");
                    map.put("上游冻结差额", transferEntity.get("upFreezeDiffer"));
                    // ;上游退还金额,;威力处理类型,;冻结商户金额,;冻结关联商户金额,;关联商户号,
                    map.put("上游退还金额", transferEntity.get("upReturnMoney"));
                    map.put("威力处理类型", transferEntity.get("mchntDealWith"));
                    map.put("冻结商户金额", transferEntity.get("mchntFreezeMoney"));

                    map.put("冻结商户差额", complaintManage.getMchntFreezeDiffer() == null ? "0" : complaintManage.getUpFreezeMoney() + "");
                    map.put("冻结关联商户金额", transferEntity.get("mchntFreezeDiffer"));
                    map.put("关联商户号", transferEntity.get("mchntFreezeCd"));
                    // ;退还商户金额,;投诉人,;投诉人联系方式,;退款卡号信息,;
                    map.put("退还商户金额", transferEntity.get("mchntReturnMoney"));
                    map.put("投诉人", transferEntity.get("complaintor"));
                    map.put("投诉人联系方式", transferEntity.get("complaintorPhone"));
                    map.put("退款卡号信息", transferEntity.get("accName"));
                    // 平台订单号,商户订单号,交易金额,卡号,银行名称,
                    map.put("平台订单号", map2.get("chnlOrderId"));
                    map.put("商户订单号", map2.get("orderId"));
                    map.put("交易金额", map2.get("transAt"));
                    map.put("卡号", map2.get("accNo"));
                    map.put("银行名称", map2.get("bankName"));
                    // 交易方式,交易创建日期,交易创建时间,商户号,商户名称
                    map.put("交易方式", map2.get("payType"));
                    map.put("交易创建日期", map2.get("extTransDt"));
                    map.put("交易创建时间", map2.get("extTransTm"));
                    map.put("商户号", map2.get("mchntCd"));
                    map.put("商户名称", map2.get("userId"));

                    // 渠道商户编号,渠道商户名称,处理结果,退款金额
                    map.put("渠道商户编号", map2.get("chnlMchntCd"));
                    map.put("渠道商户名称", map2.get("chnlMchntDesc"));
                    // 处理结果
                    String processResult = map2.get("processResult") == null ? "" : map2.get("processResult");
                    map.put("processResult", EnumUtil.translate(ComplaintEnums.ProcessResult.class, processResult, true));
                    map.put("投诉详情处理结果", map2.get("processResult"));
                    map.put("退款金额", map2.get("complaintMoney"));

                    listMap.add(map);
                }
            } else {
                Map<String, String> map = new HashMap<String, String>();
                map.put("接诉日期", transferEntity.get("recCrtTs"));
                map.put("投诉事件编号", transferEntity.get("complaintId"));
                map.put("投诉商户名称", transferEntity.get("siteDesc"));
                map.put("涉诉交易总额", sum_dealMoney + "");
                map.put("退款总额", sum_complaintMoney + "");
                // 处理记录,平台订单号,商户订单号,交易金额
                map.put("处理记录", transferEntity.get("dealHistory"));
                // 投诉类型,;渠道名称,;备注,
                map.put("投诉类型", transferEntity.get("complaintType"));
                map.put("渠道名称", transferEntity.get("chnlId"));
                map.put("备注", transferEntity.get("complaintRequire"));
                // ;处理状态,;处理结果,;上游处理类型,;上游冻结金额,;上游冻结差额,
                map.put("处理状态", transferEntity.get("processState"));
                map.put("处理结果", transferEntity.get("processResult"));
                map.put("上游处理类型", transferEntity.get("upDealWith"));
                map.put("上游冻结金额", transferEntity.get("upFreezeMoney"));
                map.put("上游冻结差额", transferEntity.get("upFreezeDiffer"));
                // ;上游退还金额,;威力处理类型,;冻结商户金额,;冻结关联商户金额,;关联商户号,
                map.put("上游退还金额", transferEntity.get("upReturnMoney"));
                map.put("威力处理类型", transferEntity.get("mchntDealWith"));
                map.put("冻结商户金额", transferEntity.get("mchntFreezeMoney"));
                map.put("冻结关联商户金额", transferEntity.get("mchntFreezeDiffer"));
                map.put("关联商户号", transferEntity.get("mchntFreezeCd"));
                // ;退还商户金额,;投诉人,;投诉人联系方式,;退款卡号信息,;
                map.put("退还商户金额", transferEntity.get("mchntReturnMoney"));
                map.put("投诉人", transferEntity.get("complaintor"));
                map.put("投诉人联系方式", transferEntity.get("complaintorPhone"));
                map.put("退款卡号信息", transferEntity.get("accName"));
                // 平台订单号, 商户订单号,交易金额,卡号,银行名称,
                map.put("平台订单号", "");
                map.put("商户订单号", "");
                map.put("交易金额,", "0.00");
                map.put("卡号", "");
                map.put("银行名称", "");
                // 交易方式,交易创建日期,交易创建时间,商户号,商户名称
                map.put("交易创建日期", "");
                map.put("交易创建时间", "");
                map.put("商户号", "");
                map.put("商户名称", "");
                // 渠道商户编号,渠道商户名称,处理结果,退款金额
                map.put("渠道商户编号", "");
                map.put("渠道商户名称", "");
                map.put("投诉详情处理结果", "");
                map.put("退款金额", "0.00");
                listMap.add(map);
            }
        }

        pager.setResultList(listMap);
        String filename = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_PROFIT_BATCH_WITHDRAW_FILENM);
        try {
            commonBO.exportToExcel(pager, mapSummary, filename, Constant.UTF8, response);
            return null;
        } catch (Exception e) {
            return super.toManage(model, true, RESULT_BASE_URI, e.getMessage());
        }
    }

    @RequestMapping(value = "/downExport.do")
    public String downExport(HttpServletRequest request, HttpServletResponse response) {
        String resultFileNm = commonBO.getSessionAttr(BMConstants.SESSION_KEY_TERM_EXPORT_RSLT_FILENM);
        this.logText(BmEnums.FuncInfo._1100010000.getDesc(), CommonEnums.OpType.DOWNLOAD_FILE,
                "下载导出结果文件:" + resultFileNm);
        commonBO.downFile(BMConfigCache.getConfig(BMConstants.CONFIG_KEY_TERMINAL_EXPORT_FILE_PATH), resultFileNm,
                Constant.UTF8, response);
        return null;
    }

    /**
     * 查询是否存在此平台订单号 chnlOrderId
     *
     * @param request
     * @param response
     * @param terraceTransId
     */
    @RequestMapping(value = "/checkTerraceTransId.do", method = RequestMethod.POST)
    public void checkTerraceTransId(HttpServletRequest request, HttpServletResponse response, String terraceTransId) {
        TxnLogView txnLogView = this.queryTxnLogVieWByParam(terraceTransId);
        if (txnLogView != null) {
            txnLogView = changeTxnlogView(txnLogView);
        }
        try {
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(txnLogView));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public TxnLogView queryTxnLogVieWByParam(String terraceTransId) {
        ITxnLogViewService iTxnLogViewService = this.getDBService(ITxnLogViewService.class);
        // 获得月份
        SimpleDateFormat simpleDateFormat_month = new SimpleDateFormat("yyyyMM");

        String format_month_start = Integer.valueOf(simpleDateFormat_month.format(new Date())) - 1 + "01";
        // 查询时间只包括这个月跟上个月的
        Map<String, String> qryParamMap = new HashMap<String, String>();
        // 先找上个月的
        qryParamMap.put("chnlOrderId", terraceTransId);    //08
        List<TxnLogView> select = iTxnLogViewService.select(format_month_start, qryParamMap);
        TxnLogView txnLogView = null;
        if (select.size() > 0) {
            txnLogView = select.get(0);
        }
        

        // 上个月查不到再查今天的
        if (txnLogView == null) {
            Map<String, String> qryParamMap_now = new HashMap<String, String>();
            // 先找上个月的
            qryParamMap_now.put("chnlOrderId", terraceTransId);
            List<TxnLogView> select1 = iTxnLogViewService.select(null, qryParamMap_now);
            if (select1.size() > 0) {
                txnLogView = select1.get(0);
            }
            
        }
        return txnLogView;
    }

    /**
     * 判断重复方法
     *
     * @param str
     * @return
     */
    public boolean isTrue(Object str) {
        if (str != null && !"".equals(str.toString().trim())) {
            return true;
        }
        return false;
    }

    /**
     * 转换格式
     *
     * @return
     */
    public TxnLogView changeTxnlogView(TxnLogView txnLogView) {
        txnLogView.setChnlOrderId(txnLogView.getChnlOrderId() == null ? "" : txnLogView.getChnlOrderId());
        txnLogView.setOrderId(txnLogView.getOrderId() == null ? "" : txnLogView.getOrderId());
        txnLogView.setTransAt(txnLogView.getTransAt().toString() == null ? 0 : txnLogView.getTransAt() / 100);
        txnLogView.setAccNo(txnLogView.getAccNo() == null ? "" : txnLogView.getAccNo());
        txnLogView.setBankName(txnLogView.getBankName() == null ? "" : txnLogView.getBankName());
        txnLogView.setIntTransCd(txnLogView.getIntTransCd() == null ? ""
                : EnumUtil.translate(TxnEnums.TxnType.class, txnLogView.getIntTransCd(), true));
        txnLogView.setExtTransDt(txnLogView.getExtTransDt() == null ? "" : txnLogView.getExtTransDt());
        txnLogView.setExtTransTm(txnLogView.getExtTransTm() == null ? "" : txnLogView.getExtTransTm());
        txnLogView.setMchntCd(txnLogView.getMchntCd() == null ? "" : txnLogView.getMchntCd());
        txnLogView.setMchntCnAbbr(txnLogView.getMchntCnAbbr() == null ? "" : txnLogView.getMchntCnAbbr());
        txnLogView.setChnlMchntCd(txnLogView.getChnlMchntCd() == null ? "" : txnLogView.getChnlMchntCd());
        txnLogView.setChnlMchntDesc(txnLogView.getChnlMchntDesc() == null ? "" : txnLogView.getChnlMchntDesc());
        return txnLogView;
    }

    public List<Map<String, String>> publicChange(ComplaintManage entity) {
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        // 商户订单号
        String terraceTransId = entity.getTerraceTransId();
        if (terraceTransId != null && !"".equals(terraceTransId)) {
            // 平台订单号
            String[] split = terraceTransId.split(",");

            // 商户号
            String[] mchntTransIds = null;
            if (isTrue(entity.getMchntTransId())) {
                mchntTransIds = entity.getMchntTransId().split(",");
            }

            // 交易金额
            String[] DealMoneys = null;
            if (isTrue(entity.getDealMoney())) {
                DealMoneys = entity.getDealMoney().toString().split(",");
            }

            // 卡号
            String[] complaintCards = null;
            if (isTrue(entity.getComplaintCard())) {
                complaintCards = entity.getComplaintCard().split(",");
            }

            // 交易方式
            String[] dealTypes = null;
            if (isTrue(entity.getDealType())) {
                dealTypes = entity.getDealType().split(",");
            }

            // 银行名称
            String[] bankNames = null;
            if (isTrue(entity.getBankName())) {
                bankNames = entity.getBankName().split(",");
            }

            // 处理日期
            String[] dealDts = null;
            if (isTrue(entity.getDealDt())) {
                dealDts = entity.getDealDt().split(",");
            }

            // 处理时间
            String[] dealTms = null;
            if (isTrue(entity.getDealTm())) {
                dealTms = entity.getDealTm().split(",");
            }

            // 商户编号
            String[] mchntCds = null;
            if (isTrue(entity.getMchntCd())) {
                mchntCds = entity.getMchntCd().split(",");
            }

            // 商户名称
            String[] mchntCnNms = null;
            if (isTrue(entity.getMchntCnNm())) {
                mchntCnNms = entity.getMchntCnNm().split(",");
            }

            // 渠道商户编号
            String[] chnlMchntCds = null;
            if (isTrue(entity.getChnlMchntCd())) {
                chnlMchntCds = entity.getChnlMchntCd().split(",");
            }

            // 渠道商户名称
            String[] chnlMchntDescs = null;
            if (isTrue(entity.getChnlMchntDesc())) {
                chnlMchntDescs = entity.getChnlMchntDesc().split(",");
            }

            // 处理结果
            String[] processResults = null;
            if (entity.getDealProcessResult() != null) {
                processResults = entity.getDealProcessResult().split(",");
            }

            // 退款金额
            List<String> complaintMoneyList = new ArrayList<String>();
            if (entity.getComplaintMoney() != null) {
                String[] complaintMoneys = entity.getComplaintMoney().split(",");
                for (String string : complaintMoneys) {
                    complaintMoneyList.add(string);
                }

            }
            int num = 0;
            double sumMoney = 0;
            double sumReturnMoney = 0;

            for (String string : split) {
                if (string != null && !"".equals(string)) {
                    TxnLogView txnLogView = this.queryTxnLogVieWByParam(string);
                    if (txnLogView != null) {
                        txnLogView = changeTxnlogView(txnLogView);
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("chnlOrderId", txnLogView.getChnlOrderId());
                        map.put("orderId", txnLogView.getOrderId());
                        map.put("transAt", txnLogView.getTransAt().toString());
                        sumMoney += txnLogView.getTransAt();
                        map.put("accNo", txnLogView.getAccNo());
                        map.put("bankName", txnLogView.getBankName());
                        map.put("payType", txnLogView.getIntTransCd());
                        map.put("extTransDt", txnLogView.getExtTransDt());
                        map.put("extTransTm", txnLogView.getExtTransTm());
                        map.put("mchntCd", txnLogView.getMchntCd());
                        map.put("userId", txnLogView.getMchntCnAbbr());
                        map.put("chnlMchntCd", txnLogView.getChnlMchntCd());
                        map.put("chnlMchntDesc", txnLogView.getChnlMchntDesc());
                        map.put("processResult",
                                processResults.length == 0 ? ""
                                        : (processResults.length > 0 && num < processResults.length)
                                        ? processResults[num]
                                        : "");
                        map.put("complaintMoney",
                                complaintMoneyList.size() == 0 ? ""
                                        : (complaintMoneyList.size() > 0 && num < complaintMoneyList.size())
                                        ? complaintMoneyList.get(num)
                                        : "");
                        if (isTrue(map.get("complaintMoney"))) {
                            sumReturnMoney += Double.valueOf(map.get("complaintMoney"));
                        }
                        mapList.add(map);
                        num++;
                    } else {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("chnlOrderId", string);
                        map.put("orderId", mchntTransIds == null ? ""
                                : (mchntTransIds.length > 0 && num < mchntTransIds.length) ? mchntTransIds[num] : "");
                        map.put("transAt", DealMoneys == null ? ""
                                : (DealMoneys.length > 0 && num < DealMoneys.length) ? DealMoneys[num] : "");
                        if (isTrue(map.get("transAt"))) {
                            sumMoney += Double.valueOf(map.get("transAt"));
                        }
                        map.put("accNo",
                                complaintCards == null ? ""
                                        : (complaintCards.length > 0 && num < complaintCards.length)
                                        ? complaintCards[num]
                                        : "");
                        map.put("bankName", bankNames == null ? ""
                                : (bankNames.length > 0 && num < bankNames.length) ? bankNames[num] : "");
                        map.put("payType",
                                dealTypes == null ? ""
                                        : (dealTypes.length > 0 && num < dealTypes.length)
                                        ? EnumUtil.translate(TxnEnums.TxnType.class, dealTypes[num], true)
                                        : "");
                        map.put("extTransDt", dealDts == null ? ""
                                : (dealDts.length > 0 && num < dealDts.length) ? dealDts[num] : "");
                        map.put("extTransTm", dealTms == null ? ""
                                : (dealTms.length > 0 && num < dealTms.length) ? dealTms[num] : "");
                        map.put("mchntCd", mchntCds == null ? ""
                                : (mchntCds.length > 0 && num < mchntCds.length) ? mchntCds[num] : "");
                        map.put("userId", mchntCnNms == null ? ""
                                : (mchntCnNms.length > 0 && num < mchntCnNms.length) ? mchntCnNms[num] : "");
                        map.put("chnlMchntCd", chnlMchntCds == null ? ""
                                : (chnlMchntCds.length > 0 && num < chnlMchntCds.length) ? chnlMchntCds[num] : "");
                        map.put("chnlMchntDesc",
                                chnlMchntDescs == null ? ""
                                        : (chnlMchntDescs.length > 0 && num < chnlMchntDescs.length)
                                        ? chnlMchntDescs[num]
                                        : "");
                        map.put("processResult",
                                processResults.length == 0 ? ""
                                        : (processResults.length > 0 && num < processResults.length)
                                        ? processResults[num]
                                        : "");
                        map.put("complaintMoney",
                                complaintMoneyList.size() == 0 ? ""
                                        : (complaintMoneyList.size() > 0 && num < complaintMoneyList.size())
                                        ? complaintMoneyList.get(num)
                                        : "");
                        if (isTrue(map.get("complaintMoney"))) {
                            sumReturnMoney += Double.valueOf(map.get("complaintMoney"));
                        }
                        num++;
                        mapList.add(map);
                    }
                }
            }
            Map<String, String> map = new HashMap<String, String>();
            map.put("sumMoney", sumMoney + "");
            map.put("sumReturnMoney", sumReturnMoney + "");
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 去除第一个值
     *
     * @param str
     * @return
     */
    public String delFirst(String str) {
        str = str.replace(" ", "");
        if (str != null && !"".equals(str) && str.indexOf(",") > -1) {
            str = str.substring(str.indexOf(",") + 1);
        }
        return str;
    }
}
