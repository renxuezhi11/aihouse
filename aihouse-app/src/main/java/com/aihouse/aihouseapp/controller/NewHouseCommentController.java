package com.aihouse.aihouseapp.controller;

import com.aihouse.aihouseapp.utils.RedisUtil;
import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.RedisConstants;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousecore.utils.keyword.BadWord;
import com.aihouse.aihousedao.bean.Appointment;
import com.aihouse.aihousedao.bean.NewHouse;
import com.aihouse.aihousedao.bean.NewHouseComment;
import com.aihouse.aihousedao.bean.NewHouseCommentPraise;
import com.aihouse.aihouseservice.AppointmentService;
import com.aihouse.aihouseservice.newhouse.NewHouseCommentPraiseService;
import com.aihouse.aihouseservice.newhouse.NewHouseCommentService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 楼盘点评
 */
@CrossOrigin
@RestController
public class NewHouseCommentController {

    @Resource
    private AppointmentService appointmentService;

    @Resource
    private NewHouseCommentService newHouseCommentService;


    @Resource
    private NewHouseCommentPraiseService newHouseCommentPraiseService;

    /**
     *判断用户是否可以点评楼盘
     * @return
     */
    @RequestMapping(value = "app/newHouse/isCommentOk",method = RequestMethod.POST)
    public DataRes isCommentOk(HttpServletRequest request,Integer houseId){
        String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
        Appointment appointment=new Appointment();
        appointment.setUserId(Integer.parseInt(userId));
        appointment.setStatus(3);
        appointment.setHouseId(houseId);
        appointment.setHouseType(1);
        List<Appointment> list=appointmentService.selectAll(appointment);
        Map<String,Object> map=new HashMap<>();
        if(list.size()>0){
            map.put("isOk",true);
            map.put("id",list.get(0).getId());
        }else{
            map.put("isOk",false);
            map.put("id",0);
        }
        return DataRes.success(map);
    }

    /**
     * 获取楼盘评论(分页)
     * @param houseId
     * @param request
     * @return
     */
    @RequestMapping(value = "app/newHouse/getHouseComment",method = RequestMethod.POST)
    public DataRes getHouseComment(Integer houseId,Integer page,Integer pageSize,HttpServletRequest request){
        if(houseId!=null) {
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            List<Map<String,Object>> list=newHouseCommentService.selectAllComment(houseId,Integer.parseInt(userId),page,pageSize);
            return DataRes.success(list);
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 写评论
     * @param newHouseComment
     * @param request
     * @return
     */
    @RequestMapping(value = "app/newHouse/publishNewHouseComment",method =RequestMethod.POST)
    public DataRes publishNewHouseComment(NewHouseComment newHouseComment,Integer appointmentId,HttpServletRequest request){
        String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
        newHouseComment.setUserId(Integer.parseInt(userId));
        if(BadWord.isContain(newHouseComment.getContent())){
            //过滤敏感词
            newHouseComment.setContent(BadWord.replace(newHouseComment.getContent(),"*"));
        }
        int i=newHouseCommentService.insertComment(newHouseComment);
        if(i>0){//写评论成功，更改预约表状态
            Appointment appointment=new Appointment();
            appointment.setId(appointmentId);
            appointment.setStatus(4);
            appointmentService.update(appointment);
            newHouseCommentService.updateNewHouseCommentCnt(newHouseComment.getNewHouseId());
            return DataRes.success("评论成功");
        }else{
            return DataRes.error(ResponseCode.PUBLISH_NEW_HOUSE_COMMENT_ERROR);
        }
    }

    /**
     * 点赞楼盘评论
     * @param commentId
     * @param request
     * @return
     */
    @RequestMapping(value = "app/addNewHousePraiseComment")
    public DataRes praiseComment(Integer commentId,HttpServletRequest request){
        if(commentId!=null){
            NewHouseComment newHouseComment=new NewHouseComment();
            newHouseComment.setId(commentId);
            newHouseComment=newHouseCommentService.selectByPrimaryKey(newHouseComment);
            if(newHouseComment!=null){
                String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
                NewHouseCommentPraise newHouseCommentPraise=new NewHouseCommentPraise();
                newHouseCommentPraise.setUserId(Integer.parseInt(userId));
                newHouseCommentPraise.setCommentId(commentId);
                List<NewHouseCommentPraise> list=newHouseCommentPraiseService.selectAll(newHouseCommentPraise);
                if(list!=null&&list.size()>0){
                    return DataRes.error(ResponseCode.NEW_HOUSE_COMMENT_PRAISE_ERROR);
                }else{
                    newHouseCommentPraiseService.insert(newHouseCommentPraise);
                    newHouseComment.setThumbsUp(newHouseComment.getThumbsUp()+1);
                    newHouseCommentService.update(newHouseComment);
                    return DataRes.success(newHouseComment.getThumbsUp());
                }

            }else{
                return DataRes.error(ResponseCode.DATA_ERROR);
            }
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }


    /**
     * 取消点赞楼盘评论
     * @param commentId
     * @param request
     * @return
     */
    @RequestMapping(value = "app/cancleNewHousePraiseComment")
    public DataRes canclePraiseComment(Integer commentId,HttpServletRequest request){
        if(commentId!=null){
            NewHouseComment newHouseComment=new NewHouseComment();
            newHouseComment.setId(commentId);
            newHouseComment=newHouseCommentService.selectByPrimaryKey(newHouseComment);
            if(newHouseComment!=null){
                String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
                NewHouseCommentPraise newHouseCommentPraise=new NewHouseCommentPraise();
                newHouseCommentPraise.setUserId(Integer.parseInt(userId));
                newHouseCommentPraise.setCommentId(commentId);
                List<NewHouseCommentPraise> list=newHouseCommentPraiseService.selectAll(newHouseCommentPraise);
                if(list!=null&&list.size()>0){
                    newHouseCommentPraiseService.deleteNewHouseCommentPraise(newHouseCommentPraise);
                    newHouseComment.setThumbsUp(newHouseComment.getThumbsUp()-1);
                    newHouseCommentService.update(newHouseComment);
                    return DataRes.success(newHouseComment.getThumbsUp());
                }else{
                    return DataRes.error(ResponseCode.DATA_ERROR);
                }
            }else{
                return DataRes.error(ResponseCode.DATA_ERROR);
            }
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }
}
