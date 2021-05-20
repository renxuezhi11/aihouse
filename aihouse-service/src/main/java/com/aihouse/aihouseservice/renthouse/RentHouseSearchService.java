package com.aihouse.aihouseservice.renthouse;

import com.aihouse.aihousecore.utils.ResultInfo;
import com.aihouse.aihousedao.bean.NewHouse;
import com.aihouse.aihousedao.bean.RentHouse;
import com.aihouse.aihousedao.bean.SolrRentHouse;
import com.aihouse.aihousedao.dao.renthouse.SysRentHouseDao;
import com.aihouse.aihouseservice.BaseService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 租房查询service
 */
public interface RentHouseSearchService extends BaseService<RentHouse,SysRentHouseDao> {
    /**
     * app首页推荐查询
     * @param lng
     * @param lat
     * @param start
     * @param rows
     * @return
     */
    public ResultInfo<SolrRentHouse> recommendHouse(String lng, String lat, Integer start, Integer rows);

    /**
     * 删除租房索引
     * @param id
     */
    public void deleteRentHouseIndex(Integer id);

    /**
     * 添加索引
     * @param id
     */
    public void addRentHouseIndex(Integer id);

    public ResultInfo<SolrRentHouse> searchRentHouse(HttpServletRequest request);

    public Map<String,Object> queryInfo(Integer id);

    /**
     * 获取详情
     * @param id
     * @return
     */
    public Map<String,Object> queryDetail(Integer id);

    public ResultInfo<SolrRentHouse> getkeywordComplet(String keyword);

    public int updateRentHouse(RentHouse rentHouse);
}
