package com.aihouse.aihouseservice;


import java.util.List;
import java.util.Map;

/**
 * app 筛选请求条件service
 */
public interface AppConditionListService {
    /**
     * 新房
     * @param cityid
     * @return
     */
    public List<Map<String,Object>> getNewHouseIndex(int cityid);

    /**
     * 整租
     * @param cityid
     * @return
     */
    public List<Map<String,Object>> entireTenancyIndex(int cityid);

    /**
     *合租
     */
    public List<Map<String,Object>> joinRent(int cityid);

    /**
     * 写字楼
     * @param cityid
     * @return
     */
    public List<Map<String,Object>> officepremises(int cityid);

    /**
     * 二手房
     * @param cityid
     * @return
     */
    public List<Map<String,Object>> secondhandhouseIndex(int cityid);

    /**
     * 商铺
     * @param cityid
     * @return
     */
    public List<Map<String,Object>>shopindex(int cityid);
}
