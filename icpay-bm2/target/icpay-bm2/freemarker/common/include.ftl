<#assign ctx = request.contextPath>
<#assign timeNow = .now>
<#assign strNow = timeNow?string["yyyyMMddHHmmss"]>
<#assign strNowMm = timeNow?string["yyyyMMddHHmm"]>

<link rel="shortcut icon" href="${ctx}/resources/images/favicon.ico" type="image/x-icon" />
<link rel="icon" href="${ctx}/resources/images/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common.css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/jquery-easy-ui/themes/gray/easyui.css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/jquery-easy-ui/themes/icon.css"></link>

<script type="text/javascript" src="${ctx}/resources/jquery-easy-ui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/jquery-easy-ui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/jquery-easy-ui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/bm.js?v=${strNowMm}"></script>
<script type="text/javascript" src="${ctx}/resources/js/validate.js"></script>
<script type="text/javascript">
	var _ctx = "${ctx}";
	var logout_url = "${ctx}/logout.do";
</script>
