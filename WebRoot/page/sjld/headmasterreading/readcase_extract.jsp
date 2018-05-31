<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
 </head>
 <body>
 <script type="text/javascript" src="page/sjld/headmasterreading/js/informextract.js"></script>
<script type="text/javascript">
	$(function() {
		var dialog = frameElement.dialog;
		var dialogData = dialog.get('data');//获取data参数
		
		$("#readcase_extractWindow_form").ligerForm().setData(dialogData.content);
	});
</script>


<form id="readcase_extractWindow_form" method="post" class="liger-form">
	<div style="overflow:hidden;">
		<div class="Co">
			<label>投诉标题:</label>
			<input type="text" name="event_title" id="event_title" />
			
		</div>
		
		<div class="Co">
		<label>问题类型:</label>
		<input type="text" id="content_type" name="content_type" />
		</div>
		
		<div class="Co">
            <label>内容:</label>
            <div class="r_div">
                <textarea id="event_content" name="event_content"  style="height:280px;width:380px;left:-50px;border:0px;"></textarea>
            </div>
        </div>
         
		<input type="hidden" name="event_no"  />
		
	</div>
</form>
<!--添加通知公告结束-->
 </body>
 </html>