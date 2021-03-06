<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<%@include file="/systemdev/myContext.jsp" %>
 <head>
<script type="text/javascript" src="page/sjc/newsManage/newsManage.js"></script>
<script type="text/javascript">
        $(function (){
            var dialog = frameElement.dialog;
            var dialogData = dialog.get('data');//获取data参数
            temp_manager = $("#newsManageFile_maingrid").ligerGrid({
		        url:'wkrjsystem/newsManage/checkFileList?news_id='+dialogData.content.news_id,
		        columns: [
		        { display: '附件名称', name: 'file_yname', id: 'file_yname', width: '35%', align: 'center' },
		        { display: '上传人账号', name: 'user_name', id: 'user_name', width: '30%', align: 'center' },
		        { display: '上传时间', name: 'file_inputtime', id: 'file_inputtime', width: '35%', align: 'center' }
		        ], height: 385,
		        width : 575,
		        usePager :true,
		        rownumbers : true,
		        alternatingRow: true,
		        toolbar : {
		            items : [ {
		                text : '删除',
		                click : delnewsManageFile,
		                icon : 'delete'
		            },{
		                text : '预览',
		                click : previewFile,
		                icon : 'prev'
		            }, {
		                text : '下载',
		                click : down_load,
		                icon : 'modify'
		            } ]
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
<div id="newsManageFile_layout" style="width:560px">	
	<div position="center">
		<div id="newsManageFile_maingrid"></div>
	</div>
</div>
<a id="sjcpopwindow" href="#" style="display:none;" target="_blank"></a>
<a id="zxhpopwindow" href="#" style="display:none;" target="_blank"></a>
</body>
</html>