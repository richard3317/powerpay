<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="大小商编交易日额" base="chnlMchntDaily" qryFuncCode="9200130001">
			<@authCheck funcCode="9200130002">
				$("#exportBtn").click(function() {
					var qryStr=$("#qryForm").serialize();
					var url =_ctx + "/chnlMchntDaily/exportRpt.do?"+qryStr;
					console.log(url);
					$.jumpTo(url);
				});
			</@authCheck>
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<div id="mngDiv" class="easyui-panel" title="大小商编交易日额" style="padding: 10px;">
			<@qryDiv qryUrl="/chnlMchntDaily/qry.do" qryFuncCode="9200130001">
				<table class="qry_tbl">
					<tr>
						<td>操作日期</td>
						<td colspan="3">
							<input id="recUpdTs" name="_QRY_recUpdTs" type="text"
								value="${(recUpdTs)!'${today}'}"
								onFocus="WdatePicker({dateFmt:'yyyyMMdd',readOnly:true })" class="Wdate" />
						</td>
					</tr>
				
				</table>
			</@qryDiv>
			<@qryResultDiv>
				<@authCheck funcCode='9200130002'>
					<a id="exportBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-print'">导 出</a>
				</@authCheck>
			</@qryResultDiv>
			<div id="detailDiv"></div>
		</div>	
	</body>
<html>