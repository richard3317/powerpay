<#include "/common/manage_macro.ftl" />
<#assign label = "渠道商户账户管理">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="渠道商户账户管理" base="chnlAccount" qryFuncCode="2000040001"
			detailFlg=true  detailDivWidth=800 detailDivHeight=550 >
			$("#monSel").monthSel();
			<@authCheck funcCode='2000040004'>
				$("#accoutFlowBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
								$.jumpTo(_ctx + "/chnlAccount/accountFlow.do?chnlId=" + sel.chnlId
								+"&mchntCd="+sel.mchntCd);
					}
				});
			</@authCheck>
			
			<@authCheck funcCode='2000040010'>
				$("#obligatedFlowBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
							$.jumpTo(_ctx + "/chnlAccount/obligatedFlow.do?chnlId=" + sel.chnlId
								+"&mchntCd="+sel.mchntCd);
					}
				});
			</@authCheck>
			
			<@authCheck funcCode='2000040002'>
				$("#accountMngBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						$.jumpTo(_ctx + "/chnlAccount/accountAdjust.do?chnlId=" + sel.chnlId + "&currCd=" + sel.currCd
							+"&mchntCd="+sel.mchntCd);
					}
				});
			</@authCheck>
			
			<@authCheck  funcCode='2000040008'>
				$("#amtSum").click(function() {
					$.ajax({
						type : "POST",
						url : baseUrl+"amtSum.do",
						async: true,
						cache:false,
						data: $("#qryForm").serialize(),
						success : function(mm) {
							var obj = parseJson(mm);
							$("#sumRealAvailableBalance").text(obj.sumRealAvailableBalance);
							$("#sumAvailableBalance").text(obj.sumAvailableBalance);
							$("#sumObligatedBalance").text(obj.sumObligatedBalance);
							$("#sumFrozenBalance").text(obj.sumFrozenBalance);
							$("#sumFrozenT1Balance").text(obj.sumFrozenT1Balance);
							$("#sumDayTxnAmt").text(obj.sumDayTxnAmt);
							$("#sumDayWithdrawAmt").text(obj.sumDayWithdrawAmt);
						},
						error:function(){
					    	alert("系统异常，请联系管理员！");
					    	return  false;
					    }
					});
				});
			</@authCheck>
			
			<@authCheck funcCode='2000040009'>
				$("#obligatedModifyBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null || sel.state != '1') {
						alert("请选择一条有效渠道商户账户记录");
					} else {
						$.jumpTo(baseUrl + "obligatedBalanceModify.goto.do?chnlId=" + sel.chnlId
							+"&mchntCd="+sel.mchntCd + "&currCd=" + sel.currCd);
					}
				});
			</@authCheck>
			
		</@head>
	</head>
	
	<body>
		<@qryDiv qryUrl="/chnlAccount/qry.do" showBtn=true qryFuncCode="2000040001">
			<table class="qry_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">渠道：</td>
					<td class="content">
						<#-- <input class="easyui-validatebox" type="text" maxlength="120" maxLength="2"
							id="" name="_QRY_chnlId" value="${(qryParamMap.chnlId)!''}" id="chnlId" /> -->
						
						<select id="chnlIdSel" name="_QRY_chnlId">
							<#-- <@enumOpts enumNm='TxnEnums.ChnlId' showEmptyOpt='true'  
								selected="${(qryParamMap.chnlId)!''}" />-->
							<@enumOpts enumNm='TxnEnums.ChnlId' showCode='true' showEmptyOpt='true' selected="${(qryParamMap.chnlId)!''}" />
						</select>	
					</td>
					<td class="label">渠道商户号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="120"
							id="chnlMchntCd" name="_QRY_mchntCd" />
					</td>
					<td>渠道商户名称：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="mchntNameInpt" maxLength="15"
							name="_QRY_mchntCnNm" value="${(qryParamMap.mchntCnNm)!''}">
					</td>
					<td class="label">状态：</td>
					<td class="content">
						<select id="" name="_QRY_state" data-options="editable:false">
							<option value="">--请选择--</option>
							<option value="0">0-无效</option>
							<option value="1">1-有效</option>
							<option value="2">2-锁定</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>查询方式：</td>
					<td>
						<select id="timeQryMethod" name="_QRY_timeQryMethod" value="${(qryParamMap.timeQryMethod)!''}">
							<@enumOpts enumNm="BmEnums.QryMethod" showEmptyOpt="true" showCode="false" emptyOptDesc="--请选择--" selected="${(qryParamMap.timeQryMethod)!''}"/>
						</select>
					</td>
					<td>币别：</td>
					<td>
						<select  name="_QRY_currCd" id="currCd" >
								<@enumOpts enumNm="CurrencyEnums.CurrType" showEmptyOpt="true" showCode="false"
								selected="${(qryParamMap.currCd)!'156'}" />
						</select>
					</td>
					<td>
					<input type="checkbox" id="showBmchnt" style="width:18px;height:18px" checked="true">&nbsp展示关联商户
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv >
			<@authCheck funcCode='2000040004'>
			 	<a id="accoutFlowBtn" href="javascript:void(0)" class="easyui-linkbutton">账户流水</a>
			 </@authCheck>
			<@authCheck funcCode='2000040002'>
			 	<a id="accountMngBtn" href="javascript:void(0)" class="easyui-linkbutton">账户调整</a>
			 </@authCheck>
			<@authCheck funcCode='2000040008'>
				<a id="amtSum" href="javascript:void(0)" class="easyui-linkbutton"  data-options="iconCls:'icon-sum'">加 总</a>
			</@authCheck>
			<@authCheck funcCode='2000040009'>
				<a id="obligatedModifyBtn" href="javascript:void(0)" class="easyui-linkbutton"  data-options="iconCls:'icon-edit'">调整保留余额</a>
			</@authCheck>
			<@authCheck funcCode='2000040010'>
				<a id="obligatedFlowBtn" href="javascript:void(0)" class="easyui-linkbutton"  data-options="iconCls:'icon-search'">保留余额记录</a>
			</@authCheck>
			<br/>
			<div id="Calculation">
				<table>
					<tr>
						<td>实际可代付总额：</td>
						<td align="right" width="100px" ><span id="sumRealAvailableBalance"></span>&nbsp;元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>当日累计交易总额：</td>
						<td align="right" width="100px" ><span id="sumDayTxnAmt"></span>&nbsp;元</td>
					</tr>
					<tr>
						<td>可代付总额：</td>
						<td align="right" width="100px" ><span id="sumAvailableBalance"></span>&nbsp;元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>当日累计提现总额：</td>
						<td align="right" width="100px" ><span id="sumDayWithdrawAmt"></span>&nbsp;元</td>
					</tr>
					<tr>
						<td>保留余额总额：</td>
						<td align="right" width="100px" ><span id="sumObligatedBalance"></span>&nbsp;元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
					<tr>
						<td>冻结总额：</td>
						<td align="right" width="100px" ><span id="sumFrozenBalance"></span>&nbsp;元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
					<tr>
						<td>T1帐户总额：</td>
						<td align="right" width="100px" ><span id="sumFrozenT1Balance"></span>&nbsp;元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
				</table>
			</div>
		</@qryResultDiv>
	</body>
</html>