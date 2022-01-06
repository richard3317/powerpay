<table class="view_tbl" cellpadding="0" cellspacing="0">
	<#list detailConfLst as detailConf>
		<tr>
			<td class="vtd_lbl">${detailConf.label}:</td>
			<td class="vtd_cnt">
				${entity['${detailConf.field}']!''}
			</td>
		</tr>
		<#if detailConf.field == 'mchntEnAbbr'>
			<tr>
				<td class="vtd_lbl">是否自主清算商户:</td>
				<td class="vtd_cnt">
					<@enumVal enumNm="MchntStCdEnums.SelfSettle" code="${mchntStCd.selfSettle}" showCode="true" />
				</td>
			</tr>
		</#if>
		<#if detailConf.field == 'mchntAddr'>
			<tr>
				<td class="vtd_lbl">商户交易权限组:</td>
				<td class="vtd_cnt">
					${transTpGroupDesc!''}
				</td>
			</tr>
			<tr>
				<td class="vtd_lbl">交易权限:</td>
				<td class="vtd_cnt">
					<@enumCheckBox enumNm="TxnEnums.TxnType" name="txnTypes" checkVals="${(transType)!''}" readOnly="true"/>
				</td>
			</tr>
		</#if>
		<#if detailConf.field == 'expiredDt'>
			<tr>
				<td class="vtd_lbl">接入方式:</td>
				<td class="vtd_cnt">
					<@enumCheckBox enumNm="TxnEnums.ApiType" name="apiType" checkVals="${(apiType)!''}" readOnly="true"/>
				</td>
			</tr>
			<tr>
				<td class="vtd_lbl">允许的充值来源:</td>
				<td class="vtd_cnt">
					<@enumCheckBox enumNm="TxnEnums.AllowedReqSrc" name="allowedReqSrc" checkVals="${(allowedReqSrc)!''}" readOnly="true"/>
				</td>
			</tr>
			<tr>
				<td class="vtd_lbl">允许的代付来源:</td>
				<td class="vtd_cnt">
					<@enumCheckBox enumNm="TxnEnums.AllowedReqSrc" name="allowedReqSrcWd" checkVals="${(allowedReqSrcWd)!''}" readOnly="true"/>
				</td>
			</tr>
			<tr>
				<td class="vtd_lbl">所属机构:</td>
				<td class="vtd_cnt">
					${(organId)!''}|${organDesc!''}
				</td>
			</tr>
		</#if>
	</#list>
</table>
	</body>
</html>