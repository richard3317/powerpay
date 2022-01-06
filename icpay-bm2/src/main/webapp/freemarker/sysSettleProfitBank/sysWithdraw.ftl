<#include "/sysSettleProfitBank/add_sysWithdraw.ftl" />
<#assign label = "分润打款">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="sysSettleProfitBank"></@head>
		<script type="text/javascript">
			function selectChnlId(obj) {
				var serialNo;
				var getId = obj.id;
				var selectedItem = $('#' + getId).val();
				
				<#-- 拿到序號 -->
  				if (getId.indexOf("_") != -1) {
    				serialNo = getId.substring(getId.indexOf("_") + 1);
  				}
				
				if (isEmpty(selectedItem))
					onSelEmptyChnlId();
				else {
					onSelectedChnlIdChanged(selectedItem, serialNo);
				}
			}
			
			function onSelEmptyChnlId() {
				$("#chnlMchntCd").clearOptions(false);
				//$("#chnlMchntCd").addOption("*","*全部*");
			}
			
			function onSelectedChnlIdChanged(selectedCh, serialNo) {
				var defaultAmt = $('#defaultAmtDesc_' + serialNo).val();
				$("#chnlMchntCd").clearOptions(false);
				$.ajax({
					type : "GET",
					url : _ctx + "/sysSettleProfitBank/getChnlMchnts.do?chnlId="+selectedCh+"&defaultAmt="+defaultAmt,
					async: true,
					cache: true,
					success : function(data) {
						var res = parseJson(data);
						if (!checkEmpty(res, "系统异常，请联系管理员！")) return false;
						if (res.respCode != "00"){
							alert("操作失败: "+res.respMsg);
							return false;
						}
						var list = res.respData.chnlMchntList;
						if (! isEmpty(list)) {
							for (var i = 0, len = list.length; i < len; i++) {
							  var item = list[i];
							  var desc = item.chnlMchntDesc+"|"+item.chnlMchntCd+"|"+item.realAvailableBalance;
							  item.chnlMchntDesc = desc;
							}
						}					
						$('#chnlMchntCd_' + serialNo).combobox({
      						data: list,
      						valueField: 'chnlMchntCd',
	        				textField: 'chnlMchntDesc',
	        				required: true,
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
			
			<#-- 计算还差多少打款金额 -->
			function calGapAmt() {
				var totalCount = document.getElementById("totalCount").value;
				var totalAmt = 0;
				for (i = 0; i < totalCount; i++) {
					var newId = "defaultAmtDesc_" + i;
					var defaultAmt = $('#' + newId).val();
				  	totalAmt = parseInt(totalAmt) + parseInt(defaultAmt);
				}
				var targetAmt = document.getElementById("targetAmtDesc").value;
				var gapAmt = parseInt(targetAmt) - parseInt(totalAmt);
				if (isNaN(gapAmt)) {
			      	gapAmt = "";
			    }
			    document.getElementById("gapAmt").innerHTML = gapAmt;
			    if (parseInt(gapAmt) < 0) {
			    	$("#gapAmt").css("color", "red");
			    } else {
			    	$("#gapAmt").css("color", "black");
			    }
			}
			
			function editBankCardBtn() {
				$.jumpTo(_ctx + "/sysSettleProfitBank/manage.do");
			}
		</script>	
	</head>
	
	<body>
		<@addDiv label="${label}" addUrl="/sysSettleProfitBank/sysWithdraw/submit.do">
			<input type="hidden" id="totalCount" name="totalCount" value="${totalCount}" />
			<a id="editBankCardBtn" href="javascript:void(0)" class="easyui-linkbutton" 
				data-options="iconCls:'icon-edit'" onclick="editBankCardBtn()">编辑分润银行卡</a>
			<br>
			<table class="qry_tbl">
				<tr>    
					<td class="label">打款目标金额(元)：</td>
					<td class="content">
				        <input class="easyui-validatebox" type="text" maxlength="20" id="targetAmtDesc" 
				        	name="targetAmtDesc" value="${targetAmtDesc}" onfocusout="calGapAmt()"/>
				    </td>
				    <td class="content" colspan="4">
				    	還差：<span id="gapAmt">0</span>&nbsp打款金额(元)
				    </td>
				</tr>
			</table>
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="content">银行卡</td>
					<td class="content">金额(元)</td>
					<td class="content">打款渠道</td>
					<td class="content">打款大商编</td>
					<td class="content">渠道商户名称|渠道商户号|实际可代付余额(元)</td>
				</td>
				
				<#if sysList??>
					<#list sysList as sysList>
						<input type="hidden" name="accountName" value="${sysList.accountName}" />
						<input type="hidden" name="accountNo" value="${sysList.accountNo}" />
						<tr>
							<td class="content">
								<input class="easyui-validatebox" type="text" name="account" 
									value="${sysList.accountNo}|${sysList.accountName}|${sysList.accountBankName}" readonly="readonly"/>
							</td>
							<td class="content">
								<input class="easyui-validatebox" type="text" maxlength="20" id="defaultAmtDesc_${sysList.i}" 
									name="defaultAmtDesc" value="${sysList.defaultAmtDesc}" onfocusout="calGapAmt()"/>
							</td>
							<td class="content">
								<select id="chnlId_${sysList.i}" name="chnlId" onchange="selectChnlId(this)">
									<@enumOpts enumNm='TxnEnums.ChnlId' showEmptyOpt='true' showCode="true" />
								</select>
							</td>
							<td class="content" colspan="2">
								<select class="easyui-validatebox" style="width: 550px; height: 27px;" 
									id="chnlMchntCd_${sysList.i}" name="chnlMchntCd">
									<option value=""></option>
								</select>
						        <span class="inputDesc"></span>
							</td>
						</tr>
					</#list>
				</#if>
			</table>
		</@addDiv>
	</body>
</html>