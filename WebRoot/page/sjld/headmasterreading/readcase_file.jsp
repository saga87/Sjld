<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<%@include file="/systemdev/myContext.jsp" %>
 <head>
<script type="text/javascript" src="page/sjld/headmasterreading/js/messagelist.js"></script>
<script type="text/javascript">
        $(function (){
            var dialog = frameElement.dialog;
            var dialogData = dialog.get('data');//获取data参数
            
            var rr = dialogData.content;
            
            $("#must_read_title").html(rr.must_read_title);
            $("#event_type").html(rr.event_type);
            $("#case_content").html('&nbsp;&nbsp;&nbsp;'+rr.case_content);
            
            if(rr.file_yname==''||rr.file_yname==undefined||rr.file_yname==null){
				$("#readcaseFile_maingrid").hide();
				return;
			}else{
				$("#readcaseFile_maingrid").show();
			}
            
            
            temp_manager = $("#readcaseFile_maingrid").ligerGrid({
		        url:'messageList/getCaseReadFile?must_read_id='+dialogData.content.must_read_id,
		        columns: [
		        { display: '附件名称', name: 'file_yname', id: 'file_yname', width: '100%', align: 'center' },
		        ], height: 385,
		        width : 575,
		        usePager :true,
		        rownumbers : true,
		        alternatingRow: true,
		        toolbar : {
		            items : [ {
		                text : '预览附件',
		                click : previewFile,
		                icon : 'prev'
		            }]
		        }
		    });
       });
        
</script>
</head>
<body >
<div id="readcaseFile_layout" style="width:560px">	
	<div position="center">
		
		<h1 id = "must_read_title" name="must_read_title" style="text-align: center;margin-top: 5px"></h1>
       
        <h3 style="margin-top: 5px;margin-left: 5px">
        <label>案例类型:</label>
		<span id = "event_type" name="event_type"></span>
        </h3>
        
        <h3 style="margin-top: 5px;margin-left: 5px">投诉内容:</h3>
		<p id="case_content" style="margin-top: 5px;margin-left: 15px;font-size: 14px;word-wrap: break-word;"></p>
	
		<div id="readcaseFile_maingrid" style="margin-top: 15px"></div>
	</div>
</div>
<a id="sjcpopwindow" href="#" style="display:none;" target="_blank"></a>
<a id="zxhpopwindow" href="#" style="display:none;" target="_blank"></a>
</body>
</html>