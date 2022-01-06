<table class="view_tbl" cellpadding="0" cellspacing="0" width="99%">
	<#list detailConfLst as detailConf>
		<tr>
			<td class="vtd_lbl">${detailConf.label}:</td>
			<td class="vtd_cnt">
				${entity['${detailConf.field}']!''}
			</td>
		</tr>
	</#list>
</table>