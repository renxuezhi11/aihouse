package com.aihouse.aihousedao.dao.secondHandHouse;

import com.aihouse.aihousedao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.aihouse.aihousedao.bean.SecondHandHouse;

import java.util.List;
import java.util.Map;


/**
 *二手房表 dao
 */
@Mapper
public interface SecondHandHouseDao  extends BaseDao<SecondHandHouse> {

	/**
	 * 根据小区删除二手房
	 */
	public Integer deleteSecondHandHouseByVillage(SecondHandHouse secondHandHouse);

	/**
	 * 根据id查询二手房信息，建立索引
	 * @param id
	 * @return
	 */
	public Map<String,Object> selectByIdForSolr(Integer id);

    public Map<String,Object> queryInfo(Integer id);

    public Map<String,Object> queryDetail(Integer id);

    List<Map<String,Object>> geUserSecondHouseList(int userId);
}
