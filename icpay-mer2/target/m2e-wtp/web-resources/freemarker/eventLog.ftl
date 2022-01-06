<#include "/common/macro.ftl">

<#assign htmlContent>
	<ol class="breadcrumb">
		<li><a href="${ctx}/index"><@msg code='eventLog.商户服务' default='商户服务'/></a></li>
		<li class="active"><@msg code='eventLog.操作纪录查询' default='操作纪录查询'/></li>
	</ol>
		
	<div class="panel panel-info">
		<div class="panel-heading">
			<h4 class="panel-title"><a href="#"><@msg code='eventLog.操作纪录查询' default='操作纪录查询'/></a></h4>
		</div>
		<div class="panel-body">
			<form id="event-log-flow-qryfrm" action="${ctx}/eventLog/qryEventLog" method="post" class="form-horizontal">
				<div class="form-group">
					<label class="col-sm-2 control-label"><@msg code='eventLog.后台帐户' default='后台帐户'/>:</label>
					<div class="col-sm-2">
						<select id="userId" name="userId" class="selectpicker form-control" style="height: 34px; width: 200px;">
	                        <option value=""><@msg code='eventLog.(请选择)' default='(请选择)'/></option>
	                        <#list mchList as mchList>
	                            <option value="${mchList.userId}">${mchList.userId}</option>
	                        </#list>
	                    </select>
					</div>
					<label class="col-sm-2 control-label"><@msg code='eventLog.起始日期' default='起始日期'/>:</label>
					<div class="col-sm-2">
						<input type="text" id="startDt" name="startDt" class="form-control Wdate" value="${defaultStart!''}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd HHmmss',startDate:'%y%M%d 000000', maxDate:'#F{$dp.$D(\'endDt\')}' , readOnly:true})" style="height: 34px; width: 200px; float: left; margin-right: 20px;" />
					</div>
					<label class="col-sm-2 control-label"><@msg code='eventLog.结束日期' default='结束日期'/>:</label>
					<div class="col-sm-2">
						<input type="text" id="endDt" name="endDt" class="form-control Wdate" value="${defaultEnd!''}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd HHmmss',startDate:'%y%M%d 235959', minDate:'#F{$dp.$D(\'startDt\')}', readOnly:true})" style="height: 34px; width: 200px; float: left;" />
					</div>
				</div>
				</br>
				<div class="form-group">
					<label class="col-sm-2 control-label"></label>
					<div class="col-sm-2">
						<a id="qryBtn" class="btn btn-primary btn-sm" href="#" role="button"><@msg code='eventLog.查询' default='查询'/></a>
					</div>	
				</div>		
			</form>
			</br>
	    	<table id="event-log-flow-tbl" width="100%" class="table table-hover"></table>
	    	<div id="event-log-flow-tbl-pagination" class="pagination"></div>
		</div>
	</div>
	
	<@modal modalId="event-log-detail-modal" title="操作纪录查询">
	</@modal>
</#assign>

<#assign htmlJS>
	$(function() {
		$('[data-toggle="tooltip"]').tooltip(); 
		
		$("#monSel").monthSel();
				
		function initQryFrm() {
			$("#event-log-flow-qryfrm").qryFrm("event-log-flow-tbl", "event-log-flow-tbl-pagination");
		}
		
		$("#qryBtn").click(function() {
			if (isEmpty($("#startDt").val()) && isEmpty($("#endDt").val())) {
				alert("<@msg code='eventLog.请输入日期范围' default='请输入日期范围'/>");
				return false;
			}
			initQryFrm();
		});
		
		$("#exportBtn").click(function() {
			if (isEmpty($("#startDt").val()) || isEmpty($("#startDt").val())) {
				alert("<@msg code='eventLog.请输入日期范围' default='请输入日期范围'/>");
				return false;
			}
			exportData();
		});
	
		initQryFrm();
	});
</#assign>

<#include "/common/layout.ftl" />