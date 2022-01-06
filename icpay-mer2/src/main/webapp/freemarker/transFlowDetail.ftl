<#include "/common/macro.ftl">
<div class="modal-dialog1" id="model">
	<div class="modal-content">
		<div class="modal-header1">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<b><@msg code='transFlowDetail.交易详情' default='交易详情'/></b>
		</div>
		
		<div class="modal-body">
			<input type="hidden" id="transSeqIdSel" name="transSeqId" value="${entity.transSeqId}" />
			<input type="hidden" id="transTmInp" name="transTm" value="${entity.transTm}" />
			<input type="hidden" id="extTransDtInpt" name="extTransDt" value="${entity.extTransDt}" />
			<#list detailConfLst as detailConf>
				<div style="clear: both;">
					<label class="col-sm-3 control-label">${detailConf.label}:</label>
					<div class="col-sm-7">
						${entity['${detailConf.field}']!''}
					</div>
				</div>
			</#list>
			<#if proof == "noImage">
				<form id="formPay" action="${ctx}/trans/uploadImage.do" method="POST" target="_blank" class="form-horizontal" role="form" enctype="multipart/form-data">
					<div style="clear: both;">
	                	<label class="col-sm-3 control-label" style="text-align: left;"><@msg code='transFlowDetail.上传汇款凭证' default='上传汇款凭证'/>:</label>
	                	<div class="col-sm-7">
	                		<input type="file" id="uploadImage" name="uploadImage" accept="image/*">
	                	</div>
	            	</div>
	            	<input type="hidden" id="transSeqIdSel" name="transSeqId" value="${entity.transSeqId}" />
				</form>
			</#if>
		</div>
		<div style="clear: both;"></div>
		<div class="modal-footer" style="margin-top: 10px;">
			<#if proof == "noImage">
				<button id ="uploadImageBtn" type="button" class="btn btn-info" onclick="uploadImage();" ><@msg code='transFlowDetail.上传凭证' default='上传凭证'/></button>&nbsp;
			</#if>
			<button id ="btnResendNotify" type="button" class="btn btn-primary" onclick="resendNotify();" ><@msg code='transFlowDetail.补发通知' default='补发通知'/></button>&nbsp;
			<button type="button" class="btn btn-default" data-dismiss="modal"><@msg code='transFlowDetail.关闭' default='关闭'/></button>
			<#--<#if entity.intTransCd == '0100' && entity.rspCd == '0000000' >
			<button id="refundBtn" type="button" class="btn btn-primary" onclick="refund();">退货</button>
			</#if>-->
		</div>
	</div>
</div>
