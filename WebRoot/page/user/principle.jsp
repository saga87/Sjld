<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
<script type="text/javascript" src="system/js/user/principle.js"></script>

</head>
<body style="overflow:hidden;">
<div class="l-loading" style="display:block" id="pageloading"></div>
    <div id="user_layout" style="width:100%;">
	    <div position="center">
<div style="height:10%; overflow:hidden;padding:5px 0">
	<div style="margin-top:5px">&nbsp;&nbsp;姓&nbsp;&nbsp;名：<input type="text" id="userName" style="width:200px"/></div>
	  <div style="margin-top:5px">&nbsp;&nbsp;学&nbsp;&nbsp;段：<input type="text" id="school_name" style="width:200px"/></div>
	  <div style="margin-top:5px;margin-bottom: 10px">&nbsp;&nbsp;县市区：<input type="text" id="counties" style="width:200px"/>
      <a href="javascript:void(0)"
                        iconCls="icon-search" plain="true"
                        style="text-decoration:none"
                        onclick="searchPrincipal()">&nbsp;&nbsp;查询</a>
      </div>
     
	        
	    
	    </div>
	    <div id="user_maingrid"></div>
    </div>
   </div>
</body>
</html>