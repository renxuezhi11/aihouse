package com.aihouse.aihouseapp.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousecore.utils.keyword.BadWord;
import com.aihouse.aihousedao.bean.*;
import com.aihouse.aihouseservice.*;
import com.aihouse.aihouseservice.users.UsersService;
import com.sun.imageio.plugins.common.I18N;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *提问表 controller
 */
@Controller
@RestController
@CrossOrigin
public class AppAskController  {

	private final  static  Integer ZERO = 0;

	/**
	 * 提问回复表
	 */
	@Resource
	private AppAskComentService appAskComentService;
	@Resource
	private AppAskService appAskService;
	@Resource
	private UsersService usersService;
	@Resource
	private AskCommentReplyService askCommentReplyService;
	@Resource
	private AskCommentPraiseService askCommentPraiseService;

	/**
	 *问答推荐页
	 **/
	@RequestMapping("app/ask/recommendindex")
	public DataRes RecommendIndex(HttpServletRequest request, HttpServletResponse response){
		String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
		List<Map<String,Object>> resultList = new ArrayList<>();
		Map<String,Object> resultMap = new HashMap<>();
		AppAsk appAsk = new AppAsk();
		//页码
		appAsk.setPage(Integer.parseInt(request.getParameter("page")));
		//当页条数
		appAsk.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
		//设置问答状态进行筛选
		appAsk.setStatus(AppAsk.ASK_STATUS_PASS);
		//排序条件
		appAsk.setOrderByString("order by create_time desc");

		appAsk.setUserId(Integer.parseInt(userId));
		List<AppAsk> list = appAskService.selectRecommendAskScreenList(appAsk);
		List<Map<String,String>> askList = new ArrayList<>();
		//问答列表
		for(AppAsk ask : list){
			Map<String,String> askMap = new HashMap<>();
			Users user = new Users();
			user.setId(ask.getUserId());
			//查询问题人状态
			user = usersService.selectByPrimaryKey(user);
			//主键
			askMap.put("id",ask.getId()+"");
			//提问人姓名
			askMap.put("askperson",user.getNickname());
			AppAskComent appAskComent = new AppAskComent();
			appAskComent.setAskId(ask.getId());
			List<AppAskComent> appAskComentCount =  appAskComentService.selectByCondition(appAskComent);
			//回答人数
			askMap.put("answercount",appAskComentCount.size()+"");
			//标题
			askMap.put("title",ask.getAskTitle());
			askList.add(askMap);
		}
		resultMap.put("asklist",askList);
		//我的回答相关动态上标数字：点赞以及回复
		//根据用户Id查询is_checked的相关数据
		AppAskComent appAskComent = new AppAskComent();
		appAskComent.setUserId(Integer.parseInt(userId));
		//获得回答动态数量
		Integer i = appAskComentService.initDao().countCommentNotChecked(appAskComent);
		resultMap.put("comentscount",i+"");
		//我的问题相关动态上标数字：回复
		//根据用户Id查询is_checked的相关问题
		AppAsk appAsk1 = new AppAsk();
		appAsk1.setUserId(Integer.parseInt(userId));
		Integer o =  appAskService.initDao().countUnCheckQuestion(appAsk1);
		resultMap.put("askcount",o+"");
		resultList.add(resultMap);
		return DataRes.success(resultList);
	}

	/**
	 *我的回答页
	 **/
	@RequestMapping("app/ask/myanswer")
	public DataRes MyAnswer(HttpServletRequest request,HttpServletResponse response){
		String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
		Users user = new Users();
		user.setId(Integer.parseInt(userId));
		user = usersService.selectByPrimaryKey(user);
		AppAskComent appAskComent =new AppAskComent();
		List<Map<String,Object>> resultList = new ArrayList<>();
		appAskComent.setPage(Integer.parseInt(request.getParameter("page")));
		appAskComent.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
		appAskComent.setUserId(Integer.parseInt(userId));
		//根据以上属性条件查询答案
		List<AppAskComent> askComentList = appAskComentService.selectCommentForMyAnswer(appAskComent);
		for(AppAskComent resultAskComent:askComentList){
			Map<String,Object> resultMap = new HashMap<>();
			AppAskComent queryAskComment = new AppAskComent();
			queryAskComment.setId(resultAskComent.getId());
			queryAskComment.setPage(ZERO);
			AppAsk appAsk = new AppAsk();
			appAsk.setId(resultAskComent.getAskId());
			//主键---用于删除
			resultMap.put("id",resultAskComent.getId()+"");
			//AskId--用于跳转问题详情页
			resultMap.put("askid",resultAskComent.getAskId()+"");
			//答案内容
			resultMap.put("title",resultAskComent.getCommentContent()+"");
			//状态
			resultMap.put("status",resultAskComent.getStatus());
			//回复人数
			Integer answercount = appAskComentService.initDao().countReplayCount(resultAskComent);
			resultMap.put("answercount",answercount+"");
			//回复条数---点赞条数
			Integer praise = appAskComentService.initDao().countPraiseCount(resultAskComent);
			resultMap.put("praise",praise+"");
			//回答人nickname
			resultMap.put("nickname",user.getNickname());
			AskCommentReply askCommentReply = new AskCommentReply();
			askCommentReply.setCommentId(resultAskComent.getId());
			askCommentReply.setIsChecked(AskCommentReply.UNCHECKED);
			List<AskCommentReply> replyList = askCommentReplyService.selectByCondition(askCommentReply);
			//未读数
			resultMap.put("replycount",replyList.size()+"");
			resultList.add(resultMap);
		}
		return DataRes.success(resultList);
	}

	/**
	 *我的提问页
	 **/
	@RequestMapping("app/ask/myquestion")
	public DataRes MyQuestion(HttpServletRequest request,HttpServletResponse response){
		String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
		List<Map<String,Object>> resultList = new ArrayList<>();
		AppAsk appAsk = new AppAsk();
		appAsk.setPage(Integer.parseInt(request.getParameter("page")));
		appAsk.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
		appAsk.setUserId(Integer.parseInt(userId));
		List<AppAsk> askList = appAskService.queryByCondition(appAsk);
		for(AppAsk resultAsk:askList){
			if(resultAsk.getStatus()!=3) {
				Map<String, Object> resultMap = new HashMap<>();
				//主键
				resultMap.put("id", resultAsk.getId() + "");
				//标题
				resultMap.put("title", resultAsk.getAskTitle());
				//状态
				resultMap.put("status", resultAsk.getStatus());
				//回答人数
				resultMap.put("answercount", resultAsk.getCommentCount() + "");
				//回复提醒条数
				AppAskComent appAskComent = new AppAskComent();
				appAskComent.setAskId(resultAsk.getId());
				Integer count = appAskComentService.initDao().countCommentNotMainChecked(appAskComent);
				resultMap.put("remindcount", count + "");
				resultList.add(resultMap);
			}
		}
		return DataRes.success(resultList);
	}

	/**
	 *问题详情页
	 **/
	@RequestMapping("app/ask/detail")
	public DataRes Detail(HttpServletRequest request,HttpServletResponse response){
		String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
		List<Map<String,Object>> resultList = new ArrayList<>();
		Map<String,Object> resultMap = new HashMap<>();
		AppAsk appAsk = new AppAsk();
		appAsk.setPage(null);
		appAsk.setId(Long.parseLong(request.getParameter("askid")));
		List<AppAsk> list  = appAskService.selectAppAskAndAppAskComentForAskDetail(appAsk);
		appAsk=list.get(ZERO);
		//标题
		resultMap.put("title",appAsk.getAskTitle());
		//创建时间
		resultMap.put("createtime",appAsk.getcreateTime_()+"");
		//补充内容
		resultMap.put("content",appAsk.getAskContent());
		//用户id
		resultMap.put("userid",appAsk.getUserId());
		//发布人nickname
		Users user = new Users();
		user.setId(appAsk.getUserId());
		user = usersService.selectByPrimaryKey(user);
		resultMap.put("name",user.getNickname());
		List<Map<String,Object>> commentList = new ArrayList<>();
		//答案列表
		for(AppAskComent asdcomment:appAsk.getAppAskComentList()){
			Map<String,Object> commentMap = new HashMap<>();
			user.setId(asdcomment.getUserId());
			user = usersService.selectByPrimaryKey(user);
			//答案用户名
			commentMap.put("name",user.getNickname());
			commentMap.put("userid",user.getId());
			//答案用户头像
			commentMap.put("headimg",user.getUserphoto());
			//答案Id
			commentMap.put("answerid",asdcomment.getId()+"");
			//答案内容
			commentMap.put("context",asdcomment.getCommentContent());
			//登陆用户是否点赞
			AskCommentPraise askCommentPraise = new AskCommentPraise();
			askCommentPraise.setUserId(Integer.parseInt(userId));
			askCommentPraise.setAskCommentId(asdcomment.getId());
			List<AskCommentPraise> praiseList = askCommentPraiseService.selectByCondition(askCommentPraise);
			if(praiseList.size()==ZERO){
				commentMap.put("iflike",ZERO+"");
			}else{
				commentMap.put("iflike","1");
			}
			//点赞数量
			asdcomment.setIsChecked(null);
			asdcomment.setPage(null);
			List<AppAskComent> asdcommentList =  appAskComentService.selectAppAskComentAndAskCommentPraiseByCondition(asdcomment);
			asdcomment = asdcommentList.get(ZERO);
			asdcomment.setPage(null);
			commentMap.put("praisecount",asdcomment.getAskCommentPraiseList().size()+"");
			//评论数量
			asdcommentList = appAskComentService.selectAppAskComentAndAskCommentReplyByCondition(asdcomment);
			asdcomment = asdcommentList.get(ZERO);
			commentMap.put("replycount",asdcomment.getAskCommentReplyList().size()+"");
			List<Map<String,Object>> replyList = new ArrayList<>();
			//评论列表
			for(AskCommentReply commentReply:asdcomment.getAskCommentReplyList()){
				//更新评论状态
				if(asdcomment.getUserId().equals(Integer.parseInt(userId))){
					commentReply.setIsChecked(AskCommentReply.CHECKED);
				}
				Map<String,Object> replyMap = new HashMap<>();
				user.setId(commentReply.getUserId());
				user = usersService.selectByPrimaryKey(user);
				//评论用户名
				replyMap.put("name",user.getNickname());
				replyMap.put("userid",user.getId());
				//评论内容
				replyMap.put("content",commentReply.getCommentText());
				//评论id
				replyMap.put("id",commentReply.getId());
				if(commentReply.getType().equals(AskCommentReply.TYPE_COMMENT)){
					//目标人为空
					replyMap.put("target","");
				}else{
					//目标人为
					user.setId(commentReply.getToUserId());
					user = usersService.selectByPrimaryKey(user);
					replyMap.put("target",user.getNickname());
				}
				if(asdcomment.getUserId().equals(Integer.parseInt(userId))) {
					commentReply.setIsChecked(AskCommentReply.CHECKED);
					askCommentReplyService.update(commentReply);
				}
				//类型
				replyMap.put("type",commentReply.getType());// 1 为评论，2为回复
				AskCommentReply queryReply = new AskCommentReply();
				queryReply.setId(commentReply.getId());
				queryReply.setCommentId(commentReply.getCommentId());
				replyList.add(replyMap);
			}
			commentMap.put("replylist",replyList);
			commentList.add(commentMap);
		}
		resultMap.put("comment",commentList);
		resultList.add(resultMap);
		return DataRes.success(resultList);
	}

	/**
	 *添加回答接口---添加答案
	 **/
	@RequestMapping("app/ask/addanswer")
	@Transactional
	public  DataRes AddAnswer(HttpServletRequest request,HttpServletResponse response){
		List<Map<String,Object>> resultList = new ArrayList<>();
		String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
		AppAsk appAsk = new AppAsk();
		appAsk.setId(Long.parseLong(request.getParameter("askId")));
		//查找是否有此问题
		appAsk = appAskService.selectByPrimaryKey(appAsk);

		AppAskComent appAskComent =new AppAskComent();
		appAskComent.setUserId(Integer.parseInt(userId));
		appAskComent.setAskId(Long.parseLong(request.getParameter("askId")));
		//查找该用户是否已经回答过该问题
		List<AppAskComent> askComentList = appAskComentService.selectByCondition(appAskComent);
		if(askComentList.size()>0){
			return DataRes.error("502","一个用户只能对一个问题回答一次");
		}
		appAskComent.setIsChecked(AppAskComent.CHECKED);
		appAskComent.setStatus(AppAskComent.COMENT_STATUS_PASS);
		String content = request.getParameter("content");
		if(BadWord.isContain(content)){
			content = BadWord.replace(content,"*");
		}
		appAskComent.setCommentContent(content);
		//添加答案
		Integer count = appAskComentService.insert(appAskComent);
		if(count == 1){
			//维护问题表
			appAsk.setCommentCount(appAsk.getCommentCount()+1);
			if (appAskService.update(appAsk)==0){
				try {
					throw new Exception("回滚");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return DataRes.success(appAsk.getId());
		}else{
			return DataRes.error("500","回复提问失败");
		}
	}

	/**
	 *提问接口----添加问题
	 **/
	@RequestMapping("app/ask/addask")
	public DataRes AddAsk(HttpServletRequest request,HttpServletResponse response){
		String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
		List<Map<String,Object>> resultList = new ArrayList<>();
		//实例化问答
		AppAsk appAsk = new AppAsk();
		appAsk.setUserId(Integer.parseInt(userId));
		String title = request.getParameter("title");
		//判断标题是否有过敏词
		if(BadWord.isContain(title)){
			//过敏词更换*
			title = BadWord.replace(title,"*");
		}
		appAsk.setAskTitle(title);
		if(request.getParameter("content")==null){
		}else{
			String content = request.getParameter("content");
			//判断文本是否有过敏词
			if(BadWord.isContain(content)){
				//过敏词更换
				content = BadWord.replace(content,"*");
			}
			appAsk.setAskContent(content);
		}
		//起始状态值设定
		appAsk.setThumbsUp(ZERO);
		appAsk.setCommentCount(ZERO);
		Integer count = appAskService.insert(appAsk);
		if(count == 1){
			return DataRes.success(resultList);
		}else{
			return DataRes.error("500","发表提问失败");
		}
	}

	/**
	 *答案回复接口---添加回复
	 **/
	@RequestMapping("app/ask/addreply")
	@Transactional
	public DataRes AddReply(HttpServletRequest request,HttpServletResponse response){
		String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
		List<Map<String,Object>> resultList = new ArrayList<>();
		AppAskComent askComment = new AppAskComent();
		askComment.setId(Integer.parseInt(request.getParameter("commentid")));
		//查找该评论条件---该评论是否存在
		askComment = appAskComentService.selectByPrimaryKey(askComment);
		AskCommentReply askCommentReply = new AskCommentReply();
		askCommentReply.setUserId(Integer.parseInt(userId));
		//设置类型
		askCommentReply.setType(AskCommentReply.TYPE_COMMENT);
		String text = request.getParameter("text");
		if(BadWord.isContain(text)){
			text = BadWord.replace(text,"*");
		}
		askCommentReply.setCommentText(text);
		askCommentReply.setCommentId(Integer.parseInt(request.getParameter("commentid")));
		Integer count =  askCommentReplyService.insert(askCommentReply);
		if(count == 1){
			askComment.setCommentCount(askComment.getCommentCount()+1);
			//更新回答表中的评论数
			if(appAskComentService.update(askComment)==0){
				try {
					throw new Exception("回滚");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return DataRes.success(askCommentReply.getId());
		}else{
			return DataRes.error("500","添加回复失败");
		}
	}
	/**
	 *点赞接口---点赞
	 **/
	@RequestMapping("app/ask/addpraise")
	public DataRes AddPraise(HttpServletRequest request,HttpServletResponse response){
		String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
		List<Map<String,Object>> resultList = new ArrayList<>();
		AppAskComent askComment = new AppAskComent();
		askComment.setId(Integer.parseInt(request.getParameter("commentid")));
		//查找是否有该答案
		askComment = appAskComentService.selectByPrimaryKey(askComment);
		AskCommentPraise askCommentPraise = new AskCommentPraise();
		askCommentPraise.setUserId(Integer.parseInt(userId));
		askCommentPraise.setAskCommentId(Integer.parseInt(request.getParameter("commentid")));
		List<AskCommentPraise> list = askCommentPraiseService.selectByCondition(askCommentPraise);
		if(list.size()>0){
			return DataRes.error("500","该用户已点赞");
		}
		//点赞表的INSERT方法
		Integer count = askCommentPraiseService.insert(askCommentPraise);
		if(count==1){
			//维护答案表的点赞数字段
			askComment.setThumbsUp(askComment.getThumbsUp()+1);
			if(appAskComentService.update(askComment)==0){
				try {
					throw new Exception("回滚");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return DataRes.success(resultList);
		}else{
			return DataRes.error("400","点赞失败");
		}
	}

	/**
	 *回复评论接口---回复评论
	 **/
	@RequestMapping("app/ask/addrevert")
	@Transactional
	public DataRes AddRevert(HttpServletRequest request,HttpServletResponse response){
		String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
		List<Map<String,Object>> resultList = new ArrayList<>();
		AskCommentReply askCommentReply = new AskCommentReply();
		askCommentReply.setId(Integer.parseInt(request.getParameter("replyid")));
		//获取回复详细信息
		askCommentReply = askCommentReplyService.selectByPrimaryKey(askCommentReply);
		if(askCommentReply.getUserId().equals(Integer.parseInt(userId))){
			return DataRes.error("400","不可以回复自己的评论或者回复");
		}
		AskCommentReply queryCommentReply = new AskCommentReply();
		queryCommentReply.setUserId(Integer.parseInt(userId));
		//设置回复的类型
		queryCommentReply.setType(AskCommentReply.TYPE_REVERT);
		queryCommentReply.setCommentId(askCommentReply.getCommentId());
		queryCommentReply.setToUserId(askCommentReply.getUserId());

		if(request.getParameter("context")!=null){
			queryCommentReply.setCommentText(request.getParameter("context"));
		}else{
			return DataRes.error("500","回复为空");
		}
		Integer count = askCommentReplyService.insert(queryCommentReply);
		if(count==1){
			//维护答案表中的评论数字段
			AppAskComent askComment = new AppAskComent();
			askComment.setId(askCommentReply.getCommentId());
			askComment = appAskComentService.selectByPrimaryKey(askComment);
			askComment.setCommentCount(askComment.getCommentCount()+1);
			if(appAskComentService.update(askComment)==0){
				try {
					throw new Exception("回滚");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return  DataRes.success(queryCommentReply.getId());
		}else{
			return DataRes.error("500","回复失败");
		}
	}

	/**
	 *取消点赞
	 **/
	@RequestMapping("app/ask/canclepraise")
	@Transactional
	public DataRes CanclePraise(HttpServletRequest request,HttpServletResponse response) {
		String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
		List<Map<String, Object>> resultList = new ArrayList<>();
		AppAskComent askComment = new AppAskComent();
		askComment.setId(Integer.parseInt(request.getParameter("commentid")));
		//查找此答案相关信息
		askComment = appAskComentService.selectByPrimaryKey(askComment);
		AskCommentPraise askCommentPraise = new AskCommentPraise();
		askCommentPraise.setUserId(Integer.parseInt(userId));
		askCommentPraise.setAskCommentId(Integer.parseInt(request.getParameter("commentid")));
		List<AskCommentPraise> list = askCommentPraiseService.selectByCondition(askCommentPraise);
		if (list.size() == 0) {
			return DataRes.error("500", "该用户未点赞");
		}
		askCommentPraise.setId(list.get(ZERO).getId());
		//调用点赞表中的DELETE方法
		Integer count = askCommentPraiseService.deleteByPrimaryKey(askCommentPraise);
		if (count == 1) {
			//维护答案表中的点赞数数量
			askComment.setThumbsUp(askComment.getThumbsUp() - 1);
			if (appAskComentService.update(askComment) == 0) {
				try {
					throw new Exception("回滚");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return DataRes.success(resultList);
		}else{
			return DataRes.error("400","取消失败");
		}
	}

	/**
	 * 搜索关键词
	 * */
	@RequestMapping("app/ask/keyword")
	public DataRes KeyWord(HttpServletRequest request,HttpServletResponse response){
		List<Map<String,Object>> resultList = new ArrayList<>();
		String key = request.getParameter("keyword");
		AppAsk appAsk = new AppAsk();
		appAsk.setAskTitle(key);
		List<AppAsk> appAskList = appAskService.initDao().selectListByKey(appAsk);
		for(AppAsk resultappAsk:appAskList){
			Map<String,Object> resultMap = new HashMap<>();
			resultMap.put("title",resultappAsk.getAskTitle());
			resultList.add(resultMap);
		}
		return DataRes.success(resultList);
	}

	/**
	 * 问答搜索
	 */
	@RequestMapping("app/ask/searchask")
	public DataRes SearchAsk(HttpServletRequest request,HttpServletResponse response){
		List<Map<String,Object>> resultList = new ArrayList<>();
		String key = request.getParameter("keyword");
		AppAsk appAsk = new AppAsk();
		appAsk.setAskTitle(key);
		appAsk.setPage(Integer.parseInt(request.getParameter("page")));
		appAsk.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
		List<AppAsk> appAskList = appAskService.initDao().showListByKey(appAsk);
		for(AppAsk resultappAsk:appAskList){
			Map<String,Object> resultMap = new HashMap<>();
			resultMap.put("title",resultappAsk.getAskTitle());
			resultMap.put("id",resultappAsk.getId()+"");
			resultMap.put("content",resultappAsk.getAskContent());
			resultList.add(resultMap);
		}
		return DataRes.success(resultList);
	}

	/**
	 * 点击我的回答后清除上标
	 */
	@RequestMapping("app/ask/clearmyanswer")
	public DataRes ClearMyAnswer(HttpServletRequest request,HttpServletResponse response){
		List<Map<String,Object>> resultList = new ArrayList<>();
		String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
		AppAskComent appAskComent = new AppAskComent();
		appAskComent.setUserId(Integer.parseInt(userId));
		appAskComent.setIsChecked(AppAskComent.CHECKED);
		//直接调用语句更新所有以上条件集合
		Integer i = appAskComentService.initDao().updateChecked(appAskComent);
		return DataRes.success(resultList);
	}


	/**
	 * 点击我的问题后清除上标
	 */
	@RequestMapping("app/ask/clearmyquestion")
	public DataRes ClearMyQuestion(HttpServletRequest request,HttpServletResponse response){
		List<Map<String,Object>> resultList = new ArrayList<>();
		String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
		AppAskComent appAskComent = new AppAskComent();
		appAskComent.setUserId(Integer.parseInt(userId));
		//直接调用语句查询所有以上条件集合
		List<AppAskComent> askComentList = appAskComentService.initDao().selectMainCheckedList(appAskComent);
		for(AppAskComent query:askComentList){
			query.setIsMainChecked(AppAskComent.COMENT_STATUS_PASS);
			appAskComentService.update(query);
		}
		return DataRes.success(resultList);
	}


	/**
	 *回答评论删除
	 //	 **/
//	@RequestMapping("app/ask/deletereply")
//	public DataRes DeleteComment(HttpServletRequest request,HttpServletResponse response){
//			List<Map<String,Object>> resultList = new ArrayList<>();
//			AskCommentReply askCommentReply = new AskCommentReply();
//			askCommentReply.setId(Integer.parseInt(request.getParameter("replyid")));
//			if(askCommentReplyService.deleteAskCommentReplyAndAskCommentReplyRevert(askCommentReply)==1) {
//				return DataRes.success(resultList);
//			}else{
//				return DataRes.error("500","删除失败");
//			}
//	}

	/**
	 *评论回复删除
	 **/
//	@RequestMapping("app/ask/deleterevert")
//	public DataRes DeleteReply(HttpServletRequest request,HttpServletResponse response){
//		List<Map<String,Object>> resultList = new ArrayList<>();
//		AskCommentReplyRevert askCommentReplyRevert = new AskCommentReplyRevert();
//		askCommentReplyRevert.setId(Integer.parseInt(request.getParameter("revertid")));
//		if (askCommentReplyRevertService.deleteByPrimaryKey(askCommentReplyRevert)==1) {
//			return DataRes.success(resultList);
//		}else{
//			return DataRes.error("500","删除失败");
//		}
//	}


	/**
	 *问题删除
	 **/
	@RequestMapping("app/ask/deleteask")
	public DataRes DeleteAsk(HttpServletRequest request,HttpServletResponse response){
		List<Map<String,Object>> resultList = new ArrayList<>();
		AppAsk appAsk = new AppAsk();
		appAsk.setId(Long.parseLong(request.getParameter("id")));
		appAsk.setStatus(AppAsk.ASK_STATUS_DELETE);
//		appAskService.update();
		return DataRes.success(resultList);
	}

	/**
	 *回答删除
	 **/
	@RequestMapping("app/ask/deleteanswer")
	public DataRes DeleteAnswer(HttpServletRequest request,HttpServletResponse response){
		List<Map<String,Object>> resultList = new ArrayList<>();
		AppAskComent appAskComent = new AppAskComent();
		appAskComent.setId(Integer.parseInt(request.getParameter("answerid")));
		if(appAskComentService.deleteAppAskComentAndAskCommentPraise(appAskComent)==1) {
			appAskComent = appAskComentService.selectAppAskComentAndAskCommentReplyByCondition(appAskComent).get(ZERO);
			for(AskCommentReply askCommentReply:appAskComent.getAskCommentReplyList()){
//				askCommentReplyService.deleteAskCommentReplyAndAskCommentReplyRevert(askCommentReply);
			}
			return DataRes.success(resultList);
		}else{
			return DataRes.error("500","删除失败");
		}
	}

	/**
	 * 删除问题，回复，评论
	 * @param request
	 * @param type 1 问题2回复3评论
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "app/ask/deleteAsk",method = RequestMethod.POST)
	public DataRes deleteAsk(HttpServletRequest request, Integer type, Long id){
		if(type!=null&&id!=null){
			String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
			if(type==1){//删除提问
				AppAsk appAsk = new AppAsk();
				appAsk.setId(id);
				appAsk=appAskService.selectByPrimaryKey(appAsk);
				if(appAsk!=null){
					if(appAsk.getUserId()==Integer.parseInt(userId)){
						//删除评论
						askCommentReplyService.deleteAppAskComentReplyByAskId(id);
						//删除点赞
						askCommentPraiseService.deleteAppAskCommentPraiseByAskId(id);
						//删除回复
						appAskComentService.deleteAppAskComentAskId(id);
						//删除问题
						appAsk.setStatus(AppAsk.ASK_STATUS_DELETE);
						appAskService.update(appAsk);
					}else{
						return DataRes.error("500","删除失败");
					}
				}else{
					return DataRes.error("500","删除失败");
				}
			}else if(type==2){//删除回复

				AppAskComent appAskComent=new AppAskComent();
				appAskComent.setId(id.intValue());
				appAskComent=appAskComentService.selectByPrimaryKey(appAskComent);
				if(appAskComent!=null){
					if(appAskComent.getUserId()==Integer.parseInt(userId)){
						//删除评论
						askCommentReplyService.deleteAppAskCommentReplyByCommentId(id);
						//删除点赞
						askCommentPraiseService.deleteAppAskCommentPraiseByCommentId(id);
						//删除回复
						appAskComentService.deleteByPrimaryKey(appAskComent);

					}else{
						return DataRes.error("500","删除回复失败");
					}
				}else{
					return DataRes.error("500","删除回复失败");
				}
			}else if(type==3){//删除评论
				AskCommentReply askCommentReply=new AskCommentReply();
				askCommentReply.setId(id.intValue());
				askCommentReply=askCommentReplyService.selectByPrimaryKey(askCommentReply);
				if(askCommentReply!=null){
					if(askCommentReply.getUserId()==Integer.parseInt(userId)){
						//删除评论
						askCommentReplyService.deleteByPrimaryKey(askCommentReply);
						//更新回复的评论数
						AppAskComent appAskComent=new AppAskComent();
						appAskComent.setId(askCommentReply.getCommentId());
						appAskComent=appAskComentService.selectByPrimaryKey(appAskComent);
						if(appAskComent!=null){
							appAskComent.setCommentCount(appAskComent.getCommentCount()-1);
							appAskComentService.update(appAskComent);
						}
					}else{
						return DataRes.error("500","删除评论失败");
					}
				}else{
					return DataRes.error("500","删除评论失败");
				}
			}
		}else{
			return DataRes.error(ResponseCode.DATA_ERROR);
		}
		return DataRes.success("删除成功");
	}
}
