<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<%@include file="/systemdev/myContext.jsp" %>
 <head>
<script type="text/javascript" src="page/sjld/headmasterreading/js/messagemanager.js"></script>
<script type="text/javascript">
 $(function (){
	 $("#start_date").ligerDateEditor({ showTime: false, label: '', labelWidth: 100, labelAlign: 'left' });
	 $("#end_date").ligerDateEditor({ showTime: false, label: '', labelWidth: 100, labelAlign: 'left' });
 });
 </script>
</head>
<body style="overflow:hidden;">
<div id="messageManage_layout" style="width:100%;">	
	<div position="center">
	<div style="height:20px; overflow:hidden;padding:5px 0">
	<div class="fl_s"><input type="text" id="must_read_title" style="float:right;weight:120px"/>&nbsp;&nbsp;案例标题：</div>
	  <div class="fl_s"><input type="text" id="comment_content" style="float:right;weight:120px"/>&nbsp;&nbsp;&nbsp;&nbsp;留言内容：</div>
	  <div class="fl_s " ><input type="text"  id="end_date"/><span style="float:right">~</span><input style="float：right" type="text" id="start_date" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;留言时间:&nbsp;</div>
                     <a href="javascript:void(0)"
                        iconCls="icon-search" plain="true"
                        style="text-decoration:none"
                        onclick="messages()">&nbsp;&nbsp;查询</a>
                     <a href="javascript:void(0)"
                     	style="text-decoration:none"
                        iconCls="icon-search" plain="true"
                        onclick="allMessages()">&nbsp;显示全部</a>
                        </div>
		<div id="messageManage_maingrid"></div>
	</div>
</div>
</body>
</html>
