package com.wkrj.eventReportWf.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.wkrj.eventReportWf.bean.WkrjEventWf;
import com.wkrj.eventReportWf.bean.WkrjProcedureRecord;

public interface WkrjEventWfService {
    /**
     * 获取事件信息
     * @param 
     * @return
     */
    public List<Map<String, Object>> getEventReportWfList(int offset, int page, String user_id, String user_dept, String caller_username, String caller_tel, boolean isGly, String event_status,
            String qianshou_status, String cuiban_status, String overtime_status, String dateF, String shenhe_status, String event_no, String event_content);
    /**
     * 获取事件信息数量
     * @param user_id
     * @return
     */
    public long getEventReportWfList(String user_id, String user_dept, String caller_username, String caller_tel, boolean isGly, String event_status, String qianshou_status, String cuiban_status,
            String overtime_status, String dateF, String shenhe_status, String event_no, String event_content);
    /**
     * 获取事件信息
     * @param 
     * @return
     */
    public List<Map<String, Object>> getJjdqEventWfList(int offset, int page, String user_id, String user_dept, String start_date, String end_date, boolean isGly, String event_status,
            String qianshou_status, String cuiban_status, String overtime_status, String dateF, String shenhe_status, String event_no, String event_content);
    /**
     * 获取事件信息数量
     * @param user_id
     * @return
     */
    public long getJjdqEventWfList(String user_id, String user_dept, String start_date, String end_date, boolean isGly, String event_status, String qianshou_status, String cuiban_status,
            String overtime_status, String dateF, String shenhe_status, String event_no, String event_content);
    /**
     * 添加事件信息
     * @param er
     * @return
     */
    public boolean addEventWf(WkrjEventWf er);
    
    /**
     * 上传事件附件
     * @param 
     * @return
     */
    public String uploadPic(MultipartFile er_icons,HttpServletRequest request,String foledArrress);


    /**
     * 获取所有用户列表
     * @return
     */
    public List<Map<String, Object>> getUserList();


    /**
     * 判断是否签收过
     * @param 
     * @return
     */
    public boolean isSignOrNot(String er, String user_id);

    /**
     * 签收
     * @param messageOp
     * @return
     */
    public boolean signEventWf(WkrjEventWf er);

    public List<Map<String, Object>> getDataDictionary(String parentID);
    
    public boolean delayEventWf(WkrjProcedureRecord delay);
    
    public List<Map<String, Object>> getRepliedEventWfList(int offset, int pagesize, String user_id, String user_dept, String caller_username, String caller_tel, boolean isGly,
            String event_status, String qianshou_status, String string);
    
    public long getRepliedEventWfList(String user_id, String user_dept, String caller_username, String caller_tel, boolean isGly, String event_status, String qianshou_status, String string);
    
    public boolean updateEventStatus(WkrjEventWf er);
    
    public boolean replyEventWf(WkrjProcedureRecord opinion);
    
    public List<Map<String, Object>> getWfNotReplyList(int offset, int pagesize, String user_id, String user_dept, String caller_username, String caller_tel, boolean isGly,
            String string, String qianshou_status, String string2, String string3, String dateF);
    
    public long getWfNotReplyList(String user_id, String user_dept, String caller_username, String caller_tel, boolean isGly, String string, String qianshou_status, String string2,
            String string3, String dateF);
    
    public boolean setRepliedStatus(String event_id, String qianshou_status);
    
    public List<Map<String, Object>> getDelayedEventWfList(int offset, int pagesize, String user_id, String caller_username, String caller_tel, boolean isGly);
    
    public long getDelayedEventWfList(String user_id, String caller_username, String caller_tel, boolean isGly);
    
//    public boolean finishEventWf(WkrjEventWf er);
//    
//    public boolean huifangFinishEventWf(WkrjEventWf er);
    
    public List<Map<String, Object>> searchEventWfList(int offset, int pagesize,
            String event_status, String qianshou_status, String event_no, String caller_nature, String content_type, String start_date, String end_date, String event_content, String caller_tel);
    
    public long searchEventWfList(String event_status, String qianshou_status, String event_no, String caller_nature, String content_type, String start_date, String end_date, 
            String event_content, String caller_tel);

    public List<Map<String, Object>> getReplyInfoWfList(int offset, int pagesize, String event_id, String dept_name, String accept_workno, String input_time, String event_no, 
            String qianshou_status, String qianshou_time, String event_status, String huifang_time, String replied_dealno, String delay_time, String current_loginno, String opt_time);
    
    public long getReplyInfoWfList(String event_id);
    
    public List<Map<String, Object>> getDelayedEventWfList_S(int offset, int pagesize, String event_no, String caller_nature, String content_type, String start_date, String end_date, String caller_tel);
    
    public long getDelayedEventWfList_S(String event_no, String caller_nature, String content_type, String start_date, String end_date, String caller_tel);
    
    public boolean updateZhuanban(WkrjEventWf er);
    
    public boolean updateSatisfyDu(WkrjEventWf erWf);
    
//    public List<Map<String, Object>> getFinishedEventWfList(int offset,
//            int pagesize, String user_id, String chengban_dept, boolean isGly,
//            String event_status, String start_date, String end_date,
//            String satisfy_status_dept, String event_no, String event_content);
//    
//    public long getFinishedEventWfList(String user_id, String chengban_dept, boolean isGly,
//            String event_status, String start_date, String end_date,
//            String satisfy_status_dept, String event_no, String event_content);
    
    public List<Map<String, Object>> getAllEventWfList();
    
    public long getEventWfCntByNo(String string);
    
    public WkrjEventWf getEventInfoById(String event_id);
    
    //public List<Map<String, Object>> getReplyFileInfoWf(String event_id);
    
    public void updateShenheStatus(WkrjEventWf er);
    
    public boolean updateEventWf(WkrjEventWf ev);
    
    public boolean delEventWf(String event_id);
    
    public boolean yanqiEvent(WkrjEventWf event);
    
    public List<Map<String, Object>> getCuibanList(String event_id);
    
    public boolean cuibanEvent(WkrjEventWf event);

}
