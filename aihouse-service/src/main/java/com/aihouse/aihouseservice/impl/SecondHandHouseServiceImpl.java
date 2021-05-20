package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousedao.bean.SecondHandHouseImg;

import com.aihouse.aihousedao.dao.VillageDao;
import com.aihouse.aihousedao.bean.Village;
import com.aihouse.aihousedao.bean.SecondHandHouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aihouse.aihousedao.dao.secondHandHouse.SecondHandHouseDao;
import com.aihouse.aihousedao.dao.secondHandHouse.SecondHandHouseImgDao;
import com.aihouse.aihouseservice.secondHandHouse.SecondHandHouseService;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sms.SmsConstant;
import sms.SmsDemo;

import javax.annotation.Resource;


/**
 *二手房表 serverImpl
 */
@Service
@Transactional
public class SecondHandHouseServiceImpl   implements SecondHandHouseService {

	/**
	 * 二手房图片
	 */
	@Resource
	private SecondHandHouseImgDao secondHandHouseImgDao;


	/**
	 * 二手房
	 */
	@Resource
	private VillageDao villageDao;



	/**
	 * 注入dao
	 */
	@Resource
	private SecondHandHouseDao secondHandHouseDao;
	/**
	 * 初始化
	 */
	@Override
	public SecondHandHouseDao initDao(){
		return secondHandHouseDao;
	}


	/**
	 * 级联查询(带分页) 小区--二手房
	 */
	@Override
	public SecondHandHouse selectVillageAndSecondHandHouse(SecondHandHouse secondHandHouse){
		secondHandHouse = this.selectAllByPaging(secondHandHouse);
		if(secondHandHouse!=null && secondHandHouse.getRows()!=null){
			secondHandHouse.getRows().forEach(t->{
				SecondHandHouse data= (SecondHandHouse) t;
				Village village=new Village();
				village.setId(data.getVillageId());
				data.setVillage(villageDao.selectByPrimaryKey(village));
			});
		}
		return secondHandHouse;

	}


	/**
	 * 级联条件查询小区--二手房
	 */
	@Override
	public List<SecondHandHouse> selectVillageAndSecondHandHouseByCondition(SecondHandHouse secondHandHouse){
		List<SecondHandHouse> datas = this.selectByCondition(secondHandHouse);
		if(datas!=null){
			datas.forEach(t->{
				Village village=new Village();
				village.setId(t.getVillageId());
				t.setVillage(villageDao.selectByPrimaryKey(village));
			});
		}
		return datas;

	}


	/**
	 * 级联删除(根据主表删除) 小区--二手房
	 */
	@Override
	public Integer deleteVillageAndSecondHandHouse(SecondHandHouse secondHandHouse){
		secondHandHouse = secondHandHouseDao.selectByPrimaryKey(secondHandHouse);
		if(secondHandHouse!=null){
			Village village=new Village();
			village.setId(secondHandHouse.getVillageId());
			villageDao.deleteByPrimaryKey(village);
		}
		return secondHandHouseDao.deleteByPrimaryKey(secondHandHouse);

	}



	/**
	 * 级联查询(带分页) 二手房源详细信息--二手房图片
	 */
	@Override
	public SecondHandHouse selectSecondHandHouseAndSecondHandHouseImg(SecondHandHouse secondHandHouse){
		secondHandHouse = this.selectAllByPaging(secondHandHouse);
		if(secondHandHouse!=null && secondHandHouse.getRows()!=null){
			secondHandHouse.getRows().forEach(t->{
				SecondHandHouse data= (SecondHandHouse) t;
				SecondHandHouseImg secondHandHouseImg=new SecondHandHouseImg();
				secondHandHouseImg.setSecondHouse(data.getId());
				List<SecondHandHouseImg> lists = secondHandHouseImgDao.selectByCondition(secondHandHouseImg);
				data.setSecondHandHouseImgListO(lists);
			});
		}
		return secondHandHouse;

	}


	/**
	 * 构建主表 级联条件查询 二手房源详细信息--二手房图片
	 */
	@Override
	public List<SecondHandHouse> selectSecondHandHouseAndSecondHandHouseImgByCondition(SecondHandHouse secondHandHouse){
		List<SecondHandHouse> datas = this.selectByCondition(secondHandHouse);
		if(datas!=null){
			datas.forEach(t->{
				SecondHandHouseImg secondHandHouseImg=new SecondHandHouseImg();
				secondHandHouseImg.setSecondHouse(t.getId());
				List<SecondHandHouseImg> lists = secondHandHouseImgDao.selectByCondition(secondHandHouseImg);
				t.setSecondHandHouseImgListO(lists);
			});
		}
		return datas;

	}


	/**
	 * 级联删除(根据主表删除) 二手房源详细信息--二手房图片
	 */
	@Override
	public Integer deleteSecondHandHouseAndSecondHandHouseImg(SecondHandHouse secondHandHouse){
		SecondHandHouseImg secondHandHouseImg=new SecondHandHouseImg();
		secondHandHouseImg.setSecondHouse(secondHandHouse.getId());
		secondHandHouseImgDao.deleteSecondHandHouseImgBySecondHandHouse(secondHandHouseImg);
		return secondHandHouseDao.deleteByPrimaryKey(secondHandHouse);

	}

	@Override
	public List<Map<String, Object>> geUserSecondHouseList(int userId) {
		return secondHandHouseDao.geUserSecondHouseList(userId);
	}

	@Override
	//@CacheEvict(value = "secondhouse",key = "#p0.id")
	public int updateSecondHouse(SecondHandHouse secondHandHouse) {
		return this.update(secondHandHouse);
	}

	@Override
	@Async
	public void sendSms(Integer status, String telephone,String reason) {
		SmsDemo.sendHouseSms(status,telephone,reason);
	}
}
