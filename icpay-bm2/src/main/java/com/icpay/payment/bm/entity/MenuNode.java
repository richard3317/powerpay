package com.icpay.payment.bm.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MenuNode {

	private String funcCd;
	private String funcNm;
	private String funcHref;
	private int funcIdx = 0;
	private String funcTp;
	
	private List<MenuNode> subMenuLst = new ArrayList<MenuNode>();
	
	public void addSubMenu(MenuNode subMenu) {
		if (subMenu != null) {
			subMenuLst.add(subMenu);
		}
	}
	
	/**
	 * 对子菜单按照FuncIdx进行排序
	 */
	public void orderSubMenu() {
		if (subMenuLst != null && subMenuLst.size() > 0) {
			Collections.sort(subMenuLst, new Comparator<MenuNode>() {
				@Override
				public int compare(MenuNode m1, MenuNode m2) {
					return m1.funcIdx - m2.funcIdx;
				}
			});
		}
	}
	
	public String getFuncCd() {
		return funcCd;
	}

	public void setFuncCd(String funcCd) {
		this.funcCd = funcCd;
	}

	public String getFuncHref() {
		return funcHref;
	}

	public void setFuncHref(String funcHref) {
		this.funcHref = funcHref;
	}

	public String getFuncTp() {
		return funcTp;
	}

	public void setFuncTp(String funcTp) {
		this.funcTp = funcTp;
	}

	public List<MenuNode> getSubMenuLst() {
		return subMenuLst;
	}

	public void setSubMenuLst(List<MenuNode> subMenuLst) {
		this.subMenuLst = subMenuLst;
	}

	public int getFuncIdx() {
		return funcIdx;
	}

	public void setFuncIdx(int funcIdx) {
		this.funcIdx = funcIdx;
	}

	public String getFuncNm() {
		return funcNm;
	}

	public void setFuncNm(String funcNm) {
		this.funcNm = funcNm;
	}
}
