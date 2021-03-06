<#include "/common/add_macro.ftl" />
<#assign label = "代理商银行帐户新增">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="chnlBankMapping">
			$("#bankNum").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[8]'
			});
			$("#intTransCd").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: ['number','maxLength[4]']
			});
			$("#currCdSel").validatebox({
				required: true,
				novalidate: true
			});
			$("#chnlId").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[2]'
			});
			$("#chnlBankNum").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[16]'
			});
			<#--  
			$("#chnlBankName").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[30]'
			});
			-->
			$("#state").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[2]'
			});
			$("#sortSeq").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: ['number','maxLength[4]']
			});
			$("#comment").validatebox({
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[256]'
			});
			
			$('#currCdSel').on('change', function(){
				getChnlBanks();
			});
		
		    function getChnlBanks(){
				var currCd = $('#currCdSel').val();
				$.ajax({
						type : "GET",
						url : baseUrl + "/getChnlBanks.do?currCd=" + currCd,
						async: false,
						cache: false,
						success : function(data) {
							var res = parseJson(data);
							fillBanksNumSel(res);
							return;
						},
						error:function(){
					    	alert("系统异常，请联系管理员！");
					    	return  false;
					    }
				});       
		    }
		    
		    function fillBanksNumSel(data){
		    	var selObj = $('#bankNum');
		    	if (isEmpty(data)||isEmpty(data.respData)||isEmpty(data.respData.bankNums)){
		    		selObj.clearOptions(false);
		    	}
		    	else{
		    		var list = data.respData.bankNums;
		    	    for (var i = 0, len = list.length; i < len; i++) {
						var item = list[i];
						var val = item.bankNum;
						var desc  = item.bankNum + "-" + item.bankName;
						selObj.addOption(val,desc);    	    	
		    	    }
		    	}
		    }
		</@head>
	</head>
	
	<body>
		<@addDiv label="${label}" addUrl="/chnlBankMapping/add/submit.do">
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">交易類型：</td>
					<td class="content">
						<select class="easyui-validatebox" id="intTransCd" name="intTransCd" style="width: 160px;">
							<@enumOpts enumNm="ProfitEnums.ProfitTxnTp" showEmptyOpt="true" showCode="true" 
							  exceptValues="0100,0050,0090" />
							<#--  <option value="*">*全部*</option> -->
						</select>
						<select class="easyui-validatebox" name="currCd" id="currCdSel" >
							<@enumOpts enumNm="CurrencyEnums.CurrType" showEmptyOpt="true" showCode="true" />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">银行编号：</td>
					<td class="content">
						<select class="easyui-validatebox"
							id="bankNum" name="bankNum" 
							style="width: 200px; height: 27px; ">
							<option value="">--请选择--</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">渠道：</td>
					<td class="content">
						<select class="easyui-validatebox" id="chnlId" name="chnlId" style="width: 160px;">
							<@enumOpts enumNm='TxnEnums.ChnlId' showEmptyOpt='true' showCode="true" />
							<#--  <option value="*">*</option> -->
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">渠道银行编号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" id="chnlBankNum" maxLength="12"  style="width: 200px;"
							name="chnlBankNum" value="">
					</td>
				</tr>
				<tr>
					<td class="label">渠道银行名称：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" id="chnlBankName" maxLength="30" style="width: 200px;"
							name="chnlBankName" value="">
					</td>
				</tr>
				<tr>
					<td class="label">有效狀態：</td>
					<td class="content">
						<select class="easyui-validatebox" id="state" name="state" style="width: 100px;" value="1">
							<@enumOpts enumNm="TxnEnums.CommonValid" showEmptyOpt="true" showCode="true" selected="1" />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">显示顺序：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="256"
							id="sortSeq" name="sortSeq" value="100" />
					</td>
				</tr>
				<tr>
					<td class="label">备注：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="256"
							id="comment" name="comment" />
					</td>
				</tr>
			</table>
		</@addDiv>
	</body>
</html>