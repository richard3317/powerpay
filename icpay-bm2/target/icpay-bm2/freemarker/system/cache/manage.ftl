<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>缓存管理</title>
		<#include "/common/include.ftl">
		<script type="text/javascript">
			var refreshUrl = "${ctx}/system/cache/refresh.do";
			function refreshCache() {
				$.post(
					refreshUrl,
					{cacheTp : $("#cacheTp").val()},
					function(data){
						$.processAjaxResp(data, function() {
							alert("刷新成功");
						}, function() {
							alert("刷新失败，请联系管理员");
						});
					}
				);
			}
		</script>
	</head>

	<body style="padding: 5px;">
		<table class="qry_tbl">
			<tr>
				<td>缓存类型:</td>
				<td>
					<select id="cacheTp" name="cacheTp">
						<option value="ALL">--全部--</option>
						<#list cacheTpMap?keys as k>
							<option value="${k}">${cacheTpMap[k]}</option>
						</#list>
					</select>
				</td>
				<td>
					<@authCheck funcCode="9900050001">
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="refreshCache();">刷新</a>
					</@authCheck>
				</td>
			</tr>
		</table>
	</body>
</html>