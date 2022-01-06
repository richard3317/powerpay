<div class="easyui-panel" title="审核内容-路由信息" style="padding:10px;">
	<table class="view_tbl" cellpadding="0" cellspacing="0">
		<tr>
			<td class="vtd_lbl" width="15%">商户代码:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.mchntCd}
			</td>
			<td class="vtd_lbl" width="15%">交易类型:</td>
			<td class="vtd_cnt" width="35%">
				<@enumVal enumNm="TxnEnums.TxnType" code="${contentObj.intTransCd}" showCode="true" />
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl" width="15%">优先级:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.priority}
			</td>
			<td class="vtd_lbl" width="15%">权重:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.weight}
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl" width="15%">状态:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.status}
			</td>
		</tr>
		<#-- <tr>
			<td class="vtd_lbl" width="15%">终端序号表达式:</td>
			<td class="vtd_cnt" colspan="3">
				${contentObj.termSnRegex}
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl" width="15%">首选通道:</td>
			<td class="vtd_cnt" colspan="3">
				<@enumVal enumNm='TxnEnums.ChnlId' code="${contentObj.chnlId1}" showCode="true" />
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl" width="15%">备选通道1:</td>
			<td class="vtd_cnt" colspan="3">
				<@enumVal enumNm='TxnEnums.ChnlId' code="${(contentObj.chnlId2)!''}" showCode="true" />
			</td>
		</tr> -->
	</table>
</div>