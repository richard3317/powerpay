<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="分润打款管理" base="agentProfitWithdraw" qryFuncCode="3000310001"
			detailFlg=true detailDivWidth=800 detailDivHeight=550 detailFuncCode="3000310002"
			detailUrl='"detail.do?id="+sel.id' 
			editFlg=true 
			editUrl='"edit.do?id="+sel.id'
			editFuncCode="3000310005"
			beforeEdit="beforeEdit"
			addFlg=true addFuncCode="3000310003"
			deleteFuncCode="3000310007"
			deleteFlg=true 
			beforeDelete="beforeDelete"
			deleteUrl='"delete.do?id="+sel.id'
			>
			
			<#-- 生成打款表 -->
			<@authCheck funcCode='3000310011'>
			$("#resetDataBtn").click(function() {
				var r=confirm("所有表中日期为${preDate!'null'}且尚未出款的项目都将删除并重新生成，请确认！");
				if (!r) return false;
			
				$.ajax({
					type : "GET",
					url : _ctx + "/agentProfitWithdraw/resetData.do",
					async: true,
					cache:false,
					<#-- data: $("#qryForm").serialize(), -->
					success : function(data) {
						var res = parseJson(data);
						if (!checkEmpty(res, "系统异常，请联系管理员！")) return false;
						if (res.respCode == "00")
							alert("操作成功!");
						else
							alert("操作失败: "+res.respMsg);
					},
					error:function(){
				    	alert("系统异常，请联系管理员！");
				    	return  false;
				    }
				});
			});
			</@authCheck>
						
			<#-- 当日打款 -->
			<@authCheck funcCode='3000310012'>
			$("#withdrawBtn").click(function() {
				var r=confirm("所有表中日期为${preDate}且尚未出款的项目都将进行出款(取现)操作，请确认！");
				if (!r) return false;
					
				$.ajax({
					type : "GET",
					url : _ctx + "/agentProfitWithdraw/withdraw.do",
					async: true,
					cache:false,
					success : function(data) {
						var res = parseJson(data);
						if (!checkEmpty(res, "系统异常，请联系管理员！")) return false;
						if (res.respCode == "00")
							alert("打款请求已完成，交易将陆续完成，请刷新页面读取打款结果。");
						else
							alert("操作失败: "+res.respMsg);
						queryPage();
					},
					error:function(){
				    	alert("系统异常，请联系管理员！");
				    	return  false;
				    }
				});					
			});
			</@authCheck>
			
			<@authCheck funcCode='3000310013'>
			$("#exportBtn").click(function(){
				var qryStr=$("#qryForm").serialize();
				var url = _ctx + "/agentProfitWithdraw/export.do?"+qryStr;
				<#--  console.log(url); -->			
				$.jumpTo(url);
			});
			</@authCheck>
			
			<@authCheck funcCode='3000310014'>
			$("#summaryBtn").click(function() {
				$.ajax({
					type : "POST",
					url : baseUrl+"amtSum.do",
					async: true,
					cache:false,
					data: $("#qryForm").serialize(),
					success : function(mm) {
						var obj = parseJson(mm);
						$("#sumTxnAmt").text(obj.sumTxnAmt);
					},
					error:function(){
				    	alert("系统异常，请联系管理员！");
				    	return  false;
				    }
				});
			});
			</@authCheck>
			function beforeEdit(sel){
				if (sel == null) {
					alert("请选择一条记录");
					return false;
				}
				if ((!isEmpty(sel.procState)) && (sel.procState != "00")){
					alert("已打款的数据不得修改或删除!");
					return false;
				}
				if (! isEmpty(sel.txnOrderId)){
					alert("已打款的数据不得修改或删除!");
					return false;
				}
				return true;
			}
			
			function beforeDelete(sel){
				var r = beforeEdit(sel);
				if (!r) return false;
				return confirm("确认要删除选中的记录吗?");
			}
			
			$("#enableAllBtn").click(function(){
				var r=confirm("符合目前查询条件的纪录都将开启操作(允许打款)，请确认！");
				if (!r) return false;
				enableAllQryResult("true");
			});
			
			$("#disableAllBtn").click(function(){
				var r=confirm("符合目前查询条件的纪录都将关闭操作(禁止打款)，请确认！");
				if (!r) return false;
				enableAllQryResult("false");
			});
			
			function enableAllQryResult(enabled){
				$.ajax({
					type : "POST",
					url : _ctx + "/agentProfitWithdraw/enableAll.do?enabled="+enabled ,
					async: true,
					cache: false,
					data: $("#qryForm").serialize(),
					success : function(data) {
						var res = parseJson(data);
						if (!checkEmpty(res, "系统异常，请联系管理员！")) return false;
						if (res.respCode == "00")
							alert(res.respMsg);
						else
							alert("操作失败: "+res.respMsg);
						queryPage();
					},
					error:function(){
				    	alert("系统异常，请联系管理员！");
				    	return  false;
				    }
				});
			}
			
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<div id="mngDiv" class="easyui-panel" title="分润打款管理" style="padding: 10px;">
			<@qryDiv qryUrl="/agentProfitWithdraw/qry.do" qryFuncCode="3000310001">
				<table class="qry_tbl">
					<tr>
						<td>分润结算日期：</td>
						<td>
							<input name="_QRY_startSettleDate" type="text" value="${(qryParamMap.settleDate)!settleDate!preDate!''}"
								onFocus="WdatePicker({dateFmt:'yyyyMMdd', readOnly:true})" class="Wdate" />
						</td>
						<td colspan="2">
							至
							<input name="_QRY_endSettleDate" type="text" value="${(qryParamMap.settleDate)!settleDate!preDate!''}"
								onFocus="WdatePicker({dateFmt:'yyyyMMdd', readOnly:true})" class="Wdate" />
						</td>
						<td>代理商编号：</td>
						<td>
							<select class="easyui-validatebox"
								id="agentCd" name="_QRY_agentCd" value="${(qryParamMap.agentCd)!agentCd!''}"
								style="width: 200px; height: 27px; ">
								<option value="">--请选择--</option>
								<#if agentsList??>
									<#list agentsList as agent>
										<#if (agent.agentCd==((qryParamMap.agentCd)!agentCd!''))>
											<option value="${agent.agentCd}" selected="selected">${agent.agentCnNm}</option>
										<#else>	
											<option value="${agent.agentCd}">${agent.agentCnNm}</option>
										</#if>	
									</#list>
								</#if>
							</select>
						</td>
						
						<td>打款操作：</td>
						<td>
							<select id="txnState" name="_QRY_state" value="${(qryParamMap.state)!''}" >
								<@enumOpts enumNm="TxnEnums.CommonSwitch" showEmptyOpt="true" showCode="true" 
								  emptyOptDesc="--请选择--" selected="${(qryParamMap.state)!''}"/>
							</select>
						</td>
					</tr>
					
					<tr>	
						<td>交易日期：</td>
						<td>
							<input name="_QRY_txnOrderDate" type="text" value="${(qryParamMap.txnOrderDate)!''}"
								onFocus="WdatePicker({dateFmt:'yyyyMMdd', readOnly:true})" class="Wdate" />
						</td>
						<td>交易订单号：</td>
						<td>
							<input class="easyui-validatebox" type="text" style="width: 200px;"
								id="txnOrderId" name="_QRY_txnOrderId" value="${(qryParamMap.txnOrderId)!''}">
						</td>
						<td>请求状态：</td>
						<td>
							<select id="txnState" name="_QRY_procState" value="${(qryParamMap.procState)!''}" >
								<@enumOpts enumNm="TxnEnums.ProcStatus" showEmptyOpt="true" showCode="true" 
								  emptyOptDesc="--请选择--" selected="${(qryParamMap.procState)!''}"/>
							</select>
						</td>
						<td>备注：</td>
						<td>
							<input class="easyui-validatebox" type="text" style="width: 160px;"
								id="comment" name="_QRY_comment" value="${(qryParamMap.comment)!''}">
						</td>
					</tr>
					
					<tr>	
						<td>打款银行帐号：</td>
						<td>
							<input class="easyui-validatebox" type="text" style="width: 150px;"
								id="accountNo" name="_QRY_accountNo" value="${(qryParamMap.accountNo)!''}">
						</td>
						<td>打款帐号户名：</td>
						<td>
							<input class="easyui-validatebox" type="text" style="width: 200px;"
								id="accountName" name="_QRY_accountName" value="${(qryParamMap.accountName)!''}">
						</td>
						<td>打款商户名称：</td>
						<td>
							<input class="easyui-validatebox" type="text" style="width: 150px;"
								id="mchntName" name="_QRY_mchntName" value="${(qryParamMap.mchntName)!''}">
						</td>
						<td>交易状态：</td>
						<td>
							<select id="txnState" name="_QRY_txnState" value="${(qryParamMap.txnState)!''}" >
								<@enumOpts enumNm="TxnEnums.TxnStatus" showEmptyOpt="true" showCode="true" 
								  emptyOptDesc="--请选择--" selected="${(qryParamMap.txnState)!''}"/>
							</select>
						</td>
					</tr>
									
				</table>
			</@qryDiv>
			<@qryResultDiv  
				detailFlg=true detailFuncCode="3000310002"
				editFlg=true editFuncCode="3000310005"
				addFlg=true addFuncCode="3000310003"
				deleteFlg=true deleteFuncCode="3000310007"
				>
				<@authCheck funcCode='3000310011'>
					<a id="resetDataBtn" href="javascript:void(0)" class="easyui-linkbutton" 
					   data-options="iconCls:'icon-reload'" >重置打款表</a>
				</@authCheck>
				<@authCheck funcCode='3000310005'>
					<a id="enableAllBtn" href="javascript:void(0)" class="easyui-linkbutton" 
					   data-options="iconCls:'icon-ok'" >批量开启</a>
					<a id="disableAllBtn" href="javascript:void(0)" class="easyui-linkbutton" 
					   data-options="iconCls:'icon-no'" >批量关闭</a>
				</@authCheck>   
				   
				<@authCheck funcCode='3000310012'>
					<a id="withdrawBtn" href="javascript:void(0)" class="easyui-linkbutton" 
					   data-options="iconCls:'icon-save'" >当日打款</a>
				</@authCheck>
				<@authCheck funcCode='3000310014'>
					<a id="summaryBtn" href="javascript:void(0)" class="easyui-linkbutton" 
					   data-options="iconCls:'icon-sum'" >加 总</a>	
				</@authCheck>	   
				
				<@authCheck funcCode='3000310013'>
					<a id="exportBtn" href="javascript:void(0)" class="easyui-linkbutton" 
						data-options="iconCls:'icon-print'">导出流水</a>
				</@authCheck>
				<br/>
				<@authCheck funcCode='3000310014'>
				<div id="Calculation">
					<table>
						<tr>
							<td>实际打款总金额：</td>
							<td align="right" width="100px" ><span id="sumTxnAmt"></span>&nbsp;元</td>
						</tr>
					</table>
				</div>
				</@authCheck>
			</@qryResultDiv>
			
			<div id="detailDiv"></div>
		</div>	
	</body>
<html>