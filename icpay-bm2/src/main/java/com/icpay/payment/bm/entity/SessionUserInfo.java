package com.icpay.payment.bm.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.icpay.payment.bm.cache.AuthDataCache;
import com.icpay.payment.db.dao.mybatis.model.BmFuncInfo;

public class SessionUserInfo {

	private String usrId;
	private String usrNm;
	private boolean isIssHead;	//是否属于总公司用户 
	private String loginIp; // 本次登录IP
	private String loginTs; // 本次登录时间
	private String lastLoginIp; // 上次登录IP
	private String lastLoginTs; // 上次登录时间
	
	private int roleId;
	private String roleNm;
	
	private Set<String> funcSet = new HashSet<String>();
	private List<MenuNode> menuLst = null;
	
	public String getUsrId() {
		return usrId;
	}
	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}
	public String getUsrNm() {
		return usrNm;
	}
	public void setUsrNm(String usrNm) {
		this.usrNm = usrNm;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleNm() {
		return roleNm;
	}
	public void setRoleNm(String roleNm) {
		this.roleNm = roleNm;
	}
	public String getLastLoginTs() {
		return lastLoginTs;
	}
	public void setLastLoginTs(String lastLoginTs) {
		this.lastLoginTs = lastLoginTs;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public String getLoginTs() {
		return loginTs;
	}
	public void setLoginTs(String loginTs) {
		this.loginTs = loginTs;
	}
	public Set<String> getFuncSet() {
		return funcSet;
	}
	
	
	public static final Integer MaxVisibleIndex=10000;
	
	public void setFuncSet(Set<String> funcSet) {
		this.funcSet = funcSet;
		
		// 根据功能权限列表构造菜单树
		if (funcSet != null && funcSet.size() > 0) {
			AuthDataCache authCache = AuthDataCache.getInstance();
			Map<String, MenuNode> menuMap = new HashMap<String, MenuNode>();
			for (String funcCd : funcSet) {
				BmFuncInfo func = authCache.getFuncInfo(funcCd);
				if (func == null) {
					continue;
				}
				if (func.getFuncIdx()<MaxVisibleIndex && AuthDataCache.FUNC_TP_0.equals(func.getFuncTp())) {
					// 如果功能码类型是模块，则存放到map中
					if (menuMap.get(funcCd) == null) {
						MenuNode m = new MenuNode();
						m.setFuncCd(funcCd);
						m.setFuncNm(func.getFuncNm());
						m.setFuncIdx(func.getFuncIdx());
						m.setFuncTp(func.getFuncTp());
						menuMap.put(funcCd, m);
					}
				} else if (func.getFuncIdx()<MaxVisibleIndex && AuthDataCache.FUNC_TP_1.equals(func.getFuncTp())) {
					// 如果功能码是菜单类型，则先判断父功能是否已在map中，如果不在，则要先构造父节点
					MenuNode p = menuMap.get(func.getParentCd());
					if (p == null) {
						p = new MenuNode();
						BmFuncInfo parent = authCache.getFuncInfo(func.getParentCd());
						p.setFuncCd(parent.getFuncCd());
						p.setFuncNm(parent.getFuncNm());
						p.setFuncIdx(parent.getFuncIdx());
						p.setFuncTp(parent.getFuncTp());
						menuMap.put(func.getParentCd(), p);
					}
					// 构造菜单信息，并加入到模块的子菜单列表中
					MenuNode m = new MenuNode();
					m.setFuncCd(funcCd);
					m.setFuncNm(func.getFuncNm());
					m.setFuncIdx(func.getFuncIdx());
					m.setFuncTp(func.getFuncTp());
					m.setFuncHref(func.getFuncHref());
					p.addSubMenu(m);
				}
			}
			List<MenuNode> lst = new ArrayList<MenuNode>();
			for (MenuNode m : menuMap.values()) {
				m.orderSubMenu();
				lst.add(m);
			}
			Collections.sort(lst, new Comparator<MenuNode>() {
				@Override
				public int compare(MenuNode m1, MenuNode m2) {
					return m1.getFuncIdx() - m2.getFuncIdx();
				}
			});
			this.menuLst = lst;
		}
	}
	public List<MenuNode> getMenuLst() {
		return menuLst;
	}
	public boolean getIsIssHead() {
		return isIssHead;
	}
	public void setIsIssHead(boolean isIssHead) {
		this.isIssHead = isIssHead;
	}
}
