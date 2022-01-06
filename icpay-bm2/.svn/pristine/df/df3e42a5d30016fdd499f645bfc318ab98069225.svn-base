package com.icpay.payment.bm.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.icpay.payment.common.enums.ProfitEnums;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.AgentInfo;
import com.icpay.payment.db.service.IAgentInfoService;

public class AgentInfoCache extends CacheBase  implements ICache {

	private static final Logger logger = Logger.getLogger(AgentInfoCache.class);
	private static final AgentInfoCache INSTANCE = new AgentInfoCache();
	
	private Map<String, String> agentNmMapCache = null;
	private Map<String,List<AgentInfo>> agentCacheByType = null;
	
	private AgentInfoCache() {}
	
	public static AgentInfoCache getInstance() {
		return INSTANCE;
	}
	
	public static Map<String, String> getAgentNmMap() {
		Map<String, String> emptyMap = Collections.emptyMap();
		return INSTANCE.agentNmMapCache == null 
			? emptyMap : Collections.unmodifiableMap(INSTANCE.agentNmMapCache);
	}
	
	public static List<AgentInfo> getAgentsByType(ProfitEnums.AgentType type){
		return INSTANCE.agentCacheByType == null 
				? new ArrayList<AgentInfo>() : INSTANCE.agentCacheByType.get(type.getCode()) ;
	}
	
	public static List<AgentInfo> getAgentsByTypes(ProfitEnums.AgentType... types){
		List<AgentInfo> list = new ArrayList<>();
		for(ProfitEnums.AgentType t : types) {
			List<AgentInfo> lista = getAgentsByType(t);
			if (lista!=null) list.addAll(lista);
		}
		return list;
	}
	
	public static String getAgentName(String agentCd) {
		if (INSTANCE.agentNmMapCache == null) return "";
		return INSTANCE.agentNmMapCache.get(agentCd);
	}
	
	public static boolean isAgentExist(String agentCd) {
		if (StringUtil.isBlank(agentCd)) {
			return false;
		}
		return INSTANCE.agentNmMapCache == null 
			? false : INSTANCE.agentNmMapCache.containsKey(agentCd);
	}
	
	@Override
	public void clear() {
		if (agentNmMapCache != null) {
			agentNmMapCache.clear();
			agentNmMapCache = null;
		}
	}
	
	protected void putSelectedAgentsByType(IAgentInfoService svc, Map<String,List<AgentInfo>> cache, ProfitEnums.AgentType agentType) {
		List<AgentInfo> list = svc.selectValidAgentsByType(agentType.getCode());
		cache.put(agentType.getCode(), list);
	}

	@Override
	public void init() {
		logger.info("获取代理商信息开始");
		IAgentInfoService service = DBHessionServiceClient.getService(IAgentInfoService.class);
		agentNmMapCache = service.selectValidAgentNameMap();
		
		Map<String,List<AgentInfo>> cache= new HashMap<>();
		putSelectedAgentsByType(service, cache,  ProfitEnums.AgentType.SELF);
		putSelectedAgentsByType(service, cache,  ProfitEnums.AgentType.CHNL);
		putSelectedAgentsByType(service, cache,  ProfitEnums.AgentType.MER);
		agentCacheByType = cache;
		logger.info("获取代理商信息完成:" + agentNmMapCache.size());
		
	}

	@Override
	public void refresh() {
		this.init();
	}
}
