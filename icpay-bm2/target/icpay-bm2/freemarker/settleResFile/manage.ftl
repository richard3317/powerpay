<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="划款文件" base="settleResFile" qryFuncCode="8000080001"
			detailFlg=true detailUrl='"detail.do?seqId=" + sel.seqId' detailFuncCode="8000080002">
			<@authCheck funcCode='8000080003'>
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
										dealFlag = false;
										$("#qryForm").submit();
									});
								}
							);
						}
					}
				});
			</@authCheck>
			<@authCheck funcCode='8000080004'>
				$("#downBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						$.jumpTo("${ctx}/settleResFile/down.do?seqId=" + sel.seqId);
					}
				});
			</@authCheck>
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/settleResFile/qry.do" qryFuncCode="8000080001">
			<table class="qry_tbl">
				<tr>
					<td>清算日期：</td>
					<td>
						<input name="_QRY_settleDt" type="text" value="" 
							onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate" />
					</td>
					<td>划款批次：</td>
					<td>
						<select name="_QRY_settleBt">
							<option value="">--请选择--</option>
							<option value="0">T+n</option>
							<#list [1,2,3,4,5,6,7,8,9,10,11,12] as i>
								<option value="${i}">T+0 - ${i}</option>
							</#list>
						</select>
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv detailFlg=true detailFuncCode="8000080002">
			<@authCheck funcCode='8000080003'>
			 	<a id="confirmBtn" href="javascript:void(0)" class="easyui-linkbutton">确认</a>
			 </@authCheck>
			 <@authCheck funcCode='8000080004'>
			 	<a id="downBtn" href="#" class="easyui-linkbutton">下载划款文件</a>
			 </@authCheck>
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
	</body>
<html>