<#include "/common/edit_macro.ftl" />
<#assign label = "渠道信息">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="chnl">
			$("#chnlDescInpt").validatebox({
				required: true,
				novalidate: true,
				validType: 'cnMaxLength[120]'
			});
			$("#feeLevelInpt").validatebox({
				required: true,
				novalidate: true,
				validType: ['number', 'maxLength[4]']
			});
			$("#transLimitInpt").validatebox({
				required: true,
				novalidate: true,
				validType: ['amount', 'maxLength[13]']
			});
			$("#dayTransLimitInpt").validatebox({
				required: true,
				novalidate: true,
				validType: ['amount', 'maxLength[15]']
			});
		</@head>
	</head>
	
	<body>
		<@editDiv label="${label}" editUrl="/chnl/edit/submit.do">
			<input type="hidden" name="chnlId" value="${entity.chnlId}" />
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">渠道代码：</td>
					<td class="content">
						${entity.chnlId}
					</td>
				</tr>
				<tr>
					<td class="label">渠道名称：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="120"
							id="chnlDescInpt" name="chnlDesc" value="${entity.chnlDesc}" />
					</td>
				</tr>
				
				<tr>
					<td class="label">状态：</td>
					<td class="content">
						<select id="statelSel" name="state" data-options="editable:false">
							<@enumOpts enumNm='CommonEnums.ChnlSt' showCode='true' 
								showEmptyOpt="false" selected="${entity.state}" />
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="label">运营条件：</td>
					<td class="content">
						<textarea rows="8" cols="100"  
							id="operateConditions" name="operateConditions" placeholder="运营条件">${entity.operateConditions}</textarea>
					</td>
				</tr>
				
				<tr>
					<td class="label">交易权限：</td>
					<td class="content">
						<@enumCheckBox enumNm="TxnEnums.TxnType" name="chnlTxnTypes" 
							checkVals="${(entity.transType)!''}" />
					</td>
				</tr>
				<#--<tr>
					<td class="label">支持的银行范围：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="120"
							id="" name="" value="" />
					</td>
				</tr>
				<tr>
					<td class="label">支持银行：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="120"
							id="" name="" value="" />
					</td>
				</tr>-->
			</table>
		</@editDiv>
	</body>
</html>