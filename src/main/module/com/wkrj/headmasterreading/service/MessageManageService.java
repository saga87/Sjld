package com.wkrj.headmasterreading.service;

import java.util.List;
import java.util.Map;

public interface MessageManageService {
	List<Map<String,Object>> getList(int offset,int pagesize,String must_read_title,
			String comment_content,String start_date,String end_date);
	int countFromLike(String user_id,String must_read_id);
	boolean deleteFromLike(String user_id,String must_read_id);
	boolean insertToLike(String user_id,String must_read_id);
	
	boolean updateLikeNumById(String must_read_id);
	
}
