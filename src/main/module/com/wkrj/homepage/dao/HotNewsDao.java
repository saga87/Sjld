package com.wkrj.homepage.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface HotNewsDao {
	List<Map<String,Object>> getHotNews(@Param("offset")int offset
			,@Param("rows")int rows);
	
	List<Map<String,Object>> getNaById(@Param("na_id")String na_id);
}
