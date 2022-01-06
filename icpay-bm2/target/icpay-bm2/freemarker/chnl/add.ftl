<#include "/common/add_macro.ftl" />
<#assign label = "渠道信息">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="chnl">
			$("#chnlIdInpt").validatebox({
				required: true,
				novalidate: true
			});
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
		<@addDiv label="${label}" addUrl="/chnl/add/submit.do">
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">渠道代码：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="120" maxLength="2"
							id="chnlIdInpt" name="chnlId"/>
					</td>
				</tr>
				<tr>
					<td class="label">渠道名称：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="120"
							id="chnlDescInpt" name="chnlDesc" />
					</td>	
				</tr>
				<#--<tr>
					<td class="label">费率级别：</td>
					<td class="content">
						<select id="feeLevelSel" name="feeLevel" data-options="editable:false">
							<@enumOpts enumNm='CommonEnums.ChnlFeeLevel' showCode='true' 
								showEmptyOpt="false" />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">单笔限额(元)：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="13"
							id="transLimitInpt" name="transLimit" />
					</td>
				</tr>
				<tr>
					<td class="label">当日累计限额(元)：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="15"
							id="dayTransLimitInpt" name="dayTransLimit" />
					</td>
				</tr>
				
				<tr>
					<td class="label">支持银行：</td>
					<td class="content">
						<textarea class="easyui-validatebox" id="" name="" row="5" col="16">
						</textarea>	
					</td>
					
				</tr>
				
				-->
				
				<tr>
					<td class="label">运营条件：</td>
					<td class="content">
						<textarea rows="8" cols="100"  
							id="operateConditions" name="operateConditions" placeholder="运营条件"></textarea>
						<!-- <input class="easyui-validatebox" type="text" maxlength="1024"
							id="operateConditionsInpt" name="operateConditions" /> -->
					</td>
				</tr>
				
				<tr>
					<td class="label">交易权限：</td>
					<td class="content">
						<@enumCheckBox enumNm="TxnEnums.TxnType" name="chnlTxnTypes" />
					</td>
				</tr>
			</table>
		</@addDiv>
	</body>
</html>