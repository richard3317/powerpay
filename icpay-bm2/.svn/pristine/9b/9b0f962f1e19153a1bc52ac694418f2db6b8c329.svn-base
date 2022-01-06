<div class="easyui-panel" title="审核内容-代理商基本信息" style="padding:10px;">
	<table class="view_tbl" cellpadding="0" cellspacing="0">
		<#if entity.opTp == '03' || entity.opTp == '05'>
		<tr>
			<td class="vtd_lbl" width="15%">代理商代码:</td>
			<td class="vtd_cnt" width="85%" colspan="3">
				${contentObj.agentCd}
			</td>
		</tr>
		</#if>
		<tr>
			<td class="vtd_lbl" width="15%">中文名称:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.agentCnNm}
			</td>
			<td class="vtd_lbl" width="15%">中文简称:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.agentCnAbbr}
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl">英文名称:</td>
			<td class="vtd_cnt">
				${contentObj.agentEnNm}
			</td>
			<td class="vtd_lbl">英文简称:</td>
			<td class="vtd_cnt">
				${(contentObj.agentEnAbbr)!''}
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl">商户地区:</td>
			<td class="vtd_cnt">
				${areaCdDesc}
			</td>
			<td class="vtd_lbl">详细地址:</td>
			<td class="vtd_cnt">
				${contentObj.agentAddr}
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl">联系人1:</td>
			<td class="vtd_cnt">
				${contentObj.contactPerson1}
			</td>
			<td class="vtd_lbl">联系电话1:</td>
			<td class="vtd_cnt">
				${contentObj.contactPhone1}
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl">电子邮箱1:</td>
			<td class="vtd_cnt">
				${contentObj.contactMail1}
			</td>
			<td class="vtd_lbl">联系人2:</td>
			<td class="vtd_cnt">
				${contentObj.contactPerson2}
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl">联系电话2:</td>
			<td class="vtd_cnt">
				${contentObj.contactPhone2}
			</td>
			<td class="vtd_lbl">电子邮箱2:</td>
			<td class="vtd_cnt">
				${contentObj.contactMail2}
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl">邮编:</td>
			<td class="vtd_cnt">
				${contentObj.zipCd}
			</td>
			<td class="vtd_lbl">传真:</td>
			<td class="vtd_cnt">
				${contentObj.fax}
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl" width="15%">代理商状态:</td>
			<td class="vtd_cnt" width="85%" colspan="3">
				<@enumVal enumNm="CommonEnums.AgentSt" code="${contentObj.agentSt}" showCode="true" />
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl">备注:</td>
			<td class="vtd_cnt" width="85%" colspan="3">
				${contentObj.comment}
			</td>
		</tr>
	</table>
</div>