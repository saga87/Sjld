<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<%@include file="/systemdev/myContext.jsp" %>
 <head>
<script type="text/javascript" src="page/sjld/viewstatistics/js/viewstatistics.js"></script>

</head>
<body style="overflow:hidden;">
 <div id="tab" style="height:100%">

 <div title="案例评论排行"  tabid='tab1' >
   <div id="viewstatistics_layout" style="width:100%;">	
	<div position="center">
	<div style="height:20px; overflow:hidden;padding:5px 0">
		<div style="margin-top:2px;margin-bottom:5px;">&nbsp;&nbsp;案例总数：<span id = "caseTotal"></span></div>
    </div>
		<div id="viewstatistics_maingrid"></div>
	</div>
   </div>

</div>

<div title="校长留言统计排行"  tabid='tab2'>
   <div id="xzliuyan_layout" style="width:100%;">	
	<div position="center">
		<div id="xzliuyan_maingrid"></div>
	</div>
   </div>
</div>
<div title="县市区留言统计排行"  tabid='tab3'>
   <div id="xs_layout" style="width:100%;">	
	<div position="center">
		<div id="xs_maingrid"></div>
	</div>
   </div>

</div>

</div>
</body>
</html>
