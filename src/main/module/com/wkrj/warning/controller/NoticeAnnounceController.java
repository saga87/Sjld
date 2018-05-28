package com.wkrj.warning.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import wkrjsystem.user.bean.WkrjUser;
import wkrjsystem.utils.AjaxJson;
import wkrjsystem.utils.FileUtils;
import wkrjsystem.utils.UtilsHelper;
import wkrjsystemdev.userdev.bean.WkrjUserDev;

import com.wkrj.warning.bean.NoticeAnnounce;
import com.wkrj.warning.bean.NoticeAnnounceFile;
import com.wkrj.warning.service.NoticeAnnounceService;

@Controller
@RequestMapping("noticeAnnounce")
public class NoticeAnnounceController {
	
	@Autowired
	private NoticeAnnounceService service;
	
	/**
	 * 获取通知公告列表
	 * @param pagesize 每页数
	 * @param page 当前页
	 * @param request 请求对象
	 * @return
	 */
	@RequestMapping("getNoticeAnnounceList")
	@ResponseBody
	public Object getNoticeAnnounceList(int pagesize, int page,String na_title){
		int offset = (page-1) * pagesize;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list = service.getNoticeAnnounceList(offset,pagesize,na_title);
		return UtilsHelper.returnMap(list, list.size());
	}
	
	/**
	 * 获取通知公告附件列表
	 * @param na_id
	 * @return
	 */
	@RequestMapping("getNoticeAnnounceFileList")
	@ResponseBody
	public Object getNoticeAnnounceFileList(String na_id){
		List<Map<String,Object>> list = service.getNoticeAnnounceFileList(null, na_id);
		return UtilsHelper.returnMap(list, list.size());
	}
	
	/**
	 * 添加信息
	 * @return
	 */
	@RequestMapping("addNoticeAnnounce")
	@ResponseBody
	public Object addNoticeAnnounce(HttpServletRequest request,NoticeAnnounce noticeAnnounce){
		WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
		WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
		String user_name = "";
		String user_dept = "";
		String user_id = "";
		if (user != null) {
			user_name = user.getUser_name();
			user_dept = user.getDept_id();
			user_id = user.getUser_id();
		} else if (userDev != null) {
			user_name = userDev.getUser_name();
			user_dept = userDev.getDept_id();
			user_id = userDev.getUser_id();
		}
		
		if(noticeAnnounce.getAccept_user()!=null&&!"".equals(noticeAnnounce.getAccept_user())){
			String users[] = noticeAnnounce.getAccept_user().split(",");
			boolean flag = false;
			for (int i = 0; i < users.length; i++) {
				if (users[i].equals(user_id)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				noticeAnnounce.setAccept_user(noticeAnnounce.getAccept_user()+","+user_id);
			}
		}
		noticeAnnounce.setNa_inputuser(user_name);
		noticeAnnounce.setNa_inputuserdept(user_dept);
		
		AjaxJson json = new AjaxJson();
		
		NoticeAnnounceFile file = new NoticeAnnounceFile();
		
		if (noticeAnnounce.getFile_xname() != null && !"".equals(noticeAnnounce.getFile_xname())) {
		//	file.setNa_id(noticeAnnounce.getNa_id());
			file.setFile_xname(noticeAnnounce.getFile_xname());
			file.setFile_yname(noticeAnnounce.getFile_yname());
//			file.setFile_inputtime(date);
			file.setFile_inputuser(user_name);
		} 
		
		if (service.addNoticeAnnounce(noticeAnnounce, file)) {
			json.setSuccess(true);
			json.setMsg("添加成功");
		}
		return json;
			
	}
	/**
	 * 更新信息
	 * @return
	 */
	@RequestMapping("updateNoticeAnnounce")
	@ResponseBody
	public Object updateNoticeAnnounce(HttpServletRequest request,NoticeAnnounce noticeAnnounce){
		WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
		WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
		String user_name = "";
		String user_dept = "";
		String user_id = "";
		if (user != null) {
			user_name = user.getUser_name();
			user_dept = user.getDept_id();
			user_id = user.getUser_id();
		} else if (userDev != null) {
			user_name = userDev.getUser_name();
			user_dept = userDev.getDept_id();
			user_id = userDev.getUser_id();
		}
		
		if(noticeAnnounce.getAccept_user()!=null&&!"".equals(noticeAnnounce.getAccept_user())){
			String users[] = noticeAnnounce.getAccept_user().split(",");
			boolean flag = false;
			for (int i = 0; i < users.length; i++) {
				if (users[i].equals(user_id)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				noticeAnnounce.setAccept_user(noticeAnnounce.getAccept_user()+","+user_id);
			}
		}
		noticeAnnounce.setNa_inputuser(user_name);
		noticeAnnounce.setNa_inputuserdept(user_dept);
		
		
		NoticeAnnounceFile file = new NoticeAnnounceFile();
		
		if (noticeAnnounce.getFile_xname() != null && !"".equals(noticeAnnounce.getFile_xname())) {
		//	file.setNa_id(noticeAnnounce.getNa_id());
			file.setFile_xname(noticeAnnounce.getFile_xname());
			file.setFile_yname(noticeAnnounce.getFile_yname());
//			file.setFile_inputtime(date);
			file.setFile_inputuser(user_name);
		} 
		
		
		AjaxJson json = new AjaxJson();
		if (service.updateNoticeAnnounce(noticeAnnounce, file)) {
			json.setSuccess(true);
			json.setMsg("更新成功");
		}
		return json;
		
	}
	/**
	 * 删除信息
	 * @param books_id
	 * @return
	 */
	@RequestMapping("deleteNoticeAnnounce")
	@ResponseBody
	public Object deleteNoticeAnnounce(HttpServletRequest request,String na_id){
		AjaxJson json = new AjaxJson();
		if (service.deleteNoticeAnnounce(request,na_id)) {
			json.setSuccess(true);
			json.setMsg("删除成功");
		}
		return json;
		
	}

	@RequestMapping("uploadFile")
	@ResponseBody
	public String uploadFile(HttpServletRequest request,MultipartFile na_file,
			String foledArrress){
		
		return this.service.uploadPic(na_file, request, foledArrress);
	}
	
	@RequestMapping("deleFile")
	@ResponseBody
	public Object deleFile(HttpServletRequest request,String path){
		AjaxJson json = new AjaxJson();
		String ctxPath = request.getSession().getServletContext().getRealPath("/");
		if(!"".equals(path) && path != null){
			FileUtils.lkh_delFile(String.format(ctxPath+"%s", path));
			json.setSuccess(true);
			json.setMsg("删除成功");
			return json;
		}
		return json;
	}
	
}
