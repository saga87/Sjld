package com.wkrj.deptShouliS.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public interface WkrjDeptShouliSService {
    
    /**
     * 获取各部门承办情况
     * @param type
     * @param start_date
     * @param end_date
     * @return
     */
    public List<Map<String,Object>> getDeptChengbanList(String type, String start_date, String end_date, String dept_id, String isshizhi);
    
    public List<Map<String,Object>> getDeptChengbanList_new(String type, String start_date, String end_date, String dept_id, String isshizhi);

    public String exportExcel(String type, String start_date, String end_date, String isshizhi, HttpServletResponse response);

    public List<Map<String, Object>> getEventInfoList(int offset, int pagesize, String dept_id, String type, String start_date, String end_date);

    public long getEventInfoList(String dept_id, String type, String start_date, String end_date);

    public Object getPostsDataInfo(String type, String begin_date, String end_date, String dept_id);

    public List<Map<String, Object>> getFactFinishWfList(int offset,
            int pagesize, String dept_id, String type, String start_date,
            String end_date);

    public long getFactFinishWfList(String dept_id, String type,
            String start_date, String end_date);

    public List<Map<String, Object>> getDelayWfList(int offset, int pagesize,
            String dept_id, String type, String start_date, String end_date);

    public long getDelayWfList(String dept_id, String type, String start_date,
            String end_date);

    public List<Map<String, Object>> getTwoUpWfList(int offset, int pagesize,
            String dept_id, String type, String start_date, String end_date);

    public long getTwoUpWfList(String dept_id, String type, String start_date,
            String end_date);

    public List<Map<String, Object>> getSatisfyDuWfList(int offset,
            int pagesize, String dept_id, String satisfy_status_dept,
            String start_date, String end_date);

    public long getSatisfyDuWfList(String dept_id, String satisfy_status_dept,
            String start_date, String end_date);

    public String export_satisfyDu(String dept_id, String start_date,
            String end_date, String satisfy_status_dept,
            HttpServletResponse response);

    public List<Map<String, Object>> getOvertimeNotReplyWfList(int offset,
            int pagesize, String dept_id, String type, String start_date,
            String end_date);

    public long getOvertimeNotReplyWfList(String dept_id, String type,
            String start_date, String end_date);
}
