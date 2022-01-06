package test.simple;

import static org.junit.Assert.*;

import org.junit.Test;

import com.icpay.payment.common.constants.Names.TAG;
import com.icpay.payment.common.utils.TagUtils;

public class Test1 {

	@Test
	public void test() {
		String curTags="{\"preCheck\":\"robin:17;\"}";
		//curTags = TagUtils.setTag(curTags, TAG.preCheck, "robin:17;");
		System.out.println(curTags);
		System.out.println(TagUtils.getString(curTags, TAG.preCheck, "NULL"));
		System.out.println(TagUtils.getString(curTags, TAG.preCheck));
	}

}
