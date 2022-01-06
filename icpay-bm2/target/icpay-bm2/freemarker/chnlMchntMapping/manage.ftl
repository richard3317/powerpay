<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="渠道商户映射信息" base="chnlMchntMapping" qryFuncCode="1000030001"
			addFlg=true addFuncCode="1000030002"
			deleteFlg=true deleteFuncCode="1000030004" deleteUrl='"delete.do?mchntCd=" + sel.mchntCd + "&chnlId=" + sel.chnlId + "&chnlMchntCd=" + sel.chnlMchntCd'>
			<@authCheck funcCode='1000030005'>
				$("#cmtBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						$("#cmtDiv").dialog({
							title: "备注",
							width: 400,
							height: 200,
							top: 10,
							closed: false,
							cache: false,
							modal: true,
							inline: false,
							buttons:[{
								text:"保存",
								handler:function() {
									var cmt = $.trim($("#commentInpt").val());
									var cmtUrl = "${ctx}/chnlMchntMapping/cmt.do";
									$.post(cmtUrl, 
										{
											mchntCd : sel.mchntCd,
											chnlId : sel.chnlId,
											chnlMchntCd : sel.chnlMchntCd,
											comment : cmt
										}, 
										function(data) {
											$.processAjaxResp(data, function() {
												$("#cmtDiv").dialog("close");
												sel.comment=cmt;
												var idx = $("#qryResultTbl").datagrid("getRowIndex", sel);
												$("#qryResultTbl").datagrid("refreshRow", idx);
											});
									});
								}
							},{
								text:"取消",
								handler:function() {
									$("#commentInpt").val("").hide();
									$("#cmtDiv").dialog("close");
								}
							}]
						});
						$("#commentInpt").val(sel.comments).show();
					}
				});
			</@authCheck>
		</@head>
	</head>

	<body style="padding: 5px;">
		<@qryDiv qryUrl="/chnlMchntMapping/qry.do" qryFuncCode="1000030001">
			<table class="qry_tbl">
				<tr>
					<td>前端商户号：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="mchntCdInpt" maxLength="15"
							name="_QRY_mchntCd" value="${(qryParamMap.mchntCd)!''}">
					</td>
					<td>渠道：</td>
					<td>
						<select id="chnlIdSel" name="_QRY_chnlId" data-options="editable:false">
							<@enumOpts enumNm='TxnEnums.ChnlId' showCode='true' 
								showEmptyOpt="true" selected="${(qryParamMap.chnlId)!''}" />
						</select>
					</td>
					<td>渠道商户号：</td>
					<td>
						<input class="easyui-validatebox" type="text" maxLength="40"
							id="chnlMchntCd" name="_QRY_chnlMchntCd" value="${(qryParamMap.chnlMchntCd)!''}">
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv addFlg=true addFuncCode="1000030002" deleteFlg=true deleteFuncCode="1000030004">
			<@authCheck funcCode='1000030005'>
				<a id="cmtBtn" href="javascript:void(0)" class="easyui-linkbutton">备注</a>
			</@authCheck>
		</@qryResultDiv>
		
		<@authCheck funcCode='1000030005'>
			<div id="cmtDiv">
				<textarea id="commentInpt" style="display:none; width: 360px; margin-left: 10px; margin-top: 5px; height: 110px;"></textarea>
			</div>
		</@authCheck>
	</body>
</html>