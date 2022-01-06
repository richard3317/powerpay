<#include "/common/edit_macro.ftl" />
<#assign label = "交易流水信息" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="virtualTransLogQuxian" >
			$("#commentInpt").validatebox({
				required: true,
				novalidate: true,
				validType: ['maxLength[254]']
			});
		</@head>
	</head>
	
	<body>
		<@editDiv label="${label}" editUrl="/virtualTransLogQuxian/transSateModify/submit.do">
			<input type="hidden" name="mon" value="${entity.extTransDt}" />
			<input type="hidden" name="tags" value='${entity.tags}' />
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">商户代码：</td>
					<td class="content" >
						<input class="easyui-validatebox" type="text" maxlength="40" readonly="readonly"
								id="mchntCdInpt" name="mchntCd" value="${entity.mchntCd}" />
					</td>
				</tr>
				<tr>
					<td class="label">交易流水号：</td>
					<td class="content" >
						<input class="easyui-validatebox" type="text" maxlength="40" readonly="readonly"
								id="transSeqIdInpt" name="transSeqId" value="${entity.transSeqId}" />
					</td>
				</tr>
				<tr>
					<td class="label">订单号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40" readonly="readonly"
								id="orderIdInpt" name="orderId" value="${entity.orderId}" />
					</td>
				</tr>
				<tr>
					<td class="label">交易金额：</td>
					<td class="content" >
						<input class="easyui-validatebox" type="text" maxlength="40" readonly="readonly"
								id="transAtInpt" name="transAt" value="${entity.transAt}" />
					</td>
				</tr>
				<tr>
					<td class="label">渠道：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40" readonly="readonly"
								id="transChnlInpt" name="transChnl" value="${entity.transChnl}" />
					</td>
				</tr>
				<tr>
					<td class="label">渠道商户号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40" readonly="readonly"
								id="chnlMchntCdInpt" name="chnlMchntCd" value="${entity.chnlMchntCd}" />
					</td>
				</tr>
				
				<tr>
					<td class="label">平台订单号：</td>
					<td class="content" >
					<input class="easyui-validatebox" type="text" maxlength="40" readonly="readonly"
								id="chnlOrderIdInpt" name="chnlOrderId" value="${entity.chnlOrderId}" />
					</td>
				</tr>
				<tr>
					<td class="label">渠道流水号：</td>
					<td class="content" >
						<input class="easyui-validatebox" type="text" maxlength="40" readonly="readonly"
								id="chnlTransIdInpt" name="chnlTransId" value="${entity.chnlTransId}" />
					</td>
				</tr>
				<tr>
					<td class="label">响应码：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40" readonly="readonly"
							id="rspCdInpt" name="rspCd" value="${entity.rspCd}" />
					</td>
				</tr>
				<tr>
					<td class="label">错误代码：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40" readonly="readonly"
								id="errMsgInpt" name="errMsg" value="${entity.errMsg}" />
					</td>
				</tr>
				<tr>
					<td class="label">交易状态：</td>
					<td class="content">
						<select id="txnStateSel" name="txnState" data-options="editable:false" readonly="readonly" disabled="disabled" >
							<@enumOpts enumNm='TxnEnums.TxnStatus' showCode='true' 
								showEmptyOpt="false" selected="${entity.txnState}" />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">交易状态调整：</td>
					<td class="content">
						<select id="txnStateSel" name="opType" data-options="editable:false" style="width: 300px;">
							<@enumOpts enumNm='TxnEnums.TransLogWithdrawAdjustOp' showCode='true'
								showEmptyOpt="false" selected="16" />
						</select>
					</td>
				</tr>				
				<tr>
					<td class="label">备注：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="100"
							id="commentInpt" name="comment" value="" />
					</td>
				</tr>
			</table>
		</@editDiv>
	</body>
</html>