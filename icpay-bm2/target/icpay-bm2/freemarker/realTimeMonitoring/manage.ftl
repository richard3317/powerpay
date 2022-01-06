<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
			<@head label="平台交易量情况" base="realTimeMonitoring">
                var $win=$('#win').window({
					collapsible:false,
					minimizable:false,
					maximizable:false,
                });


			$(document).ready(function(){
				setInterval(function(){
					console.log("调用ajax中");
					<#--调用ajax访问后台-->
					$.ajax({
				        url:_ctx + "/realTimeMonitoring/refresh.do",
				        data:null,
				        method:"post",
				        dataType:'json',
				        success:function(data){
				        		console.log("返回回来得结果为："+data);
				        		$("#csumDayTxnAmt").html(data.csumDayTxnAmt);
				        		$("#csumDayWithdrawAmt").html(data.csumDayWithdrawAmt);
				        		$("#csumRealAvailableBalance").html(data.csumRealAvailableBalance);
				        		$("#csumAvailableBalance").html(data.csumAvailableBalance);
				        		
				        		$("#msumDayTxnAmt").html(data.mmsumDayTxnAmt);
				        		$("#msumDayWithdrawAmt").html(data.msumDayWithdrawAmt);
				        		$("#msumRealAvailableBalance").html(data.msumRealAvailableBalance);
				        		$("#msumAvailableBalance").html(data.msumAvailableBalance);
				        		
				        		$("#push").html(data.pushTime);
				               }
							   
					})
					
				}, 10*60*1000);
				
				
				<#--按钮点击时间-->
				$("#but").click(function () { 
					console.log("按钮点击事件");
					<#--调用ajax访问后台-->
						$.ajax({
					        url:_ctx + "/realTimeMonitoring/refresh.do",
					        data:null,
					        method:"post",
					        dataType:'json',
					        success:function(data){
					        		console.log("返回回来得结果为："+data);
					        		$("#csumDayTxnAmt").html(data.csumDayTxnAmt);
					        		$("#csumDayWithdrawAmt").html(data.csumDayWithdrawAmt);
					        		$("#csumRealAvailableBalance").html(data.csumRealAvailableBalance);
					        		$("#csumAvailableBalance").html(data.csumAvailableBalance);
					        		
					        		$("#msumDayTxnAmt").html(data.mmsumDayTxnAmt);
					        		$("#msumDayWithdrawAmt").html(data.msumDayWithdrawAmt);
					        		$("#msumRealAvailableBalance").html(data.msumRealAvailableBalance);
					        		$("#msumAvailableBalance").html(data.msumAvailableBalance);
					        		
					        		$("#push").html(data.pushTime);
					               }
								   
						})
				 })
			});


			</@head>

</head>

	<body style="width: 100%;height: auto;">
	<#setting date_format="yyyy/MM/dd HH:mm:ss">
		<div>
			<table style="width: 100%;height: auto;">
				<tr>
					<td height="60px"><h1><span style="font-size: 20px">平台交易量情况</span> <span style="color: #C0C0C0  ">（每十分钟自动刷新）</span></h1></td>
					<td><input type="button" value="刷新" id="but" style="width: 60px;height: 30px;background: blue;color: #fff"/></td>
				</tr>
				<tr>
					<td height="60px"><h1>
					<#if pushTime ??>
						<span id="push" style="font-size: 20px">${pushTime }</span>
					<#else>
						<span id="push" style="font-size: 20px">${.now ? date}</span>
					</#if>
					
					</h1></td>
					<td></td>
					<td></td>
				</tr>
			</table>
			
		</div>
		
		<div>
			<table style="table-layout: fixed;width: 80%;height: auto;" border="1" cellspacing="0">
				<tr>
					<td style="text-align: center;height: 60px"></td>
					<td style="text-align: center;height: 60px">充值交易总额</td>
					<td style="text-align: center;height: 60px">代付交易总额</td>
					<td style="text-align: center;height: 60px">实际可代付总额</td>
					<td style="text-align: center;height: 60px">可代付总额</td>
				</tr>
				<tr>
					<td style="text-align: center;height: 60px">渠道端</td>
					<td style="text-align: center;height: 60px"><span id="csumDayTxnAmt">${chnlMchntMap.sumDayTxnAmt }</span></td>
					<td style="text-align: center;height: 60px"><span id="csumDayWithdrawAmt">${chnlMchntMap.sumDayWithdrawAmt }</span></td>
					<td style="text-align: center;height: 60px"><span id="csumRealAvailableBalance">${chnlMchntMap.sumRealAvailableBalance }</span></td>
					<td style="text-align: center;height: 60px"><span id="csumAvailableBalance">${chnlMchntMap.sumAvailableBalance }</span></td>
				</tr>
				<tr>
					<td style="text-align: center;height: 60px">商户端</td>
					<td style="text-align: center;height: 60px"><span id="msumDayTxnAmt">${mchntMap.sumDayTxnAmt }</span></td>
					<td style="text-align: center;height: 60px"><span id="msumDayWithdrawAmt">${mchntMap.sumDayWithdrawAmt }</span></td>
					<td style="text-align: center;height: 60px"><span id="msumRealAvailableBalance">${mchntMap.sumRealAvailableBalance }</span></td>
					<td style="text-align: center;height: 60px"><span id="msumAvailableBalance">${mchntMap.sumAvailableBalance }</span></td>
				</tr>
			</table>
		</div>
	</body>
</html>