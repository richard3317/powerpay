<table class="view_tbl" cellpadding="0" cellspacing="0">
	<#list detailConfLst as detailConf>
		<#if detailConf.field == 'settleLimit'>
			<#if entity.settlePeriod == '0'>
				<tr>
					<td class="vtd_lbl">${detailConf.label}:</td>
					<td class="vtd_cnt">
						${entity['${detailConf.field}']!''}(元)
					</td>
				</tr>
			</#if>
		<#else>
			<tr>
				<td class="vtd_lbl">${detailConf.label}:</td>
				<td class="vtd_cnt">
					${entity['${detailConf.field}']!''}
				</td>
			</tr>
		</#if>
	</#list>
</table>