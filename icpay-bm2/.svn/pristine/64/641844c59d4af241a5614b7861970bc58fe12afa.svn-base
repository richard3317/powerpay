<#include "/common/add_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="路由信息" base="routDaifu" addSuccMsg="新增路由任务已提交，请等待审核">
		$("#mchntCdInpt").validatebox({
			required: true,
			novalidate: true,
			validType: ['numberOrStar','lengthOrStar[15]']
		});
		$("#termSnRegexInpt").validatebox({
			required: true,
			novalidate: true,
			validType: ['maxLength[128]']
		});
		</@head>	
	</head>
	
	<body>
		<@addDiv label="路由信息" addUrl="/routDaifu/add/submit.do">
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
						<select id="chnlIdSel" name="chnlId">
							<@enumOpts enumNm='TxnEnums.ChnlId' showEmptyOpt='false' showCode="true" />
						</select>
						<#--<input class="easyui-validatebox" type="text" maxlength="15"
							id="mchntCdInpt" name="chnlId" />
						<select id="intTransCdSel" name="chnlId" data-options="editable:false">
							<option value="">--请选择---</option>
							<option value="*">*-全部</option>
							<@enumOpts enumNm='TxnEnums.RoutTxnType' showCode='true' 
								showEmptyOpt='false' selected="${(qryParamMap.intTransCd)!''}" />
						</select>-->
					</td>
				</tr>
				<tr>
					<td class="label">渠道商户号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40"
							id="mchntCdInpt" name="chnlMchntCd" />
						<#--	<select id="intTransCdSel" name="chnlMchntCd" data-options="editable:false">
							<option value="">--请选择---</option>
							<option value="*">*-全部</option>
							<@enumOpts enumNm='TxnEnums.RoutTxnType' showCode='true' 
								showEmptyOpt='false' selected="${(qryParamMap.intTransCd)!''}" />-->
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">交易类型：</td>
					<td class="content">
						<select id="intTransCdSel" name="intTransCd" data-options="editable:false">
							<option value="5210">代付交易</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">优先级：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="128"
							id="" name="priority" />
					</td>
				</tr>
				<tr>
					<td class="label">权重：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="128"
							id="" name="weight" />
					</td>
				</tr>
				<tr>
					<td class="label">状态：</td>
					<td class="content">
						<select id="" name="status" data-options="editable:false">
							<option value="2">--请选择---</option>
							<option value="1">开启</option>
							<option value="0">关闭</option>
						</select>
					</td>
				</tr>
			</table>
		</@addDiv>
	</body>
</html>