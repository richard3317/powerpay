<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>代理商分润信息-分润策略管理</title>
		<#include "/common/include.ftl">
		<script type="text/javascript">
			var dealFlag = false;
			$(function() {
				$('#dg').datagrid({
					height: 350,
					singleSelect: true
				});
				$("#addBtn").click(function() {
					$.jumpTo(_ctx + "/agentProfit/profitPolicy/add.do?agentCd=" + $("#agentCdHid").val());
				});
				$("#backBtn").click(function() {
					$.jumpTo(_ctx + "/agentProfit/backToManage.do");
				});
				$("#updBtn").click(function() {
					var sel = $('#dg').datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						var agentCd=$("#agentCdHid").val();
						var intTransCd=sel.intTransCdDesc.split('-')[0];
						var mchntCd = sel.mchntCd;
						var chnlId = sel.chnlId;
						var chnlMchntCd= sel.chnlMchntCd;
						var url = _ctx + "/agentProfit/profitPolicy/upd.do?agentCd=" + agentCd + 
												"&intTransCd=" + intTransCd 
												+ "&mchntCd=" + mchntCd
												+ "&chnlId=" + chnlId
												+ "&chnlMchntCd=" + chnlMchntCd;
						$.jumpTo(url);
					}
				});
				$("#delBtn").click(function() {
					var sel = $('#dg').datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						var agentCd=$("#agentCdHid").val();
						var intTransCd=sel.intTransCdDesc.split('-')[0];
						var mchntCd = sel.mchntCd;
						var chnlId = sel.chnlId;
						var chnlMchntCd= sel.chnlMchntCd;
						if (confirm("确认要删除选中的记录吗?")) {
							if (dealFlag) {
								alert("正在处理请求，请稍等......");
								return false;
							}
							dealFlag = true;		 
							var url = _ctx + "/agentProfit/profitPolicy/delete.do?agentCd=" + agentCd + 
												"&intTransCd=" + intTransCd 
												+ "&mchntCd=" + mchntCd
												+ "&chnlId=" + chnlId
												+ "&chnlMchntCd=" + chnlMchntCd;
							$.post(url,
								function(data) {
									dealFlag = false;
									$.processAjaxResp(data, function() {
										alert("删除分润策略成功");
										$.jumpTo(_ctx + "/agentProfit/profitPolicy/manage.do?agentCd=" + $("#agentCdHid").val());
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
		<input type="hidden" id="agentCdHid" name="agentCd" value="${agentCd}" />
		<div id="mngDiv" class="easyui-panel" title="代理商分润策略管理"  style="padding: 10px;">
			<table id="dg" style="height:auto;border:1px solid #ccc;">
				<thead>
					<tr>
						<#--  <th data-options="field:'tradeTypeDesc',width: 150">行业类别</th> -->
		                <th data-options="field:'intTransCdDesc',width: 150">交易类型</th>
		                <th data-options="field:'mchntCd',width: 150">商户编号</th>
		                <th data-options="field:'chnlId',width: 150">渠道编号</th>
		                <th data-options="field:'chnlMchntCd',width: 200">渠道商编</th>
		                <th data-options="field:'rate',width: 150">扣率</th>
		                <th data-options="field:'maxFee',width: 150">封顶手续费(元)</th>
		                <th data-options="field:'minFee',width: 150">保底手续费(元)</th>
		                <th data-options="field:'comment',width: 200">备注</th>
					</tr>
				</thead>
				<tbody>
					<#list result as r>
						<tr>
							<#--  <td class="vtd_lbl">${r['tradeTypeDesc']!''}</td> -->
							<td class="vtd_lbl">${r['intTransCdDesc']!''}</td>
							<td class="vtd_lbl">${r['mchntCd']!''}</td>
							<td class="vtd_lbl">${r['chnlId']!''}</td>
							<td class="vtd_lbl">${r['chnlMchntCd']!''}</td>
							<td class="vtd_lbl">${r['rate']!''}</td>
							<td class="vtd_lbl">${r['maxFee']!''}</td>
							<td class="vtd_lbl">${r['minFee']!''}</td>
							<td class="vtd_lbl">${r['comment']!''}</td>
						</tr>
					</#list>
				</tbody>
			</table>
			<div id="opBtns" style="margin: 10px 0;">
				<a id="addBtn" href="javascript:void(0)" class="easyui-linkbutton">新增</a>
				<a id="updBtn" href="javascript:void(0)" class="easyui-linkbutton">修改</a>
				<a id="delBtn" href="javascript:void(0)" class="easyui-linkbutton">删除</a>
				<a id="backBtn" href="javascript:void(0)" class="easyui-linkbutton">返回</a>
			</div>
		</div>
	</body>
</html>