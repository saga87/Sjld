package com.wkrj.statisticanalysis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.wkrj.statisticanalysis.bean.AnalysisType;
import com.wkrj.statisticanalysis.dao.AnalysisDao;

@Service("analysisService")
@Transactional
public class AnalysisServiceImpl implements AnalysisService {
	@Autowired
	private AnalysisDao dao;

	@Override
	public List<AnalysisType> getTypeAnalysis(String startTime,String endTime,String chengban_dept
			,String business_type,String accept_source) {
		// TODO Auto-generated method stub
		return dao.getTypeAnalysis(startTime,endTime,chengban_dept,business_type,accept_source);
	}

	@Override
	public List<Map<String, Object>> getCounties(String parent_id) {
		
		List<Map<String,Object>> all = new ArrayList<Map<String,Object>>();//记录所有的module
        List<Map<String, Object>> vals = dao.getCounties(parent_id);
        for(Map<String, Object> dd : vals){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("id", dd.get("dept_id"));
            map.put("name", dd.get("dept_name"));
            all.add(map);
        }
		
		return all;
	}
}
