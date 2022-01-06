<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="对账结果" base="acctChk" qryFuncCode="8000090002">
			<@authCheck funcCode='8000090002'>
				$("#downBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						if ($.trim(sel.filePath) == '') {
							alert("无退款文件");
							return;
						}
						$.jumpTo("${ctx}/acctChkTotal/down.do?seqId=" + sel.seqId);
					}
				});
			</@authCheck>
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/acctChkTotal/qry.do" qryFuncCode="8000090001">
			<table class="qry_tbl">
				<tr>
					<td>对账日期：</td>
					<td>
						<input id="checkDt" name="_QRY_checkDt" type="text" value=""
							onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate" />
					</td>
					<td>交易渠道：</td>
					<td>
						<select id="transChnlSel" name="_QRY_chnlId">
							<@enumOpts enumNm="TxnEnums.ChnlId" showCode="true" showEmptyOpt="true" />
						</select>
					</td>
				</tr>
				<tr>
					<td>对账结果：</td>
					<td>
						<select id="checkResultSel" name="_QRY_checkResult">
							<@enumOpts enumNm="SettleEnums.AcctResultTotalState" showCode="true" showEmptyOpt="true" />
						</select>
					</td>
					<td>渠道商户号：</td>
					<td>
						<input id="mchntCd" name="_QRY_mchntCd" type="text" value="" maxLength="15" />
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<div>
			<@authCheck funcCode="8000090002">
				<a id="downBtn" href="javascript:void(0)" class="easyui-linkbutton">下载退款文件</a>
			</@authCheck>
		</div>
		
		<@qryResultDiv>
		</@qryResultDiv>
	</body>
<html>