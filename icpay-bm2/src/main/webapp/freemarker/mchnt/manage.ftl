<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="商户信息" base="mchnt" qryFuncCode="1000010001"
			addFlg=true addFuncCode="1000010001"
			detailFlg=true detailUrl='"detail.do?mchntCd=" + sel.mchntCd' detailDivWidth=800 detailDivHeight=550 detailFuncCode="1000010002"
			editFlg=true editUrl='"edit.do?mchntCd=" + sel.mchntCd' editFuncCode="1000010005" deleteFuncCode="1000010007"
			deleteFlg=true deleteUrl='"delete.do?mchntCd=" + sel.mchntCd' deleteSuccMsg="商户删除任务已提交，请等待审核!">
			$("#txnConfigBtn").click(function() {
				var sel = $("#qryResultTbl").datagrid("getSelected");
				if (sel == null) {
					alert("请选择一条记录");
				} else {
					if (sel.mchntSt == '1') {
						$.jumpTo(baseUrl + "txnConfig.do?mchntCd=" + sel.mchntCd);
					} else {
						alert("请选择有效商户");
					}
				}
			});
			$("#resetPwdBtn").click(function() {
				if (confirm("是否确认重置商户服务网站登录密码?")) {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						if ((sel.mchntSt == '1') || (sel.mchntSt == '9')) {
							var url = baseUrl + "resetPwd.do?mchntCd=" + sel.mchntCd;
							$.post(url, function(data) {
								$.processAjaxResp(data, function(r) {
									$("#pwdDiv").html("商户服务网站登录密码重置成功，请通知商户：" + r.respData.newPwd).fadeIn();
								});
							});
						} else {
							alert("请选择有效商户");
						}
					}
				}
			});
			
			$("#unlockAdminBtn").click(function() {
				var sel = $("#qryResultTbl").datagrid("getSelected");
				if (sel == null) {
					alert("请选择一条记录");
				} else {
					if (confirm("确认解除该商户的admin锁定?")) {
						if ((sel.mchntSt == '1') || (sel.mchntSt == '9')) {
							var url = baseUrl + "unlockAdmin.do?mchntCd=" + sel.mchntCd;
							$.post(url, function(data) {
								$.processAjaxResp(data, function(r) {
									$("#pwdDiv").html("商户admin解除锁定成功，请通知商户：" + r.respData.mchntCd).fadeIn();
								});
							});
						}else {
							alert("请选择有效商户");
						}
					}
				}
			});
			
			<@authCheck funcCode='1000010011'>
			$("#resetGaCodeBtn").click(function() {
				var sel = $("#qryResultTbl").datagrid("getSelected");
				if (sel == null) {
					alert("请选择一条记录");
				} else {
					if (confirm("是否确认重置商户管理员谷歌验证码吗?")) {
						if ((sel.mchntSt == '1') || (sel.mchntSt == '9')) {
							var url = baseUrl + "resetGaCode.do?mchntCd=" + sel.mchntCd;
							$.post(url, function(data) {
								$.processAjaxResp(data, function(r) {
									$("#pwdDiv").html("商户服务网站管理员的登录谷歌验证码重置成功，请通知商户：" + r.respData.mchntCd).fadeIn();
								});
							});
						}else {
							alert("请选择有效商户");
						}
					}
				}
			});
			</@authCheck>
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/mchnt/qry.do" qryFuncCode="1000010001">
			<table class="qry_tbl">
				<tr>
					<td>商户号：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="mchntCdInpt" maxLength="15"
							name="_QRY_mchntCd" value="${(qryParamMap.mchntCd)!''}">
					</td>
					<td>商户名称：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="mchntCnNmInpt" maxLength="120"
							 name="_QRY_mchntCnNm" value="${(qryParamMap.mchntCnNm)!''}">
					</td>
					<td>状态：</td>
					<td>
						<select id="mchntSt" name="_QRY_mchntSt">
							<@enumOpts enumNm='CommonEnums.MchntSt' showEmptyOpt='true'
								selected="${(qryParamMap.mchntSt)!''}" />
						</select>
					</td>
				</tr>
				<tr>
					<td>代理商代码：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="agentInpt" maxLength="15"
							name="_QRY_agentCd" value="${(qryParamMap.agentCd)!''}">
					</td>
					<td>接入方式：</td>
					<td>
						<select id="tradeType" name="_QRY_apiType">
							<@enumOpts enumNm='TxnEnums.ApiType' showEmptyOpt='true'
								selected="${(qryParamMap.apiType)!''}" />
						</select>
					</td>
					<td>域名：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="trustDomainInpt" maxLength="30"
							name="_QRY_trustDomain" value="${(qryParamMap.trustDomain)!''}">
					</td>
				</tr>
				<tr>
					<td>站点：</td>
					<td>
						<select name="_QRY_siteId" id="siteId" >
							<@enumOpts enumNm="CommonEnums.Site" showEmptyOpt="true" showCode="true" />
						</select>
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv  
			addFlg=true addFuncCode="1000010003"
			detailFlg=true detailFuncCode="1000010002"
			editFlg=true editFuncCode="1000010005"
			 deleteFlg=true deleteFuncCode="1000010007">
			
			 <@authCheck funcCode='1000010010'>
				<a id="resetPwdBtn" href="javascript:void(0)" class="easyui-linkbutton">重置登录密码</a>
			</@authCheck>
			<@authCheck funcCode='1000010011'>
				<a id="resetGaCodeBtn" href="javascript:void(0)" class="easyui-linkbutton">重置谷歌验证码</a>
		    </@authCheck>
				<a id="unlockAdminBtn" href="javascript:void(0)" class="easyui-linkbutton">解除admin锁定</a>
		</@qryResultDiv>
		
		<div id="pwdDiv" style="display:hidden;color:red; font-weight: bold;"></div>
		
		<div id="detailDiv"></div>
	</body>
<html>