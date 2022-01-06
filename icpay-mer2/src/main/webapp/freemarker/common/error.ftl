<#include "/common/macro.ftl">
<#assign htmlTitle="错误页面" />

<#assign htmlContent>
	<div class="panel panel-default">
		<div class="panel-heading">
			<h4 class="panel-title"><@msg code='error.错误信息' default='错误信息'/></h4>
		</div>
		<div class="panel-body">
			<div style="height: 40px; line-height: 40px; color: red; text-align: center;">${errorMsg!""}</div>
		</div>
	</div>
</#assign>

<#include "/common/layout.ftl" />