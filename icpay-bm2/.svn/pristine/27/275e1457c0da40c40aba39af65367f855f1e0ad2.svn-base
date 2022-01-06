<#include "/common/edit_macro.ftl" />
<#assign label = "机构信息修改">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="organ">
			$("#backBtn").click(function() {
				$.jumpTo(baseUrl + "backToManage.do");
			});
			
		</@head>
	</head>
	
	<body>
		 <@editDiv label="${label}" editUrl="/organ/edit/submit.do">
				<input type="hidden" name="organId" value="${organId}" />
				<table class="edit_tbl" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label">机构代码：</td>
						<td class="content">
							${organId}
						</td>
					</tr>
					<tr>
						<td class="label">机构名称：</td>
						<td class="content">
							<input class="easyui-validatebox" type="text" maxlength="200"
								id="organDescInpt" name="organDesc" value="${organDesc}"/>
						</td>	
					</tr>
					<tr>
						<td class="label">机构状态：</td>
						<td class="content">
							<select id="stateSel" name="state" data-options="editable:false">
								<@enumOpts enumNm='CommonEnums.RecSt' showCode='true' 
									showEmptyOpt="false" selected="${state}" />
							</select>
						</td>
					</tr>
					
				</table>
			</form>
		</@editDiv>
	</body>
</html>