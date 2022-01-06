<#assign ctx = request.contextPath>
<#assign timeNow = .now>
<#assign strNow = timeNow?string["yyyyMMddHHmmss"]>
<#assign strNowMm = timeNow?string["yyyyMMddHHmm"]>

<#macro head label base qryFlg=true qryFuncCode="" paginationFlg=true
	addFlg=false addFuncCode="" addUrl='"add.do"' beforeAdd=""
	detailFlg=false detailFuncCode="" detailDivWidth=600 detailDivHeight=500 detailUrl="" 
	editFlg=false editUrl="" editFuncCode="" beforeEdit=""
	deleteFlg=false deleteUrl="" deleteSuccMsg="" deleteFuncCode="" beforeDelete=""
	lastErrorHandler=""
	>
	<title>${label}管理</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common.css"></link>
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/dialog.css"></link>
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/select2.css"></link>
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/jquery-easy-ui/themes/default/easyui.css"></link>
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/jquery-easy-ui/themes/icon.css"></link>
	
	<script type="text/javascript" src="${ctx}/resources/jquery-easy-ui/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/jquery-easy-ui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/jquery-easy-ui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/bm.js?v=${strNowMm}"></script>
	
	<script type="text/javascript" src="${ctx}/resources/js/select2.js"></script>
	<script type="text/javascript" src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
 

	<#-- 
	<script type="text/javascript" src="${ctx}/resources/js/FileSaver.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/ajax.js"></script> 
	-->
	<style>
		//.datagrid-row-over,
		.datagrid-header td.datagrid-header-over {
		  background: #4682b4;
		  color: #ffffff;
		  cursor: default;
		}
		.datagrid-row-selected {
		  //background: #66cccc; 
		  background: #f0fff0;
		  color: #000000;
		}	
	</style>
	
	<script type="text/javascript" src="${ctx}/resources/js/validate.js"></script>
	<script type="text/javascript">
		var _ctx = "${ctx}";
		var logout_url = "${ctx}/logout.do";
		var baseUrl = "${ctx}/${base}/";
		var lastError="${lastError!''}";
		
		if (! isEmpty(lastError)) {
			<#if lastErrorHandler??  && lastErrorHandler != ''>
			    ${lastErrorHandler}(lastError);
			<#else>
		  		alert(lastError);
			</#if>
		}
		
		<#--  
		function parseJson(data){
			try {
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
		
		function checkEmpty(obj, msg){
			if (isEmpty(obj)) {
				if (isEmpty(msg)) msg = "错误！";
				alert(msg);
				return false;
			}
			return true;
		}
		-->
		
		$(function() {
			<@authCheck funcCode='${qryFuncCode}'>
				$("#qryBtn").click(function() {
				var condlimit=0;
				if("${label}" == "交易信息" || "${label}" == "取现查询"){
				var m_mchntcd = document.getElementById("mchntCd").value+"";
				var m_orderId = document.getElementById("orderId").value+"";
				<#-- 检查商户号跟订单号 -->
				if(m_mchntcd != "" && m_orderId !=""){
					if(document.getElementById("Qry_indistinct").checked==false){
						document.getElementById("Qry_indistinct").checked=true;
						document.getElementById("Qry_indistinct").value=1;
						alert("进入订单号模糊查询模式");
						}
					}
				var checkstate = document.getElementById("Qry_indistinct").value;
				<#-- 检查模糊查询条件 -->
					if(checkstate == 1){
					var m_startdate = document.getElementById("startDate").value.toString();
					var m_endDate 	= document.getElementById("endDate").value.toString();
					<#-- init -->
					condlimit=0;
					<#-- 检查日期 是否跨月 -->
					var m_startMonth 	= m_startdate.substring(0,6);
					var m_endMonth 		= m_endDate.substring(0,6);
						if(m_startMonth != m_endMonth){
						alert("模糊查询 不得跨月");
						condlimit=1;
						}
					<#-- 检查日期跨度是否超过7天 -->
					var m_statDate 		= m_startdate.substring(0,8);
					var m_endDate 		= m_endDate.substring(0,8);
					var diff = parseInt(m_endDate) - parseInt(m_statDate);
						if(diff > 7){
						alert("模糊查询 日期跨度不得超过7天");
						condlimit=1;
						}
					<#-- 检查商户号是否为空 -->
						if(m_mchntcd == ""){
				 		alert("模糊查询  商户号不可为空");
				 		condlimit=1;
				 		}
				 	<#-- 检查订单号长度是否4位以上 -->
				 		if(m_orderId.length < 4){
				 		alert("模糊查询  订单号至少提供4码以上");
				 		condlimit=1;
				 		}
					}
				}
				document.getElementById("showMore").value=0;
				if("${label}" == "渠道虚拟终端信息" || "${label}" == "渠道商户账户管理" || "${label}" == "代付路由信息" ){
					<#-- 检查内容是否有逗号 -->
					var m_chnlmnCd = document.getElementById("chnlMchntCd").value.toString();
					if(m_chnlmnCd.indexOf(",")!=-1){
						if("${label}" == "渠道虚拟终端信息"){
							alert("不支持查询多个渠道商户");
				 			condlimit=1;
						}
						else{
							if(document.getElementById("showBmchnt").checked){
								alert("如需同时查询多个渠道商户，请取消勾选<展示关联商户>");
				 				condlimit=1;
							}
						}
					}
					if(document.getElementById("showBmchnt").checked && condlimit!=1){
						document.getElementById("showMore").value=1;
					}
				}
				
				if(condlimit==0){
					queryPage('qryForm', 1);
					}
				});
				<#if paginationFlg>
					initQry("qryForm", "qryResultDiv", "qryResultTbl");
				<#else>
					initQry("qryForm", "qryResultDiv", "qryResultTbl", false);
				</#if>
				
				<#if qryFlg>
					queryPage("qryForm", null);
				</#if>
			</@authCheck>
			$("#clrBtn").click(function() {
				clearFm('qryForm');
			});
			<@authCheck funcCode='${detailFuncCode}'>
				<#if detailFlg>
				$("#detailBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						var url = baseUrl + ${detailUrl};
						crtViewDialog("detailDiv", "${label}详情", url, ${detailDivWidth}, ${detailDivHeight});
					}
				});
				</#if>
			</@authCheck>
			<@authCheck funcCode='${addFuncCode}'>
				<#if addFlg>
					$("#addBtn").click(function() {
						var sel = $("#qryResultTbl").datagrid("getSelected");
						<#if beforeAdd??  && beforeAdd != ''>
								if (! ${beforeAdd}(sel)) return false;
						</#if>
					
						$.jumpTo(baseUrl + ${addUrl});
					});
				</#if>
			</@authCheck>
			<@authCheck funcCode='${deleteFuncCode}'>
				<#if deleteFlg>
					$("#delBtn").click(function() {
						var sel = $("#qryResultTbl").datagrid("getSelected");
						<#if beforeDelete??  && beforeDelete != ''>
							if (! ${beforeDelete}(sel)) return false;
						<#else>
							if (sel == null) {
								alert("请选择一条记录");
								return false;
							}
							if (! confirm("确认要删除选中的记录吗?")) return false;
						</#if>
						if (dealFlag) {//重复提交控制
							alert("正在处理请求，请稍等......");
							return false;
						}
						dealFlag = true;		 
						var url = baseUrl + ${deleteUrl};
						$.post(url,
							function(data) {
								$.processAjaxResp(data, function() {
									<#if deleteSuccMsg?? && deleteSuccMsg != ''>
										alert("${deleteSuccMsg}");
									<#else>
										alert("删除${label}成功");
									</#if>
									$("#qryForm").submit();
								});
								dealFlag = false;
							}
						);
					});
				</#if>
			</@authCheck>
			<@authCheck funcCode='${editFuncCode}'>
				<#if editFlg>
				$("#editBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					<#if beforeEdit??  && beforeEdit != ''>
						if (! ${beforeEdit}(sel)) return false;
					<#else>
						if (sel == null) {
							alert("请选择一条记录");
							return false;
						}	
					</#if>
					$.jumpTo(baseUrl + ${editUrl});
				});
				</#if>
			</@authCheck>
			<#nested>
		});
	</script>
</#macro>

<#macro qryDiv qryUrl showBtn=true qryFuncCode="" enctype="application/x-www-form-urlencoded" method="post">
	<@authCheck funcCode="${qryFuncCode}">
		<div id="qryDiv">
			<form id="qryForm" method="${method}" action="${ctx}${qryUrl}" enctype="${enctype}" >
				<input type="hidden" id="pageNum" name="pageNum" value="${(qryParamMap.pageNum)!1}" />
				<input type="hidden" id="pageSize" name="pageSize" value="${(qryParamMap.pageSize)!20}" />
				<#-- for 关联商户的搜寻使用 -->
				<input type="hidden" id="showMore" name="showMore" value="0"/>
				<#nested>
			</form>
			
			<#if showBtn>
				<div style="margin: 10px 0;">
					<a id="qryBtn" href="javascript:void(0)" class="easyui-linkbutton">查询</a>
					<a id="clrBtn" href="javascript:void(0)" class="easyui-linkbutton">重置</a>
				</div>
			</#if>
		</div>
	</@authCheck>
</#macro>

<#macro qryResultDiv qryFuncCode="" addFlg=false addFuncCode="" 
	detailFlg=false detailFuncCode="" editFlg=false editFuncCode="" deleteFlg=false deleteFuncCode="">
	<div style="margin: 10px 0;">
		<div id="qryResultDiv" title="查询结果">
			<div id="opBtns" style="margin: 10px 0;">
				<@authCheck funcCode='${detailFuncCode}'>
					<#if detailFlg>
						<a id="detailBtn" href="javascript:void(0)" class="easyui-linkbutton"  data-options="iconCls:'icon-search'">查 看 详 情</a>
					</#if>
				</@authCheck>
				<@authCheck funcCode='${addFuncCode}'>
					<#if addFlg>
						<a id="addBtn" href="javascript:void(0)" class="easyui-linkbutton"  data-options="iconCls:'icon-add'">新 增</a>
					</#if>
				</@authCheck>
				<@authCheck funcCode='${editFuncCode}'>
					<#if editFlg>
						<a id="editBtn" href="javascript:void(0)" class="easyui-linkbutton"  data-options="iconCls:'icon-edit'">修 改</a>
					</#if>
				</@authCheck>
				<@authCheck funcCode='${deleteFuncCode}'>
					<#if deleteFlg>
						<a id="delBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'">删 除</a>
					</#if>
				</@authCheck>
				<#nested>
			</div>
			<table id="qryResultTbl"></table>
		</div>
	</div>
</#macro>