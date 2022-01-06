package com.icpay.payment.mer.session.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.icpay.payment.mer.util.MerUtils;

public class Test1 {

	@Test
	public void test() {
		String test;
		test="张三|6227001215950083244";
		//test="张三|6227001215950083244,李四|6227001215950083244";
		System.out.println(MerUtils.getAccNoInfo(test)[1]);
		System.out.println(MerUtils.accWhiteItem("张三", "6227001215950083244"));
		test="姬喜喜|6212261001014201211";
		System.out.println(MerUtils.checkBankCard(test));
		test="6212261001014201211";
		System.out.println(MerUtils.checkBankCard(test));
		test="广州焦布网络科技有限公司|44050140100700000330";
		System.out.println(MerUtils.checkBankCard(test));
//		test="姬喜喜|621226100101420121162122610010142012116212261001014201211";
//		System.out.println(MerUtils.checkBankCard(test));
//		test="姬喜喜|62122612261";
//		System.out.println(MerUtils.checkBankCard(test));
	}

}
