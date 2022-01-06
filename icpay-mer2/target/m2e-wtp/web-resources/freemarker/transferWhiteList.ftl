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
		<li><a href="#"><@msg code='transferWhiteList.商户服务' default='商户服务'/></a></li>
		<li><a href="${ctx}/withdraw/withdrawPage"><@msg code='transferWhiteList.转帐' default='转帐'/></a></li>
		<li class="active"><@msg code='transferWhiteList.转帐白名单' default='转帐白名单'/></li>
	</ol>
    
    <div class="main_container">
		    <!-- 模态框（Modal） -->
			<div class="modal fade" id="alertDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="z-index:999999 !important;">
				<div class="modal-dialog" style="width:320px;">
					<div class="modal-content" style="width:320px;">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				            <h4 class="modal-title" id="alertTitle"><@msg code='transferWhiteList.提示' default='提示'/></h4>
						</div>
						<div class="modal-body" id="alertContent"><@msg code='transferWhiteList.在这里添加一些文本' default='在这里添加一些文本'/></div>
						<div class="modal-footer">
				            <button type="button" class="btn btn-primary" data-dismiss="modal"><@msg code='transferWhiteList.确定' default='确定'/></button>
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
			                <h4 class="modal-title" id="myModalLabel"><@msg code='transferWhiteList.确认添加' default='确认添加'/></h4>
			            </div>
			            <div class="modal-body1"><@msg code='transferWhiteList.请输入密码' default='请输入密码'/>:</div>
			            <div class="modal-body1">
			            <input type="hidden" id="secretHash" name="secretHash" value="">
			            <input type="password" id="pwd" name="pwd" class="modal-body1" style="width: 150px;" /></div>
			            <div class="modal-body1"><@msg code='transferWhiteList.请输入谷歌验证' default='请输入谷歌验证'/>:</div>
			            <div class="modal-body1">
			            <input type="text" id="googlePwd" name="googlePwd" class="modal-body1" style="width: 150px;" maxlength="6"/></div>
			            <div class="modal-body1"></div>
			            <div class="modal-footer">
			                <button type="button" id="comfirmOK" class="btn btn-primary"><@msg code='transferWhiteList.确认' default='确认'/></button>
			            </div>
			        </div>
			    </div>
			</div>
			
			<div class="modal fade" id="operation480" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog" style="width:640px;"><div class="modal-content" style="width:640px;"></div></div>
			</div>
    
	<div class="panel panel-default">
		<div class="panel-heading"><h4 class="panel-title"><@msg code='transferWhiteList.商户号新增' default='商户号新增'/></h4></div>
	
		<div class="panel-body">
		<form id="form2" action="" class="form-horizontal form-label-left formUpdate" >
			<div class="bill">
			<div class="bill-form">
			<div class="bill-input">
			<input id ="mchntCdInpt" type="text" placeholder="<@msg code='transferWhiteList.请在此输入商户号' default='请在此输入商户号'/>" onpropertychange="">
			</div>
			<div class="bill-hint"><@msg code='transferWhiteList.请以如下规范格式输入：商户号。请以逗号、空格键隔开多组。' default='请以如下规范格式输入：商户号。请以逗号、空格键隔开多组。'/>
			</div>
			</div>
			<div class="text-primary"><@msg code='transferWhiteList.例如：999100000000066,999000000000088。' default='例如：999100000000066,999000000000088。'/>
			</div>
			<div class="bill-error" id ="billError">
			</div>
			<div class="bill-submit">
				<a class="btn btn-primary cash" style=" margin-left: 40px;" title="<@msg code='transferWhiteList.添加' default='添加'/>" data-toggle="modal" data-target="#operation480" ><span id ="verify"></span><@msg code='transferWhiteList.添加' default='添加'/></a>
				<a class="btn btn-info cash" style=" margin-left: 10px;" href="${ctx}/transfer/transferPage" title="<@msg code='transferWhiteList.返回' default='返回'/>"></span><@msg code='transferWhiteList.返回' default='返回'/></a>
			</div>
			</div>
		</form>
		</div>
	</div>
	<div class="panel panel-info">
		<div class="panel-heading">
			<h4 class="panel-title"><a href="#"><@msg code='transferWhiteList.商户号列表' default='商户号列表'/></a></h4>
		</div>
		<table id="bank-list-tbl" width="10px" class="tbl-info">
			<thead>
			<tr>
			<th hidden="hidden"><@msg code='transferWhiteList.序号' default='序号'/></th>
			<th><@msg code='transferWhiteList.商户号' default='商户号'/></th>
			<th><@msg code='transferWhiteList.操作' default='操作'/></th>
			</tr>
			</thead>
			 <tbody id="J_template">
		        <#list riskList as riskMer>
		            <tr id="item">
		                <td hidden="hidden">${riskMer.id}</td>
		                <td>${riskMer.item}</td>
		                <td>
		                    <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
		                        <a id="delLink" class="blue"
		                           onclick="deleteBankCard('${riskMer.id}','${riskMer.item}')" data-toggle="modal"
		                           data-target="#tipModel"
		                           title="<@msg code='transferWhiteList.删除' default='删除'/>" style="cursor:pointer;"><@msg code='transferWhiteList.删除' default='删除'/></a>
		                    </div>
		                </td>
		            </tr>
		            </#list>
		            
		            
			 </tbody>
		</table>
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
           alert("<@msg code='transferWhiteList.请先设置安全设置' default='请先设置安全设置'/>");
           $.jumpTo("${ctx}/secrity/secritySetting");
        }

        if("su" != role){
			if("wd" != role){
				alert("<@msg code='transferWhiteList.仅代付用户可以查看该页面' default='仅代付用户可以查看该页面'/>");
				$.jumpTo("${ctx}/index");
			}
		}
		
		$('#whiteItem').on('change', function(){
			setAccInfoByWhiteItem();
		});		
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
	}
	function hideError() {
		$("#billError").hide();
	}
	
	function checkMchntCdList(){
		var inpVal =$('#mchntCdInpt').val();
		var err= validMchntCdList(inpVal);
		console.log("err: "+err);
	}

	function validMchntCdList(mchntCdListData) {
		var url = "${ctx}/secrity/validMchntCdList?mchntCdListData=" + mchntCdListData;
		$.ajax({
				url: url,
				async: false,
				success: function(data) {
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
			    	errorstr = "<@msg code='transferWhiteList.连线异常，请联系管理员！' default='连线异常，请联系管理员！'/>";
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
	
	$(function () { $('#operation480').on('show.bs.modal', function () {
	       checkMchntCdList();
	    })
	});
	
	$(function () { $('#operation480').on('shown.bs.modal', function () {
		var accNo = $('#mchntCdInpt').val();
		if ("" == accNo || accNo.length == 0) {
			$('#alertContent').html("<@msg code='transferWhiteList.请输入商户号' default='请输入商户号'/>");
			$('#alertDialog').modal('show');
			$('#operation480').modal('toggle');
		}else{
			id = document.getElementById("billError").style.display
			if(id != "none"){
				var $err = $("#billError").text();
				$('#alertContent').html($err);
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
        var pwdmd5 = CryptoJS.MD5((passwd+"PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP").substring(0, 50));
        return CryptoJS.MD5(validCode+"+PPAY_2018_MER+"+pwdmd5);
    }
	
	$("#comfirmOK").click(function(){
	　　var pwd = $("#pwd").val();　
		if($('#pwd').val()==''){
		         alert("<@msg code='transferWhiteList.密码不能为空' default='密码不能为空'/>");
			     return;
	    }
	    $('#myModal1').modal('hide');
	    enableGaCheck();
	    $("#secretHash").val(secretHash($("#pwd").val(),$("#googlePwd").val()));
		var url = "${ctx}/secrity/comfirmPwd?secretHash=" + $("#secretHash").val()+"&googlePwd=" + $("#googlePwd").val();
		$.get(url, function(data) {
			$.processAjaxResp(data, function(r) {
				if(r.respData == "10"){
					 $('#alertContent').html("<@msg code='transferWhiteList.密码错误' default='密码错误'/>");
					 $('#alertDialog').modal('show');
			    	 $("#pwd").val('');
			    	 $("#googlePwd").val('');
				}else if(r.respData == "11"){
					 $('#alertContent').html("<@msg code='transferWhiteList.谷歌验证错误' default='谷歌验证错误'/>");
					 $('#alertDialog').modal('show');
			    	 $("#pwd").val('');
			    	 $("#googlePwd").val('');
			    }else if(r.respData == "12"){
					$('#alertContent').html("<@msg code='transferWhiteList.谷歌验证码连续输入錯誤，帐号已被锁定' default='谷歌验证码连续输入錯誤，帐号已被锁定'/>");
					$('#alertDialog').modal('show');
					$("#pwd").val('');
					$("#googlePwd").val('');
				}else{
					$("#pwd").val('');
					$("#googlePwd").val('');
					add();
				}
		    });
		});
	});

	function add() {
		var addUrl = "${ctx}/secrity/addMchntCdList?mchntCdList=" + $("#mchntCdInpt").val();
		$.post(addUrl, function(data) {
			$.processAjaxResp(data,
				function(r) {
				 var lst = r.list;
				 if(lst == null || lst == ''){
					 $('#alertContent').html("<@msg code='transferWhiteList.添加成功' default='添加成功'/>");
					 $('#alertDialog').modal('show');
				 	 $.jumpTo("${ctx}/secrity/transferWhiteList");
				 }else{
					var str ="";
					for (var i = 0; i < lst.length; i ++) {
						str += lst[i]+"，";			
					}
					var msg = "<@msg code='transferWhiteList.这些商户号%s格式不正确，添加失败！' default='这些商户号%s格式不正确，添加失败！'/>";
					msg = msg.replace("%s",str);
					$('#alertContent').html(msg);
				    $('#alertDialog').modal('show');
				   }
				}
			);
		});
	}
	
	function deleteBankCard(id,item) {
		var msg = "<@msg code='transferWhiteList.商户号：%s确定删除该商户号？' default='商户号：%s确定删除该商户号？'/>"
		msg = msg.replace("%s",item);
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
						 $('#alertContent').html("<@msg code='transferWhiteList.删除成功' default='删除成功'/>");
						 $('#alertDialog').modal('show');
					 	 $.jumpTo("${ctx}/secrity/transferWhiteList");
					 }else{
					 	$('#alertContent').html("<@msg code='transferWhiteList.删除失败' default='删除失败'/>");
						 $('#alertDialog').modal('show');
					 	 $.jumpTo("${ctx}/secrity/transferWhiteList");
					 }
						
					},
				    error:function(){
				    	alert("<@msg code='transferWhiteList.系统异常，请联系管理员！' default='系统异常，请联系管理员！'/>");
				    	return  false;
				    }
				});
		}else{
			return;
		}
	}
	
</#assign>

<#include "/common/layout.ftl" />