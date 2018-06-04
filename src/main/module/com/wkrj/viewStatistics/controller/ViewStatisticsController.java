package com.wkrj.viewStatistics.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wkrj.viewStatistics.service.ViewStatisticsService;

import wkrjsystem.utils.UtilsHelper;

@Controller
@RequestMapping("viewStt")
public class ViewStatisticsController {
	@Autowired
	private ViewStatisticsService service;
	
	@RequestMapping("countNum")
	@ResponseBody
	public Object countNum(int pagesize, int page){
		int offset = (page-1) * pagesize;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//		long nums = service.countCaseTotal();//代表案例总数
		list = service.countTitleNum(offset,pagesize);//标题与其留言量
		return UtilsHelper.returnMap(list, list.size());
	}
	
	@RequestMapping("countCaseTotal")
	@ResponseBody
	public long countCaseTotal(){
		return service.countCaseTotal();
	}
	
	
	@RequestMapping("countXiaozhang")
	@ResponseBody
	public Object countXiaozhang(int pagesize, int page){
		int offset = (page-1) * pagesize;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list = service.countXiaozhang(offset,pagesize);//标题与其留言量
		return UtilsHelper.returnMap(list, list.size());
	}
	
	@RequestMapping("countByCounties")
	@ResponseBody
	public Object countByCounties(int pagesize, int page){
		int offset = (page-1) * pagesize;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list = service.countByCounties(offset,pagesize);//标题与其留言量
		return UtilsHelper.returnMap(list, list.size());
	}
}
