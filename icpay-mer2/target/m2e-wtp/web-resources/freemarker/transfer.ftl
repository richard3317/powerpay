<#include "/common/macro.ftl">
<#assign ctx = request.contextPath>

<#assign htmlHead>
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/js/jquery-easy-ui/themes/gray/easyui.css"></link>
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/js/jquery-easy-ui/themes/icon.css"></link>
	<#--<script src="${ctx}/resources/js/jquery.min.js"></script>-->
	<script type="text/javascript" src="${ctx}/resources/js/jquery-easy-ui/jquery.min.js"></script>
	<script src="${ctx}/resources/bootstrap/js/bootstrap.min.js"></script>
	
	<script type="text/javascript" src="${ctx}/resources/js/jquery-easy-ui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/jquery-easy-ui/locale/easyui-lang-zh_CN.js"></script>
	<script src="${ctx}/resources/js/jquery.form.js"></script>
	<script src="${ctx}/resources/js/jquery.validate.min.js"></script>
	<script src="${ctx}/resources/js/toastr.min.js"></script>
	<script src="${ctx}/resources/js/mer.js?v=${strNowMm!''}"></script>
	<script type="text/javascript" src="${ctx}/resources/js/validate.js"></script>
	<script src="${ctx}/resources/js/crypto-js/md5.js"></script>

	<style>
	.loader {
	  border: 16px solid #f3f3f3;
	  border-radius: 50%;
	  border-top: 16px solid #3498db;
	  width: 20px;
	  height: 20px;
	  -webkit-animation: spin 2s linear infinite; /* Safari */
	  animation: spin 2s linear infinite;
	}
	
	/* Safari */
	@-webkit-keyframes spin {
	  0% { -webkit-transform: rotate(0deg); }
	  100% { -webkit-transform: rotate(360deg); }
	}
	
	@keyframes spin {
	  0% { transform: rotate(0deg); }
	  100% { transform: rotate(360deg); }
	}
	</style>		
</#assign>

<#assign htmlContent>
	<ol class="breadcrumb">
		<li><a href="#"><@msg code='transfer.商户服务' default='商户服务'/></a></li>
		<li class="active"><@msg code='transfer.转帐' default='转帐'/></li>
	</ol>
	<div class="container body">
        <div class="main_container">
            <!-- Alert dialog -->
            <div class="modal fade" id="alertDialog" tabindex="-1" role="dialog" data-backdrop="static" 
                aria-labelledby="myModalLabel" aria-hidden="true" style="z-index: 999999 !important;">
                <div class="modal-dialog" style="width: 320px;">
                    <div class="modal-content" style="width: 320px;">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"
                                aria-hidden="true" backdrop="static">&times;</button>
                            <h4 class="modal-title" id="alertTitle"><@msg code='transfer.提示' default='提示'/></h4>
                        </div>
                        <div class="modal-body" id="alertContent"><@msg code='transfer.在这里添加一些文本' default='在这里添加一些文本'/></div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary"
                                data-dismiss="modal" data-keyboard="false"><@msg code='transfer.确定' default='确定'/></button>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Withdraw confirm dialog -->
            <div class="modal fade" id="operation480" tabindex="-1" role="dialog"
                aria-labelledby="myModalLabel" aria-hidden="true"
                data-backdrop="static" data-keyboard="false">
                <div class="modal-dialog" style="width: 640px;">
                    <div class="modal-content" style="width: 640px;">
                        <table><tr>
                        <td><div class="loader"></div></td><td><div class="modal-body" id="alertContent"><@msg code='transfer.交易进行中，请稍后。。。' default='交易进行中，请稍后。。。'/></div></td>
                        </tr></table>
                    </div>
                </div>
            </div>
            <!-- Main content -->
            <div class="container">
                <div class="row clearfix">
                    <div class="col-md-4 column">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h3 class="panel-title"><@msg code='transfer.帐户总览' default='帐户总览'/></h3>
                            </div>
                            <div class="input-group">
                                <span class="input-group-addon"><@msg code='commonPay.币别' default='币别'/>&nbsp&nbsp</span>
                                <select id="currCd" name="currCd" class="form-control selectpicker" placeholder="<@msg code='commonPay.请选择' default='请选择'/>" >
										<option value="156" selected><@msg code='fundMng.人民币' default='人民币'/></option>
										<option value="704"><@msg code='fundMng.越南盾' default='越南盾'/></option>
										<option value="764"><@msg code='fundMng.泰铢' default='泰铢'/></option>
								</select>
                            </div>
                            <div class="panel-body" style="border-color: #bce8f1;">
                                <ul class="list-group mb-3" style="border: 0px; border-color: #bce8f1;">
                                    <li class="list-group-item" style="border: 0px">
                                        <div>
                                            <h6 class="tile-stats"><@msg code='transfer.帐户总额' default='帐户总额'/></h6>
                                        </div>
                                        <span class="count_top" id="CNY_totalBalance"><font color="#666699"><strong>${totalBalance_CNY}</strong><#--  <@msg code='transfer.元' default='元'/>--></font></span>
                                        <span class="count_top" id="VND_totalBalance"><font color="#666699"><strong>${totalBalance_VND}</strong><#--  <@msg code='transfer.元' default='元'/>--></font></span>
                                        <span class="count_top" id="THB_totalBalance"><font color="#666699"><strong>${totalBalance_THB}</strong><#--  <@msg code='transfer.元' default='元'/>--></font></span>
                                    </li>
                                    <li class="list-group-item" style="border: 0px">
                                        <div>
                                            <#--<h6 class="tile-stats"><@msg code='transfer.可取现金额' default='可取现金额'/></h6>-->
                                            <h6 class="tile-stats"><@msg code='transfer.可代付馀额' default='可代付馀额'/></h6>
                                        </div>
                                        <span class="count_top" id="CNY_availableBalance"><font color="#666699"><strong>${availableBalance_CNY}</strong><#--  <@msg code='transfer.元' default='元'/>--></font></span>
                                        <span class="count_top" id="VND_availableBalance"><font color="#666699"><strong>${availableBalance_VND}</strong><#--  <@msg code='transfer.元' default='元'/>--></font></span>
                                        <span class="count_top" id="THB_availableBalance"><font color="#666699"><strong>${availableBalance_THB}</strong><#--  <@msg code='transfer.元' default='元'/>--></font></span>
	                                </li>
                                    <li class="list-group-item" style="border: 0px">
                                        <div>
                                            <#---<h6 class="tile-stats"><@msg code='transfer.T1待结算金额' default='T1待结算金额'/></h6>-->
                                            <h6 class="tile-stats"><@msg code='transfer.T+1帐户馀额' default='T+1帐户馀额'/></h6>
                                        </div>
                                        <span class="count_top" id="CNY_frozenT1Balance"><font color="#666699"><strong>${frozenT1Balance_CNY}</strong><#--  <@msg code='transfer.元' default='元'/>--></font></span>
                                        <span class="count_top" id="VND_frozenT1Balance"><font color="#666699"><strong>${frozenT1Balance_VND}</strong><#--  <@msg code='transfer.元' default='元'/>--></font></span>
                                        <span class="count_top" id="THB_frozenT1Balance"><font color="#666699"><strong>${frozenT1Balance_THB}</strong><#--  <@msg code='transfer.元' default='元'/>--></font></span>
                                    </li>
                                    <li class="list-group-item" style="border: 0px">
                                        <div>
                                            <h6 class="tile-stats"><@msg code='transfer.预扣金额' default='预扣金额'/></h6>
                                        </div>
                                        <span class="count_top" id="CNY_frozenBalance"><font color="#666699"><strong>${frozenBalance_CNY}</strong><#--  <@msg code='transfer.元' default='元'/>--></font></span>
                                        <span class="count_top" id="VND_frozenBalance"><font color="#666699"><strong>${frozenBalance_VND}</strong><#--  <@msg code='transfer.元' default='元'/>--></font></span>
                                        <span class="count_top" id="THB_frozenBalance"><font color="#666699"><strong>${frozenBalance_THB}</strong><#--  <@msg code='transfer.元' default='元'/>--></font></span>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-8 column">
						<form role="form" id="formTransfer" action="" class="form-horizontal form-label-left formUpdate" >
						<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						    <div class="modal-dialog">
						        <div class="modal-content" style="width:400px;">
						            <div class="modal-header">
						                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						                <h4 class="modal-title" id="myModalLabel"><@msg code='transfer.取现确认' default='取现确认'/></h4>
						            </div>
						            <input type="hidden" id="secretHash" name="secretHash" value="">
						         	<div class="modal-body1" id="alertMsg"></div>
						            <div class="modal-body1"><@msg code='transfer.请输入密码' default='请输入密码'/>:</div>
						            <div class="modal-body1">
						            <input type="password" id="pwd" name="pwd" class="modal-body1" style="width: 150px;" /></div>
						            <div class="modal-body1"><@msg code='transfer.请输入谷歌验证' default='请输入谷歌验证'/>:</div>
						            <div class="modal-body1">
						            <input type="text" id="gaCode" name="gaCode" class="modal-body1" style="width: 150px;" maxLength="6" /></div>
						            <div class="modal-body1"></div>
						            <div class="modal-footer">
						                <button type="button" id="comfirmOK" class="btn btn-primary"><@msg code='transfer.确认' default='确认'/></button>
						            </div>
						        </div>
						    </div>
						</div>
							<input type="hidden" id="currencyCode" name="currencyCode" value="">
							<input type="hidden" id="randv" name="randv" value="${randv!'999999'}" />
							<div class="input-group">
							<h3><@msg code='transfer.转帐操作' default='转帐操作'/></h3>
							</div>
							<br/>
							<div class="input-group">
                                <span class="input-group-addon"><@msg code='transfer.转出' default='转出'/></span>
                                <input type="text" id="mchntCd" name="mchntCd" class="form-control" placeholder="<@sessionVal key="mchntCd" />" readonly />
                                <span class="input-group-addon">#</span>
                            </div>
                            <br/>
                            <div class="input-group">
                                <span class="input-group-addon"><@msg code='transfer.转入' default='转入'/></span>
                                <select id="whiteItem" name="whiteItem" class="selectpicker form-control" placeholder="<@msg code='transfer.请由转帐白名单选择转帐商户' default='请由转帐白名单选择转帐商户'/>" >
                                    <option value=""><@msg code='transfer.(请选择)' default='(请选择)'/></option>
                                    <#list whiteList as whiteItem>
                                        <#-- 这选项不用多语吧 
                                        <option value="${whiteItem.item}">
                                        <@msg code='transfer.${whiteItem.item}' default='${whiteItem.item}'/></option> -->
                                        <option value="${whiteItem.item}">${whiteItem.item}</option>
                                    </#list>
                                </select>
                            </div>
                            <br/>
                            <div class="input-group">
                                <span class="input-group-addon col-1"><@msg code='transfer.金额' default='金额'/></span>
                                <input id="cashAmt" name="cashAmt" type="number" class="form-control" placeholder="<@msg code='transfer.转帐金额' default='转帐金额'/>" />
                                <#-- <span class="input-group-addon"><@msg code='transfer.元' default='元'/></span> -->
                            </div>
                            <br/>
                            <div class="input-group">
                                <button id="transferBtn" name="transferBtn" type="button" class="btn btn-primary"><@msg code='transfer.转帐' default='转帐'/></button>
                                <a class="btn btn-info cash" style=" margin-left: 16px;" title="<@msg code='transfer.转帐白名单' default='转帐白名单'/>"
									href="${ctx}/secrity/transferWhiteList" ><@msg code='transfer.转帐白名单' default='转帐白名单'/></a>
                            </div>
                            <br/>
                        </form>
                    </div>
                </div>
            </div>
            <!-- Main content end -->
        </div>
	</div>
</#assign>

<#assign htmlJS>
 
  $(function () { $('#myModal1').on('hide.bs.modal', function () {
       		$("#pwd").val('');
       		$("#gaCode").val('');
       		$('#transferBtn').removeAttr('disabled');
      })
    });
    
  var reloadAfterAlert=false; 
  	
  $(function() {
	 	var secretState = "${Session["secretState"]}";
    	var role = "${Session["userRole"]}";
        if("wd" == role && "0" == secretState ){
           alert("<@msg code='transfer.请先设置安全设置' default='请先设置安全设置'/>");
           $.jumpTo("${ctx}/secrity/secritySetting");
        }
        if("su" != role){
			if("wd" != role){
				alert("<@msg code='transfer.仅代付用户可以查看该页面' default='仅代付用户可以查看该页面'/>");
				$.jumpTo("${ctx}/index");
			}
		}
		
		$('#whiteItem').on('change', function(){
			setAccInfoByWhiteItem();
		});		
  });
  
  function setAccInfoByWhiteItem(){
  		var whiteItem= $('#whiteItem').val();
  		if (!(""===whiteItem)){
  			var items = whiteItem.split("|");
  			$('#accName').val(items[0]);
  			$('#accNo').val(items[1]);
  		}
  }
  
  function clearInputs(){
	    $('#whiteItem').val('');
	    $('#cashAmt').val('0');
		reloadAfterAlert=true;
  }

  $("#accName").validatebox({
				required: true,
				validType: 'isSelectNotNULL'
  });
  $("#accNo").validatebox({
				required: true,
				validType: ['number','ajaxCheck["${ctx}/preValid.do", "accNo"]']
  });
 
  $("#bankCd").validatebox({
				required: true,
				validType: ['number','isSelectNotNULL']
  });
  
  <#if whiteListType == 2 >
	  $("#gaCode").validatebox({
					required: true,
					validType: ['number','isSelectNotNULL']
	  });
  </#if>
  
    $(function () { $('#alertDialog').on('show.bs.modal', function () {
       		enableTransferBtn(false);
       		$('#operation480').modal('hide');
      })
    });
    
    $(function () { $('#alertDialog').on('hide.bs.modal', function () {
       		enableTransferBtn(true);
       		if (reloadAfterAlert) {
       			window.location.reload();
       		}
      })
    });
    
    $(function () { $('#transferBtn').off().on('click', function () {
       var whiteItem = $('#whiteItem option:selected').val();
       enableTransferBtn(false);
       $('#operation480').modal('show');
       if($('#cashAmt').val()==''){
	         $('#alertContent').html("<@msg code='transfer.转帐金额不能为空' default='转帐金额不能为空'/>");
			 $('#alertDialog').modal('show');
	    }else if ("" == whiteItem || whiteItem == null) {
	             $('#alertContent').html("<@msg code='transfer.请选商户号' default='请选商户号'/>");
				 $('#alertDialog').modal('show');
	    }else{
	    	<#-- comfirm(); -->
	    	$('#myModal1').modal('show');
	    	disableGaCheck();
	    	$('#operation480').modal('toggle');
	    }       		
      })
    });
 
  function alertDialog(msg){
	$('#alertContent').html(msg);
	$('#alertDialog').modal('show');
  } 
  
  function refreshPage(){
      location.reload();
  }

	$("#comfirmOK").click(function(){
	　　var pwd = $("#pwd").val();　
		if($('#pwd').val()==''){
		         alert("<@msg code='transfer.密码不能为空' default='密码不能为空'/>");
		         $('#transferBtn').removeAttr('disabled');
			     return;
	    }
	    if($('#gaCode').val()==''){
		         alert("<@msg code='transfer.谷歌验证码不能为空' default='谷歌验证码不能为空'/>");
		         $('#transferBtn').removeAttr('disabled');
			     return;
	    }
	    
	    $("#secretHash").val(secretHash($("#pwd").val(),$("#randv").val()));
	    comfirm();
	});
	
	function secretHash(passwd, validCode){
        var pwdmd5 =  CryptoJS.MD5((passwd+"PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP").substring(0, 50));
        return CryptoJS.MD5(validCode+"+PPAY_2018_MER+"+pwdmd5);
    }
	  
	function checkValid() {
		var accNo = $("#accNo").val();
		var url = "${ctx}/secrity/preValid?preValidData=" + accNo;
		$.ajax({
				url: url,
				async: false,
				success: function(data) {
					var dataObj = parseJson(data);
					if (dataObj === null) {
				    	alert ("<@msg code='transfer.系统异常！' default='系统异常！'/>");
				    	return;					    
					}
					var lst = dataObj.respData.list;
					if (lst[0] === 'false') {
						$('#alertContent').html("<@msg code='transfer.该银行卡' default='该银行卡'/>：" +'<font color="red" >' + lst[1].slice(0,-1) + '</font>' +"<@msg code='transfer.不合法' default='不合法'/>");
					 	$('#alertDialog').modal('show');
						return;
					}
				},
			    error:function(){
			    	alert("<@msg code='transfer.系统异常，请联系管理员！' default='系统异常，请联系管理员！'/>");
			    	return  false;
			    }
			}); 
	}
	
	function enableTransferBtn(enabled){
		$("#transferBtn").attr("disabled", "disabled");
	    if(enabled){
			$("#transferBtn").removeAttr("disabled");
	    }
	}
	
	function comfirm(){
		var cashAmt = $("#cashAmt").val();
		var whiteItem = $('#whiteItem option:selected').val();
	    var postData= $("#formTransfer").serialize();
	 	$('#myModal1').modal('hide');
	 	enableGaCheck();
	    clearInputs();
		var postUrl = "${ctx}/transfer/submitTransfer.do";
		$.ajax({
			url: postUrl,
			type: 'POST',
			data: postData,
			async: true,
			success: function(data) {
				var dataObj = parseJson(data); <#-- parseJson() is in mer.js -->
				if (dataObj === null) {
			    	alert ("<@msg code='transfer.系统异常！' default='系统异常！'/>");
			    	return;					    
				}
				var code = dataObj.respCode;
				var msg=  dataObj.respMsg;
				clearInputs();
	    		$('#alertContent').html(msg);
			 	$('#alertDialog').modal('show');
				return;
			},
		    error:function(){
				clearInputs();
		    	$('#alertContent').html("<@msg code='transfer.连线异常，请到取现查询页面确认交易结果。' default='连线异常，请到取现查询页面确认交易结果。'/>");
		    	$('#alertDialog').modal('show');
		    	return  false;
		    }
		});
	}
	
	$(function() {
	 	showByCurrCd();
	 	$('#currCd').on('change', function(){
			showByCurrCd();
		});

	 });
  
	 function showByCurrCd(){
	 
		$("#CNY_totalBalance").hide();
		$("#CNY_availableBalance").hide();
		$("#CNY_frozenT1Balance").hide();
		$("#CNY_frozenBalance").hide();
		
		$("#VND_totalBalance").hide();
		$("#VND_availableBalance").hide();
		$("#VND_frozenT1Balance").hide();
		$("#VND_frozenBalance").hide();
		
		$("#THB_totalBalance").hide();
		$("#THB_availableBalance").hide();
		$("#THB_frozenT1Balance").hide();
		$("#THB_frozenBalance").hide();

	    var currCd = $('#currCd option:selected').val();
	    $("#currencyCode").val(currCd);
	    
	    if ("156"===currCd){
			$("#CNY_totalBalance").show();
			$("#CNY_availableBalance").show();
			$("#CNY_frozenT1Balance").show();
			$("#CNY_frozenBalance").show();
		}
		else if ("704"===currCd){
			$("#VND_totalBalance").show();
			$("#VND_availableBalance").show();
			$("#VND_frozenT1Balance").show();
			$("#VND_frozenBalance").show();
		}
		else if ("764"===currCd){
			$("#THB_totalBalance").show();
			$("#THB_availableBalance").show();
			$("#THB_frozenT1Balance").show();
			$("#THB_frozenBalance").show();
		}
	 }
	 
</#assign>
<#include "/common/layout.ftl" />