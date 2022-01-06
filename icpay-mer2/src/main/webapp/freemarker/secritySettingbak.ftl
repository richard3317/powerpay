<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<#include "/common/macro.ftl">

<#assign htmlHead>
	<script src="${ctx}/resources/js/jquery.dataTables.min.js"></script>
	<script src="${ctx}/resources/bootstrap/js/dataTables.bootstrap.min.js"></script>
</#assign>
<#assign htmlJS>
	function getImg(str){
		if(str =='0'){
			var img = document.getElementById("img1"); 
			img.src = "${ctx}/gauth?uid=" + $("#mchntCd").val() + "&s=" + $("#otpSecretLogin").val();
			img.onerror=null;
		}else{
			var img = document.getElementById("img2"); 
			img.src = "${ctx}/gauth?uid=" + $("#mchntCdAdmin").val() + "&s=" + $("#optSecretAdmin").val();
			img.onerror=null;
		}
	}
	
function hideSecretImg(str){
	if(str =='0'){
		$("#loginImg").hide();
	}else if(str == '1'){
		$("#adminImg").hide();
	}else{
		$("#downImg").hide();
	}
}
function binding(str) {
	var authCode ="";
	var authCode0=$("#authCode").val();
	var authCode1=$("#authCode1").val();
	var secretType="";
	if(str =='0'){
		secretType="1";
		authCode=authCode1;
	}else{
		secretType="0";
		authCode=authCode0;
	}
	var url = "${ctx}/secrity/authCodeCheck?secretType=" +secretType  +"&authCode=" +authCode;                                                                                                                                                                                                                                                                                                                                                                                                                                                           
	$.get(url, function(data) {
		$.processAjaxResp(data, function(r) {
		if(r.respData == "0"){
			 alert("<@msg code='secritySettingbak.绑定失败！' default='绑定失败！'/>");
	    	 $("#authCode").val('');
	    	 $("#authCode1").val('');
		}else if(r.respData == "1"){
			$("#authCode").val('');
			$("#statusId").html('<font color="red" size="3"><strong><@msg code='secritySettingbak.已绑定' default='已绑定'/></strong></font>');
			alert("<@msg code='secritySettingbak.管理员绑定成功，請重新登入！' default='管理员绑定成功，請重新登入！'/>");
			$.jumpTo("${ctx}/logout");
		}else{
			$("#authCode1").val('');
			$("#statusId1").html('<font color="red" size="3"><strong><@msg code='secritySettingbak.已绑定' default='已绑定'/></strong></font>');
			alert("<@msg code='secritySettingbak.一般用户绑定成功！' default='一般用户绑定成功！'/>");
		}
		$("#status").attr("value",r.respData);
		});
	});
		 	
}	
function resetMacKey() {
		var url = "${ctx}/secrity/genMacKey.do";
		$.get(url, function(data) {
			$.processAjaxResp(data, function(r) {
				if (isEmpty(r)) {
					alert("<@msg code='secritySettingbak.系统错误！' default='系统错误！'/>");
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
					alert("<@msg code='secritySettingbak.重置成功' default='重置成功'/>");
				}
				else{
					alert(r.respMsg);
				}
			});
		});
	}
	function showSecretImg(type) {
		var state = document.getElementById("state").value;
		if (type =='0' && state == '0' ) {
			alert("<@msg code='secritySettingbak.请先选择启用Google验证来对登录做二次保护！' default='请先选择启用Google验证来对登录做二次保护！'/>");
			return;
		}
		if(type =='0'){//先判断管理员谷歌验证是否设置
			var status =$("#status").val();
			if(status == ''|| status =='0'){
				alert("<@msg code='secritySettingbak.请先绑定管理员的身份验证' default='请先绑定管理员的身份验证'/>");
				return false;
			}
		}
		if(type =='2'){
			$("#downImg").show();
		}
		var url = "${ctx}/secrity/getSecret.do?type=" + type;
		$.get(url, function(data) {
			$.processAjaxResp(data, function(r) {
				var lst = r.secretList
				if(!isEmpty(lst)){
						if(lst[0].showType =='1'){
							$("#loginImg").show();
						}
						if(lst[0].showType == '2'){
							$("#adminImg").show();
						}
				}
				else{
					alert('<@msg code='secritySettingbak.错误' default='错误'/>');
				}
			});
		});
	}
<#-- $(function() {
	var loginType = ${loginType!'1'};
	if(loginType == '1'){
		alert("仅管理员可以查看安全设置信息");
		$.jumpTo("${ctx}/index?loginType="+loginType);
	}
}); -->
$(function() {
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
		if(btn.innerHTML =='<@msg code='secritySettingbak.显示全密钥' default='显示全密钥'/>'){
			$("#td1").html(macKey);
			btn.innerHTML ="<@msg code='secritySettingbak.隐藏全密钥' default='隐藏全密钥'/>";
		}else{
			$("#td1").html(macKey.substr(0,6)+"*******"+macKey.substr(-4));
			btn.innerHTML ="<@msg code='secritySettingbak.显示全密钥' default='显示全密钥'/>";
		}
	});
	
});
function showOrShrink(macKey){
		var btn = document.getElementById("qryBtn");
		if(btn.innerHTML =='<@msg code='secritySettingbak.显示全密钥' default='显示全密钥'/>'){
			$("#td1").html(macKey);
			btn.innerHTML ="<@msg code='secritySettingbak.隐藏全密钥' default='隐藏全密钥'/>";
		}else{
			$("#td1").html(macKey.substr(0,6)+"*******"+macKey.substr(-4));
			btn.innerHTML ="<@msg code='secritySettingbak.显示全密钥' default='显示全密钥'/>";
		}
	}

</#assign>
</head>
<#assign htmlContent>
	<ol class="breadcrumb">
		<li><a href="#"><@msg code='secritySettingbak.商户服务' default='商户服务'/></a></li>
		<li class="active"><@msg code='secritySettingbak.安全设置' default='安全设置'/></li>
	</ol>
		<div class="panel panel-default">
			<div class="panel-body">
				<table width="100%">
					<tr>
						<td style="padding: 10px;" width="30%"><@msg code='secritySettingbak.手机绑定谷歌身份器，可以为 登录添加第二层保护；即使有人通过恶意软件窃取了您的登录密码，谷歌验证码也能保护您的账户安全。' default='手机绑定谷歌身份器，可以为 登录添加第二层保护；即使有人通过恶意软件窃取了您的登录密码，谷歌验证码也能保护您的账户安全。'/>
					    	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="showSecretImg('2');"><@msg code='secritySettingbak.扫码安装' default='扫码安装'/></a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="hideSecretImg('2');"><@msg code='secritySettingbak.隐藏条码' default='隐藏条码'/></a>
						</td>
					</tr>
				</table>
				<div id ="downImg" style="display:none;" >
					<table>
					<tr>
						<td>
							<img src="${ctx}/resources/images/ga-2.png" alt="<@msg code='secritySettingbak.IOS版Google身分认证器' default='IOS版Google身分认证器'/>" />
						</br>
							<@msg code='secritySettingbak.IOS版 Google Authenticator' default='IOS版 Google Authenticator'/>
						</td>
						<td>&nbsp;
						</td>
						<td>&nbsp;
						</td>
						<td>&nbsp;
						</td>
						<td>
							<img src="${ctx}/resources/images/ga-1.png" alt="<@msg code='secritySettingbak.Android版Google身分认证器' default='Android版Google身分认证器'/>" />
						</br>
							<@msg code='secritySettingbak.Android版 Google Authenticator' default='Android版 Google Authenticator'/>
						</td>
					</tr>
					</table>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
		<div class="panel-heading">
			<h4 class="panel-title"><font color="red"><@msg code='secritySettingbak.管理员' default='管理员'/></font> <@msg code='secritySettingbak.身份验证设置' default='身份验证设置'/></h4>
			<input type="hidden" id="mchntCd" name="mchntCd" value="${mchntCd}" />
			<input type="hidden" id="mchntCdAdmin" name="mchntCdAdmin" value="${mchntCdAdmin}" />
			<input type="hidden" id="status" name="status" value="${status}" />
			<input type="hidden" id="otpSecretLogin" name="otpSecretLogin" value="${otpSecretLogin}" />
			<input type="hidden" id="optSecretAdmin" name="optSecretAdmin" value="${optSecretAdmin}" />
			<input type="hidden" id="showType" name="showType" value="${showType}" />
			<input type="hidden" id="protectKeyA" name="protectKeyA" value="${protectKeyA}" />
			<input type="hidden" id="protectKeyB" name="protectKeyB" value="${protectKeyB}" />
			<input type="hidden" id="protectAlgorithm" name="protectAlgorithm" value="${protectAlgorithm}" />
			<input type="hidden" id="macKey" name="macKey" value="${macKey}" />
		</div>
		<div class="panel-body">
			<table width="100%">
				<tr>
					<td style="padding: 10px;" width="25%"><@msg code='secritySettingbak.通过Google验证器绑定指定管理者' default='通过Google验证器绑定指定管理者'/></td>
				</tr>
				</table>
				<#--<table>
				<tr width="50%">
					<td width="10%" style="padding: 2px; padding-right: 5px; text-align: left;">启用：</td>
					<td width="15%" style="padding: 2px; padding-right: 5px; text-align: left;">
						<select class="form-control m-b" id = "stateBank" name="stateBank" style="margin-bottom: 0; width: 80px;">>-->
							<#--<option value="0" <#if status == '0' && optSecretAdmin =='' > selected="selected" </#if>>否</option>showType-->
							<#--<option value="1" <#if status == '1' && optSecretAdmin != '' > selected="selected" </#if>>是</option>
						</select>
					</td>-->
				<table width="13%">
				<tr width="10%">
					<td width="1%" style="padding: 12px; padding-right: 5px; text-align: left;"><@msg code='secritySettingbak.状态' default='状态'/>：</td>
					<td width="1%" style="padding: 2px; padding-right: 5px; text-align: left;">
					<div id ="statusId">
					<#if status =='1'||status =='2'>
						<font color="red" size="3"><strong><@msg code='secritySettingbak.已绑定' default='已绑定'/></strong></font>
					<#else>
						<font color="red"  size="3"><strong><@msg code='secritySettingbak.未绑定' default='未绑定'/></strong></font>
					</#if>
					</div>
					</td>
				</tr>
				</table>
				<table>
				<tr>
					<td style="padding: 10px;" width="30%"><@msg code='secritySettingbak.条形码/私钥：用Google身份验证器扫描条码，或者在Google身份验证器输入私钥，绑定您的移动设备' default='条形码/私钥：用Google身份验证器扫描条码，或者在Google身份验证器输入私钥，绑定您的移动设备'/> <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="showSecretImg('1');"><@msg code='secritySettingbak.查看' default='查看'/></a>&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="hideSecretImg('1');"><@msg code='secritySettingbak.隐藏' default='隐藏'/></a>
					</td>
				</tr>
				
			</table>
				<div id ="adminImg" style="display:none;" >
					<label style="padding: 10px;" width="30%"><@msg code='secritySettingbak.绑定账号' default='绑定账号'/>：${mchntCdAdmin}</label>
					</br>
					<label style="padding: 10px;" width="30%"><@msg code='secritySettingbak.私钥' default='私钥'/>： ${optSecretAdmin}</label>
					</br>
					</br>
					<img src="?" id="img2" onerror="getImg(1)" class="img-thumbnail" style="width: 256px; height: 256px;"alt="<@msg code='secritySettingbak.二维码' default='二维码'/>" />
					</br>
					<label style="padding: 10px;" width="30%"><@msg code='secritySettingbak.请输入谷歌验证器上的6位验证码' default='请输入谷歌验证器上的6位验证码'/></label>
					</br>
					<input type="text" placeholder="<@msg code='secritySettingbak.请输入Google验证码' default='请输入Google验证码'/>" name="authCode" maxLength="6" id="authCode" style="width: 210px;" />
					<a class="btn btn-primary btn-sm" href="#" onclick="binding(1);" role="button"><@msg code='secritySettingbak.绑定' default='绑定'/></a>
				</div>
				
		</div>
	</div>
	
		<div class="panel panel-default">
			<div class="panel-heading"><h4 class="panel-title"><@msg code='secritySettingbak.一般用户 身份验证设置' default='一般用户 身份验证设置'/></h4></div>
			
			<div class="panel-body">
				<table width="100%">
				<tr>
					<td style="padding: 10px;" width="27%" ><@msg code='secritySettingbak.通过Google身份验证器为登录添加第二层保护' default='通过Google身份验证器为登录添加第二层保护'/></td>
				</tr>
				</table>
				<table width="50%">
				<tr width="50%">
					<td width="1%" style="padding: 2px; padding-right: 5px; text-align: left;"><@msg code='secritySettingbak.启用' default='启用'/>：</td>
					<td width="5%" style="padding: 2px; padding-right: 5px; text-align: left;">
						<select class="form-control m-b" name="state" id="state" style="margin-bottom: 0; width: 80px;" >
							<option value="0" <#if status == '' ||status == '0' ||status == '1'|| otpSecretLogin == '' > selected="selected" </#if>><@msg code='secritySettingbak.否' default='否'/></option>
							<option value="1" <#if status == '2' && otpSecretLogin !='' > selected="selected" </#if>><@msg code='secritySettingbak.是' default='是'/></option>
						</select>
					</td>
					<td width="1%" style="padding: 12px; padding-right: 5px; text-align: left;"><@msg code='secritySettingbak.状态' default='状态'/>：</td>
					<td width="10%" style="padding: 2px; padding-right: 5px; text-align: left;">
					<div id ="statusId1">
					<#if status =='2' >
						<font color="red" size="3"><strong><@msg code='secritySettingbak.已绑定' default='已绑定'/></strong></font>
					<#else>
						<font color="red" size="3"><strong><@msg code='secritySettingbak.未绑定' default='未绑定'/></strong></font>
					</#if>
					</div>
					</td>
					</td>
				</tr>
				</table>
				<table>
				<tr>
					<td style="padding: 10px;" width="30%"><@msg code='secritySettingbak.条形码/私钥：用Google身份验证器扫描条码，或者在Google身份验证器输入私钥，绑定您的移动设备' default='条形码/私钥：用Google身份验证器扫描条码，或者在Google身份验证器输入私钥，绑定您的移动设备'/> <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="showSecretImg('0');"><@msg code='secritySettingbak.查看' default='查看'/></a>&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="hideSecretImg('0');"><@msg code='secritySettingbak.隐藏' default='隐藏'/></a>
					</td>
				</tr>
				
				<#--<tr>
					<td style="padding: 10px;" >绑定账号：${mchntCd}</td>
				</tr>-->
			</table>
					<div id ="loginImg" style="display:none;"  >
					<label style="padding: 10px;" width="30%"><@msg code='secritySettingbak.绑定账号' default='绑定账号'/>：${mchntCd}</label>
					</br>
					<label style="padding: 10px;" width="30%"><@msg code='secritySettingbak.私钥' default='私钥'/>： ${otpSecretLogin}</label>
					</br>
					</br>
					<img src="?" id="img1" onerror="getImg(0)" class="img-thumbnail" style="width: 256px; height: 256px;"alt="<@msg code='secritySettingbak.二维码' default='二维码'/>" />
					</br>
					<label style="padding: 10px;" width="30%"><@msg code='secritySettingbak.请输入谷歌验证器上的6位验证码' default='请输入谷歌验证器上的6位验证码'/></label>
					</br>
					<input type="text" placeholder="<@msg code='secritySettingbak.请输入Google验证码' default='请输入Google验证码'/>" name="authCode1" maxLength="6" id="authCode1" style="width: 210px;" />
					<a class="btn btn-primary btn-sm" href="#" onclick="binding(0);" role="button"><@msg code='secritySettingbak.绑定' default='绑定'/></a>
					</div>
		</div>
	</div>
	
		<div class="panel panel-default">
			<div class="panel-heading"><h4 class="panel-title"><@msg code='secritySettingbak.安全密钥' default='安全密钥'/></h4></div>
			<div class="panel-body">
				<table width="50%">
				<tr>
					<td style="padding: 10px;" width="27%" ><@msg code='secritySettingbak.安全密钥(MAC_KEY)' default='安全密钥(MAC_KEY)'/>:</td>
				</tr>
				</table>
				<table width="50%">
				<tr width="50%">
					<td width="2%" style="padding: 2px; padding-right: 5px; text-align: left;"><@msg code='secritySettingbak.保护密钥A' default='保护密钥A'/>：</td>
					<td width="5%" style="padding: 2px; padding-right: 5px; text-align: left;">
						<div id="div1"></div>
					</td>
				</tr>
				<tr width="50%">
					<td width="2%" style="padding: 2px; padding-right: 5px; text-align: left;"><@msg code='secritySettingbak.保护密钥B' default='保护密钥B'/>：</td>
					<td width="5%" style="padding: 2px; padding-right: 5px; text-align: left;">
						<div id="div2"></div>
					</td>
				</tr>
				<tr width="50%">
					<td width="2%" style="padding: 2px; padding-right: 5px; text-align: left;"><@msg code='secritySettingbak.密钥算法' default='密钥算法'/>：</td>
					<td width="5%" style="padding: 2px; padding-right: 5px; text-align: left;">
						<div id="div3"></div>
					</td>
				</tr>
				<tr width="50%">
					<td width="2%" style="padding: 2px; padding-right: 5px; text-align: left;"><@msg code='secritySettingbak.安全密钥' default='安全密钥'/>：</td>
					<td width="5%" style="padding: 2px; padding-right: 5px; text-align: left;">
						<div id="td1"></div>
					</td>
				</tr>
			</table>
			</br>
			<div class="col-sm-2">
				<a id="resetBtn" onclick="resetMacKey();" class="btn btn-primary btn-sm" href="#" role="button"><@msg code='secritySettingbak.重置' default='重置'/></a>
				<a id="qryBtn" class="btn btn-primary btn-sm" href="#" role="button"><@msg code='secritySettingbak.显示全密钥' default='显示全密钥'/></a>
			</div>	
			</div>
	</div>

</#assign>
<#include "/common/layout.ftl" />