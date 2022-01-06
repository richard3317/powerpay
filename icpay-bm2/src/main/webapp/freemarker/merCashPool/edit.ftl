<#include "/common/edit_macro.ftl" />
<#assign label = "小商户资金池信息">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="merCashPool">
			$("#poolDescInpt").validatebox({
				required: true,
				novalidate: true,
				validType: 'cnMaxLength[120]'
			});
			
			<#-- $("#saveBtn").click(function() {
				var poolId= $("#poolId").val();
				var newPoolId= $("#newPoolId").val();
				var state= $("#state").val();
				var newState= $("#newState").val();
				
				if(poolId == newPoolId && state == newState){
					alert("数据无修改，不允许更新！");
				}else{
					if (confirm("确认保存?")) {
						$("#editForm").form("enableValidation").submit();
					}
				}
			});
			$("#editForm").form({
				url: $("#editForm").attr("action"),
				onSubmit: function() {
					return $(this).form("validate");
				},
				success: function(data) {
					$.processAjaxResp(data, function(r) {
						var respMsg = r.respMsg;
						if ("OK"!= respMsg){
							alert(respMsg);
						}else{
							alert("修改小商户资金池信息成功");
						}
						$.jumpTo(baseUrl + "backToManage.do");
					});
				}
			});
			
			$("#backBtn").click(function() {
				$.jumpTo(baseUrl + "backToManage.do");
			});
			-->
		</@head>
	</head>
	
	<body>
		 <@editDiv label="${label}" editUrl="/merCashPool/edit/submit.do">
		<#--<div id="editDiv" class="easyui-panel panel-body" title="" style="padding: 10px; width: 1175px;">
			<form id="editForm" method="post" action="${ctx}/merCashPool/edit/submit.do"> -->
				<input type="hidden" name="mchntCd" value="${entity.mchntCd}" />
				<input type="hidden" name="poolId" value="${entity.poolId}" />
				<input type="hidden" name="state" value="${entity.state}" />
				<input type="hidden" name="currCd" value="${entity.currCd}" />
				<table class="edit_tbl" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label">商户代码：</td>
						<td class="content">
							${entity.mchntCd}
						</td>
					</tr>
					<tr>
						<td  class="label">币别：</td>
							<td class="content">
							${entity.currCd}
						</td>
					</tr>
					<tr>
						<td class="label">资金池名称：</td>
						<td class="content">
							<select class="easyui-validatebox"
								id="newPoolId" name="newPoolId" 
								style="width: 200px; height: 27px; " >
								<option value="">--请选择--</option>
								<#list poolList as lst>
									<#if (lst.poolId==((entity.poolId)!''))>
										<option value="${lst.poolId}" selected="selected">${lst.poolDesc}-${lst.currCd}</option>
									<#else>	
										<option value="${lst.poolId}">${lst.poolDesc}-${lst.currCd}</option>
									</#if>	
								</#list>
							</select>
						</td>	
					</tr>
					<tr>
						<td class="label">状态：</td>
						<td class="content">
							<select id="newState" name="newState" data-options="editable:false">
								<@enumOpts enumNm='CommonEnums.RecSt' showCode='true' 
									showEmptyOpt="false" selected="${entity.state}" />
							</select>
						</td>
					</tr>
					
				</table>
			</form>
			<#-- <div style="margin: 10px; padding-left: 150px;">
				<a id="saveBtn" href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-small c2" group=""><span class="l-btn-left"><span class="l-btn-text">保存</span></span></a>
				<a id="backBtn" href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-small c2" group=""><span class="l-btn-left"><span class="l-btn-text">返回</span></span></a> 
			</div>
		</div>-->
		</@editDiv>
	</body>
</html>