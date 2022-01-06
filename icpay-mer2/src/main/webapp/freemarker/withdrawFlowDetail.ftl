<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header1">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<b><@msg code='withdrawFlowDetail.取现详情' default='取现详情'/></b>
		</div>
		
		<div class="modal-body">
			<input type="hidden" id="transSeqIdSel" name="transSeqId" value="${entity.transSeqId}" />
			<input type="hidden" id="extTransDtInpt" name="extTransDt" value="${entity.extTransDt}" />
			<#list detailConfLst as detailConf>
				<div style="clear: both;">
					<label class="col-sm-3 control-label">${detailConf.label}:</label>
					<div class="col-sm-7">
						${entity['${detailConf.field}']!''}
					</div>
				</div>
			</#list>
		</div>
		<div style="clear: both;"></div>
		<div class="modal-footer" style="margin-top: 10px;">
			<button id ="btnResendNotify" type="button" class="btn btn-primary" onclick="resendNotify();" ><@msg code='withdrawFlowDetail.补发通知' default='补发通知'/></button>&nbsp;
			<button type="button" class="btn btn-default" data-dismiss="modal"><@msg code='withdrawFlowDetail.关闭' default='关闭'/></button>
		</div>		
	</div>
</div>