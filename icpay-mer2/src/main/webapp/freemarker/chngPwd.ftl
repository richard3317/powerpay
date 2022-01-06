<#include "/common/macro.ftl">
<#assign htmlTitle="修改密码" />

<#assign htmlContent>
	<ol class="breadcrumb">
		<li><a href="${ctx}/"><@msg code='chngPwd.商户服务' default='商户服务'/></a></li>
		<li class="active"><@msg code='chngPwd.密码管理' default='密码管理'/></li>
	</ol>
	
	<div class="panel panel-default">
		<div class="panel-heading">
			<h4 class="panel-title"><@msg code='chngPwd.修改登录密码' default='修改登录密码'/></h4>
		</div>
		<div class="panel-body">
			<form id="chngPwdForm" method="post" action="${ctx}/chngPwdSubmit" class="form-horizontal">
				<input type="hidden" id="userId" name="userId" value="${userId}">
				<div class="form-group">
					<label class="col-sm-4 control-label"><@msg code='chngPwd.原密码' default='原密码'/></label>
					<div class="col-sm-6">
						<input type="password" name="oldPwd" class="form-control m-b">
						<div class="input_error">&nbsp;</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label"><@msg code='chngPwd.新密码' default='新密码'/></label>
					<div class="col-sm-6">
						<input type="password" id="newPwd1Inpt" name="newPwd1" class="form-control m-b">
						<div class="input_error">&nbsp;</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label"><@msg code='chngPwd.再次输入新密码' default='再次输入新密码'/></label>
					<div class="col-sm-6">
						<input type="password" id="newPwd2Inpt" name="newPwd2" class="form-control m-b">
						<div class="input_error">&nbsp;</div>
					</div>
				</div>
				<div>
					<b id="reLoginTip" style="position:absolute;top:65%;right:20%;font-size:20px;font-family:'Microsoft JhengHei';color:orange">
       				<@msg code='chngPwd.温馨提示：您的密码为初始/重置后的密码，为了您的账户安全，请修改密码后再登录' default='温馨提示：您的密码为初始/重置后的密码，为了您的账户安全，请修改密码后再登录'/>		
        			</b>
				</div>
				<div class="hr-line-dashed"></div>
				<div class="form-group">
                    <div class="col-sm-10 col-sm-offset-4">
                    	<button id="submitBtn" class="btn btn-primary" type="button"><@msg code='chngPwd.保存' default='保存'/></button>
                        <button id="cancleBtn" class="btn btn-default" type="button"><@msg code='chngPwd.取消' default='取消'/></button>
                    </div>
                </div>
			</form>
		</div>
	</div>
</#assign>

<#assign htmlJS>
	$(function() {	
		var dealing = false;
        $("#chngPwdForm").validate({
        	rules: {
        		newPwd1: {
        			required: true,
        			passwd: true
        		},
        		newPwd2: {
        			required: true,
        			passwd: true,
        			equalTo: newPwd1Inpt
        		}
        	},
			submitHandler : function(form) {
				if (dealing) {
					return;
				}
				$(form).ajaxSubmit(function(resp) {
					$.processAjaxResp(resp,
						function(respData) {
							var oriState = respData.oriState;
							if(oriState == '1'){
								alert("<@msg code='chngPwd.密码修改成功！' default='密码修改成功！'/>");
							  	$.jumpTo("${ctx}/");
							}
							else{
								alert("<@msg code='chngPwd.密码修改成功，请重新登录' default='密码修改成功，请重新登录'/>");
							  	$.jumpTo("${ctx}/logout");
							}
						},
						function(r) {
							$.showMsg(r.respMsg, "error");
							dealing = false;
						},
						function(r) {
							$.showMsg(r.respMsg, "error");
							dealing = false;
						}
					);
				});
				return false;
			}
		});
		$("#submitBtn").click(function() {
			if (dealing) {
				return;
			}
			$("#chngPwdForm").submit();
		});
		$("#cancleBtn").click(function() {
			$.jumpTo("${ctx}/");
		});
	});
	
	var divTip = document.getElementById("reLoginTip");
	var cbtn = document.getElementById("cancleBtn");
	if("${userState}" == "1"){
		divTip.style.display = 'none';
		cbtn.style.visibility = 'visible';
	}
	else{
		divTip.style.display = 'block';
		cbtn.style.visibility = 'hidden';
	}
</#assign>

<#include "/common/layout.ftl" />