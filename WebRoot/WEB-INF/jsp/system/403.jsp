<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<!-- <html>  
  <head>  
    <title>权限错误</title>  
  </head>  
    
  <body>  
    <h1>对不起，您没有权限请求此连接！</h1>  
  </body>  
</html>   -->
<html> 
<head><title>Exception!</title></head> 
<body> 
<% Exception ex = (Exception)request.getAttribute("exception"); %> 
<H2>Exception: <%= ex.getMessage()%></H2> 
<P/> 
<h1>对不起，您没有权限请求此连接！</h1>  
</body> 
</html>