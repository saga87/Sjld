package com.wkrj.warning.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;







import wkrjsystem.utils.FileUtils;
import wkrjsystem.utils.Guid;
import wkrjsystem.utils.UtilsHelper;

import com.wkrj.warning.bean.NoticeAnnounce;
import com.wkrj.warning.bean.NoticeAnnounceFile;
import com.wkrj.warning.dao.NoticeAnnounceDao;

@Service("noticeAnnounceService")
@Transactional
public class NoticeAnnounceServiceImpl implements NoticeAnnounceService {
	
	@Autowired
	private NoticeAnnounceDao dao;

	@Override
	public List<Map<String, Object>> getNoticeAnnounceList(int offset, int rows,String na_title) {
		return dao.getNoticeAnnounceList(offset, rows,na_title);
	}

	@Override
	public boolean addNoticeAnnounce(NoticeAnnounce noticeAnnounce,
			NoticeAnnounceFile file) {
		try {
			String na_id = Guid.getGuid();
			noticeAnnounce.setNa_id(na_id);
			noticeAnnounce.setNa_inputtime(UtilsHelper.getDateFormatTime());
			String path = file.getFile_path();
			if (!"".equals(path) && path != null) {
				String [] names = file.getFile_name().split(",");
				String [] paths = file.getFile_path().split(",");
				for (int i = 0; i < paths.length; i++) {
					NoticeAnnounceFile file2 = new NoticeAnnounceFile();
					file2.setNa_id(na_id);
					file2.setFile_id(Guid.getGuid());
					file2.setFile_name(names[i]);
					file2.setFile_path(paths[i]);
					dao.addNoticeAnnounceFile(file2);
				}
			}
			dao.addNoticeAnnounce(noticeAnnounce);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean updateNoticeAnnounce(NoticeAnnounce noticeAnnounce,
			NoticeAnnounceFile file) {
		try {
			//先删除数据之前的附件信息重新添加
			dao.deleteNoticeAnnounceFile(null, noticeAnnounce.getNa_id());
			String path = file.getFile_path();
			if (!"".equals(path) && path != null) {
				String [] names = file.getFile_name().split(",");
				String [] paths = file.getFile_path().split(",");
				for (int i = 0; i < paths.length; i++) {
					NoticeAnnounceFile file2 = new NoticeAnnounceFile();
					file2.setNa_id(noticeAnnounce.getNa_id());
					file2.setFile_id(Guid.getGuid());
					file2.setFile_name(names[i]);
					file2.setFile_path(paths[i]);
					dao.addNoticeAnnounceFile(file2);
				}
			}
			dao.updateNoticeAnnounce(noticeAnnounce);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteNoticeAnnounce(HttpServletRequest request, String na_id) {
		try {
			String idesString [] = na_id.split(",");
			for (int i = 0; i < idesString.length; i++) {
				String idString = idesString[i];
				this.deleteNoticeAnnounceFile(request, null, idString);
				dao.deleteNoticeAnnounce(idString);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Map<String, Object>> getNoticeAnnounceFileList(String file_id,
			String na_id) {
		// TODO Auto-generated method stub
		return dao.getNoticeAnnounceFileList(file_id, na_id);
	}

	@Override
	public boolean deleteNoticeAnnounceFile(HttpServletRequest request,
			String file_id, String na_id) {
		String ctxPath = request.getSession().getServletContext().getRealPath("/");
		if (file_id != null) {
			List<Map<String,Object>> list = dao.getNoticeAnnounceFileList(file_id, null);
			if (list.size()>0) {
				String path = list.get(0).get("file_path").toString();
				FileUtils.lkh_delFile(String.format(ctxPath+"%s", path));
				return dao.deleteNoticeAnnounceFile(file_id, null);
			}
		}
		if (na_id != null) {
			List<Map<String,Object>> booklist = dao.getNoticeAnnounceList(0, 10,null);
			if (booklist.get(0).containsKey("coverpath")) {
				String coverpath = booklist.get(0).get("coverpath").toString();
				FileUtils.lkh_delFile(String.format(ctxPath+"%s", coverpath));
			}
			List<Map<String,Object>> filelist = dao.getNoticeAnnounceFileList(null, na_id);
			if (filelist.size()>0) {
				for (int i = 0; i < filelist.size(); i++) {
					String path = filelist.get(i).get("file_path").toString();
					FileUtils.lkh_delFile(String.format(ctxPath+"%s", path));
				}
				if(!dao.deleteNoticeAnnounceFile(null, na_id)){
					return false;
				}
			}
		}
		return true;
	}
	
	

}
