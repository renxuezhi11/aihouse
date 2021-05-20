package com.aihouse.aihousedao.bean;

import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;


/**
 *提问表 bean
 */
public class AppAsk   extends Page {

	/**未浏览已浏览*/
	public final  static  String   CHECKED = "1";

	/**未浏览*/
	public final  static  String   UNCHECKED = "0";

	 /**审核状态：0-未审核*/
	public static final  Integer    ASK_STATUS_UNCHECKED = 0 ;

	 /**审核状态：1-审核通过*/
	public static final  Integer   ASK_STATUS_PASS       = 1;

	 /**审核状态：2-审核驳回*/
	public static final  Integer   ASK_STATUS_REJECT     = 2;

	 /**审核状态：3-已删除*/
	public static final  Integer   ASK_STATUS_DELETE     = 3;

	/**
	 * 提问回复表
	 */
	private List<AppAskComent> appAskComentList;



	/**
	 * id
	 */
	private Long id;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 标题
	 */
	private String askTitle;
	/**
	 * 提问内容
	 */
	private String askContent;
	/**
	 * 提问时间
	 */
	private Date createTime;
	/**
	 * 点赞数
	 */
	private Integer thumbsUp;
	/**
	 * 评论数
	 */
	private Integer commentCount;
	/**
	 *是否被创建人查看  0-默认为0未查看,1-已查看
	 * */
	private String isChecked;
	/**
	 *状态:（0未审核，1审核通过，2审核不通过,3已删除）
	 * */
	private Integer status;

	public Long getId(){
		return id;
	}


	public void setId(Long id){
		this.id=id;
	}


	public Integer getUserId(){
		return userId;
	}


	public void setUserId(Integer userId){
		this.userId=userId;
	}


	public String getAskTitle(){
		return askTitle;
	}


	public void setAskTitle(String askTitle){
		this.askTitle=askTitle;
	}


	public String getAskContent(){
		return askContent;
	}


	public void setAskContent(String askContent){
		this.askContent=askContent;
	}


	public String getcreateTime_(){
		return DateUtils.formatDateTime(createTime);
	}


	@JsonIgnore
	public Date getCreateTime(){
		return createTime;
	}


	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}


	public Integer getThumbsUp(){
		return thumbsUp;
	}


	public void setThumbsUp(Integer thumbsUp){
		this.thumbsUp=thumbsUp;
	}


	public Integer getCommentCount(){
		return commentCount;
	}


	public void setCommentCount(Integer commentCount){
		this.commentCount=commentCount;
	}


	/**
	 * 提问回复表
	 */
	public List<AppAskComent> getAppAskComentList(){
		return appAskComentList;
	}


	/**
	 * 提问回复表
	 */
	public void setAppAskComentList(List<AppAskComent> appAskComentList){
		this.appAskComentList = appAskComentList;
	}


	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getstatus_() {
		if(status!=null) {
			if (status == ASK_STATUS_UNCHECKED){
				return "未审核";
			}else if (status == ASK_STATUS_PASS) {
				return "审核通过";
			} else if (status == ASK_STATUS_REJECT) {
				return "审核不通过";
			}else if(status == ASK_STATUS_DELETE){
				return "已删除";
			}
		}
		return "";
	}

}
