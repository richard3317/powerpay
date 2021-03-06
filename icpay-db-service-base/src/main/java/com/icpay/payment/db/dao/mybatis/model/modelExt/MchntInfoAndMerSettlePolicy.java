package com.icpay.payment.db.dao.mybatis.model.modelExt;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicy;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicySub;
import com.icpay.payment.db.dao.mybatis.model.OrganInfo;

public class MchntInfoAndMerSettlePolicy implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
     * 商户编号
     * Database column : tbl_mchnt_info.mchnt_cd
     *
     * @mbg.generated
     */
    private String mchntCd;
    
	/**
     * 站点
     * Database column : tbl_mchnt_info.siteId
     *
     * @mbg.generated
     */
    private String siteId;

    /**
     * 机构代码
     * Database column : tbl_mchnt_info.ins_cd
     *
     * @mbg.generated
     */
    private String insCd;

    /**
     * 商户网站登入密码hash
     * Database column : tbl_mchnt_info.login_pwd
     *
     * @mbg.generated
     */
    private String loginPwd;

    /**
     * 商户中文名称
     * Database column : tbl_mchnt_info.mchnt_cn_nm
     *
     * @mbg.generated
     */
    private String mchntCnNm;

    /**
     * 商户英文名称
     * Database column : tbl_mchnt_info.mchnt_en_nm
     *
     * @mbg.generated
     */
    private String mchntEnNm;

    /**
     * 商户中文简称
     * Database column : tbl_mchnt_info.mchnt_cn_abbr
     *
     * @mbg.generated
     */
    private String mchntCnAbbr;

    /**
     * 商户英文简称
     * Database column : tbl_mchnt_info.mchnt_en_abbr
     *
     * @mbg.generated
     */
    private String mchntEnAbbr;

    /**
     * 商户地址
     * Database column : tbl_mchnt_info.mchnt_addr
     *
     * @mbg.generated
     */
    private String mchntAddr;

    /**
     * 邮编
     * Database column : tbl_mchnt_info.zip_cd
     *
     * @mbg.generated
     */
    private String zipCd;

    /**
     * 营业执照号吗
     * Database column : tbl_mchnt_info.biz_license_no
     *
     * @mbg.generated
     */
    private String bizLicenseNo;

    /**
     * 联系人
     * Database column : tbl_mchnt_info.contact_person
     *
     * @mbg.generated
     */
    private String contactPerson;

    /**
     * 联系人手机号
     * Database column : tbl_mchnt_info.contact_phone
     *
     * @mbg.generated
     */
    private String contactPhone;

    /**
     * 邮箱
     * Database column : tbl_mchnt_info.contact_mail
     *
     * @mbg.generated
     */
    private String contactMail;

    /**
     * Database column : tbl_mchnt_info.fax
     *
     * @mbg.generated
     */
    private String fax;

    /**
     * 支持的交易类型，与代码中Constant.ALL_TXN_TYPES索引对应，1代表支持，0表不支持
     * Database column : tbl_mchnt_info.trans_type
     *
     * @mbg.generated
     */
    private String transType;

    /**
     * 支持的支付类型，与代码中Constant.PAYTYPE索引顺序对应，1表示支持
     * Database column : tbl_mchnt_info.pay_type
     *
     * @mbg.generated
     */
    private String payType;

    /**
     * 支持的清算类型。目前只有第一位有效。
     * Database column : tbl_mchnt_info.st_cd
     *
     * @mbg.generated
     */
    private String stCd;

    /**
     * Database column : tbl_mchnt_info.share_cert_mchnt_cd
     *
     * @mbg.generated
     */
    private String shareCertMchntCd;

    /**
     * Database column : tbl_mchnt_info.trust_domain
     *
     * @mbg.generated
     */
    private String trustDomain;

    /**
     * 效期
     * Database column : tbl_mchnt_info.expired_dt
     *
     * @mbg.generated
     */
    private String expiredDt;

    /**
     * 商户有效状态，1=有效，其馀无效(2=无效，9=风控中)
     * Database column : tbl_mchnt_info.mchnt_st
     *
     * @mbg.generated
     */
    private String mchntSt;

    /**
     * Database column : tbl_mchnt_info.area_cd
     *
     * @mbg.generated
     */
    private String areaCd;

    /**
     * 行业类别
     * Database column : tbl_mchnt_info.trade_type
     *
     * @mbg.generated
     */
    private String tradeType;

    /**
     * 风险标识
     * Database column : tbl_mchnt_info.risk_flag
     *
     * @mbg.generated
     */
    private String riskFlag;

    /**
     * Database column : tbl_mchnt_info.last_login_ip
     *
     * @mbg.generated
     */
    private String lastLoginIp;

    /**
     * Database column : tbl_mchnt_info.last_login_ts
     *
     * @mbg.generated
     */
    private String lastLoginTs;

    /**
     * 登入失败次数
     * Database column : tbl_mchnt_info.login_failed_cnt
     *
     * @mbg.generated
     */
    private Integer loginFailedCnt;

    /**
     * 登入状况注记
     * Database column : tbl_mchnt_info.login_comment
     *
     * @mbg.generated
     */
    private String loginComment;

    /**
     * 交易权限组ID
     * Database column : tbl_mchnt_info.trans_type_group_id
     *
     * @mbg.generated
     */
    private Integer transTypeGroupId;

    /**
     * 允许的支付请求来源; 空值表示全部允许; 以下组合表示来源限制，以逗号区隔：01=接口，03=商户平台，04=BM平台，范例：03,04
     * Database column : tbl_mchnt_info.allowed_req_src
     *
     * @mbg.generated
     */
    private String allowedReqSrc;

    /**
     * 允许的代付请求来源; 空值表示全部允许; 以下组合表示来源限制，以逗号区隔：01=接口，03=商户平台，04=BM平台，范例：03,04
     * Database column : tbl_mchnt_info.allowed_req_src_wd
     *
     * @mbg.generated
     */
    private String allowedReqSrcWd;

    /**
     * 商户类型,一般商户0000, 分润商户1000
     * Database column : tbl_mchnt_info.mchnt_tp
     *
     * @mbg.generated
     */
    private String mchntTp;

    /**
     * 代理商编号
     * Database column : tbl_mchnt_info.agent_cd
     *
     * @mbg.generated
     */
    private String agentCd;

    /**
     * (已移至tbl_mer_params，不再使用!)
     * Database column : tbl_mchnt_info.bg_ret_url
     *
     * @mbg.generated
     */
    private String bgRetUrl;

    /**
     * (已移至tbl_mer_params，不再使用!)
     * Database column : tbl_mchnt_info.page_ret_url
     *
     * @mbg.generated
     */
    private String pageRetUrl;

    /**
     * OTP单次验证密码-登入用
     * Database column : tbl_mchnt_info.otp_secret_login
     *
     * @mbg.generated
     */
    private String otpSecretLogin;

    /**
     * OTP单次验证密码-管理者
     * Database column : tbl_mchnt_info.opt_secret_admin
     *
     * @mbg.generated
     */
    private String optSecretAdmin;

    /**
     * 安全设置状态：0=未完成，1=已完成(Admin已绑定，User未绑定)，2=已完成(Admin已绑定，User已绑定)
     * Database column : tbl_mchnt_info.secret_init_state
     *
     * @mbg.generated
     */
    private String secretInitState;
    
    /**
     * 接入方式：0=收银台简易接口，1=后台接口
     * Database column : tbl_mchnt_info.api_type
     *
     * @mbg.generated
     */
    private String apiType;

    /**
     *默認為1，1:表示回調(notifyUrl)、跳轉(pageReturnUrl)字段以報文帶入為主；0表示以BM填入值為主
     * Database column : tbl_mchnt_info.mchnt_url_flag
     *
     * @mbg.generated
     */
    private String mchntUrlFlag;

    /**
     * 商户后台通知(回调)地址(充值)
     * Database column : tbl_mchnt_info.notify_url_pay
     *
     * @mbg.generated
     */
    private String notifyUrlPay;

    /**
     * 商户前台通知(跳转)地址(充值)
     * Database column : tbl_mchnt_info.page_return_url_pay
     *
     * @mbg.generated
     */
    private String pageReturnUrlPay;

    /**
     * 商户后台通知(回调)地址(代付)
     * Database column : tbl_mchnt_info.notify_url_withdraw
     *
     * @mbg.generated
     */
    private String notifyUrlWithdraw;

    /**
     * 商户前台通知(跳转)地址(代付)
     * Database column : tbl_mchnt_info.page_return_url_withdraw
     *
     * @mbg.generated
     */
    private String pageReturnUrlWithdraw;
    
    /**
     * 币别
     * Database column : tbl_mer_settle_policy.curr_cd
     *
     * @mbg.generated
     */
    private String currCd;
    
    /**
     * 站点
     * @return site_id 站点
     *
     * @mbg.generated
     */
    public String getSiteId() {
        return siteId;
    }

    /**
     * 站点
     * @param site_id 站点
     *
     * @mbg.generated
     */
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
    
    /**
     * 机构代码
     * @return ins_cd 机构代码
     *
     * @mbg.generated
     */
    public String getInsCd() {
        return insCd;
    }

    /**
     * 机构代码
     * @param insCd 机构代码
     *
     * @mbg.generated
     */
    public void setInsCd(String insCd) {
        this.insCd = insCd;
    }

    /**
     * 商户网站登入密码hash
     * @return login_pwd 商户网站登入密码hash
     *
     * @mbg.generated
     */
    public String getLoginPwd() {
        return loginPwd;
    }

    /**
     * 商户网站登入密码hash
     * @param loginPwd 商户网站登入密码hash
     *
     * @mbg.generated
     */
    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    /**
     * 商户中文名称
     * @return mchnt_cn_nm 商户中文名称
     *
     * @mbg.generated
     */
    public String getMchntCnNm() {
        return mchntCnNm;
    }

    /**
     * 商户中文名称
     * @param mchntCnNm 商户中文名称
     *
     * @mbg.generated
     */
    public void setMchntCnNm(String mchntCnNm) {
        this.mchntCnNm = mchntCnNm;
    }

    /**
     * 商户英文名称
     * @return mchnt_en_nm 商户英文名称
     *
     * @mbg.generated
     */
    public String getMchntEnNm() {
        return mchntEnNm;
    }

    /**
     * 商户英文名称
     * @param mchntEnNm 商户英文名称
     *
     * @mbg.generated
     */
    public void setMchntEnNm(String mchntEnNm) {
        this.mchntEnNm = mchntEnNm;
    }

    /**
     * 商户中文简称
     * @return mchnt_cn_abbr 商户中文简称
     *
     * @mbg.generated
     */
    public String getMchntCnAbbr() {
        return mchntCnAbbr;
    }

    /**
     * 商户中文简称
     * @param mchntCnAbbr 商户中文简称
     *
     * @mbg.generated
     */
    public void setMchntCnAbbr(String mchntCnAbbr) {
        this.mchntCnAbbr = mchntCnAbbr;
    }

    /**
     * 商户英文简称
     * @return mchnt_en_abbr 商户英文简称
     *
     * @mbg.generated
     */
    public String getMchntEnAbbr() {
        return mchntEnAbbr;
    }

    /**
     * 商户英文简称
     * @param mchntEnAbbr 商户英文简称
     *
     * @mbg.generated
     */
    public void setMchntEnAbbr(String mchntEnAbbr) {
        this.mchntEnAbbr = mchntEnAbbr;
    }

    /**
     * 商户地址
     * @return mchnt_addr 商户地址
     *
     * @mbg.generated
     */
    public String getMchntAddr() {
        return mchntAddr;
    }

    /**
     * 商户地址
     * @param mchntAddr 商户地址
     *
     * @mbg.generated
     */
    public void setMchntAddr(String mchntAddr) {
        this.mchntAddr = mchntAddr;
    }

    /**
     * 邮编
     * @return zip_cd 邮编
     *
     * @mbg.generated
     */
    public String getZipCd() {
        return zipCd;
    }

    /**
     * 邮编
     * @param zipCd 邮编
     *
     * @mbg.generated
     */
    public void setZipCd(String zipCd) {
        this.zipCd = zipCd;
    }

    /**
     * 营业执照号吗
     * @return biz_license_no 营业执照号吗
     *
     * @mbg.generated
     */
    public String getBizLicenseNo() {
        return bizLicenseNo;
    }

    /**
     * 营业执照号吗
     * @param bizLicenseNo 营业执照号吗
     *
     * @mbg.generated
     */
    public void setBizLicenseNo(String bizLicenseNo) {
        this.bizLicenseNo = bizLicenseNo;
    }

    /**
     * 联系人
     * @return contact_person 联系人
     *
     * @mbg.generated
     */
    public String getContactPerson() {
        return contactPerson;
    }

    /**
     * 联系人
     * @param contactPerson 联系人
     *
     * @mbg.generated
     */
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    /**
     * 联系人手机号
     * @return contact_phone 联系人手机号
     *
     * @mbg.generated
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * 联系人手机号
     * @param contactPhone 联系人手机号
     *
     * @mbg.generated
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * 邮箱
     * @return contact_mail 邮箱
     *
     * @mbg.generated
     */
    public String getContactMail() {
        return contactMail;
    }

    /**
     * 邮箱
     * @param contactMail 邮箱
     *
     * @mbg.generated
     */
    public void setContactMail(String contactMail) {
        this.contactMail = contactMail;
    }

    /**
     * @return fax 
     *
     * @mbg.generated
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax 
     *
     * @mbg.generated
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * 支持的交易类型，与代码中Constant.ALL_TXN_TYPES索引对应，1代表支持，0表不支持
     * @return trans_type 支持的交易类型，与代码中Constant.ALL_TXN_TYPES索引对应，1代表支持，0表不支持
     *
     * @mbg.generated
     */
    public String getTransType() {
        return transType;
    }

    /**
     * 支持的交易类型，与代码中Constant.ALL_TXN_TYPES索引对应，1代表支持，0表不支持
     * @param transType 支持的交易类型，与代码中Constant.ALL_TXN_TYPES索引对应，1代表支持，0表不支持
     *
     * @mbg.generated
     */
    public void setTransType(String transType) {
        this.transType = transType;
    }

    /**
     * 支持的支付类型，与代码中Constant.PAYTYPE索引顺序对应，1表示支持
     * @return pay_type 支持的支付类型，与代码中Constant.PAYTYPE索引顺序对应，1表示支持
     *
     * @mbg.generated
     */
    public String getPayType() {
        return payType;
    }

    /**
     * 支持的支付类型，与代码中Constant.PAYTYPE索引顺序对应，1表示支持
     * @param payType 支持的支付类型，与代码中Constant.PAYTYPE索引顺序对应，1表示支持
     *
     * @mbg.generated
     */
    public void setPayType(String payType) {
        this.payType = payType;
    }

    /**
     * 支持的清算类型。目前只有第一位有效。
     * @return st_cd 支持的清算类型。目前只有第一位有效。
     *
     * @mbg.generated
     */
    public String getStCd() {
        return stCd;
    }

    /**
     * 支持的清算类型。目前只有第一位有效。
     * @param stCd 支持的清算类型。目前只有第一位有效。
     *
     * @mbg.generated
     */
    public void setStCd(String stCd) {
        this.stCd = stCd;
    }

    /**
     * @return share_cert_mchnt_cd 
     *
     * @mbg.generated
     */
    public String getShareCertMchntCd() {
        return shareCertMchntCd;
    }

    /**
     * @param shareCertMchntCd 
     *
     * @mbg.generated
     */
    public void setShareCertMchntCd(String shareCertMchntCd) {
        this.shareCertMchntCd = shareCertMchntCd;
    }

    /**
     * @return trust_domain 
     *
     * @mbg.generated
     */
    public String getTrustDomain() {
        return trustDomain;
    }

    /**
     * @param trustDomain 
     *
     * @mbg.generated
     */
    public void setTrustDomain(String trustDomain) {
        this.trustDomain = trustDomain;
    }

    /**
     * 效期
     * @return expired_dt 效期
     *
     * @mbg.generated
     */
    public String getExpiredDt() {
        return expiredDt;
    }

    /**
     * 效期
     * @param expiredDt 效期
     *
     * @mbg.generated
     */
    public void setExpiredDt(String expiredDt) {
        this.expiredDt = expiredDt;
    }

    /**
     * 商户有效状态，1=有效，其馀无效(2=无效，9=风控中)
     * @return mchnt_st 商户有效状态，1=有效，其馀无效(2=无效，9=风控中)
     *
     * @mbg.generated
     */
    public String getMchntSt() {
        return mchntSt;
    }

    /**
     * 商户有效状态，1=有效，其馀无效(2=无效，9=风控中)
     * @param mchntSt 商户有效状态，1=有效，其馀无效(2=无效，9=风控中)
     *
     * @mbg.generated
     */
    public void setMchntSt(String mchntSt) {
        this.mchntSt = mchntSt;
    }

    /**
     * @return area_cd 
     *
     * @mbg.generated
     */
    public String getAreaCd() {
        return areaCd;
    }

    /**
     * @param areaCd 
     *
     * @mbg.generated
     */
    public void setAreaCd(String areaCd) {
        this.areaCd = areaCd;
    }

    /**
     * 行业类别
     * @return trade_type 行业类别
     *
     * @mbg.generated
     */
    public String getTradeType() {
        return tradeType;
    }

    /**
     * 行业类别
     * @param tradeType 行业类别
     *
     * @mbg.generated
     */
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * 风险标识
     * @return risk_flag 风险标识
     *
     * @mbg.generated
     */
    public String getRiskFlag() {
        return riskFlag;
    }

    /**
     * 风险标识
     * @param riskFlag 风险标识
     *
     * @mbg.generated
     */
    public void setRiskFlag(String riskFlag) {
        this.riskFlag = riskFlag;
    }

    /**
     * @return last_login_ip 
     *
     * @mbg.generated
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * @param lastLoginIp 
     *
     * @mbg.generated
     */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    /**
     * @return last_login_ts 
     *
     * @mbg.generated
     */
    public String getLastLoginTs() {
        return lastLoginTs;
    }

    /**
     * @param lastLoginTs 
     *
     * @mbg.generated
     */
    public void setLastLoginTs(String lastLoginTs) {
        this.lastLoginTs = lastLoginTs;
    }

    /**
     * 登入失败次数
     * @return login_failed_cnt 登入失败次数
     *
     * @mbg.generated
     */
    public Integer getLoginFailedCnt() {
        return loginFailedCnt;
    }

    /**
     * 登入失败次数
     * @param loginFailedCnt 登入失败次数
     *
     * @mbg.generated
     */
    public void setLoginFailedCnt(Integer loginFailedCnt) {
        this.loginFailedCnt = loginFailedCnt;
    }

    /**
     * 登入状况注记
     * @return login_comment 登入状况注记
     *
     * @mbg.generated
     */
    public String getLoginComment() {
        return loginComment;
    }

    /**
     * 登入状况注记
     * @param loginComment 登入状况注记
     *
     * @mbg.generated
     */
    public void setLoginComment(String loginComment) {
        this.loginComment = loginComment;
    }

    /**
     * 交易权限组ID
     * @return trans_type_group_id 交易权限组ID
     *
     * @mbg.generated
     */
    public Integer getTransTypeGroupId() {
        return transTypeGroupId;
    }

    /**
     * 交易权限组ID
     * @param transTypeGroupId 交易权限组ID
     *
     * @mbg.generated
     */
    public void setTransTypeGroupId(Integer transTypeGroupId) {
        this.transTypeGroupId = transTypeGroupId;
    }

    /**
     * 允许的支付请求来源; 空值表示全部允许; 以下组合表示来源限制，以逗号区隔：01=接口，03=商户平台，04=BM平台，范例：03,04
     * @return allowed_req_src 允许的支付请求来源; 空值表示全部允许; 以下组合表示来源限制，以逗号区隔：01=接口，03=商户平台，04=BM平台，范例：03,04
     *
     * @mbg.generated
     */
    public String getAllowedReqSrc() {
        return allowedReqSrc;
    }

    /**
     * 允许的支付请求来源; 空值表示全部允许; 以下组合表示来源限制，以逗号区隔：01=接口，03=商户平台，04=BM平台，范例：03,04
     * @param allowedReqSrc 允许的支付请求来源; 空值表示全部允许; 以下组合表示来源限制，以逗号区隔：01=接口，03=商户平台，04=BM平台，范例：03,04
     *
     * @mbg.generated
     */
    public void setAllowedReqSrc(String allowedReqSrc) {
        this.allowedReqSrc = allowedReqSrc;
    }

    /**
     * 允许的代付请求来源; 空值表示全部允许; 以下组合表示来源限制，以逗号区隔：01=接口，03=商户平台，04=BM平台，范例：03,04
     * @return allowed_req_src_wd 允许的代付请求来源; 空值表示全部允许; 以下组合表示来源限制，以逗号区隔：01=接口，03=商户平台，04=BM平台，范例：03,04
     *
     * @mbg.generated
     */
    public String getAllowedReqSrcWd() {
        return allowedReqSrcWd;
    }

    /**
     * 允许的代付请求来源; 空值表示全部允许; 以下组合表示来源限制，以逗号区隔：01=接口，03=商户平台，04=BM平台，范例：03,04
     * @param allowedReqSrcWd 允许的代付请求来源; 空值表示全部允许; 以下组合表示来源限制，以逗号区隔：01=接口，03=商户平台，04=BM平台，范例：03,04
     *
     * @mbg.generated
     */
    public void setAllowedReqSrcWd(String allowedReqSrcWd) {
        this.allowedReqSrcWd = allowedReqSrcWd;
    }

    /**
     * 商户类型,一般商户0000, 分润商户1000
     * @return mchnt_tp 商户类型,一般商户0000, 分润商户1000
     *
     * @mbg.generated
     */
    public String getMchntTp() {
        return mchntTp;
    }

    /**
     * 商户类型,一般商户0000, 分润商户1000
     * @param mchntTp 商户类型,一般商户0000, 分润商户1000
     *
     * @mbg.generated
     */
    public void setMchntTp(String mchntTp) {
        this.mchntTp = mchntTp;
    }

    /**
     * 代理商编号
     * @return agent_cd 代理商编号
     *
     * @mbg.generated
     */
    public String getAgentCd() {
        return agentCd;
    }

    /**
     * 代理商编号
     * @param agentCd 代理商编号
     *
     * @mbg.generated
     */
    public void setAgentCd(String agentCd) {
        this.agentCd = agentCd;
    }

    /**
     * (已移至tbl_mer_params，不再使用!)
     * @return bg_ret_url (已移至tbl_mer_params，不再使用!)
     *
     * @mbg.generated
     */
    public String getBgRetUrl() {
        return bgRetUrl;
    }

    /**
     * (已移至tbl_mer_params，不再使用!)
     * @param bgRetUrl (已移至tbl_mer_params，不再使用!)
     *
     * @mbg.generated
     */
    public void setBgRetUrl(String bgRetUrl) {
        this.bgRetUrl = bgRetUrl;
    }

    /**
     * (已移至tbl_mer_params，不再使用!)
     * @return page_ret_url (已移至tbl_mer_params，不再使用!)
     *
     * @mbg.generated
     */
    public String getPageRetUrl() {
        return pageRetUrl;
    }

    /**
     * (已移至tbl_mer_params，不再使用!)
     * @param pageRetUrl (已移至tbl_mer_params，不再使用!)
     *
     * @mbg.generated
     */
    public void setPageRetUrl(String pageRetUrl) {
        this.pageRetUrl = pageRetUrl;
    }

    /**
     * OTP单次验证密码-登入用
     * @return otp_secret_login OTP单次验证密码-登入用
     *
     * @mbg.generated
     */
    public String getOtpSecretLogin() {
        return otpSecretLogin;
    }

    /**
     * OTP单次验证密码-登入用
     * @param otpSecretLogin OTP单次验证密码-登入用
     *
     * @mbg.generated
     */
    public void setOtpSecretLogin(String otpSecretLogin) {
        this.otpSecretLogin = otpSecretLogin;
    }

    /**
     * OTP单次验证密码-管理者
     * @return opt_secret_admin OTP单次验证密码-管理者
     *
     * @mbg.generated
     */
    public String getOptSecretAdmin() {
        return optSecretAdmin;
    }

    /**
     * OTP单次验证密码-管理者
     * @param optSecretAdmin OTP单次验证密码-管理者
     *
     * @mbg.generated
     */
    public void setOptSecretAdmin(String optSecretAdmin) {
        this.optSecretAdmin = optSecretAdmin;
    }

    /**
     * 安全设置状态：0=未完成，1=已完成(Admin已绑定，User未绑定)，2=已完成(Admin已绑定，User已绑定)
     * @return secret_init_state 安全设置状态：0=未完成，1=已完成(Admin已绑定，User未绑定)，2=已完成(Admin已绑定，User已绑定)
     *
     * @mbg.generated
     */
    public String getSecretInitState() {
        return secretInitState;
    }

    /**
     * 安全设置状态：0=未完成，1=已完成(Admin已绑定，User未绑定)，2=已完成(Admin已绑定，User已绑定)
     * @param secretInitState 安全设置状态：0=未完成，1=已完成(Admin已绑定，User未绑定)，2=已完成(Admin已绑定，User已绑定)
     *
     * @mbg.generated
     */
    public void setSecretInitState(String secretInitState) {
        this.secretInitState = secretInitState;
    }
    
    /**
     * 接入方式：0=收银台简易接口，1=后台接口
     * @return api_type 接入方式：0=收银台简易接口，1=后台接口
     *
     * @mbg.generated
     */
    public String getApiType() {
        return apiType;
    }

    /**
     * 接入方式：0=收银台简易接口，1=后台接口
     * @param apiType 接入方式：0=收银台简易接口，1=后台接口
     *
     * @mbg.generated
     */
    public void setApiType(String apiType) {
        this.apiType = apiType;
    }
    
    /**
     * 默認為1，1:表示回調(notifyUrl)、跳轉(pageReturnUrl)字段以報文帶入為主；0表示以BM填入值為主
     * @return mchnt_url_flag 默認為1，1:表示回調(notifyUrl)、跳轉(pageReturnUrl)字段以報文帶入為主；0表示以BM填入值為主
     *
     * @mbg.generated
     */
    public String getMchntUrlFlag() {
        return mchntUrlFlag;
    }

    /**
     * 默認為1，1:表示回調(notifyUrl)、跳轉(pageReturnUrl)字段以報文帶入為主；0表示以BM填入值為主
     * @param mchntUrlFlag 默認為1，1:表示回調(notifyUrl)、跳轉(pageReturnUrl)字段以報文帶入為主；0表示以BM填入值為主
     *
     * @mbg.generated
     */
    public void setMchntUrlFlag(String mchntUrlFlag) {
        this.mchntUrlFlag = mchntUrlFlag;
    }

    /**
     * 商户后台通知(回调)地址(充值)
     * @return notify_url_pay 商户后台通知(回调)地址(充值)
     *
     * @mbg.generated
     */
    public String getNotifyUrlPay() {
        return notifyUrlPay;
    }

    /**
     * 商户后台通知(回调)地址(充值)
     * @param notifyUrlPay 商户后台通知(回调)地址(充值)
     *
     * @mbg.generated
     */
    public void setNotifyUrlPay(String notifyUrlPay) {
        this.notifyUrlPay = notifyUrlPay;
    }

    /**
     * 商户前台通知(跳转)地址(充值)
     * @return page_return_url_pay 商户前台通知(跳转)地址(充值)
     *
     * @mbg.generated
     */
    public String getPageReturnUrlPay() {
        return pageReturnUrlPay;
    }

    /**
     * 商户前台通知(跳转)地址(充值)
     * @param pageReturnUrlPay 商户前台通知(跳转)地址(充值)
     *
     * @mbg.generated
     */
    public void setPageReturnUrlPay(String pageReturnUrlPay) {
        this.pageReturnUrlPay = pageReturnUrlPay;
    }

    /**
     * 商户后台通知(回调)地址(代付)
     * @return notify_url_withdraw 商户后台通知(回调)地址(代付)
     *
     * @mbg.generated
     */
    public String getNotifyUrlWithdraw() {
        return notifyUrlWithdraw;
    }

    /**
     * 商户后台通知(回调)地址(代付)
     * @param notifyUrlWithdraw 商户后台通知(回调)地址(代付)
     *
     * @mbg.generated
     */
    public void setNotifyUrlWithdraw(String notifyUrlWithdraw) {
        this.notifyUrlWithdraw = notifyUrlWithdraw;
    }

    /**
     * 商户前台通知(跳转)地址(代付)
     * @return page_return_url_withdraw 商户前台通知(跳转)地址(代付)
     *
     * @mbg.generated
     */
    public String getPageReturnUrlWithdraw() {
        return pageReturnUrlWithdraw;
    }

    /**
     * 商户前台通知(跳转)地址(代付)
     * @param pageReturnUrlWithdraw 商户前台通知(跳转)地址(代付)
     *
     * @mbg.generated
     */
    public void setPageReturnUrlWithdraw(String pageReturnUrlWithdraw) {
        this.pageReturnUrlWithdraw = pageReturnUrlWithdraw;
    }

    
    /**
     * 清算银行帐户
     * Database column : tbl_mer_settle_policy.settle_account
     *
     * @mbg.generated
     */
    private String settleAccount;

    /**
     * 清算户名
     * Database column : tbl_mer_settle_policy.settle_account_name
     *
     * @mbg.generated
     */
    private String settleAccountName;

    /**
     * 开户地区代码
     * Database column : tbl_mer_settle_policy.settle_account_area_code
     *
     * @mbg.generated
     */
    private String settleAccountAreaCode;

    /**
     * 开户地区名称
     * Database column : tbl_mer_settle_policy.settle_account_area_info
     *
     * @mbg.generated
     */
    private String settleAccountAreaInfo;

    /**
     * 开户银行支行名称
     * Database column : tbl_mer_settle_policy.settle_account_bank_name
     *
     * @mbg.generated
     */
    private String settleAccountBankName;

    /**
     * 开户行联行号
     * Database column : tbl_mer_settle_policy.settle_account_bank_code
     *
     * @mbg.generated
     */
    private String settleAccountBankCode;

    /**
     * 清算周期,0=T0,1=T1,2=T2,3=T3
     * Database column : tbl_mer_settle_policy.settle_period
     *
     * @mbg.generated
     */
    private String settlePeriod;

    /**
     * 当日最大清算金额
     * Database column : tbl_mer_settle_policy.settle_limit
     *
     * @mbg.generated
     */
    private String settleLimit;

    /**
     * Database column : tbl_mer_settle_policy.comment
     *
     * @mbg.generated
     */
    private String comment;

    /**
     * Database column : tbl_mer_settle_policy.last_oper_id
     *
     * @mbg.generated
     */
    private String lastOperId;

    /**
     * Database column : tbl_mer_settle_policy.rec_crt_ts
     *
     * @mbg.generated
     */
    private Date recCrtTs;

    /**
     * Database column : tbl_mer_settle_policy.rec_upd_ts
     *
     * @mbg.generated
     */
    private Date recUpdTs;

    /**
     * 结转时间间隔，单位：分钟，0表示每日结转一次。
     * Database column : tbl_mer_settle_policy.transfer_interval
     *
     * @mbg.generated
     */
    private Integer transferInterval;

    /**
     * D0结转时间
     * Database column : tbl_mer_settle_policy.transfer_time
     *
     * @mbg.generated
     */
    private String transferTime;

    /**
     * T1结转时间
     * Database column : tbl_mer_settle_policy.transfer_time_t1
     *
     * @mbg.generated
     */
    private String transferTimeT1;

    /**
     * 是否自动余额结转,1:是；0：否
     * Database column : tbl_mer_settle_policy.balance_transfer
     *
     * @mbg.generated
     */
    private String balanceTransfer;

    /**
     * 是否自动T1余额结转,1:是；0：否
     * Database column : tbl_mer_settle_policy.balance_transfer_t1
     *
     * @mbg.generated
     */
    private String balanceTransferT1;

    /**
     * 结转模式: 0=默认, 1=AB商户模式
     * Database column : tbl_mer_settle_policy.transfer_mode
     *
     * @mbg.generated
     */
    private String transferMode;

    /**
     * B商户后坠名，默认$b
     * Database column : tbl_mer_settle_policy.mchnt_suffix
     *
     * @mbg.generated
     */
    private String mchntSuffix;

    /**
     * 前置T1结转时间，空值表示不進行前置T1結轉
     * Database column : tbl_mer_settle_policy.pre_transfer_time_t1
     *
     * @mbg.generated
     */
    private String preTransferTimeT1;

    /**
     * 前置T1結轉比例，1 表示 100%,  0.9 表示 90%
     * Database column : tbl_mer_settle_policy.pre_transfer_t1_percent
     *
     * @mbg.generated
     */
    private String preTransferT1Percent;
    
    /**
     * 机构编号
     * Database column : tbl_organ_info.organ_id
     *
     * @mbg.generated
     */
    private String organId;
    
    /**
     * 机构名称
     * Database column : tbl_organ_info.organ_desc
     *
     * @mbg.generated
     */
    private String organDesc;

    /**
     * 商户号(小商编)
     * @return mchnt_cd 商户号(小商编)
     *
     * @mbg.generated
     */
    public String getMchntCd() {
        return mchntCd;
    }

    /**
     * 商户号(小商编)
     * @param mchntCd 商户号(小商编)
     *
     * @mbg.generated
     */
    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd;
    }

    /**
     * 币别
     * @return curr_cd 币别
     *
     * @mbg.generated
     */
    public String getCurrCd() {
        return currCd;
    }

    /**
     * 币别
     * @param currCd 币别
     *
     * @mbg.generated
     */
    public void setCurrCd(String currCd) {
        this.currCd = currCd;
    }
	
    /**
     * 清算银行帐户
     * @return settle_account 清算银行帐户
     *
     * @mbg.generated
     */
    public String getSettleAccount() {
        return settleAccount;
    }

    /**
     * 清算银行帐户
     * @param settleAccount 清算银行帐户
     *
     * @mbg.generated
     */
    public void setSettleAccount(String settleAccount) {
        this.settleAccount = settleAccount;
    }

    /**
     * 清算户名
     * @return settle_account_name 清算户名
     *
     * @mbg.generated
     */
    public String getSettleAccountName() {
        return settleAccountName;
    }

    /**
     * 清算户名
     * @param settleAccountName 清算户名
     *
     * @mbg.generated
     */
    public void setSettleAccountName(String settleAccountName) {
        this.settleAccountName = settleAccountName;
    }

    /**
     * 开户地区代码
     * @return settle_account_area_code 开户地区代码
     *
     * @mbg.generated
     */
    public String getSettleAccountAreaCode() {
        return settleAccountAreaCode;
    }

    /**
     * 开户地区代码
     * @param settleAccountAreaCode 开户地区代码
     *
     * @mbg.generated
     */
    public void setSettleAccountAreaCode(String settleAccountAreaCode) {
        this.settleAccountAreaCode = settleAccountAreaCode;
    }

    /**
     * 开户地区名称
     * @return settle_account_area_info 开户地区名称
     *
     * @mbg.generated
     */
    public String getSettleAccountAreaInfo() {
        return settleAccountAreaInfo;
    }

    /**
     * 开户地区名称
     * @param settleAccountAreaInfo 开户地区名称
     *
     * @mbg.generated
     */
    public void setSettleAccountAreaInfo(String settleAccountAreaInfo) {
        this.settleAccountAreaInfo = settleAccountAreaInfo;
    }

    /**
     * 开户银行支行名称
     * @return settle_account_bank_name 开户银行支行名称
     *
     * @mbg.generated
     */
    public String getSettleAccountBankName() {
        return settleAccountBankName;
    }

    /**
     * 开户银行支行名称
     * @param settleAccountBankName 开户银行支行名称
     *
     * @mbg.generated
     */
    public void setSettleAccountBankName(String settleAccountBankName) {
        this.settleAccountBankName = settleAccountBankName;
    }

    /**
     * 开户行联行号
     * @return settle_account_bank_code 开户行联行号
     *
     * @mbg.generated
     */
    public String getSettleAccountBankCode() {
        return settleAccountBankCode;
    }

    /**
     * 开户行联行号
     * @param settleAccountBankCode 开户行联行号
     *
     * @mbg.generated
     */
    public void setSettleAccountBankCode(String settleAccountBankCode) {
        this.settleAccountBankCode = settleAccountBankCode;
    }

    /**
     * 清算周期,0=T0,1=T1,2=T2,3=T3
     * @return settle_period 清算周期,0=T0,1=T1,2=T2,3=T3
     *
     * @mbg.generated
     */
    public String getSettlePeriod() {
        return settlePeriod;
    }

    /**
     * 清算周期,0=T0,1=T1,2=T2,3=T3
     * @param settlePeriod 清算周期,0=T0,1=T1,2=T2,3=T3
     *
     * @mbg.generated
     */
    public void setSettlePeriod(String settlePeriod) {
        this.settlePeriod = settlePeriod;
    }

    /**
     * 当日最大清算金额
     * @return settle_limit 当日最大清算金额
     *
     * @mbg.generated
     */
    public String getSettleLimit() {
        return settleLimit;
    }

    /**
     * 当日最大清算金额
     * @param settleLimit 当日最大清算金额
     *
     * @mbg.generated
     */
    public void setSettleLimit(String settleLimit) {
        this.settleLimit = settleLimit;
    }

    /**
     * @return comment 
     *
     * @mbg.generated
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment 
     *
     * @mbg.generated
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return last_oper_id 
     *
     * @mbg.generated
     */
    public String getLastOperId() {
        return lastOperId;
    }

    /**
     * @param lastOperId 
     *
     * @mbg.generated
     */
    public void setLastOperId(String lastOperId) {
        this.lastOperId = lastOperId;
    }

    /**
     * @return rec_crt_ts 
     *
     * @mbg.generated
     */
    public Date getRecCrtTs() {
        return recCrtTs;
    }

    /**
     * @param recCrtTs 
     *
     * @mbg.generated
     */
    public void setRecCrtTs(Date recCrtTs) {
        this.recCrtTs = recCrtTs;
    }

    /**
     * @return rec_upd_ts 
     *
     * @mbg.generated
     */
    public Date getRecUpdTs() {
        return recUpdTs;
    }

    /**
     * @param recUpdTs 
     *
     * @mbg.generated
     */
    public void setRecUpdTs(Date recUpdTs) {
        this.recUpdTs = recUpdTs;
    }

    /**
     * 结转时间间隔，单位：分钟，0表示每日结转一次。
     * @return transfer_interval 结转时间间隔，单位：分钟，0表示每日结转一次。
     *
     * @mbg.generated
     */
    public Integer getTransferInterval() {
        return transferInterval;
    }

    /**
     * 结转时间间隔，单位：分钟，0表示每日结转一次。
     * @param transferInterval 结转时间间隔，单位：分钟，0表示每日结转一次。
     *
     * @mbg.generated
     */
    public void setTransferInterval(Integer transferInterval) {
        this.transferInterval = transferInterval;
    }

    /**
     * D0结转时间
     * @return transfer_time D0结转时间
     *
     * @mbg.generated
     */
    public String getTransferTime() {
        return transferTime;
    }

    /**
     * D0结转时间
     * @param transferTime D0结转时间
     *
     * @mbg.generated
     */
    public void setTransferTime(String transferTime) {
        this.transferTime = transferTime;
    }

    /**
     * T1结转时间
     * @return transfer_time_t1 T1结转时间
     *
     * @mbg.generated
     */
    public String getTransferTimeT1() {
        return transferTimeT1;
    }

    /**
     * T1结转时间
     * @param transferTimeT1 T1结转时间
     *
     * @mbg.generated
     */
    public void setTransferTimeT1(String transferTimeT1) {
        this.transferTimeT1 = transferTimeT1;
    }

    /**
     * 是否自动余额结转,1:是；0：否
     * @return balance_transfer 是否自动余额结转,1:是；0：否
     *
     * @mbg.generated
     */
    public String getBalanceTransfer() {
        return balanceTransfer;
    }

    /**
     * 是否自动余额结转,1:是；0：否
     * @param balanceTransfer 是否自动余额结转,1:是；0：否
     *
     * @mbg.generated
     */
    public void setBalanceTransfer(String balanceTransfer) {
        this.balanceTransfer = balanceTransfer;
    }

    /**
     * 是否自动T1余额结转,1:是；0：否
     * @return balance_transfer_t1 是否自动T1余额结转,1:是；0：否
     *
     * @mbg.generated
     */
    public String getBalanceTransferT1() {
        return balanceTransferT1;
    }

    /**
     * 是否自动T1余额结转,1:是；0：否
     * @param balanceTransferT1 是否自动T1余额结转,1:是；0：否
     *
     * @mbg.generated
     */
    public void setBalanceTransferT1(String balanceTransferT1) {
        this.balanceTransferT1 = balanceTransferT1;
    }

    /**
     * 结转模式: 0=默认, 1=AB商户模式
     * @return transfer_mode 结转模式: 0=默认, 1=AB商户模式
     *
     * @mbg.generated
     */
    public String getTransferMode() {
        return transferMode;
    }

    /**
     * 结转模式: 0=默认, 1=AB商户模式
     * @param transferMode 结转模式: 0=默认, 1=AB商户模式
     *
     * @mbg.generated
     */
    public void setTransferMode(String transferMode) {
        this.transferMode = transferMode;
    }

    /**
     * B商户后坠名，默认$b
     * @return mchnt_suffix B商户后坠名，默认$b
     *
     * @mbg.generated
     */
    public String getMchntSuffix() {
        return mchntSuffix;
    }

    /**
     * B商户后坠名，默认$b
     * @param mchntSuffix B商户后坠名，默认$b
     *
     * @mbg.generated
     */
    public void setMchntSuffix(String mchntSuffix) {
        this.mchntSuffix = mchntSuffix;
    }

    /**
     * 前置T1结转时间，空值表示不進行前置T1結轉
     * @return pre_transfer_time_t1 前置T1结转时间，空值表示不進行前置T1結轉
     *
     * @mbg.generated
     */
    public String getPreTransferTimeT1() {
        return preTransferTimeT1;
    }

    /**
     * 前置T1结转时间，空值表示不進行前置T1結轉
     * @param preTransferTimeT1 前置T1结转时间，空值表示不進行前置T1結轉
     *
     * @mbg.generated
     */
    public void setPreTransferTimeT1(String preTransferTimeT1) {
        this.preTransferTimeT1 = preTransferTimeT1;
    }

    /**
     * 前置T1結轉比例，1 表示 100%,  0.9 表示 90%
     * @return pre_transfer_t1_percent 前置T1結轉比例，1 表示 100%,  0.9 表示 90%
     *
     * @mbg.generated
     */
    public String getPreTransferT1Percent() {
        return preTransferT1Percent;
    }

    /**
     * 前置T1結轉比例，1 表示 100%,  0.9 表示 90%
     * @param preTransferT1Percent 前置T1結轉比例，1 表示 100%,  0.9 表示 90%
     *
     * @mbg.generated
     */
    public void setPreTransferT1Percent(String preTransferT1Percent) {
        this.preTransferT1Percent = preTransferT1Percent;
    }
    
    private List<MerSettlePolicy> merSettlePolicy;

    public List<MerSettlePolicy> getMerSettlePolicy() {
		return merSettlePolicy;
	}

	public void setMerSettlePolicy(List<MerSettlePolicy> merSettlePolicy) {
		this.merSettlePolicy = merSettlePolicy;
	}
	
    private List<MerSettlePolicySub> merSettlePolicySub;

    public List<MerSettlePolicySub> getMerSettlePolicySub() {
		return merSettlePolicySub;
	}

	public void setMerSettlePolicySub(List<MerSettlePolicySub> merSettlePolicySub) {
		this.merSettlePolicySub = merSettlePolicySub;
	}
	
	/**
     * 机构编号
     * @return organ_id 机构编号
     *
     * @mbg.generated
     */
    public String getOrganId() {
        return organId;
    }

    /**
     * 机构编号
     * @param organId 机构编号
     *
     * @mbg.generated
     */
    public void setOrganId(String organId) {
        this.organId = organId;
    }
    
    /**
     * 机构名称
     * @return organ_desc 机构名称
     *
     * @mbg.generated
     */
    public String getOrganDesc() {
        return organDesc;
    }

    /**
     * 机构名称
     * @param organDesc 机构名称
     *
     * @mbg.generated
     */
    public void setOrganDesc(String organDesc) {
        this.organDesc = organDesc;
    }
	

	public static MchntInfo transMchntInfo(MchntInfoAndMerSettlePolicy mchntInfoAndMerSettlePolicy) {
    	MchntInfo mchntInfo = new MchntInfo();
    	mchntInfo.setMchntCd(mchntInfoAndMerSettlePolicy.getMchntCd());
    	mchntInfo.setSiteId(mchntInfoAndMerSettlePolicy.getSiteId());
    	mchntInfo.setInsCd(mchntInfoAndMerSettlePolicy.getInsCd());
    	mchntInfo.setLoginPwd(mchntInfoAndMerSettlePolicy.getLoginPwd());
    	mchntInfo.setMchntCnNm(mchntInfoAndMerSettlePolicy.getMchntCnNm());
    	mchntInfo.setMchntEnNm(mchntInfoAndMerSettlePolicy.getMchntEnNm());
    	mchntInfo.setMchntCnAbbr(mchntInfoAndMerSettlePolicy.getMchntCnAbbr());
    	mchntInfo.setMchntEnAbbr(mchntInfoAndMerSettlePolicy.getMchntEnAbbr());
    	mchntInfo.setMchntAddr(mchntInfoAndMerSettlePolicy.getMchntAddr());
    	mchntInfo.setZipCd(mchntInfoAndMerSettlePolicy.getZipCd());
    	mchntInfo.setBizLicenseNo(mchntInfoAndMerSettlePolicy.getBizLicenseNo());
    	mchntInfo.setContactPerson(mchntInfoAndMerSettlePolicy.getContactPerson());
    	mchntInfo.setContactPhone(mchntInfoAndMerSettlePolicy.getContactPhone());
    	mchntInfo.setContactMail(mchntInfoAndMerSettlePolicy.getContactMail());
    	mchntInfo.setFax(mchntInfoAndMerSettlePolicy.getFax());
    	mchntInfo.setTransType(mchntInfoAndMerSettlePolicy.getTransType());
    	mchntInfo.setPayType(mchntInfoAndMerSettlePolicy.getPayType());
    	mchntInfo.setStCd(mchntInfoAndMerSettlePolicy.getStCd());
    	mchntInfo.setShareCertMchntCd(mchntInfoAndMerSettlePolicy.getShareCertMchntCd());
    	mchntInfo.setTrustDomain(mchntInfoAndMerSettlePolicy.getTrustDomain());
    	mchntInfo.setExpiredDt(mchntInfoAndMerSettlePolicy.getExpiredDt());
    	mchntInfo.setMchntSt(mchntInfoAndMerSettlePolicy.getMchntSt());
    	mchntInfo.setComment(mchntInfoAndMerSettlePolicy.getComment());
    	mchntInfo.setLastOperId(mchntInfoAndMerSettlePolicy.getLastOperId());
    	mchntInfo.setRecCrtTs(mchntInfoAndMerSettlePolicy.getRecCrtTs());
    	mchntInfo.setRecUpdTs(mchntInfoAndMerSettlePolicy.getRecUpdTs());
    	mchntInfo.setAreaCd(mchntInfoAndMerSettlePolicy.getAreaCd());
    	mchntInfo.setTradeType(mchntInfoAndMerSettlePolicy.getTradeType());
    	mchntInfo.setRiskFlag(mchntInfoAndMerSettlePolicy.getRiskFlag());
    	mchntInfo.setLastLoginIp(mchntInfoAndMerSettlePolicy.getLastLoginIp());
    	mchntInfo.setLastLoginTs(mchntInfoAndMerSettlePolicy.getLastLoginTs());
    	mchntInfo.setLoginFailedCnt(mchntInfoAndMerSettlePolicy.getLoginFailedCnt());
    	mchntInfo.setLoginComment(mchntInfoAndMerSettlePolicy.getLoginComment());
    	mchntInfo.setTransTypeGroupId(mchntInfoAndMerSettlePolicy.getTransTypeGroupId());
    	mchntInfo.setAllowedReqSrc(mchntInfoAndMerSettlePolicy.getAllowedReqSrc());
    	mchntInfo.setAllowedReqSrcWd(mchntInfoAndMerSettlePolicy.getAllowedReqSrcWd());
    	mchntInfo.setMchntTp(mchntInfoAndMerSettlePolicy.getMchntTp());
    	mchntInfo.setAgentCd(mchntInfoAndMerSettlePolicy.getAgentCd());
    	mchntInfo.setBgRetUrl(mchntInfoAndMerSettlePolicy.getBgRetUrl());
    	mchntInfo.setPageRetUrl(mchntInfoAndMerSettlePolicy.getPageRetUrl());
    	mchntInfo.setOtpSecretLogin(mchntInfoAndMerSettlePolicy.getOtpSecretLogin());
    	mchntInfo.setOptSecretAdmin(mchntInfoAndMerSettlePolicy.getOptSecretAdmin());
    	mchntInfo.setSecretInitState(mchntInfoAndMerSettlePolicy.getSecretInitState());
    	mchntInfo.setApiType(mchntInfoAndMerSettlePolicy.getApiType());
    	mchntInfo.setMchntUrlFlag(mchntInfoAndMerSettlePolicy.getMchntUrlFlag());
    	mchntInfo.setNotifyUrlPay(mchntInfoAndMerSettlePolicy.getNotifyUrlPay());
    	mchntInfo.setPageReturnUrlPay(mchntInfoAndMerSettlePolicy.getPageReturnUrlPay());
    	mchntInfo.setNotifyUrlWithdraw(mchntInfoAndMerSettlePolicy.getNotifyUrlWithdraw());
    	mchntInfo.setPageReturnUrlWithdraw(mchntInfoAndMerSettlePolicy.getPageReturnUrlWithdraw());
    	return mchntInfo;
    }
    
    public static MerSettlePolicy transMerSettlePolicy(MchntInfoAndMerSettlePolicy mchntInfoAndMerSettlePolicy) {
    	MerSettlePolicy merSettlePolicy = new MerSettlePolicy();
    	merSettlePolicy.setMchntCd(mchntInfoAndMerSettlePolicy.getMchntCd());
    	merSettlePolicy.setCurrCd(mchntInfoAndMerSettlePolicy.getCurrCd());
    	merSettlePolicy.setSettleAccount(mchntInfoAndMerSettlePolicy.getSettleAccount());
    	merSettlePolicy.setSettleAccountName(mchntInfoAndMerSettlePolicy.getSettleAccountName());
    	merSettlePolicy.setSettleAccountAreaCode(mchntInfoAndMerSettlePolicy.getSettleAccountAreaCode());
    	merSettlePolicy.setSettleAccountAreaInfo(mchntInfoAndMerSettlePolicy.getSettleAccountAreaInfo());
    	merSettlePolicy.setSettleAccountBankName(mchntInfoAndMerSettlePolicy.getSettleAccountBankName());
    	merSettlePolicy.setSettleAccountBankCode(mchntInfoAndMerSettlePolicy.getSettleAccountBankCode());
    	merSettlePolicy.setSettlePeriod(mchntInfoAndMerSettlePolicy.getSettlePeriod());
    	merSettlePolicy.setSettleLimit(mchntInfoAndMerSettlePolicy.getSettleLimit());
    	merSettlePolicy.setComment(mchntInfoAndMerSettlePolicy.getComment());
    	merSettlePolicy.setLastOperId(mchntInfoAndMerSettlePolicy.getLastOperId());
    	merSettlePolicy.setRecCrtTs(mchntInfoAndMerSettlePolicy.getRecCrtTs());
    	merSettlePolicy.setRecUpdTs(mchntInfoAndMerSettlePolicy.getRecUpdTs());
    	merSettlePolicy.setTransferInterval(mchntInfoAndMerSettlePolicy.getTransferInterval());
    	merSettlePolicy.setTransferTime(mchntInfoAndMerSettlePolicy.getTransferTime());
    	merSettlePolicy.setTransferTimeT1(mchntInfoAndMerSettlePolicy.getTransferTimeT1());
    	merSettlePolicy.setBalanceTransfer(mchntInfoAndMerSettlePolicy.getBalanceTransfer());
    	merSettlePolicy.setBalanceTransferT1(mchntInfoAndMerSettlePolicy.getBalanceTransferT1());
    	merSettlePolicy.setTransferMode(mchntInfoAndMerSettlePolicy.getTransferMode());
    	merSettlePolicy.setMchntSuffix(mchntInfoAndMerSettlePolicy.getMchntSuffix());
    	merSettlePolicy.setPreTransferTimeT1(mchntInfoAndMerSettlePolicy.getPreTransferTimeT1());
    	merSettlePolicy.setPreTransferT1Percent(mchntInfoAndMerSettlePolicy.getPreTransferT1Percent());
    	return merSettlePolicy;
    }
    
	public static OrganInfo transOrganInfo(MchntInfoAndMerSettlePolicy mchntInfoAndMerSettlePolicy) {
		OrganInfo organInfo = new OrganInfo();
		organInfo.setOrganId(mchntInfoAndMerSettlePolicy.getOrganId());
		organInfo.setOrganDesc(mchntInfoAndMerSettlePolicy.getOrganDesc());
    	return organInfo;
    }
    
}
