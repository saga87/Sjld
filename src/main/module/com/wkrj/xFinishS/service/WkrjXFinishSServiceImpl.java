package com.wkrj.xFinishS.service;

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
import com.wkrj.xFinishS.dao.WkrjXFinishSDao;

import wkrjsystem.department.bean.WkrjDept;
import wkrjsystem.menu.dao.WkrjMenuDao;
import wkrjsystem.utils.FileUtils;

@Service("wkrjXFinishSService")
@Transactional
public class WkrjXFinishSServiceImpl implements WkrjXFinishSService {

    @Autowired
    private WkrjXFinishSDao dao;
    
    @Autowired
    private WkrjMenuDao menuDao;
    
    @Autowired
    private WkrjDeptShouliSDao dcdao;
    
    @Override
    public List<Map<String,Object>> getEventCntByBanjieTimes(String content_type, String start_date, String end_date, String isshizhi, final String sortname, final String sortorder){
        List<Map<String,Object>> newList = new ArrayList<Map<String,Object>>();  
        //获取所有部门
        List<WkrjDept> vals = this.dcdao.getDeptTree("-1", true, "", isshizhi);
        for (int i = 0; i < vals.size(); i++) {
            List<Map<String, Object>> onceList = this.dao.getOnceBanjieCnt(vals.get(i).getDept_id(), content_type, start_date, end_date);//一次办结
            List<Map<String, Object>> secondList = this.dao.getTimesBanjieCnt(vals.get(i).getDept_id(), content_type, start_date, end_date, "-2");//二次办结
            List<Map<String, Object>> thirdList = this.dao.getTimesBanjieCnt(vals.get(i).getDept_id(), content_type, start_date, end_date, "-3");//三次办结
            List<Map<String, Object>> fourthList = this.dao.getFourthBanjieCnt(vals.get(i).getDept_id(), content_type, start_date, end_date);//四次及以上办结
            long timescnt = this.dao.getTimesFinishEventCount(vals.get(i).getDept_id(), start_date, end_date);//多次办结
            //List<Map<String, Object>> totalList = this.dao.getTimesBanjieCnt(vals.get(i).getDept_id(), content_type, start_date, end_date, "");//所有办结的
            long shouldFinishWfCnt = this.dao.getShouldFinishWfCnt(start_date, end_date, vals.get(i).getDept_id()+"");//所有应办结的
            double temp = 0;
            //int total = Integer.parseInt(onceList.get(0).get("cnt")+"")+Integer.parseInt(timesList.get(0).get("cnt")+"");
            if (shouldFinishWfCnt > 0) {
            //if (Integer.parseInt(totalList.get(0).get("cnt")+"") > 0) {
                Map<String,Object> map = new HashMap<String,Object>();  
                map.put("oncecnt", onceList.get(0).get("cnt"));
                map.put("secondcnt", secondList.get(0).get("cnt"));
                map.put("thirdcnt", thirdList.get(0).get("cnt"));
                map.put("timescnt", timescnt);
                //int fourthAndUp = Integer.parseInt(totalList.get(0).get("cnt")+"")-Integer.parseInt(onceList.get(0).get("cnt")+"")-Integer.parseInt(secondList.get(0).get("cnt")+"")-Integer.parseInt(thirdList.get(0).get("cnt")+"");
                int fourthAndUp = fourthList.size();
                map.put("fourthcnt", fourthAndUp);//四次及以上办结
                //temp = (double)(Integer.parseInt(onceList.get(0).get("cnt")+""))/Integer.parseInt(totalList.get(0).get("cnt")+"")*100;
                temp = (double)(Integer.parseInt(onceList.get(0).get("cnt")+""))/shouldFinishWfCnt*100;
                BigDecimal bg = new BigDecimal(temp);
                double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                map.put("oncerate", f1+"%");
                //temp = (double)(Integer.parseInt(secondList.get(0).get("cnt")+""))/Integer.parseInt(totalList.get(0).get("cnt")+"")*100;
                temp = (double)(Integer.parseInt(secondList.get(0).get("cnt")+""))/shouldFinishWfCnt*100;
                BigDecimal bg2 = new BigDecimal(temp);
                double f2 = bg2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                map.put("secondrate", f2+"%");
                //temp = (double)(Integer.parseInt(thirdList.get(0).get("cnt")+""))/Integer.parseInt(totalList.get(0).get("cnt")+"")*100;
                temp = (double)(Integer.parseInt(thirdList.get(0).get("cnt")+""))/shouldFinishWfCnt*100;
                BigDecimal bg3 = new BigDecimal(temp);
                double f3 = bg3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                map.put("thirdrate", f3+"%");
                temp = (double)fourthAndUp/shouldFinishWfCnt*100;
                BigDecimal bg4 = new BigDecimal(temp);
                double f4 = bg4.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                map.put("fourthrate", f4+"%");
                //只显示应完成数量不为零的部门
                map.put("dept_id", vals.get(i).getDept_id());
                map.put("dept_name", vals.get(i).getDept_name());
                newList.add(map);
            } else {
//                map.put("oncerate", 0.0+"%");
//                map.put("secondrate", 0.0+"%");
//                map.put("thirdrate", 0.0+"%");
//                map.put("fourthrate", 0.0+"%");
            }
            
            //添加排序
            Collections.sort(newList, new Comparator<Map<String, Object>>() {  

                @Override
                public int compare(Map<String, Object> o1,
                        Map<String, Object> o2) {
                    int i = 0;
                    if ("oncecnt".equals(sortname) && "asc".equals(sortorder)) {//一次办结数量升序
                        i = Integer.parseInt(o1.get("oncecnt")+"") - Integer.parseInt(o2.get("oncecnt")+"");  
                        return i;
                    }
                    if ("oncecnt".equals(sortname) && "desc".equals(sortorder)) {//一次办结数量降序
                        i = Integer.parseInt(o1.get("oncecnt")+"") - Integer.parseInt(o2.get("oncecnt")+"");  
                        return -i;
                    }
                    if ("secondcnt".equals(sortname) && "asc".equals(sortorder)) {//二次办结数量升序
                        i = Integer.parseInt(o1.get("secondcnt")+"") - Integer.parseInt(o2.get("secondcnt")+"");  
                        return i;
                    }
                    if ("secondcnt".equals(sortname) && "desc".equals(sortorder)) {//二次办结数量降序
                        i = Integer.parseInt(o1.get("secondcnt")+"") - Integer.parseInt(o2.get("secondcnt")+"");  
                        return -i;
                    }
                    if ("thirdcnt".equals(sortname) && "asc".equals(sortorder)) {//三次办结数量升序
                        i = Integer.parseInt(o1.get("thirdcnt")+"") - Integer.parseInt(o2.get("thirdcnt")+"");  
                        return i;
                    }
                    if ("thirdcnt".equals(sortname) && "desc".equals(sortorder)) {//三次办结数量降序
                        i = Integer.parseInt(o1.get("thirdcnt")+"") - Integer.parseInt(o2.get("thirdcnt")+"");  
                        return -i;
                    }
                    if ("fourthcnt".equals(sortname) && "asc".equals(sortorder)) {//四次及以上办结数量升序
                        i = Integer.parseInt(o1.get("fourthcnt")+"") - Integer.parseInt(o2.get("fourthcnt")+"");  
                        return i;
                    }
                    if ("fourthcnt".equals(sortname) && "desc".equals(sortorder)) {//四次及以上办结数量降序
                        i = Integer.parseInt(o1.get("fourthcnt")+"") - Integer.parseInt(o2.get("fourthcnt")+"");  
                        return -i;
                    }
                    if ("oncerate".equals(sortname) && "asc".equals(sortorder)) {//一次办结率升序
                        i = (int) (Double.parseDouble(o1.get("oncerate").toString().replace("%", "")) - Double.parseDouble(o2.get("oncerate").toString().replace("%", "")));  
                        return i;
                    }
                    if ("oncerate".equals(sortname) && "desc".equals(sortorder)) {//一次办结率降序
                        i = (int) (Double.parseDouble(o1.get("oncerate").toString().replace("%", "")) - Double.parseDouble(o2.get("oncerate").toString().replace("%", "")));  
                        return -i;
                    }
                    if ("secondrate".equals(sortname) && "asc".equals(sortorder)) {//二次办结率升序
                        i = (int) (Double.parseDouble(o1.get("secondrate").toString().replace("%", "")) - Double.parseDouble(o2.get("secondrate").toString().replace("%", "")));  
                        return i;
                    }
                    if ("secondrate".equals(sortname) && "desc".equals(sortorder)) {//二次办结率降序
                        i = (int) (Double.parseDouble(o1.get("secondrate").toString().replace("%", "")) - Double.parseDouble(o2.get("secondrate").toString().replace("%", "")));  
                        return -i;
                    }
                    if ("thirdrate".equals(sortname) && "asc".equals(sortorder)) {//三次办结率升序
                        i = (int) (Double.parseDouble(o1.get("thirdrate").toString().replace("%", "")) - Double.parseDouble(o2.get("thirdrate").toString().replace("%", "")));  
                        return i;
                    }
                    if ("thirdrate".equals(sortname) && "desc".equals(sortorder)) {//三次办结率降序
                        i = (int) (Double.parseDouble(o1.get("thirdrate").toString().replace("%", "")) - Double.parseDouble(o2.get("thirdrate").toString().replace("%", "")));  
                        return -i;
                    }
                    if ("fourthrate".equals(sortname) && "asc".equals(sortorder)) {//四次及以上办结率升序
                        i = (int) (Double.parseDouble(o1.get("fourthrate").toString().replace("%", "")) - Double.parseDouble(o2.get("fourthrate").toString().replace("%", "")));  
                        return i;
                    }
                    if ("fourthrate".equals(sortname) && "desc".equals(sortorder)) {//四次及以上办结率降序
                        i = (int) (Double.parseDouble(o1.get("fourthrate").toString().replace("%", "")) - Double.parseDouble(o2.get("fourthrate").toString().replace("%", "")));  
                        return -i;
                    }
                    i = Integer.parseInt(o1.get("oncecnt")+"") - Integer.parseInt(o2.get("oncecnt")+"");  
                    return -i;
                }  
            });  
        }
        return newList;
    }
    
    @Override
    public List<Map<String,Object>> getEventCntByBanjieTimes_new(String content_type, String start_date, String end_date, String isshizhi, final String sortname, final String sortorder){
        List<Map<String,Object>> newList = new ArrayList<Map<String,Object>>();  
        //获取部门承办事件列表（根据承办量排序，降序）
        List<Map<String, Object>> tempList = this.dao.getDeptBanjieWfList("2", start_date, end_date, "");
        for (int i = 0; i < tempList.size(); i++) {
            double temp = 0;
            int shouldFinishWfCnt = Integer.parseInt(tempList.get(i).get("should_cnt")+"");
            if (shouldFinishWfCnt > 0) {
                Map<String,Object> map = new HashMap<String,Object>();  
                map.put("oncecnt", tempList.get(i).get("onceFinish"));
                map.put("secondcnt", tempList.get(i).get("secondFinish"));
                map.put("thirdcnt", tempList.get(i).get("thirdFinish"));
                map.put("timescnt", tempList.get(i).get("timesFinish"));
                int fourthAndUp = Integer.parseInt(tempList.get(i).get("fourthFinish")+"");
                map.put("fourthcnt", fourthAndUp);//四次及以上办结
                temp = (double)(Integer.parseInt(tempList.get(i).get("onceFinish")+""))/shouldFinishWfCnt*100;
                BigDecimal bg = new BigDecimal(temp);
                double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                map.put("oncerate", f1+"%");
                temp = (double)(Integer.parseInt(tempList.get(i).get("secondFinish")+""))/shouldFinishWfCnt*100;
                BigDecimal bg2 = new BigDecimal(temp);
                double f2 = bg2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                map.put("secondrate", f2+"%");
                temp = (double)(Integer.parseInt(tempList.get(i).get("thirdFinish")+""))/shouldFinishWfCnt*100;
                BigDecimal bg3 = new BigDecimal(temp);
                double f3 = bg3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                map.put("thirdrate", f3+"%");
                temp = (double)fourthAndUp/shouldFinishWfCnt*100;
                BigDecimal bg4 = new BigDecimal(temp);
                double f4 = bg4.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                map.put("fourthrate", f4+"%");
                //只显示应完成数量不为零的部门
                map.put("dept_id", tempList.get(i).get("dept_id"));
                map.put("dept_name", tempList.get(i).get("dept_name"));
                newList.add(map);
            }
            
            //添加排序
            Collections.sort(newList, new Comparator<Map<String, Object>>() {  

                @Override
                public int compare(Map<String, Object> o1,
                        Map<String, Object> o2) {
                    int i = 0;
                    if ("oncecnt".equals(sortname) && "asc".equals(sortorder)) {//一次办结数量升序
                        i = Integer.parseInt(o1.get("oncecnt")+"") - Integer.parseInt(o2.get("oncecnt")+"");  
                        return i;
                    }
                    if ("oncecnt".equals(sortname) && "desc".equals(sortorder)) {//一次办结数量降序
                        i = Integer.parseInt(o1.get("oncecnt")+"") - Integer.parseInt(o2.get("oncecnt")+"");  
                        return -i;
                    }
                    if ("secondcnt".equals(sortname) && "asc".equals(sortorder)) {//二次办结数量升序
                        i = Integer.parseInt(o1.get("secondcnt")+"") - Integer.parseInt(o2.get("secondcnt")+"");  
                        return i;
                    }
                    if ("secondcnt".equals(sortname) && "desc".equals(sortorder)) {//二次办结数量降序
                        i = Integer.parseInt(o1.get("secondcnt")+"") - Integer.parseInt(o2.get("secondcnt")+"");  
                        return -i;
                    }
                    if ("thirdcnt".equals(sortname) && "asc".equals(sortorder)) {//三次办结数量升序
                        i = Integer.parseInt(o1.get("thirdcnt")+"") - Integer.parseInt(o2.get("thirdcnt")+"");  
                        return i;
                    }
                    if ("thirdcnt".equals(sortname) && "desc".equals(sortorder)) {//三次办结数量降序
                        i = Integer.parseInt(o1.get("thirdcnt")+"") - Integer.parseInt(o2.get("thirdcnt")+"");  
                        return -i;
                    }
                    if ("fourthcnt".equals(sortname) && "asc".equals(sortorder)) {//四次及以上办结数量升序
                        i = Integer.parseInt(o1.get("fourthcnt")+"") - Integer.parseInt(o2.get("fourthcnt")+"");  
                        return i;
                    }
                    if ("fourthcnt".equals(sortname) && "desc".equals(sortorder)) {//四次及以上办结数量降序
                        i = Integer.parseInt(o1.get("fourthcnt")+"") - Integer.parseInt(o2.get("fourthcnt")+"");  
                        return -i;
                    }
                    if ("oncerate".equals(sortname) && "asc".equals(sortorder)) {//一次办结率升序
                        i = (int) (Double.parseDouble(o1.get("oncerate").toString().replace("%", "")) - Double.parseDouble(o2.get("oncerate").toString().replace("%", "")));  
                        return i;
                    }
                    if ("oncerate".equals(sortname) && "desc".equals(sortorder)) {//一次办结率降序
                        i = (int) (Double.parseDouble(o1.get("oncerate").toString().replace("%", "")) - Double.parseDouble(o2.get("oncerate").toString().replace("%", "")));  
                        return -i;
                    }
                    if ("secondrate".equals(sortname) && "asc".equals(sortorder)) {//二次办结率升序
                        i = (int) (Double.parseDouble(o1.get("secondrate").toString().replace("%", "")) - Double.parseDouble(o2.get("secondrate").toString().replace("%", "")));  
                        return i;
                    }
                    if ("secondrate".equals(sortname) && "desc".equals(sortorder)) {//二次办结率降序
                        i = (int) (Double.parseDouble(o1.get("secondrate").toString().replace("%", "")) - Double.parseDouble(o2.get("secondrate").toString().replace("%", "")));  
                        return -i;
                    }
                    if ("thirdrate".equals(sortname) && "asc".equals(sortorder)) {//三次办结率升序
                        i = (int) (Double.parseDouble(o1.get("thirdrate").toString().replace("%", "")) - Double.parseDouble(o2.get("thirdrate").toString().replace("%", "")));  
                        return i;
                    }
                    if ("thirdrate".equals(sortname) && "desc".equals(sortorder)) {//三次办结率降序
                        i = (int) (Double.parseDouble(o1.get("thirdrate").toString().replace("%", "")) - Double.parseDouble(o2.get("thirdrate").toString().replace("%", "")));  
                        return -i;
                    }
                    if ("fourthrate".equals(sortname) && "asc".equals(sortorder)) {//四次及以上办结率升序
                        i = (int) (Double.parseDouble(o1.get("fourthrate").toString().replace("%", "")) - Double.parseDouble(o2.get("fourthrate").toString().replace("%", "")));  
                        return i;
                    }
                    if ("fourthrate".equals(sortname) && "desc".equals(sortorder)) {//四次及以上办结率降序
                        i = (int) (Double.parseDouble(o1.get("fourthrate").toString().replace("%", "")) - Double.parseDouble(o2.get("fourthrate").toString().replace("%", "")));  
                        return -i;
                    }
                    i = Integer.parseInt(o1.get("oncecnt")+"") - Integer.parseInt(o2.get("oncecnt")+"");  
                    return -i;
                }  
            });  
        }
        return newList;
    }

    @Override
    public String exportExcel(String content_type, String start_date, String end_date, String isshizhi, HttpServletResponse response) {
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

        String title = "一次办结统计分析";
        String rowName[] = { "序号", "部门名称", "一次办结", "二次办结", "三次办结", "四次及以上办结", "多次办结汇总", "一次办结率", "二次办结率", "三次办结率" };
        List<Map<String, Object>> infoList = getEventCntByBanjieTimes_new(content_type, start_date, end_date, isshizhi, "", "");
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
                        if (!"".equals(infoList.get(i).get("oncecnt"))
                                && infoList.get(i).get("oncecnt") != null) {
                            cell.setCellValue(infoList.get(i).get("oncecnt").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 3) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("secondcnt"))
                                && infoList.get(i).get("secondcnt") != null) {
                            cell.setCellValue(infoList.get(i).get("secondcnt").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 4) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("thirdcnt"))
                                && infoList.get(i).get("thirdcnt") != null) {
                            cell.setCellValue(infoList.get(i).get("thirdcnt").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 5) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("fourthcnt"))
                                && infoList.get(i).get("fourthcnt") != null) {
                            cell.setCellValue(infoList.get(i).get("fourthcnt").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 6) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("timescnt"))
                                && infoList.get(i).get("timescnt") != null) {
                            cell.setCellValue(infoList.get(i).get("timescnt").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 7) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("oncerate"))
                                && infoList.get(i).get("oncerate") != null) {
                            cell.setCellValue(infoList.get(i).get("oncerate").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 8) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("secondrate"))
                                && infoList.get(i).get("secondrate") != null) {
                            cell.setCellValue(infoList.get(i).get("secondrate").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 9) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("thirdrate"))
                                && infoList.get(i).get("thirdrate") != null) {
                            cell.setCellValue(infoList.get(i).get("thirdrate").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 10) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("fourthrate"))
                                && infoList.get(i).get("fourthrate") != null) {
                            cell.setCellValue(infoList.get(i).get("fourthrate").toString()); // 设置单元格的值
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
    public String export_banjieEvent(String flag, String start_date, String end_date, String dept_id, HttpServletResponse response) {
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
        if ("1".equals(flag)) {
            title = "一次办结事件统计分析";
            infoList = getOnceBanjieEvent(0, 1000000, dept_id, "2", start_date, end_date);
        }
        if ("2".equals(flag)) {
            title = "二次办结事件统计分析";
            infoList = getTimesBanjieEvent(0, 1000000, dept_id, "-2", start_date, end_date);
        }
        if ("3".equals(flag)) {
            title = "三次办结事件统计分析";
            infoList = getTimesBanjieEvent(0, 1000000, dept_id, "-3", start_date, end_date);
        }
        if ("4".equals(flag)) {
            title = "四次及以上办结事件统计分析";
            infoList = getFourthBanjieEvent(0, 1000000, dept_id, start_date, end_date);
        }
        if ("5".equals(flag)) {
            title = "多次办结事件汇总";
            infoList = getTimesFinishEvent(0, 1000000, dept_id, start_date, end_date);
        }
        String rowName[] = { "序号", "事件编号", "来电内容", "时间", "内容分类", "来电性质" };
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
                        if (!"".equals(infoList.get(i).get("event_no"))
                                && infoList.get(i).get("event_no") != null) {
                            cell.setCellValue(infoList.get(i).get("event_no").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 2) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("event_content"))
                                && infoList.get(i).get("event_content") != null) {
                            cell.setCellValue(infoList.get(i).get("event_content").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 3) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("event_inputtime"))
                                && infoList.get(i).get("event_inputtime") != null) {
                            cell.setCellValue(infoList.get(i).get("event_inputtime").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 4) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("content_type"))
                                && infoList.get(i).get("content_type") != null) {
                            cell.setCellValue(infoList.get(i).get("content_type").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 5) {
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
                } else if (colNum == 2) { // 如果是来电内容列，为防止超出255报错，设置固定宽度
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
    public List<Map<String, Object>> getOnceBanjieEvent(int offset,
            int pagesize, String dept_id, String type, String start_date,
            String end_date) {
        return this.dao.getOnceBanjieEvent(offset, pagesize, dept_id, start_date, end_date);
    }

    @Override
    public long getOnceBanjieEvent(String dept_id, String type,
            String start_date, String end_date) {
        return this.dao.getOnceBanjieEventCount(dept_id, start_date, end_date);
    }

    @Override
    public List<Map<String, Object>> getTimesBanjieEvent(int offset,
            int pagesize, String dept_id, String houzhui, String start_date,
            String end_date) {
        return this.dao.getTimesBanjieEvent(offset, pagesize, dept_id, houzhui, start_date, end_date);
    }

    @Override
    public long getTimesBanjieEvent(String dept_id, String houzhui,
            String start_date, String end_date) {
        return this.dao.getTimesBanjieEventCount(dept_id, houzhui, start_date, end_date);
    }

    @Override
    public List<Map<String, Object>> getFourthBanjieEvent(int offset,
            int pagesize, String dept_id, String start_date, String end_date) {
        return this.dao.getFourthBanjieEvent(offset, pagesize, dept_id, start_date, end_date);
    }

    @Override
    public long getFourthBanjieEvent(String dept_id, String start_date,
            String end_date) {
        return this.dao.getFourthEventCount(dept_id, start_date, end_date);
    }

    @Override
    public List<Map<String, Object>> getTimesFinishEvent(int offset,
            int pagesize, String dept_id, String start_date, String end_date) {
        return this.dao.getTimesFinishEvent(offset, pagesize, dept_id, start_date, end_date);
    }

    @Override
    public long getTimesFinishEvent(String dept_id, String start_date,
            String end_date) {
        return this.dao.getTimesFinishEventCount(dept_id, start_date, end_date);
    }
}
