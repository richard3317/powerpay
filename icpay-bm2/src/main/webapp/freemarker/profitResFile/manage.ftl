<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="代理商划款文件" base="profitResFile" qryFuncCode="8000130001"
			detailFlg=true detailFuncCode="8000130002" detailUrl='"detail.do?seqId=" + sel.seqId'>
			<@authCheck funcCode='8000130003'>
				$("#downBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						$.jumpTo("${ctx}/profitResFile/down.do?seqId=" + sel.seqId);
					}
				});
			</@authCheck>
			<@authCheck funcCode='8000130004'>
				$("#confirmBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						if (sel.state == '1') {
							alert('已经为"1-已划款"状态');
							return;
						}
						if (dealFlag) {//重复提交控制
							alert("正在处理请求，请稍等......");
							return false;
						}
						if (confirm("确认已划款?")) {
							dealFlag = true;
							var url = baseUrl + "confirm.do?seqId=" + sel.seqId;
							$.post(url,
								function(data) {
									$.processAjaxResp(data, function() {
										alert("已确认划款");
										$("#qryForm").submit();
									});
									dealFlag = false;
								}
							);
						}
					}
				});
			</@authCheck>
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/profitResFile/qry.do" qryFuncCode="8000130001">
			<table class="qry_tbl">
				<tr>
					<td>分润日期：</td>
					<td>
						<input name="_QRY_profitDt" type="text"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd', readOnly:true})" class="Wdate" />
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv 
			detailFlg=true detailFuncCode="8000130002">
			<@authCheck funcCode='8000130003'>
				<a id="downBtn" href="javascript:void(0)" class="easyui-linkbutton">下载划款文件</a>
			</@authCheck>
			<@authCheck funcCode='8000130004'>
				<a id="confirmBtn" href="#" class="easyui-linkbutton">确认划款</a>
			</@authCheck>
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
	</body>
<html>
	