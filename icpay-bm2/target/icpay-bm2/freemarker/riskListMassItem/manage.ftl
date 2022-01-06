<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head> <#-- label="${title}"  -->
		<@head label="风控规则名单配置信息" base="riskListMassItem" qryFuncCode="7000110001"
			addFlg=false addFuncCode="7000110002"
			deleteFlg=true deleteFuncCode="7000110004" deleteUrl='"delete.do?groupId="+sel.groupId+"&chnlId="+sel.chnlId+"&mchntCd="+sel.mchntCd+"&item="+sel.item'>
			<@authCheck funcCode='7000110002'>
				$("#addBtn1").click(function() {
					var groupId=$("#groupIdInpt").val();
					$.jumpTo(baseUrl + "add.do?groupId="+groupId);
				});
			</@authCheck>			
		</@head>
	</head>

	<body style="padding: 5px;">
		<@qryDiv qryUrl="/riskListMassItem/qry.do" qryFuncCode="7000110001">
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
					<#--  
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
					-->
					<td>风控项目内容：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="itemInpt" name="_QRY_itemFilter" value="">
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv 
			addFlg=false addFuncCode="7000110002"
			deleteFlg=true deleteFuncCode="7000110004">
			<@authCheck funcCode='7000110002'>
			<a id="addBtn1" href="javascript:void(0)" class="easyui-linkbutton"  data-options="iconCls:'icon-add'">新 增</a>
			</@authCheck>
		</@qryResultDiv>
	</body>
</html>