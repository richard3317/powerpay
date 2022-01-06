package com.icpay.payment.bm.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.BmFuncInfo;
import com.icpay.payment.db.dao.mybatis.model.BmRoleInfo;
import com.icpay.payment.db.service.IBmFuncInfoService;
import com.icpay.payment.db.service.IBmRoleService;

public class AuthDataCache extends CacheBase implements ICache {

	private static final Logger logger = Logger.getLogger(AuthDataCache.class);
	/**
	 * 静态单例
	 */
	private static final AuthDataCache INSTANCE = new AuthDataCache();
	
	private static final String SYS_MODULE_CD = "99";
	
	public static final String FUNC_TP_0 = "0"; // 功能码级别0-模块
	public static final String FUNC_TP_1 = "1"; // 功能码级别1-菜单
	public static final String FUNC_TP_2 = "2"; // 功能码级别2-功能点
	
	private Set<String> funcCodeCache = null; // 所有的功能权限码缓存
	private Set<String> superAdminCodeCache = null; // 系统管理权限码缓存，仅超级管理员可以访问
	private List<BmFuncInfo> moduleCache = null; // 模块缓存
	private List<BmFuncInfo> menuCache = null; // 菜单缓存
	private List<BmFuncInfo> functionCache = null; // 功能点缓存
	private Map<String, String> url2FuncCodeCache = null; // URL对应的功能码缓存
	private Map<String, BmFuncInfo> code2FuncCache = null; // 功能码对应的功能详情缓存
	private Map<String, Map<String, List<String>>> funcHierCache = null;
	
	private Map<Integer, BmRoleInfo> roleInfoCache = null; // 角色码对应的角色信息缓存
	private Map<Integer, Set<String>> role2FuncCache = null; // 角色码对应的功能权限信息
	
	/**
	 * 私有构造函数
	 */
	private AuthDataCache () {  }
	
	/**
	 * 静态函数获取静态单例
	 * @return
	 */
	public static AuthDataCache getInstance() {
		return INSTANCE;
	}
	
	/**
	 * 获取角色信息Map
	 * @return
	 */
	public Map<Integer, BmRoleInfo> getRoleInfoMap() {
		if (roleInfoCache == null) {
			return Collections.emptyMap();
		} else {
			return roleInfoCache;
		}
	}
	
	/**
	 * 获取菜单信息
	 * @param menuCd
	 * @return
	 */
	public BmFuncInfo getMenuInfo(String menuCd) {
		if (menuCache != null && !StringUtil.isBlank(menuCd)) {
			for (BmFuncInfo func : menuCache) {
				if (func.getFuncCd().equals(menuCd)) {
					return func;
				}
			}
		}
		return null;
	}
	
	/**
	 * 根据功能码获取功能信息
	 * @param funcCd
	 * @return
	 */
	public BmFuncInfo getFuncInfo(String funcCd) {
		if (code2FuncCache != null && !StringUtil.isBlank(funcCd)) {
			return code2FuncCache.get(funcCd);
		}
		return null;
	}
	
	/**
	 * 根据功能权限返回相应的缓存
	 * @param funcTp
	 * @return
	 */
	public List<BmFuncInfo> getFuncCache(String funcTp) {
		if (FUNC_TP_0.equals(funcTp)) {
			return Collections.unmodifiableList(moduleCache);
		} else if (FUNC_TP_1.equals(funcTp)) {
			return Collections.unmodifiableList(menuCache);
		} else if (FUNC_TP_2.equals(funcTp)) {
			return Collections.unmodifiableList(functionCache);
		}
		return Collections.emptyList();
	}
	
	@Override
	public synchronized void init() {
		// 初始化功能权限信息
		this.initFuncInfo();
		// 初始化角色信息
		this.initRoleInfo();
	}

	@Override
	public void refresh() {
		this.init();
	}

	@Override
	public void clear() {
		logger.info("====清空功能权限缓存开始 ====");
		if (funcCodeCache != null) {
			funcCodeCache.clear();
			funcCodeCache = null;
		}
		if (moduleCache != null) {
			moduleCache.clear();
			moduleCache = null;
		}
		if (menuCache != null) {
			menuCache.clear();
			menuCache = null;
		}
		if (functionCache != null) {
			functionCache.clear();
			functionCache = null;
		}
		if (url2FuncCodeCache != null) {
			url2FuncCodeCache.clear();
			url2FuncCodeCache = null;
		}
		if (code2FuncCache != null) {
			code2FuncCache.clear();
			code2FuncCache = null;
		}
		if (funcHierCache != null) {
			funcHierCache.clear();
			funcHierCache = null;
		}
		if (roleInfoCache != null) {
			roleInfoCache.clear();
		}
		if (role2FuncCache != null) {
			role2FuncCache.clear();
		}
		logger.info("====清空功能权限缓存完成====");
	}

	/**
	 * 构造功能权限树
	 * @param checkedFuncs
	 * @return
	 */
	public String funcTreeData(Set<String> checkedFuncs) {
		StringBuilder treeData = new StringBuilder("[");
		
		List<BmFuncInfo> moduleLst = new ArrayList<BmFuncInfo>();
		for (int i = 0; i < moduleCache.size(); i ++) {
			if (moduleCache.get(i).getModuleCd().equals(SYS_MODULE_CD)) {
				continue;
			}
			moduleLst.add(moduleCache.get(i));
		}
		
		// 拼装模块数据
		for (int i = 0; i < moduleLst.size(); i ++) {
			BmFuncInfo module = moduleLst.get(i);
			treeData.append("{\"id\":\"" + module.getFuncCd() + "\"");
			treeData.append(",\"text\":\"" + module.getFuncNm() + "\"");
			treeData.append(",\"children\":[");
			Map<String, List<String>> mn = funcHierCache.get(module.getModuleCd());
			int cnt = 0;
			
			// 拼装菜单数据
			for (String menuCd : mn.keySet()) {
				cnt ++;
				BmFuncInfo menu = code2FuncCache.get(menuCd);
				treeData.append("{\"id\":\"" + menu.getFuncCd() + "\"");
				treeData.append(",\"text\":\"" + menu.getFuncNm() + "\"");
				// 拼装功能点数据
				List<String> functions = mn.get(menuCd);
				if (functions.size() == 0) {
					if (checkedFuncs != null && checkedFuncs.contains(menuCd)) {
						treeData.append(",\"checked\":true");
					}
				}
				treeData.append(",\"children\":[");
				for (int j = 0; j < functions.size(); j ++) {
					BmFuncInfo f = code2FuncCache.get(functions.get(j));
					treeData.append("{\"id\":\"" + f.getFuncCd() + "\"");
					treeData.append(",\"text\":\"" + f.getFuncNm() + "\"");
					if (checkedFuncs != null && checkedFuncs.contains(f.getFuncCd())) {
						treeData.append(",\"checked\": true");
					}
					treeData.append("}");
					
					if (j != functions.size() - 1) {
						treeData.append(",");
					}
				}
				treeData.append("]}");
				if (cnt != mn.size()) {
					treeData.append(",");
				}
			}
			treeData.append("]}");
			if (i != moduleLst.size() - 1) {
				treeData.append(",");
			}
		}
		treeData.append("]");
		return treeData.toString();
	}
	
	/**
	 * 获取角色拥有的权限
	 * @param roleId
	 * @return
	 */
	public Set<String> getRoleFuncSet(int roleId) {
		// 如果是超级管理员，则返回所有的权限码
		// 否则，则根据角色ID返回权限码
		if (BMConstants.SUPER_ADMIN_ROLE_ID == roleId) {
			return Collections.unmodifiableSet(superAdminCodeCache);
		} else {
			if (role2FuncCache != null) {
				return Collections.unmodifiableSet(role2FuncCache.get(roleId));
			} else {
				return Collections.emptySet();
			}
		}
	}
	
	/**
	 * 获取角色信息
	 * @param roleId
	 * @return
	 */
	public BmRoleInfo getRoleInfo(int roleId) {
		if (roleInfoCache != null) {
			return roleInfoCache.get(roleId);
		}
		return null;
	}
	
	/**
	 * 初始化功能权限信息
	 */
	private void initFuncInfo() {
		try {
			logger.info("初始化功能权限信息开始");
			IBmFuncInfoService funcInfoService = 
				DBHessionServiceClient.getService(IBmFuncInfoService.class);
			List<BmFuncInfo> funcLst = funcInfoService.getAllBmFuncInfo();
			Set<String> funcCodeSet = new HashSet<String>();
			Set<String> superAdminCodeSet = new HashSet<String>();
			List<BmFuncInfo> moduleLst = new ArrayList<BmFuncInfo>();
			List<BmFuncInfo> menuLst = new ArrayList<BmFuncInfo>();
			List<BmFuncInfo> functionLst = new ArrayList<BmFuncInfo>();
			Map<String, BmFuncInfo> code2FuncMap = new LinkedHashMap<String, BmFuncInfo>();
			Map<String, String>  url2FuncCodeMap = new HashMap<String, String>();
			Map<String, Map<String, List<String>>> funcHierMap = new LinkedHashMap<String, Map<String, List<String>>>();
			for (BmFuncInfo func : funcLst) {
				String funcCd = func.getFuncCd();
				if (StringUtil.isBlank(funcCd)) {
					logger.info("该条功能权限信息不符合规则:" + BeanUtils.describe(func));
					continue;
				}
				funcCodeSet.add(funcCd);
				if (SYS_MODULE_CD.equals(func.getModuleCd())) {
					superAdminCodeSet.add(funcCd);
				}
				code2FuncMap.put(funcCd, func);
				String href = func.getFuncHref();
				if (!StringUtil.isBlank(href)) {
					url2FuncCodeMap.put(func.getFuncHref(), funcCd);
				}
				
				String moduleCd = func.getModuleCd();
				if (FUNC_TP_0.equals(func.getFuncTp())) {
					moduleLst.add(func);
					
					if (!funcHierMap.containsKey(moduleCd)) {
						funcHierMap.put(moduleCd, new LinkedHashMap<String, List<String>>());
					}
				} else if (FUNC_TP_1.equals(func.getFuncTp())) {
					menuLst.add(func);
					
					Map<String, List<String>> m = null;
					if (funcHierMap.containsKey(moduleCd)) {
						m = funcHierMap.get(moduleCd);
						if (!m.containsKey(funcCd)) {
							m.put(funcCd, new ArrayList<String>());
						}
					} else {
						m = new LinkedHashMap<String, List<String>>();
						m.put(funcCd, new ArrayList<String>());
						funcHierMap.put(moduleCd, m);
					}
				} else if (FUNC_TP_2.equals(func.getFuncTp())) {
					functionLst.add(func);
					
					Map<String, List<String>> m = null;
					String parentCd = func.getParentCd();
					if (funcHierMap.containsKey(moduleCd)) {
						m = funcHierMap.get(moduleCd);
						if (m.containsKey(parentCd)) {
							m.get(parentCd).add(funcCd);
						} else {
							List<String> lst = new ArrayList<String>();
							lst.add(funcCd);
							m.put(parentCd, lst);
						}
					} else {
						m = new LinkedHashMap<String, List<String>>();
						List<String> lst = new ArrayList<String>();
						lst.add(funcCd);
						m.put(parentCd, lst);
						funcHierMap.put(moduleCd, m);
					}
				} else {
					logger.error("该功能信息未分类");
				}
			}
			
			this.funcCodeCache = funcCodeSet;
			this.superAdminCodeCache = superAdminCodeSet;
			this.code2FuncCache = code2FuncMap;
			this.url2FuncCodeCache = url2FuncCodeMap;
			this.moduleCache = moduleLst;
			this.menuCache = menuLst;
			this.functionCache = functionLst;
			this.funcHierCache = funcHierMap;
			
			logger.info("功能权限信息个数:" + funcLst.size());
			logger.info("模块个数:" + moduleCache.size());
			logger.info("菜单个数:" + menuCache.size());
			logger.info("功能点个数:" + functionCache.size());
			
			logger.info("初始化功能权限信息完成");
		} catch (Exception e) {
			logger.error("初始化功能权限信息失败", e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 初始化角色信息缓存
	 */
	private void initRoleInfo() {
		try {
			logger.info("初始化角色信息缓存开始");
			IBmRoleService roleService = 
				DBHessionServiceClient.getService(IBmRoleService.class);
			List<BmRoleInfo> roleList = roleService.getAllRoleInfo();
			Map<Integer, BmRoleInfo> roleId2RoleMap = new LinkedHashMap<Integer, BmRoleInfo>();
			Map<Integer, Set<String>> role2funcMap = new LinkedHashMap<Integer, Set<String>>();
			for (BmRoleInfo r : roleList) {
				Integer roleId = r.getRoleId();
				roleId2RoleMap.put(roleId, r);
				role2funcMap.put(roleId, roleService.selectRoleFunc(roleId));
			}
			roleInfoCache = roleId2RoleMap;
			role2FuncCache = role2funcMap;
			logger.info("初始化角色信息缓存成功");
		} catch (Exception e) {
			logger.error("初始化角色信息缓存失败", e);
			throw new RuntimeException(e);
		}
	}
	
	
	/**根据链接路径获取对应的功能菜单信息*/
	public  BmFuncInfo findByFuncHref(String href) {
		if (url2FuncCodeCache == null || code2FuncCache == null) {
			initFuncInfo();
		}
		String funcCd = url2FuncCodeCache.get(href);
		if (StringUtil.isBlank(funcCd)) {
			logger.info("请求链接未找到对应的功能码：" + href);
			return null;
		}
		return code2FuncCache.get(funcCd);
	}
}
