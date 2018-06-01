package com.wkrj.headmasterreading.service;

import java.util.List;
import java.util.Map;

import com.wkrj.headmasterreading.bean.MessageManage;
import com.wkrj.headmasterreading.bean.ReadCase;
import com.wkrj.headmasterreading.bean.ReadCaseFile;


public interface MessageListService {
	List<Map<String,Object>> getMessageList(int offset,int pagesize);
	List<Map<String,Object>> getCaseReadFile(String must_read_id);
	boolean deleteCaseRead(String must_read_id);
	boolean updateCaseRead(ReadCase readCase,ReadCaseFile file);
	
	boolean addMessage(MessageManage mm);
}
