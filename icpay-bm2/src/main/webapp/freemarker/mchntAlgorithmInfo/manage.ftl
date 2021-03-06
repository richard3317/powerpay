<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="商户计费方式信息" base="algorithm" qryFuncCode="1000120001"
			addFlg=true addFuncCode="1000120002"
			editFlg=true editUrl='"edit.do?mchntCd=" + sel.mchntCd + "&intTransCd=" + sel.intTransCd+"&currCd="+sel.currCd' editFuncCode="1000120004" deleteFuncCode="1000120006"
			deleteFlg=true deleteUrl='"delete.do?mchntCd=" + sel.mchntCd + "&intTransCd=" + sel.intTransCd+"&currCd="+sel.currCd' deleteSuccMsg="商户计费方式信息已删除">
			
			$("#batchUpdRate").click(function(){
				showBg();
				$("#h3BatchUpd").hide();
				$("#h3BatchUpdRate").show();
				$("#h3BatchUpdParam").hide();
				$("#h3BatchUpdPercent").hide();
				$("#tblBatchUpdRate").show();
				$("#tblBatchUpdParam").hide();
				$("#tblBatchUpdPercent").hide();
				$("#trBatchUpd").hide();
				$("#trBatchUpdRate").show();
				$("#trBatchUpdParam").hide();
				$("#trBatchUpdPercent").hide();
			});
			
			$("#batchUpdParam").click(function(){
				showBg();
				$("#h3BatchUpd").hide();
				$("#h3BatchUpdRate").hide();
				$("#h3BatchUpdParam").show();
				$("#h3BatchUpdPercent").hide();
				$("#tblBatchUpdRate").hide();
				$("#tblBatchUpdParam").show();
				$("#tblBatchUpdPercent").hide();
				$("#trBatchUpd").hide();
				$("#trBatchUpdRate").hide();
				$("#trBatchUpdParam").show();
				$("#trBatchUpdPercent").hide();
			});
			
			$("#batchUpdPercent").click(function(){
				showBg();
				$("#h3BatchUpd").hide();
				$("#h3BatchUpdRate").hide();
				$("#h3BatchUpdParam").hide();
				$("#h3BatchUpdPercent").show();
				$("#tblBatchUpdRate").hide();
				$("#tblBatchUpdParam").hide();
				$("#tblBatchUpdPercent").show();
				$("#trBatchUpd").hide();
				$("#trBatchUpdRate").hide();
				$("#trBatchUpdParam").hide();
				$("#trBatchUpdPercent").show();
			});
			
			function batchFixRate(newFixRate,newMaxfee,newMinfee,currCd){
				$.ajax({
					type : "POST",
					url : _ctx + "/algorithm/batchFixRate.do?newFixRate=" + newFixRate + "&newMaxfee=" + newMaxfee 
						+ "&newMinfee=" + newMinfee+ "&currCd=" + currCd,
					async: true,
					cache: false,
					data: $("#qryForm").serialize(),
					success : function(data) {
						var res = parseJson(data);
						if (!checkEmpty(res, "系统异常，请联系管理员！")) return false;
						if (res.respCode == "00"){
							alert(res.respMsg);
						}else{
							alert("操作失败: "+res.respMsg);
	            		}
	            		clear();
						queryPage();
					},
					error:function(){
				    	alert("系统异常，请联系管理员！");
				    	clear();
				    	return  false;
				    }
				});
			}
			
			function batchFixRateParam(newTxDayMax,newTxAmtMin,newTxAmtMax,newTxCardDayMax,txTimeLimitInpt,currCd){
				$.ajax({
					type : "POST",
					url : _ctx + "/algorithm/batchFixRate.do?newTxDayMax=" + newTxDayMax + "&newTxAmtMin=" + newTxAmtMin 
						+ "&newTxAmtMax=" + newTxAmtMax + "&newTxCardDayMax=" + newTxCardDayMax
						+ "&txTimeLimitInpt=" + txTimeLimitInpt+ "&currCd=" + currCd,
					async: true,
					cache: false,
					data: $("#qryForm").serialize(),
					success : function(data) {
						var res = parseJson(data);
						if (!checkEmpty(res, "系统异常，请联系管理员！")) return false;
						if (res.respCode == "00"){
							alert(res.respMsg);
						}else{
							alert("操作失败: "+res.respMsg);
	            		}
	            		clear();
						queryPage();
					},
					error:function(){
				    	alert("系统异常，请联系管理员！");
				    	clear();
				    	return  false;
				    }
				});
			}
			
			function batchFixRatePercent(newTxT0Percent,comment,currCd){
				$.ajax({
					type : "POST",
					url : _ctx + "/algorithm/batchFixRate.do?&newTxT0Percent=" + newTxT0Percent + "&comment=" + comment+ "&currCd=" + currCd,
					async: true,
					cache: false,
					data: $("#qryForm").serialize(),
					success : function(data) {
						var res = parseJson(data);
						if (!checkEmpty(res, "系统异常，请联系管理员！")) return false;
						if (res.respCode == "00"){
							alert(res.respMsg);
						}else{
							alert("操作失败: "+res.respMsg);
	            		}
	            		clear();
						queryPage();
					},
					error:function(){
				    	alert("系统异常，请联系管理员！");
				    	clear();
				    	return  false;
				    }
				});
			}
			
			function showBg() {
	            var bh = $("body").height();
	            var bw = $("body").width();
	            $("#fullbg").css({
	                height: bh,
	                width: bw, 
	                display: "block"
	            });
	            $("#dialog").show();
	        }
	        
	        $("#closeBtn").click(function(){
	       		clear();
	        });
	        
			$("#submitBtnRate").click(function(){
	        	var newFixRate = $('#newFixRate').val();
	        	var newMaxfee = $('#newMaxfee').val();
	        	var newMinfee = $('#newMinfee').val();
	        	var currCd = $('#currCd').val();
	        	if(newFixRate == null ||  newFixRate =="") {
	        		alert("请输入扣率！");
	        		return false;
	        	}
	        	if(newMaxfee == null ||  newMaxfee =="") {
	        		alert("请输入封顶手续费！");
	        		return false;
	        	}
	        	if(newMinfee == null ||  newMinfee =="") {
	        		alert("请输入保底手续费！");
	        		return false;
	        	}
	        	var comment = $("#comment").val();
	        	
	        	batchFixRate(newFixRate,newMaxfee,newMinfee,currCd);
	        });
	        
	        $("#submitBtnParam").click(function(){
	        	var newTxDayMax = $('#newTxDayMax').val();
	        	var newTxAmtMin = $('#newTxAmtMin').val();
	        	var newTxAmtMax = $('#newTxAmtMax').val();
	        	var newTxCardDayMax = $('#newTxCardDayMax').val();
	        	var txTimeLimitInpt = $('#txTimeLimitInpt').val();
	        	var currCd = $('#currCd').val();
	        	if(newTxAmtMin == null ||  newTxAmtMin =="") {
	        		alert("请输入交易单笔下限！");
	        		return false;
	        	}
	        	if(newTxAmtMax == null ||  newTxAmtMax =="") {
	        		alert("请输入交易单笔上限！");
	        		return false;
	        	}
	        	var comment = $("#comment").val();
	        	
	        	batchFixRateParam(newTxDayMax,newTxAmtMin,newTxAmtMax,newTxCardDayMax,txTimeLimitInpt,currCd);
	        });
	        
	        $("#submitBtnPercent").click(function(){
	        	var newTxT0Percent = $('#newTxT0Percent').val();
	        	var currCd = $('#currCd').val();
	        	if(newTxT0Percent == null ||  newTxT0Percent =="") {
	        		alert("请输入垫资比例！");
	        		return false;
	        	}
	        	       	
	        	if(newTxT0Percent > 1) {
	        		alert("代付垫资比例不可超过1！");
	        		return false;
	        	}
	        	var comment = $("#comment").val();
	        	
	        	batchFixRatePercent(newTxT0Percent,comment,currCd);
	        });
			
			function clear(){
				$("#fullbg").hide();
	            $("#dialog").hide();
	            $('#newFixRate').val('');
	        	$('#newMaxfee').val('');
	        	$('#newMinfee').val('');
	        	$('#newTxDayMax').val('');
	        	$('#newTxAmtMin').val('');
	        	$('#newTxAmtMax').val('');
	        	$('#newTxCardDayMax').val('');
	        	$('#newTxT0Percent').val('');
	        	$('#comment').val('');
			}
			
		$("#transChnlSel").change(function() {
				var chnlId = $(this).val();
				if (chnlId == '') {
					$("#chnlMchntCd").html("").append('<option value="">--请选择--</option>');
				} else {
					var url =  _ctx + "/algorithm/getChnlMchnts.do?chnlId="+chnlId;
					$.get(url, function(data) {
						$.processAjaxResp(data, function(r) {
							var lst = r.respData.chnlMchntList;
							$("#chnlMchntCd").html("").append('<option value="">--请选择--</option>');
							for (var i = 0; i < lst.length; i ++) {
								var chnlMchntCd = lst[i].chnlMchntCd;
								var chnlMchntDesc = lst[i].chnlMchntDesc;
								$("#chnlMchntCd").append('<option value="' + chnlMchntCd + '">' + chnlMchntDesc + '|' + chnlMchntCd + '</option>');
							}
						});
					});
				}
			});
			
			
			
			
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/algorithm/qry.do" qryFuncCode="1000120001">
			<table class="qry_tbl">
				<tr>
					<td>商户号：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="mchntCdInpt" maxLength="15"
							name="_QRY_mchntCd" value="${(qryParamMap.mchntCd)!''}">
					</td>
					<td>商户名称：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="mchntCnNmInpt" maxLength="15"
							name="_QRY_mchntCnNm" value="${(qryParamMap.mchntCnNm)!''}">
					</td>
					<td>交易类型：</td>
					<td>
						<select id="intTransCdSel" name="_QRY_intTransCd" value="${(qryParamMap.intTransCd)!''}" >
							<!-- <@enumOpts enumNm="BmEnums.TxnTypesForSel" selected="01" exceptValues="52" showEmptyOpt="false" showCode="true" /> -->
							<@enumOpts enumNm="ProfitEnums.ProfitTxnTp" showEmptyOpt="true" showCode="true" />
						</select>
					</td>
					<td>费率：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="fixRateInpt" maxLength="15"
							name="_QRY_fixRate" value="${(qryParamMap.fixRate)!''}">
					</td>
				</tr>
				<tr>
					<td>保底：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="minFeeInpt" maxLength="15"
							name="_QRY_minFee" value="${(qryParamMap.minFee)!''}">
					</td>
					<td>交易单笔下限：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="txAmtMinInpt" maxLength="15"
							name="_QRY_txAmtMin" value="${(qryParamMap.txAmtMin)!''}">
					</td>
					<td>交易单笔上限：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="txAmtMaxInpt" maxLength="15"
							name="_QRY_txAmtMax" value="${(qryParamMap.txAmtMax)!''}">
					</td>
					<td>垫资比例：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="txT0PercentInpt" maxLength="15"
							name="_QRY_txT0Percent" value="${(qryParamMap.txT0Percent)!''}">
					</td>
				</tr>
				<tr>
					<td>已配置路由渠道：</td>
					<td>
						<select id="transChnlSel" name="_QRY_transChnl">
							<@enumOpts enumNm="TxnEnums.ChnlId" showCode="true" showEmptyOpt="true" />
						</select>
					</td>
					<td>渠道商户号：</td>
					<td>
						<select id="chnlMchntCd" name="_QRY_chnlMchntCd" style="width: 300px;">
							<option value="">--请选择--</option>
						</select>
					</td>
					<td>自站关键字：</td>
					<td>
						<select id="keyWordType" name="_QRY_keyWordType">
							<option value="">--请选择--</option>
							<option value="0">包含關鍵字</option>
							<option value="1">排除關鍵字</option>
						</select>
					</td>
					<td>币别:</td>
					<td>
						<select  name="_QRY_currCd" id="currCd" >
								<@enumOpts enumNm="CurrencyEnums.CurrType" showEmptyOpt="false" showCode="true" selected="${(qryParamMap.currCd)!'156'}"/>
						</select>
					</td>
				</tr>
			</table>
		</@qryDiv> 
		
		<@qryResultDiv  
			addFlg=true addFuncCode="1000120002"
			editFlg=true editFuncCode="1000120004"
			 deleteFlg=true deleteFuncCode="1000120006">
			 
			 <@authCheck funcCode='1000120007'>
			 <a id="batchUpdRate" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" >批量修改费率</a>
			 <a id="batchUpdParam" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" >批量修改限额</a>
			 <a id="batchUpdPercent" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" >批量修改垫资比例</a>
			 </@authCheck>
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
    	
    	<div id="fullbg"></div>  
        <div id="dialog">  
            <p class="close"><a href="javascript:void(0)" id="closeBtn">关闭</a></p>
            <h3 id="h3BatchUpdRate">批量修改费率</h3>
            <h3 id="h3BatchUpdParam">批量修改限额</h3>
            <h3 id="h3BatchUpdPercent">批量修改垫资比例</h3>
            <h6>符合目前查询条件的纪录都将被修改 ,请确认后输入 </h6>
            <table class="edit_tbl" cellpadding="0" cellspacing="0" id="tblBatchUpdRate">
				<tr>
					<td class="label" style="width: 135px;">计费方式：</td>
					<td class="content" id="settleModeTr">
						<div id="settleMode2">
							<ul class="input_ele">
								<li>
									<span class="input_lbl">费率：</span>
									<input class="easyui-validatebox" type="text" maxlength="8" 
										id="newFixRate" name="newFixRate" style="width: 100px;" />
									<span class="inputDesc">必填，请输入小数，小数点后最多5位,如:0.12345</span>
								</li>
								<li>
									<span class="input_lbl">保底手续费：</span>
									<input class="easyui-validatebox" type="text" maxlength="20" 
										id="newMinfee" name="newMinfee" style="width: 100px;" />(元)
									<span class="inputDesc">选填，请输入金额，如:1.00</span>
								</li>
								<li>
									<span class="input_lbl">封顶手续费：</span>
									<input class="easyui-validatebox" type="text" maxlength="20" 
										id="newMaxfee" name="newMaxfee" style="width: 100px;" />(元)
									<span class="inputDesc">选填，请输入金额，如:1.00</span>
								</li>
							</ul>
						</div>
					</td>
				</tr>
				<tr id="trBatchUpdRate">
					<td style="margin: 10px; text-align: center;">
        				<a id="submitBtnRate" href="javascript:void(0)" class="easyui-linkbutton" style="margin-right: 20px;">确 定</a>
        			</td>
        		</tr>
			</table>
			<table class="edit_tbl" cellpadding="0" cellspacing="0" id="tblBatchUpdParam">
				<tr>
					<td class="label">交易日限额(元)：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="15"
							id="newTxDayMax" name="newTxDayMax"  />
						<span class="inputDesc">如果不填写，则默认当日累计交易限额为999999999999.99元(支付交易)</span>
					</td>
				</tr>
				<tr>
					<td class="label">交易单笔下限(元)：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="15"
							id="newTxAmtMin" name="newTxAmtMin"  />
					</td>
				</tr>
				<tr>
					<td class="label">交易单笔上限(元)：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="20"
							id="newTxAmtMax" name="newTxAmtMax" />
					</td>
				</tr>
				<tr>
					<td class="label">单日单卡限额(元)：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="20"
							id="newTxCardDayMax" name="newTxCardDayMax" />
						<span class="inputDesc">如果不填写，则默认当日累计交易限额为999999999999.99元</span>
					</td>
				</tr>
				<tr>
					<td class="label">交易支持的时段：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="20"
							id="txTimeLimitInpt" name="txTimeLimit" />
						<span class="inputDesc">例："ALL"或"0800-1200,1400-2000"</span>
					</td>
				</tr>
				<tr id="trBatchUpdParam">
					<td style="margin: 10px; text-align: center;">
        				<a id="submitBtnParam" href="javascript:void(0)" class="easyui-linkbutton" style="margin-right: 20px;">确 定</a>
        			</td>
        		</tr>
			</table>
			<table class="edit_tbl" cellpadding="0" cellspacing="0" id="tblBatchUpdPercent">
				<tr>
					<td class="label">代付垫资比例：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="20"
							id="newTxT0Percent" name="newTxT0Percent" />
						<span class="inputDesc">如：0.05，最大值为1</span>
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 135px;">备注：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="256"
							id="comment" name="comment"  />
					</td>
				</tr>
				<tr id="trBatchUpdPercent">
					<td style="margin: 10px; text-align: center;">
        				<a id="submitBtnPercent" href="javascript:void(0)" class="easyui-linkbutton" style="margin-right: 20px;">确 定</a>
        			</td>
        		</tr>
        		<tr id="trBatchUpd">
					<td style="margin: 10px; text-align: center;">
        				<a id="submitBtn" href="javascript:void(0)" class="easyui-linkbutton" style="margin-right: 20px;">确 定</a>
        			</td>
        		</tr>
			</table>
       </div>
	</body>
<html>