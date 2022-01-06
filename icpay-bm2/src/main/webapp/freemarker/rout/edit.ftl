<#include "/common/edit_macro.ftl" />
<#assign label = "支付路由信息">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="rout" editSuccMsg="">
			var status=$("[name=statusHid]").val();
			var options=document.getElementById('statusSel').children;
			if(status=="0"){
				options[0].selected ="true";
			}else {
				options[1].selected ="true";
			}
		</@head>
	</head>
	
	<body>
		<@editDiv label="${label}" editUrl="/rout/edit/submit.do">
			<#-- <input type="hidden" name="statusHid" value="${entity.status}" /> -->
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">前端商户号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="15"
							id="mchntCdInpt" name="mchntCd" value="${entity.mchntCd}"  readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td class="label">渠道：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="15"
							id="mchntCdInpt" name="chnlId" value="${entity.chnlId}"  readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td class="label">渠道商户号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40"
							id="mchntCdInpt" name="chnlMchntCd" value="${entity.chnlMchntCd}"  readonly="readonly" />
						<input class="easyui-validatebox" type="text" maxlength="40"
							id="currCd" name="currCd" value="${entity.currCd}"  readonly="readonly" />

					</td>
				</tr>
				<tr>
					<td class="label">交易类型：</td>
					<td class="content">
					<input class="easyui-validatebox" type="text" maxlength="15"
							id="mchntCdInpt" name="intTransCd" value="${entity.intTransCd}"  readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td class="label">优先级：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="128"
							id="" name="priority" value="${entity.priority}"/>
					</td>
				</tr>
				<tr>
					<td class="label">权重：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="128"
							id="" name="weight" value="${entity.weight}"/>
					</td>
				</tr>
				<tr>
					<td class="label">状态：</td>
					<td class="content">
						<select id="statusSel" name="status" data-options="editable:false">
							<option value="">--请选择---</option>
							<option value="1">开启</option>
							<option value="0">关闭</option>
						</select>
					</td>
				</tr>
			</table>
		</@editDiv>
	</body>
</html>