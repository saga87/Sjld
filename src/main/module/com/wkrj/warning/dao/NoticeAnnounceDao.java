package com.wkrj.warning.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wkrj.warning.bean.NoticeAnnounce;
import com.wkrj.warning.bean.NoticeAnnounceFile;

public interface NoticeAnnounceDao {
	/**通知公告增删改查**/
	List<Map<String,Object>> getNoticeAnnounceList(@Param("offset")int offset,@Param("rows")int rows,@Param("user_id")String user_id,@Param("user_dept")String user_dept,@Param("na_title")String na_title,
			@Param("na_inputtime")String na_inputtime,@Param("end_date")String end_date);
	boolean addNoticeAnnounce(NoticeAnnounce noticeAnnounce);
	boolean updateNoticeAnnounce(NoticeAnnounce noticeAnnounce);
	boolean deleteNoticeAnnounce(@Param("na_id")String na_id);
	
	/**通知公告附件增删改查**/
	List<Map<String,Object>> getNoticeAnnounceFileList(@Param("file_id")String file_id,@Param("na_id")String na_id);
	boolean addNoticeAnnounceFile(NoticeAnnounceFile file);
	boolean updateNoticeAnnounceFile(NoticeAnnounceFile file);
	boolean deleteNoticeAnnounceFile(@Param("file_id")String file_id,@Param("na_id")String na_id);
	
	public List<NoticeAnnounceFile> getNaFile(@Param("na_id")String na_id);
}
