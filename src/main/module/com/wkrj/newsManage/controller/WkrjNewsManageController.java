package com.wkrj.newsManage.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wkrj.newsManage.bean.WkrjNewsFile;
import com.wkrj.newsManage.bean.WkrjNewsManage;
import com.wkrj.newsManage.bean.WkrjNewsManageOp;
import com.wkrj.newsManage.service.WkrjNewsManageService;

import wkrjsystem.department.bean.WkrjDept;
import wkrjsystem.department.service.WkrjDeptService;
import wkrjsystem.user.bean.WkrjUser;
import wkrjsystem.user.service.WkrjUserService;
import wkrjsystem.utils.AjaxJson;
import wkrjsystem.utils.Guid;
import wkrjsystem.utils.SpringContextUtil;
import wkrjsystem.utils.UtilsHelper;
import wkrjsystemdev.userdev.bean.WkrjUserDev;

@Controller
@RequestMapping("wkrjsystem/newsManage")
public class WkrjNewsManageController {
	
	@Autowired
	//@Qualifier("WkrjNewsInfoService")
	private WkrjNewsManageService newsManageService;
	
	@Autowired
	private WkrjDeptService deptService;
	
	@Autowired
	private WkrjUserService userService;
	
	@RequestMapping("getPage")
	public String getPage(){
		return "systemdev/newsManage/newsManage";
	}
	
	@RequestMapping("getAddPage")
	public String getAddPage(){
		
		return "system/newsManage/newsManage_add";
	}
	
	/**
	 * 获取通知公告信息
	 * @param request
	 * @return
	 */
	@RequestMapping("getNewsList")
	@ResponseBody
	public Object getNewsManageList(HttpServletRequest request){
		WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
		WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
		boolean isGly = false;
		String user_id = "";
		String user_dept = "";
		String isSchool = "";
		String roleIds = request.getSession().getAttribute("userRoleId")+"";
		String[] role_ids = roleIds.split(",");
		for (int i = 0; i < role_ids.length; i++) {
			//如果是系统管理员
			if ("2".equals(role_ids[i])) {
				isGly = true;
				break;
			}
		}
		if (user != null) {
			user_id = user.getUser_id();
			user_dept = user.getDept_id();
		} else if (userDev != null) {
			user_id = userDev.getUser_id();
			user_dept = userDev.getDept_id();
			isSchool = userDev.getSchool_id();
		}
		String news_title = request.getParameter("news_title");
		String news_inputtime = request.getParameter("news_inputtime");
		String end_date = request.getParameter("end_date");
		int page=Integer.parseInt(request.getParameter("page"));
		int pagesize=Integer.parseInt(request.getParameter("pagesize"));
		int offset = (page-1)*pagesize;
		
		List<Map<String, Object>> list = this.newsManageService.getNewsList(offset, pagesize, user_id, user_dept, news_title, news_inputtime, end_date, isGly,isSchool);
		long count = this.newsManageService.getNewsList(user_id, user_dept, news_title, news_inputtime, end_date, isGly,isSchool);
		
		return UtilsHelper.returnMap(list,count);
	}

	/**
	 * 获取通知公告操作记录信息
	 * @param request
	 * @return
	 */
	@RequestMapping("getNewsOpDetail")
	@ResponseBody
	public Object getNewsOpDetail(HttpServletRequest request){
		WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
		WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
		boolean isGly = false;
		String user_id = "";
		String user_dept = "";
		String isSchool = "";
		String roleIds = request.getSession().getAttribute("userRoleId")+"";
		String[] role_ids = roleIds.split(",");
		for (int i = 0; i < role_ids.length; i++) {
			//如果是系统管理员
			if ("2".equals(role_ids[i])) {
				isGly = true;
				break;
			}
		}
		if (user != null) {
			user_id = user.getUser_id();
			user_dept = user.getDept_id();
		} else if (userDev != null) {
			user_id = userDev.getUser_id();
			user_dept = userDev.getDept_id();
		}
		String news_title = request.getParameter("news_title");
		String op_signtime = request.getParameter("op_signtime");
		String op_looktime = request.getParameter("op_looktime");
		String end_date = request.getParameter("end_date");
		String end_looktime = request.getParameter("end_looktime");
		int page=Integer.parseInt(request.getParameter("page"));
		int pagesize=Integer.parseInt(request.getParameter("pagesize"));
		int offset = (page-1)*pagesize;
		List<Map<String, Object>> list = this.newsManageService.getNewsOpDetail(offset, pagesize, news_title, op_signtime, op_looktime, end_date, end_looktime, isGly, user_id, user_dept, isSchool);
		long count = this.newsManageService.getNewsOpDetail(news_title,op_signtime,op_looktime,end_date,end_looktime, isGly, user_id, user_dept, isSchool);
		
		return UtilsHelper.returnMap(list,count);
	}
	
	@RequestMapping("getUserTree")
	@ResponseBody
	public Object getUserTree(){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			List<Map<String,Object>> list = newsManageService.getUserList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 获取用户名称通过id
	 * @param userId
	 * @return
	 */
	@RequestMapping("getUserNameById")
	@ResponseBody
	public AjaxJson getUserNameById(String userId){
		
		AjaxJson json = new AjaxJson();
		String userIds[] = userId.split(",");
		String _user = "";
		for (int i = 0; i < userIds.length; i++) {
			WkrjUser user = newsManageService.getUserNameById(userIds[i]);
			if (user != null) {
				_user += user.getUser_realname();
				if (i < userIds.length-1) {
					_user += ",";
				}
			}
		}
		
		json.setObj(_user);
		json.setSuccess(true);
		json.setMsg("返回成功");
		
		return json;
	}
	
	/**
	 * 根据ID获取查看用户名称
	 * @param userId
	 * @return
	 */
	@RequestMapping("getLookUserName")
	@ResponseBody
	public AjaxJson getLookUserName(String userId){
		
		AjaxJson json = new AjaxJson();
		String userIds[] = userId.split(",");
		String _user = "";
		for (int i = 0; i < userIds.length; i++) {
			WkrjUser user = newsManageService.getUserNameById(userIds[i]);
			if (user != null) {
				_user += user.getUser_realname();
				if (i < userIds.length-1) {
					_user += ",";
				}
			}
		}
		
		json.setObj(_user);
		json.setSuccess(true);
		json.setMsg("返回成功");
		
		return json;
	}

	/**
	 * 获取签收查看人名称通过id
	 * @param userId
	 * @return
	 */
	@RequestMapping("getReceiverById")
	@ResponseBody
	public AjaxJson getReceiverById(String userId){
		
		AjaxJson json = new AjaxJson();
		if(userId!=null && !"".equals(userId)){
			String userIds[] = userId.split(",");
			String _user = "";
			for (int i = 0; i < userIds.length; i++) {
				WkrjUser user = newsManageService.getUserNameById(userIds[i]);
				if (user != null) {
					_user += user.getUser_realname();
					if (i < userIds.length-1) {
						_user += ",";
					}
				}
			}
			
			json.setObj(_user);
		}
	
		json.setSuccess(true);
		json.setMsg("返回成功");
		
		return json;
	}
	
	@RequestMapping("addNews")
	@ResponseBody
	public AjaxJson addNewsManage(HttpServletRequest request, WkrjNewsManage newsManage){
		WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
		WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
		AjaxJson json = new AjaxJson();
		String user_name = "";
		String user_dept = "";
		String user_id = "";
		newsManage.setNews_id(Guid.getGuid());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date());
		newsManage.setNews_inputtime(date);
		WkrjNewsFile newsManageFile = new WkrjNewsFile();
		if (user != null) {
			user_name = user.getUser_name();
			user_dept = user.getDept_id();
			user_id = user.getUser_id();
		} else if (userDev != null) {
			user_name = userDev.getUser_name();
			user_dept = userDev.getDept_id();
			user_id = userDev.getUser_id();
		}
		//如果页面上的查看范围没选择自己（即当前用户），则添加通知公告的用户也自动包括在内
		if (!"".equals(newsManage.getNews_lookarea()) && newsManage.getNews_lookarea() != null) {
			String users[] = newsManage.getNews_lookarea().split(",");
			boolean flag = false;
			for (int i = 0; i < users.length; i++) {
				if (users[i].equals(user_id)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				newsManage.setNews_lookarea(newsManage.getNews_lookarea()+","+user_id);
			}
		}
		newsManage.setNews_inputuser(user_id);
		newsManage.setNews_inputuserdept(user_dept);
		if (newsManage.getFile_xname() != null && !"".equals(newsManage.getFile_xname())) {
			newsManageFile.setNews_id(newsManage.getNews_id());
			newsManageFile.setFile_xname(newsManage.getFile_xname());
			newsManageFile.setFile_yname(newsManage.getFile_yname());
			newsManageFile.setFile_inputtime(date);
			newsManageFile.setFile_inputuser(user_name);
			if (newsManageService.addNews(newsManage) && newsManageService.addNewsFile(newsManageFile)) {
				json.setSuccess(true);
				json.setMsg("保存成功");
			}
		} else {
			if (newsManageService.addNews(newsManage)) {
				json.setSuccess(true);
				json.setMsg("保存成功");
			}
		}
		
		return json;
	}
	
	@RequestMapping("updateNews")
	@ResponseBody
	public AjaxJson updateNewsManage(WkrjNewsManage newsManage, String yFileName, HttpServletRequest request){
		WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
		WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
		AjaxJson json = new AjaxJson();
		//前台传过来的是分号分隔的，替换掉
		newsManage.setNews_lookarea(newsManage.getNews_lookarea().replace(";", ","));
		String user_id = "";
		//String user_name = "";
		if (user != null) {
			user_id = user.getUser_id();
			//user_name = user.getUser_name();
		} else if (userDev != null) {
			user_id = userDev.getUser_id();
			//user_name = userDev.getUser_name();
		}
		//修改通知公告的用户也自动包括在内
//		if (!"".equals(newsManage.getNews_lookarea()) && newsManage.getNews_lookarea() != null) {
//			newsManage.setNews_lookarea(newsManage.getNews_lookarea()+","+user_id);
//		}
		//如果页面上的查看范围没选择自己（即当前用户），则添加通知公告的用户也自动包括在内
		if (!"".equals(newsManage.getNews_lookarea()) && newsManage.getNews_lookarea() != null) {
			String users[] = newsManage.getNews_lookarea().split(",");
			boolean flag = false;
			for (int i = 0; i < users.length; i++) {
				if (users[i].equals(user_id)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				newsManage.setNews_lookarea(newsManage.getNews_lookarea()+","+user_id);
			}
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date());
		WkrjNewsFile newsManageFile = new WkrjNewsFile();
		if (newsManage.getFile_xname() != null && !"".equals(newsManage.getFile_xname())) {
			newsManageFile.setNews_id(newsManage.getNews_id());
			newsManageFile.setFile_xname(newsManage.getFile_xname());
			newsManageFile.setFile_yname(newsManage.getFile_yname());
			newsManageFile.setFile_inputtime(date);
			newsManageFile.setFile_inputuser(user_id);
			if (newsManageService.updateNews(newsManage, yFileName) && newsManageService.addNewsFile(newsManageFile)) {
				json.setSuccess(true);
				json.setMsg("修改成功");
			}
		} else {
			if (newsManageService.updateNews(newsManage, yFileName)) {
				json.setSuccess(true);
				json.setMsg("修改成功");
			}
		}
		
		return json;
	}
	
	@RequestMapping("delNews")
	@ResponseBody
	public AjaxJson delNewsManage(String id){
		
		AjaxJson json = new AjaxJson();
		try{
			if(newsManageService.delNews(id)){
				json.setSuccess(true);
				json.setMsg("删除成功");
			}
		}catch(Exception e){
			if(e.getStackTrace()[0].getClassName().indexOf("SQLErrorCodeSQLExceptionTranslator")>=0){
				json.setMsg("删除失败,已被其他模块使用");
			}else{
				e.printStackTrace();
			}
		}
		return json;
	}
	
	@RequestMapping("signNews")
	@ResponseBody
	public AjaxJson signNews(HttpServletRequest request, String news_id){
		
		AjaxJson json = new AjaxJson();
		WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
		WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
		String op_signuser = "";
		if (user != null) {
			op_signuser = user.getUser_id();
			//op_signuser = user.getUser_realname();
		} else if (userDev != null) {
			op_signuser = userDev.getUser_id();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date());
		WkrjNewsManageOp newsOp = new WkrjNewsManageOp();
		newsOp.setNews_id(news_id);
		newsOp.setOp_signuser(op_signuser);
		newsOp.setOp_signtime(date);
		try{
			if (!newsManageService.isSignOrNot(news_id, op_signuser)) {
				if (newsManageService.signNews(newsOp)) {
					json.setSuccess(true);
					json.setMsg("签收成功");
				}
			} else {
				json.setSuccess(false);
				json.setMsg("已经签收过");
			}
		}catch(Exception e){
			if(e.getStackTrace()[0].getClassName().indexOf("SQLErrorCodeSQLExceptionTranslator")>=0){
				json.setMsg("签收失败");
			}else{
				e.printStackTrace();
			}
		}
		return json;
	}
	
	@RequestMapping("checkNews")
	@ResponseBody
	public AjaxJson checkNews(HttpServletRequest request, String news_id){
		
		AjaxJson json = new AjaxJson();
		WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
		WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
		String op_signuser = "";
		String op_lookuser = "";
		if (user != null) {
			op_signuser = user.getUser_id();
			op_lookuser = user.getUser_id();
		} else if (userDev != null) {
			op_signuser = userDev.getUser_id();
			op_lookuser = userDev.getUser_id();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date());
		WkrjNewsManageOp newsOp = new WkrjNewsManageOp();
		newsOp.setNews_id(news_id);
		newsOp.setOp_signuser(op_signuser);
		newsOp.setOp_signtime(date);
		try{
			if (!newsManageService.isSignOrNot(news_id, op_signuser)) {
				json.setSuccess(false);
				json.setMsg("请先签收再查看");
			} else {
				newsOp.setOp_lookuser(op_lookuser);
				newsOp.setOp_looktime(date);
				if (newsManageService.checkNews(newsOp)) {
					json.setSuccess(true);
					json.setMsg("查看成功");
				}
			}
		}catch(Exception e){
			if(e.getStackTrace()[0].getClassName().indexOf("SQLErrorCodeSQLExceptionTranslator")>=0){
				json.setMsg("查看失败");
			}else{
				e.printStackTrace();
			}
		}
		return json;
	}
	
	@RequestMapping("ifCheckNews")
	@ResponseBody
	public AjaxJson ifCheckNews(HttpServletRequest request, String news_id){
		AjaxJson json = new AjaxJson();
		WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
		WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
		String op_lookuser = "";
		if (user != null) {
			op_lookuser = user.getUser_id();
		} else if (userDev != null) {
			op_lookuser = userDev.getUser_id();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date());
		WkrjNewsManageOp newsOp = new WkrjNewsManageOp();
		newsOp.setNews_id(news_id);
		newsOp.setOp_signtime(date);
		try{
				newsOp.setOp_lookuser(op_lookuser);
				newsOp.setOp_looktime(date);
				if (newsManageService.ifCheckNews(op_lookuser,news_id)) {
					json.setSuccess(true);
					json.setMsg("查看成功");
				}
		}catch(Exception e){
			if(e.getStackTrace()[0].getClassName().indexOf("SQLErrorCodeSQLExceptionTranslator")>=0){
				json.setMsg("查看失败");
			}else{
				e.printStackTrace();
			}
		}
		return json;
	}
	
	/**
	 * 
	 * @param newsManage_icons 上传文件的name名称
	 * @param request
	 * @param foledArrress 保存文件的路径，
	 * @return
	 */
	@RequestMapping("/uploadPic")
	@ResponseBody
	public String uploadPic(MultipartFile newsManage_file,HttpServletRequest request,String foledArrress){
		return this.newsManageService.uploadPic(newsManage_file, request, foledArrress);
	} 
	
	/**
	 * 获取部门名称通过id
	 * @param deptId
	 * @return
	 */
	@RequestMapping("getDeptNameById")
	@ResponseBody
	public AjaxJson getDeptNameById(String deptId){
		
		AjaxJson json = new AjaxJson();
		WkrjDept dept = deptService.getDeptNameById(deptId);
		
		json.setObj(dept);
		json.setSuccess(true);
		json.setMsg("返回成功");
		
		return json;
	}
	
	@RequestMapping("getUserList")
	@ResponseBody
	public Object getUserList(HttpServletRequest request, String deptId){
		int page=Integer.parseInt(request.getParameter("page"));
		String userName = request.getParameter("userName");
		String schoolId = request.getParameter("schoolId");
		int pagesize=Integer.parseInt(request.getParameter("pagesize"));
		
		WkrjUser sessionUser = (WkrjUser) request.getSession().getAttribute("user");
		WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
		boolean isGly = false;
		//String user_id = "";
		String user_dept = "";
		String isSchool = "";
		String roleIds = request.getSession().getAttribute("userRoleId")+"";
		String[] role_ids = roleIds.split(",");
		for (int i = 0; i < role_ids.length; i++) {
			//如果是系统管理员
			if ("2".equals(role_ids[i])) {
				isGly = true;
				break;
			}
		}
		/*if (sessionUser != null) {
			//user_id = sessionUser.getUser_id();
			user_dept = sessionUser.getDept_id();
			isSchool=sessionUser.getSchool_id();
		} else if (userDev != null) {
			//user_id = userDev.getUser_id();
			user_dept = userDev.getDept_id();
		}
		
		int offset = (page-1)*pagesize;
		List<WkrjUser> list = this.userService.getUserList(offset, pagesize, deptId, userName,schoolId,isGly,isSchool,user_dept);
		for(WkrjUser user:list){
			//获取角色信息
			user.setUser_role(this.userService.getRoleListByUserId(user.getUser_id()));
		}
		long count = this.userService.getUserList(deptId, userName,schoolId,isGly,isSchool,user_dept);*/
		
		return null;//UtilsHelper.returnMap(list,count);
	}
	
	/**
	 * 获取部门树
	 * @return
	 */
	@RequestMapping("getDeptTree")
	@ResponseBody
	public Object getDeptTree(HttpServletRequest request){
		deptService = (WkrjDeptService) SpringContextUtil.getBean("wkrjDeptService");
		
		WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
		WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
		boolean isGly = false;
		String user_dept = "";
		String roleIds = request.getSession().getAttribute("userRoleId")+"";
		String[] role_ids = roleIds.split(",");
		for (int i = 0; i < role_ids.length; i++) {
			//如果是系统管理员
			if ("2".equals(role_ids[i])) {
				isGly = true;
				break;
			}
		}
		if (user != null) {
			user_dept = user.getDept_id();
		} else if (userDev != null) {
			user_dept = userDev.getDept_id();
		}
		
		return deptService.getRoleList("-1",isGly,user_dept);
	}
	/**
	 * 获取通知公告相关的附件
	 * @param request
	 * @return
	 */
	@RequestMapping("checkFileList")
	@ResponseBody
	public Object checkFileList(HttpServletRequest request, String notice_id,String news_id){
		int page=Integer.parseInt(request.getParameter("page"));

		int pagesize=Integer.parseInt(request.getParameter("pagesize"));
		int offset = (page-1)*pagesize;
		List<Map<String, Object>> list = this.newsManageService.getnewsManageFileList(offset, pagesize, news_id);
		long count = this.newsManageService.getnewsManageFileList(news_id);
		
		return UtilsHelper.returnMap(list,count);
	}
	/**
	 * 获取通知公告相关的操作记录
	 * @param request
	 * @return
	 */
	@RequestMapping("checkOpList")
	@ResponseBody
	public Object checkOpList(HttpServletRequest request, String news_id){
		int page=Integer.parseInt(request.getParameter("page"));

		int pagesize=Integer.parseInt(request.getParameter("pagesize"));
		int offset = (page-1)*pagesize;
		List<WkrjNewsManageOp> list = this.newsManageService.checkOpList(offset, pagesize, news_id);
		long count = this.newsManageService.checkOpList(news_id);
		
		return UtilsHelper.returnMap(list,count);
	}
	/*
	 * 删除附件
	 */
	@RequestMapping("delnewsManageFile")
	@ResponseBody
	public AjaxJson delnewsManageFile(String id, String xFileName){
		
		AjaxJson json = new AjaxJson();
		
		try{
			if(newsManageService.delnewsManageFile(id, xFileName)){
				json.setSuccess(true);
				json.setMsg("删除成功");
			}
		}catch(Exception e){
			if(e.getStackTrace()[0].getClassName().indexOf("SQLErrorCodeSQLExceptionTranslator")>=0){
				json.setMsg("删除失败");
			}else{
				e.printStackTrace();
			}
		}
		
		return json;
	}
	
	/**
	 * 下载附件
	 * @param request
	 * @param response
	 * @param fileName
	 * @param realName
	 * @throws Exception
	 */
	@RequestMapping("/downloadFile")
    public static void downloadFile(HttpServletRequest request,
            HttpServletResponse response, String fileName,
            String realName)  {
        response.setContentType("text/html;charset=UTF-8");  
        try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}  
        BufferedInputStream bis = null;  
        BufferedOutputStream bos = null;  
  
        String newsManagePath = new WkrjNewsManageController().getClass().getClassLoader().getResource("/")
				.getPath()+ "../../upload/newsManage/";  
        String downLoadPath = newsManagePath + fileName;
  
        long fileLength = new File(downLoadPath).length();  
  
        try {
			 response.setHeader("Content-disposition", "attachment; filename="  
			        + new String(realName.getBytes("utf-8"), "ISO8859-1"));
			
			 response.setHeader("Content-Length", String.valueOf(fileLength));  
			  
		        bis = new BufferedInputStream(new FileInputStream(downLoadPath));  
		        bos = new BufferedOutputStream(response.getOutputStream());  
		        byte[] buff = new byte[2048];  
		        int bytesRead;  
		        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
		            bos.write(buff, 0, bytesRead);  
		        }  
		        bis.close();  
		        bos.close();  
			
		} catch (Exception e) {
			
			response.setHeader("Location", response.encodeRedirectURL("index"));
			
		}  
    }  
}
