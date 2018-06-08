<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
 </head>
 <body>
<script type="text/javascript"
   src="page/sjld/warning/js/noticeannounce.js"></script>
   <script src="plug-in/uploadify/jquery.uploadify.min.js" type="text/javascript"></script>
 <link rel="stylesheet" type="text/css" href="plug-in/uploadify/uploadify.css">
 <script type="text/javascript" src="plug-in/uploadify/jquery.uploadify.js"></script>
<script type="text/javascript">
  var  im_combobox;
 var parentID = '4500fa86-8766-4125-9b93-71a23c81e022';     
       
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
		
		im_combobox = $("#importance").ligerComboBox({
                url: 'eventWf/WkrjEventWf/getDataDictionary?parentID='+parentID,
                valueField: 'id',
                textField: 'name',
                width: 240,height: 30,selectBoxWidth: 240,selectBoxHeight: 100
            });
		
		im_combobox.setValue(dialogData.content.importance);
		
		
		var str_userids = dialogData.content.accept_user;
		var user = showUserName(str_userids);
		$("#txtContactName").val(user);
		
		var userid = "";
		$.each(str_userids.split(","),function(i,v){
			userid+=v;
			if(i < str_userids.split(",").length - 1){
				userid += ";";
			}
		});
		$("#accept_user").val(userid);

		var messagea_ue = UE.getEditor("na_content");
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
            <label><span class="text_red" >*</span>&nbsp;&nbsp;公告标题:</label>
            <input type="text" name="na_title" />
            <label><span class="text_red" >*</span>&nbsp;&nbsp;查看范围:</label>
            <div class="r_div">
                <input type="text" id="txtContactName" name="txtContactName" />
            </div>
        </div>
        
        <div class="Co">
            <label>&nbsp;&nbsp;重要程度:</label>
            <input type="text" name="importance" id="importance" />
        </div>
        
        <div class="Co">
            <label><span class="text_red" >*</span>&nbsp;&nbsp;公告内容:</label>
            <div class="r_div">
                <textarea id="na_content" name="na_content"  style="height:280px;width:700px;left:-50px;border:0px;"></textarea>
            </div>
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;备注:</label>
            <div class="r_div">
                <textarea name="na_other" style="height:50px;width:700px;"></textarea>
            </div>
        </div>
        
        
         <div class="Co">
            <label>&nbsp;&nbsp;附件:</label>
            <div class="r_div">
                <input type="file" name="na_file" id="uploadify" />
            </div>
                        <input type="hidden" name="file_yname" id="file_yname" />
                        <input type="hidden" name="file_xname" id="file_xname" />
           </div>
        
        <input type="hidden" name="na_id" id="na_id">
        <input type="hidden" name="accept_user" id="accept_user">
	</div>
</form>
 </body>
 <script type="text/javascript">
	
		$("#uploadify").uploadify({
			'buttonText' : '请选择',
			'height' : 30,
			'swf' : 'plug-in/uploadify/uploadify.swf',
			'uploader' : '../../../noticeAnnounce/uploadFile',
			'width' : 120,
			'auto' : true,
			'multi' : true,//多选
			'fileTypeExts' : '*.jpg;*.jpeg;*.png;*.xls;*.xlsx;*.doc;*.docx',
			'formData' : {
				file_index : 0
			},
			'fileObjName' : 'na_file',
			'onUploadSuccess' : function(file, data, response) {
				var d = eval('(' + data + ')');
				d = eval('(' + d + ')');
				var filename = d.filename;
				var yFileName = d.yFileName;
		//		var file_type = d.fileType;
				$("#file_xname").val(filename + "," + $("#file_xname").val());
				$("#file_yname").val(yFileName + "," + $("#file_yname").val());
		//		$("#file_type").val(file_type + "," + $("#file_type").val());
            
				top.$(".l-window-mask").hide();
				//$("#isupload").val(1);
			},
			'removeCompleted' : false
		});
	</script>
 </html>