package com.aihouse.aihousesys.controller.users;

import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousedao.bean.UserHistory;
import com.aihouse.aihousedao.bean.Users;
import com.aihouse.aihouseservice.users.UserHistoryService;
import com.aihouse.aihouseservice.users.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class SysUserHistoryController {

    @Resource
    private UserHistoryService userHistoryService;

    @Resource
    private UsersService usersService;

    /**
     * 跳转到房源的浏览记录
     * @param userHistory
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "userHistory/gotoList")
    public String gotoList(UserHistory userHistory, HttpServletRequest request, HttpServletResponse response){
        request.setAttribute("userHistory",userHistory);
        return "users/users_history_list";
    }


    /**
     * 分页获取房源浏览记录
     * @param userHistory
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("userHistory/selectAllByPaging")
    public DataRes selectAllByPaging(UserHistory userHistory,HttpServletRequest request,HttpServletResponse response){
        userHistory.setOrderByString(" order by createtime desc ");
        userHistory=userHistoryService.selectAllByPaging(userHistory);
        for(UserHistory u:(List<UserHistory>)userHistory.getRows()){
            Users users=new Users();
            users.setId(u.getUserId());
            users=usersService.selectByPrimaryKey(users);
            u.setUsers(users);
        }
        return DataRes.success(userHistory);
    }
}
