package com.wkrj.headmasterreading.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wkrj.headmasterreading.bean.ReadCase;
import com.wkrj.headmasterreading.bean.ReadCaseFile;

public interface MessageListDao {
	List<Map<String,Object>> getMessageList(@Param("offset")int offset,@Param("pagesize")int pagesize);
	List<Map<String,Object>> getCaseReadFile(@Param("must_read_id")String must_read_id);
	
	List<ReadCaseFile> getCrFile(@Param("must_read_id")String must_read_id);
	boolean deleteReadCaseFile(@Param("file_id")String file_id,@Param("must_read_id")String must_read_id);
	boolean deleteReadCase(@Param("must_read_id")String must_read_id);
	
	boolean updateReadCase(ReadCase readCase);
}
