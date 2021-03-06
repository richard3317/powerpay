<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title></title>
		<#include "/common/include.ftl">
		<script type="text/javascript">
			$(function() {
				$("#backBtn").click(function() {
					$.jumpTo(_ctx + "/chnlAccount/backToManage.do");
				});
				$("#submitBtn").click(function() {
					$("#obligatedBalanceModifyForm").form("enableValidation").submit();
				});
				var dealFlg = false;
				$("#obligatedBalanceModifyForm").form({
					url: $("#obligatedBalanceModifyForm").attr("action"),
					onSubmit: function() {
						var r = $(this).form("validate");
						if (r) {
							dealFlg = true;
							$.messager.progress({
								title: '商户保留余额账户调整',
								msg: '正在执行...'
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
						$.jumpTo(_ctx + "/chnlAccount/backToManage.do");
						
					}
				});
				$("#obligatedBalanceInpt").validatebox({
					novalidate: true,
					delay: 1000,
					required: true,
					validType: ['amount']
				});
				
			});
		</script>
	</head>

	<body style="padding: 5px;">
		<form id="obligatedBalanceModifyForm" method="post" action="${ctx}/chnlAccount/obligatedBalanceModify.do">
			<input type="hidden" id="chnlIdHid" name="chnlId" value="${entity.chnlId}" /> 
			<input type="hidden" id="mchntCdHid" name="mchntCd" value="${entity.mchntCd}" />
			<input type="hidden" id="currCdHid" name="currCd" value="${entity.currCd}" />
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">渠道商户代码:</td>
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
					<td class="label">可代付金额:</td>
					<td class="content">
						${entity.availableBalanceDesc} (元)
					</td>
				</tr>
				<tr>
					<td class="label">保留余额:</td>
					<td class="content">
						<input id="obligatedBalanceInpt" name="obligatedBalance" type="text" value="${entity.obligatedBalanceDesc}" 
							class="easyui-validatebox" style="width: 195px;" />&nbsp;(元)
					</td>
				</tr>
				<tr>
					<td class="label">备注:</td>
					<td class="content">
						<input class="easyui-validatebox" type="text"
							id="noteInpt" name="note" value="" >
				</td>
			</table>
			<div style="margin: 10px; padding-left: 150px;">
				<a id="submitBtn" href="javascript:void(0)" class="easyui-linkbutton">提交</a>
				<a id="backBtn" href="javascript:void(0)" class="easyui-linkbutton">返回</a>
			</div>
		</form>
	</body>
</html>