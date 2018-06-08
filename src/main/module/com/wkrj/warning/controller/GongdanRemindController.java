package com.wkrj.warning.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import wkrjsystem.user.bean.WkrjUser;
import wkrjsystem.utils.UtilsHelper;
import wkrjsystemdev.userdev.bean.WkrjUserDev;

import com.wkrj.warning.service.GongdanRemindService;

/**
 * 即将超期与超期 工单提醒
 * @author wlp
 * @Description:
 * @date 2018年6月7日 上午9:10:59
 */
@Controller
@RequestMapping("remind")
public class GongdanRemindController {
	
	@Autowired
	private GongdanRemindService service;
	
	@RequestMapping("getEventOrder")
	@ResponseBody
	public Object getEventOrder(HttpServletRequest request,int page,int pagesize){
		int offset = (page-1)*pagesize;
		List<Map<String, Object>> lists = new ArrayList<Map<String,Object>>();
		WkrjUser user = (WkrjUser) request.getSession().getAttribute("user");
		WkrjUserDev userDev = (WkrjUserDev) request.getSession().getAttribute("userDev");
//		String user_id = "";
		String dept_id = "";
		if (user != null) {
			dept_id = user.getDept_id();
		} else if (userDev != null) {
			dept_id = userDev.getDept_id();
		}
		
//		if("1".equals(user_id)||"58dfbf28-ed18-4d52-a051-ac407c182dcd".equals(user_id) ){
//			user_id = "";
//		}
		
		boolean isGly = false;
		
		 String roleIds = request.getSession().getAttribute("userRoleId")+"";
	        String[] role_ids = roleIds.split(",");
	        for (int i = 0; i < role_ids.length; i++) {
	            //如果是系统管理员
	            if ("2".equals(role_ids[i]) || "7e3d28f7-2e18-4b71-82df-1fa3d15e0e9c".equals(role_ids[i])) {
	                isGly = true;
	                break;
	            }
	        }
		
		
		lists = service.getEventOrder(offset, pagesize,dept_id,isGly);
		return UtilsHelper.returnMap(lists, lists.size());
	}
	
}
