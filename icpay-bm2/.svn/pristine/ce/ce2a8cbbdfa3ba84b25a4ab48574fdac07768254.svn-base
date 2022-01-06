<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="资金池配置" base="chnlCashPool" qryFuncCode="2001020001"
			addFlg=true addFuncCode="2001020002"
			editFlg=true editFuncCode="2001020008" editUrl='"edit.do?poolId=" + sel.poolId + "&chnlId=" + sel.chnlId+ "&chnlMchntCd=" + sel.chnlMchntCd+ "&currCd=" + sel.currCd'
			deleteFlg=true deleteFuncCode="2001020004" deleteUrl='"delete.do?poolId=" + sel.poolId + "&chnlId=" + sel.chnlId+ "&chnlMchntCd=" + sel.chnlMchntCd+ "&currCd=" + sel.currCd'
			>
			
			<@authCheck funcCode='2001020005'>
			
 				$('#poolId').combobox({
					required: false,
					novalidate: true,
					filter : function(q, row){
						var opts = $(this).combobox('options');
						return row[opts.textField].indexOf(q) > -1;
					}
				});
			
				$("#enableBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						if (sel.state == '1') {
							alert('该资金池配置已经为"1-启用"状态');
							return;
						}
						if (confirm("确认启用?")) {
							if (dealFlag) {//重复提交控制
								alert("正在处理请求，请稍等......");
								return false;
							}
							dealFlag = true;
							var url = baseUrl + "enable.do?chnlMchntCd=" + sel.chnlMchntCd + "&poolId=" + sel.poolId + "&chnlId=" + sel.chnlId+ "&currCd=" + sel.currCd;
							$.post(url,
								function(data) {
									$.processAjaxResp(data, function() {
										alert("资金池配置启用成功");
										dealFlag = false;
										$("#qryForm").submit();
									});
								}
							);
						}
					}
				});
			</@authCheck>
			
			<@authCheck funcCode='2001020006'>
				$("#disableBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						if (sel.state == '0') {
							alert('该资金池配置已经为"0-无效"状态');
							return;
						}
						if (confirm("确认禁用?")) {
							if (dealFlag) {//重复提交控制
								alert("正在处理请求，请稍等......");
								return false;
							}
							dealFlag = true;
							var url = baseUrl + "disable.do?chnlMchntCd=" + sel.chnlMchntCd + "&poolId=" + sel.poolId + "&chnlId=" + sel.chnlId+ "&currCd=" + sel.currCd;
							$.post(url,
								function(data) {
									$.processAjaxResp(data, function() {
										alert("资金池配置禁用成功");
										dealFlag = false;
										$("#qryForm").submit();
									});
								}
							);
						}
					}
				});
			</@authCheck>
			
			<@authCheck funcCode='2001020007'>
				$("#defaultPriorityBtn").click(function(){
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						if (sel.wdState == '0') {
							alert('该资金池配置已经为 "0-默认" 出款状态');
							return;
						}
						if (confirm("确认调整为默认出款状态?")) {
							if (dealFlag) {//重复提交控制
								alert("正在处理请求，请稍等......");
								return false;
							}
							dealFlag = true;
							var url = baseUrl + "priorityModify.do?chnlMchntCd=" + sel.chnlMchntCd + "&poolId=" + sel.poolId + "&chnlId=" + sel.chnlId + "&wdState=0" + "&currCd=" + sel.currCd;
							$.post(url,
								function(data) {
									$.processAjaxResp(data, function() {
										<#--alert("出款状态调整为默认成功");-->
										dealFlag = false;
										$("#qryForm").submit();
									});
								}
							);
						}
					}
				});
				
				$("#betterPriorityBtn").click(function(){
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						if (sel.wdState == '1') {
							alert('该资金池配置已经为 "1-优先" 出款状态');
							return;
						}
						if (confirm("确认调整为优先出款状态?")) {
							if (dealFlag) {//重复提交控制
								alert("正在处理请求，请稍等......");
								return false;
							}
							dealFlag = true;
							var url = baseUrl + "priorityModify.do?chnlMchntCd=" + sel.chnlMchntCd + "&poolId=" + sel.poolId + "&chnlId=" + sel.chnlId + "&wdState=1"+ "&currCd=" + sel.currCd;
							$.post(url,
								function(data) {
									$.processAjaxResp(data, function() {
										<#--alert("出款状态调整为优先成功");-->
										dealFlag = false;
										$("#qryForm").submit();
									});
								}
							);
						}
					}
				});
				
			</@authCheck>
			
			
				$("#amtSum").click(function() {
					$("#sumChnlAvailable").show();
					$.ajax({
						type : "POST",
						url : baseUrl+"amtSum.do",
						async: true,
						cache:false,
						data: $("#qryForm").serialize(),
						success : function(mm) {
							var obj = parseJson(mm);
							$("#sumAvailableBalance").text(obj.sumAvailableBalance);
						},
						error:function(){
					    	alert("系统异常，请联系管理员！");
					    	return  false;
					    }
					});
				});
			
			
		</@head>
	</head>

	<body style="padding: 5px;">
		<@qryDiv qryUrl="/chnlCashPool/qry.do" showBtn=true  qryFuncCode="2001020001">
		<table class="qry_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td>渠道商户号：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="chnlMchntCdInpt" maxLength="128"
							name="_QRY_chnlMchntCd" value="${(qryParamMap.chnlMchntCd)!''}">
					</td>
					<td>渠道商户名称：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="chnlMchntDescInpt" maxLength="256"
							name="_QRY_chnlMchntDesc" value="${(qryParamMap.chnlMchntDesc)!''}">
					</td>
					<td>资金池名称：</td>
					<td>
						<select class="easyui-validatebox"
							id="poolId" name="_QRY_poolId" 
							style="width: 200px; height: 27px; ">
							<option value="">--请选择--</option>
							<#list poolList as lst>
								<#if (lst.poolId==((qryParamMap.poolId)!''))>
									<option value="${lst.poolId}" selected="selected">${lst.poolDesc}</option>
								<#else>	
									<option value="${lst.poolId}">${lst.poolDesc}</option>
								</#if>	
							</#list>
						</select>
					</td>
					<td class="label">状态：</td>
					<td class="content">
						<select id="state" name="_QRY_state" data-options="editable:false">
							<option value="">--请选择--</option>
							<option value="0">0-无效</option>
							<option value="1">1-有效</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">出款状态：</td>
					<td class="content">
						<select id="wdState" name="_QRY_wdState" data-options="editable:false">
							<option value="">--请选择--</option>
							<option value="0">0-默认</option>
							<option value="1">1-优先</option>
						</select>
					</td>
					<td>优先时段：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="txTimeLimitInpt" maxLength="15"
							name="_QRY_txTimeLimit" value="${(qryParamMap.txTimeLimit)!''}">
					</td>
					<td class="label">渠道：</td>
					<td class="content">
						<select id="chnlIdSel" name="_QRY_chnlId">
							<@enumOpts enumNm='TxnEnums.ChnlId' showCode='true' showEmptyOpt='true' selected="${(qryParamMap.chnlId)!''}" />
						</select>
					</td>
					<td>币别：</td>
					<td>
						<select  name="_QRY_currCd" id="currCd" >
								<@enumOpts enumNm="CurrencyEnums.CurrType" showEmptyOpt="true" showCode="false"
								selected="${(qryParamMap.currCd)!''}" />
						</select>
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv
			addFlg=true addFuncCode="2001020002"
			editFlg=true editFuncCode="2001020008"
			deleteFlg=true deleteFuncCode="2001020004">
			
			<@authCheck funcCode='2001020005'>
			 	<a id="enableBtn" href="javascript:void(0)" class="easyui-linkbutton" 
					   data-options="iconCls:'icon-ok'" >启用</a>
			 </@authCheck>
			<@authCheck funcCode='2001020006'>
			 	<a id="disableBtn" href="javascript:void(0)" class="easyui-linkbutton" 
					   data-options="iconCls:'icon-no'">停用</a>
			 </@authCheck>
		<#--  客服不想要只有優先跟默認兩個選項，比照路由的優先權
			<@authCheck funcCode='2001020007'>
				<a id="betterPriorityBtn" href="javascript:void(0)" class="easyui-linkbutton"  
					  data-options="iconCls:'icon-edit'">打开紧急出款</a>
				<a id="defaultPriorityBtn" href="javascript:void(0)" class="easyui-linkbutton"  
					  data-options="iconCls:'icon-edit'">关闭紧急出款</a>
			</@authCheck>
		-->
			
				<a id="amtSum" href="javascript:void(0)" class="easyui-linkbutton"  data-options="iconCls:'icon-sum'">通道余额加总</a>
			
			
			<div id="Calculation">
				<table>
					<tr id="sumChnlAvailable" style="display: none;">
						<td>通道余额总额：</td>
						<td align="right" width="100px" ><span id="sumAvailableBalance"></span>&nbsp;元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
				</table>
			</div>
			
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
	</body>
</html>