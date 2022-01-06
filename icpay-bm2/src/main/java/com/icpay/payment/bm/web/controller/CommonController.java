package com.icpay.payment.bm.web.controller;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.cache.BankInfoCaChe;
import com.icpay.payment.bm.cache.ChnlMchntInfoCache;
import com.icpay.payment.bm.cache.DataDicCache;
import com.icpay.payment.bm.cache.RegionInfoCache;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.ValidateCodeImg;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.IOUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitPolicy;
import com.icpay.payment.db.dao.mybatis.model.BankInfo;
import com.icpay.payment.db.dao.mybatis.model.RegionInfo;
import com.icpay.payment.db.service.IAgentProfitPolicyService;

@Controller
public class CommonController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(CommonController.class);

	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public String index(Model model) {
		if (commonBO.getSessionUser() != null) {
			return "main";
		} else {
			model.addAttribute("rondom", String.valueOf((new Date()).getTime()));
			return "login";
		}
	}
	
	@RequestMapping(value = "/main.do")
	public String main(Model model) {
		return "main";
	}
	
	@RequestMapping(value = "/welcome.do", method = RequestMethod.GET)
	public String welcome() {
		return "welcome";
	}
	
	@RequestMapping(value = "/expired.do", method = RequestMethod.GET)
	public String expired() {
		return "common/expired";
	}
	
	@RequestMapping(value = "/validateCode.do", method = RequestMethod.GET)
	public String validateCode(HttpServletRequest request, HttpServletResponse response) {
		ValidateCodeImg img = new ValidateCodeImg();
		commonBO.setSessionAttr(Constant.VALIDATION_CODE_KEY, img.getStrRand());
		BufferedImage image = img.getImage();
		OutputStream os = null;
		try {
			response.reset();
			response.setContentType("image/jpeg");
			os = response.getOutputStream();
			ImageIO.write(image, "jpg", os);
		} catch (Exception e) {
			logger.error("生成图形校验码失败", e);
		} finally {
			if (os != null) {
				IOUtil.close(os);
			}
		}
		return null;
	}
	
	@RequestMapping(value = "/loadCity.do", method = RequestMethod.GET)
	public @ResponseBody AjaxResult loadCity(String provCd) {
		List<RegionInfo> cityLst = RegionInfoCache.getCityLst(provCd);
		return commonBO.buildSuccResp("cityLst", cityLst);
	}
	
	@RequestMapping(value = "/loadDistrict.do", method = RequestMethod.GET)
	public @ResponseBody AjaxResult loadDistrict(String cityCd) {
		List<RegionInfo> districtLst = RegionInfoCache.getDistrictLst(cityCd);
		return commonBO.buildSuccResp("districtLst", districtLst);
	}
	
	@RequestMapping(value = "/loadBankBranchLst.do")
	public @ResponseBody List<BankInfo> loadBankBranchLst(String bankName, String brankBranchNm) {
		AssertUtil.strIsBlank(bankName, "bankName is blank.");
		
		List<BankInfo> lst = BankInfoCaChe.getBankBranchLst(bankName);
		List<BankInfo> result = new ArrayList<BankInfo>();
		for (BankInfo b : lst) {
			if (StringUtil.isBlank(brankBranchNm) 
					|| b.getBranchBankName().indexOf(brankBranchNm) > -1) {
				result.add(b);
				if (result.size() == 50) {
					break;
				}
			}
		}
		return result;
	}
	
	@RequestMapping(value="/loadAgentTradeType.do", method = RequestMethod.GET)
	public @ResponseBody AjaxResult loadAgentTradeType(String agentCd) {
		AssertUtil.strIsBlank(agentCd, "agentCd is blank.");
		IAgentProfitPolicyService service = this.getDBService(IAgentProfitPolicyService.class);
		Map<String, String> qryParamMap = new HashMap<String, String>();
		qryParamMap.put("agentCd", agentCd);
		List<AgentProfitPolicy> lst = service.select(qryParamMap);
		List<Map<String, String>> tradeTypeLst = new ArrayList<Map<String,String>>();
		Set<String> existTradeTp = new HashSet<String>();
		if (lst != null && lst.size() > 0) {
			for (AgentProfitPolicy ap : lst) {
				String tradeType = ap.getTradeType();
				if (!existTradeTp.contains(tradeType)) {
					existTradeTp.add(tradeType);
					Map<String, String> m = new HashMap<String, String>();
					m.put("tp", tradeType);
					m.put("v", DataDicCache.translate(Constant.DATA_DIC_DATA_TP.TRADE_TYPE, tradeType));
					tradeTypeLst.add(m);
				}
			}
		}
		if (tradeTypeLst.size() > 0) {
			Collections.sort(tradeTypeLst, new Comparator<Map<String, String>>() {
				@Override
				public int compare(Map<String, String> o1, Map<String, String> o2) {
					return o1.get("tp").compareTo(o2.get("tp"));
				}
			});
		}
		return commonBO.buildSuccResp("tradeTypeLst", tradeTypeLst);
	}
	
	@RequestMapping(value = "/getChnlMchnts.do", method = RequestMethod.GET)
	public @ResponseBody AjaxResult getChnlMchnts(String chnlId) {
		List<Map<String,String>> list = new ArrayList<>();
		list = ChnlMchntInfoCache.getInstance().getByChnlId(chnlId);
		AjaxResult resp= commonBO.buildSuccResp("chnlMchntList", list);
		return resp;
	}
	
}
