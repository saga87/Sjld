<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
 <!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
<script type="text/javascript" src="system/js/user/user.js"></script>
<script type="text/javascript" src="plug-in/jquery/jquery.form.min.js"></script>
<script type="text/javascript">
$(function (){
	var dialog = frameElement.dialog;
	
    $("#user_department_id").ligerComboBox({
       width:300,
       height:30,
       selectBoxWidth:300,
       selectBoxHeight:200, valueField: 'id', treeLeafOnly: false, isMultiSelect: false,
       tree: { 
     	  checkbox: false,
     	  url: 'wkrjsystem/wkrjDept/getDeptTree',
     	  ajaxType: 'post',
     	  idField: 'id',
     	  idFieldName: 'text',
     	  nodeWidth: 150 
     	}
    });
    
	$("#btn_import_school").ligerButton({
        icon: 'plug-in/ligerui/ligerUI/skins/icons/true.gif',
    	click: function (){
    		$("#div_info").html("正在导入，请稍等...");
    		$("#div_info").show();    		
    		var form = $("#user_import_school_form");
			var options  = {
				url:'wkrjsystem/schoolManage/importSchool',
				type:'post',
				success:function(data){
					try {
						data=eval('('+data+')');
					} catch (e) {}
					if(data.success){
						top.$.ligerDialog.alert("导入成功！");
						dialog.close();
					}else{
						$("#div_info").html('<font color="red">导入信息有错误，请看按钮下方提示！</font>');
						$("#div_error").html(data.msg);
						$("#div_error").show();
					}
	            }
			};
	        form.ajaxSubmit(options);
    	}
	});
});
</script>
 </head>
 <body>
<!--添加用户开始-->
<form id="user_import_school_form" method="post"
	enctype="multipart/form-data">
	<div class="formdiv">
		<label style="text-align:right;">选择Excel:</label>
	    <input name="filenames" id="filenames" type="file" accept="application/vnd.ms-excel" />
	</div>
	<center style="margin:10px;display:none;" id="div_info"><font color="green">正在导入，请稍等...</font></center>
	<center>
		<div id="btn_import_school">开始导入</div>
	</center>
	<div id="div_error" style="display:none;margin-left:10px;"></div>
</form>
 </body>
 </html>