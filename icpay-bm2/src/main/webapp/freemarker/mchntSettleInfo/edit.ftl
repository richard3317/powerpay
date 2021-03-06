<#include "/common/edit_macro.ftl" />
<#assign label = "商户清算账户信息">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="mchntSettleInfo">
			$("#settleAccountInpt").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: ['number','maxLength[50]']
			});
			$("#settleAccountNameInpt").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[256]'
			});
			$("#settleLimitInpt").validatebox({
				novalidate: true,
				delay: 1000,
				validType: ['amount', 'maxLength[15]']
			});
			$("#commentInpt").validatebox({
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[256]'
			});
			$("#settleAccountBankCodeInpt").validatebox({
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
			$("#settleAccountAreaCodeSel").validatebox({
				required: true,
				novalidate: true
			});
			$("#provSel").val('${provCd!''}').change(function() {
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
			$("#citySel").val('${cityCd!''}').change(function() {
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
			$("#settleAccountAreaCodeSel").val('${(entity.settleAccountAreaCode)!''}');
			$('#bankNameSel').val('${bankName!''}').combobox({
				required: true,
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
		<@editDiv label="${label}" editUrl="/mchntSettleInfo/edit/submit.do">
			<input type="hidden" maxlength="15" name="mchntCd" value="${entity.mchntCd}" />
			<input type="hidden" maxlength="15" name="currCd" value="${entity.currCd}" />
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">商户代码：</td>
					<td class="content">
						${entity.mchntCd}
					</td>
				</tr>
				<tr>
					<td class="label">币别：</td>
					<td class="content">
						${entity.currCdDesc}
					</td>
				</tr>
				<#-- <tr>
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
							<#if cityLst??>
								<#list cityLst as c>
									<option value="${c.regionCd}">${c.regionCd}-${c.regionCnNm}</option>
								</#list>
							</#if>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">开户区县：</td>
					<td class="content">
						<select class="easyui-validatebox" id="settleAccountAreaCodeSel" 
							name="settleAccountAreaCode" style="width: 300px;" required="true">
							<option value="">--请选择--</option>
							<#if districtLst??>
								<#list districtLst as c>
									<option value="${c.regionCd}">${c.regionCd}-${c.regionCnNm}</option>
								</#list>
							</#if>
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
							id="settleAccountBankNameInpt" name="settleAccountBankName" value="${entity.settleAccountBankName}" />
						<span class="inputDesc">请选择企业网银开户银行信息</span>
					</td>
				</tr>
				<tr>
					<td class="label">开户行联行号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" readonly="readonly"
							id="settleAccountBankCodeInpt" name="settleAccountBankCode" value="${entity.settleAccountBankCode}" />
					</td>
				</tr>
				<tr>
					<td class="label">清算账号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="50"
							id="settleAccountInpt" name="settleAccount" value="${entity.settleAccount}" />
					</td> 
				</tr> -->
				<tr>
					<td class="label">清算账号户名：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="256"
							id="settleAccountNameInpt" name="settleAccountName" value="${entity.settleAccountName}" />
					</td>
				</tr>
				<#-- --> 
				<tr>
					<td class="label">结算方式：</td>
					<td class="content">
						<select id="settlePeriodSel" name="settlePeriod">
							<@enumOpts enumNm='SettleEnums.SettlePeriod' selected=entity.settlePeriod showEmptyOpt='false' showCode="false" />
						</select>
					</td>					
				</tr>
				
				<#-- 
				<tr>
					<td class="label">清算周期：</td>
					<td class="content">
						<select id="settlePeriodSel" name="settlePeriod" value ="${entity.settlePeriod}" data-options="editable:true">
							<option value="9">支持D0</option>
							<option value="0">支持T0</option>
							<option value="1">支持T1</option>
							<option value="2">支持T2</option>
							<option value="3">支持T3</option>
						</select>
					</td>
				</tr>
				 -->
				<#if entity.settlePeriod == '0'>
					<tr id="settleLimitTr">
						<td class="label">当日最大清算金额：</td>
						<td class="content">
							<input class="easyui-validatebox" type="text" maxlength="14"
								id="settleLimitInpt" name="settleLimit" value="${(entity.settleLimit)!''}" />(元)
							<span class="inputDesc">T+0日清算时填写</span>
						</td>
					</tr>
				</#if>
				<tr>
					<td class="label">自动D0余额结转：</td>
					<td class="content">
						<select class="easyui-validatebox" id="balanceTransferSel" name="balanceTransfer" value ="${entity.balanceTransfer!'0'}" style="width: 300px;">
							<@enumOpts enumNm='TxnEnums.CommonYesNo' selected=entity.balanceTransfer!'0' showEmptyOpt='false' showCode="false" />
						</select>
					</td>					
				</tr>
				<tr id="transferTimeTr">
					<td class="label">D0余额结转时间：</td>
					<td class="content">
						<input class="easyui-validatebox" type ="text" value="${entity.transferTime!''}" id="balanceTransDateInpt" name ="transferTime"/>
						<span class="inputDesc">格式HHmmss，例如：235000(可省略，使用默认值)</span>
					</td>
				</tr>
				<tr>
					<td class="label">自动T1余额结转反还：</td>
					<td class="content">
						<select class="easyui-validatebox" id="balanceTransferT1Sel" name="balanceTransferT1" value ="${entity.balanceTransferT1!'0'}" style="width: 300px;">
							<@enumOpts enumNm='TxnEnums.CommonYesNo' selected=entity.balanceTransferT1!'0' showEmptyOpt='false' showCode="false" />
						</select>
					</td>
				</tr>				
				<tr id="preTransferTimeT1Tr">
					<td class="label">第一次T1结转时间：</td>
					<td class="content">
						<input class="easyui-validatebox" type ="text" value="${entity.preTransferTimeT1!''}" id="preTransferTimeT1Inpt" name ="preTransferTimeT1"/>
						<span class="inputDesc">格式HHmmss，空值表示不進行前置T1结转</span>
					</td>
				</tr>
				<tr id="preTransferT1PercentTr">
					<td class="label">第一次T1结转比例：</td>
					<td class="content">
						<input class="easyui-validatebox" type ="text" value="${entity.preTransferT1Percent!''}" id="preTransferT1PercentInpt" name ="preTransferT1Percent"/>
						<span class="inputDesc">格式：1 表示 100%,  0.9 表示 90%</span>
					</td>
				</tr>
				<tr id="transferTimeT1Tr">
					<td class="label">第二次T1余额结转反还时间：</td>
					<td class="content">
						<input class="easyui-validatebox" type ="text" value="${entity.transferTimeT1!''}" id="balanceTransDateInpt" name ="transferTimeT1"/>
						<span class="inputDesc">格式HHmmss，例如：163000(可省略，使用默认值)</span>
					</td>
				</tr>
				<tr>
					<td class="label">备注：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="255"
							id="commentInpt" name="comment" value="${entity.comment!''}"/>
					</td>
				</tr>
			</table>
		</@editDiv>
	</body>
</html>