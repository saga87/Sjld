package com.wkrj.warning.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface GongdanRemindDao {
	List<Map<String, Object>> getEventOrder(@Param("offset")int offset,
			@Param("pagesize")int pagesize,@Param("dept_id")String dept_id,
			@Param("isGly") boolean isGly);
}
