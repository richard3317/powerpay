<#assign ctx = request.contextPath>
<#assign timeNow = .now>
<#assign strNow = timeNow?string["yyyyMMddHHmmss"]>
<#assign strNowMm = timeNow?string["yyyyMMddHHmm"]>

<#macro head label="" base="" addSuccMsg="">
	<title>新增${label}</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common.css"></link>
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/select2.css"></link>
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/jquery-easy-ui/themes/gray/easyui.css"></link>
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/jquery-easy-ui/themes/icon.css"></link>
	
	<script type="text/javascript" src="${ctx}/resources/jquery-easy-ui/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/jquery-easy-ui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/jquery-easy-ui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/bm.js?v=${strNowMm}"></script>
	<script type="text/javascript" src="${ctx}/resources/js/validate.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/select2.js"></script>
	<script type="text/javascript" src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
	
	<script type="text/javascript">
		var _ctx = "${ctx}";
		var logout_url = "${ctx}/logout.do";
		var isAdding = false;
		<#--  has moved to bm.js
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
				if (isAdding) {
					return;
				}
				$("#addForm").form("enableValidation").submit();
			});
			$("#addForm").form({
				url: $("#addForm").attr("action"),
				onSubmit: function() {
				<#-- 去大商户号空白格  -->
			if("${label}" == "渠道虚拟商户信息"){
			var m_chnlMchntCdInpt = document.getElementById("chnlMchntCdInpt").value+"";
			document.getElementById("chnlMchntCdInpt").value = m_chnlMchntCdInpt.replace(/\s+/g, "");
			}
				
					var r = $(this).form("validate");
					if (r) {
						isAdding = true;
					}
					return r;
				},
				success: function(data) {
					

					$.processAjaxResp(data, function() {
						<#if addSuccMsg?? && addSuccMsg != ''>
							alert("${addSuccMsg}");
						<#else>
						try {
						   	let respData = data;
							var obj = JSON.parse(respData);
							if(obj.respMsg !== undefined){
								alert(obj.respMsg);
							}else{
								alert("新增${label}成功");
							}
						}
						catch (e) {
						   console.log(e.message);// 警報 'Error'
						   alert("新增${label}成功");
						}	
						</#if>
						$.jumpTo(baseUrl + "backToManage.do");
					});
					isAdding = false;
				}
			});
			$("#backBtn").click(function() {
				$.jumpTo(baseUrl + "backToManage.do");
			});
			<#nested>
		});
	</script>
</#macro>

<#macro addDiv label addUrl="">
<div id="addDiv" class="easyui-panel" title="新增${label}" style="padding: 10px;">
	<form id="addForm" method="post" action="${ctx}${addUrl}">
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