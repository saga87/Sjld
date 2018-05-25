package com.wkrj.newsManage.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import wkrjsystem.user.bean.WkrjUser;

import com.wkrj.newsManage.bean.WkrjNewsFile;
import com.wkrj.newsManage.bean.WkrjNewsManage;
import com.wkrj.newsManage.bean.WkrjNewsManageOp;

public interface WkrjNewsManageService {
    
	/**
	 * 获取通知公告信息
	 * @param 
	 * @return
	 */
	public List<Map<String, Object>> getNewsList(int offset, int page, String user_id, String user_dept, String news_title, String news_inputtime, String end_date, boolean isGly,String isSchool);
	/**
	 * 获取通知公告信息数量
	 * @param user_id
	 * @return
	 */
	public long getNewsList(String user_id, String user_dept, String news_title, String news_inputtime, String end_date, boolean isGly,String isSchool);
	/**
	 * 添加通知公告信息
	 * @param newsManage
	 * @return
	 */
	public boolean addNews(WkrjNewsManage newsManage);
	
	/**
	 * 修改通知公告信息
	 * @param 
	 * @return
	 */
	public boolean updateNews(WkrjNewsManage newsManage,String yFileName);
	
	/**
	 * 删除通知公告信息
	 * @param id
	 * @return
	 */
	public boolean delNews(String id);
	
	/**
	 * 上传通知公告附件
	 * @param 
	 * @return
	 */
	public String uploadPic(MultipartFile newsManage_icons,HttpServletRequest request,String foledArrress);

	/**
	 * 添加通知公告附件信息
	 * @param newsManageFile
	 * @return
	 */
	public boolean addNewsFile(WkrjNewsFile newsManageFile);

	/**
	 * 获取所有用户列表
	 * @return
	 */
	public List<Map<String, Object>> getUserList();

	/**
	 * 根据用户ID获取用户信息
	 * @param userId
	 * @return
	 */
	public WkrjUser getUserNameById(String userId);

	/**
	 * 判断是否签收过
	 * @param 
	 * @return
	 */
	public boolean isSignOrNot(String news_id, String user_id);

	/**
	 * 签收
	 * @param newsOp
	 * @return
	 */
	public boolean signNews(WkrjNewsManageOp newsOp);

	/**
	 * 查看
	 * @param newsOp
	 * @return
	 */
	public boolean checkNews(WkrjNewsManageOp newsOp);
	
	/*
	 * 是否查看
	 */
	public boolean ifCheckNews(String op_lookuser,String news_id);
	/**
	 * 获取通知公告操作记录信息
	 * @param 
	 * @return
	 */
	public List<Map<String, Object>> getNewsOpDetail(int offset, int pagesize, String news_title, String op_signtime, String op_looktime, String end_date, String end_looktime, boolean isGly, String user_id, String user_dept, String isSchool);

	/**
	 * 获取通知公告操作记录信息数量
	 * @param 
	 * @return
	 */
	public long getNewsOpDetail(String news_title,String op_signtime, String op_looktime,String end_date,String end_looktime, boolean isGly, String user_id, String user_dept, String isSchool);

	/**
	 * 获取附件信息
	 * @param 
	 * @return
	 */
	public List<Map<String, Object>> getnewsManageFileList(int offset,
			int pagesize, String news_id);

	/**
	 * 获取附件信息数量
	 * @param 
	 * @return
	 */
	public long getnewsManageFileList(String news_id);

	/**
	 * 删除单个附件
	 * @param 
	 * @return
	 */
	public boolean delnewsManageFile(String id, String xFileName);
	
	/**
	 * 获取通知公告的操作记录
	 * @param 
	 * @return
	 */
	public List<WkrjNewsManageOp> checkOpList(int offset, int pagesize,
			String news_id);
	
	/**
	 * 获取通知公告的操作记录数量
	 * @param 
	 * @return
	 */
	public long checkOpList(String news_id);
}
