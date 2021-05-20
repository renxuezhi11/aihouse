package com.aihouse.aihousesys.controller.newhouse;

import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousedao.bean.NewHouse;
import com.aihouse.aihousedao.bean.NewHouseBroker;
import com.aihouse.aihousedao.bean.NewHouseImg;
import com.aihouse.aihousedao.bean.Users;
import com.aihouse.aihouseservice.newhouse.NewHouseBrokerService;
import com.aihouse.aihouseservice.users.UsersService;
import org.apache.catalina.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class NewHouseBrokerController {
    @Resource
    private NewHouseBrokerService newHouseBrokerService;

    @Resource
    private UsersService usersService;

    @RequestMapping("newHouseBroker/gotoList")
    @RequiresPermissions("newHouseBroker/gotoList")
    public String gotoList(Integer id, HttpServletRequest request){
        request.setAttribute("houseId",id);
        return "newhouse/newhouse_broker_list";
    }
    /**
     * 查询分页
     * @param newHouseBroker
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("newHouseBroker/selectAllByPaging")
    @ResponseBody
    public DataRes selectAll(NewHouseBroker newHouseBroker, HttpServletRequest request, HttpServletResponse response)throws Exception{
        newHouseBroker.setStatus(0);
        newHouseBroker.setOrderByString(" order by createtime desc");
        return DataRes.success(newHouseBrokerService.selectAllByPaging(newHouseBroker));
    }

    /**
     * 删除经纪人
     * @param newHouseBroker
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("newHouseBroker/deleteBroker")
    @RequiresPermissions("newHouseBroker/deleteBroker")
    @ResponseBody
    public DataRes deleteBroker(NewHouseBroker newHouseBroker, HttpServletRequest request, HttpServletResponse response){
        if(newHouseBroker.getId()!=null){
            newHouseBroker.setStatus(1);
            return DataRes.success(newHouseBrokerService.update(newHouseBroker));
        }
        return DataRes.success(null);
    }

    /**
     * 选择经纪人
     * @return
     */
    @RequestMapping("newHouseBroker/addBroker")
    @RequiresPermissions("newHouseBroker/addBroker")
    public String addBroker(){
        return "newhouse/broker_list";
    }

    /**
     * 获取所有经纪人
     * @param users
     * @return
     */
    @RequestMapping("newHouseBroker/selectAllBroker")
    @ResponseBody
    public DataRes selectAllBroker(Users users){
        users.setRole(6);
        return DataRes.success(usersService.selectAllByPaging(users));
    }

    @RequestMapping("newHouseBroker/addHouseBroker")
    @ResponseBody
    public DataRes addHouseBroker(Integer houseId,String brokerIds,HttpServletRequest request){
        if(brokerIds.split(",").length>0){
            for(String s:brokerIds.split(",")){
                NewHouseBroker newHouseBroker=new NewHouseBroker();
                newHouseBroker.setBrokerId(Integer.parseInt(s));
                newHouseBroker.setNewHouseId(houseId);
                List<NewHouseBroker> list=newHouseBrokerService.selectAll(newHouseBroker);
                if(list.size()==0){
                    newHouseBrokerService.insert(newHouseBroker);
                }else{
                    if(list.get(0).getStatus()==1){
                        list.get(0).setStatus(0);
                        newHouseBrokerService.update(list.get(0));
                    }
                }
            }
        }
        return DataRes.success("");
    }
}
