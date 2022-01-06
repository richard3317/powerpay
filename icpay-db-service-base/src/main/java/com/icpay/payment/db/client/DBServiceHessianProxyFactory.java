package com.icpay.payment.db.client;

import com.caucho.hessian.client.HessianConnectionFactory;
import com.caucho.hessian.client.HessianProxyFactory;

public class DBServiceHessianProxyFactory extends HessianProxyFactory {

	public DBServiceHessianProxyFactory() {
		super();
		HessianConnectionFactory hessianConnectionFactory = new DBServiceHessianConnectionFactory();
		hessianConnectionFactory.setHessianProxyFactory(this);
		this.setConnectionFactory(hessianConnectionFactory);
	}
	
}
