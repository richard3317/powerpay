package com.icpay.payment.common.enums;

import com.icpay.payment.common.constants.Constant;

public class MerEnums {
	
	/**
	 * 用戶角色：
	 * su(超级用户，僅能設置安全頁面), wd(可代付+充值), py(僅充值)
	 * @author robin
	 */
	public enum MerUserRole {
		/** 超级用户，代码: su */
	    SuperUser ("su","超級用戶"),
		/** 代付用戶，代码: wd */
	    WithdrawUser  ("wd","代付用戶"),
		/** 支付用戶，代码: py */
	    PaymentUser ("py","支付用戶"),
	    ;
	    private String code;
	    private String desc;
	    
	    private MerUserRole(String code, String desc) {
	        this.code = code;
	        this.desc = desc;
	    }
	    
	    public String getCode() {
	        return code;
	    }
	    public String getDesc() {
	        return desc;
	    }
	}
	
	/**
	 * 用戶状态
	 * 9=已锁定,2=已禁用,0=初始状况(需重置密码),1=正常使用
	 * @author robin
	 */
	public enum MerUserState {
		/** 已禁用 */
	    Disabled ("2","已禁用"),
		/** 已锁定(风控中) */
	    Locked ("9","已锁定"),
		/** 已锁定(风控中) */
	    Normal ("1","正常使用"),
		/** 已锁定(风控中) */
	    Init ("0","初始状况"),
	    ;
	    private String code;
	    private String desc;
	    
	    private MerUserState(String code, String desc) {
	        this.code = code;
	        this.desc = desc;
	    }
	    
	    public String getCode() {
	        return code;
	    }
	    public String getDesc() {
	        return desc;
	    }
	}

	public enum TimeQryMethod {
	    ByUpdateTs  ("UpdTS","以 更新时间 查询"),
//	    ByCrtateTs  ("CrtTS","以 创建时间 查询"),
	    ByOrderTime ("OrdDT","以 订单时间 查询"),
	    ;
	    private String code;
	    private String desc;
	    
	    private TimeQryMethod(String code, String desc) {
	        this.code = code;
	        this.desc = desc;
	    }
	    
	    public String getCode() {
	        return code;
	    }
	    public String getDesc() {
	        return desc;
	    }
	}
	
	
	public enum AccOperTpCatalog {
	    //TRANS("00;31;32;33","交易"),
	    TRANS_INCOME("00","交易-消费"),
	    TRANS_WITHDRAW("31;32;33","交易-代付"),
	    //TRANSADJUST("11;12;13;16;17;18;19","交易调整"),
	    TRANSADJUST_INCOME("11;12;13","交易调整-消费"),
	    TRANSADJUST_WITHDRAW("16;17;18;19","交易调整-代付"),
	    SATTLE_D0(Constant.OPERTYPE._51,"馀额结转"),
	    SATTLE_T1(Constant.OPERTYPE._52,"T1结转"),
	    ADJUST("40;41;42;43;45;46","其他调帐"),
	    TRANSFER_REQUEST_OUT("61","B0转出请求"),
	    TRANSFER_REQUEST_IN("63","转入B0请求"),
	    TRANSFER_CONFIRM_OUT("65","B0转出确认"),
	    TRANSFER_CONFIRM_IN("67","转入B0确认"),
	    ;
	    private String code;
	    private String desc;
	    
	    private AccOperTpCatalog(String code, String desc) {
	        this.code = code;
	        this.desc = desc;
	    }
	    
	    public String getCode() {
	        return code;
	    }
	    public String getDesc() {
	        return desc;
	    }
	}
	
	
	
	/**
	 * <pre>
	 * 虚拟帐户数据操作类型。
	 * 请参考: {@link com.icpay.payment.common.constants.Constant.OPERTYPE Constant.OPERTYPE}
	 * 
	 * 相关服务类：
	 *   {@link com.icpay.payment.service.MerAccServiceImpl MerAccServiceImpl}
	 *   及{@link com.icpay.payment.service.MerAccService MerAccService}
	 *   及{@link com.icpay.payment.bm.web.controller.business.MchntAccountController#accountAdjustSubmit(Model, String, String, String, String) MchntAccountController.accountAdjustSubmit}
	 * 
	 * 说明：
	 *   假设:
	 *   [虚拟帐户]
	 *   B0 = 可代付馀额
	 *   B1 = 不可代付馀额(T1馀额) 
	 *   F0 = 预扣款
	 *   [操作金额]
     *   a - 交易额
     *   fa - 交易手续费
     *   z - 代付垫资比例,如: 0.95	 
     * </pre>
	 */
	public enum AccOperTp {
		////////////////////////////////////////////////
		// 假设:
		// [虚拟帐户]
		// B0 = 可代付馀额
		// B1 = 不可代付馀额(T1馀额) 
		// F0 = 预扣款
		// [操作金额]
		// a - 交易额
		// fa - 交易手续费
		// z - 代付垫资比例,如: 0.95
		
		//// 消费
		// B0 = B0 +  (a-fa)*z ;
		// B1 = B1 +  (a-fa)*(1-z) ;
		/**
		 * 消费(收入) B0+
		 */
		_00(Constant.OPERTYPE._00, "收入-消费"),
		
		/** 差错调整-冻结 B0->F0*/
		_40(Constant.OPERTYPE._40, "调整-预扣款"),
		/** 差错调整-扣除冻结金额 F0- */
		_41(Constant.OPERTYPE._41, "调整-扣除预扣款"),
		/** 差错调整-解冻 F0->B0 */
		_42(Constant.OPERTYPE._42, "调整-取消预扣"),
		/** 差错调整-增加冻结金额 F0+ */
		_43(Constant.OPERTYPE._43, "调整-增加预扣款"),
		/** 差错调整-增加冻结金额 B1->F0 */
		_45(Constant.OPERTYPE._45, "调整-转至T1馀额"),
		/** 差错调整-增加冻结金额 F0->B1 */
		_46(Constant.OPERTYPE._46, "调整-取消T1馀额"),
		
		// 每日00:00(parameter) 将当日代付馀额B0，结转到B1
		// B1 = B1 + B0 ;
		// B0 = 0 ;
		/**馀额结转 B0->B1 */
		_51(Constant.OPERTYPE._51,"馀额结转"),
		
		// 每日16:00(parameter) 将前日馀额b1(每日0点结算B1帐户馀额==>b1)，结转到B0
		// B0 = B0 + b1 ;
		// B1 = B1 - b1 ;
		/**T1结转 B1->B0 */
		_52(Constant.OPERTYPE._52,"T1结转"),
		
		
		//// 代付
		// B0 = B0 -a -fa
		// F0 = F0 +a +fa
		/**代付请求-冻结; B0->F0 */
		_31(Constant.OPERTYPE._31,"代付请求-预扣款"), //代付请求-冻结
		
		// F0 = F0 -a -fa
		/**代付请求成功-解冻扣除; F0-*/
		_32(Constant.OPERTYPE._32,"代付成功-扣款"), //代付成功-解冻扣除
		
		// F0 = F0 -a -fa
		// B0 = B0 +a +fa
		/**代付请求失败-解冻归还; F0->B0 */
		_33(Constant.OPERTYPE._33,"代付失败-取消预扣"), //代付失败-解冻归还
		
		/**
		 * 交易记录调帐，包含:<br/> 
		 * 消费 处理中 -> 失败 <br/>
		 * <br/>
		 * [运算]<br/>
		 * (注：此过程对帐务无影响不需调帐)<br/>
		 */
		_11(Constant.OPERTYPE._11,"消费交易：处理中->失败"),
		
		/**
		 * 交易记录调帐，包含:<br/> 
		 * 消费 处理中 -> 成功 <br/>
		 * (注：消费 处理中 -> 失败，此过程对帐务无影响不需调帐)<br/>
		 * [运算]<br/>
		 * 消费(收入) B0+ <br/>
		 * B0 = B0 +  (a-fa)*z ; <br/>
		 * B1 = B1 +  (a-fa)*(1-z) ; <br/>
		 */
		_12(Constant.OPERTYPE._12,"消费交易：处理中->成功"),
		
		/**
		 * 交易记录调帐，包含:<br/> 
		 * 消费 失败 -> 成功<br/>
		 * (注：消费 处理中 -> 失败，此过程对帐务无影响不需调帐)<br/>
		 * [运算]<br/>
		 * 消费(收入) B0+ <br/>
		 * B0 = B0 +  (a-fa)*z ; <br/>
		 * B1 = B1 +  (a-fa)*(1-z) ; <br/>
		 */
		_13(Constant.OPERTYPE._13,"消费交易：失败->成功"),
		
		
		/**
		 * 交易记录调帐，包含:<br/> 
		 * 代付 处理中 -> 成功 <br/>
		 * [运算]<br/>
		 * 代付请求成功-扣除-解冻; F0- <br/>
		 */
		_16(Constant.OPERTYPE._16,"代付交易：处理中->成功"),
		
		/**
		 * 交易记录调帐，包含:<br/> 
		 * 代付 处理中 -> 失败 <br/>
		 * [运算]<br/>
		 * 代付请求失败-解冻 F0->B0 <br/>
		 */
		_17(Constant.OPERTYPE._17,"代付交易：处理中->失败"),

		/**
		 * 交易记录调帐，包含:<br/> 
		 * 代付 失败 -> 成功 <br/>
		 * [运算]<br/>
		 * 代付请求-冻结; B0->F0 <br/>
		 */
		_18(Constant.OPERTYPE._18,"代付交易：失败->成功"),
		
		/**
		 * 交易记录调帐，包含:<br/> 
		 * 代付 失败 -> 成功 <br/>
		 * [运算]<br/>
		 * 代付请求成功-扣除-解冻; F0- <br/>
		 */
		_19(Constant.OPERTYPE._19,"代付交易：失败->成功(扣除解冻)"),
		
		_20(Constant.OPERTYPE._20,"代付交易：成功->失败"),
		
		/** 服务请求，冻结服务费 */
		_71(Constant.OPERTYPE._71,"交易请求，预扣手续费"),	
		/** 服务成功，扣除服务费 */
		_72(Constant.OPERTYPE._72,"交易成功，扣除手续费"),	
		/** 服务失败，解冻服务费 */
		_73(Constant.OPERTYPE._73,"交易失败，退还手续费"),	
		
		/** 交易：处理中->成功 */
		_74(Constant.OPERTYPE._74,"交易：处理中->成功"),	
		/** 交易：处理中->失败 */
		_75(Constant.OPERTYPE._75,"交易：处理中->失败"),	
		/** 交易：失败->成功(预扣手续费) */
		_76(Constant.OPERTYPE._76,"交易：失败->成功"),	
		/** 交易：失败->成功(扣除手续费) */
		_77(Constant.OPERTYPE._77,"交易：失败->成功(扣除手续费)"),			
		
		/**
		 * 重置日累计额
		 */
		_90(Constant.OPERTYPE._90,"重置日累计额"),
		
		/** B0转出请求 */
		_61(Constant.OPERTYPE._61,"B0转出请求"),	
		/** 转入B0请求 */
		_63(Constant.OPERTYPE._63,"转入B0请求"),	
		/** B0转出确认 */
		_65(Constant.OPERTYPE._65,"B0转出确认"),
		/** 转入B0确认 */
		_67(Constant.OPERTYPE._67,"转入B0确认"),
		;
		
		private String code;
		private String desc;
		
		private AccOperTp(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}
		
		public String getCode() {
			return code;
		}
		public String getDesc() {
			return desc;
		}
	}
}
