<#include "/common/edit_macro.ftl" />
<#assign label = "代付路由信息">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="routDaifu" editSuccMsg="批量修改完成">

		</@head>
	</head>
	
	<body>
		<@editDiv label="${label}" editUrl="/routDaifu/batchEditSubmit.do">
			<#-- hidden inputs -->
			${queryParamInps!''} 
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
			    <#--  
				<tr>
					<td class="label">前端商户号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="15"
							id="mchntCdInpt" name="mchntCd" value="${entity.mchntCd}"  readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td class="label">渠道：</td>
					<td class="content">
						<select id="chnlIdSel" name="chnlId">
							<@enumOpts enumNm='TxnEnums.ChnlId' showEmptyOpt='true' showCode="true" />
						</select>							
					</td>
				</tr>
				<tr>
					<td class="label">渠道商户号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40"
							id="mchntCdInpt" name="chnlMchntCd" />
					</td>
				</tr>
				-->
				<#--  
				<tr>
					<td class="label">交易类型：</td>
					<td class="content">
					<input class="easyui-validatebox" type="text" maxlength="15"
							id="mchntCdInpt" name="intTransCd" value="${entity.intTransCd}"  readonly="readonly" />
					</td>
				</tr>
				-->
				<tr>
					<td class="label">优先级：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="128"
							id="" name="priority" />
						<span class="inputDesc">空白表示不修改</span>
					</td>
				</tr>
				<#--  
				<tr>
					<td class="label">权重：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="128"
							id="" name="weight" value="${entity.weight}"/>
					</td>
				</tr>
				-->
				<tr>
					<td class="label">状态：</td>
					<td class="content">
						<select id="statusSel" name="status" data-options="editable:false">
							<option value="">--不修改---</option>
							<option value="1">开启</option>
							<option value="0">关闭</option>
						</select>
						<span class="inputDesc">空白表示不修改</span>
					</td>
				</tr>
			</table>
		</@editDiv>
	</body>
</html>