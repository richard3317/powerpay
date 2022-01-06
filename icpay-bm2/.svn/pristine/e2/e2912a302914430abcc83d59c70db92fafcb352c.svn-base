<#assign ctx = request.contextPath>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>${loginTitle}多渠道收单平台运营管理系统</title>
		<#include "/common/include.ftl">
		<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/menu.css"></link>
		<script type="text/javascript">
			function closeAllTabs(){
				var count = $('#tabsDiv').tabs('tabs').length;
				for(var i=count-1; i>=0; i--){
					if (i>=1)
						$('#tabsDiv').tabs('close',i);
				}
				$("#btnCloseAllTabs").hide();
			}
			$(function() {
				var _ctx = "${ctx}";
				$("#btnCloseAllTabs").hide();
				<#-- 
				$("#btnCloseAllTabs").click(function() {
					closeAllTabs();
				});
				-->
				
				$("#MenuDiv").accordion({
					fit: false,
					animate:false,
					border: false,
					borderWidth: 0
				});
				var panels = $("#MenuDiv").accordion("panels");
				$.each(panels, function(i, pp) {
					pp.panel("header").find(".accordion-collapse").bind("click", function(e) {
						pp.panel("body").find(".menu_container").hide();
						if (pp.panel("options").collapsed == false){
							pp.panel("body").find(".menu_container").fadeIn(300);
							pp.panel("expand", false);
						} else {
							pp.panel("collapse", false);
						}
						return false;
					});
				});
				$(".menu_container>ul.menu_list li a").click(function() {
					var chngTab = false;
					var tabId = $(this).attr("id") + "_tab";
					var title = $(this).attr("title");
					$("#btnCloseAllTabs").hide();
					if ($('#tabsDiv').tabs('getTab', title)) {
						$('#tabsDiv').tabs('select', title)
						chngTab = true;
					} else {
						if ($('#tabsDiv').tabs('tabs').length > 1) {
							$("#btnCloseAllTabs").show();
						}
						if ($('#tabsDiv').tabs('tabs').length > 15) {
							alert('打开页面不能超过15个，请关闭不使用的标签页');
							return false;
						}
						var ifrmUrl = $(this).attr("href");
						var tabHtml = '<div id="' + tabId + '" style="overflow: hidden; height: 100%;"><iframe scrolling="auto" src="' + ifrmUrl + '" height="100%" width="100%" frameborder="0" hidefocus></iframe></div>';
						$('#tabsDiv').tabs('add',{
			                title: title,
			                content: tabHtml,
			                closable: true
			            });
						chngTab = true;
					}
					if (chngTab && $(this).parent("li").attr("class") != "active") {
						$(".menu_container>ul.menu_list li").removeClass("active");
						$(this).parent("li").addClass("active");
						$("#contentDiv").siblings(".panel-header").children(".panel-title").html($(this).attr("title"));
					}
					return false;
				});
				$(".menu_container>ul.menu_list li a").focus(function() {
					$(this).blur();
				});
			});
		</script>
		<STYLE type="text/css">
			#headerDiv {
				height:77px;
				background:#3a3a3a;
				padding: 0;
				margin: 0;
			}
		</STYLE>
	</head>

	<body class="easyui-layout">
		<!-- header -->
		<div id="headerDiv" data-options="region:'north',border:false">
			<div style="margin: 0;padding: 0;">
				<div style="float: right; color: white; width: 320px;">
						<table style="width: 100%; height: 77px;">
							<tr>
								<td align="right" valign="bottom" style="padding-right: 10px;">当前用户: ${Session.SESSION_USR_INFO.usrId} - ${Session.SESSION_USR_INFO.usrNm}</td>
							</tr>
							<tr>
								<td align="right" valign="middle" style="padding-right: 10px;">
									<a id="homeBtn" href="javascript:void(0)" style="color: white; margin-right: 10px; text-decoration: none;">返回首页</a>
									<a id="chngPwdBtn" href="javascript:void(0)" style="color: white; margin-right: 10px; text-decoration: none;">修改密码</a>
									<a id="quitBtn" href="javascript:void(0)" style="color: white; text-decoration: none;">退出登录</a>
								</td>
							</tr>
						</table>
						<script type="text/javascript">
							$("#homeBtn").click(function() {
								$('#tabsDiv').tabs('select', '首页')
							}).focus(function() {
								$(this).blur();
							});
							$("#quitBtn").click(function() {
								$.jumpTo("${ctx}/logout.do");
							});
							$("#chngPwdBtn").click(function() {
								var tabId = $(this).attr("chngPwdBtn_tab");
								if ($('#tabsDiv').tabs('getTab', "修改密码")) {
									$('#tabsDiv').tabs('select', "修改密码")
								} else {
									if ($('#tabsDiv').tabs('tabs').length > 15) {
										alert('打开页面不能超过15个，请关闭不使用的标签页');
										return false;
									}
									var ifrmUrl = "${ctx}/system/bmUser/chngPwd.do";
									var tabHtml = '<div id="' + tabId + '" style="overflow: hidden; height: 100%;"><iframe scrolling="auto" src="' + ifrmUrl + '" height="100%" width="100%" frameborder="0" hidefocus></iframe></div>';
									$('#tabsDiv').tabs('add',{
						                title: "修改密码",
						                content: tabHtml,
						                closable: true
						            });
								}
							});
						</script>
				</div>
				<div style="line-height:70px; color: white; font-weight: bold; font-size: 24px; text-align: center;">
					${loginTitle}多渠道收单平台运营管理系统
				</div>
			</div>
		</div>
	 
		<!-- Menu -->
		<div data-options="region:'west',split:false,title:' 菜单栏 '" style="width:230px;padding:0;background:white;">
			<div id="MenuDiv">
				<#list Session.SESSION_USR_INFO.menuLst as menu>
					<div title="${menu.funcNm}">
						<div class="menu_container">
							<ul class="menu_list">
								<#list menu.subMenuLst as subMenu>
									<li>
										<a href="${ctx}${subMenu.funcHref}" 
										title="${subMenu.funcNm}"
										id="func_${subMenu.funcCd}">
											${subMenu.funcNm}
										</a>
									</li>
								</#list>
							</ul>
						</div>
					</div>
				</#list>
			</div>
		</div>
		
		<!-- content -->
		<div id="tabsDiv" class="easyui-tabs" data-options="region:'center', tools:'#tab-tools'" style="overflow: hidden;">	
			<div title="首页" style="overflow: hidden;">
				<iframe id="contentFrame" 
					src="${ctx}/welcome.do" 
					scrolling="auto" 
					height="100%" 
					width="100%" 
					frameborder="0"
					hidefocus
					>
				</iframe>
			</div>
    	</div>
    	
	    <div id="tab-tools">
	        <#--  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="addPanel()"></a> -->
	        <a id="btnCloseAllTabs" class="easyui-linkbutton easyui-tooltip" data-options="plain:true, iconCls:'icon-cancel'" title="关闭所有页签" onclick="closeAllTabs()" style="overflow: hidden; padding: 2px 2px;"></a>
	    </div>    	
	 
		<!-- footer -->
		<div data-options="region:'south',border:false" style="height:40px;background:#333333;padding:10px;">
			<div style="padding: 0;margin: 0; text-align: center;">
				<span style="font-size: 12px; color: white;">©版权所有 2013-2014</span>
			</div>
		</div>
	</body>
</html>