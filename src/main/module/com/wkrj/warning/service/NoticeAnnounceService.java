package com.wkrj.warning.service;

import java.util.List;
import java.util.Map;






import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

import com.wkrj.warning.bean.NoticeAnnounce;
import com.wkrj.warning.bean.NoticeAnnounceFile;

public interface NoticeAnnounceService {
	List<Map<String,Object>> getNoticeAnnounceList(int offset,int rows,String na_title);
	boolean addNoticeAnnounce(NoticeAnnounce noticeAnnounce
			,NoticeAnnounceFile file);
	boolean updateNoticeAnnounce(NoticeAnnounce noticeAnnounce
			,NoticeAnnounceFile file);
	boolean deleteNoticeAnnounce(HttpServletRequest request,String na_id);
	
	List<Map<String,Object>> getNoticeAnnounceFileList(String file_id,String na_id);
	boolean deleteNoticeAnnounceFile(HttpServletRequest request,@Param("file_id")String file_id,@Param("na_id")String na_id);
	
	
}
