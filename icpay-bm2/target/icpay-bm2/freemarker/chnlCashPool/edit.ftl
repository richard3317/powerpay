<#include "/common/edit_macro.ftl" />
<#assign label = "资金池配置信息">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="chnlCashPool">
			
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
			-->
			$("#backBtn").click(function() {
				$.jumpTo(baseUrl + "backToManage.do");
			});
			
		</@head>
	</head>
	
	<body>
		 <@editDiv label="${label}" editUrl="/chnlCashPool/edit/submit.do">
		<#--<div id="editDiv" class="easyui-panel panel-body" title="" style="padding: 10px; width: 1175px;">
			<form id="editForm" method="post" action="${ctx}/merCashPool/edit/submit.do"> -->
				<input type="hidden" name="chnlMchntCd" value="${entity.chnlMchntCd}" />
				<input type="hidden" name="poolId" value="${entity.poolId}" />
				<input type="hidden" name="chnlId" value="${entity.chnlId}" />
				<input type="hidden" name="poolCurrCd" value="${entity.poolCurrCd}" />
				<table class="edit_tbl" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label">渠道商户代码：</td>
						<td class="content">
							${entity.chnlMchntCd}
						</td>
					</tr>
					<tr>
						<td class="label">币别：</td>
						<td class="content">
							${entity.poolCurrCd}
						</td>
					</tr>
					<tr>
						<td class="label">渠道商户名称：</td>
						<td class="content">
							${entity.chnlMchntDesc}
						</td>
					</tr>
					<tr>
						<td class="label">资金池名称：</td>
						<td class="content">
							${entity.poolDesc}
						</td>	
					</tr>
					<tr>
					<td class="label">优先级：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="128"
							id="" name="priority" value="${entity.priority}"/>
					</td>
				</tr>
					<tr>
						<td class="label">优先时段：</td>
						<td class="content">
							<input class="easyui-validatebox" type="text" maxlength="20"
								id="txTimeLimitInpt" name="txTimeLimit" value="${entity.txTimeLimit}"/>
							<span class="inputDesc">例： "0800-1200,1400-2000”或“不设置” ，空值=不设置。</span>
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