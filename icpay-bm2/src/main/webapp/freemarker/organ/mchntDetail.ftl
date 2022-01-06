<#-- <div class="easyui-panel" title="机构商户信息" style="padding:10px;"> -->
	<table class="view_tbl" cellpadding="0" cellspacing="0">
		<#list organMchntList as lst >
			<tr>
				<td class="vtd_lbl" width="15%">商户号:</td>
				<td class="vtd_cnt" width="35%">
					${lst.mchntCd!''}
				</td>
				<td class="vtd_lbl" width="15%">商户名称:</td>
				<td class="vtd_cnt" width="35%">
					${lst.mchntCnNm!''}
				</td>
			</tr>
		</#list>
	</table>
<#-- </div> -->