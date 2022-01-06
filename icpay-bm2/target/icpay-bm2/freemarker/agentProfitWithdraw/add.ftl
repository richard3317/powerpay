<#include "/common/add_macro.ftl" />
<#assign label = "代理商银行帐户新增">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="agentProfitWithdraw">
			$("#agentCd").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[30]'
			});
			$("#mchntCd").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[30]'
			});
			
			$("#accountBankCode").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: ['number','maxLength[8]']
			});
			$("#accountNo").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: ['number','maxLength[30]']
			});
			$("#accountName").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[64]'
			});
			
			$("#comment").validatebox({
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[256]'
			});
			
			var lastSelAgentCd=null;
			$('#agentCdSel').on('change', function(){
				var selAgentCd= $('#agentCdSel').val();
				if (isEmpty(selAgentCd))
					clearAgentBankAccs();
				else {
					if (lastSelAgentCd != selAgentCd)
						loadAgentBankAccs(selAgentCd);
				}
				lastSelAgentCd = selAgentCd;
			});
			
			function clearAgentBankAccs(){
				var objBankAccSel = $('#bankAccSel');
				var objMchntCdSel = $('#mchntCdSel');
				<#--  objBankAccSel.empty(); -->
				<#-- remove-all-options-except-first-option -->
				objBankAccSel.children('option:not(:first)').remove();
				objMchntCdSel.children('option:not(:first)').remove();
			}
			
			function loadAgentBankAccs(selAgentCd){
				clearAgentBankAccs();
				$.ajax({
					type : "GET",
					url : _ctx + "/agentProfitWithdraw/agentBankAccs.do?agentCd="+selAgentCd,
					async: true,
					cache: true,
					<#-- data: $("#qryForm").serialize(), -->
					success : function(data) {
						var res = parseJson(data);
						if (!checkEmpty(res, "系统异常，请联系管理员！")) return false;
						if (res.respCode != "00"){
							alert("操作失败: "+res.respMsg);
							return false;
						}	
						setupAgentBankAccs(res.respData.accsList);
						var list = res.respData.amerList;
						if (! isEmpty(list)) {
							for (var i = 0, len = list.length; i < len; i++) {
							  var item = list[i];
							  var val = item.frontMchntCd+";"+item.frontMchntDesc;
							  item.frontMchntCd = val;
							}
						}					
						$('#mchntCdSel').combobox({
      						data: list,
      						valueField: 'frontMchntCd',
	        				textField: 'frontMchntDesc',
							filter : function(q, row){
								var opts = $(this).combobox('options');
								return row[opts.textField].indexOf(q) > -1;
							}
     					});
					},
					error:function(){
				    	alert("系统异常，请联系管理员！");
				    	return  false;
				    }
				});
			}
			
			function setupAgentBankAccs(accsList){
				console.log(accsList);
				if (isEmpty(accsList)) return false;
				var objBankAccSel = $('#bankAccSel');
				for (var i = 0, len = accsList.length; i < len; i++) {
				  var item = accsList[i];
				  var val = item.accountNo+";"+item.accountName+";"+item.accountBankCode+";"+item.accountBankName;
				  var desc  = item.accountNo+"|"+item.accountName+"|"+item.accountBankName;
				  item.accountNo = val;
				  item.accountName = desc;
				  <#-- objBankAccSel.append($("<option></option>").attr("value",val).text(desc)); -->
				}
				$('#bankAccSel').combobox({
					data: accsList,
					valueField: 'accountNo',
    				textField: 'accountName',
					filter : function(q, row){
						var opts = $(this).combobox('options');
						return row[opts.textField].indexOf(q) > -1;
					}
				});
			}
			
		</@head>
	</head>
	
	<body>
		<@addDiv label="${label}" addUrl="/agentProfitWithdraw/add/submit.do">
			<input type="hidden" name="settleDate" value="${preDate!''}" />

			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>    <td class="label">代理商：</td>
					<td class="content">
						<select class="easyui-validatebox"
							id="agentCdSel" name="agentCd" 
							style="width: 300px; height: 27px; ">
							<option value="">--请选择--</option>
							<#if agentsList??>
								<#list agentsList as agent>
									<option value="${agent.agentCd}">${agent.agentCnNm}</option>
								</#list>
							</#if>
						</select>
						<span class="inputDesc">请选择代理商</span>
					</td>
				</tr>
				<tr>    <td class="label">打款商户号：</td>
				    <td class="content">
						<select class="easyui-validatebox" style="width: 300px; height: 27px;"
							id="mchntCdSel" name="mchntCd">
							<option value=""></option>
						</select>				            
				        <span class="inputDesc">请选择出款的渠道商户</span>
				    </td>
				</tr>
				<tr>    <td class="label">打款银行帐号：</td>
				    <td class="content">
						<select class="easyui-validatebox" style="width: 300px; height: 27px;"
							id="bankAccSel" name="accountNo">
							<option value=""></option>
						</select>
				        <span class="inputDesc"></span>
				    </td>
				    
				</tr>
				<#--  
				<tr>    <td class="label">打款帐号户名：</td>
				    <td class="content">
				        <input class="easyui-validatebox" type="text" maxlength="100" 
				            id="accountName" name="accountName" />
				        <span class="inputDesc"></span>
				    </td>
				</tr>
				<tr>    <td class="label">打款行联号：</td>
				    <td class="content">
				        <input class="easyui-validatebox" type="text" maxlength="100" 
				            id="accountBankCode" name="accountBankCode" />
				        <span class="inputDesc"></span>
				    </td>
				</tr>
				-->
				<#--  
				<tr>    <td class="label">打款順序：</td>
				    <td class="content">
				        <input class="easyui-validatebox" type="text" maxlength="100" 
				            id="seq" name="seq" />
				        <span class="inputDesc"></span>
				    </td>
				</tr>
				<tr>    <td class="label">當日应付总额：</td>
				    <td class="content">
				        <input class="easyui-validatebox" type="text" maxlength="100" 
				            id="apAmtTotalDesc" name="apAmtTotalDesc" />
				        <span class="inputDesc">元</span>
				    </td>
				</tr>
				
				<tr>    <td class="label">应打款金额：</td>
				    <td class="content">
				        <input class="easyui-validatebox" type="text" maxlength="100" 
				            id="apAmtDesc" name="apAmtDesc" />
				        <span class="inputDesc">元</span>
				    </td>
				</tr>
				-->
				
				<tr>    <td class="label">实际打款金额：</td>
				    <td class="content">
				        <input class="easyui-validatebox" type="text" maxlength="100" 
				            id="txnAmtDesc" name="txnAmtDesc" />
				        <span class="inputDesc">元</span>
				    </td>
				</tr>
				
				<tr>    <td class="label">打款操作：</td>
				    <td class="content">
				        <select id="state" name="state" value="1" >
							<@enumOpts enumNm="TxnEnums.CommonSwitch" showEmptyOpt="false" showCode="false" selected="1"/>
						</select>
				        <span class="inputDesc">关闭/开启打款操作</span>
				    </td>
				</tr>
				
				<tr>    <td class="label">备注：</td>
				    <td class="content">
				        <input class="easyui-validatebox" type="text" maxlength="200" 
				            id="comment" name="comment" />
				        <span class="inputDesc"></span>
				    </td>
				</tr>
			</table>
		</@addDiv>
	</body>
</html>