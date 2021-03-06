package com.wkrj.headmasterreading.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import wkrjsystem.user.bean.WkrjUser;
import wkrjsystem.utils.AjaxJson;
import wkrjsystem.utils.UtilsHelper;
import wkrjsystemdev.userdev.bean.WkrjUserDev;

import com.wkrj.headmasterreading.service.MessageManageService;

/**
 * 留言管理
 * @author wlp
 * @Description:
 * @date 2018年5月30日 上午9:19:24
 */
@Controller
@RequestMapping("messageManage")
public class MessageManageController {
	@Autowired
	private MessageManageService service;
	
	@RequestMapping("getList")
	@ResponseBody
	public Object getList(HttpServletRequest request,int pagesize, int page,String must_read_id,
			String must_read_title,String comment_content,String start_date,String end_date){
		int offset = (page-1) * pagesize;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list = service.getList(offset,pagesize,must_read_id,must_read_title,comment_content,start_date,end_date);
		long counts = service.countMessage(must_read_id, must_read_title, comment_content, start_date, end_date);
		return UtilsHelper.returnMap(list, counts);
	}
	
	@RequestMapping("likeMessage")
	@ResponseBody
	public Object likeMessage(HttpServletRequest request,String must_read_id,String commenter,
			String comment_content){
		WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
		WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
		String user_name = "";
		String user_dept = "";
		String user_id = "";
		if (user != null) {
			user_name = user.getUser_name();
			user_dept = user.getDept_id();
			user_id = user.getUser_id();
		} else if (userDev != null) {
			user_name = userDev.getUser_name();
			user_dept = userDev.getDept_id();
			user_id = userDev.getUser_id();
		}
		AjaxJson json = new AjaxJson();
		try{
			int num = service.countFromLike(user_id, must_read_id,commenter,comment_content);
			if(num>0){
//				service.deleteFromLike(user_id, must_read_id);
				json.setSuccess(true);
				json.setMsg("该留言你已点过赞");
			}else{
				service.insertToLike(user_id, must_read_id,commenter,comment_content);
				service.updateLikeNumById(must_read_id,commenter,comment_content);
				json.setSuccess(true);
				json.setMsg("点赞成功");
			}
		}catch(Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("点赞异常");
		}
		return json;
	}
	
	
	@RequestMapping("deleteMessage")
	@ResponseBody
	public Object deleteMessage(String must_read_id,String commenter,String comment_content){
		AjaxJson json = new AjaxJson();
		if (service.deleteMessage(must_read_id,commenter,comment_content)) {
			json.setSuccess(true);
			json.setMsg("删除成功");
		}
		return json;
		
	}
	
}
