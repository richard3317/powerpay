<#include "/common/add_macro.ftl" />
<#assign label = "公告信息">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="announcementManagement">
			$("#type1").click(function() {
					$("#contentMchntId").hide();
					// 将文本域的值设为空
					$("#contentMchntId_content").val("");
					$("#errorMsg").html("");
				})

			$("#type2").click(function() {
				$("#contentMchntId").show();
			})

			<#--正则  -->
			$("#contentTitle").validatebox({
				required: true,
				novalidate: true,
				invalidMessage: '请输入标题'
			});
			$("#contentText").validatebox({
				required: true,
				novalidate: true,
				validType: 'cnMaxLength[2000]',
				invalidMessage: '请输入内容'
			});

			$("#beginTime").validatebox({
				required: true,
				novalidate: true
				
			});

			$("#endTime").validatebox({
				required: true,
				novalidate: true
			});

			$.extend($.fn.validatebox.defaults.rules, {
					mchntStr: {
		            validator: function(value,param){
		                var radValue=document.getElementsByName("type")[0];
		                var valueStr=$("#contentMchntId_content").val();
					    if(radValue=='2' && (valueStr ==null ||valueStr=='')){
					    	return false;
					    }else if(radValue=='1'){
					    	return true;
					    }{
                        <#--调用ajax实时从后台获取数据-->
                            var contentMchntId=$("#contentMchntId_content").val();
                            $.ajax({
                                url:"${ctx}/announcementManagement/addQuery.do",
                                data:{"contentMchntId":contentMchntId},
                                type:"post",
                                success:function(date){
                            <#--将错误信息显示在界面上，并标明颜色-->
                                if(date!=null && date !=''){
                                    $("#errorMsg").html(date+"商户不存在，请重新输入");
                                    $("#errorMsg").css("color","red");
                                }else{
                                    $("#errorMsg").html("");
                                }
                                }
                            });
                        }
                        if($("#errorMsg").html()== null || $("#errorMsg").html()=='' ||$("#errorMsg").html()=='商户号之间由回车隔开'){
                            return true;
                        }else{
                            return false;
                        }
                        return true;
		            },
		            message: '请输入正确的商户号'
		        }
		    });
		    
		</@head>
	</head>

	<body>
		<@addDiv label="${label}" addUrl="/announcementManagement/add/submit.do">
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
			<tr>
					<td class="label">标题：</td>
					<td class="content">
						<input type="text" class="easyui-validatebox" name="contentTitle" id="contentTitle" style="width:200px; height:20px;"/>
						<span  class="inputDesc">必填字段</span>
					</td>
				</tr>
				<tr>
					<td class="label">正文：</td>
					<td class="content">
						<textarea class="easyui-validatebox" name="contentText" id="contentText" rows="" cols="" style="width:200px; height:50px;" wrap="physical"></textarea>
						<span  class="inputDesc">必填字段</span>
					</td>
				</tr>
				<tr>
					<td class="label">有效时段：</td>
					<td class="content">
						<input id="beginTime" name="beginTime" type="text"
							   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH',maxDate:'#F{$dp.$D(\'beginTime\')}', readOnly:true})"
							   class="Wdate" style="width:200px; height:20px;"/>
						至
						<input id="endTime" name="endTime" type="text"
							   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH',minDate:'#F{$dp.$D(\'endTime\')}', readOnly:true})"
							   class="Wdate" style="width:200px; height:20px;"/><span class="inputDesc">必填字段</span>
					</td>
	
				</tr>
				<tr>
					<td class="label">发布范围：</td>
					<td class="content">
						<input type="radio" name="type" id="type1" checked="checked" value="1">全部商户
						<input type="radio" name="type" id="type2" value="2">指定商户
					</td>
				</tr>
				<tr id="contentMchntId" style="display: none;">
					<td class="label">输入商户号</td>
					<td class="content">
						<textarea name="contentMchntId" class="easyui-validatebox" id="contentMchntId_content" rows="" cols="" style="width:200px; height:50px;" data-options="validType:'mchntStr[5]'"></textarea>
                        <span id="errorMsg" class="inputDesc" >商户号之间由回车隔开</span>
					</td>
				</tr>

			</table>
		</@addDiv>
	</body>
</html>