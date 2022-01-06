package com.icpay.payment.mer.session;

public enum SessionKeys {

	MCHNT_INFO("MCHNT_INFO", "商户信息"),
	MCHNT_USER_INFO("MCHNT_USER_INFO", "商户用户信息"),
	MCHNT_ACCOUNT_INFO("MCHNT_ACCOUNT_INFO", "商户账户信息"),
	VALIDATION_CODE("VALIDATION_CODE", "图形验证码"),
	GOOGLE_USER_LOGIN_TYPE("GOOGLE_USER_LOGIN_TYPE","谷歌验证登录类型"),
	LOGIN_STATE("LOGIN_STATE","登入狀態"), // ok = 登入成功, 其他=失敗
	Last_GoogleAuth_Time("Last_GoogleAuth_Time","上次谷歌验证时间"), //Long
	SECRET_STATE("SECRET_STATE","安全设置状态"), // 0 - 未初始化，1- 管理员，2 - 其他user 3 - 成功
	ROLE("ROLE","用户角色"),
	CLIENTIP("CLIENT_IP","登入地址"),
	TIME_STAMP("TIME_STAMP","时间戳记"),
	IS_IP_ALLOWED_WITHDRAW("IS_IP_ALLOWED_WITHDRAW","是否允许此IP代付"),
	RANDOM("RANDOM","随机数"),
	LAST_RANDOM("LAST_RANDOM","上次验证随机数"),
	LAST_WITHDRAW_TIME("LAST_WITHDRAW_TIME","上次代付時間"),
	LAST_WITHDRAW_CONTENT("LAST_WITHDRAW_CONTENT","上次代付內容"),
	WITHDRAW_MD5("WITHDRAW_MD5","代付提交MD5"),
	GACODE_FLAG("GACODE_FLAG","谷歌验证码标志"),
//	BATCH_GACODE_FLAG("BATCH_GACODE_FLAG","批量代付谷歌验证码标志"),
	LAST_VALIDATION_CODE("LAST_VALIDATION_CODE", "上次代付的图形验证码"),
	LAST_WITHDRAW_LIST_MD5("LAST_WITHDRAW_LIST_MD5", "上次批量代付文本md5"),
	WITHDRAW_LIST_MD5("WITHDRAW_LIST_MD5", "批量代付文本md5"),

	LAST_SESSION_ATTACH("LAST_SESSION_ATTACH", "上次Session附加值"),
	LAST_LOGIN_STATE("LAST_LOGIN_STATE", "登入状态"),
	LAST_LOGIN_TIME("LAST_LOGIN_TIME", "登入时间"),
	LAST_VISIT_TIME("LAST_VISIT_TIME", "活跃时间"),
	USED_LANG("lan", "使用中的语系"),
	GOOGLE_AUTHENTICATOR_CODE("GOOGLE_AUTHENTICATOR_CODE", "Googl验证码"),
	GOOGLE_AUTHENTICATOR_TIME("GOOGLE_AUTHENTICATOR_TIME", "Googl验证码输入时间"),

	;

	private SessionKeys(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	private String code;
	private String desc;

	public String getCode() {
		return code;
	}
	public String getDesc() {
		return desc;
	}

}
