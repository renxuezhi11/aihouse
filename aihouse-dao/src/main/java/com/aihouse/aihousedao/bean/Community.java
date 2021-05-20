package com.aihouse.aihousedao.bean;

import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;

import java.util.Date;
import java.util.List;

/**
 * 圈子信息bean
 */
public class Community extends Page {

    /**
     * 审核状态（未审核）
     */
    public  static final  Integer UNREVIEWED = 0;
    /**
     * 审核状态（审核通过）
     */
    public  static final  Integer REVIEWED_PASS = 1;
    /**
     * 审核状态（审核不通过）
     */
    public  static final  Integer REVIEWED_REJECT = 2;
    /**
     * 已删除
     */
    public  static final  Integer REVIEWED_DELETE = 3;

    /**
     * 圈子点赞表
     */
    private List<CommunityPraise> communityPraiseList;


    /**
     * 圈子信息评论表
     */
    private List<CommunityComment> communityCommentList;

    /**
     * id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片路径
     */
    private String picture;

    /**
     * s视频路径
     */
    private String video;

    /**
     * 创建时间
     */
    private Date createtime;


    /**
     * 经度
     */
    private String lng;

    /**
     * 纬度
     */
    private String lat;

    /**
     * 审核状态（0未审核，1审核通过，2审核不通过，3已删除）
     */
    private Integer status;

    /**
     * 点赞数
     */
    private Integer thumbsUp;

    /**
     * 可见度（1全部，2关注的人）
     */
    private Integer visual;

    /**
     * 审核回复
     */
    private String statusContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(Integer thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public Integer getVisual() {
        return visual;
    }

    public void setVisual(Integer visual) {
        this.visual = visual;
    }

    public String getStatusContent() {
        return statusContent;
    }

    public void setStatusContent(String statusContent) {
        this.statusContent = statusContent;
    }
    public String getCreatetime_() {
        return DateUtils.formatDateTime(createtime);
    }
    public String getStatus_() {
        if(status!=null) {
            if (status == UNREVIEWED){
                return "未审核";
            }else if (status == REVIEWED_PASS) {
                return "审核通过";
            } else if (status == REVIEWED_REJECT) {
                return "审核不通过";
            }else if(status == REVIEWED_DELETE){
                return "已删除";
            }
        }
        return "";
    }

    public List<CommunityPraise> getCommunityPraiseList() {
        return communityPraiseList;
    }

    public void setCommunityPraiseList(List<CommunityPraise> communityPraiseList) {
        this.communityPraiseList = communityPraiseList;
    }

    public List<CommunityComment> getCommunityCommentList() {
        return communityCommentList;
    }

    public void setCommunityCommentList(List<CommunityComment> communityCommentList) {
        this.communityCommentList = communityCommentList;
    }
}
