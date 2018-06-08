<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<%@include file="/systemdev/myContext.jsp" %>
 <head>
<script type="text/javascript" src="page/sjld/headmasterreading/js/messagemanager.js"></script>
<script type="text/javascript">
	$(function() {
		var dialog = frameElement.dialog;
		var dialogData = dialog.get('data');//获取data参数
		var r= dialogData.content;
		$("#view_message_form").ligerForm().setData(dialogData.content);
	});
</script>
</head>
<body>
<form id="view_message_form" method="post" class="liger-form">
	<div style="overflow:hidden;">
		<div class="Co">
            <label>标题:</label>
			<input type="text" name="must_read_title" id="must_read_title" disabled="disabled"/>
            <label>留言人:</label>
			<input type="text" id="commenter" name="commenter" disabled="disabled"/>
        </div>
        <div class="Co">
           <label>内容:</label>
            <div class="r_div">
                <textarea id="comment_content" name="comment_content" disabled="disabled" style="height:380px;width:700px;left:-50px;border:0px;"></textarea>
            </div>
        </div>
 	</div>
 </form>	       
</body>
</html>