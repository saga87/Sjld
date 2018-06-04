package com.wkrj.viewStatistics.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wkrj.viewStatistics.dao.ViewStatisticsDao;

@Service("viewStatisticsService")
@Transactional
public class ViewStatisticsServiceImpl implements ViewStatisticsService {
	@Autowired
	private ViewStatisticsDao dao;

	@Override
	public long countCaseTotal() {
		// TODO Auto-generated method stub
		return dao.countCaseTotal();
	}

	@Override
	public List<Map<String, Object>> countTitleNum(int offset, int pagesize) {
		// TODO Auto-generated method stub
		return dao.countTitleNum(offset, pagesize);
	}

	@Override
	public List<Map<String, Object>> countXiaozhang(int offset, int pagesize) {
		// TODO Auto-generated method stub
		return dao.countXiaozhang(offset, pagesize);
	}

	@Override
	public List<Map<String, Object>> countByCounties(int offset, int pagesize) {
		// TODO Auto-generated method stub
		return dao.countByCounties(offset, pagesize);
	}
}
