package com.icpay.payment.bm.quartz.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.icpay.payment.common.constants.RspCd;
import com.icpay.payment.common.constants.Constant.INTER_MSG;
import com.icpay.payment.common.constants.Constant.MSG;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.VirtualTermInfo;
import com.icpay.payment.db.service.IVirtualTermInfoService;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.service.ChannelService;

@Component("virualTermSignInJob")
public class VirualTermSignInJob extends BaseQuartzJob {
	
	private static final Logger logger = Logger.getLogger(VirualTermSignInJob.class);

	@Override
	public void doProcess() {
		
//		// 查找所有需要自动签到的虚拟终端信息
//		Map<String, String> qryParamMap = new HashMap<String, String>();
//		qryParamMap.put("autoSignIn", "1");
//		IVirtualTermInfoService virtualTermService = 
//			DBHessionServiceClient.getService(IVirtualTermInfoService.class);
//		List<VirtualTermInfo> lst = virtualTermService.select(qryParamMap);
//		logger.info("自动签到虚拟终端信息条数:" + lst.size());
//		
//		try {
//			// 逐条调用签到服务
//			ChannelService service = ServiceProxy.getService(ChannelService.class);
//			for (VirtualTermInfo vtm : lst) {
//				String termInfo = StringUtil.concat("_", vtm.getChnlId(), vtm.getMchntCd(), vtm.getTermId());
//				try {
//					Map<String, String> req = new HashMap<String, String>();
//					req.put(INTER_MSG.channel, vtm.getChnlId());
//					req.put(INTER_MSG.chnlMchntId, vtm.getMchntCd());
//					req.put(INTER_MSG.chnlTermId, vtm.getTermId());
//					logger.info("#签到开始#:" + termInfo);
//					Map<String, String> resp = service.signIn(req);
//					String rspCode = resp.get(MSG.respCode);
//					// 成功是否会返回成功代码？
//					if (RspCd.Z_0001.equals(rspCode)) {
//						logger.info("#签到失败#:" + termInfo);
//					} else {
//						logger.info("#签到成功#:" + termInfo);
//					}
//				} catch (Exception e) {
//					logger.error("#签到出错#:" + termInfo, e);
//				}
//			}
//		} catch (Exception e) {
//			logger.error("#自动签到出错#", e);
//		}
//		
//		// 记录操作日志
//		operLogBO.log(CommonEnums.SysInfo.CONSOLE, "system", this.getJobDesc(), 
//				CommonEnums.OpType.QUARTZ_JOB_RUNNING, "system", "虚拟终端自动签到任务");
	}

	@Override
	protected String getJobDesc() {
		return "虚拟终端自动签到任务";
	}
	
}
