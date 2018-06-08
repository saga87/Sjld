<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<%@include file="/systemdev/myContext.jsp" %>
 <head>
<script type="text/javascript" src="page/sjld/warning/js/noticeannounce.js"></script>
<script type="text/javascript">
        $(function (){
            var dialog = frameElement.dialog;
            var dialogData = dialog.get('data');//获取data参数
            var r = dialogData.content;
            $("#title").html(r.na_title);
		    $("#tsnr").html("&nbsp;&nbsp;"+r.na_content);
		   
            
            temp_manager = $("#newsManageFile_maingrid").ligerGrid({
		        url:'noticeAnnounce/getNoticeAnnounceFileList?na_id='+dialogData.content.na_id,
		        columns: [
		        { display: '附件名称', name: 'file_yname', id: 'file_yname', width: '50%', align: 'center' },
		        { display: '上传人账号', name: 'uname', id: 'uname', width: '20%', align: 'center' },
		        { display: '上传时间', name: 'file_inputtime', id: 'file_inputtime', width: '30%', align: 'center' }
		        ], height: 485,
		        width : '100%',
		        usePager :true,
		        rownumbers : true,
		        alternatingRow: true,
		        toolbar : {
		            items : [ {
		                text : '预览',
		                click : previewFile,
		                icon : 'prev'
		            },{
		                text : '删除',
		                click : delnewsManageFile,
		                icon : 'delete'
		            }]
		        }
		    });
       });
        function getDeptName(rowdata, rowindex, value){
    
		    var deptname="";
		    
		    $.ajax({  
		        url : "wkrjsystem/newsManage/getDeptNameById",  
		        async : false, // 注意此处需要同步，因为返回完数据后，下面才能让结果的第一条selected  
		        type : "POST",
		        data:{deptId:rowdata.lookuserdept},
		        dataType : "json",  
		        success : function(res) {  
		            if(res.obj){
		                deptname = res.obj.dept_name;
		            }
		        }  
		    });
		    
		    return deptname;
		}
</script>
</head>
<body >
<div id="newsManageFile_layout" >	
	<div position="center">
		<div style = "padding: 30px">
		<h1  id = "title" name="title" style="text-align: center;margin-top: 5px"></h1>
		<h3>投诉内容:</h3>
		<p id="tsnr" style="font-size: 16px;word-wrap: break-word;"></p>
		</div>
	
	
		<div id="newsManageFile_maingrid"></div>
	</div>
</div>
<a id="sjcpopwindow" href="#" style="display:none;" target="_blank"></a>
<a id="zxhpopwindow" href="#" style="display:none;" target="_blank"></a>
</body>
</html>