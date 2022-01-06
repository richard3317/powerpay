package com.icpay.payment.mer.constants;

public class MerConstants {

	// 配置文件存放路径
	public static final String CONFIG_PATH_PARAM = "MERCONFIG.PATH";
	public static String CONFIG_PATH = null;

	// 配置文件名称常量
	public static final String PORTAL_CONFIG_FILE_NM = "MerConfig.properties";
	public static final String COLUMN_CONFIG_FILE_NM = "column.conf";
	public static final String DETAIL_CONFIG_FILE_NM = "detail.conf";

	// 查询参数MAP相关常量
	public static final String QRY_PARAM_MAP_KEY = "_QRY_PARAM_MAP";
	public static final String QRY_PARAM_KEY_PREFFIX = "_QRY_";
	public static final int QRY_PARAM_KEY_PREFFIX_LENGTH = QRY_PARAM_KEY_PREFFIX.length();

	// Session中存放信息的KEY
	public static final String SESSION_KEY_MER_INFO = "SESSION_MER_INFO"; // Session用户信息
	
	// Session超时页面
	public static final String PAGE_EXPIRED = "/expired";
	
	// 操作失败
	public static final String PAGE_OPFAIL = "/opfail.do";
	
	public static final String ENTITY_RESULT = "entity";
	public static final String PAGER_RESULT = "pagerResult";
	public static final String LIST_RESULT = "listResult";
	public static final String DETAIL_CONF_LST = "detailConfLst";

	public static final String REQ_PARAM_NM_PAGENUM = "pageNum";
	public static final String REQ_PARAM_NM_PAGESIZE = "pageSize";
	public static final int DEFAULT_PAGE_SIZE = 10;

	public static final String PAGE_CONF_MCHNTINFO = "MchntInfo";
	public static final String PAGE_CONF_TRANSLOG = "TransLog";
	public static final String PAGE_CONF_TRANSLOGEXP = "TransLogExport";
	public static final String PAGE_CONF_WITHDRAWMNG = "WithdrawMng";
	public static final String PAGE_CONF_WITHDRAWMNGEXP = "WithdrawMngExport";
	public static final String PAGE_CONF_PAYMENT = "PaymentMng";
	public static final String PAGE_CONF_MERACCTFLOW = "MerAccountFlow";
	public static final String PAGE_CONF_MERSETTLETASKLOG = "MerSettleTaskLog";
	public static final String PAGE_CONF_BANKLIST = "BankList";
	public static final String PAGE_CONF_BANK_CODE_EXP = "BankCodeExport";
	public static final String PAGE_CONF_TXNLOGVIEW = "TxnLogView";
	public static final String PAGE_CONF_MCHNTUSERINFO = "MchntUserInfo";
	public static final String EVENT_LOG = "EventLog";
	
	// 校验规则配置KEY
	public static final String VALID_CONF_KEY_TERMINFO_TERMSN = "TermInfo.termSn"; // 终端序列号
	public static final String VALID_CONF_KEY_TERMINFO_TERMMN = "TermInfo.termMn"; // 终端型号
	public static final String VALID_CONF_KEY_TERMINFO_TERMBN = "TermInfo.termBn"; // 终端批次号
	public static final String VALID_CONF_KEY_TERMINFO_EXPORT_KEY = "TermInfo.exportKey"; // 终端批次号
	public static final String VALID_CONF_KEY_BMUSER_PWD = "BmUserInfo.loginPwd"; // 用户登录密码
	public static final String VALID_CONF_KEY_MCHNT_MCHNTCD = "MchntInfo.mchntCd"; // 终端型号
	
	public static final String CONFIG_KEY_TRANSLOG_EXPORT_FILE_PATH = "transLog_export_file_path";
	public static final String CONFIG_KEY_TRANS_EXPORT_FILENM = "transLog_export_file_nm";
	public static final String CONFIG_KEY_WITHDRAW_EXPORT_FILENM = "withdrawLog_export_file_nm";
	public static final String CONFIG_KEY_ACCFLOW_EXPORT_FILENM = "accflow_export_file_nm";
	public static final String CONFIG_KEY_BACTH_WITHDRAW_ERROR_FILENM = "batch_withdraw_error_file_nm";
	public static final String CONFIG_KEY_BANK_CODE_FILENM = "bank_code_file_nm";
	
	public static final String CONFIG_KEY_MAX_LOGIN_RETRIES = "maxLoginRetries";
	
	/**
	 * 用戶角色，即将过期，改用 {@link com.icpay.payment.common.enums.MerEnums.MerUserRole}
	 * @deprecated
	 */
	public static class Roles {
		public static final String NONE="";
		public static final String ADMIN="admin";
		public static final String USER="user";
	}
	
	public static class LoginType {
		public static final String ADMIN ="0";
		public static final String USER ="1";
	}
	
	public static class SecretState {
		public static final String INIT ="0";
		public static final String ADMIN_INITED ="1";
		public static final String WD_USER_BIND ="2";
		public static final String USER_BIND ="4";
		
		public static final String OK ="3"; //已设置
	}
	
	public static class LoginState {
		public static final String INIT ="0";
		public static final String PRELOGIN="preLogin";
		public static final String OK ="ok";
		public static final String FAILED ="";
	}
	public static class GoogleState {
		public static final String INIT ="0";
		public static final String PRELOGIN="preLogin";
		public static final String OK ="ok";
		public static final String FAILED ="";
	}
	
	public static class LastLoginState {
		public static final String NOT_LOGGED_IN ="0"; //0=未登入
		public static final String LOGGED_IN = "1"; //1=已登入
	}
}
