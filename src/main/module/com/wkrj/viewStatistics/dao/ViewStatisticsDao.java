package com.wkrj.viewStatistics.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ViewStatisticsDao {
	long countCaseTotal();
	
	List<Map<String,Object>> countTitleNum(@Param("offset")int offset,@Param("pagesize")int pagesize);
	List<Map<String,Object>> countXiaozhang(@Param("offset")int offset,@Param("pagesize")int pagesize);
	List<Map<String,Object>> countByCounties(@Param("offset")int offset,@Param("pagesize")int pagesize);
	
	List<Map<String,Object>> countCase(@Param("offset")int offset,@Param("pagesize")int pagesize);
}
