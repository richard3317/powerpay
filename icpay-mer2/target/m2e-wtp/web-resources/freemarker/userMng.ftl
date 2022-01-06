<#include "/common/macro.ftl"> 
<#--<%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"]> 
<#assign
fn=JspTaglibs["http://java.sun.com/jsp/jstl/functions"]>--> 
<#assign htmlHead>
	<script src="${ctx}/resources/js/crypto-js/md5.js"></script>
</#assign>
<#assign htmlContent>
<ol class="breadcrumb">
	<li><a href="${ctx}/index"><@msg code='userMng.商户服务' default='商户服务'/></a></li>
	<li class="active"><@msg code='userMng.人员管理' default='人员管理'/></li>
</ol>


<div class="panel panel-default">
	<div class="panel-heading">
		<h4 class="panel-title"><@msg code='userMng.人员管理' default='人员管理'/></h4>
	</div>

	<div class="panel-body">

		<form action="${ctx}/mchntUser/query">
			<table >
				<tr >
						<td width="10%" style="padding: 5px; padding-right: 10px; text-align: right;"><@msg code='userMng.账户类型' default='账户类型'/></td>
						<td width="23%">
							<select class="form-control m-b" name="userRole" id="userRole"  style="margin-bottom: 0; width: 200px;">
							<option value='' selected="selected">&nbsp;&nbsp;<@msg code='userMng.--请选择--' default='--请选择--'/></option>
							<option value="py"><@msg code='userMng.业务查询专员' default='业务查询专员'/></option>
							<option value="wd"><@msg code='userMng.清算出款专员' default='清算出款专员'/></option>
							</select>
						</td>
					

						
						<td width="10%" style="padding: 5px; padding-right: 10px; text-align: right;"><@msg code='userMng.登录账号' default='登录账号'/></td>
						<td width="23%">
							<input type="text" name="userId" class="form-control" style="width: 200px;" />
						</td>
						
						
					<td>&nbsp;&nbsp;<input class="btn btn-primary btn-sm"
					style="margin-right: 10px;  height:35px;" role="button" type="submit" value="<@msg code='userMng.查询' default='查询'/>"></td>
				<td>
	
				</tr>
			</table>

		</form>
	</div>



	<!--form表单绑值  -->
	<div class="panel-body">
		<form action="">
			<table  width="100%" class="tbl-info">
				<tr class="even">
					<td colspan="8" class="td-btns"><a id="qryBtn1" class="btn btn-primary btn-sm"
						href="${ctx}/mchntUser/kipeUrl?type=0" style="float: left; margin-right: 10px;" role="button"
						><@msg code='userMng.新增' default='新增'/></a> 

						<a id="qryBtn3" class="btn btn-primary btn-sm" href="#"
						style="float: left; margin-right: 10px;" role="button" onclick="delUserMng()"><@msg code='userMng.删除' default='删除'/></a> 
						
						<a id="qryBtn5" class="btn btn-primary btn-sm" href="#"
						style="float: left; margin-right: 10px;" role="button" onclick="disableUserMng()"><@msg code='userMng.禁用' default='禁用'/></a>
						
						<a id="qryBtn6" class="btn btn-primary btn-sm" href="#"
						style="float: left; margin-right: 10px;" role="button" onclick="enableUserMng()"><@msg code='userMng.解除禁用' default='解除禁用'/></a>
					
						<a id="qryBtn7" class="btn btn-primary btn-sm" href="#"
						style="float: left; margin-right: 10px;" role="button" onclick="unlockUserMng()"><@msg code='userMng.解除锁定' default='解除鎖定'/></a>
					</td>
				</tr>
				<tr  class="odd">
					<td  class="td-btns"></td>
					<td  class="td-btns"><@msg code='userMng.登录账号' default='登录账号'/></td>
					<td  class="td-btns"><@msg code='userMng.账户类型' default='账户类型'/></td>
					<td  class="td-btns"><@msg code='userMng.状态' default='状态'/></td>
					<td  class="td-btns"><@msg code='userMng.创建时间' default='创建时间'/></td>
					<td  class="td-btns"><@msg code='userMng.最后更新时间' default='最后更新时间'/></td>
					<td  class="td-btns"><@msg code='userMng.操作' default='操作'/></td>
				</tr>
				<#list mchList as mchs>
				
				<tr  class="even">
					<td  class="td-btns" ><input type="checkbox" name="checks" value="${mchs.mchntCd };${mchs.userId};${mchs.userState}"></td>
					<td  class="td-btns">
					
					<div>
					<font size="2">	
					${mchs.userId}
					</font>
					</div>
					</td> 
					<#if "py"==mchs.userRole>
					<td  class="td-btns">
					<div>
					<font size="2">
					<@msg code='userMng.业务查询专员' default='业务查询专员'/>  
					</font>
					</div>
					</td> 
					</#if> 
					<#if "wd"==mchs.userRole>
					<td  class="td-btns">
					<div>
					<font size="2">
					<@msg code='userMng.清算出款专员' default='清算出款专员'/>
					</font>
					</div>
					</td> 
					</#if> 
					
					<#-- userState:9=已锁定,2=已禁用,0=初始状况(需重置密码),1=正常使用 -->
					<#if "0"==mchs.userState>
					<td  class="td-btns">
					<div>
					<font size="2">
					<@msg code='userMng.初始' default='初始'/>
					</font>
					</div>
					</td>
					</#if> 
					<#if "1"==mchs.userState>
					<td  class="td-btns">
					<div>
					<font size="2">
					<@msg code='userMng.有效' default='有效'/>
					</font>
					</div>
					</td>
					</#if> 
					<#if "2"==mchs.userState>
					<td  class="td-btns">
					<div>
					<font size="2">
					<@msg code='userMng.已禁用' default='已禁用'/>
					</font>
					</div>
					</td>
					</#if> 
					<#if "9"==mchs.userState>
					<td  class="td-btns">
					<div>
					<font size="2">
					<@msg code='userMng.已锁定' default='已锁定'/>
					</font>
					</div>
					</td>
					</#if> 
					
					<td  class="td-btns">
					<div>
					<font size="2">
					${mchs.recCrtTs ? string("yyyy-MM-dd HH:mm:ss")}
					</font>
					</div>
					</td>
					<td  class="td-btns" >
					<div>
					<font size="2">
					${mchs.recUpdTs ? string("yyyy-MM-dd HH:mm:ss")}
					</font>
					</div>
					</td> 
				 
				 <td  class="td-btns">	<a id="qryBtn2"
						class="btn btn-primary btn-sm" 
						style="float: left; margin-right: 10px;" role="button" id="edit" onclick="editUserMng('${mchs.userId}')"><@msg code='userMng.修改' default='修改'/></a> 
						</td>
				</tr>
				</#list>
			</table>
		</form>
		
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
				              	 <@msg code='userMng.提示' default='提示'/>
				            </h4>
				         </div>
				         <div class="modal-body" id="alertContent">
				           	 <@msg code='userMng.在这里添加一些文本' default='在这里添加一些文本'/>
				         </div>
				         <div class="modal-footer">
				            <button type="button" class="btn btn-primary" 
				               data-dismiss="modal"><@msg code='userMng.确定' default='确定'/>
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
		                <h4 class="modal-title" id="myModalLabel"><@msg code='userMng.确认' default='确认'/></h4>
		            </div>
		            <div class="modal-body1"><@msg code='userMng.请输入密码' default='请输入密码'/>:</div>
		            <div class="modal-body1">
		            <input type="hidden" id="secretHash" name="secretHash" value="">
		            <input type="password" id="pwd" name="pwd" class="modal-body1" style="width: 150px;" /></div>
		            <div class="modal-body1"><@msg code='userMng.请输入谷歌验证' default='请输入谷歌验证'/>:</div>
		            <div class="modal-body1">
		            <input type="text" id="googlePwd" name="googlePwd" class="modal-body1" style="width: 150px;" maxlength="6"/></div>
		            <div class="modal-body1"></div>
		            <div class="modal-footer">
		                <button type="button" onclick="comfirmOK()" class="btn btn-primary"><@msg code='userMng.确认' default='确认'/></button>
		            </div>
		        </div>
		    </div>
		</div>
		
	</div>
	<div id="summary" style="color:#F00" ></div>
</#assign>
<#assign htmlJS>
	var actionUrl = '';<#--刪除、禁用、解除禁用的url-->
	var funcVals = '';<#--刪除、禁用、解除禁用往後端送的值-->
	
	<#--删除-->
	function delUserMng(){
		var vals = '';
		vals=$("input:checkbox[name='checks']:checked").map(function(index,elem) {
	        return $(elem).val();
	    }).get().join(',');
		
		if(vals==null||vals==""){
			alert("<@msg code='userMng.请选择您需要删除的数据' default='请选择您需要删除的数据'/>");
			return ;
		}
		if(confirm("<@msg code='userMng.确定删除吗' default='确定删除吗'/>")){
			actionUrl = "/mchntUser/delUserMng";
			funcVals = vals;
			$('#myModal1').modal('show');<#--彈出密碼、谷歌驗證碼視窗-->
			disableGaCheck();
		}
	}
	
	<#--禁用-->
	function disableUserMng(){
		var vals = '';
		vals=$("input:checkbox[name='checks']:checked").map(function(index,elem) {
	        return $(elem).val();
	    }).get().join(',');
		
		if(vals==null||vals==""){
			alert("<@msg code='userMng.请选择您需要禁用的数据' default='请选择您需要禁用的数据'/>");
			return ;
		}
		if(confirm("<@msg code='userMng.确定禁用吗' default='确定禁用吗'/>")){
			actionUrl = "/mchntUser/disableUserMng";
			funcVals = vals;
			$('#myModal1').modal('show');<#--彈出密碼、谷歌驗證碼視窗-->
			disableGaCheck();
		}
	}
	
	<#--解除禁用-->
	function enableUserMng(){
		var vals = '';
		vals=$("input:checkbox[name='checks']:checked").map(function(index,elem) {
			var val = $(elem).val();
	        var data = val.split(";");
			if("2" != data[2]){
				return false;
			}
			return val;
	    }).get().join(',');
		
		if(vals==null||vals==""){
			alert("<@msg code='userMng.请选择您需要解除禁用的数据' default='请选择您需要解除禁用的数据'/>");
			return ;
		} else {
			var data = vals.split(",");
	    	var flag = false;
		    for(var i =0 ; i< data.length ; i++ ){
			    if(data[i]=="false"){
			    	flag= true;
			    	break;
			    }
		    }
		    if(flag){
		    	alert("<@msg code='userMng.只能选择状态为禁用的用户去解除' default='只能选择状态为禁用的用户去解除'/>");
		    	return;
		    }
		}
		if(confirm("<@msg code='userMng.确定解除禁用吗' default='确定解除禁用吗'/>")){
			actionUrl = "/mchntUser/enableUserMng";
			funcVals = vals;
			$('#myModal1').modal('show');<#--彈出密碼、谷歌驗證碼視窗-->
			disableGaCheck();
		}
	}
	
	<#--解除锁定-->
	function unlockUserMng(){
		var vals = '';
		vals=$("input:checkbox[name='checks']:checked").map(function(index,elem) {
			var val = $(elem).val();
	        var data = val.split(";");
			if("9" != data[2]){
				return false;
			}
			return val;
	    }).get().join(',');
		
		if(vals==null||vals==""){
			alert("<@msg code='userMng.请选择您需要解除锁定的数据' default='请选择您需要解除锁定的数据'/>");
			return ;
		} else {
			var data = vals.split(",");
	    	var flag = false;
		    for(var i =0 ; i< data.length ; i++ ){
			    if(data[i]=="false"){
			    	flag= true;
			    	break;
			    }
		    }
		    if(flag){
		    	alert("<@msg code='userMng.只能选择状态为锁定的用户去解除' default='只能选择状态为锁定的用户去解除'/>");

		    	return;
		    }
		}
		if(confirm("<@msg code='userMng.确定解除锁定吗' default='确定解除锁定吗'/>")){
			actionUrl = "/mchntUser/unlockUserMng";
			funcVals = vals;
			$('#myModal1').modal('show');<#--彈出密碼、谷歌驗證碼視窗-->
		}
	}
	
	<#--修改-->
	function editUserMng(userId){
		if(confirm("<@msg code='userMng.确定修改吗？' default='确定修改吗？'/>")){
	
		window.location.href="${ctx}/mchntUser/kipeUrl?type="+userId;
			return  true;
		}
	}
	
	<#--輸入密碼、谷歌驗證碼後按下確認-->
	function comfirmOK(){
	　　var pwd = $("#pwd").val();　
		if($('#pwd').val()==''){
			alert("<@msg code='userMng.密码不能为空' default='密码不能为空'/>");
			return;
	    }
		if($("#googlePwd").val()==''){
		    alert("<@msg code='userMng.谷歌验证码不能为空' default='谷歌验证码不能为空'/>");
			return;
	    }
	    $('#myModal1').modal('hide');
	    enableGaCheck();
	    $("#secretHash").val(secretHash($("#pwd").val(),$("#googlePwd").val()));
	    comfirmPwd();
	}

	<#--刪除、禁用、解除禁用的ajax(依url名來區分)-->
	function comfirmPwd() {
		var comfirmUrl = "${ctx}" + actionUrl;
		$.ajax({
			url: comfirmUrl,
			async: false,
			type : "POST",
			data: {
				secretHash : $("#secretHash").val(),
				googlePwd : $("#googlePwd").val(),
				funcVals : funcVals,
			},
			success: function(data) {
				var dataObj = '';
				var checkVal = '';				
				<#--密码、谷歌验证码的验证状态返回-->
				if (data.indexOf('respData') != -1) {
					dataObj = JSON.parse(data);
					checkVal = dataObj.respData.respData;
				}
			 	if(checkVal == "10"){
					$('#alertContent').html("<@msg code='userMng.密码错误' default='密码错误'/>");
					$('#alertDialog').modal('show');
					$("#pwd").val('');
					$("#googlePwd").val('');
				} else if(checkVal == "11"){
					$('#alertContent').html("<@msg code='userMng.谷歌验证错误' default='谷歌验证错误'/>");
					$('#alertDialog').modal('show');
					$("#pwd").val('');
					$("#googlePwd").val('');
				} else if(checkVal == "12"){
					$('#alertContent').html("<@msg code='userMng.谷歌验证码连续输入錯誤，帐号已被锁定' default='谷歌验证码连续输入錯誤，帐号已被锁定'/>");
					$('#alertDialog').modal('show');
					$("#pwd").val('');
					$("#googlePwd").val('');
				} else{
					if("1"==data){
						alert("<@msg code='userMng.成功' default='成功'/>");
						window.location.href="${ctx}/mchntUser/userMng";
					}else{
						alert("<@msg code='userMng.失败' default='失败'/>");
					}
					actionUrl = '';
					funcFlag = '';
				}
			},error:function(){
			    alert("<@msg code='userMng.连线异常，请联系管理员！' default='连线异常，请联系管理员！'/>");
			}
		});
	}
	
	function secretHash(passwd, validCode){
        var pwdmd5 = CryptoJS.MD5((passwd+"PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP").substring(0, 50));
        return CryptoJS.MD5(validCode+"+PPAY_2018_MER+"+pwdmd5);
    }
	
</#assign>
<#include "/common/layout.ftl" />