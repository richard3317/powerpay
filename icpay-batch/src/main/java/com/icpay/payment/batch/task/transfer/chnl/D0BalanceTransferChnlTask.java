package com.icpay.payment.batch.task.transfer.chnl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import com.icpay.payment.batch.task.transfer.BalanceTransferBaseTask;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.data.dao.ChnlMchntAccountInfoMapper;
import com.icpay.payment.common.data.model.ChnlMchntAccountInfo;
import com.icpay.payment.common.data.model.ChnlMchntAccountInfoKey;
import com.icpay.payment.common.data.svc.DAO;
import com.icpay.payment.common.data.utils.TxnDataUtils;
import com.icpay.payment.common.enums.CurrencyEnums.CurrType;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.service.ChnlMerAccService;
import com.icpay.payment.service.MerParams;

/**
 * TODO : 结转功能需改为多币别支持
 */
@Component("D0BalanceTransferChnlTask")
public class D0BalanceTransferChnlTask extends BalanceTransferBaseTask  {
	
	//SELECT 
	//	t.chnl_id, 
	//	t.chnl_mchnt_cd, 
	//	tp.curr_cd, 
	//	tp.balance_transfer, 
	//	tp.balance_transfer_t1 
	//FROM 
	//	tbl_chnl_mchnt_info AS t 
	//	RIGHT JOIN 
	//	tbl_chnl_mchnt_settle_policy AS tp 
	//	ON 
	//		t.chnl_id = tp.chnl_id AND 
	//		t.chnl_mchnt_cd = tp.chnl_mchnt_cd AND 
	//		tp.curr_cd = '156' 
	//WHERE
	//	( tp.balance_transfer = '1' OR tp.balance_transfer_t1 = '1' ) 
	//	AND t.chnl_id = 'P9' 
	//	AND t.state = '1' 
	//	AND t.chnl_mchnt_cd NOT IN ( 
	//	  SELECT tr.mchnt_cd FROM tbl_balance_transfer_log tr 
	//		WHERE tr.transfer_dt = '20210609' AND tr.trans_chnl = t.chnl_id AND tr.d0_result = '1' 
	//	)	
	/**
	 * 查詢所有需要進行結轉的商戶號
	 */
	private static final String query_settle_policy=
	  //"SELECT t.chnl_mchnt_cd,t.transfer_time,t.balance_transfer,t.balance_transfer_t1 FROM tbl_chnl_mchnt_info AS t WHERE (t.`balance_transfer` = '1' OR t.`balance_transfer_t1` = '1') AND t.chnl_id = ? AND t.state = '1' AND t.chnl_mchnt_cd NOT IN ( SELECT tr.mchnt_cd FROM tbl_balance_transfer_log tr WHERE tr.transfer_dt = ? AND tr.trans_chnl = t.chnl_id AND tr.d0_result = '1' )" ;
				"SELECT \r\n"
				+ "	t.chnl_id, \r\n"
				+ "	t.chnl_mchnt_cd, \r\n"
				+ "	tp.curr_cd, \r\n"
				+ "	tp.balance_transfer, \r\n"
				+ "	tp.balance_transfer_t1 \r\n"
				+ "FROM \r\n"
				+ "	tbl_chnl_mchnt_info AS t \r\n"
				+ "	RIGHT JOIN \r\n"
				+ "	tbl_chnl_mchnt_settle_policy AS tp \r\n"
				+ "	ON \r\n"
				+ "		t.chnl_id = tp.chnl_id AND \r\n"
				+ "		t.chnl_mchnt_cd = tp.chnl_mchnt_cd AND \r\n"
				+ "		tp.curr_cd = ? \r\n"
				+ "WHERE\r\n"
				+ "	( tp.balance_transfer = '1' OR tp.balance_transfer_t1 = '1' ) \r\n"
				+ "	AND t.chnl_id = ? \r\n"
				+ "	AND t.state = '1' \r\n"
				+ "	AND t.chnl_mchnt_cd NOT IN ( \r\n"
				+ "	  SELECT tr.mchnt_cd FROM tbl_balance_transfer_log tr \r\n"
				+ "		WHERE tr.transfer_dt = ? AND tr.trans_chnl = t.chnl_id AND tr.d0_result = '1' \r\n"
				+ "	)	"
			 	+"";
	
//	private static final String query_settle_policy=
//			  "SELECT t.chnl_mchnt_cd,t.balance_transfer,t.balance_transfer_t1 FROM tbl_chnl_mchnt_info AS t WHERE t.`balance_transfer` = '1' AND t.chnl_id = ? AND t.state = '1' AND t.chnl_mchnt_cd NOT IN ( SELECT tr.mchnt_cd FROM tbl_balance_transfer_log tr WHERE tr.transfer_dt = ? AND tr.trans_chnl = t.chnl_id AND tr.d0_result = '1' )" ;
	
	@Override
	protected void doTask() {
		//查询渠道编号
		List<String> list = getChnlList();
			if(list != null && list.size() > 0) {
				for(String chnlId : list) {//循环渠道编号
					try {
						exe(chnlId);
					} catch (Exception e) {
						e.printStackTrace();
						this.error(e, "结转任务失败! "+e.getMessage());
					}
				}
			}
	}
	
	public void exe(String chnlId) throws Exception {
//		String taskTime ="";
//		taskTime = MerParams.getParam(chnlId,DEFAULT_MER, TRANSFER_CAT ,TRANSFER_D0TIME, "235000");
//		if (! this.shouldRunTask(taskTime)) return ;
		
		String defaultTaskTime =
				MerParams.getParam(chnlId,DEFAULT_MER, TRANSFER_CAT ,TRANSFER_D0TIME, "000000");
		String taskTime;		
		
//		CurrType currency=CurrType._156;
		for (CurrType currency :CurrType.values())
			doTaskForCurrency(chnlId, defaultTaskTime, currency);

	}

	private void doTaskForCurrency(String chnlId, String defaultTaskTime, CurrType currency) {
		String taskTime;
		//查詢所有需要進行結轉的商戶號
		//List<String> mchntList = jdbcTemplate.queryForList(query_settle_policy,new Object[]{chnlId, this.batchDt}, String.class);
		List<Map<String, Object>> mchntList = jdbcTemplate.queryForList(query_settle_policy,new Object[]{currency.getCode(), chnlId, this.batchDt});
		if (! TxnDataUtils.hasRecord(mchntList)) {
			info("渠道：%s 日期：%s 币别：%s， 任務： %s， 无任何商户符合執行条件! ", chnlId, this.batchDt, currency.getCode(), this.getTaskNm());
			return;
		}
		else {
			debug("渠道：%s 日期：%s 币别：%s， 任務：%s 開始。。。", chnlId, this.batchDt, currency.getCode(), this.getTaskNm());
		}
		
		
		for(Map<String, Object> mchntInfo : mchntList) {
			String mchnt = ""+mchntInfo.get("chnl_mchnt_cd");
			String trans_d0 = ""+mchntInfo.get("balance_transfer");
			String trans_t1 = ""+mchntInfo.get("balance_transfer_t1");
			taskTime = strVal(mchntInfo.get("transfer_time"));
			if (Utils.isEmpty(taskTime)) taskTime = defaultTaskTime;
			if (! this.shouldRunTask(taskTime, chnlId, mchnt)) continue; //非执行时间
			
			//查詢虛擬帳戶
			ChnlMchntAccountInfoMapper dao = DAO.getChnlMchntAccountInfoMapper();
			ChnlMchntAccountInfo macc = dao.selectByPrimaryKey(new ChnlMchntAccountInfoKey(chnlId, mchnt, currency.getCode()));
			if(macc != null ) {
				String opType = Constant.OPERTYPE._51 ;
				String d0Amount =  ""+macc.getAvailableBalance();
				if (!"1".equals(trans_d0))
					d0Amount="0";
				String memo = String.format("商戶: %s-%s, 结转金额: %s, 操作: %s", chnlId, mchnt, d0Amount, opType);
				info(memo);
				String taskResult="";
				try {
					this.chnlMerAccService().adjust(chnlId, mchnt, opType, d0Amount, currency.getCode(), currency.getDefaultUnit(), this.getTaskNm(), memo);
					taskResult="1";
				} catch (Exception e) {
					taskResult="2";
					error(e, "结转任务发生失败! 商戶:%s-%s, 结转金额:%s, 操作:%s",chnlId,  mchnt, d0Amount, opType);
				}
				//插入余额结转结果表
				info("插入余额结转结果表，执行结果：" + taskResult);
				insertBalanceTransferD0Result(chnlId, mchnt, d0Amount, currency.getCode(), currency.getDefaultUnit(), taskResult, trans_t1, this.getTaskNm(), opType);
			}
			else {
				warn(String.format("无效商户: %s-%s", chnlId, mchnt));
			}
		}
	}
	
	@Override
	protected String getTaskNm() {
		return "渠道D0余额结转任务";
	}
	
	ChnlMerAccService cmas=null;
	protected ChnlMerAccService chnlMerAccService(){
		if(cmas==null){
			cmas=ServiceProxy.getService(ChnlMerAccService.class);
		}
		return cmas;
	}
}
