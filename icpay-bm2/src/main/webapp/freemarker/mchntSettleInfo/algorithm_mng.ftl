<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>商户清算信息-计费方式管理</title>
		<#include "/common/include.ftl">
		<script type="text/javascript">
			var dealFlag = false;
			$(function() {
				$('#dg').datagrid({
					height: 350,
					singleSelect: true
				});
				$("#addBtn").click(function() {
					$.jumpTo(_ctx + "/mchntSettleInfo/algorithm/add.do?mchntCd=" + $("#mchntCdHid").val());
				});
				$("#backBtn").click(function() {
					$.jumpTo(_ctx + "/mchntSettleInfo/backToManage.do");
				});
				$("#updBtn").click(function() {
					var sel = $('#dg').datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						var mchntCd=$("#mchntCdHid").val();
						var intTransCd=sel.intTransCdDesc.split('-')[0];
						var currCd=sel.currCdDesc.split('-')[0];
						var url = _ctx + "/mchntSettleInfo/algorithm/upd.do?mchntCd=" + mchntCd + 
												"&intTransCd=" + intTransCd+"&currCd="+currCd;
						$.jumpTo(url);
					}
				});
				$("#delBtn").click(function() {
					var sel = $('#dg').datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						var mchntCd=$("#mchntCdHid").val();
						var intTransCd=sel.intTransCdDesc.split('-')[0];
						var currCd=sel.currCdDesc.split('-')[0];
						if (confirm("确认要删除选中的记录吗?")) {
							if (dealFlag) {
								alert("正在处理请求，请稍等......");
								return false;
							}
							dealFlag = true;		 
							var url = _ctx + "/mchntSettleInfo/algorithm/delete.do?mchntCd=" + mchntCd + 
												"&intTransCd=" + intTransCd+"&currCd="+currCd;
							$.post(url,
								function(data) {
									dealFlag = false;
									$.processAjaxResp(data, function() {
										alert("删除计费方式成功");
										$.jumpTo(_ctx + "/mchntSettleInfo/algorithm/manage.do?mchntCd=" + $("#mchntCdHid").val());
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
		<input type="hidden" id="mchntCdHid" name="mchntCd" value="${mchntCd}" />
		<div id="mngDiv" class="easyui-panel" title="计费方式管理-${mchntCd}" style="padding: 10px;">
			<table id="dg" style="height:auto;border:1px solid #ccc;">
				<thead>
					<tr>
		                <th data-options="field:'intTransCdDesc',width: 150">交易类型</th>
		                <th data-options="field:'currCdDesc',width: 150">币别</th>
		                <th data-options="field:'settleModeDesc',width: 120">计费模式</th>
		                <th data-options="field:'txT0Percent',width: 100">垫资比例</th>
		                <th data-options="field:'settleAlgorithmDesc',width: 300">费率计费方式</th>
		                <th data-options="field:'settleAlgorithmLimit',width: 400">交易限制</th>
					</tr>
				</thead>
				<tbody>
					<#list result as r>
						<tr>
							<td class="vtd_lbl">${r['intTransCdDesc']!''}</td>
							<td class="vtd_lbl">${r['currCdDesc']!''}</td>
							<td class="vtd_lbl">${r['settleModeDesc']!''}</td>
							<td class="vtd_lbl">${r['txT0Percent']!''}</td>
							<td class="vtd_lbl">${r['settleAlgorithmDesc']!''}</td>
							<td class="vtd_lbl">${r['settleAlgorithmLimit']!''}</td>
						</tr>
					</#list>
				</tbody>
			</table>
			<div id="opBtns" style="margin: 10px 0;">
				<#--  
				<#if result?size < 3>
				</#if>
				-->
				<@authCheck funcCode='1000050009'>
				<a id="addBtn" href="javascript:void(0)" class="easyui-linkbutton">新增计费方式</a>
				</@authCheck>
				<@authCheck funcCode='1000050011'>
				<a id="updBtn" href="javascript:void(0)" class="easyui-linkbutton">修改计费方式</a>
				</@authCheck>
				<@authCheck funcCode='1000050013'>
				<a id="delBtn" href="javascript:void(0)" class="easyui-linkbutton">删除计费方式</a>
				</@authCheck>
				<a id="backBtn" href="javascript:void(0)" class="easyui-linkbutton">返回</a>
			</div>
		</div>
	</body>
</html>