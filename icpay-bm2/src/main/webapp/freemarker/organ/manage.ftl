<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>

		<@head label="机构管理" base="organ" qryFuncCode="1001010001" addFlg=true addFuncCode="1001010003" >
			
			$(function() {
				
				$("#qryButton").click(function() {
					queryTablePage('qryForm', 1);
				});
					var cellnames = ["organId","organDesc"];
					initQryForm("qryForm", "qryResultDiv", "qryResultTbl",cellnames);
				
					queryTablePage("qryForm", null);
				$("#clrBtn").click(function() {
					clearFm('qryForm');
				});
			
				<@authCheck funcCode='1001010002'>
				$("#detailBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getChecked");
					if(sel.length < 1){
						alert("请选择一条记录");
						return;
					}else if(sel.length > 1){
						alert("不可同时查看多条记录详情！");
						return;
					}else {
						var url = baseUrl + "detail.do?organId="+sel[0].organId;
						crtViewDialog("detailDiv", "机构管理详情", url, 800, 550);
					}
				});
				</@authCheck>
				<@authCheck funcCode='1001010007' deleteSuccMsg="机构删除任务已提交，请等待审核!">
					$("#delBtn").click(function() {
						var sel = $("#qryResultTbl").datagrid("getChecked");
						if(sel.length < 1){
							alert("请选择一条记录");
							return;
						}
						if (! beforeDelete(sel)) return false;
						if (dealFlag) {//重复提交控制
							alert("正在处理请求，请稍等......");
							return false;
						}
						dealFlag = true;
						var organIdStr ="";
						for(var i =0 ; i< sel.length ; i++){
							organIdStr = organIdStr + sel[i].organId + ";";
						}
						organIdStr = organIdStr.substring(-1);
						var url = baseUrl + "delete.do?organIdStr="+organIdStr;
						$.post(url,
							function(data) {
								$.processAjaxResp(data, function() {
									$.messager.alert('提示', "机构删除任务已提交，请等待审核!");
									$("#qryForm").submit();
								});
								dealFlag = false;
							}
						);
					});
				</@authCheck>
				<@authCheck funcCode='1001010005'>
				$("#editBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getChecked");
					if(sel.length < 1){
						alert("请选择一条记录");
						return;
					}
					if(sel.length > 1){
						alert("不可同时修改多条！");
						return;
					}
					var organId = sel[0].organId;
					$.jumpTo(baseUrl + "edit.do?organId="+organId);
				});
			</@authCheck>
			
			
			function beforeDelete(sel){
				return confirm("确认要删除选中的记录吗?");
			}
		
		});
		<@authCheck funcCode='1001010008'>
		$("#resetPwdBtn").click(function() {
				var sel = $("#qryResultTbl").datagrid("getChecked");
				var organIdArr = [] ;
				if(sel.length < 1){
					alert("至少选择一条记录");
					return;
				} else {
					if (confirm("是否确认重置机构管理员登录密码?")) {
						for (var i =0 ; i < sel.length ;i++ ){
							if (sel[i].state != '1') {
								alert("只能重置有效的机构！");
							} else {
								<#-- organIdArr = organIdArr + sel[i].organId + ";"; -->
								if(organIdArr.indexOf(sel[i].organId) == -1){
          							organIdArr .push(sel[i].organId);
								}
							}
						}
						var url = baseUrl + "resetPwd.do?organIdArr=" + organIdArr;
						$.post(url, function(data) {
							$.processAjaxResp(data, function(r) {
								var data = eval("("+r.respData.respJson+")");
								var str="";
								$.each(data,function(index,item){
									str+="登录账户为："+item.organId+"  重置后的密码为："+item.newPwd+"</br/>";
								});
								 $('#pwdDiv').html("机构管理员登录密码重置成功!" +  "</br/>" + str);
							});
						});
					}
				}
			});
			</@authCheck>
			
		<@authCheck funcCode='1001010009'>
		$("#resetGaCodeBtn").click(function() {
				var sel = $("#qryResultTbl").datagrid("getChecked");
				var organIdArr = [];
				if(sel.length < 1){
					alert("至少选择一条记录");
					return;
				} else {
					if (confirm("是否确认重置机构管理员谷歌验证码吗?")) {
						for (var i =0 ; i < sel.length ;i++ ){
							if (sel[i].state != '1') {
								alert("只能重置有效的机构！");
							} else {
								<#-- organIdArr = organIdArr + sel[i].organId + ";"; -->
								if(organIdArr.indexOf(sel[i].organId) == -1){
          							organIdArr .push(sel[i].organId);
								}
							}
						}
						var url = baseUrl + "resetGaCode.do?organIdArr=" + organIdArr;
						$.post(url, function(data) {
							$.processAjaxResp(data, function(r) {
								var data = eval("("+r.respData.respJson+")");
								var str="";
								$.each(data,function(index,item){
									str += item.organId+"，";
								});
								 $('#pwdDiv').html(str + "管理员登录谷歌验证码重置成功!");
							});
						});
					}
				}
			});
			</@authCheck>
		
		</@head>
		
		
<script type="text/javascript">

/**
 * 初始化查询功能
 * @param qryDivId 查询表单所在的DIV
 * @param resultDivId 查询结果所在的DIV
 * @param gridId 查询结果列表
 * @param qryChecker 查询前对查询条件进行校验
 * @return
 */
function initQryForm(qryFmId, resultDivId, gridId,cellnames, pagination, singleSel, qryChecker, callBack) {
	// 初始化查询DIV
	var qryFm = $("#" + qryFmId);
	var resultDiv = $("#" + resultDivId);
	qryFm.form({
		url: qryFm.attr("action"),
		onSubmit: function() {
			var isValid = $(this).form('validate');
			if (!isValid){
				$.messager.progress('close');	// hide progress bar while the form is invalid
			}
			if (typeof(qryChecker) == "function") {
				isValid = qryChecker();
			}
			return isValid;
		},
		success: function(data) {
			var j = eval("(" + data + ")");
			if (j.respCode == "00") {
				resultDiv.panel("open");
				showQryDataResult(qryFmId, gridId, j.respData.pagerResult, pagination, singleSel, resultDivId,cellnames);
				if (typeof(callBack) == "function") {
					callBack()					 
				}
			} else if (j.respCode == "ZZ") {
				alert("查询失败:" + j.respMsg);
			} else {
				alert("未知错误，请联系管理员");
			}
		}
	});
	
	// 初始化查询结果DIV
	var title = resultDiv.attr("title");
	if ($.trim(title) == "") {
		title = "查询结果";
	}
	resultDiv.panel({
		title: resultDiv.attr("title"),
		closed: true
	});
}
 
/**
 * 创建一个DataGrid
 * @param gridId
 * @param j
 * @param qryFm
 * @return
 */
function showQryDataResult(qryFmId, gridId, pagerResult, pagination, singleSel,resultDivId,cellnames) {
	if (pagination == null || pagination == "undefined") {
		pagination = true;
	}
	if (singleSel == null || singleSel == "undefined") {
		singleSel = true;
	}
	
	
	
	var grd = $("#" + gridId);
	var qryFm = $("#" + qryFmId);
	var index='';
	if (pagination) {
		grd.datagrid({
			//height: 380,
			singleSelect: false,
//			nowrap: true,  // 截取超出部分的数据
			striped: true,
			checkOnSelect: false,
			selectOnCheck: false,
			pageNumber: pagerResult.pageNum,
			pageSize: pagerResult.pageSize,
			pageList: [10,20,50,100,150],
			loadMsg: "查询中...",
		    
			onLoadSuccess:function(data){	//在加载完成之后合并	
				//所有列进行合并操作
		        //$(this).datagrid("autoMergeCells");
		        //指定列进行合并操作
		        $(this).datagrid("autoMergeCells", cellnames);
			},
			
			frozenColumns:[[  
	              {field:'ck',checkbox:true},
	              {field:'organId',title:'机构编号',width:100}
	              ]],
			rownumbers:true,  //显示带有行号的列。
			selectOnCheck:false, //如果设置为 true，点击复选框将会选中该行。如果设置为 false，选中该行将不会选中复选框
			columns:[[
				{field:'organDesc',title:'机构名称',width:150,align:'left',editor:'text'},
<#-- //				{field:'organDesc',title:'机构名称',width:150,
//					formatter:function(value){
//						for(var i=0; i< organList.length; i++){
//							if (organList[i].organId == value) return organList[i].name;
//						}
//						return value;
//					},
//					editor:{
//						type:'combobox',
//						options:{
//							valueField:'organId',
//							textField:'organDesc',
//							data:organList,
//							required:true
//						}
//					}
//				}, -->
				{field:'mchntCd',title:'商户号',width:150,align:'left'},
				{field:'mchntCnNm',title:'商户名称',width:180,align:'left'},
				{field:'state',title:'状态',width:80,align:'center',
				<#--  //editor:'text', -->
					formatter:function(value,row,index){
						if(row.state == 0){
							return "无效";
						}else{
							return "有效"
						}
					}
					<#--//,
					//editor:{
					//	type:'checkbox',
					//	options:{
					//		on: '1',
					//		off: '0'
					//	}
					//} -->
				},
				{field:'lastOperId',title:'最后操作员',width:150},
				{field:'recCrtTs',title:'创建时间',width:150},
				{field:'recUpdTs',title:'更新时间',width:150},
			   <#--  {field:'updateOpt',title:'更新',width:70,align:'center',
					formatter:function(value,row,index){
						if (row.editing){
							var s = '<a href="#" onclick="saverow(this)">保存</a> ';
							var c = '<a href="#" onclick="cancelrow(this)">取消</a>';
							return s+c;
						} else {
							var e = '<a href="#" onclick="editrow(this)">修改</a> ';
							return e;
						}
					}
				}, -->
			    {field:'opt',title:'操作',width:70,align:'center',
					formatter:function(value,row,index){
							var d = '<a href="#" onclick="deleterow(this)">删除</a>';
							return d;
					}
				}
			         
			]],
			
			onBeforeEdit:function(index,row){
				row.editing = true;
				updateActions(index);
			},
			onAfterEdit:function(index,row){
				row.editing = false;
				updateActions(index);
			},
			onCancelEdit:function(index,row){
				row.editing = false;
				updateActions(index);
			},
			
			data: pagerResult.resultList,
			pagination: true, //底部分页工具栏 
			rowStyler:function(index,row){
				if ((row!=null) &&(row.rowStyler!=null)){
					return row.rowStyler;
				}
			}

		});
		grd.datagrid("getPager").pagination({
			beforePageText:'第',
			afterPageText:'页，共{pages}页',
			displayMsg:'{from}至{to}/共{total}条记录&nbsp;',
			pageNumber: pagerResult.pageNum,
			total: pagerResult.total,
			onSelectPage: function(pageNum, pageSize) {
				if (pageNum == 0) {
					pageNum = 1;
				}
				qryFm.children("input[name='pageNum']").val(pageNum);
				qryFm.children("input[name='pageSize']").val(pageSize);
				qryFm.submit();
			}
		});		 
	} else {
		grd.datagrid({
			//height: 220,
			singleSelect: singleSel,
			//fitColumns: true,
			checkOnSelect: false,
			selectOnCheck: false,
			columns:[
			         pagerResult.columnLst
			],
			data: pagerResult.resultList,
			rowStyler:function(index,row){
				if ((row!=null) &&(row.rowStyler!=null)){
					//return 'background-color:pink;color:blue;font-weight:bold;';
					return row.rowStyler;
				}
			}
		});
	}
}


$("#ck").click(function() {
	queryTablePage('qryForm', 1);
});

function updateActions(index){
	$('#qryResultTbl').datagrid('updateRow',{
		index: index,
		row:{}
	});
}


function getRowIndex(target){
	var tr = $(target).closest('tr.datagrid-row');
	return parseInt(tr.attr('datagrid-row-index'));
}
function editrow(target){
	$('#qryResultTbl').datagrid('beginEdit', getRowIndex(target));
	var cellnames = ["organId","organDesc"];
	$('#qryResultTbl').datagrid("autoMergeCells", cellnames);
}
function deleterow(target){
	$.messager.confirm('Confirm','确认删除？',function(r){
		if (r){
			var indexId=getRowIndex(target);
			var rows = $('#qryResultTbl').datagrid('getRows');
			var row=rows[indexId];
			var organId = row.organId;
			var organDesc = row.organDesc;
			var mchntCd = row.mchntCd;
			$.ajax({
				type : "POST",
				url : "${ctx}/organ/delete.do",
				async : false,
				data : { organId : organId, organDesc : organDesc,mchntCd : mchntCd},
				success : function(res) {
					var data = parseJson(res);
					if (data.respCode == "00") {
						$.messager.alert('提示', "机构删除任务已提交，请等待审核!");
						queryTablePage('qryForm', 1);
					} else {
						$.messager.alert('提示', data.respMsg);
						queryTablePage('qryForm', 1);
					}
				}
			});
		
			$('#qryResultTbl').datagrid('deleteRow', getRowIndex(target));
			var cellnames = ["organId","organDesc"];
			$('#qryResultTbl').datagrid("autoMergeCells", cellnames);
		}
	});
}
function saverow(target){
	var indexId=getRowIndex(target);
	
	var rows = $('#qryResultTbl').datagrid('getRows');
	var row=rows[indexId];
	var organId = row.organId;
	var organDesc = row.organDesc;
	$.ajax({
		type : "POST",
		url : "${ctx}/organ/edit/submit.do",
		async : true,
		data : {organId : organId,organDesc : organDesc},
		success : function(res) {
			var data = parseJson(res);
			if (data.respCode == "00") {
				$.messager.alert('提示', "机构名称修改成功！");
				$('#qryResultTbl').datagrid('endEdit', getRowIndex(target));
				var cellnames = ["organId","organDesc"];
				$('#qryResultTbl').datagrid("autoMergeCells", cellnames);
			} else {
				$.messager.alert('提示', data.respMsg);
				$('#qryResultTbl').datagrid('endEdit', getRowIndex(target));
				var cellnames = ["organId","organDesc"];
				$('#qryResultTbl').datagrid("autoMergeCells", cellnames);
			}
		}
	});
	
	$('#qryResultTbl').datagrid('endEdit', getRowIndex(target));
	var cellnames = ["organId","organDesc"];
	$('#qryResultTbl').datagrid("autoMergeCells", cellnames);
	
}
function cancelrow(target){
	$('#qryResultTbl').datagrid('cancelEdit', getRowIndex(target));
	var cellnames = ["organId","organDesc"];
	$('#qryResultTbl').datagrid("autoMergeCells", cellnames);
}

function NOTNULL(obj) {
    if (typeof (obj) == "undefined" || obj === "" || obj == null || obj == "null") {
        return false;
    }
    return true;
}

function NULL(obj) {
    if (typeof (obj) == "undefined" || obj === "" || obj == null || obj == "null") {
        return true;
    }
    return false;
}

//查询
function queryTablePage(qryForm, pageno) {
	//if (pageno != null && pageno != "undefined") {
	//	$("#"+qryForm).children("input[name='pageNum']").val(pageno);
	//}
	if (pageno == null || pageno == "undefined") {
		pageno = 1;
	}
	if (qryForm == null || qryForm == "undefined") {
		qryForm = "qryForm";
	}
	$("#"+qryForm).children("input[name='pageNum']").val(pageno);
	$("#"+qryForm).submit();
}




$.extend($.fn.datagrid.methods, {
    autoMergeCells: function(jq, fields) {
        return jq.each(function() {
            var target = $(this);
            if (!fields) {
                fields = target.datagrid("getColumnFields");
            }
            var rows = target.datagrid("getRows");
            var i = 0,
            j = 0,
            temp = {};
            for (i; i < rows.length; i++) {
                var row = rows[i];
                j = 0;
                for (j; j < fields.length; j++) {
                    var field = fields[j];
                    var tf = temp[field];
                    if (!tf) {
                        tf = temp[field] = {};
                        tf[row[field]] = [i];
                    } else {
                        var tfv = tf[row[field]];
                        if (tfv) {
                            tfv.push(i);
                        } else {
                            tfv = tf[row[field]] = [i];
                        }
                    }
                }
            }
            $.each(temp,
            function(field, colunm) {
                $.each(colunm,
                function() {
                    var group = this;
                    if (group.length > 1) {
                        var before, after, megerIndex = group[0];
                        for (var i = 0; i < group.length; i++) {
                            before = group[i];
                            after = group[i + 1];
                            if (after && (after - before) == 1) {
                                continue;
                            }
                            var rowspan = before - megerIndex + 1;
                            if (rowspan > 1) {
                                target.datagrid('mergeCells', {
                                    index: megerIndex,
                                    field: field,
                                    rowspan: rowspan
                                });
                                target.datagrid('mergeCells', {
                                	index: megerIndex,
                                	field: 'ck',
                                	rowspan: rowspan
                                });
                                target.datagrid('mergeCells', {
                                	index: megerIndex,
                                	field: 'updateOpt',
                                	rowspan: rowspan
                                });
                                
                            }
                            if (after && (after - before) != 1) {
                                megerIndex = after;
                            }
                        }
                    }
                });
            });
        });
    }
});
</script>
	</head>
	
	<body style="padding: 5px;">
		<div id="mngDiv" class="easyui-panel" title="机构管理" style="padding: 10px;">
			<#-- <@qryDiv qryUrl="/organ/qry.do" qryFuncCode="1001010001"> -->
		<div id="qryDiv">
			<form id="qryForm" method="post" action="${ctx}/organ/qry.do" enctype="application/x-www-form-urlencoded" >
				<input type="hidden" id="pageNum" name="pageNum" value="${(qryParamMap.pageNum)!1}" />
				<input type="hidden" id="pageSize" name="pageSize" value="${(qryParamMap.pageSize)!20}" />
				<table class="qry_tbl">
					<tr>
						<td>商户号：</td>
						<td>
							<input class="layui-input" type="text" id="mchntCdInpt" maxLength="15"
								name="_QRY_mchntCd" value="${(qryParamMap.mchntCd)!''}">
						</td>
						<td>商户名称：</td>
						<td>
							<input class="layui-input" type="text" id="mchntDescInpt" maxLength="15" 
								name="_QRY_mchntDesc" value="${(qryParamMap.mchntDesc)!''}">
						</td>
						<td>状态：</td>
						<td>
							<select id="state" name="_QRY_state" data-options="editable:false">
								<option value="">--请选择--</option>
								<option value="0">0-无效</option>
								<option value="1">1-有效</option>
							</select>
						</td>
					</tr>
					
					<tr>	
						<td>机构编号：</td>
						<td>
							<input class="layui-input" type="text" id="organIdInpt" maxLength="15"
								name="_QRY_organId" value="${(qryParamMap.organId)!''}">
						</td>
						<td>机构名称：</td>
						<td>
							<input class="layui-input" type="text" id="organDescInpt" maxLength="15"
								name="_QRY_organDesc" value="${(qryParamMap.organDesc)!''}">
						</td>
					</tr>
									
				</table>
				</form>
				<div style="margin: 10px 0;">
					<a id="qryButton" href="javascript:void(0)" class="easyui-linkbutton">查询</a>
					<a id="clrBtn" href="javascript:void(0)" class="easyui-linkbutton">重置</a>
				</div>
			</div>
			<div style="margin: 10px 0;">
		<div id="qryResultDiv" title="查询结果">
			<div id="opBtns" style="margin: 10px 0;">
				<@authCheck funcCode='1001010002'>
						<a id="detailBtn" href="javascript:void(0)" class="easyui-linkbutton" 
							data-options="iconCls:'icon-search'">查询详情</a>
				</@authCheck>
				<@authCheck funcCode='1001010003'>
						<a id="addBtn" href="javascript:void(0)" class="easyui-linkbutton" 
							data-options="iconCls:'icon-add'">新增</a>
				</@authCheck>
				<@authCheck funcCode='1001010005'>
						<a id="editBtn" href="javascript:void(0)" class="easyui-linkbutton" 
							data-options="iconCls:'icon-edit'">修改</a>
				</@authCheck>
				<@authCheck funcCode='1001010007'>
						<a id="delBtn" href="javascript:void(0)" class="easyui-linkbutton" 
							data-options="iconCls:'icon-clear'">删除</a>
				</@authCheck>
				
				<@authCheck funcCode='1001010008'>
					<a id="resetPwdBtn" href="javascript:void(0)" class="easyui-linkbutton">重置登录密码</a>
				</@authCheck>
				
				<@authCheck funcCode='1001010009'>
					<a id="resetGaCodeBtn" href="javascript:void(0)" class="easyui-linkbutton">重置谷歌验证码</a>
				</@authCheck>
			
			</div>
			<table id="qryResultTbl"></table>
		</div>
		
			<div id="pwdDiv" style="display:hidden;color:red; font-weight: bold;"></div>

			<div id="detailDiv"></div>
		</div>	
	</body>
<html>
