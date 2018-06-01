<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
  <style type="text/css">
  	.ulli{
  		clear:both;
  		margin-left:150px;
  	}
  </style>
 </head>
 <body>
<script type="text/javascript"
   src="page/sjld/headmasterreading/js/messagelist.js"></script>
   
<script type="text/javascript">
	$(function() {
		var dialog = frameElement.dialog;
		var dialogData = dialog.get('data');//获取data参数
		var r = dialogData.content;
		$("#readcase_commentWindow_form").ligerForm().setData(dialogData.content);
		$("#title").html(r.must_read_title);
		$("#tsnr").html("&nbsp;&nbsp;"+r.case_content);
		
		$.ajax({
		url:'messageManage/getList',
		type:'post',
		data:{page:1,pagesize:6,must_read_id:r.must_read_id},
		dataType:'json',
		success:function (data) {
		 var lists = data.Rows;
			if (lists.length>0) {
				for (var i = 0; i < lists.length; i++) {
					$("#commentul").append('<li class = "ulli">'+  lists[i].commenter+'&nbsp;&nbsp;'+lists[i].comment_school+'</br>'+lists[i].comment_content+'</li>');
				}
			}
		}
	});
		
		
	});
</script>

<form id="readcase_commentWindow_form" method="post" class="liger-form">
	<div style="overflow:hidden;">
		<div style = "padding: 30px">
		<h1  id = "title" name="title" style="text-align: center;margin-top: 5px"></h1>
		<h3>投诉内容:</h3>
		<p id="tsnr" style="margin-top: 5px;font-size: 16px"></p>
		
		<ul style = "margin-top: 10px;" id="commentul">
			
		</ul>
		</div>
		<div class="Co">
            <div class="r_div">
                <textarea id="comment_content" name="comment_content"  style="height:280px;width:800px;left:-50px;border:0px;"></textarea>
            </div>
          <!-- <input type="button" style="float:right;width:70px;margin-top:10%" value = "提交"/> -->  		
            
        </div>
        
        <input type="hidden" name="must_read_title"/>
		
		<input type="hidden" name="must_read_id"/>
	</div>
</form>
 </body>
 
 </html>