package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.bean.SecondHandHouse;
import com.aihouse.aihousedao.bean.SecondHandHouseImg;
import java.util.List;

import com.aihouse.aihousedao.dao.secondHandHouse.SecondHandHouseDao;
import com.aihouse.aihousedao.dao.secondHandHouse.SecondHandHouseImgDao;
import com.aihouse.aihouseservice.secondHandHouse.SecondHandHouseImgService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 *二手房图片表 serverImpl
 */
@Service
@Transactional
public class SecondHandHouseImgServiceImpl   implements SecondHandHouseImgService {

	/**
	 * 二手房图片
	 */
	@Resource
	private SecondHandHouseDao secondHandHouseDao;



	/**
	 * 注入dao
	 */
	@Resource
	private SecondHandHouseImgDao secondHandHouseImgDao;
	/**
	 * 初始化
	 */
	@Override
	public SecondHandHouseImgDao initDao(){
		return secondHandHouseImgDao;
	}


	/**
	 * 级联查询(带分页) 二手房源详细信息--二手房图片
	 */
	@Override
	public SecondHandHouseImg selectSecondHandHouseAndSecondHandHouseImg(SecondHandHouseImg secondHandHouseImg){
		secondHandHouseImg = this.selectAllByPaging(secondHandHouseImg);
		if(secondHandHouseImg!=null && secondHandHouseImg.getRows()!=null){
			secondHandHouseImg.getRows().forEach(t->{
				SecondHandHouseImg data= (SecondHandHouseImg) t;
				SecondHandHouse secondHandHouse=new SecondHandHouse();
				secondHandHouse.setId(data.getSecondHouse());
				data.setSecondHandHouse(secondHandHouseDao.selectByPrimaryKey(secondHandHouse));
			});
		}
		return secondHandHouseImg;

	}


	/**
	 * 级联条件查询二手房源详细信息--二手房图片
	 */
	@Override
	public List<SecondHandHouseImg> selectSecondHandHouseAndSecondHandHouseImgByCondition(SecondHandHouseImg secondHandHouseImg){
		List<SecondHandHouseImg> datas = this.selectByCondition(secondHandHouseImg);
		if(datas!=null){
			datas.forEach(t->{
				SecondHandHouse secondHandHouse=new SecondHandHouse();
				secondHandHouse.setId(t.getSecondHouse());
				t.setSecondHandHouse(secondHandHouseDao.selectByPrimaryKey(secondHandHouse));
			});
		}
		return datas;

	}


	/**
	 * 级联删除(根据主表删除) 二手房源详细信息--二手房图片
	 */
	@Override
	public Integer deleteSecondHandHouseAndSecondHandHouseImg(SecondHandHouseImg secondHandHouseImg){
		secondHandHouseImg = secondHandHouseImgDao.selectByPrimaryKey(secondHandHouseImg);
		if(secondHandHouseImg!=null){
			SecondHandHouse secondHandHouse=new SecondHandHouse();
			secondHandHouse.setId(secondHandHouseImg.getSecondHouse());
			secondHandHouseDao.deleteByPrimaryKey(secondHandHouse);
		}
		return secondHandHouseImgDao.deleteByPrimaryKey(secondHandHouseImg);

	}


	@Override
	public Integer deleteAllByHouseId(Integer id) {
		return secondHandHouseImgDao.deleteAllByHouseId(id);
	}

	@Override
	public int saveImg(SecondHandHouseImg secondHandHouseImg, List<String> imgUrls) {
		secondHandHouseImgDao.deleteSecondHandHouseImgBySecondHandHouse(secondHandHouseImg);
		if(imgUrls.size()>0){
			for(String s:imgUrls){
				SecondHandHouseImg secondHandHouseImg1=new SecondHandHouseImg();
				secondHandHouseImg1.setSecondHouse(secondHandHouseImg.getSecondHouse());
				secondHandHouseImg1.setImgType(1);
				secondHandHouseImg1.setImgUrl(s);
				secondHandHouseImgDao.insert(secondHandHouseImg1);
			}
		}
		return 0;
	}
}
