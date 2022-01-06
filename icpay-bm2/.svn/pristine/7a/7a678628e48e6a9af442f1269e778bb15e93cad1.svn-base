<#include "/common/add_macro.ftl" />
<#assign label = "资金池信息配置">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="chnlCashPool">
			$("#poolIdSel").validatebox({
				required: true,
				novalidate: true
			});
			$("#chnlId").validatebox({
				required: true,
				novalidate: true,
				validType: ['maxLength[8]']
			});
			$("#wdStatusInp").validatebox({
				required: true,
				novalidate: true,
				validType: ['maxLength[4]']
			});
			
			<#-- $("#addForm").click(function () {
			$("#addForm").form({-->
			var isAdding = false;
			
			$("#saveBtn").click(function () {
				if (isAdding) {
					return;
				} 
			  var chnlMchntCd = $("input[name='chnlMchntCd']").val();
			  var currCd = $("input[name='currCd']").val();
			  
			  <#-- var poolId = $("#poolIdSel").val(); -->
			  var poolId = $("input[name='poolId']").val();
			  if(chnlMchntCd != '' ){
			  	var url =  "${ctx}/chnlCashPool/selectChnlCash.do?poolId=" + poolId + "&chnlMchntCd=" + chnlMchntCd + "&currCd=" + currCd;
				$.get(url, function(data) {
					$.processAjaxResp(data, function(r) {
						var respMsg = r.respMsg;
						if(respMsg == 'OTHER'){
							$.messager.confirm('确认','该大商户已配置到其他资金池，是否继续?',function(r){
							    if (r){
									addSubmit();
							    }
							});
						}else{
							addSubmit();
						}
					});
				});
			}
		});
			
		function addSubmit(){
			isAdding = true;
			
			$.ajax({
				type : "POST",
				url : baseUrl + "add/submit.do",
				async: true,
				cache: false,
				data: $("#addForm").serialize(),
				success: function(data) {
					$.processAjaxResp(data, function() {
							alert("新增资金池配置成功");
						$.jumpTo(baseUrl + "backToManage.do");
					});
					isAdding = false;
				}
			});
		}
		
		var lastSelectedItem=null;
		$('#chnlId').on('change', function(){
			var selectedItem= $('#chnlId').val();
			if (isEmpty(selectedItem)){
				onSelEmptyChnlId();
			}else {
				if (lastSelectedItem != selectedItem)
					onSelectedChnlIdChanged(selectedItem);
			}
			lastSelectedItem = selectedItem;
		});
		
		function onSelEmptyChnlId() {
			$("#chnlMchntCdSel").clearOptions(false);
		}
		
		function onSelectedChnlIdChanged(selectedCh) {
			$("#chnlMchntCdSel").clearOptions(false);
			var poolId = $("input[name='poolId']").val();
			$.ajax({
				type : "GET",
				url : baseUrl + "/getChnlMchntsByCurrCd.do?chnlId="+selectedCh + "&poolId=" + poolId ,
				async: true,
				cache: true,
				success : function(data) {
					var res = parseJson(data);
					if (!checkEmpty(res, "系统异常，请联系管理员！")) return false;
					if (res.respCode != "00"){
						alert("操作失败: "+res.respMsg);
						return false;
					}
					var list = res.respData.chnlMchntList;
					if (! isEmpty(list)) {
						for (var i = 0, len = list.length; i < len; i++) {
						  var item = list[i];
						  var currCdesc ='';
						  if(item.currCd == '156'){
						  	currCdesc = '人民币';
						  }else if (item.currCd == '704'){
						  	currCdesc = '越南盾';
						  }else if (item.currCd == '764'){
						  	currCdesc = '泰铢';
						  }
						  $('#chnlMchntCdSel').append('<option value="' + item.chnlMchntCd + '">' + item.chnlMchntDesc + '|' + item.chnlMchntCd +'|'+ currCdesc + '</option>');
						}
					}					
					
				},
				error:function(){
			    	alert("系统异常，请联系管理员！");
			    	return  false;
			    }
			});
		}
		
		function getchnls() {
			$.ajax({
				type : "GET",
				url : baseUrl + "/getChnls.do" ,
				async: true,
				cache: true,
				success : function(data) {
					var res = parseJson(data);
					if (!checkEmpty(res, "系统异常，请联系管理员！")) return false;
					if (res.respCode != "00"){
						alert("操作失败: "+res.respMsg);
						return false;
					}
					var list = res.respData.chnlsList;
					
					if (! isEmpty(list)) {
						for (var i = 0, len = list.length; i < len; i++) {
						  var item = list[i];
						 
						  $('#chnlId').append('<option value="' + item.chnl + '">' + item.chnlDesc + '</option>');
						}
					}					
					
				},
				error:function(){
			    	alert("系统异常，请联系管理员！");
			    	return  false;
			    }
			});
		}
		
		$('#poolIdSel').combobox({
			onChange: function (newValue, oldValue) {
                   $("#chnlId").clearOptions(false);
                   $("#chnlMchntCdSel").clearOptions(false);
                   getchnls();
            },
			required: false,
			novalidate: true,
			filter : function(q, row){
				var opts = $(this).combobox('options');
				return row[opts.textField].indexOf(q) > -1;
			}
		});
			
		</@head>
	</head>
	
	<body>
		<#-- <@addDiv label="${label}" addUrl="/chnlCashPool/add/submit.do">-->
		<div id="addDiv" class="easyui-panel" title="资金池配置信息" style="padding: 10px;">
			<form id="addForm" method="post" action="">
				<table class="edit_tbl" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label">资金池名称：</td>
						<td class="content">
							<select class="easyui-validatebox" id="poolIdSel" name="poolId" style="width: 300px; height: 27px; ">
								<option value="">--请选择--</option>
								<#list poolList as lst>
									<option value="${lst.poolId}">${lst.poolDesc}-${lst.poolId}-${lst.currCd}</option>
								</#list>
							</select>
						</td>
						
					</tr>
						
					<tr>
						<td class="label">渠道：</td>
						<td class="content">
							<select class="easyui-validatebox" id="chnlId" name="chnlId">
							<option value="">--请选择--</option>
								<@enumOpts enumNm='TxnEnums.ChnlId' showEmptyOpt='false' showCode="true" />
							</select>
						</td>
					</tr>
			
					<tr>
						<td class="label">渠道商户号：</td>
						<td class="content">
							<select class="easyui-validatebox" style="width: 500px; height: 27px;"
								id="chnlMchntCdSel" name="chnlMchntCd">
								<option value="*">--请选择--</option>
							</select>
					        <span class="inputDesc">如渠道商户账户&交易参数中，皆有该资金池的币别且交易方式也符合。则显示在选项中</span>
						</td>
					</tr>
					<tr>
						<td class="label">优先时段：</td>
						<td class="content">
							<input class="easyui-validatebox" type="text" maxlength="20"
							id="txTimeLimitInpt" name="txTimeLimit" />
							<span class="inputDesc">例： "0800-1200,1400-2000”或“不设置” ，空值=不设置。</span>
						</td>
					</tr>
					<tr>
						<td class="label">优先级：</td>
						<td class="content">
							<input class="easyui-validatebox" type="text" maxlength="128"
								id="priorityInp" name="priority" value="50" />
						</td>
					</tr>
					<#--  
					<tr>
						<td class="label">出款状态：</td>
						<td class="content">
							<select id="wdState" name="wdState" data-options="editable:false">
								<option value="0">0-默认</option>
								<option value="1">1-优先</option>
							</select>
						</td>
					</tr>
					-->
				</table>
				<div id="opBtns" style="margin: 10px 0;">
					<a id="saveBtn" href="javascript:void(0)" class="easyui-linkbutton">保存</a>
					<a id="backBtn" href="javascript:void(0)" class="easyui-linkbutton">返回</a>
				</div>
			</form>
		</div>
	</body>
</html>