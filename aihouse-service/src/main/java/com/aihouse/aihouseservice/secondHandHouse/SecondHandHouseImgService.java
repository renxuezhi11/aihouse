package com.aihouse.aihouseservice.secondHandHouse;

import java.util.List;
import com.aihouse.aihousedao.bean.SecondHandHouseImg;
import com.aihouse.aihousedao.dao.secondHandHouse.SecondHandHouseImgDao;
import com.aihouse.aihouseservice.BaseService;


/**
 *二手房图片表 service
 */
public interface SecondHandHouseImgService extends BaseService<SecondHandHouseImg, SecondHandHouseImgDao> {




	/**
	 * 级联查询(带分页) 二手房源详细信息--二手房图片
	 */
	public SecondHandHouseImg selectSecondHandHouseAndSecondHandHouseImg(SecondHandHouseImg secondHandHouseImg);
	/**
	 * 级联条件查询 二手房源详细信息--二手房图片
	 */
	public List<SecondHandHouseImg> selectSecondHandHouseAndSecondHandHouseImgByCondition(SecondHandHouseImg secondHandHouseImg);
	/**
	 * 级联删除(根据主键删除) 二手房源详细信息--二手房图片
	 */
	public Integer deleteSecondHandHouseAndSecondHandHouseImg(SecondHandHouseImg secondHandHouseImg);

    public Integer deleteAllByHouseId(Integer id);

    int saveImg(SecondHandHouseImg secondHandHouseImg, List<String> imgUrls);
}
