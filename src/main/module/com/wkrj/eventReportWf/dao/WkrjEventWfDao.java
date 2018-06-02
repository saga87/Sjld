package com.wkrj.eventReportWf.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wkrj.eventReportWf.bean.WkrjEventWf;
import com.wkrj.eventReportWf.bean.WkrjProcedureRecord;

import wkrjsystem.user.bean.WkrjUser;

public interface WkrjEventWfDao {
    /**
     * 通过id获取WkrjEventReport
     * @param id
     * @return
     */
    //public WkrjEventWf getEventReportById(String id);
    /**
     * 添加信息
     * @param er
     * @return
     */
    public boolean addEventWf(WkrjEventWf er);
    /**
     * 获取事件信息
     * @param offset
     * @param page
     * @return
     */
    public List<Map<String, Object>> getEventReportWfList(@Param("offset")int offset, @Param("page")int page, @Param("user_id")String user_id, @Param("user_dept")String user_dept, 
            @Param("caller_username")String caller_username, @Param("caller_tel")String caller_tel, @Param("isGly")boolean isGly, @Param("event_status")String event_status,
            @Param("qianshou_status")String qianshou_status, @Param("cuiban_status")String cuiban_status, @Param("overtime_status")String overtime_status, @Param("dateF")String dateF,
            @Param("shenhe_status")String shenhe_status, @Param("event_no")String event_no, @Param("event_content")String event_content);
    /**
     * 获取事件信息数量
     * @param user_id
     * @return
     */
    public long getEventReportWfListCount(@Param("user_id")String user_id, @Param("user_dept")String user_dept, @Param("caller_username")String caller_username, 
            @Param("caller_tel")String caller_tel, @Param("isGly")boolean isGly, @Param("event_status")String event_status,
            @Param("qianshou_status")String qianshou_status, @Param("cuiban_status")String cuiban_status, @Param("overtime_status")String overtime_status, @Param("dateF")String dateF,
            @Param("shenhe_status")String shenhe_status, @Param("event_no")String event_no, @Param("event_content")String event_content);
    /**
     * 删除事件附件
     * @param id
     * @return
     */
    //public boolean delEventReportFile(@Param("event_id")String id);
    /**
     * 查询是否存在事件附件
     * @param event_id
     * @return
     */
    //public boolean checkeIsHaveFile(@Param("event_id")String event_id);
    /**
     * 根据公告ID获取事件附件
     * @param event_id
     * @return
     */
    //public List<WkrjEventReportFile> getEventReportFile(@Param("event_id")String event_id);
    /**
     * 添加事件附件信息
     * @param plFile
     * @return
     */
    //public boolean addEventReportFile(WkrjEventReportFile plFile);
    /**
     * 获取所有用户信息
     * @param 
     * @return
     */
    public List<WkrjUser> getUserList();
    /**
     * 根据用户ID获取用户信息
     * @param userId
     * @return
     */
    //public WkrjUser getUserNameById(String userId);
    /**
     * 判断是否签收过
     * @param event_id
     * @param userId
     * @return
     */
    public boolean isSignOrNot(@Param("event_id")String event_id, @Param("accept_dept")String accept_dept);
    /**
     * 签收
     * @param messageOp
     * @return
     */
    public boolean signEventWf(WkrjEventWf er);
    
    public boolean delayEventWf(WkrjProcedureRecord delay);
    
    public List<Map<String, Object>> getRepliedEventWfList(@Param("offset")int offset, @Param("page")int page, @Param("user_id")String user_id, @Param("event_status")String event_status,
            @Param("qianshou_status")String qianshou_status);
    
    public long getRepliedEventWfListCount(@Param("user_id")String user_id, @Param("event_status")String event_status, @Param("qianshou_status")String qianshou_status);
    
    public boolean updateEventStatus(WkrjEventWf er);
    
    public boolean replyEventWf(WkrjProcedureRecord opinion);
    
    //public boolean addDeptOpinionFileWf(WkrjDeptOpinionFileWf opinionFile);
    
    public long getWfNotReplyListCount(@Param("user_id")String user_id, @Param("user_dept")String user_dept,
            @Param("qianshou_status")String qianshou_status, @Param("dateF")String dateF);
    
    public List<Map<String, Object>> getWfNotReplyList(@Param("offset")int offset, @Param("page")int page, @Param("user_id")String user_id, @Param("user_dept")String user_dept,
            @Param("qianshou_status")String qianshou_status, @Param("dateF")String dateF);
    
    public boolean setRepliedStatus(@Param("event_id")String event_id, @Param("qianshou_status")String qianshou_status);
    
    public List<Map<String, Object>> getDelayedEventWfList(@Param("offset")int offset, @Param("page")int page, @Param("user_id")String user_id);
    
    public long getDelayedEventWfListCount(@Param("user_id")String user_id);
    
    public List<Map<String, Object>> searchEventWfList(@Param("offset")int offset, @Param("page")int page,
            @Param("event_status")String event_status, @Param("qianshou_status")String qianshou_status, @Param("event_no")String event_no, @Param("caller_nature")String caller_nature, 
            @Param("content_type")String content_type, @Param("start_date")String start_date, @Param("end_date")String end_date, @Param("event_content")String event_content, @Param("caller_tel")String caller_tel);
    
    public long searchEventWfListCount(@Param("event_status")String event_status, @Param("qianshou_status")String qianshou_status, @Param("event_no")String event_no, @Param("caller_nature")String caller_nature, 
            @Param("content_type")String content_type, @Param("start_date")String start_date, @Param("end_date")String end_date, @Param("event_content")String event_content, @Param("caller_tel")String caller_tel);
    
    public List<Map<String, Object>> getReplyInfoWfList(@Param("offset")int offset, @Param("page")int page, @Param("event_id")String event_id);
    
    public long getReplyInfoWfListCount(@Param("event_id")String event_id);
    
    public List<Map<String, Object>> getDelayedEventWfList_S(@Param("offset")int offset, @Param("page")int page, @Param("event_no")String event_no, @Param("caller_nature")String caller_nature, 
            @Param("content_type")String content_type, @Param("start_date")String start_date, @Param("end_date")String end_date, @Param("caller_tel")String caller_tel);
    
    public long getDelayedEventWfListCount_S(@Param("event_no")String event_no, @Param("caller_nature")String caller_nature, 
            @Param("content_type")String content_type, @Param("start_date")String start_date, @Param("end_date")String end_date, @Param("caller_tel")String caller_tel);
    
    public List<Map<String, Object>> exportDubanWfList(@Param("start_date")String start_date, @Param("end_date")String end_date, @Param("isGly")boolean isGly, @Param("user_dept")String user_dept);
    
    public boolean updateZhuanban(WkrjEventWf er);
    
    public boolean updateSatisfyDu(WkrjEventWf erWf);
    
    public List<Map<String, Object>> getAllEventWfList();
    
    public long getEventWfCntByNo(String event_no);
    
    public WkrjEventWf getEventInfoById(String event_id);
    
    //public List<Map<String, Object>> getReplyFileInfoWf(String event_id);
    
    public void updateShenheStatus(WkrjEventWf er);
    
    public boolean updateEventWf(WkrjEventWf ev);
    
    public boolean delEventWf(String event_id);
}
