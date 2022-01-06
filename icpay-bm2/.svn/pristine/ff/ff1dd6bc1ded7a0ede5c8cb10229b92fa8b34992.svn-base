<#include "/common/add_macro.ftl" />
<#assign label = "代理商银行帐户新增">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="agentProfitPolicy">
			$("#agentCd").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[30]'
			});
			$("#intTransCd").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[4]'
			});
			$("#mchntCd").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[40]'
			});
			$("#chnlId").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[2]'
			});
			$("#chnlMchntCd").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[40]'
			});
			$("#comment").validatebox({
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[256]'
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
				$("#chnlMchntCd").addOption("*","*全部*");
			}
			
			function onSelectedChnlIdChanged(selectedCh) {
				$("#chnlMchntCd").clearOptions(false);
				$.ajax({
					type : "GET",
					url : _ctx + "/agentProfitPolicy/getChnlMchnts.do?chnlId="+selectedCh,
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
						setupChnlMchnts(res.respData.chnlMchntList);	
					},
					error:function(){
				    	alert("系统异常，请联系管理员！");
				    	return  false;
				    }
				});				
			}
			
			function setupChnlMchnts(list){
				//console.log(list);
				var objSel = $("#chnlMchntCd");
				if (! isEmpty(list)) {
					for (var i = 0, len = list.length; i < len; i++) {
					  var item = list[i];
					  var val = item.chnlMchntCd;
					  var desc  = item.chnlMchntDesc+"|"+item.chnlMchntCd;
					  objSel.addOption(val,desc);
					}
				}
				objSel.addOption("*","*全部*");
			}			
			
		</@head>
	</head>
	
	<body>
		<@addDiv label="${label}" addUrl="/agentProfitPolicy/add/submit.do">
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">代理商代码：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="30"
							id="agentCd" name="agentCd" value="${agentCd!''}" />
						<span class="inputDesc">代理商(或中人)编号</span>
					</td>
				</tr>
				<tr>
					<td class="label">交易類型：</td>
					<td class="content">
						<select class="easyui-validatebox" id="intTransCd" name="intTransCd" style="width: 200px;">
							<@enumOpts enumNm="ProfitEnums.ProfitTxnTp" showEmptyOpt="true" showCode="true" />
							<option value="*">*全部*</option>
						</select>
						<span class="inputDesc">輸入'*'表示全部</span>
					</td>
				</tr>
				<tr>
					<td class="label">前端商户号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="15"
							id="mchntCd" name="mchntCd" />
						<span class="inputDesc">輸入'*'表示全部</span>
					</td>
				</tr>
				<tr>
					<td class="label">渠道：</td>
					<td class="content">
						<select class="easyui-validatebox" id="chnlId" name="chnlId" style="width: 200px;">
							<@enumOpts enumNm='TxnEnums.ChnlId' showEmptyOpt='true' showCode="true" />
							<option value="*">*全部*</option>
						</select>
						<span class="inputDesc">请选择渠道編號</span>
					</td>
				</tr>
				<#--  
				<tr>
					<td class="label">渠道商户号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40"
							id="chnlMchntCd" name="chnlMchntCd" />
						<span class="inputDesc">輸入'*'表示全部</span>
					</td>
				</tr>
				-->
				<tr>   
					 <td class="label">渠道商户号：</td>
				    <td class="content">
						<select class="easyui-validatebox" style="width: 300px; height: 27px;"
							id="chnlMchntCd" name="chnlMchntCd">
							<option value="">--请选择--</option>
						</select>
				        <span class="inputDesc"></span>
				    </td>
				</tr>
				<tr>
					<td class="label">扣率：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="12"
							id="rate" name="rate" value="0.00"/>
						<span class="inputDesc">代理商扣率, 小数点, 例如: 0.0035</span>
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 135px;">封顶手续费：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" id="maxFeeInpt" name="maxFeeDesc" />(元)
						<span class="inputDesc">封顶手续费, 單位：元, 自營代理商必填</span>
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 135px;">保底手续费：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" id="minFeeInpt" name="minFeeDesc" />(元)
						<span class="inputDesc">保底手续费, 單位：元, 自營代理商必填</span>
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