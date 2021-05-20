package com.aihouse.aihousesys.controller;

import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousedao.bean.Community;
import com.aihouse.aihousedao.bean.CommunityComment;
import com.aihouse.aihouseservice.CommunityCommentService;
import com.aihouse.aihouseservice.CommunityService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 圈子信息后台管理
 */
@Controller
public class SysCommnuityController {

    @Resource
    private CommunityService communityService;

    @Resource
    private CommunityCommentService communityCommentService;

    /**
     * 跳转到圈子审核页面
     * @return
     */
    @RequestMapping("community/gotoList")
    @RequiresPermissions("community/gotoList")
    public String gotoList(){
        return "sys/sys_community_list";
    }

    /**
     * 分页查询
     * @param community
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("community/selectAll")
    @ResponseBody
    public DataRes selectAll(Community community, HttpServletRequest request, HttpServletResponse response){
        return DataRes.success(communityService.selectAllByPaging(community));
    }

    /**
     * 导出
     * @param community
     * @param response
     * @param request
     */
    @RequestMapping("community/export")
    public void export(Community community ,HttpServletResponse response,HttpServletRequest request){

    }

    /**
     * 跳转到详情页面
     * @param community
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("community/gotoDetail")
    public String gotoDetail(Community community,HttpServletRequest request,HttpServletResponse response){
        if(community.getId()!=null){
            community=communityService.selectByPrimaryKey(community);
            List<String> list=new ArrayList<>();
            for(String str:community.getPicture().split(",")){
                list.add(str);
            }
            request.setAttribute("comm",community);
            request.setAttribute("imgList",list);
        }else{
            request.setAttribute("comm",community);
            request.setAttribute("imgList",null);
        }
        return "sys/sys_community_detail";
    }

    /**
     * 跳转到评论详情
     * @param communityComment
     * @param request
     * @return
     */
    @RequestMapping("community/gotoCommentDetail")
    public String gotoCommentDetail(CommunityComment communityComment,HttpServletRequest request){
        if(communityComment.getId()!=null){
            communityComment=communityCommentService.selectByPrimaryKey(communityComment);
            request.setAttribute("comment",communityComment);
        }
        return "sys/sys_community_comment_detail";
    }

    /**
     * 跳转到评论列表
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("community/gotoCommentList")
    public String gotoCommentList(Integer id,HttpServletRequest request){
        if(id!=null){
            CommunityComment communityComment=new CommunityComment();
            communityComment.setCommunityId(id);
            communityComment.setOrderByString(" order by createtime desc ");
            List<CommunityComment> list=communityCommentService.selectAll(communityComment);
            request.setAttribute("list",list);
        }
        return "sys/sys_community_comment_list";
    }

    /**
     * 管理员删除评论
     * @param communityComment
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("community/deleteComment")
    public DataRes deleteComment(CommunityComment communityComment,HttpServletRequest request){
        return DataRes.success(communityCommentService.deleteByPrimaryKey(communityComment));
    }

    /**
     * 保存
     * @param community
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("community/save")
    @ResponseBody
    public DataRes save(Community community,HttpServletResponse response,HttpServletRequest request ){
        return DataRes.success(communityService.update(community));
    }

    /**
     * 删除图片
     * @param community
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("community/deleteImg")
    @ResponseBody
    public DataRes deleteImg(Community community,HttpServletRequest request,HttpServletResponse response){
        Community community1=communityService.selectByPrimaryKey(community);
        List<String> list=new ArrayList<>();
        for(String str:community1.getPicture().split(",")){
            if(!str.equals(community.getPicture())){

                list.add(str);
            }
        }
        if(list.size()>0){
            community1.setPicture(String.join(",",list));
        }else{
            community1.setPicture(",");
        }
        return DataRes.success(communityService.update(community1));
    }
}
