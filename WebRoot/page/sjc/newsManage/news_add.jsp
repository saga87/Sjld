<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
 </head>
 <body>
 <script type="text/javascript" src="page/sjc/newsManage/newsManage.js"></script>
 
<script type="text/javascript">

       $(function (){
            $("#txtContactName").ligerComboBox({
                width : 300, height : 30, selectBoxWidth : 300, selectBoxHeight : 100, 
                onBeforeOpen: f_selectContact//,valueFieldID: 'hidCustomerID'
            });
           //$dialog = top.$("iframe[src='page/sjc/newsManage/news_add.jsp']")[0].contentWindow;//用于关闭dialog
           
            //var messagea_ue=UE.getEditor("news_content");
       });
       
</script>

<!--添加通知公告开始-->
<form id="newsManage_addWindow_form" method="post" class="liger-form">
	<div style="overflow:hidden;">
		<div class="Co">
			<label>新闻标题:</label>
			<input type="text" name="news_title" id="news_title" />
			<label>查看范围:</label>
			<div class="r_div">
				<input type="text" id="txtContactName" name="txtContactName" />
			</div>
		</div>
		<div class="Co">
            <label>新闻内容:</label>
            <div class="r_div">
                <textarea id="news_content" name="news_content"  style="height:280px;width:700px;left:-50px;border:0px;"></textarea>
            </div>
        </div>
		<div class="Co">
			<label>备注:</label>
	        <div class="r_div">
	        	<textarea name="news_other" style="height:50px;width:700px;"></textarea>
	        </div>
		</div>
		
		<input type="hidden" name="news_id"  />
		<input type="hidden" name="news_lookarea" id="news_lookarea" />
	</div>
</form>
<!--添加通知公告结束-->
 </body>
 </html>