<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="绑定谷歌验证码" base="gaCodeBind">
			$(function() {
				showSecretImg();
				
				$("#saveBtn").click(function() {
					var userId = $("#userId").val();
					var authCode = $("#authCode_" + userId).val();
					var otpSecret = $("#otpSecret_" + userId).html();
					var url = _ctx + "/gaCodeBind/authCodeCheck.do";
					$.ajax({
						url: url,
						async: false,
						type : "POST",
						data: {
							userId : userId,
							authCode : authCode,
							otpSecret : otpSecret,
						},
						success: function(data) {
							var dataObj = JSON.parse(data);
							console.log(dataObj);
							if (dataObj.respCode == "00") {
								alert("绑定成功，請重新登入！");
							 	$.jumpTo("${ctx}/logout.do");
							}
							if (dataObj.respCode == "ZZ") {
								alert("绑定失败！");
								$("#authCode_" + userId).val('');
							}
						},
					    error:function(){
					    	errorstr = "连线异常，请联系管理员！";
					    	alert(errorstr);
					    }
					});
				});
			}); 
			
			function showSecretImg() {
				var url = _ctx + "/gaCodeBind/getSecret.do";
				var userId = $("#userId").val();
				$.ajax({
					url: url,
					async: false,
					type : "POST",
					data: {
						userId : userId,
					},
					success: function(data) {
						var dataObj = JSON.parse(data);
						var otpSecret = dataObj.respData.otpSecret;
						if(!isEmpty(otpSecret)){
							$("#otpSecret_"+ userId).html(otpSecret);
							getImg(userId);
						}else{
							alert('错误');
						}
					},
				    error:function(){
				    	errorstr = "连线异常，请联系管理员！";
				    	showError(errorstr);
				    }
				});
			}
			
			function getImg(userId){
				var otpSecret = $("#otpSecret_" + userId).text();
				var img = document.getElementById("img"+"_" + userId);
				img.src = _ctx + "/gauth?uid=" + userId + "&s=" + otpSecret;
				img.onerror=null;
			}
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<div id="mngDiv" class="easyui-panel" title="绑定谷歌验证码" style="padding: 10px;">
			<form action="" id ="form1">
				<input type="hidden" name="userId" id="userId" value="${userId!''}" />
				
				<div>
					<label style="padding: 10px;" width="30%">绑定账号：${userId!''}</label>
					</br>
					<label style="padding: 10px;" width="30%">私钥：  <span id ="otpSecret_${userId!''}"></span></label>
					</br>
					</br>
					<img src="" id="img_${userId!''}" onerror="getImg('${userId!''}')" class="img-thumbnail" style="width: 256px; height: 256px;"alt="二维码" />
					</br>
					<label style="padding: 10px;" width="30%">请输入谷歌验证器上的6位验证码</label>
					</br>
					<input type="text" placeholder="请输入Google验证码" maxLength="6" id="authCode_${userId!''}" style="width: 210px;" />
					<a id="saveBtn" href="javascript:void(0)" class="easyui-linkbutton">绑定</a>
				</div>
			</form>
			
		</div>	
	</body>
<html>