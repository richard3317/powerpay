<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="人工调帐报表" base="accountFlow" qryFuncCode="9200120001">
			<@authCheck funcCode='9200120002'>
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
			
			<@authCheck funcCode='9200120003'>
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
					var _transSeqId  = $("#transSeqId").val();
					var _timeQryMethod  = $("#timeQryMethod").val();
					var _note  = $("#note").val();
					var _chnlId  = $("#chnlId").val();
					var _mchntCd  = $("#mchntCd").val();
					var _lastOperId  = $("#lastOperId").val();
					$.jumpTo("${ctx}/accountFlow/export.do?"
						+"chnlId="+_chnlId
						+"&mchntCd="+_mchntCd
						+"&startDate="+_startDate
						+"&endDate="+_endDate
						+"&operateTpCat="+_operateTpCat
						+"&transSeqId="+_transSeqId
						+"&timeQryMethod="+_timeQryMethod
						+"&note="+_note
						+"&lastOperId="+_lastOperId
						);
				}
			</@authCheck>		
			
			<@authCheck funcCode='9200120004'>
				$("#amtSum").click(function() {
					var _startDate = $("#startDate").val();
					var _endDate = $("#endDate").val();
					var _operateTpCat = $("#operateTpCat").val();
					var _transSeqId  = $("#transSeqId").val();
					var _timeQryMethod  = $("#timeQryMethod").val();
					var _note  = $("#note").val();
					var _chnlId  = $("#chnlId").val();
					var _mchntCd  = $("#mchntCd").val();
					var _lastOperId  = $("#lastOperId").val();
					$.ajax({
						type : "POST",
						url : "${ctx}/accountFlow/amtSum.do",
						async: true,
						cache: false,
						data: {
							startDate : _startDate,
							endDate : _endDate,
							operateTpCat : _operateTpCat,
							transSeqId : _transSeqId,
							timeQryMethod : _timeQryMethod,
							note : _note,
							chnlId : _chnlId,
							mchntCd : _mchntCd,
							lastOperId : _lastOperId,
						},
						success : function(mm) {
							var obj = parseJson(mm);
							$("#sum_1").show();
							$("#sumTransAt").text(obj.sumTransAt);
						},
						error:function(){
					    	alert("系统异常，请联系管理员！");
					    	return  false;
					    }
					});
				});
			</@authCheck>		
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/accountFlow/accountFlowQry.do" qryFuncCode="9200120001">
			<table class="qry_tbl">
			    <tr>
					<td>起始日期：</td>
					<td>
						<input name="startDate" id="startDate" type="text" value="${(qryParamMap.today)!today}" 
							onFocus="WdatePicker({dateFmt:'yyyyMMdd', readOnly:true})" class="Wdate" />
					</td>
					<td>结束日期：</td>
					<td>
						<input name="endDate" id="endDate" type="text" value="${(qryParamMap.today_end)!today_end}" 
							onFocus="WdatePicker({dateFmt:'yyyyMMdd', readOnly:true})" class="Wdate" />
					</td>
					<td>查询方式：</td>
					<td>
						<select id="timeQryMethod" name="timeQryMethod" value="UpdTS">
							<@enumOpts enumNm="BmEnums.TimeQryMethod" showEmptyOpt="false" showCode="false" selected="UpdTS" exceptValues="OrdDT"/>
						</select>
					</td>
			    </tr>
				<tr>
					<td>交易流水号：</td>
					<td>
						<input type="text" id="transSeqId" name="transSeqId" maxLength="64" />
					</td>
					<td class="label">操作类型:</td>
					<td class="content">
						<select id="operateTpCat" name="operateTpCat" class="easyui-validatebox" style="width: 200px">
							<option value="ALL">--请选择--</option>
							<#list accOpTp?keys as k>
								<option value="${k}">${k}-${accOpTp[k]}</option>
							</#list>
						</select>
					</td>
					<td>备注：</td>
					<td>
						<input type="text" name="note" id="note" maxLength="64" />
					</td>
				</tr>
				<tr>
					<td>渠道：</td>
					<td>
						<select id="chnlId" name="chnlId" value="${(qryParamMap.chnlId)!''}">
							<option value=''>--请选择--</option>
							<option value='00'>00-小商编</option>
							<@enumOpts enumNm='TxnEnums.ChnlId' showCode='true' showEmptyOpt='false' selected="${(qryParamMap.chnlId)!''}" />
						</select>
					</td>
					<td class="label">商户号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="120"
							id="mchntCd" name="mchntCd" />
					</td>
					<td>操作员：</td>
					<td>
						<input type="text" id="lastOperId" name="lastOperId" maxLength="64" />
					</td>
				</tr>	
			</table>
		</@qryDiv>
		
		<@qryResultDiv>
			 <@authCheck funcCode='9200120002'>
			 	<a id="detailBtn" href="javascript:void(0)" class="easyui-linkbutton">查看详情</a>
			 </@authCheck>
			 
			 <@authCheck funcCode='9200120003'>
			 	<a id="exportBtn" href="javascript:void(0)" class="easyui-linkbutton">导出</a>
			 </@authCheck>
			 
			 <@authCheck funcCode='9200120004'>
			 	<a id="amtSum" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-sum'">加总</a>	
			 </@authCheck>
			 
			 <div id="Calculation">
				<table>
					<tr id="sum_1">
						<td >操作金额总金额：</td>
						<td align="right" width="100px" ><span id="sumTransAt"></span>&nbsp;元</td>
					</tr>
				</table>
			</div>
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
	</body>
<html>