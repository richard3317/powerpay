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
			<td class="vtd_lbl" width="15%">中文简称:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.mchntCnAbbr}
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl" width="15%">英文名称:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.mchntEnNm}
			</td>
			<td class="vtd_lbl" width="15%">英文简称:</td>
			<td class="vtd_cnt" width="35%">
				${(contentObj.mchntEnAbbr)!''}
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl" width="15%">商户类型:</td>
			<td class="vtd_cnt" width="35%">
				${mchntTpDesc}
			</td>
			<td class="vtd_lbl" width="15%">代理商:</td>
			<td class="vtd_cnt" width="35%">
				${(contentObj.agentCd)!''}&nbsp;${agentCnNm!''}
			</td>
		</tr>
		<#--<tr>
			<td class="vtd_lbl" width="15%">行业类别:</td>
			<td class="vtd_cnt" width="35%">
				<@dataDicVal dataTp="TRADE_TYPE" dataKey="${(contentObj.tradeType)!''}" includeKey='true' />
			</td>
			<td class="vtd_lbl" width="15%">风险标识:</td>
			<td class="vtd_cnt" width="35%">
				<@dataDicVal dataTp="MER_RISK_FLG" dataKey="${(contentObj.riskFlag)!''}" includeKey='true' />
			</td>
		</tr>-->
		<tr>
			<#-- <td class="vtd_lbl" width="15%">是否自主清算商户:</td>
			<td class="vtd_cnt" width="35%">
				<@enumVal enumNm="MchntStCdEnums.SelfSettle" code="${mchntStCd.selfSettle}" showCode="true" />
			</td>
			-->
			<td class="vtd_lbl" width="15%">商户状态:</td>
			<td class="vtd_cnt" width="35%">
				<@enumVal enumNm="CommonEnums.MchntSt" code="${contentObj.mchntSt}" showCode="true" />
			</td>
			 <td class="vtd_lbl" width="15%">联系人:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.contactPerson}
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl" width="15%">商户地区:</td>
			<td class="vtd_cnt" width="35%">
				${areaCdDesc}
			</td>
			<td class="vtd_lbl" width="15%">详细地址:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.mchntAddr}
			</td>
		</tr>
		<#--<tr>
			<td class="vtd_lbl" width="15%">邮编:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.zipCd}
			</td>
		</tr>-->
		<tr>
			<td class="vtd_lbl" width="15%">联系电话:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.contactPhone}
			</td>
			<td class="vtd_lbl" width="15%">电子邮箱:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.contactMail}
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl" width="15%">商户交易权限组:</td>
			<td class="vtd_cnt" width="35%">
				${transTpGroupDesc}
			</td>
			<td class="vtd_lbl" width="15%">失效日期:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.expiredDt}
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl" width="15%">允许的充值来源:</td>
			<td class="vtd_cnt" width="35%">
				<@enumCheckBox enumNm="TxnEnums.AllowedReqSrc" name="allowedReqSrc" checkVals="${(allowedReqSrc)!''}" readOnly="true"/>
			</td>
			<td class="vtd_lbl" width="15%">允许的代付来源:</td>
			<td class="vtd_cnt" width="35%">
				<@enumCheckBox enumNm="TxnEnums.AllowedReqSrc" name="allowedReqSrcWd" checkVals="${(allowedReqSrcWd)!''}" readOnly="true"/>
			</td>
		</tr>
		<tr>
			<td class="vtd_lbl" width="15%">机构:</td>
			<td class="vtd_cnt" width="35%">
				${(organId)!''}|${organDesc!''}
			</td>
		</tr>
		<tr>
			<#--<td class="vtd_lbl" width="15%">传真:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.fax}
			</td>-->
			<td class="vtd_lbl" width="15%">备注:</td>
			<td class="vtd_cnt" width="35%">
				${contentObj.comment}
			</td>
		</tr>
	</table>
</div>