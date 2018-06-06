package com.wkrj.homepage.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wkrj.homepage.dao.HotNewsDao;

@Service("hotNewsService")
@Transactional
public class HotNewsServiceImpl implements HotNewsService {
	@Autowired
	private HotNewsDao dao;

	@Override
	public List<Map<String, Object>> getHotNews(int offset, int rows) {
		// TODO Auto-generated method stub
		return dao.getHotNews(offset, rows);
	}

	@Override
	public List<Map<String, Object>> getNaById(String na_id) {
		// TODO Auto-generated method stub
		return dao.getNaById(na_id);
	}
	
	
}
