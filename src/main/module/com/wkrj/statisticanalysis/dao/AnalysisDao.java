package com.wkrj.statisticanalysis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


import com.wkrj.statisticanalysis.bean.AnalysisType;

public interface AnalysisDao {
	List<AnalysisType> getTypeAnalysis(@Param("startTime")String startTime
			,@Param("endTime")String endTime,@Param("chengban_dept")String chengban_dept
			,@Param("business_type")String business_type
			,@Param("accept_source")String accept_source);
	
	List<Map<String,Object>> getCounties(@Param("parent_id")String parent_id);
	
	
	List<AnalysisType> getBusinessTypeAnalysis(@Param("startTime")String startTime
			,@Param("endTime")String endTime,@Param("chengban_dept")String chengban_dept
			,@Param("accept_source")String accept_source);
}
