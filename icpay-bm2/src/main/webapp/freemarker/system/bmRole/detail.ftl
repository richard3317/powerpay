<table class="view_tbl" cellpadding="0" cellspacing="0">
	<#list detailConfLst as detailConf>
		<tr>
			<td class="vtd_lbl">${detailConf.label}:</td>
			<td class="vtd_cnt">
				${entity['${detailConf.field}']!''}
			</td>
		</tr>
	</#list>
	<tr>
		<td class="vtd_lbl">角色权限:</td>
		<td class="vtd_cnt">
			<ul id="funcTree" ></ul>
		</td>
	</tr>
</table>
<div id="treeData" style="display: none;">${funcTreeData}</div>
<script type="text/javascript">
	$(function() {
		var treeData = $("#treeData").html();
		$("#funcTree").tree({
			lines:true,
			data:$.parseJSON(treeData),
			checkbox:true,
			onLoadSuccess:function(){
				$(this).find("span.tree-checkbox").unbind().click(function(){
					return false;
				})
			}
		});
	});
</script>