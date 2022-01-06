<#include "/common/add_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="代付路由信息" base="routDaifu" addSuccMsg="">
			$("#mchntCdInpt").validatebox({
				required: true,
				novalidate: true,
				<#-- validType: ['numberOrStar','lengthOrStar[15]'] -->
				validType: ['maxLength[254]']
			});
			$("#intTransCdSel").validatebox({
				required: true,
				novalidate: true,
				validType: ['maxLength[8]']
			});
			$("#chnlId").validatebox({
				required: true,
				novalidate: true,
				validType: ['maxLength[8]']
			});
			$("#priorityInp").validatebox({
				required: true,
				novalidate: true,
				validType: ['maxLength[4]']
			});
			$("#statusInp").validatebox({
				required: true,
				novalidate: true,
				validType: ['maxLength[4]']
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
				//$("#chnlMchntCd").addOption("*","*全部*");
			}
			
			function onSelectedChnlIdChanged(selectedCh) {
				$("#chnlMchntCd").clearOptions(false);
				$.ajax({
					type : "GET",
					url : _ctx + "/getChnlMchnts.do?chnlId="+selectedCh,
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
		<@addDiv label="路由信息" addUrl="/routDaifu/add/submit.do">
			<input type="hidden" id="weightInp" name="weight" value="100" />
			
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">前端商户号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="15"
							id="mchntCdInpt" name="mchntCd" />
					</td>
				</tr>
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
				        <span class="inputDesc"></span>
				        <select  name="currCd" id="currCd" >
								<@enumOpts enumNm="CurrencyEnums.CurrType" showEmptyOpt="true" showCode="false"
								selected="${(qryParamMap.currCd)!''}" />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">交易类型：</td>
					<td class="content">
						<select id="intTransCdSel" name="intTransCd" data-options="editable:false">
							<@enumOpts enumNm="ProfitEnums.ProfitTxnDaifuTp" showEmptyOpt="false" showCode="true" />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">优先级：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="128"
							id="priorityInp" name="priority" value="50" />
					</td>
				</tr>
				<#--  
				<tr>
					<td class="label">权重：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="128"
							id="" name="weight" />
					</td>
				</tr>
				-->
				<tr>
					<td class="label">状态：</td>
					<td class="content">
						<select id="statusInp" name="status" data-options="editable:false">
							<option value="">--请选择---</option>
							<option value="1" selected='selected'>开启</option>
							<option value="0">关闭</option>
						</select>
					</td>
				</tr>
			</table>
		</@addDiv>
	</body>
</html>