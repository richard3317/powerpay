<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<#include "/common/macro.ftl">

<#assign htmlHead>
	<script src="${ctx}/resources/js/jquery.dataTables.min.js"></script>
	<script src="${ctx}/resources/bootstrap/js/dataTables.bootstrap.min.js"></script>
</#assign>
<#assign htmlJS>
	function getImg(role,str){
		otpSecret = $("#otpSecret_" + role +"_" + str).text();
		var img = document.getElementById("img"+"_" + role +"_" + str);
		var mchntCd = $("#mchntCd").val();
		var uid = mchntCd + "-" + str;
		img.src = "${ctx}/gauth?uid=" + uid + "&s=" + otpSecret;
		img.onerror=null;
	}

function hideSecretImg(str){
	if(str !='2'){
		$("#loginImg_" + str).hide();
	}else{
		$("#downImg").hide();
	}
}
function binding(role,userId) {
	var authCode=$("#authCode_" + role +"_"+ userId).val();
	var otpSecret="";
	otpSecret = $("#otpSecret_" + role +"_"+ userId).html();
	
	$("#form1").serialize();
	
	var url = "${ctx}/secrity/authCodeCheck?userId=" +userId  +"&authCode=" +authCode + "&otpSecret=" + otpSecret;
	
	  $.post(url, function(data) {                                                                                                                                                                                                                                                                                                                                                                                                                                                        
	<#-- $.get(url, function(data) { -->
		$.processAjaxResp(data, function(r) {
		if(r.respData == "ZZ"){
			 alert("<@msg code='secritySetting.绑定失败！' default='绑定失败！'/>");
			 $("#authCode_" + role +"_"+ userId).val('');
		}else{
			 alert("<@msg code='secritySetting.绑定成功，請重新登入！' default='绑定成功，請重新登入！'/>");
			 $.jumpTo("${ctx}/logout");
	    	 $("#authCode_"+ role +"_"+ userId).val('');
		     $("#statusId_"+ role +"_"+ userId).html('<font color="red" size="3"><strong>已绑定</strong></font>');
		}
		});
	});
		 	
}
function isEmpty(obj){
		if (obj===null) return true;
		if ("" === obj) return true;
		return false;
	}
	
	function showError(str) {
		var $err = this.$("#billError");
		$err.text(str);
		$err.show();
		<#--  errorstr=""; -->
	}
	function hideError() {
		$("#billError").hide();
		<#--  errorstr=""; -->
	}
function resetMacKey() {
		var url = "${ctx}/secrity/genMacKey.do";
		$.get(url, function(data) {
			$.processAjaxResp(data, function(r) {
				if (isEmpty(r)) {
					alert("<@msg code='secritySetting.系统错误！' default='系统错误！'/>");
					return false;
				} 
				var lst =r.list;
				if(!isEmpty(lst)) {
					$("#protectKeyA").attr("value",lst[0].protectKeyA);
					$("#protectKeyB").attr("value",lst[0].protectKeyB);
					$("#protectAlgorithm").attr("value",lst[0].protectAlgorithm);
					$("#macKey").attr("value",lst[0].macKey);
					$("#div1").html(lst[0].protectKeyA);
					$("#div2").html(lst[0].protectKeyB);
					$("#div3").html(lst[0].protectAlgorithm);
					$("#td1").html(lst[0].macKey.substr(0,6)+"*******"+lst[0].macKey.substr(-4));
					alert("<@msg code='secritySetting.重置成功' default='重置成功'/>");
				}
				else{
					alert(r.respMsg);
				}
			});
		});
	}
	function showSecretImg(role,userId) {
		if(role =='2'){
			$("#downImg").show();
		}else{
			var otpSecret = $("#otpSecret_" + role +"_" + userId).text();
			if(otpSecret !== null && otpSecret !== '' && otpSecret !== undefined){
				getImg(role, userId);
				$("#loginImg_" + role +"_"+userId).show();
			}else{
				var method ='1';  //查看
				var status="";
				var url = "${ctx}/secrity/getSecret.do";
				$.ajax({
					url: url,
					async: false,
					type : "POST",
					data: {
						role : role,
						userId : userId,
						method : method
					},
					success: function(data) {
						var dataObj = JSON.parse(data);
						var lst = dataObj.respData.secretList;
						if(!isEmpty(lst)){
							$("#loginImg_" + role +"_"+userId).show();
							$("#otpSecret_"+ role +"_"+userId).html(lst[0].otpSecret);
							getImg(role, userId);
						}else{
							alert('<@msg code='secritySetting.错误' default='错误'/>');
						}
					},
				    error:function(){
				    	errorstr = "<@msg code='secritySetting.连线异常，请联系管理员！' default='连线异常，请联系管理员！'/>";
				    	showError(errorstr);
				    }
				});
			}
		}
	}

	
<#-- $(function() {
	var loginType = ${loginType!'1'};
	if(loginType == '1'){
		alert("仅管理员可以查看安全设置信息");
		$.jumpTo("${ctx}/index?loginType="+loginType);
	}
}); -->

$(function() {
	if("su" == $("#role").val()){
		var protectKeyA = ""+$("#protectKeyA").val();
		var protectKeyB = ""+$("#protectKeyB").val();
		var protectAlgorithm =""+$("#protectAlgorithm").val();
		var macKey = ""+$("#macKey").val();
		
		$("#div1").html(protectKeyA);
		$("#div2").html(protectKeyB);
		$("#div3").html(protectAlgorithm);
		$("#td1").html(macKey.substr(0,6)+"*******"+macKey.substr(-4));
		$("#qryBtn").click(function() {
			var btn = document.getElementById("qryBtn");
			var macKey = ""+$("#macKey").val();
			if(btn.innerHTML =='<@msg code='secritySetting.显示全密钥' default='显示全密钥'/>'){
				$("#td1").html(macKey);
				btn.innerHTML ="<@msg code='secritySetting.隐藏全密钥' default='隐藏全密钥'/>";
			}else{
				$("#td1").html(macKey.substr(0,6)+"*******"+macKey.substr(-4));
				btn.innerHTML ="<@msg code='secritySetting.显示全密钥' default='显示全密钥'/>";
			}
		});
	}
	var hUserRole = $("#hUserRole").val();
	var hUserId = $("#hUserId").val();
	if(!isEmpty(hUserRole) && !isEmpty(hUserId)) {
		showSecretImg(hUserRole, hUserId);	
	}
});
function showOrShrink(macKey){
		var btn = document.getElementById("qryBtn");
		if(btn.innerHTML =='<@msg code='secritySetting.显示全密钥' default='显示全密钥'/>'){
			$("#td1").html(macKey);
			btn.innerHTML ="<@msg code='secritySetting.隐藏全密钥' default='隐藏全密钥'/>";
		}else{
			$("#td1").html(macKey.substr(0,6)+"*******"+macKey.substr(-4));
			btn.innerHTML ="<@msg code='secritySetting.显示全密钥' default='显示全密钥'/>";
		}
	}

</#assign>
</head>

<#assign htmlContent>
	<ol class="breadcrumb">
		<li><a href="#"><@msg code='secritySetting.商户服务' default='商户服务'/></a></li>
		<li class="active"><@msg code='secritySetting.安全设置' default='安全设置'/></li>
	</ol>
		<div class="panel panel-default">
			<div class="panel-body">
				<table width="100%">
					<tr>
						<td style="padding: 10px;" width="30%"><@msg code='secritySetting.手机绑定谷歌身份器，可以为 登录添加第二层保护；即使有人通过恶意软件窃取了您的登录密码，谷歌验证码也能保护您的账户安全。' default='手机绑定谷歌身份器，可以为 登录添加第二层保护；即使有人通过恶意软件窃取了您的登录密码，谷歌验证码也能保护您的账户安全。'/>
					    	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="showSecretImg('2');"><@msg code='secritySetting.扫码安装' default='扫码安装'/></a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="hideSecretImg('2');"><@msg code='secritySetting.隐藏条码' default='隐藏条码'/></a>
						</td>
					</tr>
				</table>
				<div id ="downImg" style="display:none;" >
					<table>
					<tr>
						<td>
							<img src="${ctx}/resources/images/ga-2.png" alt="<@msg code='secritySetting.IOS版Google身份认证器' default='IOS版Google身份认证器'/>" />
						</br>
							<@msg code='secritySetting.IOS版 Google Authenticator' default='IOS版 Google Authenticator'/>
						</td>
						<td>&nbsp;
						</td>
						<td>&nbsp;
						</td>
						<td>&nbsp;
						</td>
						<td>
							<img src="${ctx}/resources/images/ga-1.png" alt="<@msg code='secritySetting.Android版Google身份认证器' default='Android版Google身份认证器'/>" />
						</br>
							<@msg code='secritySetting.Android版 Google Authenticator' default='Android版 Google Authenticator'/>
						</td>
					</tr>
					</table>
				</div>
			</div>
		</div>
	<#list muser as r>
	<form action="" id ="form1">
		<div class="panel panel-default">
		<div class="panel-heading">
			<h4 class="panel-title">
			<#if ((r['userRole']!'') == 'su')>
				<font color="red"> <@msg code='secritySetting.管理员' default='管理员'/> </font>
			<#elseif ((r['userRole']!'') == 'wd')> 
				<@msg code='secritySetting.代付用户' default='代付用户'/>
			<#else>
				<@msg code='secritySetting.一般用户' default='一般用户'/>
			</#if>
			 <@msg code='secritySetting.身份验证设置' default='身份验证设置'/></h4>
			<input type="hidden" id="mchntCd" name="mchntCd" value="${mchntCd}" />
			<input type="hidden" id="userId" name="userId" value="${userId}" />
			<input type="hidden" id="role" name="role" value="${role}" />
 			<input type="hidden" id="otpSecretPy" name="otpSecretPy" value="" />
			<input type="hidden" id="optSecretSu" name="optSecretSu" value="" />
			<input type="hidden" id="optSecretWd" name="optSecretWd" value="" />
			<input type="hidden" id="showType" name="showType" value="${showType}" />
			<input type="hidden" id="protectKeyA" name="protectKeyA" value="${protectKeyA}" />
			<input type="hidden" id="protectKeyB" name="protectKeyB" value="${protectKeyB}" />
			<input type="hidden" id="protectAlgorithm" name="protectAlgorithm" value="${protectAlgorithm}" />
			<input type="hidden" id="macKey" name="macKey" value="${macKey}" />
		</div>
		<div class="panel-body">
			<table width="100%">
				<tr>
					<td style="padding: 10px;" width="25%"><@msg code='secritySetting.通过Google验证器绑定指定用户' default='通过Google验证器绑定指定用户'/></td>
				</tr>
				</table>
				<table width="30%">
				<tr width="10%">
					<td width="5%" style="padding: 12px; padding-right: 5px; text-align: left;"><@msg code='secritySetting.状态：' default='状态：'/></td>
					<td width="5%" style="padding: 2px; padding-right: 5px; text-align: left;">
					<div id ="statusId_${r['userRole']!''}_${r['userId']!''}" style="margin-bottom: 0; width: 80px;">
					<#if ((r['otpSecret']!'') != '') >
						<font color="red" size="3"><strong><@msg code='secritySetting.已绑定' default='已绑定'/></strong></font>
					<#else>
						<font color="red"  size="3"><strong><@msg code='secritySetting.未绑定' default='未绑定'/></strong></font>
					</#if>
					</div>
					</td>
				</tr>
				</table>
				<#if ((r['otpSecret']!'') == '') >
					<input type="hidden" id="hUserRole" value="${r['userRole']!''}" />
					<input type="hidden" id="hUserId" value="${r['userId']!''}" />
					<div id ="loginImg_${r['userRole']!''}_${r['userId']!''}" style="display:none;" >
						<label style="padding: 10px;" width="30%"><@msg code='secritySetting.绑定账号' default='绑定账号'/>： ${r['userId']!''}-${r['userId']!''}</label>
						</br>
						<label style="padding: 10px;" width="30%"><@msg code='secritySetting.私钥' default='私钥'/>：  <span id ="otpSecret_${r['userRole']!''}_${r['userId']!''}">${r['otpSecret']!''}</span></label>
						</br>
						</br>
						<img src="" id="img_${r['userRole']!''}_${r['userId']!''}" onerror="getImg('${r['userRole']!''}','${r['userId']!''}')" class="img-thumbnail" style="width: 256px; height: 256px;"alt="<@msg code='secritySetting.二维码' default='二维码'/>" />
						</br>
						<label style="padding: 10px;" width="30%"><@msg code='secritySetting.请输入谷歌验证器上的6位验证码' default='请输入谷歌验证器上的6位验证码'/></label>
						</br>
						<input type="text" placeholder="<@msg code='secritySetting.请输入Google验证码' default='请输入Google验证码'/>" maxLength="6" id="authCode_${r['userRole']!''}_${r['userId']!''}" style="width: 210px;" />
						<a class="btn btn-primary btn-sm" href="#" onclick="binding('${r['userRole']!''}','${r['userId']!''}');" role="button"><@msg code='secritySetting.绑定' default='绑定'/></a>
					</div>
				</#if>
			</div>
		</div>
		</form>
	</#list>
	
	<#if ("su" == (Session.userRole!"")) >
		<div class="panel panel-default">
			<div class="panel-heading"><h4 class="panel-title"><@msg code='secritySetting.安全密钥' default='安全密钥'/></h4></div>
			<div class="panel-body">
				<table width="50%">
				<tr>
					<td style="padding: 10px;" width="27%" ><@msg code='secritySetting.安全密钥(MAC_KEY)' default='安全密钥(MAC_KEY)'/>:</td>
				</tr>
				</table>
				<table width="50%">
				<tr width="50%">
					<td width="2%" style="padding: 2px; padding-right: 5px; text-align: left;"><@msg code='secritySetting.保护密钥A' default='保护密钥A'/>：</td>
					<td width="5%" style="padding: 2px; padding-right: 5px; text-align: left;">
						<div id="div1"></div>
					</td>
				</tr>
				<tr width="50%">
					<td width="2%" style="padding: 2px; padding-right: 5px; text-align: left;"><@msg code='secritySetting.保护密钥B' default='保护密钥B'/>：</td>
					<td width="5%" style="padding: 2px; padding-right: 5px; text-align: left;">
						<div id="div2"></div>
					</td>
				</tr>
				<tr width="50%">
					<td width="2%" style="padding: 2px; padding-right: 5px; text-align: left;"><@msg code='secritySetting.密钥算法' default='密钥算法'/>：</td>
					<td width="5%" style="padding: 2px; padding-right: 5px; text-align: left;">
						<div id="div3"></div>
					</td>
				</tr>
				<tr width="50%">
					<td width="2%" style="padding: 2px; padding-right: 5px; text-align: left;"><@msg code='secritySetting.安全密钥' default='安全密钥'/>：</td>
					<td width="5%" style="padding: 2px; padding-right: 5px; text-align: left;">
						<div id="td1"></div>
					</td>
				</tr>
			</table>
			</br>
			<div class="col-sm-2">
				<a id="resetBtn" onclick="resetMacKey();" class="btn btn-primary btn-sm" href="#" role="button"><@msg code='secritySetting.重置' default='重置'/></a>
				<a id="qryBtn" class="btn btn-primary btn-sm" href="#" role="button"><@msg code='secritySetting.显示全密钥' default='显示全密钥'/></a>
			</div>	
			</div>
		</div>
	</#if>

</#assign>
<#include "/common/layout.ftl" />