<#include "/common/add_macro.ftl" />
<#assign label = "小商户资金池信息配置">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="organ" addSuccMsg="机构新增任务已提交，请等待审核">
			$("#organDescSel").validatebox({
				required: true,
				novalidate: true
			});
			
			
			<#-- 检查输入的商户号是否正确 -->
		<#-- 	$("#mchntCdsList").click(function () {
				var lines = $('#mchntCdsList').val().split(/\n/);
				for (var i=0; i < lines.length; i++) {
				 	var text = lines[i];
				 	var resp = checkMchnt(text);
				 	if("false" == resp){
				 		
				 		var mchntCdsList =document.getElementById('mchntCdsList').value;
						var j=text.length;
						var len=mchntCdsList.length;
						for(var i=0;i<len-j;i++){
						    var son_string=mchntCdsList.substring(i,i+j);
							if(son_string==text){
							     document.getElementById('textarea').setSelectionRange(i,i+j) 
							   	break;
							}
						}
				 	}
				}
			}); -->
			
			function checkMchnt(text){
				<#-- 检查商户号是否有效 -->
				var url =  "${ctx}/organ/checkMchntCd.do?&mchntCd=" + text;
				$.get(url, function(data) {
					$.processAjaxResp(data, function(r) {
						return r
					});
				});
			}
												
			<#-- $("#addForm").click(function () {
			$("#addForm").form({-->
			var isAdding = false;
			
			$("#saveBottn").click(function () {
				if (isAdding) {
					return;
				} 
			  addSubmit();
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
							<#-- alert("新增机构审核任务成功"); -->
						var res = parseJson(data);
						if (res.respCode == "00"  && res.respMsg != "OK" ) {
							alert(res.respMsg + '\r\n'  + '新增机构商户已提交审核!');
						}else if(res.respCode != "00"){
							alert(res.respMsg);
						}else{
							alert('新增机构商户已提交审核!');
						}
					
						$.jumpTo(baseUrl + "backToManage.do");
					});
					isAdding = false;
				},
			    error:function(){
			    	alert("系统异常，请联系管理员！");
			    	return  false;
			    }
			});
		}
		<#-- $('#organDescSel').combobox({
			required: true,
			novalidate: true,
			filter : function(q, row){
				var opts = $(this).combobox('options');
				return row[opts.textField].indexOf(q) > -1;
			},
			onSelect : function(rec){
				$("#organId").val("");
			}
		});-->
		
		$("#organDescSel").select2();
		
		
		
		</@head>
		<script type="text/javascript">
		<#-- $("#organDescSel").onchange=function(){-->
			function organChange(){
				var id=$("#organDescSel").select2("data")[0].id ; //id
				var rec =$("#organDescSel").select2("data")[0].text ; //文本值
				$("#organId").val(id);
				$("#organDescInp").val(rec);
			}
			function organDescChange(){
				var rec =$("#organDescSel").select2("data")[0].text ; //文本值
				var organDesc = $("#organDescInp").val();
				if(rec != organDesc){
					$("#organId").val('');
				}
			}
		</script>
	</head>
	
	<body>
		<div id="addDiv" class="easyui-panel" title="机构配置信息" style="padding: 10px;">
			<form id="addForm" method="post" action="">
				<input type="hidden"  id="organId" name ="organId"/>
				<table class="edit_tbl" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label">机构名称：</td>
						<td class="content">
							<input class="easyui-validatebox" type="text" maxlength="128" 
							id="organDescInp" name="organDesc" value="" onchange="organDescChange()" />  - 搜索：
							<select class="easyui-validatebox"
								id="organDescSel" name="organDescSel"  onchange="organChange()"
								style="width: 200px; height: 27px; " >
								<option value=""></option>
								<#list organList as organ>
									<option value="${organ.organId}">${organ.organId}-${organ.organDesc}</option>
								</#list>
							</select>
						</td>	
					</tr>
					<tr>
						<td class="label">商户号：</td>
						<td class="content">
							<textarea cols="50" rows="1000" class="easyui-validatebox" maxlength="16000"
								id="mchntCdsListInpt" name="mchntCdsList" style="margin: 0px; width: 426px; height: 314px;"
								placeholder="可输入多个商户号,以「换行」区隔,允许多行输入" ></textarea>							
							<span class="inputDesc">可输入多个项目以「换行」区隔，允许多行输入</span>
						</td>
					</tr>
					
				</table>
				<div id="opBtns" style="margin: 10px 0;">
					<a id="saveBottn" href="javascript:void(0)" class="easyui-linkbutton">保存</a>
					<a id="backBtn" href="javascript:void(0)" class="easyui-linkbutton">返回</a>
				</div>
			</form>
		</div>
	</body>
</html>