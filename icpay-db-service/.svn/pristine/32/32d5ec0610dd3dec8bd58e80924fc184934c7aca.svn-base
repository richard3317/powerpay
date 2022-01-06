package com.icpay.payment.db.dao.mybatis.mapper.ExtMapper;

import java.util.List;

import com.icpay.payment.db.dao.mybatis.model.ChnlInfoMapping;
import com.icpay.payment.db.dao.mybatis.model.ChnlInfoMappingKey;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoSub;
import com.icpay.payment.db.dao.mybatis.model.TxnRouting;
import com.icpay.payment.db.dao.mybatis.model.TxnRoutingExample;
import com.icpay.payment.db.dao.mybatis.model.TxnRouting_Mapping;

/** 
* @author  作者 : wangyi
* @date 创建时间：2018年3月30日 上午12:52:15 
* @version 1.0 
* @parameter  
* @since  
* @return 
*/

public interface CustomExtMapper {

	/**
	 * 联查
	 * @param key
	 * @return
	 */
	ChnlInfoMapping selectMciAndMcisByPrimarykey(ChnlInfoMappingKey key);
	
	/**
	 * 根据主键No  intTransCd  查询所有信息  
	 */
	public List<ChnlMchntInfoSub> queryAllNoIntTransCd(ChnlMchntInfo key);
	
	/**
	 * 路由联查
	 */
	List<TxnRouting_Mapping> selectByPageByTxnRoutingMapping(TxnRoutingExample example);
}
