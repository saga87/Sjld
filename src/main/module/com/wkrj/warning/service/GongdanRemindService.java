package com.wkrj.warning.service;

import java.util.List;
import java.util.Map;

public interface GongdanRemindService {
	 List<Map<String, Object>> getEventOrder(int offset,int pagesize,String dept_id,boolean isGly);
}
