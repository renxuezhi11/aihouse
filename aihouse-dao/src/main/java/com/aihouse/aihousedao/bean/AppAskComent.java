package com.aihouse.aihousedao.bean;

import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.List;


/**
 *提问回复表 bean
 */
public class AppAskComent   extends Page {

	/**未浏览已浏览*/
	public final  static  String   CHECKED = "1";

	/**未浏览*/
	public final  static  String   UNCHECKED = "0";

	/**审核状态：0-未审核*/
	public static final  Integer    COMENT_STATUS_UNCHECKED = 0 ;

	/**审核状态：1-审核通过*/
	public static final  Integer   COMENT_STATUS_PASS       = 1;

	/**审核状态：2-审核驳回*/
	public static final  Integer   COMENT_STATUS_REJECT     = 2;

	/**审核状态：3-已删除*/
	public static final  Integer   COMENT_STATUS_DELETE     = 3;

	/**
	 * 回复评论表
	 */
	private List<AskCommentReply> askCommentReplyList;

	/**
	 * 回复点赞表
	 */
	private List<AskCommentPraise> askCommentPraiseList;

	/**
	 * 问答题
	 */
	private AppAsk appAsk;

	/**
	 * id
	 */
	private Integer id;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 提问id
	 */
	private Long askId;
	/**
	 * 点赞数
	 */
	private Integer thumbsUp;
	/**
	 * 评论数
	 */
	private Integer commentCount;
	/**
	 * 回复内容
	 */
	private String commentContent;
	/**
	 * 回复时间
	 */
	private Date createTime;
	/**
	 *是否被创建人查看  默认为0未查看,1-已查看
	 * */
	private String isChecked;
	/**
	 *状态:（0未审核，1审核通过，2审核不通过,3已删除）
	 * */
	private Integer status;
	/**
	 *是否被题主查看  -默认为0未查看,1-已查看
	 * */
	private Integer isMainChecked;


	public Integer getId(){
		return id;
	}


	public void setId(Integer id){
		this.id=id;
	}


	public Integer getUserId(){
		return userId;
	}


	public void setUserId(Integer userId){
		this.userId=userId;
	}


	public Long getAskId(){
		return askId;
	}


	public void setAskId(Long askId){
		this.askId=askId;
	}


	public String getCommentContent(){
		return commentContent;
	}


	public void setCommentContent(String commentContent){
		this.commentContent=commentContent;
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


	/**
	 * 问答题
	 */
	public AppAsk getAppAsk(){
		return appAsk;
	}


	/**
	 * 问答题
	 */
	public void setAppAsk(AppAsk appAsk){
		this.appAsk = appAsk;
	}


	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	/**
	 * 回复点赞表
	 */
	public List<AskCommentPraise> getAskCommentPraiseList(){
		return askCommentPraiseList;
	}
	/**
	 * 回复点赞表
	 */
	public void setAskCommentPraiseList(List<AskCommentPraise> askCommentPraiseList){
		this.askCommentPraiseList = askCommentPraiseList;
	}
	/**
	 * 回复评论表
	 */
	public List<AskCommentReply> getAskCommentReplyList(){
		return askCommentReplyList;
	}
	/**
	 * 回复评论表
	 */
	public void setAskCommentReplyList(List<AskCommentReply> askCommentReplyList){
		this.askCommentReplyList = askCommentReplyList;
	}

	public Integer getThumbsUp() {
		return thumbsUp;
	}

	public void setThumbsUp(Integer thumbsUp) {
		this.thumbsUp = thumbsUp;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getstatus_() {
		if(status!=null) {
			if (status == COMENT_STATUS_UNCHECKED){
				return "未审核";
			}else if (status == COMENT_STATUS_PASS) {
				return "审核通过";
			} else if (status == COMENT_STATUS_REJECT) {
				return "审核不通过";
			}else if(status == COMENT_STATUS_DELETE){
				return "已删除";
			}
		}
		return "";
	}

	public Integer getIsMainChecked() {
		return isMainChecked;
	}

	public void setIsMainChecked(Integer isMainChecked) {
		this.isMainChecked = isMainChecked;
	}
}
