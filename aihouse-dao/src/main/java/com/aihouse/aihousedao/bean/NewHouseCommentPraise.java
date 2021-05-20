package com.aihouse.aihousedao.bean;

import com.aihouse.aihousedao.Page;

import java.util.Date;

/**
 * 楼盘评论点赞bean
 */
public class NewHouseCommentPraise extends Page {
    private Integer id;

    private Integer commentId;

    private Integer userId;

    private Date createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
