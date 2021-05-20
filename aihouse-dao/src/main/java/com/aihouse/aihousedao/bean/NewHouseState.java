package com.aihouse.aihousedao.bean;

import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;

import java.util.Date;

/**
 * 楼盘动态bean
 */
public class NewHouseState extends Page {
    /**
     * id
     */
    private Integer id;

    /**
     * 楼盘id
     */
    private Integer newHouseId;

    /**
     * 动态类型0预售证1开盘2交房
     */
    private Integer type;

    /**
     * 标题
     */
    private String title;

    /**
     * 详情
     */
    private String content;

    /**
     * 楼栋
     */
    private String building;

    /**
     * 时间
     */
    private String publictime;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 状态0正常1删除
     */
    private Integer isDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNewHouseId() {
        return newHouseId;
    }

    public void setNewHouseId(Integer newHouseId) {
        this.newHouseId = newHouseId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getPublictime() {
        return publictime;
    }

    public void setPublictime(String publictime) {
        this.publictime = publictime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getType_() {
        if(type!=null){
            if(type==0){
                return "预售证";
            }else if(type==1){
                return "开盘";
            }else if(type==2){
                return "交楼";
            }
        }
        return "";
    }

    public String getIsDel_(){
        if(isDel!=null){
            if(isDel==0){
                return "正常";
            }else if(isDel==1){
                return "删除";
            }
        }
        return "";
    }
    public String getCreatetime_() {
        return DateUtils.formatDateTime(createtime);
    }
}
