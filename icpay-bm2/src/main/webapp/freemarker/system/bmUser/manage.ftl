<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="用户信息" base="system/bmUser" qryFuncCode="9900030001"
			addFlg=true addFuncCode="9900030003"
			detailFlg=true detailFuncCode="9900030002" detailUrl='"detail.do?usrId=" + sel.usrId'>
			var super_admin = "${super_admin}";
			<@authCheck funcCode="9900030005">
				$("#editBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						if (super_admin == sel.usrId) {
							alert("不能修改超级管理员");
							return;
						}
						$.jumpTo(baseUrl + "edit.do?usrId=" + sel.usrId);
					}
				});
			</@authCheck>
			<@authCheck funcCode="9900030007">
				$("#delBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						if (super_admin == sel.usrId) {
							alert("不能删除超级管理员");
							return;
						}
						if (confirm("确认要删除选中的记录吗?")) {
							if (dealFlag) {
								alert("正在处理请求，请稍等......");
								return false;
							}
							dealFlag = true;		 
							var url = baseUrl + "delete.do?usrId=" + sel.usrId;
							$.post(url,
								function(data) {
									$.processAjaxResp(data, function() {
										alert("删除用户成功");
										$("#qryForm").submit();
									});
									dealFlag = false;
								}
							);
						}
					}
				});
			</@authCheck>
			<@authCheck funcCode="9900030008">
			$("#enableBtn").click(function() {
				var sel = $("#qryResultTbl").datagrid("getSelected");
				if (sel == null) {
					alert("请选择一条记录");
				} else {
					if (super_admin == sel.usrId) {
						alert("不能操作超级管理员");
						return;
					}
					if (sel.usrSt == '1') {
						alert("该用户已经是有效状态");
						return;
					}
					if (confirm("确认启用?")) {
						if (dealFlag) {
							alert("正在处理请求，请稍等......");
							return false;
						}
						dealFlag = true;
						var url = baseUrl + "enable.do?usrId=" + sel.usrId;
						$.post(url,
							function(data) {
								$.processAjaxResp(data, function() {
									alert("用户已启用");
									$("#qryForm").submit();
								});
								dealFlag = false;
							}
						);
					}
				}
			});
			</@authCheck>
			<@authCheck funcCode="9900030009">
			$("#disableBtn").click(function() {
				var sel = $("#qryResultTbl").datagrid("getSelected");
				if (sel == null) {
					alert("请选择一条记录");
				} else {
					if (super_admin == sel.usrId) {
						alert("不能操作超级管理员");
						return;
					}
					if (sel.usrSt == '0') {
						alert("该用户已经是无效状态");
						return;
					}
					if (confirm("确认禁用?")) {
						if (dealFlag) {
							alert("正在处理请求，请稍等......");
							return false;
						}
						dealFlag = true;
						var url = baseUrl + "disable.do?usrId=" + sel.usrId;
						$.post(url,
							function(data) {
								$.processAjaxResp(data, function() {
									alert("用户已禁用");
									$("#qryForm").submit();
								});
								dealFlag = false;
							}
						);
					}
				}
			});
			</@authCheck>
			<@authCheck funcCode="9900030010">
				$("#resetPwdBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						if (super_admin == sel.usrId) {
							alert("不能操作超级管理员");
							return;
						}
						if (confirm("确认重置用户密码?")) {
							if (dealFlag) {
								alert("正在处理请求，请稍等......");
								return false;
							}
							dealFlag = true;
							var url = baseUrl + "resetPwd.do?usrId=" + sel.usrId;
							$.post(url,
								function(data) {
									$.processAjaxResp(data, function(r) {
										alert("用户密码已重置");
										$("#resetPwdDiv").html("用户【" + sel.usrId + "】登录密码已重置，请通知用户：" + r.respMsg).fadeIn();
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
		<@qryDiv qryUrl="/system/bmUser/qry.do" qryFuncCode="9900030001">
			<table class="qry_tbl">
				<tr>
					<td>用户ID:</td>
					<td>
						<input class="easyui-validatebox" type="text"
							name="_QRY_usrId" value="${(qryParamMap.usrId)!''}" />
					<td>
					</td>
					<td>用户姓名:</td>
					<td>
						<input class="easyui-validatebox" type="text" 
							name="_QRY_usrNm" value="${(qryParamMap.usrNm)!''}" />
					</td>
					<td>角色:</td>
					<td>
						<select id="roleSel" name="_QRY_roleId">
							<option value="">--全部--</option>
							<#list roleMap?keys as r>
								<#if qryParamMap??>
									<#if qryParamMap.roleId == r>
										<option value="${r}" selected="selected">${r}-${roleMap[r]}</option>
										<#else>
										<option value="${r}">${r}-${roleMap[r]}</option>
									</#if>
								<#else>
									<option value="${r}">${r}-${roleMap[r]}</option>
								</#if>
								
							</#list>
						</select>
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv qryFuncCode="9900030001" 
			detailFlg=true detailFuncCode="9900030002"
			editFlg=true editFuncCode="9900030005"
			addFlg=true addFuncCode="9900030003"
			deleteFlg=true deleteFuncCode="9900030007">
			<@authCheck funcCode="9900030008">
				<a id="enableBtn" href="javascript:void(0)" class="easyui-linkbutton">启用</a>
			</@authCheck>
			<@authCheck funcCode="9900030009">
				<a id="disableBtn" href="javascript:void(0)" class="easyui-linkbutton">禁用</a>
			</@authCheck>
			<@authCheck funcCode="9900030010">
				<a id="resetPwdBtn" href="javascript:void(0)" class="easyui-linkbutton">重置密码</a>
			</@authCheck>
		</@qryResultDiv>
		
		<div id="resetPwdDiv" style="display: none; color: red; font-size: 14px; font-weight: bold;"></div>
		<div id="detailDiv"></div>
	</body>
</html>