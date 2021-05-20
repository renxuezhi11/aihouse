package com.aihouse.aihousedao.bean;
import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.math.BigDecimal;


/**
 *行情表 bean
 */
public class SysMarket   extends Page {


	/**
	 * id
	 */
	private Integer id;
	/**
	 * 均价
	 */
	private BigDecimal averagePrice;
	/**
	 * 上涨
	 */
	private BigDecimal priceJump;
	/**
	 * 城市id
	 */
	private Integer cityID;
	/**
	 * 添加时间
	 */
	private Date createTime;
	public Integer getId(){
		return id;
	}


	public void setId(Integer id){
		this.id=id;
	}


	public BigDecimal getAveragePrice(){
		return averagePrice;
	}


	public void setAveragePrice(BigDecimal averagePrice){
		this.averagePrice=averagePrice;
	}


	public BigDecimal getPriceJump(){
		return priceJump;
	}


	public void setPriceJump(BigDecimal priceJump){
		this.priceJump=priceJump;
	}


	public Integer getCityID(){
		return cityID;
	}


	public void setCityID(Integer cityID){
		this.cityID=cityID;
	}


	public String getCreateTime_(){
		return DateUtils.formatDateTime(createTime);
	}


	@JsonIgnore
	public Date getCreateTime(){
		return createTime;
	}


	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}


}
