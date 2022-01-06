<table class="view_tbl" cellpadding="0" cellspacing="0" width="99%">
	<#list detailConfLst as detailConf>
		<tr>
			<td class="vtd_lbl">${detailConf.label}:</td>
			<td class="vtd_cnt">
				${entity['${detailConf.field}']!''}
			</td>
		</tr>
	</#list>
	<tr rowspan="5">
        <div>
            <table border="0" style="border: 1px solid #151515">
                <tr>
                    <td colspan="14"><h4>涉诉交易总额:<#if sumMoney??><span id="sumMoney">${sumMoney}</span><#else><span id="sumMoney">0.00</span></#if></h4><h4>退款总额:<#if sumReturnMoney??><span id="sumReturnMoney">${sumReturnMoney}</span><#else><span id="sumReturnMoney">0.00</span></#if>
                    </h4></td>
                </tr>
                <tr>
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
                </tr>
                    <tbody>
                        <#list mapList as obj>
							<tr>
	                    		<td class="1" style="width: 200px"><nobr><input type="text" name="terraceTransId" id="terraceTransId" readonly="readonly" style="width: 100px" value="${obj.chnlOrderId }"/></nobr></td>
	                            <td class="2"><input type="text" readonly="readonly" name="mchntTransId" id="mchntTransId" value="${obj.orderId }" /></td>
	                            <td class="3"><input type="text" readonly="readonly" name="dealMoney_sum" id="dealMoney_sum" value="${obj.transAt }" /></td>
	                            <td class="4"><input type="text" readonly="readonly" name="complaintCard" id="complaintCard" value="${obj.accNo }" /></td>
	                            <td class="5"><#if obj.bankName??><input type="text" readonly="readonly" name="bankName" id="bankName" value="${obj.bankName }" /><#else><input type="text" readonly="readonly" name="bankName" id="bankName"/></#if></td>
	                            <td class="6"><#if obj.payType??><input type="text" readonly="readonly" name="payType" id="payType" value="${obj.payType }" /><#else><input type="text" readonly="readonly" name="payType" id="payType"/></#if></td>
	                            <td class="7"><#if obj.extTransDt??><input type="text" readonly="readonly" name="dealDt" id="dealdt" value="${obj.extTransDt }" /><#else><input type="text" readonly="readonly" name="dealDt" id="dealdt"/></#if></td>
	                            <td class="8"><#if obj.extTransTm??><input type="text" readonly="readonly" name="dealTm" id="dealTm" value="${obj.extTransTm }" /><#else><input type="text" readonly="readonly" name="dealTm" id="dealTm"/></#if></td>
	                            <td class="9"><#if obj.mchntCd??><input type="text" readonly="readonly" name="mchntCd" id="mchntcd" value="${obj.mchntCd }" /><#else><input type="text" readonly="readonly" name="mchntCd" id="mchntcd"/></#if></td>
	                            <td class="10"><#if obj.userId??><input type="text" readonly="readonly" name="mchntCnNm" id="mchntCnNm" value="${obj.userId }" /><#else><input type="text" readonly="readonly" name="mchntCnNm" id="mchntCnNm"/></#if></td>
	                            <td class="11"><#if obj.chnlMchntCd??><input type="text" readonly="readonly" name="chnlMchntCd" id="chnlMchntCd" value="${obj.chnlMchntCd }" /><#else><input type="text" readonly="readonly" name="chnlMchntCd" id="chnlMchntCd"/></#if></td>
	                            <td class="12"><#if obj.chnlMchntDesc??><input type="text" readonly="readonly" name="chnlMchntDesc" id="chnlMchntDesc" value="${obj.chnlMchntDesc }" /><#else><input type="text" readonly="readonly" name="chnlMchntDesc" id="chnlMchntDesc"/></#if></td>
	                            <td class="13"><select name="processResult_sum">
	                                <#list processResultMap as pro>
	                                    <#if obj.processResult?? &&  pro.value==obj.processResult>
	                                        <option value="${pro.value }" selected="selected">${pro.name }</option>
	                                    <#else>
	                                        <option value="${pro.value }">${pro.name }</option>
	                                    </#if>
	                                </#list>
	                            </select></td>
	                            <td class="14"><#if obj.complaintMoney??><input type="text" name="complaintMoney_sum" value="${obj.complaintMoney}" /><#else><input type="text" name="complaintMoney_sum"/></#if></td>
	                        </tr>
							</#list>
                    </tbody>
                </table>
            </div>
        </tr> 
        <tr>
        	<td>添加记录<td>
        	<#if dealHistory??>
        		<td colspan="13"><pre style='white-space: pre-wrap; white-space: -moz-pre-wrap; white-space: -pre-wrap; white-space: -o-pre-wrap; word-wrap: break-word;'>${dealHistory }</pre></td>
        	<#else>
        	    <td colspan="13"><pre style='white-space: pre-wrap; white-space: -moz-pre-wrap; white-space: -pre-wrap; white-space: -o-pre-wrap; word-wrap: break-word;'></pre></td>
        	</#if>
        </tr>
</table>