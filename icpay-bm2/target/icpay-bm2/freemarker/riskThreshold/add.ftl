<#include "/common/add_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>新增限额规则配置信息</title>
		<#include "/common/include.ftl" />
		<script type="text/javascript">
			$(function() {
				var baseUrl = "${ctx}/riskThreshold/";
				$("#saveBtn").click(function() {
					if (confirm("确认提交?")) {
						$("#addForm").form("enableValidation").submit();
					}
				});
				$("#addForm").form({
					url: $("#addForm").attr("action"),
					onSubmit: function() {
						if ($("#mchntCdInpt").val() == ""
							&& $("#priAcctNoInpt").val() == ""
							&& $("#ipInpt").val() == ""
							&& $("#termSnInpt").val() == "") {
							alert("监控项卡号、商户代码、IP地址、终端序号不能都为空");
							return false;
						}
						return $(this).form("validate");
					},
					success: function(data) {
						$.processAjaxResp(data, function() {
								alert("新增限额规则配置信息成功");
							$.jumpTo(baseUrl + "backToManage.do");
						});
					}
				});
				$("#backBtn").click(function() {
						$.jumpTo(baseUrl + "backToManage.do");
					});
					$("#ruleNmInpt").validatebox({
					required: true,
					novalidate: true,
					validType: 'cnMaxLength[254]'
				});
				$("#ruleTpSel").change(function() {
					if ("01" == $(this).val()) {
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
					} else {
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
					}
				});
				$("#periodInpt").validatebox({
					required: true,
					novalidate: true,
					validType: ['number', 'maxInteger[168]']
				});
				$("#threholdInpt").validatebox({
					required: true,
					novalidate: true,
					validType: 'number'
				});
				$("#expiredTmStrInpt").validatebox({
					required: true,
					novalidate: true
				});
				$("#priAcctNoInpt").validatebox({
					novalidate: true,
					validType: ['numberOrStar','maxLength[19]']
				});
				$("#intTransCdInpt").validatebox({
					novalidate: true,
					validType: ['numberOrStar','lengthOrStar[4]']
				});
				$("#mchntCdInpt").validatebox({
					novalidate: true,
					validType: ['numberOrStar','lengthOrStar[15]']
				});
				$("#ipInpt").validatebox({
					novalidate: true,
					validType: ['ipOrStar','maxLength[32]']
				});
				$("#termSnInpt").validatebox({
					novalidate: true,
					validType: ['hexOrStar','maxLength[64]']
				});
				$("#commentInpt").validatebox({
					novalidate: true,
					validType: 'cnMaxLength[254]'
				});
			});
		</script>
	</head>
	
	<body>
		<@addDiv label="限额规则配置信息" addUrl="/riskThreshold/add/submit.do">
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">规则名称：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="254"
							id="ruleNmInpt" name="ruleNm" />
					</td>
				</tr>
				<tr>
					<td class="label">规则类型：</td>
					<td class="content">
						<select id="ruleTpSel" name="ruleTp" data-options="editable:false">
							<@enumOpts enumNm='RiskEnums.ThresholdRuleTp' showCode='true' showEmptyOpt='false' />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">风险类型：</td>
					<td class="content">
						<select id="riskTpSel" name="riskTp" data-options="editable:false">
							<@enumOpts enumNm='RiskEnums.RiskTp' showCode='true' showEmptyOpt='false' />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">统计周期(小时)：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="9"
							id="periodInpt" name="period" />
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
							id="threholdInpt" name="threholdStr" /> (<span id="threholdUnit">元</span>)
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
							<@enumOpts enumNm='RiskEnums.Result' showCode='true' showEmptyOpt='false' />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">过期时间：</td>
					<td class="content">
						<input id="expiredTmStrInpt" name="expiredTmStr" type="text" value=""
							onFocus="WdatePicker({dateFmt:'yyyyMMdd HH:mm:ss'})" class="Wdate" style="width: 150px;" />
					</td>
				</tr>
				<tr>
					<td class="content" colspan="3">
						<br />
						<@inptInfo>
							监控项录入说明：<br/>
							1. 监控项卡号、商户代码、IP地址、终端序号不能都为空<br/>
							2. 如果某监控项不填写，则表示该项不进行监控<br/>
							3. 如果某监控项填写*，则表示该规则适用该监控项的每一条元素，例子：如果商户号输入为*，则表示该规则对每个商户号都适用<br/>
						</@inptInfo>
					</td>
				</tr>
				<tr>
					<td class="label">监控项-卡号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="19"
							id="priAcctNoInpt" name="priAcctNo" />
					</td>
				</tr>
				<tr>
					<td class="label">监控项-交易类型：</td>
					<td class="content">
						<select id="intTransCdSel" name="intTransCd" data-options="editable:false">
							<@enumOpts enumNm='RiskEnums.ThresholdTxnTp' showCode='true' showEmptyOpt='false' />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">监控项-商户代码：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="15"
							id="mchntCdInpt" name="mchntCd" />
					</td>
				</tr>
				<tr>
					<td class="label">监控项-IP地址：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="32"
							id="ipInpt" name="ip" />
					</td>
				</tr>
				<tr>
					<td class="label">监控项-终端序号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="64"
							id="termSnInpt" name="termSn" />
					</td>
				</tr>
				<tr>
					<td class="label">备注：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="254"
							id="commentInpt" name="comment" />
					</td>
				</tr>
			</table>
		</@addDiv>	
	</body>
</html>