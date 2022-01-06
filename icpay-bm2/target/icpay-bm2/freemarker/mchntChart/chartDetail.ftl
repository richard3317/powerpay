<#include "/common/manage_macro.ftl" />
<#assign ctx = request.contextPath>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.link_button {
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
    border: solid 1px #20538D;
    text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.4);
    -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.4), 0 1px 1px rgba(0, 0, 0, 0.2);
    -moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.4), 0 1px 1px rgba(0, 0, 0, 0.2);
    box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.4), 0 1px 1px rgba(0, 0, 0, 0.2);
    background: #4479BA;
    color: #F5F5F5;
    padding: 8px 12px;
    text-decoration: none;
    align-content: center;
}
</style>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="https://code.highcharts.com.cn/highcharts/highcharts.js"></script>
        <script src="https://code.highcharts.com.cn/highcharts/modules/exporting.js"></script>
        <script src="https://code.highcharts.com.cn/highcharts/modules/series-label.js"></script>
        <script src="https://code.highcharts.com.cn/highcharts/modules/oldie.js"></script>
        <script src="https://code.highcharts.com.cn/highcharts-plugins/highcharts-zh_CN.js"></script>
</head>
<body>
<#if ajaxMchntCd??> <input type="hidden" id="ajaxMchntCd" value="${ajaxMchntCd}">
	<#else> <input type="hidden" id="ajaxMchntCd" value=""> </#if>
	
	<#if ajaxTxnState??> <input type="hidden" id="ajaxTxnState" value="${ajaxTxnState}">
	<#else> <input type="hidden" id="ajaxTxnState" value=""> </#if>
	
	
	<#if avgDay??> <input type="hidden" id="avgDay" value="${avgDay}">
	<#else> <input type="hidden" id="avgDay" value=""> </#if>
	
	<a class="link_button" style="color: #F5F5F5" href="javascript:history.go(-1)">返回上一页</a>
	<div id="container"  style="min-width:1000px;height:600px"></div>
	<table>
	<tr>
	<td>
	<div id="divBtn"   style="font-size:12px">
	<span>可点击上面除代付以外任意一点查看数据<br></span>
	交&nbsp;&nbsp;&nbsp;易&nbsp;&nbsp;&nbsp;量:<input id="jyl" value="0" readonly="readonly"/><br/>
	总&nbsp;&nbsp;&nbsp;比&nbsp;&nbsp;&nbsp;数:<input id="zbs" value="0" readonly="readonly"/><br/>
	成&nbsp;&nbsp;&nbsp;功&nbsp;&nbsp;&nbsp;率:<input id="cgl" value="0" readonly="readonly"/><br/>
	交易量低于平均:<input id="avg" value="0" readonly="readonly"/><br/>
	</div>
	</td>
	
	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	<td>
	<div id="divBtn1" style="font-size:12px">
	<span>可点击代付上面任意一点查看数据<br></span>
	代付交易量:<input id="jyl1" value="0" readonly="readonly"/><br/>
	代付总笔数:<input id="zbs1" value="0" readonly="readonly"/><br/>
	代付失败笔数：<input id="avg1" value="0" readonly="readonly"/><br/>
	代付处理中笔数：<input id="cgl1" value="0" readonly="readonly"/><br/>
	</div>
	</td>
	</tr>
	</table>
	
	<script>
            // JS 代码 
            $(document).ready(function(){
						//$('#divBtn1').hide();
		});
        </script>
</body>
</body>
<script type="text/javascript">
var chart = Highcharts.chart('container', {
	title: {
		//图表的标题
			text: ''
	},
	subtitle: {
		//图表的子标题
			text: '数据来源：24小时交易统计'
	},
	//Y轴选项
	yAxis: [{
			labels: {
	            format: '{value} 万元'
	        },
			title: {
				//y轴名称，支持text、enabled、align、rotation、style等属性
					text: '充值金额'
			}		
	}],
	xAxis: {
		//X轴坐标数据
			   categories: ${xList}
			},
	//图例选项
	legend: {
			//显示形式，支持水平horizontal和垂直vertical
			layout: 'vertical',
			//对齐方式。
			align: 'right',
			//垂直对齐方式。
			verticalAlign: 'middle'
	},
	//表示在无数据的时候  显示暂无数据
	 lang: { noData: "暂无数据" },
	  legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle',
	            borderWidth: 0
	       },
	//数据点选项
	 plotOptions: {
			//数据列选项
			series: {
					//显示节点数据
					dataLabels: {
						//是否在数据点上直接显示数据
	            		enabled: true,
	            		//是否允许使用鼠标选中数据点
	            		allowPointSelect: true
	        		},
					label: {
							connectorAllowed: false
					},
	        		cursor: 'pointer', 
	                events: { 
	                    click: function(e) { 
	                        var xValue=e.point.category; 
	                        var yValue=e.point.y;
	                        var name=e.point.series.name; 
	                       if(name=='充值基准线'||name=='代付基准线'||name=='充值总金额'){
	                    	   return;
	                       }
	                        $.ajax({
	                        	url: "${ctx}/mchntChart/getDataByX.do",
	                        	data:{"ajaxName":name,"xValue":xValue,"yValue":yValue,"avgDay":$("#avgDay").val(),"ajaxMchntCd":$("#ajaxMchntCd").val(),"ajaxTxnState":$("#ajaxTxnState").val()},
	                        	type:"post",
	                        	success:function(data){
	                        		var da=eval("("+data+")");
	                        		//alert(da);
	                        	if(da.ajaxIntTransCd=='单笔代付')	{
	                        		$("#jyl1").val(yValue+" 万元");
	                        		$("#zbs1").val(da.sum);
	                        		$("#cgl1").val(da.being);
	                        		$("#avg1").val(da.fail);	
	                        	}else{
	                        		//alert(data);
	                        		var map=eval('('+data+')')
	                        		//console.log(map);
	                        		$("#jyl").val(yValue+" 万元");
	                        		$("#zbs").val(map.sum);
	                        		$("#cgl").val(map.successValue==null ? 0.00 :map.successValue);
	                        		$("#avg").val(map.upperAvg);
	                        	}	
	                       }
	                        });
	                    } 
	                }
			}
	}, 
	//数据列选项
	series:  /* [{
			//显示数据列的名称。
			'name': '总金额',
			//显示在图表中的数据列，可以为数组或者JSON格式的数据
			'data': [43934, 52503, 57177, 69658, 97031, 119931, 137133, 154175]
	}, {
			'name': '支付宝',
			'data': [24916, 24064, 29742, 29851, 32490, 30282, 38121, 40434]
	}, {
			'name': '微信',
			'data': [11744, 17722, 16005, 19771, 20185, 24377, 32147, 39387]
	}, {
			'name': '银联',
			'data': [null, null, 7988, 12169, 15112, 22452, 34400, 34227]
	}, {
			'name': '其他',
			'data': [12908, 5948, 8105, 11248, 8989, 11816, 18274, 18111]
	}]  */
	${dataList},
	responsive: {
			rules: [{
					/* condition: {
							maxWidth: 1800
					}, */
					chartOptions: {
							legend: {
									layout: 'horizontal',
									align: 'center',
									verticalAlign: 'bottom'
							}
					}
			}]
	}
});

</script>
</html>