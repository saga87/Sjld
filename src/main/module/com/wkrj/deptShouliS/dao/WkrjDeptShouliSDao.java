package com.wkrj.deptShouliS.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import wkrjsystem.department.bean.WkrjDept;

public interface WkrjDeptShouliSDao {

    public List<Map<String, Object>> getDeptChengbanList(@Param("type")String type,@Param("start_date")String start_date,@Param("end_date")String end_date,@Param("dept_id")String dept_id);
    
    public List<Map<String, Object>> getDeptChengbanWfList(@Param("type")String type,@Param("start_date")String start_date,@Param("end_date")String end_date,@Param("dept_id")String dept_id);

    public List<WkrjDept> getDeptTree(@Param("parent_id")String parent_id,@Param("isGly")boolean isGly,@Param("user_dept")String user_dept,@Param("isshizhi")String isshizhi);

    public List<Map<String, Object>> getEventReportWfList(@Param("offset")int offset, @Param("page")int page,
            @Param("dept_id")String dept_id, @Param("start_date")String start_date, @Param("end_date")String end_date);

    public long getEventReportWfListCount(@Param("dept_id")String dept_id, @Param("start_date")String start_date, @Param("end_date")String end_date);

    public List<Map<String, Object>> getFactFinishWfList(@Param("offset")int offset, @Param("page")int page, @Param("dept_id")String dept_id, @Param("start_date")String start_date, @Param("end_date")String end_date);

    public long getFactFinishWfListCount(@Param("dept_id")String dept_id, @Param("start_date")String start_date, @Param("end_date")String end_date);

    public List<Map<String, Object>> getDelayWfList(@Param("offset")int offset, @Param("page")int page, @Param("dept_id")String dept_id, @Param("start_date")String start_date, @Param("end_date")String end_date);

    public long getDelayWfListCount(@Param("dept_id")String dept_id, @Param("start_date")String start_date, @Param("end_date")String end_date);

    public List<Map<String, Object>> getTwoUpWfList(@Param("offset")int offset, @Param("page")int page, @Param("dept_id")String dept_id, @Param("start_date")String start_date, @Param("end_date")String end_date);

    public long getTwoUpWfListCount(@Param("dept_id")String dept_id, @Param("start_date")String start_date, @Param("end_date")String end_date);
    
    public List<Map<String, Object>> getOvertimeNotReplyWfList(@Param("offset")int offset, @Param("page")int page, @Param("dept_id")String dept_id, @Param("start_date")String start_date, @Param("end_date")String end_date);

    public long getOvertimeNotReplyWfListCount(@Param("dept_id")String dept_id, @Param("start_date")String start_date, @Param("end_date")String end_date);

    public List<Map<String, Object>> getSatisfyDuWfList(@Param("offset")int offset, @Param("page")int page, @Param("dept_id")String dept_id, @Param("satisfy_status_dept")String satisfy_status_dept,
            @Param("start_date")String start_date, @Param("end_date")String end_date);

    public long getSatisfyDuWfListCount(@Param("dept_id")String dept_id, @Param("satisfy_status_dept")String satisfy_status_dept,
            @Param("start_date")String start_date, @Param("end_date")String end_date);
    
    public List<Map<String, Object>> getShJianfenWfList(@Param("start_date")String start_date,@Param("end_date")String end_date,@Param("dept_id")String dept_id);
}
