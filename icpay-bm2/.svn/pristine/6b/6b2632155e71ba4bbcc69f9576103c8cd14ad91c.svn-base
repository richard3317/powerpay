<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="角色信息" base="system/bmRole" qryFuncCode="9900020001"
			addFlg=true addFuncCode="9900020003"
			detailFlg=true detailFlg=true detailUrl='"detail.do?roleId=" + sel.roleId' detailDivHeight=400 detailFuncCode="9900020002"
			editFlg=true editFuncCode="9900020005" editUrl='"edit.do?roleId=" + sel.roleId'
			deleteFlg=true deleteFuncCode="9900020007" deleteUrl='"delete.do?roleId=" + sel.roleId'>
			
			$("#copyBtn").click(function(){
				var sel = $("#qryResultTbl").datagrid("getSelected");
				if (sel == null) {
					alert("请选择一条记录");
				} else {
					var url = baseUrl + "copy.do?roleId=" + sel.roleId;
					crtDialog("copyDiv","复制角色", url, function() {
						$("#addForm").form("submit", {
							url: $("#addForm").attr("action"),
							onSubmit: function(){
							   
						    },
							success: function(data) {
								$.processAjaxResp(data, function() {
									alert("复制角色权限信息配置完成");
									$.jumpTo("${ctx}/system/bmRole/backToManage.do");
								});
							}
						});
					});
				}
			});
			
			$("#roleFuncBtn").click(function() {
				var sel = $("#qryResultTbl").datagrid("getSelected");
				if (sel == null) {
					alert("请选择一条记录");
				} else {
					$.jumpTo(baseUrl + "roleFunc.do?roleId=" + sel.roleId);
				}
			});
			
	        

	        
		</@head>
	</head>

	<body style="padding: 5px;">
		<@qryDiv qryUrl="/system/bmRole/qry.do" qryFuncCode="9900020001">
			<table class="qry_tbl">
				<tr>
					<td>角色ID:</td>
					<td>
						<input class="easyui-validatebox" type="text" 
							name="_QRY_roleId" value="">
					</td>
					<td>角色名称:</td>
					<td>
						<input class="easyui-validatebox" type="text" 
							name="_QRY_roleNm" value="">
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv qryFuncCode="9900020001"
			addFlg=true addFuncCode="9900020003" 
			detailFlg=true detailFuncCode="9900020002"
			editFlg=true editFuncCode="9900020005"
			deleteFlg=true deleteFuncCode="9900020007">
			<a id="copyBtn" href="javascript:void(0)" class="easyui-linkbutton" >复制</a>
			<a id="roleFuncBtn" href="javascript:void(0)" class="easyui-linkbutton">设置功能权限</a>
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
		<div id="copyDiv"></div>

	</body>
</html>