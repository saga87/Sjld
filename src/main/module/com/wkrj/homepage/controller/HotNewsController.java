package com.wkrj.homepage.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import wkrjsystem.user.bean.WkrjUser;
import wkrjsystem.utils.UtilsHelper;
import wkrjsystemdev.userdev.bean.WkrjUserDev;

import com.wkrj.homepage.service.HotNewsService;

/**
 * 首页最新消息
 * @author wlp
 * @Description:
 * @date 2018年6月6日 下午3:51:33
 */
@Controller
@RequestMapping("homepage/news")
public class HotNewsController {
	
	@Autowired
	private HotNewsService service;
	
	@RequestMapping("getHotNews")
	@ResponseBody
	public Object getHotNews(HttpServletRequest request,int page,int pagesize){
		int offset = (page-1) * pagesize;
		List<Map<String,Object>> lists = new ArrayList<Map<String,Object>>();
		WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
		WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
		String user_dept = "";
		String user_id = "";
		if (user != null) {
			user_dept = user.getDept_id();
			user_id = user.getUser_id();
		} else if (userDev != null) {
			user_dept = userDev.getDept_id();
			user_id = userDev.getUser_id();
		}
		
		if("1".equals(user_id)){
			user_id = "";
		}
		lists = service.getHotNews(offset, pagesize,user_id);
		return UtilsHelper.returnMap(lists, lists.size());
	}
	
	@RequestMapping("getNaById")
	@ResponseBody
	public Object getNaById(String na_id){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list = service.getNaById(na_id);
		return list;
	}
	
}
