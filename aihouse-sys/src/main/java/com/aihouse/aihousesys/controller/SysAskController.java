package com.aihouse.aihousesys.controller;

import javax.annotation.Resource;

import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ExcelUtils;
import com.aihouse.aihousedao.bean.AppAskComent;
import com.aihouse.aihousedao.bean.AskCommentReply;
import com.aihouse.aihouseservice.AppAskComentService;
import com.aihouse.aihouseservice.AppAskService;
import com.aihouse.aihouseservice.AskCommentReplyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import com.aihouse.aihousedao.bean.AppAsk;


/**
 *提问表 controller
 */
@Controller
public class SysAskController {

	/**
	 * 提问回复表
	 */
	@Resource
	private AppAskComentService appAskComentService;



	@Resource
	private AppAskService appAskService;

	@Resource
	private AskCommentReplyService askCommentReplyService;

	/**
	 * 跳转问答列表
	 * */
	@RequestMapping("sysAsk/gotoList")
	@RequiresPermissions("sysAsk/gotoList")
	public String gotoList(){
		return "sys/sys_ask_list";
	}

	/**
	 * 跳转问答详情页
	 *
	 * */
	@RequestMapping("sysAsk/gotoDetail")
	public String gotoDetail(AppAsk appAsk,HttpServletRequest request, HttpServletResponse response){
		if (null == appAsk.getId()) {
			request.setAttribute("sys_ask",appAsk);
		}else{
			request.setAttribute("sys_ask",appAskService.selectByPrimaryKey(appAsk));
		}
		return "sys/sys_ask_detail";
	}


	/**
	 * 问答回复
	 * @return
	 */
	@RequestMapping("sysAsk/gotoCommentDetail")
	public String gotoCommentDetail(AppAskComent appAskComent,HttpServletRequest request){
		if(appAskComent.getId()!=null){
			request.setAttribute("askComment",appAskComentService.selectByPrimaryKey(appAskComent));
		}else{
			request.setAttribute("askComment",appAskComent);
		}
		return "sys/sys_ask_comment_detail";
	}

	/**
	 *删除问答回复
	 * @param appAskComent
	 * @return
	 */
	@RequestMapping("sysAsk/deleteComment")
	@ResponseBody
	public DataRes deleteComment(AppAskComent appAskComent){
		return DataRes.success(appAskComentService.deleteByPrimaryKey(appAskComent));
	}


	/**
	 *删除问答回复评论
	 * @param askCommentReply
	 * @return
	 */
	@RequestMapping("sysAsk/deleteCommentReplay")
	@ResponseBody
	public DataRes deleteCommentReplay(AskCommentReply askCommentReply){
		return DataRes.success(askCommentReplyService.deleteByPrimaryKey(askCommentReply));
	}

	/**
	 * 回复评论
	 * @return
	 */
	@RequestMapping("sysAsk/gotoCommentReplayDetail")
	public String gotoCommentReplayDetail(Integer id, HttpServletRequest request){
		if(id!=null){
			AskCommentReply askCommentReply=new AskCommentReply();
			askCommentReply.setId(id);
			askCommentReply=askCommentReplyService.selectByPrimaryKey(askCommentReply);
			request.setAttribute("askCommentReply",askCommentReply);
		}else{
			request.setAttribute("askCommentReply",null);
		}
		return "sys/sys_ask_comment_replay_detail";
	}

	/**
	 * 删除-提问表
	 */
	@ResponseBody
	@RequestMapping("sysAsk/deleteByPrimaryKey")
	public DataRes deleteByPrimaryKey(AppAsk appAsk, HttpServletRequest request, HttpServletResponse response){
		return DataRes.success(appAskService.deleteByPrimaryKey(appAsk));
	}


	/**
	 *  ��存 (主键为空则增加 否则 修改)-> 提问表
	 */
	@ResponseBody
	@RequestMapping("sysAsk/save")
	public DataRes save(AppAsk appAsk,HttpServletRequest request,HttpServletResponse response){
		if(appAsk.getId()==null){
			return DataRes.success(appAskService.insert(appAsk));
		}
		return DataRes.success(appAskService.update(appAsk));

	}

	/**
	 * 根据主键查询->提问表
	 */
	@ResponseBody
	@RequestMapping("sysAsk/selectByPrimaryKey")
	public DataRes selectByPrimaryKey(AppAsk appAsk,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(appAskService.selectByPrimaryKey(appAsk));
	}


	/**
	 * 根据条件查询(所有的实体属性都是条件,如果为空则忽略该字段)->提问表
	 */
	@ResponseBody
	@RequestMapping("sysAsk/selectByCondition")
	public DataRes selectByCondition(AppAsk appAsk,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(appAskService.selectByCondition(appAsk));
	}


	/**
	 * 分页查询 (所有的实体��性都是条件,如果为空则忽略该字段) (详见Page类.所以的实体都继承该类 默认 page=1 pageSize=10)->提问表
	 */
	@ResponseBody
	@RequestMapping("sysAsk/selectAllByPaging")
	public DataRes selectAllByPaging(AppAsk appAsk,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(appAskService.selectAllByPaging(appAsk));
	}


	/**
	 * 导出报表->提问表
	 */
	@RequestMapping("sysAsk/export")
	public void export(AppAsk appAsk,HttpServletRequest request,HttpServletResponse response){
		List<AppAsk> list= appAskService.selectAll(appAsk);
		Map<String, String> header = new LinkedHashMap<>();
		header.put("id", "id");
		header.put("userId", "用户id");
		header.put("askTitle", "标题");
		header.put("askContent", "提问内容");
		header.put("createTime_", "提问时间");
		header.put("thumbsUp", "点赞数");
		header.put("commentCount", "评论数");
		ExcelUtils.exportExcel("提问表",header,list,response,request);

	}


	/**
	 * 级联查询(带分页) 问答题--提问回复表
	 */
	@RequestMapping("sysAsk/selectAppAskAndAppAskComent")
	@ResponseBody
	public DataRes selectAppAskAndAppAskComent(AppAsk appAsk,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(appAskService.selectAppAskAndAppAskComent(appAsk));
	}


	/**
	 * 级联条件查询 问答题--提问回复表
	 */
	@RequestMapping("sysAsk/selectAppAskAndAppAskComentByCondition")
	@ResponseBody
	public DataRes selectAppAskAndAppAskComentByCondition(AppAsk appAsk,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(appAskService.selectAppAskAndAppAskComentByCondition(appAsk));
	}


	/**
	 * 级联删除(根据主键删除) 问答题--提问回复表
	 */
	@RequestMapping("sysAsk/deleteAppAskAndAppAskComent")
	@ResponseBody
	public DataRes deleteAppAskAndAppAskComent(AppAsk appAsk,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(appAskService.deleteAppAskAndAppAskComent(appAsk));
	}



}
