<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="渠道虚拟终端信息" base="virtualTerm" qryFuncCode="1100040001"
			addFlg=true addFuncCode="1100040002"
			editFlg=true editFuncCode="1100040004" editUrl='"edit.do?chnlId=" + sel.chnlId + "&chnlMchntCd=" + sel.chnlMchntCd'
			deleteFlg=true deleteFuncCode="1100040006" deleteUrl='"delete.do?chnlId=" + sel.chnlId + "&chnlMchntCd=" + sel.chnlMchntCd'>
			<#--  
			$("#signInBtn").click(function() {
				var sel = $("#qryResultTbl").datagrid("getSelected");
				if (sel == null) {
					alert("请选择一条记录");
				} else {
					if (confirm("确认发起签到?")) {
						if (dealFlag) {
							alert("正在处理请求，请稍等......");
							return false;
						}
						dealFlag = true;		 
						var url = baseUrl + "signIn.do?chnlId=" + sel.chnlId + "&mchntCd=" + sel.mchntCd + "&termId=" + sel.termId;
						$.post(url,
							function(data) {
								$.processAjaxResp(data, function() {
									alert("签到成功");
									$("#qryForm").submit();
								});
								dealFlag = false;
							}
						);
					}
				}
			}); 
			-->
			<@authCheck funcCode='1100040008'>
				$("#transParamMngBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						$.jumpTo(_ctx + "/virtualTerm/transParam/manage.do?chnlId=" + sel.chnlId+"&chnlMchntCd="+encodeURI(sel.chnlMchntCd));
					}
				});
			</@authCheck>
			
				$("#settlePolicyMngBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						$.jumpTo(_ctx + "/virtualTerm/settlePolicy/manage.do?chnlId=" + sel.chnlId+"&chnlMchntCd="+encodeURI(sel.chnlMchntCd));
					}
				});
				
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/virtualTerm/qry.do" qryFuncCode="1100040001">
			<table class="qry_tbl">
				<tr>
					<td>渠道：</td>
					<td>
						<select id="chnlIdSel" name="_QRY_chnlId">
							<#-- <@enumOpts enumNm='TxnEnums.ChnlId' showEmptyOpt='true'  
								selected="${(qryParamMap.chnlId)!''}" />-->
							<@enumOpts enumNm='TxnEnums.ChnlId' showCode='true' showEmptyOpt='true' selected="${(qryParamMap.chnlId)!''}"/>
						</select>
					</td>
					<td>商户号：</td>
					<td>
						<input class="easyui-validatebox" type="text" maxLength="40"
							id="chnlMchntCd" name="_QRY_chnlMchntCd" value="${(qryParamMap.chnlMchntCd)!''}" />
					</td>
					<td>状态：</td>
					<td>
						<select id="stateSel" name="_QRY_state">
							<@enumOpts enumNm='TxnEnums.CommonValid' showEmptyOpt='true' showCode='false' 
								selected="${(qryParamMap.state)!''}" />
						</select>
					</td>
				</tr>
				<tr>
					<td>商户名称：</td>
					<td>
						<input class="easyui-validatebox" type="text" maxLength="40"
							id="chnlMchntDescInpt" name="_QRY_chnlMchntDesc" value="${(qryParamMap.chnlMchntDesc)!''}" />
					</td>
					<#--  
					<td>清算周期：</td>
					<td>
						<select id="settlePeriodSel" name="_QRY_settlePeriod">
							<@enumOpts enumNm='SettleEnums.SettlePeriod' showEmptyOpt='true' showCode='false'
								selected="${(qryParamMap.settlePeriod)!''}"  />
						</select>
					</td>-->
					<td>
					<input type="checkbox" id="showBmchnt" style="width:18px;height:18px" checked="true">&nbsp展示关联商户
					</td>
				</tr>	
			</table>
		</@qryDiv>
		
		<@qryResultDiv addFlg=true addFuncCode="1100040002" 
			editFlg=true editFuncCode="1100040004"
			deleteFlg=true deleteFuncCode="1100040006">
			<#--  <a id="signInBtn" href="javascript:void(0)" class="easyui-linkbutton">签到</a> -->
			<a id="settlePolicyMngBtn" href="javascript:void(0)" class="easyui-linkbutton">清算信息管理</a>
			<@authCheck funcCode='1100040008'>
			 	<a id="transParamMngBtn" href="javascript:void(0)" class="easyui-linkbutton">交易参数管理</a>
			</@authCheck>
		</@qryResultDiv>
	</body>
<html>