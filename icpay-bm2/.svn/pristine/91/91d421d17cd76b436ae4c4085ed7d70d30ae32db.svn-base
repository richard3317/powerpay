<#assign ctx = request.contextPath>
<#assign timeNow = .now>
<#assign strNow = timeNow?string["yyyyMMddHHmmss"]>
<#assign strNowMm = timeNow?string["yyyyMMddHHmm"]>

<#macro head label="" base="" editSuccMsg="">
	<title>修改${label}</title>
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
		<#--  moved to bm.js
		function parseJson(data){
			try {
				return JSON.parse(data);
			}
			catch(err) {
			    return null;
			}
		};
		
		function isEmpty(obj){
			if (obj === null || obj === undefined || obj === '') return true;
			if ((''+obj).replace(/(^s*)|(s*$)/g, "").length ==0) return true;
			return false; 
		}
		
		function checkEmpty(obj, msg){
			if (isEmpty(obj)) {
				if (isEmpty(msg)) msg = "错误！";
				alert(msg);
				return false;
			}
			return true;
		}
		-->	
		$(function() {
			var baseUrl = "${ctx}/${base}/";
			$("#saveBtn").click(function() {
				if (confirm("确认保存?")) {
					$("#editForm").form("enableValidation").submit();
				}
			});
			$("#editForm").form({
				url: $("#editForm").attr("action"),
				onSubmit: function() {
					return $(this).form("validate");
				},
				success: function(data) {
					$.processAjaxResp(data, function(r) {
						var respMsg = r.respMsg;
						if ("OK"!= respMsg){
							alert(respMsg);
						}
						else
						{
							<#if editSuccMsg?? && editSuccMsg != ''>
								alert("${editSuccMsg}");
							<#else>
								alert("修改${label}成功");
							</#if>
						}
						$.jumpTo(baseUrl + "backToManage.do");
					});
				}
			});
			$("#backBtn").click(function() {
				$.jumpTo(baseUrl + "backToManage.do");
			});
			<#nested>
		});
	</script>
</#macro>

<#macro editDiv label editUrl="">
<div id="editDiv" class="easyui-panel" title="修改${label}" style="padding: 10px;">
	<form id="editForm" method="post" action="${ctx}${editUrl}">
		<#nested>
	</form>
	
	<div style="margin: 10px; padding-left: 150px;">
		<a id="saveBtn" href="javascript:void(0)" class="easyui-linkbutton">保存</a>
		<a id="backBtn" href="javascript:void(0)" class="easyui-linkbutton">返回</a> 
	</div>
</div>
</#macro>

<#macro inptInfo>
	<span style="color: green; font-style: italic; font-weight: bold;">
	<#nested>
	</span>
</#macro>