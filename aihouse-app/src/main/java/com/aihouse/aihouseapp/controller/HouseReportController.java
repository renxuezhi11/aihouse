package com.aihouse.aihouseapp.controller;

import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousedao.bean.HouseReport;
import com.aihouse.aihouseservice.HouseReportService;
import com.aihouse.aihouseservice.officehouse.OfficeHouseSearchService;
import com.aihouse.aihouseservice.renthouse.RentHouseSearchService;
import com.aihouse.aihouseservice.secondHandHouse.SecondHouseSearchService;
import com.aihouse.aihouseservice.shophouse.ShopHouseSearchService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 房源举报controller
 */
@CrossOrigin
@RestController
public class HouseReportController {
    @Resource
    private HouseReportService houseReportService;

    @Resource
    private SecondHouseSearchService secondHouseSearchService;

    @Resource
    private RentHouseSearchService rentHouseSearchService;

    @Resource
    private OfficeHouseSearchService officeHouseSearchService;

    @Resource
    private ShopHouseSearchService shopHouseSearchService;
    /**
     * 添加房源举报
     * @param houseReport
     * @param request
     * @return
     */
    @RequestMapping(value = "app/houseReport/add",method = RequestMethod.POST)
    public DataRes addHouseReport(HouseReport houseReport, HttpServletRequest request){
        if(houseReport!=null){
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            houseReport.setUserId(Integer.parseInt(userId));
            Map<String,Object> map=null;
            if(houseReport.getHouseType()==1){
                map=secondHouseSearchService.queryDetail(houseReport.getHouseId());
            }else if(houseReport.getHouseType()==2){
                map=rentHouseSearchService.queryDetail(houseReport.getHouseId());
            }else if(houseReport.getHouseType()==3){
                map=shopHouseSearchService.queryDetail(houseReport.getHouseId());
            }else {
                map=officeHouseSearchService.queryDetail(houseReport.getHouseId());
            }
            if(map.get("title")!=null){
                houseReport.setHouseName(map.get("title").toString());
            }
            if(map.get("img_url")!=null){
                houseReport.setHousePicture(map.get("img_url").toString());
            }
            houseReportService.insert(houseReport);
            return DataRes.success("举报成功");
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }
}
