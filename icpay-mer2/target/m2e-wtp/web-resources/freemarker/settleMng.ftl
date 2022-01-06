<#include "/common/macro.ftl">

<#assign htmlContent>
	<ol class="breadcrumb">
		<li><a href="${ctx}/index"><@msg code='settleMng.商户服务' default='商户服务'/></a></li>
		<li class="active"><@msg code='settleMng.清算对账查询' default='清算对账查询'/></li>
	</ol>
	
	<div class="panel panel-info">
		<div class="panel-heading">
			<h4 class="panel-title"><a href="#"><@msg code='settleMng.清算记录查询' default='清算记录查询'/></a></h4>
		</div>
		<div class="panel-body">
			<form id="settle-task-frm" action="${ctx}/mchntSettle/qrySettleLog" method="post" class="form-horizontal">
				<div class="form-group">
					<label class="col-sm-2 control-label"><@msg code='settleMng.清算日期' default='清算日期'/></label>
					<div class="col-sm-2">
						<input type="text" name="settleDt" class="form-control Wdate"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" style="height: 34px;" />
					</div>
					<label class="col-sm-2 control-label"><@msg code='settleMng.清算批次' default='清算批次'/></label>
					<div class="col-sm-2">
						<select class="form-control m-b" name="settleBt">
							<option value=""><@msg code='settleMng.--请选择--' default='--请选择--'/></option>
							<#list [1,2,3,4,5,6,7,8,9,10,11,12] as i>
								<option value="${i}">${i}</option>
							</#list>
						</select>
					</div>
					<div class="col-sm-2">
						<a id="qryBtn" class="btn btn-primary btn-sm" href="#" role="button"><@msg code='settleMng.查询' default='查询'/></a>
						<a id="resetBtn" class="btn btn-default btn-sm" href="#" role="button"><@msg code='settleMng.重置' default='重置'/></a>
					</div>
				</div>
			</form>
	    	<table id="settle-task-tbl" width="100%" class="tbl-info"></table>
	    	<div id="settle-task-tbl-pagination" class="pagination"></div>
		</div>
	</div>
</#assign>

<#assign htmlJS>
	$(function() {
		function initQryFrm() {
			$("#settle-task-frm").qryFrm("settle-task-tbl", "settle-task-tbl-pagination", {
				resultHandlers : [
					{
						"btnTxt" : "<@msg code='settleMng.下载对账文件' default='下载对账文件'/>",
						"btnHandler" : function(row) {
							var seqId = row.seqId;
							var r = Math.floor(Math.random() * ( 100000000 + 1));
							var downUrl = "${ctx}/mchntSettle/downStlFile?seqId=" + seqId + "&r=" + r;
							$.jumpTo(downUrl);
						}
					}
				]
			});
		}
		
		$("#qryBtn").click(function() {
			initQryFrm();
		});
		$("#resetBtn").click(function() {
			$("#settle-task-frm")[0].reset();
		});
		initQryFrm();
	});
</#assign>

<#assign htmlStyle>
	td.td-btns { width: 120px; }
</#assign>

<#include "/common/layout.ftl" />