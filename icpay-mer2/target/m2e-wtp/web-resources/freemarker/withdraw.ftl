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
		<li><a href="#"><@msg code='withdraw.商户服务' default='商户服务'/></a></li>
		<li class="active"><@msg code='withdraw.取现' default='取现'/></li>
	</ol>

	<div class="container body">
	        <div class="main_container">

	            <!-- Alert dialog -->
	            <div class="modal fade" id="alertDialog" tabindex="-1" role="dialog" data-backdrop="static"
	                aria-labelledby="myModalLabel" aria-hidden="true" style="z-index: 999999 !important;">
	                <div class="modal-dialog" style="width: 520px;">
	                    <div class="modal-content" style="width: 520px;">
	                        <div class="modal-header">
	                            <button type="button" class="close" data-dismiss="modal"
	                                aria-hidden="true" backdrop="static">&times;</button>
	                            <h4 class="modal-title" id="alertTitle"><@msg code='withdraw.提示' default='提示'/></h4>
	                        </div>
	                        <div class="modal-body" id="alertContent"><@msg code='withdraw.在这里添加一些文本' default='在这里添加一些文本'/></div>
	                        <div class="modal-footer">
	                            <button type="button" class="btn btn-primary"
	                                data-dismiss="modal" data-keyboard="false"><@msg code='withdraw.确定' default='确定'/></button>
	                        </div>
	                    </div>
	                </div>
	            </div>

	            <!-- demo dialog -->
	           <#--   <div class="modal fade" id="myModal1" tabindex="-1" role="dialog"
	                aria-labelledby="myModalLabel" aria-hidden="true" backdrop='false'>
	                <div class="modal-dialog">
	                    <div class="modal-content" style="width: 400px;">
	                        <div class="modal-header">
	                            <button type="button" class="close" data-dismiss="modal"
	                                aria-hidden="true" backdrop='false'>×</button>
	                            <h4 class="modal-title" id="myModalLabel">取现确认</h4>
	                        </div>
	                        <input type="hidden" id="secretHash" name="secretHash" value="">
				         	<div class="modal-body1" id="alertMsg"></div>
				            <div class="modal-body1">请输入密码:</div>
				            <div class="modal-body1">
				            <input type="password" id="pwd" name="pwd" class="modal-body1" style="width: 150px;" /></div>
	                        <div class="modal-body1"></div>
	                        <div class="modal-footer">
	                            <button type="button" id="comfirmOK" class="btn btn-primary">确认</button>
	                        </div>
	                    </div>
	                </div>
	            </div>-->

	            <div class="modal fade" id="myModal2" tabindex="-1" role="dialog"
	                aria-labelledby="myModalLabel" aria-hidden="true" backdrop='false'>
	                <div class="modal-dialog">
	                    <div class="modal-content" style="width: 400px;">
	                        <div class="modal-header">
	                            <button type="button" class="close" data-dismiss="modal"
	                                aria-hidden="true" backdrop='false'>×</button>
	                            <h4 class="modal-title" id="myModalLabel"><@msg code='withdraw.取现确认' default='取现确认'/></h4>
	                        </div>
	                        <input type="hidden" id="secretHash" name="secretHash" value="">
	                        <input type="hidden" id="cashAmtHid" name="cashAmt" value="">
	                        <input type="hidden" id="accNoHid" name="accNo" value="">
	                        <input type="hidden" id="accNameHid" name="accName" value="">
	                        <input type="hidden" id="bankCdHid" name="bankCd" value="">
	                        
				         	<#-- <div class="modal-body1" id="alertMsg"></div>
				            <div class="modal-body1">请输入密码:</div>
				            <div class="modal-body1">
				            <input type="password" id="pwd" name="pwd" class="modal-body1" style="width: 150px;" /></div> -->
				            <div class="modal-body1"><@msg code='withdraw.请输入谷歌验证' default='请输入谷歌验证'/>:</div>
				            <div class="modal-body1">
				            <input type="text" id="gaCode" name="gaCode" class="modal-body1" style="width: 150px;" maxLength="6" /></div>
	                        <div class="modal-body1"></div>
	                        <div class="modal-footer">
	                            <button type="button" id="comfirmOK" class="btn btn-primary"><@msg code='withdraw.确认' default='确认'/></button>
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
	                        <td><div class="loader"></div></td><td><div class="modal-body" id="alertContent"><@msg code='withdraw.交易进行中，请稍后。。。' default='交易进行中，请稍后。。。'/></div></td>
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
                                        <h3 class="panel-title">
                                            <@msg code='withdraw.帐户总览' default='帐户总览'/>
                                        </h3>
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
                                                            <h6 class="tile-stats"><@msg code='withdraw.帐户总额' default='帐户总额'/></h6>
                                                            </div>
					                                        <span class="count_top" id="CNY_totalBalance"><font color="#666699"><strong>${totalBalance_CNY}</strong><#--  <@msg code='withdraw.元' default='元'/>--></font></span>
					                                        <span class="count_top" id="VND_totalBalance"><font color="#666699"><strong>${totalBalance_VND}</strong><#--  <@msg code='withdraw.元' default='元'/>--></font></span>
					                                        <span class="count_top" id="THB_totalBalance"><font color="#666699"><strong>${totalBalance_THB}</strong><#--  <@msg code='withdraw.元' default='元'/>--></font></span>
                                                    </li>

                                                    <li class="list-group-item" style="border: 0px">
                                                            <div>
                                                            <#--<h6 class="tile-stats"><@msg code='withdraw.可取现金额' default='可取现金额'/></h6>-->
                                                            <h6 class="tile-stats"><@msg code='withdraw.可代付馀额' default='可代付馀额'/></h6>
                                                            </div>
					                                        <span class="count_top" id="CNY_availableBalance"><font color="#666699"><strong>${availableBalance_CNY}</strong><#--  <@msg code='withdraw.元' default='元'/>--></font></span>
					                                        <span class="count_top" id="VND_availableBalance"><font color="#666699"><strong>${availableBalance_VND}</strong><#--  <@msg code='withdraw.元' default='元'/>--></font></span>
					                                        <span class="count_top" id="THB_availableBalance"><font color="#666699"><strong>${availableBalance_THB}</strong><#--  <@msg code='withdraw.元' default='元'/>--></font></span>
                                                    </li>

                                                    <li class="list-group-item" style="border: 0px">
                                                            <div>
                                                            <#--<h6 class="tile-stats"><@msg code='withdraw.T1待结算金额' default='T1待结算金额'/></h6>-->
                                                            <h6 class="tile-stats"><@msg code='withdraw.T+1帐户馀额' default='T+1帐户馀额'/></h6>
                                                            </div>
					                                        <span class="count_top" id="CNY_frozenT1Balance"><font color="#666699"><strong>${frozenT1Balance_CNY}</strong><#--  <@msg code='withdraw.元' default='元'/>--></font></span>
					                                        <span class="count_top" id="VND_frozenT1Balance"><font color="#666699"><strong>${frozenT1Balance_VND}</strong><#--  <@msg code='withdraw.元' default='元'/>--></font></span>
					                                        <span class="count_top" id="THB_frozenT1Balance"><font color="#666699"><strong>${frozenT1Balance_THB}</strong><#--  <@msg code='withdraw.元' default='元'/>--></font></span>
                                                    </li>

                                                    <li class="list-group-item" style="border: 0px">
                                                            <div>
                                                            <h6 class="tile-stats"><@msg code='withdraw.预扣金额' default='预扣金额'/></h6>
                                                            </div>
					                                        <span class="count_top" id="CNY_frozenBalance"><font color="#666699"><strong>${frozenBalance_CNY}</strong><#--  <@msg code='withdraw.元' default='元'/>--></font></span>
					                                        <span class="count_top" id="VND_frozenBalance"><font color="#666699"><strong>${frozenBalance_VND}</strong><#--  <@msg code='withdraw.元' default='元'/>--></font></span>
					                                        <span class="count_top" id="THB_frozenBalance"><font color="#666699"><strong>${frozenBalance_THB}</strong><#--  <@msg code='withdraw.元' default='元'/>--></font></span>
                                                    </li>
                                            </ul>
                                    </div>

                                </div>
                            </div>


                            <div class="col-md-8 column">


                                    <form role="form" id="formWithdraw" action="" class="form-horizontal form-label-left formUpdate" >
                                    	<input type="hidden" id="currencyCode" name="currencyCode" value="">
                                    	<input type="hidden" id="randv" name="randv" value="${randv!'999999'}" />
                                    	<div class="input-group">
										<h3><@msg code='withdraw.取現操作' default='取現操作'/></h3>
										</div>
										<br/>
										
                                        <div class="input-group">
                                            <span class="input-group-addon col-1"><@msg code='withdraw.取款金额' default='取款金额'/>&nbsp&nbsp</span>
                                            <#--  <input id="txnAmt" name="txnAmt" type="text" class="form-control" placeholder="充值金额(以元为单位)" data-options="precision:2,groupSeparator: ',',decimalSeparator: '.'"  /> -->
                                            <input id="cashAmt" name="cashAmt" type="number" class="form-control" <#-- placeholder="<@msg code='withdraw.取款金额(以元为单位)' default='取款金额(以元为单位)'/>"--> />
                                            <#-- <span class="input-group-addon"><@msg code='withdraw.元' default='元'/></span> -->
                                        </div>
                                        <br/>
                                        <#-- 
                                        <div class="input-group">
                                            <span class="input-group-addon">订单编号&nbsp&nbsp</span>
                                            <input id="orderId" name="orderId" type="text" readOnly value="${orderId!''}" class="form-control" />
                                            <span class="input-group-addon">#</span>
                                        </div>
                                        <br/>
                                        -->

                                        <div class="input-group">
                                            <span class="input-group-addon"><@msg code='withdraw.交易类型' default='交易类型'/>&nbsp&nbsp</span>
                                             <select id="payType" name="payType" class="form-control selectpicker" placeholder="请选择支付方式" >
                                                <#list payTypes as payType>
                                                     <option value="${payType.code}"><@msg code='transfer.${payType.desc}' default='${payType.desc}'/></option>
                                                </#list>
                                            </select>
                                        </div>
                                        <br/>

										<div class="input-group">
                                            <span class="input-group-addon"><@msg code='withdraw.取现帐户' default='取现帐户'/>&nbsp&nbsp</span>
                                            <select id="whiteItem" name="whiteItem" class="selectpicker form-control" placeholder="<@msg code='withdraw.请由取现白名单选择取现帐户' default='请由取现白名单选择取现帐户'/>" >
                                                <option value=""><@msg code='withdraw.(请选择)' default='(请选择)'/></option>
                                                <#list whiteList as whiteItem>
                                                    <option value="${whiteItem.item}">${whiteItem.item}</option>
                                                </#list>
                                            </select>
                                        </div>
                                        <br/>
                                        
                                        <div class="input-group">
                                            <span class="input-group-addon"><@msg code='withdraw.户名' default='户名'/>&nbsp&nbsp</span>
                                            <input id="accName" name="accName" type="text" class="form-control" />
                                            <span class="input-group-addon">#</span>
                                        </div>
                                        <br/>
                                        <div class="input-group">
                                            <span class="input-group-addon"><@msg code='withdraw.卡号' default='卡号'/>&nbsp&nbsp</span>
                                            <input id="accNo" name="accNo" type="text" class="form-control" />
                                            <span class="input-group-addon">#</span>
                                        </div>
                                        <br/>
										<div class="input-group">
                                            <span class="input-group-addon"><@msg code='withdraw.开户行' default='开户行'/></span>
                                            <select id="bankCd" name="bankCd" class="form-control selectpicker" placeholder="<@msg code='withdraw.请选择开户行' default='请选择开户行'/>" >
                                                <option value=""><@msg code='withdraw.(请选择)' default='(请选择)'/></option>
                                                <#list bankNameLst as bankNm>
                                                    <option value="${bankNm.bankNum}">${bankNm.bankName}</option>
                                                </#list>
                                            </select>
                                        </div>
                                        <br/>
                                        <div class="input-group" style="text-align: left;">
                                        	<span class="input-group-addon"><@msg code='withdraw.图形验证' default='图形验证'/>&nbsp&nbsp</span>
			                            	<img id="validCodeImg" title="<@msg code='withdraw.点击刷新验证码' default='点击刷新验证码'/>"
												src="${ctx}/validateCode?r=${rondom}" 
												width="105" height="33" 
												style="cursor: pointer; margin-right: 5px;display: inline-block;" />
			                                <input type="text" placeholder="<@msg code='withdraw.请输入图形验证码' default='请输入图形验证码'/>" maxLength="4"
			                                	style="width: 150px; display: inline-block;" requiredInput="<@msg code='withdraw.图形验证码' default='图形验证码'/>"
			                                	name="validateCode" id="validateCode" class="form-control">
			                            </div>
			                            <br/>
                                        
                                        <#-- <#if whiteListType == 2 >
	                                        <div class="input-group">
	                                            <span class="input-group-addon">谷歌验证&nbsp&nbsp</span>
	                                            <input id="gaCode" name="gaCode" type="text" class="form-control" 
	                                            	placeholder="请输入 ${mchntCd}-${userId} 的谷歌验证码" />
	                                            <span class="input-group-addon">#</span>
	                                        </div>
	                                        <br/>
                                        </#if> -->
                                        <#--  <input id="cashType" name="cashType" type="hidden" value="0" /> -->
                                        
                                        <div class="input-group">
                                            <button id="withdrawBtn" name="withdrawBtn" type="button" class="btn btn-primary"><@msg code='withdraw.取現' default='取現'/></button>
                                            <a class="btn btn-primary" style=" margin-left: 16px;" title="<@msg code='withdraw.批量取現' default='批量取現'/>"
												href="${ctx}/batchWithdraw/withdrawMng" > <@msg code='withdraw.批量取現' default='批量取現'/> </a>
                                            <a class="btn btn-info cash" style=" margin-left: 16px;" title="<@msg code='withdraw.取现卡白名单' default='取现卡白名单'/>"
												href="${ctx}/secrity/bankCardWhiteList" > <@msg code='withdraw.取现卡白名单' default='取现卡白名单'/> </a>
                                        </div>
                                        <br/>
                                    <#-- 
                						<div class="form-group">
										  <h3>取现操作</h3>
										</div>
                                            <div class="form-group">
                                                    <label for="cashType" class="control-label col-lg-2 col-md-2 col-sm-3 col-xs-6">取现类型：</label>
                                                    <div class="col-lg-3 col-md-2 col-sm-3 col-xs-6">
                                                        <select class="form-control" id="cashType" name="cashType" style="height: 34px; width: 300px;" onChange=>
                                                            <option value="0">T+0取现</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="cashAmt" class="control-label col-lg-2 col-md-2 col-sm-3 col-xs-6">取款金额(元)：</label>
                                                    <div class="col-md-3 col-sm-6 col-xs-12">
                                                        <input id="cashAmt" class="easyui-numberbox" type="text" maxlength="15" style="height: 34px; width: 300px;" data-options="precision:2,groupSeparator: ',',decimalSeparator: '.'" name="cashAmt">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="accName" class="control-label col-lg-2 col-md-2 col-sm-3 col-xs-6">户名：</label>
                                                    <div class="col-lg-3 col-md-2 col-sm-3 col-xs-6">
                                                        <input id="accName" class="easyui-validatebox" type="text" maxlength="50" style="height: 34px; width: 300px;" name="accName">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="accNo" class="control-label col-lg-2 col-md-2 col-sm-3 col-xs-6">卡号：</label>
                                                    <div class="col-lg-3 col-md-2 col-sm-3 col-xs-6">
                                                        <input id="accNo" class="easyui-validatebox" type="text" maxlength="19" style="height: 34px; width: 300px;" name="accNo">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2 col-md-2 col-sm-3 col-xs-6">开户行：</label>
                                                    <div class="col-lg-3 col-md-2 col-sm-3 col-xs-6">
                                                        <select class="easyui-validatebox" id="bankCd" name="bankCd" style="width: 300px; height: 34px;">
                                                            <option value="">请选择</option>
                                                            <#list bankNameLst as bankNm>
                                                                <option value="${bankNm.bankNum}">${bankNm.bankName}</option>
                                                            </#list>
                                                        </select>
                                                    </div>
                                                </div>
                                                
                                                <div class="form-group">
                                                        <label class="control-label col-lg-2 col-md-2 col-sm-3 col-xs-6"></label>
                                                        <div class="col-lg-3 col-md-2 col-sm-3 col-xs-6">
                                                            <button type="button" id="withdrawBtn" name="withdrawBtn" class="btn btn-primary cash" title="取现" >取现</button>
                                                        </div>
                                                </div>
                                        
                                    -->    
                                    </form>
                
                
                            </div>
                        </div>
                </div>
	            <!-- Main content end -->
	
	        </div>
	        
	</div>

</#assign>

 <#assign htmlJS>
 
  var reloadAfterAlert=false; 
  	
  $(function() {
	 	var secretState = "${Session["secretState"]}";
    	var role = "${Session["userRole"]}";
        if("wd" == role && "0" == secretState ){
           alert("<@msg code='withdraw.请先设置安全设置' default='请先设置安全设置'/>");
           $.jumpTo("${ctx}/secrity/secritySetting");
        }

        if("su" != role){
        	if("wd" != role){
        		alert("<@msg code='withdraw.仅代付用户可以查看该页面' default='仅代付用户可以查看该页面'/>");
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
	    <#--  
		$('#formWithdraw').reset();
	    -->
	    <#--var r=''+Math.random()*1000000;
	    $('#randv').val(r); -->    
	    $('#whiteItem').val('');
	    $('#cashAmt').val('0');
	    $('#accName').val('');
	    $('#accNo').val('');
	    $("#cashAmtHid").val('');
		$("#accNoHid").val('');
		$("#accNameHid").val('');
		$("#bankCdHid").val('');
		$("#randv").val('');
		$('#gaCode').val('');
	    <#if whiteListType == 2 >
	    $('#gaCode').val('');
	    </#if>
		reloadAfterAlert=true;
  }

  
  
 <#-- 
  $("#cashAmt").numberbox({
				required: true,
				novalidate: true
  });
-->  
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
  
 <#-- $('#bankNameSel').combobox({
				required: true,
				novalidate: true,
				filter : function(q, row){
					var opts = $(this).combobox('options');
					return row[opts.textField].indexOf(q) > -1;
				},
				onSelect : function(rec){
					$("#branchBankName").combogrid("clear");
					$("#branchBankName").val("");
				}
			});
			$("#branchBankName").combogrid({
				required: true,
				novalidate: true,
				mode: 'remote',
				delay: 500,
				url: "${ctx}/loadBankBranchLst.do",
				idField: "branchBankName",
				textField: "branchBankName",
				columns: [[
					{field:'branchBankName',title:'支行名称',width:240,sortable:true},
					{field:'bankCd',title:'联行号',width:90,sortable:true}
				 ]],
				panelWidth:350,
				onBeforeLoad: function(param){
					var bankName = $('input[name="bankName"').val();
					if (bankName == '') {
						return false;
					}
					param.bankName = $('input[name="bankName"').val();
					param.brankBranchNm = param.q;
				},
				onSelect : function(i, r) {
					$("#bankCode").val(r.bankCd);
					$("#branchBankName").val(r.branchBankName);
				}
			});-->
	  		
    $(function () { $('#alertDialog').on('show.bs.modal', function () {
       		enableWithdrawBtn(false);
       		$("#comfirmOK").attr("disabled", "disabled");
       		$('#operation480').modal('hide');
      })
    });
    
    $(function () { $('#alertDialog').on('hide.bs.modal', function () {
       		enableWithdrawBtn(true);
       		$("#comfirmOK").removeAttr("disabled");
       		if (reloadAfterAlert) {
       			<#-- window.location.reload(); -->
       			$.jumpTo("${ctx}/withdraw/withdrawPage");
       		}
      })
    });
    
    $(function () { $('#withdrawBtn').off().on('click', function () {
		if($('#validateCode').val()==''){
		         alert("<@msg code='withdraw.图形验证码不能为空' default='图形验证码不能为空'/>");
			     return;
	    }
       var accNo = $('#accNo').val();
       var accName = $('#accName').val();
       <#--var bankName = $('input[name="bankName"').val();
       var branchBankName = $("#branchBankName").val();-->
       var bankCd = $('#bankCd option:selected').val();
       
       enableWithdrawBtn(false);
       
       if($('#cashAmt').val()==''){
	         $('#alertContent').html("<@msg code='withdraw.取现金额不能为空' default='取现金额不能为空'/>");
			 $('#alertDialog').modal('show');
			 <#--  $('#operation480').modal('toggle'); -->
        }else if ("" == accName || accName.length == 0) {
	             $('#alertContent').html("<@msg code='withdraw.请输入取现户名' default='请输入取现户名'/>");
				 $('#alertDialog').modal('show');
			     <#--  $('#operation480').modal('toggle'); -->
	    }else if ("" == accNo || accNo.length == 0) {
	             $('#alertContent').html("<@msg code='withdraw.请输入取现卡号' default='请输入取现卡号'/>");
				 $('#alertDialog').modal('show');
			     <#--  $('#operation480').modal('toggle'); -->
	    <#--}else if ("" == bankName || bankName.length == 0) {
	             $('#alertContent').html("请输入开户银行");
				 $('#alertDialog').modal('show');
			     $('#operation480').modal('toggle');
	    }else if ("" == branchBankName || branchBankName.length == 0) {
	             $('#alertContent').html("请输入开户银行支行");
				 $('#alertDialog').modal('show');
			     $('#operation480').modal('toggle');
	    }else{-->
	    }else if ("" == bankCd || bankCd == null) {
	             $('#alertContent').html("<@msg code='withdraw.请选择联行号' default='请选择联行号'/>");
				 $('#alertDialog').modal('show');
			     <#--  $('#operation480').modal('toggle'); -->
	    }else if (accName.indexOf(" ") != -1 || accName.indexOf("　") != -1 || accName.indexOf("\t") != -1){
				$('#alertContent').html("<@msg code='withdraw.取现户名不能包含空白' default='取现户名不能包含空白'/>");
				$('#alertDialog').modal('show');
	    }else{
	    <#--else{
	    	$('#myModal1').modal('show');
	    	disableGaCheck();
		    $('#operation480').modal('toggle');
	    }-->
	    	checkWithdraw();
	    }       		
      })
    });
 
  <#--   
  $(function () { $('#operation480').on('show.bs.modal', function () {
       var accNo = $('#accNo').val();
       var accName = $('#accName').val();
       var bankCd = $('#bankCd option:selected').val();
       
       enableWithdrawBtn(false);
       
       if($('#cashAmt').val()==''){
	         $('#alertContent').html("取现金额不能为空");
			 $('#alertDialog').modal('show');
        }else if ("" == accName || accName.length == 0) {
	             $('#alertContent').html("请输入取现户名");
				 $('#alertDialog').modal('show');
	    }else if ("" == accNo || accNo.length == 0) {
	             $('#alertContent').html("请输入取现卡号");
				 $('#alertDialog').modal('show');
	    }else if ("" == bankCd || bankCd == null) {
	             $('#alertContent').html("请选择联行号");
				 $('#alertDialog').modal('show');
	    }else{
	    	comfirm();
	    }
    })
  }); 
  -->

  function alertDialog(msg){
	$('#alertContent').html(msg);
	$('#alertDialog').modal('show');
	<#--  $('#alertDialog').modal({backdrop: 'static', keyboard: false}); --> 
  } 
  
  function refreshPage(){
      location.reload();
  }

	$("#comfirmOK").click(function(){
		$("#comfirmOK").attr("disabled", "disabled");
	　　　var gaCode = $("#gaCode").val();　
		if($('#gaCode').val()==''){
		         alert("<@msg code='withdraw.谷歌验证码不能为空' default='谷歌验证码不能为空'/>");
		         $("#comfirmOK").attr("disabled", "disabled");
			     return;
	    }
		comfirm();
	  });
	  
	function checkValid() {
		var accNo = $("#accNo").val();
		var url = "${ctx}/secrity/preValid?preValidData=" + accNo;
		$.ajax({
				url: url,
				async: false,
				success: function(data) {
					<#--  var dataObj = JSON.parse(data); -->
					var dataObj = parseJson(data); <#-- parseJson() is in mer.js -->
					if (dataObj === null) {
				    	alert ("<@msg code='withdraw.系统异常！' default='系统异常！'/>");
				    	return;					    
					}
					var lst = dataObj.respData.list;
					if (lst[0] === 'false') {
						$('#alertContent').html("<@msg code='withdraw.该银行卡' default='该银行卡'/>：" +'<font color="red" >' + lst[1].slice(0,-1) + '</font>' +"<@msg code='withdraw.不合法' default='不合法'/>");
					 	$('#alertDialog').modal('show');
						return;
					}
				},
			    error:function(){
			    	alert("<@msg code='withdraw.系统异常，请联系管理员！' default='系统异常，请联系管理员！'/>");
			    	return  false;
			    }
			}); 
	}
	
	function enableWithdrawBtn(enabled){
	    <#--  -->
		$("#withdrawBtn").attr("disabled", "disabled");
	    if(enabled){
			$("#withdrawBtn").removeAttr("disabled");
	    }
	}
	
	function checkWithdraw(){
		var cashType = $('#cashType option:selected').val();
		var cashAmt = $("#cashAmt").val();
		var accNo = $("#accNo").val();
		var accName = $('#accName').val();
	    var bankCd = $('#bankCd option:selected').val(); 
	    var postData= $("#formWithdraw").serialize();
				
	    clearInputs();
	    var postUrl = "${ctx}/withdraw/checkWithdraw.do";
		$.ajax({
			url: postUrl,
			type: 'POST',
			data: postData,
			async: false,
			success: function(data) {
				var dataObj = parseJson(data);
				if (dataObj === null) {
			    	alert ("<@msg code='withdraw.系统异常！' default='系统异常！'/>");
			    	return;
				}
									
				var code = dataObj.respCode;
				var msg=  dataObj.respMsg;
                var respData = dataObj.respData;
				if(code == '00'){
					if(JSON.stringify(respData) == JSON.stringify({})){ <#-- 表示已经代付完成 -->
						$('#alertContent').html(msg);
					 	$('#alertDialog').modal('show');
						return;
					}else{
						$('#alertContent').html("<@msg code='withdraw.系统异常，请联系管理员！' default='系统异常，请联系管理员！'/>");
                        $('#alertDialog').modal('show');
                        return;
					}
                }else if(code == '01') { <#-- 要求GA验证 -->
                        $("#cashAmtHid").val(respData.res.cashAmt);
                        $("#accNoHid").val(respData.res.accNo);
                        $("#accNameHid").val(respData.res.accName);
                        $("#bankCdHid").val(respData.res.bankCd);
                        $('#myModal2').modal('show');
                        disableGaCheck();
				}else{
					$('#alertContent').html(msg);
			 		$('#alertDialog').modal('show');
			 		return;
				}
			},
            error:function(){
                clearInputs();
                 <#-- 刷新验证码 -->
                $("#validCodeImg").click();
                $('#alertContent').html("<@msg code='withdraw.系统异常，请联系管理员！' default='系统异常，请联系管理员！'/>");
                $('#alertDialog').modal('show');
                return  false;
            }			
		});
	}
	
	function comfirm(){
		$('#myModal2').modal('hide');
		enableGaCheck();
		$('#operation480').modal('show');
		<#-- var cashType = $('#cashType option:selected').val();
		var cashAmt = $("#cashAmt").val();
		var accNo = $("#accNo").val();
		var accName = $('#accName').val();
	    <#--var bankName = $('input[name="bankName"').val();
	    var branchBankName = $("#branchBankName").val();
	    var bankCd = $('#bankCd option:selected').val();
	    var postData= $("#formWithdraw").serialize();-->
	    var cashAmt = $("#cashAmtHid").val();
		var accNo = $("#accNoHid").val();
		var accName =$("#accNameHid").val();
		var bankCd =$("#bankCdHid").val();
		var randv =$("#randv").val();
		var gaCode =$("#gaCode").val();
		var payType = $('#payType option:selected').val();
		var currencyCode = $('#currencyCode').val();
	    clearInputs();
	    
	    <#--  
		var postUrl = "${ctx}/withdraw/submitWithdraw.do?cashType=" + cashType + "&cashAmt=" + cashAmt +"&accNo=" + accNo +"&accName=" + accName +"&bankCd=" + bankCd;
	    var gaCode = $('#gaCode').val();
		var postUrl = "${ctx}/withdraw/submitWithdraw.do?cashType=" + cashType + "&cashAmt=" + cashAmt +"&accNo=" + accNo +"&accName=" + accName +"&bankCd=" + bankCd +"&gaCode=" + gaCode;
		-->
		var postUrl = "${ctx}/withdraw/submitWithdraw.do";
		$.ajax({
			url: postUrl,
			type: 'POST',
			data: {
				cashAmt:cashAmt,
				accNo:accNo,
				accName:accName,
				bankCd:bankCd,
				randv:randv,
				gaCode:gaCode,
				payType:payType,
				currencyCode:currencyCode
			},
			async: false,
			success: function(data) {
				var dataObj = parseJson(data); <#-- parseJson() is in mer.js -->
				if (dataObj === null) {
			    	alert ("<@msg code='withdraw.系统异常！' default='系统异常！'/>");
			    	return;					    
				}
									
				var code = dataObj.respCode;
				var msg=  dataObj.respMsg;
				
				//clearInputs();
				
				 <#-- 刷新验证码 -->
        		 $("#validCodeImg").click();
				<#--  
				if (code === '00') {
					$('#alertContent').html("请求成功，处理中。请到取现查询页面查看结果。");
					$('#alertDialog').modal('show');
					return;
				}else{
		    		$('#alertContent').html("请求异常，请到取现查询页面确认交易结果。"+msg);
				 	$('#alertDialog').modal('show');
					return;
				}
				-->
	    		$('#alertContent').html(msg);
			 	$('#alertDialog').modal('show');
				return;
			},
		    error:function(){
				clearInputs();
				 <#-- 刷新验证码 -->
        		$("#validCodeImg").click();
		    	$('#alertContent').html("<@msg code='withdraw.连线异常，请到取现查询页面确认交易结果。' default='连线异常，请到取现查询页面确认交易结果。'/>");
		    	$('#alertDialog').modal('show');
		    	return  false;
		    }
		});
	}


		$("#validCodeImg").click(function() {
			var r = Math.floor(Math.random() * ( 100000000 + 1));
			$(this).attr("src", "${ctx}/validateCode?r=" + r);
			$("#validateCode").val('');
		});
	
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
	 
	$('#currCd').on('change', function(){
		$("#payType").val("");
		$("#bankCd").val("");
		$("#payType").clearOptions(true);
		$("#bankCd").clearOptions(false);
		getPayTypesWithdrawByCurrCd();
		getBankNumsList();
	});
	
	
    function getPayTypesWithdrawByCurrCd(){
		var currCd = $('#currCd').val();
		var postUrl="${ctx}/withdraw/getPayTypesWithdrawByCurrCd.do?currCd=" + currCd;
		$.ajax({
				type : "GET",
				url : postUrl,
				async: false,
				cache: false,
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
					} else {
						$('#payType').append('<option value=""><@msg code='withdraw.(请选择)' default='(请选择)'/></option>');
					}
				},
				error:function(){
			    	alert("系统异常，请联系管理员！");
			    	return  false;
			    }
		});       
    }
    
    function getBankNumsList(){
		var currCd = $('#currCd').val();
		var postUrl="${ctx}/withdraw/bankNums.do?currCd=" + currCd;
		$.ajax({
				type : "GET",
				url : postUrl,
				async: false,
				cache: false,
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
    	var selObj = $('#bankCd');
    	if (isEmpty(data)||isEmpty(data.respData)||isEmpty(data.respData.bankNums)){
    		selObj.clearOptions(false);
    	}
    	else{
    		var list = data.respData.bankNums;
    	    for (var i = 0, len = list.length; i < len; i++) {
				var item = list[i];
				var val = item.bankNum;
				var desc  = item.bankName+"("+item.bankNum+")";
				selObj.addOption(val,desc);    	    	
    	    }
    	}
    }
		
</#assign>
<#include "/common/layout.ftl" />
