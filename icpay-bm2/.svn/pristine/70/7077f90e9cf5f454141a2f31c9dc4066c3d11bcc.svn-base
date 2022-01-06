<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
			<@head label="公告提醒" base="announcementManagement" qryFuncCode="9500020001"
				addFlg=true addFuncCode="9500020003"
				detailFlg=true detailUrl='"detail.do?contentId=" + sel.contentId' detailFuncCode="9500020002"
				editFlg=true editUrl='"edit.do?contentId=" + sel.contentId' editFuncCode="9500020005" 
				 deleteFlg=true deleteUrl='"delete.do?contentId=" + sel.contentId'   deleteFuncCode="9500020007">		 
			<#-- 删除 -->
			<@authCheck funcCode='9500020007'>
				$("#delete").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						if(confirm("确认删除吗")){
							var contentId = sel.contentId ;
							$.ajax({
								url:'${ctx}/announcementManagement/delete.do',
								type:'post',
								data:{"contentId":contentId},
								success:function(data){
									window.location.reload();
								}
							});
						}else{
							return false;
						}
					}
				});
				</@authCheck>
			<#-- 修改-->
			<@authCheck funcCode='9500020005'>
				$("#edit").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						var contentId = sel.contentId ;
						$.jumpTo(_ctx +
						"/announcementManagement/edit.do?contentId="+ contentId);
					}
				});
				</@authCheck>
			</@head>

</head>

	<body style="padding: 5px;">
	<@qryDiv qryUrl="/announcementManagement/qry.do" qryFuncCode="9500020001">
			<table class="qry_tbl">
				<tr>
					<td>公告编号：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="contentId" maxLength="20"
							name="_QRY_contentId" value="${(qryParamMap.contentId)!''}">
					</td>
				
					<td>
						<select id="contentState" maxLength="120"name="_QRY_contentState" style="display:none">
							<option value="-1">所有</option>
							<option value="0">上线</option>
							<option value="1">下线</option>
						</select>
					</td>
					<td>创建时间：</td>
						
						<td colspan="3">
						<input id="creatTimeStart" name="_QRY_creatTimeStart" type="text" value="${(qryParamMap.creatTimeStart)!''}"
							onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH',maxDate:'#F{$dp.$D(\'creatTimeEnd\')}', readOnly:true})"
							class="Wdate" />
						至
						<input id="creatTimeEnd" name="_QRY_creatTimeEnd" type="text" value="${(qryParamMap.creatTimeEnd)!''}"
							onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH',minDate:'#F{$dp.$D(\'creatTimeStart\')}', readOnly:true})"
							class="Wdate" />
					</td>
				</tr>
			</table>													
		</@qryDiv>
	
	<@qryResultDiv 
		 	addFlg=true addFuncCode="9500020003"
			detailFlg=true detailFuncCode="9500020002"
			editFlg=true editFuncCode="9500020005"
			deleteFlg=true deleteFuncCode="9500020007"
			 >
		<#-- 	<@authCheck funcCode='3000310013'> 
			  <a id="delete" href="javascript:void(0)"
			class="easyui-linkbutton l-btn l-btn-small c2" 
			data-options="iconCls:'icon-clear'">删除</a>
		</@authCheck>
		<@authCheck funcCode='3000310013'> 
			   <a id="edit" href="javascript:void(0)"
			class="easyui-linkbutton l-btn l-btn-small c2" 
			data-options="iconCls:'icon-edit'" >修改</a>
		</@authCheck> -->
		
		</@qryResultDiv>
		

		<div id="detailDiv"></div>
	</body>
</html>