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
			,String business_type) {
		// TODO Auto-generated method stub
		return dao.getTypeAnalysis(startTime,endTime,chengban_dept,business_type);
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

	@Override
	public List<AnalysisType> getBusinessTypeAnalysis(String startTime,
			String endTime, String chengban_dept, String accept_source) {
		// TODO Auto-generated method stub
		return dao.getBusinessTypeAnalysis(startTime, endTime, chengban_dept, accept_source);
	}

	@Override
	public List<Map<String, Object>> dealRanking(String business_type,
			String event_status, String qianshou_status, String satisfy_status) {
		// TODO Auto-generated method stub
		return dao.dealRanking(business_type, event_status, qianshou_status, satisfy_status);
	}

	@Override
	public List<Map<String, Object>> dealRankingOpt(String content_type,String opt) {
		// TODO Auto-generated method stub
		return dao.dealRankingOpt(content_type,opt);
	}

	@Override
	public List<Map<String, Object>> getSatisfaction(String dept_id) {
		// TODO Auto-generated method stub
		return dao.getSatisfaction(dept_id);
	}

	@Override
	public List<Map<String, Object>> getRatio(String content_type,
			String startTime, String endTime) {
		// TODO Auto-generated method stub
		return dao.getRatio(content_type, startTime, endTime);
	}
}
