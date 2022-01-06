<#assign ctx = request.contextPath>
<#assign timeNow = .now>
<#assign strNow = timeNow?string["yyyyMMddHHmmss"]>
<#assign strNowMm = timeNow?string["yyyyMMddHHmm"]>
<#assign lan = Session["lan"]!"">
<!DOCTYPE html>

<html>
	<head>
		<title>${loginTitle}><@msg code='login.商户服务网站' default='商户服务网站'/></title>
		<link rel="shortcut icon" href="${ctx}/resources/images/favicon.ico" type="image/x-icon" />
		<link rel="icon" href="${ctx}/resources/images/favicon.ico" type="image/x-icon" />

		<link rel="stylesheet" href="${ctx}/resources/bootstrap/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${ctx}/resources/css/sweet-alert.css">
		<link rel="stylesheet" href="${ctx}/resources/css/toastr.min.css">
		<link rel="stylesheet" href="${ctx}/resources/css/style.css?v=${strNowMm!''}">

		<script src="${ctx}/resources/js/jquery.min.js"></script>
		<script src="${ctx}/resources/bootstrap/js/bootstrap.min.js"></script>
		<script src="${ctx}/resources/js/jquery.form.js"></script>
		<script src="${ctx}/resources/js/jquery.validate.min.js"></script>
		<script src="${ctx}/resources/js/toastr.min.js"></script>
		<script src="${ctx}/resources/js/crypto-js/md5.js"></script>
		<script src="${ctx}/resources/js/moment-with-locales.js"></script>
		<script src="${ctx}/resources/js/mer.js?v=${strNowMm!''}"></script>
		<script src="${ctx}/resources/js/common.js?v=${strNowMm!''}""></script>

		<script type="text/javascript">
		 var i18nMsg = {
		  <!-- 操作 -->
		 	"operation": "<@msg code='layout.操作' default='操作'/>",
		 <!--暂无记录 -->
		 	"noData":   "<@msg code='layout.暂无记录' default='暂无记录'/>",
		 <!--上一页 -->
		 	"prePage":  "<@msg code='layout.上一页' default='上一页'/>",
		 <!--下一页 -->
		 	"nextPage":  "<@msg code='layout.下一页' default='下一页'/>",
		 <!--使用中的语系 -->
		 	"usedLan":   "${lan ! defaultLan}",
		 	"navigatorLan":  (navigator.language || navigator.browserLanguage).toLowerCase(),
		 <!--ctx -->
		 	"ctx":  "${ctx}",
		 }
		 </script>
		 <script type="text/javascript">
		 function initLan(){
		 	<!--首页初始化语系-->
			var cookieLan = "";
			var cookie =document.cookie;
			<!--抓COOKIE中的语系，参数lan-->
		 	if(cookie.indexOf('lan')!= -1){
				var cookieLanStr = cookie.substring(cookie.indexOf('lan')+4,cookie.indexOf('lan')+9);
				if(cookieLanStr=='zh_TW'||cookieLanStr=='en_US'||cookieLanStr=='th_TH'||cookieLanStr=='vi_VN'||cookieLanStr=='zh_CN'){
					cookieLan = cookieLanStr;
				}
			}
			<#-- 测试看
			console.log("cookieLan内容："+cookieLan);
			console.log("lan的内容："+ "${lan}");
			console.log("defaultLan的内容："+ "${defaultLan}");
			console.log("navigatorLan的内容："+ i18nMsg.navigatorLan);
			-->
			 
			if(i18nMsg.usedLan.length ==0){
				<!--console.log("1");-->
				<!--如果COOKIE中有语系，先捞COOKIE的，避免登出后，又重新从浏览器捞语系-->
			 	if(cookieLan){
			 		<!--console.log("2");-->
			 		ajaxi18n(i18nMsg.ctx +"/updateI18n" , {lan: cookieLan} , function(){
				    	console.log("从COOKIES捞的语系= " + cookieLan); 
				    	window.location.reload(); 
			  	 	});
			 	}else{
			 		<!--console.log("3");-->
			 		
					ajaxi18n(i18nMsg.ctx +"/updateI18n" , {lan: i18nMsg.navigatorLan} , function(){
				    	console.log("从浏览器捞的语系= " + i18nMsg.navigatorLan); 
				    	window.location.reload(); 
			  	 	});
			 	}
		  	 }			 
	  	 }
	  	 initLan();
		</script>
	</head>
	
	<body>
		<div id="${headerId}">
			<#--  
			<div class="center_wrapper">
				<div id="logo">
					<img src="${ctx}/resources/images/${login_flt_logo_name}" />
					</div>
				</div>
	    
    			<div id="navbar-right" class="navbar-right">
    				<a href="${ctx}/">首页</a>
    				<a href="${logo_flag}">官网</a>
		            <a href="#">个人服务</a>
        		</div>
			</div>
			-->
			
			<div class="center_wrapper">
			    <#--  
				<div id="navbar-left" class="navbar-left">
					<h1><a href="${ctx}/index">商户平台</a></h1>
				</div>
				-->
				<div id="navbar-left" class="navbar-left">
					<div id="logo">
						<img src="${ctx}/resources/images/${login_flt_logo_name}" />
					</div>
				</div>
				
    			<div id="navbar-right" class="navbar-right" >
    				<a href="${ctx}/" style="${navBar_style}"><@msg code='login.首页' default='首页'/></a>
    				<a href="${logo_flag}" style="${navBar_style}"><@msg code='login.官网' default='官网'/></a>
		            <a href="#" style="${navBar_style}"><@msg code='login.个人服务' default='个人服务'/></a>
        		</div>
        						
			</div>		
			
		</div>
		
<div id="content" >
		
		<div class="login_center">
			
			<div id="banner_carousel" class="carousel slide" data-ride="carousel" data-interval="5000">
				<ol class="carousel-indicators">
					<li data-target="#banner_carousel" data-slide-to="0" class="active"></li>
				</ol>
				<div class="carousel-inner">
					<div class="item active">
						<div><img src="${ctx}/resources/images/${login_flt_banner01_name}" /></div>
					</div>
				</div>
			</div>
			
			<div class="login-container">
				<div class="row">
					<div class="col-md-12">
			            <div class="${hpaneltype}">
			                <div class="panel-body">
			                        <form action="${ctx}/login" id="loginFrm" name="loginFrm">
			                            <input type="hidden" id="secretHash" name="secretHash" value="">
			                            <div class="form-group" style="text-align: left;">
			                            	<label style="color:#FFFFFF;font-size:16px;"><@msg code='login.语系' default='语系'/></label>
							            	<select id="lang-selector" style="width:70%;"></select> 
							            </div>
							            </br>
			                            <div class="form-group">
			                            	<input type="text" placeholder="<@msg code='login.请输入商户号' default='请输入商户号'/>" class="form-control" 
			                            		name="mchntCd" id="mchntCd" maxLength="15" onkeyup="this.value=this.value.replace(/\s+/g,'')"/>
			                                <div class="input_error">&nbsp;</div>
			                            </div>
			                            <!-- 加入用户ID -->
			                            <div class="form-group">
			                            	<input type="text" placeholder="<@msg code='login.请输入用户名' default='请输入用户名'/>" class="form-control" 
			                            		name="userId" id="userId" maxLength="40" onkeyup="this.value=this.value.replace(/\s+/g,'')"/>
			                                <div class="input_error">&nbsp;</div>
			                            </div>
			                            <div class="form-group">
			                                <input type="password" placeholder="<@msg code='login.请输入密码' default='请输入密码'/>" maxLength="32" requiredInput="<@msg code='login.登录密码' default='登录密码'/>"
			                                	name="loginPwd" id="loginPwd" class="form-control">
			                                <div class="input_error">&nbsp;</div>
			                            </div>
			                            <div class="form-group" style="text-align: left;">
			                            	<img id="validCodeImg" title="<@msg code='login.点击刷新验证码' default='点击刷新验证码'/>"
												src="${ctx}/validateCode?r=${rondom}" 
												width="105" height="33" 
												style="cursor: pointer; margin-right: 5px;display: inline-block;" />
			                                <input type="text" placeholder="<@msg code='login.请输入图形验证码' default='请输入图形验证码'/>" maxLength="4"
			                                	style="width: 150px; display: inline-block;" requiredInput="<@msg code='login.图形验证码' default='图形验证码'/>"
			                                	name="validateCode" id="validateCode" class="form-control">
			                            </div>
			                            <#-- <div class="form-group" style="text-align: left;">
			                                <input name="loginType"  type="checkbox" style="vertical-align:middle; width: 50px; height:20px; margin-top:0;" value="0" ${adminCheckBox!''} /><font color="red">以管理者身份登录</font>
			                            </div> -->
			                            </br>
			                            <a id="loginBtn" class="${btnClass}" href="#" style="font-size: 16px; font-weight: 800;"  data-toggle="modal" data-target="#operation480" ><@msg code='login.登录' default='登录'/></a>
			                       </form>
			                </div>
			            </div>
			        </div>
			    </div>
			</div>
		</div>

		<div id="business" class="center_wrapper" style="${footPic_style}">
			<div class="col-sm-4 col-md-3">
				<div class="thumbnail">
					<img src="${ctx}/resources/images/${footPic1}" width="169" />
				</div>
				<div class="caption" style="text-align: center;font-weight: bold;font-size: 20px;">${footTotal1}</div>
			</div>
			<div class="col-sm-4 col-md-3">
				<div class="thumbnail">
					<img src="${ctx}/resources/images/${footPic2}"  width="169" />
				</div>
				<div class="caption" style="text-align: center;font-weight: bold;font-size: 20px;">${footTotal2}</div>
			</div>
			<div class="col-sm-4 col-md-3">
				<div class="thumbnail">
					<img src="${ctx}/resources/images/${footPic3}" width="169" />
				</div>
				<div class="caption" style="text-align: center;font-weight: bold;font-size: 20px;">${footTotal3}</div>
			</div>
			<div class="col-sm-4 col-md-3">
				<div class="thumbnail">
					<img src="${ctx}/resources/images/${footPic4}"  width="169" />
				</div>
				<div class="caption" style="text-align: center;font-weight: bold;font-size: 20px;">${footTotal4}</div>
			</div>
	    </div>
	     
		<div id="${footId}">
		<div class="div1"><a class="pic" target="_blank" href="${footcertsite}" ><img src="https://static.anquan.org/static/outer/image/aqkx_124x47.png"></img></a><p class="con">${footer}</p></div>
	    </div>
		
		<div class="container body"> 
		<div class="main_container">
			<div class="modal fade" id="alertDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="z-index:999999 !important;">
				<div class="modal-dialog" style="width:320px;">
					<div class="modal-content" style="width:320px;">
						<div class="modal-header">
				            <button type="button" class="close" 
				               data-dismiss="modal" aria-hidden="true">
				                  &times;
				            </button>
				            <h4 class="modal-title" id="alertTitle">
				              	 <@msg code='login.提示' default='提示'/>
				            </h4>
				         </div>
				         <div class="modal-body" id="alertContent">
				           	 <@msg code='login.在这里添加一些文本' default='在这里添加一些文本'/>
				         </div>
				         <div class="modal-footer">
				            <button type="button" class="btn btn-primary" 
				               data-dismiss="modal">
				               <@msg code='login.确定' default='确定'/>
				            </button>
				         </div>
					</div>
				</div>
			</div>
 			<#-- 谷歌驗證碼 ${ctx}/loginCheck-->
			<form action="" id="loginFrm1" name="loginFrm1">
			<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false" >
				    <div class="modal-dialog">
				        <div class="modal-content" style="width: 400px;margin-left: 160px;margin-top: 118px;">
				            <div class="modal-header">
				                <#--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>-->
				                <h4 class="modal-title" id="myModalLabel"><@msg code='login.谷歌身份验证器' default='谷歌身份验证器'/></h4>
				            </div>
				            <div class="modal-body">
				            <table>
				            <tr>
				            <td  align="left" bgcolor="#DFE0E4" class="general-td">
								<@msg code='login.1.在您的移动设备上运行Google身份验证器' default='1.在您的移动设备上运行Google身份验证器'/>
							</td>
							</tr>
							<tr>
							<td align="left" bgcolor="#DFE0E4" class="general-td">
								<@msg code='login.2.在下面框中输入，账号：' default='2.在下面框中输入，账号：'/><div id="td1" ></div> <@msg code='login.2.的当前验证码' default='的当前验证码'/>
							</td></tr>
							<tr>	
							<td align="left" bgcolor="#DFE0E4" class="general-td">
								<@msg code='login.3.点击验证' default='3.点击验证'/>
							</td></tr>
							</table>
				            </div>
				            <div class="modal-body">
				            <input id="authCode" class="general-td" type="text" autofocus="true" placeholder="<@msg code='login.请输入Google验证码' default='请输入Google验证码'/>" class="general-td" name="authCode" maxLength="6" >
				            <div class="modal-body"></div>
				            <div class="modal-footer">
				                <button type="button" id="comfirmOK" class="btn btn-primary"><@msg code='login.确认' default='确认'/></button>
				                <button type="button" class="btn btn-default" id="close"><@msg code='login.关闭' default='关闭'/>  </button>
				            </div>
				        </div>
				    </div>
			</div>
			</div>
			</form>
			
			<div class="modal fade" id="operation480" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog" style="width:640px;"><div class="modal-content" style="width:640px;"></div></div>
			</div>
			
</dev> <#-- end of content -->
		
<script type="text/javascript">
	var _ctx = "${ctx}";
	var loginFlg = false;
			
    function getTimestamp(){
        var now = new Date();
        return moment(now).format('YYYYMMDDHHmmss');
    }

    function secretHash(passwd, validCode){
        var pwdmd5 =  CryptoJS.MD5((passwd+"PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP").substring(0, 50));
        return CryptoJS.MD5(validCode+"+PPAY_2018_MER+"+pwdmd5);
    }
    
    function toggleGAuth(visible){
    	if (visible){
			$('#myModal1').modal('show');
			<#-- disableGaCheck(); -->
		}
		else{
			$('#myModal1').modal('hide');
			<#-- enableGaCheck(); -->
		$('#operation480').modal('toggle');}
    }
    
	$(function() {
	
		$('#myModal1').on('shown.bs.modal', function () {
		  	$('#authCode').trigger('focus');
		});
	
		$("#validCodeImg").click(function() {
			var r = Math.floor(Math.random() * ( 100000000 + 1));
			$(this).attr("src", "${ctx}/validateCode?r=" + r);
		});
        $("#loginFrm").validate({
        	rules: {
        		mchntCd: {
        			required: true,
        			numberFixLen: 15,
        			requiredInput: "<@msg code='login.商户号' default='商户号'/>",
        			remote: {
        				url: "${ctx}/login/checkMchntCd",
        				type: "post",
        				data: {
        					mchntCd: function() {
        						return $("#mchntCd").val();
        					}
        				}
        			}
        		},
        		userId: {
        			required: true,
        			requiredInput: "<@msg code='login.用戶代码' default='用戶代码'/>",
        		}
        	},
			submitHandler : function(form) {
				if (loginFlg) {
					return;
				}
				/* var loginType = $("input[name='loginType']:checked").val();
				if(loginType ==null || loginType ==''){
			    	loginType =1; <#-- 一般用户 -->
			    } */
				$("#loginBtn").html("<@msg code='login.登录中' default='登录中'/>...");
				$(form).ajaxSubmit(function(resp) {
					$.processAjaxResp(resp,
						function(respData) {
						     // 
							 var lst = respData.list; <#-- 0 mchntCd ,1 secretState , 2 userId , 3 role 4 userState:0/1-->
							 if(lst[4] == '0'){
								 $.jumpTo("${ctx}/chngPwd");
							 }else{
								 if(lst[3] == 'py' && (lst[1] == '' || lst[1] == null || lst[1] == '0' || lst[1] =='3')){
								    	$.jumpTo("${ctx}/index");
								 }else if((lst[1] ==null || lst[1] =='' || lst[1] =='0' || lst[1] =='3' || lst[1] =='4')  && lst[3] != 'py'){
								 		$.jumpTo("${ctx}/secrity/secritySetting");
								 }else{
							    		$("#td1").html(lst[0]+"-" +lst[2]);
							    		$("#td1").addClass('red');
								    	$('#myModal1').modal('show');
								    	<#-- disableGaCheck(); -->
									    $('#operation480').modal('toggle');
								}
							 }
						},
						function(r) {
							$.showMsg(r.respMsg, "error");
							$("#validCodeImg").trigger("click");
							$("#loginBtn").html("<@msg code='login.登录' default='登录'/>");
							loginFlg = false;
							$('#myModal1').modal('hide');
							<#-- enableGaCheck(); -->
							$('#operation480').modal('toggle');
						},
						function(r) {
							$.showMsg(r.respMsg, "error");
							$("#validCodeImg").trigger("click");
							$("#loginBtn").html("<@msg code='login.登录' default='登录'/>");
							loginFlg = false;
							$('#myModal1').modal('hide');
							<#-- enableGaCheck(); -->
							$('#operation480').modal('toggle');
						}
					);
				});
				return false;
			}
		});
		$("#loginBtn").click(function() {
			if (loginFlg) {
				return;
			}
			$("#secretHash").val(secretHash($("#loginPwd").val(),$("#validateCode").val()));
			$("#loginPwd").val('${rondom}');
			$("#loginFrm").submit();
		});
	});

	$("#close").click(function(){
		$('#myModal1').modal('hide');
		$.jumpTo("${ctx}/logout");
	});


	$("#comfirmOK").click(function(){
		var authCode = $("#authCode").val();
		if($('#authCode').val()==''){
		         alert("<@msg code='login.谷歌验证码不能为空' default='谷歌验证码不能为空'/>");
			     return;
	    }
	    $('#myModal1').modal('hide');
	    /* var loginType = $("input[name='loginType']:checked").val();
	    if(loginType ==null || loginType ==''){
	    	loginType =1;
	    } */
		<#--var url = "${ctx}/loginCheck?authCode=" +authCode +"&mchntCd=" +$("#mchntCd").val()+"&userId=" +$("#userId").val();                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		 $.get(url, function(data) {
			$.processAjaxResp(data, function(r) {
			if(r.respData == "10"){
				 alert("谷歌验证码错误");
		    	 $("#authCode").val('');
		    	 $.jumpTo("${ctx}/logout");
			}else{
				$("#authCode").val('');
				$.jumpTo("${ctx}/index");
			}
			});
		});-->

		var url = "${ctx}/loginCheck";
		$.ajax({
				url: url,
				async: false,
				type : "POST",
				data: {
					authCode : authCode,
					mchntCd : $("#mchntCd").val(),
					userId : $("#userId").val()
				},
				success: function(data) {
				 	var dataObj = JSON.parse(data);
				 	var recode = dataObj.respCode;
					if(recode=='00'){
			 			$("#authCode").val('');
						$.jumpTo("${ctx}/index");
			 		}else{
			 			alert(dataObj.respMsg);
			 			$("#authCode").val('');
		    	 		$.jumpTo("${ctx}/logout");
			 		}
			 	},
			    error:function(){
			    	 alert('<@msg code='login.系统异常，请联系管理员！' default='系统异常，请联系管理员！'/>');
			    	 $("#authCode").val('');
			    	 $.jumpTo("${ctx}/logout");
			    }
		});
			 	
	  });
  
		</script>
	</body>
</html>