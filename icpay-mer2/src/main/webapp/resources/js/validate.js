String.prototype.lenB=function(){return this.replace(/[^\x00-\xff]/g, "**").length;}
var integer_reg = /^-?\d+$/;
var num_reg = /^[0-9]*[0-9][0-9]*$/;
var number_reg = /^\d+$/;
var vardecimal = /^([1-9][\d]{0,15}|0)(\.[\d]{1,2})?$/;
var feeReg = /^[-]?([1-9][\d]{0,15}|0)(\.[\d]{1,2})?$/;
var rateReg = /^[-]?0.[\d]{1,5}$/;
var hex_reg = /^[0-9a-fA-F]*$/;
var ip_reg = /^[\d.]{7,32}$/;
$.extend($.fn.validatebox.defaults.rules, {
	integer : {
		validator : function(value, param) {
			if (integer_reg.test(value)) {
				var i = parseInt(value);
				if (i <= 2147483647) {
					return true;
				} else {
					$.fn.validatebox.defaults.rules.integer.message = "请输入不超过2147483647的有效整数";
					return false;
				}
			} else {
				$.fn.validatebox.defaults.rules.integer.message = "格式有误，请输入整数";
				return false;
			}
		},
		message : "请输入整数"
	},
	maxInteger : {
		validator:function(value, param){
			var i = parseInt(value);
			return i <= param[0];
		},
		message : "输入的整数不能超过{0}"
	},
	minInteger : {
		validator:function(value, param){
			var i = parseInt(value);
			return i >= param[0];
		},
		message : "输入的整数不能低于{0}"
	},
	number : {
		validator : function(value, param) {
			return num_reg.test(value);
		},
		message : "请输入数字"
	},
	numberOrStar : {
		validator : function(value, param) {
			return num_reg.test(value) || "*" == value;
		},
		message : "请输入数字或*"
	},
	maxLength : {
		validator : function(value, param) {
			return value.length <= param[0];
		},
		message : "长度不能超过{0}"
	},
	minLength : {
		validator : function(value, param) {
			return value.length >= param[0];
		},
		message : "长度不能少于{0}"
	},
	length: {
		validator : function(value, param) {
			return value.length == param[0];
		},
		message : "请输入{0}位字符串"
	},
	lengthOrStar: {
		validator : function(value, param) {
			return value.length == param[0] || "*" == value;
		},
		message : "请输入{0}位字符串或者\"*\""
	},
	lengthNum: {
		validator : function(value, param) {
			if(num_reg.test(value)) {
				$.fn.validatebox.defaults.rules.lengthNum.message = "请输入{0}位数字";
				return value.length == param[0];
			}else {
				$.fn.validatebox.defaults.rules.lengthNum.message = "请输入数字";
				return false;
			}
		},
		message : "请输入数字"
	},
	cnMaxLength : {
		validator : function(value, param) {
			return value.lenB() <= param[0];
		},
		message : "输入内容不能超过{0}个字符,一个中文文字算两个字符"
	},
	isCardNo : {
		validator:function(value, param){
			var regex = /(^\d{15}$)|(^\d{17}(\d|X)$)/;
			return regex.test(value);
		},
		message : "身份证输入不正确"
	},
	isZipCd : {
		validator:function(value, param){
			var regex = /^[0-9]{6}$/;
			return regex.test(value);
		},
		message : "邮政编码输入不正确"		
	},
	isSelectNotNULL : {
		validator : function(value, param) {
			if (value == param){
				return false;
			}
			return true;
		},
		message : "该输入项为必输项"
	},
	amount : {
		validator : function(value, param) {
			return vardecimal.test(value);
		},
		message : "金额格式错误"
	},
	fee : {
		validator : function(value, param) {
			return feeReg.test(value);
		},
		message : "手续费格式错误"
	},
	rate : {
		validator : function(value, param) {
			return rateReg.test(value);
		},
		message : "费率格式错误"
	},
	checkMPhone : {
		validator:function(value, param){
			var regex = /^1[3|4|5|8][0-9]\d{4,8}$/;
			return regex.test(value);
		},
		message : "手机号码输入不正确"
	},
	checkTelePhone : {
		validator:function(value, param){
			var regex = /^(\d{3,4}-)?\d{7,8}$/;
			return regex.test(value);
		},
		message : "电话号码输入不正确"		
	},
	checkPwd : {
		validator:function(value, param){
			if (value != param[0]){
				return false;
			}
			return true;
		},
		message : "两次输入的密码不一致"
	},
	isChinese : {
		validator:function(value, param){
			var regex = /.*[\u4e00-\u9fa5]+.*$/;
			return regex.test(value);
		},
		message : "请输入中文字符"		
	},
	isNotChinese : {
		validator:function(value, param){		 
			var regex = /.*[\u4e00-\u9fa5]+.*$/;
			return !regex.test(value);
		},
		message : "不能输入中文字符"	
	},
	characters: {
		validator:function(value, param){		
			var regex = /^\w*$/;
			return regex.test(value);
		},
		message : "请输入数字、字母、下划线字符"	
	},
	enNm : {
		validator:function(value, param){		
			var regex = /^[\w]+[\w|\s]*$/;
			return "" == param || regex.test(value);
		},
		message : "请输入数字、字母、下划线字符或空格"	
	},
	word: {
		validator:function(value, param){		
			var regex = /^[0-9a-zA-Z]*$/;
			return regex.test(value);
		},
		message : "请输入数字或英文字母"	
	},
	checkMPhoneTel : {
		validator:function(value, param){
			var regexPhone = /^1[3|4|5|7|8][0-9]\d{4,8}$/;
			var regexTel = /^(\d{3,4}-?)?\d{7,8}$/;
			return regexPhone.test(value) || regexTel.test(value);
		},
		message : "手机号码格式错误"
	},
	loginPwd : {
		validator:function(value, param){
			var pwdReg = /^[A-Za-z0-9!@#$*()_+^&}{:?.]+$/;
			return pwdReg.test(value);
		},
		message : "密码格式错误，请输入数字、字符或如下特殊符号!@#$*()_+^&}{:?."
	},
	equalTo : {
		validator:function(value, param){
			return value == $("#" + param[0]).val();
		},
		message : "两次密码输入需一致"
	},
	hex : {
		validator:function(value, param){
			return hex_reg.test(value);
		},
		message : "请输入16进制字符：[0-9a-fA-F]"
	},
	hexOrStar : {
		validator:function(value, param){
			return hex_reg.test(value) || "*" == value;
		},
		message : "请输入16进制字符：[0-9a-fA-F]或*"
	},
	ipOrStar : {
		validator:function(value, param){
			return ip_reg.test(value) || "*" == value;
		},
		message : "请输入正确格式的IP地址或*"
	},
	ajaxCheck : {
		validator : function(value, param){
			var p = {};
			p[param[1]] = value;
			var resp = $.ajax({
				url: param[0],
				dataType: "json",
				data: p,
				async: false,
				cache: false,
				type: "post"
			}).responseText;
			if (resp == "expired") {
				window.top.location.href = logout_url;
				return false;
			}
			if (resp != "true") {
				$.fn.validatebox.defaults.rules.ajaxCheck.message = resp;
				return false;
			}
			return true;
		},
		message : "校验失败"
	}
});

