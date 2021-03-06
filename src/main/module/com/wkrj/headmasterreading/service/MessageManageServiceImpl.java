package com.wkrj.headmasterreading.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wkrj.headmasterreading.dao.MessageManageDao;

@Service("messageManageService")
@Transactional
public class MessageManageServiceImpl implements MessageManageService {
	@Autowired
	private MessageManageDao dao;
	
	@Override
	public List<Map<String, Object>> getList(int offset, int pagesize,String must_read_id,
			String must_read_title, String comment_content, String start_date,
			String end_date) {
		return dao.getList(offset, pagesize,must_read_id, must_read_title, comment_content, start_date, end_date);
	}

	@Override
	public int countFromLike(String user_id, String must_read_id,String commenter,
			String comment_content) {
		return dao.countFromLike(user_id, must_read_id,commenter,comment_content);
	}


	@Override
	public boolean insertToLike(String user_id, String must_read_id,String commenter,
			String comment_content) {
		return dao.insertToLike(user_id, must_read_id,commenter,comment_content);
	}

	@Override
	public boolean updateLikeNumById(String must_read_id,String commenter,String comment_content) {
		return dao.updateLikeNumById(must_read_id,commenter,comment_content);
	}

	@Override
	public boolean deleteMessage(String must_read_id,String commenter,String comment_content) {
		int sum = dao.countFromLike(null, must_read_id, commenter, comment_content);
		if(sum>0){
			return dao.deleteFromLike(null, must_read_id,commenter,comment_content)&&dao.deleteMessage(must_read_id,commenter,comment_content);
		}else{
			return dao.deleteMessage(must_read_id,commenter,comment_content);
		}
	}

	@Override
	public long countMessage(String must_read_id, String must_read_title,
			String comment_content, String start_date, String end_date) {
		// TODO Auto-generated method stub
		return dao.countMessage(must_read_id, must_read_title, comment_content, start_date, end_date);
	}

}
