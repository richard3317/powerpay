<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
			<@head label="投诉管理" base="complaintManage" qryFuncCode="9600010001"
				addFlg=true addFuncCode="9600010003"
				detailFlg=true detailUrl='"detail.do?id=" + sel.comId' detailFuncCode="9600010002"
				editFlg=true editUrl='"edit.do?id=" + sel.comId' editFuncCode="9600010005"
				 deleteFlg=true deleteUrl='"delete.do?id=" + sel.comId'   deleteFuncCode="9600010007">
                var $win=$('#win').window({
					collapsible:false,
					minimizable:false,
					maximizable:false,
                });
	<@authCheck funcCode='9600010008'>

				$("#exportBtn").click(function(){
					var qryStr=$("#qryForm").serialize();
					var url = _ctx + "/complaintManage/export.do?"+qryStr;
					$.jumpTo(url);
				});

</@authCheck>
<@authCheck funcCode="9600010010">
			<#--按钮点击处理记录，显示窗口-->
				$("#dealBtn").click(function(){
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
						return false;
					}
                	$win.window('open');
				});
</@authCheck>
			<#--弹出框确定事件-->
				$("#sub_bu").click(function(){
					var sel = $("#qryResultTbl").datagrid("getSelected");
					$.ajax({
						url: _ctx + "/complaintManage/edit/dealSubmit.do",
						type:"post",
						data:{"comId":sel.comId,"dealHistory":$("#dealHistory").val()},
						success:function(data){
							window.location.reload(true);
						}
					});
					<#--关闭窗口-->
                	$win.window("close");
				});

				$("#sub_bu1").click(function(){
					<#--关闭窗口-->
                	$win.window("close");
				});


			$("#clrBtn").click(function() {
			//alert('点击了清除按钮')
				$("#mchntCnNm").val("");
				$("#mchntCd").val("");
				$("#chnlId").val("-1");
				$("#chnlMchntDesc").val("");
				$("#chnlMchntCd").val("");
				$("#upDealWith").val("-1");
				$("#recCrtTsStart").val("");
				$("#recCrtTsEnd").val("");
				$("#processState").val("-1");
				$("#processResult").val("-2");
				$("#siteDesc").val("-1");
			});
			</@head>

</head>

	<body style="padding: 5px;">
	<@qryDiv qryUrl="/complaintManage/qry.do" qryFuncCode="9600010001">

			<table class="qry_tbl">
				<tr>
					<td>平台订单号：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="terraceTransId"
							   name="_QRY_terraceTransId" value="${(qryParamMap.terraceTransId)!''}">
					</td>
					<td>商户名称：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="mchntCnNm"
							name="_QRY_mchntCnNm" value="${(qryParamMap.mchntCnNm)!''}">
					</td>
					<td>商户编号：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="mchntCd"
							name="_QRY_mchntCd" value="${(qryParamMap.mchntCd)!''}">
					</td>

				</tr>
				<tr>
					<td>渠道名称：</td>
					<td>
						<select id="chnlId" name="_QRY_chnlId" value="${(qryParamMap.chnlId)!''}">
							<@enumOpts enumNm='TxnEnums.ChnlId' showCode='true' showEmptyOpt='true' selected="${(qryParamMap.chnlId)!''}" />
						</select>
					</td>
					<td>渠道商户名称：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="chnlMchntDesc"
							name="_QRY_chnlMchntDesc" value="${(qryParamMap.chnlMchntDesc)!''}">
					</td>
					<td>渠道商户编号：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="chnlMchntCd"
							name="_QRY_chnlMchntCd" value="${(qryParamMap.chnlMchntCd)!''}">
					</td>

				</tr>
				<tr>
					<td>站点：</td>
					<td>
						<select name="_QRY_siteDesc" id="siteDesc">
							<@enumOpts enumNm='ComplaintEnums.SiteDesc' showCode='true' showEmptyOpt='true' selected="${(qryParamMap.siteDesc)!''}" />
						</select>
					</td>
					<td>上游处理类型：</td>
					<td>
						<select name="_QRY_upDealWith" id="upDealWith">
							<@enumOpts enumNm='ComplaintEnums.UpDealWith' showCode='true' showEmptyOpt='true' selected="${(qryParamMap.upDealWith)!''}" />
						</select>
					</td>
					<td>接诉日期：</td>
					<td>
						<input class="easyui-validatebox" type="date" id="recCrtTsStart"
							   name="_QRY_recCrtTsStart" value="${(qryParamMap.recCrtTsStart)!''}">
					--
						<input class="easyui-validatebox" type="date" id="recCrtTsEnd"
							   name="_QRY_recCrtTsEnd" value="${(qryParamMap.recCrtTsEnd)!''}">
					</td>
				</tr>
				<tr>
					<td>处理状态：</td>
					<td>
						<select name="_QRY_processState" id="processState">
							<@enumOpts enumNm='ComplaintEnums.ProcessState' showCode='true' showEmptyOpt='true' selected="${(qryParamMap.processState)!''}" />
						</select>
					</td>
					<td>处理结果：</td>
					<td>
						<select name="_QRY_processResult" id="processResult">
							<@enumOpts enumNm='ComplaintEnums.ProcessResult' showCode='true' showEmptyOpt='true' selected="${(qryParamMap.processResult)!''}" />
						</select>
					</td>
					<td></td>
					<td></td>
				</tr>
			</table>
		</@qryDiv>
	<#--弹窗地址-->
	<div id="win" class="easyui-window" title="添加处理记录" closed="true" style="padding:10px;width:300px;height:150px;">
		<table>
			<tr>
				<td>处理记录</td>
				<td><textarea rows="" cols="" name="dealHistory" id="dealHistory" style="width:200px; height:50px;"></textarea></td>
			</tr>
			<tr>
				<td></td>
				<td><a id="sub_bu" class="easyui-linkbutton">确定</a>
                    <a id="sub_bu1" class="easyui-linkbutton">取消</a></td>
			</tr>
		</table>
	</div>
	<@qryResultDiv 
		 	addFlg=true addFuncCode="9600010003"
			editFlg=true editFuncCode="9600010005"
			deleteFlg=true deleteFuncCode="9600010007"
			detailFlg=true detailFuncCode="9600010002"
			 > 
			 
			<#-- <a id="exportBtn" class="easyui-linkbutton">导出</a>
			 	<a id="dealBtn" class="easyui-linkbutton">添加处理记录</a> --> 
		 
			 <@authCheck funcCode="9600010008">
				<a id="exportBtn" href="javascript:void(0)" class="easyui-linkbutton">导出</a>
			</@authCheck>
			 
			 <@authCheck funcCode="9600010010">
				<a id="dealBtn" href="javascript:void(0)" class="easyui-linkbutton">添加处理记录</a>
			</@authCheck>
			
		</@qryResultDiv>
		

		<div id="detailDiv"></div>
	</body>
</html>