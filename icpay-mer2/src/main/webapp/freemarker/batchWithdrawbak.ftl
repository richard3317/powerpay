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
		<li><a href="#"><@msg code='batchWithdrawbak.商户服务' default='商户服务'/></a></li>
		<li><a href="${ctx}/withdraw/withdrawPage"><@msg code='batchWithdrawbak.取现' default='取现'/></a></li>
		<li class="active"><@msg code='batchWithdrawbak.批量取现' default='批量取现'/></li>
	</ol>
    
    <div class="main_container">
		    <!-- 模态框（Modal） -->
			<div class="modal fade" id="myModalLabel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="z-index:999999 !important;">
				<div class="modal-dialog" style="width:320px;">
					<div class="modal-content" style="width:320px;">
						<div class="modal-header">
				            <button type="button" class="close" 
				               data-dismiss="modal" aria-hidden="true">
				                  &times;
				            </button>
				            <h4 class="modal-title" id="alertTitle">
				              	 <@msg code='batchWithdrawbak.提示' default='提示'/>
				            </h4>
				         </div>
				         <div class="modal-body" id="alertContent">
				           	 <@msg code='batchWithdrawbak.在这里添加一些文本' default='在这里添加一些文本'/>
				         </div>
				         <div class="modal-footer">
				            <button type="button" class="btn btn-primary" 
				               data-dismiss="modal"><@msg code='batchWithdrawbak.确定' default='确定'/>
				            </button>
				         </div>
					</div>
				</div>
			</div>
			
			 <!-- Withdraw confirm dialog -->
	            <div class="modal fade" id="confirmDialog" tabindex="-1" role="dialog"
	                aria-labelledby="myModalLabel" aria-hidden="true"
	                data-backdrop="static" data-keyboard="false">
	                <div class="modal-dialog" style="width: 640px;">
	                    <div class="modal-content" style="width: 640px;">
	                        <table><tr>
	                        <td><div class="loader"></div></td><td><div class="modal-body" id="alertContent"><@msg code='batchWithdrawbak.交易进行中，请稍后...' default='交易进行中，请稍后...'/></div></td>
                            </tr></table>
	                    </div>
	                </div>
	            </div>
			
			<div class="modal fade" id="myModalLabel1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true" style="z-index:999999 !important;">
				<div class="modal-dialog" style="width:320px;">
					<div class="modal-content" style="width:320px;">
						<div class="modal-header">
				            <button type="button" class="close" 
				               data-dismiss="modal" aria-hidden="true">
				                  &times;
				            </button>
				            <h4 class="modal-title" id="alertTitle">
				              	 <@msg code='batchWithdrawbak.取现交易结果' default='取现交易结果'/>
				            </h4>
				         </div>
				         <div class="modal-body" id="alertContent1">
				           	 <@msg code='batchWithdrawbak.在这里添加一些文本' default='在这里添加一些文本'/>
				         </div>
				         <div class="modal-footer">
				                <button type="button" id="flist" class="btn btn-primary"><@msg code='batchWithdrawbak.导出失败数据' default='导出失败数据'/></button>
				            <button type="button" class="btn btn-primary" 
				               data-dismiss="modal"><@msg code='batchWithdrawbak.确定' default='确定'/>
				            </button>
				         </div>
					</div>
				</div>
			</div>
		</div>
			
			
	<div class="panel panel-default">
		<div class="panel-heading"><h4 class="panel-title"><@msg code='batchWithdrawbak.批量取现' default='批量取现'/></h4></div>
	
		<div class="panel-body">
		<form id="form2" action="" class="form-horizontal form-label-left formUpdate" >
		
			<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				    <div class="modal-dialog">
				        <div class="modal-content" style="width:400px;">
				            <div class="modal-header">
				                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				                <h4 class="modal-title" id="myModalLabel"><@msg code='batchWithdrawbak.取现确认' default='取现确认'/></h4>
				            </div>
				            <input type="hidden" id="secretHash" name="secretHash" value="">
				         	<div class="modal-body1" id="alertMsg"></div>
				            <div class="modal-body1"><@msg code='batchWithdrawbak.请输入密码:' default='请输入密码:'/></div>
				            <div class="modal-body1">
				            <input type="password" id="pwd" name="pwd" class="modal-body1" style="width: 150px;" /></div>
				            <div class="modal-body1"><@msg code='batchWithdrawbak.请输入谷歌验证:' default='请输入谷歌验证:'/></div>
				            <div class="modal-body1">
				            <input type="text" id="gaCode" name="gaCode" class="modal-body1" style="width: 150px;" /></div>
				            <div class="modal-body1"></div>
				            <div class="modal-footer">
				                <button type="button" id="comfirmOK" class="btn btn-primary"><@msg code='batchWithdrawbak.确认' default='确认'/></button>
				            </div>
				        </div>
				    </div>
			</div>
			<div class="bill">
			<div class="bill-form">
			<#--<div class="form-group">-->
			<div class="bill-input">
				<textarea cols="50" rows="10" style="margin: 0px; width: 1317px; height: 250px; border: 0px;" id="withdrawListInpt" name="withdrawList" placeholder="<@msg code='batchWithdrawbak.请在此按下要求及示例输入取现申请' default='请在此按下要求及示例输入取现申请'/>" ></textarea>
			<#--</div>-->
			</div>
			</div>
			<div class="text-primary"><@msg code='batchWithdrawbak.1.输入格式：取现金额|户名|卡号|开户行|开户行代码(可通过下载' default='1.输入格式：取现金额|户名|卡号|开户行|开户行代码(可通过下载'/><a href="${ctx}/batchWithdraw/downBankCodeList" class="easyui-linkbutton"><span style="color: blue;"><@msg code='batchWithdrawbak.开户行代码表' default='开户行代码表'/></span></a> <@msg code='batchWithdrawbak.查询开户行对应的银行代码)。' default='查询开户行对应的银行代码)。'/>
			</div>
			<div class="text-primary"><@msg code='batchWithdrawbak.2.输入示例：' default='2.输入示例：'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<@msg code='batchWithdrawbak.300.00|张三|6227001215950083244|建设银行|01050000' default='300.00|张三|6227001215950083244|建设银行|01050000'/><br/>
            &nbsp;&nbsp;&nbsp;&nbsp;<@msg code='batchWithdrawbak.500.00|李四|6227001215950567833|中国银行|01040000' default='500.00|李四|6227001215950567833|中国银行|01040000'/>
			</div>
			<div class="text-warning"><@msg code='batchWithdrawbak.3.单次最多支持50条取现申请。' default='3.单次最多支持50条取现申请。'/>
			</div>
			<div class="text-primary"><@msg code='batchWithdrawbak.4.失败项目将会返回错误列表，可导出查看，也可通过取现查询菜单页面查看。' default='4.失败项目将会返回错误列表，可导出查看，也可通过取现查询菜单页面查看。'/>
			</div>
			 <br/>
            <div class="input-group" style="text-align: left;">
            	<span class="input-group-addon"><@msg code='batchWithdrawbak.图形验证' default='图形验证'/>&nbsp&nbsp</span>
            	<img id="validCodeImg" title="<@msg code='batchWithdrawbak.点击刷新验证码' default='点击刷新验证码'/>"
					src="${ctx}/validateCode?r=${rondom}" 
					width="105" height="33" 
					style="cursor: pointer; margin-right: 5px;display: inline-block;" />
                <input type="text" placeholder="<@msg code='batchWithdrawbak.请输入图形验证码' default='请输入图形验证码'/>" maxLength="4"
                	style="width: 150px; display: inline-block;" requiredInput="<@msg code='batchWithdrawbak.图形验证码' default='图形验证码'/>"
                	name="validateCode" id="validateCode" class="form-control">
            </div>
			<div class="bill-error" id ="batchNo">
			</div>
			<div class="bill-error" id ="billError">
			</div>
			<div class="bill-submit">
				<#-- <a class="btn btn-primary cash" style=" margin-left: 40px;" title="提交" data-toggle="modal" data-target="#operation480" ><span id ="verify"></span>提交</a> -->
				<button id="withdrawBtn" name="withdrawBtn" type="button" class="btn btn-primary"><@msg code='batchWithdrawbak.提交' default='提交'/></button>
				<a class="btn btn-info cash" style=" margin-left: 10px;" href="${ctx}/withdraw/withdrawPage" title="<@msg code='batchWithdrawbak.返回' default='返回'/>"></span><@msg code='batchWithdrawbak.返回' default='返回'/></a>
			</div>
			</div>
		</form>
		</div>
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
           alert("<@msg code='batchWithdrawbak.请先设置安全设置' default='请先设置安全设置'/>");
           $.jumpTo("${ctx}/secrity/secritySetting");
        }
        if("su" != role){
			if("wd" != role){
				alert("<@msg code='batchWithdrawbak.仅代付用户可以查看该页面' default='仅代付用户可以查看该页面'/>");
				$.jumpTo("${ctx}/index");
			}
		}
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
	
	$(function () { $('#alertDialog').on('show.bs.modal', function () {
       		<#-- enableWithdrawBtn(false); -->
       		$('#operation480').modal('hide');
      })
    });
    
    $(function () { $('#myModalLabel1').on('hide.bs.modal', function () {
       		$("#pwd").val('');
       		$("#gaCode").val('');
       		$('#withdrawListInpt').val('');
      })
    });
    
    $(function () { $('#myModalLabel').on('hide.bs.modal', function () {
       		$("#pwd").val('');
       		$("#gaCode").val('');
       		<#--  $('#withdrawListInpt').val('');-->
       		$("#validCodeImg").click();
      })
    });
    $(function () { $('#myModal1').on('hide.bs.modal', function () {
       		$("#pwd").val('');
       		$("#gaCode").val('');
       		$('#withdrawBtn').removeAttr('disabled');
      })
    });
    
	
	 $(function () { $('#withdrawBtn').off().on('click', function () {
	 	$('#withdrawBtn').attr('disabled', 'disabled');
	 	
	 	if($('#validateCode').val()==''){
		         alert("<@msg code='batchWithdrawbak.图形验证码不能为空' default='图形验证码不能为空'/>");
		         $('#withdrawBtn').removeAttr('disabled');
			     return;
	    }
	 	
	 	var inpVal = $('#withdrawListInpt').val();
		   var inpArr = inpVal.split(/[(\r\n)\r\n]+/);
		   var pattern = new RegExp("[`~!@#$^&*()={}':;',\\[\\]<>《》/?~！@#￥……&*（）——{}【】‘；：”“'。，、？]");
		   
	       if ("" == inpVal || inpVal.length == 0) {
		             $('#alertContent').html("<@msg code='batchWithdrawbak.请输入取现申请' default='请输入取现申请'/>");
					 $('#myModalLabel').modal('show');
				     $('#operation480').modal('toggle');
				     $('#withdrawBtn').removeAttr('disabled');
				     return;
			}else if( inpArr.length >= 51){
		    		$('#alertContent').html("<@msg code='batchWithdrawbak.单次最多支持50条取现申请，请检查后提交' default='单次最多支持50条取现申请，请检查后提交'/>");
					 $('#myModalLabel').modal('show');
				     $('#operation480').modal('toggle');
				     $('#withdrawBtn').removeAttr('disabled');
				     return;
			}else if( pattern.test(inpVal)){
		    		$('#alertContent').html("<@msg code='batchWithdrawbak.取现申请内容格式出错，请参照页面输入示例检查后重新提交' default='取现申请内容格式出错，请参照页面输入示例检查后重新提交'/>");
					 $('#myModalLabel').modal('show');
				     $('#operation480').modal('toggle');
				     $('#withdrawBtn').removeAttr('disabled');
				     return;
		    }else{
	           var flag=1;
	           var amount=0;
	    		for (i=0;i< inpArr.length;i++) {
		    		var inpArrVal = inpArr[i].split("|");
		    		if(inpArrVal.length != 5){
		    			$('#alertContent').html("<@msg code='batchWithdrawbak.取现申请内容格式出错，请参照页面输入示例检查后重新提交' default='取现申请内容格式出错，请参照页面输入示例检查后重新提交'/>");
						$('#myModalLabel').modal('show');
				     	$('#operation480').modal('toggle');
				     	$('#withdrawBtn').removeAttr('disabled');
				     	return;
		    		}else{
			    		for (j=0;j< inpArrVal.length;j++) {
		    				var regu = /^(\+)?\d+(\.\d+)?$/;
		    				if(inpArrVal[j].length==0){
			    				$('#alertContent').html("<@msg code='batchWithdrawbak.取现申请内容格式出错，请参照页面输入示例检查后重新提交' default='取现申请内容格式出错，请参照页面输入示例检查后重新提交'/>");
								$('#myModalLabel').modal('show');
								$('#operation480').modal('toggle');
								$('#withdrawBtn').removeAttr('disabled');
								return;
			    			}else if(!regu.test(inpArrVal[0])){
		    					$('#alertContent').html("<@msg code='batchWithdrawbak.输入金额格式不正确，请检查后重新提交' default='输入金额格式不正确，请检查后重新提交'/>");
								$('#myModalLabel').modal('show');
								$('#operation480').modal('toggle');
								$('#withdrawBtn').removeAttr('disabled');
								return;
			    			}else{
			    				if(j ==0 ){
									amount = amount + inpArrVal[0]*100;
									var total = ""+amount/100;
									if(total.indexOf(".")==-1)
								 		 total= total+".00";
								}
				  			}
				  		}
			  		}
			  	}
			  	
				var str = "<@msg code='bankCardWhiteList.您本次提交%s笔取现申请，取现总金额%v元。<br/>请输入登录密码和谷歌验证码进行确定提交：' default='您本次提交%s笔取现申请，取现总金额%v元。<br/>请输入登录密码和谷歌验证码进行确定提交：'/>";
				var msg = str.replace('%s', inpArr.length);
				msg = str.replace('%v', total);
				$('#alertMsg').html(msg);
		    	$('#myModal1').modal('show');
		    	disableGaCheck();
			    $('#operation480').modal('toggle');
		    }
    	})
    });
	
	
	$("#comfirmOK").click(function(){
	　　var pwd = $("#pwd").val();　
		if($('#pwd').val()==''){
		         alert("<@msg code='batchWithdrawbak.密码不能为空' default='密码不能为空'/>");
		         $('#withdrawBtn').removeAttr('disabled');
			     return;
	    }
	    if($('#gaCode').val()==''){
		         alert("<@msg code='batchWithdrawbak.谷歌验证码不能为空' default='谷歌验证码不能为空'/>");
		         $('#withdrawBtn').removeAttr('disabled');
			     return;
	    }
	    
	    $("#secretHash").val(secretHash($("#pwd").val(),$("#gaCode").val()));
	    
		var url = "${ctx}/secrity/comfirmPwd?secretHash=" + $("#secretHash").val()+"&googlePwd=" + $("#gaCode").val();
		$.get(url, function(data) {
			$.processAjaxResp(data, function(r) {
				if(r.respData == "10"){
					 $('#alertContent').html("<@msg code='batchWithdrawbak.密码错误' default='密码错误'/>");
					 $('#myModalLabel').modal('show');
					 $('#myModal1').modal('hide');
					 enableGaCheck();
					 <#--  $('#confirmDialog').modal('hide');-->
			    	 $("#pwd").val('');
			    	 $("#gaCode").val('');
			    	 <#-- 刷新验证码 -->
        			 $("#validCodeImg").click();
			    	 $('#withdrawBtn').removeAttr('disabled');
				}else if(r.respData == "11"){
					 $('#alertContent').html("<@msg code='batchWithdrawbak.谷歌验证错误' default='谷歌验证错误'/>");
					 $('#myModalLabel').modal('show');
					 $('#myModal1').modal('hide');
					 enableGaCheck();
					 <#--  $('#confirmDialog').modal('hide');-->
			    	 $("#pwd").val('');
			    	 $("#gaCode").val('');
			    	 <#-- 刷新验证码 -->
        			 $("#validCodeImg").click();
			    	 $('#withdrawBtn').removeAttr('disabled');
				}else{
					$("#pwd").val('');
					$("#gaCode").val('');
					add();
				}
		    });
		});
	});

	$("#flist").click(function(){
		$('#myModalLabel').modal('hide');
		var url = "${ctx}/batchWithdraw/export?batchNo=" + $("#batchNo").val();
		$.jumpTo(url);
	});
	
	function add() {
		var postData= $("#form2").serialize();
		
        $('#myModal1').modal('hide');
        enableGaCheck();
		$('#confirmDialog').modal('show');

		var postUrl = "${ctx}/batchWithdraw/submitWithdraw.do";
		$.ajax({
			url: postUrl,
			type: 'POST',
			data: postData,
			async: true,
			success: function(data) {
				$('#confirmDialog').modal('hide');
				
				var dataObj = parseJson(data); 
				if (dataObj === null) {
			    	alert ("<@msg code='batchWithdrawbak.系统异常！' default='系统异常！'/>");
			    	$('#withdrawBtn').removeAttr('disabled');
			    	return;					    
				}
									
				var code = dataObj.respCode;
				var msg=  dataObj.respMsg;
				var data = dataObj.respData;
				var batchNo = data.batchNo;
				$('#load').remove();
				if(batchNo != null && batchNo !='' ){
					$('#batchNo').val(batchNo);
	    			$('#alertContent1').html(msg);
			 		$('#myModalLabel1').modal('show');
				}else{
	    			$('#alertContent').html(msg);
			 		$('#myModalLabel').modal('show');
			 		<#-- 刷新验证码 -->
        			$("#validCodeImg").click();
			    	$('#withdrawBtn').removeAttr('disabled');
				}
				
			 	$('#withdrawListInpt').val('');
			 	<#-- 刷新验证码 -->
        		$("#validCodeImg").click();
			 	$('#withdrawBtn').removeAttr('disabled');
				return;
			},
		    error:function(){
		    	$('#confirmDialog').modal('hide');
				clearInputs();
				<#-- 刷新验证码 -->
        		$("#validCodeImg").click();
		    	$('#alertContent').html("<@msg code='batchWithdrawbak.连线异常，请到取现查询页面确认交易结果。' default='连线异常，请到取现查询页面确认交易结果。'/>");
		    	$('#myModalLabel').modal('show');
		    	$('#withdrawBtn').removeAttr('disabled');
		    	return  false;
		    }
		});
	}
	
	 function secretHash(passwd, validCode){
        var pwdmd5 =  CryptoJS.MD5((passwd+"PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP").substring(0, 50));
        return CryptoJS.MD5(validCode+"+PPAY_2018_MER+"+pwdmd5);
    }
    
     function clearInputs(){
	    $('#withdrawListInpt').val('');
	    $('#pwd').val('');
	    $('#gaCode').val('');
	    $('#batchNo').val('');
  }
  
  
		$("#validCodeImg").click(function() {
			var r = Math.floor(Math.random() * ( 100000000 + 1));
			$(this).attr("src", "${ctx}/validateCode?r=" + r);
			$("#validateCode").val('');
		});
	
</#assign>

<#include "/common/layout.ftl" />