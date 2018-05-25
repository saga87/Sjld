<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>潍坊校园安全管理平台</title>
<!-- <link type="text/css" rel="stylesheet" href="system/css/all.css" /> -->
<script type="text/javascript">	var base = '<%=basePath%>';</script>
<script type="text/javascript" src="system/js/jquery-1.8.3.min.js" ></script>
<script type="text/javascript" src="system/js/login.js" ></script>
<!-- <link rel="stylesheet" type="text/css" href="plug-in/login/css/index.css"/> -->
    <link rel="stylesheet" type="text/css" href="system/css/Login.css" />
</head>
<body>
	   <div class="LoginBg1">
        <img src="plug-in/login/imgs/LoginBg1.jpg" alt="" />
    </div>
    <div class="LoginBar">
        <div class="LoginMain">
            <div class="LoginTit"><img src="plug-in/login/imgs/LoginTit.png" alt="" /></div>
            <div class="LoginStit"><img src="plug-in/login/imgs/LoginStit.png" alt="" /></div>
            <div class="LoginForm">
                <form autocorrect="off" autocapitalize="off" spellcheck="false" >
                        <p class="ComInp user">
                        	<input type="text" class="username"
                        		id="strName" name="username" value="用户名" autocomplete="off" />
                        </p>
                        <p class="ComInp pass">
                        <input type="text" class="pass1" id="strPass1" value="密码" autocomplete="off" />
                        <input type="password" class="pass2" id="strPass2" name="password" value="" autocomplete="off" />
                        </p>
                        <p class="ComInp verif">
                        <input id="verif" type="text" name="yzmin" value="验证码"   />
                        <img src="validateCodeServlet" onclick="reloadImg(this);" title="点击更换一张验证码图片"/>
                        </p>
                    <p class="LoginSub"><input class="sub" type="button" value="登陆" id="longinbutton"  onclick="login()" /><input class="reset" type="reset" value="取消" /></p>
                </form>
            </div>
            <p class="Copy">技术支持：山东潍科软件科技有限公司&nbsp;&nbsp;服务热线：400-668-3609</p>
        </div>
    </div>
    <script>
    $(function(){
    	$("#strPass1").focus(function(){
    		$(this).hide();
    		$("#strPass2").show().focus();
    	})
    	$("#strPass2").blur(function(){
    		if($(this).val()==""){
    			$(this).hide();
    			$("#strPass1").show().val("密码");
    		}
    	})
    })
    </script>
</body>
</html>

