package com.wkrj.eventReportWf.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
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
import org.springframework.web.multipart.MultipartFile;

import com.wkrj.basic.dataDictionary.dao.WkrjDataDictionaryDao;
import com.wkrj.eventReportWf.bean.WkrjEventWf;
import com.wkrj.eventReportWf.bean.WkrjProcedureRecord;
import com.wkrj.eventReportWf.dao.WkrjEventWfDao;

import wkrjsystem.user.bean.WkrjUser;

@Service("wkrjEventWfService")
@Transactional
public class WkrjEventWfServiceImpl implements WkrjEventWfService {

    @Autowired
    private WkrjEventWfDao dao;
    
    @Autowired
    private WkrjDataDictionaryDao dddao;
    
    //路径
    private String realPath= getClass().getClassLoader().getResource("/").getPath()+ "../../upload/deptOpinion/";

    @Override
    public boolean addEventWf(WkrjEventWf er) {
        return dao.addEventWf(er);
    }
    
//    @Override
//    public boolean addDeptOpinionFileWf(WkrjDeptOpinionFileWf opinionFile) {
//        String yfilenames = opinionFile.getFile_yname();
//        String xfilenames = opinionFile.getFile_xname();
//        boolean flag = true;
//        //获取每个附件的原名称现名称（因为逗号分隔的所以解析一下）
//        for (int i = 0, len = yfilenames.split(",").length; i < len; i++) {
//            opinionFile.setFile_yname(yfilenames.split(",")[i]);
//            opinionFile.setFile_xname(xfilenames.split(",")[i]);
//            opinionFile.setFile_id(Guid.getGuid());
//            if (!dao.addDeptOpinionFileWf(opinionFile)) {
//                flag = false;
//            }
//        }
//        
//        return flag;
//    }
    
    /**
     * 删除附件物理地址
     * @param icon
     * @return
     */
//    public boolean delFile(String id) {
//        List<WkrjEventReportFile> fileList = dao.getEventReportFile(id);    
//        for (int i = 0; i < fileList.size(); i++) {
//            //删除本地附件
//            String sPath = this.realPath+fileList.get(i).getFile_xname();
//            File file = new File(sPath);  
//               // 路径为文件且不为空则进行删除  
//            if (file.isFile() && file.exists()) {  
//                file.delete();  
//            }
//        }
//        
//        return true;
//    }
    
    public String uploadPic(MultipartFile er_file,HttpServletRequest request,String foledArrress){

        if(null==foledArrress || "".equals(foledArrress)){
            foledArrress="upload/fuheReason/";
        }
        
        String realPath = request.getSession().getServletContext().getRealPath("/")+foledArrress;
        
        String yFileName = er_file.getOriginalFilename();//原文件名
        
        long timeMillis = System.currentTimeMillis();//获取时间戳 这种速度更快一些
        //String extra = "wkrj";//由于样式不能是数字所以加上wkrj来区分
        String houzhui = yFileName.substring(yFileName.lastIndexOf("."));//文件后缀
        String xFileName = timeMillis+yFileName.substring(yFileName.lastIndexOf("."), yFileName.length());//现文件名
        
        try {
            FileUtils.copyInputStreamToFile(er_file.getInputStream(), new File(realPath,xFileName));
            if(houzhui.equals(".doc") || houzhui.equals(".docx")){
                new wkrjsystem.utils.ConvertWordToPdfByAspose().word2pdf(realPath+"\\"+xFileName, realPath+"\\"+timeMillis);
                xFileName = timeMillis+".pdf";
            }else if(houzhui.equals(".xls") || houzhui.equals(".xlsx")){
                new wkrjsystem.utils.ConvertWordToPdfByAspose().excel2pdf(realPath+"\\"+xFileName, realPath+"\\"+timeMillis);
                xFileName = timeMillis+".pdf";
            }else{
                //pdfName = xfileName;
            }
        } catch (Exception e) {
            System.err.println("上传附件出错了,位于WkrjEventReportController.java中");
        }
        if (".jpg".equals(houzhui) || ".png".equals(houzhui) || ".jpeg".equals(houzhui)) {
            houzhui = "1";//1代表图片
        } else {
            houzhui = "0";//0代表非图片
        }
        return "{\"success\":\"true\",\"filename\":\""+xFileName+"\",\"yFileName\":\""+yFileName+"\",\"fileType\":\""+houzhui+"\"}";
    } 
    
    public void writeContentToFile(String file, String conent) {     
        BufferedWriter out = null;     
        try {     
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));     
            out.write(conent);     
        } catch (Exception e) {     
            e.printStackTrace();     
        } finally {     
            try {     
                if(out != null){  
                    out.close();     
                }  
            } catch (IOException e) {     
                e.printStackTrace();     
            }     
        }     
    }

    @Override
    public List<Map<String, Object>> getEventReportWfList(int offset, int page, String user_id, String user_dept, String caller_username, String caller_tel, boolean isGly, String event_status,
            String qianshou_status, String cuiban_status, String overtime_status, String dateF, String shenhe_status, String event_no, String event_content) {
        return this.dao.getEventReportWfList(offset, page, user_id, user_dept, caller_username, caller_tel, isGly, event_status, qianshou_status, cuiban_status, overtime_status, dateF, 
                shenhe_status, event_no, event_content);
    }

    @Override
    public long getEventReportWfList(String user_id, String user_dept, String caller_username, String caller_tel, boolean isGly, String event_status, String qianshou_status, String cuiban_status,
            String overtime_status, String dateF, String shenhe_status, String event_no, String event_content) {
        return this.dao.getEventReportWfListCount(user_id, user_dept, caller_username, caller_tel, isGly, event_status, qianshou_status, cuiban_status, overtime_status, dateF, shenhe_status,
                event_no, event_content);
    }


    @Override
    public List<Map<String, Object>> getUserList() {
        //return this.dao.getUserList();
        List<Map<String,Object>> all = new ArrayList<Map<String,Object>>();//记录所有的module
        List<WkrjUser> users = this.dao.getUserList();
        for(WkrjUser user : users){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("id", user.getUser_id());
            map.put("text", user.getUser_realname());
            all.add(map);
        }
        return all;
    }


    @Override
    public boolean isSignOrNot(String event_id, String user_dept) {
        return this.dao.isSignOrNot(event_id, user_dept);
    }

    @Override
    public boolean signEventWf(WkrjEventWf er) {
        return this.dao.signEventWf(er);
    }
    
//    @Override
//    public boolean checkEventReport(WkrjEventReportOp messageOp) {
//        return this.dao.checkEventReport(messageOp);
//    }

    /**
     * 删除附件物理地址(单个)
     * @param icon
     * @return
     */
    public boolean delSingleFile(String xFileName) {
        //删除本地附件
        String sPath = this.realPath+xFileName;
        File file = new File(sPath);
           // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
        }
        
        return true;
    }


    @Override
    public List<Map<String, Object>> getDataDictionary(String parentID) {
        //return this.dddao.getChildrenDictionary(parentID);
        List<Map<String,Object>> all = new ArrayList<Map<String,Object>>();//记录所有的module
        List<Map<String, Object>> vals = this.dddao.getChildrenDictionary(parentID);
        if ("6c3d5d3b-17c4-42ac-b465-370b1ff11b2e".equals(parentID)) {//如果查询内容分类
            for(Map<String, Object> dd : vals){
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("id", dd.get("id"));
                map.put("hex", dd.get("id"));
                map.put("label", dd.get("typename"));
                all.add(map);
            }
        } else {
            for(Map<String, Object> dd : vals){
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("id", dd.get("id"));
                map.put("name", dd.get("typename"));
                all.add(map);
            }
        }
        return all;
    }

//    @Override
//    public boolean shenheEvent(WkrjEventWf er) {
//        return this.dao.shenheEventWf(er);
//    }
//    
//    @Override
//    public boolean cuibanEvent(WkrjEventWf er) {
//        return this.dao.cuibanEventWf(er);
//    }
    
    @Override
    public boolean updateEventStatus(WkrjEventWf er) {
        return this.dao.updateEventStatus(er);
    }



    @Override
    public boolean delayEventWf(WkrjProcedureRecord delay) {
        return this.dao.delayEventWf(delay);
    }

    @Override
    public List<Map<String, Object>> getRepliedEventWfList(int offset,
            int pagesize, String user_id, String user_dept,
            String caller_username, String caller_tel, boolean isGly,
            String event_status, String qianshou_status, String string) {
        return this.dao.getRepliedEventWfList(offset, pagesize, user_id, event_status, qianshou_status);
    }

    @Override
    public long getRepliedEventWfList(String user_id, String user_dept,
            String caller_username, String caller_tel, boolean isGly,
            String event_status, String qianshou_status, String string) {
        return this.dao.getRepliedEventWfListCount(user_id, event_status, qianshou_status);
    }

    @Override
    public boolean replyEventWf(WkrjProcedureRecord opinion) {
        return this.dao.replyEventWf(opinion);
    }

    @Override
    public List<Map<String, Object>> getWfNotReplyList(int offset,
            int pagesize, String user_id, String user_dept,
            String caller_username, String caller_tel, boolean isGly,
            String event_status, String qianshou_status, String cuiban_status,
            String overtime_status, String dateF) {
        return this.dao.getWfNotReplyList(offset, pagesize, user_id, user_dept, qianshou_status, dateF);
    }

    @Override
    public long getWfNotReplyList(String user_id, String user_dept,
            String caller_username, String caller_tel, boolean isGly,
            String event_status, String qianshou_status, String cuiban_status,
            String overtime_status, String dateF) {
        return this.dao.getWfNotReplyListCount(user_id, user_dept, qianshou_status, dateF);
    }

    @Override
    public boolean setRepliedStatus(String event_id, String qianshou_status) {
        return this.dao.setRepliedStatus(event_id, qianshou_status);
    }

    @Override
    public List<Map<String, Object>> getDelayedEventWfList(int offset,
            int pagesize, String user_id, String caller_username,
            String caller_tel, boolean isGly) {
        return this.dao.getDelayedEventWfList(offset, pagesize, user_id);
    }

    @Override
    public long getDelayedEventWfList(String user_id, String caller_username,
            String caller_tel, boolean isGly) {
        return this.dao.getDelayedEventWfListCount(user_id);
    }

//    @Override
//    public boolean finishEventWf(WkrjEventWf er) {
//        return this.dao.finishEventWf(er);
//    }
//
//    @Override
//    public boolean huifangFinishEventWf(WkrjEventWf er) {
//        return this.dao.huifangFinishEventWf(er);
//    }


    @Override
    public List<Map<String, Object>> searchEventWfList(int offset, int pagesize,
            String event_status, String qianshou_status, String event_no, String caller_nature, String content_type, String start_date, String end_date, String event_content, String caller_tel) {
        return this.dao.searchEventWfList(offset, pagesize, event_status, qianshou_status, event_no, caller_nature, content_type, start_date, end_date, event_content, caller_tel);
    }

    @Override
    public long searchEventWfList(String event_status, String qianshou_status, String event_no, String caller_nature, String content_type, String start_date, String end_date, 
            String event_content, String caller_tel) {
        return this.dao.searchEventWfListCount(event_status, qianshou_status, event_no, caller_nature, content_type, start_date, end_date, event_content, caller_tel);
    }

    @Override
    public List<Map<String, Object>> getReplyInfoWfList(int offset, int pagesize, String event_id, String dept_name, String accept_workno, String input_time, String event_no, 
            String qianshou_status, String qianshou_time, String event_status, String huifang_time, String replied_dealno, String delay_time, String current_loginno, String opt_time) {
        List<Map<String, Object>> newList = new ArrayList<Map<String,Object>>();
        Map<String, Object> temMap = new HashMap<String, Object>();
        temMap.put("opt_result", "部门未接收");
        temMap.put("accept_workno", "呼叫中心("+accept_workno+")");
        temMap.put("dept_name", dept_name);
        temMap.put("opt_time", input_time);
        temMap.put("event_no", event_no);
        newList.add(temMap);
        
        if ("undefined".equals(opt_time)) {//针对以前的数据（没加该字段之前）
            opt_time = "";
        }
        
        if (!"0".equals(qianshou_status)) {
            temMap = new HashMap<String, Object>();
            temMap.put("opt_result", "部门接收");
            temMap.put("accept_workno", "呼叫中心("+accept_workno+")");
            temMap.put("dept_name", dept_name);
            temMap.put("opt_time", qianshou_time);
            temMap.put("event_no", event_no);
            newList.add(temMap);
            List<Map<String, Object>> replyList = this.dao.getReplyInfoWfList(offset, pagesize, event_id);
            if (replyList.size() > 0) {
                temMap = new HashMap<String, Object>();
                temMap.put("opt_result", "已回复");
                temMap.put("accept_workno", "呼叫中心("+accept_workno+")");
                temMap.put("dept_name", dept_name);
                temMap.put("opt_time", replyList.get(0).get("replytime"));
                temMap.put("event_no", event_no);
                newList.add(temMap);
            }
            if ("回访完成".equals(event_status)) {
                temMap = new HashMap<String, Object>();
                temMap.put("opt_result", "已回访");
                temMap.put("accept_workno", "呼叫中心("+replied_dealno+")");
                temMap.put("dept_name", dept_name);
                temMap.put("opt_time", huifang_time);
                temMap.put("event_no", event_no);
                newList.add(temMap);
            }
            if ("延时".equals(event_status)) {
                temMap = new HashMap<String, Object>();
                temMap.put("opt_result", "已延时");
                temMap.put("accept_workno", "呼叫中心("+accept_workno+")");
                temMap.put("dept_name", dept_name);
                temMap.put("opt_time", delay_time);
                temMap.put("event_no", event_no);
                newList.add(temMap);
            }
            if ("完成".equals(event_status)) {
                temMap = new HashMap<String, Object>();
                temMap.put("opt_result", "已完成");
                temMap.put("accept_workno", "呼叫中心("+replied_dealno+")");
                temMap.put("dept_name", dept_name);
                temMap.put("opt_time", opt_time);
                temMap.put("event_no", event_no);
                newList.add(temMap);
            }
        }
        if ("撤销".equals(event_status)) {
            temMap = new HashMap<String, Object>();
            temMap.put("opt_result", "已撤销");
            temMap.put("accept_workno", "呼叫中心("+current_loginno+")");
            temMap.put("dept_name", dept_name);
            temMap.put("opt_time", opt_time);
            temMap.put("event_no", event_no);
            newList.add(temMap);
        }
        return newList;
        //return this.dao.getReplyInfoWfList(offset, pagesize, event_id);
    }

    @Override
    public long getReplyInfoWfList(String event_id) {
        return this.dao.getReplyInfoWfListCount(event_id);
    }

    @Override
    public List<Map<String, Object>> getDelayedEventWfList_S(int offset,
            int pagesize, String event_no, String caller_nature,
            String content_type, String start_date, String end_date, String caller_tel) {
        return this.dao.getDelayedEventWfList_S(offset, pagesize, event_no, caller_nature, content_type, start_date, end_date, caller_tel);
    }

    @Override
    public long getDelayedEventWfList_S(String event_no, String caller_nature,
            String content_type, String start_date, String end_date, String caller_tel) {
        return this.dao.getDelayedEventWfListCount_S(event_no, caller_nature, content_type, start_date, end_date, caller_tel);
    }
    
    public String exportDubanWf(String start_date, String end_date, HttpServletResponse response, boolean isGly, String user_dept) {

        String fileName = System.currentTimeMillis() + ".xls";
        String path = getClass().getClassLoader().getResource("/").getPath()
                + "../../upload/" + fileName;
        wkrjsystem.utils.FileUtils.delAllFile(path);// 删除以前的excel
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        String title = "督办事件统计分析";
        String rowName[] = { "序号", "编号", "内容", "姓名", "来电号码", "处理时限", "来电性质", "发布时间", "状态", "处理状态" };
        List<Map<String, Object>> infoList = dao.exportDubanWfList(start_date, end_date, isGly, user_dept);
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
            HSSFCellStyle columnTopStyle = wkrjsystem.utils.FileUtils.getColumnTopStyle(workbook);// 获取列头样式对象
            // XSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);
            HSSFCellStyle style = wkrjsystem.utils.FileUtils.getStyle(workbook); // 单元格样式对象
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            HSSFCellStyle style2 = wkrjsystem.utils.FileUtils.getStyle(workbook);
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
                        if (!"".equals(infoList.get(i).get("caller_username"))
                                && infoList.get(i).get("caller_username") != null) {
                            cell.setCellValue(infoList.get(i).get("caller_username").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 4) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("caller_tel"))
                                && infoList.get(i).get("caller_tel") != null) {
                            cell.setCellValue(infoList.get(i).get("caller_tel").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 5) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("deal_days"))
                                && infoList.get(i).get("deal_days") != null) {
                            cell.setCellValue(infoList.get(i).get("deal_days").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 6) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("caller_nature"))
                                && infoList.get(i).get("caller_nature") != null) {
//                            String temp = "";
//                            if ("1".equals(infoList.get(i).get("is_expire"))) {
//                                temp = "是";
//                            } else {
//                                temp = "否";
//                            }
                            cell.setCellValue(infoList.get(i).get("caller_nature").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 7) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("event_inputtime"))
                                && infoList.get(i).get("event_inputtime") != null) {
                            cell.setCellValue(infoList.get(i).get("event_inputtime").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 8) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("event_status"))
                                && infoList.get(i).get("event_status") != null) {
                            cell.setCellValue(infoList.get(i).get("event_status").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    } else if (j == 9) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(infoList.get(i).get("deal_type"))
                                && infoList.get(i).get("deal_type") != null) {
                            cell.setCellValue(infoList.get(i).get("deal_type").toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    }
                    if (j == 2) {// 如果巡查内容列则设置左对齐
                        cell.setCellStyle(style2); // 设置单元格样式
                    } else {
                        cell.setCellStyle(style); // 设置单元格样式
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
                    // if ((columnWidth-2) >= 255) {
                    // sheet.setColumnWidth(colNum, 255);
                    // } else {
                    // sheet.setColumnWidth(colNum, (columnWidth-2) * 256);
                    // }
                    sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
                } else if (colNum == 4) { // 如果是巡查内容列，为防止超出255报错，设置固定宽度
                    sheet.setColumnWidth(colNum, (32 + 4) * 256);
                    // if ((columnWidth+4) >= 255) {
                    // sheet.setColumnWidth(colNum, 255 * 256);
                    // } else {
                    // sheet.setColumnWidth(colNum, (columnWidth+4) * 256);
                    // }
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
    public boolean updateZhuanban(WkrjEventWf er) {
        return this.dao.updateZhuanban(er);
    }


    @Override
    public boolean updateSatisfyDu(WkrjEventWf erWf) {
        return this.dao.updateSatisfyDu(erWf);
    }


//    @Override
//    public List<Map<String, Object>> getFinishedEventWfList(int offset,
//            int pagesize, String user_id, String chengban_dept, boolean isGly,
//            String event_status, String start_date, String end_date,
//            String satisfy_status_dept, String event_no, String event_content) {
//        return this.dao.getFinishedEventWfList(offset, pagesize, user_id, chengban_dept, isGly, event_status, start_date, end_date, satisfy_status_dept, event_no, event_content);
//    }
//
//    @Override
//    public long getFinishedEventWfList(String user_id, String chengban_dept, boolean isGly,
//            String event_status, String start_date, String end_date,
//            String satisfy_status_dept, String event_no, String event_content) {
//        return this.dao.getFinishedEventWfListCount(user_id, chengban_dept, isGly, event_status, start_date, end_date, satisfy_status_dept, event_no, event_content);
//    }

//    @Override
//    public List<Map<String, Object>> getWfFinishedEventList(int offset,
//            int pagesize, String user_dept, String event_status,
//            String event_no, String caller_nature, String content_type) {
//        return this.dao.getWfFinishedEventList(offset, pagesize, user_dept, event_status, event_no, caller_nature, content_type);
//    }
//
//    @Override
//    public long getWfFinishedEventList(String user_dept, String event_status,
//            String event_no, String caller_nature, String content_type) {
//        return this.dao.getWfFinishedEventListCount(user_dept, event_status, event_no, caller_nature, content_type);
//    }

    @Override
    public List<Map<String, Object>> getAllEventWfList() {
        return this.dao.getAllEventWfList();
    }

    @Override
    public long getEventWfCntByNo(String event_no) {
        return this.dao.getEventWfCntByNo(event_no);
    }

    @Override
    public WkrjEventWf getEventInfoById(String event_id) {
        return this.dao.getEventInfoById(event_id);
    }

//    @Override
//    public List<Map<String, Object>> getReplyFileInfoWf(String event_id) {
//        return this.dao.getReplyFileInfoWf(event_id);
//    }


    @Override
    public void updateShenheStatus(WkrjEventWf er) {
        this.dao.updateShenheStatus(er);
    }

    @Override
    public boolean updateEventWf(WkrjEventWf ev) {
        return this.dao.updateEventWf(ev);
    }

    @Override
    public boolean delEventWf(String event_id) {
        return this.dao.delEventWf(event_id);
    }

}
