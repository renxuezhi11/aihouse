package com.aihouse.aihouseapp.controller;


import com.aihouse.aihouseapp.utils.RedisUtil;
import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousedao.bean.UserSearch;
import com.aihouse.aihouseservice.users.UserSearchService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class UserSearchController {

    @Resource
    private UserSearchService userSearchService;

    /**
     * 清空用户的搜索记录
     * @param type
     * @param request
     * @return
     */
    @RequestMapping(value = "app/user/deleteUserSearch",method = RequestMethod.POST)
    public DataRes deleteAllUserSearch(Integer type, HttpServletRequest request){
        if(type!=null){
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            this.userSearchService.deleteAllUserSearch(type,Integer.parseInt(userId));
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
        return DataRes.success("删除成功！");
    }

    /**
     * 首页搜索记录，热门搜索
     * @param request
     * @return
     */
    @RequestMapping(value = "app/getAllSearchHistory",method = RequestMethod.POST)
    public DataRes getAllSearchHisory(HttpServletRequest request){
        //搜索记录
        Map<String, Object> map = new HashMap<>();
        if (request.getHeader("token") != null && !request.getHeader("token").equals("")) {
            String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
            UserSearch userSearch = new UserSearch();
            userSearch.setUserId(Integer.parseInt(userId));
            userSearchService.selectAllByPaging(userSearch);
            List<Map<String, Object>> list = userSearchService.queryUserSearch(userSearch);
//        map.put("hotsearch",set);
            map.put("historysearch", list);
        }
        List<Map<String,Object>> list1=userSearchService.queryHotSearch();
        map.put("hotsearch",list1);
        return DataRes.success(map);

    }
}
