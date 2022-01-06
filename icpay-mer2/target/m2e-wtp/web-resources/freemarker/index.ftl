<#assign ctx = request.contextPath>

<#assign htmlHead>
  <link rel="stylesheet" type="text/css" href="${ctx}/resources/js/jquery-easy-ui/themes/jquery-ui.min.css"></link>
  <script type="text/javascript" src="${ctx}/resources/js/jquery-ui.min.js"></script>
  <script>
  $( function() {
    $( "#accordion" ).accordion();
  } );
  </script>
</#assign>

<#assign htmlContent>
	<ol class="breadcrumb">
		<li><a href="${ctx}/index"><@msg code='index.商户服务' default='商户服务'/></a></li>
		<li class="active"><@msg code='index.首页' default='首页'/></li>
	
	</ol>
		<div background-color: white; class="alert">
			<span style="float:right;"  background-color: white;>
			<@msg code='index.欢迎回来，上次登录时间：' default='欢迎回来，上次登录时间：'/>：${lastLoginTs}，<@msg code='index.上次登录IP' default='上次登录IP'/>: ${lastLoginIp} </span>
		</div>

		<div class="alert alert-success">
				<h3><@msg code='index.公告栏' default='公告栏'/></h3>
				<div id="tableDiv" style="width:100%;height:100%;">
					
				</div>
	</div>
	
	
	<div class="panel panel-default">
		<div class="panel-heading">
			<h4 class="panel-title"><@msg code='index.账户信息' default='账户信息'/></h4>
		</div>
		<div class="panel-body">
			<table width="100%">
				<tr>
					<td style="padding: 10px;" width="30%"><strong><@msg code='index.商户号' default='商户号'/></strong>：</td>
					<td style="padding: 10px;" width="70%">${mchntCd}</td>
				</tr>
				<tr>
					<td style="padding: 10px;" width="30%"><strong><@msg code='index.商户名' default='商户名'/></strong>：</td>
					<td style="padding: 10px;" width="70%">${mchntNm}</td>
				</tr>
			</table>
		</div>
		<div id="accordion">
	  		<h3><strong><@msg code='index.人民币' default='人民币'/>&nbsp;&nbsp-&nbsp;&nbsp<@msg code='index.帐户总额' default='帐户总额'/>：&nbsp;&nbsp;${totalBalance_CNY}&nbsp;&nbsp;<#-- <@msg code='index.(元)' default='(元)'/> --></strong></h3>
	  		<div class="panel-body">
				<table width="100%">
					<tr>
						<td style="padding: 10px;" width="30%"><@msg code='index.可取现金额' default='可取现金额'/>：</td>
						<td style="padding: 10px;" width="70%">${availableBalance_CNY}&nbsp;&nbsp;<#-- <@msg code='index.(元)' default='(元)'/> --></td>
					</tr>
					<tr>
						<td style="padding: 10px;" width="30%"><@msg code='index.预扣金额' default='预扣金额'/>：</td>
						<td style="padding: 10px;" width="70%">
							${frozenBalance_CNY}&nbsp;&nbsp;<#-- <@msg code='index.(元)' default='(元)'/> -->
						</td>
					</tr>
					<tr>
						<td style="padding: 10px;" width="30%"><@msg code='index.T1待结算金额' default='T1待结算金额'/>：</td>
						<td style="padding: 10px;" width="70%">
							${frozenT1Balance_CNY}&nbsp;&nbsp;<#-- <@msg code='index.(元)' default='(元)'/> -->
						</td>
					</tr>
				</table>
			</div>
	  		<h3><strong><@msg code='index.越南盾' default='越南盾'/>&nbsp;&nbsp-&nbsp;&nbsp<@msg code='index.帐户总额' default='帐户总额'/>：&nbsp;&nbsp;${totalBalance_VND}&nbsp;&nbsp;<#-- <@msg code='index.(元)' default='(元)'/> --></strong></h3>
			<div class="panel-body">
				<table width="100%">
					<tr>
						<td style="padding: 10px;" width="30%"><@msg code='index.可取现金额' default='可取现金额'/>：</td>
						<td style="padding: 10px;" width="70%">${availableBalance_VND}&nbsp;&nbsp;<#-- <@msg code='index.(元)' default='(元)'/> --></td>
					</tr>
					<tr>
						<td style="padding: 10px;" width="30%"><@msg code='index.预扣金额' default='预扣金额'/>：</td>
						<td style="padding: 10px;" width="70%">
							${frozenBalance_VND}&nbsp;&nbsp;<#-- <@msg code='index.(元)' default='(元)'/> -->
						</td>
					</tr>
					<tr>
						<td style="padding: 10px;" width="30%"><@msg code='index.T1待结算金额' default='T1待结算金额'/>：</td>
						<td style="padding: 10px;" width="70%">
							${frozenT1Balance_VND}&nbsp;&nbsp;<#-- <@msg code='index.(元)' default='(元)'/> -->
						</td>
					</tr>
				</table>
			</div>
	  		<h3><strong><@msg code='index.泰铢' default='泰铢'/>&nbsp;&nbsp-&nbsp;&nbsp<@msg code='index.帐户总额' default='帐户总额'/>：&nbsp;&nbsp;${totalBalance_THB}&nbsp;&nbsp;<#-- <@msg code='index.(元)' default='(元)'/> --></strong></h3>
	  		<div class="panel-body">
				<table width="100%">
					<tr>
						<td style="padding: 10px;" width="30%"><@msg code='index.可取现金额' default='可取现金额'/>：</td>
						<td style="padding: 10px;" width="70%">${availableBalance_THB}&nbsp;&nbsp;<#-- <@msg code='index.(元)' default='(元)'/> --></td>
					</tr>
					<tr>
						<td style="padding: 10px;" width="30%"><@msg code='index.预扣金额' default='预扣金额'/>：</td>
						<td style="padding: 10px;" width="70%">
							${frozenBalance_THB}&nbsp;&nbsp;<#-- <@msg code='index.(元)' default='(元)'/> -->
						</td>
					</tr>
					<tr>
						<td style="padding: 10px;" width="30%"><@msg code='index.T1待结算金额' default='T1待结算金额'/>：</td>
						<td style="padding: 10px;" width="70%">
							${frozenT1Balance_THB}&nbsp;&nbsp;<#-- <@msg code='index.(元)' default='(元)'/> -->
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="panel-body">
			<table width="100%">
				<tr>
					<td style="padding: 10px;" width="30%"><a id="detailBtn" class="btn btn-info btn-xs" href="${ctx}/mchntAcct/acctMng" role="button"><@msg code='index.查看详情' default='查看详情'/></a></td>
					<td style="padding: 10px;" width="70%"></td>
					<input type="hidden" value="${mchntCd}" id="abcMchntCd" />
				</tr>
			</table>
		</div>
	</div>
	<#include "/common/macro.ftl">
	<@modal modalId="transModal" title="交易流水详情">
		<@msg code='index.这里是交易详情' default='这里是交易详情'/>
	</@modal>
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" 
								aria-hidden="true">×
						</button>
						<h4 class="modal-title" id="myModalLabel">
							<@msg code='index.最新公告' default='最新公告'/>
						</h4>
					</div>
					<div class="modal-body">
						<table cellspacing='20'  
						style="border-collapse:separate; border-spacing:0px 10px;width:100%;height:100%;"
						>
							<tr >
								<td  style="width: 70px;"><@msg code='index.标题' default='标题'/>：</td>
								<td><span id="contentTitle"></span></td> 
							</tr>
							<tr>
								<td style="width: 70px;"><@msg code='index.正文' default='正文'/>：</td>
								<td><div id="contentText"></div></td>
							</tr>
							<tr>
								<td style="width: 70px;"><@msg code='index.有效期' default='有效期'/>：</td>
								<td><span id="beginTime"></span></td> 
							</tr>

							
							<input type="hidden" id="content_id">
						</table>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" 
								data-dismiss="modal"><@msg code='index.确认' default='确认'/>
						</button>
						<input type="button" id="sub" class="btn btn-primary" value="<@msg code='index.下次不再提醒' default='下次不再提醒'/>"/>
					</div>
				</div>
			</div>
		</div>
		
<#-- 密码久未更新提示 -->
		<div id="popBox" class="popBox">
<#-- css zone -->
	<style type = "text/css">
	.popBox{
	display:none;
	} 
	.popBox-mask{
	position: fixed;
	left: 0;
	right: 0;
	top: 0;
	bottom: 0;
	opacity: .1;
	z-index: 990;
	background-color: #000;
	}
	.dialog{	
	position:fixed;
	top:10px;left:35%;
	width:600px;
	height:400px;
	padding:10px;
	border:2px solid #888888;
	background-color:#eeeeee;
	box-shadow:0px 0px 10px #000000;
	z-index: 991;
	}
		.dialog>.close{
		position:absolute;
    	top:15px;
    	right:10px;
    	border:3px solid #888888;
    	cursor:pointer;
    	z-index: 992;
		}
		.dialog>.titleTip{
		position:relative;
    	top:50px;
    	left:-9px;
    	width:103%;
		height:89%;
    	background-color:#ffffff;
    	z-index: 992;
		}
		.dialog>.titleTip>.skipTip{
		position:relative;
		width:50%;
    	top:200px;
    	right:170x;
    	z-index: 992;
		}
		button:active {background-color: #FFFFBB;}
		button:focus {outline:0;}
	</style>
		
    <div class="popBox-mask"></div>
    <div id="dialog" class="dialog">
    	<div onclick="closeDialog();" class="close">&nbsp✖&nbsp</div>
        
    	<div class="titleTip">
    	<b style="font-size:30px;font-family:'Microsoft JhengHei'">
        <p>&nbsp&nbsp<@msg code='index.安全提示' default='安全提示'/>:
        </b>
        <p style="font-size:20px;font-family:'Microsoft JhengHei'">
        &nbsp&nbsp&nbsp&nbsp&nbsp<@msg code='index.您的密码已长期未更新, 为了账户安全, 建议您修改登录密码。' default='您的密码已长期未更新, 为了账户安全, 建议您修改登录密码。'/>		
        </p>

        <button onclick="closeAndLinkToModifyPWD();" style="position:absolute;top:130px;right:30%;width:250px;height:60px;cursor:pointer;border-radius:10px;background-color:#0088A8;">
        <b style="font-size:20px;font-family:'Microsoft JhengHei';color:white">
              <@msg code='index.修改密码' default='修改密码'/></b>
        </button>
        
        <button onclick="closeDialog();" style="position:absolute;top:210px;right:30%;width:250px;height:60px;cursor:pointer;border-radius:10px;">
        <b style="font-size:20px;font-family:'Microsoft JhengHei'";>
              <@msg code='index.暂不修改' default='暂不修改'/></b>
        </button>
        
        	<div class="skipTip">
        	<input type="checkbox" id="TempSkipTip" style="width:18px;height:18px;position:absolute;right:65px"/>
        	<b style="position:absolute;top:2px;right:-40px"><@msg code='index.30天内不再提醒' default='30天内不再提醒'/></b>
       		</div>
        
    	</div>
    </div>
</div>
		
</#assign>
<#assign htmlJS>
	$(function() {
		$.ajax({
				url:"${ctx}/login/queryValidAnnouncement",
				type:"post",
				data:{"mchntCd":$("#abcMchntCd").val()},
				success:function(data){
					if(data=='[]'){
					}else{
					//alert(data);
						var jsonObj = JSON.parse(data);
						<#-- 然后给公告栏赋值 -->
						var str="<table border='1' cellspacing='10' bordercolor='#FFFFFF' style='width:100%;height:100%;background:#F0FFF0;'><tr bordercolor='#FFFFFF'><td width='15%' style='padding: 10px;'>标题</td><td width='50%' style='padding: 10px;'>正文</td><td width='30%' style='padding: 10px;'>有效期</td></tr>";
						for(var i=0 ; i<jsonObj.length ; i++){
							str+="<tr bordercolor='#FFFFFF'><td	 style='padding: 10px;'>"+jsonObj[i].contentTitle+"</td><td style='padding: 10px;'><pre style='background:#F0FFF0;white-space: pre-wrap; white-space: -moz-pre-wrap; white-space: -pre-wrap; white-space: -o-pre-wrap; word-wrap: break-word;'>"+jsonObj[i].contentText+"</pre></td><td style='padding: 10px;'>"+format(new Date(jsonObj[i].beginTime), 'yyyy-MM-dd HH:mm')+"--"+format(new Date(jsonObj[i].endTime), 'yyyy-MM-dd HH:mm')+"</td></tr>";
						}
						str+="</table>";
						$("#tableDiv").html(str);
						for(var i=0 ; i<jsonObj.length ; i++){
							if(jsonObj[i].contentState == 6){
								$("#contentText").html("<pre style='white-space: pre-wrap; white-space: -moz-pre-wrap; white-space: -pre-wrap; white-space: -o-pre-wrap; word-wrap: break-word;'>"+jsonObj[i].contentText+"</pre>");
								$("#beginTime").html(format(new Date(jsonObj[i].beginTime), 'yyyy-MM-dd HH:mm')+"--"+format(new Date(jsonObj[i].endTime), 'yyyy-MM-dd HH:mm'));
								$("#content_id").val(jsonObj[i].contentId);
								$("#contentTitle").html(jsonObj[i].contentTitle);
								$("#myModal").modal("show");
							}
						}

					}
				}
			});

	var format = function(time, format){
		var t = new Date(time);
		var tf = function(i){return (i < 10 ? '0' : '') + i};
		return format.replace(/yyyy|MM|dd|HH|mm/g, function(a){
		switch(a){
		case 'yyyy':
		return tf(t.getFullYear());
		break;
		case 'MM':
		return tf(t.getMonth() + 1);
		break;
		case 'mm':
		return tf(t.getMinutes());
		break;
		case 'dd':
		return tf(t.getDate());
		break;
		case 'HH':
		return tf(t.getHours());
		break;
		case 'ss':
		return tf(t.getSeconds());
		break;
		}
		})
	}
		
		$("#sub").click(function() {
			$.ajax({
				url:"${ctx}/login/updateState",
				type:"post",
				data:{"mchntCd":$("#abcMchntCd").val(),"contentId":$("#content_id").val()},
				success:function(data){
				}
			});
			<#--关闭窗口-->
			$("#myModal").modal("hide")
		});
	});
	
	var popBox;
	var skipCheck;
	window.onload=function(){
	popBox = document.getElementById("popBox");
	skipCheck = document.getElementById("TempSkipTip");
		if (typeof(Storage) !== "undefined") {
			if("${changePwdTip}" == "True"){
				if (localStorage.getItem("skipChangPwdTipDate"+"${mchntCd}"+"${userID}")){
					<#--登录时间大于等于缓存的时间弹出提示-->
    				if ( (Date.parse("${loginToDate}")).valueOf() >= (Date.parse(localStorage.getItem("skipChangPwdTipDate"+"${mchntCd}"+"${userID}"))).valueOf()){
    					showDialog();
    				}
    				else{
    					closeDialog();
    					<#--showDialog();
    					alert("隐藏的");-->
    				}
				}
				else{
    				showDialog();
				}
			}
			else{
				<#--无需提示时-->
				localStorage.removeItem("skipChangPwdTipDate"+"${mchntCd}"+"${userID}");
				closeDialog();
			}
		}
		else{
			closeDialog();
		}
	};
	function showDialog(){
	popBox.style.display="block";
	}
	
	function closeDialog(){
	popBox.style.display="none";
	}
	
	function closeAndLinkToModifyPWD(){
	popBox.style.display="none";
	location.href = "${ctx}/chngPwd";
	}
	
	var tempOriDate;
	$('#TempSkipTip').change(function(){
		if (typeof(Storage) !== "undefined") {
			var saveDate;
   			if(skipCheck.checked){
           		if (localStorage.getItem("skipChangPwdTipDate"+"${mchntCd}"+"${userID}")){
           			<#--检查缓存日期是否小于等于登录日期-->
           			if ((Date.parse(localStorage.getItem("skipChangPwdTipDate"+"${mchntCd}"+"${userID}"))).valueOf()<=(Date.parse("${loginToDate}")).valueOf()){
						<#--缓存日期再加30天-->
    					tempOriDate=localStorage.getItem("skipChangPwdTipDate"+"${mchntCd}"+"${userID}");
    					saveDate = new Date(localStorage.getItem("skipChangPwdTipDate"+"${mchntCd}"+"${userID}"));
    					saveDate.setDate(saveDate.getDate()+30);
    					localStorage.setItem("skipChangPwdTipDate"+"${mchntCd}"+"${userID}", saveDate.getFullYear().toString()+"-"+(saveDate.getMonth()+1).toString()+"-"+saveDate.getDate().toString());
    				}
           		}
           		else{
    				<#--无存在缓存日期时 以登录日期再加30天-->
    				tempOriDate="${loginToDate}";
    				saveDate = new Date("${loginToDate}");
    				saveDate.setDate(saveDate.getDate()+30);
    				localStorage.setItem("skipChangPwdTipDate"+"${mchntCd}"+"${userID}",saveDate.getFullYear().toString()+"-"+(saveDate.getMonth()+1).toString()+"-"+saveDate.getDate().toString());
    			}
    			<#--alert("skipChangPwdTipDate"+"${mchntCd}"+"${userID}"+"   "+ localStorage.getItem("skipChangPwdTipDate"+"${mchntCd}"+"${userID}"));-->
   			}
   			else{
           		if (localStorage.getItem("skipChangPwdTipDate"+"${mchntCd}"+"${userID}")){
           			localStorage.setItem("skipChangPwdTipDate"+"${mchntCd}"+"${userID}",tempOriDate);
           		}
           		<#--alert("skipChangPwdTipDate"+"${mchntCd}"+"${userID}"+"   "+ localStorage.getItem("skipChangPwdTipDate"+"${mchntCd}"+"${userID}"));-->
   			}
   		}
	});

</#assign>
<#include "/common/layout.ftl" />