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
		
		
	lay=$("#comment_layout").ligerLayout({rightWidth:360,isRightCollapse: true});
	
	manager = $("#comment_maingrid").ligerGrid({
		url:'messageManage/getList?must_read_id='+r.must_read_id,
        columns: [
        { display: '评论者', name: 'commenter', id: 'commenter', width: '8%', align: 'center' },
        { display: '学校', name: 'school', id: 'school', width: '20%', align: 'center' },
        { display: '评论内容', name: 'comment_content', id: 'comment_content', width: '72%', align: 'center' },
        ], height: '370px',
        width:'100%',
        usePager :true,
        pageSize:"10",//分页页面大小
        pageSizeOptions:[5,10],//可指定每页页面大小
		rownumbers : true,
        alternatingRow: true
    });
		
		
		
		
	});
</script>

<form id="readcase_commentWindow_form" method="post" class="liger-form">
	<div style="overflow:hidden;">
		<div style = "padding: 30px">
		<h1  id = "title" name="title" style="text-align: center;margin-top: 5px"></h1>
		<h3>投诉内容:</h3>
		<p id="tsnr" style="margin-top: 5px;font-size: 16px;word-wrap: break-word;"></p>
		
		</div>
		
		<div id="comment_layout" style="height:380px;">	
		<div id="comment_maingrid"></div>
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