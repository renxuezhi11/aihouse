package com.aihouse.aihousedao.dao.secondHandHouse;

import com.aihouse.aihousedao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.aihouse.aihousedao.bean.SecondHandHouseImg;


/**
 *二手房图片表 dao
 */
@Mapper
public interface SecondHandHouseImgDao  extends BaseDao<SecondHandHouseImg> {




	/**
	 * 根据二手房源详细信息删除二手房图片
	 */
	public Integer deleteSecondHandHouseImgBySecondHandHouse(SecondHandHouseImg secondHandHouseImg);

    public Integer deleteAllByHouseId(Integer id);
}
