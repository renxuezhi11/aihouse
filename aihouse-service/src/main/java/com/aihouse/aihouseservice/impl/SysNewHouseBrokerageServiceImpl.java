package com.aihouse.aihouseservice.impl;
import com.aihouse.aihousedao.dao.SysNewHouseBrokerageDao;
import com.aihouse.aihouseservice.SysNewHouseBrokerageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 *楼盘佣金表 serverImpl
 */
@Service
@Transactional
public class SysNewHouseBrokerageServiceImpl   implements SysNewHouseBrokerageService {


	/**
	 * 注入dao
	 */
	@Resource
	private SysNewHouseBrokerageDao sysNewHouseBrokerageDao;
	/**
	 * 初始化
	 */
	@Override
	public SysNewHouseBrokerageDao initDao(){
		return sysNewHouseBrokerageDao;
	}


}
