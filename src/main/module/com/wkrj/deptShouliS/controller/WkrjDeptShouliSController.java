package com.wkrj.deptShouliS.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wkrj.deptShouliS.service.WkrjDeptShouliSService;

import wkrjsystem.user.service.WkrjUserService;
import wkrjsystem.utils.AjaxJson;
import wkrjsystem.utils.UtilsHelper;

@Controller
@RequestMapping("deptChengbanS/WkrjDeptChengbanS")
public class WkrjDeptShouliSController {
    
    @Autowired
    private WkrjDeptShouliSService ssService;
    
    @Autowired
    private WkrjUserService userService;
    
    //路径
    //private String realPath = "dept/";
    
    @RequestMapping("getPage")
    public String getPage(){
        return "system/dept/dept";
    }
    
    @RequestMapping("getDeptChengbanList")
    @ResponseBody
    public Object getDeptChengbanList(HttpServletRequest request){
        String start_date = request.getParameter("start_date");
        String end_date = request.getParameter("end_date");
        if (start_date == null || "".equals(start_date)) {
            start_date = UtilsHelper.getBeforeDate();
        }
        if (end_date == null || "".equals(end_date)) {
            end_date = UtilsHelper.getDateFormatDate();
        }
        String type = request.getParameter("type");
        String isshizhi = request.getParameter("isshizhi");
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            List<Map<String,Object>> list = ssService.getDeptChengbanList_new(type, start_date, end_date, "", isshizhi);
            map.put("Rows", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    @RequestMapping("getEventInfoList")
    @ResponseBody
    public Object getEventReportList(String parentId, HttpServletRequest request){
        String flag = request.getParameter("flag");
        String dept_id = request.getParameter("dept_id");
        String type = request.getParameter("type");
        String start_date = request.getParameter("start_date");
        String end_date = request.getParameter("end_date");
        if (start_date == null || "".equals(start_date)) {
            start_date = UtilsHelper.getBeforeDate();
        }
        if (end_date == null || "".equals(end_date)) {
            end_date = UtilsHelper.getDateFormatDate();
        }
        int page=Integer.parseInt(request.getParameter("page"));
        int pagesize=Integer.parseInt(request.getParameter("pagesize"));
        int offset = (page-1)*pagesize;
        List<Map<String, Object>> list = null;
        long count = 0;
        if ("1".equals(flag)) {//实际完成
            list = this.ssService.getFactFinishWfList(offset, pagesize, dept_id, type, start_date, end_date);
            count = this.ssService.getFactFinishWfList(dept_id, type, start_date, end_date);
        } else if ("2".equals(flag)) {//延时
            list = this.ssService.getDelayWfList(offset, pagesize, dept_id, type, start_date, end_date);
            count = this.ssService.getDelayWfList(dept_id, type, start_date, end_date);
        } else if ("3".equals(flag)) {//转发两次及以上
            list = this.ssService.getTwoUpWfList(offset, pagesize, dept_id, type, start_date, end_date);
            count = this.ssService.getTwoUpWfList(dept_id, type, start_date, end_date);
        } else if ("4".equals(flag)) {//超时
            list = this.ssService.getOvertimeNotReplyWfList(offset, pagesize, dept_id, type, start_date, end_date);
            count = this.ssService.getOvertimeNotReplyWfList(dept_id, type, start_date, end_date);
        } else {
            list = this.ssService.getEventInfoList(offset, pagesize, dept_id, type, start_date, end_date);
            count = this.ssService.getEventInfoList(dept_id, type, start_date, end_date);
        }
        return UtilsHelper.returnMap(list,count);
    }
    @RequestMapping("getSatisfyDuWfList")
    @ResponseBody
    public Object getSatisfyDuWfList(String parentId, HttpServletRequest request){
        String satisfy_status_dept = request.getParameter("satisfy_status_dept");
        String dept_id = request.getParameter("dept_id");
        //String type = request.getParameter("type");
        String start_date = request.getParameter("start_date");
        String end_date = request.getParameter("end_date");
        if (start_date == null || "".equals(start_date)) {
            start_date = UtilsHelper.getBeforeDate();
        }
        if (end_date == null || "".equals(end_date)) {
            end_date = UtilsHelper.getDateFormatDate();
        }
        int page=Integer.parseInt(request.getParameter("page"));
        int pagesize=Integer.parseInt(request.getParameter("pagesize"));
        int offset = (page-1)*pagesize;
        List<Map<String, Object>> list = null;
        long count = 0;
        list = this.ssService.getSatisfyDuWfList(offset, pagesize, dept_id, satisfy_status_dept, start_date, end_date);
        count = this.ssService.getSatisfyDuWfList(dept_id, satisfy_status_dept, start_date, end_date);
        return UtilsHelper.returnMap(list,count);
    }
    /**
     * 导出统计
     * @param start_date
     * @param end_date
     * @return
     */
    @RequestMapping(value = "/export", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String export(String type, String start_date, String end_date, String isshizhi, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (start_date == null || "".equals(start_date)) {
                start_date = UtilsHelper.getBeforeDate();
            }
            if (end_date == null || "".equals(end_date)) {
                end_date = UtilsHelper.getDateFormatDate();
            }
            String filename = ssService.exportExcel(type, start_date, end_date, isshizhi, response);
            String st = "{success:true,file_name:'" + filename + "'}";
            return st;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{success:false}";
    }
    /**
     * 导出统计（满意度事件）
     * @param start_date
     * @param end_date
     * @return
     */
    @RequestMapping(value = "/export_satisfyDu", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String export_satisfyDu(String dept_id, String start_date, String end_date, String satisfy_status_dept, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (start_date == null || "".equals(start_date)) {
                start_date = UtilsHelper.getBeforeDate();
            }
            if (end_date == null || "".equals(end_date)) {
                end_date = UtilsHelper.getDateFormatDate();
            }
            String filename = ssService.export_satisfyDu(dept_id, start_date, end_date, satisfy_status_dept, response);
            String st = "{success:true,file_name:'" + filename + "'}";
            return st;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{success:false}";
    }
    /**
     * hightcharts-获取数据
     * @param request
     * @return
     */
    @RequestMapping("getPostsDataInfo")
    @ResponseBody
    public AjaxJson getPostsDataInfo(HttpServletRequest request) {
        String type = request.getParameter("type");
        String dept_id = request.getParameter("dept_id");
        String begin_date = request.getParameter("start_date");
        String end_date = request.getParameter("end_date");
        if (begin_date == null || "".equals(begin_date)) {
            begin_date = UtilsHelper.getBeforeDate();
        }
        if (end_date == null || "".equals(end_date)) {
            end_date = UtilsHelper.getDateFormatDate();
        }
        AjaxJson j = new AjaxJson();

        j.setMsg("操作成功");
        j.setSuccess(true);

        j.setObj(ssService.getPostsDataInfo(type, begin_date, end_date, dept_id));
        return j;
    }
}
