<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="小商户资金池配置" base="merCashPool" qryFuncCode="2001030001"
			addFlg=true addFuncCode="2001030002"
			editFlg=true editFuncCode="2001030004" editUrl='"edit.do?poolId=" + sel.poolId + "&mchntCd=" + sel.mchntCd + "&currCd=" + sel.currCd'
			deleteFlg=true deleteFuncCode="2001030006" deleteUrl='"delete.do?poolId=" + sel.poolId + "&mchntCd=" + sel.mchntCd + "&currCd=" + sel.currCd'
			>
			<@authCheck funcCode='2001030007'>
			$("#batchAddBtn").click(function() {
				var r=confirm("将进行批量新增，请确认！");
				if (!r) return false;
				var url = _ctx + "/merCashPool/batchAdd.do?" + $("#qryForm").serialize();
				$.jumpTo(url);
			});
			</@authCheck>
			<@authCheck funcCode='2001030009'>
			$("#batchEditBtn").click(function() {
					var r=confirm("所有符合查询条件的项目将进行批量编辑，请确认！");
					if (!r) return false;
					var url = _ctx + "/merCashPool/batchEdit.do?" + $("#qryForm").serialize();
					console.log(url);
					$.jumpTo(url);
			});
			</@authCheck>
			<@authCheck funcCode='2001030011'>
			$("#batchDeleteBtn").click(function() {
				var r=confirm("所有符合查询条件的项目都将删除，请务必确认！");
				if (!r) return false;
				$(this).disable();
				$.ajax({
					type : "POST",
					url : _ctx + "/merCashPool/batchDelete.do",
					async: true,
					cache:false,
					success : function(data) {
						var res = parseJson(data);
						$("#batchDeleteBtn").enable();
						if (!checkEmpty(res, "系统异常，请联系管理员！")) return false;
						alert(res.respMsg);
						queryPage();
					},
					error:function(){
				    	$("#batchDeleteBtn").enable();
				    	alert("系统异常，请联系管理员！");
				    	return  false;
				    }
				});
			});
			</@authCheck>
			
		</@head>
	</head>

	<body style="padding: 5px;">
		<@qryDiv qryUrl="/merCashPool/qry.do" showBtn=true  qryFuncCode="2001030001">
		<table class="qry_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td>商户号：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="mchntCdInpt" maxLength="15"
							name="_QRY_mchntCd" value="${(qryParamMap.mchntCd)!''}">
					</td>
					<td>商户名称：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="mchntNameInpt" maxLength="15"
							name="_QRY_mchntName" value="${(qryParamMap.mchntName)!''}">
					</td>
					<#--  <td class="label">资金池ID：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="120"
							id="poolId" name="_QRY_poolId"  value="${(qryParamMap.poolId)!''}"/>
					</td>-->
					<td>资金池名称：</td>
					<td>
						<select class="easyui-validatebox"
							id="poolId" name="_QRY_poolId" 
							style="width: 200px; height: 27px; ">
							<option value="">--请选择--</option>
							<#list poolList as lst>
								<#if (lst.poolId==((qryParamMap.poolId)!''))>
									<option value="${lst.poolId}" selected="selected">${lst.poolDesc}-${lst.currCd}</option>
								<#else>	
									<option value="${lst.poolId}">${lst.poolDesc}-${lst.currCd}</option>
								</#if>	
							</#list>
						</select>
					</td>
					<td class="label">状态：</td>
					<td class="content">
						<select id="" name="_QRY_state" data-options="editable:false">
							<option value="">--请选择--</option>
							<option value="0">0-无效</option>
							<option value="1">1-有效</option>
						</select>
					</td>
					<td>币别：</td>
					<td>
						<select  name="_QRY_currCd" id="currCd" >
							<@enumOpts enumNm="CurrencyEnums.CurrType" showEmptyOpt="true" showCode="false"
							selected="${(qryParamMap.currCd)!''}" />
						</select>
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv
			addFlg=true addFuncCode="2001030002"
			editFlg=true editFuncCode="2001030004"
			deleteFlg=true deleteFuncCode="2001030006">
			<@authCheck funcCode='2001030007'>
				<a id="batchAddBtn" href="javascript:void(0)" class="easyui-linkbutton" 
				   data-options="iconCls:'icon-add'" >批量新增</a>
			</@authCheck>
			<@authCheck funcCode='2001030009'>
				<a id="batchEditBtn" href="javascript:void(0)" class="easyui-linkbutton" 
				   data-options="iconCls:'icon-edit'" >批量编辑</a>
			</@authCheck>
			<@authCheck funcCode='2001030011'>
				<a id="batchDeleteBtn" href="javascript:void(0)" class="easyui-linkbutton" 
				   data-options="iconCls:'icon-cancel'" >批量删除</a>
			</@authCheck>  	
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
	</body>
</html>