<div class="easyui-panel" title="审核内容-商户交易权限" style="padding:10px;">
	<table class="view_tbl" cellpadding="0" cellspacing="0">
		<tr>
			<td class="vtd_lbl" width="15%">商户代码:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.mchntCd}
			</td>
			<td class="vtd_lbl" width="15%">中文名称:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.mchntCnNm}
			</td>
		</tr>
		<tr>
		<td class="vtd_lbl" width="15%">交易权限:</td>
		<td class="vtd_cnt" colspan="3">
			<@enumCheckBox enumNm="TxnEnums.TxnType" name="txnTypes" checkVals="${(contentObj.transType)!''}" readOnly="true"/>
		</td>
	</tr>
	</table>
</div>