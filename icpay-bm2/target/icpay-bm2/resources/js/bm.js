function parseJson(data){
	// console.log('[parseJson] '+data);
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

/**
 * 检查是否空值，如果为空值则显示讯息。
 * @param obj
 * @param msg 空值时显示的讯息
 * @returns
 */
function checkEmpty(obj, msg){
	if (isEmpty(obj)) {
		if (isEmpty(msg)) msg = "错误！";
		alert(msg);
		return false;
	}
	return true;
}

;jQuery.extend({
	processAjaxResp: function(resp, succCallBack, failCallBack, errCallBack) {
		if ($.trim(resp) == "expired") {
			alert("会话已失效,请重新登录");
			window.top.location.href = logout_url;
		}
		var r = eval('(' + resp + ')');
		var code = r.respCode;
		if (code == "00") {
			if (typeof(succCallBack) == "function") {
				succCallBack(r);
			}
		} else if (code == "ZZ") {
			if (typeof(errCallBack) == "function") {
				errCallBack(r);
			} else {
				alert(r.respMsg);
			}
		} else {
			if (typeof(failCallBack) == "function") {
				failCallBack(r);
			} else {
				alert(r.respMsg);
			}
		}
	},
	jumpTo: function (url) {
		window.location.href=url;
	}
});
;$.fn.extend({
	monthSel : function() {
		var d = new Date();
		for (var i = 0; i < 13; i ++) {
			var mon = d.getMonth() + 1;
			if (mon < 10) {
				mon = "0" + mon;
			}
			var st = d.getFullYear() + "" + mon;
			if (i == 0) {
				$(this).append("<option value='" + st + "' selected='selected'>" + st + "</option>");
			} else {
				$(this).append("<option value='" + st + "'>" + st + "</option>");
			}
			d.setMonth(d.getMonth() - 1);
		}
	},
	clearOptions : function(removeFirstOption){
		if (removeFirstOption)
			$(this).children('option').remove();
		else
			$(this).children('option:not(:first)').remove();
	},
	addOption : function(val,desc, selected){
		if (selected)
			$(this).append($("<option></option>").attr("value",val).attr('selected', true).text(desc));
		else
			$(this).append($("<option></option>").attr("value",val).text(desc));
	},
	selectOption : function(val){
		$(this).val(val).change();
	},
	disable : function(){
		try {
			$(this).attr("disabled", "disabled");
			$(this).prop("disabled",true);
			$(this).linkbutton('disable');
		}
		catch(err) {}
	},
	enable : function(){
		try {
			$(this).removeAttr("disabled");
			$(this).prop("disabled",false);
			$(this).linkbutton('enable');
		}
		catch(err) {}
	},
	once : function(handler){
		$(this).disable();
		try {
			if (typeof(handler) == "function") {
				handler();
			}
		}
		finally {
			$(this).enable();
		}
	}

});
$(function() {
	$("a.easyui-linkbutton").bind("focus", function() {
		$(this).blur();
	}).addClass("c2");
	$('.validatebox-text').bind('blur', function(){
		$(this).validatebox('enableValidation').validatebox('validate');
	});
});

/**
 * 初始化查询功能
 * @param qryDivId 查询表单所在的DIV
 * @param resultDivId 查询结果所在的DIV
 * @param gridId 查询结果列表
 * @param qryChecker 查询前对查询条件进行校验
 * @return
 */
function initQry(qryFmId, resultDivId, gridId, pagination, singleSel, qryChecker, callBack) {
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
			var j = eval('(' + data + ')');
			if (j.respCode == "00") {
				resultDiv.panel("open");
				showQryResult(qryFmId, gridId, j.respData.pagerResult, pagination, singleSel);
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
function showQryResult(qryFmId, gridId, pagerResult, pagination, singleSel) {
	if (pagination == null || pagination == "undefined") {
		pagination = true;
	}
	if (singleSel == null || singleSel == "undefined") {
		singleSel = true;
	}
	var grd = $("#" + gridId);
	var qryFm = $("#" + qryFmId);
	if (pagination) {
		grd.datagrid({
			//height: 380,
			singleSelect: singleSel,
			//fitColumns: true,
			//collapsible: true,
			//fit: true,
			//view: scrollview,
			checkOnSelect: true,
			selectOnCheck: true,
			pageNumber: pagerResult.pageNum,
			pageSize: pagerResult.pageSize,
			pageList: [10,20,50,100,150],
			loadMsg: "查询中...",
			columns:[
			         pagerResult.columnLst
			],
			data: pagerResult.resultList,
			pagination: true,
			rowStyler:function(index,row){
				if ((row!=null) &&(row.rowStyler!=null)){
					//return 'background-color:pink;color:blue;font-weight:bold;';
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


//查询
function queryPage(qryForm, pageno) {
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

function goToPage(url) {
	window.location.href=url;
}

var dealFlag = false;		//正在处理请求标识
/**
 * 处理后台处理结果
 * @param data 处理结果JSON字符串
 * @param desc 操作描述
 * @param callBk 回调函数
 * @return
 */
function dealResult(data, desc, callBk) {
	dealFlag = false;	//重置处理标识
	if ($.trim(data) == "expired") {
		alert("会话已失效,请重新登录");
		window.top.location.href = logout_url;
	} else {
		var r = eval('(' + data + ')');
		var code = r.respCode;
		if (code == "00") {
			if (typeof(succCallBack) == "function") {
				succCallBack(r.respData);
			}
		} else if (code == "ZZ") {
			if (typeof(errCallBack) == "function") {
				errCallBack(r.respData);
			} else {
				alert(r.respMsg);
			}
		} else {
			if (typeof(failCallBack) == "function") {
				failCallBack(r.respData);
			} else {
				alert(r.respMsg);
			}
		}
	}
}

/**
 * AJAX方式检查会话是否已失效，如果未失效，则执行callBack函数
 * @param href
 * @param callBack
 * @return
 */
function ajaxCheckExpired(href, callBack) {
//會話檢察這裡有做(SessionCheckFilter)
	if (typeof(callBack) == "function") {
		if (callBack()) {
			d.dialog("close");
		}
	}
}

/**
 * 将一个DIV创建为一个弹出层，包含两个按钮，确认/取消
 * @param divId DIV的ID
 * @param confCalbk 点击"确认"按钮后的回调函数
 * @return
 */
function crtDialog(divId, title, href, confCalbk) {
	var d = $("#" + divId);
	var t = d.attr("title");
	if ($.trim(t) == "") {
		t = title;
	}
	ajaxCheckExpired(href, function() {
		d.dialog({
			title: t,
			width: 300,
			height: 150,
			top: 10,
			closed: false,
			cache: false,
			modal: true,
			inline: false,
			href: href,
			buttons:[{
				text:"确认",
				handler:function() {
					if (typeof(confCalbk) == "function") {
						if (confCalbk()) {
							d.dialog("close");
						}
					}
				}
			},{
				text:"取消",
				handler:function() {
					d.dialog("close");
				}
			}]
		});
	});
}

/**
 * 建立内嵌式对话框
 * @param divId
 * @param title
 * @param href
 * @param width
 * @param length
 * @param top
 * @returns
 */
function crtViewDialog(divId, title, href, width, length, top) {
	var d = $("#" + divId);
	var t = d.attr("title");
	if ($.trim(t) == "") {
		t = title;
	}
	var w = 650;
	if (width != undefined) {
		w = width;
	}
	var l = 300;
	if (length != undefined) {
		l = length;
	}
	var tp = 10;
	if (top != undefined) {
		tp = top;
	}
	ajaxCheckExpired(href, function() {
		d.dialog({
			title: t,
			width: w,
			height: l,
			top: tp,
			closed: false,
			cache: false,
			modal: true,
			inline: false,
			href: href,
			buttons:[{
				text:"关闭",
				handler:function() {
					d.dialog("close");
				}
			}]
		});
	});
}

function clearFm(fmId) {
	$("#" + fmId).find("input[type='text']").val("");
	$("#" + fmId).find("select").each(function() {
		$(this).children("option").eq(0).attr("selected", "selected");
	});
}


/**
 * linkbutton方法扩展
 * @param {Object} jq
 */
$.extend($.fn.linkbutton.methods, {
    /**
     * 激活选项（覆盖重写）
     * @param {Object} jq
     */
    enable: function(jq){
        return jq.each(function(){
            var state = $.data(this, 'linkbutton');
            if ($(this).hasClass('l-btn-disabled')) {
                var itemData = state._eventsStore;
                //恢复超链接
                if (itemData.href) {
                    $(this).attr("href", itemData.href);
                }
                //回复点击事件
                if (itemData.onclicks) {
                    for (var j = 0; j < itemData.onclicks.length; j++) {
                        $(this).bind('click', itemData.onclicks[j]);
                    }
                }
                //设置target为null，清空存储的事件处理程序
                itemData.target = null;
                itemData.onclicks = [];
                $(this).removeClass('l-btn-disabled');
            }
        });
    },
    /**
     * 禁用选项（覆盖重写）
     * @param {Object} jq
     */
    disable: function(jq){
        return jq.each(function(){
            var state = $.data(this, 'linkbutton');
            if (!state._eventsStore)
                state._eventsStore = {};
            if (!$(this).hasClass('l-btn-disabled')) {
                var eventsStore = {};
                eventsStore.target = this;
                eventsStore.onclicks = [];
                //处理超链接
                var strHref = $(this).attr("href");
                if (strHref) {
                    eventsStore.href = strHref;
                    $(this).attr("href", "javascript:void(0)");
                }
                //处理直接耦合绑定到onclick属性上的事件
                var onclickStr = $(this).attr("onclick");
                if (onclickStr && onclickStr != "") {
                    eventsStore.onclicks[eventsStore.onclicks.length] = new Function(onclickStr);
                    $(this).attr("onclick", "");
                }
                //处理使用jquery绑定的事件
                var eventDatas = $(this).data("events") || $._data(this, 'events');
                if (eventDatas["click"]) {
                    var eventData = eventDatas["click"];
                    for (var i = 0; i < eventData.length; i++) {
                        if (eventData[i].namespace != "menu") {
                            eventsStore.onclicks[eventsStore.onclicks.length] = eventData[i]["handler"];
                            $(this).unbind('click', eventData[i]["handler"]);
                            i--;
                        }
                    }
                }
                state._eventsStore = eventsStore;
                $(this).addClass('l-btn-disabled');
            }
        });
    }
});



