<#include "/common/edit_macro.ftl" />
<#assign label = "商户信息">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="mchnt" editSuccMsg="商户修改任务已提交，请等待审核">
			$("#mchntCdInpt").validatebox({
				required: true,
				novalidate: true,
				validType: ['number','length[15]']
			});
			$("#mchntCnNmInpt").validatebox({
				required: true,
				novalidate: true,
				validType: 'cnMaxLength[100]'
			});
			$("#mchntEnNmInpt").validatebox({
				required: true,
				novalidate: true,
				validType: ['enNm', 'maxLength[100]']
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
			$("#expiredDtInpt").validatebox({
				required: true,
				novalidate: true
			});
			
			var firstLoad = true;
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
								if (firstLoad) {
									$('#tradeTypeSel').val('${(entity.tradeType)!''}').validatebox({
										required: true,
										novalidate: true
									});
									firstLoad = false;
								}
							}
						});
					});
				}
			});
			
			$('#agentCdSel').val('${(entity.agentCd)!''}').validatebox({
				required: true,
				novalidate: true
			}).trigger('change');
			
			<#-- 允许的充值来源 -->
			var allowedReqSrc = '${entity.allowedReqSrc}';
			if (allowedReqSrc == "ALL") {
				$("#allowedReqSrc01").attr("checked",true);
				$("#allowedReqSrc03").attr("checked",true);
			} else {
				var reqArray = new Array();
				var reqArray = allowedReqSrc.split(",");
				for (var i = 0; i < reqArray.length; i ++) {
					if (reqArray[i] == "01") {
						$("#allowedReqSrc01").attr("checked",true);
					}
					if (reqArray[i] == "03") {
						$("#allowedReqSrc03").attr("checked",true);
					}
				}
			}
			
			<#-- 允许的代付来源 -->
			var allowedReqSrcWd = '${entity.allowedReqSrcWd}';
			if (allowedReqSrcWd == "ALL") {
				$("#allowedReqSrcWd01").attr("checked",true);
				$("#allowedReqSrcWd03").attr("checked",true);
			} else {
				var wdArray = new Array();
				var wdArray = allowedReqSrcWd.split(",");
				for (var i = 0; i < wdArray.length; i ++) {
					if (wdArray[i] == "01") {
						$("#allowedReqSrcWd01").attr("checked",true);
					}
					if (wdArray[i] == "03") {
						$("#allowedReqSrcWd03").attr("checked",true);
					}
				}
			}
			
			<#-- 接入方式 -->
			var apiTypeCode = '${entity.apiTypeCode}';
			var apiArray = new Array();
			var apiArray = apiTypeCode.split(",");
			for (var i = 0; i < apiArray.length; i ++) {
				if (apiArray[i] == "0") {
					$("#apiType0").attr("checked",true);
				}
				if (apiArray[i] == "1") {
					$("#apiType1").attr("checked",true);
				}
			}
			
			<#-- 添加域名 -->
  			var maxFields = 10;<#-- 最大添加域名數 -->
    		var wrapper = $("#inputDomainName");
    		var addButton = $("#addButton");
    		var domainName = $("#domainName");
    		var x = 1;
    		var editTrustDomain = '${entity.trustDomain}';
    		var tdArray = new Array();
			var tdArray = editTrustDomain.split(",");
			for (var i = 0; i < tdArray.length; i ++) {
				$(wrapper).append('<div><input type="text" class="easyui-validatebox" id="newDomainName'+x+'"/></div>');
		        $("#newDomainName"+x).val(tdArray[i]);
		        x++;
			}
		    $(addButton).click(function(e){
		        e.preventDefault();
		        if (x < maxFields && domainName.val() != "") {
	            	$(wrapper).append('<div><input type="text" class="easyui-validatebox" id="newDomainName'+x+'"/></div>');
		            $("#newDomainName"+x).val(domainName.val());
	            	domainName.val("");
	            	x++;
		        }
		    });
		    
		    $("form").submit(function() {
		    	var trustDomain = "";
		    	for (var i = 1; i < x; i++) {
		    		trustDomain = trustDomain + $("#newDomainName"+i).val() + ",";
		    	}
			  	$("#trustDomain").val(trustDomain);
			});
		</@head>
		
		
	</head>
	
	<body>
		<@editDiv label="${label}" editUrl="/mchnt/edit/submit.do">
			<input type="hidden" name="mchntCd" value="${entity.mchntCd}" />
			<input type="hidden" name="siteId" value="${entity.siteId}" />
			<input type="hidden" name="tradeType" value="00" />
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">商户代码：</td>
					<td class="content">
						${entity.mchntCd}
					</td>
				</tr>
				<tr>
					<td class="label">商户名称：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="100"
							id="mchntCnNmInpt" name="mchntCnNm" value="${entity.mchntCnNm}" />
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
						${entity.siteId}
					</td>
				</tr>
				<tr>
					<td class="label">商户有效日期：</td>
					<td class="content">
						<input id="endDt" name="expiredDt" type="text" value="${entity.expiredDt}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd', readOnly:true})" class="Wdate" />
					</td>
				</tr>
				<tr>
					<td class="label">交易权限组：</td>
					<td class="content">
						<select class="easyui-validatebox" id="transTypeGroupIdSel" name="transTypeGroupId" style="width: 300px;">
							<option value="">--请选择--</option>
							<#list transTpGroup as t>
								<#if (t.seqId)?string == (entity.transTypeGroupId)>
									<option value="${t.seqId}" selected="selected">${t.seqId}-${t.groupNm}</option>
								<#else>
									<option value="${t.seqId}">${t.seqId}-${t.groupNm}</option>
								</#if>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">商戶状态：</td>
					<td class="content">
						<select id="mchntStSel" name="mchntSt" data-options="editable:false">
							<@enumOpts enumNm='CommonEnums.MchntSt' showCode='true' 
								showEmptyOpt="false" selected="${entity.mchntSt}" />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">联系人：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40"
							id="contactPersonInpt" name="contactPerson" value="${entity.contactPerson}" />
					</td>
				</tr>
				<tr>
					<td class="label">电话：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40"
							id="contactPhoneInpt" name="contactPhone" value="${entity.contactPhone}" />
					</td>
				</tr>
				<tr>
					<td class="label">邮箱：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="90"
							id="contactMailInpt" name="contactMail" value="${entity.contactMail}"/>
						<span class="inputDesc">必填</span>
					</td>
				</tr>
				<tr>
					<td class="label">备注：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="254"
							id="commentInpt" name="comment" value="${entity.comment}" />
					</td>
				</tr>
				<tr>
					<td class="label">允许的充值来源：</td>
					<td class="content">
						<input class='chkbox_inpt' type="checkbox" name="allowedReqSrc" value="01" id="allowedReqSrc01"> 接口     
						<input class='chkbox_inpt' type="checkbox" name="allowedReqSrc" value="03" id="allowedReqSrc03"> 商户平台     
					</td>
				</tr>
				<tr>
					<td class="label">允许的代付来源：</td>
					<td class="content">
						<input class='chkbox_inpt' type="checkbox" name="allowedReqSrcWd" value="01" id="allowedReqSrcWd01"> 接口
						<input class='chkbox_inpt' type="checkbox" name="allowedReqSrcWd" value="03" id="allowedReqSrcWd03"> 商户平台          
					</td>
				</tr>
				<tr>
					<td class="label">接入方式：</td>
					<td class="content">
						<input class='chkbox_inpt' type="checkbox" name="apiType" value="0" id="apiType0"> 收银台简易接口
						<input class='chkbox_inpt' type="checkbox" name="apiType" value="1" id="apiType1"> 后台接口
					</td>
				</tr>
				<tr>
					<td class="label">域名：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="90"
							id="domainName" name="domainName"/>
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
							<#list olst as t>
							<#if (t.organId)?string == (organId)>
									<option value="${t.organId}" selected="selected">${t.organId}-${t.organDesc}</option>
								<#else>
									<option value="${t.organId}">${t.organId}-${t.organDesc}</option>
								</#if>
							</#list>
						</select>
					</td>
				</tr>
			</table>
			<input type="hidden" id="trustDomain" name="trustDomain">
		</@editDiv>
	</body>
</html>