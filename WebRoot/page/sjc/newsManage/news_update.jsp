<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
 </head>
 <body>
<script type="text/javascript"
   src="page/sjc/newsManage/newsManage.js"></script>
   
<script type="text/javascript">
       
	$(function() {
		$("#txtContactName").ligerComboBox({
			width : 300,
			height : 30,
			selectBoxWidth : 300,
			selectBoxHeight : 100,
			onBeforeOpen : f_selectContact,
			valueFieldID : 'hidCustomerID'
		});
		
		
		var dialog = frameElement.dialog;
		var dialogData = dialog.get('data');//获取data参数
		$("#newsManage_updateWindow_form").ligerForm().setData(dialogData.content);
		
		var str_userids = dialogData.content.news_lookarea;
		var user = showUserName(str_userids);
		$("#txtContactName").val(user);
		
		var userid = "";
		$.each(str_userids.split(","),function(i,v){
			userid+=v;
			if(i < str_userids.split(",").length - 1){
				userid += ";";
			}
		});
		$("#news_lookarea").val(userid);

		var messagea_ue = UE.getEditor("news_content");
	});
	
	
	function showUserName(value) {
		var userName = "";
		if (value.split(",").length > 0) {
			for (var i = 0; i < value.split(",").length; i++) {
				var username = "";
				$.ajax({
					url : "wkrjsystem/newsManage/getUserNameById",
					async : false, // 注意此处需要同步，因为返回完数据后，下面才能让结果的第一条selected  
					type : "POST",
					data : {
						userId : value.split(",")[i]
					},
					dataType : "json",
					success : function(res) {
						if (res.obj) {
							username = res.obj;
						}
					}
				});

				//return username;
				userName += username;
				if (i < value.split(",").length - 1) {
					userName += ";";
				}
			}
		}
		return userName;
	}
</script>

<!--修改通知公告开始-->
<form id="newsManage_updateWindow_form" method="post" class="liger-form">
	<div style="overflow:hidden;">
		<div class="Co">
            <label><span class="text_red" >*</span>&nbsp;&nbsp;新闻标题:</label>
            <input type="text" name="news_title" />
            <label><span class="text_red" >*</span>&nbsp;&nbsp;查看范围:</label>
            <div class="r_div">
                <input type="text" id="txtContactName" name="txtContactName" />
            </div>
        </div>
        <div class="Co">
            <label><span class="text_red" >*</span>&nbsp;&nbsp;新闻内容:</label>
            <div class="r_div">
                <textarea id="news_content" name="news_content"  style="height:280px;width:700px;left:-50px;border:0px;"></textarea>
            </div>
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;备注:</label>
            <div class="r_div">
                <textarea name="news_other" style="height:50px;width:700px;"></textarea>
            </div>
        </div>
        
        <input type="hidden" name="news_id" id="news_id">
        <input type="hidden" name="news_lookarea" id="news_lookarea">
	</div>
</form>
 </body>
 </html>