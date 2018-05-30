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
		                text : '预览',
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
		<div id="readcaseFile_maingrid"></div>
	</div>
</div>
<a id="sjcpopwindow" href="#" style="display:none;" target="_blank"></a>
<a id="zxhpopwindow" href="#" style="display:none;" target="_blank"></a>
</body>
</html>