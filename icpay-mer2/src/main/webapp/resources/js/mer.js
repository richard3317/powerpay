;toastr.options = {
    "debug": false,
    "preventDuplicates": true,
    "newestOnTop": false,
    "positionClass": "toast-top-center",
    "closeButton": true,
    "toastClass": "fadeInDown",
};

(function ( $ ) {
	$.fn.extend({
		"monthSel" : function() {
			$(this).addClass("month_sel");
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
		"notifyTip" : function() {
			$(this).on("mouseover", function(e) {
				var notify = $(this).attr("notify");
				$('body').append('<div id="notifyTip">' + notify + '</div>');
				$('#notifyTip').css({
					"background": "#53585e",
					"color": "#fff",
					"display": "none",
					"font-family": "宋体",
					"font-size": "12px",
					"max-width": "200px",
					"position": "absolute",
					"top": "0",
					"line-height" : "20px",
					"width": "auto",
					"padding": "5px",
					"z-index" : "1001"
				});
				var left_pos = $(this).offset().left + $(this).width() + 10;
				var top_pos = $(this).offset().top - 5;
				$('#notifyTip').css({left:left_pos, top:top_pos});
				$('#notifyTip').fadeIn(200);
			}).on("mouseout", function(){
				$('#notifyTip').remove();
			});
		},
		"qryFrm" : function(resultTblId, paginationId, opts) {
			opts = $.extend({
				pageSize : 10,
				pageNum : 1,
				resultHandlers : null,
				qryChecker : function() { return true; }
	        }, opts||{});
			return this.each(function() {
				var isValid = opts.qryChecker();
				var frm = this;
				if (isValid) {
					$(frm).find("input[name='pageNum']").remove();
					$(frm).find("input[name='pageSize']").remove();
					$(frm).append("<input type='hidden' name='pageNum' value='" + opts.pageNum + "' />")
							.append("<input type='hidden' name='pageSize' value='" + opts.pageSize + "' />");
					$(frm).ajaxSubmit(function(resp) {
						$.processAjaxResp(resp,
							function(respData) {
								$("#" + paginationId).html("");
								var pager = respData.pagerResult;
								$("#" + resultTblId).jsonTable({
									cols : pager.columnLst,
									rows : pager.resultList,
									btns : opts.resultHandlers
								});
								if (pager.resultList.length > 0) {
									$("#" + paginationId).pagination(pager.total, {
										callback: changePage,
										items_per_page: pager.pageSize,
										current_page: (pager.pageNum - 1)
									});
								}
							}
						);
					});
				}
				function changePage(page_id) {
					opts = $.extend(opts, {pageNum : (page_id + 1)});
					$(frm).qryFrm(resultTblId, paginationId, opts);
				}
			});
		},
		"jsonTable" : function(options) {
			var settings = $.extend({
				"cols" : {},
				"rows" : {},
				"btns" : null
	        }, options, { table: this } );
	        table = this;
	        table.data("settings",settings).html("");
	        var cols = settings.cols;
	        if (table.find("thead").length == 0) {
	            table.append($("<thead></thead>").append("<tr></tr>"));
	        }
	        if (table.find("thead").find("tr").length == 0) {
	            table.find("thead").append("<tr></tr>");
	        }
	        if (table.find("tbody").length == 0) {
	            table.append($("<tbody></tbody>"));
	        }
	        var rowFields = new Array();
			var len = $(cols).length;
	        $(cols).each(function(i) {
	        	var colNm = cols[i].title;
	        	var width = cols[i].width;
	        	rowFields.push(cols[i].field);
	        	if (width && i != (len - 1)) {
	        		table.find("thead").find("tr").append("<th width='" + width + "'>"+colNm+"</th>");
	        	} else {
	        		table.find("thead").find("tr").append("<th>"+colNm+"</th>");
	        	}
	        });
			var btnNum = $(settings.btns).length;
			if (btnNum > 0) {
				var btnWidth = btnNum * 60;
				table.find("thead").find("tr").append("<th width='" + btnWidth + "'>"+i18nMsg.operation+"</th>");
			}
	        table.find("tbody").empty();
	        var rows = settings.rows;
			if (rows.length >= 1) {
				$(rows).each(function(i) {
		        	var tableRow = $("<tr></tr>");
		        	if (i % 2 != 0) {
		        		tableRow.addClass("odd");
		        	} else {
		        		tableRow.addClass("even");
		        	}
		            var row = rows[i];
		            for (var i = 0; i < rowFields.length; i ++) {
		            	var f = rowFields[i];
		            	if (row[f]) {
		            		tableRow.append($("<td>" + row[f] + "</td>"));
		            	} else {
		            		tableRow.append($("<td></td>"));
		            	}
			        }
					if (btnNum > 0) {
						var btnWidth = btnNum * 60;
						var btnsTd = $("<td class='td-btns'></td>");
						$(settings.btns).each(function() {
							var btnObj = this;
							var btn = $("<a href='#' class='tbl_op_btn tbl_op_btn_space'>" + this.btnTxt + "</a>");
							btn.click(function(event) {
								event.preventDefault();
								event.stopPropagation();
								btnObj.btnHandler(row);
								return false;
							});
							btnsTd.append(btn);
						});
						tableRow.append(btnsTd);
					}
		            table.append(tableRow);
		        });
			} else {
				var tableRow = $("<tr></tr>");
				var colspan = len;
				if (btnNum > 0) {
					colspan = len + 1;
				}
				tableRow.append($("<td align='center' valign='middle' class='td-nodata' colspan='" + colspan + "'>"+i18nMsg.noData+"</td>"));
				table.append(tableRow);
			}
	        return table;
		},
		"pagination" : function(maxentries, opts){
			opts = jQuery.extend({
				items_per_page:10,
				num_display_entries:10,
				current_page:0,
				num_edge_entries:0,
				link_to:"#",
				prev_text: i18nMsg.prePage,
				next_text: i18nMsg.nextPage,
				ellipse_text:"...",
				prev_show_always:true,
				next_show_always:true,
				callback:function(){return false;}
			},opts||{});
			return this.each(function() {
				function numPages() {
					return Math.ceil(maxentries/opts.items_per_page);
				}	
				function getInterval()  {
					var ne_half = Math.ceil(opts.num_display_entries/2);
					var np = numPages();
					var upper_limit = np-opts.num_display_entries;
					var start = current_page>ne_half?Math.max(Math.min(current_page-ne_half, upper_limit), 0):0;
					var end = current_page>ne_half?Math.min(current_page+ne_half, np):Math.min(opts.num_display_entries, np);
					return [start,end];
				}
				function pageSelected(page_id, evt){
					current_page = page_id;
					drawLinks();
					var continuePropagation = opts.callback(page_id, panel);
					if (!continuePropagation) {
						if (evt.stopPropagation) {
							evt.stopPropagation();
						}
						else {
							evt.cancelBubble = true;
						}
					}
					return continuePropagation;
				}
				function drawLinks() {
					panel.empty();
					var interval = getInterval();
					var np = numPages();
					var getClickHandler = function(page_id) {
						return function(evt){ return pageSelected(page_id,evt); }
					}
					var appendItem = function(page_id, appendopts){
						page_id = page_id<0?0:(page_id<np?page_id:np-1); // 规范page id值
						appendopts = jQuery.extend({text:page_id+1, classes:""}, appendopts||{});
						if(page_id == current_page){
							var lnk = jQuery("<span class='current'>"+(appendopts.text)+"</span>");
						}else{
							var lnk = jQuery("<a>"+(appendopts.text)+"</a>")
								.bind("click", getClickHandler(page_id))
								.attr('href', opts.link_to.replace(/__id__/,page_id));		
						}
						if(appendopts.classes){lnk.addClass(appendopts.classes);}
						panel.append(lnk);
					}
					if(opts.prev_text && (current_page > 0 || opts.prev_show_always)){
						appendItem(current_page-1,{text:opts.prev_text, classes:"prev"});
					}
					if (interval[0] > 0 && opts.num_edge_entries > 0)
					{
						var end = Math.min(opts.num_edge_entries, interval[0]);
						for(var i=0; i<end; i++) {
							appendItem(i);
						}
						if(opts.num_edge_entries < interval[0] && opts.ellipse_text)
						{
							jQuery("<span>"+opts.ellipse_text+"</span>").appendTo(panel);
						}
					}
					for(var i=interval[0]; i<interval[1]; i++) {
						appendItem(i);
					}
					if (interval[1] < np && opts.num_edge_entries > 0)
					{
						if(np-opts.num_edge_entries > interval[1]&& opts.ellipse_text)
						{
							jQuery("<span>"+opts.ellipse_text+"</span>").appendTo(panel);
						}
						var begin = Math.max(np-opts.num_edge_entries, interval[1]);
						for(var i=begin; i<np; i++) {
							appendItem(i);
						}
						
					}
					if(opts.next_text && (current_page < np-1 || opts.next_show_always)){
						appendItem(current_page+1,{text:opts.next_text, classes:"next"});
					}
				}
				var current_page = opts.current_page;
				maxentries = (!maxentries || maxentries < 0)?1:maxentries;
				opts.items_per_page = (!opts.items_per_page || opts.items_per_page < 0)?1:opts.items_per_page;
				var panel = jQuery(this);
				this.selectPage = function(page_id){ pageSelected(page_id);}
				this.prevPage = function(){ 
					if (current_page > 0) {
						pageSelected(current_page - 1);
						return true;
					}
					else {
						return false;
					}
				}
				this.nextPage = function(){ 
					if(current_page < numPages()-1) {
						pageSelected(current_page+1);
						return true;
					}
					else {
						return false;
					}
				}
				drawLinks();
			});
		},

		"clearOptions" : function(removeFirstOption){
			if (removeFirstOption)
				$(this).children('option').remove();
			else
				$(this).children('option:not(:first)').remove();
		},
		"addOption" : function(val,desc, selected){
			if (selected)
				$(this).append($("<option></option>").attr("value",val).attr('selected', true).text(desc));
			else
				$(this).append($("<option></option>").attr("value",val).text(desc));
		},
		"selectOption" : function(val){
			$(this).val(val).change();
		},
		"disable" : function(){
			try {
				$(this).attr("disabled", "disabled");
				$(this).prop("disabled",true);
				$(this).linkbutton('disable');
			}
			catch(err) {}
		},
		"enable" : function(){
			try {
				$(this).removeAttr("disabled");
				$(this).prop("disabled",false);
				$(this).linkbutton('enable');
			}
			catch(err) {}
		},
		"once" : function(handler){
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
	$.extend({
		"processAjaxResp" : function(resp, succCallBack, failCallBack, errCallBack) {
			if (resp == "expired") {
				window.location.href=_ctx + "/logout";
			}
			var j = $.parseJSON(resp);
			var code = j.respCode;
			if (code == "00") {
				if (typeof(succCallBack) == "function") {
					succCallBack(j.respData);
				}
			} else if (code == "99") {
				if (typeof(errCallBack) == "function") {
					errCallBack(j);
				} else {
					$.showMsg(j.respMsg, "error");
				}
			} else {
				if (typeof(failCallBack) == "function") {
					failCallBack(j);
				} else {
					$.showMsg(j.respMsg, "error");
				}
			}
		},
		"jumpTo" : function (url) {
			window.location.href=url;
		},
		"showMsg" : function (msg, flg) {
			$.hideMsg();
			if (flg == "succ") {
				toastr.info(msg);
			} else {
				toastr.error(msg);
			}
		},
		"hideMsg" : function () {
			toastr.clear();
		},
		"loadData" : function(url, dealData, mtd, params) {
			if ("post" == mtd) {
				$.ajax({
					type: "post",
					data: params,
					url: url,
					success: function(resp) {
						alert(resp);
						$.processAjaxResp(resp, function(respData) {
							dealData(respData);
						});
					}
				});
			} else {
				$.ajax({
					type: "get",
					url: url,
					success: function(resp) {
						$.processAjaxResp(resp, function(respData) {
							dealData(respData);
						});
					}
				});
			}
		},
		"getLength" : function(value) {
			//获取值长度，（一个双字节字符长度计(包括汉字在内)：2，ASCII字符计1）
			var cnreg = new RegExp(/[^\x00-\xff]/g);
			return value.replace(cnreg,"**").length;
		},
		"getSpcLength" : function(value, replaceReg) {
			//获取值长度，（一个双字节字符长度计(包括汉字在内)： replaceReg长度，ASCII字符计1）
			var cnreg = new RegExp(/[^\x00-\xff]/g);
			return value.replace(cnreg,replaceReg).length;
		},
	});
	
	// JS校验框架定制
	jQuery.extend(jQuery.validator.messages, {
	    required: "必填",
		remote: "请修正该字段",
		email: "请输入正确格式的电子邮件，如:example@test.com",
		url: "请输入合法的网址，如:http://www.example.com",
		date: "请输入合法的日期",
		dateISO: "请输入合法的日期 (ISO).",
		number: "请输入数字",
		digits: "只能输入整数",
		creditcard: "请输入合法的信用卡号",
		equalTo: "请再次输入相同的值",
		accept: "请输入拥有合法后缀名的字符串",
		maxlength: jQuery.validator.format("请输入一个 长度最多是 {0} 的字符串"),
		minlength: jQuery.validator.format("请输入一个 长度最少是 {0} 的字符串"),
		rangelength: jQuery.validator.format("请输入 一个长度介于 {0} 和 {1} 之间的字符串"),
		range: jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
		max: jQuery.validator.format("请输入一个最大为{0} 的值"),
		min: jQuery.validator.format("请输入一个最小为{0} 的值")
	});
	$.validator.setDefaults({
	    submitHandler: function(form) {
	        $(form).find("input:text").each(function() {
				$(this).val($.trim($(this).val()));
			});
	        form.submit();
	    },
		errorPlacement: function(error, element) {
			element.next("div.input_error").append(error);
		}
	});
	jQuery.validator.addMethod("requiredInput", function( value, element ) {
		return $.trim(value).length > 0;
	}, "请输入${0}.");
	jQuery.validator.addMethod("passwd", function( value, element ) {
		return this.optional(element) || value.length >= 6 && /\d/.test(value) && /[a-z]/i.test(value);
	}, "密码长度最小为6个字符，且至少包含一个数字和一个字符.");
	jQuery.validator.addMethod("zipcode", function( value, element ) {
		var reg = /^[0-9]{6}$/;
		return this.optional(element) || (reg.test(value));
	}, "请输入6位数字");
	jQuery.validator.addMethod("cnWord", function(value, element) { 
	    return this.optional(element) || /^[\u4E00-\u9FA5]*$/i.test(value);  
	}, jQuery.validator.format("只能输入中文"));
	jQuery.validator.addMethod("cnMaxLen", function(value, element, length) {
		return this.optional(element) || $.getLength(value) <= length;
	}, jQuery.validator.format("长度不能超过{0}位，一个中文字符算2位."));
	jQuery.validator.addMethod("cnMinLen", function(value, element, length) {
		return this.optional(element) || $.getLength(value) >= length;
	}, jQuery.validator.format("长度不能少于{0}位，一个中文字符算2位."));
	jQuery.validator.addMethod("cnFixLen", function(value, element, length) {
		return this.optional(element) || $.getLength(value) == length;
	}, jQuery.validator.format("输入内容长度必须为{0}位，一个中文字符算2位."));
	jQuery.validator.addMethod("text", function(value, element) { 
	  return this.optional(element) || /^([\x20-\x7e]|[\u4E00-\u9FA5]|[\uFF00-\uFFFF])*$/i.test(value);
	}, "请输入字母、数字、空格、符号或中文");
	jQuery.validator.addMethod("notCnFixLen", function(value, element, length) {
		return $.getLength(value) == length && !/^[\u4E00-\u9FA5]*$/i.test(value);
	}, jQuery.validator.format("输入内容长度必须为{0}位，不能输入中文."));
	jQuery.validator.addMethod("usrId", function(value, element, length) {
		return $.getLength(value) <= length && /^[A-Za-z]+[A-Za-z0-9|_]*$/.test(value);
	}, jQuery.validator.format("只能输入英文字符、数字、下划线，必须以英文字符开头，不能超过{0}位."));
	jQuery.validator.addMethod("numberFixLen", function(value, element, length) {
		return $.getLength(value) == length && /\d/.test(value);
	}, jQuery.validator.format("输入长度为{0}位的数字."));
	jQuery.validator.addMethod("phoneNo", function(value, element) {
		return $.getLength(value) == 11 && /^1[1-9][0-9]+\d{8}$/.test(value);
	}, jQuery.validator.format("请输入合法的手机号码"));
	jQuery.validator.addMethod("amt", function(value, element) {
		return /(^([0-9]\d{0,9}|0)\.\d{1,2}$)|(^[1-9]\d{0,9}$)/.test(value);
	}, jQuery.validator.format("请输入格式正确的金额字符串"));
	jQuery.validator.addMethod("amtMax", function(value, element, max) {
		var v = parseFloat(value).toFixed(2);
		var m = parseFloat(max).toFixed(2);
		return parseInt(v - m) <= 0;
	}, jQuery.validator.format("金额不能超过最大值{0}"));
	jQuery.validator.addMethod("amtMin", function(value, element, min) {
		var v = parseFloat(value).toFixed(2);
		var m = parseFloat(min).toFixed(2);
		return parseInt(v - m) >= 0;
	}, jQuery.validator.format("金额不能低于最小值{0}"));
}( jQuery ));