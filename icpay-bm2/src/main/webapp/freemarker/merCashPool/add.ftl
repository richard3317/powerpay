<#include "/common/add_macro.ftl" />
<#assign label = "小商户资金池信息配置">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="merCashPool">
			$("#poolId").validatebox({
				required: true,
				novalidate: true
			});
			$("#mchntCdInpt").validatebox({
				required: true,
				novalidate: true
			});
			
			<#-- $("#addForm").click(function () {
			$("#addForm").form({-->
			var isAdding = false;
			$("#saveBtn").click(function () {
				if (isAdding) {
					return;
				}
			  var mchntCd = $("#mchntCd").val();
			  var poolId = $("#poolId").val();
			  if(mchntCd != '' ){
			  	var url =  "${ctx}/merCashPool/selectMerCash.do?poolId=" + poolId + "&mchntCd=" + mchntCd;
				$.get(url, function(data) {
					$.processAjaxResp(data, function(r) {
						var respMsg = r.respMsg;
						if(respMsg == 'OTHER'){
							$.messager.confirm('确认','该商户已配置到其他资金池，是否继续?',function(r){
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
								alert("新增小商户资金池信息配置成功");
							$.jumpTo(baseUrl + "backToManage.do");
						});
						isAdding = false;
					}
				});
			}
		<#--币别变更，资金池也变更为该币别的选项-->	
		var lastSelectedItem=null;
		$('#currCd').on('change', function(){
			var selectedItem= $('#currCd').val();
			if (isEmpty(selectedItem)){
				$("#poolId").clearOptions(false);
			}else {
				if (lastSelectedItem != selectedItem){
					onSelectedCurrCdChanged(selectedItem);
				}					
			}
			lastSelectedItem = selectedItem;
		});
			
		function onSelectedCurrCdChanged(selectedCh) {
			$("#poolId").clearOptions(false);
			$.ajax({
				type : "GET",
				url : baseUrl + "/getPoolsByCurrCd.do?currCd="+selectedCh ,
				async: true,
				cache: true,
				success : function(data) {
					var res = parseJson(data);
					if (!checkEmpty(res, "系统异常，请联系管理员！")) return false;
					if (res.respCode != "00"){
						alert("操作失败: "+res.respMsg);
						return false;
					}
					var list = res.respData.poolList;
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
						  $('#poolId').append('<option value="' + item.poolId + '">' + item.poolDesc + '|' + item.poolId +'|'+ currCdesc + '</option>');
						}
					}					
					
				},
				error:function(){
			    	alert("系统异常，请联系管理员！");
			    	return  false;
			    }
			});
		}	
			
		</@head>
	</head>
	
	<body>
		<#-- <@addDiv label="${label}" addUrl="/merCashPool/add/submit.do">-->
		<div id="addDiv" class="easyui-panel" title="小商户资金池信息配置" style="padding: 10px;">
			<form id="addForm" method="post" action="">
				<table class="edit_tbl" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label">商户代码：</td>
						<td class="content">
							<input class="easyui-validatebox" type="text" maxlength="15"
								id="mchntCd" name="mchntCd" />
						</td>				
					</tr>
					<tr>
						<td class="label">资金池名称：</td>
						<td class="content">
							<select class="easyui-validatebox"
								id="poolId" name="poolId" 
								style="width: 300px; height: 27px; ">
								<option value="">--请选择--</option>
								<#list poolList as lst>
									<option value="${lst.poolId}">${lst.poolId}-${lst.poolDesc}-${lst.currCd}</option>
								</#list>
							</select>
						</td>	
					</tr>
					
				</table>
				<div id="opBtns" style="margin: 10px 0;">
					<a id="saveBtn" href="javascript:void(0)" class="easyui-linkbutton">保存</a>
					<a id="backBtn" href="javascript:void(0)" class="easyui-linkbutton">返回</a>
				</div>
			</form>
		</div>
	</body>
</html>