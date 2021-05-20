package com.aihouse.aihousedao.bean;

import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;

import java.util.Date;
import java.util.Map;

/**
 * 预约看房bean
 */
public class Appointment extends Page {

    /**
     * id
     */
    private Integer id;

    /**
     *用户id
     */
    private Integer userId;

    /**
     * 预约房源类型
     */
    private Integer houseType;

    /**
     * 房源id
     */
    private Integer houseId;

    /**
     * 预约日期
     */
    private String appointmentDate;

    /**
     * 预约时间段
     *
     */
    private String appointmentTime;

    /**
     * 经纪人id
     */
    private Integer borkerId;

    /**
     * 预约状态0预约成功1取消预约2看房完成3评论完成
     */
    private Integer status;

    /**
     * 手机号
     */
    private String userTelephone;

    /**
     * 房源名称
     */
    private String name;

    private Date createtime;

    private Map<String,Object> map;

    private String username;

    private String brokername;

    public String getBrokername() {
        return brokername;
    }

    public void setBrokername(String brokername) {
        this.brokername = brokername;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

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

    public Integer getHouseType() {
        return houseType;
    }

    public void setHouseType(Integer houseType) {
        this.houseType = houseType;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Integer getBorkerId() {
        return borkerId;
    }

    public void setBorkerId(Integer borkerId) {
        this.borkerId = borkerId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserTelephone() {
        return userTelephone;
    }

    public void setUserTelephone(String userTelephone) {
        this.userTelephone = userTelephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouseType_(){
        if(houseType!=null){
            if(houseType==1){
                return "新房";
            }else if(houseType==2){
                return "二手房";
            }else if(houseType==3){
                return "租房";
            }else if(houseType==4){
                return "商铺出租";
            }else if(houseType==5){
                return "写字楼出租";
            }
        }
        return "";
    }

    public String getCreatetime_(){
        return DateUtils.formatDateTime(createtime);
    }

    public String getStatus_(){
        if(status!=null){
            if(status==0){
                return "预约中";
            }else if(status==1){
                return "预约取消";
            }else if(status==2){
                return "预约成功";
            }else if(status==3){
                return "已看房";
            }else if(status==4){
                return "已评价";
            }
        }
        return "";
    }
}
