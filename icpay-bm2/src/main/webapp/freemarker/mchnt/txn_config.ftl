<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>商户交易权限配置页面</title>
		<#include "/common/include.ftl">
		<script type="text/javascript">
			$(function() {
				$("#saveBtn").click(function() {
					if (confirm("确认保存?")) {
						$("#txnConfigForm").form("enableValidation").submit();
					}
				});
				$("#txnConfigForm").form({
					url: $("#txnConfigForm").attr("action"),
					onSubmit: function() {
						return $(this).form("validate");
					},
					success: function(data) {
						$.processAjaxResp(data, function() {
							alert("商户交易权限配置任务已提交，请等待审核");
							$.jumpTo("${ctx}/mchnt/backToManage.do");
						});
					}
				});
				$("#backBtn").click(function() {
					$.jumpTo("${ctx}/mchnt/backToManage.do");
				});
			});
		</script>
	</head>
	
	<body>
		<div id="txnConfigDiv" class="easyui-panel" title="商户交易权限配置" style="padding: 10px;">
			<form id="txnConfigForm" method="post" action="${ctx}/mchnt/txnConfig/submit.do">
				<input type="hidden" name="mchntCd" value="${mchnt.mchntCd}" />
				<table class="edit_tbl" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label">商户信息：</td>
						<td class="content">
							${mchnt.mchntCd} - ${mchnt.mchntCnNm}
						</td>
					</tr>
					<tr>
						<td class="label">交易权限：</td>
						<td class="content">
							<@enumCheckBox enumNm="TxnEnums.TxnType" name="txnTypes" checkVals="${(mchnt.transType)!''}" />
						</td>
					</tr>
				</table>
			</form>
			
			<div style="margin: 10px; padding-left: 150px;">
				<a id="saveBtn" href="javascript:void(0)" class="easyui-linkbutton">保存</a>
				<a id="backBtn" href="javascript:void(0)" class="easyui-linkbutton">返回</a> 
			</div>
		</div>
	</body>
</html>