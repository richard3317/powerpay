package com.icpay.payment.bm.web.controller.business;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.db.dao.mybatis.model.TransRptInfo;
import com.icpay.payment.db.service.ITransRptInfoService;

@Controller
@RequestMapping("/mchntChart")
public class MchntChartController extends BaseController {

	private static final Logger logger = Logger.getLogger(MchntChartController.class);

	private static final String RESULT_BASE_URI = "mchntChart";

	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			// 对于一些数据的处理
			// 处理交易状态
			String txnState = m.get("txnState");
			m.put("txnState", EnumUtil.translate(TxnEnums.TxnStatus.class, txnState, true));
			// 交易类型int_trans_cd
			String intTransCd = m.get("intTransCd");
			m.put("intTransCd", EnumUtil.translate(TxnEnums.TxnType.class, intTransCd, true));
		}
	};

	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		return super.toManage(model, false, RESULT_BASE_URI);
	}

	@SuppressWarnings("unlikely-arg-type")
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize, HttpServletRequest req) throws Exception {
		// 获取模糊查询的参数
		Map<String, String> qryParamMap = this.getQryParamMap();
		// System.out.println("测试" + qryParamMap);
		// 进行模糊查询前的处理
		if (null != qryParamMap && "01".equals(qryParamMap.get("intTransCd"))) {
			qryParamMap.put("intTransCd", null);
		}
		if (null != qryParamMap && "00".equals(qryParamMap.get("txnState"))) {
			qryParamMap.put("txnState", null);
		}
		double averageAt = 0;
		if (qryParamMap.get("averageAt") != null && !qryParamMap.get("averageAt").toString().equals("")) {
			averageAt = Double.valueOf(qryParamMap.get("averageAt").toString());
			qryParamMap.remove("averageAt");
		}
		ITransRptInfoService dbService = this.getDBService(ITransRptInfoService.class);
		List<TransRptInfo> resultList = dbService.select(qryParamMap);
		// 查找所有的商户id
		Set<String> newSet = new HashSet();
		for (TransRptInfo transRptInfo : resultList) {
			newSet.add(transRptInfo.getMchntCd());
			// 做数据处理---- 数据库是分，要显示元
			transRptInfo.setTransAt(transRptInfo.getTransAt() / 100);
			transRptInfo.setLastTransAt(transRptInfo.getLastTransAt() / 100);
		}
		// 最终结果，前台数据绑定
		List<TransRptInfo> finalList = new ArrayList<>();
		for (String newStr : newSet) {
			long curren = 0;
			long avg = 0;
			TransRptInfo result = null;
			for (TransRptInfo transRptInfo : resultList) {
				if (transRptInfo.getMchntCd().equals(newStr) && transRptInfo.getIntTransCd().indexOf("01") > -1) {
					if(result==null) {
						result = transRptInfo;
					}else {
						if(result.getRecCrtTs().getTime()<transRptInfo.getRecCrtTs().getTime()) {
							result=transRptInfo;
						}
					}
					curren += transRptInfo.getTransAt();
					avg = transRptInfo.getAverageAt() / 100;
				}
			}
			if (result != null) {
				result.setTransAt(curren);
				result.setAverageAt(avg);
				String percentageResult = "0.0";
				if (result.getAverageAt() != 0) {
					percentageResult = ((result.getAverageAt() - result.getTransAt()) * 1.0 / result.getAverageAt())
							* 100 + "";
				}
				result.setAverageAtWd(
						Math.abs(Long.valueOf(percentageResult.substring(0, percentageResult.indexOf(".")))));
				// if(当前小时交易量-七日平均小时交易量>=0){此商户无需继续计算和显示} else{交易量低于平均=
				// 七(日平均小时交易量/1000-当前小时交易量/1000) / (七日平均小时交易量/1000)}
				// System.out.println("七日的平均" + transRptInfo.getAverageAt());
				if (averageAt > 0) {
					if (result.getAverageAt() > 0) {
						if (result.getMchntCd().equals(newStr) && result.getTransAt() - result.getAverageAt() >= 0) {
							// 此商户无需继续计算和显示
						} else if (((result.getAverageAt() / 1000.0 - result.getTransAt() / 1000.0)
								/ (result.getAverageAt() / 1000)) <= averageAt) {
							finalList.add(result);
						}
					}
				} else {
					finalList.add(result);
				}
			}
		}
		
		List<List<TransRptInfo>> finallyList=groupList(finalList,pageSize);
		
		//声明集合，用于页面存储
		List<TransRptInfo> pageList=new ArrayList();
		for(int i=0;i<finallyList.size();i++){
			if(i==(pageNum-1)){
				pageList=finallyList.get(i);
			}
		}

		Pager<TransRptInfo> selectByPage = dbService.selectByPage(pageNum, pageSize, qryParamMap);
		selectByPage.setResultList(pageList);
		selectByPage.setTotal(finalList.size());
		selectByPage.setPageSize(pageSize);
		selectByPage.setPageNum(pageNum);
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT,
				commonBO.transferPager(selectByPage, BMConstants.PAGE_CONF_TRANSRPTINFO, ENTITY_TRANSFER));
	}

	/**
	 * 查看折线图
	 * 
	 */
	@RequestMapping(value = "/chart.do")
	public String statisticChart(String mchntCd, String txnState, String intTransCd, Model model) throws Exception {
		// 将参数存入作用域中，为的是给之后点击节点，提供参数
		model.addAttribute("ajaxTxnState", txnState);
		model.addAttribute("ajaxMchntCd", mchntCd);
		// 放入交易类型得值 为了判断是否是代付
		intTransCd = intTransCd.substring(0, 4);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		txnState = txnState.substring(0, 2);
		// 通过商户id查找数据 查询出多条数据
		Map<String, String> qryParamMap = new HashMap<>();
		qryParamMap.put("mchntCd", mchntCd);
		qryParamMap.put("endDate", "qry");
		qryParamMap.put("startDate", "qry");
		ITransRptInfoService dbService = this.getDBService(ITransRptInfoService.class);
		List<TransRptInfo> select = dbService.select(qryParamMap);
		// 声明集合,用来存储支付方式
		List<String> payforList = new ArrayList<>();
		// 遍历集合,目的是查找数据是否一致
		for (TransRptInfo transRptInfo : select) {
			// 首先判断该商户有几种支付方式
			// 第一次进去，集合肯定是没值的，所以做添加处理
			if (payforList.size() == 0) {
				payforList.add(transRptInfo.getIntTransCd());
			} else {
				// 判断交易方式是否已存在，存在的话，则中断当前循环，进入下一循环
				if (payforList.contains(transRptInfo.getIntTransCd())) {
					continue;
				} else {
					// 不存在则做添加处理
					payforList.add(transRptInfo.getIntTransCd());
				}
			}
		}

		// X轴坐标数据
		List<String> xList = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		int currentHour = calendar.get(calendar.HOUR_OF_DAY) + 1;
		// 制作x轴坐标
		for (int i = 0; i <= currentHour; i++) {
			xList.add(i + "");
		}
		// 制作X轴对应的数据
		List<Map<String, Object>> dataList = new ArrayList<>();
		for (String s : payforList) {
			Map<String, Object> map = new HashMap<>();
			// 处理支付类型的显示
			String translate = EnumUtil.translate(TxnEnums.TxnType.class, s, true);
			map.put("name", translate.substring(5));
			List<Double> listInt = new ArrayList<Double>();
			// 制作data
			for (int i = 0; i < currentHour; i++) {
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
				String format = df.format(new Date());
				// 声明变量，用来装总金额
				double sumMoney = 0;
				for (TransRptInfo transRptInfo : select) {
					if (transRptInfo.getMchntCd().equals(mchntCd) && transRptInfo.getTxnState().equals("10")
							&& transRptInfo.getIntTransCd().equals(s)) {
						// 代表这个时间段有值
						if (transRptInfo.getTransDt().equals(format) && transRptInfo.getTransHour().equals(i + "")) {// 更新时间
							BigDecimal bg = new BigDecimal(transRptInfo.getTransAt() / 1000000.0);
							double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
							sumMoney += f1;
						}
					}
				}
				BigDecimal bg = new BigDecimal(sumMoney);
				double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				listInt.add(f1);// 交易金额
			}
			map.put("data", listInt);
			dataList.add(map);
		}
		// ====================================================================
		// 制作七天之内的基准线
		Map<String, Object> servenDayLine = new HashMap<>();
		servenDayLine.put("name", "充值基准线");
		// 基准线y轴数据
		List<Double> servenDayList = new ArrayList<>();
		// 代付基准线
		Map<String, Object> servenWdDayLine = new HashMap<>();
		servenWdDayLine.put("name", "代付基准线");
		// 代付基准线y轴数据
		List<Double> servenWdDayList = new ArrayList<>();
		for (int i = 0; i < currentHour; i++) {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			String format = df.format(new Date());
			// 声明变量，用来装总金额
			double sumMoney = 0;
			// 声明变量，用来装代付总金额
			double dfMoney = 0;
			for (TransRptInfo transRptInfo : select) {
				if (transRptInfo.getMchntCd().equals(mchntCd) && transRptInfo.getTxnState().equals("10")) {
					// 代表这个时间段有值
					if (transRptInfo.getTransDt().equals(format) && transRptInfo.getTransHour().equals(i + "")) {// 更新时间
						BigDecimal bg = new BigDecimal(transRptInfo.getAverageAt() / 1000000.0);
						double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						sumMoney = f1;
						BigDecimal bg1 = new BigDecimal(transRptInfo.getAverageAtWd() / 1000000.0);
						double f2 = bg1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						dfMoney = f2;
					}
				}
			}
			servenDayList.add(sumMoney);
			servenWdDayList.add(dfMoney);
		}
		servenDayLine.put("data", servenDayList);
		servenWdDayLine.put("data", servenWdDayList);
		// ================================================================================================
		// 交易总金额（除代付外）
		Map<String, Object> allLine = new HashMap<>();
		allLine.put("name", "充值总金额");
		List<Double> listAllInt = new ArrayList<Double>();
		// 制作data
		for (int i = currentHour - 1; i >= 0; i--) {
			// 声明变量，用来装总金额
			double sumMoney = 0;
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			String format = df.format(new Date());
			for (TransRptInfo transRptInfo : select) {
				if (transRptInfo.getMchntCd().equals(mchntCd) && transRptInfo.getTxnState().equals("10")
						&& !"5210".equals(transRptInfo.getIntTransCd())) {
					String dateString = formatter.format(transRptInfo.getRecCrtTs());
					// 代表这个时间段有值
					if (transRptInfo.getTransDt().equals(format) && transRptInfo.getTransHour().equals(i + "")) {// 更新时间
						BigDecimal bg = new BigDecimal(transRptInfo.getTransAt() / 1000000.0);
						double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						sumMoney += f1;
					}
				}
			}
			BigDecimal bg = new BigDecimal(sumMoney);
			double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			listAllInt.add(f1);// 交易金额
		}
		allLine.put("data", listAllInt);
		dataList.add(servenDayLine);
		dataList.add(servenWdDayLine);
		dataList.add(allLine);
		model.addAttribute("dataList", JSON.toJSONString(dataList));
		model.addAttribute("xList", JSON.toJSONString(xList));
		return "/mchntChart/chartDetail";
	}

	/**
	 * 当点击图像上的某一点时触发
	 * 
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping("/getDataByX.do")
	public void getDataByX(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// x轴得值
		String xValue = req.getParameter("xValue");
		// y轴得值
		String yValue = req.getParameter("yValue");
		// 查询商户得状态
		String ajaxTxnState = req.getParameter("ajaxTxnState").substring(0, 2);
		// 商户id
		String ajaxMchntCd = req.getParameter("ajaxMchntCd");
		// 支付方式
		String int_trans_cd = req.getParameter("ajaxName");
		Map<String, String> qryParamMap = new HashMap<>();
		qryParamMap.put("mchntCd", ajaxMchntCd);
		qryParamMap.put("endDate", "qry");
		qryParamMap.put("startDate", "qry");
		ITransRptInfoService dbService = this.getDBService(ITransRptInfoService.class);
		List<TransRptInfo> select = dbService.select(qryParamMap);

		// 声明map集合，用来装返回得结果
		Map<String, Object> map = new HashMap<>();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String format = df.format(new Date());

		if (!"单笔代付".equals(int_trans_cd)) {
			// 网银单总笔数：
			int sum = 0;
			// 网银成功数：
			// 交易量等于成功数/总比数
			double success = 0;
			// 交易量低于平均：
			double upperAvg = 0;

			for (TransRptInfo transRptInfo : select) {
				String translate = EnumUtil.translate(TxnEnums.TxnType.class, transRptInfo.getIntTransCd(), true);
				if (transRptInfo.getTransDt().equals(format) && transRptInfo.getTransHour().equals(xValue)
						&& translate.indexOf(int_trans_cd) > -1) {
					sum++;
					upperAvg = Double.valueOf(transRptInfo.getAverageAt()) - Double.valueOf(yValue);
					if (transRptInfo.getTxnState().equals("10")) {
						success++;
					}
				}
			}
			success = success / sum;

			map.put("sum", sum);
			map.put("successValue", Double.valueOf(String.format("%.2f", success == 0 ? 0.00 : success)));
			map.put("upperAvg", Double.valueOf(String.format("%.2f", upperAvg)));
		} else {
			// 查询代付类型匹配的值
			// 代付交易总笔数
			int sum = 0;
			// 代付处理中笔数(01)
			int being = 0;
			// 代付失败笔数
			int fail = 0;
			for (TransRptInfo transRptInfo : select) {
				if (transRptInfo.getTransDt().equals(format) && transRptInfo.getTransHour().equals(xValue)
						&& transRptInfo.getIntTransCd().equals("5210")) {
					sum++;
					if (transRptInfo.getTxnState().equals("20")) {// 交易失败
						fail++;
					}
					if (transRptInfo.getTxnState().equals("01")) {// 交易处理中
						being++;
					}
				}
			}
			map.put("sum", sum);
			map.put("being", being);
			map.put("fail", fail);
		}
		map.put("ajaxIntTransCd", int_trans_cd);// 只是为了来判断是不是代付
		PrintWriter writer = resp.getWriter();
		writer.write(JSON.toJSONString(map));
		writer.flush();
		writer.close();
	}

    /**
     * 分组调用
     *
     * @param list
     * @return
     */
    public List<List<TransRptInfo>> groupList(List<TransRptInfo> list,int pageSize) {
        int listSize = list.size();
        int toIndex = pageSize;
        List<List<TransRptInfo>> TransRptInfoList = new ArrayList<>();

        for (int i = 0; i < list.size(); i += pageSize) {
            if (i + pageSize > listSize) {        //作用为toIndex最后没有100条数据则剩余几条newList中就装几条
                toIndex = listSize - i;
            }
            List<TransRptInfo> newList = list.subList(i, i + toIndex);
            TransRptInfoList.add(newList);
        }

        return TransRptInfoList;
    }

}
