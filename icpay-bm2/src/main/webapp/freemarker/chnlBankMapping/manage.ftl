<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="渠道银行编号管理" base="chnlBankMapping" qryFuncCode="2000110001"
			detailFlg=true detailDivWidth=800 detailDivHeight=550 detailFuncCode="2000110002"
			detailUrl='"detail.do?intTransCd="+sel.intTransCd +"&bankNum="+sel.bankNum +"&chnlId="+sel.chnlId + "&currCd=" + sel.currCd' 
			addFlg=true addFuncCode="2000110010"
			editFlg=true 
			editUrl='"edit.do?intTransCd="+sel.intTransCd +"&bankNum="+sel.bankNum +"&chnlId="+sel.chnlId + "&currCd=" + sel.currCd'
			editFuncCode="2000110020" 
			deleteFuncCode="2000110030"
			deleteFlg=true 
			deleteUrl='"delete.do?intTransCd="+sel.intTransCd +"&bankNum="+sel.bankNum +"&chnlId="+sel.chnlId + "&currCd=" + sel.currCd'
			>
			<#--  
			$("#backBtn").click(function() {
					$.jumpTo(_ctx + "/chnlBankMapping/backToManage.do");
			});
			-->
			<@authCheck funcCode='2000110040'>
				$("#batchEditBtn").click(function() {
					var r=confirm("所有符合查询条件的项目将进行批量编辑，请确认！");
					if (!r) return false;
					var url = _ctx + "/chnlBankMapping/batchEdit.do?" + $("#qryForm").serialize();
					console.log(url);
					$.jumpTo(url);
				});
			</@authCheck>
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<div id="mngDiv" class="easyui-panel" title="渠道银行编号管理" style="padding: 10px;">
			<@qryDiv qryUrl="/chnlBankMapping/qry.do" qryFuncCode="2000110001">
				<table class="qry_tbl">
					<tr>
						<td class="label">交易類型：</td>
						<td class="content">
							<select class="easyui-validatebox" id="intTransCd" name="_QRY_intTransCd" style="width: 160px;">
								<@enumOpts enumNm="ProfitEnums.ProfitTxnTp" showEmptyOpt="true" showCode="true" 
								  exceptValues="0100,0050,0090" selected="${(qryParamMap.intTransCd)!''}" />
								<#--  <option value="*">*全部*</option> -->
							</select>
						</td>
						<td class="label">银行编号：</td>
						<td class="content">
							<select class="easyui-validatebox"
								id="bankNum" name="_QRY_bankNum" 
								style="width: 200px; height: 27px; ">
								<option value="">--请选择--</option>
								<#list bankCodeList as bank>
									<#if (bank.bankNum==((qryParamMap.bankNum)!''))>
										<option value="${bank.bankNum}" selected="selected">${bank.bankNum}-${bank.bankName}</option>
									<#else>	
										<option value="${bank.bankNum}">${bank.bankNum}-${bank.bankName}</option>
									</#if>	
								</#list>
								<#--  <option value="*">*</option> -->
							</select>
							<#--  <span class="inputDesc">请选择银行编号</span>-->
						</td>
						<td class="label">有效狀態：</td>
						<td class="content">
							<select class="easyui-validatebox" id="state" name="_QRY_state" style="width: 100px;">
								<@enumOpts enumNm="TxnEnums.CommonValid" showEmptyOpt="true" showCode="true" selected="${(qryParamMap.state)!''}" />
							</select>
						</td>
					</tr>
					<tr>
						<td class="label">渠道：</td>
						<td class="content">
							<select class="easyui-validatebox" id="chnlId" name="_QRY_chnlId" style="width: 160px;">
								<@enumOpts enumNm='TxnEnums.ChnlId' showEmptyOpt='true' showCode="true"  selected="${(qryParamMap.chnlId)!''}" />
								<#--  <option value="*">*</option> -->
							</select>
						</td>
						<td>渠道银行编号：</td>
						<td class="content">
							<input class="easyui-validatebox" type="text" id="chnlBankNum" maxLength="12"  style="width: 200px;"
								name="_QRY_chnlBankNum" value="${(qryParamMap.chnlBankNum)!''}">
						</td>
						<td>渠道银行名称：</td>
						<td>
							<input class="easyui-validatebox" type="text" id="chnlBankName" maxLength="30" style="width: 200px;"
								name="_QRY_chnlBankName" value="${(qryParamMap.chnlBankName)!''}">
						</td>
						<td>币别：</td>
						<td>
							<select  name="_QRY_currCd" id="currCd" >
									<@enumOpts enumNm="CurrencyEnums.CurrType" showEmptyOpt="true" showCode="false"
									selected="${(qryParamMap.currCd)!'156'}" />
							</select>
						</td>
					</tr>
				</table>
			</@qryDiv>
			<@qryResultDiv  
				addFlg=true addFuncCode="2000110010"
				detailFlg=true detailFuncCode="2000110002"
				editFlg=true editFuncCode="2000110020"
				deleteFlg=true deleteFuncCode="2000110030">
				<#--  
				<a id="backBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-back'" >返回</a>
				-->
				<@authCheck funcCode='2000110040'>
					<a id="batchEditBtn" href="javascript:void(0)" class="easyui-linkbutton" 
					   data-options="iconCls:'icon-edit'" >批量编辑</a>
				</@authCheck>
			</@qryResultDiv>
			
			<div id="detailDiv"></div>
		</div>	
	</body>
<html>