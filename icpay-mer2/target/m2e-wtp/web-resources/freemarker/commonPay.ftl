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
        
        .input-group {
		    margin-bottom: 15px
		}
		
		.Wdate {
		    height: 28px;
		}
		
	</style>		
</#assign>

<#assign htmlContent>
	<ol class="breadcrumb">
		<li><a href="#"><@msg code='commonPay.商户服务' default='商户服务'/></a></li>
		<li class="active"><@msg code='commonPay.充值' default='充值'/></li>
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
	                            <h4 class="modal-title" id="alertTitle"><@msg code='commonPay.提示' default='提示'/></h4>
	                        </div>
	                        <div class="modal-body" id="alertContent"><@msg code='commonPay.讯息' default='讯息'/></div>
	                        <div class="modal-footer">
	                            <button type="button" class="btn btn-primary"
	                                data-dismiss="modal" data-keyboard="false"><@msg code='commonPay.确定' default='确定'/></button>
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
	                        <td><div class="loader"></div></td><td><div class="modal-body" id="alertContent"><@msg code='commonPay.交易进行中，请稍后。。。' default='交易进行中，请稍后。。。'/></div></td>
                            </tr></table>
	                    </div>
	                </div>
	            </div>
	
	            <#-- Main content -->
	            
                <div class="container">
                        <div class="row clearfix">
                
                            <div id="formBlock" class="col-md-10 column">
                            		
                                    <form id="formPay" action="${ctx}/payment/commonPaySubmit.do" method="POST" target="_blank" class="form-horizontal" role="form" enctype="multipart/form-data">
										<div class="input-group">
										<h3><@msg code='commonPay.充值操作' default='充值操作'/></h3>
										</div>
										<br/>
										<div id="block_offPayBank" style="background-color:#F7F8FA;">
										<@msg code='commonPay.收款账户信息' default='收款账户信息'/>  <span style="color:#FF6600;"><@msg code='commonPay.＊汇款前请再次刷新页面，确认当前卡号＊' default='＊汇款前请再次刷新页面，确认当前卡号＊'/></span>
										<button id ="refreshPageBtn" type="button" class="btn btn-default btn-sm" onclick="refreshPage()" ><@msg code='commonPay.刷新' default='刷新'/></button><br/><br/>
										<@msg code='commonPay.户名：' default='户名：'/><span id="offPayAccName" style="color:#FF6600;"></span>&nbsp&nbsp
										<button id ="copyBtn2" type="button" class="btn btn-default btn-sm" onclick="copyToClipboard('#offPayAccName')" ><@msg code='commonPay.复制' default='复制'/></button><br/><br/>
										<@msg code='commonPay.银行卡号：' default='银行卡号：'/><span id="offPayAccNo" style="color:#FF6600;"></span>&nbsp&nbsp
										<button id ="copyBtn1" type="button" class="btn btn-default btn-sm" onclick="copyToClipboard('#offPayAccNo')" ><@msg code='commonPay.复制' default='复制'/></button><br/><br/>
										<@msg code='commonPay.银行名称：' default='银行名称：'/><span id="offPayBankName" style="color:#FF6600;"></span><br/><br/>
										<@msg code='commonPay.开户行：' default='开户行：'/><span id="offPayAccountBankName" style="color:#FF6600;"></span><br/><br/>
										</div><br/>
										
										<div class="input-group">
                                            <span class="input-group-addon"><@msg code='commonPay.币别' default='币别'/>&nbsp&nbsp</span>
                                            <select id="currCd" name="currCd" class="form-control selectpicker" placeholder="<@msg code='commonPay.请选择' default='请选择'/>" >
													<option value=""><@msg code='commonPay.(请选择)' default='(请选择)'/></option>
													<option value="156"><@msg code='fundMng.人民币' default='人民币'/></option>
													<option value="704"><@msg code='fundMng.越南盾' default='越南盾'/></option>
													<option value="764"><@msg code='fundMng.泰铢' default='泰铢'/></option>
											</select>
                                        </div>
                                        <div class="input-group">
                                            <span class="input-group-addon"><@msg code='commonPay.支付方式' default='支付方式'/>&nbsp&nbsp</span>
                                            <select id="payType" name="payType" class="form-control selectpicker" placeholder="<@msg code='commonPay.请选择支付方式' default='请选择支付方式'/>" >
                                                <option value=""><@msg code='commonPay.(请选择)' default='(请选择)'/></option>
                                            </select>
                                        </div>
                                        
                                        <div class="input-group">
                                            <span class="input-group-addon"><@msg code='commonPay.充值金额' default='充值金额'/>&nbsp&nbsp</span>
                                            <#--  <input id="txnAmt" name="txnAmt" type="text" class="form-control" placeholder="充值金额(以元为单位)" data-options="precision:2,groupSeparator: ',',decimalSeparator: '.'"  /> -->
                                            <input id="txnAmt" name="txnAmt" type="text" class="form-control" <#-- placeholder="<@msg code='commonPay.充值金额(以元为单位)' default='充值金额(以元为单位)'/>" -->  />
                                            <#-- <span class="input-group-addon"><@msg code='commonPay.元' default='元'/></span> -->
                                        </div>
                                        
										<#--  
                                        <div class="input-group">
                                            <span class="input-group-addon">订单编号&nbsp&nbsp</span>
                                            <input id="orderId" name="orderId" type="text" readOnly value="${orderId}" class="form-control" />
                                            <span class="input-group-addon">#</span>
                                        </div>
                                        <br/>
                                        -->
                                        
                                        <#--  
                                        <div class="input-group" id="block_bankNum">
                                            <span class="input-group-addon">银行编号&nbsp&nbsp</span>
                                            <select id="bankNum" name="bankNum" class="form-control selectpicker" placeholder="请选择银行" >
                                                <option value="">(请选择)</option>
                                                <#list merBanks as item>
                                                     <option value="${item.bankNum}">${item.bankName}</option>
                                                </#list>
                                            </select>
                                        </div>
                                        -->
                                        <div class="input-group" id="block_bankNum">
                                            <span class="input-group-addon"><@msg code='commonPay.银行编号' default='银行编号'/>&nbsp&nbsp</span>
                                            <select id="bankNum" name="bankNum" class="form-control selectpicker" placeholder="<@msg code='commonPay.请选择银行' default='请选择银行'/>" >
                                                <option value=""><@msg code='commonPay.(请选择)' default='(请选择)'/></option>
                                            </select>
                                        </div>
                                        
										<div class="input-group" id="block_AccGroup">
                                            <span class="input-group-addon"><@msg code='commonPay.自充帐户' default='自充帐户'/>&nbsp&nbsp</span>
                                            <select id="whiteItem" name="whiteItem" class="selectpicker form-control" placeholder="<@msg code='commonPay.请由常用帐户列表选择自充帐户' default='请由常用帐户列表选择自充帐户'/>" >
                                                <option value=""><@msg code='commonPay.(请选择)' default='(请选择)'/></option>
                                                <#list cardList as listItem>
                                                    <option value="${listItem.item}">${listItem.item}</option>
                                                </#list>
                                            </select>
                                        </div>
                                        
										<div class="input-group" id="block_AccGroup2">
                                            <span class="input-group-addon"><@msg code='commonPay.自充帐户' default='自充帐户'/>&nbsp&nbsp</span>
                                            <select id="whiteItem2" name="whiteItem2" class="selectpicker form-control" placeholder="<@msg code='commonPay.请由白名单选择充值帐户' default='请由白名单选择充值帐户'/>" >
                                                <option value=""><@msg code='commonPay.(请选择)' default='(请选择)'/></option>
                                                <#list cardList as listItem>
                                                    <option value="${listItem.item}">${listItem.item}</option>
                                                </#list>
                                                <#list cardList2 as listItem2>
                                                    <option value="${listItem2.item}">${listItem2.item}</option>
                                                </#list>
                                            </select>
                                        </div>
                                        
                                        <div class="input-group" id="block_AccGroup0191">
                                            <span class="input-group-addon">自充帐户&nbsp&nbsp</span>
                                            <select id="whiteItem0191" name="whiteItem0191" class="selectpicker form-control" >
                                                <option value="">(请选择)</option>
                                                <#list cardList0191 as listItem0191>
                                                    <option value="${listItem0191.item}">${listItem0191.item}</option>
                                                </#list>
                                            </select>
                                        </div>
                                        
                                        <div class="input-group" id="block_accNum">
                                            <span class="input-group-addon"><@msg code='commonPay.银行卡号' default='银行卡号'/>&nbsp&nbsp</span>
                                            <input id="accNum" name="accNum" type="text" value="" class="form-control" />
                                            <span class="input-group-addon">#</span>
                                        </div>
                                        
                                        <div class="input-group" id="block_accName">
                                            <span class="input-group-addon"><@msg code='commonPay.帐户名称' default='帐户名称'/>&nbsp&nbsp</span>
                                            <input id="accName" name="accName" type="text" value="" class="form-control" />
                                            <span class="input-group-addon">#</span>
                                        </div>
                                        <div class="input-group" id="block_actualTxnDate">
                                            <span class="input-group-addon"><@msg code='commonPay.打款日期' default='打款日期'/>&nbsp&nbsp</span>
                                            <input id="actualTxnDate" name="actualTxnDate" type="text" value="${today}" class="form-control Wdate" 
                                            	onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" <#--  style="height: 34px; float: left;" -->/>
                                            <span class="input-group-addon">#</span>
                                        </div> 
                                        
                                        <div class="input-group" id="block_uploadImage">
                                            <span class="input-group-addon"><@msg code='commonPay.上传转账凭证' default='上传转账凭证'/>&nbsp&nbsp</span>
                                            <input type="file" id="uploadImage" name="uploadImage" accept="image/*">
                                        </div>
                                        
                                        <div class="input-group">
                                            <button id="payBtn" name="payBtn" type="button" class="btn btn-primary"><@msg code='commonPay.充值' default='充值'/></button>
											<a id="paymentCardList" class="btn btn-info cash" style=" margin-left: 16px;" title="<@msg code='commonPay.常用充值账户' default='常用充值账户'/>"
												href="${ctx}/payment/paymentCardList"><@msg code='commonPay.常用充值账户' default='常用自充卡'/></a>
                                        </div>
                                        <br/>
                                    </form>                            		
                            </div>
                            <#-- col-md-8 column end -->
                        </div>
                        <#-- row clearfix -->
                </div>
	            <#-- Main content end -->
	
	        </div>
	        
	</div>

</#assign>

<#assign htmlJS>

		var lastSelectedItem=null;
		$('#currCd').on('change', function(){
			var selectedItem= $('#currCd').val();
			if (isEmpty(selectedItem)){
				$("#payType").clearOptions(false);
			}else {
				if (lastSelectedItem != selectedItem){
					onSelectedCurrCdChanged(selectedItem);
				}
			}
			lastSelectedItem = selectedItem;
		});
		
		
		function onSelectedCurrCdChanged(selectedCh) {
			$("#payType").clearOptions(false);
			var postUrl="${ctx}/payment/getPayTypeByCurrCd.do?currCd="+selectedCh;
			$.ajax({
				type : "GET",
				url : postUrl ,
				async: true,
				cache: true,
				success : function(data) {
					var res = parseJson(data);
					if (res.respCode != "00"){
						alert("操作失败: "+res.respMsg);
						return false;
					}
					var list = res.respData.payTypes;
					if (! isEmpty(list)) {
						for (var i = 0, len = list.length; i < len; i++) {
						  var item = list[i];
						  $('#payType').append('<option value="' + item.code + '">' + item.desc + '</option>');
						}
					}					
					
				},
				error:function(){
			    	alert("系统异常，请联系管理员！");
			    	return  false;
			    }
			});
		}


	
    $(function() {
		showByPayType();
		
		$('#currCd').on('change', function(){
			$("#payType").val("");
			getMerBanksList();
			showByPayType();
		});
		
		$('#payType').on('change', function(){
			getMerBanksList();
			showByPayType();
		});
		
		$('#whiteItem').on('change', function(){
		  	var whiteItem= $('#whiteItem').val();
			setAccInfoByWhiteItem(whiteItem);
		});			
		
		$('#whiteItem2').on('change', function(){
		  	var whiteItem= $('#whiteItem2').val();
			setAccInfoByWhiteItem(whiteItem);
		});	
		
		$('#whiteItem0191').on('change', function(){
		  	var whiteItem= $('#whiteItem0191').val();
			setAccInfoByWhiteItem(whiteItem);
		});			
		
    });
    
    function getMerBanksList(){
		var payType = $('#payType option:selected').val();
		var currCd = $('#currCd').val();
		if (!(("0121"===payType) || ("0192"===payType)  || ("0193"===payType))) return;
		var postUrl="${ctx}/payment/merBanks.do?payType="+payType + "&currCd=" + currCd;
		$.ajax({
				type : "GET",
				url : postUrl,
				async: false,
				cache:false,
				success : function(data) {
					var res = parseJson(data);
					fillBanksNumSel(res);
					return;
				},
				error:function(){
			    	//alert("系统异常，请联系管理员！");
			    	return  false;
			    }
		});       
    }
    
    function fillBanksNumSel(data){
    	var selObj = $('#bankNum');
    	if (isEmpty(data)||isEmpty(data.respData)||isEmpty(data.respData.merBanks)){
    		selObj.clearOptions(false);
    	}
    	else{
    		var list = data.respData.merBanks;
    	    for (var i = 0, len = list.length; i < len; i++) {
				var item = list[i];
				var val = item.bankNum;
				var desc  = item.bankName+"("+item.bankNum+")";
				selObj.addOption(val,desc);    	    	
    	    }
    	}
    }
    
    function showByPayType(){
	    var payType = $('#payType option:selected').val();
		$("#block_AccGroup").hide();
		$("#block_AccGroup2").hide();
		$("#block_AccGroup0191").hide();
	    $("#block_accNum").hide();
	    $("#block_accName").hide();
	    $("#block_actualTxnDate").hide();
	    $("#block_bankNum").hide();
	    $("#block_offPayBank").hide();
	    $("#block_uploadImage").hide();
	    $("#paymentCardList").show();
	    $("#accNum").val("").prop("readonly", false);
		$("#accName").val("").prop("readonly", false);
	    
	    if ("0122"===payType){
			$("#block_accNum").show();
		}
		else if ("0121"===payType){
			$("#block_accNum").show();
			$("#block_accName").show();
			$("#block_bankNum").show();
		}
		else if ("0191"===payType){
			$("#block_AccGroup0191").show();
			$("#block_accNum").show();
			$("#block_accName").show();
			$("#block_actualTxnDate").show();
			$("#paymentCardList").hide();
			$("#accNum").prop("readonly", true);
			$("#accName").prop("readonly", true);
			getOffPayBank();
		}
		else if (("0192"===payType) || ("0193"===payType) ){
			$("#block_AccGroup2").show();
			$("#block_bankNum").show();
			$("#block_accNum").show();
			$("#block_accName").show();
		}
    }
    
	function getOffPayBank(){
		var postUrl="${ctx}/payment/offPayBank.do";
		$.ajax({
				type : "GET",
				url : postUrl,
				async: false,
				cache:false,
				success : function(data) {
					var res = parseJson(data);
					var offPayBankBean = res.respData.offPayBank;
					$('#offPayAccNo').html(offPayBankBean.accNo);
					$('#offPayBankName').html(offPayBankBean.bankName);
					$('#offPayAccName').html(offPayBankBean.accName);
					$('#offPayAccountBankName').html(offPayBankBean.accountBankName);
					$("#block_offPayBank").show();
					$("#block_uploadImage").show();
					return;
				},
				error:function(){
			    	//alert("系统异常，请联系管理员！");
			    	return  false;
			    }
		});       
    }
    
    function copyToClipboard(element) {
		var $temp = $("<input>");
	  	$("body").append($temp);
	  	$temp.val($(element).text()).select();
	  	document.execCommand("copy");
	  	$temp.remove();
    }
    
   function setAccInfoByWhiteItem(whiteItem){
  		if (!isEmpty(whiteItem)){
			try {
	  			var items = whiteItem.split("|");
	  			$('#accName').val(items[0]);
	  			$('#accNum').val(items[1]);
			}
			catch(err) {
				console.log(err);
			}  			
  		}
   }    
    
    <#--  
	function nextOrderId() {
		var url = "${ctx}/newOrderNum.do";
		$.ajax({
				url: url,
				async: true,
				success: function(data) {
                    $("#orderId").val(eval('(' + data + ')'));
				},
			    error:function(){
			    	$("#orderId").val("(无法获取订单号)");
                    return false;
			    }
			}); 
	}
	-->

    function alertDialog(msg){
        $('#alertContent').html(msg);
        $('#alertDialog').modal('show');
        <#--  $('#alertDialog').modal({backdrop: 'static', keyboard: false}); --> 
    }

    function refreshPage(){
        location.reload();
    }
    
    $(function () { $('#payBtn').on('click', function () {
    		<#--  nextOrderId(); -->
    		$('#formPay').submit();
    		$('#txnAmt').val('');
    		<#--  
    		var postUrl="${ctx}/payment/commonPaySubmit.do";
    		$.ajax({
					type : "POST",
					url : postUrl,
					async: true,
					cache:false,
					data: $("#formPay").serialize(),
					success : function(data) {
						return;
					},
					error:function(){
				    	alert("系统异常，请联系管理员！");
				    	return  false;
				    }
			});
			-->
      })
    });
    
    <#--
    //OK
	$('#payType').on('change', function(){
	   var selected = $('.selectpicker option:selected').val();
	   alert(selected);
	});
    
    $(function () { $("#payType").on('changed.bs.select', function (e, clickedIndex, newValue, oldValue) {
		    console.log(this.value, clickedIndex, newValue, oldValue);
	    	var payType=newValue;
	    	if ("22"===payType)
	    		$("#block_accNum").show();
	    	else	
	    		$("#block_accNum").hide();	    
      })
    });
    
    -->

</#assign>
<#include "/common/layout.ftl" />
