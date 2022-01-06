<#include "/common/macro.ftl">
<#assign ctx = request.contextPath>

<#assign htmlHead>
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/js/jquery-easy-ui/themes/gray/easyui.css"></link>
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/js/jquery-easy-ui/themes/icon.css"></link>
	<#-- <script src="${ctx}/resources/js/jquery.min.js"> </script>-->
	<script type="text/javascript" src="${ctx}/resources/js/jquery-easy-ui/jquery.min.js"></script>
	<script src="${ctx}/resources/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/jquery-easy-ui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/jquery-easy-ui/locale/easyui-lang-zh_CN.js"></script>
	<script src="${ctx}/resources/js/jquery.form.js"></script>
	<script src="${ctx}/resources/js/jquery.validate.min.js"></script>
	<script src="${ctx}/resources/js/toastr.min.js"></script>
	<script src="${ctx}/resources/js/mer.js?v=${strNowMm!''}"></script>
	<script type="text/javascript" src="${ctx}/resources/js/validate.js"></script>

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
		<li><a href="#"><@msg code='commonPayResult.商户服务' default='商户服务'/></a></li>
		<li class="active"><@msg code='commonPayResult.充值' default='充值'/></li>
	</ol>

	<div class="container body">
	        <div class="main_container">
	
	            <#-- Alert dialog -->
	            <div class="modal fade" id="alertDialog" tabindex="-1" role="dialog" data-backdrop="static" 
	                aria-labelledby="myModalLabel" aria-hidden="true" style="z-index: 999999 !important;">
	                <div class="modal-dialog" style="width: 320px;">
	                    <div class="modal-content" style="width: 320px;">
	                        <div class="modal-header">
	                            <button type="button" class="close" data-dismiss="modal"
	                                aria-hidden="true" backdrop="static">&times;</button>
	                            <h4 class="modal-title" id="alertTitle"><@msg code='commonPayResult.提示' default='提示'/></h4>
	                        </div>
	                        <div class="modal-body" id="alertContent"><@msg code='commonPayResult.讯息' default='讯息'/></div>
	                        <div class="modal-footer">
	                            <button type="button" class="btn btn-primary"
	                                data-dismiss="modal" data-keyboard="false"><@msg code='commonPayResult.确定' default='确定'/></button>
	                        </div>
	                    </div>
	                </div>
	            </div>
	
	            <#-- Busy dialog -->
	            <div class="modal fade" id="busyDialog" tabindex="-1" role="dialog"
	                aria-labelledby="myModalLabel" aria-hidden="true"
	                data-backdrop="static" data-keyboard="false">
	                <div class="modal-dialog" style="width: 640px;">
	                    <div class="modal-content" style="width: 640px;">
	                        <table><tr>
	                        <td><div class="loader"></div></td><td><div class="modal-body" id="alertContent"><@msg code='commonPayResult.交易进行中，请稍后。。。' default='交易进行中，请稍后。。。'/></div></td>
                            </tr></table>
	                    </div>
	                </div>
	            </div>
	
	            <#-- Main content -->
	            
                <div class="container">
                        <div class="row clearfix">
                            <div class="col-md-6 column">
                                <div class="panel ${panelClass!'panel-danger'}">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">
                                            <@msg code='commonPayResult.支付响应' default='支付响应'/>
                                        </h3>
                                    </div>
                                    <div class="panel-body">
                                            <ul class="list-group mb-3" style="border: 0px">
                                                    <li class="list-group-item" style="border: 0px">
                                                            <div>
                                                            <h6 class="tile-stats"><@msg code='commonPayResult.响应代码' default='响应代码'/></h6>
                                                            </div>
                                                            <span class="count_top"><font color="#666699"><strong>${payResult.respCode}</strong></font></span>
                                                    </li>
                                                                                            
                                                    <li class="list-group-item" style="border: 0px">
                                                            <div>
                                                            <h6 class="tile-stats"><@msg code='commonPayResult.响应讯息' default='响应讯息'/></h6>
                                                            </div>
                                                            <span class="count_top"><font color="#666699"><strong>${payResult.respMsg}</strong></font></span>
                                                    </li>
                
                                                    <#--  <#if (isOk) > -->
                                                        <li class="list-group-item" style="border: 0px">
                                                                <div>
                                                                <h6 class="tile-stats"><@msg code='commonPayResult.商户号' default='商户号'/></h6>
                                                                </div>
                                                                <span class="count_top"><font color="#666699"><strong>${payResult.merId!""}</strong></font></span>
                                                        </li>
                                                        <li class="list-group-item" style="border: 0px">
                                                                <div>
                                                                <h6 class="tile-stats"><@msg code='commonPayResult.币别' default='币别'/></h6>
                                                                </div>
                                                                <span class="count_top"><font color="#666699"><strong>${payResult.currencyCode!""}</strong></font></span>
                                                        </li>
                                                        <li class="list-group-item" style="border: 0px">
                                                                <div>
                                                                <h6 class="tile-stats"><@msg code='commonPayResult.交易金额' default='交易金额'/></h6>
                                                                </div>
                                                                <span class="count_top"><font color="#666699"><strong>${payResult.txnAmt!""}</strong> <#-- <@msg code='commonPayResult.元' default='元'/>--></font></span>
                                                        </li>
                                                        <li class="list-group-item" style="border: 0px">
                                                                <div>
                                                                <h6 class="tile-stats"><@msg code='commonPayResult.订单日期' default='订单日期'/></h6>
                                                                </div>
                                                                <span class="count_top"><font color="#666699"><strong>${payResult.orderDate!""}</strong></font></span>
                                                        </li>
                                                        <li class="list-group-item" style="border: 0px">
                                                                <div>
                                                                <h6 class="tile-stats"><@msg code='commonPayResult.订单编号' default='订单编号'/></h6>
                                                                </div>
                                                                <span class="count_top"><font color="#666699"><strong>${payResult.orderId!""}</strong></font></span>
                                                        </li>
                                                    <#--  </#if> -->

                                            </ul>
                                    </div>
                                    <#--
                                    <div class="panel-footer">
                                    </div> -->
                                </div>
                            </div> <#-- <div class="col-md-6 column"> -->
                            <#if (hasQrcode) >
                            	<div class="col-md-6 column">
                                	<div class="panel panel-success">
                                        <div class="panel-heading">
	                                        <h3 class="panel-title">
	                                            <@msg code='commonPayResult.支付请求成功，请扫描二维码' default='支付请求成功，请扫描二维码'/>
	                                        </h3>
	                                    </div>
										<div class="panel-body">
											<img src="${ctx}/qrcode?content=${qrContent!'#'}" style="width: 256px; height: 256px;" alt="<@msg code='commonPayResult.无法显示二维码' default='无法显示二维码'/>"/>
										</div>
										<div class="panel-footer">
										    <@msg code='commonPayResult.或点击' default='或点击'/>&nbsp<a calss="btn btn-success" href="${qrContentUrl!'#'}" role="button"><@msg code='commonPayResult.【支付网址】' default='【支付网址】'/></a>&nbsp
										</div>
									</div>
                            	</div>
							</#if>
                        </div>
                        <#-- row clearfix -->
                </div>
	            <#-- Main content end -->
	
	        </div>
	        
	</div>

</#assign>

<#assign htmlJS>

    $(function() {

    });
    
	function nextOrderId() {
		var url = "${ctx}/newOrderNum.do";
		$.ajax({
				url: url,
				async: true,
				success: function(data) {
                    $("#orderId").value(data);
				},
			    error:function(){
			    	$("#orderId").value("<@msg code='commonPayResult.(无法获取订单号)' default='(无法获取订单号)'/>");
                    return false;
			    }
			}); 
	}     

    function alertDialog(msg){
        $('#alertContent').html(msg);
        $('#alertDialog').modal('show');
        <#--  $('#alertDialog').modal({backdrop: 'static', keyboard: false}); --> 
    } 

    function refreshPage(){
        location.reload();
    }


    $(function () { $('#alertDialog').on('show.bs.modal', function () {
       		enableWithdrawBtn(false);
       		$('#busyDialog').modal('hide');
      })
    });
    
    $(function () { $('#alertDialog').on('hide.bs.modal', function () {
       		enableWithdrawBtn(true);
      })
    });
    
    $(function () { $('#payBtn').on('click', function () {
    		nextOrderId();
      })
    });
    
    var href="${qrContentUrl!''}";
    if(href!=""){
    	<#--检查交易类型是否为H5 -->
    	var payType = "${payType}";
    	if(payType.indexOf("014")>-1){
			document.location.replace(href);
    	}
		
	}
</#assign>
<#include "/common/layout.ftl" />
