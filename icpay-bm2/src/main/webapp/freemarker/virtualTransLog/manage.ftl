<#include "/common/manage_macro.ftl" />
<#assign label="交易信息" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="virtualTransLog" qryFuncCode="5000050001"
			detailFlg=true detailFuncCode="5000050002" detailUrl='"detail.do?transSeqId=" + sel.transSeqId + "&mon=" + $("#monSel").val()' detailDivWidth=800 detailDivHeight=550 >
			$("#monSel").monthSel();
			
			$("#transAmtMin, #transAmtMax").validatebox({
				novalidate: true,
				validType: 'amount'
			});
			
			$("#startDate").validatebox({
				required: true
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
						$("#sum_1").show();
						$("#sum_2").show();
						$("#sumTransAt").text(obj.sumTransAt);
						$("#sumTransFee").text(obj.sumTransFee);
						$("#sumExAmount").text(obj.sumExAmount);
						<#--点击交易总金额，这个不显示-->
						$("#pro").hide();
					},
					error:function(){
				    	alert("系统异常，请联系管理员！");
				    	return  false;
				    }
				});
			});

			$("#proSum").click(function() {
				$.ajax({
					type : "POST",
					url : baseUrl+"amtSum.do",
					async: true,
					cache:false,
					data: $("#qryForm").serialize(),
					success : function(mm) {
						var obj = parseJson(mm);
						$("#sum_1").hide();
						$("#sum_2").hide();
						$("#pro").show();
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
				var url ="${ctx}/virtualTransLog/exportTransLog.do?"+qryStr;
				<#--  console.log(url); -->			
				$.jumpTo(url);
			});
			
			$("#Qry_indistinct").click(function(){
				if($("#Qry_indistinct").prop("checked")) {
					$(this).prop("value", "1");
				}
				else{
					$(this).prop("value", "0");
				}
			});
			
			$("#transSateModify").click(function() {
				var sel = $("#qryResultTbl").datagrid("getSelected");
				if (sel == null) {
					alert("请选择一条记录");
				} else {
					if(sel.txnState != '20' && sel.txnState != '01'){
						alert("交易状态不为失败或者处理中，无法调整！");
						return false;
					}
					if(sel.chnlOrderId ==null || sel.chnlOrderId ==""){
						alert("渠道订单号为空，不能修改该记录状态");
						return false;
					}
					$.jumpTo("${ctx}/virtualTransLog/transSateModify.do?transSeqId=" + sel.transSeqId + "&mon=" + sel.extTransDt + "&orderState=" + sel.txnState);
				}
			});
			
			$("#mandatoryModify").click(function() {
				var sel = $("#qryResultTbl").datagrid("getSelected");
				if (sel == null) {
					alert("请选择一条记录");
				} else {
					if(sel.txnState == '31'){
						alert("交易状态为其他状态，无法调整！");
						return false;
					}
					if(sel.chnlOrderId ==null || sel.chnlOrderId ==""){
						alert("渠道订单号为空，不能修改该记录状态");
						return false;
					}
					$.jumpTo("${ctx}/virtualTransLog/mandatoryModify.do?transSeqId=" + sel.transSeqId + "&mon=" + sel.extTransDt + "&orderState=" + sel.txnState);
				}
			});
			
			<#-- $("#differentModify").click(function() {
				var sel = $("#qryResultTbl").datagrid("getSelected");
				if (sel == null) {
					alert("请选择一条记录");
				} else {
					if(!((sel.txnState == '01' && sel.orderState != '10' && sel.orderState != '10' 
						&& sel.orderState != '91' && sel.orderState != '11' && sel.orderState != '20') || 
						((sel.orderState == '10' || sel.orderState == '10' || sel.orderState == '91'
						|| sel.orderState == '11' || sel.orderState == '20') && sel.txnState != '01'))){
						alert("交易状态不为不一致，无法调整！");
						return false;
					}
					if(sel.chnlOrderId ==null || sel.chnlOrderId ==""){
						alert("渠道订单号为空，不能修改该记录状态");
						return false;
					}
					$.jumpTo("${ctx}/virtualTransLog/differentModify.do?transSeqId=" + sel.transSeqId + "&mon=" + sel.extTransDt + "&orderState=" + sel.txnState);
				}
			});-->
			
			$("#queryChnlResult").click(function() {
				$(this).once( function(){
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						<#--交易创建时间-->
						var recCrtTs =  sel.recCrtTs;
						var creaTime = new Date(recCrtTs.replace(new RegExp(/-/gm) ,"/"));
						<#--现在交易时间前3分钟-->
						var now = new Date();
						now.setMinutes(now.getMinutes()-3);
						
						<#--判断現在是否已超过訂單創建三分钟，才可查询上游-->
						if(now.getTime() > creaTime.getTime()){
							var r=confirm("即将执行上游查询，查询结果可能影响目前交易状态，请确认！");
							if (!r) return false;
							var url = "${ctx}/virtualTransLog/queryChnlResult.do?transSeqId=" + sel.transSeqId + "&orderDate=" + sel.extTransDt;
							crtViewDialog("detailDiv", "上游交易状态查询结果", url, 800, 550);
						}else{
							alert("请耐心稍等，订单发起后3分钟后方可查询"); 
							return false;
						}
					}
				
				});
			});
			$("#cancelPayment").click(function() {
				$(this).once( function(){
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						
						var chnlOrderId =  sel.chnlOrderId;
						var extTransDt =  sel.extTransDt;
						var transSeqId =  sel.transSeqId;
						var chnlMchntCd =  sel.chnlMchntCd;
						var r = confirm("将执行取消充币单，确认取消此单?：" + transSeqId);
						if (!r) return false;
						
						var url = "${ctx}/virtualTransLog/cancelPayment.do?chnlOrderId=" + chnlOrderId 
						+ "&extTransDt=" + extTransDt + "&transSeqId=" +transSeqId
						+ "&chnlMchntCd=" + chnlMchntCd;
						
						$.ajax({
							type : "GET",
							url : url,
							async: false,
							cache: false,
							success : function(mm) {
								var obj = parseJson(mm);
								alert(obj.respMsg);
								console.log(obj);
								var url ="${ctx}/virtualTransLog/backToManage.do";
								$.jumpTo(url);
							},
							error:function(){
						    	alert("系统异常，请联系管理员！");
						    	return  false;
						    }
						});
						
					}
				
				});
			});
			$("#resendNotify").click(function() {
				$(this).once( function(){
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						$.ajax({
							type : "GET",
							url : baseUrl+"renotify.do?transSeqId=" + sel.transSeqId + "&orderDate=" + sel.extTransDt,
							async: false,
							cache: false,
							success : function(mm) {
								var obj = parseJson(mm);
								console.log(obj);
								alert(obj.respMsg);
							},
							error:function(){
						    	alert("系统异常，请联系管理员！");
						    	return  false;
						    }
						});
					}
				});
			});
			
		</@head>
	</head>
	
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/virtualTransLog/qry.do" qryFuncCode="5000050001">
			<table class="qry_tbl">
				<#-- <input type="hidden" value='01' id="transType" name="_QRY_transType"/>  -->
				<tr>
					<td>交易日期：</td>
					<td colspan="3">
						<input id="startDate" name="_QRY_startDate" type="text" value="${(qryParamMap.today)!today}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd HHmmss',maxDate:'#F{$dp.$D(\'endDate\')}', readOnly:true})"
							class="Wdate" />
						至
						<input id="endDate" name="_QRY_endDate" type="text" value="${(qryParamMap.today_end)!today_end}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd HHmmss',minDate:'#F{$dp.$D(\'startDate\')}', readOnly:true})"
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
					<td>交易流水号：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="transSeqId" 
							name="_QRY_transSeqId" value="${(qryParamMap.transSeqId)!''}">
					</td>
					
					<td>交易状态：</td>
					<td>
						<select id="txnState" name="_QRY_txnState" value="${(qryParamMap.txnState)!''}" >
							<@enumOpts enumNm="TxnEnums.TxnStatus" showEmptyOpt="true" showCode="true" emptyOptDesc="--请选择--" selected="${(qryParamMap.txnState)!''}"/>
						</select>
					</td>
					
					<td>商户订单号：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="orderId" 
							name="_QRY_orderId" value="${(qryParamMap.orderId)!''}">
							<input type="checkbox" value="0" id="Qry_indistinct" style="zoom:40%">模糊匹配
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
				<#--
					<td>银行名称：</td>
					<td>
						<select class="easyui-validatebox" id="bankNum" name="_QRY_bankNum"	style="width: 200px; height: 27px; ">
							<option value="">--请选择--</option>
							<#list bankCodeList as bank>
								<#if (bank.bankNum==((qryParamMap.bankNum)!''))>
									<option value="${bank.bankNum}" selected="selected">${bank.bankNum}-${bank.bankName}</option>
								<#else>	
									<option value="${bank.bankNum}">${bank.bankNum}-${bank.bankName}</option>
								</#if>	
							</#list>
						</select>
					</td>
					<td>卡号：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="accNo" maxLength="30"
							name="_QRY_accNo" value="${(qryParamMap.accNo)!''}">
					</td>
				-->
					
					<td>交易类型：</td>
					<td>
						<select id="transType" name="_QRY_transType" value="${(qryParamMap.transType)!'01'}" >
							<@enumOpts enumNm="BmEnums.TxnTypesForSel" selected="01" exceptValues="52" showEmptyOpt="false" showCode="true" />
						</select>
					</td>
					
					<td>外部系統/區塊鍊系統 交易訂單號 ：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="exTxnId" maxLength="100"
							name="_QRY_exTxnId" value="${(qryParamMap.exTxnId)!''}">
					</td>
				</tr>		
			</table>
		</@qryDiv>
		
		<@qryResultDiv detailFlg=true detailFuncCode="5000050002">
			<@authCheck funcCode='5000050003'>
				<a id="amtSum" href="javascript:void(0)" class="easyui-linkbutton"  data-options="iconCls:'icon-sum'">交易量加总</a>
			</@authCheck>
			<@authCheck funcCode='5000050006'>
				<a id="proSum" href="javascript:void(0)" class="easyui-linkbutton"  data-options="iconCls:'icon-sum'">利润加总</a>
			</@authCheck>
			
			<@authCheck funcCode='5000050004'>
				<a id="exportTransLog" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-print'">导 出 流 水</a>
			</@authCheck>
			<@authCheck funcCode='5000050005'>
				<a id="transSateModify" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"> 调 帐 - 更 改 交 易 状 态</a>
				<a id="queryChnlResult" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"> 查 询 上 游 状 态</a>
				<a id="cancelPayment" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">取 消 充 币 单</a>
				<a id="resendNotify" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-back'"> 补 发 通 知</a>
				
			</@authCheck>
			<@authCheck funcCode='5000050007'>
				<a id="mandatoryModify" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"> 调帐 - 强制改状态</a>
			</@authCheck>
			<#-- <@authCheck funcCode='5000050008'>
				<a id="differentModify" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"> 状态不一致调整</a>
			</@authCheck>-->
			<br/>
			<div id="Calculation">
				<table>
					<tr id="sum_1">
						<td >查询流水总金额：</td>
						<td align="right" width="100px" ><span id="sumTransAt"></span>&nbsp;VHKD</td>
					</tr>
					<tr id="sum_2">
						<td >手续费总金额：</td>
						<td align="right" width="100px" ><span id="sumTransFee"></span>&nbsp;VHKD</td>
					</tr>
					<tr id="sum_3">
						<td >原始數量总金额：</td>
						<td align="right" width="100px" ><span id="sumExAmount"></span>&nbsp;USDT</td>
					</tr>
					<tr id="pro" style="display:none;" >
						<td >总利润：</td>
						<td align="right" width="100px" ><span id="sumTransFeeDelta"></span>&nbsp;VHKD</td>
					</tr>
				</table>
			</div>
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
		
	</body>
<html>
	