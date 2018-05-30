package com.wkrj.warning.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import org.springframework.web.multipart.MultipartFile;

import wkrjsystem.utils.FileUtils;
import wkrjsystem.utils.Guid;
import wkrjsystem.utils.UtilsHelper;

import com.wkrj.warning.bean.NoticeAnnounce;
import com.wkrj.warning.bean.NoticeAnnounceFile;
import com.wkrj.warning.dao.NoticeAnnounceDao;

@Service("noticeAnnounceService")
@Transactional
public class NoticeAnnounceServiceImpl implements NoticeAnnounceService {
	
	//路径
	private String realPath= getClass().getClassLoader().getResource("/").getPath()+ "../../upload/noticeannounce/";
	
	@Autowired
	private NoticeAnnounceDao dao;

	@Override
	public List<Map<String, Object>> getNoticeAnnounceList(int offset, int rows,String user_id,String user_dept,String na_title,
			String na_inputtime,String end_date) {
		return dao.getNoticeAnnounceList(offset, rows,user_id,user_dept,na_title,na_inputtime,end_date);
	}

	@Override
	public boolean addNoticeAnnounce(NoticeAnnounce noticeAnnounce,
			NoticeAnnounceFile file) {
		try {
			String na_id = Guid.getGuid();
			noticeAnnounce.setNa_id(na_id);
			noticeAnnounce.setNa_inputtime(UtilsHelper.getDateFormatTime());
			if(file.getFile_yname()!=null&&!"".equals(file.getFile_yname())){
				String yfilenames = file.getFile_yname();
				String xfilenames = file.getFile_xname();
				for (int i = 0, len = yfilenames.split(",").length; i < len; i++) {
					file.setFile_yname(yfilenames.split(",")[i]);
					file.setFile_xname(xfilenames.split(",")[i]);
					file.setFile_id(Guid.getGuid());
					file.setNa_id(noticeAnnounce.getNa_id());
					file.setFile_inputtime(noticeAnnounce.getNa_inputtime());
					if (!dao.addNoticeAnnounceFile(file)) {
						return false;
					}
				}
			}
				dao.addNoticeAnnounce(noticeAnnounce);
		} catch (Exception e) {
//			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean updateNoticeAnnounce(NoticeAnnounce noticeAnnounce,
			NoticeAnnounceFile file) {
		try {
			if(file.getFile_yname()!=null&&!"".equals(file.getFile_yname())){
				//先删除数据之前的附件信息重新添加
				dao.deleteNoticeAnnounceFile(null, noticeAnnounce.getNa_id());
				
				String yfilenames = file.getFile_yname();
				String xfilenames = file.getFile_xname();
				for (int i = 0, len = yfilenames.split(",").length; i < len; i++) {
					file.setFile_yname(yfilenames.split(",")[i]);
					file.setFile_xname(xfilenames.split(",")[i]);
					file.setFile_id(Guid.getGuid());
					file.setNa_id(noticeAnnounce.getNa_id());
					file.setFile_inputtime(UtilsHelper.getDateFormatTime());
					file.setFile_inputuser(noticeAnnounce.getNa_inputuser());
					if (!dao.addNoticeAnnounceFile(file)) {
						return false;
					}
				}
			}
			dao.updateNoticeAnnounce(noticeAnnounce);
		} catch (Exception e) {
//			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteNoticeAnnounce(HttpServletRequest request, String na_id) {
		try {
			if(deleteLocalFile(na_id) && dao.deleteNoticeAnnounceFile(null, na_id)
					&&dao.deleteNoticeAnnounce(na_id)){
				return true;
			}else{
				return false;
			}
			
		} catch (Exception e) {
//			e.printStackTrace();
			return false;
		}
		
	}
	
	
	public boolean deleteLocalFile(String na_id){
		List<NoticeAnnounceFile> fileList = dao.getNaFile(na_id);	
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
	
	
	public String uploadPic(MultipartFile newsManage_file,HttpServletRequest request,String foledArrress){
		
		
		
		if(null==foledArrress || "".equals(foledArrress)){
			foledArrress="upload/noticeannounce/";
		}
		
		String realPath = request.getSession().getServletContext().getRealPath("/")+foledArrress;
		
		String yFileName = newsManage_file.getOriginalFilename();//原文件名
		
		long timeMillis = System.currentTimeMillis();//获取时间戳 这种速度更快一些
		String extra = "wkrj";//由于样式不能是数字所以加上wkrj来区分
		
		String xFileName = extra+timeMillis+extra+yFileName.substring(yFileName.lastIndexOf("."), yFileName.length());//现文件名
		
		try {
			org.apache.commons.io.FileUtils.copyInputStreamToFile(newsManage_file.getInputStream(), new File(realPath,xFileName));
		} catch (Exception e) {
//			System.err.println("上传附件出错了");
		}
		return "{\"success\":\"true\",\"filename\":\""+xFileName+"\",\"yFileName\":\""+yFileName+"\"}";
	}

	@Override
	public boolean delSingleNaFile(String file_id, String xFileName) {
		if (dao.deleteNoticeAnnounceFile(file_id,null) && this.delSingleFile(xFileName)) {
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

}
