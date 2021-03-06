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
			<td class="vtd_cnt" width="85%" colspan="3">
				${contentObj.comment}
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl" width="15%">站点:</td>
			<td class="vtd_cnt" width="85%" colspan="3">
				${siteIdDesc!''}
			</td>
		</tr>
	</table>
</div>
<div class="easyui-panel" title="审核内容-清算信息设置" style="padding:10px;">
	<table class="view_tbl" cellpadding="0" cellspacing="0">
		<tr>
			<td width="10%">币别</td>
            <td width="10%">清算周期</td>
            <td width="10%">D0结转</td>
            <td width="10%">D0结转时间</td>
            <td width="10%">T1反还</td>
            <td width="10%">前置T1返还时间</td>
            <td width="10%">前置T1返还比例</td>
            <td width="10%">后置T1返还时间</td>
		</tr>
		<#list settleResult as sr>
			<tr>
				<td class="vtd_lbl">${sr['currCdDesc']!''}</td>
				<td class="vtd_lbl">${sr['settlePeriodDesc']!''}</td>
				<td class="vtd_lbl">${sr['balanceTransferDesc']!''}</td>
				<td class="vtd_lbl">${sr['transferTime']!''}</td>
				<td class="vtd_lbl">${sr['balanceTransferT1Desc']!''}</td>
				<td class="vtd_lbl">${sr['preTransferTimeT1']!''}</td>
				<td class="vtd_lbl">${sr['preTransferT1Percent']!''}</td>
				<td class="vtd_lbl">${sr['transferTimeT1']!''}</td>
			</tr>
		</#list>
	</table>
</div>
<div class="easyui-panel" title="审核内容-支付方式配置" style="padding:10px;">
	<table class="view_tbl" cellpadding="0" cellspacing="0">
		<tr>
            <td width="10%">交易类型</td>
            <td width="10%">币别</td>
            <td width="10%">计费模式</td>
            <td width="10%">垫资比例</td>
            <td width="30%">费率计费方式</td>
            <td width="40%">交易限制</td>
		</tr>
		<#list result as r>
			<tr>
				<td class="vtd_lbl">${r['intTransCdDesc']!''}</td>
				<td class="vtd_lbl">${r['currCdDesc']!''}</td>
				<td class="vtd_lbl">${r['settleModeDesc']!''}</td>
				<td class="vtd_lbl">${r['txT0Percent']!''}</td>
				<td class="vtd_lbl">${r['settleAlgorithmDesc']!''}</td>
				<td class="vtd_lbl">${r['settleAlgorithmLimit']!''}</td>
			</tr>
		</#list>
	</table>
</div>