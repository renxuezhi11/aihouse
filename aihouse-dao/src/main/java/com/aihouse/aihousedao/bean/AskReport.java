package com.aihouse.aihousedao.bean;

import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * 问答举报bean
 */
public class AskReport extends Page {

    private Integer id;

    private Integer askId;

    private Integer userId;

    private String reason;

    private Date createtime;

    private Integer status;

    private Integer type;

    private String nickname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAskId() {
        return askId;
    }

    public void setAskId(Integer askId) {
        this.askId = askId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
                return "提问";
            }else if(type==1){
                return "答复";
            }else if(type==2){
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
