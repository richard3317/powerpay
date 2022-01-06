<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>渠道虚拟终端信息-交易参数修改</title>
		<#include "/common/include.ftl">
		<script type="text/javascript">
			var dealFlg = false;
			$(function() {
				$("#backBtn").click(function() {
					$.jumpTo(_ctx + "/virtualTerm/transParam/manage.do?chnlId=" + $("#chnlIdHid").val()+"&chnlMchntCd=" + $("#chnlMchntCdHid").val());
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
							$.jumpTo(_ctx + "/virtualTerm/transParam/manage.do?chnlId=" + $("#chnlIdHid").val()+"&chnlMchntCd=" + $("#chnlMchntCdHid").val());
						});
						dealFlg = false;
					}
				});
				
				
			});
		</script>
	</head>

	<body style="padding: 5px;">
		<div id="updDiv" class="easyui-panel" title="渠道虚拟终端信息-交易参数修改-${entity.chnlId}-${entity.chnlMchntCd}" style="padding: 10px;">
			<form id="updForm" method="post" action="${ctx}/virtualTerm/transParam/upd/submit.do">
				<input type="hidden" id="chnlIdHid" name="chnlId" value="${entity.chnlId}" />
				<input type="hidden" id="intTransCdHid" name="intTransCd" value="${entity.intTransCd}" />
		    	<input type="hidden" id="chnlMchntCdHid" name="chnlMchntCd" value="${entity.chnlMchntCd}" />
				<input type="hidden" id="currCdHid" name="currCd" value="${entity.currCd}" />
		    	<input type="hidden" name="settleMode" value="2" />
				<table class="edit_tbl" cellpadding="0" cellspacing="0">
					<table class="edit_tbl" cellpadding="0" cellspacing="0">
					<tr>
					<td class="label">支付交易类型：</td>
					<td class="content">
						<@enumVal enumNm="SettleEnums.SettleTxnType" code="${entity.intTransCd}" />
						(<@enumVal enumNm="CurrencyEnums.CurrType" code="${entity.currCd}" />)
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
										id="fixRateInpt" name="fixRate" value="${entity.fixRate!}" /><span class="inputDesc">请输入小数，小数点后最多五位，如：0.12345</span>
								</td>
							</tr>
							<tr>
								<td class="label">保底手续费(元)：</td>
								<td class="content">
									<input class="easyui-validatebox" type="text" maxlength="7"
										id="minFeeInpt" name="minFee" value="${entity.minFee!}" /><span class="inputDesc">请输入金额，如：1.00元</span>
								</td>
							</tr>
							<tr>
								<td class="label">封顶手续费(元)：</td>
								<td class="content">
									<input class="easyui-validatebox" type="text" maxlength="7"
										id="maxFeeInpt" name="maxFee" value="${entity.maxFee!}" /><span class="inputDesc">请输入金额，如：1.00元</span>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<td class="label">交易日限额(元)：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="15"
							id="txDayMaxInpt" name="txDayMax" value="${entity.txDayMax!}" />
						<span class="inputDesc">如果不填写，则默认当日累计交易限额为999999999999.99元(支付交易)</span>
					</td>
				</tr>
				<tr>
					<td class="label">交易单笔下限(元)：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="15"
							id="txAmtMinInpt" name="txAmtMin" value="${entity.txAmtMin!}" />
					</td>
				</tr>
				<tr>
					<td class="label">交易单笔上限(元)：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="20"
							id="txAmtMaxInpt" name="txAmtMax" value="${entity.txAmtMax!}"/>
					</td>
				</tr>
				<tr>
					<td class="label">交易支持的时段：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="20"
							id="txTimeLimitInpt" name="txTimeLimit" value="${entity.txTimeLimit!}" />
						<span class="inputDesc">例："ALL" 或 "0800-1200,1400-2000"</span>
					</td>
				</tr>
				<tr>
					<td class="label">单日单卡限额(元)：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="20"
							id="txCardDayMaxIput" name="txCardDayMax" value="${entity.txCardDayMax!}"/>
						<span class="inputDesc">如果不填写，则默认当日累计交易限额为999999999999.99元</span>
					</td>
				</tr>
				<tr>
					<td class="label">代付垫资比例：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="20"
							id="txT0PercentIput" name="txT0Percent" value="${entity.txT0Percent!}"/>
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