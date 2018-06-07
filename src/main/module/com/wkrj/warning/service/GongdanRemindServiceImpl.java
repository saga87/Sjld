package com.wkrj.warning.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wkrj.warning.dao.GongdanRemindDao;

@Service("gongdanRemindService")
@Transactional
public class GongdanRemindServiceImpl implements GongdanRemindService {
	@Autowired
	private GongdanRemindDao dao;
	@Override
	public List<Map<String, Object>> getEventOrder(int offset, int pagesize,String dept_id) {
		return dao.getEventOrder(offset, pagesize,dept_id);
	}

}
