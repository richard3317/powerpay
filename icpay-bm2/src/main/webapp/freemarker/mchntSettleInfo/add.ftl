<#include "/common/add_macro.ftl" />
<#assign label = "商户清算账户信息">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="mchntSettleInfo">
			$("#mchntCdInpt").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: ['number','length[15]', 'ajaxCheck["${ctx}/ajaxCheck/checkMchntCd.do", "mchntCd"]']
			});
			
			$("#settleAccountInpt").validatebox({
				required: false,
				novalidate: true,
				delay: 1000,
				validType: ['number','maxLength[50]']
			});
			$("#settleAccountNameInpt").validatebox({
				required: false,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[256]'
			});
			
			$("#commentInpt").validatebox({
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[256]'
			});
			$("#settleLimitInpt").validatebox({
				novalidate: true,
				delay: 1000,
				validType: ['amount', 'maxLength[15]']
			});
			$("#settleAccountBankCodeInpt").validatebox({
				required: false,
				novalidate: true
			});
			$("#provSel").validatebox({
				required: false,
				novalidate: true
			});
			$("#citySel").validatebox({
				required: false,
				novalidate: true
			});
			$("#settleAccountAreaCodeSel").validatebox({
				required: false,
				novalidate: true
			});
			$("#settlePeriodSel").change(function() {
				var v = $(this).val();
				if (v == "0") {
					$("#settleLimitTr").show();
					$("#settleLimitInpt").removeAttr("disabled");
				} else {
					$("#settleLimitTr").hide();
					$("#settleLimitInpt").attr("disabled", "disabled");
				}
			});
			$("#settleLimitTr").hide();
			$("#settleLimitInpt").attr("disabled", "disabled");
			
			$("#provSel").change(function() {
				var provCd = $(this).val();
				if (provCd == '') {
					$("#citySel").html("").append('<option value="">--请选择--</option>');
				} else {
					var url =  "${ctx}/loadCity.do?provCd=" + provCd;
					$.get(url, function(data) {
						$.processAjaxResp(data, function(r) {
							var lst = r.respData.cityLst;
							$("#citySel").html("").append('<option value="">--请选择--</option>');
							for (var i = 0; i < lst.length; i ++) {
								var regionCd = lst[i].regionCd;
								var regionNm = lst[i].regionCnNm;
								$("#citySel").append('<option value="' + regionCd + '">' + regionCd + '-' + regionNm + '</option>');
							}
							$("#settleAccountAreaCodeSel").html("").append('<option value="">--请选择--</option>');
						});
					});
				}
			});
			$("#citySel").change(function() {
				var cityCd = $(this).val();
				if (cityCd == '') {
					$("#settleAccountAreaCodeSel").html("").append('<option value="">--请选择--</option>');
				} else {
					var url =  "${ctx}/loadDistrict.do?cityCd=" + cityCd;
					$.get(url, function(data) {
						$.processAjaxResp(data, function(r) {
							var lst = r.respData.districtLst;
							$("#settleAccountAreaCodeSel").html("").append('<option value="">--请选择--</option>');
							for (var i = 0; i < lst.length; i ++) {
								var regionCd = lst[i].regionCd;
								var regionNm = lst[i].regionCnNm;
								$("#settleAccountAreaCodeSel").append('<option value="' + regionCd + '">' + regionCd + '-' + regionNm + '</option>');
							}
						});
					});
				}
			});
			$('#bankNameSel').combobox({
				required: false,
				novalidate: true,
				filter : function(q, row){
					var opts = $(this).combobox('options');
					return row[opts.textField].indexOf(q) > -1;
				},
				onSelect : function(rec){
					$("#settleAccountBankNameInpt").combogrid("clear");
					$("#settleAccountBankCodeInpt").val("");
				}
			});
			$("#settleAccountBankNameInpt").combogrid({
				required: false,
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
					$("#settleAccountBankCodeInpt").val(r.bankCd);
				}
			});
		</@head>
	<#-- <script type="text/javascript">
		function checkPreT1() {
        	var preTransferTimeT1 = $("#preTransferTimeT1Inpt").val();
        	if('' == preTransferTimeT1){
        		alert("请先设置前置T1结转时间");
        		return;
        	}
	     }
    </script> -->
	</head>
	
	<body>
		<@addDiv label="${label}" addUrl="/mchntSettleInfo/add/submit.do">
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">商户代码：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="15"
							id="mchntCdInpt" name="mchntCd" />
						<span class="inputDesc">必填，请输入有效的二清商户代码</span>
					</td>
				</tr>
				<tr>
					<td class="label">开户省：</td>
					<td class="content">
						<select class="easyui-validatebox" id="provSel" name="prov" style="width: 300px;">
							<option value="">--请选择--</option>
							<#list provMap?keys as k>
								<option value="${k}">${k}-${provMap['${k}']}</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">开户市：</td>
					<td class="content">
						<select class="easyui-validatebox" id="citySel" name="cityCd" style="width: 300px;">
							<option value="">--请选择--</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">开户区县：</td>
					<td class="content">
						<select class="easyui-validatebox" id="settleAccountAreaCodeSel" 
							name="settleAccountAreaCode" style="width: 300px;">
							<option value="">--请选择--</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">开户银行：</td>
					<td class="content">
						<select class="easyui-validatebox"
							id="bankNameSel" name="bankName" 
							style="width: 300px; height: 27px; ">
							<option value=""></option>
							<#list bankNameLst as bankNm>
								<option value="${bankNm}">${bankNm}</option>
							</#list>
						</select>
						<span class="inputDesc">请选择企业网银开户银行信息</span>
					</td>
				</tr>
				<tr>
					<td class="label">开户银行支行：</td>
					<td class="content">
						<input class="easyui-validatebox"
							type="text" style="height:27px; width: 300px;"
							id="settleAccountBankNameInpt" name="settleAccountBankName" />
						<span class="inputDesc">请选择企业网银开户银行信息</span>
					</td>
				</tr>
				<tr>
					<td class="label">开户行联行号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" readonly="readonly"
							id="settleAccountBankCodeInpt" name="settleAccountBankCode" />
					</td>
				</tr>
				<tr>
					<td class="label">清算账号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="50"
							id="settleAccountInpt" name="settleAccount" />
						<span class="inputDesc">请输入企业账号</span>
					</td>
				</tr>
				<tr>
					<td class="label">清算账号户名：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="256"
							id="settleAccountNameInpt" name="settleAccountName" />
						<span class="inputDesc">请输入企业网银账户名</span>
					</td>
				</tr>
				<tr>
					<td class="label">币别：</td>
					<td class="content">
						<select class="easyui-validatebox" name="currCd" id="currCd" required="true">
							<@enumOpts enumNm="CurrencyEnums.CurrType" showEmptyOpt="true" showCode="true" />
						</select>
						<span class="inputDesc">必填</span>
					</td>
				</tr>
				<tr>
					<td class="label">清算周期：</td>
					<td class="content">
						<select class="easyui-validatebox" name="settlePeriod" id="settlePeriodSel" required="true">
							<@enumOpts enumNm="SettleEnums.SettlePeriod" showEmptyOpt="true" />
						</select>
						<span class="inputDesc">必填，请注意：清算周期后续无法修改</span>
					</td>
				</tr>
				<tr id="settleLimitTr">
					<td class="label">当日最大清算金额：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="14"
							id="settleLimitInpt" name="settleLimit" />(元)
						<span class="inputDesc">T+0日清算时填写</span>
					</td>
				</tr>
				<tr id="balanceTransferTr">
					<td class="label">自动D0余额结转：</td>
					<td class="content">
						<select class="easyui-validatebox" id="balanceTransferSel" name="balanceTransfer" style="width: 300px;">
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</td>
				</tr>
				<tr id="balanceTransTimeTr">
					<td class="label">D0余额结转时间：</td>
					<td class="content">
						<input class="easyui-validatebox" type ="text" id="transferTimeInpt" name ="transferTime"/>
						<span class="inputDesc">格式HHmmss，例如：235000(可省略，使用默认值)</span>
					</td>
				</tr>
				<tr id="balanceTransferT1Tr">
					<td class="label">自动T1余额结转反还：</td>
					<td class="content">
						<select class="easyui-validatebox" id="balanceTransferT1Sel" name="balanceTransferT1" style="width: 300px;">
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</td>
				</tr>
				<tr id="preTransferTimeT1Tr">
					<td class="label">第一次T1结转时间：</td>
					<td class="content">
						<input class="easyui-validatebox" type ="text" id="preTransferTimeT1Inpt" name ="preTransferTimeT1"/>
						<span class="inputDesc">格式HHmmss，空值表示不進行前置T1结转</span>
					</td>
				</tr>
				<tr id="preTransferT1PercentTr">
					<td class="label">第一次T1结转比例：</td>
					<td class="content">
						<input class="easyui-validatebox" type ="number" id="preTransferT1PercentInpt" name ="preTransferT1Percent" />
						<span class="inputDesc">格式：1 表示 100%,  0.9 表示 90%</span>
					</td>
				</tr>
				<tr id="balanceTransTimeT1Tr">
					<td class="label">第二次T1余额结转时间：</td>
					<td class="content">
						<input class="easyui-validatebox" type ="text" id="transferTimeT1Inpt" name ="transferTimeT1"/>
						<span class="inputDesc">格式HHmmss，例如：163000(可省略，使用默认值)</span>
					</td>
				</tr>
				<tr>
					<td class="label">备注：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="255"
							id="commentInpt" name="comment" />
					</td>
				</tr>
			</table>
		</@addDiv>
	</body>
</html>