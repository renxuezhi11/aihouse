package com.aihouse.aihousedao.bean;

import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * 圈子举报表
 */
public class CommunityReport extends Page {

    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 圈子id或者圈子评论id
     */
    private Integer communityId;

    /**
     * 举报原因
     */
    private String reason;

    /**
     * 举报时间
     */
    private Date createtime;

    /**
     * 0举报中1已处理
     */
    private Integer status;

    /**
     * 0圈子1评论
     */
    private Integer type;

    /**
     * 举报人
     */
    private String nickname;

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

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @JsonIgnore
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getType_(){
        if(type!=null){
            if(type==0){
                return "圈子";
            }else if(type==1){
                return "评论";
            }
        }
        return "";
    }

    public String getStatus_(){
        if(status!=null){
            if(status==0){
                return "审核中";
            }else if(status==1){
                return "审核完成";
            }
        }
        return "";
    }

    public String getCreatetime_() {
        return DateUtils.formatDateTime(createtime);
    }
}
