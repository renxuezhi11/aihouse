package com.aihouse.aihousesys.controller.newhouse;

import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ExcelUtils;
import com.aihouse.aihousecore.utils.ShiroMd5Utils;
import com.aihouse.aihousedao.bean.Area;
import com.aihouse.aihousedao.bean.NewHouse;
import com.aihouse.aihousedao.bean.SysUser;
import com.aihouse.aihouseservice.AreaService;
import com.aihouse.aihouseservice.newhouse.NewHouseSearchService;
import com.aihouse.aihouseservice.newhouse.NewHouseService;
import com.aihouse.aihousesys.utils.SessionConstant;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class NewHouseController {

    @Resource
    private NewHouseService newHouseService;

    @Resource
    private NewHouseSearchService newHouseSearchService;

    @Resource
    private AreaService areaService;

    /**
     * 跳转到新房列表
     * @return
     */
    @RequestMapping("newhouse/gotoList")
    @RequiresPermissions("newhouse/gotoList")
    public String gotoNewHouse(){
        return "newhouse/newhouse_list";
    }

    @RequestMapping("common/gotoMap")
    public String gotoMap(){
        return "common/common-map";
    }

    /**
     * 查询分页
     * @param newHouse
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("newhouse/selectAll")
    @RequiresPermissions("newhouse/selectAll")
    @ResponseBody
    public DataRes selectAll(NewHouse newHouse, HttpServletRequest request, HttpServletResponse response)throws Exception{
        if(newHouse.getHouseType()!=null){
            newHouse.setHouseType("%"+newHouse.getHouseType()+"%");
        }
        newHouse.setOrderByString(" order by createtime desc ");
        return DataRes.success(newHouseService.selectAllByPaging(newHouse));
    }

    /**
     * 跳转到新房详情页
     * @param request
     * @param response
     * @param newHouse
     * @return
     */
    @RequestMapping("newhouse/gotoDetail")
    @RequiresPermissions("newhouse/save")
    public String gotoDetail(HttpServletRequest request, HttpServletResponse response,NewHouse newHouse){
        if(newHouse.getId()!=null){
            try {
                newHouse = newHouseService.selectByPrimaryKey(newHouse);
                request.setAttribute("newhouse", newHouse);
                Area area = new Area();
                area.setParentId(0);
                area.setOrderByString("order by sort asc");
                request.setAttribute("provice", areaService.queryByCondition(area));
                area = new Area();
                area.setParentId(newHouse.getProvinceId());
                request.setAttribute("city", areaService.queryByCondition(area));
                area.setParentId(newHouse.getCityId());
                request.setAttribute("area", areaService.queryByCondition(area));
                area.setParentId(newHouse.getAreaId());
                request.setAttribute("strees", areaService.queryByCondition(area));
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            request.setAttribute("newhouse",newHouse);
            Area area=new Area();
            area.setParentId(0);
            area.setOrderByString("order by sort asc");
            request.setAttribute("provice",areaService.queryByCondition(area));
        }
        return "newhouse/newhouse_detail";
    }

    @RequestMapping("newhouse/getAreaList")
    @ResponseBody
    public DataRes getAreaList(Area area,HttpServletRequest request, HttpServletResponse response){
        return DataRes.success(areaService.queryByCondition(area));
    }

    /**
     * 保存 如果id存在则修改否则新增
     * @param newHouse
     * @return
     */
    @RequestMapping("newhouse/save")
    @RequiresPermissions("newhouse/save")
    @ResponseBody
    public DataRes save(NewHouse newHouse, HttpServletRequest request, HttpServletResponse response) {
        if(newHouse.getId()==null){
            try {
                return DataRes.success(newHouseService.insert(newHouse));
            }catch (Exception e){
                e.printStackTrace();
                return  DataRes.error();
            }
        }
        try {
            NewHouse newHouse1=newHouseService.selectByPrimaryKey(newHouse);
            int i=newHouseService.updateNewhouse(newHouse);
            if(newHouse1.getStatus()==1){
                newHouseSearchService.addNewHouseIndex(newHouse.getId());
            }else{
                newHouseSearchService.deleteNewHouseIndex(newHouse.getId());
            }
            return DataRes.success(i);
        }catch (Exception e){
            e.printStackTrace();
            return DataRes.error();
        }
    }

    /**
     * 修改状态
     * @param newHouse
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("newhouse/updateStatus")
    @ResponseBody
    @RequiresPermissions("newhouse/save")
    public DataRes updateStatus(NewHouse newHouse, HttpServletRequest request, HttpServletResponse response){
//        SysUser user = (SysUser) request.getSession().getAttribute(SessionConstant.sysUserSession);
        //异步任务处理索引
        if(newHouse.getStatus()==1){//添加索引
            newHouseSearchService.addNewHouseIndex(newHouse.getId());
        }else{//删除索引
            newHouseSearchService.deleteNewHouseIndex(newHouse.getId());
        }
        return DataRes.success(newHouseService.updateNewhouse(newHouse));
    }

    /**
     * 导出
     * @param newHouse
     * @param request
     * @param response
     */
    @RequestMapping("newhouse/export")
    public void export(NewHouse newHouse,HttpServletRequest request,HttpServletResponse response){
        List<NewHouse> list=newHouseService.selectAll(newHouse);
        Map<String, String> header = new LinkedHashMap<>();
        header.put("id","房源编号");
        header.put("name","楼盘名称");
        header.put("alias","别名");
        header.put("address","地址");
        header.put("averagePrice","均价");
        header.put("downPaymentScale","首付比例");
        header.put("houseType","产品类型");
        header.put("coveredType","建筑类型");
        header.put("feature","项目特色");
        header.put("fixture","装修情况");
        header.put("propertyYear","产权年限");
        header.put("developer","开发商");
        header.put("sellStage_","销售进度");
        header.put("salesOfficeAddress","售楼地址");
        header.put("telephone","咨询电话");
        header.put("floorSpace","占地面积单位m2");
        header.put("coveredArea","建筑面积单位m2");
        header.put("plotRatio","容积率");
        header.put("greeningRate","绿化率");
        header.put("carPlaceUp","停车位地上");
        header.put("carPlaceDown","停车位地下");
        header.put("building","楼栋数");
        header.put("total_house","总户数");
        header.put("propertyCompany","物业公司");
        header.put("propertyFee","物业费");
        header.put("createtime_","添加时间");
        header.put("status_","上架状态");
        ExcelUtils.exportExcel("新房列表",header,list,response,request);
    }

}
