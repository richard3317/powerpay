<#include "/common/macro.ftl">
<#assign htmlHead>
	<script src="${ctx}/resources/js/jquery.dataTables.min.js"></script>
	<script src="${ctx}/resources/bootstrap/js/dataTables.bootstrap.min.js"></script>
	<script src="${ctx}/resources/js/mer.js?v=${strNowMm!''}"></script>
	<script src="${ctx}/resources/js/crypto-js/md5.js"></script>
</#assign>
<#assign htmlContent>
<ol class="breadcrumb">
	<li><a href="${ctx}/index"><@msg code='addUserMng.商户服务' default='商户服务'/></a></li>
	<li class="active"><@msg code='addUserMng.人员添加' default='人员添加'/>&nbsp;|&nbsp;<@msg code='addUserMng.人员修改' default='人员修改'/></li>
</ol>
<div style="text-align: center;">
	<form action="${ctx}/mchntUser/verifyUserId" onsubmit="return reg()" method="post" id="mchntUserForm">
		<input type="hidden" id="hSecretHash" name="hSecretHash" value="">
		<input type="hidden" id="hGooglePwd" name="hGooglePwd" value="">
		<table>
			<#if mchntUserInfo??>
			<input type="hidden" id="type" value="1" name="type" />
			<tr>
				<td width="10%" style="padding: 5px; padding-right: 10px; text-align: right;">
					<div>
						<font size="2">
							<@msg code='addUserMng.登录账号' default='登录账号'/>
						</font>
					</div>
				</td>
				<td width="23%">
					<input type="text" name="userId" onfocus="focusa()" onblur="blura()" id="rege" class="form-control" style="width: 200px;" value="${mchntUserInfo.userId }" readonly="readonly"/><br/>
				</td>
			</tr>
			<tr>
				<td width="10%" style="padding: 5px; padding-right: 10px; text-align: right;">
					<div>
						<font size="2">
							<@msg code='addUserMng.账户类型' default='账户类型'/>
						</font>
					</div>
				</td>
				<td width="23%">
					<select class="form-control m-b" name="userRole" id="userRole"  style="margin-bottom: 0; width: 200px;">
						<#if "py"==mchntUserInfo.userRole>
					<option value="py" selected="selected">
					<div>
					<font size="2">
					<@msg code='addUserMng.查询专员' default='查询专员'/>
					</font>
					</div>
					</option>
							<option value="wd">
							<div>
					<font size="2">
							<@msg code='addUserMng.清算出款专员' default='清算出款专员'/>
							</font>
							</div>
							</option></#if>
						
						<#if "wd"==mchntUserInfo.userRole>
						<option value="py">
						<div>
					<font size="2">
						<@msg code='addUserMng.业务查询专员' default='业务查询专员'/>
						</font>
						</div>
						
						</option>
						<option value="wd" selected="selected">
						<div>
					<font size="2"><@msg code='addUserMng.清算出款专员' default='清算出款专员'/>
					</font></div></option> 
						</#if>
							</select>
						</td>
			</tr>
			<#else>
			<input type="hidden" name="type" id="type" value="0" />
			<tr>
				<td width="10%" style="padding: 5px; padding-right: 10px; text-align: right;">
					<div>
						<font size="2">
						<@msg code='addUserMng.登录账号' default='登录账号'/>
						</font>
					</div>
				</td>
				<td width="23%">
					<input type="text" name="userId" onfocus="focusa()" onblur="blura()" id="rege" value="" class="form-control" style="width: 200px;"  />
				</td>
			</tr>
			<tr>
				<span align="left" id="spanId" style="display:inline-block;width:30%;color:#c4cbc7;font-size: x-small"><@msg code='addUserMng.只能输入数字英文以及特殊符号_-' default='只能输入数字英文以及特殊符号_-'/></span>
			</tr>
			<tr>
				<td colspan="2"><br></td>
			</tr>
			<tr>
							<td width="10%" style="padding: 5px; padding-right: 10px; text-align: right;">
								<div>
					<font size="2">
							<@msg code='addUserMng.账户类型' default='账户类型'/>
							</font>
							</div></td>
						<td width="23%">
							<select class="form-control m-b" name="userRole" id="userRole"  style="margin-bottom: 0; width: 200px;">
							<option value='1' selected="selected">&nbsp;&nbsp;--<@msg code='addUserMng.请选择' default='请选择'/>--</option>
							<option value="py"><div>
					<font size="2"><@msg code='addUserMng.业务查询专员' default='业务查询专员'/></font></div></option>
							<option value="wd"><div>
					<font size="2"><@msg code='addUserMng.清算出款专员' default='清算出款专员'/></font></div></option>
							</select>
						</td>
			</tr>
			</#if>

			<tr>
				<td colspan="2"><br></td>

			</tr>


			<tr>
				<td></td>
				<td>
					<span>	
						<div align="left">
							<font size="2"><@msg code='addUserMng.查询专员菜单权限：充值查询、取现查询、账户明细、充值。' default='查询专员菜单权限：充值查询、取现查询、账户明细、充值。'/></font>
						</div>
					</span><br />
					<span>
						<div align="left">
							<font size="2"><@msg code='addUserMng.清算出款专员菜单权限：充值查询、取现查询、账户明细、充值、取现、转账。' default='清算出款专员菜单权限：充值查询、取现查询、账户明细、充值、取现、转账。'/></font>
						</div>
					</span>
				</td>
			</tr>
			<tr>
				<td colspan="2"><br></td>
			</tr>
			<tr>
				<td></td>
				<td>
				</td>
			</tr>

			<tr>
				<td colspan="2"><br></td>
			</tr>
			<tr>
				<td colspan="2"><br></td>
			</tr>

			<tr>
				<td width="10%" style="padding: 5px; padding-right: 15px; text-align: right;">
				<input class="btn btn-primary btn-sm"
					style="margin-left: 200px;" role="button" type="button" value="<@msg code='addUserMng.确定' default='确定'/>" onclick="verifyUserId()"></td>
				<td  width="10%" style="padding: 5px; padding-left: 100px; text-align: left;">
				<a class="btn btn-primary btn-sm"
					style="margin-right: 10px;" role="button" onclick="backHome()"><@msg code='addUserMng返回上一页.' default='返回上一页'/></a>

				</td>
			</tr>
			</tr>
			<tr>
				<td colspan="2"><br></td>
			</tr>
			</tr>
			<tr>
				<td colspan="2"><br></td>
			</tr>
			<tr>
				<td colspan="2"><br></td>

			</tr>
			<tr>
				<td colspan="2"><br></td>

			</tr>
			<tr>
				<td colspan="2"><br></td>

			</tr>


		</table>
	</form>
	<#if result??> <input type="hidden" id="result" value="${result}">
	<#else> <input type="hidden" id="result" value="0"> </#if>
	
	<#if newPwd??> <input type="hidden" id="newPwd" value="${newPwd}">
	<#else> <input type="hidden" id="newPwd" value=""> </#if>
</div>
<#-- 密码及谷歌验证输入框 -->
<div class="main_container">
    <#-- 模态框（Modal） -->
	<div class="modal fade" id="alertDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="z-index:999999 !important;">
		<div class="modal-dialog" style="width:320px;">
			<div class="modal-content" style="width:320px;">
				<div class="modal-header">
		            <button type="button" class="close" 
		               data-dismiss="modal" aria-hidden="true">
		                  &times;
		            </button>
		            <h4 class="modal-title" id="alertTitle">
		              	 <@msg code='addUserMng.提示' default='提示'/>
		            </h4>
		         </div>
		         <div class="modal-body" id="alertContent">
		           	 <@msg code='addUserMng.在这里添加一些文本' default='在这里添加一些文本'/>
		         </div>
		         <div class="modal-footer">
		            <button type="button" class="btn btn-primary" 
		               data-dismiss="modal"><@msg code='addUserMng.确定' default='确定'/>
		            </button>
		         </div>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:400px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel"><@msg code='addUserMng.确认' default='确认'/></h4>
            </div>
            <div class="modal-body1"><@msg code='addUserMng.请输入密码' default='请输入密码'/>:</div>
            <div class="modal-body1">
            <input type="hidden" id="secretHash" name="secretHash" value="">
            <input type="password" id="pwd" name="pwd" class="modal-body1" style="width: 150px;" /></div>
            <div class="modal-body1"><@msg code='addUserMng.请输入谷歌验证' default='请输入谷歌验证'/>:</div>
            <div class="modal-body1">
            <input type="text" id="googlePwd" name="googlePwd" class="modal-body1" style="width: 150px;" maxlength="6"/></div>
            <div class="modal-body1"></div>
            <div class="modal-footer">
                <button type="button" onclick="comfirmOK()" class="btn btn-primary"><@msg code='addUserMng.确认' default='确认'/></button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:400px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h2 class="modal-title" id="myModalLabel"><@msg code='addUserMng.提醒' default='提醒'/></h4>
            </div>
            <div class="modal-body1">
            <font color="red" size="3"><strong><@msg code='addUserMng.预设密码' default='预设密码'/>：</strong></font><input type="text" id="newPwdStr" name="newPwdStr" value="${newPwd}" class="modal-body1" style="width: 150px;" maxlength="6"/></div>
            <div class="modal-body1"></div>
            <div class="modal-footer">
                <button type="button" onclick="window.location.href = '${ctx}/mchntUser/userMng'" class="btn btn-primary"><@msg code='addUserMng.关闭' default='关闭'/></button>
            </div>
        </div>
    </div>
</div>
</#assign>
<#assign htmlJS>
	//一进入页面，触发事件
	window.onload = function() {
		var value = document.getElementById("result").value;
		var newPwd = document.getElementById("newPwd").value;
		if (value == '1') {
			alert("<@msg code='addUserMng.该用户已存在' default='该用户已存在'/>");
		} else if (value == '2') {
			//新增人员成功后，提示8位初始密码，关闭可跳回上页面
			alert('<@msg code='addUserMng.添加成功' default='添加成功'/>');
			$('#myModal2').modal('show');
		} else if (value == '3') {
			alert('<@msg code='addUserMng.修改成功' default='修改成功'/>');
			window.location.href = "${ctx}/mchntUser/userMng";
		}
	};

	//查看用户是否选择角色
	function reg() {
		var value = document.getElementById("userRole").value;
		
		var rege=document.getElementById("rege").value;
		
		var regex=/[^0-9a-zA-Z_-]/g;

		if (value == '1') {
			alert('<@msg code='addUserMng.请选择角色' default='请选择角色'/>');
			return false;
		}
		
		if(regex.test(rege)){
			alert("<@msg code='addUserMng.请正确输入登录账号' default='请正确输入登录账号'/>");
			return false;
		}
		
		if(rege == ""){
			alert("<@msg code='addUserMng.请正确输入登录账号' default='请正确输入登录账号'/>");
			return false;
		}
		
		return true;
	}

	//返回上一页
	function backHome() {
		window.location.href = "${ctx}/mchntUser/userMng";
	}
	
	//失去焦点事件
	function blura(){
		var regex=/[^0-9a-zA-Z_-]/g;
		var rege=document.getElementById("rege").value;
		var span=document.getElementById("spanId");
		if(!regex.test(rege) && rege!=''){
			span.innerHTML="";
			span.color="#c4cbc7";
		}else{
			span.innerHTML="<@msg code='addUserMng.请输入正确的登录账号' default='请输入正确的登录账号'/>";
			span.style.color="red";
		}
		
	}
	
	function focusa(){
		var span=document.getElementById("spanId");
		span.innerHTML="<@msg code='addUserMng.只能输入数字英文以及特殊符号_-' default='只能输入数字英文以及特殊符号_-'/>";
		span.style.color="#c4cbc7";
	}
	
	function verifyUserId() {
		if (reg()) {
			$('#myModal1').modal('show');<#--彈出密碼、谷歌驗證碼視窗-->
			disableGaCheck();
		};
	}
	
	<#--輸入密碼、谷歌驗證碼後按下確認-->
	function comfirmOK(){
	　　var pwd = $("#pwd").val();　
		if($('#pwd').val()==''){
			alert("密码不能为空");
			return;
	    }
		if($("#googlePwd").val()==''){
		    alert("谷歌验证不能为空");
			return;
	    }
	    $('#myModal1').modal('hide');
	    enableGaCheck();
	    $("#secretHash").val(secretHash($("#pwd").val(),$("#googlePwd").val()));
	    comfirmPwd();
	}

	<#--驗證密码、谷歌验证码-->
	function comfirmPwd() {
		var comfirmUrl = "${ctx}/secrity/comfirmPwd";
		var secretHash = $("#secretHash").val();
		var googlePwd = $("#googlePwd").val();
		$.ajax({
			url: comfirmUrl,
			async: false,
			type : "POST",
			data: {
				secretHash : secretHash,
				googlePwd : googlePwd,
			},
			success: function(data) {
				<#--密码、谷歌验证码的验证状态返回-->
				var dataObj = JSON.parse(data);
				var	checkVal = dataObj.respData.respData;
			 	if(checkVal == "10"){
					$('#alertContent').html("<@msg code='addUserMng.密码错误' default='密码错误'/>");
					$('#alertDialog').modal('show');
					$("#pwd").val('');
					$("#googlePwd").val('');
				} else if(checkVal == "11"){
					$('#alertContent').html("<@msg code='addUserMng.谷歌验证错误' default='谷歌验证错误'/>");
					$('#alertDialog').modal('show');
					$("#pwd").val('');
					$("#googlePwd").val('');
				} else if(checkVal == "12"){
					$('#alertContent').html("<@msg code='addUserMng.谷歌验证码连续输入錯誤，帐号已被锁定' default='谷歌验证码连续输入錯誤，帐号已被锁定'/>");
					$('#alertDialog').modal('show');
					$("#pwd").val('');
					$("#googlePwd").val('');
				} else{
					$("#hSecretHash").val(secretHash);
					$("#hGooglePwd").val(googlePwd);
					document.getElementById("mchntUserForm").submit();
				}
			},error:function(){
			    alert("<@msg code='addUserMng.连线异常，请联系管理员！' default='连线异常，请联系管理员！'/>");
			}
		});
	}
	
	function secretHash(passwd, validCode){
        var pwdmd5 = CryptoJS.MD5((passwd+"PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP").substring(0, 50));
        return CryptoJS.MD5(validCode+"+PPAY_2018_MER+"+pwdmd5);
    }
</#assign>
<#include "/common/layout.ftl" />