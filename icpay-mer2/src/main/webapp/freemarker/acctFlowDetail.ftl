<div class="modal-dialog1" >
	<div class="modal-content">
		<div class="modal-header1" >
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<b><@msg code='acctFlowDetail.资金流水明细' default='资金流水明细'/></b>
			<#-- <b><@msg code='acctFlowDetail.账户明细' default='账户明细'/></b> -->
		</div>
		
		<div class="modal-body">
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
			<button type="button" class="btn btn-default" data-dismiss="modal"><@msg code='acctFlowDetail.关闭' default='关闭'/></button>
		</div>
	</div>
</div>