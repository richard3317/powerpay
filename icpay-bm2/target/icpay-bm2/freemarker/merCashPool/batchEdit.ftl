<#include "/common/edit_macro.ftl" />
<#assign label = "小商户资金池配置">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="merCashPool" editSuccMsg="批量修改完成">

		</@head>
	</head>
	
	<body>
		<@editDiv label="${label}" editUrl="/merCashPool/batchEditSubmit.do">
			${queryParamInps!''} 
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
			   
				<tr>
					<td class="label">资金池名称：</td>
					<td class="content">
						<select id="poolId" name="poolId">
							<option value="">--不修改---</option>						
							<#list poolList as lst>
								<#if (lst.poolId==((qryParamMap.poolId)!''))>
									<option value="${lst.poolId}" selected="selected">${lst.poolDesc}</option>
								<#else>	
									<option value="${lst.poolId}">${lst.poolDesc}</option>
								</#if>	
							</#list>						
						</select>
						<span class="inputDesc">空白表示不修改</span>
					</td>
				</tr>
				<tr>
					<td class="label">状态：</td>
					<td class="content">
						<select id="statusSel" name="state" data-options="editable:false">
							<option value="">--不修改---</option>
							<option value="1">有效</option>
							<option value="0">无效</option>
						</select>
						<span class="inputDesc">空白表示不修改</span>
					</td>
				</tr>
			</table>
		</@editDiv>
	</body>
</html>