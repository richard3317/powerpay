package com.icpay.payment.bm.constants;

public class BMConstants {

	// 配置文件存放路径
	public static final String CONFIG_PATH_PARAM = "BMCONFIG.PATH";
	public static String CONFIG_PATH = null;
	
	// 超级管理员配置
	public static final String CONFIG_KEY_SUPER_ADMIN = "super_user_id";
	public static final int SUPER_ADMIN_ROLE_ID = -1;
	public static final String SUPER_ADMIN_ROLE_NM = "超级管理员";
	
	public static final String ADMIN_USER = "admin";  //商户默认管理员
	
	// 终端信息导入文件路径
	public static final String CONFIG_KEY_TERMINAL_IMPORT_FILE_PATH = "terminal_import_file_path";
	public static final String CONFIG_KEY_TERMINAL_IMPORT_SAMPLE_FILE_NM = "terminal_import_sample_file_nm";
	public static final String CONFIG_KEY_TERMINAL_EXPORT_FILE_PATH = "terminal_export_file_path";
	public static final String CONFIG_KEY_TERMINAL_EXPORT_MAX_SIZE = "terminal_export_max_size";
	public static final String TERMINAL_IMPORT_FILE_PREFIX = "term_import_";
	
	public static final String CONFIG_KEY_EXPORT_FILE_PATH = "export_file_path";
	public static final String CONFIG_KEY_TRANS_EXPORT_FILENM = "transLog_export_file_nm";
	public static final String CONFIG_KEY_WITHDRAW_EXPORT_FILENM = "withdrawLog_export_file_nm";
	public static final String CONFIG_KEY_ACCFLOW_EXPORT_FILENM = "accflow_export_file_nm";
	public static final String CONFIG_KEY_TRANS_COMPARE_FILENM = "transLog_compare_file_nm";
	public static final String CONFIG_KEY_PROFIT_BATCH_WITHDRAW_FILENM = "profit_batch_withdraw_file_nm";
	
	public static final String CONFIG_KEY_AGENT_PROFIT_CHNL_FILENM = "agent_profit_chnl_file_nm";
	public static final String CONFIG_KEY_AGENT_PROFIT_RES_FILENM = "agent_profit_res_file_nm";
	public static final String CONFIG_KEY_SYS_SETTLE_PROFIT_RES_FILENM = "sys_settle_profit_res_file_nm";
	
	public static final String CONFIG_KEY_TRANS_PROFIT_EXPORT_FILENM = "trans_profit_export_file_nm";
	public static final String CONFIG_KEY_TRANS_PROFIT_WEEK_FILENM = "trans_profit_week_file_nm";
	public static final String CONFIG_KEY_TRANS_HUANBI_FILENM = "trans_huanbi_file_nm";
	public static final String CONFIG_KEY_TRANS_PROFIT_FILE_PATH = "trans_profit_file_path";
	public static final String CONFIG_KEY_HUANBI_REPORT_FILE_NM = "huanbi_report_file_nm";
	public static final String CONFIG_KEY_MANAGER_REPORT_FILE_NM = "manager_report_file_nm";
	public static final String CONFIG_KEY_EMPLOYEE_REPORT_FILE_NM = "employee_report_file_nm";
	public static final String CONFIG_KEY_CHNLTRANS_REPORT_FILE_NM = "chnltrans_report_file_nm";
	public static final String CONFIG_KEY_PAY_NAME = "pay_name";
	
	public static final String CONFIG_KEY_PRE_USCAN_EXPORT_FILENM = "pre_uscan_export_file_nm";//预充值云闪付报表
	public static final String CONFIG_KEY_ROUT_EXPORT_FILENM = "rout_export_file_nm";
	public static final String CONFIG_KEY_ROUTDAIFU_EXPORT_FILENM = "routdaifu_export_file_nm";
	public static final String COMPLAINT_MANAGE = "complaint_manage";
	
	// 商户信息导入文件相关常量
	public static final String CONFIG_KEY_MCHNT_IMPORT_FILE_PATH = "mchnt_import_file_path";
	public static final String MCHNT_IMPORT_FILE_PREFIX = "mer_import_";
	public static final String CONFIG_KEY_MCHNT_IMPORT_SAMPLE_FILE_NM = "mchnt_import_sample_file_nm";

	// 配置文件名称常量
	public static final String BM_CONFIG_FILE_NM = "BMConfig.properties";
	public static final String COLUMN_CONFIG_FILE_NM = "column.conf";
	public static final String DETAIL_CONFIG_FILE_NM = "detail.conf";

	// 查询参数MAP相关常量
	public static final String QRY_PARAM_MAP_KEY = "_QRY_PARAM_MAP";
	public static final String QRY_PARAM_KEY_PREFFIX = "_QRY_";
	public static final int QRY_PARAM_KEY_PREFFIX_LENGTH = QRY_PARAM_KEY_PREFFIX.length();

	// Session中存放信息的KEY
	public static final String SESSION_KEY_USR_INFO = "SESSION_USR_INFO"; // Session用户信息
	public static final String SESSION_KEY_CURRENT_FUNC_HREF = "SESSION_CURRENT_FUNC_HREF"; // 当前访问的URL
	
	public static final String SESSION_KEY_TERM_IMPORT_RSLT_FILENM = "SESSION_KEY_TERM_IMPORT_RSLT_FILENM";
	public static final String SESSION_KEY_TERM_EXPORT_RSLT_FILENM = "SESSION_KEY_TERM_EXPORT_RSLT_FILENM";
	
	public static final String SESSION_KEY_MCHNT_IMPORT_RSLT_FILENM = "SESSION_KEY_MCHNT_IMPORT_RSLT_FILENM";
	
	// Session超时页面
	public static final String PAGE_EXPIRED = "/expired.do";
	// 操作失败
	public static final String PAGE_OPFAIL = "/opfail.do";
	
	public static final String ENTITY_RESULT = "entity";
	public static final String PAGER_RESULT = "pagerResult";
	public static final String LIST_RESULT = "listResult";
	public static final String DETAIL_CONF_LST = "detailConfLst";
	public static final String VIEW_IMAGE_LIST = "viewImageList";

	public static final String REQ_PARAM_NM_PAGENUM = "pageNum";
	public static final String REQ_PARAM_NM_PAGESIZE = "pageSize";
	public static final int DEFAULT_PAGE_SIZE = 15;
	
	public static final String PAGE_CONF_DATADIC = "DataDic";
	public static final String PAGE_CONF_BMOPERLOG = "BmOperLog";
	public static final String PAGE_CONF_BMUSERINFO = "BmUsrInfo";
	public static final String PAGE_CONF_BMROLEINFO = "BmRoleInfo";
	public static final String PAGE_CONF_BMFUNCINFO = "BmFuncInfo";
	public static final String PAGE_CONF_EVENT_LOG = "EventLog";
	public static final String PAGE_CONF_TERMINAL = "TermInfo";
	public static final String PAGE_CONF_TERMSIGNINFO = "TermSignInfo";
	public static final String PAGE_CONF_MCHNTINFO = "MchntInfo";
	public static final String PAGE_CONF_AGENTINFO = "AgentInfo";
	public static final String PAGE_CONF_AGENT_ACC_INFO = "AgentAccountInfo";
	public static final String PAGE_CONF_AGENTPROFITINFO = "AgentProfitInfo";
	public static final String PAGE_CONF_AGENTPROFITPOLICY = "AgentProfitPolicy";
	public static final String PAGE_CONF_AGENTPROFITQUERY = "AgentProfitQuery";
	public static final String PAGE_CONF_MERSETTLEPOLICY = "MerSettlePolicy";
	public static final String PAGE_CONF_MERALGORITHM = "MerAlgorithm";
	public static final String PAGE_CONF_MERACCOUNTINFO = "MerAccountInfo";
	public static final String PAGE_CONF_MERACCOUNTFLOW = "MerAccountFlow";
	public static final String PAGE_CONF_TRANSTYPEGROUP = "TransTypeGroup";
	public static final String PAGE_CONF_CHNLMCHNTMAPPINGINFO = "ChnlMchntMappingInfo";
	public static final String PAGE_CONF_CHNLINFO = "ChnlInfo";
	public static final String PAGE_CONF_CASH_POOL_INFO = "CashPoolInfo";
	public static final String PAGE_CONF_CHNL_MCHNT_DAILY = "ChnlMchntDaily";
	public static final String PAGE_CONF_MER_CASH_POOL_INFO = "MerCashPoolInfo";
	public static final String PAGE_CONF_CHNL_CASH_POOL_INFO = "ChnlCashPoolInfo";
	public static final String PAGE_CONF_ORGAN_MCHNT_INFO = "OrganMchntInfo";
	public static final String PAGE_CONF_VIRTUALTERMINFO = "VirtualTermInfo";
	public static final String PAGE_CONF_ROUTINFO = "RoutInfo";
	public static final String PAGE_CONF_MCHNTTERMINFO = "MchntTermInfo";
	public static final String PAGE_CONF_BUSCHECKTASK = "BusCheckTask";
	public static final String PAGE_CONF_RISKLISTGROUP = "RiskListGroup";
	public static final String PAGE_CONF_RISKLISTITEM = "RiskListItem";
	public static final String PAGE_CONF_RISKLISTMASSITEM = "RiskListMassItem";
	public static final String PAGE_CONF_RISKTHRESHOLD = "RiskThreshold";
	public static final String PAGE_CONF_RISKTRANSLOG = "RiskTransLog";
	public static final String PAGE_CONF_TRANSLOG = "TransLog";
	public static final String PAGE_CONF_VIRTUALTRANSLOG = "VirtualTransLog";
	public static final String PAGE_CONF_BATCHTASKEXECLOG = "BatchTaskExecLog";
	public static final String PAGE_CONF_ACCTCHKRESULT = "AcctChkResult";
	public static final String PAGE_CONF_ACCTCHKRESULTTOTAL = "AcctChkResultTotal";
	public static final String PAGE_CONF_CHNLVIRTUALMERSETTLEINFO = "ChnlVirtualMerSettleInfo";
	public static final String PAGE_CONF_SETTLERESFILE = "SettleResFile";
	public static final String PAGE_CONF_SYS_SETTLE_PROFIT_BANK = "SysSettleProfitBank";
	public static final String PAGE_CONF_SYS_SETTLE_PROFIT_RESULT = "SysSettleProfitResult";
	public static final String PAGE_CONF_MERSETTLETASKLOG = "MerSettleTaskLog";
	public static final String PAGE_CONF_RPTINFO = "RptInfo";
	public static final String PAGE_CONF_SETTLEPROFITRESULT = "SettleProfitResult";
	public static final String PAGE_CONF_AGENTACCCHECKFILE = "AgentAccCheckFile";
	public static final String PAGE_CONF_PROFITRESFILE = "ProfitResFile";
	public static final String PAGE_CONF_AGENTPROFITTASKLOG = "AgentProfitTaskLog";
	
	public static final String PAGE_CONF_AgentProfitSettleResult = "AgentProfitSettleResult";
	public static final String PAGE_CONF_AgentProfitResultByChnl = "AgentProfitResultByChnl";
	public static final String PAGE_CONF_AgentProfitWithdrawLog = "AgentProfitWithdrawLog";
	
	public static final String PAGE_CONF_BANK_CHNL_MAP = "BankChnlMap";
	
	public static final String PAGE_CONF_DAILYPROFITRESULT = "DailyProfitResult";
	public static final String PAGE_CONF_DAILYPROFITRESULTBYCHNL = "DailyProfitResultByChnl";
	public static final String PAGE_CONF_DAILYPROFITRESULTHUANBI = "DailyProfitResultHuanbi";
	public static final String PAGE_CONF_DAILYPROFITRESULTMANAGER = "DailyProfitResultManager";
	public static final String PAGE_CONF_DAILYPROFITRESULTEMPLOYEE = "DailyProfitResultEmployee";
	public static final String PAGE_CONF_DAILYPROFITRESULTCHNLTRANS = "DailyProfitResultChnlTrans";
	
	public static final String PAGE_CONF_PRE_USCAN_RPT_MAP = "PreUScanRptMap"; //预充值云闪付报表
	
	public static final String PAGE_CONF_TRANSRPTINFO="TransRptinfo";
	
	public static final String PAGE_CONF_CONTENT="Content";
	
	public static final String PAGE_CONF_COMPLAINTMANAGE="ComplaintManage";
	
	//----------------------
	public static final String PAGE_CONF_MCHNT_CHNLINFO = "ChnlMchntInfo";
	public static final String PAGE_RISK_LIST_ITEM_MER = "RiskListItemMer";
	public static final String PAGE_TBLWITH_DRAW_LOG = "TblWithdrawLog";
	public static final String PAGE_VIRTUAL_TBLWITH_DRAW_LOG = "VirtualTblWithdrawLog";
	public static final String PAGE_CHNL_MCHNT_ACCOUNT_INFO = "ChnlMchntAccountInfo";
	public static final String PAGE_TXN_ROUTING = "TxnRouting";
	public static final String PAGE_CHNL_INFO_MAPPING = "ChnlInfoMapping";
	public static final String PAGE_TXN_ROUTING_MAPPING = "TxnRoutingMapping";
	public static final String PAGE_OFFPAY_TRANS_LOG = "OffpayTransLog";
	public static final String PAGE_OFF_PAY_BANK = "OffPayBank";
	public static final String PAGE_WITHDRAW_AUDIT = "WithdrawAudit";
	public static final String PAGE_CHNL_QRY_RESP = "ChnlQueryResp";
	
	public static final String PAGE_CONF_CHNLMCHNTACCOUNTFLOW = "ChnlMchntAccountFlow";//渠道账户
	public static final String PAGE_CONF_CHNLMCHNTACCOUNTFLOW2 = "ChnlMchntAccountFlow2";//商户账户(无渠道)
	
	public static final String PAGE_CONF_CHNLMCHNTOBLIGATEDFLOW = "ChnlMchntObligatedFlow";//渠道账户
	public static final String PAGE_CONF_CHNLMCHNTOBLIGATEDFLOW2 = "ChnlMchntObligatedFlow2";//商户账户(无渠道)
	
	
	// 数据字典类型
	public static final String DATA_DIC_TP_FUNC_TP = "FUNC_TP";
	public static final String DATA_DIC_TP_ENTITY_ST = "ENTITY_ST";
	public static final String DATA_DIC_TP_BUSS_TYPE = "BUSS_TYPE";
	public static final String DATA_DIC_TP_INT_TRANS_CD = "INT_TRANS_CD";
	public static final String DATA_DIC_TP_OPERATION_TP = "OPER_TP";
	
	// 校验规则配置KEY
	public static final String VALID_CONF_KEY_TERMINFO_TERMSN = "TermInfo.termSn"; // 终端序列号
	public static final String VALID_CONF_KEY_TERMINFO_TERMMN = "TermInfo.termMn"; // 终端型号
	public static final String VALID_CONF_KEY_TERMINFO_TERMBN = "TermInfo.termBn"; // 终端批次号
	public static final String VALID_CONF_KEY_TERMINFO_EXPORT_KEY = "TermInfo.exportKey"; // 终端批次号
	public static final String VALID_CONF_KEY_BMUSER_PWD = "BmUserInfo.loginPwd"; // 用户登录密码
	public static final String VALID_CONF_KEY_MCHNT_MCHNTCD = "MchntInfo.mchntCd"; // 终端型号
}
