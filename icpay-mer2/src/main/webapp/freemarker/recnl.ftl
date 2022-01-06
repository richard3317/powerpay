<#include "/common/macro.ftl">

<#assign htmlHead>
	<script src="${ctx}/resources/js/jquery.dataTables.min.js"></script>
	<script src="${ctx}/resources/bootstrap/js/dataTables.bootstrap.min.js"></script>
</#assign>

<#assign htmlContent>
	<ol class="breadcrumb">
		<li><a href="#"><@msg code='profile.商户服务' default='商户服务'/></a></li>
		<li class="active"><@msg code='profile.清算对账' default='清算对账'/></li>
	</ol>
    
	<div class="panel panel-default">
		<div class="panel-heading"><h4 class="panel-title"><@msg code='profile.对账文件下载' default='对账文件下载'/></h4></div>
	
		<div class="panel-body">
			<form method="get" class="form-horizontal">
				<div class="form-group">
					<label class="col-sm-2 control-label"><@msg code='profile.对账日期' default='对账日期'/></label>
					<div class="col-sm-2"><input type="text" class="form-control"></div>
					<div class="col-sm-2">
						<a class="btn btn-primary btn-sm" href="#" role="button"><@msg code='profile.查询' default='查询'/></a>
					</div>
				</div>
			</form>
			
			<table class="table">
				<thead>
					<tr>
						<th><@msg code='profile.对账日期' default='对账日期'/></th>
						<th><@msg code='profile.文件名' default='文件名'/></th>
						<th><@msg code='profile.交易笔数合计' default='交易笔数合计'/></th>
						<th><@msg code='profile.交易金额合计(元)' default='交易金额合计(元)'/></th>
						<th><@msg code='profile.手续费合计(元)' default='手续费合计(元)'/></th>
						<th><@msg code='profile.操作' default='操作'/></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>20150504</td>
						<td>123456789101114_20150504_MER</td>
						<td>1200</td>
						<td>120000.00</td>
						<td>100.00</td>
						<td>
							<a href="#"><@msg code='profile.下载' default='下载'/></a>
						</td>
					</tr>
					<tr>
						<td>20150503</td>
						<td>123456789101114_20150503_MER</td>
						<td>1200</td>
						<td>120000.00</td>
						<td>100.00</td>
						<td>
							<a href="#"><@msg code='profile.下载' default='下载'/></a>
						</td>
					</tr>
					<tr>
						<td>20150502</td>
						<td>123456789101114_20150502_MER</td>
						<td>1200</td>
						<td>120000.00</td>
						<td>100.00</td>
						<td>
							<a href="#"><@msg code='profile.下载' default='下载'/></a>
						</td>
					</tr>
					<tr>
						<td>20150501</td>
						<td>123456789101114_20150501_MER</td>
						<td>1200</td>
						<td>120000.00</td>
						<td>100.00</td>
						<td>
							<a href="#"><@msg code='profile.下载' default='下载'/></a>
						</td>
					</tr>
					<tr>
						<td>20150430</td>
						<td>123456789101114_20150430_MER</td>
						<td>1200</td>
						<td>120000.00</td>
						<td>100.00</td>
						<td>
							<a href="#"><@msg code='profile.下载' default='下载'/></a>
						</td>
					</tr>
				</tbody>
			</table>
			
			<ul class="pagination pull-right">
	      		<li><a href="#">&laquo;</a></li>
	      		<li><a href="#">1</a></li>
	      		<li><a href="#">2</a></li>
	      		<li><a href="#">3</a></li>
	      		<li><a href="#">4</a></li>
	      		<li><a href="#">5</a></li>
	      		<li><a href="#">&raquo;</a></li>
	      	</ul>
		</div>
	</div>
</#assign>

<#assign htmlJS>
	$(function() {
		$(".table").find("a").click(function() {
			if(confirm("<@msg code='profile.确认下载?' default='确认下载?'/>")) {
				alert("<@msg code='profile.已下载成功' default='已下载成功'/>");
			}
		});
	});
</#assign>

<#include "/common/layout.ftl" />