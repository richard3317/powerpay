<#include "/common/edit_macro.ftl" />
<#assign label = "提现审核" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="withdrawAudit" >
			$("#commentInpt").validatebox({
				required: requireComment(),
				<#-- novalidate: true, -->
				validType: ['maxLength[254]']
			});
			
			function requireComment(){
				return ("20"===$("#apprResultSel").val());
			}
		</@head>
	</head>
	
	<body>
		<@editDiv label="${label}" editUrl="/withdrawAudit/appr/submit.do">
			<input type="hidden" name="mon" value="${entity.extTransDt}" />
			<input type="hidden" name="intTransCd" value="${entity.intTransCd}" />
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">商户号：</td>
					<td class="content" >
						<input class="easyui-validatebox" type="text" maxlength="40" readonly="readonly"
								id="mchntCdInpt" name="mchntCd" value="${entity.mchntCd}" />
					</td>
				</tr>
				<tr>
					<td class="label">商户名称：</td>
					<td class="content" >
						<input class="easyui-validatebox" type="text" maxlength="40" readonly="readonly"
								id="mchntCnAbbrInpt" name="mchntCnAbbr" value="${entity.mchntCnAbbr}" />
					</td>
				</tr>
				<tr>
					<td class="label">交易类型：</td>
					<td class="content" >
						<input class="easyui-validatebox" type="text" maxlength="40" readonly="readonly"
								id="intTransCdDescInp" name="intTransCdDesc" value="${entity.intTransCdDesc}"  />
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
					<td class="label">打款日期：</td>
					<td class="content" >
						<input class="easyui-validatebox" type="text" maxlength="40" readonly="readonly"
								id="extActuaTxnDtInpt" name="extActuaTxnDt" value="${entity.extActuaTxnDt}" />
					</td>
				</tr>
				<tr>
					<td class="label">打款時間：</td>
					<td class="content" >
						<input class="easyui-validatebox" type="text" maxlength="40" readonly="readonly"
								id="extActuaTxnTmInpt" name="extActuaTxnDt" value="${entity.extActuaTxnTm}" />
					</td>
				</tr>
				<tr>
					<td class="label">打款金额：</td>
					<td class="content" >
						<input class="easyui-validatebox" type="text" maxlength="40" readonly="readonly"
								id="transAtInpt" name="transAmtDesc" value="${entity.transAmtDesc}" />
					</td>
				</tr>
				<tr>
					<td class="label">户名：</td>
					<td class="content" >
					<input class="easyui-validatebox" type="text" maxlength="40" readonly="readonly"
								id="accNameInpt" name="accName" value="${entity.accName}" />
					</td>
				</tr>
				<tr>
					<td class="label">卡号：</td>
					<td class="content" >
					<input class="easyui-validatebox" type="text" maxlength="40" readonly="readonly"
								id="accNoInpt" name="accNo" value="${entity.accNo}" />
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
					<td class="label">渠道商户名称：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40" readonly="readonly"
								id="chnlMchntDescInpt" name="chnlMchntDesc" value="${entity.chnlMchntDesc}" />
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
				<@authCheck funcCode='6001020006'>
					<tr>
						<td class="label">充值审核：</td>
						<td class="content">
							<select id="apprResultSel" name="apprResult" class="easyui-validatebox" required="true"  data-options="editable:false" >
								<@enumOpts enumNm='TxnEnums.CommonOkFailed' showCode='false' showEmptyOpt="true"/>
							</select>
						</td>
					</tr>				
					<tr>
						<td class="label">备注：</td>
						<td class="content">
							<input class="easyui-validatebox" type="text" maxlength="254"
								id="commentInpt" name="comment" value="" />
						</td>
					</tr>
				</@authCheck>
			</table>
		</@editDiv>
	</body>
</html>