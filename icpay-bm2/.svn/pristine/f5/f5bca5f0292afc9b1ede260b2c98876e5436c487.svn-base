<#--  王允  added   -->
<div class="easyui-panel" title="审核内容-商户清算信息审核" style="padding:10px;">
	<table class="view_tbl" cellpadding="0" cellspacing="0">
		<tr>
			<td class="vtd_lbl" width="15%">商户代码:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.mchntCd}
			</td>
			<td class="vtd_lbl" width="15%">开户地区:</td>
			<td class="vtd_cnt" width="35%">
                 ${contentObj.settleAccountAreaInfo} 
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl" width="15%">开户行名称:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.settleAccountBankName}
			</td>
		
			<td class="vtd_lbl" width="15%">开户行联行号:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.settleAccountBankCode}
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl" width="15%">清算账号:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.settleAccount}
			</td>
		
			<td class="vtd_lbl" width="15%">清算账号户名:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.settleAccountName}
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl" width="15%">清算周期:</td>
			<td class="vtd_cnt" colspan="3">
				${settlePeriodDesc}
			</td>
		</tr>
		<#if settleLimit??>
			<tr>
				<td class="vtd_lbl" width="15%">当日最大清算金额:</td>
				<td class="vtd_cnt" colspan="3">
					${settleLimit} (元)
				</td>
			</tr>
		</#if>
		<tr>
			<td class="vtd_lbl" width="15%">最近更新人:</td>
			<td class="vtd_cnt" colspan="3">
				${contentObj.lastOperId}
			</td>
		</tr>
	</table>
</div>