<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
 </head>
 <body>
 <script type="text/javascript" src="page/sjld/warning/js/noticeannounce.js"></script>
 <script src="plug-in/uploadify/jquery.uploadify.min.js" type="text/javascript"></script>
 <link rel="stylesheet" type="text/css" href="plug-in/uploadify/uploadify.css">
 <script type="text/javascript" src="plug-in/uploadify/jquery.uploadify.js"></script>
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
			<label>公告标题:</label>
			<input type="text" name="na_title" id="na_title" />
			<label>查看范围:</label>
			<div class="r_div">
				<input type="text" id="txtContactName" name="txtContactName" />
			</div>
		</div>
		<div class="Co">
            <label>公告内容:</label>
            <div class="r_div">
                <textarea id="na_content" name="na_content"  style="height:280px;width:700px;left:-50px;border:0px;"></textarea>
            </div>
        </div>
		<div class="Co">
			<label>备注:</label>
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
		
		<input type="hidden" name="na_id"  />
		<input type="hidden" name="accept_user" id="accept_user" />
	</div>
</form>
<!--添加通知公告结束-->
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