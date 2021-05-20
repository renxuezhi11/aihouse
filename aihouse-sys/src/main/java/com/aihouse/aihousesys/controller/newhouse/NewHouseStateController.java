package com.aihouse.aihousesys.controller.newhouse;

import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousedao.bean.NewHouseImg;
import com.aihouse.aihousedao.bean.NewHouseState;
import com.aihouse.aihouseservice.newhouse.NewHouseStateService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class NewHouseStateController {


    @Resource
    private NewHouseStateService newHouseStateService;

    @RequestMapping("newhousestate/gotoList")
    public String gotohouseImg(Integer id, HttpServletRequest request, HttpServletResponse response){
        if(id!=null){
            try {
                request.setAttribute("houseId",id);
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            request.setAttribute("houseId",id);
        }
        return "newhouse/newhousestate_list";
    }

    /**
     * 分页获取动态信息
     * @param newHouseState
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("newhousestate/selectAll")
    @ResponseBody
    public DataRes selectAll(NewHouseState newHouseState,HttpServletRequest request,HttpServletResponse response){
        if(newHouseState!=null){
            try {
                newHouseState.setOrderByString("order by createtime asc");
                newHouseState=newHouseStateService.selectAllByPaging(newHouseState);
                return DataRes.success(newHouseState);
            }catch (Exception e){
                return DataRes.error();
            }
        }else{
            return DataRes.error();
        }
    }

    /**
     * 跳转到动态详情页
     * @param newHouseState
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("newhousestate/gotoDetail")
    public String gotoDetail(NewHouseState newHouseState,HttpServletRequest request,HttpServletResponse response){
        if(newHouseState.getId()!=null){
            request.setAttribute("newhousestate",newHouseStateService.selectByPrimaryKey(newHouseState));
        }else{
            request.setAttribute("newhousestate",newHouseState);
        }
        return "newhouse/newhousestate_detail";
    }

    /**
     * 新增或者修改
     * @param newHouseState
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("newhousestate/save")
    @ResponseBody
    public DataRes save(NewHouseState newHouseState,HttpServletRequest request,HttpServletResponse response){
        if(newHouseState.getId()==null){
            try {
                return DataRes.success(newHouseStateService.insert(newHouseState));
            }catch (Exception e){
                e.printStackTrace();
                return DataRes.error();
            }
        }else{
            return DataRes.success(newHouseStateService.update(newHouseState));
        }
    }
}
