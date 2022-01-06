<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="对账结果" base="acctChk" qryFlg=false qryFuncCode="8000100001"
			detailFlg=true detailUrl='"detail.do?seqId=" + sel.seqId' detailDivHeight=550 detailFuncCode="8000100002">
			<@authCheck funcCode='8000100003'>
				$("#cmtBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						$("#cmtDiv").dialog({
							title: "备注",
							width: 400,
							height: 200,
							top: 10,
							closed: false,
							cache: false,
							modal: true,
							inline: false,
							buttons:[{
								text:"保存",
								handler:function() {
									var cmt = $.trim($("#commentInpt").val());
									var cmtUrl = "${ctx}/acctChk/cmt.do";
									$.post(cmtUrl, 
										{
											seqId : sel.seqId,
											comments : cmt
										}, 
										function(data) {
											$.processAjaxResp(data, function() {
												$("#cmtDiv").dialog("close");
												sel.comments=cmt;
												var idx = $("#qryResultTbl").datagrid("getRowIndex", sel);
												$("#qryResultTbl").datagrid("refreshRow", idx);
											});
									});
								}
							},{
								text:"取消",
								handler:function() {
									$("#commentInpt").val("").hide();
									$("#cmtDiv").dialog("close");
								}
							}]
						});
						$("#commentInpt").val(sel.comments).show();
					}
				});
			</@authCheck>
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/acctChk/qry.do" qryFuncCode="8000100001">
			<table class="qry_tbl">
				<tr>
					<td>对账日期：</td>
					<td>
						<input id="checkDt" name="_QRY_checkDt" type="text" value=""
							onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate" />
					</td>
					<td>交易渠道：</td>
					<td>
						<select id="transChnlSel" name="_QRY_transChnl">
							<@enumOpts enumNm="TxnEnums.ChnlId" showCode="true" showEmptyOpt="true" />
						</select>
					</td>
					<td>对账结果：</td>
					<td>
						<select id="checkResultSel" name="_QRY_checkResult">
							<@enumOpts enumNm="CommonEnums.AcctChkResult" showCode="true" showEmptyOpt="true" />
						</select>
					</td>
				</tr>
				<tr>
					<td>商户号：</td>
					<td>
						<input id="mchntCd" name="_QRY_mchntCd" type="text" value="" maxLength="15" />
					</td>
					<td>订单号：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="orderId" maxLength="120"
							 name="_QRY_orderId" value="">
					</td>
					<td>交易日期：</td>
					<td>
						<input id="extTransDt" name="_QRY_extTransDt" type="text" value=""
							onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate" />
					</td>
				</tr>
				<tr>
					<td>交易流水号：</td>
					<td>
						<input id="transSeqIdInpt" name="_QRY_transSeqId" type="text" value="" />
					</td>
					<td>交易类型：</td>
					<td>
						<select name="_QRY_intTransCd">
							<@enumOpts enumNm="TxnEnums.TxnType" showCode="true" showEmptyOpt="true" />
						</select>
					</td>
					<td>终端号：</td>
					<td>
						<input id="termSnInpt" name="_QRY_termSn" type="text" value="" />
					</td>
				</tr>
				<tr>
					<td>备注：</td>
					<td colspan="5">
						<input id="commentsInpt" name="_QRY_comments" type="text" value="" />
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv detailFlg=true detailFuncCode="8000100002">
			<@authCheck funcCode='8000100003'>
				<a id="cmtBtn" href="javascript:void(0)" class="easyui-linkbutton">备注</a>
			</@authCheck>
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
		<@authCheck funcCode='8000100003'>
			<div id="cmtDiv">
				<textarea id="commentInpt" style="display:none; width: 360px; margin-left: 10px; margin-top: 5px; height: 110px;"></textarea>
			</div>
		</@authCheck>
	</body>
<html>