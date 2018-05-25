<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
<script type="text/javascript" src="system/js/user/user.js"></script>
<script type="text/javascript">
$(function (){

   
});
</script>
</head>
<body style="overflow:hidden;">
<a href="page/user/school.xls" id="school_a_id" target="_blank" style="display: none;"></a>
<div class="l-loading" style="display:block" id="pageloading"></div>
    <div id="user_layout" style="width:100%;">
	    <div position="center">
		    <div style="height:31px;">    
		    管理员姓名:
		    		<input class="" type="text" id="searchUserRealname"
	               		   style="width:160px;margin-top:4px;margin-left:3px;" />
	          
	            <a id="sea_a" href="javascript:void(0)"
	                   ekper="wkrjsystem/wkrjUser/getUserList"
	                   class="" iconCls="icon-search" plain="true"
	                   onclick="searchUser()">查询</a>
	            
	        </div>
	        <div id="user_maingrid"></div>
	    </div>
        <div position="left" left="150px" >
            <ul id="user_dept_tree"></ul> 
        </div> 
    </div>
    <style>.l-text-wrapper{left:300px;top:-22px;}
    	#sea_a{position:relative;left:610px;top:-43px;z-index:3;}
    </style>
</body>
</html>