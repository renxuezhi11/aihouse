package com.aihouse.aihouseservice.secondHandHouse;


import java.util.List;
import java.util.Map;

import com.aihouse.aihousecore.utils.ResultInfo;
import com.aihouse.aihousedao.bean.SecondHandHouse;
import com.aihouse.aihousedao.bean.SolrSecondHouse;
import com.aihouse.aihousedao.dao.secondHandHouse.SecondHandHouseDao;
import com.aihouse.aihouseservice.BaseService;


/**
 *二手房表 service
 */
public interface SecondHandHouseService extends BaseService<SecondHandHouse, SecondHandHouseDao> {






	/**
	 * 级联查询(带分页) 小区--二手房
	 */
	public SecondHandHouse selectVillageAndSecondHandHouse(SecondHandHouse secondHandHouse);
	/**
	 * 级联条件查询 小区--二手房
	 */
	public List<SecondHandHouse> selectVillageAndSecondHandHouseByCondition(SecondHandHouse secondHandHouse);
	/**
	 * 级联删除(根据主键删除) 小区--二手房
	 */
	public Integer deleteVillageAndSecondHandHouse(SecondHandHouse secondHandHouse);

	/**
	 * 级联查询(带分页) 二手房源详细信息--二手房图片
	 */
	public SecondHandHouse selectSecondHandHouseAndSecondHandHouseImg(SecondHandHouse secondHandHouse);
	/**
	 * 级联查询(带分页) 二手房源详细信息--二手房图片
	 */
	public List<SecondHandHouse> selectSecondHandHouseAndSecondHandHouseImgByCondition(SecondHandHouse secondHandHouse);
	/**
	 * 级联删除(根据主键删除) 二手房源详细信息--二手房图片
	 */
	public Integer deleteSecondHandHouseAndSecondHandHouseImg(SecondHandHouse secondHandHouse);

	public List<Map<String,Object>> geUserSecondHouseList(int i);

	public int updateSecondHouse(SecondHandHouse secondHandHouse);

    public void sendSms(Integer status, String telephone,String reason);
}
