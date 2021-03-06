<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>渠道虚拟终端信息-清算信息管理</title>
		<#include "/common/include.ftl">
		<script type="text/javascript">
			var dealFlag = false;
			$(function() {
				$('#dg').datagrid({
					height: 350,
					singleSelect: true
				});
				$("#addBtn").click(function() {
					$.jumpTo(_ctx + "/virtualTerm/settlePolicy/add.do?chnlId=" + $("#chnlIdHid").val()+"&chnlMchntCd=" + $("#chnlMchntCdHid").val());
				});
				$("#backBtn").click(function() {
					$.jumpTo(_ctx + "/virtualTerm/backToManage.do");
				});
				$("#updBtn").click(function() {
					var sel = $('#dg').datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						var chnlId=$("#chnlIdHid").val();
						var currCd=sel.currCdDesc.split('-')[0];
						var chnlMchntCd=$("#chnlMchntCdHid").val();
						var url = _ctx + "/virtualTerm/settlePolicy/upd.do?chnlId=" + chnlId + 
												"&chnlMchntCd=" + chnlMchntCd+"&currCd=" + currCd;
						$.jumpTo(url);
					}
				});
				$("#delBtn").click(function() {
					var sel = $('#dg').datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						var chnlId=$("#chnlIdHid").val();
						var currCd=sel.currCdDesc.split('-')[0];
						var chnlMchntCd=$("#chnlMchntCdHid").val();
						if (confirm("确认要删除选中的记录吗?")) {
							if (dealFlag) {
								alert("正在处理请求，请稍等......");
								return false;
							}
							dealFlag = true;		 
							var url = _ctx + "/virtualTerm/settlePolicy/delete.do?chnlId=" + chnlId + 
												"&chnlMchntCd=" + chnlMchntCd+"&currCd="+currCd;
							$.post(url,
								function(data) {
									dealFlag = false;
									$.processAjaxResp(data, function() {
										alert("删除清算信息成功");
										$.jumpTo(_ctx + "/virtualTerm/settlePolicy/manage.do?chnlId=" + $("#chnlIdHid").val()+"&chnlMchntCd=" + $("#chnlMchntCdHid").val());
									});
								}
							);
						}
					}
				});
			});
		</script>
	</head>

	<body style="padding: 5px;">
		<input type="hidden" id="chnlIdHid" name="chnlId" value="${chnlId}" />
		<input type="hidden" id="chnlMchntCdHid" name="chnlMchntCd" value="${chnlMchntCd}" />
		<div id="mngDiv" class="easyui-panel" title="清算信息管理-${chnlId}-${chnlMchntCd}" style="padding: 10px;">
			<table id="dg" style="height:auto;border:1px solid #ccc;">
				<thead>
					<tr>
		                <th data-options="field:'currCdDesc',width: 100">币别</th>
		                <th data-options="field:'settlePeriodDesc',width: 100">清算周期</th>
		                <th data-options="field:'balanceTransferDesc',width: 150">自动D0余额结转</th>
		                <th data-options="field:'transferTime',width: 100">D0余额结转时间</th>
		                <th data-options="field:'balanceTransferT1Desc',width: 150">自动T1余额结转反还</th>
		                <th data-options="field:'transferTimeT1',width: 100">T1结转时间 </th>
		                <th data-options="field:'transferModeDesc',width: 100">T1余额结转模式 </th>
		                <th data-options="field:'lastOperId',width: 100">操作人员 </th>
		                <th data-options="field:'recCrtTs',width: 200">创建时间 </th>
						<th data-options="field:'recUpdTs',width: 200">更新时间 </th>
					</tr>
				</thead>
				<tbody>
					<#list result as r>
						<tr>
							<td class="vtd_lbl">${r['currCdDesc']!''}</td>
							<td class="vtd_lbl">${r['settlePeriodDesc']!''}</td>
							<td class="vtd_lbl">${r['balanceTransferDesc']!''}</td>
							<td class="vtd_lbl">${r['transferTime']!''}</td>
							<td class="vtd_lbl">${r['balanceTransferT1Desc']!''}</td>
							<td class="vtd_lbl">${r['transferTimeT1']!''}</td>
							<td class="vtd_lbl">${r['transferModeDesc']!''}</td>
							<td class="vtd_lbl">${r['lastOperId']!''}</td>
							<td class="vtd_lbl">${r['recCrtTs']!''}</td>
							<td class="vtd_lbl">${r['recUpdTs']!''}</td>
						</tr>
					</#list>
				</tbody>
			</table>
			<div id="opBtns" style="margin: 10px 0;">
				<#--  
				<#if result?size < 3>
					<a id="addBtn" href="javascript:void(0)" class="easyui-linkbutton">新增交易参数</a>
				</#if>
				-->
				<#-- <@authCheck funcCode='1100040009'>-->
				<a id="addBtn" href="javascript:void(0)" class="easyui-linkbutton">新增清算信息</a>
				<#--</@authCheck>-->
				<#--<@authCheck funcCode='1100040011'>-->
				<a id="updBtn" href="javascript:void(0)" class="easyui-linkbutton">修改清算信息</a>
				<#--</@authCheck>-->
				<#--<@authCheck funcCode='1100040013'>-->
				<a id="delBtn" href="javascript:void(0)" class="easyui-linkbutton">删除清算信息</a>
				<#--</@authCheck>-->
				<a id="backBtn" href="javascript:void(0)" class="easyui-linkbutton">返回</a>
			</div>
		</div>
	</body>
</html>