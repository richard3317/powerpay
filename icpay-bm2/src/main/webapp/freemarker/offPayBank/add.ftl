<#include "/common/add_macro.ftl" />
<#assign label = "电汇收款卡管理">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="offPayBank">
			$("#chnlId").validatebox({
				required: true,
				novalidate: true,
				validType: ['maxLength[8]']
			});
			$("#bankNameInpt").validatebox({
				required: true,
				novalidate: true,
				validType: 'cnMaxLength[120]'
			});
			$("#accNoInpt").validatebox({
				required: true,
				novalidate: true,
				validType: 'cnMaxLength[120]'
			});
			$("#accNameInpt").validatebox({
				required: true,
				novalidate: true,
				validType: 'cnMaxLength[120]'
			});
			
			var lastSelectedItem=null;
			$('#chnlId').on('change', function(){
				var selectedItem= $('#chnlId').val();
				if (isEmpty(selectedItem))
					onSelEmptyChnlId();
				else {
					if (lastSelectedItem != selectedItem)
						onSelectedChnlIdChanged(selectedItem);
				}
				lastSelectedItem = selectedItem;
			});
			
			function onSelEmptyChnlId() {
				$("#chnlMchntCd").clearOptions(false);
			}
			
			function onSelectedChnlIdChanged(selectedCh) {
				$("#chnlMchntCd").clearOptions(false);
				$.ajax({
					type : "GET",
					url : _ctx + "/getChnlMchnts.do?chnlId="+selectedCh,
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
							  var desc  = item.chnlMchntDesc+"|"+item.chnlMchntCd;
							  item.chnlMchntDesc = desc;
							}
						}					
						$('#chnlMchntCd').combobox({
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
			
		</@head>
	</head>
	
	<body>
		<@addDiv label="${label}" addUrl="/offPayBank/add/submit.do">
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">渠道：</td>
					<td class="content">
						<select id="chnlId" name="chnlId">
							<@enumOpts enumNm='TxnEnums.ChnlId' showEmptyOpt='true' showCode="true" />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">渠道商户号：</td>
					<td class="content">
						<select class="easyui-validatebox" style="width: 550px; height: 27px;"
							id="chnlMchntCd" name="chnlMchntCd">
							<option value=""></option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">户名：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="120"
							id="accNameInpt" name="accName" />
					</td>	
				</tr>
					<td class="label">银行卡号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="120"
							id="accNoInpt" name="accNo" />
					</td>	
				</tr>
				<tr>
					<td class="label">银行名称：</td>
					<td class="content">
						<select class="easyui-validatebox"
							id="bankNameInpt" name="bankName" 
							style="width: 300px; height: 27px; ">
							<option value="">--请选择--</option>
							<#list bankCodeList as bank>
								<option value="${bank.bankName}">${bank.bankName}</option>
							</#list>
						</select>
					</td>
				</tr>				
				<tr>
					<td class="label">开户行：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="100"
							id="accountBankNameInpt" name="accountBankName" />
					</td>	
				</tr>
				<tr>
					<td class="label">状态：</td>
					<td class="content">
						<select id="statelSel" name="state" data-options="editable:false">
							<@enumOpts enumNm='BmEnums.UserSt' showCode='true' 
								showEmptyOpt="false" selected="1" />
						</select>
					</td>
				</tr>
			</table>
		</@addDiv>
	</body>
</html>