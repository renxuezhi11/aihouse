package com.aihouse.aihousedao.bean;

import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;
import com.aihouse.aihousedao.bean.SecondHandHouse;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.math.BigDecimal;


/**
 *二手房图片表 bean
 */
public class SecondHandHouseImg   extends Page {

	/**
	 * 二手房源详细信息
	 */
	private SecondHandHouse secondHandHouse;



	/**
	 * id
	 */
	private Long id;
	/**
	 * 图片类型（1室内图，2交通图，3实景图，4户型图）
	 */
	private Integer imgType;
	/**
	 * 图片路径
	 */
	private String imgUrl;
	/**
	 * 图片描述
	 */
	private String imgDesc;
	/**
	 * 关联二手房表id
	 */
	private Integer secondHouse;
	/**
	 * 上传时间
	 */
	private Date createtime;
	/**
	 * 删除状态0正常1删除
	 */
	private Integer isDel;
	public Long getId(){
		return id;
	}


	public void setId(Long id){
		this.id=id;
	}


	public Integer getImgType(){
		return imgType;
	}


	public void setImgType(Integer imgType){
		this.imgType=imgType;
	}


	public String getImgUrl(){
		return imgUrl;
	}


	public void setImgUrl(String imgUrl){
		this.imgUrl=imgUrl;
	}


	public String getImgDesc(){
		return imgDesc;
	}


	public void setImgDesc(String imgDesc){
		this.imgDesc=imgDesc;
	}


	public Integer getSecondHouse(){
		return secondHouse;
	}


	public void setSecondHouse(Integer secondHouse){
		this.secondHouse=secondHouse;
	}


	public String getCreatetime_(){
		return DateUtils.formatDateTime(createtime);
	}


	@JsonIgnore
	public Date getCreatetime(){
		return createtime;
	}


	public void setCreatetime(Date createtime){
		this.createtime=createtime;
	}


	public Integer getIsDel(){
		return isDel;
	}


	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}


	/**
	 * 二手房源详细信息
	 */
	public SecondHandHouse getSecondHandHouse(){
		return secondHandHouse;
	}


	/**
	 * 二手房源详细信息
	 */
	public void setSecondHandHouse(SecondHandHouse secondHandHouse){
		this.secondHandHouse = secondHandHouse;
	}



}
