<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>商户计费方式修改</title>
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
							alert("计费方式修改成功");
							$.jumpTo(_ctx + "/algorithm/manage.do?mchntCd=" + $("#mchntCdHid").val());
						});
						dealFlg = false;
					}
				});
				var status=$("#whiteIpStateHid").val();
				var options=document.getElementById('transTypeGroupIdSel').children;
				if(status=="list"){
					options[0].selected ="true";
				}else {
					options[1].selected ="true";
				}
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
				});
			});
		</script>
	</head>

	<body style="padding: 5px;">
		<div id="updDiv" class="easyui-panel" title="商户计费方式修改-${algorithm.mchntCd}" style="padding: 10px;">
			<form id="updForm" method="post" action="${ctx}/algorithm/edit/submit.do">
				<input type="hidden" id="mchntCdHid" name="mchntCd" value="${algorithm.mchntCd}" />
				<input type="hidden" id="intTransCdHid" name="intTransCd" value="${algorithm.intTransCd}" />
				<input type="hidden" id="settleModeHid" name="settleMode" value="2" />
				<input type="hidden" id="currCdHid" name="currCd" value="${algorithm.currCd}" />
				<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
						<td class="label" style="width: 135px;">币别：</td>
						<td class="content">
						<@enumVal enumNm="BmEnums.CurrCdType" code="${algorithm.currCd}" />
						</td>
					</tr>
					<tr>
						<td class="label" style="width: 135px;">交易类型：</td>
						<td class="content">
							<@enumVal enumNm="SettleEnums.SettleTxnType" code="${algorithm.intTransCd}" />
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
								id="commentInpt" name="comment" value="${algorithm.comment}" />
						</td>
					</tr>
					<tr>
						<td class="label" style="width: 135px;">计费方式：</td>
						<td class="content" id="settleModeTr">
							<div id="settleMode2">
								<ul class="input_ele">
									<li>
										<span class="input_lbl">费率：</span>
										<input class="easyui-validatebox" type="text" maxlength="7" value="${algorithm.fixRate}"
											id="fixRateInpt" name="fixRate" style="width: 100px;" />
										<span class="inputDesc">必填，请输入小数，小数点后最多5位,如:0.12345</span>
									</li>
									<li>
										<span class="input_lbl">保底手续费：</span>
										<input class="easyui-validatebox" type="text" maxlength="7" value="${(algorithm.minFee)!''}"
											id="minFeeInpt" name="minFee" style="width: 100px;" />(元)
										<span class="inputDesc">选填，请输入金额，如:1.00</span>
									</li>
									<li>
										<span class="input_lbl">封顶手续费：</span>
										<input class="easyui-validatebox" type="text" maxlength="12" value="${(algorithm.maxFee)!''}"
											id="maxFeeInpt" name="maxFee" style="width: 100px;" />(元)
										<span class="inputDesc">选填，请输入金额，如:1.00，如果不填写，则默认为999999999999.99元</span>
									</li>
								</ul>
							</div>
						</td>
					</tr>
				</tr>
			<tr>
					<td class="label">交易日限额(元)：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="12"
							id="txDayMaxInpt" name="txDayMax" value="${algorithm.txDayMax!}" />
						<span class="inputDesc">如果不填写，则默认当日累计交易限额为999999999999.99元(支付交易)</span>
					</td>
				</tr>
				<tr>
					<td class="label">交易单笔下限(元)：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="12"
							id="txAmtMinInpt" name="txAmtMin" value="${algorithm.txAmtMin!}" />
					</td>
				</tr>
				<tr>
					<td class="label">交易单笔上限(元)：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="12"
							id="txAmtMaxInpt" name="txAmtMax" value="${algorithm.txAmtMax!}"/>
						<span class="inputDesc">如果不填写，则默认为999999999999.99元</span>
					</td>
				</tr>
				<tr>
					<td class="label">交易支持的时段：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="20"
							id="txTimeLimitInpt" name="txTimeLimit" value="${algorithm.txTimeLimit!}" />
						<span class="inputDesc">例："ALL" 或 "0800-1200,1400-2000"</span>
					</td>
				</tr>
				<tr>
					<td class="label">单日单卡限额(元)：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="12"
							id="txCardDayMaxIput" name="txCardDayMax" value="${algorithm.txCardDayMax!}"/>
						<span class="inputDesc">如果不填写，则默认当日累计交易限额为999999999999.99元</span>
					</td>
				</tr>
				<tr>
					<td class="label">代付垫资比例：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="20"
							id="txT0PercentIput" name="txT0Percent" value="${algorithm.txT0Percent!}"/>
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