package com.icpay.payment.db.dao.mybatis.mapper;

import java.util.List;

import com.icpay.payment.db.dao.mybatis.model.DailyProfitResultView;
import com.icpay.payment.db.dao.mybatis.model.DailyProfitResultViewExample;
import com.icpay.payment.db.dao.mybatis.model.modelExt.DailyProfitResultChnlTrans;
import com.icpay.payment.db.dao.mybatis.model.modelExt.DailyProfitResultEmployee;
import com.icpay.payment.db.dao.mybatis.model.modelExt.DailyProfitResultHuanbi;
import com.icpay.payment.db.dao.mybatis.model.modelExt.DailyProfitResultManager;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MchntProfitToMerBean;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MchntProfitToWeekBean;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MchntTransHuanbiBean;

public interface DailyProfitResultViewMapper {
    /**
     * Database table : view_daily_profit_result
     *
     * @mbg.generated
     */
    long countByExample(DailyProfitResultViewExample example);

    /**
     * Database table : view_daily_profit_result
     *
     * @mbg.generated
     */
    List<DailyProfitResultView> selectByPage(DailyProfitResultViewExample example);

    /**
     * Database table : view_daily_profit_result
     *
     * @mbg.generated
     */
    List<DailyProfitResultView> selectByExample(DailyProfitResultViewExample example);
    
    /*商户环比*/
    List<MchntTransHuanbiBean> selectHuanbi(MchntTransHuanbiBean bean);
	
    /*周报表*/
	List<MchntProfitToWeekBean> selectWeek(MchntProfitToWeekBean bean);
	
	/*商户日报*/
	List<MchntProfitToMerBean> selectMchntProfit(MchntProfitToMerBean bean);
	
	//商户交易环比报表-威力麻吉
	long countByHuanbiPpayFpay(DailyProfitResultHuanbi example);
	List<DailyProfitResultHuanbi> selectHuanbiPpayFpay(DailyProfitResultHuanbi bean);
	
	//商户交易环比报表-乐力太极
	long countByHuanbiLpayVpay(DailyProfitResultHuanbi example);
	List<DailyProfitResultHuanbi> selectHuanbiLpayVpay(DailyProfitResultHuanbi bean);
	
	//各支付类型环比报表-主管-威力麻吉
	long countByManagerPpayFpay(DailyProfitResultManager example);
	List<DailyProfitResultManager> selectManagerPpayFpay(DailyProfitResultManager bean);
	
	//各支付类型环比报表-主管-乐力太极
	long countByManagerLpayVpay(DailyProfitResultManager example);
	List<DailyProfitResultManager> selectManagerLpayVpay(DailyProfitResultManager bean);
	
	//各支付类型环比报表-员工-威力麻吉
	long countByEmployeePpayFpay(DailyProfitResultEmployee example);
	List<DailyProfitResultEmployee> selectEmployeePpayFpay(DailyProfitResultEmployee bean);
	
	//各支付类型环比报表-员工-乐力太极
	long countByEmployeeLpayVpay(DailyProfitResultEmployee example);
	List<DailyProfitResultEmployee> selectEmployeeLpayVpay(DailyProfitResultEmployee bean);
	
	//渠道交易环比
	long countByChnlTrans(DailyProfitResultChnlTrans example);
	List<DailyProfitResultChnlTrans> selectChnlTrans(DailyProfitResultChnlTrans bean);
	
}