<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
 </head>
 <body>
<script type="text/javascript"
   src="page/sjld/headmasterreading/js/messagelist.js"></script>
   <script src="plug-in/uploadify/jquery.uploadify.min.js" type="text/javascript"></script>
 <link rel="stylesheet" type="text/css" href="plug-in/uploadify/uploadify.css">
 <script type="text/javascript" src="plug-in/uploadify/jquery.uploadify.js"></script>
<script type="text/javascript">
	$(function() {
		var dialog = frameElement.dialog;
		var dialogData = dialog.get('data');//获取data参数
		
		var r= dialogData.content;
		
		if(r.file_yname==''||r.file_yname==undefined||r.file_yname==null){
			$("#fujian").hide();
	    }else{
	    	$("#fujian").show();
	    }
		
		$("#readcase_updateWindow_form").ligerForm().setData(dialogData.content);
	});
</script>

<form id="readcase_updateWindow_form" method="post" class="liger-form">
	<div style="overflow:hidden;">
		<div class="Co">
            <label>标题:</label>
			<input type="text" name="must_read_title" id="must_read_title" />
            <label>案例类型:</label>
			<input type="text" id="event_type" name="event_type" disabled="disabled"/>
        </div>
        <div class="Co">
           <label>内容:</label>
            <div class="r_div">
                <textarea id="case_content" name="case_content"  style="height:380px;width:700px;left:-50px;border:0px;"></textarea>
            </div>
        </div>
        
         <div class="Co" id="fujian">
            <label>&nbsp;&nbsp;附件:</label>
            <div class="r_div">
                <input type="file" name="readcase_file" id="uploadify" />
            </div>
                <input type="hidden" name="file_yname" id="file_yname" />
                <input type="hidden" name="file_xname" id="file_xname" />
           </div>
		
		<input type="hidden" name="must_read_id"/>
	</div>
</form>
 </body>
 <script type="text/javascript">
		$("#uploadify").uploadify({
			'buttonText' : '请选择',
			'height' : 30,
			'swf' : 'plug-in/uploadify/uploadify.swf',
			'uploader' : '../../../informExtract/uploadFile',
			'width' : 120,
			'auto' : true,
			'multi' : true,//多选
			'fileTypeExts' : '*.jpg;*.jpeg;*.png;*.xls;*.xlsx;*.doc;*.docx',
			'formData' : {
				file_index : 0
			},
			'fileObjName' : 'readcase_file',
			'onUploadSuccess' : function(file, data, response) {
				var d = eval('(' + data + ')');
				d = eval('(' + d + ')');
				var filename = d.filename;
				var yFileName = d.yFileName;
				$("#file_xname").val(filename + "," + $("#file_xname").val());
				$("#file_yname").val(yFileName + "," + $("#file_yname").val());
            
				top.$(".l-window-mask").hide();
			},
			'removeCompleted' : false
		});
	</script>
 </html>