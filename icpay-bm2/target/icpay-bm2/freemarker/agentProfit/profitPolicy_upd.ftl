<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>代理商分润信息-代理商分润策略修改</title>
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
					$("#updForm").form("enableValidation").submit();
				});
				$("#updForm").form({
					url: $("#updForm").attr("action"),
					onSubmit: function() {
						var r = $(this).form("validate");
						if (r) {
							dealFlg = true;
						}
						return r;
					},
					success: function(data) {
						$.processAjaxResp(data, function() {
							alert("代理商分润策略修改成功");
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
		<div id="updDiv" class="easyui-panel" title="代理商分润信息-代理商分润策略修改" style="padding: 10px;">
			<form id="updForm" method="post" action="${ctx}/agentProfit/profitPolicy/upd/submit.do">
				<input type="hidden" id="agentCdHid" name="agentCd" value="${entity.agentCd}" />
				<input type="hidden" id="intTransCdHid" name="intTransCd" value="${entity.intTransCd}" />
				<input type="hidden" id="mchntCdHid" name="mchntCd" value="${entity.mchntCd}" />
				<input type="hidden" id="chnlIdHid" name="chnlId" value="${entity.chnlId}" />
				<input type="hidden" id="chnlMchntCdHid" name="chnlMchntCd" value="${entity.chnlMchntCd}" />
				<input type="hidden" id="tradeTypeHid" name="tradeType" value="${entity.tradeType}" />
				<table class="edit_tbl" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label" style="width: 135px;">代理商：</td>
						<td class="content">
							${entity.agentCd}
						</td>
					</tr>
					<tr>
						<td class="label" style="width: 135px;">交易类型：</td>
						<td class="content">
							<@enumVal enumNm="ProfitEnums.ProfitTxnTp" code="${entity.intTransCd}" showCode="true" />
						</td>
					</tr>
					<tr>
						<td class="label" style="width: 135px;">商户编号：</td>
						<td class="content">
							${entity.mchntCd}
						</td>
					</tr>
					<tr>
						<td class="label" style="width: 135px;">渠道编号：</td>
						<td class="content">
							${entity.chnlId}
						</td>
					</tr>
					<tr>
						<td class="label" style="width: 135px;">渠道商编：</td>
						<td class="content">
							${entity.chnlMchntCd}
						</td>
					</tr>
					
					<tr>
						<td class="label" style="width: 135px;">扣率：</td>
						<td class="content">
							<input class="easyui-validatebox" type="text" id="rateInpt" 
								name="rate" value="${entity.rate}" />
						</td>
					</tr>
					
					<#--  
					<tr>
						<td class="label" style="width: 135px;">行业类别：</td>
						<td class="content">
							<@dataDicVal dataTp='TRADE_TYPE' dataKey='${entity.tradeType}' includeKey='true'  />
						</td>
					</tr>
					-->
					<tr>
						<td class="label" style="width: 135px;">封顶手续费：</td>
						<td class="content">
							<input class="easyui-validatebox" type="text" id="maxFeeInpt" 
								name="maxFee" value="${entity.maxFee}" />(元)
						</td>
					</tr>
					<tr>
						<td class="label" style="width: 135px;">保底手续费：</td>
						<td class="content">
							<input class="easyui-validatebox" type="text" id="minFeeInpt" 
								name="minFee" value="${entity.minFee}" />(元)
						</td>
					</tr>
					<tr>
						<td class="label" style="width: 135px;">备注：</td>
						<td class="content">
							<input class="easyui-validatebox" type="text" maxlength="256" id="commentInpt" 
								name="comment" value="${entity.comment}" />
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