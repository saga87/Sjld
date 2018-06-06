package com.wkrj.homepage.service;

import java.util.List;
import java.util.Map;

public interface HotNewsService {
	List<Map<String,Object>> getHotNews(int offset,int rows);
	
	List<Map<String,Object>> getNaById(String na_id);
}
