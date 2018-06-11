package com.wkrj.xFinishS.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wkrj.xFinishS.service.WkrjXFinishSService;

import wkrjsystem.user.service.WkrjUserService;
import wkrjsystem.utils.UtilsHelper;

@Controller
@RequestMapping("xFinishS/WkrjXFinishS")
public class WkrjXFinishSController {
    
    @Autowired
    private WkrjXFinishSService ssService;
    
    @Autowired
    private WkrjUserService userService;
    
    //路径
    //private String realPath = "dept/";
    
    @RequestMapping("getPage")
    public String getPage(){
        return "system/dept/dept";
    }
    
    @RequestMapping("getEventCntByBanjieTimes")
    @ResponseBody
    public Object getEventCntByBanjieTimes(HttpServletRequest request){
        String start_date = request.getParameter("start_date");
        String end_date = request.getParameter("end_date");
        if (start_date == null || "".equals(start_date)) {
            start_date = UtilsHelper.getBeforeDate();
        }
        if (end_date == null || "".equals(end_date)) {
            end_date = UtilsHelper.getDateFormatDate();
        }
        String isshizhi = request.getParameter("isshizhi");
        String content_type = request.getParameter("content_type");
        String sortname=request.getParameter("sortname");
        String sortorder=request.getParameter("sortorder");
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            List<Map<String,Object>> list = ssService.getEventCntByBanjieTimes_new(content_type, start_date, end_date, isshizhi, sortname, sortorder);
            map.put("Rows", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    /**
     * 导出统计
     * @param start_date
     * @param end_date
     * @return
     */
    @RequestMapping(value = "/export", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String export(String content_type, String start_date, String end_date, String isshizhi, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (start_date == null || "".equals(start_date)) {
                start_date = UtilsHelper.getBeforeDate();
            }
            if (end_date == null || "".equals(end_date)) {
                end_date = UtilsHelper.getDateFormatDate();
            }
            String filename = ssService.exportExcel(content_type, start_date, end_date, isshizhi, response);
            String st = "{success:true,file_name:'" + filename + "'}";
            return st;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{success:false}";
    }
    /**
     * 导出办结事件
     * @param start_date
     * @param end_date
     * @return
     */
    @RequestMapping(value = "/export_banjieEvent", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String export_banjieEvent(String flag, String start_date, String end_date, String dept_id, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (start_date == null || "".equals(start_date)) {
                start_date = UtilsHelper.getBeforeDate();
            }
            if (end_date == null || "".equals(end_date)) {
                end_date = UtilsHelper.getDateFormatDate();
            }
            String filename = ssService.export_banjieEvent(flag, start_date, end_date, dept_id, response);
            String st = "{success:true,file_name:'" + filename + "'}";
            return st;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{success:false}";
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
        if ("1".equals(flag)) {//一次办结
            list = this.ssService.getOnceBanjieEvent(offset, pagesize, dept_id, type, start_date, end_date);
            count = this.ssService.getOnceBanjieEvent(dept_id, type, start_date, end_date);
        } else if ("2".equals(flag)) {//二次办结
            list = this.ssService.getTimesBanjieEvent(offset, pagesize, dept_id, "-2", start_date, end_date);
            count = this.ssService.getTimesBanjieEvent(dept_id, "-2", start_date, end_date);
        } else if ("3".equals(flag)) {//三次办结
            list = this.ssService.getTimesBanjieEvent(offset, pagesize, dept_id, "-3", start_date, end_date);
            count = this.ssService.getTimesBanjieEvent(dept_id, "-3", start_date, end_date);
        } else if ("4".equals(flag)) {//四次及以上
            list = this.ssService.getFourthBanjieEvent(offset, pagesize, dept_id, start_date, end_date);
            count = this.ssService.getFourthBanjieEvent(dept_id, start_date, end_date);
        } else {
            list = this.ssService.getTimesFinishEvent(offset, pagesize, dept_id, start_date, end_date);
            count = this.ssService.getTimesFinishEvent(dept_id, start_date, end_date);
        }
        return UtilsHelper.returnMap(list,count);
    }
}
