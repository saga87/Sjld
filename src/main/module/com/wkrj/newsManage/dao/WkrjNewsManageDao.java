package com.wkrj.newsManage.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import wkrjsystem.user.bean.WkrjUser;

import com.wkrj.newsManage.bean.WkrjNewsFile;
import com.wkrj.newsManage.bean.WkrjNewsManage;
import com.wkrj.newsManage.bean.WkrjNewsManageOp;

public interface WkrjNewsManageDao {

	/**
	 * 通过id获取WkrjNewsManage
	 * @param id
	 * @return
	 */
	public WkrjNewsManage getWkrjNewsById(String id);
	/**
	 * 添加信息
	 * @param newsManage
	 * @return
	 */
	public boolean addNews(WkrjNewsManage newsManage);
	/**
	 * 修改信息
	 * @param newsManage
	 * @return
	 */
	public boolean updateNews(WkrjNewsManage newsManage);
	/**
	 * 删除信息
	 * @param id
	 * @return
	 */
	public boolean delNews(String news_id);
	/**
	 * 获取通知公告信息
	 * @param offset
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> getNewsList(@Param("offset")int offset, @Param("page")int page, @Param("user_id")String user_id, @Param("user_dept")String user_dept, @Param("news_title")String news_title, @Param("news_inputtime")String news_inputtime, @Param("end_date")String end_date, @Param("isGly")boolean isGly,@Param("isSchool")String isSchool);
	/**
	 * 获取通知公告信息数量
	 * @param user_id
	 * @return
	 */
	public long getNewsListCount(@Param("user_id")String user_id, @Param("user_dept")String user_dept, @Param("news_title")String news_title, @Param("news_inputtime")String news_inputtime, @Param("end_date")String end_date, @Param("isGly")boolean isGly,@Param("isSchool")String isSchool);
	/**
	 * 删除通知公告附件
	 * @param id
	 * @return
	 */
	public boolean delNewsFile(String id);
	/**
	 * 查询是否存在通知公告附件
	 * @param news_id
	 * @return
	 */
	public boolean checkeIsHaveFile(@Param("news_id")String news_id);
	/**
	 * 根据公告ID获取通知公告附件
	 * @param news_id
	 * @return
	 */
	public List<WkrjNewsFile> getNewsFile(@Param("news_id")String news_id);
	/**
	 * 添加通知公告附件信息
	 * @param newsManageFile
	 * @return
	 */
	public boolean addNewsFile(WkrjNewsFile newsManageFile);
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
	public WkrjUser getUserNameById(String userId);
	/**
	 * 判断是否签收过
	 * @param news_id
	 * @param userId
	 * @return
	 */
	public boolean isSignOrNot(@Param("news_id")String news_id, @Param("op_signuser")String user_id);
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
	/**
	 * 是否查看
	 * @param newsOp
	 * @return
	 */
	public boolean ifCheckNews(@Param("op_lookuser")String op_lookuser, @Param("news_id")String news_id);
	/**
	 * 查看所有通知公告的操作记录
	 * @param 
	 * @return
	 */
	public List<Map<String, Object>> getNewsOpDetail(@Param("offset")int offset, @Param("page")int page,
			@Param("news_title")String news_title, @Param("op_signtime")String op_signtime, 
			@Param("op_looktime")String op_looktime, @Param("end_date")String end_date, 
			@Param("end_looktime")String end_looktime, @Param("isGly")boolean isGly,
			@Param("user_id")String user_id, @Param("user_dept")String user_dept, @Param("isSchool")String isSchool);
	/**
	 * 查看所有通知公告的操作记录数量
	 * @return
	 */
	public long getNewsOpDetailCount(@Param("news_title")String news_title,
			@Param("op_signtime")String op_signtime,
		    @Param("op_looktime")String op_looktime,
		    @Param("end_date")String end_date, @Param("end_looktime")String end_looktime,
		    @Param("isGly")boolean isGly, @Param("user_id")String user_id,
		    @Param("user_dept")String user_dept, @Param("isSchool")String isSchool);
	/**
	 * 删除发布信息的附件（单个）
	 * @param file_id
	 * @return
	 */
	public boolean delFileByID(@Param("file_id")String file_id);
	/**
	 * 查询相关发布信息所有的附件列表
	 * @param publishInfoFile
	 * @return
	 */
	public List<Map<String, Object>> getnewsManageFileList(@Param("offset")int offset, @Param("page")int page, @Param("news_id")String news_id);
	/**
	 * 查询相关发布信息所有的附件列表数量
	 * @param notice_id
	 * @return
	 */
	public long getnewsManageFileListCount(String notice_id);
	/**
	 * 查询某条通知公告相关的操作记录
	 * @param news_id
	 * @return
	 */
	public List<WkrjNewsManageOp> checkOpList(@Param("offset")int offset, @Param("page")int page, @Param("news_id")String news_id);
	/**
	 * 查询某条通知公告相关的操作记录数量
	 * @param news_id
	 * @return
	 */
	public long checkOpListCnt(@Param("news_id")String news_id);
	
}
