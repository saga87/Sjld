<%@page import="wkrjsystem.user.bean.WkrjUser"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	WkrjUser user = (WkrjUser)request.getSession().getAttribute("user");
	String session_user_deptid = user.getDept_id();
	//String session_user_roler = user.getUser_role().get(0).getRole_name();
	String session_user_name = user.getUser_realname();
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head >
<meta http-equiv="X-UA-Compatible" content="IE=10"/>
<base href="<%=basePath%>"></base>
<title>潍坊市校园安全管理平台</title>

<script type="text/javascript">	var session_user_deptid = "<%=session_user_deptid%>";</script>
<script type="text/javascript">	var session_user_roler = "<%=session.getAttribute("userRoleId")%>";</script>
<script type="text/javascript" src="plug-in/load_user_js_css.js"></script>


<script type="text/javascript">
	
	var tab;
	$(function(){
		
	});
	
	$(".tabs li").live('contextmenu',function(e){
		        
		        /* 选中当前触发事件的选项卡 */
		        var subtitle =$(this).text();
		        $('#mainTabs').tabs('select',subtitle);
		        
		        //显示快捷菜单
		        $('#menu').menu('show', {
		            left: e.pageX,
		            top: e.pageY
		        });
		        
		        return false;
	});
	
	$(function(){
		
		var perms = "${sessionScope.userPermission}";
		//alert(perms);
		//function extjsbutton(v,b){
			//alert(v);
			//if(v!=null && b!=null){
				//if(permission.indexOf(b.ekper)<0){
				//alert("111");
					//b.setDisabled(true);	
					//b.setVisible(false);	
				//}
			//}
		//}
		
	});
	
</script>
<style type="text/css">
/*首页样式修改*/
.l-layout-top{border:0px;}

</style>
</head>
<body style="overflow:hidden;">
	
	 <div id="main_layout" style="width:99.9%;overflow:hidden;">
		<div position="top" style="overflow:hidden;background:url(system/imgs/banner_bg.png) repeat-x;">
			<div style="float: left;">
	    		<img src="system/imgs/LoginTit.png" alt="" style="height:70px; margin:10px 0 20px 50px" />
	        </div>
			<div style="width:473px;height:85px;position:absolute;right:0px;z-index:1;background: url(system/imgs/banner_right.png) no-repeat;">
				<img style="position:relative;left:325px;top:25px;cursor:pointer"
					src="system/imgs/001.png" /> <img onClick="password()"
					style="position:relative;left:335px;top:25px;cursor:pointer"
					src="system/imgs/002.png" />
				<a href="wkrjsystem/wkrjlogin/loginout"> <img
					style="position:relative;border:0px;left:345px;top:25px;cursor:pointer"
					src="system/imgs/003.png" /></a>
			</div>
		</div>
		<div position="left" title="<%=session_user_name%>,欢迎您!">
			<div id="leftMenuTree"></div>
		</div>
		<div position="center" id="mainTabs">
			<div tabid="home" title="首页" style="height:200px" >
				<img src="system/imgs/welcome.png"  />
            </div> 
		</div>
	</div>
	
	
    
   <!--  <div id="passwordWindow" class="easyui-dialog" modal="true"
		style="width: 330px; height: 155px; padding: 10px 20px" closed="true"
		buttons="#password-buttons">
		<form id="passwordForm" method="post" novalidate>
			<div class="fitem">
				<label style="text-align:right;">旧密码:</label> <input name="oldpwd"
					type="password" style="width: 212px;" class="easyui-validatebox">
			</div>
			<div class="fitem">
				<label style="text-align:right;">新密码:</label> <input name="newpwd"
					type="password" style="width: 212px;" class="easyui-validatebox">
			</div>
		</form>
	</div>
	<div id="password-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="updatePassword()">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel"
			onclick="javascript:$('#passwordWindow').dialog('close')">关闭</a>
	</div> -->

	<!-- <div id="menu" class="easyui-menu" style="width:150px;">
	    <div id="m-closeall">全部关闭</div>
	    <div id="m-closeother">除此之外全部关闭</div>
	    <div class="menu-sep"></div>
	    <div id="m-close">关闭当前</div>
	</div> -->
	
</body>

<a id="zxhpopwindow" href="#" style="display:none;" target="_blank"></a>


</html>