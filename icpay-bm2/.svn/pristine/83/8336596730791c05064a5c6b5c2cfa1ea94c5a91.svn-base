<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="商户账户信息" base="mchntAccout" qryFuncCode="1000070001">
		$("#monSel").monthSel();
			<@authCheck funcCode='1000070002'>
				$("#accoutFlowBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
							$.jumpTo(_ctx + "/mchntAccout/accountFlow.do?mchntCd=" + sel.mchntCd);
					}
				});
			</@authCheck>
			<@authCheck funcCode='1000070012'>
				$("#obligatedFlowBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
							$.jumpTo(_ctx + "/mchntAccout/obligatedFlow.do?mchntCd=" + sel.mchntCd);
					}
				});
			</@authCheck>
			<@authCheck funcCode='1000070004'>
				$("#enableBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						if (sel.state == '1') {
							alert('该商户账户已经为"1-启用"状态');
							return;
						}
						if (confirm("确认启用?")) {
							if (dealFlag) {//重复提交控制
								alert("正在处理请求，请稍等......");
								return false;
							}
							dealFlag = true;
							var url = baseUrl + "enable.do?mchntCd=" + sel.mchntCd + "&currCd=" + sel.currCd;
							$.post(url,
								function(data) {
									$.processAjaxResp(data, function() {
										alert("商户账户启用成功");
										dealFlag = false;
										$("#qryForm").submit();
									});
								}
							);
						}
					}
				});
			</@authCheck>
			<@authCheck funcCode='1000070005'>
				$("#disableBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						if (sel.state == '0') {
							alert('该商户账户已经为"0-无效"状态');
							return;
						}
						if (confirm("确认禁用?")) {
							if (dealFlag) {//重复提交控制
								alert("正在处理请求，请稍等......");
								return false;
							}
							dealFlag = true;
							var url = baseUrl + "disable.do?mchntCd=" + sel.mchntCd + "&currCd=" + sel.currCd;
							$.post(url,
								function(data) {
									$.processAjaxResp(data, function() {
										alert("商户账户禁用成功");
										dealFlag = false;
										$("#qryForm").submit();
									});
								}
							);
						}
					}
				});
			</@authCheck>
			<@authCheck funcCode='1000070006'>
				$("#acctAdjusttBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null || sel.state != '1') {
						alert("请选择一条有效商户账户记录");
					} else {
						$.jumpTo(baseUrl + "accountAdjust.do?mchntCd=" + sel.mchntCd + "&currCd=" + sel.currCd);
					}
				});
			</@authCheck>
			
			<@authCheck  funcCode='1000070009'>
				$("#amtSum").click(function() {
					$.ajax({
						type : "POST",
						url : baseUrl+"amtSum.do",
						async: true,
						cache:false,
						data: $("#qryForm").serialize(),
						success : function(mm) {
							var obj = parseJson(mm);
							$("#sumAvailableBalance").text(obj.sumAvailableBalance);
							$("#sumRealAvailableBalance").text(obj.sumRealAvailableBalance);
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



				$.ajax({
					type : "POST",
					url : _ctx + "/chnlAccount/amtSum.do",
					async: true,
					cache:false,
					data: {"_QRY_mchntCd":$("#chnlMchntCd").val()},
					success : function(mm) {
						var obj = parseJson(mm);
						$("#chnlSumAvailableBalance").text(obj.sumAvailableBalance);
						$("#chnlSumFrozenBalance").text(obj.sumFrozenBalance);
						$("#chnlSumFrozenT1Balance").text(obj.sumFrozenT1Balance);
						$("#chnlSumDayTxnAmt").text(obj.sumDayTxnAmt);
						$("#chnlSumDayWithdrawAmt").text(obj.sumDayWithdrawAmt);
					},
					error:function(){
						alert("系统异常，请联系管理员！");
						return  false;
					}
				});

				});
			</@authCheck>
			
			<@authCheck funcCode='1000070010'>
				$("#obligatedModifyBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null || sel.state != '1') {
						alert("请选择一条有效商户账户记录");
					} else {
						$.jumpTo(baseUrl + "obligatedBalanceModify.goto.do?mchntCd=" + sel.mchntCd + "&currCd=" + sel.currCd);
					}
				});
			</@authCheck>
			
			
			$("#chnlMchntCd").blur(function(){
  				<#--var ch_reg = /^[u4E00-u9FA5{u-z}]+$/;-->
		        var value = $("#chnlMchntCd").val();

		        if(value == ''){
            		return false;
        		}
<#--
				if(!ch_reg.test(value.trim())){
					alert('不可输入汉字');
					return false;
				}
				-->
		        <#--调用ajax判断用户是否存在-->
		        $.ajax({
						type : "POST",
						url : baseUrl+"existChnlMchnt.do",
						data: {"chnlMchntCd":value},
						success : function(mm) {
							if(mm!=''){
								alert(mm+"渠道商户号不存在，请检查后输入");
								$("#qryForm").attr("onSubmit","return false")
							}else{
								$("#qryForm").attr("onSubmit","return true")
							}
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
		<@qryDiv qryUrl="/mchntAccout/qry.do" qryFuncCode="1000070001">
			<table class="qry_tbl">
				<tr>
					<td>商户号：</td>
					<td>
						<input class="easyui-validatebox" style="width: 200px" type="text" id="mchntCdInpt" maxLength="15"
							name="_QRY_mchntCd" value="${(qryParamMap.mchntCd)!''}">
					</td>
					<td>商户名称：</td>
					<td>
						<input class="easyui-validatebox" style="width: 200px" type="text" id="mchntNameInpt" maxLength="15"
							name="_QRY_mchntName" value="${(qryParamMap.mchntName)!''}">
					</td>

				</tr>
				<tr>
					<td>查询方式：</td>
					<td>
						<select style="width: 220px" id="timeQryMethod" name="_QRY_timeQryMethod" value="${(qryParamMap.timeQryMethod)!''}">
							<@enumOpts enumNm="BmEnums.QryMethod" showEmptyOpt="true" showCode="false" emptyOptDesc="--请选择--" selected="${(qryParamMap.timeQryMethod)!''}"/>
						</select>
					</td>
					<td>币别:</td>
					<td>
						<select  name="_QRY_currCd" id="currCd" >
							<@enumOpts enumNm="CurrencyEnums.CurrType" showEmptyOpt="false" showCode="true" selected="${(qryParamMap.currCd)!'156'}"/>
						</select>
					</td>
				</tr>
				<tr>
					<td>渠道商户号(路由)：</td>
					<td>
						<input class="easyui-validatebox" style="width: 200px" type="text" id="chnlMchntCd"
							name="_QRY_chnlMchntCd" value="${(qryParamMap.chnlMchntCd)!''}"><span>(多个用英文逗号隔开)</span>
					</td>
					<td>站点：</td>
					<td>
						<select name="_QRY_siteId" id="siteId" >
							<@enumOpts enumNm="CommonEnums.Site" showEmptyOpt="true" showCode="true" />
						</select>
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv  >
			<@authCheck funcCode='1000070002'>
			 	<a id="accoutFlowBtn" href="javascript:void(0)" class="easyui-linkbutton">账户流水</a>
			 </@authCheck>
			 <!--
			 <@authCheck funcCode='1000070004'>
			 	<a id="enableBtn" href="javascript:void(0)" class="easyui-linkbutton">账户启用</a>
			 </@authCheck>
			 <@authCheck funcCode='1000070005'>
			 	<a id="disableBtn" href="javascript:void(0)" class="easyui-linkbutton">账户禁用</a>
			 </@authCheck>
			-->
			 <@authCheck funcCode='1000070007'>
			 	<a id="acctAdjusttBtn" href="javascript:void(0)" class="easyui-linkbutton">账户调整</a>
			 </@authCheck>
			 
			 <@authCheck funcCode='1000070009'>
				<a id="amtSum" href="javascript:void(0)" class="easyui-linkbutton"  data-options="iconCls:'icon-sum'">加 总</a>
			</@authCheck>
			
			 <@authCheck funcCode='1000070010'>
				<a id="obligatedModifyBtn" href="javascript:void(0)" class="easyui-linkbutton"  data-options="iconCls:'icon-edit'">调整保留余额</a>
			</@authCheck>
			<@authCheck funcCode='1000070012'>
				<a id="obligatedFlowBtn" href="javascript:void(0)" class="easyui-linkbutton"  data-options="iconCls:'icon-search'">保留余额记录</a>
			</@authCheck>
			<br/>
			<div id="Calculation">
				<table>
					<tr>
						<td>实际可代付总额：</td>
						<td align="right" width="100px" ><span id="sumRealAvailableBalance"></span>&nbsp;元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
					<tr>
						<td>可代付总额：</td>
						<td align="right" width="100px" ><span id="sumAvailableBalance"></span>&nbsp;元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td width="150px" >渠道商户可代付总额：</td>
						<td align="right" width="100px" ><span id="chnlSumAvailableBalance"></span>&nbsp;元</td>
					</tr>
					<tr>
						<td>冻结总额：</td>
						<td align="right" width="100px" ><span id="sumFrozenBalance"></span>&nbsp;元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td width="150px" >渠道商户冻结总额：</td>
						<td align="right" width="100px" ><span id="chnlSumFrozenBalance"></span>&nbsp;元</td>
					</tr>
					<tr>
						<td>T1帐户总额：</td>
						<td align="right" width="100px" ><span id="sumFrozenT1Balance"></span>&nbsp;元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td width="150px" >T1帐户总额：</td>
						<td align="right" width="100px" ><span id="chnlSumFrozenT1Balance"></span>&nbsp;元</td>
					</tr>
					<tr>
						<td>当日累计交易总额：</td>
						<td align="right" width="100px" ><span id="sumDayTxnAmt"></span>&nbsp;元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td width="150px" >当日累计交易总额：</td>
						<td align="right" width="100px" ><span id="chnlSumDayTxnAmt"></span>&nbsp;元</td>
					</tr>
					<tr>
						<td>当日累计提现总额：</td>
						<td align="right" width="100px" ><span id="sumDayWithdrawAmt"></span>&nbsp;元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td width="150px" >当日累计提现总额：</td>
						<td align="right" width="100px" ><span id="chnlSumDayWithdrawAmt"></span>&nbsp;元</td>
					</tr>
				</table>
			</div>
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
	</body>
<html>