<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title></title>
		<#include "/common/include.ftl">
		<script type="text/javascript">
			$(function() {
				$("#backBtn").click(function() {
					$.jumpTo(_ctx + "/mchntAccout/backToManage.do");
				});
				$("#submitBtn").click(function() {
					$("#acctAdjustForm").form("enableValidation").submit();
				});
				var dealFlg = false;
				$("#acctAdjustForm").form({
					url: $("#acctAdjustForm").attr("action"),
					onSubmit: function() {
						var r = $(this).form("validate");
						if (r) {
							dealFlg = true;
							var s = $("#opTpSel").val();
							var t = $("#opTpSel option[value='" + s + "']").html()
							$.messager.progress({
								title: '商户账户调整',
								msg: t + '正在执行...'
							});
						}
						return r;
					},
					success: function(data) {
						$.processAjaxResp(data, function() {
							alert("处理成功");
						}, function(r) {
							alert("处理失败:" + r.respMsg);
						}, function(r) {
							alert("处理失败:" + r.respMsg);
						});
						dealFlg = false;
						$.messager.progress('close');
						$.jumpTo(_ctx + "/mchntAccout/accountAdjust.do?mchntCd=" + $("#mchntCdHid").val() + "&currCd=" + $("#currCdHid").val() );
						
					}
				});
				$("#txnAmtInpt").validatebox({
					novalidate: true,
					delay: 1000,
					required: true,
					validType: ['amount']
				});
				$("#noteInpt").validatebox({
					novalidate: true,
					required: true,
					delay: 1000
				});
				$("#opTpSel").validatebox({
					novalidate: true,
					required: true,
					delay: 1000
				});
			});
		</script>
	</head>

	<body style="padding: 5px;">
		<form id="acctAdjustForm" method="post" action="${ctx}/mchntAccout/accountAdjustSubmit.do">
			<input type="hidden" id="mchntCdHid" name="mchntCd" value="${entity.mchntCd}" />
			<input type="hidden" id="currCdHid" name="currCd" value="${entity.currCd}" />
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">商户号:</td>
					<td class="content">
						${entity.mchntCd}
					</td>
				</tr>
				<tr>
					<td class="label">商户名称:</td>
					<td class="content">
						${entity.mchntDesc}
					</td>
				</tr>
				<tr>
					<td class="label">币别:</td>
					<td class="content">
						${entity.currCdDesc}
					</td>
				</tr>
				<tr>
					<td class="label">可代付金额B0:</td>
					<td class="content">
						${entity.availableBalanceDesc} (元)
					</td>
				</tr>
				<tr>
					<td class="label">冻结金额F0:</td>
					<td class="content">
						${entity.frozenBalanceDesc} (元)
					</td>
				</tr>
				<tr>
					<td class="label">T1账户金额B1:</td>
					<td class="content">
						${entity.frozenT1BalanceDesc} (元)
					</td>
				</tr>
				
				<tr>
					<td class="label">操作类型:</td>
					<td class="content">
						<select id="opTpSel" name="opTp" class="easyui-validatebox" style="width: 200px">
							<option value="">--请选择--</option>
							<#list accOpTp?keys as k>
								<option value="${k}">${k}-${accOpTp[k]}</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">金额:</td>
					<td class="content">
						<input id="txnAmtInpt" name="txnAmt" type="text" value="" 
							class="easyui-validatebox" style="width: 195px;" />&nbsp;(元)
					</td>
				</tr>
				<tr>
					<td class="label">调帐原因:</td>
					<td class="content">
						<input id="noteInpt" name="note" type="text" value="" maxLength="100"
							class="easyui-validatebox" style="width: 900px;" />
					</td>
				</tr>
			</table>
			<div style="margin: 10px; padding-left: 150px;">
				<@authCheck funcCode="1000070008">
					<a id="submitBtn" href="javascript:void(0)" class="easyui-linkbutton">提交</a>
				</@authCheck>
				<a id="backBtn" href="javascript:void(0)" class="easyui-linkbutton">返回</a>
			</div>
		</form>
	</body>
</html>