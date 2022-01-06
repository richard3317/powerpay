package test.dbservice;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.icpay.payment.common.utils.ApplicationContextUtil;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.mapper.ExtMapper.TxnLogViewExtMapper;
import com.icpay.payment.db.dao.mybatis.model.TxnLogView;
import com.icpay.payment.db.dao.mybatis.model.TxnLogViewExample;
import com.icpay.payment.db.dao.mybatis.model.modelExt.TxnLogSummary;
import com.icpay.payment.db.service.impl.TxnLogViewService;

public class DbSvcTests {
	static {
		init();
	}
	
	private static ApplicationContext ac=null;
	public static void init(){
    	System.out.println("[init] App init...");
    	//ac = new ClassPathXmlApplicationContext(new String[]{"classpath:common-data-ctx.xml"});
        ac = new ClassPathXmlApplicationContext(new String[]{"classpath:app-ctx.xml"});
    	
    	SqlSessionTemplate sqlSession = (SqlSessionTemplate) ac.getBean("sqlSession");
    	System.out.println("[init] App init with no error! sqlSession="+sqlSession);
        //从Spring容器中根据bean的id取出我们要使用的userService对象
		//    	hsmKeysDataSvc = (HsmKeysDataSvc) ac.getBean("hsmKeysDataSvc");
		//    	System.out.println("[init] hsmKeysDataSvc bean get OK! hsmKeysDataSvc = "+hsmKeysDataSvc);
    }
	
	protected <T> T getMapper(Class<T> clazz) {
		SqlSession sqlSession = ApplicationContextUtil.getBean("sqlSession");
		T mapper = sqlSession.getMapper(clazz);
		AssertUtil.objIsNull(mapper, "未找到指定的mapper:" + clazz.getName());
		return mapper;
	}
	
	protected <T> T getBean(String beanName) {
		Object obj = ApplicationContextUtil.getBean(beanName);
		return (T) obj;
	}

	@Test
	public void test1() {
		TxnLogViewExtMapper dao=getMapper(TxnLogViewExtMapper.class);
		Long count=dao.countByExample(null, "09");
		System.out.println(count);
	}
	
	@Test
	public void testTxnLogView() {
		TxnLogViewExtMapper dao=getMapper(TxnLogViewExtMapper.class);
		TxnLogViewExample example= new TxnLogViewExample();
		example.createCriteria().andIntTransCdEqualTo("5210");
		TxnLogSummary res=dao.selectSummary(example, "09");
		System.out.println(res);
		if (res!=null)
			System.out.println(res.getSumTransFeeDelta());
	}
	
	@Test
	public void testTxnLogViewSvc() {
		TxnLogViewService svc = getBean("txnLogViewService");
		System.out.println(svc);
		TxnLogSummary res=svc.selectSummary("09", Utils.newMap(
				"startDate","20180905"
				));
		System.out.println(res);
	}
	
	@Test
	public void testTxnLogViewSvc2() {
		TxnLogViewService svc = getBean("txnLogViewService");
		System.out.println(svc);
		List<TxnLogView> list=svc.select("09", Utils.newMap(
				"startDate","20180905",
				"endDate","20180908",
				"transType","52"
				));
		if (list!=null)
			for(TxnLogView rec : list)
				System.out.println(rec);
	}

}
