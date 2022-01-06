<#include "/common/add_macro.ftl" />
<#assign label="名单配置信息" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="riskListItem">
			$("#itemInpt").validatebox({
				required: true,
				novalidate: true,
				validType: 'maxLength[254]'
			});
			
			var lastSelectedItem=null;
			$('#groupIdInpt').on('change', function(){
				var selectedItem= $('#groupIdInpt').val();
				if (isEmpty(selectedItem))
					onSelEmpty();
				else {
					if (lastSelectedItem != selectedItem)
						onSelectedChanged(selectedItem);
				}
				lastSelectedItem = selectedItem;
			});
			
			function onSelEmpty() {
				$("#groupComment").text("");
			}
			
			function onSelectedChanged(selectedItem) {
				if (isEmpty(selectedItem))
					selectedItem= $('#groupIdInpt').val();
				$("#groupComment").text("");
				$.ajax({
					type : "GET",
					url : _ctx + "/riskListItem/getRiskGroup.do?groupId="+selectedItem,
					async: true,
					cache: true,
					<#-- data: $("#qryForm").serialize(), -->
					success : function(data) {
						//console.log(data);
						var res = parseJson(data);
						if (!checkEmpty(res, "系统异常，请联系管理员！")) return false;
						if (res.respCode != "00"){
							alert("操作失败: "+res.respMsg);
							return false;
						}
						if (isEmpty(res.respData)) return;
						if (isEmpty(res.respData.selectedGroup)) return;
						if (isEmpty(res.respData.selectedGroup.comment)) return;
						$("#groupComment").text(res.respData.selectedGroup.comment);
					},
					error:function(){
				    	alert("系统异常，请联系管理员！");
				    	return  false;
				    }
				});				
			}
			
			onSelectedChanged(null);	
						
		</@head>
	</head>
	
	<body>
		<@addDiv label="${label}" addUrl="/riskListItem/add/submit.do">
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<#-- 
				<tr>
					<td class="label">风控组：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" readonly="readonly"
							id="groupDesc" name="groupDesc" value="${groupDesc!''}" >
					</td>
				</tr>
				-->
				<tr>
					<td class="label">风控组：</td>
					<td class="content">
                        <select id="groupIdInpt" name="groupId" class="easyui-validatebox" value="${groupId!''}" placeholder="请选择风控组" >
                            <#list riskGroups as item>
                                 <option value="${item.groupId}"
                                 	<#if item.groupId==(groupId!'')> selected='selected' </#if>
                                 	>
                                 	${item.groupId}-${item.groupNm}</option>
                            </#list>
                        </select>
						<span id="groupComment" class="inputDesc"></span>
					</td>
				</tr>
				<tr>
					<td class="label" >渠道：</td>
					<td class="content">
						<select id="chnlIdInpt" name="chnlId" value="00">
							<option value="00">(小商户)</option>
							<@enumOpts enumNm='TxnEnums.ChnlId' showCode='true' showEmptyOpt='false' selected="00" />
						</select>
					</td>					
				</tr>
				<tr>
					<td class="label">商户号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text"
							id="mchntCdInpt" name="mchntCd" value="">
					</td>
				</tr>				
				<tr>
					<td class="label">风控项目内容：</td>
					<td class="content">
						<textarea cols="80" rows="10" class="easyui-validatebox" maxlength="254"
							id="itemsInpt" name="items" placeholder="可输入多个项目，每行一个项目" ></textarea>
					</td>
				</tr>
				<tr>
					<td class="label">备注：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text"
							id="commentInpt" name="comment" value="" >
					</td>
				</tr>
			</table>
		</@addDiv>	
	</body>
</html>