<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="渠道信息" base="chnl" paginationFlg=false qryFuncCode="2000010001"
			detailFlg=true detailFuncCode="2000010002" detailUrl='"detail.do?chnlId=" + sel.chnlId' detailDivWidth=700
			addFlg=true addFuncCode="2000010003"
			editFlg=true editFuncCode="2000010005" editUrl='"edit.do?chnlId=" + sel.chnlId'
			deleteFlg=true deleteFuncCode="2000010003" deleteUrl='"delete.do?chnlId=" + sel.chnlId'
			>
		</@head>
	</head>

	<body style="padding: 5px;">
		<@qryDiv qryUrl="/chnl/qry.do" showBtn=false qryFuncCode="2000010001">
		</@qryDiv>
		
		<@qryResultDiv
			addFlg=true addFuncCode="2000010003"
			editFlg=true editFuncCode="2000010005"
			deleteFlg=true deleteFuncCode="2000010007"
			detailFlg=true detailFuncCode="2000010002">
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
	</body>
</html>