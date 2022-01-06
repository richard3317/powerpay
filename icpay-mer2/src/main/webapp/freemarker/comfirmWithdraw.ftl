<#include "/common/macro.ftl">

<#assign htmlHead>
	<script src="${ctx}/resources/js/jquery.dataTables.min.js"></script>
	<script src="${ctx}/resources/bootstrap/js/dataTables.bootstrap.min.js"></script>
</#assign>

<#assign htmlContent>
	<ol class="breadcrumb">
		<li><a href="#"><@msg code='comfirmWithdraw.商户服务' default='商户服务'/></a></li>
		<li class="active"><@msg code='comfirmWithdraw.取现查询' default='取现查询'/></li>
	</ol>
    
	<div class="panel panel-default">
		<div class="panel-heading"><h4 class="panel-title"><@msg code='comfirmWithdraw.确认' default='确认'/></h4></div>
		<input type="hidden" id="cashType" name="cashType" value="${cashType}" />
		<input type="hidden" id="cashAmt" name="cashAmt" value="${cashAmt}" />
		<input type="hidden" id="accNo" name="accNo" value="${accNo}" />
		<div class="panel-body">
			<form id="withdraw-flow-qryfrm" action="${ctx}/withdraw/comfirmWithdraw" method="post" class="form-horizontal">
				<table width="100%" style="margin-bottom: 20px;">
					<tr style="height: 50px;">
						<td width="10%" style="padding: 5px; padding-right: 10px; text-align: right;"><@msg code='comfirmWithdraw.请输入登录密码:' default='请输入登录密码:'/></td>
						<td width="23%">
							<input type="text" id="pwd" name="pwd" class="form-control" style="width: 200px;" />
						</td>
						
					</tr>
					<tr style="height: 50px;">
						<td width="50%" style="text-align: left; padding-left: 90px" colspan="2">
							<a id="comfirmBtn" class="btn btn-primary btn-sm" href="#" style="float: left;margin-right: 10px;" role="button"><@msg code='comfirmWithdraw.确定' default='确定'/></a>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>

</#assign>

<#assign htmlJS>
	var cashType =$("#cashType").val();
	var cashAmt =$("#cashAmt").val();
	var accNo =$("#accNo").val();
	$(function() {
		$("#comfirmBtn").click(function() {
			if ($("#pwd").val() == '' ) {
				alert("<@msg code='comfirmWithdraw.请输入密码' default='请输入密码'/>");
				return false;
			}
		var url = "${ctx}/withdraw/comfirmWithdraw?pwd=" + $("#pwd").val();
		$.get(url, function(data) {
			$.processAjaxResp(data, function() {
				comfirm();
			});
		});
	});
	});
	function comfirm(){
		var url = "${ctx}/withdraw/submitWithdraw.do?cashType=" + cashType + "&cashAmt=" + cashAmt +"&accNo=" + accNo;
		$.post(url, function(data) {
			$.processAjaxResp(data,
				function(respData) {
					alert("<@msg code='comfirmWithdraw.操作成功!' default='操作成功!'/>");
					$.jumpTo("${ctx}/withdraw/withdrawPage");
				}
			);
		});
	}
</#assign>

<#include "/common/layout.ftl" />