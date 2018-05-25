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
            temp_manager = $("#newsOpRecord_maingrid").ligerGrid({
		        url:'wkrjsystem/newsManage/checkOpList?news_id='+dialogData.content.news_id,
		        columns: [
		        { display: '签收人', name: 'op_signuser', id: 'op_signuser', width: '20%', align: 'center', render:getSignUserName },
		        { display: '签收时间', name: 'op_signtime', id: 'op_signtime', width: '30%', align: 'center' },
		        { display: '查看人', name: 'op_lookuser', id: 'op_lookuser', width: '20%', align: 'center', render:getLookUserName },
		        { display: '查看时间', name: 'op_looktime', id: 'op_looktime',width: '30%', align: 'center' }
		        ], height: 385,
		        width : 575,
		        usePager :true,
		        rownumbers : true,
		        alternatingRow: true
		    });
        });
        
        function getSignUserName(rowdata, rowindex, value){
    
		    var username="";
		    
		    $.ajax({  
		        url : "wkrjsystem/newsManage/getReceiverById",  
		        async : false, // 注意此处需要同步，因为返回完数据后，下面才能让结果的第一条selected  
		        type : "POST",
		        data:{userId:rowdata.op_signuser},
		        dataType : "json",  
		        success : function(res) {  
		            if(res.obj){
		                username = res.obj;
		            }
		        }  
		    });
		    
		     return username;
		}
		
		function getLookUserName(rowdata, rowindex, value){
		    
		    var username="";
		    if (rowdata.op_lookuser != null && rowdata.op_lookuser != "") {
		        $.ajax({
		            url : "wkrjsystem/newsManage/getLookUserName",
		            async : false, // 注意此处需要同步，因为返回完数据后，下面才能让结果的第一条selected  
		            type : "POST",
		            data:{userId:rowdata.op_lookuser},
		            dataType : "json",
		            success : function(res) {
		                if(res.obj){
		                    username = res.obj;
		                }
		            }  
		        });
		    }
		    
		    return username;
		}

</script>
</head>
<body >
<div id="newsOpRecord_layout" style="width:560px">	
	<div position="center">
		<div id="newsOpRecord_maingrid"></div>
	</div>
</div>
</body>
</html>