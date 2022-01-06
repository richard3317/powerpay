<#include "/common/manage_macro.ftl" />
<#assign label="B2B/B2C网银充值" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="B2B/B2C网银充值" base="pay4b2cb" qryFuncCode="6001020000"
		detailFlg=true detailFuncCode="6001020002" detailUrl='"detail.do?transSeqId="+sel.transSeqId' detailDivWidth=800 detailDivHeight=550 >
			var t = null;
			var mFlg = false;
			var dealing = false;
			var scd = 0;
			
			function startMonitor() {
				if (scd > 0) {
					scd --;
				} else {
					queryPage('qryForm', 1);
					scd = $("#refreshSeconds").val();
					if (isEmpty(scd)) scd=30;
					if (scd<30) scd=30;
				}
				$("#refreshMsgSpan").html(scd + "秒后自动刷新").show();
				t = setTimeout(startMonitor, 1000);
			}
			
			$("#monitorBtn").click(function() {
				if (dealing) {
					return;
				}
				dealing = true;
				if (mFlg) {
					$(this).find("span.l-btn-text").html("开始监控");
					mFlg = false;
					if (t != null) {
						clearTimeout(t);
					}
					$("#refreshMsgSpan").html("").hide();
				} else {
					$(this).find("span.l-btn-text").html("停止监控");
					mFlg = true;
					scd = $("#refreshSeconds").val();
					queryPage('qryForm', 1);
					startMonitor();
				}
				dealing = false;
			});			
			
			$("#monSel").monthSel();
			
			$("#transAmtMin, #transAmtMax").validatebox({
				novalidate: true,
				validType: 'amount'
			});
			
			$("#startDate").validatebox({
				required: true
			});
			$("#apprBtn").click(function() {
				var sel = $("#qryResultTbl").datagrid("getSelected");
				if (sel == null) {
					alert("请选择一条记录");
				} else {
					if(sel.txnState != '01'){
						alert("交易状态不合理(必須為为处理中)，无法進行審核！");
						return false;
					}
					if(sel.chnlOrderId ==null || sel.chnlOrderId ==""){
						alert("渠道订单号为空，无法進行審核");
						return false;
					}
					$.jumpTo("${ctx}/pay4b2cb/appr.do?transSeqId=" + sel.transSeqId + "&mon=" + sel.extTransDt + "&orderState=" + sel.txnState);
				}
			});
			
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
			$("#exportTransLog").click(function(){
				var qryStr=$("#qryForm").serialize();
				var url ="${ctx}/pay4b2cb/exportTransLog.do?"+qryStr;
				<#--  console.log(url); -->			
				$.jumpTo(url);
			});
			
			$("#quickQryBtn").click(function(){
				var sel = $("#qryResultTbl").datagrid("getSelected");
				if (sel == null) {
					alert("请选择一条记录");
					return false;
				}	
				$("#orderId").val("");
				$("#transSeqId").val("");
				$("#txnState").val("");
				$("#txnState").selectOption("");
				$("#txnSrc").val("");
				$("#transChnl").val("");
				$("#chnlMchntCd").val("");
				$("#chnlOrderId").val("");
				$("#refreshSeconds").val("90");
				
				$("#transAmtMin").val(sel.transAmtDesc);
				$("#accNo").val(sel.accNo);
				$("#accName").val(sel.accName);
				$("#mchntCd").val(sel.mchntCd);
				queryPage();
			});
			
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/pay4b2cb/qry.do" qryFuncCode="6001020001" showBtn=false >
			<table class="qry_tbl">
				<input type="hidden" value='0192,0193' id="transType" name="_QRY_transType"/>
				<tr>
					<td>交易日期：</td>
					<td colspan="3">
						<input id="startDate" name="_QRY_startDate" type="text" value="${(qryParamMap.startDate)!today}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd',maxDate:'#F{$dp.$D(\'endDate\')}', readOnly:true})" 
							class="Wdate" />
						至
						<input id="endDate" name="_QRY_endDate" type="text" value="${(qryParamMap.endDate)!''}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd',minDate:'#F{$dp.$D(\'startDate\')}', readOnly:true})" 
							class="Wdate" />
					</td>
					<td>日期查询方式：</td>
					<td>
						<select id="timeQryMethod" name="_QRY_timeQryMethod" value="${(qryParamMap.timeQryMethod)!'UpdTS'}">
							<@enumOpts enumNm="BmEnums.TimeQryMethod" showEmptyOpt="false" showCode="false" selected="${(qryParamMap.timeQryMethod)!'UpdTS'}"/>
						</select>
					</td>
					<#-- 
					<td>交易月份：</td>
					<td>
						<select id="monSel" name="mon"></select>
					</td>
					 -->
				</tr>
				<tr>	
					<td>商户订单号：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="orderId" 
							name="_QRY_orderId" value="${(qryParamMap.orderId)!''}">
					</td>
					<td>交易流水号：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="transSeqId" 
							name="_QRY_transSeqId" value="${(qryParamMap.transSeqId)!''}">
					</td>
					<td>交易状态：</td>
					<td>
						<select id="txnState" name="_QRY_txnState" value="01" >
							<@enumOpts enumNm="TxnEnums.TxnStatus" showEmptyOpt="true" showCode="true" selected="01"/>
						</select>
					</td>
				</tr>
				<tr>
					<td>商户号：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="mchntCd" maxLength="32"
							name="_QRY_mchntCd" 
							value="${(qryParamMap.mchntCd)!''}" >
					</td>
					
					<td>交易金额：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="transAmtMin"
							name="_QRY_transAmtMin" value="${(qryParamMap.transAmtMin)!''}" style="width: 65px;">
						-
						<input class="easyui-validatebox" type="text"
							id="transAmtMax"
							name="_QRY_transAmtMax" value="${(qryParamMap.transAmtMax)!''}" style="width: 65px;">
					</td>
					<td>交易来源：</td>
					<td>
						<select id="txnSrc" name="_QRY_txnSrc" value="${(qryParamMap.txnSrc)!''}" >
							<@enumOpts enumNm="BmEnums.TxnSourceForSel" showEmptyOpt="true" showCode="true" emptyOptDesc="--请选择--" selected="${(qryParamMap.txnSrc)!''}"/>
						</select>
					</td>
					
					<#--  
					<td>交易类型：</td>
					<td>
						<select id="transType" name="_QRY_transType">
							<@enumOpts enumNm="BmEnums.TxnTypesForSel" showEmptyOpt="true" showCode="true" />
						</select>
					</td>
					-->
				</tr>
				<tr>
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
				
				<tr>
					<td>卡号：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="accNo" maxLength="64"
							name="_QRY_accNo" value="${(qryParamMap.accNo)!''}">
					</td>
					<td>户名：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="accName" maxLength="30"
							name="_QRY_accName"  value="${(qryParamMap.accName)!''}">
					</td>
					<td>监控刷新间隔(秒)</td>
					<td>
						<input class="easyui-validatebox" type="text" id="refreshSeconds" 
							name="_QRY_refreshSeconds" value="90" style="width: 80px; padding-left: 5px;" 
							data-options="required:true,validType:['integer','minInteger[30]','maxInteger[300]']"/>
					</td>
				</tr>
			</table>
		</@qryDiv>
		<div style="margin: 10px 0;">
			<@authCheck funcCode="6001020000">
				<a id="qryBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" >查询</a>
				<a id="quickQryBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" title="查询相同姓名、卡号、金额与商户号的数据">快速查询</a>
				<a id="clrBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo">重置</a>
				<a id="monitorBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">开 始 监 控</a>
				<span style="margin-left: 10px;font-weight: bold; color: green;" id="refreshMsgSpan"></span>
			</@authCheck>
		</div>
		

		<@qryResultDiv detailFlg=true detailFuncCode="6001020001">
			<@authCheck funcCode='6001020005'>
				<a id="apprBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">
				审批</a>
			</@authCheck>
			
			<@authCheck funcCode='6001020001'>
				<a id="amtSum" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-sum'">流 水 加 总</a>
			</@authCheck>
			<@authCheck funcCode='6001020001'>
				<a id="exportTransLog" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-print'">导 出 流 水</a>
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
	