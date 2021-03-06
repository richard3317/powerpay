<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="代付路由信息" base="routDaifu" qryFuncCode="2000030001"
			addFlg=true addFuncCode="2000030002"
			editFlg=true editFuncCode="2000030004" editUrl='"edit.do?mchntCd=" + sel.mchntCd + "&intTransCd=" + sel.intTransCd + "&chnlId=" + sel.chnlId + "&chnlMchntCd=" + sel.chnlMchntCd+ "&currCd=" + sel.currCd'
			deleteFlg=true deleteFuncCode="2000030006" deleteUrl='"delete.do?mchntCd=" + sel.mchntCd + "&intTransCd=" + sel.intTransCd + "&chnlId=" + sel.chnlId + "&chnlMchntCd=" + sel.chnlMchntCd+ "&currCd=" + sel.currCd' deleteSuccMsg="删除任务已完成">
			<@authCheck funcCode='2000030013'>
				$("#batchAddBtn").click(function() {
					var r=confirm("将进行批量新增，请确认！");
					if (!r) return false;
					var url = _ctx + "/routDaifu/batchAdd.do?" + $("#qryForm").serialize();
					$.jumpTo(url);
				});
			</@authCheck>
			<@authCheck funcCode='2000030011'>
				$("#batchEditBtn").click(function() {
					var r=confirm("所有符合查询条件的项目将进行批量编辑，请确认！");
					if (!r) return false;
					var url = _ctx + "/routDaifu/batchEdit.do?" + $("#qryForm").serialize();
					console.log(url);
					$.jumpTo(url);
				});
			</@authCheck>
			<@authCheck funcCode='2000030012'>
				$("#batchDeleteBtn").click(function() {
					var r=confirm("所有符合查询条件的项目将删除，请确认！");
					if (!r) return false;
					$(this).disable();
					$.ajax({
						type : "POST",
						url : _ctx + "/routDaifu/batchDelete.do",
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
			$("#exportRoutDaifuLog").click(function(){
				var url = _ctx + "/routDaifu/exportRoutDaifuLog.do?" + $("#qryForm").serialize();
				$.jumpTo(url);
			});
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/routDaifu/qry.do" qryFuncCode="2000030001">
			<input type="hidden" id="ifPay" name="_QRY_ifPay" value="0" />
			
			<table class="qry_tbl">
				<tr>
					<td>前端商户号：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="mchntCd" maxLength="15"
							name="_QRY_mchntCd" value="${(qryParamMap.mchntCd)!''}">
					</td>
					<td>交易类型：</td>
					<td>
						<select id="intTransCd" name="_QRY_intTransCd" value="${(qryParamMap.intTransCd)!'52'}" >
							<@enumOpts enumNm="BmEnums.TxnDaifuTypesForSel" selected="${(qryParamMap.intTransCd)!'52'}" showEmptyOpt="false" showCode="true" />
						</select>
					</td>
					<td>状态：</td>
					<td>
						<select id="" name="_QRY_status" data-options="editable:false">
							<@enumOpts enumNm="TxnEnums.CommonValid" selected="${(qryParamMap.status)!''}" showEmptyOpt="true" showCode="true" />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">优先级：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="128"
							id="priority" name="_QRY_priority" value="${(qryParamMap.priority)!''}" />
						<#--  <span class="inputDesc">空白表示不修改</span> -->
					</td>
					<td>渠道：</td>
					<td>
						<select id="chnlId" name="_QRY_chnlId" data-options="editable:false">
							<#-- <@enumOpts enumNm='TxnEnums.ChnlId' showEmptyOpt='true'  
								selected="${(qryParamMap.chnlId)!''}" />-->
							<@enumOpts enumNm='TxnEnums.ChnlId' showCode='true' showEmptyOpt='true' selected="${(qryParamMap.chnlId)!''}" />
						</select>
					</td>
					<td>渠道商户号：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="chnlMchntCd" maxlength="128"
							name="_QRY_chnlMchntCd" value="${(qryParamMap.chnlMchntCd)!''}">
					</td>
					<td>
					<input type="checkbox" id="showBmchnt" style="width:18px;height:18px" checked="true">&nbsp展示关联商户
					</td>
				</tr>
				<tr>
					<td>币别：</td>
					<td>
						<select  name="_QRY_currCd" id="currCd" >
							<@enumOpts enumNm="CurrencyEnums.CurrType" showEmptyOpt="true" showCode="false"
							selected="${(qryParamMap.currCd)!''}" />
						</select>
					</td>
					<td>站点：</td>
					<td>
						<select name="_QRY_siteId" id="siteId" >
							<@enumOpts enumNm="CommonEnums.Site" showEmptyOpt="true" showCode="true" />
						</select>
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv addFlg=true addFuncCode="2000030002"
			editFlg=true editFuncCode="2000030004"
			deleteFlg=true deleteFuncCode="2000030006">
			
			<@authCheck funcCode='2000030013'>
				<a id="batchAddBtn" href="javascript:void(0)" class="easyui-linkbutton" 
				   data-options="iconCls:'icon-add'" >批量新增</a>
			</@authCheck>
			<@authCheck funcCode='2000030011'>
				<a id="batchEditBtn" href="javascript:void(0)" class="easyui-linkbutton" 
				   data-options="iconCls:'icon-edit'" >批量编辑</a>
			</@authCheck>
			<@authCheck funcCode='2000030012'>
				<a id="batchDeleteBtn" href="javascript:void(0)" class="easyui-linkbutton" 
				   data-options="iconCls:'icon-cancel'" >批量删除</a>
				<a id="exportRoutDaifuLog" href="javascript:void(0)" class="easyui-linkbutton"
				   data-options="iconCls:'icon-print'"> 导 出 </a>
			</@authCheck>
			  			
		</@qryResultDiv>
		
	</body>
<html>
	