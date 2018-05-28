<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
 </head>
 <body>
 <script type="text/javascript" src="page/sjld/warning/js/noticeannounce.js"></script>
 
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
		
		<div class ="Co">
                    <label style=" display: block; width: 100%;text-align: left;">附件:</label>
                    <div class="nafile"></div>
                    <div style=" margin-left: 100px;"><button type="button" class="layui-btn" id="test3"><i class="layui-icon"></i>上传附件</button></div>
         </div>
		
		<input type="hidden" name="na_id"  />
		<input type="hidden" name="accept_user" id="accept_user" />
	</div>
</form>
<!--添加通知公告结束-->
 </body>
 <script type="text/javascript">
 	layui.use('upload', function(){
 	var $ = layui.jquery  ,upload = layui.upload;
 	//指定允许上传的文件类型
  upload.render({
    elem: '#test3'
    ,url: 'noticeAnnounce/uploadFile'
    ,accept: 'file' //普通文件
    ,multiple: true//可选多个文件
    ,done: function(res){
      if (res.length>0) {
		for (var i = 0; i < res.length; i++) {
			$(".nafile").append('<div class="pdffile" style="margin-left:35px"><span>'+res[i].filename+'.'+res[i].fileextend+'</span>'+
			'<div class="delebook"><i class="layui-icon" style="font-size: 20px; color: #000;background:#d0d0d0;border-radius:5px; margin-right: 11px;cursor: pointer;">&#xe640;</i></div>'+
			' <input type="hidden" name="file_path" value="'+res[i].fileurl+'"><input type="hidden" name="file_name" value="'+res[i].filename+'"><input type="hidden" name="file_type" value="'+res[i].fileextend+'"></div>');
		}
	}
    }
  });
 //删除文件
$('.nafile').on('click','.delebook',function(){
    var path = $(this).parent().find('input').val();
    if(null != path && path != '' && path != "undefined"){
        $.post('noticeAnnounce/deleFile', {  path : path },
            function(data) {
                if (data.success) {
                }else{
                    $.messager.alert('提示','删除失败');
                }
        },"json");
    } 
    $(this).parent().remove();
      
});
 });
 </script>
 </html>