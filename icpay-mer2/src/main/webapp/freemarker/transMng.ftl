<#include "/common/macro.ftl">

<#assign htmlHead>
	<script src="${ctx}/resources/js/jquery.dataTables.min.js"></script>
	<script src="${ctx}/resources/bootstrap/js/dataTables.bootstrap.min.js"></script>
</#assign>

<#assign htmlContent>
	<ol class="breadcrumb">
		<li><a href="#"><@msg code='transMng.商户服务' default='商户服务'/></a></li>
		<li class="active"><@msg code='transMng.充值查询' default='充值查询'/></li>
	</ol>
    
	<div class="panel panel-default">
		<div class="panel-heading"><h4 class="panel-title"><@msg code='transMng.充值查询' default='充值查询'/></h4></div>
	
		<div class="panel-body">
			<form id="trans-flow-qryfrm" action="${ctx}/trans/transQry" method="post" class="form-horizontal">
				<table width="100%" style="margin-bottom: 20px;">
					<tr style="height: 50px;">
						<#-- <td width="10%" style="padding: 5px; padding-right: 10px; text-align: right;">月份</td>
						<td width="24%"><select id="monSel" name="mon" class="form-control m-b" style="margin-bottom: 0; width: 200px;"></select></td>
						
						<td width="10%" style="padding: 5px; padding-right: 10px; text-align: right;">起始日期:</td>
						<td width="23%">
							<input type="text" id="startDt" name="startDt" class="form-control Wdate" value="${today}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd',maxDate:'#F{$dp.$D(\'endDt\')}'})" style="height: 34px; width: 200px; float: left; margin-right: 20px;" />
						</td>
						<td width="10%" style="padding: 5px; padding-right: 10px; text-align: right;">结束日期:</td>
						<td width="23%">
							<input type="text" id="endDt" name="endDt" class="form-control Wdate" value="${today}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd',minDate:'#F{$dp.$D(\'startDt\')}'})" style="height: 34px; width: 200px; float: left;" />
						</td>
						</td>-->
						<td width="10%" style="padding: 5px; padding-right: 10px; text-align: right;"><@msg code='transMng.起始日期' default='起始日期'/>:</td>
						<td width="24%">
							<input type="text" id="startDt" name="startDt" class="form-control Wdate" value="${defaultStart!''}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd HHmmss',startDate:'%y%M%d 000000',maxDate:'#F{$dp.$D(\'endDt\')}' , readOnly:true})" style="height: 34px; width: 200px; float: left; margin-right: 20px;" />
						</td>
						<td width="10%" style="padding: 5px; padding-right: 10px; text-align: right;"><@msg code='transMng.结束日期' default='结束日期'/>:</td>
						<td width="24%">
							<input type="text" id="endDt" name="endDt" class="form-control Wdate" value="${defaultEnd!''}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd HHmmss',startDate:'%y%M%d 235959', minDate:'#F{$dp.$D(\'startDt\')}' ,readOnly:true})" style="height: 34px; width: 200px; float: left;" />
						</td>
						<td width="10%" style="padding: 5px; padding-right: 10px; text-align: right;"><@msg code='transMng.流水号' default='流水号'/>:</td>
						<td width="24%">
							<input type="text" id="transSeqId" name="transSeqId" class="form-control" style="width: 200px;" />
						</td>
					</tr>
					<#-- <tr style="height: 50px;">
						
						<td width="10%" style="padding: 5px; padding-right: 10px; text-align: right;">起始时间:</td>
						<td width="24%">
							<input type="number" id="startTm" name="startTm" class="form-control" placeholder="格式:HHmmss或HHmm" style="width: 200px;" />
						</td>
						<td width="10%" style="padding: 5px; padding-right: 10px; text-align: right;">结束时间:</td>
						<td width="23%">
							<input type="number" id="endTm" name="endTm" class="form-control" placeholder="格式:HHmmss或HHmm" style="width: 200px;" />
						</td>																																							 
					</tr> -->
					<tr style="height: 50px;">
						<td width="10%" style="padding: 5px; padding-right: 10px; text-align: right;"><@msg code='transMng.交易金额' default='交易金额'/>:</td>
						<td width="24%">
							<input type="text" id="transAt" name="transAt" class="form-control" style="width: 200px;" />
						</td>
						<td width="10%" style="padding: 5px; padding-right: 10px; text-align: right;"><@msg code='transMng.交易状态' default='交易状态'/>:</td>
						<td width="23%">
							<select class="form-control m-b" name="transState" id="transState" style="margin-bottom: 0; width: 200px;">>
								<option value=""><@msg code='transMng.--请选择--' default='--请选择--'/></option>
								<option value="00"><@msg code='transMng.00-初始状态' default='00-初始状态'/></option>
								<option value="01"><@msg code='transMng.01-交易处理中' default='01-交易处理中'/></option>
								<option value="10"><@msg code='transMng.10-交易成功' default='10-交易成功'/></option>
								<option value="20"><@msg code='transMng.20-交易失败' default='20-交易失败'/></option>
								<option value="30"><@msg code='transMng.30-其他' default='30-其他'/></option>
							</select>
						</td>
						<td width="10%" style="padding: 5px; padding-right: 10px; text-align: right;"><@msg code='transMng.订单号' default='订单号'/>:</td>
						<td width="23%">
							<input type="text" id="orderId" name="orderId" class="form-control" style="width: 200px;" />
						</td>
					</tr>
					<tr style="height: 50px;">
						<td width="10%" style="padding: 5px; padding-right: 10px; text-align: right;"><@msg code='transMng.交易来源' default='交易来源'/>:</td>
						<td width="23%">
							<select class="form-control m-b" name="txnSrc" id="txnSrc" style="margin-bottom: 0; width: 200px;">>
								<option value=""><@msg code='transMng.--请选择--' default='--请选择--'/></option>
								<option value="01"><@msg code='transMng.01-接口' default='01-接口'/></option>
								<option value="03"><@msg code='transMng.03-商戶平台' default='03-商戶平台'/></option>
							</select>
						</td>
						<td width="10%" style="padding: 5px; padding-right: 10px; text-align: right;"><@msg code='transMng.交易类型' default='交易类型'/>:</td>
						<td width="23%">
							<select class="form-control m-b" name="transType" id="transType" style="margin-bottom: 0; width: 200px;">>
        		                <option value=""><@msg code='transMng.--请选择--' default='--请选择--'/></option>
                	            <#list payTypes as payType>
    	                            <option value="${payType.code}">
    	                            <@msg code='transfer.${payType.desc}' default='${payType.desc}'/></option>
	                            </#list>
							</select>
						</td>
						<td width="10%" style="padding: 5px; padding-right: 10px; text-align: right;"><@msg code='transMng.操作用户' default='操作用户'/>:</td>
						<td width="24%">
							<input type="text" id="userId" name="userId" class="form-control" style="width: 200px;" />
						</td>	
					</tr>
					<tr style="height: 50px;">
						<td width="10%" style="padding: 5px; padding-right: 10px; text-align: right;"><@msg code='transMng.币别' default='币别'/>:</td>
						<td width="23%">
							<select class="form-control m-b" name="currCd" id="currCd" style="margin-bottom: 0; width: 200px;">>
								<option value=""><@msg code='transMng.--请选择--' default='--请选择--'/></option>
								<option value="156" selected><@msg code='transMng.人民币' default='人民币'/></option>
								<option value="704"><@msg code='transMng.越南盾' default='越南盾'/></option>
								<option value="764"><@msg code='transMng.泰铢' default='泰铢'/></option>
							</select>
						</td>
					</tr>
					<tr style="height: 50px;">
						<td width="50%" style="text-align: left; padding-left: 90px" colspan="2">
							<a id="qryBtn" class="btn btn-primary btn-sm" href="#" style="float: left;margin-right: 10px;" role="button"><@msg code='transMng.查询' default='查询'/></a>
							<a id="exportBtn" class="btn btn-primary btn-sm" href="#" style="float: left;margin-right: 10px;" role="button"><@msg code='transMng.导出' default='导出'/></a>
							<a id="sumBtn" class="btn btn-primary btn-sm" href="#" style="float: left;" role="button"><@msg code='transMng.加总' default='加总'/></a>
						</td>
					</tr>
				</table>
			</form>
			<table id="sumTbl">
				<tr>
					<#--<td><@msg code='transMng.查询订单总金额' default='查询订单总金额'/>：</td>-->
					<td><@msg code='transMng.查询流水总金额' default='查询流水总金额'/>：</td>
					<td align="right" width="100px" ><span id="sumTransAt"></span>&nbsp;<#-- <@msg code='transMng.元' default='元'/>--></td>
				</tr>
				<tr>
					<td><@msg code='transMng.手续费总金额' default='手续费总金额'/>：</td>
					<td align="right" width="100px" ><span id="sumTransFee"></span>&nbsp;<#-- <@msg code='transMng.元' default='元'/>--></td>
				</tr>
				<tr>
					<td><@msg code='transMng.总笔数' default='总笔数'/>：</td>
					<td align="right" width="100px" ><span id="recCount"></span>&nbsp;<@msg code='transMng.笔' default='笔'/></td>
				</tr>
			</table>
			<table id="trans-flow-tbl" width="100%" class="tbl-info"></table>
	    	<div id="trans-flow-tbl-pagination" class="pagination"></div>
		</div>
	</div>
		
	<@modal modalId="transModal" title="交易详情" 
		customBtns='<button id="modalSubMitBtn" type="button" class="btn btn-primary">提交</button>'>
	</@modal>
</#assign>

<#assign htmlJS>
	$("#sumBtn").hide();
	$("#sumTbl").hide();
	$(function() {
		<#-- if(${secretFlag} == false && ${loginType} =='0'){
			alert("请管理员先设置安全设置");
			$.jumpTo("${ctx}/secrity/secritySetting");
		} -->
		function initQryFrm() {
			$("#trans-flow-qryfrm").qryFrm("trans-flow-tbl", "trans-flow-tbl-pagination", {
				resultHandlers : [
					{
						"btnTxt" : "<@msg code='transMng.详情' default='详情'/>",
						"btnHandler" : function(row) {
							var transSeqId = row.transSeqId;
							var r = Math.floor(Math.random() * ( 100000000 + 1));
							var mon = row.extTransDt;
					        $('#transModal').modal({
								keyboard: true,
								remote : "${ctx}/trans/transFlowDetail?transSeqId=" + transSeqId + "&mon=" + mon + "&r=" + r
							});
							$("#transModal").on("hidden.bs.modal", function() {
							    $(this).removeData("bs.modal");
							});
							<#--  
							$("#transModal").on("shown.bs.modal", function() {
							    initDetailModal();
							});
							-->
						}
					}
				]
			});
		}
		$("#monSel").monthSel();
		$("#qryBtn").click(function() {
			if ($("#startDt").val() == '' || $("#endDt").val() == '') {
				alert("<@msg code='transMng.请输入交易日期范围' default='请输入交易日期范围'/>");
				return false;
			}
			var transAt = $("#transAt").val();
			if ($.trim(transAt) != '' && !/(^([0-9]\d{0,9}|0)\.\d{1,2}$)|(^[1-9]\d{0,9}$)/.test(transAt)) {
				alert("<@msg code='transMng.金额格式输入有误' default='金额格式输入有误'/>");
				$("#transAt").val("");
				return;
			}
			initQryFrm();
			if ($("#currCd").val() != '') {
				$("#sumBtn").show();
				$("#sumTbl").show();
			} else {
				$("#sumBtn").hide();
				$("#sumTbl").hide();
			}
		});
		
		$("#exportBtn").click(function() {
			if ($("#startDt").val() == '' || $("#endDt").val() == '') {
				alert("<@msg code='transMng.请输入交易日期范围' default='请输入交易日期范围'/>");
				return false;
			}
			var transAt = $("#transAt").val();
			if ($.trim(transAt) != '' && !/(^([0-9]\d{0,9}|0)\.\d{1,2}$)|(^[1-9]\d{0,9}$)/.test(transAt)) {
				alert("<@msg code='transMng.金额格式输入有误' default='金额格式输入有误'/>");
				$("#transAt").val("");
				return;
			}
			exportTrans();
		});
		

	});
	
	<#--  
	function initDetailModal(){
		$("#btnResendNotify").click(function() {
			var transSeqId = $("#transSeqIdSel").val();
			var mon = $("#monSel").val();
			var postUrl = "${ctx}/trans/renotify.do?transSeqId=" + transSeqId + "&mon=" + mon;
			$.ajax({
					type : "GET",
					url  : postUrl,
					async: false,
					cache: false,
					// data: $("#qryForm").serialize(),
					success : function(respJson) {
						var obj = parseJson(respJson);
						console.log(obj);
						alert(obj.respMsg);
					},
					error:function(){
				    	alert("系统异常，请联系管理员！");
				    	return  false;
				    }
			});
		});
				
	}
	-->
	
	function resendNotify(){
		var transSeqId = $("#transSeqIdSel").val();
		var extTransDt = $("#extTransDtInpt").val();
		var mon = extTransDt;
		var postUrl = "${ctx}/trans/renotify.do?transSeqId=" + transSeqId + "&mon=" + mon;
		$.ajax({
				type : "GET",
				url  : postUrl,
				async: false,
				cache: false,
				<#--  data: $("#qryForm").serialize(),-->
				success : function(respJson) {
					var obj = parseJson(respJson);
					console.log(obj);
					alert(obj.respMsg);
				},
				error:function(){
			    	alert("<@msg code='transMng.系统异常，请联系管理员！' default='系统异常，请联系管理员！'/>");
			    	return  false;
			    }
		});
	}
	
	function uploadImage(){
		$('#formPay').submit();
		$("#transModal").modal('hide');
	}
	
	function exportTrans(){
		var _mon = $("#monSel").val();
		var _transSeqId= $("#transSeqId").val();
		var _startDt= $("#startDt").val();
		var _endDt = $("#endDt").val();
		var _transState= $("#transState").val();
		var _orderId= $("#orderId").val();
		var _transAt=$("#transAt").val();
		var _userId= $("#userId").val();
		var _transType= $("#transType").val();
		var _currCd= $("#currCd").val();
		$.jumpTo("${ctx}/trans/export?transSeqId=" + _transSeqId 
							+ "&mon=" + _mon + "&startDt=" + _startDt
							+ "&endDt=" + _endDt + "&transState=" + _transState + "&transType=" + _transType
							+ "&orderId=" + _orderId + "&transAt=" + _transAt + "&userId=" + _userId + "&currCd=" + _currCd);
	}
	
	$("#sumBtn").click(function() {
		var _mon = $("#monSel").val();
		var _startDt= $("#startDt").val();
		var _endDt = $("#endDt").val();
		var _startTm = $("#startTm").val();
		var _endTm = $("#endTm").val();
		var _transSeqId = $("#transSeqId").val();
		var _orderId= $("#orderId").val();
		var _transAt=$("#transAt").val();
		var _transState= $("#transState").val();
		var _txnSrc= $("#txnSrc").val();
		var _transType= $("#transType").val();
		var _userId= $("#userId").val();
		var _currCd= $("#currCd").val();
		var postUrl = "${ctx}/trans/amtSum.do?startDt=" + _startDt + "&endDt=" + _endDt + "&startTm=" + _startTm
					+ "&endTm=" + _endTm + "&transSeqId=" + _transSeqId + "&orderId=" + _orderId + "&mon=" + _mon
					+ "&transAt=" + _transAt + "&transState=" + _transState + "&txnSrc=" + _txnSrc
					+ "&transType=" + _transType + "&userId=" + _userId + "&currCd=" + _currCd;
		$.ajax({
			type : "POST",
			url  : postUrl,
			async: false,
			cache: false,
			success : function(mm) {
				var obj = parseJson(mm);
				$("#sumTransAt").text(obj.sumTransAt);
				$("#sumTransFee").text(obj.sumTransFee);
				$("#recCount").text(obj.recCount);
			},
			error:function(){
		    	alert("<@msg code='transMng.系统异常，请联系管理员！' default='系统异常，请联系管理员！'/>");
		    	return  false;
		    }
		});
	});
	
</#assign>

<#include "/common/layout.ftl" />