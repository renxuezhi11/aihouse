package com.aihouse.aihousedao.bean;

import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;

import java.util.Date;
import java.util.Map;

/**
 * 房源举报bean
 */
public class HouseReport extends Page {

    /**
     * id
     */
    private Integer id;

    /**
     * 房源id
     */
    private Integer houseId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 房源类型
     */
    private Integer houseType;

    /**
     * 举报内容
     */
    private String content;

    /**
     * 举报原因
     */
    private String reason;

    /**
     * 举报时间
     */
    private Date createtime;

    /**
     * 举报状态
     */
    private Integer status;

    private String username;

    private String houseName;

    private String housePicture;

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHousePicture() {
        return housePicture;
    }

    public void setHousePicture(String housePicture) {
        this.housePicture = housePicture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getHouseType() {
        return houseType;
    }

    public void setHouseType(Integer houseType) {
        this.houseType = houseType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

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
    public String getStatus_(){
        if(status!=null){
            if(status==0){
                return "预约成功";
            }else if(status==1){
                return "预约取消";
            }else if(status==2){
                return "看房成功";
            }
        }
        return "";
    }
    public String getHouseType_(){
        if(houseType!=null){
            if(houseType==1){
                return "二手房";
            }else if(houseType==2){
                return "租房";
            }else if(houseType==3){
                return "商铺出租";
            }else if(houseType==4){
                return "写字楼出租";
            }
        }
        return "";
    }
    public String getCreatetime_(){
        return DateUtils.formatDateTime(createtime);
    }
}
