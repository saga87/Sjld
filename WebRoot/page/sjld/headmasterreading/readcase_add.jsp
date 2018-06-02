<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
  <script src="plug-in/jquery/jquery.autocompleter.js"></script>
	<link rel="stylesheet" href="plug-in/jquery/main.css">
 </head>
 <body>
 <style type="text/css">
input.tjj{
    background:#585555;
    height:34px;
    width:100px;
    line-height:34px;
    color:#fff;
    font-family:"微软雅黑";
    border:0;
    margin:0 0 0 0px;
    float:left;
    cursor: pointer;
}
input.file{
     width:100px;height:34px;position:absolute;top:0px;left:0px;opacity: 0; filter:Alpha(opacity=1);z-index:999;
}
.picdiv{
    width: 166px;
    float: left;        
}
.picdiv img{
    width: 125px;
    cursor: pointer;
}
.picdiv a{
    color: red;
    margin-top: 30px;
    display: block;
    float: right;
}
.l-form li {
    float: none;
    overflow: auto;
    text-align: inherit;
    line-height: auto;
    padding: 0;
    padding-top: 2px;
    padding-bottom: 2px;
    position: auto;
}
#autocompleter-1,#autocompleter-2{
    margin-left:500px;
    width:244px;
}
</style>
 <script type="text/javascript" src="page/sjld/headmasterreading/js/informextract.js"></script>
 <script src="plug-in/uploadify/jquery.uploadify.min.js" type="text/javascript"></script>
 <link rel="stylesheet" type="text/css" href="plug-in/uploadify/uploadify.css">
 <script type="text/javascript" src="plug-in/uploadify/jquery.uploadify.js"></script>
<script type="text/javascript">
var type_combobox;
var parentID = "6c3d5d3b-17c4-42ac-b465-370b1ff11b2e";
$(function (){
	$.ajax({
                type:'POST',
                url:'eventWf/WkrjEventWf/getDataDictionary?parentID='+parentID,
                dataType:'json',
                success:function(datas){
                     $('#event_type').autocompleter({                
                         minChars:0,
                         // marker for autocomplete matches
                         highlightMatches: true,
                         // object to local or url to remote search
                         source: datas,
                         // custom template
                         template: '{{ label }}',
                         // show hint
                         hint: false,
                         // abort source if empty field
                         empty: true,
                         // max results
                         callback: function (value, index, selected) {
                             if (selected) {
                                 $("#content_type_hidden").val(selected.label);
                             }
                         }
                     });
                    
                }
            });
});
	
</script>

<!--添加通知公告开始-->
<form id="readcase_addWindow_form" method="post" class="liger-form">
	<div style="overflow:hidden;">
		<div class="Co">
			<label>标题:</label>
			<input type="text" name="must_read_title" id="must_read_title" />
			<label>案例类型:</label>
			<input type="text" id="event_type" name="event_type" data-options="method:'post'"/>
			<input type="hidden" id="content_type_hidden">
		</div>
		<div class="Co">
            <label>内容:</label>
            <div class="r_div">
                <textarea id="case_content" name="case_content"  style="height:280px;width:700px;left:-50px;border:0px;"></textarea>
            </div>
        </div>
         
          <div class="Co">
            <label>&nbsp;&nbsp;附件:</label>
            <div class="r_div">
                <input type="file" name="readcase_file" id="uploadify" />
            </div>
                        <input type="hidden" name="file_yname" id="file_yname" />
                        <input type="hidden" name="file_xname" id="file_xname" />
           </div>
		
		<input type="hidden" name="must_read_id"  />
		
	</div>
</form>
<!--添加通知公告结束-->
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