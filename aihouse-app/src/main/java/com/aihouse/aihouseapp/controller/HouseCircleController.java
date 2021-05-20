package com.aihouse.aihouseapp.controller;

import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousecore.utils.keyword.BadWord;
import com.aihouse.aihousedao.bean.*;
import com.aihouse.aihouseservice.CommunityCommentService;
import com.aihouse.aihouseservice.CommunityPraiseService;
import com.aihouse.aihouseservice.CommunityService;
import com.aihouse.aihouseservice.users.UsersService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class HouseCircleController {

    private static final Integer ZERO = 0;

    @Resource
    UsersService usersService;

    @Resource
    CommunityService communityService;

    @Resource
    CommunityCommentService communityCommentService;

    @Resource
    CommunityPraiseService communityPraiseService;


    /**
     * 房圈子列表
     **/
    @RequestMapping("app/housecircle/list")
    public DataRes list(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        //结果集
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        //从Header中获取用户ID
        String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
        //实例化圈子条件作为查询条件
        Community community = new Community();
        //已通过的圈子
        community.setStatus(Community.REVIEWED_PASS);

        community.setUserId(Integer.parseInt(userId));
        //当前页码
        community.setPage(Integer.parseInt(request.getParameter("page")));
        //当前页面条数
        community.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
        //新建的查询方法根据以上条件进行分页条件查询,屏蔽的用户动态看不到
        List<Community> list = communityService.selectAppIndexScreenList(community);
        //遍历查询集
        for(Community resultCommunity:list){
            Map<String,Object> resultMap = new HashMap<>();
            //发布人信息查询
            Users user = new Users();
            user.setId(resultCommunity.getUserId());
            user = usersService.selectByPrimaryKey(user);
            if(user!=null) {
                //发布人昵称
                resultMap.put("nickname", user.getNickname());
                //发布人头像
                resultMap.put("userimg", user.getUserphoto());
                //发布人用户Id
                resultMap.put("userid", user.getId() + "");
            }
            //圈子主键
            resultMap.put("id",resultCommunity.getId()+"");
            //发布内容
            resultMap.put("text",resultCommunity.getContent());
            //发布时间
            resultMap.put("time",DateUtils.returnZhiQian(resultCommunity.getCreatetime())+"");
            //图片判空
            if(resultCommunity.getPicture()==null){
                resultMap.put("img","");
            }else{
                //图片拼接
                List<String> imgList = new ArrayList<>();
                for (String singleImg : resultCommunity.getPicture().split(",")){
                    imgList.add(singleImg);
                }
                //图片
                resultMap.put("img",imgList);
            }
            //点赞相关
            Map<String,String> gotLikeMap = new HashMap<>();
            //根据圈子ID查询点赞的数据条数
            resultCommunity = communityService.selectCommunityAndCommunityPraiseByCondition(resultCommunity).get(ZERO);
            //点赞数量
            gotLikeMap.put("count",resultCommunity.getCommunityPraiseList().size()+"");
            //设置条件查询登陆用户是否点赞
            CommunityPraise communityPraise = new CommunityPraise();
            communityPraise.setUserId(Integer.parseInt(userId));
            communityPraise.setCommunityId(resultCommunity.getId());
            List<CommunityPraise> getlikeList = communityPraiseService.selectByCondition(communityPraise);
            //判断结果集的条数来返回结果
            if(getlikeList.size()==0){
                gotLikeMap.put("ifUserLike","0");
            }else{
                gotLikeMap.put("ifUserLike","1");
            }
            //放置点赞的结果集
            resultMap.put("getLike",gotLikeMap);
            user.setId(resultCommunity.getUserId());
            //评论相关
            List<Map<String,Object>> commentMapList = new ArrayList<>();
            //根据圈子的现有条件查询评论的数据集
            resultCommunity = communityService.selectCommunityAndCommunityCommentByCondition(resultCommunity).get(ZERO);
            //评论数据集
            List<CommunityComment> communityCommentList  = resultCommunity.getCommunityCommentList();
            CommunityComment queryComment = new CommunityComment();
            queryComment.setCommunityId(resultCommunity.getId());
            //查询该圈子下前两条评论或者回复
            List<CommunityComment> resultcomment = communityCommentService.initDao().indexList(queryComment);
            //评论回复计数
            Integer replyCount = communityCommentService.initDao().countCommentReplayByCommunityId(queryComment);
            //遍历评论集合以便于传参
            for(CommunityComment communityComment:resultcomment){
                //评论Map便于构造参数
                Map<String,Object> commentMap = new HashMap<>();
                /**
                 *!!!!BUG:
                 *!!!!BUG:
                 *!!!BUG:
                 * 查询集不知道何故返回空集时依旧遍历集合导致报错
                 *!!!!BUG:
                 *!!!!BUG:
                 *!!!!BUG:
                 */
                if(communityComment==(null)){
                    continue;
                }
                //查询当前评论的用户信息
                user.setId(communityComment.getUserId());
                user = usersService.selectByPrimaryKey(user);
                //评论ID
                commentMap.put("id",communityComment.getId()+"");
                //防止部分脏数据遍历导致报错或者评论时用户数据未传进来起始时只有评论内容
                if(user!=null) {
                    //用户名
                    commentMap.put("username", user.getNickname() + "");
                    //用户ID
                    commentMap.put("userid", user.getId() + "");
                }
                //判断评论类型----评论、回复 // 1 为评论，2为回复
                if(communityComment.getType().equals(CommunityComment.TYPE_COMMENT)){
                    //评论标的人
                    commentMap.put("target","");
                    //评论
                    commentMap.put("type",CommunityComment.TYPE_COMMENT+"");
                }else{
                    //评论标的人ID
                    user.setId(communityComment.getToUid());
                    user = usersService.selectByPrimaryKey(user);
                    //评论标的人
                    commentMap.put("target",user.getNickname());
                    //回复
                    commentMap.put("type",CommunityComment.TYPE_REPLAY+"");
                }
                //内容文本
                commentMap.put("commenttext",communityComment.getContent());
                commentMapList.add(commentMap);
            }
            //评论以及回复数量
            resultMap.put("totalcount",communityCommentList.size()+replyCount+"");
            //圈子对应的----评论集
            resultMap.put("comment",commentMapList);
            resultList.add(resultMap);
        }
        return DataRes.success(resultList);
    }
    /**
     * 房圈子添加
     **/
    @RequestMapping("app/housecircle/insert")
    public DataRes insert(HttpServletRequest request, HttpServletResponse response){
        //结果集
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
        Community community = new Community();
        //发送人
        community.setUserId(Integer.parseInt(userId));
        String context = null;
        //判断内容是否为空
        if(request.getParameter("context")!=null&&!request.getParameter("context").equals("")){
            context = request.getParameter("context");
            //判断是否有敏感词
            if(BadWord.isContain(context)){
                //将敏感词转换为*
                context = BadWord.replace(context,"*");
            }
        }
        //发送内容
        community.setContent(context);
        //获取图片数组
        String [] imgurl = request.getParameterValues("imgurl");
        //判断图片路径是否为空
        if(imgurl==null){
        }
        //合并图片数组成一个字符串进行存储
        else {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < imgurl.length; i++) {
                sb.append(imgurl[i] + ",");
            }
            community.setPicture(sb.toString());
        }
        //设置圈子状态
        community.setStatus(Community.REVIEWED_PASS);
        //调用INSERT方法
        Integer count = communityService.insert(community);
        if(count==1){
            return DataRes.success(resultList);
        }else{
            return DataRes.error("500","添加房圈子失败");
        }
    }
    /**
     * 房圈子评论
     **/
    @RequestMapping("app/housecircle/comment")
    public DataRes comment(HttpServletRequest request, HttpServletResponse response){
        //结果集
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
        //实例化评论
        CommunityComment communityComment = new CommunityComment();
        communityComment.setUserId(Integer.parseInt(userId));
        Community community = new Community();
        //设置圈子ID
        community.setId(Integer.parseInt(request.getParameter("communityid")));
        //查找是否有该圈子
        community = communityService.selectByPrimaryKey(community);
        if(community.getStatus()==3||community.getStatus()==2){
           return DataRes.error("400","该房圈子不存在");
        }
        if(!community.getStatus().equals(Community.REVIEWED_PASS)){
           return DataRes.error("500","该圈子未通过审核");
        }
        //设置圈子ID
        communityComment.setCommunityId(community.getId());
        //设置Target标的人Id
        communityComment.setToUid(community.getUserId());
        //获取文本
        String content = request.getParameter("content");
        //判断敏感词
        if(BadWord.isContain(content)){
            //过滤敏感词
            content = BadWord.replace(content,"*");
        }
        //设置文本
        communityComment.setContent(content);
        //设置类型
        communityComment.setType(CommunityComment.TYPE_COMMENT);
        //添加评论
        Integer count = communityCommentService.insert(communityComment);
        if(count==1){
            return DataRes.success(communityComment.getId());
        }else{
            return DataRes.error("500","添加评论失败");
        }
    }
    /**
     * 房圈子点赞
     *
     **/
    @RequestMapping("app/housecircle/rasie")
    @Transactional
    public DataRes rasie(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
        CommunityPraise communityPraise = new CommunityPraise();
        communityPraise.setCommunityId(Integer.parseInt(request.getParameter("communityid")));
        communityPraise.setUserId(Integer.parseInt(userId));
        //查找该圈子是否已经被该用户点赞
        List<CommunityPraise> communityPraiseList = communityPraiseService.selectByCondition(communityPraise);
        Community community = new Community();
        community.setId(Integer.parseInt(request.getParameter("communityid")));
        //查找该圈子信息
        community = communityService.selectByPrimaryKey(community);
        if(community.getStatus()==3||community.getStatus()==2){
            return DataRes.error("400","该圈子已被删除了");
        }
        if(communityPraiseList.size()>0){
            return DataRes.error("502","该用户已经点过赞了");
        }else if (!community.getStatus().equals(Community.REVIEWED_PASS)){
            return DataRes.error("500","该圈子未通过审核");
        }
        //调用点赞INSERT方法
        Integer count = communityPraiseService.insert(communityPraise);
        if(count==1){
            //维护圈子表的ThumbsUp字段----点赞数量
            community.setThumbsUp(community.getThumbsUp()+1);
            //判断是否更新成功
            if(communityService.update(community) == 0){
                throw new Exception("更新Community失败");
            }
            return DataRes.success(resultList);
        }else{
            return DataRes.error("500","点赞失败");
        }
    }
    /**
     * 房圈子回复评论
     **/
    @RequestMapping("app/housecircle/replay")
    public DataRes replay(HttpServletRequest request, HttpServletResponse response){
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
        CommunityComment communityComment = new CommunityComment();
        communityComment.setId(Integer.parseInt(request.getParameter("commentid")));
        //查找该评论状态
        communityComment = communityCommentService.selectByPrimaryKey(communityComment);
        if(communityComment==null){
            return DataRes.error("400","该评论已被删除");
        }
        if(communityComment.getUserId().equals(Integer.parseInt(userId))){
            return DataRes.error("500","用户禁止回复自己的评论");
        }
        //实例化圈子评论进行回复
        CommunityComment queryComment = new CommunityComment();
        queryComment.setCommunityId(communityComment.getCommunityId());
        //回复发起人
        queryComment.setUserId(Integer.parseInt(userId));
        //设置Target参数---回复指向用户
        queryComment.setToUid(communityComment.getUserId());
        //评论表中Type    1---评论   2---回复
        queryComment.setType(CommunityComment.TYPE_REPLAY);
        String context = request.getParameter("context");
        //判断敏感词
        if(BadWord.isContain(context)){
            //过滤敏感词
            context = BadWord.replace(context,"*");
        }
        queryComment.setContent(context);
        //调用回复的INSERT方法
        Integer count = communityCommentService.insert(queryComment);
        if(count == 1){
            return DataRes.success(queryComment.getId());
        }else{
            return DataRes.error("500","回复评论失败请稍候重试");
        }
    }
    /**
     * 房圈子取消点赞
     *
     **/
    @RequestMapping("app/housecircle/cancle")
    public DataRes cancle(HttpServletRequest request, HttpServletResponse response){
        String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
        Integer circleId = Integer.parseInt(request.getParameter("communityid"));
        //设置条件查询圈子信息
        Community communityqb = new Community();
        communityqb.setId(circleId);
        communityqb = communityService.selectByPrimaryKey(communityqb);
        if(communityqb.getStatus()==3||communityqb.getStatus()==2){
            return DataRes.error("400","该圈子已被删除");
        }
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        //设置条件查询点赞表是否有该点赞信息
        CommunityPraise communityPraise = new CommunityPraise();
        communityPraise.setUserId(Integer.parseInt(userId));
        communityPraise.setCommunityId(circleId);
        List<CommunityPraise> communityPraiseList = communityPraiseService.selectByCondition(communityPraise);
        if(communityPraiseList.size()==0){
            return DataRes.error("400","用户没有点赞过");
        }
        //调用点赞表的DELETE方法
        Integer count = communityPraiseService.deleteByPrimaryKey(communityPraiseList.get(ZERO));
        if(count == 1){
            //维护房圈子表
            Community community = new Community();
            community.setId(circleId);
            community = communityService.selectByPrimaryKey(community);
            community.setThumbsUp(community.getThumbsUp()-1);
            //判断是否维护成功
            if(communityService.update(community)==0){
                try {
                    throw new Exception("维护房圈子表失败");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return DataRes.success(resultList);
        }else{
            return DataRes.error("500","取消点赞失败");
        }
    }

    /**
     * 自己的房圈子
     * */
    @RequestMapping("app/housecircle/mycircle")
    public DataRes MyCircle(HttpServletRequest request, HttpServletResponse response){
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
        Community community = new Community();
        community.setUserId(Integer.parseInt(userId));
        community.setPage(Integer.parseInt(request.getParameter("page")));
        community.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
        community.setStatus(Community.REVIEWED_PASS);
        List<Community> list = communityService.selectAppIndexList(community);
        for(Community resultCommunity:list){
            Map<String,Object> resultMap = new HashMap<>();
            //发布人昵称
            Users user = new Users();
            user.setId(resultCommunity.getUserId());
            user = usersService.selectByPrimaryKey(user);
            resultMap.put("nickname",user.getNickname());
            //发布人头像
            resultMap.put("userimg",user.getUserphoto());
            //发布人用户Id
            resultMap.put("userid",user.getId()+"");
            //圈子主键
            resultMap.put("id",resultCommunity.getId()+"");
            //发布内容
            resultMap.put("text",resultCommunity.getContent());
            //发布时间
            try {
                resultMap.put("time",DateUtils.returnZhiQian(resultCommunity.getCreatetime())+"");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //图片拼接
            List<String> imgList = new ArrayList<>();
            if(resultCommunity.getPicture()==null){
                resultMap.put("img","");
            }else{
                for (String singleImg : resultCommunity.getPicture().split(",")){
                    imgList.add(singleImg);
                }
                //图片
                resultMap.put("img",imgList);
            }
            //点赞相关
            Map<String,String> gotLikeMap = new HashMap<>();
            resultCommunity = communityService.selectCommunityAndCommunityPraiseByCondition(resultCommunity).get(ZERO);
            //点赞数量
            gotLikeMap.put("count",resultCommunity.getCommunityPraiseList().size()+"");
            //登陆用户是否点赞
            CommunityPraise communityPraise = new CommunityPraise();
            communityPraise.setUserId(Integer.parseInt(userId));
            communityPraise.setCommunityId(resultCommunity.getId());
            List<CommunityPraise> getlikeList = communityPraiseService.selectByCondition(communityPraise);
            if(getlikeList.size()==0){
                gotLikeMap.put("ifUserLike","0");
            }else{
                gotLikeMap.put("ifUserLike","1");
            }
            resultMap.put("getLike",gotLikeMap);
            user.setId(resultCommunity.getUserId());
            //评论相关
            List<Map<String,Object>> commentMapList = new ArrayList<>();
            resultCommunity = communityService.selectCommunityAndCommunityCommentByCondition(resultCommunity).get(ZERO);
            List<CommunityComment> communityCommentList  = resultCommunity.getCommunityCommentList();
            CommunityComment queryComment = new CommunityComment();
            queryComment.setCommunityId(resultCommunity.getId());
            List<CommunityComment> resultcomment = communityCommentService.initDao().indexList(queryComment);
            //评论回复计数
            Integer replyCount = communityCommentService.initDao().countCommentReplayByCommunityId(queryComment);
            for(CommunityComment communityComment:resultcomment){
                if(communityComment==null){
                    continue;
                }
                Map<String,Object> commentMap = new HashMap<>();
                user.setId(communityComment.getUserId());
                user = usersService.selectByPrimaryKey(user);
                commentMap.put("id",communityComment.getId()+"");
                commentMap.put("username",user.getNickname()+"");
                commentMap.put("userid",user.getId()+"");
                if(communityComment.getType().equals(CommunityComment.TYPE_COMMENT)){
                    commentMap.put("target","");
                }else{
                    user.setId(communityComment.getToUid());
                    user = usersService.selectByPrimaryKey(user);
                    commentMap.put("target",user.getNickname());
                }
                commentMap.put("type",communityComment.getType().toString());// 1 为评论，2为回复
                commentMap.put("commenttext",communityComment.getContent());
                commentMapList.add(commentMap);
            }
            resultMap.put("totalcount",communityCommentList.size()+replyCount+"");
            resultMap.put("comment",commentMapList);
            resultList.add(resultMap);
        }
        return DataRes.success(resultList);
    }

    /**
     *单独一个好友的房圈子
     * */
    @RequestMapping("app/housecircle/singlecircle")
    public DataRes SingleCircle(HttpServletRequest request, HttpServletResponse response){
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
        Community community = new Community();
        community.setUserId(Integer.parseInt(request.getParameter("userid")));
        community.setPage(Integer.parseInt(request.getParameter("page")));
        community.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
        community.setStatus(Community.REVIEWED_PASS);
        List<Community> list = communityService.selectAppIndexList(community);
        for(Community resultCommunity:list){
            Map<String,Object> resultMap = new HashMap<>();
            //发布人昵称
            Users user = new Users();
            user.setId(resultCommunity.getUserId());
            user = usersService.selectByPrimaryKey(user);
            resultMap.put("nickname",user.getNickname());
            //发布人头像
            resultMap.put("userimg",user.getUserphoto());
            //发布人用户Id
            resultMap.put("userid",user.getId()+"");
            //圈子主键
            resultMap.put("id",resultCommunity.getId()+"");
            //发布内容
            resultMap.put("text",resultCommunity.getContent());
            //发布时间
            try {
                resultMap.put("time",DateUtils.returnZhiQian(resultCommunity.getCreatetime())+"");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //图片拼接
            List<String> imgList = new ArrayList<>();
            if(resultCommunity.getPicture()==null){
                resultMap.put("img","");
            }else{
                for (String singleImg : resultCommunity.getPicture().split(",")){
                    imgList.add(singleImg);
                }
                //图片
                resultMap.put("img",imgList);
            }
            //点赞相关
            Map<String,String> gotLikeMap = new HashMap<>();
            resultCommunity = communityService.selectCommunityAndCommunityPraiseByCondition(resultCommunity).get(ZERO);
            //点赞数量
            gotLikeMap.put("count",resultCommunity.getCommunityPraiseList().size()+"");
            //登陆用户是否点赞
            CommunityPraise communityPraise = new CommunityPraise();
            communityPraise.setUserId(Integer.parseInt(userId));
            communityPraise.setCommunityId(resultCommunity.getId());
            List<CommunityPraise> getlikeList = communityPraiseService.selectByCondition(communityPraise);
            if(getlikeList.size()==0){
                gotLikeMap.put("ifUserLike","0");
            }else{
                gotLikeMap.put("ifUserLike","1");
            }
            resultMap.put("getLike",gotLikeMap);
            user.setId(resultCommunity.getUserId());
            //评论相关
            List<Map<String,Object>> commentMapList = new ArrayList<>();
            resultCommunity = communityService.selectCommunityAndCommunityCommentByCondition(resultCommunity).get(ZERO);
            List<CommunityComment> communityCommentList  = resultCommunity.getCommunityCommentList();
            CommunityComment queryComment = new CommunityComment();
            queryComment.setCommunityId(resultCommunity.getId());
            List<CommunityComment> resultcomment = communityCommentService.initDao().indexList(queryComment);
            //评论回复计数
            Integer replyCount = communityCommentService.initDao().countCommentReplayByCommunityId(queryComment);
            for(CommunityComment communityComment:resultcomment){
                if(communityComment==null){
                    continue;
                }
                Map<String,Object> commentMap = new HashMap<>();
                user.setId(communityComment.getUserId());
                user = usersService.selectByPrimaryKey(user);
                commentMap.put("id",communityComment.getId()+"");
                commentMap.put("username",user.getNickname()+"");
                commentMap.put("userid",user.getId()+"");
                if(communityComment.getType().equals(CommunityComment.TYPE_COMMENT)){
                    commentMap.put("target","");
                }else{
                    user.setId(communityComment.getToUid());
                    user = usersService.selectByPrimaryKey(user);
                    commentMap.put("target",user.getNickname());
                }
                commentMap.put("type",communityComment.getType().toString());// 1 为评论，2为回复
                commentMap.put("commenttext",communityComment.getContent());
                commentMapList.add(commentMap);
            }
            resultMap.put("totalcount",communityCommentList.size()+replyCount+"");
            resultMap.put("comment",commentMapList);
            resultList.add(resultMap);
        }
        return DataRes.success(resultList);
    }



    /**
     * 删除房圈子
     *
     **/
    @RequestMapping("app/housecircle/deletecircle")
    public DataRes deleteCircle(HttpServletRequest request, HttpServletResponse response){
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
        Integer askId = Integer.parseInt(request.getParameter("id"));
        //设置条件查找房圈子
        Community community = new Community();
        community.setId(askId);
        community = communityService.selectByPrimaryKey(community);
        //判断是否登录用户所发布方能对其进行操作
        if(!community.getUserId().equals(Integer.parseInt(userId))){
            return DataRes.error("500","禁止对他人圈子进行删除");
        }
        //设置圈子状态
        community.setStatus(Community.REVIEWED_DELETE);
        if(communityService.update(community)==ZERO){
            return DataRes.error("502","删除圈子失败");
        }
        //设置条件查找该评论集
       /* CommunityComment communityComment=new CommunityComment();
        communityComment.setCommunityId(community.getId());
        List<CommunityComment> commentList = communityCommentService.selectByCondition(communityComment);
        for(CommunityComment query:commentList){
            //调用Comment的DELETE方法
            communityCommentService.deleteCommentAndCommentReply(query);
        }*/
       //删除评论
        communityCommentService.deleteCommunityCommentByCommentId(community.getId());
        //设置条件查找该点赞集
        /*CommunityPraise communityPraise = new CommunityPraise();
        communityPraise.setCommunityId(community.getId());
        List<CommunityPraise> communityPraiseList = communityPraiseService.selectByCondition(communityPraise);
        for(CommunityPraise praise:communityPraiseList){
            //调用点赞的DELETE方法
            communityPraiseService.deleteByPrimaryKey(praise);
        }*/
        communityPraiseService.deleteCommunityPraiseByCommentId(community.getId());
        return DataRes.success(resultList);
    }
    /**
     * 删除评论
     *
     **/
    @RequestMapping("app/housecircle/deletecomment")
    public DataRes deleteComment(HttpServletRequest request, HttpServletResponse response){
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
        //获取评论的主键
        Integer replyId = Integer.parseInt(request.getParameter("id"));
        CommunityComment communityComment = new CommunityComment();
        communityComment.setId(replyId);
        //判断该评论是登录用户所创建
        communityComment = communityCommentService.selectByPrimaryKey(communityComment);
        if(!communityComment.getUserId().equals(Integer.parseInt(userId))){
            return DataRes.error("500","禁止对他人评论进行删除");
        }
        //调用评论的DELETE方法
        if(communityCommentService.deleteCommentAndCommentReply(communityComment)==ZERO){
            return DataRes.error("502","删除评论失败");
        }
        return DataRes.success(resultList);
    }
    /**
     * 删除评论回复
     *
     **/
    @RequestMapping("app/housecircle/deletereplay")
    public DataRes deleteReplay(HttpServletRequest request, HttpServletResponse response){
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
        //获取评论回复ID
        Integer replayId = Integer.parseInt(request.getParameter("id"));
        CommunityComment communityComment = new CommunityComment();
        communityComment.setId(replayId);
        //根据条件查询评论回复
        communityComment = communityCommentService.selectByPrimaryKey(communityComment);
        if(!communityComment.getUserId().equals(Integer.parseInt(userId))){
            return DataRes.error("500","禁止对他人评论进行删除");
        }
        //调用评论的DELETE方法
        if(communityCommentService.deleteByPrimaryKey(communityComment)==ZERO){
            return DataRes.error("502","删除回复失败");
        }
        return DataRes.success(resultList);
    }

    /**
     * 圈子详情
     * */
    @RequestMapping("app/housecircle/detail")
    public DataRes Detail(HttpServletRequest request, HttpServletResponse response){
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
        //根据条件查询该圈子
        Community community = new Community();
        community.setId(Integer.parseInt(request.getParameter("communityid")));
        List<Community> communityList = communityService.selectCommunityAndCommunityCommentByCondition(community);
        community = communityList.get(ZERO);
        Map<String,Object> resultMap = new HashMap<>();
        //发布人昵称
        Users user = new Users();
        user.setId(community.getUserId());
        user = usersService.selectByPrimaryKey(user);
        resultMap.put("nickname",user.getNickname());
        //发布人头像
        resultMap.put("userimg",user.getUserphoto());
        //发布人ID
        resultMap.put("userid",user.getId());
        //圈子主键
        resultMap.put("id",community.getId()+"");
        //发布内容
        resultMap.put("text",community.getContent());
        //圈子状态
        resultMap.put("status",community.getStatus()+"");
        //发布时间
        try {
            resultMap.put("time",DateUtils.returnZhiQian(community.getCreatetime())+"");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //图片拼接
        List<String> imgList = new ArrayList<>();
        for (String singleImg : community.getPicture().split(",")){
            imgList.add(singleImg);
        }
        //图片
        resultMap.put("img",imgList);
        //评论数量
        resultMap.put("count",community.getCommunityCommentList().size()+"");
        //点赞相关
        Map<String,String> gotLikeMap = new HashMap<>();
        community = communityService.selectCommunityAndCommunityPraiseByCondition(community).get(ZERO);
        //点赞数量
        gotLikeMap.put("count",community.getCommunityPraiseList().size()+"");
        //登陆用户是否点赞
        CommunityPraise communityPraise = new CommunityPraise();
        communityPraise.setUserId(Integer.parseInt(userId));
        communityPraise.setCommunityId(community.getId());
        List<CommunityPraise> getlikeList = communityPraiseService.selectByCondition(communityPraise);
        if(getlikeList.size()==0){
            gotLikeMap.put("ifUserLike","0");
        }else{
            gotLikeMap.put("ifUserLike","1");
        }
        resultMap.put("getLike",gotLikeMap);
        resultList.add(resultMap);
        return DataRes.success(resultList);
    }
    /*
    *圈子详情评论
    * */
    @RequestMapping("app/housecircle/detailcomment")
    public DataRes DetailComment(HttpServletRequest request, HttpServletResponse response){
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        //设置条件
        CommunityComment communityComment = new CommunityComment();
        communityComment.setPage(Integer.parseInt(request.getParameter("page")));
        communityComment.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
        communityComment.setCommunityId(Integer.parseInt(request.getParameter("id")));
        //评论以及评论回复的集合
        List<CommunityComment> commentList = communityCommentService.selectCommentAndReplayByCommunityId(communityComment);
        //遍历集合方便传参
        for(CommunityComment resultComment:commentList){
            /**
             * 空参BUG
             * */
            if(resultComment==null){
                continue;
            }
            //结果集
            Map<String,Object> resultMap = new HashMap<>();
            //设置用户条件查找用户信息
            Users user = new Users();
            user.setId(resultComment.getUserId());
            user = usersService.selectByPrimaryKey(user);
            //评论主键
            resultMap.put("id",resultComment.getId()+"");
            //评论人姓名
            resultMap.put("username",user.getNickname());
            //发布人ID
            resultMap.put("userid",user.getId().toString());
            //判断评论类型
            if(resultComment.getType().equals(CommunityComment.TYPE_COMMENT)){
                resultMap.put("target","");
            }else{
                user.setId(resultComment.getToUid());
                user = usersService.selectByPrimaryKey(user);
                resultMap.put("target",user.getNickname());
            }
            resultMap.put("type",resultComment.getType()+"");// 1 为评论，2为回复
            resultMap.put("commenttext",resultComment.getContent());
            resultList.add(resultMap);
        }
        return DataRes.success(resultList);
    }
}
