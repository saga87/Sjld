package com.wkrj.deptShouliS.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wkrj.deptShouliS.dao.WkrjDeptShouliSDao;

import wkrjsystem.department.bean.WkrjDept;
import wkrjsystem.department.dao.WkrjDeptDao;
import wkrjsystem.utils.FileUtils;

@Service("wkrjDeptShouliSService")
@Transactional
public class WkrjDeptShouliSServiceImpl implements WkrjDeptShouliSService {

    @Autowired
    private WkrjDeptShouliSDao dao;
    
    @Autowired
    private WkrjDeptDao deptDao;
    
    @Override
    public List<Map<String,Object>> getDeptChengbanList(String type, String start_date, String end_date, String dept_id, String isshizhi){//isFirst是否第一次查询,(迭代时用到，第一次查询时，查询其父节点下的所有成员，办好自己）
        //List<Map<String,Object>> newlist=new ArrayList<Map<String,Object>>();
        if (type == null || "".equals(type)) {
            type = "2";
        }
        List<Map<String,Object>> newList = new ArrayList<Map<String,Object>>();
        //获取所有部门
        List<WkrjDept> vals = this.dao.getDeptTree("-1", true, "", isshizhi);
        for (int i = 0; i < vals.size(); i++) {
            Map<String,Object> map = new HashMap<String,Object>();  
            map.put("dept_id", vals.get(i).getDept_id());
            map.put("dept_name", vals.get(i).getDept_name());
            List<Map<String, Object>> tempList1 = null;
            List<Map<String, Object>> tempList2 = null;
            double temp = 0;
            if ("1".equals(type)) {
                tempList1 = this.dao.getDeptChengbanList(type, start_date, end_date, vals.get(i).getDept_id()+"");
                if (tempList1.size() > 0) {
                    map.put("cnt", tempList1.get(0).get("cnt"));
                    map.put("should_cnt", tempList1.get(0).get("should_cnt"));
                    map.put("fact_cnt", tempList1.get(0).get("fact_cnt"));
                    map.put("overtime_cnt", tempList1.get(0).get("overtime_cnt"));
                    map.put("notReply_cnt", tempList1.get(0).get("notReply_cnt"));
                    map.put("delay_cnt", tempList1.get(0).get("delay_cnt"));
                    map.put("twoUp_cnt", tempList1.get(0).get("twoUp_cnt"));
                    map.put("satisfied", tempList1.get(0).get("satisfied"));
                    map.put("basicSatisfied", tempList1.get(0).get("basicSatisfied"));
                    map.put("notSatisfied", tempList1.get(0).get("notSatisfied"));
                    long total = Integer.parseInt(tempList1.get(0).get("satisfied")+"")+Integer.parseInt(tempList1.get(0).get("basicSatisfied")+"")+Integer.parseInt(tempList1.get(0).get("notSatisfied")+"");
                    //判断分母是否等于0
                    if (total > 0) {
                        temp = (double)(Integer.parseInt(tempList1.get(0).get("satisfied")+""))/total*100;
                        BigDecimal bg = new BigDecimal(temp);
                        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        map.put("satisfyRate", f1+"%");
                        temp = (double)(Integer.parseInt(tempList1.get(0).get("basicSatisfied")+""))/total*100;
                        BigDecimal bg2 = new BigDecimal(temp);
                        double f2 = bg2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        map.put("basicSRate", f2+"%");
                        temp = (double)(Integer.parseInt(tempList1.get(0).get("notSatisfied")+""))/total*100;
                        BigDecimal bg3 = new BigDecimal(temp);
                        double f3 = bg3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        map.put("notSRate", f3+"%");
                    } else {
                        map.put("satisfyRate", 0.0+"%");
                        map.put("basicSRate", 0.0+"%");
                        map.put("notSRate", 0.0+"%");
                    }
                    newList.add(map);
                }
            }
            if ("2".equals(type)) {
                tempList2 = this.dao.getDeptChengbanWfList(type, start_date, end_date, vals.get(i).getDept_id()+"");
                if (tempList2.size() > 0) {
                    map.put("cnt", tempList2.get(0).get("cnt"));
                    map.put("should_cnt", tempList2.get(0).get("should_cnt"));
                    map.put("fact_cnt", tempList2.get(0).get("fact_cnt"));
                    map.put("ontime_cnt", tempList2.get(0).get("ontime_cnt"));
                    map.put("overtime_cnt", tempList2.get(0).get("overtime_cnt"));
                    map.put("notReply_cnt", tempList2.get(0).get("notReply_cnt"));
                    map.put("delay_cnt", tempList2.get(0).get("delay_cnt"));
                    map.put("twoUp_cnt", tempList2.get(0).get("twoUp_cnt"));
                    map.put("satisfied", tempList2.get(0).get("satisfied"));
                    map.put("basicSatisfied", tempList2.get(0).get("basicSatisfied"));
                    map.put("notSatisfied", tempList2.get(0).get("notSatisfied"));
                    long total = Integer.parseInt(tempList2.get(0).get("satisfied")+"")+Integer.parseInt(tempList2.get(0).get("basicSatisfied")+"")+Integer.parseInt(tempList2.get(0).get("notSatisfied")+"");
                    //判断分母是否等于0
                    if (total > 0) {
                        temp = (double)(Integer.parseInt(tempList2.get(0).get("satisfied")+""))/total*100;
                        BigDecimal bg = new BigDecimal(temp);
                        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        map.put("satisfyRate", f1+"%");
                        temp = (double)(Integer.parseInt(tempList2.get(0).get("basicSatisfied")+""))/total*100;
                        BigDecimal bg2 = new BigDecimal(temp);
                        double f2 = bg2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        map.put("basicSRate", f2+"%");
                        temp = (double)(Integer.parseInt(tempList2.get(0).get("notSatisfied")+""))/total*100;
                        BigDecimal bg3 = new BigDecimal(temp);
                        double f3 = bg3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        map.put("notSRate", f3+"%");
                    } else {
                        map.put("satisfyRate", 0.0+"%");
                        map.put("basicSRate", 0.0+"%");
                        map.put("notSRate", 0.0+"%");
                    }
                    newList.add(map);
                }
            }
            if ("3".equals(type)) {
                tempList1 = this.dao.getDeptChengbanList(type, start_date, end_date, vals.get(i).getDept_id()+"");
                tempList2 = this.dao.getDeptChengbanWfList(type, start_date, end_date, vals.get(i).getDept_id()+"");
                if (tempList1.size() > 0 && tempList2.size() == 0) {//如果寿光事件有记录潍坊事件无记录
                    map.put("cnt", Integer.parseInt(tempList1.get(0).get("cnt")+""));
                    map.put("should_cnt", Integer.parseInt(tempList1.get(0).get("should_cnt")+""));
                    map.put("fact_cnt", Integer.parseInt(tempList1.get(0).get("fact_cnt")+""));
                    map.put("overtime_cnt", Integer.parseInt(tempList1.get(0).get("overtime_cnt")+""));
                    map.put("notReply_cnt", Integer.parseInt(tempList1.get(0).get("notReply_cnt")+""));
                    map.put("delay_cnt", Integer.parseInt(tempList1.get(0).get("delay_cnt")+""));
                    map.put("twoUp_cnt", Integer.parseInt(tempList1.get(0).get("twoUp_cnt")+""));
                    map.put("satisfied", Integer.parseInt(tempList1.get(0).get("satisfied")+""));
                    map.put("basicSatisfied", Integer.parseInt(tempList1.get(0).get("basicSatisfied")+""));
                    map.put("notSatisfied", Integer.parseInt(tempList1.get(0).get("notSatisfied")+""));
                    long total = Integer.parseInt(tempList1.get(0).get("satisfied")+"")+Integer.parseInt(tempList1.get(0).get("basicSatisfied")+"")+Integer.parseInt(tempList1.get(0).get("notSatisfied")+"");
                    //判断分母是否等于0
                    if (total > 0) {
                        temp = (double)(Integer.parseInt(tempList1.get(0).get("satisfied")+""))/total*100;
                        BigDecimal bg = new BigDecimal(temp);
                        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        map.put("satisfyRate", f1+"%");
                        temp = (double)(Integer.parseInt(tempList1.get(0).get("basicSatisfied")+""))/total*100;
                        BigDecimal bg2 = new BigDecimal(temp);
                        double f2 = bg2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        map.put("basicSRate", f2+"%");
                        temp = (double)(Integer.parseInt(tempList1.get(0).get("notSatisfied")+""))/total*100;
                        BigDecimal bg3 = new BigDecimal(temp);
                        double f3 = bg3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        map.put("notSRate", f3+"%");
                    } else {
                        map.put("satisfyRate", 0.0+"%");
                        map.put("basicSRate", 0.0+"%");
                        map.put("notSRate", 0.0+"%");
                    }
                    newList.add(map);
                }
                if (tempList1.size() == 0 && tempList2.size()  > 0) {//如果寿光事件无记录潍坊事件有记录
                    map.put("cnt", Integer.parseInt(tempList2.get(0).get("cnt")+""));
                    map.put("should_cnt", Integer.parseInt(tempList2.get(0).get("should_cnt")+""));
                    map.put("fact_cnt", Integer.parseInt(tempList2.get(0).get("fact_cnt")+""));
                    map.put("overtime_cnt", Integer.parseInt(tempList2.get(0).get("overtime_cnt")+""));
                    map.put("notReply_cnt", Integer.parseInt(tempList2.get(0).get("notReply_cnt")+""));
                    map.put("delay_cnt", Integer.parseInt(tempList2.get(0).get("delay_cnt")+""));
                    map.put("twoUp_cnt", Integer.parseInt(tempList2.get(0).get("twoUp_cnt")+""));
                    map.put("satisfied", Integer.parseInt(tempList2.get(0).get("satisfied")+""));
                    map.put("basicSatisfied", Integer.parseInt(tempList2.get(0).get("basicSatisfied")+""));
                    map.put("notSatisfied", Integer.parseInt(tempList2.get(0).get("notSatisfied")+""));
                    long total = Integer.parseInt(tempList2.get(0).get("satisfied")+"")+Integer.parseInt(tempList2.get(0).get("basicSatisfied")+"")+Integer.parseInt(tempList2.get(0).get("notSatisfied")+"");
                    //判断分母是否等于0
                    if (total > 0) {
                        temp = (double)(Integer.parseInt(tempList2.get(0).get("satisfied")+""))/total*100;
                        BigDecimal bg = new BigDecimal(temp);
                        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        map.put("satisfyRate", f1+"%");
                        temp = (double)(Integer.parseInt(tempList2.get(0).get("basicSatisfied")+""))/total*100;
                        BigDecimal bg2 = new BigDecimal(temp);
                        double f2 = bg2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        map.put("basicSRate", f2+"%");
                        temp = (double)(Integer.parseInt(tempList2.get(0).get("notSatisfied")+""))/total*100;
                        BigDecimal bg3 = new BigDecimal(temp);
                        double f3 = bg3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        map.put("notSRate", f3+"%");
                    } else {
                        map.put("satisfyRate", 0.0+"%");
                        map.put("basicSRate", 0.0+"%");
                        map.put("notSRate", 0.0+"%");
                    }
                    newList.add(map);
                }
                if (tempList1.size() > 0 && tempList2.size()  > 0) {//如果寿光事件有记录潍坊事件有记录，两者加起来
                    map.put("cnt", Integer.parseInt(tempList1.get(0).get("cnt")+"")+Integer.parseInt(tempList2.get(0).get("cnt")+""));
                    map.put("should_cnt", Integer.parseInt(tempList1.get(0).get("should_cnt")+"")+Integer.parseInt(tempList2.get(0).get("should_cnt")+""));
                    map.put("fact_cnt", Integer.parseInt(tempList1.get(0).get("fact_cnt")+"")+Integer.parseInt(tempList2.get(0).get("fact_cnt")+""));
                    map.put("overtime_cnt", Integer.parseInt(tempList1.get(0).get("overtime_cnt")+"")+Integer.parseInt(tempList2.get(0).get("overtime_cnt")+""));
                    map.put("notReply_cnt", Integer.parseInt(tempList1.get(0).get("notReply_cnt")+"")+Integer.parseInt(tempList2.get(0).get("notReply_cnt")+""));
                    map.put("delay_cnt", Integer.parseInt(tempList1.get(0).get("delay_cnt")+"")+Integer.parseInt(tempList2.get(0).get("delay_cnt")+""));
                    map.put("twoUp_cnt", Integer.parseInt(tempList1.get(0).get("twoUp_cnt")+"")+Integer.parseInt(tempList2.get(0).get("twoUp_cnt")+""));
                    map.put("satisfied", Integer.parseInt(tempList1.get(0).get("satisfied")+"")+Integer.parseInt(tempList2.get(0).get("satisfied")+""));
                    map.put("basicSatisfied", Integer.parseInt(tempList1.get(0).get("basicSatisfied")+"")+Integer.parseInt(tempList2.get(0).get("basicSatisfied")+""));
                    map.put("notSatisfied", Integer.parseInt(tempList1.get(0).get("notSatisfied")+"")+Integer.parseInt(tempList2.get(0).get("notSatisfied")+""));
                    long total = Integer.parseInt(tempList1.get(0).get("satisfied")+"")+Integer.parseInt(tempList2.get(0).get("satisfied")+"")+Integer.parseInt(tempList1.get(0).get("basicSatisfied")+"")+Integer.parseInt(tempList2.get(0).get("basicSatisfied")+"")+Integer.parseInt(tempList1.get(0).get("notSatisfied")+"")+Integer.parseInt(tempList2.get(0).get("notSatisfied")+"");
                    //判断分母是否等于0
                    if (total > 0) {
                        temp = (double)(Integer.parseInt(tempList1.get(0).get("satisfied")+"")+Integer.parseInt(tempList2.get(0).get("satisfied")+""))/total*100;
                        BigDecimal bg = new BigDecimal(temp);
                        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        map.put("satisfyRate", f1+"%");
                        temp = (double)(Integer.parseInt(tempList1.get(0).get("basicSatisfied")+"")+Integer.parseInt(tempList2.get(0).get("basicSatisfied")+""))/total*100;
                        BigDecimal bg2 = new BigDecimal(temp);
                        double f2 = bg2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        map.put("basicSRate", f2+"%");
                        temp = (double)(Integer.parseInt(tempList1.get(0).get("notSatisfied")+"")+Integer.parseInt(tempList2.get(0).get("notSatisfied")+""))/total*100;
                        BigDecimal bg3 = new BigDecimal(temp);
                        double f3 = bg3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        map.put("notSRate", f3+"%");
                    } else {
                        map.put("satisfyRate", 0.0+"%");
                        map.put("basicSRate", 0.0+"%");
                        map.put("notSRate", 0.0+"%");
                    }
                    newList.add(map);
                }
            }
            //添加排序
            Collections.sort(newList, new Comparator<Map<String, Object>>() {  

                @Override
                public int compare(Map<String, Object> o1,
                        Map<String, Object> o2) {
                    int i = Integer.parseInt(o1.get("cnt")+"") - Integer.parseInt(o2.get("cnt")+"");  
                    return -i;
                }  
            });  
        }
        return newList;
    }
    
    @Override
    public List<Map<String,Object>> getDeptChengbanList_new(String type, String start_date, String end_date, String dept_id, String isshizhi){//isFirst是否第一次查询,(迭代时用到，第一次查询时，查询其父节点下的所有成员，办好自己）
        if (type == null || "".equals(type)) {
            type = "2";
        }
        List<Map<String,Object>> newList = new ArrayList<Map<String,Object>>();
        //获取所有部门承办情况
        List<Map<String, Object>> tempList2 = this.dao.getDeptChengbanWfList(type, start_date, end_date, "");
        for (int i = 0; i < tempList2.size(); i++) {
            Map<String,Object> map = new HashMap<String,Object>();  
            if (tempList2.get(i).get("dept_id") != null) {
                map.put("dept_id", tempList2.get(i).get("dept_id"));
                map.put("dept_name", tempList2.get(i).get("dept_name"));
                double temp = 0;
                map.put("cnt", tempList2.get(i).get("cnt"));
                map.put("should_cnt", tempList2.get(i).get("should_cnt"));
                map.put("fact_cnt", tempList2.get(i).get("fact_cnt"));
                map.put("ontime_cnt", tempList2.get(i).get("ontime_cnt"));
                map.put("overtime_cnt", tempList2.get(i).get("overtime_cnt"));
                map.put("notReply_cnt", tempList2.get(i).get("notReply_cnt"));
                map.put("delay_cnt", tempList2.get(i).get("delay_cnt"));
                map.put("twoUp_cnt", tempList2.get(i).get("twoUp_cnt"));
                map.put("satisfied", tempList2.get(i).get("satisfied"));
                map.put("basicSatisfied", tempList2.get(i).get("basicSatisfied"));
                map.put("notSatisfied", tempList2.get(i).get("notSatisfied"));
                long total = Integer.parseInt(tempList2.get(i).get("satisfied")+"")+Integer.parseInt(tempList2.get(i).get("basicSatisfied")+"")+Integer.parseInt(tempList2.get(i).get("notSatisfied")+"");
                //判断分母是否等于0
                if (total > 0) {
                    temp = (double)(Integer.parseInt(tempList2.get(i).get("satisfied")+""))/total*100;
                    BigDecimal bg = new BigDecimal(temp);
                    double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    map.put("satisfyRate", f1+"%");
                    temp = (double)(Integer.parseInt(tempList2.get(i).get("basicSatisfied")+""))/total*100;
                    BigDecimal bg2 = new BigDecimal(temp);
                    double f2 = bg2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    map.put("basicSRate", f2+"%");
                    temp = (double)(Integer.parseInt(tempList2.get(i).get("notSatisfied")+""))/total*100;
                    BigDecimal bg3 = new BigDecimal(temp);
                    double f3 = bg3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    map.put("notSRate", f3+"%");
                } else {
                    map.put("satisfyRate", 0.0+"%");
                    map.put("basicSRate", 0.0+"%");
                    map.put("notSRate", 0.0+"%");
                }
                newList.add(map);
            }
            
            //添加排序
            Collections.sort(newList, new Comparator<Map<String, Object>>() {  

                @Override
                public int compare(Map<String, Object> o1,
                        Map<String, Object> o2) {
                    int i = Integer.parseInt(o1.get("cnt")+"") - Integer.parseInt(o2.get("cnt")+"");  
                    return -i;
                }  
            });  
        }
        return newList;
    }
    
    @Override
    public Object getPostsDataInfo(String type, String start_date, String end_date, String dept_id) {
        if (type == null || "".equals(type)) {
            type = "2";
        }
        Object[][] barData = new Object[7][2];
        //List<Map<String, Object>> eList = this.dao.getDeptChengbanList(type, start_date, end_date, dept_id);
        List<Map<String, Object>> eWfList = this.dao.getDeptChengbanWfList(type, start_date, end_date, dept_id);
//        if ("1".equals(type)) {
//            if (eList.size() > 0) {
//                barData[0][0] = "转发数量";
//                barData[0][1] = eList.get(0).get("cnt");
//                barData[1][0] = "应完成数量";
//                barData[1][1] = eList.get(0).get("should_cnt");
//                barData[2][0] = "实际完成数量";
//                barData[2][1] = eList.get(0).get("fact_cnt");
//                barData[3][0] = "超时数量";
//                barData[3][1] = eList.get(0).get("overtime_cnt");
//                barData[4][0] = "未反馈数量";
//                barData[4][1] = eList.get(0).get("notReply_cnt");
//                barData[5][0] = "延时数量";
//                barData[5][1] = eList.get(0).get("delay_cnt");
//                barData[6][0] = "两次及以上转发数量";
//                barData[6][1] = eList.get(0).get("twoUp_cnt");
//            }
//        }
        if ("2".equals(type)) {
            if (eWfList.size() > 0) {
                barData[0][0] = "转发数量";
                barData[0][1] = eWfList.get(0).get("cnt");
                barData[1][0] = "应完成数量";
                barData[1][1] = eWfList.get(0).get("should_cnt");
                barData[2][0] = "实际完成数量";
                barData[2][1] = eWfList.get(0).get("fact_cnt");
                barData[3][0] = "超时数量";
                barData[3][1] = eWfList.get(0).get("overtime_cnt");
                barData[4][0] = "未反馈数量";
                barData[4][1] = eWfList.get(0).get("notReply_cnt");
                barData[5][0] = "延时数量";
                barData[5][1] = eWfList.get(0).get("delay_cnt");
                barData[6][0] = "两次及以上转发数量";
                barData[6][1] = eWfList.get(0).get("twoUp_cnt");
            }
        }
//        if ("3".equals(type) || type == null || "".equals(type)) {
//            if (eList.size() > 0 && eWfList.size() > 0) {
//                barData[0][0] = "转发数量";
//                barData[0][1] = Integer.parseInt(eList.get(0).get("cnt")+"")+Integer.parseInt(eWfList.get(0).get("cnt")+"");
//                barData[1][0] = "应完成数量";
//                barData[1][1] = Integer.parseInt(eList.get(0).get("should_cnt")+"")+Integer.parseInt(eWfList.get(0).get("should_cnt")+"");
//                barData[2][0] = "实际完成数量";
//                barData[2][1] = Integer.parseInt(eList.get(0).get("fact_cnt")+"")+Integer.parseInt(eWfList.get(0).get("fact_cnt")+"");
//                barData[3][0] = "超时数量";
//                barData[3][1] = Integer.parseInt(eList.get(0).get("overtime_cnt")+"")+Integer.parseInt(eWfList.get(0).get("overtime_cnt")+"");
//                barData[4][0] = "未反馈数量";
//                barData[4][1] = Integer.parseInt(eList.get(0).get("notReply_cnt")+"")+Integer.parseInt(eWfList.get(0).get("notReply_cnt")+"");
//                barData[5][0] = "延时数量";
//                barData[5][1] = Integer.parseInt(eList.get(0).get("delay_cnt")+"")+Integer.parseInt(eWfList.get(0).get("delay_cnt")+"");
//                barData[6][0] = "两次及以上转发数量";
//                barData[6][1] = Integer.parseInt(eList.get(0).get("twoUp_cnt")+"")+Integer.parseInt(eWfList.get(0).get("twoUp_cnt")+"");
//            }
//            if (eList.size() > 0 && eWfList.size() == 0) {
//                barData[0][0] = "转发数量";
//                barData[0][1] = Integer.parseInt(eList.get(0).get("cnt")+"");
//                barData[1][0] = "应完成数量";
//                barData[1][1] = Integer.parseInt(eList.get(0).get("should_cnt")+"");
//                barData[2][0] = "实际完成数量";
//                barData[2][1] = Integer.parseInt(eList.get(0).get("fact_cnt")+"");
//                barData[3][0] = "超时数量";
//                barData[3][1] = Integer.parseInt(eList.get(0).get("overtime_cnt")+"");
//                barData[4][0] = "未反馈数量";
//                barData[4][1] = Integer.parseInt(eList.get(0).get("notReply_cnt")+"");
//                barData[5][0] = "延时数量";
//                barData[5][1] = Integer.parseInt(eList.get(0).get("delay_cnt")+"");
//                barData[6][0] = "两次及以上转发数量";
//                barData[6][1] = Integer.parseInt(eList.get(0).get("twoUp_cnt")+"");
//            }
//            if (eList.size() == 0 && eWfList.size() > 0) {
//                barData[0][0] = "转发数量";
//                barData[0][1] = Integer.parseInt(eWfList.get(0).get("cnt")+"");
//                barData[1][0] = "应完成数量";
//                barData[1][1] = Integer.parseInt(eWfList.get(0).get("should_cnt")+"");
//                barData[2][0] = "实际完成数量";
//                barData[2][1] = Integer.parseInt(eWfList.get(0).get("fact_cnt")+"");
//                barData[3][0] = "超时数量";
//                barData[3][1] = Integer.parseInt(eWfList.get(0).get("overtime_cnt")+"");
//                barData[4][0] = "未反馈数量";
//                barData[4][1] = Integer.parseInt(eWfList.get(0).get("notReply_cnt")+"");
//                barData[5][0] = "延时数量";
//                barData[5][1] = Integer.parseInt(eWfList.get(0).get("delay_cnt")+"");
//                barData[6][0] = "两次及以上转发数量";
//                barData[6][1] = Integer.parseInt(eWfList.get(0).get("twoUp_cnt")+"");
//            }
//        }
        HashMap<String, Object> map=new HashMap<String, Object>();              
        map.put("barData",barData);
        return map;
    }

    @Override
    public String exportExcel(String type, String start_date, String end_date, String isshizhi, HttpServletResponse response) {
        String fileName = System.currentTimeMillis() + ".xls";
        String path = getClass().getClassLoader().getResource("/").getPath()
                + "../../upload/" + fileName;
        FileUtils.delAllFile(path);// 删除以前的excel
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        String title = "各部门承办情况统计分析";
        String rowName[] = { "序号", "部门名称", "转发数量", "应完成数量", "实际完成数量", "超时数量", "延时数量", "两次及以上转发数量", "满意", "基本满意", "不满意", "满意率", "基本满意率", "不满意率" };
        List<Map<String, Object>> infoList = getDeptChengbanList_new(type, start_date, end_date, "", isshizhi);
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作簿对象
            // XSSFWorkbook workbook = new XSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(title); // 创建工作表
            // XSSFSheet sheet = workbook.createSheet(title);
            // 产生表格标题行
            HSSFRow rowm = sheet.createRow(0);
            // XSSFRow rowm = sheet.createRow(0);
            HSSFCell cellTiltle = rowm.createCell(0);
            // XSSFCell cellTiltle = rowm.createCell(0);
            // sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面 - 可扩展】
            HSSFCellStyle columnTopStyle = FileUtils.getColumnTopStyle(workbook);// 获取列头样式对象
            // XSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);
            HSSFCellStyle style = FileUtils.getStyle(workbook); // 单元格样式对象
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            HSSFCellStyle style2 = FileUtils.getStyle(workbook);
            style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            // XSSFCellStyle style = this.getStyle(workbook);
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0,
                    (rowName.length - 1)));
            cellTiltle.setCellStyle(columnTopStyle);
            cellTiltle.setCellValue(title);

            // 定义所需列数
            int columnNum = rowName.length;
            HSSFRow rowRowName = sheet.createRow(2); // 在索引2的位置创建行(最顶端的行开始的第二行)
            // XSSFRow rowRowName = sheet.createRow(2);
            // 将列头设置到sheet的单元格中
            for (int n = 0; n < columnNum; n++) {
                HSSFCell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
                // XSSFCell cellRowName = rowRowName.createCell(n);
                cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
                HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
                // XSSFRichTextString text = new XSSFRichTextString(rowName[n]);
                cellRowName.setCellValue(text); // 设置列头单元格的值
                cellRowName.setCellStyle(columnTopStyle); // 设置列头单元格样式
            }

            // 将查询出的数据设置到sheet对应的单元格中
            for (int i = 0; i < infoList.size(); i++) {

                // Map<String, Object> obj = infoList.get(i);//遍历每个对象
                HSSFRow row = sheet.createRow(i + 3);// 创建所需的行数
                // XSSFRow row = sheet.createRow(i+3);
                for (int j = 0; j < columnNum; j++) {
                    HSSFCell cell = null; // 设置单元格的数据类型
                    // XSSFCell cell = null;
                    if (j == 0) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(i + 1);
                    } else if (j == 1) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("dept_name"))
                                && infoList.get(i).get("dept_name") != null) {
                            cell.setCellValue(infoList.get(i).get("dept_name").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 2) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("cnt"))
                                && infoList.get(i).get("cnt") != null) {
                            cell.setCellValue(infoList.get(i).get("cnt").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 3) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("should_cnt"))
                                && infoList.get(i).get("should_cnt") != null) {
                            cell.setCellValue(infoList.get(i).get("should_cnt").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 4) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("fact_cnt"))
                                && infoList.get(i).get("fact_cnt") != null) {
                            cell.setCellValue(infoList.get(i).get("fact_cnt").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 5) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("overtime_cnt"))
                                && infoList.get(i).get("overtime_cnt") != null) {
                            cell.setCellValue(infoList.get(i).get("overtime_cnt").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    }/* else if (j == 6) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("notReply_cnt"))
                                && infoList.get(i).get("notReply_cnt") != null) {
                            cell.setCellValue(infoList.get(i).get("notReply_cnt").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    }*/ else if (j == 6) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("delay_cnt"))
                                && infoList.get(i).get("delay_cnt") != null) {
                            cell.setCellValue(infoList.get(i).get("delay_cnt").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 7) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("twoUp_cnt"))
                                && infoList.get(i).get("twoUp_cnt") != null) {
                            cell.setCellValue(infoList.get(i).get("twoUp_cnt").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 8) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("satisfied"))
                                && infoList.get(i).get("satisfied") != null) {
                            cell.setCellValue(infoList.get(i).get("satisfied").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 9) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("basicSatisfied"))
                                && infoList.get(i).get("basicSatisfied") != null) {
                            cell.setCellValue(infoList.get(i).get("basicSatisfied").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 10) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("notSatisfied"))
                                && infoList.get(i).get("notSatisfied") != null) {
                            cell.setCellValue(infoList.get(i).get("notSatisfied").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 11) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("satisfyRate"))
                                && infoList.get(i).get("satisfyRate") != null) {
                            cell.setCellValue(infoList.get(i).get("satisfyRate").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 12) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("basicSRate"))
                                && infoList.get(i).get("basicSRate") != null) {
                            cell.setCellValue(infoList.get(i).get("basicSRate").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 13) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("notSRate"))
                                && infoList.get(i).get("notSRate") != null) {
                            cell.setCellValue(infoList.get(i).get("notSRate").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    }
                }
            }
            // 让列宽随着导出的列长自动适应
            for (int colNum = 0; colNum < columnNum; colNum++) {
                // sheet.autoSizeColumn(colNum);
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                    HSSFRow currentRow;
                    // XSSFRow currentRow;
                    // 当前行未被使用过
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }
                    if (currentRow.getCell(colNum) != null) {
                        HSSFCell currentCell = currentRow.getCell(colNum);
                        // XSSFCell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                            int length = currentCell.getStringCellValue()
                                    .getBytes().length;
                            if (columnWidth < length) {
                                columnWidth = length;
                            }
                        }
                    }
                }
                if (colNum == 0) {
                    if ((columnWidth - 2) >= 255) {
                        sheet.setColumnWidth(colNum, 255);
                    } else {
                        sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
                    }
                    //sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
                } else {
                    sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
                }
            }

            if (workbook != null) {
                try {
                    OutputStream fOut = new FileOutputStream(file);
                    workbook.write(fOut);
                    fOut.flush();
                    fOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }
    
    @Override
    public String export_satisfyDu(String dept_id, String start_date, String end_date, String satisfy_status_dept, HttpServletResponse response) {
        String fileName = System.currentTimeMillis() + ".xls";
        String path = getClass().getClassLoader().getResource("/").getPath()
                + "../../upload/" + fileName;
        FileUtils.delAllFile(path);// 删除以前的excel
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        List<Map<String, Object>> infoList = null;
        String title = "";
        if ("2".equals(satisfy_status_dept)) {
            title = "基本满意事件统计分析";
            
        }
        if ("3".equals(satisfy_status_dept)) {
            title = "不满意事件统计分析";
        }
        infoList = dao.getSatisfyDuWfList(0, 1000000, dept_id, satisfy_status_dept, start_date, end_date);
        String rowName[] = { "序号", "回复部门", "编号", "来电内容", "时间", "内容分类", "来电性质" };
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作簿对象
            // XSSFWorkbook workbook = new XSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(title); // 创建工作表
            // XSSFSheet sheet = workbook.createSheet(title);
            // 产生表格标题行
            HSSFRow rowm = sheet.createRow(0);
            // XSSFRow rowm = sheet.createRow(0);
            HSSFCell cellTiltle = rowm.createCell(0);
            // XSSFCell cellTiltle = rowm.createCell(0);
            // sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面 - 可扩展】
            HSSFCellStyle columnTopStyle = FileUtils.getColumnTopStyle(workbook);// 获取列头样式对象
            // XSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);
            HSSFCellStyle style = FileUtils.getStyle(workbook); // 单元格样式对象
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            HSSFCellStyle style2 = FileUtils.getStyle(workbook);
            style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            // XSSFCellStyle style = this.getStyle(workbook);
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0,
                    (rowName.length - 1)));
            cellTiltle.setCellStyle(columnTopStyle);
            cellTiltle.setCellValue(title);

            // 定义所需列数
            int columnNum = rowName.length;
            HSSFRow rowRowName = sheet.createRow(2); // 在索引2的位置创建行(最顶端的行开始的第二行)
            // XSSFRow rowRowName = sheet.createRow(2);
            // 将列头设置到sheet的单元格中
            for (int n = 0; n < columnNum; n++) {
                HSSFCell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
                // XSSFCell cellRowName = rowRowName.createCell(n);
                cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
                HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
                // XSSFRichTextString text = new XSSFRichTextString(rowName[n]);
                cellRowName.setCellValue(text); // 设置列头单元格的值
                cellRowName.setCellStyle(columnTopStyle); // 设置列头单元格样式
            }

            // 将查询出的数据设置到sheet对应的单元格中
            for (int i = 0; i < infoList.size(); i++) {

                // Map<String, Object> obj = infoList.get(i);//遍历每个对象
                HSSFRow row = sheet.createRow(i + 3);// 创建所需的行数
                // XSSFRow row = sheet.createRow(i+3);
                for (int j = 0; j < columnNum; j++) {
                    HSSFCell cell = null; // 设置单元格的数据类型
                    // XSSFCell cell = null;
                    if (j == 0) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(i + 1);
                    } else if (j == 1) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("dept_name"))
                                && infoList.get(i).get("dept_name") != null) {
                            cell.setCellValue(infoList.get(i).get("dept_name").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 2) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("event_no"))
                                && infoList.get(i).get("event_no") != null) {
                            cell.setCellValue(infoList.get(i).get("event_no").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 3) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("event_content"))
                                && infoList.get(i).get("event_content") != null) {
                            cell.setCellValue(infoList.get(i).get("event_content").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 4) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("event_inputtime"))
                                && infoList.get(i).get("event_inputtime") != null) {
                            cell.setCellValue(infoList.get(i).get("event_inputtime").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 5) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("content_type"))
                                && infoList.get(i).get("content_type") != null) {
                            cell.setCellValue(infoList.get(i).get("content_type").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 6) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("nature"))
                                && infoList.get(i).get("nature") != null) {
                            cell.setCellValue(infoList.get(i).get("nature").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    }
                }
            }
            // 让列宽随着导出的列长自动适应
            for (int colNum = 0; colNum < columnNum; colNum++) {
                // sheet.autoSizeColumn(colNum);
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                    HSSFRow currentRow;
                    // XSSFRow currentRow;
                    // 当前行未被使用过
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }
                    if (currentRow.getCell(colNum) != null) {
                        HSSFCell currentCell = currentRow.getCell(colNum);
                        // XSSFCell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                            int length = currentCell.getStringCellValue()
                                    .getBytes().length;
                            if (columnWidth < length) {
                                columnWidth = length;
                            }
                        }
                    }
                }
                if (colNum == 0) {
                    if ((columnWidth-2) >= 255) {
                        sheet.setColumnWidth(colNum, 255);
                    } else {
                        sheet.setColumnWidth(colNum, (columnWidth-2) * 256);
                    }
                    sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
                } else if (colNum == 3) { // 如果是来电内容列，为防止超出255报错，设置固定宽度
                    if ((columnWidth+4) >= 255) {
                        sheet.setColumnWidth(colNum, 255 * 256);
                    } else {
                        sheet.setColumnWidth(colNum, (columnWidth+4) * 256);
                    }
                } else {
                    sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
                }
            }

            if (workbook != null) {
                try {
                    OutputStream fOut = new FileOutputStream(file);
                    workbook.write(fOut);
                    fOut.flush();
                    fOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    @Override
    public List<Map<String, Object>> getEventInfoList(int offset, int page, String dept_id, String type, String start_date, String end_date) {
//        List<Map<String, Object>> eList = erDao.getEventReportList(offset, page, "", dept_id, "", "", true, "", "", "", "", "", "");
//        if ("1".equals(type)) {
//            return eList;
//        }
        List<Map<String, Object>> eWfList = dao.getEventReportWfList(offset, page, dept_id, start_date, end_date);
        if ("2".equals(type)) {
            return eWfList;
        }
        //eList.addAll(eWfList);
        return eWfList;
    }

    @Override
    public long getEventInfoList(String dept_id, String type, String start_date, String end_date) {
//        long cnt = erDao.getEventReportListCount("", dept_id, "", "", true, "", "", "", "", "", "");
//        if ("1".equals(type)) {
//            return cnt;
//        }
        long cntWf = dao.getEventReportWfListCount(dept_id, start_date, end_date);
        if ("2".equals(type)) {
            return cntWf;
        }
        //return cnt+cntWf;
        return cntWf;
    }

    @Override
    public List<Map<String, Object>> getFactFinishWfList(int offset,
            int pagesize, String dept_id, String type, String start_date,
            String end_date) {
        return dao.getFactFinishWfList(offset, pagesize, dept_id, start_date, end_date);
    }

    @Override
    public long getFactFinishWfList(String dept_id, String type,
            String start_date, String end_date) {
        return dao.getFactFinishWfListCount(dept_id, start_date, end_date);
    }

    @Override
    public List<Map<String, Object>> getDelayWfList(int offset, int pagesize,
            String dept_id, String type, String start_date, String end_date) {
        return dao.getDelayWfList(offset, pagesize, dept_id, start_date, end_date);
    }

    @Override
    public long getDelayWfList(String dept_id, String type, String start_date,
            String end_date) {
        return dao.getDelayWfListCount(dept_id, start_date, end_date);
    }

    @Override
    public List<Map<String, Object>> getTwoUpWfList(int offset, int pagesize,
            String dept_id, String type, String start_date, String end_date) {
        return dao.getTwoUpWfList(offset, pagesize, dept_id, start_date, end_date);
    }

    @Override
    public long getTwoUpWfList(String dept_id, String type, String start_date,
            String end_date) {
        return dao.getTwoUpWfListCount(dept_id, start_date, end_date);
    }
    
    @Override
    public List<Map<String, Object>> getOvertimeNotReplyWfList(int offset, int pagesize,
            String dept_id, String type, String start_date, String end_date) {
        return dao.getOvertimeNotReplyWfList(offset, pagesize, dept_id, start_date, end_date);
    }

    @Override
    public long getOvertimeNotReplyWfList(String dept_id, String type, String start_date,
            String end_date) {
        return dao.getOvertimeNotReplyWfListCount(dept_id, start_date, end_date);
    }

    @Override
    public List<Map<String, Object>> getSatisfyDuWfList(int offset,
            int pagesize, String dept_id, String satisfy_status_dept,
            String start_date, String end_date) {
        return dao.getSatisfyDuWfList(offset, pagesize, dept_id, satisfy_status_dept, start_date, end_date);
    }

    @Override
    public long getSatisfyDuWfList(String dept_id, String satisfy_status_dept,
            String start_date, String end_date) {
        return dao.getSatisfyDuWfListCount(dept_id, satisfy_status_dept, start_date, end_date);
    }
}
