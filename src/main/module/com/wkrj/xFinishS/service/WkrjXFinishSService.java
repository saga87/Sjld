package com.wkrj.xFinishS.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public interface WkrjXFinishSService {
    /**
     * 获取事件
     * @param content_type
     * @param start_date
     * @param end_date
     * @return
     */
    public List<Map<String,Object>> getEventCntByBanjieTimes(String content_type, String start_date, String end_date, String isshizhi, String sortname, String sortorder);
    
    public List<Map<String,Object>> getEventCntByBanjieTimes_new(String content_type, String start_date, String end_date, String isshizhi, String sortname, String sortorder);

    public String exportExcel(String content_type, String start_date, String end_date, String isshizhi, HttpServletResponse response);

    public List<Map<String, Object>> getOnceBanjieEvent(int offset,
            int pagesize, String dept_id, String type, String start_date,
            String end_date);

    public long getOnceBanjieEvent(String dept_id, String type,
            String start_date, String end_date);

    public List<Map<String, Object>> getTimesBanjieEvent(int offset,
            int pagesize, String dept_id, String houzhui, String start_date,
            String end_date);

    public long getTimesBanjieEvent(String dept_id, String houzhui,
            String start_date, String end_date);

    public String export_banjieEvent(String flag, String start_date,
            String end_date, String dept_id, HttpServletResponse response);

    public List<Map<String, Object>> getFourthBanjieEvent(int offset,
            int pagesize, String dept_id, String start_date, String end_date);

    public long getFourthBanjieEvent(String dept_id, String start_date,
            String end_date);

    public List<Map<String, Object>> getTimesFinishEvent(int offset,
            int pagesize, String dept_id, String start_date, String end_date);

    public long getTimesFinishEvent(String dept_id, String start_date,
            String end_date);
}
