<#include "/common/manage_macro.ftl" />
<#assign ctx = request.contextPath>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="终端信息" base="terminal" qryFuncCode="1100010001">
			$("#exportBtn").click(function() {
				$("#exportKeyInpt").val("");
				$("#exportDiv").dialog("open");
			});
			$("#exportConfirmBtn").click(function() {
				var exportKey1 = $("#exportKeyInpt1").val();
				var exportKey2 = $("#exportKeyInpt2").val();
				var hexReg = /^[0-9a-fA-F]{16}$/;
				if (!hexReg.test(exportKey1)) {
					alert("传输密钥1格式错误，请输入16位十六进制字符串");
					$("#exportKeyInpt1").focus();
					return;
				}
				if (!hexReg.test(exportKey2)) {
					alert("传输密钥2格式错误，请输入16位十六进制字符串");
					$("#exportKeyInpt2").focus();
					return;
				}
				if (confirm("确认导出?")) {
					$("#exptTermMn").val($("#qryTermMn").val());
					$("#exptTermBn").val($("#qryTermBn").val());
					$("#exptState").val($("#qryState").val());
					$("#exptImportDt").val($("#qryImportDt").val());
					$("#exptBatNo").val($("#qryBatNo").val());
					$("#exportFrm").submit();
					$("#exportDiv").dialog("close");
					$.messager.show({
						title: '处理中', 
						msg: '正在导出，请稍候...',
						showType:'fade',
						style: {
							top: 100							
						}
					});
				}
			});
			$("#exportDiv").dialog({
			    title: '导出终端信息',
			    top: 100,
			    width: 450,
			    height: 200,
			    closed: true,
			    cache: false,
			    modal: true
			});
		</@head>
	</head>

	<body style="padding: 5px;">
		<@qryDiv qryUrl="/terminal/qry.do" qryFuncCode="1100010001">
			<table class="qry_tbl">
				<tr>
					<td>导入文件批次号：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="qryBatNo" style="width: 220px;" 
							name="_QRY_batNo" value="">
					</td>
					<td>终端序列号：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="qryTermSn" style="width: 220px;" 
							name="_QRY_termSn" value="">
					</td>
				</tr>
				<tr>
					<td>终端型号：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="qryTermMn" style="width: 220px;" 
							name="_QRY_termMn" value="">
					</td>
					<td>终端批次号：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="qryTermBn" style="width: 220px;" 
							name="_QRY_termBn" value="">
					</td>
				</tr>
				<tr>
					<td>状态：</td>
					<td>
						<select id="qryState" name="_QRY_state">
							<@enumOpts enumNm='CommonEnums.RecSt' selected='1' showEmptyOpt='true' />
						</select>
					</td>
					<td>导入日期</td>
					<td colspan="5">
						<input id="qryImportDt" name="_QRY_importDt" type="text" value=""
							onFocus="WdatePicker({dateFmt:'yyyyMMdd', readOnly: true})" class="Wdate" />
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv>
			<@authCheck funcCode="1100010002">
				<a id="exportBtn" href="javascript:void(0)" class="easyui-linkbutton">导出查询结果</a>
			</@authCheck>
		</@qryResultDiv>
		
		<div id="exportDiv" style="padding: 12px 5px 0 5px;">
			<form id="exportFrm" action="${ctx}/terminal/export.do" method="POST">
				<input type="hidden" id="exptTermMn" name="_QRY_termMn" value="" />
				<input type="hidden" id="exptTermBn" name="_QRY_termBn" value="" />
				<input type="hidden" id="exptState" name="_QRY_state" value="" />
				<input type="hidden" id="exptImportDt" name="_QRY_importDt" value="" />
				<input type="hidden" id="exptBatNo" name="_QRY_batNo" value="" />
				<div>
				传输密钥1:
				<input class="easyui-validatebox" type="text" id="exportKeyInpt1" name="exportKey1" value="" 
					style="width: 220px; font-size: 12px; height: 25px; line-height: 25px;" maxLength="16" />
				</div>
				<div style="margin: 20px 0;">
				传输密钥2:
				<input class="easyui-validatebox" type="text" id="exportKeyInpt2" name="exportKey2" value="" 
					style="width: 220px; font-size: 12px; height: 25px; line-height: 25px;" maxLength="16" />
				</div>
				<a id="exportConfirmBtn" href="javascript:void(0)" class="easyui-linkbutton">导出</a>
			</form>
		</div>
	</body>
</html>