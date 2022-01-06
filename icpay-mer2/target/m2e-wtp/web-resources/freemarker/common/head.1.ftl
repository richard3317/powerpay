<div id="header">
	<div class="center_wrapper">
			<nav class="navbar navbar-right" role="navigation">
				<div class="navbar-header">
                     <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"> 
                     <span class="sr-only">Toggle</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button> 
                     <a class="navbar-brand" href="${ctx}/index"><@msg code='head1.商户平台' default='商户平台'/></a>
				</div>
				
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav navbar-right">
						<#-- <li><a href="#">Link</a></li> -->
                        
                        <#if "admin" == (Session.userRole!"") >
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><@msg code='head1.查询' default='查询'/><strong class="caret"></strong></a>
                                <ul class="dropdown-menu">
                                        <li><a href="${ctx}/trans/transMng"><@msg code='head1.支付查询' default='支付查询'/></a></li>
                                        <li><a href="${ctx}/withdraw/withdrawMng"><@msg code='head1.取现查询' default='取现查询'/></a></li>
                                        <li><a href="${ctx}/mchntAcct/fundMng"><@msg code='head1.资金流水' default='资金流水'/></a></li>
                                    </ul>
                            </li>
                            <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><@msg code='head1.交易' default='交易'/><strong class="caret"></strong></a>
                                    <ul class="dropdown-menu">
                                        <li><a href="${ctx}/payment/commonPay"><@msg code='head1.充值' default='充值'/></a></li>
                                        <li><a href="${ctx}/withdraw/withdrawPage"><@msg code='head1.取现' default='取现'/></a></li>
                                    </ul>
                            </li>
        
                            <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><@msg code='head1.设置' default='设置'/><strong class="caret"></strong></a>
                                    <ul class="dropdown-menu">
                                            <li><a href="${ctx}/chngPwd"><@msg code='head1.修改密码' default='修改密码'/></a></li>
                                            <li><a href="${ctx}/secrity/secritySetting"><@msg code='head1.安全设置' default='安全设置'/></a></li>
                                        	<li><a href="${ctx}/secrity/bankCardWhiteList"><@msg code='head1.银行卡白名单' default='银行卡白名单'/></a></li>
                                    </ul>
                            </li>
                         <#else>
                                <li><a href="${ctx}/trans/transMng"><@msg code='head1.支付查询' default='支付查询'/></a></li>
                                <li><a href="${ctx}/withdraw/withdrawMng"><@msg code='head1.取现查询' default='取现查询'/></a></li>
                                <li><a href="${ctx}/mchntAcct/fundMng"><@msg code='head1.资金流水' default='资金流水'/></a></li>
                                <li><a href="${ctx}/payment/commonPay"><@msg code='head1.充值' default='充值'/></a></li>
                                <li><a href="${ctx}/chngPwd"><@msg code='head1.修改密码' default='修改密码'/></a></li>
                        </#if>
    
  
					</ul>
				</div>
				
			</nav>
	</div>
</div>