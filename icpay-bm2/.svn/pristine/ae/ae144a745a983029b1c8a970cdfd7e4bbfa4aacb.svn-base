<#include "/common/add_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="小商户资金池批量配置" base="merCashPool" addSuccMsg="批量新增完成">
			$("#mchntCdsListInpt").validatebox({
				required: true,
				novalidate: true,
				validType: ['maxLength[15000]']
			});
			
			$("#chnlId").validatebox({
				required: true,
				novalidate: true,
				validType: ['maxLength[8]']
			});	
			
		</@head>	
	</head>
	
	<body>
		<@addDiv label="小商户资金池批量配置" addUrl="/merCashPool/batchAddSubmit.do">
			<input type="hidden" id="weightInp" name="weight" value="100" />
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">前端商户号*：</td>
					<td class="content">
						<textarea cols="50" rows="10" class="easyui-validatebox" maxlength="15000"
							id="mchntCdsListInpt" name="mchntCdsList"  
							placeholder="可输入多个商户号,以「换行」区隔,允许多行输入" >${mchntCdsList!''}</textarea>							
						<span class="inputDesc">可输入多个项目以「换行」区隔，允许多行输入</span>
					</td>
				</tr>
				<tr>
					<td class="label">资金池名称：</td>
					<td class="content">
						<select id="poolId" name="poolId">
							<option value="">--请选择--</option>						
							<#list poolList as lst>
								<#if (lst.poolId==((qryParamMap.poolId)!''))>
									<option value="${lst.poolId}" selected="selected">${lst.poolDesc}-${lst.currCd}</option>
								<#else>	
									<option value="${lst.poolId}">${lst.poolDesc}-${lst.currCd}</option>
								</#if>	
							</#list>						
						</select>
					</td>
				</tr>
			</table>
		</@addDiv>
	</body>
</html>