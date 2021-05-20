package com.aihouse.aihousedao.bean;
import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;


/**
 *楼盘佣金表 bean
 */
public class SysNewHouseBrokerage  extends Page {

	//正常
	private final  static  Integer NORMAL_STATE = 0;
	//删除
	private final  static  Integer DELETE_STATE = 1;
	//结佣
	private final static  Integer  KNOT_COMMISSION = 1;
	//现佣
    private final static Integer   NOW_COMMISSION  = 2;

    //固定佣金
	private final  static Integer  DEAD_COMMISSION = 1;
	//固定金额
	private final static Integer   FIXED_AMOUNT    = 2;


	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 关联楼盘表id
	 */
	private Integer newHouseId;
	/**
	 * 业务类型（1.代理佣金2.）
	 */
	private Integer type;
	/**
	 * 佣金类型（1.结佣2.现佣）
	 */
	private Integer brokerageType;
	/**
	 * 佣金模式1.固定佣金2.固定金额
	 */
	private Integer brokerageModel;
	/**
	 * 佣金金额
	 */
	private BigDecimal brokerageMoney;
	/**
	 * 佣金点数
	 */
	private BigDecimal brokerageScale;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 删除状态0正常1删除
	 */
	private Integer isDel;
	/**
	 * 上传时间
	 */
	private Date createtime;
	public Integer getId(){
		return id;
	}


	public void setId(Integer id){
		this.id=id;
	}


	public Integer getNewHouseId(){
		return newHouseId;
	}


	public void setNewHouseId(Integer newHouseId){
		this.newHouseId=newHouseId;
	}


	public Integer getType(){
		return type;
	}


	public void setType(Integer type){
		this.type=type;
	}


	public Integer getBrokerageType(){
		return brokerageType;
	}


	public void setBrokerageType(Integer brokerageType){
		this.brokerageType=brokerageType;
	}


	public Integer getBrokerageModel(){
		return brokerageModel;
	}


	public void setBrokerageModel(Integer brokerageModel){
		this.brokerageModel=brokerageModel;
	}


	public BigDecimal getBrokerageMoney(){
		return brokerageMoney;
	}


	public void setBrokerageMoney(BigDecimal brokerageMoney){
		this.brokerageMoney=brokerageMoney;
	}


	public BigDecimal getBrokerageScale(){
		return brokerageScale;
	}


	public void setBrokerageScale(BigDecimal brokerageScale){
		this.brokerageScale=brokerageScale;
	}


	public String getRemark(){
		return remark;
	}


	public void setRemark(String remark){
		this.remark=remark;
	}


	public Integer getIsDel(){
		return isDel;
	}


	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}


	public Date getCreatetime() { return createtime; }

	public void setCreatetime(Date createtime) { this.createtime = createtime; }

	public String gettype_(){ return"代理佣金"; }

	//佣金模式1.固定佣金2.固定金额
    public String getbrokerageModel_(){
		if(StringUtils.isEmpty(brokerageModel)){
			return "" ;
		}else if(brokerageModel.equals(DEAD_COMMISSION)){
			return "固定佣金";
		}else if(brokerageModel.equals(FIXED_AMOUNT)){
			return "固定金额";
		}
		return "";
	}

	//1.结佣2现佣
	public String getbrokerageType_(){
		if(StringUtils.isEmpty(brokerageType)){
			return "" ;
		}else if(brokerageType.equals(KNOT_COMMISSION)){
			return "结佣";
		}else if(brokerageType.equals(NOW_COMMISSION)){
			return "现佣";
		}
		return "";
	}

	//删除状态
	public String getisDel_(){
		if(StringUtils.isEmpty(isDel)){
			return "" ;
		}else if(isDel.equals(NORMAL_STATE)){
			return "正常" ;
		}else if(isDel.equals(DELETE_STATE)){
			return "删除" ;
		}
		return "";
	}

	public String getcreattime_(){ return DateUtils.formatDateTime(createtime); }

}
