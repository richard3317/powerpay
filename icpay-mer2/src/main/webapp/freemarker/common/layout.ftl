<#assign timeNow = .now>
<#assign strNow = timeNow?string["yyyyMMddHHmmss"]>
<#assign strNowMm = timeNow?string["yyyyMMddHHmm"]>
<#assign lan = Session["lan"]!"">
<!DOCTYPE html>

<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<#-- <meta http-equiv="X-UA-Compatible" content="IE=edge">-->
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

		<title>[${htmlTitle!"商户服务"}]</title>
		<script type="text/javascript">
		 var i18nMsg = {
		  <!-- 操作 -->
		 	"operation": "<@msg code='layout.操作' default='操作'/>",
		 <!--暂无记录 -->
		 	"noData":   "<@msg code='layout.暂无记录' default='暂无记录'/>",
		 <!--上一页 -->
		 	"prePage":  "<@msg code='layout.上一页' default='上一页'/>",
		 <!--下一页 -->
		 	"nextPage":  "<@msg code='layout.下一页' default='下一页'/>",
		 <!--使用中的语系 -->
		 	"usedLan":  "${lan ! defaultLan}",
		 <!--ctx -->
		 	"ctx":  "${ctx}",
		 <!-- msg -->
		 	"beforePageText":   "<@msg code='layout.第' default='第'/>",
		 	"afterPageText":   "<@msg code='layout.共{pages}页' default='共{pages}页'/>",
		 	"displayMsg":   "<@msg code='layout.显示{from}到{to},共{total}记录' default='显示{from}到{to},共{total}记录'/>",
		 	"loadMsg":   "<@msg code='layout.正在处理，请稍待。。。' default='正在处理，请稍待。。。'/>",
		 	"ok":   "<@msg code='layout.确定' default='确定'/>",
		 	"cancel":   "<@msg code='layout.取消' default='取消'/>",
		 	"missingMessage":   "<@msg code='layout.該輸入項為必輸項' default='該輸入項為必輸項'/>",
		 	"email":   "<@msg code='layout.请输入有效的电子邮件地址' default='请输入有效的电子邮件地址'/>",
		 	"length":   "<@msg code='layout.输入内容长度必须介于{0}和{1}之间' default='输入内容长度必须介于{0}和{1}之间'/>",
		 	"url":   "<@msg code='layout.请输入有效的URL地址' default='请输入有效的URL地址'/>",
		 	"remote":   "<@msg code='layout.请修正此栏位' default='请修正此栏位'/>"
		 }
		</script>
		<!-- Vendor styles -->
		<link rel="shortcut icon" href="${ctx}/resources/images/favicon.ico" type="image/x-icon" />
		<link rel="icon" href="${ctx}/resources/images/favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" href="${ctx}/resources/css/metisMenu.css" />
		<link rel="stylesheet" href="${ctx}/resources/bootstrap/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${ctx}/resources/bootstrap/css/bootstrap-theme.min.css" /><#-- added:2018 -->
		<link rel="stylesheet" href="${ctx}/resources/css/toastr.min.css">
		<link rel="stylesheet" href="${ctx}/resources/css/style.css?v=${strNowMm!''}">

		<script src="${ctx}/resources/js/jquery.min.js"></script>
		<script src="${ctx}/resources/bootstrap/js/bootstrap.min.js"></script>
		<script src="${ctx}/resources/js/jquery.form.js"></script>
		<script src="${ctx}/resources/js/jquery.validate.min.js"></script>
		<script src="${ctx}/resources/js/toastr.min.js"></script>
		<script type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
		<script src="${ctx}/resources/js/mer.js?v=${strNowMm!''}"></script>
		<script src="${ctx}/resources/js/common.js?v=${strNowMm!''}""></script>
		${htmlHead!""}
		<style type="text/css">
			${htmlStyle!""}
		</style>
	</head>

	<body>
		<div id="top-tool">
			<div class="center_wrapper">
				<div id="top-tool-msg"><@msg code='layout.欢迎使用' default='欢迎使用'/>${merTitle}<@msg code='layout.商户服务网站' default='商户服务网站'/>.</div>
				<div id="top-tool-nav">
					<select id="lang-selector" style="background: black;">
					</select>	
					<@msg code='layout.您好' default='您好'/>，<@sessionVal key="userId" />&nbsp;
					<a href="${ctx}/chngPwd"><@msg code='layout.修改密码' default='修改密码'/></a>&nbsp;|&nbsp;
					<a href="${ctx}/logout"><@msg code='layout.安全退出' default='安全退出'/></a>
				</div>
			</div>
		</div>

		<#include "/common/head.ftl" />

		<div id="content" >
			<div class="center_wrapper">
				${htmlContent!""}
			</div>
		</div>

		<div class="clr">&nbsp;</div>
		<#--
		<div id="footer2">
	        <div class="div1"><a class="pic" target="_blank" href="${footcertsite}" ><img src="https://static.anquan.org/static/outer/image/aqkx_124x47.png"></img></a><p class="con">${footer}</p></div>
	    </div>
	    -->

		<div id="footer1" >
            <div class="center_wrapper">
                <div class="navbar-left"><a class="pic" target="_blank" href="${footcertsite}" ><img src="https://static.anquan.org/static/outer/image/aqkx_124x47.png"></img></a><p class="con">${footer}</p></div>
            </div>
	    </div>

        <#-- 谷歌驗證碼 ${ctx}/enterGoogle -->
        <#--  <form action="" id="gaForm" name="gaForm"> -->
        <div class="modal fade" id="gaModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false" >
                <div class="modal-dialog">
                    <div class="modal-content" style="width: 400px;margin-left: 160px;margin-top: 118px;">
                        <div class="modal-header">
                            <#--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>-->
                            <h4 class="modal-title" id="myModalLabel"><@msg code='login.谷歌身份验证器' default='谷歌身份验证器'/></h4>
                        </div>

                        <table style="margin:20px;">
                        <tr>
                        <td  align="left" bgcolor="#DFE0E4" class="general-td">
                            <@msg code='layout.1.在您的移动设备上运行Google身份验证器' default='1.在您的移动设备上运行Google身份验证器'/>
                        </td>
                        </tr>
                        <tr>
                        <td align="left" bgcolor="#DFE0E4" class="general-td">
                            <@msg code='layout.2.在下面框中输入，账号：' default='2.在下面框中输入，账号：'/><div id="td1" ></div> <@msg code='login.2.的当前验证码' default='的当前验证码'/>
                        </td></tr>
                        <tr>
                        <td align="left" bgcolor="#DFE0E4" class="general-td">
                            <@msg code='layout.3.点击验证' default='3.点击验证'/>
                        </td></tr>
                        </table>


                        <input id="ga_authCode"  type="text" autofocus="true" autofocus
                        placeholder="<@msg code='layout.请输入Google验证码' default='请输入Google验证码'/>" class="general-td" name="ga_authCode" maxLength="6"
                        style="margin-left:20px;margin-bottom:20px;"
                         >

                        <div class="modal-footer">
                            <button type="button" id="sendOut" class="btn btn-primary"><@msg code='layout.确认' default='确认'/></button>
                        </div>
                    </div>
                </div>
        </div>
        </div>
        <#--  </form> -->


		<script type="text/javascript">
			var _ctx = "${ctx}";

			function parseJson(data){
				try {
					<#-- console.log('[parseJson] '+data); -->
					return JSON.parse(data);
				}
				catch(err) {
				    return null;
				}
			};

			function isEmpty(obj){
				if (obj === null || obj === undefined || obj === '') return true;
				if ((''+obj).replace(/(^s*)|(s*$)/g, "").length ==0) return true;
				return false;
			}

			${htmlJS!""}

            var checkVar = null;

            enableGaCheck();

            function enableGaCheck(){
                checkVar = setInterval("checkGoogle()", "10000");
            }

            function disableGaCheck(){
                if (checkVar==null) return;
                clearInterval(checkVar);
            }
					$('#gaModal').on('shown.bs.modal', function () {
					    $('#ga_authCode').focus();
					})
            function checkGoogle() {
                $.ajax({
                    method: "POST",
                    url: "${ctx}/checkGoogle",
                    data: {},
                }).done(function (msg) {
                    var dataObj = JSON.parse(msg);
                    if (dataObj==null) return;
                    if ("00" == dataObj.respCode) return;
                    if ("01" == dataObj.respCode){
                        disableGaCheck();
                        $("#td1").html(dataObj.respData.mchntCd + "-" + dataObj.respData.userId);
                        $("#td1").addClass("red");
                        $("#gaModal").modal("show");
                    }
                    else{
                        <#-- logout -->
                        $.jumpTo("${ctx}/logout");
                    }
                });
            }


            $("#sendOut").click(function () {
                var authCode = $("#ga_authCode").val();
                if ($("#ga_authCode").val() == "") {
                    alert("<@msg code='layout.谷歌验证码不能为空' default='谷歌验证码不能为空'/>");
                    return;
                }
                $("#gaModal").modal("hide");
                /* var loginType = $("input[name='loginType']:checked").val();
                    if(loginType ==null || loginType ==''){
                        loginType =1;
                    } */

                var url = "${ctx}/enterGoogle";
                $.ajax({
                    url: url,
                    async: false,
                    type: "POST",
                    data: {
                        authCode: authCode,
                    },
                    success: function (data) {
                        var dataObj = JSON.parse(data);
                        if (dataObj==null) return;
                        var recode = dataObj.respCode;
                        if ("00" == recode) {
                            $("#ga_authCode").val("");
                            enableGaCheck();
                        } else {
                            alert(dataObj.respMsg);
                            $("#ga_authCode").val("");
                            $.jumpTo("${ctx}/logout");
                        }
                    },
                    error: function () {
                        alert("<@msg code='layout.连线逾时！' default='连线逾时！'/>");
                        $("#ga_authCode").val("");
                        $.jumpTo("${ctx}/logout");
                    },
                });
            });

		</script>
	</body>
</html>