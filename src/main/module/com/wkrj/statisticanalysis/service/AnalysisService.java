package com.wkrj.statisticanalysis.service;

import java.util.List;
import java.util.Map;


import com.wkrj.statisticanalysis.bean.AnalysisType;


public interface AnalysisService {

	List<AnalysisType> getTypeAnalysis(String startTime,String endTime,String chengban_dept
			,String business_type,String accept_source);

	List<Map<String, Object>> getCounties(String parent_id);

}
