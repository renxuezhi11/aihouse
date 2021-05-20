package com.aihouse.aihousesys.controller.users;


import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousedao.bean.UserSpreadBean;
import com.aihouse.aihousedao.bean.UserSpreadLog;
import com.aihouse.aihousedao.bean.Users;
import com.aihouse.aihouseservice.users.UserSpreadLogService;
import com.aihouse.aihouseservice.users.UsersService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class UserSpreadController {

    @Resource
    private UserSpreadLogService userSpreadLogService;

    @Resource
    private UsersService usersService;

    @RequestMapping("userSpread/gotoStatic")
    public String gotoStatic(){
        return "users/users_spread_static";
    }

    /**
     * 查询
     * @param userSpreadLog
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("userSpread/selectAllByPaging")
    @RequiresPermissions("userSpread/selectAll")
    @ResponseBody
    public DataRes selectAll(UserSpreadLog userSpreadLog, HttpServletRequest request, HttpServletResponse response){
        Users users=new Users();
        users.setIsSpread(1);
        users.setPage(userSpreadLog.getPage());
        users.setPageSize(userSpreadLog.getPageSize());
        users.setOrderByString(" order by id desc ");
        users=usersService.selectAllByPaging(users);
        UserSpreadBean userSpreadBean=new UserSpreadBean();
        List list=new ArrayList();
        userSpreadBean.setTotal(users.getTotal());
        userSpreadBean.setPage(users.getPage());
        userSpreadBean.setPageSize(users.getPageSize());
        for(Users u:(List<Users>)users.getRows()){
            UserSpreadBean userSpreadBean1=new UserSpreadBean();
            userSpreadLog.setParentId(u.getId());
            Map<String,Object> map=userSpreadLogService.selectCnt(userSpreadLog);
            userSpreadBean1.setTotal(Integer.parseInt(map.get("total").toString()));
            userSpreadBean1.setToday(Integer.parseInt(map.get("today").toString()));
            userSpreadBean1.setId(u.getId());
            userSpreadBean1.setNickname(u.getNickname());
            list.add(userSpreadBean1);
        }
        userSpreadBean.setRows(list);
        return DataRes.success(userSpreadBean);
    }

    @RequestMapping("userSpread/gotoSpreadList")
    public String gotoSpreadList(Integer userId,HttpServletResponse response,HttpServletRequest request){
        request.setAttribute("userId",userId);
        return "users/users_spread_list";
    }

    @RequestMapping("userSpread/selectSpreadList")
    @ResponseBody
    public DataRes selectSpreadList(UserSpreadLog userSpreadLog,HttpServletRequest request,HttpServletResponse response){
        userSpreadLog.setOrderByString(" order by createtime desc ");
        userSpreadLog=userSpreadLogService.selectAllByPaging(userSpreadLog);
        for(UserSpreadLog u:(List<UserSpreadLog>)userSpreadLog.getRows()){
            Users users=new Users();
            users.setId(u.getUserId());
            users=usersService.selectByPrimaryKey(users);
            u.setUsers(users);
        }
        return DataRes.success(userSpreadLog);
    }
}
