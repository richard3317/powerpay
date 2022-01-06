<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head> <#-- label="${title}"  -->
		<@head label="风控规则名单配置信息" base="riskListItem" qryFuncCode="7000020001"
			<#-- addFlg=true addFuncCode="7000020002" -->
			editFlg=true editFuncCode="7000020005" editUrl='"edit.do?id=" + sel.id'
			deleteFlg=true deleteFuncCode="7000020004" deleteUrl='"delete.do?id=" + sel.id'>
			<@authCheck funcCode='7000020002'>
				$("#addBtn").click(function() {
					var groupId=$("#groupIdInpt").val();
					$.jumpTo(baseUrl + "add.do?groupId="+groupId);
				});
			</@authCheck>			
		</@head>
	</head>

	<body style="padding: 5px;">
		<@qryDiv qryUrl="/riskListItem/qry.do" qryFuncCode="7000020001">
			<table class="qry_tbl">
				<tr>
				    <#-- <input type="hidden" value='102' id="groupIdInpt" name="_QRY_groupId"/> -->
					<td>风控组：</td>
					<td>
                        <select id="groupIdInpt" name="_QRY_groupId" class="easyui-validatebox" placeholder="请选择风控组" >
                            <#list riskGroups as item>
                                 <option value="${item.groupId}">${item.groupId}-${item.groupNm}</option>
                            </#list>
                        </select>
					</td>
					
					<td>渠道：</td>
					<td>
						<select id="chnlIdInpt" name="_QRY_chnlId" value="00">
							<option value="00">(小商户)</option>
							<@enumOpts enumNm='TxnEnums.ChnlId' showCode='true' showEmptyOpt='false' selected="00" />
						</select>
					</td>					
					<td>商户号：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="mchntCdInpt" name="_QRY_mchntCd" value="">
					</td>
					<td>风控项目内容：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="itemInpt" name="_QRY_itemFilter" value="">
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv 
			<#-- addFlg=true addFuncCode="7000020002" --> 
			editFlg=true editFuncCode="7000020005"
			deleteFlg=true deleteFuncCode="7000020004">
			<@authCheck funcCode='7000020002'>
			<a id="addBtn" href="javascript:void(0)" class="easyui-linkbutton"  data-options="iconCls:'icon-add'">新 增</a>
			</@authCheck>
		</@qryResultDiv>
	</body>
</html>