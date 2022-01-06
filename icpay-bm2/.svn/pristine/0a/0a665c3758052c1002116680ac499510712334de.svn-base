<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="渠道虚拟商户清算信息" base="chnlVirtualMerSettleInfo" qryFuncCode="8000010001"
			detailFlg=true detailUrl='"detail.do?seqId=" + sel.seqId' detailFuncCode="8000010002">
			<@authCheck funcCode='8000010003'>
				$("#confirmBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						if (sel.state == '1') {
							alert('已经为"1-确认"状态');
							return;
						}
						if (dealFlag) {//重复提交控制
							alert("正在处理请求，请稍等......");
							return false;
						}
						if (confirm("确认渠道虚拟商户清算信息无误?")) {
							dealFlag = true;
							var url = baseUrl + "confirm.do?seqId=" + sel.seqId;
							$.post(url,
								function(data) {
									$.processAjaxResp(data, function() {
										alert("渠道虚拟商户清算信息已确认");
										dealFlag = false;
										$("#qryForm").submit();
									});
								}
							);
						}
					}
				});
			</@authCheck>
			<@authCheck funcCode='8000010004'>
				$("#downBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						$.jumpTo("${ctx}/chnlVirtualMerSettleInfo/downStlFile.do?seqId=" + sel.seqId);
					}
				});
			</@authCheck>
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/chnlVirtualMerSettleInfo/qry.do" qryFuncCode="8000010001">
			<table class="qry_tbl">
				<tr>
					<td>渠道：</td>
					<td>
						<select name="_QRY_chnlId">
							<@enumOpts enumNm="TxnEnums.ChnlId" showCode="true" showEmptyOpt="true"/>
						</select>
					</td>
					<td>渠道商户号：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="mchntCdInpt" maxLength="15"
							name="_QRY_mchntCd" value="${(qryParamMap.mchntCd)!''}">
					</td>
					<td>清算日期：</td>
					<td>
						<input name="_QRY_settleDt" type="text" value="" 
							onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate" />
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv detailFlg=true detailFuncCode="8000010002">
			<@authCheck funcCode='8000010003'>
			 	<a id="confirmBtn" href="javascript:void(0)" class="easyui-linkbutton">确认</a>
			 </@authCheck>
			 <@authCheck funcCode='8000010004'>
			 	<a id="downBtn" href="#" class="easyui-linkbutton">下载渠道对账文件</a>
			 </@authCheck>
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
	</body>
<html>