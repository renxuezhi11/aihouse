package com.aihouse.aihouseservice;

import java.util.List;
import java.util.Map;

import com.aihouse.aihousedao.bean.Village;
import com.aihouse.aihousedao.dao.VillageDao;


/**
 *小区表 service
 */
public interface VillageService extends BaseService<Village,VillageDao>{




	/**
	 * 级联查询(带分页) 小区--二手房
	 */
	public Village selectVillageAndSecondHandHouse(Village village);
	/**
	 * 级联查询(带分页) 小区--二手房
	 */
	public List<Village> selectVillageAndSecondHandHouseByCondition(Village village);
	/**
	 * 级联删除(根据主键删除) 小区--二手房
	 */
	public Integer deleteVillageAndSecondHandHouse(Village village);

    public List<Map<String,Object>> selectAllByName(Village village);

    List<Map<String,Object>> getSource(Integer start, Integer limit);
}
