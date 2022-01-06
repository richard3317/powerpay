<div class="easyui-panel" title="审核内容-机构商户信息" style="padding:10px;">
	<table class="view_tbl" cellpadding="0" cellspacing="0">
		
		<#if contentObj.organIdList ??>
			<#list contentObj.organIdList as organ>
				<tr>
					<td class="vtd_lbl" width="15%">机构编号:</td>
					<td class="vtd_cnt" width="35%">
						${organ.organId}
					</td>
					<td class="vtd_lbl" width="15%">机构名称:</td>
					<td class="vtd_cnt" width="35%">
						${organ.organDesc}
					</td>
				</tr>
			</#list>
		<#else>
			<#if entity.opTp == '03' || entity.opTp == '05'>
				<tr>
					<td class="vtd_lbl" width="15%">机构编号:</td>
					<td class="vtd_cnt" width="85%" colspan="3">
						${contentObj.organId}
					</td>
				</tr>
			</#if>
			<tr>
				<td class="vtd_lbl" width="15%">机构名称:</td>
				<td class="vtd_cnt" width="35%">
					${contentObj.organDesc}
				</td>
				<#if mchntCdList ??>
					<td class="vtd_lbl" width="15%">商户代码:</td>
					<td class="vtd_cnt" width="35%">
						<textarea readonly="readonly" style="margin: 0px; height: 60px; width: 395px; text-align: left; BORDER-BOTTOM: 0px solid; BORDER-LEFT: 0px solid; BORDER-RIGHT: 0px solid; BORDER-TOP: 0px solid;height:100px;"><#list mchntCdList as mchntCd>${mchntCd}&#10;</#list></textarea>
					</td>
				<#-- <#else>
					<td class="vtd_lbl" width="15%">商户代码:</td>
					<td class="vtd_cnt" width="35%">
						${contentObj.mchntCd}
					</td> -->
				</#if>
			</tr>
		</#if>
	</table>
</div>