<#include "/common/edit_macro.ftl" />
<#assign label = "修改渠道商户信息">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="virtualTerm">
			$("#termIdInpt").validatebox({
				required: true,
				novalidate: true,
				validType: ['characters','maxLength[20]']
			});
			$("#chnlMchntNmInpt").validatebox({
				required: true,
				novalidate: true,
				validType: ['maxLength[100]']
			});
			$("#feeRateInpt").validatebox({
				novalidate: true,
				required: true,
				validType: ['rate','maxLength[7]']
			});
			$("#dailyLimitInpt").validatebox({
				novalidate: true,
				validType: ['amount','maxLength[15]']
			});
			var state=$("[name=stateCheck]").val();
			var options=document.getElementById('stateSel').children;
			if(state=="0"){
				options[0].selected ="true";
			}else {
				options[1].selected ="true";
			}

			$("#commentInpt").validatebox({
				validType: ['maxLength[256]']
			});
			
		</@head>
	</head>
	
	<body>
		<@editDiv label="${label}" editUrl="/virtualTerm/edit/submit.do">
			<input type="hidden" name="chnlId" value="${entity.chnlId}" />
			<input type="hidden" name="stateCheck" value="${entity.state}" />
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">渠道：</td>
					<td class="content">
						<input  type="text" maxlength="100"
							id="chnlIdSel" name="chnlIdDesc"
							 value="<@enumVal enumNm="TxnEnums.ChnlId" code="${entity.chnlId}" showCode="true" />"
							 readonly="readonly"  />
					</td>
				</tr>
				<tr>
					<td class="label">渠道商户代码：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40"
							id="chnlMchntCdInpt" name="chnlMchntCd" value="${entity.chnlMchntCd}" readonly="readonly"  />
					</td>
				</tr>
				<tr>
					<td class="label">渠道商户名称：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="100"
							id="chnlMchntDescInpt" name="chnlMchntDesc" value="${entity.chnlMchntDesc}"/>
					</td>
				</tr>
				
				<tr>
					<td class="label">状态：</td>
					<td class="content">
						<select id="stateInput" name="state" value="${entity.state}" data-options="editable:true">
							<@enumOpts enumNm='TxnEnums.CommonValid' selected=entity.state showEmptyOpt='false' showCode="false" />
						</select>
					</td>
				</tr>
				
				<tr id="mchntSuffixTr">
					<td class="label">B商户后坠名：</td>
					<td class="content">
						<input class="easyui-validatebox" type ="text" value="${entity.mchntSuffix!''}"  id="mchntSuffixInpt" name ="mchntSuffix"/>
						<span class="inputDesc">例如：$b (可省略，表示默认值)</span>
					</td>
				</tr>
				
				<tr>
					<td class="label">备注：</td>
					<td class="content">
						<input class="easyui-validatebox" type ="text" id="commentInpt" name ="comment" value="${entity.comment!''}"/>
					</td>
				</tr>
				
			</table>
			
		</@editDiv>
	</body>
</html>