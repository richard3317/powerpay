<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title></title>
		<#include "/common/include.ftl">
		<script type="text/javascript">
			$(function() {
				$("#backBtn").click(function() {
					$.jumpTo(_ctx + "/mchntAccout/accountFlow.do?mchntCd=" + $("#mchntCdHid").val());
				});
				$("#submitBtn").click(function() {
					$("#flowNoteEditForm").form("enableValidation").submit();
				});
				var dealFlg = false;
				$("#flowNoteEditForm").form({
					url: $("#flowNoteEditForm").attr("action"),
					onSubmit: function() {
						var r = $(this).form("validate");
						if (r) {
							dealFlg = true;
							$.messager.progress({
								title: '流水备注修改',
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
						$.jumpTo(_ctx + "/mchntAccout/accountFlow.do?mchntCd=" + $("#mchntCdHid").val());
						
					}
				});
				<#--
				$("noteInpt").validatebox({
					novalidate: true,
					delay: 1000,
					required: true,
					validType: ['amount']
				});
				-->
				
			});
		</script>
	</head>

	<body style="padding: 5px;">
		<form id="flowNoteEditForm" method="post" action="${ctx}/mchntAccout/flowNoteEdit.do">
			<input type="hidden" id="mchntCdHid" name="mchntCd" value="${entity.mchntCd}" />
			<input type="hidden" id="seqIdHid" name="seqId" value="${entity.seqId}" />
			<input type="hidden" id="recUpdTsHid" name="recUpdTs" value="${entity.recUpdTs}" />
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">备注:</td>
					<td class="content">
						<input id="noteInpt" name="note" type="text" value="${entity.note}" 
							class="easyui-validatebox" style="width: 195px;" />&nbsp;
					</td>
				</tr>
			</table>
			<div style="margin: 10px; padding-left: 150px;">
				<a id="submitBtn" href="javascript:void(0)" class="easyui-linkbutton">提交</a>
				<a id="backBtn" href="javascript:void(0)" class="easyui-linkbutton">返回</a>
			</div>
		</form>
	</body>
</html>