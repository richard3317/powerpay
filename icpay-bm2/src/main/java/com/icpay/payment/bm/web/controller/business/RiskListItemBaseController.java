package com.icpay.payment.bm.web.controller.business;

import static com.icpay.payment.risk.BmUser.bmUser;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.cache.ChnlMchntInfoCache;
import com.icpay.payment.bm.cache.MchntInfoCache;
import com.icpay.payment.bm.cache.RiskGroupInfoCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.common.constants.Names.INTER_MSG;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.RiskListItemMer;
import com.icpay.payment.db.dao.mybatis.model.RiskListMassItem;
import com.icpay.payment.db.dao.mybatis.model.RiskListMassItemKey;
import com.icpay.payment.db.service.IRiskListItemMerService;
import com.icpay.payment.db.service.IRiskListMassItemService;
import com.icpay.payment.risk.RISK;
import com.icpay.payment.risk.RiskEvent;

public abstract class RiskListItemBaseController extends BaseController {

	//private static final Logger logger = Logger.getLogger(RiskListItemBaseController.class);
	
	protected abstract String getBaseUri();
	protected abstract String getPageConfig();
	protected abstract boolean isMassList();
	protected abstract String[] excludedGroupIds();
	
	protected static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String mchntSt = m.get("mchntSt");
			m.put("mchntStDesc", EnumUtil.translate(CommonEnums.MchntSt.class, mchntSt, false));
			
			String groupId=m.get("groupId"); //RiskGroupInfoCache.getInstance()
			m.put("groupDesc", getGroupDesc(groupId));
			
			String chnlId=m.get("chnlId");
			String mchntCd=m.get("mchntCd");
			if (StringUtil.isBlank(chnlId)||"00".equals(chnlId)) {
				Map<String,String> mchnt = MchntInfoCache.getInstance().get(mchntCd);
				if (!Utils.isEmpty(mchnt)) {
					String mchntDesc = mchnt.get("mchntCnNm");
					m.put("mchntDesc", String.format("[%s]%s", mchntCd,mchntDesc));
				}
			}
			else {
				Map<String,String> chnlMchnt = ChnlMchntInfoCache.getInstance().get(chnlId, mchntCd);
				if (!Utils.isEmpty(chnlMchnt)) {
					String chnlMchntDesc = chnlMchnt.get("chnlMchntDesc");
					m.put("mchntDesc", String.format("[%s-%s]%s", chnlId, mchntCd,chnlMchntDesc));
				}
			}
		}
	};
	
	protected static String getGroupDesc(String groupId) {
		if (StringUtil.isBlank(groupId)) return "";
		Map<String,String> group = RiskGroupInfoCache.getInstance().get(groupId);
		if (!Utils.isEmpty(group))
			return String.format("%s-%s", groupId, group.get("groupNm"));
		return "";
	}
	
	public String manage(Model model, String groupId) {
		//model.addAttribute("riskGroups", this.getRiskGroups());
		if (!StringUtil.isBlank(groupId)) {
			model.addAttribute("groupId", groupId);
			model.addAttribute("groupDesc", getGroupDesc(groupId));
		}
		else {
			model.addAttribute("riskGroups", this.getRiskGroups());
		}
		return super.toManage(model, false, getBaseUri());
	}
	
	public String backToManage(Model model) {
		model.addAttribute("riskGroups", this.getRiskGroups());
		return super.toManage(model, true, getBaseUri());
	}
	
	protected List<Map<String,String>> getRiskGroups(){
		return RiskGroupInfoCache.getInstance().list(isMassList(), excludedGroupIds());
	}
	
	protected Map<String,String> getRiskGroup(String groupId){
		return RiskGroupInfoCache.getInstance().get(groupId);
	}
	
	public AjaxResult getRiskGroup(Model model, String groupId) {
		Map<String,String> grp= getRiskGroup(groupId);
		return commonBO.buildSuccResp("selectedGroup", grp);
	}
	
	public String add(Model model, String groupId) {
		model.addAttribute("groupId", groupId);
		model.addAttribute("groupDesc", getGroupDesc(groupId));
		model.addAttribute("riskGroups", this.getRiskGroups());
		return super.toAdd(model, getBaseUri());
	}
	
	public AjaxResult addRiskItemSubmit(Integer groupId, String chnlId, String mchntCd, String items, String comment) {
		IRiskListItemMerService svc = this.getDBService(IRiskListItemMerService.class);
		StringBuilder errBuf=new StringBuilder();
		RiskListItemMer entity = null;
		String[] itemsAry = items.split("\n");
		for (String item: itemsAry) {
			entity = new RiskListItemMer();
			entity.setGroupId(groupId);
			entity.setComment(comment);
			entity.setChnlId(chnlId);
			entity.setMchntCd(mchntCd);
			item=StringUtil.trim(item);
			entity.setItem(item);
			entity.setLastOperId(this.getSessionUsrId());
			int r=0;
			try {
				r = svc.add(entity);
			} catch (Exception e) {
				r=0;
				this.error(e.getMessage(), e);
			}
			if (r<=0)
			{
				this.error(String.format("[risk][add] Entity add error: groupId=%s, chnlId=%s, mchntCd=%s, item=%s", groupId, chnlId, mchntCd, item));
				errBuf.append(""+item).append(",");
			}
			else {
				this.debug(String.format("[risk][add] Entity: groupId=%s, chnlId=%s, mchntCd=%s, item=%s", groupId, chnlId, mchntCd, item));
				this.logObj(BmEnums.FuncInfo._7000020000.getCode(), CommonEnums.OpType.ADD, entity);
				
				RiskEvent.bm()
					.who(bmUser(commonBO.getSessionUser().getUsrId()))
					.event(RISK.Event.Risklist_Item_Add)
					.result(RISK.Result.Ok)
					.target(groupId + "|" + chnlId + "|" + mchntCd + "|" + item + "|" + comment)
					.message(String.format("用户： %s, 新增风控规则名单配置。风控组： %s, 渠道： %s, 商户号： %s, 风控项目： %s, 备注： %s", commonBO.getSessionUser().getUsrId(), groupId, chnlId, mchntCd, item, comment))
					.params("groupId", groupId)
					.params("chnlId", chnlId)
					.params("mchntCd", mchntCd)
					.params("item", item)
					.params("comment", comment)
					.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
					.submit();
			}
		}
		if (errBuf.length()>1) {
			errBuf.deleteCharAt(errBuf.length()-1);
			return commonBO.buildErrorResp(String.format("以下项目新增失败：\n%s", errBuf.toString()));
		}
		else
			return commonBO.buildSuccResp();
	}
	
	public AjaxResult addRiskMassItemSubmit(Integer groupId, String chnlId, String mchntCd, String items, String comment) {
		IRiskListMassItemService svc = this.getDBService(IRiskListMassItemService.class);
		StringBuilder errBuf=new StringBuilder();
		RiskListMassItem entity = null;
		String[] itemsAry = items.split("\n");
		for (String item: itemsAry) {
			entity = new RiskListMassItem();
			entity.setGroupId(groupId);
			entity.setComment(comment);
			entity.setChnlId(chnlId);
			entity.setMchntCd(mchntCd);
			item=StringUtil.trim(item);
			entity.setItem(item);
			int r=0;
			try {
				r = svc.add(entity);
			} catch (Exception e) {
				r=0;
				this.error(e.getMessage(), e);
			}
			
			if (r<=0)
			{
				this.error(String.format("[risk][add] Entity add error: groupId=%s, chnlId=%s, mchntCd=%s, item=%s", groupId, chnlId, mchntCd, item));
				errBuf.append(""+item).append(",");
			}
			else {
				this.debug(String.format("[risk][add] Entity: groupId=%s, chnlId=%s, mchntCd=%s, item=%s", groupId, chnlId, mchntCd, item));
				this.logObj(BmEnums.FuncInfo._7000020000.getCode(), CommonEnums.OpType.ADD, entity);
			
				RiskEvent.bm()
					.who(bmUser(commonBO.getSessionUser().getUsrId()))
					.event(RISK.Event.Risklist_Massitem_Add)
					.result(RISK.Result.Ok)
					.target(groupId + "|" + chnlId + "|" + mchntCd + "|" + item + "|" + comment)
					.message(String.format("用户： %s, 新增风控规则黑名单配置。风控组： %s, 渠道： %s, 商户号： %s, 风控项目： %s, 备注： %s", commonBO.getSessionUser().getUsrId(), groupId, chnlId, mchntCd, item, comment))
					.params("groupId", groupId)
					.params("chnlId", chnlId)
					.params("mchntCd", mchntCd)
					.params("item", item)
					.params("comment", comment)
					.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
					.submit();
			}
		}
		if (errBuf.length()>1) {
			errBuf.deleteCharAt(errBuf.length()-1);
			return commonBO.buildErrorResp(String.format("以下项目新增失败：\n%s", errBuf.toString()));
		}
		else
			return commonBO.buildSuccResp();
	}

	
	public AjaxResult qryRiskItems(int pageNum, int pageSize) {
		IRiskListItemMerService svc = this.getDBService(IRiskListItemMerService.class);
		Pager<RiskListItemMer> p = svc.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, getPageConfig(), ENTITY_TRANSFER));
	}
	
	public AjaxResult qryRiskMassItems(int pageNum, int pageSize) {
		IRiskListMassItemService svc = this.getDBService(IRiskListMassItemService.class);
		Pager<RiskListMassItem> p = svc.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, getPageConfig(), ENTITY_TRANSFER));
	}
	
	public AjaxResult deleteRiskItem(Long id, Integer groupId) {
		IRiskListItemMerService svc = this.getDBService(IRiskListItemMerService.class);
		RiskListItemMer entity = svc.selectByPrimaryKey(id);
		AssertUtil.objIsNull(entity, "查无记录: "+id);
		int rows= svc.delete(id);
		AssertUtil.intIsZero(rows, String.format("删除失败： id=%s, item=%s", entity.getId(), entity.getItem()));
		this.logObj(BmEnums.FuncInfo._7000020000.getCode(), CommonEnums.OpType.DELETE, entity);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Risklist_Item_Del)
			.result(RISK.Result.Ok)
			.target(id + "|" + entity.getItem())
			.message(String.format("用户： %s, 删除风控规则名单配置。ID: %s, 项目： %s", commonBO.getSessionUser().getUsrId(), id, entity.getItem()))
			.params("id", id)
			.params("item", entity.getItem())
			.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
			.submit();
		
		return commonBO.buildSuccResp();
	}
	
	public AjaxResult deleteRiskMassItem(Integer groupId, String chnlId, String mchntCd, String item) {
		info("删除： groupId=%s, chnlId=%s, mchntCd=%s, item=%s", groupId, chnlId, mchntCd, item);
		
		IRiskListMassItemService svc = this.getDBService(IRiskListMassItemService.class);
		RiskListMassItemKey key = new RiskListMassItemKey(groupId, chnlId, mchntCd, item);
		int rows= svc.delete(key);
		AssertUtil.intIsZero(rows, String.format("删除失败： groupId=%s, chnlId=%s, mchntCd=%s, item=%s", groupId, chnlId, mchntCd, item));
		this.logObj(BmEnums.FuncInfo._7000020000.getCode(), CommonEnums.OpType.DELETE, key);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Risklist_Massitem_Del)
			.result(RISK.Result.Ok)
			.target(groupId + "|" + chnlId + "|" + mchntCd + "|" + item)
			.message(String.format("用户： %s, 删除风控规则黑名单配置。风控组： %s, 渠道： %s, 商户号： %s, 风控项目： %s", commonBO.getSessionUser().getUsrId(), groupId, chnlId, mchntCd, item))
			.params("groupId", groupId)
			.params("chnlId", chnlId)
			.params("mchntCd", mchntCd)
			.params("item", item)
			.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
			.submit();
		
		return commonBO.buildSuccResp();
	}
	
	public String edit(Model model, Long id) {
		IRiskListItemMerService riskListItemMerService = this.getDBService(IRiskListItemMerService.class);
		RiskListItemMer entity = riskListItemMerService.selectByPrimaryKey(id);
		AssertUtil.objIsNull(entity, "未找到规则信息:" + id);
		model.addAttribute("riskGroups", this.getRiskGroups());
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_RISKLISTITEM, ENTITY_TRANSFER));
		return super.toEdit(model, getBaseUri());
	}
	
	public @ResponseBody AjaxResult editSubmit(RiskListItemMer entity) {
		AssertUtil.objIsNull(entity, "entity is null.");
		
		Long id = entity.getId();
		IRiskListItemMerService riskListItemMerService = this.getDBService(IRiskListItemMerService.class);
		RiskListItemMer dbEntity = riskListItemMerService.selectByPrimaryKey(id);
		AssertUtil.objIsNull(dbEntity, "未找到规则记录");
		
		entity.setRecCrtTs(dbEntity.getRecCrtTs());
		entity.setLastOperId(this.getSessionUsrId());
		riskListItemMerService.update(entity);
		this.logObj(BmEnums.FuncInfo._7000030000.getCode(), CommonEnums.OpType.UPDATE, entity);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Risklist_Item_Modify)
			.result(RISK.Result.Ok)
			.target(entity.getGroupId() + "|" + entity.getChnlId() + "|" + entity.getMchntCd() + "|" + entity.getItem() + "|" + entity.getComment())
			.message(String.format("用户： %s, 修改风控规则名单配置。风控组： %s, 渠道： %s, 商户号： %s, 风控项目： %s, 备注： %s", commonBO.getSessionUser().getUsrId(), entity.getGroupId(), entity.getChnlId(), entity.getMchntCd(), entity.getItem(), entity.getComment()))
			.params("groupId", entity.getGroupId())
			.params("chnlId", entity.getChnlId())
			.params("mchntCd", entity.getMchntCd())
			.params("item", entity.getItem())
			.params("comment", entity.getComment())
			.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
			.submit();
		
		return commonBO.buildSuccResp();
	}
}
