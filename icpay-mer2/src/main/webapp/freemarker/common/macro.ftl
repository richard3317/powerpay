<#assign ctx = request.contextPath>
<#assign timeNow = .now>
<#assign strNow = timeNow?string["yyyyMMddHHmmss"]>
<#assign strNowMm = timeNow?string["yyyyMMddHHmm"]>

<#macro succMsg>
	<div class="alert alert-succ">
		<a class="close" data-dismiss="alert">×</a>
		<#nested>
	</div>
</#macro>

<#macro errMsg>
	<div class="alert alert-error">
		<a class="close" data-dismiss="alert">×</a>
		<#nested>
	</div>
</#macro>

<#macro infoMsg>
	<div class="alert alert-info">
		<a class="close" data-dismiss="alert">×</a>
		<#nested>
	</div>
</#macro>

<#macro modal modalId title="" customBtns="">
	<div class="modal fade" id="${modalId}" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<#if title??>
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<span>${title}</span>
					</div>
				</#if>

				<div class="modal-body"><#nested /></div>

				<div class="modal-footer">
					<#if customBtns??>
						${customBtns}
					</#if>
					<button type="button" class="btn btn-default" data-dismiss="modal"><@msg code='macro.关闭' default='关闭'/></button>
				</div>
			</div>
		</div>
	</div>
</#macro>
