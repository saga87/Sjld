package com.wkrj.headmasterreading.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wkrj.headmasterreading.bean.ReadCase;
import com.wkrj.headmasterreading.bean.ReadCaseFile;

public interface InformExtractDao {
	List<Map<String,Object>> getFinishedEvent(@Param("offset")int offset,@Param("pagesize")int pagesize,
			@Param("event_no")String event_no,@Param("event_title")String event_title,
			@Param("event_content")String event_content,@Param("business_type")String business_type);
	boolean addReadCaseFile(ReadCaseFile file);
	boolean addReadCase(ReadCase readCase);
}
