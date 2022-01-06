<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>渠道虚拟终端信息-交易参数管理</title>
		<#include "/common/include.ftl">
		<script type="text/javascript">
			var dealFlag = false;
			$(function() {
				$('#dg').datagrid({
					height: 350,
					singleSelect: true
				});
				$("#addBtn").click(function() {
					$.jumpTo(_ctx + "/virtualTerm/transParam/add.do?chnlId=" + $("#chnlIdHid").val()+"&chnlMchntCd=" + $("#chnlMchntCdHid").val());
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
						var intTransCd=sel.intTransCdDesc.split('-')[0];
						var chnlMchntCd=$("#chnlMchntCdHid").val();
						var currCd=sel.currCd.split('-')[0];
						var url = _ctx + "/virtualTerm/transParam/upd.do?chnlId=" + chnlId + 
												"&chnlMchntCd=" + chnlMchntCd+"&intTransCd=" + intTransCd + "&currCd=" + currCd;
						$.jumpTo(url);
					}
				});
				$("#delBtn").click(function() {
					var sel = $('#dg').datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						var chnlId=$("#chnlIdHid").val();
						var intTransCd=sel.intTransCdDesc.split('-')[0];
						var chnlMchntCd=$("#chnlMchntCdHid").val();
						var currCd=sel.currCd.split('-')[0];
						if (confirm("确认要删除选中的记录吗?")) {
							if (dealFlag) {
								alert("正在处理请求，请稍等......");
								return false;
							}
							dealFlag = true;		 
							var url = _ctx + "/virtualTerm/transParam/delete.do?chnlId=" + chnlId + 
												"&chnlMchntCd=" + chnlMchntCd+"&intTransCd="+intTransCd + "&currCd=" + currCd;
							$.post(url,
								function(data) {
									dealFlag = false;
									$.processAjaxResp(data, function() {
										alert("删除计费方式成功");
										$.jumpTo(_ctx + "/virtualTerm/transParam/manage.do?chnlId=" + $("#chnlIdHid").val()+"&chnlMchntCd=" + $("#chnlMchntCdHid").val());
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
		<div id="mngDiv" class="easyui-panel" title="交易参数管理-${chnlId}-${chnlMchntCd}" style="padding: 10px;">
			<table id="dg" style="height:auto;border:1px solid #ccc;">
				<thead>
					<tr>
		                <th data-options="field:'intTransCdDesc',width: 150">交易类型</th>
		                <th data-options="field:'currCd',width: 100">币别</th>
		                <th data-options="field:'settleAlgorithmDesc',width: 400">计费方式</th>
		                <th data-options="field:'txDayMaxDesc',width: 150">交易日限额</th>
		                <th data-options="field:'txAmtMinDesc',width: 150">交易单笔下限</th>
		                <th data-options="field:'txAmtMaxDesc',width: 150">交易单笔上限</th>
		                <th data-options="field:'txTimeLimitDesc',width: 150">交易支持的时段</th>
		                <th data-options="field:'txCardDayMaxDesc',width: 150">单日单卡限额</th>
		                <th data-options="field:'txT0PercentDesc',width: 150">代付垫资比例</th>
					</tr>
				</thead>
				<tbody>
					<#list result as r>
						<tr>
							<td class="vtd_lbl">${r['intTransCdDesc']!''}</td>
							<td class="vtd_lbl">${r['currCd']!''}</td>
							<td class="vtd_lbl">${r['settleAlgorithmDesc']!''}</td>
							<td class="vtd_lbl">${r['txDayMaxDesc']!''}</td>
							<td class="vtd_lbl">${r['txAmtMinDesc']!''}</td>
							<td class="vtd_lbl">${r['txAmtMaxDesc']!''}</td>
							<td class="vtd_lbl">${r['txTimeLimitDesc']!''}</td>
							<td class="vtd_lbl">${r['txCardDayMaxDesc']!''}</td>
							<td class="vtd_lbl">${r['txT0PercentDesc']!''}</td>
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
				<@authCheck funcCode='1100040009'>
				<a id="addBtn" href="javascript:void(0)" class="easyui-linkbutton">新增交易参数</a>
				</@authCheck>
				<@authCheck funcCode='1100040011'>
				<a id="updBtn" href="javascript:void(0)" class="easyui-linkbutton">修改交易参数</a>
				</@authCheck>
				<@authCheck funcCode='1100040013'>
				<a id="delBtn" href="javascript:void(0)" class="easyui-linkbutton">删除交易参数</a>
				</@authCheck>
				<a id="backBtn" href="javascript:void(0)" class="easyui-linkbutton">返回</a>
			</div>
		</div>
	</body>
</html>