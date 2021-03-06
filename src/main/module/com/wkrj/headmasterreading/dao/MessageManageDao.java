package com.wkrj.headmasterreading.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface MessageManageDao {
	List<Map<String,Object>> getList(@Param("offset")int offset,
			@Param("pagesize")int pagesize,@Param("must_read_id")String must_read_id,
			@Param("must_read_title")String must_read_title,
			@Param("comment_content")String comment_content,@Param("start_date")String start_date,
			@Param("end_date")String end_date);
	
	int countFromLike(@Param("user_id")String user_id,@Param("must_read_id")String must_read_id,
			@Param("commenter")String commenter,@Param("comment_content")String comment_content);
	boolean deleteFromLike(@Param("user_id")String user_id,@Param("must_read_id")String must_read_id,
			@Param("commenter")String commenter,@Param("comment_content")String comment_content);
	boolean insertToLike(@Param("user_id")String user_id,@Param("must_read_id")String must_read_id,
			@Param("commenter")String commenter,@Param("comment_content")String comment_content);
	
	boolean updateLikeNumById(@Param("must_read_id")String must_read_id,
			@Param("commenter")String commenter,@Param("comment_content")String comment_content);
	
	boolean deleteMessage(@Param("must_read_id")String must_read_id,
			@Param("commenter")String commenter,@Param("comment_content")String comment_content);
	
	long countMessage(@Param("must_read_id")String must_read_id,
			@Param("must_read_title")String must_read_title,
			@Param("comment_content")String comment_content,@Param("start_date")String start_date,
			@Param("end_date")String end_date);
}
