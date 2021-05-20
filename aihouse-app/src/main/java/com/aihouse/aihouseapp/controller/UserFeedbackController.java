package com.aihouse.aihouseapp.controller;

import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousedao.bean.UserFeedback;
import com.aihouse.aihouseservice.users.UserFeedbackService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
public class UserFeedbackController {

    @Resource
    private UserFeedbackService userFeedbackService;

    /**
     * 用户反馈
     * @param content
     * @param reason
     * @param request
     * @return
     */
    @RequestMapping(value = "app/user/addFeedback",method = RequestMethod.POST)
    public DataRes addUserFeedback(String content,String reason,HttpServletRequest request){
        if(reason!=null&&content!=null){
            UserFeedback userFeedback=new UserFeedback();
            userFeedback.setContent(content);
            userFeedback.setReason(reason);
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            userFeedback.setUserId(Integer.parseInt(userId));
            userFeedbackService.insert(userFeedback);
            return DataRes.success("反馈成功");
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }
}

