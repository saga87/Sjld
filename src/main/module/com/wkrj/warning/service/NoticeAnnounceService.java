package com.wkrj.warning.service;

import java.util.List;
import java.util.Map;









import javax.servlet.http.HttpServletRequest;





import org.springframework.web.multipart.MultipartFile;

import com.wkrj.warning.bean.NoticeAnnounce;
import com.wkrj.warning.bean.NoticeAnnounceFile;

public interface NoticeAnnounceService {
	List<Map<String,Object>> getNoticeAnnounceList(int offset,int rows,String na_title,
			String na_inputtime,String end_date);
	boolean addNoticeAnnounce(NoticeAnnounce noticeAnnounce
			,NoticeAnnounceFile file);
	boolean updateNoticeAnnounce(NoticeAnnounce noticeAnnounce
			,NoticeAnnounceFile file);
	boolean deleteNoticeAnnounce(HttpServletRequest request,String na_id);
	
	List<Map<String,Object>> getNoticeAnnounceFileList(String file_id,String na_id);
	boolean deleteNoticeAnnounceFile(HttpServletRequest request,String file_id,String na_id);
	
	public String uploadPic(MultipartFile newsManage_icons,HttpServletRequest request,String foledArrress);
	
	/**
	 * 删除单个附件
	 * @param 
	 * @return
	 */
	public boolean delSingleNaFile(String file_id, String xFileName);
	
}
