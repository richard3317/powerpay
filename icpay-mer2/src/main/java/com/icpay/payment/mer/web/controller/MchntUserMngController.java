package com.icpay.payment.mer.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.icpay.gauth.GoogleAuthenticator;
import com.icpay.payment.common.constants.Names.INTER_MSG;
import com.icpay.payment.common.constants.Names.MSG;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.enums.CommonEnums.MchntUserSt;
import com.icpay.payment.common.enums.MerEnums.MerUserRole;
import com.icpay.payment.common.enums.MerEnums.MerUserState;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.Converter;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.PwdUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfo;
import com.icpay.payment.mer.bo.MchntBO;
import com.icpay.payment.mer.session.SessionKeys;
import com.icpay.payment.mer.util.GoogleAuthenticatorUtils;
import com.icpay.payment.mer.util.MerUtils;
import com.icpay.payment.mer.web.interceptor.RolesSessionCheck;
import com.icpay.payment.risk.RISK;
import com.icpay.payment.risk.RiskEvent;

import static com.icpay.payment.risk.MerUser.merUser;

@Controller
@RequestMapping(value = "/mchntUser")
public class MchntUserMngController extends BaseController {

	private static final Logger logger = Logger.getLogger(MchntUserMngController.class);
	@Autowired
	private MchntBO mchntBO;

	/**
	 * 人员管理的界面
	 *
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/userMng")
	@RolesSessionCheck(roles= {MerUserRole.SuperUser})
	public String secritySetting(Model model, HttpServletRequest request, HttpServletResponse resp)
			throws ParseException {
		HttpSession session = request.getSession();
		// 获取商户号
		String mchntCd = commonBO.getMchntCd();
		// 获取所有的商户信息mchntCd, userId, role
		List<MchntUserInfo> mchList = mchntBO.selectMchntUserList(mchntCd, null, null);
		// 声明变量，查询只符合两种角色的对象
		List<MchntUserInfo> resultList = new ArrayList<MchntUserInfo>();
		for (MchntUserInfo mchntUserInfo : mchList) {
			if ("wd".equals(mchntUserInfo.getUserRole()) || "py".equals(mchntUserInfo.getUserRole())) {
				resultList.add(mchntUserInfo);
			}
		}
		model.addAttribute("mchList", resultList);
		return "userMng";
	}

	/**
	 * 跳转页面kipeUrl(增加和修改)
	 */
	@RequestMapping("/kipeUrl")
	@RolesSessionCheck(roles= {MerUserRole.SuperUser})
	public String kipeUrl(String type, Model model, HttpServletRequest request, HttpServletResponse resp) {
		MchntUserInfo mchntUserInfo = null;
		if (!type.equals("0")) {// 相当于 修改
			// 获取商户号
			String mchntCd = commonBO.getMchntCd();
			String userId = type;
			// 获取商户信息
			mchntUserInfo = mchntBO.selectMchntUserInfo(mchntCd, userId);
			model.addAttribute("mchntUserInfo", mchntUserInfo);
			model.addAttribute("newPwd", "");
		}
		if (type.equals("0")) {// 相当于 添加
			model.addAttribute("mchntUserInfo", mchntUserInfo);
			model.addAttribute("newPwd", "");
		}

		return "addUserMng";
	}

	/**
	 * 验证该商户下的二级商户账号（userId）是否存在 (包含修改的方法)
	 *
	 * @param model
	 * @param request
	 * @param resp
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/verifyUserId")
	@RolesSessionCheck(roles= {MerUserRole.SuperUser})
	public String verifyUserId(String type, String userId, Model model, String userRole, String hSecretHash, String hGooglePwd,
			HttpServletRequest request,	HttpServletResponse resp) throws IOException {
		AssertUtil.strIsBlank(hSecretHash, "密码不能为空");
		AssertUtil.strIsBlank(hGooglePwd, "谷歌验证不能为空");
		
		Map<String,String> eventParams = //登入资讯(Event夹带用)
        		Utils.newMap(
        				INTER_MSG.sessionId, this.getSessionId(request),
        				INTER_MSG.requestUri , "/verifyUserId" ,
        				MSG.merId, commonBO.getMchntCd(),
        				MSG.userId, commonBO.getMchntUser().getUserId(),
        				INTER_MSG.reqIp, ""+MerUtils.getRemoteIp(request),
        				"userRole", userRole
        				);
		
		String vResult = validatePwdAndGoogleCode(hSecretHash, hGooglePwd, eventParams);
		if ("10".equals(vResult)) {//登录密码失败
			throw new BizzException("密码错误");
		} else if ("11".equals(vResult)) {//谷歌验证失败
			throw new BizzException("谷歌验证错误");
		} else if ("12".equals(vResult)) {//谷歌验证码连续输入錯誤三次，帐号已被锁定
			throw new BizzException("谷歌验证码连续输入錯誤三次，帐号已被锁定");
		} else {
			MchntUserInfo mchntUserInfo = new MchntUserInfo();
			// 获得登录的商户号
			String mchntCd = commonBO.getMchntCd();
			PrintWriter pWriter = resp.getWriter();
			String result = "3";
			String newPwd = "";
			if ("0".equals(type)) {
				// 初始密码跟登录名一致
				mchntUserInfo.setMchntCd(mchntCd);
				mchntUserInfo.setUserId(userId);
				// 根据条件查询对象
				List<MchntUserInfo> mchList = mchntBO.selectMchntUserList(mchntCd, userId, null);
				if (mchList.size() > 0) {// 存在
					result = "1";
				} else {
					// 不存在 进行添加操作 调用添加方法
					//新建人员，默认密码变更为 8 位乱码
					newPwd = StringUtil.randomPwd(8);
					mchntUserInfo.setLoginPwd(PwdUtil.computeMD5Pwd(newPwd));
					mchntUserInfo.setPwdUpdTs(DateUtil.now19());
					mchntUserInfo.setUserRole(userRole);
					mchntUserInfo.setUserState(MchntUserSt._0.getCode());
				//	System.out.println("添加的角色" + userRole);
					int insertState = mchntBO.insertSelective(mchntUserInfo);
					result = "2";

		           	//TODO Publish event 新增登入账号
		           	RiskEvent.mer()
		               	.who(merUser(commonBO.getMchntCd(), commonBO.getMchntUser().getUserId()))
		               	.event(RISK.Event.User_Id_Add)
		               	.result(RISK.Result.Ok)
		               	.message("商户： %s, 用户： %s, 登入IP： %s, 新增登入账号成功：%s, 帐户类型：%s " , mchntCd, commonBO.getMchntUser().getUserId(), ""+MerUtils.getRemoteIp(request), userId, userRole)
		               	.target(userId+"|"+userRole)
		               	.params(eventParams) //夹带其他资讯
		               	.submit();

					this.info("商户： %s, 用户： %s, 登入IP： %s, 新增登入账号成功：%s, 帐户类型：%s ", mchntCd, commonBO.getMchntUser().getUserId(), ""+MerUtils.getRemoteIp(request), userId, userRole);
				}
			}
			if ("1".equals(type)) {// 进行修改
				mchntUserInfo.setUserId(userId);
				mchntUserInfo.setUserRole(userRole);
				mchntUserInfo.setMchntCd(mchntCd);
				//System.out.println("修改的对象" + mchntUserInfo);
				mchntBO.updatePwdSelective(mchntUserInfo);
				result = "3";

	           	//修改登入账号成功
	           	RiskEvent.mer()
	               	.who(merUser(commonBO.getMchntCd(), commonBO.getMchntUser().getUserId()))
	               	.event(RISK.Event.User_Id_Modify)
	               	.result(RISK.Result.Ok)
	               	.message("商户： %s, 用户： %s, 登入IP： %s, 修改登入账号%s的帐户类型：%s 成功 " , mchntCd, commonBO.getMchntUser().getUserId(), ""+MerUtils.getRemoteIp(request), userId, userRole)
	               	.target(userId+"|"+userRole)
	               	.params(eventParams) //夹带其他资讯
	               	.submit();

				this.info("商户%s修改登入账号%s的帐户类型：%s 成功", mchntCd, userId, userRole);
			}
			model.addAttribute("result", result);
			model.addAttribute("newPwd", newPwd);
			return "/addUserMng";
			// System.out.println("结果为："+result);
			// pWriter.write(result);
			// pWriter.flush();
			// pWriter.close();
		}
	}

	/**
	 * 重置密码
	 *
	 * @param vals
	 */
	@RequestMapping("/updatePwd")
	@RolesSessionCheck(roles= {MerUserRole.SuperUser})
	public void updatePwd(String vals, HttpServletRequest request, HttpServletResponse resp) {
		logger.info("重置密码参数vals为：" + vals);
		// 按照逗号切割
		String[] split = vals.split(",");
		String str = "";
		try {
			List list = new ArrayList<>();
			for (String mchntCd : split) {
				String[] split2 = mchntCd.split(";");
				// 先找对象
				List<MchntUserInfo> mchList = mchntBO.selectMchntUserList(split2[0], null, null);
				if (mchList != null && mchList.size() > 0) {
					for (MchntUserInfo mchntUserInfo : mchList) {
						if (mchntUserInfo.getUserId().equals(split2[1])) {
							String newPwd = StringUtil.randomPwd(10); // 随机生成10位字符串
							// 对密码进行重置
							mchntUserInfo.setLoginPwd(PwdUtil.computeMD5Pwd(newPwd));
							mchntUserInfo.setPwdUpdTs(DateUtil.now19());
							mchntUserInfo.setUserState(MchntUserSt._0.getCode());
							// 调修改的方法
							HashMap map = new HashMap();
							mchntBO.updatePwdSelective(mchntUserInfo);
							map.put("userId", split2[1]);
							map.put("newPwd", newPwd);
							list.add(map);
						}
					}
				}
				str = JSON.toJSONString(list);// 为重置密码显示修改后的结果的数据
			}
			PrintWriter writer = resp.getWriter();
			writer.write(str);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delUserMng")
	@RolesSessionCheck(roles= {MerUserRole.SuperUser})
	public @ResponseBody AjaxResult delUserMng(String secretHash, String googlePwd, String funcVals, HttpServletRequest request, HttpServletResponse resp) {
		
		Map<String,String> eventParams = //登入资讯(Event夹带用)
        		Utils.newMap(
        				INTER_MSG.sessionId, this.getSessionId(request),
        				INTER_MSG.requestUri , "/delUserMng" ,
        				MSG.merId, commonBO.getMchntCd(),
        				MSG.userId, commonBO.getMchntUser().getUserId(),
        				INTER_MSG.reqIp, ""+MerUtils.getRemoteIp(request)
        				);
		
		String vResult = validatePwdAndGoogleCode(secretHash, googlePwd, eventParams);
		if ("10".equals(vResult)) {
			return commonBO.buildSuccResp("respData", vResult);//登录密码失败
		} else if ("11".equals(vResult)) {
			return commonBO.buildSuccResp("respData", vResult);//谷歌验证失败
		} else if ("12".equals(vResult)) {
			return commonBO.buildSuccResp("respData", vResult);//谷歌验证码连续输入錯誤三次，帐号已被锁定
		} else {

			// 按照逗号切割
			String[] split = funcVals.split(",");
			try {
				for (String mchntCd : split) {
					String[] split2 = mchntCd.split(";");
					// 先找对象
					List<MchntUserInfo> mchList = mchntBO.selectMchntUserList(split2[0], null, null);
					PrintWriter writer = resp.getWriter();
					if (mchList != null && mchList.size() > 0) {
						for (MchntUserInfo mchntUserInfo : mchList) {
							if (mchntUserInfo.getUserId().equals(split2[1])) {
								mchntUserInfo.setUserId(split2[1]);
								// 调用删除的方法
								int delState = mchntBO.delSelective(mchntUserInfo);
								if (delState > 0) {
									writer.write("1");

						           	//删除登入账号成功
						           	RiskEvent.mer()
						               	.who(merUser(commonBO.getMchntCd(), commonBO.getMchntUser().getUserId()))
						               	.event(RISK.Event.User_Id_Del)
						               	.result(RISK.Result.Ok)
						               	.message("商户： %s, 用户： %s, 登入IP： %s, 删除登入账号成功：%s " , split2[0], commonBO.getMchntUser().getUserId(), ""+MerUtils.getRemoteIp(request), split2[1])
						               	.target(split2[1])
						               	.params(eventParams) //夹带其他资讯
						               	.submit();

									this.info("商户%s删除登入账号成功：%s", split2[0], split2[1]);
								} else {
									writer.write("0");

						           	//删除登入账号失败
						           	RiskEvent.mer()
						               	.who(merUser(commonBO.getMchntCd(), commonBO.getMchntUser().getUserId()))
						               	.event(RISK.Event.User_Id_Del)
						               	.result(RISK.Result.Failed)
						               	.message("商户： %s, 用户： %s, 登入IP： %s, 删除登入账号失败：%s " , split2[0], commonBO.getMchntUser().getUserId(), ""+MerUtils.getRemoteIp(request), split2[1])
						               	.target(split2[1])
						               	.params(eventParams) //夹带其他资讯
						               	.submit();

									this.info("商户%s删除登入账号失败：%s", split2[0], split2[1]);
								}
							}
						}
					}
					writer.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
				return commonBO.buildErrorResp(e.getMessage());
			}
			return commonBO.buildSuccResp();
		}
	}

	/**
	 * 禁用
	 */
	@RequestMapping("/disableUserMng")
	@RolesSessionCheck(roles= {MerUserRole.SuperUser})
	public @ResponseBody AjaxResult disableUserMng(String secretHash, String googlePwd, String funcVals, HttpServletRequest request, HttpServletResponse resp) {
        
		Map<String,String> eventParams = //登入资讯(Event夹带用)
        		Utils.newMap(
        				INTER_MSG.sessionId, this.getSessionId(request),
        				INTER_MSG.requestUri , "/disableUserMng" ,
        				MSG.merId, commonBO.getMchntCd(),
        				MSG.userId, commonBO.getMchntUser().getUserId(),
        				INTER_MSG.reqIp, ""+MerUtils.getRemoteIp(request)
        				);
        
		String vResult = validatePwdAndGoogleCode(secretHash, googlePwd, eventParams);
		if ("10".equals(vResult)) {
			return commonBO.buildSuccResp("respData", vResult);//登录密码失败
		} else if ("11".equals(vResult)) {
			return commonBO.buildSuccResp("respData", vResult);//谷歌验证失败
		} else if ("12".equals(vResult)) {
			return commonBO.buildSuccResp("respData", vResult);//谷歌验证码连续输入錯誤三次，帐号已被锁定
		} else {

			// 按照逗号切割
			String[] split = funcVals.split(",");
			try {
				for (String mchntCd : split) {
					String[] split2 = mchntCd.split(";");
					// 先找对象
					MchntUserInfo mchntUserInfo = mchntBO.selectMchntUserInfo(split2[0], split2[1]);
					PrintWriter writer = resp.getWriter();
					if (mchntUserInfo != null) {
						mchntUserInfo.setUserState(MchntUserSt._2.getCode()); //状态: 任何 -> 已禁用(2)
						int result = mchntBO.updatePwdSelective(mchntUserInfo);
						if (result > 0) {
							writer.write("1");

				           	//禁用登入账号成功
				           	RiskEvent.mer()
				               	.who(merUser(commonBO.getMchntCd(), commonBO.getMchntUser().getUserId()))
				               	.event(RISK.Event.User_Id_Disable)
				               	.result(RISK.Result.Ok)
				               	.message("商户： %s, 用户： %s, 登入IP： %s, 禁用登入账号成功：%s " , split2[0], commonBO.getMchntUser().getUserId(), ""+MerUtils.getRemoteIp(request), split2[1])
				               	.target(split2[1])
				               	.params(eventParams) //夹带其他资讯
				               	.submit();

							this.info("商户%s禁用登入账号成功：%s", split2[0], split2[1]);

						} else {
							writer.write("0");

				           	//禁用登入账号失败
				           	RiskEvent.mer()
				               	.who(merUser(commonBO.getMchntCd(), commonBO.getMchntUser().getUserId()))
				               	.event(RISK.Event.User_Id_Disable)
				               	.result(RISK.Result.Failed)
				               	.message("商户： %s, 用户： %s, 登入IP： %s, 禁用登入账号失败：%s " , split2[0], commonBO.getMchntUser().getUserId(), ""+MerUtils.getRemoteIp(request), split2[1])
				               	.target(split2[1])
				               	.params(eventParams) //夹带其他资讯
				               	.submit();

							this.info("商户%s禁用登入账号失败：%s", split2[0], split2[1]);

						}
					}
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return commonBO.buildErrorResp(e.getMessage());
			}
			return commonBO.buildSuccResp();
		}
	}

	/**
	 * 解除禁用
	 */
	@RequestMapping("/enableUserMng")
	@RolesSessionCheck(roles= {MerUserRole.SuperUser})
	public @ResponseBody AjaxResult enableUserMng(String secretHash, String googlePwd, String funcVals, HttpServletRequest request, HttpServletResponse resp) {
		
		Map<String,String> eventParams = //登入资讯(Event夹带用)
        		Utils.newMap(
        				INTER_MSG.sessionId, this.getSessionId(request),
        				INTER_MSG.requestUri , "/enableUserMng" ,
        				MSG.merId, commonBO.getMchntCd(),
        				MSG.userId, commonBO.getMchntUser().getUserId(),
        				INTER_MSG.reqIp, ""+MerUtils.getRemoteIp(request)
        				);
		
		String vResult = validatePwdAndGoogleCode(secretHash, googlePwd, eventParams);
		if ("10".equals(vResult)) {
			return commonBO.buildSuccResp("respData", vResult);//登录密码失败
		} else if ("11".equals(vResult)) {
			return commonBO.buildSuccResp("respData", vResult);//谷歌验证失败
		} else if ("12".equals(vResult)) {
			return commonBO.buildSuccResp("respData", vResult);//谷歌验证码连续输入錯誤三次，帐号已被锁定
		} else {

			// 按照逗号切割
			String[] split = funcVals.split(",");
			try {
				for (String mchntCd : split) {
					String[] split2 = mchntCd.split(";");
					// 先找对象
					MchntUserInfo mchntUserInfo = mchntBO.selectMchntUserInfo(split2[0], split2[1]);
					PrintWriter writer = resp.getWriter();
					if (mchntUserInfo != null) {
						mchntUserInfo.setUserState(MchntUserSt._1.getCode()); //更新状态: 已禁用(2) -> 有效(1)
						int result = mchntBO.updatePwdSelective(mchntUserInfo);
						if (result > 0) {
							writer.write("1");

				           	//解除禁用登入账号成功
				           	RiskEvent.mer()
				               	.who(merUser(commonBO.getMchntCd(), commonBO.getMchntUser().getUserId()))
				               	.event(RISK.Event.User_Id_Enable)
				               	.result(RISK.Result.Ok)
				               	.message("商户： %s, 用户： %s, 登入IP： %s, 解除禁用登入账号成功：%s " , split2[0], commonBO.getMchntUser().getUserId(), ""+MerUtils.getRemoteIp(request), split2[1])
				               	.target(split2[1])
				               	.params(eventParams) //夹带其他资讯
				               	.submit();

							this.info("商户%s解除禁用登入账号成功：%s", split2[0], split2[1]);
						} else {
							writer.write("0");

							//解除禁用登入账号失败
				           	RiskEvent.mer()
				               	.who(merUser(commonBO.getMchntCd(), commonBO.getMchntUser().getUserId()))
				               	.event(RISK.Event.User_Id_Enable)
				               	.result(RISK.Result.Failed)
				               	.message("商户： %s, 用户： %s, 登入IP： %s, 解除禁用登入账号失败：%s " , split2[0], commonBO.getMchntUser().getUserId(), ""+MerUtils.getRemoteIp(request), split2[1])
				               	.target(split2[1])
				               	.params(eventParams) //夹带其他资讯
				               	.submit();

							this.info("商户%s解除禁用登入账号失败：%s", split2[0], split2[1]);
						}
					}
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return commonBO.buildErrorResp(e.getMessage());
			}
			return commonBO.buildSuccResp();
		}
	}
	
	/**
	 * 解除鎖定
	 */
	@RequestMapping("/unlockUserMng")
	@RolesSessionCheck(roles= {MerUserRole.SuperUser})
	public @ResponseBody AjaxResult unlockUserMng(String secretHash, String googlePwd, String funcVals, HttpServletRequest request, HttpServletResponse resp) {
		Map<String,String> eventParams = //登入资讯(Event夹带用)
        		Utils.newMap(
        				INTER_MSG.sessionId, this.getSessionId(request),
        				INTER_MSG.requestUri , "/unlockUserMng" ,
        				MSG.merId, commonBO.getMchntCd(),
        				MSG.userId, commonBO.getMchntUser().getUserId(),
        				INTER_MSG.reqIp, ""+MerUtils.getRemoteIp(request)
        				);
		
		
		String vResult = validatePwdAndGoogleCode(secretHash, googlePwd, eventParams);
		if ("10".equals(vResult)) {
			return commonBO.buildSuccResp("respData", vResult);//登录密码失败
		} else if ("11".equals(vResult)) {
			return commonBO.buildSuccResp("respData", vResult);//谷歌验证失败
		}else if ("12".equals(vResult)) {
				return commonBO.buildSuccResp("respData", vResult);//谷歌验证码连续输入錯誤三次，帐号已被锁定
		} else {
			// 按照逗号切割
			String[] split = funcVals.split(",");
			try {
				for (String mchntCd : split) {
					String[] split2 = mchntCd.split(";");
					// 先找对象
					MchntUserInfo mchntUserInfo = mchntBO.selectMchntUserInfo(split2[0], split2[1]);
					PrintWriter writer = resp.getWriter();
					if (mchntUserInfo != null) {
						mchntUserInfo.setUserState(MchntUserSt._1.getCode()); //更新状态: 已鎖定(9) -> 有效(1)
						int result = mchntBO.updatePwdSelective(mchntUserInfo); 
						if (result > 0) {
							writer.write("1");
							
						 	//解除鎖定登入账号成功
				           	RiskEvent.mer()
				               	.who(merUser(commonBO.getMchntCd(), commonBO.getMchntUser().getUserId()))
				               	.event(RISK.Event.User_Id_Enable)
				               	.result(RISK.Result.Ok)
				               	.message("商户： %s, 用户： %s, 登入IP： %s, 解除锁定登入账号成功：%s " , split2[0], commonBO.getMchntUser().getUserId(), ""+MerUtils.getRemoteIp(request), split2[1])
				               	.target(split2[1])
				               	.params(eventParams) //夹带其他资讯
				               	.submit();
				           	
				           	this.info("商户%s解除鎖定登入账号成功：%s", split2[0], split2[1]);
						} else {
							writer.write("0");
							
							//解除禁用登入账号失败
				           	RiskEvent.mer()
				               	.who(merUser(commonBO.getMchntCd(), commonBO.getMchntUser().getUserId()))
				               	.event(RISK.Event.User_Id_Enable)
				               	.result(RISK.Result.Failed)
				               	.message("商户： %s, 用户： %s, 登入IP： %s, 解除锁定登入账号失败：%s " , split2[0], commonBO.getMchntUser().getUserId(), ""+MerUtils.getRemoteIp(request), split2[1])
				               	.target(split2[1])
				               	.params(eventParams) //夹带其他资讯
				               	.submit();
							this.info("商户%s解除鎖定登入账号失败：%s", split2[0], split2[1]);
						}
					}
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return commonBO.buildErrorResp(e.getMessage());
			}
			return commonBO.buildSuccResp();
		}
	}
	
	/**
	 * 模糊查询
	 */

	@RequestMapping("/query")
	@RolesSessionCheck(roles= {MerUserRole.SuperUser})
	public String selectMchntUserList(MchntUserInfo mchntUserInfo, Model model, HttpServletRequest request,
			HttpServletResponse resp) {
		// 获取商户号
		String mchntCd = commonBO.getMchntCd();
		List<MchntUserInfo> mchList = new ArrayList<MchntUserInfo>();
		// 判断前台传过来的是否为空字符串
		if ("".equals(mchntUserInfo.getUserRole())) {
			mchntUserInfo.setUserRole(null);
		}

		if ("".equals(mchntUserInfo.getUserId())) {
			mchntUserInfo.setUserId(null);
		}

		List<MchntUserInfo> list = mchntBO.selectMchntUserList(mchntCd, mchntUserInfo.getUserId(), null);
		// 查到不到数据，直接返回原界面
		if (list.size() == 0) {
			model.addAttribute("mchList", mchList);
			return "userMng";
		}
		for (MchntUserInfo mchntUserInfo2 : list) {
			if (mchntUserInfo2.getUserRole() != null && !mchntUserInfo2.getUserRole().equals("su")
					&& mchntUserInfo.getUserRole() != null
					&& mchntUserInfo2.getUserRole().equals(mchntUserInfo.getUserRole())) {
				mchList.add(mchntUserInfo2);
			}

			if (mchntUserInfo.getUserRole() == null && !mchntUserInfo2.getUserRole().equals("su")) {
				mchList.add(mchntUserInfo2);
			}
		}
		model.addAttribute("mchList", mchList);
		return "userMng";
	}

	/**
	 * 谷歌验证码验证接口
	 *
	 * @param userId
	 * @param model
	 * @param request
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping("resetOptSecret")
	@RolesSessionCheck(roles= {MerUserRole.SuperUser})
	public void resetOptSecret(String userId, Model model, HttpServletRequest request, HttpServletResponse resp)
			throws Exception {
		// 获得登录的商户号
		String mchntCd = commonBO.getMchntCd();
		PrintWriter pWriter = resp.getWriter();
		// 0.代表失败，1，代表成功
		String result = "0";
		MchntUserInfo mchntUserInfo = mchntBO.selectMchntUserList(mchntCd, userId, null).get(0);
		if (mchntUserInfo == null) {
			System.err.println("谷歌验证码验证接口resetOptSecret，数据为空");
			return;
		} else {
			// 将已绑定的用户谷歌验证码修改成“”
			mchntUserInfo.setOtpSecret("");
			mchntBO.updatePwdSelective(mchntUserInfo);
			result = "1";
		}
		pWriter.write(result);
		pWriter.close();
	}

	/**
	 * 校验登录密码、谷歌验证
	 * @param secretHash 密码
	 * @param googlePwd 谷歌验证码
	 * @return
	 */
	private String validatePwdAndGoogleCode(String secretHash, String googlePwd, Map<String,String> eventParams) {
		String mchntCd = commonBO.getMchntCd();
		GoogleAuthenticator ga = null;
		MchntUserInfo muser = commonBO.getMchntUser();
		muser = mchntBO.selectMchntUserInfo(mchntCd, muser.getUserId());
		if (!PwdUtil.validatePwd(secretHash, googlePwd, null, muser.getLoginPwd())) {
			
        	RiskEvent.mer()
        	.who(merUser(mchntCd, muser.getUserId()))
        	.event(RISK.Event.PwdCheck)
        	.result(RISK.Result.Failed)
        	.reason(RISK.Reason.Verify_Passwd)
        	.message("商户： %s; 用户： %s; 密码错误", mchntCd, muser.getUserId())
        	.params(eventParams) //夹带其他资讯
        	.submit();
        	
			return "10";//登录密码失败
		}
		ga = new GoogleAuthenticator(mchntCd +"-" + muser.getUserId(), muser.getOtpSecret());
		boolean loginOk = ga.checkCode(googlePwd);
		if(!loginOk) {
			
			RiskEvent.mer()
        	.who(merUser(mchntCd, muser.getUserId()))
        	.event(RISK.Event.GAuthCodeCheck)
        	.result(RISK.Result.Failed)
        	.reason(RISK.Reason.Verify_Otp)
        	.message("商户： %s; 用户： %s; 谷歌验证失败", mchntCd, muser.getUserId())
        	.params(eventParams) //夹带其他资讯
        	.submit();
			
			//GA验证码输入错误，记录错误次数
			if (GoogleAuthenticatorUtils.countGaLoginFail(muser)) {
        		return "11";//谷歌验证失败
        	} else {
        		//放進session
        		muser.setUserState(MerUserState.Locked.getCode());
        		commonBO.setSessionData(SessionKeys.MCHNT_USER_INFO.getCode(), muser);
        		return "12";//谷歌验证码连续输入錯誤，帐号已被锁定
        	}
		}
		this.updateLastGaTime();
		return "ok";
	}

}
