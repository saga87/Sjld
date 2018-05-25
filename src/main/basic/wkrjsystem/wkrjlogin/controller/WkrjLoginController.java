package wkrjsystem.wkrjlogin.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import wkrjsystem.role.bean.WkrjRole;
import wkrjsystem.role.service.WkrjRoleService;
import wkrjsystem.user.bean.WkrjUser;
import wkrjsystem.utils.AjaxJson;
import wkrjsystem.utils.MD5;
import wkrjsystem.wkrjlogin.service.WkrjLonginService;


@Controller
@RequestMapping("/wkrjsystem/wkrjlogin")
public class WkrjLoginController {

	@Autowired
	private WkrjLonginService wkrjLonginService;
	
	@Autowired
	private WkrjRoleService roleService;	
	
	@RequestMapping("checkLogin")
	@ResponseBody
	public AjaxJson checkLogin(String username,String password,String yzm,HttpServletRequest req){
		
		AjaxJson json = new AjaxJson();
		
		String realYzm = (String) req.getSession().getAttribute("validateCode");		
		if(null==yzm || !yzm.equalsIgnoreCase(realYzm)){
			json.setMsg("验证码输入错误");
			json.setSuccess(false);
			return json;
		}
		
		WkrjUser user = new WkrjUser();
		user.setUser_name(username);
		user.setUser_password(MD5.MD5Encode(password));
		
		user = wkrjLonginService.findUserByNameAndPwd(user);
		
		if(null!=user){
		
			String userId = user.getUser_id();
			user = this.wkrjLonginService.getUserInfoById(userId);
			
			//使用权限工具进行用户登录，登录成功后跳到shiro配置的successUrl中，与下面的return没什么关系！  
			UsernamePasswordToken token = new UsernamePasswordToken(user.getUser_name()+"_wkrj", user.getUser_password());
			SecurityUtils.getSubject().login(token);
			
			String roleId="";
			
			List<WkrjRole> user_role = user.getUser_role();
			if(user_role.size()>0){
				for(WkrjRole uRole:user_role){
					roleId += uRole.getRole_id();
					roleId +=",";
				}
			}

			req.getSession().setAttribute("user", user);//将user对象放入sessin中
			req.getSession().setAttribute("userCountType", user.getUser_accounttype());//将user类型放入sessin中
			req.getSession().setAttribute("userRoleId", roleId);//将role放入sessin中
			if(1==user.getUser_accounttype()){
				req.getSession().setAttribute("userPermission", roleService.getAllPermission());
			}else{
				req.getSession().setAttribute("userPermission", roleService.getRolePermissionByRoleId(roleId.substring(0, roleId.length()-1)));
			}
			
			json.setSuccess(true);
		}else{
			json.setSuccess(false);
			json.setMsg("用户名或者密码错误！");
		}

		return json;
	}
	
	@RequestMapping("checkLogin_mobile")
	@ResponseBody
	public AjaxJson checkLogin_mobile(String username,String password,HttpServletRequest req){
		
		AjaxJson json = new AjaxJson();

		WkrjUser user = new WkrjUser();
		user.setUser_name(username);
		user.setUser_password(MD5.MD5Encode(password));
		
		user = wkrjLonginService.findUserByNameAndPwd(user);
		
		if(null!=user){
		
			String userId = user.getUser_id();
			user = this.wkrjLonginService.getUserInfoById(userId);
			
			//使用权限工具进行用户登录，登录成功后跳到shiro配置的successUrl中，与下面的return没什么关系！  
			UsernamePasswordToken token = new UsernamePasswordToken(user.getUser_name()+"_wkrj", user.getUser_password());
			SecurityUtils.getSubject().login(token);
			
			String roleId="";
			
			List<WkrjRole> user_role = user.getUser_role();
			if(user_role.size()>0){
				for(WkrjRole uRole:user_role){
					roleId += uRole.getRole_id();
					roleId +=",";
				}
			}

			req.getSession().setAttribute("user", user);//将user对象放入sessin中
			req.getSession().setAttribute("userCountType", user.getUser_accounttype());//将user类型放入sessin中
			req.getSession().setAttribute("userRoleId", roleId);//将role放入sessin中
			if(1==user.getUser_accounttype()){
				req.getSession().setAttribute("userPermission", roleService.getAllPermission());
			}else{
				req.getSession().setAttribute("userPermission", roleService.getRolePermissionByRoleId(roleId.substring(0, roleId.length()-1)));
			}
			
			json.setSuccess(true);
		}else{
			json.setSuccess(false);
			json.setMsg("用户名或者密码错误！");
		}

		return json;
	}
	
	@RequestMapping("login")
	public String login(String username,String password,HttpServletRequest req){
		
		WkrjUser user = (WkrjUser) req.getSession().getAttribute("user");
		
		if(null != user){
			
//			if(1==user.getUser_accounttype()){
//				return "system/developer";
//			}else{
				return "system/index";
//			}
			
		}
		
		return "redirect:/system/login.jsp";
	}	
	
	@RequestMapping("/loginout")
	public String logout(HttpServletRequest req,RedirectAttributes redirectAttributes){
	
		WkrjUser user = (WkrjUser) req.getSession().getAttribute("user");
		if(null==user){
			return "redirect:/system/login.jsp";
		}
		
		//使用权限管理工具进行用户的退出，跳出登录，给出提示信息  
        SecurityUtils.getSubject().logout();    
        redirectAttributes.addFlashAttribute("message", "您已安全退出");    
		
		Enumeration<String> attributeNames = req.getSession().getAttributeNames();
		
		while(attributeNames.hasMoreElements()){
			req.getSession().removeAttribute(attributeNames.nextElement());
		}
		
		req.getSession().invalidate();
		
		
		return "redirect:/system/login.jsp";
	}
	
	@RequestMapping("/loginout_mobile")
	public String loginout_mobile(HttpServletRequest req,RedirectAttributes redirectAttributes){
	
		WkrjUser user = (WkrjUser) req.getSession().getAttribute("user");
		if(null==user){
			return "redirect:/system/mobile_login.jsp";
		}
		
		//使用权限管理工具进行用户的退出，跳出登录，给出提示信息  
        SecurityUtils.getSubject().logout();    
        redirectAttributes.addFlashAttribute("message", "您已安全退出");    
		
		@SuppressWarnings("unchecked")
		Enumeration<String> attributeNames = req.getSession().getAttributeNames();
		
		while(attributeNames.hasMoreElements()){
			req.getSession().removeAttribute(attributeNames.nextElement());
		}
		
		req.getSession().invalidate();
		
		return "redirect:/system/mobile_login.jsp";
	}
	
	/**
	 * 权限判断
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="permissionsCheck")
    @ResponseBody
	private AjaxJson permissionsCheck(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		AjaxJson json = new AjaxJson();
		
		String url = request.getParameter("url");
		
		//权限校验。判断是否包含权限。
		//具体响应MyRealm。doGetAuthorizationInfo，判断是否包含此url的响应权限
		int userCountType=0;
    	
    	
    	if(null!=request.getSession().getAttribute("userCountType")){
    		userCountType = (Integer) request.getSession().getAttribute("userCountType");
    	}
    	
    	if(1==userCountType){
    		json.setMsg("用户超级管理员权限");
    		json.setSuccess(true);
    	}else{
			boolean isPermitted = SecurityUtils.getSubject().isPermitted(url);
			if(isPermitted) {
				json.setSuccess(true);
			}else{
				json.setMsg("没有权限");
			}
		}
		
		return json;
	}
	
	
	@RequestMapping("updatepassword")
	@ResponseBody
	public AjaxJson updatepassword(String oldpwd,String newpwd,HttpServletRequest request){
		WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
		String userId = user.getUser_id();
		
		AjaxJson json = new AjaxJson();
		
		if(MD5.MD5Encode(oldpwd).equals(user.getUser_password())){
			
			if (wkrjLonginService.updatePassword(userId, MD5.MD5Encode(newpwd))) {
				
				json.setMsg("修改成功");
				json.setSuccess(true);
				
				return json;
			}
		}else {
			
			json.setMsg("原密码不正确");
			json.setSuccess(false);
			
			return json;
		}
		
		return json;
	}
	
}
