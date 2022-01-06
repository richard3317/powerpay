<#include "/common/macro.ftl">
	<#--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"]>
<#assign fn=JspTaglibs["http://java.sun.com/jsp/jstl/functions"]>-->

<#assign htmlHead>
	<script src="${ctx}/resources/js/jquery.dataTables.min.js"></script>
	<script src="${ctx}/resources/bootstrap/js/dataTables.bootstrap.min.js"></script>
	<script src="${ctx}/resources/js/crypto-js/md5.js"></script>
</#assign>

<#assign htmlContent>
	<ol class="breadcrumb">
		<li><a href="#"><@msg code='bankCardWhiteList.商户服务' default='商户服务'/></a></li>
		<li><a href="${ctx}/withdraw/withdrawPage"><@msg code='bankCardWhiteList.取现' default='取现'/></a></li>
		<li class="active"><@msg code='bankCardWhiteList.取现卡白名单' default='取现卡白名单'/></li>
	</ol>
    
    <div class="main_container">
		    <!-- 模态框（Modal） -->
			<div class="modal fade" id="alertDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="z-index:999999 !important;">
				<div class="modal-dialog" style="width:320px;">
					<div class="modal-content" style="width:320px;">
						<div class="modal-header">
				            <button type="button" class="close" 
				               data-dismiss="modal" aria-hidden="true">
				                  &times;
				            </button>
				            <h4 class="modal-title" id="alertTitle">
				              	 <@msg code='bankCardWhiteList.提示' default='提示'/>
				            </h4>
				         </div>
				         <div class="modal-body" id="alertContent">
				           	 <@msg code='bankCardWhiteList.在这里添加一些文本' default='在这里添加一些文本'/>
				         </div>
				         <div class="modal-footer">
				            <button type="button" class="btn btn-primary" 
				               data-dismiss="modal"><@msg code='bankCardWhiteList.确定' default='确定'/>
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
				                <h4 class="modal-title" id="myModalLabel"><@msg code='bankCardWhiteList.确认添加' default='确认添加'/></h4>
				            </div>
				            <div class="modal-body1"><@msg code='bankCardWhiteList.请输入密码' default='请输入密码'/>:</div>
				            <div class="modal-body1">
				            <input type="hidden" id="secretHash" name="secretHash" value="">
				            <input type="password" id="pwd" name="pwd" class="modal-body1" style="width: 150px;" /></div>
				            <div class="modal-body1"><@msg code='bankCardWhiteList.请输入谷歌验证' default='请输入谷歌验证'/>:</div>
				            <div class="modal-body1">
				            <input type="text" id="googlePwd" name="googlePwd" class="modal-body1" style="width: 150px;" maxlength="6"/></div>
				            <div class="modal-body1"></div>
				            <div class="modal-footer">
				                <button type="button" id="comfirmOK" class="btn btn-primary"><@msg code='bankCardWhiteList.确认' default='确认'/></button>
				            </div>
				        </div>
				    </div>
			</div>
			
			<div class="modal fade" id="operation480" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog" style="width:640px;"><div class="modal-content" style="width:640px;"></div></div>
			</div>
    
	<div class="panel panel-default">
		<div class="panel-heading"><h4 class="panel-title"><@msg code='bankCardWhiteList.银行卡新增' default='银行卡新增'/></h4></div>
	
		<div class="panel-body">
		<form id="form2" action="" class="form-horizontal form-label-left formUpdate" >
			<div class="bill">
			<div class="bill-form">
			<#--<div class="form-group">-->
			<div class="bill-input">
			<input id ="bankInpt" type="text" placeholder="<@msg code='bankCardWhiteList.请在此输入开户名和卡号或输入「ALL」则表示允许所有卡号' default='请在此输入开户名和卡号或输入「ALL」则表示允许所有卡号'/>" onpropertychange="">
			<#--</div>-->
			</div>
			<div class="bill-hint"><@msg code='bankCardWhiteList.请以如下规范格式输入：户名|卡号。请以逗号、空格键隔开多组。' default='请以如下规范格式输入：户名|卡号。请以逗号、空格键隔开多组。'/>
			</div>
			</div>
			<div class="text-primary"><@msg code='bankCardWhiteList.例如：张三|6227001215950083244,李四|6227001215950083244' default='例如：张三|6227001215950083244,李四|6227001215950083244'/>
			</div>
			<div class="text-warning"><@msg code='bankCardWhiteList.如果输入「ALL」(全大写字母)则表示不需绑定取现卡，但每笔取现均须谷歌验证。' default='如果输入「ALL」(全大写字母)则表示不需绑定取现卡，但每笔取现均须谷歌验证。'/>
			</div>
			<div class="bill-error" id ="billError">
			</div>
			<div class="bill-submit">
				<a class="btn btn-primary cash" style=" margin-left: 40px;" title="<@msg code='bankCardWhiteList.添加' default='添加'/>" data-toggle="modal" data-target="#operation480" ><span id ="verify"></span><@msg code='bankCardWhiteList.添加' default='添加'/></a>
				<a class="btn btn-info cash" style=" margin-left: 10px;" href="${ctx}/withdraw/withdrawPage" title="<@msg code='bankCardWhiteList.返回' default='返回'/>"></span><@msg code='bankCardWhiteList.返回' default='返回'/></a>
			</div>
			</div>
		</form>
		</div>
	</div>
	<#-- <div class="panel-body">
		<form id="bank-list-tbl-qryfrm" action="${ctx}/secrity/bankCardWhiteList" method="post" class="form-horizontal">
		</form>
	</div> -->
	<div class="panel panel-info">
		<div class="panel-heading">
			<h4 class="panel-title"><a href="#"><@msg code='bankCardWhiteList.银行卡列表' default='银行卡列表'/></a></h4>
		</div>
		<table id="bank-list-tbl" width="10px" class="tbl-info">
			<thead>
			<tr>
			<th hidden="hidden"><@msg code='bankCardWhiteList.序号' default='序号'/></th>
			<th><@msg code='bankCardWhiteList.银行卡' default='银行卡'/></th>
			<th><@msg code='bankCardWhiteList.操作' default='操作'/></th>
			</tr>
			</thead>
			 <tbody id="J_template">
		        <#--<c:forEach var="riskList" items="${riskList}">-->
		        <#list riskList as riskMer>
		            <tr id="item">
		                <td hidden="hidden">${riskMer.id}</td>
		                <td>${riskMer.item}</td>
		                <td>
		                    <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
		                        <a id="delLink" class="blue"
		                           onclick="deleteBankCard('${riskMer.id}','${riskMer.item}')" data-toggle="modal"
		                           data-target="#tipModel"
		                           title="<@msg code='bankCardWhiteList.删除' default='删除'/>" style="cursor:pointer;"><@msg code='bankCardWhiteList.删除' default='删除'/></a>
		                    </div>
		                </td>
		            </tr>
		            </#list>
		        <#--</c:forEach>-->
			 </tbody>
		</table>
	    <#--<div id="bank-list-tbl-pagination" class="pagination"></div>-->
	</div>
	
	<@modal modalId="bank-list-delete-modal" title="">
	</@modal>
	
</#assign>

<#assign htmlJS>

    var errorstr ="";
    
    $(function() {
    	var secretState = "${Session["secretState"]}";
    	var role = "${Session["userRole"]}";
        if("wd" == role && "0" == secretState ){
           alert("<@msg code='bankCardWhiteList.请先设置安全设置' default='请先设置安全设置'/>");
           $.jumpTo("${ctx}/secrity/secritySetting");
        }
		if("su" != role){
			if("wd" != role){
			alert("<@msg code='bankCardWhiteList.仅代付用户可以查看该页面' default='仅代付用户可以查看该页面'/>");
				$.jumpTo("${ctx}/index");
			}
		}
        $("#bank-list-tbl-qryfrm").qryFrm("bank-list-tbl", "bank-list-tbl-pagination");
    });

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
	
	function checkAccNoList(){
		var inpVal =$('#bankInpt').val();
		var err= validCardList(inpVal);
		console.log("err: "+err);
		<#--  
		if(! isEmpty(err)){ 
			showError(err);
		}else{
			hideError();
		}
		-->
	}

	function validCardList(accListData) {
	    console.log('check accListData: '+accListData);
		var url = "${ctx}/secrity/validCardList";
		$.ajax({
				url: url,
				async: false,
				type : "POST",
				data: {
					accListData : accListData
				},
				success: function(data) {
					console.log("ajax ret: "+data);
					var resp = JSON.parse(data);
					if ("00" === resp.respCode){
						hideError();
					    errorstr = "";
					}
					else{
					    errorstr = resp.respMsg ;
						var lst = resp.respData.errList;
					    if (lst.length>0){
					      errorstr = resp.respMsg + ": ";
					      for(var i=0;i<lst.length;i++){
					          errorstr = errorstr + lst[i]+ " ";
					      }
					    }
					    showError(errorstr);
					}
				},
			    error:function(){
			    	errorstr = "<@msg code='bankCardWhiteList.连线异常，请联系管理员！' default='连线异常，请联系管理员！'/>";
			    	showError(errorstr);
			    }
			});
	}

	function checkValid(accNo) {
	    console.log('check accNo: '+accNo);
		var url = "${ctx}/secrity/preValid?preValidData=" + accNo;
		$.ajax({
				url: url,
				async: false,
				success: function(data) {
					console.log("ajax ret: "+data);
					var dataObj = JSON.parse(data);
					var lst = dataObj.respData.list;
					if (lst[0] === 'false') {
						errorstr = errorstr + lst[1];
					} else {
						return "";
					}
				},
			    error:function(){
			    	return  false;
			    }
			}); 
	}
	
	<#--  
	$("#bankInpt").bind('input propertychange', function() {
		checkAccNoList();
	}); 
	-->

	$(function () { $('#operation480').on('show.bs.modal', function () {
	       checkAccNoList();
	    })
	});
	
	$(function () { $('#operation480').on('shown.bs.modal', function () {
	       var accNo = $('#bankInpt').val();
	       if ("" == accNo || accNo.length == 0) {
		             $('#alertContent').html("<@msg code='bankCardWhiteList.请输入银行卡' default='请输入银行卡'/>");
					 $('#alertDialog').modal('show');
				     $('#operation480').modal('toggle');
		    }else{
		    	id = document.getElementById("billError").style.display
		    	if(id != "none"){
		    		<#--$('#alertContent').html("<@msg code='bankCardWhiteList.请先修正错误的银行卡号' default='请先修正错误的银行卡号'/>");-->
		    		$('#alertContent').html("<@msg code='bankCardWhiteList.请先修正错误的银行卡号' default='请先修正错误的银行卡号'/>");
					 $('#alertDialog').modal('show');
				     $('#operation480').modal('toggle');
		    	}else{
			    	$('#myModal1').modal('show');
			    	disableGaCheck();
				    $('#operation480').modal('toggle');
			    }
		    }
	    })
	});
	
	function secretHash(passwd, validCode){
        var pwdmd5 =  CryptoJS.MD5((passwd+"PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP").substring(0, 50));
        return CryptoJS.MD5(validCode+"+PPAY_2018_MER+"+pwdmd5);
    }
	
	$("#comfirmOK").click(function(){
	　　var pwd = $("#pwd").val();　
		if($('#pwd').val()==''){
		         alert("<@msg code='bankCardWhiteList.密码不能为空' default='密码不能为空'/>");
			     return;
	    }
		if($("#googlePwd").val()==''){
		         alert("谷歌验证不能为空");
			     return;
	    }
	    
	    $('#myModal1').modal('hide');
	    enableGaCheck();
	    $("#secretHash").val(secretHash($("#pwd").val(),$("#googlePwd").val()));
	    add();
	});

	function add() {

			var addUrl = "${ctx}/secrity/addBankList";
			$.ajax({
					url: addUrl,
					async: false,
					type : "POST",
					data: {
						bankList : $("#bankInpt").val(),
						secretHash : $("#secretHash").val(),
						googlePwd : $("#googlePwd").val(),
					},
			success: function(data) {
					 var dataObj = JSON.parse(data);
					 <#--密码、谷歌验证码的验证状态返回-->
					 var checkVal = dataObj.respData.respData;
					 
					 var lst = dataObj.respData.list;
				 	
				 	 if(checkVal == "10"){
						 $('#alertContent').html("<@msg code='bankCardWhiteList.密码错误' default='密码错误'/>");
						 $('#alertDialog').modal('show');
						 $("#pwd").val('');
						 $("#googlePwd").val('');
					 }else if(checkVal == "11"){
						 $('#alertContent').html("<@msg code='bankCardWhiteList.谷歌验证错误' default='谷歌验证错误'/>");
						 $('#alertDialog').modal('show');
						 $("#pwd").val('');
						 $("#googlePwd").val('');
					 }else if(checkVal == "12"){
						 $('#alertContent').html("<@msg code='bankCardWhiteList.谷歌验证码连续输入錯誤，帐号已被锁定' default='谷歌验证码连续输入錯誤，帐号已被锁定'/>");
						 $('#alertDialog').modal('show');
						 $("#pwd").val('');
						 $("#googlePwd").val('');
					 }else{
						if(lst == null || lst == ''){
							 $('#alertContent').html("<@msg code='bankCardWhiteList.添加成功' default='添加成功'/>");
							 $('#alertDialog').modal('show');
						 	 $.jumpTo("${ctx}/secrity/bankCardWhiteList");
						 }else{
							 var str ="";
							 for (var i = 0; i < lst.length; i ++) {
								str += lst[i]+"，";			
							 }
							 var msg = "<@msg code='bankCardWhiteList.这些银行卡 %s 格式不正确，添加失败！' default='这些银行卡 %s 格式不正确，添加失败！'/>";
							 var errMsg = msg.replace('%s', str);
							 $('#alertContent').html(errMsg);
						     $('#alertDialog').modal('show');
						 }
					 }
			},error:function(){
			    errorstr = "<@msg code='bankCardWhiteList.连线异常，请联系管理员！' default='连线异常，请联系管理员！'/>";
			    showError(errorstr);
			}
		});
	}
	
	function deleteBankCard(id,item) {
		var str = "<@msg code='bankCardWhiteList.银行卡：%s,确定删除该银行卡？' default='银行卡：%s,确定删除该银行卡？'/>";
		var msg = str.replace('%s', item);
		var re = confirm(msg);
		if(re == true){
			var url = "${ctx}/secrity/deleteBankCard?id=" + id;
			$.ajax({
					url: url,
					async: false,
					success: function(data) {
						var dataObj = JSON.parse(data);
						var recode = dataObj.respCode;
						if(recode=='00'){
						 $('#alertContent').html("<@msg code='bankCardWhiteList.删除成功' default='删除成功'/>");
						 $('#alertDialog').modal('show');
					 	 $.jumpTo("${ctx}/secrity/bankCardWhiteList");
					 }else{
					 	$('#alertContent').html("<@msg code='bankCardWhiteList.删除失败' default='删除失败'/>");
						 $('#alertDialog').modal('show');
					 	 $.jumpTo("${ctx}/secrity/bankCardWhiteList");
					 }
						
					},
				    error:function(){
				    	alert("<@msg code='bankCardWhiteList.系统异常，请联系管理员！' default='系统异常，请联系管理员！'/>");
				    	return  false;
				    }
				});
		}else{
			return;
		}
	}
	
</#assign>

<#include "/common/layout.ftl" />