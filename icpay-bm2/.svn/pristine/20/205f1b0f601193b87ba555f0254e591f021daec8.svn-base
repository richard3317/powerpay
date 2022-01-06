<#include "/common/edit_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>新增限额规则配置信息</title>
		<#include "/common/include.ftl" />
		<script type="text/javascript">
			$(function() {
				var baseUrl = "${ctx}/riskThreshold/";
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
						$.processAjaxResp(data, function() {
							alert("修改限额规则配置信息成功");
							$.jumpTo(baseUrl + "backToManage.do");
						});
					}
				});
				$("#backBtn").click(function() {
					$.jumpTo(baseUrl + "backToManage.do");
				});
				<#if entity.ruleTp == '01'>
					$("#threholdUnit").html("元");
					$("#threholdInpt").validatebox({
						required: true,
						novalidate: true,
						validType: ['amount']
					});
					$("#periodInpt").validatebox({
						required: true,
						novalidate: true,
						validType: ['number', 'maxInteger[168]']
					});
				<#else>
					$("#threholdUnit").html("笔");
					$("#threholdInpt").validatebox({
						required: true,
						novalidate: true,
						validType: 'number'
					});
					$("#periodInpt").validatebox({
						required: true,
						novalidate: true,
						validType: ['number', 'maxInteger[168]', 'minInteger[1]']
					});
				</#if>
				$("#expiredTmStrInpt").validatebox({
					required: true,
					novalidate: true
				});
				$("#commentInpt").validatebox({
					novalidate: true,
					validType: 'cnMaxLength[254]'
				});
			});
		</script>
	</head>
	
	<body>
		<@editDiv label="限额规则配置信息" editUrl="/riskThreshold/edit/submit.do">
			<input type="hidden" name="ruleId" value="${entity.ruleId}" />
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">规则名称：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="254"
							id="ruleNmInpt" name="ruleNm" value="${entity.ruleNm}" />
					</td>
				</tr>
				<tr>
					<td class="label">规则类型：</td>
					<td class="content">
						<@enumVal enumNm='RiskEnums.ThresholdRuleTp' code='${entity.ruleTp}' showCode="true" />
					</td>
				</tr>
				<tr>
					<td class="label">风险类型：</td>
					<td class="content">
						<select id="riskTpSel" name="riskTp" data-options="editable:false">
							<@enumOpts enumNm='RiskEnums.RiskTp' showCode='true' 
								showEmptyOpt='false' selected="${entity.riskTp}" />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">统计周期(小时)：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="9"
							id="periodInpt" name="period" value="${entity.period}" />
						<br />
						<@inptInfo>
							请输入整数，以小时为单位<br />
							1. 如果规则类型选择“金额阀值”，则填写0表示单笔限额，最大值为168，即7天<br/>
							2. 如果规则类型选择“笔数阀值”，则不能填写0
						</@inptInfo>
					</td>
				</tr>
				<tr>
					<td class="label">阀值：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="12"
							id="threholdInpt" name="threholdStr" value="${entity.threhold}" /> (<span id="threholdUnit"></span>)
						<br />
						<@inptInfo>
							1. 如果规则类型选择“金额阀值”，则填写最大金额，不能为0，以元为单位<br/>
							2. 如果规则类型选择“笔数阀值”，则填写周期内最大笔数，不能为0
						</@inptInfo>
					</td>
				</tr>
				<tr>
					<td class="label">处理结果：</td>
					<td class="content">
						<select id="resultSel" name="result" data-options="editable:false">
							<@enumOpts enumNm='RiskEnums.Result' showCode='true' 
								showEmptyOpt='false' selected="${entity.result}" />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">过期时间：</td>
					<td class="content">
						<input id="expiredTmStrInpt" name="expiredTmStr" type="text" value="${expiredTmStr}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd HH:mm:ss'})" class="Wdate" style="width: 150px;" />
					</td>
				</tr>
				<tr>
					<td class="label">监控项-卡号：</td>
					<td class="content">
						${entity.priAcctNo}
					</td>
				</tr>
				<tr>
					<td class="label">监控项-交易类型：</td>
					<td class="content">
						<@enumVal enumNm='RiskEnums.ThresholdTxnTp' code='${entity.intTransCd}' showCode="true" />
					</td>
				</tr>
				<tr>
					<td class="label">监控项-商户代码：</td>
					<td class="content">
						${entity.mchntCd}
					</td>
				</tr>
				<tr>
					<td class="label">监控项-IP地址：</td>
					<td class="content">
						${entity.ip}
					</td>
				</tr>
				<tr>
					<td class="label">监控项-终端序号：</td>
					<td class="content">
						${entity.termSn}
					</td>
				</tr>
				<tr>
					<td class="label">备注：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="254"
							id="commentInpt" name="comment" value="${entity.comment}" />
					</td>
				</tr>
			</table>
		</@editDiv>	
	</body>
</html>