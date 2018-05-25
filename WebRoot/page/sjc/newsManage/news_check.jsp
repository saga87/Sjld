<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
<script type="text/javascript">
$(function (){
    /* var dialog = frameElement.dialog;
    var dialogData = dialog.get('data');//获取data参数
    $("#news_Window_form").ligerForm().setData(dialogData.content); */
    var data = frameElement.dialog.get('data').content;
        
	$("#news_view_title").html(data.news_title);
	if(data.user_name==null||data.user_name==undefined||data.user_name=="undefined"||data.user_name==""){
		$("#news_view_time").html("录入时间："+data.news_inputtime);
	}else{
		$("#news_view_time").html("录入人："+data.user_name+"&nbsp;&nbsp;&nbsp;&nbsp;录入时间："+data.news_inputtime);
	}
	$("#news_view_content").html(data.news_content);
});
</script>

<style>
body{margin:20px;}
.title{font-size: 26px; text-align: center; font-weight: bold; margin: 15px;}
.line10{border-top: 1px dashed #aaa; height: 10px;}
.time{font-size: 12px; text-align: center; color: #bbb; margin-bottom: 10px;}
</style>
</head>
<body>
	<div id="news_view_title" class="title"></div>
	<div class="line10"></div>
	<div id="news_view_time" class="time"></div>
	<div id="news_view_content"></div>
</body>
</html>