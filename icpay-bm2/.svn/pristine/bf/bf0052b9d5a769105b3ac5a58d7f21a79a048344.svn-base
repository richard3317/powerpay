<div class="easyui-panel" title="审核内容-商户基本信息" style="padding:10px;">
	<table class="view_tbl" cellpadding="0" cellspacing="0">
		<#if entity.opTp == '03' || entity.opTp == '05'>
		<tr>
			<td class="vtd_lbl" width="15%">商户代码:</td>
			<td class="vtd_cnt" width="85%" colspan="3">
				${contentObj.mchntCd}
			</td>
		</tr>
		</#if>
		<tr>
			<td class="vtd_lbl" width="15%">中文名称:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.mchntCnNm}
			</td>
			<td class="vtd_lbl" width="15%">归属代理商:</td>
			<td class="vtd_cnt" width="35%">
				${(contentObj.agentCd)!''}&nbsp;${agentCnNm!''}
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl" width="15%">商户有效日期:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.expiredDt}
			</td>
			<td class="vtd_lbl" width="15%">接入方式:</td>
			<td class="vtd_cnt" width="35%">
				<@enumCheckBox enumNm="TxnEnums.ApiType" name="apiType" checkVals="${(apiType)!''}" readOnly="true"/>
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl" width="15%">交易权限组:</td>
			<td class="vtd_cnt" width="35%">
				${transTpGroupDesc}
			</td>
			<td class="vtd_lbl" width="15%">商戶状态:</td>
			<td class="vtd_cnt" width="35%">
				${mchntStDesc}
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl" width="15%">联系人:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.contactPerson}
			</td>
			<td class="vtd_lbl" width="15%">电话:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.contactPhone}
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl" width="15%">邮箱:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.contactMail}
			</td>
			<td class="vtd_lbl" width="15%">允许的充值来源:</td>
			<td class="vtd_cnt" width="35%">
				<@enumCheckBox enumNm="TxnEnums.AllowedReqSrc" name="allowedReqSrc" checkVals="${(allowedReqSrc)!''}" readOnly="true"/>
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl" width="15%">允许的代付来源:</td>
			<td class="vtd_cnt" width="35%">
				<@enumCheckBox enumNm="TxnEnums.AllowedReqSrc" name="allowedReqSrcWd" checkVals="${(allowedReqSrcWd)!''}" readOnly="true"/>
			</td>
			<td class="vtd_lbl" width="15%">域名:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.trustDomain}
			</td>
		</tr>
		
		<tr>
			<td class="vtd_lbl" width="15%">所属机构:</td>
			<td class="vtd_cnt" width="35%">
				${(organId)!''}|${organDesc!''}
			</td>
			<td class="vtd_lbl" width="15%">备注:</td>
			<td class="vtd_cnt" width="35%" colspan="3">
				${contentObj.comment}
			</td>
		</tr>
	</table>
</div>