package com.aihouse.aihouseapp.controller;


import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousedao.bean.UserHistory;
import com.aihouse.aihouseservice.users.UserHistoryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户浏览记录
 */
@CrossOrigin
@RestController
public class UserHistoryController {
    @Resource
    private UserHistoryService userHistoryService;

    /**
     * 获取用户的浏览记录
     * @param userHistory
     * @param request
     * @return
     */
    @RequestMapping(value = "app/user/getUserHistory",method = RequestMethod.POST)
    public DataRes getUserHistoryList(UserHistory userHistory, HttpServletRequest request){
        String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
        userHistory.setUserId(Integer.parseInt(userId));
        userHistory.setOrderByString(" order by createtime desc ");
        userHistoryService.selectAllByPaging(userHistory);
        return DataRes.success(userHistory);
    }

    /**
     * 删除浏览记录
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping(value = "app/user/deleteUserHistory",method = RequestMethod.POST)
    public DataRes deleteUserHistory(Integer [] ids,HttpServletRequest request){
        if(ids!=null){
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            userHistoryService.deleteUserHistory(ids,Integer.parseInt(userId));
            return DataRes.success("删除成功");
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }
}
