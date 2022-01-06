package com.icpay.payment.db.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.data.utils.SeqNums;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.PwdUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.mapper.MchntInfoMapper;
import com.icpay.payment.db.dao.mybatis.mapper.MchntUserInfoMapper;
import com.icpay.payment.db.dao.mybatis.mapper.OrganInfoMapper;
import com.icpay.payment.db.dao.mybatis.mapper.OrganMchntInfoMapper;
import com.icpay.payment.db.dao.mybatis.mapper.ViewOrganMchntInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntInfoExample;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfoExample;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfoKey;
import com.icpay.payment.db.dao.mybatis.model.OrganInfo;
import com.icpay.payment.db.dao.mybatis.model.OrganInfoExample;
import com.icpay.payment.db.dao.mybatis.model.OrganMchntInfo;
import com.icpay.payment.db.dao.mybatis.model.OrganMchntInfoExample;
import com.icpay.payment.db.dao.mybatis.model.OrganMchntInfoExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.OrganMchntInfoKey;
import com.icpay.payment.db.dao.mybatis.model.ViewOrganMchntInfo;
import com.icpay.payment.db.dao.mybatis.model.ViewOrganMchntInfoExample;
import com.icpay.payment.db.dao.mybatis.model.modelExt.OrganMchntExtInfo;
import com.icpay.payment.db.service.IOrganMchntInfoService;

@Service("organMchntInfoService")
public class OrganMchntInfoService extends BaseService implements IOrganMchntInfoService {
	private static final Logger logger = Logger.getLogger(OrganMchntInfoService.class);

	public static String JG_ADMIN_USER = "superadmin";  //机构默认管理员
	public static String JG_SU_ROLE = "su";  //机构默认管理员角色
	
	@Override
	public Pager<ViewOrganMchntInfo> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		ViewOrganMchntInfoExample example = this.buildViewExample(qryParamMap);
		example.setOrderByClause("organ_id desc ,rec_upd_ts desc");
		ViewOrganMchntInfoMapper mapper = this.getMapper(ViewOrganMchntInfoMapper.class);
		Pager<ViewOrganMchntInfo> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}
	
	@Override
	public List<OrganMchntInfo> selectMchntByOrganId(String organId) {
		OrganMchntInfoExample example = new OrganMchntInfoExample();
		example.createCriteria().andOrganIdEqualTo(organId);
		OrganMchntInfoMapper mapper = getMapper();
		return mapper.selectByPage(example);
	}
	
	@Override
	public OrganMchntInfo selectByPrimaryKey(OrganMchntInfoKey key) {
		return getMapper().selectByPrimaryKey(key);
	}
	
	/**
	 * 新增
	 */
	public void add(OrganMchntExtInfo info) {
		i18ArgIsNull(info, this.getClass().getSimpleName(), "待保存的资金池信息对象为null");

		if(Utils.isEmpty(info.getOrganId())) {  // 为空，生成机构号
			try {
				Long i = (System.currentTimeMillis()/1000/86400);
				String prefix= ("JG"+ i.toString()).substring(0, 5);
				String organId = prefix+SeqNums.nextSeq(SeqNums.MER_ID);
				info.setOrganId(organId);
				logger.info(String.format("生成机构代码成功，商户号: %s, %s", organId, info.getMchntCd()));
				
				//新增机构信息
				OrganInfo entity = new  OrganInfo();
				entity.setLastOperId(info.getLastOperId());
				entity.setOrganId(info.getOrganId());
				entity.setOrganDesc(info.getOrganDesc());
				entity.setState(info.getState());
				entity.setRecCrtTs(new Date());
				entity.setRecUpdTs(new Date());
				getOrganMapper().insert(entity);
				
				//生成机构管理员用户
				insertUser(entity);
				
			} catch (Exception e) {
				logger.error(String.format("生成机构代码错误，商户: %s", info.getMchntCd()), e);
			}
		}
		
		i18StrIsBlank(info.getOrganId(), this.getClass().getSimpleName(), "机构代码为空");

		if(!Utils.isEmpty(info.getMchntCdStr())) {
			for(String mchntCd : info.getMchntCdStr()) {
				OrganMchntInfo entity = new  OrganMchntInfo();
				entity.setLastOperId(info.getLastOperId());
				entity.setMchntCd(mchntCd);
				entity.setOrganId(info.getOrganId());
				entity.setState(info.getState());
				
				Date now = new Date();
				entity.setRecCrtTs(now);
				entity.setRecUpdTs(now);
				
				getMapper().insert(entity);
			}
		}else if(!Utils.isEmpty(info.getMchntCd())){
			OrganMchntInfo entity = new  OrganMchntInfo();
			entity.setLastOperId(info.getLastOperId());
			entity.setMchntCd(info.getMchntCd());
			entity.setOrganId(info.getOrganId());
			entity.setState(info.getState());
			
			Date now = new Date();
			entity.setRecCrtTs(now);
			entity.setRecUpdTs(now);
			
			getMapper().insert(entity);
		}
		
	}
	
	/**
	 * 修改
	 */
	public void update(OrganMchntExtInfo info) {
		i18ArgIsNull(info, this.getClass().getSimpleName(), "待更新的资金池配置信息对象为null");

		OrganInfo o = new OrganInfo();
		o.setLastOperId(info.getLastOperId());
		o.setOrganDesc(info.getOrganDesc());
		o.setOrganId(info.getOrganId());
		o.setState(info.getState());
		o.setRecUpdTs(new Date());
		getOrganMapper().updateByPrimaryKeySelective(o);
	}
	
	/**
	 * 删除
	 * @param funcCd
	 * @return
	 */
	public void delete(OrganMchntExtInfo info) {
//		AssertUtil.argIsBlank(info.getOrganId(), "机构id不能为空");
		
		if(!Utils.isEmpty(info.getOrganIdList())) {
				for(OrganInfo m : info.getOrganIdList()) {
	//		OrganInfo m = getOrganMapper().selectByPrimaryKey(info.getOrganId());
	//		AssertUtil.objIsNull(m, "待删除的机构信息不存在：" + info);
			
	//		if(Utils.isEmpty(info.getMchntCdStr())) { // 删除机构
				getOrganMapper().deleteByPrimaryKey(m.getOrganId());
				
				//查询该机构下的所有商户，和用户操作员，一并删除
				OrganMchntInfoExample example = new OrganMchntInfoExample();
				example.createCriteria().andOrganIdEqualTo(m.getOrganId());
				List<OrganMchntInfo> mcnhtList = getMapper().selectByPage(example);
				for(OrganMchntInfo mchnt : mcnhtList) {
					OrganMchntInfoKey key = new  OrganMchntInfoKey();
					key.setOrganId(mchnt.getOrganId());
					key.setMchntCd(mchnt.getMchntCd());
					getMapper().deleteByPrimaryKey(key);
				}
				
				MchntUserInfoMapper userMapper = this.getMapper(MchntUserInfoMapper.class);
				MchntUserInfoExample userExample = new MchntUserInfoExample();
				userExample.createCriteria().andMchntCdEqualTo(m.getOrganId());
				List<MchntUserInfo> userList = userMapper.selectByPage(userExample);
				for(MchntUserInfo user : userList) {
					MchntUserInfoKey userKey = new MchntUserInfoKey();
					userKey.setMchntCd(user.getMchntCd());
					userKey.setUserId(user.getUserId());
					userMapper.deleteByPrimaryKey(userKey);
				}
			}
		}
		
		if(!Utils.isEmpty(info.getMchntCd())) {  //只删除映射的商户
//			for(String mchntCd : info.getMchntCdStr()) {
				OrganMchntInfoKey key = new  OrganMchntInfoKey();
				key.setOrganId(info.getOrganId());
//				key.setMchntCd(mchntCd);
				key.setMchntCd(info.getMchntCd());
				getMapper().deleteByPrimaryKey(key);
//			}
		}
		
	}
	
	private OrganMchntInfoMapper getMapper() {
		return this.getMapper(OrganMchntInfoMapper.class);
	}
	private OrganInfoMapper getOrganMapper() {
		return this.getMapper(OrganInfoMapper.class);
	}

	@Override
	protected OrganMchntInfoExample buildQryExample(Map<String, String> qryParamMap) {
		OrganMchntInfoExample example = new OrganMchntInfoExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();

			// 查询条件：
			String organId = StringUtil.trim(qryParamMap.get("organId"));
			if (!StringUtil.isBlank(organId)) {
				c.andOrganIdEqualTo(organId);
			}
			
			// 查询条件：
			String organDesc = StringUtil.trim(qryParamMap.get("organDesc"));
			if (!StringUtil.isBlank(organDesc)) {
				OrganInfoMapper mapper = getOrganMapper();
				OrganInfoExample ex =  new OrganInfoExample();
				ex.createCriteria().andOrganDescLike("%"+organDesc+"%");
				List<OrganInfo> organ = mapper.selectByExample(ex);
				if ((organ!=null)&& organ.size()>0) {
					List<String> organIds = new ArrayList<>();
					for(OrganInfo o: organ) {
						organIds.add(o.getOrganId());
					}
					c.andOrganIdIn(organIds);
				}
			}
			
			String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdEqualTo(mchntCd);
			}
			
			//String mchntName
			String mchntName = StringUtil.trim(qryParamMap.get("mchntDesc"));
			if (!StringUtil.isBlank(mchntName)) {
				MchntInfoMapper mdao = getMapper(MchntInfoMapper.class);
				MchntInfoExample mex= new MchntInfoExample();
				mex.createCriteria().andMchntCnAbbrLike("%"+mchntName+"%");
				mex.or().andMchntCnNmLike("%"+mchntName+"%");
				List<MchntInfo> mers=mdao.selectByExample(mex);
				if ((mers!=null)&& mers.size()>0) {
					List<String> merIds= new ArrayList<>();
					for(MchntInfo m: mers) {
						merIds.add(m.getMchntCd());
					}
					c.andMchntCdIn(merIds);
				}
			}
			
			// 查询条件：状态
			String state = StringUtil.trim(qryParamMap.get("state"));
			if (!StringUtil.isBlank(state)) {
				c.andStateEqualTo(state);
			}
		}
		return example;
	}
	
	protected ViewOrganMchntInfoExample buildViewExample(Map<String, String> qryParamMap) {
		ViewOrganMchntInfoExample example = new ViewOrganMchntInfoExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			com.icpay.payment.db.dao.mybatis.model.ViewOrganMchntInfoExample.Criteria c = example.createCriteria();
			
			// 查询条件：
			String organId = StringUtil.trim(qryParamMap.get("organId"));
			if (!StringUtil.isBlank(organId)) {
				c.andOrganIdEqualTo(organId);
			}
			
			// 查询条件：
			String organDesc = StringUtil.trim(qryParamMap.get("organDesc"));
			if (!StringUtil.isBlank(organDesc)) {
				OrganInfoMapper mapper = getOrganMapper();
				OrganInfoExample ex =  new OrganInfoExample();
				ex.createCriteria().andOrganDescLike("%"+organDesc+"%");
				List<OrganInfo> organ = mapper.selectByExample(ex);
				if ((organ!=null)&& organ.size()>0) {
					List<String> organIds = new ArrayList<>();
					for(OrganInfo o: organ) {
						organIds.add(o.getOrganId());
					}
					c.andOrganIdIn(organIds);
				}
			}
			
			String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdEqualTo(mchntCd);
			}
			
			//String mchntName
			String mchntName = StringUtil.trim(qryParamMap.get("mchntDesc"));
			if (!StringUtil.isBlank(mchntName)) {
				MchntInfoMapper mdao = getMapper(MchntInfoMapper.class);
				MchntInfoExample mex= new MchntInfoExample();
				mex.createCriteria().andMchntCnAbbrLike("%"+mchntName+"%");
				mex.or().andMchntCnNmLike("%"+mchntName+"%");
				List<MchntInfo> mers=mdao.selectByExample(mex);
				if ((mers!=null)&& mers.size()>0) {
					List<String> merIds= new ArrayList<>();
					for(MchntInfo m: mers) {
						merIds.add(m.getMchntCd());
					}
					c.andMchntCdIn(merIds);
				}
			}
			
			// 查询条件：状态
			String state = StringUtil.trim(qryParamMap.get("state"));
			if (!StringUtil.isBlank(state)) {
				c.andStateEqualTo(state);
			}
		}
		return example;
	}



	@Override
	public List<OrganInfo> selectByOrganId(String organId) {
		OrganInfoMapper mapper = getOrganMapper();
		OrganInfoExample example =  new OrganInfoExample();
		example.createCriteria().andOrganIdEqualTo(organId);
		return mapper.selectByPage(example);
	}


	@Override
	public List<OrganInfo> select() {
		OrganInfoMapper mapper = getOrganMapper();
		OrganInfoExample example =  new OrganInfoExample();
		return mapper.selectByPage(example);
	}
	
	protected void insertUser(OrganInfo info) {
		MchntUserInfoMapper mapper = (MchntUserInfoMapper) this.getMapper(MchntUserInfoMapper.class);
		MchntUserInfo record = new MchntUserInfo();
		record.setMchntCd(info.getOrganId());
		record.setUserId(JG_ADMIN_USER); // 默认的管理员
		record.setLoginPwd(PwdUtil.computeMD5Pwd(StringUtil.randomPwd(10)));
		record.setPwdUpdTs(DateUtil.now19());
		record.setUserState(CommonEnums.MchntUserSt._0.getCode());
		record.setUserRole(JG_SU_ROLE);
		record.setComment("系统默认机构管理员账户");
		mapper.insertSelective(record);
	}


	@Override
	public OrganInfo selectOrganInfoByOrganId(String organId) {
		return getOrganMapper().selectByPrimaryKey(organId);
	}

	@Override
	public OrganInfo selectOrganInfoByOrganDesc(String organDesc) {
		OrganInfoMapper mapper = getOrganMapper();
		OrganInfoExample example =  new OrganInfoExample();
		example.createCriteria().andOrganDescEqualTo(organDesc);
		List<OrganInfo>  list = mapper.selectByExample(example);
		OrganInfo info = null;
		if(!Utils.isEmpty(list))
			info  = list.get(0);
		return info;
	}

	@Override
	public OrganMchntInfo selectMchntByMchnt(String mchntCd) {
		OrganMchntInfoExample example = new OrganMchntInfoExample();
		example.createCriteria().andMchntCdEqualTo(mchntCd);
		List<OrganMchntInfo> mcnhtList = getMapper().selectByExample(example);
		if(Utils.isEmpty(mcnhtList))
			return null;
		return mcnhtList.get(0);
	}

	@Override
	public void updateOrganMchnt(OrganMchntInfo info, Map<String,String> qryParamMap) {
		OrganMchntInfoExample example = this.getQryExample(qryParamMap);
		getMapper().updateByExampleSelective(info,example);
	}
}
