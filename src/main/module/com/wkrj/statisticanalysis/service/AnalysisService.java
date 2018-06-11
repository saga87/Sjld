package com.wkrj.statisticanalysis.service;

import java.util.List;
import java.util.Map;




import com.wkrj.statisticanalysis.bean.AnalysisType;


public interface AnalysisService {

	List<AnalysisType> getTypeAnalysis(String startTime,String endTime,String chengban_dept
			,String business_type);

	List<Map<String, Object>> getCounties(String parent_id);

	List<AnalysisType> getBusinessTypeAnalysis(String startTime,String endTime,String chengban_dept
			,String accept_source);
	
	List<Map<String,Object>> dealRanking(String business_type,String event_status,String qianshou_status
			,String satisfy_status);
	
	List<Map<String,Object>> dealRankingOpt(String business_type,String opt);
}
