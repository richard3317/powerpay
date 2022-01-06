<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>渠道虚拟终端信息-清算信息修改</title>
		<#include "/common/include.ftl">
			
		<script type="text/javascript">
			var dealFlg = false;
			$(function() {
				$("#backBtn").click(function() {
					$.jumpTo(_ctx + "/virtualTerm/settlePolicy/manage.do?chnlId=" + $("#chnlIdHid").val()+"&chnlMchntCd=" + $("#chnlMchntCdHid").val());
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
							alert("清算信息修改成功");
							$.jumpTo(_ctx + "/virtualTerm/settlePolicy/manage.do?chnlId=" + $("#chnlIdHid").val()+"&chnlMchntCd=" + $("#chnlMchntCdHid").val());
						});
						dealFlg = false;
					}
				});
				
				
			});
		</script>
	</head>

	<body style="padding: 5px;">
		<div id="updDiv" class="easyui-panel" title="渠道虚拟终端信息-清算信息修改-${entity.chnlId}-${entity.chnlMchntCd}" style="padding: 10px;">
			<form id="updForm" method="post" action="${ctx}/virtualTerm/settlePolicy/upd/submit.do">
				<input type="hidden" id="chnlIdHid" name="chnlId" value="${entity.chnlId}" />
				<input type="hidden" id="currCdHid" name="currCd" value="${entity.currCd}" />
		    	<input type="hidden" id="chnlMchntCdHid" name="chnlMchntCd" value="${entity.chnlMchntCd}" />
				<input type="hidden" name="settlePeriodCheck" value="${entity.settlePeriod}" />
				<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">币别：</td>
					<td class="content">
						<@enumVal enumNm="BmEnums.CurrCdType" code="${entity.currCd}" />
					</td>
				</tr>
				<tr>
					<td class="label">结算方式：</td>
					<td class="content">
						<select id="settlePeriodSel" name="settlePeriod">
							<@enumOpts enumNm='SettleEnums.SettlePeriod' selected=entity.settlePeriod showEmptyOpt='false' showCode="false" />
						</select>
					</td>					
				</tr>
				
				<tr>
					<td class="label">自动D0余额结转：</td>
					<td class="content">
						<select class="easyui-validatebox" id="balanceTransferSel" name="balanceTransfer" value ="${entity.balanceTransfer!'0'}" style="width: 300px;">
							<@enumOpts enumNm='TxnEnums.CommonYesNo' selected=entity.balanceTransfer!'0' showEmptyOpt='false' showCode="false" />
						</select>
					</td>					
				</tr>
				<tr id="transferTimeTr">
					<td class="label">D0余额结转时间：</td>
					<td class="content">
						<input class="easyui-validatebox" type ="text" value="${entity.transferTime!''}" id="transferTimeInpt" name ="transferTime"/>
						<span class="inputDesc">格式HHmmss，例如：235000(可省略，使用默认值)</span>
					</td>
				</tr>
				
				<tr>
					<td class="label">自动T1余额结转反还：</td>
					<td class="content">
						<select class="easyui-validatebox" id="balanceTransferT1Sel" name="balanceTransferT1" value ="${entity.balanceTransferT1!'0'}" style="width: 300px;">
							<@enumOpts enumNm='TxnEnums.CommonYesNo' selected=entity.balanceTransferT1!'0' showEmptyOpt='false' showCode="false" />
						</select>
					</td>
				</tr>
				<tr id="transferTimeT1Tr">
					<td class="label">T1余额结转时间：</td>
					<td class="content">
						<input class="easyui-validatebox" type ="text" value="${entity.transferTimeT1!''}" id="transferTimeT1Inpt" name ="transferTimeT1"/>
						<span class="inputDesc">格式HHmmss，例如：163000(可省略，使用默认值)</span>
					</td>
				</tr>
				
				<tr id="transferModeTr">
					<td class="label">T1余额结转模式：</td>
					<td class="content">
						<select class="easyui-validatebox" value="${entity.transferMode!'0'}" id="transferModeSel" name="transferMode" style="width: 300px;">
							<@enumOpts enumNm='TxnEnums.DailyTransferMode' selected=entity.transferMode!'0' showEmptyOpt='false' showCode="false" />
						</select>
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