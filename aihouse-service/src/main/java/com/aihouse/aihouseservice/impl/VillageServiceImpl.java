package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.bean.Village;
import com.aihouse.aihousedao.bean.SecondHandHouse;
import java.util.List;
import java.util.Map;

import com.aihouse.aihousedao.dao.secondHandHouse.SecondHandHouseDao;
import com.aihouse.aihouseservice.VillageService;
import com.aihouse.aihousedao.dao.VillageDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 *小区表 serverImpl
 */
@Service
@Transactional
public class VillageServiceImpl   implements VillageService {

	/**
	 * 二手房
	 */
	@Resource
	private SecondHandHouseDao secondHandHouseDao;



	/**
	 * 注入dao
	 */
	@Resource
	private VillageDao villageDao;
	/**
	 * 初始化
	 */
	@Override
	public VillageDao initDao(){
		return villageDao;
	}


	/**
	 * 级联查询(带分页) 小区--二手房
	 */
	@Override
	public Village selectVillageAndSecondHandHouse(Village village){
		village = this.selectAllByPaging(village);
		if(village!=null && village.getRows()!=null){
			village.getRows().forEach(t->{
				Village data= (Village) t;
				SecondHandHouse secondHandHouse=new SecondHandHouse();
				secondHandHouse.setVillageId(data.getId());
				List<SecondHandHouse> lists = secondHandHouseDao.selectByCondition(secondHandHouse);
				if(lists!=null && lists.size()>0){
					data.setSecondHandHouse(lists.get(0));
				}
			});
		}
		return village;

	}


	/**
	 * 级联条件查询 小区--二手房
	 */
	@Override
	public List<Village> selectVillageAndSecondHandHouseByCondition(Village village){
		List<Village> datas = this.selectByCondition(village);
		if(datas!=null){
			datas.forEach(t->{
				SecondHandHouse secondHandHouse=new SecondHandHouse();
				secondHandHouse.setVillageId(t.getId());
				List<SecondHandHouse> lists = secondHandHouseDao.selectByCondition(secondHandHouse);
				if(lists!=null && lists.size()>0){
					t.setSecondHandHouse(lists.get(0));
				}
			});
		}
		return datas;

	}


	/**
	 * 级联删除(根据主表删除) 小区--二手房
	 */
	@Override
	public Integer deleteVillageAndSecondHandHouse(Village village){
		SecondHandHouse secondHandHouse=new SecondHandHouse();
		secondHandHouse.setVillageId(village.getId());
		secondHandHouseDao.deleteSecondHandHouseByVillage(secondHandHouse);
		return villageDao.deleteByPrimaryKey(village);

	}

	@Override
	public List<Map<String, Object>> selectAllByName(Village village) {
		return villageDao.selectAllByName(village);
	}


	@Override
	public List<Map<String, Object>> getSource(Integer start, Integer limit) {
		return this.villageDao.getSource(start,limit);
	}
}
