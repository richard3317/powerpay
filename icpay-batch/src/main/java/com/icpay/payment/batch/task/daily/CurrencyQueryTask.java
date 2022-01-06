package com.icpay.payment.batch.task.daily;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;

import com.icpay.payment.batch.cache.BatchConfigCache;
import com.icpay.payment.batch.constants.BatchConstants;
import com.icpay.payment.batch.task.BatchTaskTemplate;
import com.icpay.payment.batch.task.BatchTaskTemplate.Consts;
import com.icpay.payment.common.enums.ProfitEnums.ProfitPeriod;
import com.icpay.payment.common.enums.ProfitEnums.ProfitState;
import com.icpay.payment.common.exception.SystemException;
import com.icpay.payment.common.jdbc.bo.AgentProfitInfo;
import com.icpay.payment.common.jdbc.bo.AgentProfitLog;
import com.icpay.payment.common.jdbc.bo.SettleProfitResult;
import com.icpay.payment.common.jdbc.mapper.AgentProfitInfoMapper;
import com.icpay.payment.common.jdbc.mapper.SettleProfitResultMapper;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.FileUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.service.InternalGatewayService;
import com.icpay.payment.service.client.InternalGatewayClient;
import com.icpay.payment.service.MerParams;

@Component("currencyQueryTask")
public class CurrencyQueryTask extends BatchTaskTemplate {
	
	private static final String update_tbl_exchange_rate_pay = "update tbl_exchange_rate set amount_target = ?,rec_upd_ts = CURRENT_TIMESTAMP  where cat = ? and (int_trans_cd= 0181 or int_trans_cd= 0182)";
	private static final String update_tbl_exchange_rate_withdraw = "update tbl_exchange_rate set amount_target = ?,rec_upd_ts = CURRENT_TIMESTAMP where cat = ? and int_trans_cd= 5250";
	private static final String intTxnType ="0091";
	@Override
	protected void doTask() {
		logger.info("進入汇率查询作业");
		Map req = new HashMap<String, String>();

		Map param = new HashMap<String, String>();
		try {
			//币种查询-分类
			String cat = MerParams.getParam("00", Consts.DEFAULT_MER, Consts.CAT_DAILY_CURRENCY_QUERY,
					Consts.CAT, "BITPAY");
			//币种查询-渠道编号
			String chnlId = MerParams.getParam("00", Consts.DEFAULT_MER, Consts.CAT_DAILY_CURRENCY_QUERY,
					Consts.CHNL_ID, "V0");
			//币种查询-渠道商户号
			String chnlMerId = MerParams.getParam("00", Consts.DEFAULT_MER, Consts.CAT_DAILY_CURRENCY_QUERY,
					Consts.CHNL_MER_ID, "VGP20200001");
			
			req.put("chnlMerId", chnlMerId);
			req.put("currencyCode", "USDT");
			req.put("exCurrencyCode", "VHKD");
			req.put("txnAmt", "1");
			req.put("unit", "1");
			Map<String, String> resMap = invokeChnlService(chnlId, intTxnType, req, param);
			logger.info("上游返回 =" + resMap); //"inPrice":"7.67106" "outPrice":"7.75"
			updatePayCurrency(resMap, cat);
			updateWithDrawCurrency(resMap, cat);

			logger.info("BITPAY汇率更新处理完成"+resMap);
		} catch (SystemException e) {
			logger.warn("BITPAY汇率更新执行失败",e);
			e.printStackTrace();
		}

	}
	
	
	private Map<String, String> invokeChnlService(String string, String string2, Map map, Map map2) throws SystemException {
		InternalGatewayService svc = getSvc();
		return svc.invokeChnlService(string, string2, map, map2);
		
	}
	
	private static InternalGatewayService getSvc() throws SystemException {
		InternalGatewayService svc = (InternalGatewayService) ServiceProxy.getService(InternalGatewayService.class);
		if (svc == null)
			throw new SystemException("InternalGatewayService not found!");
		return svc;
	}

	@Override
	protected String getTaskNm() {
		return "BitPay币别汇率查询作业任务";
	}
	
	
	public int updateWithDrawCurrency(final Map<String, String> res, final String category){

		return this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(update_tbl_exchange_rate_withdraw);
				ps.setString(1, res.get("outPrice"));
				ps.setString(2, category);
				return ps;
			}
		});

	}
	
	public int updatePayCurrency(final Map<String, String> res, final String category){

		return this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(update_tbl_exchange_rate_pay);
				ps.setString(1, res.get("inPrice"));
				ps.setString(2, category);
				return ps;
			}
		});

	}
}

