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
				
				
				<#--  
				var totalCount = document.getElementById("totalCount").value;
				
				for (i = 0; i < totalCount; i++) {
					var defaultAmt = document.getElementsByName("defaultAmtDesc")[i].value;
					var chnlId = document.getElementsByName("chnlId")[i].value;
					var chnlMchntCd = document.getElementsByName("chnlMchntCd")[i].value;
				
				
			    	if (defaultAmt  === "") {
						alert("请输入金额(元)");
						return false;
			    	}
					if (chnlId  === "") {
						alert("请选择打款渠道");
						return false;
			    	}
			    	if (chnlMchntCd  === "") {
						alert("请选择打款大商编");
						return false;
			    	}
					
				  	
				}
				-->
			
			
			
				var gapAmt = document.getElementById("gapAmt").innerHTML;
				if (gapAmt  === "") {
					alert("请输打款目标金额(元)");
					return false;
				}
				if (parseInt(gapAmt) < 0) {
			    	alert("打款目标金额不得小于0");
			    	return false;
			    } else {
					<#-- 戶名卡號 打款 12345 RBM ,使用 xxx 渠道 ,商編 xxx -->
					var totalCount = document.getElementById("totalCount").value;
					var confirmDesc = "";
				  	for (i = 0; i < totalCount; i++) {
					    var accountName = document.getElementsByName("accountName")[i].value;
					    var accountNo = document.getElementsByName("accountNo")[i].value;
					    var defaultAmtDesc = document.getElementsByName("defaultAmtDesc")[i].value;
					    var chnlId = document.getElementsByName("chnlId")[i].value;
						var chnlMchntCd = document.getElementsByName("chnlMchntCd")[i].value;
					    confirmDesc = confirmDesc + "户名: " + accountName + " ,卡号: " + accountNo + " ,打款: " + defaultAmtDesc + " RMB" + " ,使用: " + chnlId + " 渠道 ,商编: " + chnlMchntCd + "\n\n";
					    
					    
					    if (defaultAmtDesc  === "") {
							alert("请输入金额(元)");
							return false;
				    	}
						if (chnlId  === "") {
							alert("请选择打款渠道");
							return false;
				    	}
				    	if (chnlMchntCd  === "") {
							alert("请选择打款大商编");
							return false;
				    	}
					    
					    
				  	}
					var confirmResult = confirm(confirmDesc);
					if (confirmResult) {
						if (isAdding) {
							return;
						}
						$("#addForm").form("enableValidation").submit();
					} else {
						return false;
					}	
				}		
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
				var fromFlag = $("#fromFlag").val(); 
				$.jumpTo(baseUrl + "backToManage.do?fromFlag=" + fromFlag);
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
	<center>
		<br>
		<div>
			<a id="saveBtn" href="javascript:void(0)" class="easyui-linkbutton">送出</a>
			<a id="backBtn" href="javascript:void(0)" class="easyui-linkbutton">返回</a> 
		</div>
	</center>
</div>
</#macro>

<#macro inptInfo>
	<span style="color: green; font-style: italic; font-weight: bold;">
	<#nested>
	</span>
</#macro>