package com.wkrj.homepage.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import wkrjsystem.utils.UtilsHelper;

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
	public Object getHotNews(int page,int pagesize){
		int offset = (page-1) * pagesize;
		List<Map<String,Object>> lists = new ArrayList<Map<String,Object>>();
		lists = service.getHotNews(offset, pagesize);
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
