package com.aihouse.aihousedao.vo;

import java.util.List;

/**
 * @author ：rxz
 * @description：用户推广记录
 * @date ：Created in 2021/6/10 10:54
 */
public class UserSpreadLogVO {

    private String userId;

    private String yearAndMonth;

    private Integer total;

    private List<SpreadLog> spreadLogList;

    public String getYearAndMonth() {
        return yearAndMonth;
    }

    public void setYearAndMonth(String yearAndMonth) {
        this.yearAndMonth = yearAndMonth;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<SpreadLog> getSpreadLogList() {
        return spreadLogList;
    }

    public void setSpreadLogList(List<SpreadLog> spreadLogList) {
        this.spreadLogList = spreadLogList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static class SpreadLog{

        private String name;

        private String phone;

        private String createTime;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
