package com.icpay.payment.db.client;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

import com.caucho.hessian.client.HessianConnection;
import com.caucho.hessian.client.HessianURLConnectionFactory;

public class DBServiceHessianConnectionFactory extends HessianURLConnectionFactory {

	@Override
	public HessianConnection open(URL url) throws IOException {
		HessianConnection conn = super.open(url);
		conn.addHeader("token", "T" + (new Date()).getTime());
		return conn;
	}

}
