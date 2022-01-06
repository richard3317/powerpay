<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="商户交易权限组信息" base="transTypeGroup" qryFuncCode="1000060001"
			addFlg=true addFuncCode="1000060001"
			detailFlg=true detailUrl='"detail.do?seqId=" + sel.seqId' detailFuncCode="1000060002"
			editFlg=true editUrl='"edit.do?seqId=" + sel.seqId' editFuncCode="1000060005" deleteFuncCode="1000060007"
			deleteFlg=true deleteUrl='"delete.do?seqId=" + sel.seqId'>
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/transTypeGroup/qry.do" qryFuncCode="1000060001">
			<table class="qry_tbl">
				<tr>
					<td>组号：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="seqIdInpt" maxLength="20"
							name="_QRY_seqId" value="${(qryParamMap.seqId)!''}">
					</td>
					<td>组名：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="groupNmInpt" maxLength="120"
							 name="_QRY_groupNm" value="${(qryParamMap.groupNm)!''}">
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv  
			addFlg=true addFuncCode="1000060003"
			detailFlg=true detailFuncCode="1000060002"
			editFlg=true editFuncCode="1000060005"
			 deleteFlg=true deleteFuncCode="1000060007">
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
	</body>
<html>