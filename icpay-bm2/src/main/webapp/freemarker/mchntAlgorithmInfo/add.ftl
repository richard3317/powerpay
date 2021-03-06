<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>商户计费方式新增</title>
		<#include "/common/include.ftl">
		<script type="text/javascript">
			var dealFlg = false;
			$(function() {
				$("#backBtn").click(function() {
					$.jumpTo(_ctx + "/algorithm/manage.do?mchntCd=" + $("#mchntCdHid").val());
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
							alert("新增计费方式成功");
							$.jumpTo(_ctx + "/algorithm/manage.do?mchntCd=" + $("#mchntCdHid").val());
						});
						dealFlg = false;
					}
				});
				
				<#--
				$("#fixRateInpt").validatebox({
					required: true,
					novalidate: true,
					delay: 1000,
					validType: ['rate','maxLength[8]']
				});
				$("#fixFeeInpt").validatebox({
					required: true,
					novalidate: true,
					delay: 1000,
					validType: ['fee']
				});
				$("input[name='minFee']").validatebox({
					novalidate: true,
					delay: 1000,
					validType: ['fee']
				});
				$("input[name='maxFee']").validatebox({
					novalidate: true,
					delay: 1000,
					validType: ['fee']
				});-->
			});
		</script>
	</head>

	<body style="padding: 5px;">
		<div id="addDiv" class="easyui-panel" title="商户计费方式新增" style="padding: 10px;">
			<form id="addForm" method="post" action="${ctx}/algorithm/add/submit.do">
				<input type="hidden" id="settleModeHid" name="settleMode" value="2" />
				<table class="edit_tbl" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label">商户代码：</td>
						<td class="content">
							<input class="easyui-validatebox" type="text" maxlength="15"
								id="mchntCdInpt" name="mchntCd" />
							<span class="inputDesc">必填，请输入有效的二清商户代码</span>
						</td>
					</tr>
					<tr>
						<td class="label" style="width: 135px;">交易类型：</td>
						<td class="content">
							<select class="easyui-validatebox" name="currCd" id="currCd" required="true">
								<@enumOpts enumNm="CurrencyEnums.CurrType" showEmptyOpt="true" showCode="true" />
							</select>
							<select class="easyui-validatebox" name="intTransCd" id="intTransCdSel" required="true">
								<@enumOpts enumNm="ProfitEnums.ProfitTxnTp" showEmptyOpt="true" showCode="true" />
							</select>
						</td>
					</tr>
					<tr>
						<td class="label" style="width: 135px;">计费模式：</td>
						<td class="content">
							<@enumVal enumNm="SettleEnums.SettleMode" code="2" />
						</td>
					</tr>
					<tr>
						<td class="label" style="width: 135px;">备注：</td>
						<td class="content">
							<input class="easyui-validatebox" type="text" maxlength="256"
								id="commentInpt" name="comment" />
						</td>
					</tr>
					<tr>
					<td class="label">计费方式：</td>
					<td class="content">
						<table class="edit_tbl" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label">扣率：</td>
								<td class="content">
									<input class="easyui-validatebox" type="text" maxlength="7"
										id="fixRateInpt" name="fixRate" /><span class="inputDesc">请输入小数，小数点后最多五位，如：0.12345</span>
								</td>
							</tr>
							<tr>
								<td class="label">保底手续费(元)：</td>
								<td class="content">
									<input class="easyui-validatebox" type="text" maxlength="7"
										id="minFeeInpt" name="minFee" /><span class="inputDesc">请输入金额，如：1.00元</span>
								</td>
							</tr>
							<tr>
								<td class="label">封顶手续费(元)：</td>
								<td class="content">
									<input class="easyui-validatebox" type="text" maxlength="12"
										id="maxFeeInpt" name="maxFee" /><span class="inputDesc">请输入金额，如：1.00元，如果不填写，则默认为999999999999.99元</span>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<td class="label">交易日限额(元)：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="12"
							id="txDayMaxInpt" name="txDayMax" />
						<span class="inputDesc">如果不填写，则默认当日累计交易限额为999999999999.99元</span>
					</td>
				</tr>
				<tr>
					<td class="label">交易单笔下限(元)：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="12"
							id="txAmtMinInpt" name="txAmtMin" />
					</td>
				</tr>
				<tr>
					<td class="label">交易单笔上限(元)：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="12"
							id="txAmtMaxInpt" name="txAmtMax" />
						<span class="inputDesc">如果不填写，则默认为999999999999.99元</span>
					</td>
				</tr>
				<tr>
					<td class="label">交易支持的时段：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="20"
							id="txTimeLimitInpt" name="txTimeLimit" />
						<span class="inputDesc">例："ALL" 或 "0800-1200,1400-2000"</span>
					</td>
				</tr>
				<tr>
					<td class="label">单日单卡限额(元)：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="12"
							id="txCardDayMaxIput" name="txCardDayMax" />
						<span class="inputDesc">如果不填写，则默认当日累计交易限额为999999999999.99元</span>
					</td>
				</tr>
				<tr>
					<td class="label">代付垫资比例：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="20"
							id="txT0PercentIput" name="txT0Percent" />
						<span class="inputDesc">如：0.05，最大值为1</span>
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