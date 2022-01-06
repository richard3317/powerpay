<div id="header">
	<div class="center_wrapper">
		<#--<div id="logo">-->
			<#--<img src="${ctx}/resources/images/logo.png" />-->
		<#--</div>-->

		<div id="navbar-left" class="navbar-left">
			<h1><a href="${ctx}/index"><@msg code='head.商户平台' default='商户平台'/></a></h1>
		</div>
		<div id="navbar-right" class="navbar-right">
			<#--  
			<h1>商户平台</h1>
			<a href="${ctx}/index">首页</a>
			-->
			
			<#if ("1" == (Session.userState!''))>
				<#if (("wd" == (Session.userRole!""))  && ("2" == (Session.secretState!""))) || ("py" == (Session.userRole!"")) >
		            <a href="${ctx}/trans/transMng"><@msg code='head.充值查询' default='充值查询'/></a>
		            <a href="${ctx}/withdraw/withdrawMng"><@msg code='head.取现查询' default='取现查询'/></a>
		            <a href="${ctx}/eventLog/eventLog"><@msg code='head.操作纪录查询' default='操作纪录查询'/></a>
		            <a href="${ctx}/mchntAcct/fundMng"><@msg code='head.资金流水' default='资金流水'/></a>
			        <a href="${ctx}/payment/commonPay"><@msg code='head.充值' default='充值'/></a>
				</#if>
				
				 <#if ("su" == (Session.userRole!"")) && ("1" == (Session.secretState!"")) >
					<a href="${ctx}/mchntUser/userMng"><@msg code='head.人员管理' default='人员管理'/></a>
					<a href="${ctx}/trans/transMng"><@msg code='head.充值查询' default='充值查询'/></a>
		            <a href="${ctx}/withdraw/withdrawMng"><@msg code='head.取现查询' default='取现查询'/></a>
					<a href="${ctx}/eventLog/eventLog"><@msg code='head.操作纪录查询' default='操作纪录查询'/></a>
		            <a href="${ctx}/mchntAcct/fundMng"><@msg code='head.资金流水' default='资金流水'/></a>
		            <a href="${ctx}/payment/commonPay"><@msg code='head.充值' default='充值'/></a>
					<a href="${ctx}/withdraw/withdrawPage"><@msg code='head.取现' default='取现'/></a>
			        <a href="${ctx}/transfer/transferPage"><@msg code='head.转帐' default='转帐'/></a>
				</#if> 
				
				<#if ("wd" == (Session.userRole!"")) && ("2" == (Session.secretState!"")) >
					<a href="${ctx}/withdraw/withdrawPage"><@msg code='head.取现' default='取现'/></a>
			        <a href="${ctx}/transfer/transferPage"><@msg code='head.转帐' default='转帐'/></a>
				</#if> 
				
			     <a href="${ctx}/secrity/secritySetting"><@msg code='head.安全设置' default='安全设置'/></a>
	            <#--<a href="${ctx}/mchntSettle/settleMng">清算对账查询</a>-->
	            <#--<a href="${ctx}/mchntAcct/acctMng">账户信息</a>-->
	            
            </#if>
            
            
            
		</div>
	</div>
</div>