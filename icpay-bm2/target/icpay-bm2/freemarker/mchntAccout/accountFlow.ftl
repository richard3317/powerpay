<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="商户账户流水" base="mchntAccout" qryFuncCode="1000070003">
			$("#backToMngBtn").click(function() {
				$.jumpTo(_ctx + "/mchntAccout/backToManage.do");
			});
			<@authCheck funcCode='1000070006'>
				$("#detailBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						var url = baseUrl + "accountFlowDetail.do?seqId=" + sel.seqId+"&recUpdTs="+sel.recUpdTs;
						crtViewDialog("detailDiv", "商户账户流水详情", url, 650, 350);
					}
				});
			</@authCheck>
			<@authCheck funcCode='1000070002'>
				$("#exportBtn").click(function() {
					if (isEmpty($("#startDate").val()) && isEmpty($("#endDate").val())) {
						alert("请输入日期范围");
						return false;
					}
					exportData();
				});
			
				function exportData(){
					var _startDate = $("#startDate").val();
					var _endDate = $("#endDate").val();
					var _operateTpCat = $("#operateTpCat").val();
					var _operateTp = $("#operateTp").val();
					var _transSeqId  = $("#transSeqId").val();
					var _timeQryMethod  = $("#timeQryMethod").val();
					var _note  = $("#note").val();
					
					$.jumpTo("${ctx}/mchntAccout/export.do?"
						+"mchntCd=${mchntCd}"
						+"&startDate="+_startDate
						+"&endDate="+_endDate
						+"&operateTpCat="+_operateTpCat
						+"&operateTp="+_operateTp
						+"&transSeqId="+_transSeqId
						+"&timeQryMethod="+_timeQryMethod
						+"&note="+_note
						);
				}
			</@authCheck>	
			
			<@authCheck funcCode='1000070015'>
				$("#flowNoteEditBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						$.jumpTo(baseUrl + "flowNoteEdit.goto.do?seqId=" + sel.seqId+"&recUpdTs="+sel.recUpdTs);
					}
				});
			</@authCheck>
			
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/mchntAccout/accountFlowQry.do" qryFuncCode="1000070003">
			<input type="hidden" name="mchntCd" value="${mchntCd}" />
			<table class="qry_tbl">
				<tr>
					<td>起始日期：</td>
					<td>
						<input name="startDate" id="startDate" type="text" value="${today}" 
							onFocus="WdatePicker({dateFmt:'yyyyMMdd', readOnly:true})" class="Wdate" />
					</td>
					<td>结束日期：</td>
					<td>
						<input name="endDate" id="endDate" type="text" value="" 
							onFocus="WdatePicker({dateFmt:'yyyyMMdd', readOnly:true})" class="Wdate" />
					</td>
					<td>查询方式：</td>
					<td>
						<select id="timeQryMethod" name="timeQryMethod" value="UpdTS">
							<@enumOpts enumNm="BmEnums.TimeQryMethod" showEmptyOpt="false" showCode="false" selected="UpdTS"/>
						</select>
					</td>
					<#--  
					<td>订单日期：</td>
					<td>
						<input id="operateDtInpt" name="transDt" type="text" value="" 
							onFocus="WdatePicker({dateFmt:'yyyyMMdd', readOnly:true})" class="Wdate" />
					</td>
					<td>更新日期：</td>
					<td>
						<input id="operateDtInpt" name="recUpdTs" type="text" value="${today}" 
							onFocus="WdatePicker({dateFmt:'yyyyMMdd', readOnly:true})" class="Wdate" />
					</td>
					-->
				</tr>
				
				<tr>
					<td>交易流水号：</td>
					<td>
						<input type="text" name="transSeqId" id="transSeqId" maxLength="64" />
					</td>
					<td>操作类型：</td>
					<td>
						<select id="operateTpCat" name="operateTpCat" data-options="editable:false">
							<@enumOpts enumNm="BmEnums.AccOperTpCatalog" showEmptyOpt="true" showCode="false" emptyOptDesc="(全部)" />
							<#--  <@enumOpts enumNm='AccEnums.AccOperTp' showEmptyOpt='true' /> -->
							<option value=''>-------------------</option>
							<@enumOpts enumNm='BmEnums.AccOperTp' showEmptyOpt='false' showCode="true"  />
						</select>
					</td>
					<td>备注：</td>
					<td>
						<input type="text" name="note" id="note" maxLength="64" />
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv>
			 <@authCheck funcCode='1000070006'>
			 	<a id="detailBtn" href="javascript:void(0)" class="easyui-linkbutton">查看详情</a>
			 </@authCheck>
			 <@authCheck funcCode='1000070002'>
			 	<a id="exportBtn" href="javascript:void(0)" class="easyui-linkbutton">导出</a>
			 </@authCheck>
			 <@authCheck funcCode='1000070015'>
			 	<a id="flowNoteEditBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改流水备注</a>	
			 </@authCheck>
			 <a id="backToMngBtn" href="javascript:void(0)" class="easyui-linkbutton">返回</a>
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
	</body>
<html>