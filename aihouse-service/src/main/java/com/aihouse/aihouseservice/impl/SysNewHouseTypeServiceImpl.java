package com.aihouse.aihouseservice.impl;
import com.aihouse.aihousedao.dao.SysNewHouseTypeDao;
import com.aihouse.aihouseservice.SysNewHouseTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 *楼盘户型表 serverImpl
 */
@Service
@Transactional
public class SysNewHouseTypeServiceImpl   implements SysNewHouseTypeService {


	/**
	 * 注入dao
	 */
	@Resource
	private SysNewHouseTypeDao sysNewHouseTypeDao;
	/**
	 * 初始化
	 */
	@Override
	public SysNewHouseTypeDao initDao(){
		return sysNewHouseTypeDao;
	}


}
