package com.wkrj.statisticanalysis.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.wkrj.statisticanalysis.bean.AnalysisType;
import com.wkrj.statisticanalysis.service.AnalysisService;

@Controller
@RequestMapping("analysis")
public class AnalysisController {
	@Autowired
	private AnalysisService service;
	
	/**
	 * 统计受理类别
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param chengban_dept 承办单位（县市区）
	 * @param business_type 类型（咨询、建议...）
	 * @param accept_source 接收渠道（用户来电...）
	 * @return
	 */
	@RequestMapping("getTypeAnalysis")
	@ResponseBody
	public Object getTypeAnalysis(String startTime,String endTime,String chengban_dept
			,String business_type,String accept_source){
		List<AnalysisType> list = service.getTypeAnalysis(startTime,endTime,chengban_dept,business_type,accept_source);
		return list;
	}
	
	@RequestMapping("getCounties")
	@ResponseBody
	public Object getCounties(String parent_id){
		List<Map<String,Object>> list = service.getCounties(parent_id);
		return list;
	}
	
	
	
	
}
