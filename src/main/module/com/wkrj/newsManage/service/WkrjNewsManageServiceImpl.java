package com.wkrj.newsManage.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import wkrjsystem.user.bean.WkrjUser;
import wkrjsystem.utils.Guid;

import com.wkrj.newsManage.bean.WkrjNewsFile;
import com.wkrj.newsManage.bean.WkrjNewsManage;
import com.wkrj.newsManage.bean.WkrjNewsManageOp;
import com.wkrj.newsManage.dao.WkrjNewsManageDao;

@Service("wkrjNewsManageService")
@Transactional
public class WkrjNewsManageServiceImpl implements WkrjNewsManageService {

	@Autowired
	private WkrjNewsManageDao dao;
	
	//路径
	private String realPath= getClass().getClassLoader().getResource("/").getPath()+ "../../upload/newsManage/";

	@Override
	public boolean addNews(WkrjNewsManage newsManage) {
		return dao.addNews(newsManage);
	}
	
	@Override
	public boolean addNewsFile(WkrjNewsFile newsFile) {
		String yfilenames = newsFile.getFile_yname();
		String xfilenames = newsFile.getFile_xname();
		boolean flag = true;
		//获取每个附件的原名称现名称（因为逗号分隔的所以解析一下）
		for (int i = 0, len = yfilenames.split(",").length; i < len; i++) {
			newsFile.setFile_yname(yfilenames.split(",")[i]);
			newsFile.setFile_xname(xfilenames.split(",")[i]);
			newsFile.setFile_id(Guid.getGuid());
			if (!dao.addNewsFile(newsFile)) {
				flag = false;
			}
		}
		
		return flag;
	}
	
	@Override
	public boolean updateNews(WkrjNewsManage newsManage,String yFileName) {
		return dao.updateNews(newsManage);
	}
	@Override
	public boolean delNews(String id) {

		if (dao.checkeIsHaveFile(id)) {
			if (this.delFile(id) && dao.delNewsFile(id) && dao.delNews(id)) {
				return true;
			}
		} else {
			if (dao.delNews(id)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 删除附件物理地址
	 * @param icon
	 * @return
	 */
	public boolean delFile(String id) {
		List<WkrjNewsFile> fileList = dao.getNewsFile(id);	
		for (int i = 0; i < fileList.size(); i++) {
			//删除本地附件
			String sPath = this.realPath+fileList.get(i).getFile_xname();
			File file = new File(sPath);  
			   // 路径为文件且不为空则进行删除  
			if (file.isFile() && file.exists()) {  
				file.delete();  
			}
		}
		
		return true;
	}
	
	public String uploadPic(MultipartFile newsManage_file,HttpServletRequest request,String foledArrress){

		if(null==foledArrress || "".equals(foledArrress)){
			foledArrress="upload/newsManage/";
		}
		
		String realPath = request.getSession().getServletContext().getRealPath("/")+foledArrress;
		
		String yFileName = newsManage_file.getOriginalFilename();//原文件名
		
		long timeMillis = System.currentTimeMillis();//获取时间戳 这种速度更快一些
		String extra = "wkrj";//由于样式不能是数字所以加上wkrj来区分
		
		String xFileName = extra+timeMillis+extra+yFileName.substring(yFileName.lastIndexOf("."), yFileName.length());//现文件名
		
		try {
			FileUtils.copyInputStreamToFile(newsManage_file.getInputStream(), new File(realPath,xFileName));
		} catch (Exception e) {
			System.err.println("上传附件出错了,位于WkrjNewsController.java中");
		}
		return "{\"success\":\"true\",\"filename\":\""+xFileName+"\",\"yFileName\":\""+yFileName+"\"}";
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
	public List<Map<String, Object>> getNewsList(int offset, int page, String user_id, String user_dept, String news_title, String news_inputtime, String end_date, boolean isGly,String isSchool) {
		return this.dao.getNewsList(offset, page, user_id, user_dept, news_title, news_inputtime, end_date, isGly, isSchool);
	}

	@Override
	public long getNewsList(String user_id, String user_dept, String news_title, String news_inputtime, String end_date, boolean isGly,String isSchool) {
		return this.dao.getNewsListCount(user_id, user_dept, news_title, news_inputtime, end_date, isGly, isSchool);
	}

	@Override
	public List<Map<String, Object>> getNewsOpDetail(int offset, int page, String news_title, String op_signtime, String op_looktime, String end_date, String end_looktime, boolean isGly, String user_id, String user_dept, String isSchool) {
		return this.dao.getNewsOpDetail(offset, page,news_title, op_signtime, op_looktime, end_date, end_looktime, isGly, user_id, user_dept, isSchool);
	}

	@Override
	public long getNewsOpDetail(String news_title, String op_signtime, String op_looktime, String end_date, String end_looktime, boolean isGly, String user_id, String user_dept, String isSchool) {
		return this.dao.getNewsOpDetailCount(news_title, op_signtime, op_looktime, end_date, end_looktime, isGly, user_id, user_dept, isSchool);
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
	public WkrjUser getUserNameById(String userId) {
		return this.dao.getUserNameById(userId);
	}

	@Override
	public boolean isSignOrNot(String news_id, String user_id) {
		return this.dao.isSignOrNot(news_id, user_id);
	}

	@Override
	public boolean signNews(WkrjNewsManageOp newsOp) {
		return this.dao.signNews(newsOp);
	}
	
	@Override
	public boolean checkNews(WkrjNewsManageOp newsOp) {
		return this.dao.checkNews(newsOp);
	}
	
	@Override
	public boolean ifCheckNews(String op_lookuser,String news_id) {
		return this.dao.ifCheckNews(op_lookuser,news_id);
	}
	
	@Override
	public List<Map<String, Object>> getnewsManageFileList(int offset,
			int pagesize, String news_id) {
		return this.dao.getnewsManageFileList(offset, pagesize, news_id);
	}

	@Override
	public long getnewsManageFileList(String news_id) {
		return this.dao.getnewsManageFileListCount(news_id);
	}

	@Override
	public boolean delnewsManageFile(String id, String xFileName) {
		if (dao.delFileByID(id) && this.delSingleFile(xFileName)) {
			return true;
		}
		return false;
	}
	
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
	public List<WkrjNewsManageOp> checkOpList(int offset, int pagesize,
			String news_id) {
		return this.dao.checkOpList(offset, pagesize, news_id);
	}

	@Override
	public long checkOpList(String news_id) {
		return this.dao.checkOpListCnt(news_id);
	}
}
