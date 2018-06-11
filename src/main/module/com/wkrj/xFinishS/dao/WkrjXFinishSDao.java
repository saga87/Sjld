package com.wkrj.xFinishS.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface WkrjXFinishSDao {

    public List<Map<String, Object>> getOnceBanjieCnt(@Param("dept_id")String dept_id,@Param("content_type")String content_type,@Param("start_date")String start_date,
            @Param("end_date")String end_date);

    public List<Map<String, Object>> getTimesBanjieCnt(@Param("dept_id")String dept_id,@Param("content_type")String content_type,@Param("start_date")String start_date,
            @Param("end_date")String end_date,@Param("houzhui")String houzhui);

    public long getShouldFinishWfCnt(@Param("start_date")String start_date, @Param("end_date")String end_date, @Param("dept_id")String dept_id);

    public List<Map<String, Object>> getFourthBanjieCnt(@Param("dept_id")String dept_id,@Param("content_type")String content_type,@Param("start_date")String start_date,
            @Param("end_date")String end_date);

    public List<Map<String, Object>> getOnceBanjieEvent(@Param("offset")int offset, @Param("page")int page,
            @Param("dept_id")String dept_id, @Param("start_date")String start_date, @Param("end_date")String end_date);

    public long getOnceBanjieEventCount(@Param("dept_id")String dept_id, @Param("start_date")String start_date, @Param("end_date")String end_date);

    public List<Map<String, Object>> getTimesFinishEvent(@Param("offset")int offset, @Param("page")int page,
            @Param("dept_id")String dept_id, @Param("start_date")String start_date, @Param("end_date")String end_date);

    public long getTimesFinishEventCount(@Param("dept_id")String dept_id, @Param("start_date")String start_date, @Param("end_date")String end_date);
    
    public List<Map<String, Object>> getTimesBanjieEvent(@Param("offset")int offset, @Param("page")int page,
            @Param("dept_id")String dept_id, @Param("houzhui")String houzhui, @Param("start_date")String start_date, @Param("end_date")String end_date);

    public long getTimesBanjieEventCount(@Param("dept_id")String dept_id, @Param("houzhui")String houzhui, @Param("start_date")String start_date, @Param("end_date")String end_date);

    public List<Map<String, Object>> getFourthBanjieEvent(@Param("offset")int offset, @Param("page")int page,
            @Param("dept_id")String dept_id, @Param("start_date")String start_date, @Param("end_date")String end_date);

    public long getFourthEventCount(@Param("dept_id")String dept_id, @Param("start_date")String start_date, @Param("end_date")String end_date);
    
    //public List<Map<String, Object>> getEventWfListByZhenjie(@Param("content_type")String type,@Param("start_date")String start_date,@Param("end_date")String end_date);
    
    public List<Map<String, Object>> getDeptBanjieWfList(@Param("type")String type,@Param("start_date")String start_date,@Param("end_date")String end_date,@Param("dept_id")String dept_id);
}
