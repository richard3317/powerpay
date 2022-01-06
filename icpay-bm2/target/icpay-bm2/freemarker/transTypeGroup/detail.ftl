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
		<td class="vtd_lbl">交易权限:</td>
		<td class="vtd_cnt">
			<@enumCheckBox enumNm="TxnEnums.TxnType" name="txnTypes" checkVals="${(entity.transType)!''}" readOnly="true"/>
		</td>
	</tr>
</table>