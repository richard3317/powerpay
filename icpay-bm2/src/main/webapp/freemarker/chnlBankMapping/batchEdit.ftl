<#include "/common/edit_macro.ftl" />
<#assign label = "渠道银行编号管理">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="chnlBankMapping" editSuccMsg="批量修改完成">

		</@head>
	</head>
	
	<body>
		<@editDiv label="${label}" editUrl="/chnlBankMapping/batchEditSubmit.do">
			<#-- hidden inputs -->
			${queryParamInps!''} 
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">状态：</td>
					<td class="content">
						<select id="stateSel" name="state" data-options="editable:false">
							<option value="">--不修改---</option>
							<option value="1">开启</option>
							<option value="0">关闭</option>
						</select>
					</td>
				</tr>
			</table>
		</@editDiv>
	</body>
</html>