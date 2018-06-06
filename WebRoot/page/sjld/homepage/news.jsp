<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<%@include file="/systemdev/myContext.jsp" %>

 <head>
 	<script type="text/javascript">
 		<% 
		String na_id = (String)request.getParameter("na_id");
		%>

		var na_id = '<%=na_id%>';
		$(function(){
		$.ajax({
		url:'homepage/news/getNaById',
		type:'post',
		data:{na_id:na_id},
		dataType:'json',
		success:function (data) {
		 if(data.length>0){
		 	var ret = data[0];
		 	$("#title").html(ret.na_title);
		 	$("#lrname").html(ret.user_realname);
		 	$("#lrtime").html(ret.na_inputtime);
		 	$("#content").html(ret.na_content);
		 }
		}
	});
	});
 	</script>
 </head>
 <body>
 	<div style="width:800px;height:700px;padding:20px;border:1px solid #000;margin-top: 10px;margin-left: 10px;" >
 	<h2 id="title" style="text-align: center;"></h2>
 	<p style="margin-top: 10px">录入人:<span id="lrname"></span>&nbsp;&nbsp;录入时间:<span id="lrtime"></span>  </p>
 	<br/>
 	<p>内容:</p>
 	<p id="content" style="margin-top: 5px;margin-left: 15px;font-size: 14px;word-wrap: break-word;"></p>
 	</div>
 </body>