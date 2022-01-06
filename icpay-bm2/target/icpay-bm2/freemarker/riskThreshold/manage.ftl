<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="限额规则配置信息" base="riskThreshold" qryFuncCode="7000030001"
			detailFlg=true detailFuncCode="7000030001" detailUrl='"detail.do?ruleId=" + sel.ruleId'
			addFlg=true addFuncCode="7000030003"
			editFlg=true editFuncCode="7000030005" editUrl='"edit.do?ruleId=" + sel.ruleId'
			deleteFlg=true deleteFuncCode="7000030007" deleteUrl='"delete.do?ruleId=" + sel.ruleId'>
			$("#enableBtn").click(function() {
				var sel = $("#qryResultTbl").datagrid("getSelected");
				if (sel == null) {
					alert("请选择一条记录");
				} else {
					if (sel.status == '1') {
						alert("该规则已启用");
						return;
					}
					if (confirm("确认启用?")) {
						if (dealFlag) {//重复提交控制
							alert("正在处理请求，请稍等......");
							return false;
						}
						dealFlag = true;
						var url = baseUrl + "enable.do?ruleId=" + sel.ruleId;
						$.post(url,
							function(data) {
								$.processAjaxResp(data, function() {
									alert("规则启用成功");
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
						if (dealFlag) {
							alert("正在处理请求，请稍等......");
							return false;
						}
						dealFlag = true;
						var url = baseUrl + "invalid.do?ruleId=" + sel.ruleId;
						$.post(url,
							function(data) {
								$.processAjaxResp(data, function() {
									alert("规则已失效");
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
		<@qryDiv qryUrl="/riskThreshold/qry.do" qryFuncCode="7000030001">
			<table class="qry_tbl">
				<tr>
					<td>规则ID：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="ruleIdInpt" maxLength="9"
							name="_QRY_ruleId" value="${(qryParamMap.ruleId)!''}" />
					</td>
					<td>规则名称：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="ruleNmInpt" name="_QRY_ruleNm" value="${(qryParamMap.ruleNm)!''}" />
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
		
		<@qryResultDiv detailFlg=true  detailFuncCode="7000030002"
			addFlg=true addFuncCode="7000030003"
			editFlg=true editFuncCode="7000030005"
			deleteFlg=true deleteFuncCode="7000030007">
			<@authCheck funcCode="7000030008">
				<a id="enableBtn" href="javascript:void(0)" class="easyui-linkbutton">启用</a>
			</@authCheck>
			<@authCheck funcCode="7000030009">
				<a id="invalidBtn" href="javascript:void(0)" class="easyui-linkbutton">禁用</a>
			</@authCheck>
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
	</body>
</html>