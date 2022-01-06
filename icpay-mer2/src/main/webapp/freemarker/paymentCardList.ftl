<#include "/common/macro.ftl">
	<#--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"]>
<#assign fn=JspTaglibs["http://java.sun.com/jsp/jstl/functions"]>-->

<#assign htmlHead>
	<script src="${ctx}/resources/js/jquery.dataTables.min.js"></script>
	<script src="${ctx}/resources/bootstrap/js/dataTables.bootstrap.min.js"></script>
</#assign>

<#assign htmlContent>
	<ol class="breadcrumb">
		<li><a href="${ctx}"><@msg code='paymentCardList.商户服务' default='商户服务'/></a></li>
		<li><a href="${ctx}/payment/commonPay"><@msg code='paymentCardList.充值' default='充值'/></a></li>
		<li class="active"><@msg code='paymentCardList.常用充值账户列表' default='常用充值账户列表'/></li>
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
				              	 <@msg code='paymentCardList.提示' default='提示'/>
				            </h4>
				         </div>
				         <div class="modal-body" id="alertContent">
				           	 <@msg code='paymentCardList.在这里添加一些文本' default='在这里添加一些文本'/>
				         </div>
				         <div class="modal-footer">
				            <button type="button" class="btn btn-primary" 
				               data-dismiss="modal"><@msg code='paymentCardList.确定' default='确定'/>
				            </button>
				         </div>
					</div>
				</div>
			</div>
	</div>
			
    <div class="modal fade" id="operation480" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog" style="width:640px;"><div class="modal-content" style="width:640px;"></div></div>
    </div>
    
	<div class="panel panel-default">
		<div class="panel-heading"><h4 class="panel-title"><@msg code='paymentCardList.新增常用自充卡' default='新增常用自充卡'/></h4></div>
	
		<div class="panel-body">
		<form id="form2" action="" class="form-horizontal form-label-left formUpdate" >
			<div class="bill">
			<div class="bill-form">
			<#--<div class="form-group">-->
			<div class="bill-input">
			<input id ="bankInpt" type="text" placeholder="<@msg code='paymentCardList.请在此输入常用自充卡，格式：户名|卡号' default='请在此输入常用自充卡，格式：户名|卡号'/>" onpropertychange="">
			<#--</div>-->
			</div>
			<div class="bill-hint"><@msg code='paymentCardList.请以如下规范格式输入：户名|卡号。请以逗号、空格键隔开多组。' default='请以如下规范格式输入：户名|卡号。请以逗号、空格键隔开多组。'/>
			</div>
			</div>
			<div class="text-primary"><@msg code='paymentCardList.例如：张三|6227001215950083244,李四|6227001215950083244' default='例如：张三|6227001215950083244,李四|6227001215950083244'/>
			</div>
			<div class="bill-error" id ="billError">
			</div>
			<div class="bill-submit">
				<a id="btnAdd" class="btn btn-primary cash" style=" margin-left: 40px;" title="<@msg code='paymentCardList.添加' default='添加'/>"><span id ="verify"></span><@msg code='paymentCardList.添加' default='添加'/></a>
				<a class="btn btn-info cash" style=" margin-left: 10px;" href="${ctx}/payment/commonPay" title="<@msg code='paymentCardList.返回' default='返回'/>"></span><@msg code='paymentCardList.返回' default='返回'/></a>
			</div>
			</div>
		</form>
		</div>
	</div>
	<div class="panel-body">
		<form id="bank-list-tbl-qryfrm" action="${ctx}/secrity/bankCardWhiteList" method="post" class="form-horizontal">
		</form>
	</div>
	<div class="panel panel-info">
		<div class="panel-heading">
			<h4 class="panel-title"><a href="#"><@msg code='paymentCardList.银行卡列表' default='银行卡列表'/></a></h4>
		</div>
		<table id="bank-list-tbl" width="10px" class="tbl-info">
			<thead>
			<tr>
			<th hidden="hidden"><@msg code='paymentCardList.序号' default='序号'/></th>
			<th><@msg code='paymentCardList.银行卡' default='银行卡'/></th>
			<th><@msg code='paymentCardList.操作' default='操作'/></th>
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
		                           title="<@msg code='paymentCardList.删除' default='删除'/>" style="cursor:pointer;"><@msg code='paymentCardList.删除' default='删除'/></a>
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
			    	errorstr = "<@msg code='paymentCardList.连线异常，请联系管理员！' default='连线异常，请联系管理员！'/>";
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

	function confirmAdd(){
		checkAccNoList();
		
		var accNo = $('#bankInpt').val();
        if ("" == accNo || accNo.length == 0) {
            $('#alertContent').html("<@msg code='paymentCardList.请输入银行卡' default='请输入银行卡'/>");
            $('#alertDialog').modal('show');
            $('#operation480').modal('toggle');
        }else{
            id = document.getElementById("billError").style.display
            if(id != "none"){
                $('#alertContent').html("<@msg code='paymentCardList.请先修正错误的银行卡号' default='请先修正错误的银行卡号'/>");
                $('#alertDialog').modal('show');
            }else{
                add();
            }
        }		
	}
	
	$("#btnAdd").click(function(){
		confirmAdd();
	});	

	$(function () { $('#operation480').on('show.bs.modal', function () {
	       checkAccNoList();
	    })
	});
	
	$(function () { $('#operation480').on('shown.bs.modal', function () {
	       var accNo = $('#bankInpt').val();
	       if ("" == accNo || accNo.length == 0) {
		             $('#alertContent').html("<@msg code='paymentCardList.请输入银行卡' default='请输入银行卡'/>");
					 $('#alertDialog').modal('show');
				     $('#operation480').modal('toggle');
		    }else{
		    	id = document.getElementById("billError").style.display
		    	if(id != "none"){
		    		$('#alertContent').html("<@msg code='paymentCardList.请先修正错误的银行卡号' default='请先修正错误的银行卡号'/>");
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

	function add() {
		var addUrl = "${ctx}/payment/addCardList";
		 var cardList = $("#bankInpt").val();
		$.ajax({
				url: addUrl,
				async: false,
				type : "POST",
				data: {
					cardList : cardList
				},
				success: function(data) {
				 var dataObj = JSON.parse(data);
				 var lst = dataObj.respData.list;
				 if(lst == null || lst == ''){
					 $('#alertContent').html("<@msg code='paymentCardList.添加成功' default='添加成功'/>");
					 $('#alertDialog').modal('show');
				 	$.jumpTo("${ctx}/payment/paymentCardList");
				 }else{
					var str ="";
					for (var i = 0; i < lst.length; i ++) {
						str += lst[i]+"，";			
					}
					var errMsg = '<@msg code='paymentCardList.这些银行卡%s格式不正确，添加失败！' default='这些银行卡%s格式不正确，添加失败！'/>';
					errMsg = errMsg.replace('%s', str);
					$('#alertContent').html(errMsg);
				    $('#alertDialog').modal('show');
				 }
			},
			    error:function(){
			    	errorstr = "<@msg code='paymentCardList.连线异常，请联系管理员！' default='连线异常，请联系管理员！'/>";
			    	showError(errorstr);
			    }
		});
	}
	
	function deleteBankCard(id,item) {
		var msg = '<@msg code='paymentCardList.银行卡：%s确定删除该银行卡？' default='银行卡：%s确定删除该银行卡？'/>';
		msg = msg.replace('%s', item);
		var re = confirm(msg);
		if(re == true){
			var url = "${ctx}/payment/deleteCard?id=" + id;
			$.ajax({
					url: url,
					async: false,
					success: function(data) {
						var dataObj = JSON.parse(data);
						var recode = dataObj.respCode;
						if(recode=='00'){
						 $('#alertContent').html("<@msg code='paymentCardList.删除成功' default='删除成功'/>");
						 $('#alertDialog').modal('show');
					 	 $.jumpTo("${ctx}/payment/paymentCardList");
					 }else{
					 	$('#alertContent').html("<@msg code='paymentCardList.删除失败' default='删除失败'/>");
						 $('#alertDialog').modal('show');
					 	 $.jumpTo("${ctx}/payment/paymentCardList");
					 }
						
					},
				    error:function(){
				    	alert("<@msg code='paymentCardList.连线异常，请联系管理员！' default='连线异常，请联系管理员！'/>");
				    	return  false;
				    }
				});
		}else{
			return;
		}
	}
	
</#assign>

<#include "/common/layout.ftl" />