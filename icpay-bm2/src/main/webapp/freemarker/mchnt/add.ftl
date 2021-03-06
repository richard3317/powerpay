<#include "/common/add_macro.ftl" />
<#assign label = "商户信息">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="mchnt" addSuccMsg="商户新增任务已提交，请等待审核">
			$("#mchntCnNmInpt").validatebox({
				required: true,
				novalidate: true,
				validType: 'cnMaxLength[40]'
			});
			$("#mchntEnNmInpt").validatebox({
				required: true,
				novalidate: true,
				validType: ['enNm', 'maxLength[100]']
			});
			$("#agentCdSel").validatebox({
				required: true,
				novalidate: true
			});
			$("#transTypeGroupIdSel").validatebox({
				required: true,
				novalidate: true
			});
			$("#mchntCnAbbrInpt").validatebox({
				required: true,
				novalidate: true,
				validType: 'cnMaxLength[40]'
			});
			$("#mchntEnAbbrInpt").validatebox({
				required: true,
				novalidate: true,
				validType: ['enNm', 'maxLength[40]']
			});
			$("#endDt").validatebox({
				required: true,
				novalidate: true
			});
			$("#contactMailInpt").validatebox({
				required: true,
				novalidate: true
			});
			$("#currCdSel").validatebox({
				required: true,
				novalidate: true
			});
			$("#settlePeriodSel").validatebox({
				required: true,
				novalidate: true
			});
			$("#preTransferT1PercentInpt").validatebox({
				required: true,
				novalidate: true
			});
			
			<#--清算信息设置的币别-->
			$("#currCdSel").change(function() {
			var currCd = $(this).val();
				if (currCd == 'all') {
					$("#settlePeriodSel").validatebox({
						required: true,
						novalidate: true
					});
				}
				if (currCd != 'all') {
					$("#settlePeriodSel").validatebox({
						required: false,
						novalidate: false
					});
				}
			});
			
			<#--T1反还。否：返还时间及比例应允许为空-->
			$("#balanceTransferT1Sel").change(function() {
				if ($("#balanceTransferT1Sel")[0].selectedIndex == 1) {
					$("#preTransferT1PercentInpt").validatebox({
						required: false,
						novalidate: false
					});
					$("#preTransferT1PercentInpt").val("").attr('disabled', true);
					$("#preTransferTimeT1Inpt").val("").attr('disabled', true);
				}
				if ($("#balanceTransferT1Sel")[0].selectedIndex == 0) {
					$("#preTransferT1PercentInpt").validatebox({
						required: true,
						novalidate: true
					});
					$("#preTransferT1PercentInpt").val("").attr('disabled', false);
					$("#preTransferTimeT1Inpt").val("").attr('disabled', false);
				}
			});
			
			<#--商户状态默认为有效-->
			$("#mchntSt")[0].selectedIndex = 2;
			
			var emptyTradeSelOpt = $('<option value="">--请选择--</option>');
			$("#agentCdSel").change(function() {
				var agentCd = $(this).val();
				$("#tradeTypeSel").html("").append(emptyTradeSelOpt);
				if (agentCd != '') {
					var url =  "${ctx}/loadAgentTradeType.do?agentCd=" + agentCd;
					$.get(url, function(data) {
						$.processAjaxResp(data, function(r) {
							var lst = r.respData.tradeTypeLst;
							for (var i = 0; i < lst.length; i ++) {
								var tp = lst[i].tp;
								var v = lst[i].v;
								$("#tradeTypeSel").append('<option value="' + tp + '">' + v + '</option>');
							}
						});
					});
				}
			});
			
			var emptyOpt = $('<option value="">--请选择--</option>');
			$("#provSel").change(function() {
				var provCd = $(this).val();
				if (provCd == '') {
					$("#areaCdSel").html("").append(emptyOpt);
				} else {
					var url =  "${ctx}/loadCity.do?provCd=" + provCd;
					$.get(url, function(data) {
						$.processAjaxResp(data, function(r) {
							var lst = r.respData.cityLst;
							$("#areaCdSel").html("").append(emptyOpt);
							for (var i = 0; i < lst.length; i ++) {
								var regionCd = lst[i].regionCd;
								var regionNm = lst[i].regionCnNm;
								$("#areaCdSel").append('<option value="' + regionCd + '">' + regionCd + '-' + regionNm + '</option>');
							}
						});
					});
				}
			});
			
			<#-- 商户有效日期默認10年 -->
			$("#endDt").val(getToDay());
			function getToDay(){
			   var now = new Date();
			   var nowYear = now.getFullYear() + 10;
			   var nowMonth = now.getMonth();
			   var nowDate = now.getDate();
			   newdate = new Date(nowYear,nowMonth,nowDate);
			   nowMonth = doHandleMonth(nowMonth + 1);
			   nowDate = doHandleMonth(nowDate);
			   return nowYear+""+nowMonth+""+nowDate;
			}
			
			function doHandleMonth(month){
   				if(month.toString().length == 1){
    				month = "0" + month;
   				}
   				return month;
  			}
  			
  			<#-- 添加域名 -->
  			var maxFields = 20;<#-- 最大添加域名數 -->
    		var wrapper = $("#inputDomainName");
    		var addButton = $("#addButton");
    		var domainName = $("#domainName");
    		var x = 1;
		    $(addButton).click(function(e){
		        e.preventDefault();
		        if (x < maxFields && domainName.val() != "") {
	            	$(wrapper).append('<div><input type="text" class="easyui-validatebox" id="newDomainName'+x+'"/></div>');
		            $("#newDomainName"+x).val(domainName.val());
	            	domainName.val("");
	            	x++;
		        }
		    });
		    
		    <#-- 添加清算信息(单一币别) -->
		    var maxFields = 10;<#-- 最大添加清算信息數 -->
    		var wrapperMsp = $("#inputMerSettlePolicy");
    		var addSettleInfoButton = $("#addSettleInfoButton");
            var currCdSel = $("#currCdSel");
    		var settlePeriodSel = $("#settlePeriodSel");
            var balanceTransferSel = $("#balanceTransferSel");
            var transferTimeInpt = $("#transferTimeInpt");
            var balanceTransferT1Sel = $("#balanceTransferT1Sel");
            var preTransferTimeT1Inpt = $("#preTransferTimeT1Inpt");
            var preTransferT1PercentInpt = $("#preTransferT1PercentInpt");
            var transferTimeT1Inpt = $("#transferTimeT1Inpt");     
            
		    var z = 1;
		    $(addSettleInfoButton).click(function(e){
		    
		    	if (currCdSel.val() == "" || currCdSel.val() == "all") {
		    		alert("请选择币别 或 币别不能选全部");
		    		return;
		    	} 
		    	
		    	if (settlePeriodSel.val() == "") {
		    		alert("请选择清算周期");
		    		return;
		    	} 
		    	
		    	e.preventDefault();
		    	
		    	var settlePeriodSelected = $("#settlePeriodSel").find(":selected");
		    	var balanceTransferSelected = $("#balanceTransferSel").find(":selected");
		    	var balanceTransferT1Selected = $("#balanceTransferT1Sel").find(":selected");
		    	
	    		var currCdSelSelected = $("#currCdSel").find(":selected");
	    		for (var k = 1; k < z; k++) {
	    			var oldCd = $("#currCdSel"+k).val();
	    			if (currCdSelSelected.val() == oldCd) {
	    				alert("该币别的清算周期已存在");
	    				return false;
	    			}
	    		}
	    		
		        if (z < maxFields) {

	            	$(wrapperMsp).append('<div id="'+z+'"><span id="newMerSettlePolicy'+z+'"></span>&nbsp<button class="removeField" style="color:red;">&nbspX&nbsp</button></div>');
		            $("#newMerSettlePolicy"+z).text("币别："+ currCdSelSelected.text() + "，清算周期：" + settlePeriodSelected.text() + "，D0结转：" + balanceTransferSelected.text() + "，D0结转时间：" + transferTimeInpt.val() + "，T1反还：" +  balanceTransferT1Selected.text() + "，前置T1返还时间：" + preTransferTimeT1Inpt.val() + "，前置T1返还比例：" + preTransferT1PercentInpt.val() + "，后置T1返还时间：" + transferTimeT1Inpt.val());
	            	
                    $(wrapperMsp).append('<input type="hidden" id="currCdSel'+z+'">');
                    $("#currCdSel"+z).val(currCdSel.val());
                    
	            	$(wrapperMsp).append('<input type="hidden" id="settlePeriodSel'+z+'">');
	            	$("#settlePeriodSel"+z).val(settlePeriodSel.val());
	            	
	            	$(wrapperMsp).append('<input type="hidden" id="balanceTransferSel'+z+'">');
	            	$("#balanceTransferSel"+z).val(balanceTransferSel.val());
	            	
	            	$(wrapperMsp).append('<input type="hidden" id="transferTimeInpt'+z+'">');
	            	$("#transferTimeInpt"+z).val(transferTimeInpt.val());
	            	
	            	$(wrapperMsp).append('<input type="hidden" id="balanceTransferT1Sel'+z+'">');
	            	$("#balanceTransferT1Sel"+z).val(balanceTransferT1Sel.val());
    				
    				$(wrapperMsp).append('<input type="hidden" id="preTransferTimeT1Inpt'+z+'">');
	            	$("#preTransferTimeT1Inpt"+z).val(preTransferTimeT1Inpt.val());
                    
                    $(wrapperMsp).append('<input type="hidden" id="preTransferT1PercentInpt'+z+'">');
	            	$("#preTransferT1PercentInpt"+z).val(preTransferT1PercentInpt.val());
	            	
                    $(wrapperMsp).append('<input type="hidden" id="transferTimeT1Inpt'+z+'">');
	            	$("#transferTimeT1Inpt"+z).val(transferTimeT1Inpt.val());
	            	
	            	
	            	$("#currCdSel").val("");
	            	$("#settlePeriodSel").val("");
	            	
	            	$("#currCdSel").validatebox({
						required: false,
						novalidate: false
					});
					
	            	$("#settlePeriodSel").validatebox({
						required: false,
						novalidate: false
					});
					
	            	$("#balanceTransferSel").val("0");
	            	$("#transferTimeInpt").val("");
	            	$("#balanceTransferT1Sel").val("0");
	            	<#--  $("#preTransferTimeT1Inpt").val("");
	            	$("#preTransferT1PercentInpt").val("");-->
					$("#transferTimeT1Inpt").val("");
					
					$("#preTransferT1PercentInpt").validatebox({
						required: false,
						novalidate: false
					});
					$("#preTransferT1PercentInpt").val("").attr('disabled', true);
					$("#preTransferTimeT1Inpt").val("").attr('disabled', true);
	            	
	            	z++;
		        }

		    }); 
		    $(wrapperMsp).on("click",".removeField", function(e){
        		if (confirm("确认要删除记录吗?")) {
        			e.preventDefault(); 
        			var index = $(this).parent('div').attr('id');
        			$(this).parent('div').remove(); 
		            $("#currCdSel"+index).remove(); 
		            $("#settlePeriodSel"+index).remove(); 
		            $("#balanceTransferSel"+index).remove(); 
		            $("#transferTimeInpt"+index).remove(); 
		            $("#balanceTransferT1Sel"+index).remove(); 
		            $("#preTransferTimeT1Inpt"+index).remove(); 
		            $("#preTransferT1PercentInpt"+index).remove(); 
		            $("#transferTimeT1Inpt"+index).remove(); 
        		}else {
        			return false;
        		}
    		}); 
		    		    		    		    		    
		    <#-- 添加支付方式 -->
		    var maxFields = 20;<#-- 最大添加支付方式數 -->
    		var wrapperSub = $("#inputMerSettlePolicySub");
    		var addPolicyButton = $("#addPolicyButton");
    		var intTransCdSel = $("#intTransCdSel");
            var transCurrCd=$("#transCurrCd");
    		var fixRateInpt = $("#fixRateInpt");
    		var minFeeInpt = $("#minFeeInpt");
    		var maxFeeInpt = $("#maxFeeInpt");
    		var txDayMaxInpt = $("#txDayMaxInpt");
    		var txCardDayMaxIput = $("#txCardDayMaxIput");
    		var txAmtMinInpt = $("#txAmtMinInpt");
    		var txAmtMaxInpt = $("#txAmtMaxInpt");
    		var txTimeLimitInpt = $("#txTimeLimitInpt");
    		var txT0PercentIput = $("#txT0PercentIput");
    		var y = 1;
		    $(addPolicyButton).click(function(e){
		    	
		    	if (intTransCdSel.val() == "") {
		    		alert("请选择交易类型");
		    		return;
		    	} 
                if (transCurrCd.val() == "") {
                    alert("请选择币别");
                    return;
                } 
		    	
	    		e.preventDefault();
	    		var intTransCdSelSelected = $("#intTransCdSel").find(":selected");
	    		var transCurrCdSelSelected = transCurrCd.find(":selected");
	    		for (var k = 1; k < y; k++) {
	    			var oldCd = $("#intTransCdSel"+k).val();
	    			if (intTransCdSelSelected.val() == oldCd) {
	    				alert("该交易类型清算策略已存在");
	    				return false;
	    			}
	    		}
		        if (y < maxFields) {
		        	<#--封顶、单日限额、单日单卡限额默认为999999999999.99-->
		        	if (maxFeeInpt.val() == "") {
		        		maxFeeInpt.val("999999999999.99");
		        	}
		        	if (txDayMaxInpt.val() == "") {
		        		txDayMaxInpt.val("999999999999.99");
		        	}
		        	if (txCardDayMaxIput.val() == "") {
		        		txCardDayMaxIput.val("999999999999.99");
		        	}
	            	$(wrapperSub).append('<div id="'+y+'"><span id="newMerSettlePolicySub'+y+'"></span>&nbsp<button class="removeField" style="color:red;">&nbspX&nbsp</button></div>');
		            $("#newMerSettlePolicySub"+y).text("币别："+ transCurrCdSelSelected.text() + "，交易类型：" + intTransCdSelSelected.text() + "，费率：" + fixRateInpt.val() + "%，保底：" + minFeeInpt.val() + "元，封顶：" +  maxFeeInpt.val() + "元，单日限额：" + txDayMaxInpt.val() + "元，交易支持时段：" + txTimeLimitInpt.val());
	            	
	            	$(wrapperSub).append('<input type="hidden" id="intTransCdSel'+y+'">');
	            	$("#intTransCdSel"+y).val(intTransCdSel.val());
	            	
	            	$(wrapperSub).append('<input type="hidden" id="fixRateInpt'+y+'">');
	            	$("#fixRateInpt"+y).val(fixRateInpt.val());
	            	
	            	$(wrapperSub).append('<input type="hidden" id="minFeeInpt'+y+'">');
	            	$("#minFeeInpt"+y).val(minFeeInpt.val());
	            	
	            	$(wrapperSub).append('<input type="hidden" id="maxFeeInpt'+y+'">');
	            	$("#maxFeeInpt"+y).val(maxFeeInpt.val());
    				
    				$(wrapperSub).append('<input type="hidden" id="txDayMaxInpt'+y+'">');
	            	$("#txDayMaxInpt"+y).val(txDayMaxInpt.val());
    				
    				$(wrapperSub).append('<input type="hidden" id="txCardDayMaxIput'+y+'">');
	            	$("#txCardDayMaxIput"+y).val(txCardDayMaxIput.val());
    				
    				$(wrapperSub).append('<input type="hidden" id="txAmtMinInpt'+y+'">');
	            	$("#txAmtMinInpt"+y).val(txAmtMinInpt.val());
    				
    				$(wrapperSub).append('<input type="hidden" id="txAmtMaxInpt'+y+'">');
	            	$("#txAmtMaxInpt"+y).val(txAmtMaxInpt.val());
    				
    				$(wrapperSub).append('<input type="hidden" id="txTimeLimitInpt'+y+'">');
	            	$("#txTimeLimitInpt"+y).val(txTimeLimitInpt.val());
                    
                    $(wrapperSub).append('<input type="hidden" id="txT0PercentIput'+y+'">');
	            	$("#txT0PercentIput"+y).val(txT0PercentIput.val());
	            	
                    $(wrapperSub).append('<input type="hidden" id="transCurrCd'+y+'">');
                    $("#transCurrCd"+y).val(transCurrCd.val());
	            	
	            	
	            	$("#intTransCdSel").val("");
	            	$("#fixRateInpt").val("");
	            	$("#minFeeInpt").val("");
	            	$("#maxFeeInpt").val("");
	            	$("#txDayMaxInpt").val("");
	            	$("#txCardDayMaxIput").val("");
	            	$("#txAmtMinInpt").val("");
	            	$("#txAmtMaxInpt").val("");
	            	$("#txTimeLimitInpt").val("");
	            	$("#txT0PercentIput").val("");
	            	transCurrCd.val("");
	            	
	            	y++;
		        }
				
		    });
		    $(wrapperSub).on("click",".removeField", function(e){
        		if (confirm("确认要删除记录吗?")) {
        			e.preventDefault(); 
        			var index = $(this).parent('div').attr('id');
        			$(this).parent('div').remove(); 
		            $("#intTransCdSel"+index).remove(); 
		            $("#fixRateInpt"+index).remove(); 
		            $("#minFeeInpt"+index).remove(); 
		            $("#maxFeeInpt"+index).remove(); 
		            $("#txDayMaxInpt"+index).remove(); 
		            $("#txCardDayMaxIput"+index).remove(); 
		            $("#txAmtMinInpt"+index).remove(); 
		            $("#txAmtMaxInpt"+index).remove(); 
		            $("#txTimeLimitInpt"+index).remove(); 
		            $("#txT0PercentIput"+index).remove(); 
        		}else {
        			return false;
        		}
    		});
		    
		    $("form").submit(function() {
		    	var inputMerSettlePolicy = $("#inputMerSettlePolicy");
		    	var currCdSel = $("#currCdSel");
		    	if (inputMerSettlePolicy.text() == '' && currCdSel.val() != "all") {
		    		alert("当未添加「单一币别」清算信息时，「币别」请选择「全部」，将统一设置");
		    	} 
		    	
		    	var inputMerSettlePolicySub = $("#inputMerSettlePolicySub");
		    	if (inputMerSettlePolicySub.text() == '') {
		    		alert("请添加支付方式");
		    	}
		    	
		    	var trustDomain = "";
		    	var array = [];
		    	var mspArray = [];
		    	for (var i = 1; i < x; i++) {
		    		trustDomain = trustDomain + $("#newDomainName"+i).val() + ",";
		    	}
		    	for (var j = 1; j < y; j++) {
		    		if (typeof $("#intTransCdSel"+j).val() != "undefined") {
		    			var txTimeLimitInpt = $("#txTimeLimitInpt"+j).val();
		    			if (txTimeLimitInpt.indexOf(",") != -1) {
		    				txTimeLimitInpt = txTimeLimitInpt.replace(",", "，");
		    			}
		    			array.push($(
		    			   "#intTransCdSel"+j).val() 
		    			   + "@~" + $("#fixRateInpt"+j).val() 
		    			   + "@~" + $("#minFeeInpt"+j).val() 
		    			   + "@~" + $("#maxFeeInpt"+j).val() 
		    			   + "@~" + $("#txDayMaxInpt"+j).val() 
		    			   + "@~" + $("#txCardDayMaxIput"+j).val() 
		    			   + "@~" + $("#txAmtMinInpt"+j).val() 
		    			   + "@~" + $("#txAmtMaxInpt"+j).val()  
		    			   + "@~" + txTimeLimitInpt 
		    			   + "@~" + $("#txT0PercentIput"+j).val()
		    			   + "@~" + $("#transCurrCd"+j).val()
		    			   );
		    		}
		    	}
		    	
		    	for (var k = 1; k < z; k++) {
		    		if (typeof $("#currCdSel"+k).val() != "undefined") {
		    			mspArray.push($(
		    			   "#currCdSel"+k).val() 
		    			   + "@~" + $("#settlePeriodSel"+k).val() 
		    			   + "@~" + $("#balanceTransferSel"+k).val() 
		    			   + "@~" + $("#transferTimeInpt"+k).val() 
		    			   + "@~" + $("#balanceTransferT1Sel"+k).val() 
		    			   + "@~" + $("#preTransferTimeT1Inpt"+k).val() 
		    			   + "@~" + $("#preTransferT1PercentInpt"+k).val() 
		    			   + "@~" + $("#transferTimeT1Inpt"+k).val()  
		    			   );

		    		}
		    	}
			  	$("#trustDomain").val(trustDomain);
			  	$("#settleAlgorithmAll").val(array);
			  	$("#merSettlePolicyAll").val(mspArray);
			});
		</@head>
	</head>
	
	<body>
		<@addDiv label="${label}" addUrl="/mchnt/add/submit.do">
			一、基本信息录入<br>
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">商户名称：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="100"
							id="mchntCnNmInpt" name="mchntCnNm" />
					</td>
				</tr>
				<tr>
					<td class="label">归属代理商：</td>
					<td class="content">
						<select class="easyui-validatebox" style="width: 300px; height: 30px;"
							id="agentCdSel" name="agentCd">
							<option value=''>--请选择--</option>
							<#list agentlst as t>
								<option value="${t.agentCd}">${t.agentCd}-${t.agentCnNm}</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">站点：</td>
					<td class="content">
						<select name="siteId" id="siteId" >
							<@enumOpts enumNm="CommonEnums.Site" showEmptyOpt="false" showCode="true" />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">商户有效日期：</td>
					<td class="content">
						<input id="endDt" name="expiredDt" type="text" 
							onFocus="WdatePicker({dateFmt:'yyyyMMdd', readOnly:true})" class="Wdate" />
					</td>
				</tr>
				<tr>
					<td class="label">交易权限组：</td>
					<td class="content">
						<select class="easyui-validatebox" id="transTypeGroupIdSel" name="transTypeGroupId" style="width: 300px;">
							<option value="">--请选择--</option>
							<#list transTpGroup as t>
								<option value="${t.seqId}">${t.seqId}-${t.groupNm}</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">商戶状态：</td>
					<td class="content">
						<select id="mchntSt" name="_QRY_mchntSt">
							<@enumOpts enumNm='CommonEnums.MchntSt' showEmptyOpt='true'
								selected="${(qryParamMap.mchntSt)!''}" />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">联系人：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="90"
							id="contactPersonInpt" name="contactPerson" />
					</td>
				</tr>
				<tr>
					<td class="label">电话：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="90"
							id="contactPhoneInpt" name="contactPhone" />
					</td>
				</tr>
				<tr>
					<td class="label">邮箱：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="90"
							id="contactMailInpt" name="contactMail" />
						<span class="inputDesc">必填</span>
					</td>
				</tr>
				<tr>
					<td class="label">备注：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="90"
							id="commentInpt" name="comment" />
					</td>
				</tr>
				<tr>
					<td class="label">允许的充值来源：</td>
					<td class="content">
						<input class='chkbox_inpt' type="checkbox" name="allowedReqSrc" value="01" checked="checked"> 接口     
						<input class='chkbox_inpt' type="checkbox" name="allowedReqSrc" value="03" checked="checked"> 商户平台     
					</td>
				</tr>
				<tr>
					<td class="label">允许的代付来源：</td>
					<td class="content">
						<input class='chkbox_inpt' type="checkbox" name="allowedReqSrcWd" value="01" checked="checked"> 接口
						<input class='chkbox_inpt' type="checkbox" name="allowedReqSrcWd" value="03" checked="checked"> 商户平台          
					</td>
				</tr>
				<tr>
					<td class="label">接入方式：</td>
					<td class="content">
						<input class='chkbox_inpt' type="checkbox" name="apiType" value="0" checked="checked"> 收银台简易接口
						<input class='chkbox_inpt' type="checkbox" name="apiType" value="1" checked="checked"> 后台接口
					</td>
				</tr>
				<tr>
					<td class="label">域名：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="90"
							id="domainName" name="domainName" />
						<a id="addButton" href="javascript:void(0)" class="easyui-linkbutton">添加</a>
					</td>
				</tr>
				<tr>
					<td class="label"></td>
					<td class="content">
						<div id="inputDomainName"></div>
					</td>
				</tr>
				<tr>
					<td class="label">所属机构：</td>
					<td class="content">
						<select class="easyui-validatebox" style="width: 300px; height: 30px;"
							id="organIdSel" name="organId" >
							<option value=''>--请选择--</option>
							<#if olst ??>
								<#list olst as t>
									<option value="${t.organId}">${t.organId}-${t.organDesc}</option>
								</#list>
							</#if>
						</select>
					</td>
				</tr>
			</table><br><br>
			二、清算信息设置<br>
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">币别：</td>
					<td class="content">
						<select class="easyui-validatebox" name="currCd" id="currCdSel" >
							<@enumOpts enumNm="CurrencyEnums.CurrType" showEmptyOpt="true" showCode="true" />
							<option value='all' selected='selected'>全部</option>
						</select>
						<span class="inputDesc">必填，请注意：当未添加「单一币别」清算信息时，「币别」请选择「全部」，将统一设置</span>
					</td>
				</tr>
				<tr>
					<td class="label">清算周期：</td>
					<td class="content">
						<select class="easyui-validatebox" name="settlePeriod" id="settlePeriodSel">
							<@enumOpts enumNm="SettleEnums.SettlePeriod" showEmptyOpt="true" />
						</select>
						<span class="inputDesc">必填，请注意：清算周期后续无法修改</span>
					</td>
				</tr>
				<tr>
					<td class="label">D0结转：</td>
					<td class="content">
						<select class="easyui-validatebox" id="balanceTransferSel" name="balanceTransfer">
							<@enumOpts enumNm="TxnEnums.CommonYesNo" />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">D0结转时间：</td>
					<td class="content">
						<input class="easyui-validatebox" type ="text" id="transferTimeInpt" name ="transferTime"/>
						<span class="inputDesc">格式HHmmss，例如：235000(可省略，使用默认值)</span>
					</td>
				</tr>
				<tr>
					<td class="label">T1反还：</td>
					<td class="content">
						<select class="easyui-validatebox" id="balanceTransferT1Sel" name="balanceTransferT1">
							<@enumOpts enumNm="TxnEnums.CommonYesNo" />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">前置T1返还时间：</td>
					<td class="content">
						<input class="easyui-validatebox" type ="text" id="preTransferTimeT1Inpt" name ="preTransferTimeT1"/>
						<span class="inputDesc">格式HHmmss，空值表示不進行前置T1结转</span>
					</td>
				</tr>
				<tr>
					<td class="label">前置T1返还比例：</td>
					<td class="content">
						<input class="easyui-validatebox" type ="number" id="preTransferT1PercentInpt" name ="preTransferT1Percent" />
						<span class="inputDesc">%  例如: 90%</span>
					</td>
				</tr>
				<tr>
					<td class="label">后置T1返还时间：</td>
					<td class="content">
						<input class="easyui-validatebox" type ="text" id="transferTimeT1Inpt" name ="transferTimeT1"/>
						<span class="inputDesc">格式HHmmss，T1余额一次性返还，仅需设置后置时间</span>
						<a id="addSettleInfoButton" href="javascript:void(0)" class="easyui-linkbutton">添加「单一币别」清算信息</a>
					</td>
				</tr>
				<tr>
					<td class="label"></td>
					<td class="content">
						<div id="inputMerSettlePolicy"></div>
					</td>
				</tr>
			</table><br><br>
			三、支付方式配置<br>
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label" style="width: 135px;">交易类型：</td>
					<td class="content">
						<select class="easyui-validatebox" name="intTransCd" id="intTransCdSel">
							<@enumOpts enumNm="ProfitEnums.ProfitTxnTp" showEmptyOpt="true" showCode="true" />
						</select>
						<select class="easyui-validatebox" name="transCurrCd" id="transCurrCd" >
							<@enumOpts enumNm="CurrencyEnums.CurrType" showEmptyOpt="true" showCode="true" />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">费率：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="7" id="fixRateInpt" name="fixRate" />
						<span class="inputDesc">请输入小数，小数点后最多五位，如：0.12345</span>
					</td>
				</tr>
				<tr>
					<td class="label">保底：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="7"	id="minFeeInpt" name="minFee" />
						封顶：
						<input class="easyui-validatebox" type="text" maxlength="7"	id="maxFeeInpt" name="maxFee" />
					</td>
				</tr>
				<tr>
					<td class="label">单日限额：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="15" id="txDayMaxInpt" name="txDayMax" />
						
						<span class="inputDesc">元</span>
						单日单卡限额：
						<input class="easyui-validatebox" type="text" maxlength="20" id="txCardDayMaxIput" name="txCardDayMax" />
						<span class="inputDesc">元</span>
					</td>
				</tr>
				<tr>
					<td class="label">单笔限额：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="15" id="txAmtMinInpt" name="txAmtMin" /> --
						<input class="easyui-validatebox" type="text" maxlength="20" id="txAmtMaxInpt" name="txAmtMax" />
						<span class="inputDesc">元</span>
					</td>
				</tr>
				<tr>
					<td class="label">交易支持时段：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="20" id="txTimeLimitInpt" name="txTimeLimit" />
						<span class="inputDesc">例："ALL"或"0800-1200,1400-2000"</span>
					</td>
				</tr>
				<tr>
					<td class="label">垫资比例：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="20" id="txT0PercentIput" name="txT0Percent" />
						<span class="inputDesc">如：0.05</span>
						<a id="addPolicyButton" href="javascript:void(0)" class="easyui-linkbutton">添加</a>
					</td>
				</tr>
				<tr>
					<td class="label"></td>
					<td class="content">
						<div id="inputMerSettlePolicySub"></div>
					</td>
				</tr>
			</table>
			<input type="hidden" id="trustDomain" name="trustDomain">
			<input type="hidden" id="settleAlgorithmAll" name="settleAlgorithmAll">
			<input type="hidden" id="merSettlePolicyAll" name="merSettlePolicyAll">
		</@addDiv>
	</body>
</html>