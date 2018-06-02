<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<%@include file="/systemdev/myContext.jsp" %>
 <head>
<script type="text/javascript" src="page/sjld/headmasterreading/js/informextract.js"></script>

</head>
<body style="overflow:hidden;">
<div id="informExtract_layout" style="width:100%;">	
	<div position="center">
	<div style="height:90px; overflow:hidden;padding:5px 0">
	<div style="margin-top:5px">&nbsp;&nbsp;编&nbsp;号：<input type="text" id="event_no" style="width:300px"/></div>
	  <div style="margin-top:5px">&nbsp;&nbsp;标&nbsp;题：<input type="text" id="event_title" style="width:300px"/></div>
	  <div style="margin-top:5px">&nbsp;&nbsp;内&nbsp;容：<input type="text" id="event_content" style="width:300px">
      	<a href="javascript:void(0)"
                        iconCls="icon-search" plain="true"
                        style="text-decoration:none"
                        onclick="messages()">&nbsp;&nbsp;查询</a>
      </div>              
                     
     </div>
		<div id="informExtract_maingrid"></div>
	</div>
</div>
</body>
</html>
