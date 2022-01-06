<#include "/common/macro.ftl">

<#assign htmlContent>
	<ol class="breadcrumb">
		<li><a href="${ctx}/index"><@msg code='fundMng.商户服务' default='商户服务'/></a></li>
		<li class="active"><@msg code='fundMng.资金流水查询' default='资金流水查询'/></li>
	</ol>
		
	<div class="panel panel-info">
		<div class="panel-heading">
			<h4 class="panel-title"><a href="#"><@msg code='fundMng.资金流水查询' default='资金流水查询'/></a></h4>
		</div>
		<div class="panel-body">
			<form id="mer-acct-flow-qryfrm" action="${ctx}/mchntAcct/qryAcctFlow" method="post" class="form-horizontal">
				<#--  
				<label class="col-sm-2 control-label">起始日期</label>
				<div class="col-sm-2">
					<input type="text" name="operateDt" id="operateDt" class="form-control Wdate"  value="${today}"
						onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" style="height: 34px;" />
				</div>
				-->
				<div class="form-group">
					<label class="col-sm-2 control-label"><@msg code='fundMng.起始日期' default='起始日期'/>:</label>
					<div class="col-sm-2">
						<input type="text" id="startDt" name="startDt" class="form-control Wdate" value="${defaultStart!''}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd HHmmss',startDate:'%y%M%d 000000', maxDate:'#F{$dp.$D(\'endDt\')}' , readOnly:true})" style="height: 34px; width: 200px; float: left; margin-right: 20px;" />
					</div>
					<label class="col-sm-2 control-label"><@msg code='fundMng.结束日期' default='结束日期'/>:</label>
					<div class="col-sm-2">
						<input type="text" id="endDt" name="endDt" class="form-control Wdate" value="${defaultEnd!''}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd HHmmss',startDate:'%y%M%d 235959', minDate:'#F{$dp.$D(\'startDt\')}', readOnly:true})" style="height: 34px; width: 200px; float: left;" />
					</div>
					<label class="col-sm-2 control-label"><@msg code='fundMng.日期查询方式' default='日期查询方式'/>:</label>
					<div class="col-sm-2">
						<select class="form-control m-b" id="timeQryMethod" name="timeQryMethod" value="UpdTS" >
							<@enumOpts enumNm="MerEnums.TimeQryMethod" showEmptyOpt="false" showCode="false" selected="UpdTS"/>
						</select>
					</div>
				</div>
				<div class="form-group">
					<#-- <label class="col-sm-2 control-label">起始时间</label>
					<div class="col-sm-2">
						<input type="number" name="startTime" id="startTime" class="form-control" placeholder="格式:HHmmss或HHmm" style="width: 210px;" />
					</div>
					<label class="col-sm-2 control-label">结束时间</label>
					<div class="col-sm-2">
						<input type="number" name="endTime" id="endTime" class="form-control" placeholder="格式:HHmmss或HHmm" style="width: 210px;"
							data-toggle="tooltip" title="请注意：目前查询结果暂不支持跨月，若需跨月请区分两次查询。" 
							/>
					</div>-->
					<label class="col-sm-2 control-label"><@msg code='fundMng.操作类型' default='操作类型'/>:</label>
					<div class="col-sm-2">
						<select class="form-control m-b" name="operateTpCat" id="operateTpCat" style="margin-bottom: 0; width: 210px;">>
								<option value=''><@msg code='fundMng.(全部)' default='(全部)'/></option>
								<@enumOpts enumNm="MerEnums.AccOperTpCatalog" showEmptyOpt="false" showCode="false" />
						</select>
					</div>
					<label class="col-sm-2 control-label"><@msg code='fundMng.交易序号' default='交易序号'/>:</label>
					<div class="col-sm-2">
						<input type="text" name="transSeqId" id="transSeqId" class="form-control" style="width: 210px;" />
					</div>
					<label class="col-sm-2 control-label"><@msg code='fundMng.币别' default='币别'/>:</label>
					<div class="col-sm-2">
						<select class="form-control m-b" name="currCd" id="currCd" style="margin-bottom: 0; width: 210px;">>
								<option value=''><@msg code='fundMng.--请选择--' default='--请选择--'/></option>
								<option value="156" selected><@msg code='fundMng.人民币' default='人民币'/></option>
								<option value="704"><@msg code='fundMng.越南盾' default='越南盾'/></option>
								<option value="764"><@msg code='fundMng.泰铢' default='泰铢'/></option>
						</select>
					</div>
				</div>
				<#-- <div class="form-group">
					<label class="col-sm-2 control-label">交易序号</label>
					<div class="col-sm-2">
						<input type="text" name="transSeqId" id="transSeqId" class="form-control" style="width: 210px;" />
					</div>
				</div>-->
				</br>
				<div class="form-group">
					<label class="col-sm-2 control-label"></label>
					<div class="col-sm-2">
					    <#--  <div class="col-sm-3"></div> -->
						<a id="qryBtn" class="btn btn-primary btn-sm" href="#" role="button"><@msg code='fundMng.查询' default='查询'/></a>
						<a id="exportBtn" class="btn btn-primary btn-sm" href="#" role="button"><@msg code='fundMng.导出' default='导出'/></a>
						<a id="resetBtn" class="btn btn-default btn-sm" href="#" role="button"><@msg code='fundMng.重置' default='重置'/></a>
					</div>	
				</div>		
			</form>
			</br>
	    	<table id="mer-acct-flow-tbl" width="100%" class="tbl-info"></table>
	    	<div id="mer-acct-flow-tbl-pagination" class="pagination"></div>
		</div>
	</div>
	
	<@modal modalId="acct-flow-detail-modal" title="资金流水详情">
	</@modal>
</#assign>

<#assign htmlJS>
	$(function() {
		$('[data-toggle="tooltip"]').tooltip(); 
		
		<#-- if(${secretFlag} == false && ${loginType} =='0'){
			alert("请管理员先设置安全设置");
			$.jumpTo("${ctx}/secrity/secritySetting");
		} -->
		
		$("#monSel").monthSel();
				
		function initQryFrm() {
			$("#mer-acct-flow-qryfrm").qryFrm("mer-acct-flow-tbl", "mer-acct-flow-tbl-pagination", {
				resultHandlers : [
					{
						"btnTxt" : "<@msg code='fundMng.详情' default='详情'/>",
						"btnHandler" : function(row) {
							var seqId = row.seqId;
							<#--  var operateDt = $("#operateDt").val(); 
							var startDate = $("#startDate").val();
							var endDate = $("#endDate").val();-->
							var transDt = row.transDt;
							var transTm = row.recCrtTs;
							transTm = transTm.substring(0,10);
							<#-- var startTime = $("#startTime").val();
							var endTime = $("#endTime").val(); -->
							
					        $('#acct-flow-detail-modal').modal({
								keyboard: true,
								<#-- remote : "${ctx}/mchntAcct/acctFlowDetail?seqId=" + seqId + "&operateDt="+ operateDt -->
								<#-- remote : "${ctx}/mchntAcct/acctFlowDetail?seqId=" + seqId + "&startDate="+ startDate + "&startTime="+ startTime  + "&endDate="+ endDate + "&endTime="+ endTime -->
								remote : "${ctx}/mchntAcct/acctFlowDetail?seqId=" + seqId + "&transDt="+ transDt + "&transTm=" + transTm
							});
							$("#acct-flow-detail-modal").on("hidden.bs.modal", function() {
							    $(this).removeData("bs.modal");
							});
							
						}
					}
				]
			});
		}
		
		$("#qryBtn").click(function() {
			if (isEmpty($("#startDt").val()) && isEmpty($("#endDt").val())) {
				alert("<@msg code='fundMng.请输入日期范围' default='请输入日期范围'/>");
				return false;
			}
			var transAt = $("#transAtInpt").val();
			if ($.trim(transAt) != '' && !/(^([0-9]\d{0,9}|0)\.\d{1,2}$)|(^[1-9]\d{0,9}$)/.test(transAt)) {
				alert("<@msg code='fundMng.金额格式输入有误' default='金额格式输入有误'/>");
				$("#transAtInpt").val("");
				return;
			}
			initQryFrm();
		});
		$("#resetBtn").click(function() {
			$("#mer-acct-flow-qryfrm")[0].reset();
		});
		
		$("#exportBtn").click(function() {
			if (isEmpty($("#startDt").val()) || isEmpty($("#startDt").val())) {
				alert("<@msg code='fundMng.请输入日期范围' default='请输入日期范围'/>");
				return false;
			}
			exportData();
		});
	
		function exportData(){
			var _startDt = $("#startDt").val();
			var _endDt = $("#endDt").val();
			<#-- var _startTime = $("#startTime").val();
			var _endTime = $("#endTime").val(); -->
			var _operateTp = $("#operateTp").val();
			var _operateTpCat = $("#operateTpCat").val();
			var _transSeqId  = $("#transSeqId").val();
			var _timeQryMethod  = $("#timeQryMethod").val();
			var _currCd= $("#currCd").val();
			$.jumpTo("${ctx}/mchntAcct/export?startDt="+_startDt+"&endDt="+_endDt+"&operateTp="+_operateTp+"&operateTpCat="+_operateTpCat+"&transSeqId="+_transSeqId+"&timeQryMethod="+_timeQryMethod + "&currCd=" + _currCd);
		}		
		
		initQryFrm();
	});
</#assign>

<#include "/common/layout.ftl" />