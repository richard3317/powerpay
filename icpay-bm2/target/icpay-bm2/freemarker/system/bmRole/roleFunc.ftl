<html>
	<head>
		<title>角色权限配置页面</title>
		<#include "/common/include.ftl">
		<script type="text/javascript">
			$(function() {
				$("#saveBtn").click(function() {
					$("#editForm").form("submit", {
						url: $("#editForm").attr("action"),
						onSubmit: function() {
							var isValid = $(this).form("validate");
							if (isValid) {
								var nodes = $("#funcTree").tree("getChecked");
								$.each(nodes, function(i,v) {
									if ($("#funcTree").tree("isLeaf", v.target)) {
										// 功能
										addFunc(v.id);
										var m = $("#funcTree").tree("getParent", v.target);
										// 菜单
										if (m != null) {
											addFunc(m.id);
											var r = $("#funcTree").tree("getParent", m.target);
											// 模块
											if (r != null) {
												addFunc(r.id);
											}
										}
									}
								});
							}
							
							if (dealFlag) {//重复提交控制
								alert("正在处理请求，请稍等......");
								return false;
							}
							dealFlag = isValid;		//根据校验更新处理标识， 在dealResult中重置dealFlag为false;
							return isValid;
						},
						success: function(data) {
							$.processAjaxResp(data, function() {
								alert("角色权限信息配置完成");
								$.jumpTo("${ctx}/system/bmRole/backToManage.do");
							});
						}
					});
				});

				$("#backBtn").click(function() {
					$.jumpTo("${ctx}/system/bmRole/backToManage.do");
				});

				var treeData = $("#treeData").html();
				$("#funcTree").tree({
					lines:true,
					data:$.parseJSON(treeData),
					checkbox:true
				});
			});
			function addFunc(funcCd) {
				if ($("#editForm").children("input[value='" + funcCd + "']").length == 1) {
					return;
				}
				$("#editForm").append("<input type='hidden' name='funcCds' value='" + funcCd + "'/>");
			}
			function clearFunc() {
				$("#editForm").children("input[name='funcCds']").remove();
			}
		</script>
	</head>

	<body>
		<div id="roleFuncDiv" class="easyui-panel" title="角色功能权限设置" style="padding: 10px;">
			<form id="editForm" method="post" action="${ctx}/system/bmRole/roleFunc/submit.do">
				<input id="roleId" type="hidden" value="${role.roleId}" name="roleId"/>
				<table class="edit_tbl" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label">角色:</td>
						<td class="content">
							${role.roleId} - ${role.roleNm}
						</td>
					</tr>
					<tr>
						<td class="label">功能权限:</td>
						<td class="content">
							<div id="sysInfoList">
							   <ul id="funcTree" ></ul>
							</div>
						</td>
					</tr>
				</table>
			</form>
			
			<div style="margin: 10px;">
				<a id="saveBtn" href="javascript:void(0)" class="easyui-linkbutton">保存</a>
				<a id="backBtn" href="javascript:void(0)" class="easyui-linkbutton">返回</a> 
			</div>
			
			<div id="treeData" style="display: none;">${funcTreeData}</div>
		</div>
	</body>
</html>