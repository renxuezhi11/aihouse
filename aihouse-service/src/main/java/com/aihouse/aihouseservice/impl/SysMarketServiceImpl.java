package com.aihouse.aihouseservice.impl;
import com.aihouse.aihousedao.dao.SysMarketDao;
import com.aihouse.aihouseservice.SysMarketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
/**
 *行情表 serverImpl
 */
@Service
@Transactional
public class SysMarketServiceImpl   implements SysMarketService {
	/**
	 * 注入dao
	 */
	@Resource
	private SysMarketDao sysMarketDao;
	/**
	 * 初始化
	 */
	@Override
	public SysMarketDao initDao(){
		return sysMarketDao;
	}
}
