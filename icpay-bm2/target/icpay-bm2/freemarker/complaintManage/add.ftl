<#include "/common/add_macro.ftl" />
<#assign label = "投诉事件">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<@head label="${label}" base="complaintManage">
    $("a[name='addBtn']").click(function(){
        var value=$("#addTr").val();

        $("input[name='terraceTransId']").each(function(key,value){
              if(key>0&&($(value).val()==null ||$(value).val()=="")){
                    $("#addTr").val("1");
                }
        });

        if(value=='1'){
            alert('请先增加上一条');
        }else{
            <#--在原有的基础上，在tr下面再加一行-->
            var hideTr = $("#hide_tbody").children().first();
            var newTr = hideTr.clone().show();
            $("#show_tbody").append(newTr);
            $("#addTr").val("1");
        }
    });

    $(document).on("click","a[name='queryMchnt']",function(){
        var trObj=$(this).parent().parent().parent();
        var terraceTransId=$(this).parent().children("#terraceTransId").val();
        if(terraceTransId==null || ''==terraceTransId){
            alert("请输入要查询的值");
            return false;
        }
        $.ajax({
            url:baseUrl+"/checkTerraceTransId.do",
            data:{"terraceTransId":terraceTransId},
            type:"post",
            success:function(data){
                if(data==null || data.trim()==''){
                    alert("查无此订单号");
                }else{
                    var obj=eval('('+data+')');
                    if(obj==null){
                    	alert("查无此订单号");
                    	return false;
                    }
                    trObj.children(".1").html('<input type="text" readonly="readonly" name="terraceTransId" id="terraceTransId" value=" '+obj.chnlOrderId+'"/>');
                    trObj.children(".2").html('<input type="text" readonly="readonly" name="mchntTransId" id="mchntTransId"  value=" '+obj.orderId+'"/>');
                    trObj.children(".3").html('<input type="text" readonly="readonly" name="dealMoney" id="dealMoney"  value=" '+obj.transAt+'"/>');
                    trObj.children(".4").html('<input type="text" readonly="readonly" name="complaintCard" id="complaintCard"  value=" '+obj.accNo+'"/>');
                    trObj.children(".5").html('<input type="text" readonly="readonly" name="bankName" id="bankName"  value=" '+obj.bankName+'"/>');
                    trObj.children(".6").html('<input type="text" readonly="readonly" name="dealType" id="dealType"  value=" '+obj.intTransCd+'"/>');
                    trObj.children(".7").html('<input type="text" readonly="readonly" name="dealDt" id="dealDt"  value=" '+obj.extTransDt+'"/>');
                    trObj.children(".8").html('<input type="text" readonly="readonly" name="dealTm" id="dealTm" value=" '+obj.extTransTm+'"/>');
                    trObj.children(".9").html('<input type="text" readonly="readonly" name="mchntCd" id="mchntcd"  value=" '+obj.mchntCd+'"/>');
                    trObj.children(".10").html('<input type="text" readonly="readonly" name="mchntCnNm" id="mchntCnNm"  value=" '+obj.mchntCnAbbr+'"/>');
                    trObj.children(".11").html('<input type="text" readonly="readonly" name="chnlMchntCd" id="chnlMchntCd" value=" '+obj.chnlMchntCd+'"/>');
                    trObj.children(".12").html('<input type="text" readonly="readonly" name="chnlMchntDesc" id="chnlMchntDesc" value=" '+obj.chnlMchntDesc+'"/>');
                    $("#sumMoney").html(parseFloat($("#sumMoney").html())+parseFloat(obj.transAt));
                <#--$("#sumReturnMoney").html(parseFloat($("#sumReturnMoney").html())+parseFloat(obj.transAt));-->
                    $("#addTr").val("0");

                <#-- 再给隐藏域赋值 -->
                }
            }
        });
    });


<#--关联商户号-->
    $("#checkMchnt").click(function(){
        if($("#checkMchntValue").val()==null || $("#checkMchntValue").val()==""){
            alert("请输入商户号"+$("#checkMchntValue").val());
            return false;
        }

        if($("#hidden_mchnt").val().indexOf($("#checkMchntValue").val())>-1){
            alert('该商户号已选择，请重输');
            return false;
        }
        $("#hidden_mchnt").val($("#hidden_mchnt").val()+","+$("#checkMchntValue").val());
        $.ajax({
        url:"${ctx}/announcementManagement/addQuery.do",
        data:{"contentMchntId":$("#checkMchntValue").val()},
        type:"post",
        success:function(date){
        <#--将错误信息显示在界面上，并标明颜色-->
            if(date!=null && date.trim() !=''){
                alert(date.trim()+"商户不存在，请重新输入");
            }else{
                if($("#contentMchnt").html()==null || $("#contentMchnt").html()==""){
                    $("#contentMchnt").html('<div><input name="mchntFreezeCd" readonly="readonly" value="'+$(" #checkMchntValue").val()+'"/><input type="button" name="delMchnt" class="easyui-linkbutton" value="X" style="color: #6e77cb;"/></div>');
                }else{
                    $("#contentMchnt").append('<div><input name="mchntFreezeCd" readonly="readonly" value="'+$(" #checkMchntValue").val()+'"/><input type="button" name="delMchnt" class="easyui-linkbutton" value="X" style="color: #6e77cb;"/></div>');
                }
            <#--添加正确，文本框中的值清除-->
                $("#checkMchntValue").val("");
                }
            }
        });
    });

<#--删除关联商户-->
    $(document).on("click","input[name='delMchnt']",function(){
        if(confirm('确认删除吗？')){
            $(this).parent().remove();
        }
    });


<#--删除整行-->
    $(document).on("click","a[name='delMchnt_tab']",function(){
        if(confirm('确认删除吗？')){
            $(this).parent().parent().remove();
            var total=0.00;
            $("input[name='complaintMoney']").each(function(key,value){
                    total=parseFloat(total)+parseFloat($(value).val())
             });
        <#--赋值-->
            $("#sumReturnMoney").html(total);
            var total_sum=0.00;
            $("input[name='dealMoney']").each(function(key,value){
                    total_sum=parseFloat(total_sum)+parseFloat($(value).val())
             });
        <#--赋值-->
            $("#sumMoney").html(total_sum);
        }
    });

<#--鼠标移出事件-->
    $(document).on("blur","input[name='complaintMoney']",function(){
        var total=0.00;
        $("input[name='complaintMoney']").each(function(key,value){
                    total=parseFloat(total)+parseFloat($(value).val())
                });
    <#--赋值-->
        $("#sumReturnMoney").html(total);
    });

<#-- 交易金额 -->
    $(document).on("blur","input[name='dealMoney']",function(){
        var total=0.00;
        $("input[name='dealMoney']").each(function(key,value){
                    total=parseFloat(total)+parseFloat($(value).val())
                });
    <#--赋值-->
        $("#sumMoney").html(total);
    });

<#-- 交易金额 -->
    $(document).on("blur","input[name='terraceTransId']",function(){
        var value=$(this).val();
        if(value==null || ""==value){
            $("#addTr").val("1");
        }else{
            $("#addTr").val("0");
        }
    });


    <#--单选框点击事件-->
    $(document).on("click","input[name='check_id']",function(){
        var value=$(this).val();
        if(value=='1'){
            $("#sele").show();
            $("#inpu").hide();
			$("#sele_2").val("");
        }else{
            $("#inpu").show();
			$("#sele").hide();
			$("#sele_1").val("普通投诉");
        }
    });
</@head>
</head>

<body>
<@addDiv label="${label}" addUrl="/complaintManage/add/submit.do">
<table border="0">
	<tr>
        <td colspan="2"><label style="color: red;float: right">同一投诉事件中仅包含同一渠道商编下且同一小商户的涉诉交易，同渠道商编下不同小商户号交易，请另外录入投诉事件</label>
        <label style="color: red;float: right">注意：所填金额单位为元，且为整数；</label></td>
    </tr>
    <tr style="height: 10px">
        <td></td>
        <td></td>
    </tr>
    <tr>
        <td class="label">涉诉商户站点：</td>
        <td class="content">
            <select name="siteDesc" id="siteDesc" style="width:200px; height:30px;">
                <@enumOpts enumNm='ComplaintEnums.SiteDesc' showCode='true' showEmptyOpt='true'/>
            </select>
        </td>
    </tr>
    <tr>
        <td class="label">投诉类型：</td>
        <td class="content">
            <input type="radio" name="check_id" value="1" checked="checked"/> 下拉框选择 &nbsp;&nbsp;&nbsp;
            <input type="radio" name="check_id" value="2"/> 手动输入
        </td>
    </tr>
    <tr>
        <td class="label"></td>
        <td class="content">
            <div id="sele" style="">
                <select name="complaintType" id="sele_1" style="width:200px; height:30px;">
                    <option value="普通投诉">普通投诉</option>
                    <option value="人行投诉">人行投诉</option>
                    <option value="现场举报">现场举报</option>
                    <option value="警方协查">警方协查</option>
                    <option value="司法冻结">司法冻结</option>
                    <option value="疑似欺诈">疑似欺诈</option>
                    <option value="疑似博彩">疑似博彩</option>
                    <option value="疑似洗钱">疑似洗钱</option>
                    <option value="疑似外汇">疑似外汇</option>
                    <option value="疑似金融">疑似金融</option>
                    <option value="调单">调单</option>
                    <option value="否认交易">否认交易</option>
                    <option value="盗卡盗刷">盗卡盗刷</option>
                    <option value="电信诈骗">电信诈骗</option>
                    <option value="重置未到账">重置未到账</option>
                    <option value="欺诈交易">欺诈交易</option>
                </select>
            </div>
            <div id="inpu"  style="display:none">
                <input type="text"  id="sele_2" class="easyui-validatebox" name="complaintType"
                       style="width:195px; height:23px;" value=""/>
            </div>
        </td>
    </tr>
    <tr>
        <td class="label">渠道名称：</td>
        <td class="content">
            <select name="chnlId" id="chnlId" style="width:200px; height:30px;">
                <@enumOpts enumNm='TxnEnums.ChnlId' showCode='true' showEmptyOpt='true' />
            </select>
        </td>
    </tr>
    <tr>
        <td class="label">处理状态：</td>
        <td class="content">
            <select name="processState" id="processState" style="width:200px; height:30px;">
                <@enumOpts enumNm='ComplaintEnums.ProcessState' showCode='true' showEmptyOpt='true'/>
            </select>
        </td>
    </tr>
    <tr>
        <td class="label">上游处理类型：</td>
        <td class="content">
            <select name="upDealWith" id="upDealWith" style="width:200px; height:30px;">
                <@enumOpts enumNm='ComplaintEnums.UpDealWith' showCode='true' showEmptyOpt='true'/>
            </select>
        </td>
    </tr>
    <tr>
        <td class="label">上游冻结金额：</td>
        <td class="content">
            <input type="text" class="easyui-validatebox" name="upFreezeMoney" id="upFreezeMoney"
                   style="width:195px; height:23px;"/>
        </td>
    </tr>
    <tr>
        <td class="label">上游冻结差额：</td>
        <td class="content">
            <input type="text" class="easyui-validatebox" name="upFreezeDiffer" id="upFreezeDiffer"
                   style="width:195px; height:23px;"/>
        </td>
    </tr>
    <tr>
        <td class="label">威力处理类型：</td>
        <td class="content">
            <select name="mchntDealWith" id="mchntDealWith" id="mchntDealWith" style="width:200px; height:30px;">
                <@enumOpts enumNm='ComplaintEnums.MchntDealWith' showCode='true' showEmptyOpt='true'/>
            </select>
        </td>
    </tr>
    <tr>
        <td class="label">冻结商户金额：</td>
        <td class="content">
            <input type="text" class="easyui-validatebox" name="mchntFreezeMoney" id="mchntFreezeMoney"
                   style="width:195px; height:23px;"/>
        </td>
    </tr>
    <tr>
        <td class="label">冻结商户差额：</td>
        <td class="content">
            <input type="text" class="easyui-validatebox" name="mchntFreezeDiffer" id="mchntFreezeDiffer"
                   style="width:195px; height:23px;"/>
        </td>
    </tr>
    <tr>
        <td class="label">冻结关联商户金额：</td>
        <td class="content">
            <input type="text" class="easyui-validatebox" name="mchntFreezeReMoney" id="mchntFreezeReMoney"
                   style="width:195px; height:23px;"/>
        </td>
    </tr>
    <tr>
        <td class="label">关联商户号：</td>
        <td class="content">
            <div>
                <input type="text" class="easyui-validatebox" id="checkMchntValue"
                       style="width:195px; height:23px;"/>
                <a id="checkMchnt" class="easyui-linkbutton" data-options="iconCls:'icon-add'"></a>
            </div>
        </td>
    </tr>
    <tr>
        <td class="label"><input type="hidden" id="hidden_mchnt"/></td>
        <td class="content" id="contentMchnt">
        </td>
    </tr>
    <tr>
        <td class="label">投诉人：</td>
        <td class="content">
            <input type="text" class="easyui-validatebox" name="complaintor" id="complaintor"
                   style="width:195px; height:23px;"/>
            <span class="inputDesc">如有身份证号，请在此栏位按“姓名|身份证号”格式一并填写</span>
        </td>
    </tr>
    <tr>
        <td class="label">投诉人联系方式：</td>
        <td class="content">
            <input type="text" class="easyui-validatebox" name="complaintorPhone" id="complaintorPhone"
                   style="width:195px; height:23px;"/>
        </td>
    </tr>
    <tr>
        <td class="label">退款卡号信息：</td>
        <td class="content">
            <input type="text" class="easyui-validatebox" name="accName" id="accName"
                   style="width:195px; height:23px;"/>
            <span class="inputDesc">格式：姓名|卡号|开户行</span>
        </td>
    </tr>
    <tr>
        <td class="label">备注：</td>
        <td class="content">
        <input type="text" class="easyui-validatebox" name="complaintRequire" id="complaintRequire"
                   style="width:195px; height:23px;"/>
        </td>
    </tr>
    <tr>
        <td class="label" COLSPAN="12"><h4>涉诉交易信息</h4></td>
    </tr>
    <tr rowspan="5">
        <div>
            <input type="hidden" id="addTr" value="1">
            <table id="tab_02" border="0" style="border: 1px solid #151515">
                <tr>
                    <td colspan="14"><h4>涉诉交易总额:<span id="sumMoney">0.00</span></h4><h4>退款总额:<span id="sumReturnMoney">0.00</span>
                    </h4></td>
                </tr>
                <tr>
                    <td></td>
                    <td>平台订单号</td>
                    <td>商户订单号</td>
                    <td>交易金额</td>
                    <td>卡号</td>
                    <td>银行名称</td>
                    <td>交易方式</td>
                    <td>交易创建日期</td>
                    <td>交易创建时间</td>
                    <td>商户号</td>
                    <td>商户名称</td>
                    <td>渠道商户编号</td>
                    <td>渠道商户名称</td>
                    <td>处理结果</td>
                    <td>退款金额</td>
                    <td>操作</td>
                </tr>
                <tbody id="hide_tbody">
                <tr style="display:none">
                    <td></td>
                    <td class="1" style="width: 200px">
                        <nobr><input type="text" name="terraceTransId" id="terraceTransId" style="width: 100px"/><a
                                name="queryMchnt" class="easyui-linkbutton" style="width: 10px"
                                data-options="iconCls:'icon-search'"></a></nobr>
                    </td>
                    <td class="2"><input type="text" name="mchntTransId"/></td>
                    <td class="3"><input type="text" name="dealMoney" value="0.00"/></td>
                    <td class="4"><input type="text" name="complaintCard"/></td>
                    <td class="5"><input type="text" name="bankName"/></td>
                    <td class="6"><select id="dealType" name="dealType">
                        <@enumOpts enumNm="BmEnums.TxnTypesForSel" selected="01" exceptValues="52" showEmptyOpt="false" showCode="true" />
                    </select></td>
                    <td class="7"><input type="text" name="dealDt"/></td>
                    <td class="8"><input type="text" name="dealTm"/></td>
                    <td class="9"><input type="text" name="mchntCd"/></td>
                    <td class="10"><input type="text" name="mchntCnNm"/></td>
                    <td class="11"><input type="text" name="chnlMchntCd"/></td>
                    <td class="12"><input type="text" name="chnlMchntDesc"/></td>
                    <td class="13"><select name="dealProcessResult">
                        <@enumOpts enumNm='ComplaintEnums.ProcessResult' showCode='true' showEmptyOpt='true'/>
                    </select></td>
                    <td class="14"><input type="text" name="complaintMoney" value="0.00">
                    <td><a name="delMchnt_tab" class="easyui-linkbutton" style="width: 10px"
                           data-options="iconCls:'icon-cancel'"></a></td>
                </tr>
                </tbody>
                <tbody id="show_tbody">
                <tr>
                    <td></td>
                    <td class="1" style="width: 200px">
                        <nobr><input type="text" name="terraceTransId" id="terraceTransId" style="width: 100px"/><a
                                name="queryMchnt" class="easyui-linkbutton" style="width: 10px"
                                data-options="iconCls:'icon-search'"></a></nobr>
                    </td>/
                    <td class="2"><input type="text" name="mchntTransId"/></td>
                    <td class="3"><input type="text" name="dealMoney"/></td>
                    <td class="4"><input type="text" name="complaintCard"/></td>
                    <td class="5"><input type="text" name="bankName"/></td>
                    <td class="6"><select id="dealType" name="dealType">
                        <@enumOpts enumNm="BmEnums.TxnTypesForSel" selected="01" exceptValues="52" showEmptyOpt="false" showCode="true" />
                    </select></td>
                    <td class="7"><input type="text" name="dealDt"/></td>
                    <td class="8"><input type="text" name="dealTm"/></td>
                    <td class="9"><input type="text" name="mchntCd"/></td>
                    <td class="10"><input type="text" name="mchntCnNm"/></td>
                    <td class="11"><input type="text" name="chnlMchntCd"/></td>
                    <td class="12"><input type="text" name="chnlMchntDesc"/></td>
                    <td class="13"><select name="dealProcessResult">
                        <@enumOpts enumNm='ComplaintEnums.ProcessResult' showCode='true' showEmptyOpt='true'/>
                    </select></td>
                    <td class="14"><input type="text" name="complaintMoney" value="0.00">
                    <td><a name="delMchnt_tab" class="easyui-linkbutton" style="width: 10px"
                           data-options="iconCls:'icon-cancel'"></a></td>
                </tr>
                </tbody>
                <tr>
                    <td><a name="addBtn" class="easyui-linkbutton" data-options="iconCls:'icon-add'"
                           href="javascript:void(0)"></a></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </table>
        </div>
    </tr>
</table>
</@addDiv>
</body>
</html>