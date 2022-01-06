<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>机构管理</title>
		<#include "/common/include.ftl">
		<script type="text/javascript">
			$(function() {
				var baseUrl = "${ctx}/organ/";
				$("#backBtn").click(function() {
					$.jumpTo(baseUrl + "/backToManage.do");
				});
			});
		</script>
	</head>
	
	<body>
		<div class="easyui-panel" title="查看机构详情" style="padding:10px;">
			<#-- <div class="easyui-panel" title="机构信息" style="padding:10px;">-->
				<table class="view_tbl" cellpadding="0" cellspacing="0">
					<tr>
						<td class="vtd_lbl" width="15%">机构编号:</td>
						<td class="vtd_cnt" width="35%">
							<span id="organIdSpan">${entity.organId}</span>
						</td>
						<td class="vtd_lbl" width="15%">机构名称:</td>
						<td class="vtd_cnt" width="35%">
							${entity.organDesc}
						</td>
					</tr>
					<tr>
						<td class="vtd_lbl" width="15%">创建时间:</td>
						<td class="vtd_cnt" width="35%">
							${entity.recCrtTs?string("yyyy-MM-dd HH:mm:dd")}
						</td>
						<td class="vtd_lbl" width="15%">更新时间:</td>
						<td class="vtd_cnt" width="35%">
							${entity.recUpdTs?string("yyyy-MM-dd HH:mm:dd")}
						</td>
					</tr>
					<tr>
						<#include "/organ/mchntDetail.ftl">
					</tr>
					
				</table>
			<#-- </div> 
			
			<div id="opBtns" style="margin: 10px 0;">
				<a id="backBtn" href="javascript:void(0)" class="easyui-linkbutton">返回</a>
			</div>-->
		</div>
	</body>
</html>
