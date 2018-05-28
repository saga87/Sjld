<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@page import="wkrjsystem.user.bean.WkrjUser"%>
<%@page import="wkrjsystemdev.userdev.bean.WkrjUserDev"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	WkrjUser user = (WkrjUser)request.getSession().getAttribute("user");
	WkrjUserDev userDev = (WkrjUserDev)request.getSession().getAttribute("userDev");
	String session_user_name = "";
	String user_id = "";
	String dept_id = "";
	if (user != null) {
	   session_user_name = user.getUser_name();
	   user_id = user.getUser_id();
	   dept_id = user.getDept_id();
	} else {
	   session_user_name = userDev.getUser_name();
	   user_id = userDev.getUser_id();
	   dept_id = userDev.getDept_id();
	}
%>

<head>
<script type="text/javascript">	var session_user_name = "<%=session_user_name%>";</script>
<script type="text/javascript"> var user_id = "<%=user_id%>";</script>
<script type="text/javascript"> var role_id = "3d96ff0e-ccfc-4dc2-8250-1307bf50189d";</script>
<script type="text/javascript"> var role_name = "学校分管理员";</script>
<script type="text/javascript"> var dept_id = "<%=dept_id%>";</script>
<base href="<%=basePath%>"></base>

<link rel="stylesheet" type="text/css" href="system/icons/wkrjicon.css">
<link rel="stylesheet" type="text/css" href="system/css/public.css">
<link href="plug-in/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />  
<link href="plug-in/ligerui/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
<link href="plug-in/ligerui/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />  

<script src="plug-in/ligerui/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>    
<script src="plug-in/ligerui/ligerUI/js/core/base.js" type="text/javascript"></script> 
<script src="plug-in/ligerui/ligerUI/js/ligerui.all.js" type="text/javascript"></script>

<script src="plug-in/ligerui/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>
<script src="plug-in/ligerui/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
<script src="plug-in/ligerui/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
<script src="plug-in/ligerui/jquery.cookie.js"></script>

<script type="text/javascript" src="system/js/main/deve.js"></script>
<script type="text/javascript" src="system/js/main/tab_deve.js"></script>
<script type="text/javascript" src=	"plug-in/ligerui/ligerUI/js/plugins/ligerTree.js"></script>

<script type="text/javascript">
	var urlarr = window.location.href.split("/");
	var bathurl = urlarr[0]+"//"+urlarr[2]+"/"+urlarr[3]+"/";
	/* if(bathurl.indexOf("sddnzz.cn")>=0){
		bathurl = urlarr[0]+"//"+urlarr[2]+"/";
	} */
</script>

<!-- UEditor -->
<link href="plug-in/ueditor/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="plug-in/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="plug-in/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="plug-in/ueditor/lang/zh-cn/zh-cn.js"> </script>

<script type="text/javascript">
	$(function(){
	
		var perms = "${sessionScope.userPermission}";
		var userTypeCount = "${sessionScope.userCountType}";
		var pp ="";
		
		if(1!=userTypeCount){
			setTimeout(function(){
				
			    if(top.$('#mainTabs').length>0){
			        var selectedTabId = top.$('#mainTabs').ligerGetTabManager().selectedTabId;
					pp = top.$('#mainTabs').ligerGetTabManager().tab;
					
					try{
			   			var iframe = pp.find("iframe[id='"+selectedTabId+"']");
			   			
			   			var btns = iframe[0].contentWindow.$(".l-toolbar-item-hasicon");
			   			
						btns.each(function(){
						
							var perm = $(this).attr('toolbarid');
							
							if(typeof(perm)!="undefined"){
								if(perms.indexOf(perm)>=0){
									$(this).show();
								}else{
									$(this).hide();
								}
							}
							
						});
								
					}catch(e){}
				}
				
			}, 100)
			
		} 
		
	});
</script>

</head>
