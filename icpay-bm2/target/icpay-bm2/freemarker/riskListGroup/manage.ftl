<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="风控规则名单组信息" base="riskListGroup" qryFuncCode="7000010001"
			addFlg=true addFuncCode="7000010003"
			detailFlg=true detailFuncCode="7000010002" detailUrl='"detail.do?groupId=" + sel.groupId'
			editFlg=true editFuncCode="7000010005" editUrl='"edit.do?groupId=" + sel.groupId'
			deleteFlg=true deleteFuncCode="7000010007" deleteUrl='"delete.do?groupId=" + sel.groupId'>
			$("#enableBtn").click(function() {
				var sel = $("#qryResultTbl").datagrid("getSelected");
				if (sel == null) {
					alert("请选择一条记录");
				} else {
					if (sel.status == '1') {
						alert("该名单组信息已启用");
						return;
					}
					if (confirm("确认启用?")) {
						if (dealFlag) {//重复提交控制
							alert("正在处理请求，请稍等......");
							return false;
						}
						dealFlag = true;
						var url = baseUrl + "enable.do?groupId=" + sel.groupId;
						$.post(url,
							function(data) {
								$.processAjaxResp(data, function() {
									alert("名单组信息启用成功");
									dealFlag = false;
									$("#qryForm").submit();
								});
							}
						);
					}
				}
			});
			$("#invalidBtn").click(function() {
				var sel = $("#qryResultTbl").datagrid("getSelected");
				if (sel == null) {
					alert("请选择一条记录");
				} else {
					if (sel.status != '1') {
						alert("请选择状态为\"1-启用\"的记录");
						return;
					}
					if (confirm("确认禁用?")) {
						if (dealFlag) {//重复提交控制
							alert("正在处理请求，请稍等......");
							return false;
						}
						dealFlag = true;
						var url = baseUrl + "invalid.do?groupId=" + sel.groupId;
						$.post(url,
							function(data) {
								$.processAjaxResp(data, function() {
									alert("名单组信息已禁用");
									dealFlag = false;
									$("#qryForm").submit();
								});
							}
						);
					}
				}
			});
		</@head>
	</head>

	<body style="padding: 5px;">
		<@qryDiv qryUrl="/riskListGroup/qry.do" qryFuncCode="7000010001">
			<table class="qry_tbl">
				<tr>
					<td>名单组ID：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="groupIdInpt" maxLength="15"
							name="_QRY_groupId" value="${(qryParamMap.groupId)!''}">
					</td>
					<td>名单组名称：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="groupNmInpt" name="_QRY_groupNm" value="${(qryParamMap.groupNm)!''}">
					</td>
					<td>状态：</td>
					<td>
						<select id="statusSel" name="_QRY_status" data-options="editable:false">
							<@enumOpts enumNm='RiskEnums.Status' showCode='true' 
								showEmptyOpt="true" selected="${(qryParamMap.status)!''}" />
						</select>
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv detailFlg=true detailFuncCode="7000010002"
			addFlg=true addFuncCode="7000010003"
			editFlg=true editFuncCode="7000010005" 
			deleteFlg=true deleteFuncCode="7000010007">
			<@authCheck funcCode="7000010008">
				<a id="enableBtn" href="javascript:void(0)" class="easyui-linkbutton">启用</a>
			</@authCheck>
			<@authCheck funcCode="7000010009">
				<a id="invalidBtn" href="javascript:void(0)" class="easyui-linkbutton">禁用</a>
			</@authCheck>
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
	</body>
</html>