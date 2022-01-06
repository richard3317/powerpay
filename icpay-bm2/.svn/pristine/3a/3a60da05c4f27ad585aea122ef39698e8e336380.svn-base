<#include "/common/add_macro.ftl" />
<#assign label = "代理商分润信息">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="agentProfit">
			$("#agentCdInpt").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: ['length[15]', 'ajaxCheck["${ctx}/ajaxCheck/checkProfitAgentCd.do", "agentCd"]']
			});
			$("#accountInpt").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: ['number','maxLength[50]']
			});
			$("#accountNameInpt").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[256]'
			});
			$("#commentInpt").validatebox({
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[256]'
			});
			$("#accountBankCodeInpt").validatebox({
				required: true,
				novalidate: true
			});
			$("#provSel").validatebox({
				required: true,
				novalidate: true
			});
			$("#citySel").validatebox({
				required: true,
				novalidate: true
			});
			$("#accountAreaCodeSel").validatebox({
				required: true,
				novalidate: true
			});
			
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
							$("#accountAreaCodeSel").html("").append('<option value="">--请选择--</option>');
						});
					});
				}
			});
			$("#citySel").change(function() {
				var cityCd = $(this).val();
				if (cityCd == '') {
					$("#accountAreaCodeSel").html("").append('<option value="">--请选择--</option>');
				} else {
					var url =  "${ctx}/loadDistrict.do?cityCd=" + cityCd;
					$.get(url, function(data) {
						$.processAjaxResp(data, function(r) {
							var lst = r.respData.districtLst;
							$("#accountAreaCodeSel").html("").append('<option value="">--请选择--</option>');
							for (var i = 0; i < lst.length; i ++) {
								var regionCd = lst[i].regionCd;
								var regionNm = lst[i].regionCnNm;
								$("#accountAreaCodeSel").append('<option value="' + regionCd + '">' + regionCd + '-' + regionNm + '</option>');
							}
						});
					});
				}
			});
			$('#bankNameSel').combobox({
				required: true,
				novalidate: true,
				filter : function(q, row){
					var opts = $(this).combobox('options');
					return row[opts.textField].indexOf(q) > -1;
				},
				onSelect : function(rec){
					$("#accountBankNameInpt").combogrid("clear");
					$("#accountBankCodeInpt").val("");
				}
			});
			$("#accountBankNameInpt").combogrid({
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
					$("#accountBankCodeInpt").val(r.bankCd);
				}
			});
		</@head>
	</head>
	
	<body>
		<@addDiv label="${label}" addUrl="/agentProfit/add/submit.do">
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">代理商代码：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="15"
							id="agentCdInpt" name="agentCd" />
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
						<select class="easyui-validatebox" id="accountAreaCodeSel" 
							name="accountAreaCode" style="width: 300px;">
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
						<span class="inputDesc">请选择代理商企业网银开户银行信息</span>
					</td>
				</tr>
				<tr>
					<td class="label">开户银行支行：</td>
					<td class="content">
						<input class="easyui-validatebox"
							type="text" style="height:27px; width: 300px;"
							id="accountBankNameInpt" name="accountBankName" />
						<span class="inputDesc">请选择代理商企业网银开户银行信息</span>
					</td>
				</tr>
				<tr>
					<td class="label">开户行联行号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" readonly="readonly"
							id="accountBankCodeInpt" name="accountBankCode" />
					</td>
				</tr>
				<tr>
					<td class="label">清算账号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="50"
							id="accountInpt" name="accountNo" />
						<span class="inputDesc">必填，请输入代理商企业账号</span>
					</td>
				</tr>
				<tr>
					<td class="label">清算账号户名：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="256"
							id="accountNameInpt" name="accountName" />
						<span class="inputDesc">必填，请输入代理商企业网银账户名</span>
					</td>
				</tr>
				<tr>
					<td class="label">分润周期：</td>
					<td class="content">
						<select class="easyui-validatebox" name="profitPeriod" id="profitPeriodSel" required="true">
							<@enumOpts enumNm="ProfitEnums.ProfitPeriod" showEmptyOpt="true" />
						</select>
						<span class="inputDesc">必填，请注意：分润周期后续无法修改</span>
					</td>
				</tr>
				<tr>
					<td class="label">备注：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="256"
							id="commentInpt" name="comment" />
					</td>
				</tr>
			</table>
		</@addDiv>
	</body>
</html>