<#include "/common/macro.ftl">

<#assign htmlHead>
  <link rel="stylesheet" type="text/css" href="${ctx}/resources/js/jquery-easy-ui/themes/jquery-ui.min.css"></link>
  <script type="text/javascript" src="${ctx}/resources/js/jquery-ui.min.js"></script>
  <script>
  $( function() {
    $( "#accordion" ).accordion();
  } );
  </script>
</#assign>

<#assign htmlContent>
	<ol class="breadcrumb">
		<li><a href="${ctx}/index"><@msg code='acctMng.商户服务' default='商户服务'/></a></li>
		<li class="active"><@msg code='acctMng.账户信息' default='账户信息'/></li>
	</ol>
	
	<div class="panel panel-default">
		<div class="panel-heading">
			<h4 class="panel-title"><@msg code='acctMng.商户基本信息' default='商户基本信息'/></h4>
		</div>
		<div class="panel-body">
			<table width="100%">
				<tr>
					<td style="padding: 10px;" width="30%"><strong><@msg code='acctMng.商户号' default='商户号'/></strong>：</td>
					<td style="padding: 10px;" width="70%">${mchntCd}</td>
				</tr>
				<tr>
					<td style="padding: 10px;" width="30%"><strong><@msg code='acctMng.商户名' default='商户名'/></strong>：</td>
					<td style="padding: 10px;" width="70%">${mchntNm}</td>
				</tr>
			</table>
		</div>
	</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">
			<h4 class="panel-title"><@msg code='acctMng.账户信息' default='账户信息'/></h4>
		</div>
		 
		<div id="accordion">
	  		<h3><strong><@msg code='acctMng.人民币' default='人民币'/>&nbsp;&nbsp-&nbsp;&nbsp<@msg code='index.帐户总额' default='帐户总额'/>：&nbsp;&nbsp;${totalBalance_CNY}&nbsp;&nbsp;<#-- (<@msg code='acctMng.元' default='元'/>) --></strong></h3>
			<div class="panel-body">
				<table width="100%">
					<tr>
						<td style="padding: 10px;" width="30%"><@msg code='acctMng.可代付余额' default='可代付余额'/>：</td>
						<#-- <td style="padding: 10px;" width="30%"><@msg code='acctMng.可取现金额' default='可取现金额'/>：</td> -->
						<td style="padding: 10px;" width="70%">${availableBalance_CNY}&nbsp;&nbsp;<#-- (<@msg code='acctMng.元' default='元'/>) --></td>
					</tr>
					<tr>
						<td style="padding: 10px;" width="30%"><@msg code='acctMng.预扣金额' default='预扣金额'/>：</td>
						<td style="padding: 10px;" width="70%">${frozenBalance_CNY}&nbsp;&nbsp;<#-- (<@msg code='acctMng.元' default='元'/>) --></td>
					</tr>
					<tr>
						<td style="padding: 10px;" width="30%"><@msg code='acctMng.T1账户余额' default='T1账户余额'/>：</td>
						<#--<td style="padding: 10px;" width="30%"><@msg code='acctMng.T1待结算金额' default='T1待结算金额'/>：</td> -->
						<td style="padding: 10px;" width="70%">${frozenT1Balance_CNY}&nbsp;&nbsp;<#-- (<@msg code='acctMng.元' default='元'/>) --></td>
					</tr>
				</table>
			</div>
	  		<h3><strong><@msg code='acctMng.越南盾' default='越南盾'/>&nbsp;&nbsp-&nbsp;&nbsp<@msg code='index.帐户总额' default='帐户总额'/>：&nbsp;&nbsp;${totalBalance_VND}&nbsp;&nbsp;<#-- (<@msg code='acctMng.元' default='元'/>) --></strong></h3>
			<div class="panel-body">
				<table width="100%">
					<tr>
						<td style="padding: 10px;" width="30%"><@msg code='acctMng.可代付余额' default='可代付余额'/>：</td>
						<#-- <td style="padding: 10px;" width="30%"><@msg code='acctMng.可取现金额' default='可取现金额'/>：</td> -->
						<td style="padding: 10px;" width="70%">${availableBalance_VND}&nbsp;&nbsp;<#-- (<@msg code='acctMng.元' default='元'/>) --></td>
					</tr>
					<tr>
						<td style="padding: 10px;" width="30%"><@msg code='acctMng.预扣金额' default='预扣金额'/>：</td>
						<td style="padding: 10px;" width="70%">${frozenBalance_VND}&nbsp;&nbsp;<#-- (<@msg code='acctMng.元' default='元'/>) --></td>
					</tr>
					<tr>
						<td style="padding: 10px;" width="30%"><@msg code='acctMng.T1账户余额' default='T1账户余额'/>：</td>
						<#--<td style="padding: 10px;" width="30%"><@msg code='acctMng.T1待结算金额' default='T1待结算金额'/>：</td> -->
						<td style="padding: 10px;" width="70%">${frozenT1Balance_VND}&nbsp;&nbsp;<#-- (<@msg code='acctMng.元' default='元'/>) --></td>
					</tr>
				</table>
			</div>
	  		<h3><strong><@msg code='acctMng.泰铢' default='泰铢'/>&nbsp;&nbsp-&nbsp;&nbsp<@msg code='index.帐户总额' default='帐户总额'/>：&nbsp;&nbsp;${totalBalance_THB}&nbsp;&nbsp;<#-- (<@msg code='acctMng.元' default='元'/>) --></strong></h3>
			<div class="panel-body">
				<table width="100%">
					<tr>
						<td style="padding: 10px;" width="30%"><@msg code='acctMng.可代付余额' default='可代付余额'/>：</td>
						<#-- <td style="padding: 10px;" width="30%"><@msg code='acctMng.可取现金额' default='可取现金额'/>：</td> -->
						<td style="padding: 10px;" width="70%">${availableBalance_THB}&nbsp;&nbsp;<#-- (<@msg code='acctMng.元' default='元'/>) --></td>
					</tr>
					<tr>
						<td style="padding: 10px;" width="30%"><@msg code='acctMng.预扣金额' default='预扣金额'/>：</td>
						<td style="padding: 10px;" width="70%">${frozenBalance_THB}&nbsp;&nbsp;<#-- (<@msg code='acctMng.元' default='元'/>) --></td>
					</tr>
					<tr>
						<td style="padding: 10px;" width="30%"><@msg code='acctMng.T1账户余额' default='T1账户余额'/>：</td>
						<#--<td style="padding: 10px;" width="30%"><@msg code='acctMng.T1待结算金额' default='T1待结算金额'/>：</td> -->
						<td style="padding: 10px;" width="70%">${frozenT1Balance_THB}&nbsp;&nbsp;<#-- (<@msg code='acctMng.元' default='元'/>) --></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	
	<#-- 2019/11/11 拿掉此信息展示
	<div class="panel panel-default">
		<div class="panel-heading">
			<h4 class="panel-title">清算信息</h4>
		</div>
		<div class="panel-body">
			<table width="100%">
				<tr>
					<td style="padding: 10px;" width="30%"><strong>清算账号</strong>：</td>
					<td style="padding: 10px;" width="70%">${settleInfo.settleAccount}</td>
				</tr>
				<tr>
					<td style="padding: 10px;" width="30%"><strong>清算账号户名</strong>：</td>
					<td style="padding: 10px;" width="70%">${settleInfo.settleAccountName}</td>
				</tr>
				<tr>
					<td style="padding: 10px;" width="30%"><strong>清算账号所属银行</strong>：</td>
					<td style="padding: 10px;" width="70%">${settleInfo.settleAccountBankName}</td>
				</tr>
				<tr>
					<td style="padding: 10px;" width="30%"><strong>清算周期</strong>：</td>
					<td style="padding: 10px;" width="70%">${settlePeriodDesc}</td>
				</tr>
				<#if settleInfo.settlePeriod == '0'>
				<tr>
					<td style="padding: 10px;" width="30%"><strong>当日最大清算金额</strong>：</td>
					<td style="padding: 10px;" width="70%">${settleLimit}&nbsp;&nbsp;(元)</td>
				</tr>
				</#if>
			</table>
		</div>
	</div>
	-->
	
	<div class="panel panel-default">
		<div class="panel-heading">
			<h4 class="panel-title"><@msg code='acctMng.计费方式' default='计费方式'/></h4>
		</div>
		<div class="panel-body">
			<table width="100%">
				<thead>
					<tr style="background-color: #f3f3f3">
		                <th style="padding: 10px;" width="30%"><@msg code='acctMng.交易类型' default='交易类型'/></th>
		                <th style="padding: 10px;" width="30%"><@msg code='acctMng.币别' default='币别'/></th>
		                <th style="padding: 10px;" width="30%"><@msg code='acctMng.计费方式' default='计费方式'/></th>
		                <th style="padding: 10px;"><@msg code='acctMng.计费方式' default='计费方式'/></th>
					</tr>
				</thead>
				<tbody>
					<#list settleAlgorithmLst as r>
						<tr>
							<td style="padding: 10px;" width="30%">${r['intTransCdDesc']!''}</td>
							<td style="padding: 10px;" width="30%">${r['currCdDesc']!''}</td>
							<td style="padding: 10px;" width="30%">${r['settleModeDesc']!''}</td>
							<td style="padding: 10px;">${r['settleAlgorithmDesc']!''}</td>
						</tr>
					</#list>
				</tbody>
			</table>
		</div>
	</div>
</#assign>

<#include "/common/layout.ftl" />