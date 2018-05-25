<%@page import="wkrjsystemdev.userdev.bean.WkrjUserDev"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String session_user_deptid = "";
	String session_user_roler = "系统管理员";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head >
<base href="<%=basePath%>"></base>
<title>开发管理员-首页</title>

<script type="text/javascript">	var session_user_deptid = "<%=session_user_deptid%>";</script>
<script type="text/javascript">	var session_user_roler = "<%=session_user_roler%>";</script>
<script type="text/javascript">	var session_user_roler_showall = "系统管理员,区县统战管理员,";</script>

<script type="text/javascript" src="plug-in/load_js_css.js"></script>
    
    
    
<script type="text/javascript">
var tab;
$(function(){
	$("#main_layout").ligerLayout({
		leftWidth: 240,
		topHeight: 85,
        space: 4
    });
	
	tab = $("#mainTabs_dev").ligerTab({height:'100%'});
});
</script>
</head>

<body>
	<div id="main_layout" style="width:99.8%;">
		<div position="top" style="overflow: hidden;background:url(system/imgs/banner_bg.png) repeat-x;">
			<div style="float: left;">
	    		<img src="system/imgs/LoginTit.png" alt="" style="height:70px; margin:10px 0 20px 50px"/>
	        </div>
			<div style="width:473px;height:90px;position:absolute;right:0px;z-index:1;background: url(system/imgs/banner_right.png) no-repeat;">
				<img style="position:relative;left:325px;top:25px;cursor:pointer"
					src="systemdev/imgs/001.png" /> <img onClick="password()"
					style="position:relative;left:335px;top:25px;cursor:pointer"
					src="systemdev/imgs/002.png" />
				<a href="wkrjsystemdev/wkrjLoginDev/loginout.wk"> <img
					style="position:relative;border:0px;left:345px;top:25px;cursor:pointer"
					src="systemdev/imgs/003.png" /></a>
			</div>
		</div>
		<div position="left" title="菜单导航栏">
			<div id="leftMenuTree_dev"></div>
		</div>
		<div position="center" id="mainTabs_dev">
			<div tabid="home" title="欢迎您" style="height:200px" >
                欢迎使用
            </div> 
		</div>
	</div>
	

<!-- <div id="menu_dev" class="easyui-menu" style="width:150px;">
    <div id="m-refresh">刷新</div> 
    <div class="menu-sep"></div>
    <div id="m-closeall">全部关闭</div>
    <div id="m-closeother">除此之外全部关闭</div>
    <div class="menu-sep"></div>
    <div id="m-close">关闭当前</div>
</div>

<div id="zz">
	
</div> -->

<a id="zxhpopwindow" href="#" style="display:none;" target="_blank"></a>

</body>
</html>