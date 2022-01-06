<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>代理商分润信息-代理商分润策略新增</title>
		<#include "/common/include.ftl">
		<script type="text/javascript">
			var dealFlg = false;
			$(function() {
				$("#backBtn").click(function() {
					$.jumpTo(_ctx + "/agentProfit/profitPolicy/manage.do?agentCd=" + $("#agentCdHid").val());
				});
				$("#saveBtn").click(function() {
					if (dealFlg) {
						alert("正在处理中，请稍后...");
						return;
					}
					$("#addForm").form("enableValidation").submit();
				});
				$("#addForm").form({
					url: $("#addForm").attr("action"),
					onSubmit: function() {
						var r = $(this).form("validate");
						if (r) {
							dealFlg = true;
						}
						return r;
					},
					success: function(data) {
						$.processAjaxResp(data, function() {
							alert("新增代理商分润策略成功");
							$.jumpTo(_ctx + "/agentProfit/profitPolicy/manage.do?agentCd=" + $("#agentCdHid").val());
						});
						dealFlg = false;
					}
				});
				
				$("#rateInpt").validatebox({
					novalidate: true,
					required: true,
					delay: 1000,
					validType: ['rate','maxLength[8]']
				});
				$("#maxFeeInpt, #minFeeInpt").validatebox({
					novalidate: true,
					delay: 1000,
					validType: ['fee']
				});
			});
		</script>
	</head>

	<body style="padding: 5px;">
		<div id="addDiv" class="easyui-panel" title="代理商分润信息-新增代理商分润策略" style="padding: 10px;">
			<form id="addForm" method="post" action="${ctx}/agentProfit/profitPolicy/add/submit.do">
				<input type="hidden" id="agentCdHid" name="agentCd" value="${agent.agentCd}" />
				<table class="edit_tbl" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label" style="width: 135px;">代理商：</td>
						<td class="content">
							${agent.agentCd}
						</td>
					</tr>
					<#--<tr>
						<td class="label">行业类别：</td>
						<td class="content">
							<select class="easyui-validatebox" id="tradeTypeSel" name="tradeType" required="true">
								<@dataDicOpts dataTp='TRADE_TYPE' showEmptyOpt='true' showKey='true' />
							</select>
						</td>
					</tr>-->
					<tr>
						<td class="label" style="width: 135px;">交易类型：</td>
						<td class="content">
							<select class="easyui-validatebox" name="intTransCd" id="intTransCdSel" required="true">
								<@enumOpts enumNm="ProfitEnums.ProfitTxnTp" showEmptyOpt="true" showCode="true" />
							</select>
						</td>
					</tr>
					
					<tr>
						<td class="label" style="width: 135px;">商户编号：</td>
						<td class="content">
							<input class="easyui-validatebox" type="text" id="mchntCdInpt" name="mchntCd" />(输入*表示全部)
						</td>
					</tr>
					<tr>
						<td class="label" style="width: 135px;">渠道编号：</td>
						<td class="content">
							<select class="easyui-validatebox" name="chnlId" id="chnlIdSel" required="true">
								<option value=''>(请选择)</option>
								<option value='*'>***全部***</option>
								<@enumOpts enumNm='TxnEnums.ChnlId' showEmptyOpt='false' showCode="true" />
							</select>
						</td>
					</tr>
					<tr>
						<td class="label" style="width: 135px;">渠道商编：</td>
						<td class="content">
							<input class="easyui-validatebox" type="text" id="chnlMchntCdInpt" name="chnlMchntCd" />(输入*表示全部)
						</td>
					</tr>
					
					<tr>
						<td class="label" style="width: 135px;">扣率：</td>
						<td class="content">
							<input class="easyui-validatebox" type="text" id="rateInpt" name="rate" />(小数点，例如: 0.0035)
						</td>
					</tr>
					<#--  
					<tr>
						<td class="label" style="width: 135px;">封顶手续费：</td>
						<td class="content">
							<input class="easyui-validatebox" type="text" id="maxFeeInpt" name="maxFee" />(元)
						</td>
					</tr>
					<tr>
						<td class="label" style="width: 135px;">保底手续费：</td>
						<td class="content">
							<input class="easyui-validatebox" type="text" id="minFeeInpt" name="minFee" />(元)
						</td>
					</tr>
					-->
					<tr>
						<td class="label" style="width: 135px;">备注：</td>
						<td class="content">
							<input class="easyui-validatebox" type="text" maxlength="256"
								id="commentInpt" name="comment" />
						</td>
					</tr>
				</table>
				
				<div id="opBtns" style="margin: 10px 0;">
					<a id="saveBtn" href="javascript:void(0)" class="easyui-linkbutton">保存</a>
					<a id="backBtn" href="javascript:void(0)" class="easyui-linkbutton">返回</a>
				</div>
			</form>
		</div>
	</body>
</html>