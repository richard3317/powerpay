<#include "/common/manage_macro.ftl" />
<#assign label="对帐工具" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="transLog" qryFlg=false qryFuncCode="5000040001"
			detailFlg=true detailFuncCode="5000010002" detailUrl='"detail.do?transSeqId=" + sel.transSeqId ' detailDivWidth=800 detailDivHeight=550>
			<#-- $("#monSel").monthSel(); -->
			
			$("#transAmtMin, #transAmtMax").validatebox({
				novalidate: true,
				validType: 'amount'
			});
			
			$("#startDate").validatebox({
				required: true
			});
			
			$("#compareBtn").click(function() {
				$('#frm2_startDate').val($('#startDate').val());
				$('#frm2_startTime').val($('#startTime').val());
				$('#frm2_endDate').val($('#endDate').val());
				$('#frm2_endTime').val($('#endTime').val());
				$('#frm2_timeQryMethod').val($('#timeQryMethod').val());
				$('#frm2_transType').val($('#transType').val());
				$('#frm2_transChnl').val($('#transChnl').val());
				$('#frm2_chnlMchntCd').val($('#chnlMchntCd').val());
				$('#frm2_chnlOrderId').val($('#chnlOrderId').val());
				$('#uploadForm').submit();
			});
			
			<#--  
			$("#compareBtn").click(function() {
				if (!$("#qryForm").form('validate')) {
					return;
				}
				queryPage('qryForm', 1);
			});
			-->
			<#--  
			$("#compareBtn").click(function(){
				$.ajax({
					type : "POST",
					//url : "${ctx}/transLog/qry.do",
					url : "${ctx}/transLog/compare/submit.do",
					data: new FormData($('#qryForm')[0]), //$("#qryForm").serialize(), 
					enctype: 'multipart/form-data',
					resDataType:'blob',
					responseType:'blob',
					//xhrFields: { responseType: 'blob' },
					dataType : 'binary',
					contentType: false,
					processData: false
					<!--  
					success : function(data) {
						//console.log(data);
						var a = document.createElement('a');
						var objUrl = URL.createObjectURL(data);
						a.href = objUrl;
						a.download="test.xls";
						a.click();
						window.URL.revokeObjectURL(objUrl);
					}
					--
				})
				.complete(function (xhr) {
					console.log(xhr);
					var file = xhr.responseText;
					var $a = $('<a />'),
						objUrl = URL.createObjectURL(new Blob(file, {type: "application/msexcel"}));
					$a.attr({
							'href': objUrl,
							'download': 'test.xls'
						})
						.trigger('click');
					URL.revokeObjectURL(objUrl);
				})
				//.done(function () { alert('下载成功!'); })
			});		
			-->
			
			$("#amtSum").click(function() {
				$.ajax({
					type : "POST",
					url : baseUrl+"amtSum.do",
					async: true,
					cache:false,
					data: $("#qryForm").serialize(),
					success : function(mm) {
						var obj = parseJson(mm);
						$("#sumTransAt").text(obj.sumTransAt);
						$("#sumTransFee").text(obj.sumTransFee);
						$("#sumTransFeeDelta").text(obj.sumTransFeeDelta);
					},
					error:function(){
				    	alert("系统异常，请联系管理员！");
				    	return  false;
				    }
				});
			});
			

		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/transLog/qry.do" qryFuncCode="5000010001" showBtn=false>
			<table class="qry_tbl">
				<#-- <input type="hidden" value='01' id="transType" name="_QRY_transType"/>  -->
				<input type="hidden" value='10' id="txnState" name="_QRY_txnState"/><#-- 只查询成功的交易 -->

				<tr>
					<td>交易日期：</td>
					<td colspan="5">
						<input id="startDate" name="_QRY_startDate" type="text" value="${(qryParamMap.startDate)!today!''}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd',maxDate:'#F{$dp.$D(\'endDate\')}', readOnly:true})" 
							class="Wdate" />&nbsp;
						<input id="startTime" name="_QRY_startTime" type="text" value="${(qryParamMap.startTime)!''}" placeholder="起始时间，格式HHmm"/>	
						至
						<input id="endDate" name="_QRY_endDate" type="text" value="${(qryParamMap.endDate)!''}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd',minDate:'#F{$dp.$D(\'startDate\')}', readOnly:true})" 
							class="Wdate" />&nbsp;
						<input id="endTime" name="_QRY_endTime" type="text" value="${(qryParamMap.endTime)!''}" placeholder="结束时间，格式HHmm"/>	
					</td>
					<td>日期查询方式：</td>
					<td>
						<select id="timeQryMethod" name="_QRY_timeQryMethod" value="${(qryParamMap.timeQryMethod)!'UpdTS'}">
							<@enumOpts enumNm="BmEnums.TimeQryMethod" showEmptyOpt="false" showCode="false" selected="${(qryParamMap.timeQryMethod)!'UpdTS'}"/>
						</select>
					</td>
				</tr>
				<tr>
					<td>交易类型：</td>
					<td>
						<select id="transType" name="_QRY_transType" value="${(qryParamMap.transType)!'01'}" >
							<@enumOpts enumNm="BmEnums.TxnTypesForSel" selected="01" showEmptyOpt="true" emptyOptDesc="--所有交易--" showCode="true" />
						</select>
					</td>
					<td>渠道：</td>
					<td>
						<select id="transChnl" name="_QRY_transChnl" value="${(qryParamMap.transChnl)!''}">
							<@enumOpts enumNm='TxnEnums.ChnlId' showCode='true' showEmptyOpt='true' selected="${(qryParamMap.transChnl)!''}" />
						</select>
					</td>
					<td>渠道商编：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="chnlMchntCd" maxLength="40"
							name="_QRY_chnlMchntCd" value="${(qryParamMap.chnlMchntCd)!''}">
					</td>
					<td>平台订单号：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="chnlOrderId" maxLength="30"
							name="_QRY_chnlOrderId" value="${(qryParamMap.chnlOrderId)!''}">
					</td>
				</tr>
			</table>
			
			<div style="margin: 10px 0;">
				<@authCheck funcCode="5000040001">
					<a id="qryBtn" href="javascript:void(0)" class="easyui-linkbutton">查询</a>
					<a id="clrBtn" href="javascript:void(0)" class="easyui-linkbutton">重置</a>
				</@authCheck>
			</div>
		</@qryDiv>
		
		<form id="uploadForm" action="${ctx}/transLog/compare/submit.do" method="POST" enctype="multipart/form-data">
			<input type="hidden" value='10' name="_QRY_txnState"/>
			<input type="hidden" id="frm2_startDate" name="_QRY_startDate" type="text" value="${(qryParamMap.startDate)!today!''}" />
			<input type="hidden" id="frm2_startTime" name="_QRY_startTime" type="text" value="${(qryParamMap.startTime)!''}"  />
			<input type="hidden" id="frm2_endDate" name="_QRY_endDate" type="text" value="${(qryParamMap.endDate)!''}" />
			<input type="hidden" id="frm2_endTime" name="_QRY_endTime" type="text" value="${(qryParamMap.endTime)!''}" />
			<input type="hidden" id="frm2_timeQryMethod" name="_QRY_timeQryMethod" value="${(qryParamMap.timeQryMethod)!'UpdTS'}" />
			<input type="hidden" id="frm2_transType" name="_QRY_transType" value="${(qryParamMap.transType)!'01'}"  />
			<input type="hidden" id="frm2_transChnl" name="_QRY_transChnl" value="${(qryParamMap.transChnl)!''}" />
			<input type="hidden" id="frm2_chnlMchntCd" name="_QRY_chnlMchntCd" value="${(qryParamMap.chnlMchntCd)!''}" />
			<input type="hidden" id="frm2_chnlOrderId" name="_QRY_chnlOrderId" value="${(qryParamMap.chnlOrderId)!''}" />
		
			<table class="qry_tbl">
				<tr>
					<td>渠道对帐文件：</td>
					<td colspan="6">
						<input id="chnlLogFile" class="easyui-filebox" name="chnlLogFile" style="width: 400px; height:26px; padding-top: 0px; padding-bottom: 0px;"
						    data-options="buttonText:'选择文件', iconCls:'icon-save', iconAlign:'left'"
							placeholder="渠道对帐文件，需小于10MB" />
					</td>
					<td>
						<@authCheck funcCode="5000040001">
							<a id="compareBtn" href="javascript:void(0)" class="easyui-linkbutton">比对</a>
						</@authCheck>
					</td>
				</tr>		
			</table>
			<#--  
			<div style="margin: 10px 0;">
				<@authCheck funcCode="5000040001">
					<a id="compareBtn" href="javascript:void(0)" class="easyui-linkbutton">比对</a>
				</@authCheck>
			</div>
			-->
		</form>
		
		<@qryResultDiv detailFlg=true detailFuncCode="5000010002">
			<@authCheck funcCode='5000010003'>
				<a id="amtSum" href="javascript:void(0)" class="easyui-linkbutton"  data-options="iconCls:'icon-add'">加 总 流 水</a>
			</@authCheck>
			<br/>
			<div id="Calculation">
				<table>
					<tr>
						<td>查询流水总金额：</td>
						<td align="right" width="100px" ><span id="sumTransAt"></span>&nbsp;元</td>
					</tr>
					<tr>
						<td>手续费总金额：</td>
						<td align="right" width="100px" ><span id="sumTransFee"></span>&nbsp;元</td>
					</tr>
					<tr>
						<td>总利润：</td>
						<td align="right" width="100px" ><span id="sumTransFeeDelta"></span>&nbsp;元</td>
					</tr>
				</table>
			</div>
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
		
	</body>
<html>
	


