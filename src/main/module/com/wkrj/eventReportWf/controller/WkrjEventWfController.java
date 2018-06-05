package com.wkrj.eventReportWf.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import wkrjsystem.department.service.WkrjDeptService;
import wkrjsystem.user.bean.WkrjUser;
import wkrjsystem.user.service.WkrjUserService;
import wkrjsystem.utils.AjaxJson;
import wkrjsystem.utils.Guid;
import wkrjsystem.utils.SpringContextUtil;
import wkrjsystem.utils.UtilsHelper;
import wkrjsystemdev.userdev.bean.WkrjUserDev;

import com.wkrj.eventReportWf.bean.WkrjEventWf;
import com.wkrj.eventReportWf.bean.WkrjProcedureRecord;
import com.wkrj.eventReportWf.service.WkrjEventWfService;

@Controller
@RequestMapping("eventWf/WkrjEventWf")
public class WkrjEventWfController {
    
    @Autowired
    private WkrjEventWfService erService;
    
    @Autowired
    private WkrjUserService userService;
    
    @Autowired
    private WkrjDeptService deptService;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @RequestMapping("getEventReportWfList")
    @ResponseBody
    public Object getEventReportWfList(String parentId, HttpServletRequest request){

        String dateF = "";
        boolean isGly = false;
        String roleIds = request.getSession().getAttribute("userRoleId")+"";
        String[] role_ids = roleIds.split(",");
        for (int i = 0; i < role_ids.length; i++) {
            //如果是系统管理员
            if ("2".equals(role_ids[i]) || "ee228c85-d868-4228-90b9-41f3377806c0".equals(role_ids[i])) {
                isGly = true;
                break;
            }
        }

        String caller_username = request.getParameter("caller_username");
        String caller_tel = request.getParameter("caller_tel");
        int page=Integer.parseInt(request.getParameter("page"));
        int pagesize=Integer.parseInt(request.getParameter("pagesize"));
        int offset = (page-1)*pagesize;
        
        List<Map<String, Object>> list = this.erService.getEventReportWfList(offset, pagesize, "", "", caller_username, caller_tel, isGly, "", "", "", "", dateF, "", "", "");
        long count = this.erService.getEventReportWfList("", "", caller_username, caller_tel, isGly, "", "", "", "", dateF, "", "", "");
        
        return UtilsHelper.returnMap(list,count);
    }
    
    @RequestMapping("searchEventWfList")
    @ResponseBody
    public Object searchEventWfList(String parentId, HttpServletRequest request){
        String qianshou_status = "";
        String event_status = "";
        String caller_nature = request.getParameter("caller_nature");
        String deal_status = request.getParameter("deal_status");
        if ("未回复".equals(deal_status)) {
            qianshou_status = "1";
        }
        if ("已回复".equals(deal_status)) {
            qianshou_status = "2";
        }
        if ("已完成".equals(deal_status)) {
            event_status = "完成";
        }
        if ("已撤销".equals(deal_status)) {
            event_status = "撤销";
        }
        String event_no = request.getParameter("event_no");
        String caller_tel = request.getParameter("caller_tel");
        String event_content = request.getParameter("event_content");
        String content_type = request.getParameter("content_type");
        String start_date = request.getParameter("start_date");
        String end_date = request.getParameter("end_date");
//        if (start_date != null && end_date != null && start_date.equals(end_date)) {//如果选择同一天，默认加上时间
//            start_date = start_date+" 00:00:00";
//            end_date = end_date+" 23:59:59";
//        }
        int page=Integer.parseInt(request.getParameter("page"));
        int pagesize=Integer.parseInt(request.getParameter("pagesize"));
        int offset = (page-1)*pagesize;
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        long count = 0;
        if ("延时".equals(deal_status)) {
            list = this.erService.getDelayedEventWfList_S(offset, pagesize, event_no, caller_nature, content_type, start_date, end_date, caller_tel);
            count = this.erService.getDelayedEventWfList_S(event_no, caller_nature, content_type, start_date, end_date, caller_tel);
        } else {
            list = this.erService.searchEventWfList(offset, pagesize, event_status, qianshou_status, event_no, caller_nature, content_type, start_date, end_date, event_content, caller_tel);
            count = this.erService.searchEventWfList(event_status, qianshou_status, event_no, caller_nature, content_type, start_date, end_date, event_content, caller_tel);
        }
        
        return UtilsHelper.returnMap(list,count);
    }
    @RequestMapping("getReplyInfoWfList")
    @ResponseBody
    public Object getReplyInfoWfList(String event_id, String dept_name, String accept_workno, String input_time, String event_no, String qianshou_status, String qianshou_time, 
            String event_status, String huifang_time, String replied_dealno, String delay_time, String current_loginno, String opt_time, HttpServletRequest request){
        int page=Integer.parseInt(request.getParameter("page"));
        int pagesize=Integer.parseInt(request.getParameter("pagesize"));
        int offset = (page-1)*pagesize;
        List<Map<String, Object>> list = this.erService.getReplyInfoWfList(offset, pagesize, event_id, dept_name, accept_workno, input_time, event_no, qianshou_status, qianshou_time, 
                event_status, huifang_time, replied_dealno, delay_time, current_loginno, opt_time);
        //long count = this.erService.getReplyInfoWfList(event_id);
        long count = list.size();
        return UtilsHelper.returnMap(list,count);
    }
    @RequestMapping("getShenheEventList")
    @ResponseBody
    public Object getShenheEventList(String parentId, HttpServletRequest request){
        int page=Integer.parseInt(request.getParameter("page"));
        int pagesize=Integer.parseInt(request.getParameter("pagesize"));
        String shenhe_status = request.getParameter("shenhe_status");
        shenhe_status = "0";//未审核的
        int offset = (page-1)*pagesize;
        
        List<Map<String, Object>> list = this.erService.getEventReportWfList(offset, pagesize, "", "", "", "", true, "", "", "", "", "", shenhe_status, "", "");
        long count = this.erService.getEventReportWfList("", "", "", "", true, "", "", "", "", "", shenhe_status, "", "");
        
        return UtilsHelper.returnMap(list,count);
    }
    @RequestMapping("getZhuanfaEventList")
    @ResponseBody
    public Object getZhuanfaEventList(String parentId, HttpServletRequest request){
        int page=Integer.parseInt(request.getParameter("page"));
        int pagesize=Integer.parseInt(request.getParameter("pagesize"));
        String shenhe_status = "1";//已审核未转发
        int offset = (page-1)*pagesize;
        
        List<Map<String, Object>> list = this.erService.getEventReportWfList(offset, pagesize, "", "", "", "", true, "", "", "", "", "", shenhe_status, "", "");
        long count = this.erService.getEventReportWfList("", "", "", "", true, "", "", "", "", "", shenhe_status, "", "");
        
        return UtilsHelper.returnMap(list,count);
    }
    
    /**
     * 获取潍坊未处理事件
     * @param request
     * @return
     */
    @RequestMapping("getWfNotDealList")
    @ResponseBody
    public Object getWfNotDealList(String parentId, HttpServletRequest request){
//        WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
//        WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
//        String user_id = "";
        boolean isGly = false;
        String event_status = "1";
        String qianshou_status = "1";//处理中的（已签收）
        String dateF = "";
        String roleIds = request.getSession().getAttribute("userRoleId")+"";
        String[] role_ids = roleIds.split(",");
        for (int i = 0; i < role_ids.length; i++) {
            //如果是系统管理员
            if ("2".equals(role_ids[i]) || "ee228c85-d868-4228-90b9-41f3377806c0".equals(role_ids[i])) {
                isGly = true;
                break;
            }
        }
//        if (user != null) {
//            user_id = user.getUser_id();
//        } else if (userDev != null) {
//            user_id = userDev.getUser_id();
//        }
        String caller_username = request.getParameter("caller_username");
        String caller_tel = request.getParameter("caller_tel");
        int page=Integer.parseInt(request.getParameter("page"));
        int pagesize=Integer.parseInt(request.getParameter("pagesize"));
        int offset = (page-1)*pagesize;
        String event_no = request.getParameter("event_no");
        String event_content = request.getParameter("event_content");
        List<Map<String, Object>> list = this.erService.getEventReportWfList(offset, pagesize, "", "", caller_username, caller_tel, isGly, event_status, qianshou_status, "", "", dateF, "",
                event_no, event_content);
        long count = this.erService.getEventReportWfList("", "", caller_username, caller_tel, isGly, event_status, qianshou_status, "", "", dateF, "", event_no, event_content);
        
        return UtilsHelper.returnMap(list,count);
    }
    /**
     * 获取潍坊已回复事件
     * @param request
     * @return
     */
    @RequestMapping("getRepliedEventWfList")
    @ResponseBody
    public Object getRepliedEventWfList(String parentId, HttpServletRequest request){
        boolean isGly = false;
        String qianshou_status = "2";//已回复
        String dateF = "";
        String roleIds = request.getSession().getAttribute("userRoleId")+"";
        String[] role_ids = roleIds.split(",");
        for (int i = 0; i < role_ids.length; i++) {
            //如果是系统管理员
            if ("2".equals(role_ids[i]) || "ee228c85-d868-4228-90b9-41f3377806c0".equals(role_ids[i])) {
                isGly = true;
                break;
            }
        }
        String caller_username = request.getParameter("caller_username");
        String caller_tel = request.getParameter("caller_tel");
        int page=Integer.parseInt(request.getParameter("page"));
        int pagesize=Integer.parseInt(request.getParameter("pagesize"));
        int offset = (page-1)*pagesize;
        String event_no = request.getParameter("event_no");
        String event_content = request.getParameter("event_content");
        List<Map<String, Object>> list = this.erService.getEventReportWfList(offset, pagesize, "", "", caller_username, caller_tel, isGly, "", qianshou_status, "", "", dateF, "", event_no,
                event_content);
        long count = this.erService.getEventReportWfList("", "", caller_username, caller_tel, isGly, "", qianshou_status,  "", "", dateF, "", event_no, event_content);
        return UtilsHelper.returnMap(list,count);
    }
    
    /**
     * 获取潍坊已回复事件（部门）
     * @param request
     * @return
     */
    @RequestMapping("getWfRepliedEventList_dept")
    @ResponseBody
    public Object getWfRepliedEventList_dept(String parentId, HttpServletRequest request){
        WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
        WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
        boolean isGly = false;
        String user_dept = "";
        //String event_status = "1";
        String qianshou_status = "2";//已回复
        String dateF = "";
        String roleIds = request.getSession().getAttribute("userRoleId")+"";
        String[] role_ids = roleIds.split(",");
        for (int i = 0; i < role_ids.length; i++) {
            //如果是系统管理员
            if ("2".equals(role_ids[i]) || "ee228c85-d868-4228-90b9-41f3377806c0".equals(role_ids[i])) {
                isGly = true;
                break;
            }
        }
        if (user != null) {
            user_dept = user.getDept_id();
        } else if (userDev != null) {
            user_dept = userDev.getDept_id();
        }
        String caller_username = request.getParameter("caller_username");
        String caller_tel = request.getParameter("caller_tel");
        int page=Integer.parseInt(request.getParameter("page"));
        int pagesize=Integer.parseInt(request.getParameter("pagesize"));
        int offset = (page-1)*pagesize;
        String event_no = request.getParameter("event_no");
        String event_content = request.getParameter("event_content");
        List<Map<String, Object>> list = this.erService.getEventReportWfList(offset, pagesize, "", user_dept, caller_username, caller_tel, isGly, "", qianshou_status, "", "", dateF, "", event_no, event_content);
        long count = this.erService.getEventReportWfList("", user_dept, caller_username, caller_tel, isGly, "", qianshou_status, "", "", dateF, "", event_no, event_content);
        
        return UtilsHelper.returnMap(list,count);
    }
    /**
     * 获取已撤销事件
     * @param request
     * @return
     */
    @RequestMapping("getChexiaoEventWfList")
    @ResponseBody
    public Object getChexiaoEventList(String parentId, HttpServletRequest request){
//        WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
//        WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
//        String user_id = "";
        boolean isGly = false;
        String event_status = "撤销";
        //String qianshou_status = "2";//已回复
        String roleIds = request.getSession().getAttribute("userRoleId")+"";
        String[] role_ids = roleIds.split(",");
        for (int i = 0; i < role_ids.length; i++) {
            //如果是系统管理员
            if ("2".equals(role_ids[i]) || "ee228c85-d868-4228-90b9-41f3377806c0".equals(role_ids[i])) {
                isGly = true;
                break;
            }
        }
//        if (user != null) {
//            user_id = user.getUser_id();
//        } else if (userDev != null) {
//            user_id = userDev.getUser_id();
//        }
        String caller_username = request.getParameter("caller_username");
        String caller_tel = request.getParameter("caller_tel");
        int page=Integer.parseInt(request.getParameter("page"));
        int pagesize=Integer.parseInt(request.getParameter("pagesize"));
        int offset = (page-1)*pagesize;
        String event_no = request.getParameter("event_no");
        String event_content = request.getParameter("event_content");
        List<Map<String, Object>> list = this.erService.getEventReportWfList(offset, pagesize, "", "", caller_username, caller_tel, isGly, event_status, "", "", "", "", "", event_no,
                event_content);
        long count = this.erService.getEventReportWfList("", "", caller_username, caller_tel, isGly, event_status, "", "", "", "", "", event_no, event_content);
        
        return UtilsHelper.returnMap(list,count);
    }
    /**
     * 获取潍坊已撤销事件（部门）
     * @param request
     * @return
     */
    @RequestMapping("getWfChexiaoEventList_dept")
    @ResponseBody
    public Object getWfChexiaoEventList_dept(String parentId, HttpServletRequest request){
        WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
        WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
        boolean isGly = false;
        //String user_id = "";
        String user_dept = "";
        String event_status = "撤销";
        String dateF = "";
        String roleIds = request.getSession().getAttribute("userRoleId")+"";
        String[] role_ids = roleIds.split(",");
        for (int i = 0; i < role_ids.length; i++) {
            //如果是系统管理员
            if ("2".equals(role_ids[i]) || "ee228c85-d868-4228-90b9-41f3377806c0".equals(role_ids[i])) {
                isGly = true;
                break;
            }
        }
        if (user != null) {
            user_dept = user.getDept_id();
            //user_id = user.getUser_id();
        } else if (userDev != null) {
            user_dept = userDev.getDept_id();
            //user_id = userDev.getUser_id();
        }
        String caller_username = request.getParameter("caller_username");
        String caller_tel = request.getParameter("caller_tel");
        int page=Integer.parseInt(request.getParameter("page"));
        int pagesize=Integer.parseInt(request.getParameter("pagesize"));
        int offset = (page-1)*pagesize;
        
        List<Map<String, Object>> list = this.erService.getEventReportWfList(offset, pagesize, "", user_dept, caller_username, caller_tel, isGly, event_status, "", "", "", dateF, "", "", "");
        long count = this.erService.getEventReportWfList("", user_dept, caller_username, caller_tel, isGly, event_status, "", "", "", dateF, "", "", "");
        
        return UtilsHelper.returnMap(list,count);
    }
    /**
     * 获取已退回事件
     * @param request
     * @return
     */
    @RequestMapping("getBackedEventWfList")
    @ResponseBody
    public Object getBackedEventWfList(String parentId, HttpServletRequest request){
        boolean isGly = false;
        String event_status = "退回";
        String roleIds = request.getSession().getAttribute("userRoleId")+"";
        String[] role_ids = roleIds.split(",");
        for (int i = 0; i < role_ids.length; i++) {
            //如果是系统管理员
            if ("2".equals(role_ids[i]) || "ee228c85-d868-4228-90b9-41f3377806c0".equals(role_ids[i])) {
                isGly = true;
                break;
            }
        }
        String caller_username = request.getParameter("caller_username");
        String caller_tel = request.getParameter("caller_tel");
        int page=Integer.parseInt(request.getParameter("page"));
        int pagesize=Integer.parseInt(request.getParameter("pagesize"));
        int offset = (page-1)*pagesize;
        
        List<Map<String, Object>> list = this.erService.getEventReportWfList(offset, pagesize, "", "", caller_username, caller_tel, isGly, event_status, "", "", "", "", "", "", "");
        long count = this.erService.getEventReportWfList("", "", caller_username, caller_tel, isGly, event_status, "", "", "", "", "", "", "");
        
        return UtilsHelper.returnMap(list,count);
    }
    /**
     * 获取已催办事件
     * @param request
     * @return
     */
    @RequestMapping("getYicuibanEventWfList")
    @ResponseBody
    public Object getYicuibanEventList(String parentId, HttpServletRequest request){
        WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
        WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
        boolean isGly = false;
        String user_id = "";
        String cuiban_status = "1";//
        String roleIds = request.getSession().getAttribute("userRoleId")+"";
        String[] role_ids = roleIds.split(",");
        for (int i = 0; i < role_ids.length; i++) {
            //如果是系统管理员
            if ("2".equals(role_ids[i]) || "ee228c85-d868-4228-90b9-41f3377806c0".equals(role_ids[i])) {
                isGly = true;
                break;
            }
        }
        if (user != null) {
            //user_id = user.getUser_id();
            user_id = user.getUser_id();
        } else if (userDev != null) {
            //user_id = userDev.getUser_id();
            user_id = userDev.getUser_id();
        }
        String caller_username = request.getParameter("caller_username");
        String caller_tel = request.getParameter("caller_tel");
        int page=Integer.parseInt(request.getParameter("page"));
        int pagesize=Integer.parseInt(request.getParameter("pagesize"));
        int offset = (page-1)*pagesize;
        
        List<Map<String, Object>> list = this.erService.getEventReportWfList(offset, pagesize, user_id, "", caller_username, caller_tel, isGly, "", "", cuiban_status, "", "", "", "", "");
        long count = this.erService.getEventReportWfList(user_id, "", caller_username, caller_tel, isGly, "", "", cuiban_status, "", "", "", "", "");
        
        return UtilsHelper.returnMap(list,count);
    }
    /**
     * 获取已超时事件
     * @param request
     * @return
     */
    @RequestMapping("getOvertimeEventWfList")
    @ResponseBody
    public Object getOvertimeEventList(String parentId, HttpServletRequest request){
        WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
        WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
        boolean isGly = false;
        String user_id = "";
        String overtime_status = "1";//超时
        String roleIds = request.getSession().getAttribute("userRoleId")+"";
        String[] role_ids = roleIds.split(",");
        for (int i = 0; i < role_ids.length; i++) {
            //如果是系统管理员
            if ("2".equals(role_ids[i]) || "ee228c85-d868-4228-90b9-41f3377806c0".equals(role_ids[i])) {
                isGly = true;
                break;
            }
        }
        if (user != null) {
            //user_id = user.getUser_id();
            user_id = user.getUser_id();
        } else if (userDev != null) {
            //user_id = userDev.getUser_id();
            user_id = userDev.getUser_id();
        }
        String caller_username = request.getParameter("caller_username");
        String caller_tel = request.getParameter("caller_tel");
        int page=Integer.parseInt(request.getParameter("page"));
        int pagesize=Integer.parseInt(request.getParameter("pagesize"));
        int offset = (page-1)*pagesize;
        
        List<Map<String, Object>> list = this.erService.getEventReportWfList(offset, pagesize, user_id, "", caller_username, caller_tel, isGly, "", "", "", overtime_status, "", "", "", "");
        long count = this.erService.getEventReportWfList(user_id, "", caller_username, caller_tel, isGly, "", "", "", overtime_status, "", "", "", "");
        
        return UtilsHelper.returnMap(list,count);
    }
    /**
     * 获取潍坊延时事件
     * @param request
     * @return
     */
    @RequestMapping("getDelayedEventWfList")
    @ResponseBody
    public Object getDelayedEventList(String parentId, HttpServletRequest request){
//        WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
//        WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
//        String user_id = "";
        boolean isGly = false;
        //String event_status = "办结";
        String roleIds = request.getSession().getAttribute("userRoleId")+"";
        String[] role_ids = roleIds.split(",");
        for (int i = 0; i < role_ids.length; i++) {
            //如果是系统管理员
            if ("2".equals(role_ids[i]) || "ee228c85-d868-4228-90b9-41f3377806c0".equals(role_ids[i])) {
                isGly = true;
                break;
            }
        }
//        if (user != null) {
//            user_id = user.getUser_id();
//        } else if (userDev != null) {
//            user_id = userDev.getUser_id();
//        }
        String caller_username = request.getParameter("caller_username");
        String caller_tel = request.getParameter("caller_tel");
        int page=Integer.parseInt(request.getParameter("page"));
        int pagesize=Integer.parseInt(request.getParameter("pagesize"));
        int offset = (page-1)*pagesize;
        
        List<Map<String, Object>> list = this.erService.getDelayedEventWfList(offset, pagesize, "", caller_username, caller_tel, isGly);
        long count = this.erService.getDelayedEventWfList("", caller_username, caller_tel, isGly);
        
        return UtilsHelper.returnMap(list,count);
    }
    /**
     * 获取潍坊未接收事件
     * @param request
     * @return
     */
    @RequestMapping("getWfNotAcceptList")
    @ResponseBody
    public Object getWfNotAcceptList(String parentId, HttpServletRequest request){
//        WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
//        WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
//        String user_id = "";
        boolean isGly = false;
        String qianshou_status = "0";//未签收
        String event_status = "1";
        String dateF = "";
        String roleIds = request.getSession().getAttribute("userRoleId")+"";
        String[] role_ids = roleIds.split(",");
        for (int i = 0; i < role_ids.length; i++) {
            //如果是系统管理员
            if ("2".equals(role_ids[i]) || "ee228c85-d868-4228-90b9-41f3377806c0".equals(role_ids[i])) {
                isGly = true;
                break;
            }
        }
//        if (user != null) {
//            user_id = user.getUser_id();
//        } else if (userDev != null) {
//            user_id = userDev.getUser_id();
//        }
        String caller_username = request.getParameter("caller_username");
        String caller_tel = request.getParameter("caller_tel");
        int page=Integer.parseInt(request.getParameter("page"));
        int pagesize=Integer.parseInt(request.getParameter("pagesize"));
        int offset = (page-1)*pagesize;
        String event_no = request.getParameter("event_no");
        String event_content = request.getParameter("event_content");
        List<Map<String, Object>> list = this.erService.getEventReportWfList(offset, pagesize, "", "", caller_username, caller_tel, isGly, event_status, qianshou_status, "", "", dateF,"",
                event_no, event_content);
        long count = this.erService.getEventReportWfList("", "", caller_username, caller_tel, isGly, event_status, qianshou_status, "", "", dateF, "", event_no, event_content);
        
        return UtilsHelper.returnMap(list,count);
    }
    /**
     * 获取潍坊未接收事件（部门）
     * @param request
     * @return
     */
    @RequestMapping("getWfNotAcceptList_dept")
    @ResponseBody
    public Object getWfNotAcceptList_dept(String parentId, HttpServletRequest request){
        WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
        WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
        boolean isGly = false;
        String user_dept = "";
        //String shenhe_status = "2";
        String qianshou_status = "0";//未签收
        String event_status = "1";
        String dateF = "";
        String roleIds = request.getSession().getAttribute("userRoleId")+"";
        String[] role_ids = roleIds.split(",");
        for (int i = 0; i < role_ids.length; i++) {
            //如果是系统管理员
            if ("2".equals(role_ids[i]) || "ee228c85-d868-4228-90b9-41f3377806c0".equals(role_ids[i])) {
                isGly = true;
                break;
            }
        }
        if (user != null) {
            //user_id = user.getUser_id();
            user_dept = user.getDept_id();
        } else if (userDev != null) {
            //user_id = userDev.getUser_id();
            user_dept = userDev.getDept_id();
        }
        String caller_username = request.getParameter("caller_username");
        String caller_tel = request.getParameter("caller_tel");
        int page=Integer.parseInt(request.getParameter("page"));
        int pagesize=Integer.parseInt(request.getParameter("pagesize"));
        int offset = (page-1)*pagesize;
        List<Map<String, Object>> list = this.erService.getEventReportWfList(offset, pagesize, "", user_dept, caller_username, caller_tel, isGly, event_status, qianshou_status, "", "", dateF, "", "", "");
        long count = this.erService.getEventReportWfList("", user_dept, caller_username, caller_tel, isGly, event_status, qianshou_status, "", "", dateF, "", "", "");
        return UtilsHelper.returnMap(list,count);
    }
    /**
     * 获取潍坊未回复事件
     * @param request
     * @return
     */
    @RequestMapping("getWfNotReplyList")
    @ResponseBody
    public Object getWfNotReplyList(String parentId, HttpServletRequest request){
        WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
        WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
        boolean isGly = false;
        //String user_id = "";
        String user_dept = "";
        String event_status = "1";
        //String shenhe_status = "2";//已审核已转发
        String qianshou_status = "1";//已签收
        String dateF = "";
        String roleIds = request.getSession().getAttribute("userRoleId")+"";
        String[] role_ids = roleIds.split(",");
        for (int i = 0; i < role_ids.length; i++) {
            //如果是系统管理员
            if ("2".equals(role_ids[i]) || "ee228c85-d868-4228-90b9-41f3377806c0".equals(role_ids[i])) {
                isGly = true;
                break;
            }
        }
        if (user != null) {
            //user_id = user.getUser_id();
            user_dept = user.getDept_id();
        } else if (userDev != null) {
            //user_id = userDev.getUser_id();
            user_dept = userDev.getDept_id();
        }
        String caller_username = request.getParameter("caller_username");
        String caller_tel = request.getParameter("caller_tel");
        int page=Integer.parseInt(request.getParameter("page"));
        int pagesize=Integer.parseInt(request.getParameter("pagesize"));
        int offset = (page-1)*pagesize;
        String event_no = request.getParameter("event_no");
        String event_content = request.getParameter("event_content");
//        List<Map<String, Object>> list = this.erService.getWfNotReplyList(offset, pagesize, "", user_dept, caller_username, caller_tel, isGly, event_status, qianshou_status, "", "", dateF);
//        long count = this.erService.getWfNotReplyList("", user_dept, caller_username, caller_tel, isGly, event_status, qianshou_status, "", "", dateF);
        List<Map<String, Object>> list = this.erService.getEventReportWfList(offset, pagesize, "", user_dept, caller_username, caller_tel, isGly, event_status, qianshou_status, "", "", dateF, "", event_no, event_content);
        long count = this.erService.getEventReportWfList("", user_dept, caller_username, caller_tel, isGly, event_status, qianshou_status, "", "", dateF, "", event_no, event_content);

        return UtilsHelper.returnMap(list,count);
    }
    
    @RequestMapping("getDataDictionary")
    @ResponseBody
    public Object getDataDictionary(String parentID){
        Map<String,Object> map = new HashMap<String, Object>();
        //String parentID = "";
        if ("deal_days".equals(parentID)) {
            List<Map<String,Object>> temp_list = new ArrayList<Map<String,Object>>();
            for (int i = 1; i <= 30; i++) {
                Map<String,Object> temp_map = new HashMap<String,Object>();
                temp_map.put("id", i+"");
                temp_map.put("name", i+"");
                temp_list.add(temp_map);
            }
            return temp_list;
        }
        try {
            List<Map<String,Object>> list = erService.getDataDictionary(parentID);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    
    @RequestMapping("addEventWf")
    @ResponseBody
    public AjaxJson addEventReport(HttpServletRequest request, WkrjEventWf er){
        WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
        WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
        AjaxJson json = new AjaxJson();
        //String user_name = "";
        String user_id = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        er.setInput_time(date);
        if (user != null) {
            //user_name = user.getUser_name();
            user_id = user.getUser_id();
        } else if (userDev != null) {
            //user_name = userDev.getUser_name();
            user_id = userDev.getUser_id();
        }
        er.setInput_user(user_id);
        String event_no = generateEventNo(er.getEvent_no());
        if (event_no == null || "".equals(event_no)) {//针对手动录入的情况
            event_no = er.getEvent_no();
        }
        er.setEvent_no(event_no);
        er.setEvent_status("1");
        //er.setEvent_inputuserdept(user_dept);
        String chengbanDept = er.getChengban_dept();
        boolean tempFlag = false;
        if (chengbanDept != null && chengbanDept != "") {
            String[] chengbanDepts = chengbanDept.split(";|,");
            for (int i = 0; i < chengbanDepts.length; i++) {
                er.setChengban_dept(chengbanDepts[i]);
                if (!erService.addEventWf(er)) {
                    tempFlag = true;
                }
            }
        }
        if (!tempFlag) {
            json.setSuccess(true);
            json.setMsg("保存成功");
        }
        return json;
    }
    
    /*
     * 生成事件编号
     */
    private String generateEventNo(String event_no) {
        if (event_no != null && !"".equals(event_no)) {
            if (event_no.contains("-")) {
                String temp = event_no.split("-")[1];
                int tempno = Integer.parseInt(temp);
                tempno++;
                event_no = event_no.split("-")[0]+"-"+tempno;
            } else {
                event_no = event_no+"-"+1;
            }
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String date = sdf.format(new Date());
            long cnt = erService.getEventReportWfList("", "", "", "", false, "", "", "", "", date, "", "", "");
            cnt++;
            if (cnt < 10) {
                event_no = date+"000"+cnt;
            }
            if (cnt < 100 && cnt >= 10) {
                event_no = date+"00"+cnt;
            }
            if (cnt < 1000 && cnt >= 100) {
                event_no = date+"0"+cnt;
            }
            if (cnt < 10000 && cnt >= 1000) {
                event_no = date+cnt;
            }
        }
        return event_no;
    }
//    @RequestMapping("zhuanbanEvent_back")
//    @ResponseBody
//    public AjaxJson zhuanbanEvent_back(WkrjEventWf er, HttpServletRequest request){
//        WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
//        WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
//        AjaxJson json = new AjaxJson();
//        if ("1".equals(er.getDeal_type())) {
//            er.setEvent_status("1");
//            boolean zhuanbanFlag = false;
//            if (er.getChengban_dept_old() != null && !"".equals(er.getChengban_dept_old()) && er.getChengban_dept().equals(er.getChengban_dept_old())) {
//                if (er.getEvent_no().contains("-")) {
//                    String temp = er.getEvent_no().split("-")[1];
//                    int tempno = Integer.parseInt(temp);
//                    tempno++;
//                    er.setEvent_no(er.getEvent_no().split("-")[0]+"-"+tempno);
//                } else {
//                    er.setEvent_no(er.getEvent_no()+"-"+2);//再次转办从“-2”开始
//                }
//                zhuanbanFlag = true;
//            }
//            //er.setEvent_no(generateEventNo(er.getEvent_no()));
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String date = sdf.format(new Date());
//            er.setInput_time(date);
//            er.setZhuanbanornot("1");//设置已再次转办
//            String user_id = "";
//            if (user != null) {
//                user_id = user.getUser_id();
//            } else if (userDev != null) {
//                user_id = userDev.getUser_id();
//            }
//            er.setAccept_workno(user_id);//再次转办的事件受理工号为当前登录工号
//            if (er.getCbd_time() == null || "".equals(er.getCbd_time())) {
//                er.setCbd_time(date);
//            }
//            if (erService.addEventWf(er)/* && erService.updateZhuanban(er)*/) {
//                if (zhuanbanFlag) {//如果是相同部门才设置再次转办状态，设置为再次转办
//                    erService.updateZhuanban(er);
//                }
//                json.setSuccess(true);
//                json.setMsg("转办成功");
//            }
//        }
//        if ("完成".equals(er.getDeal_type())) {
//            er.setEvent_status("完成");
//            if (erService.updateEventStatus(er)) {
//                json.setSuccess(true);
//                json.setMsg("事件完成");
//            }
//        }
//        return json;
//    }
    
//    @RequestMapping("shenheBackReason")
//    @ResponseBody
//    public AjaxJson shenheBackReason(WkrjEventWf er, HttpServletRequest request){
//        AjaxJson json = new AjaxJson();
//        erService.updateShenheStatus(er);//更新审核状态
//        if ("1".equals(er.getShenhe_status())) {//通过，继续转办
//            json = this.zhuanbanEvent_back(er, request);
//            if (json.isSuccess()) {
//                json.setSuccess(true);
//                json.setMsg("处理完成");
//            }
//        }
//        
//        if ("2".equals(er.getShenhe_status())) {//未通过，更新事件状态，部门继续办理
//            er.setEvent_status("1");
//            if (erService.updateEventStatus(er)) {
//                json.setSuccess(true);
//                json.setMsg("处理完成");
//            }
//        }
//        return json;
//    }
    @RequestMapping("zhuanbanEvent")
    @ResponseBody
    public AjaxJson zhuanbanEvent(WkrjEventWf er, HttpServletRequest request){
        WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
        WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
        AjaxJson json = new AjaxJson();
        er.setEvent_status("1");
        //er.setDeal_type("1");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        er.setInput_time(date);
        er.setZhuanbanornot("1");//设置已再次1
//        if (er.getEvent_no().contains("-")) {
//            String temp = er.getEvent_no().split("-")[1];
//            int tempno = Integer.parseInt(temp);
//            tempno++;
//            er.setEvent_no(er.getEvent_no().split("-")[0]+"-"+tempno);
//        } else {
//            er.setEvent_no(er.getEvent_no()+"-"+2);//再次转办从“-2”开始
//        }
        er.setEvent_no(er.getEvent_no());
        String user_id = "";
        if (user != null) {
            user_id = user.getUser_id();
        } else if (userDev != null) {
            user_id = userDev.getUser_id();
        }
        er.setInput_user(user_id);
        if (erService.addEventWf(er) && erService.updateZhuanban(er)) {
            json.setSuccess(true);
            json.setMsg("转办成功");
        }
        return json;
    }
//    @RequestMapping("finishEvent")
//    @ResponseBody
//    public AjaxJson finishEvent(WkrjEventWf er, HttpServletRequest request){
//        AjaxJson json = new AjaxJson();
//        er.setEvent_status("完成");
//        if (erService.huifangFinishEventWf(er)) {
//            json.setSuccess(true);
//            json.setMsg("事件完成");
//        }
//        return json;
//    }
//    @RequestMapping("notFinishEvent")
//    @ResponseBody
//    public AjaxJson notFinishEvent(WkrjEventWf er, HttpServletRequest request){
//        AjaxJson json = new AjaxJson();
//        er.setEvent_status("未完成");
//        if (erService.zhijieFinishEventWf(er)) {
//            json.setSuccess(true);
//            json.setMsg("已经设置未完成");
//        }
//        return json;
//    }
    @RequestMapping("pingjiaEventWf")
    @ResponseBody
    public AjaxJson pingjiaEvent(WkrjEventWf er, HttpServletRequest request){
        AjaxJson json = new AjaxJson();
        WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
        WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
        String user_id = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        er.setInput_time(date);
        if (user != null) {
            user_id = user.getUser_id();
        } else if (userDev != null) {
            user_id = userDev.getUser_id();
        }
        er.setInput_time(date);
        er.setInput_user(user_id);
        if (erService.updateSatisfyDu(er)) {
            json.setSuccess(true);
            json.setMsg("处理成功");
        }
        return json;
    }
    
    @RequestMapping("signEventWf")
    @ResponseBody
    public AjaxJson signEventWf(HttpServletRequest request, String event_id){
        
        AjaxJson json = new AjaxJson();
        WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
        WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
        String accept_dept = "";
        if (user != null) {
            accept_dept = user.getDept_id();
        } else if (userDev != null) {
            accept_dept = userDev.getDept_id();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        WkrjEventWf er = new WkrjEventWf();
        er.setEvent_id(event_id);
        er.setQianshou_status("1");
        er.setQianshou_time(date);
        er.setAccept_dept(accept_dept);
        try{
            if (!erService.isSignOrNot(event_id, accept_dept)) {
                if (erService.signEventWf(er)) {
                    json.setSuccess(true);
                    json.setMsg("签收成功");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("已经签收过");
            }
        }catch(Exception e){
            if(e.getStackTrace()[0].getClassName().indexOf("SQLErrorCodeSQLExceptionTranslator")>=0){
                json.setMsg("签收失败");
            }else{
                e.printStackTrace();
            }
        }
        return json;
    }
    
    @RequestMapping("editEventWf")
    @ResponseBody
    public AjaxJson editEventWf(HttpServletRequest request, WkrjEventWf ev){
        AjaxJson json = new AjaxJson();
        try{
            if (erService.updateEventWf(ev)) {
                json.setSuccess(true);
                json.setMsg("修改成功");
            }
        }catch(Exception e){
            if(e.getStackTrace()[0].getClassName().indexOf("SQLErrorCodeSQLExceptionTranslator")>=0){
                json.setMsg("修改失败");
            }else{
                e.printStackTrace();
            }
        }
        return json;
    }
    
    @RequestMapping("delEventWf")
    @ResponseBody
    public AjaxJson delEventWf(HttpServletRequest request, String event_id){
        AjaxJson json = new AjaxJson();
        try{
            if (erService.delEventWf(event_id)) {
                json.setSuccess(true);
                json.setMsg("删除成功");
            }
        }catch(Exception e){
            if(e.getStackTrace()[0].getClassName().indexOf("SQLErrorCodeSQLExceptionTranslator")>=0){
                json.setMsg("删除失败");
            }else{
                e.printStackTrace();
            }
        }
        return json;
    }
    
    @RequestMapping("delayEventWf")
    @ResponseBody
    public AjaxJson delayEvent(HttpServletRequest request, String event_id, String event_no, String opt_content){
        AjaxJson json = new AjaxJson();
        WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
        WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
        String input_user = "";
        if (user != null) {
            input_user = user.getUser_id();
        } else if (userDev != null) {
            input_user = userDev.getUser_id();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        WkrjProcedureRecord pr = new WkrjProcedureRecord();
        pr.setEvent_id(event_id);
        pr.setEvent_no(event_no);
        pr.setPr_id(Guid.getGuid());
        pr.setOpt_time(date);
        pr.setOpt_user(input_user);
        pr.setOpt_content(opt_content);
        WkrjEventWf wf = new WkrjEventWf();
        wf.setEvent_id(event_id);
        wf.setEvent_status("4");//设置状态，未回复事件中就查询不到了
        try{
            if (erService.delayEventWf(pr) && erService.updateEventStatus(wf)) {
                json.setSuccess(true);
                json.setMsg("延时成功");
            }
        }catch(Exception e){
            if(e.getStackTrace()[0].getClassName().indexOf("SQLErrorCodeSQLExceptionTranslator")>=0){
                json.setMsg("延时失败");
                e.printStackTrace();
            }else{
                e.printStackTrace();
            }
        }
        return json;
    }
    
    @RequestMapping("replyEventWf")
    @ResponseBody
    public AjaxJson replyEventWf(HttpServletRequest request, String event_id, String event_no, String opt_content){
        AjaxJson json = new AjaxJson();
        WkrjEventWf erWf = erService.getEventInfoById(event_id);
        if ("撤销".equals(erWf.getEvent_status())) {
            json.setSuccess(false);
            json.setMsg("回复失败，该事件已撤销，请刷新");
            return json;
        }
        WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
        WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
        String input_user = "";
        if (user != null) {
            input_user = user.getUser_id();
        } else if (userDev != null) {
            input_user = userDev.getUser_id();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        WkrjProcedureRecord pr = new WkrjProcedureRecord();
        pr.setEvent_id(event_id);
        pr.setEvent_no(event_no);
        pr.setPr_id(Guid.getGuid());
        pr.setOpt_time(date);
        pr.setOpt_user(input_user);
        pr.setOpt_content(opt_content);
        pr.setOpt_type("1");//回复
        //String event_status = "2";
        String qianshou_status = "2";//2表示已回复
//        String file_yname = request.getParameter("file_yname");
//        String file_xname = request.getParameter("file_xname");
        //String file_type = request.getParameter("file_type");
//        WkrjDeptOpinionFileWf opinionFile = new WkrjDeptOpinionFileWf();
//        opinionFile.setFile_xname(file_xname);
//        opinionFile.setFile_yname(file_yname);
//        opinionFile.setFile_inputtime(date);
//        opinionFile.setFile_inputuser(input_user);
//        opinionFile.setOpinion_id(opinion.getOpinionID());
        try{
            if (erService.replyEventWf(pr) && erService.setRepliedStatus(event_id, qianshou_status)/* && erService.addDeptOpinionFileWf(opinionFile)*/) {
                json.setSuccess(true);
                json.setMsg("回复成功");
            }
        }catch(Exception e){
            if(e.getStackTrace()[0].getClassName().indexOf("SQLErrorCodeSQLExceptionTranslator")>=0){
                json.setMsg("回复失败");
                e.printStackTrace();
            }else{
                e.printStackTrace();
            }
        }
        return json;
    }
    
    @RequestMapping("sendBackEventWf")
    @ResponseBody
    public AjaxJson sendBackEventWf(HttpServletRequest request, String qianshou_status, String event_id, String event_no, String event_inputtime, String opt_content){
        AjaxJson json = new AjaxJson();
        WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
        WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
        String input_user = "";
        if (user != null) {
            input_user = user.getUser_id();
        } else if (userDev != null) {
            input_user = userDev.getUser_id();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        
        Calendar calen = Calendar.getInstance();
        //日历对象默认的日期为当前日期，调用setTime设置该日历对象的日期为程序中指定的日期
        try {
            calen.setTime(sdf.parse(event_inputtime));
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        int temp = 1;
        //将日历的"天"增加1
        calen.add(Calendar.DAY_OF_YEAR,temp);
        //获取日历对象的时间，并赋给日期对象c
        Date c = calen.getTime();
        if (c.before(new Date())) {
            json.setSuccess(false);
            json.setMsg("已超过24小时不能退回");
            return json;
        }
        WkrjProcedureRecord pr = new WkrjProcedureRecord();
        pr.setEvent_id(event_id);
        if ("2".equals(qianshou_status)) {//如果是回复之后的退回，则改变编号，为了后期方便统计办结率
            //pr.setEvent_no(generateEventNo(event_no));
        }
        pr.setEvent_no(event_no);
        pr.setPr_id(Guid.getGuid());
        pr.setOpt_time(date);
        pr.setOpt_user(input_user);
        pr.setOpt_content(opt_content);
        pr.setOpt_type("3");//退回
        String event_status = "3";//退回
        WkrjEventWf er = new WkrjEventWf();
        er.setEvent_id(event_id);
        er.setEvent_status(event_status);
        try{
            if (erService.replyEventWf(pr) && erService.updateEventStatus(er)) {
                json.setSuccess(true);
                json.setMsg("退回成功");
            }
        }catch(Exception e){
            if(e.getStackTrace()[0].getClassName().indexOf("SQLErrorCodeSQLExceptionTranslator")>=0){
                json.setMsg("退回失败");
                e.printStackTrace();
            }else{
                e.printStackTrace();
            }
        }
        return json;
    }
    
//    @RequestMapping("cuibanEvent")
//    @ResponseBody
//    public AjaxJson cuibanEvent(HttpServletRequest request, String event_id){
//        AjaxJson json = new AjaxJson();
//        WkrjEventWf er = new WkrjEventWf();
//        er.setEvent_id(event_id);
//        er.setCuiban_status("1");
//        try{
//                if (erService.cuibanEventWf(er)) {
//                    json.setSuccess(true);
//                    json.setMsg("催办成功");
//                }
//        }catch(Exception e){
//            if(e.getStackTrace()[0].getClassName().indexOf("SQLErrorCodeSQLExceptionTranslator")>=0){
//                json.setMsg("催办失败");
//            }else{
//                e.printStackTrace();
//            }
//        }
//        return json;
//    }
    
    /**
     * 
     * @param er_icons 上传文件的name名称
     * @param request
     * @param foledArrress 保存文件的路径，
     * @return
     */
    @RequestMapping("/uploadPic")
    @ResponseBody
    public String uploadPic(MultipartFile fuheReason_file,HttpServletRequest request,String foledArrress){
        return this.erService.uploadPic(fuheReason_file, request, foledArrress);
    }

    @RequestMapping("getUserList")
    @ResponseBody
    public Object getUserList(HttpServletRequest request, String deptId){
        int page=Integer.parseInt(request.getParameter("page"));
        String userName = request.getParameter("userName");
        //String schoolId = request.getParameter("schoolId");
        int pagesize=Integer.parseInt(request.getParameter("pagesize"));
        
        WkrjUser sessionUser = (WkrjUser) request.getSession().getAttribute("user");
        WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
        boolean isGly = false;
        //String user_id = "";
        String user_dept = "";
        String roleIds = request.getSession().getAttribute("userRoleId")+"";
        String[] role_ids = roleIds.split(",");
        for (int i = 0; i < role_ids.length; i++) {
            //如果是系统管理员
            if ("2".equals(role_ids[i]) || "ee228c85-d868-4228-90b9-41f3377806c0".equals(role_ids[i])) {
                isGly = true;
                break;
            }
        }
        if (sessionUser != null) {
            //user_id = sessionUser.getUser_id();
            user_dept = sessionUser.getDept_id();
        } else if (userDev != null) {
            //user_id = userDev.getUser_id();
            user_dept = userDev.getDept_id();
        }
        
        int offset = (page-1)*pagesize;
        List<WkrjUser> list = this.userService.getUserList(offset, pagesize, deptId, userName, isGly, user_dept);
        for(WkrjUser user:list){
            //获取角色信息
            user.setUser_role(this.userService.getRoleListByUserId(user.getUser_id()));
        }
        long count = this.userService.getUserList(deptId, userName, isGly, user_dept);
        
        return UtilsHelper.returnMap(list,count);
    }
    
  @RequestMapping("getUserTree")
  @ResponseBody
  public Object getUserTree(){
      Map<String,Object> map = new HashMap<String, Object>();
      try {
          List<WkrjUser> list = userService.getUserList(0, 1000000, "", "", true, "");
          return list;
      } catch (Exception e) {
          e.printStackTrace();
      }
      return map;
  }
    
    /**
     * 获取部门树
     * @return
     */
    @RequestMapping("getDeptTree")
    @ResponseBody
    public Object getDeptTree(HttpServletRequest request){
        deptService = (WkrjDeptService) SpringContextUtil.getBean("wkrjDeptService");
        
        WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
        WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
        boolean isGly = false;
        String user_dept = "";
        String roleIds = request.getSession().getAttribute("userRoleId")+"";
        String[] role_ids = roleIds.split(",");
        for (int i = 0; i < role_ids.length; i++) {
            //如果是系统管理员
            if ("2".equals(role_ids[i]) || "ee228c85-d868-4228-90b9-41f3377806c0".equals(role_ids[i])) {
                isGly = true;
                break;
            }
        }
        if (user != null) {
            user_dept = user.getDept_id();
        } else if (userDev != null) {
            user_dept = userDev.getDept_id();
        }
        
        return deptService.getRoleList("-1",isGly,user_dept);
    }
    /**
     * 下载附件
     * @param request
     * @param response
     * @param fileName
     * @param realName
     * @throws Exception
     */
    @RequestMapping("/downloadFile")
    public static void downloadFile(HttpServletRequest request,
            HttpServletResponse response, String fileName,
            String realName)  {
        response.setContentType("text/html;charset=UTF-8");  
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }  
        BufferedInputStream bis = null;  
        BufferedOutputStream bos = null;  
  
        String erPath = new WkrjEventWfController().getClass().getClassLoader().getResource("/")
                .getPath()+ "../../upload/message/";  
        String downLoadPath = erPath + fileName;
  
        long fileLength = new File(downLoadPath).length();  
  
        try {
             response.setHeader("Content-disposition", "attachment; filename="  
                    + new String(realName.getBytes("utf-8"), "ISO8859-1"));
            
             response.setHeader("Content-Length", String.valueOf(fileLength));  
              
                bis = new BufferedInputStream(new FileInputStream(downLoadPath));  
                bos = new BufferedOutputStream(response.getOutputStream());  
                byte[] buff = new byte[2048];  
                int bytesRead;  
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
                    bos.write(buff, 0, bytesRead);  
                }  
                bis.close();  
                bos.close();  
            
        } catch (Exception e) {
            
            response.setHeader("Location", response.encodeRedirectURL("index"));
            
        }  
    }
    /**
     * 获取部门回复格式
     * @param request
     * @return
     */
    @RequestMapping("getDeptReplyFormat")
    @ResponseBody
    public AjaxJson getDeptReplyFormat(HttpServletRequest request){
        AjaxJson j=new AjaxJson();
        j.setSuccess(false);
        //WkrjUser user=(WkrjUser) request.getSession().getAttribute("user");                             
        List<Map<String, Object>> list=jdbcTemplate.queryForList("SELECT value from `wkrj_sysconfig` where name='reply_template'");
        if(list!=null && list.size()==1){
            j.setObj(list.get(0).get("value")==null?"":list.get(0).get("value")+"");                              
            j.setSuccess(true);
        }
        return j;
    }
    /**
     * 导出督办
     * @return
     */
//    @RequestMapping(value = "/exportDubanWf", produces = "text/html; charset=utf-8")
//    @ResponseBody
//    public String exportDubanWf(String start_date, String end_date, HttpServletResponse response, HttpServletRequest request) {
//        WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
//        WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
//        boolean isGly = false;
//        String user_dept = "";
//        String roleIds = request.getSession().getAttribute("userRoleId")+"";
//        String[] role_ids = roleIds.split(",");
//        for (int i = 0; i < role_ids.length; i++) {
//            //如果是系统管理员
//            if ("2".equals(role_ids[i]) || "ee228c85-d868-4228-90b9-41f3377806c0".equals(role_ids[i])) {
//                isGly = true;
//                break;
//            }
//        }
//        if (user != null) {
//            user_dept = user.getDept_id();
//        } else if (userDev != null) {
//            user_dept = userDev.getDept_id();
//        }
//        try {
//            String filename = erService.exportDubanWf(start_date, end_date, response, isGly, user_dept);
//            String st = "{success:true,file_name:'" + filename + "'}";
//            return st;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "{success:false}";
//    }
    /**
     * 
     * @param module_icons
     *            上传
     * @param request
     * @param foledArrress
     *            保存文件的路径，
     * @return
     */
//    @RequestMapping("/uploadCbd")
//    @ResponseBody
//    public AjaxJson uploadCbd(HttpServletRequest request, String foledArrress) {
//
//        AjaxJson j = new AjaxJson();
//        // String msg="操作失败！";
//        boolean flag = false;
//
//        Map<String, Object> wordContent = new HashMap<String, Object>();
//
//        FileUtils.readWordContent(request, "cbd", wordContent);
//        if (wordContent.size() > 0)
//            flag = true;
//        j.setAttributes(wordContent);
//
//        j.setSuccess(flag);
//        return j;
//    }
    /**
     * 
     * @param module_icons
     *            上传
     * @param request
     * @param foledArrress
     *            保存文件的路径，
     * @return
     */
//    @RequestMapping("/uploadCbd_new")
//    @ResponseBody
//    public AjaxJson uploadCbd_new(HttpServletRequest request, String foledArrress) {
//
//        AjaxJson j = new AjaxJson();
//        // String msg="操作失败！";
//        boolean flag = false;
//
//        Map<String, Object> wordContent = new HashMap<String, Object>();
//
//        FileUtils.readCbdContent(request, "cbd", wordContent);
//        if (wordContent.size() > 0)
//            flag = true;
//        j.setAttributes(wordContent);
//
//        j.setSuccess(flag);
//        return j;
//    }
    /**
     * 获取打印页面
     * @param request
     * @param erWf
     * @return
     */
    @RequestMapping("getPrintPage")
    public ModelAndView getPrintPage(HttpServletRequest request,WkrjEventWf erWf){
        //Map<String,Object> map = msRddbService.getRddbInfoById(rd_id);
        //HashMap<String, Object> map_new=new HashMap<String, Object>();
        //String event_inputtime = request.getParameter("event_inputtime");
        String event_id = request.getParameter("event_id");
        erWf = erService.getEventInfoById(event_id);
        //erWf.setInput_time(event_inputtime);
        request.setAttribute("map", erWf);
        return new ModelAndView("forward:/page/sjc/wfNotReply/printPage.jsp");
    }
}
