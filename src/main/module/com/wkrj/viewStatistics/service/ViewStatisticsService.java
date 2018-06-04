package com.wkrj.viewStatistics.service;

import java.util.List;
import java.util.Map;

public interface ViewStatisticsService {
	long countCaseTotal();
	
	List<Map<String,Object>> countTitleNum(int offset,int pagesize);
	List<Map<String,Object>> countXiaozhang(int offset,int pagesize);
	List<Map<String,Object>> countByCounties(int offset,int pagesize);
}
