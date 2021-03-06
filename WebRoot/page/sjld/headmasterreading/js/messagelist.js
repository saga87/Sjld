var url="";
var lay;
var manager;
var perm;
$(function(){

	lay=$("#messagelist_layout").ligerLayout({rightWidth:560,isRightCollapse: true});
	
	manager = $("#messagelist_maingrid").ligerGrid({
		url:'messageList/getMessageList',
        columns: [
//        { display: '编号', name: 'must_read_id', id: 'must_read_id', width: '20%', align: 'center' },
        { display: '标题', name: 'must_read_title', id: 'must_read_title', width: '20%', align: 'center' },
        { display: '内容', name: 'case_content', id: 'case_content', width: '45%', align: 'center' },
        { display: '类型', name: 'event_type', id: 'event_type', width: '10%', align: 'center' },
        { display: '添加时间', name: 'addtime', id: 'addtime', width: '10%', align: 'center' },
        { display: '浏览量', name: 'pageview', id: 'pageview', width: '10%', align: 'center' },
        { display: '附件', name: 'file_yname', id: 'file_yname', width: '5%', align: 'center',render:function(rowdata, rowindex, value){
            if(value!=null && value!=""){
                return "有";
            }else{
            	return "无";
            }
            
             } },
        ], height: '100%',
        width:'99.9%',
        usePager :true,
		rownumbers : true,
        alternatingRow: true,
        fixedCellHeight:false,
		toolbar : {
			items : [  {
				text : '编辑',
				click : cr_edit,
				icon : 'modify',
				id:'messageList/updateCaseRead'
			}, {
				line : true
			}, {
				text : '删除',
				click : cr_delRow,
				icon : 'delete',
				id:'messageList/deleteCaseRead'
			}, 
//			{
//				text : '浏览',
//				click : viewFile,
//				icon : 'search',
//				id:'messageList/getCaseReadFile'
//			}, 
			{
				text : '评论',
				click : cr_comment,
				icon : 'search',
				id:'messageList/commentCaseRead'
			}]
		}
    });
	
});

function cr_comment(){
	var g = $("#messagelist_maingrid").ligerGetGridManager();
	var r = g.getSelectedRow();
	if (r == undefined)	{
		$.ligerDialog.alert('请选择一条记录进行评论!');
		return;
	}
	
	
	$.ajax({
        url: "messageList/addPageviews",
        data: {must_read_id:r.must_read_id},
        dataType : "json",  
        type : "POST",
        success: function(res){}
         });
	
	
	var s = parent.$.ligerDialog.open({
		url : "page/sjld/headmasterreading/comment.jsp",
		width : 900,
		height :600,
		data: {
            content:r
        },
		buttons : [ {
			text : '评论',
			onclick : function(item, dialog) {
				var data = dialog.frame.liger.get("readcase_commentWindow_form").getData();
				if (data.must_read_title == null || data.must_read_title == "") {
					top.$.ligerDialog.alert("标题不能为空");
					return;
				}else if (data.comment_content == null || data.comment_content == "") {
					top.$.ligerDialog.alert("评论内容不能为空");
					return;
				}
				
//				data.must_read_title = data.title;
				
				
				$.ajax({
		            url: "messageList/commentCaseRead",
		            data: data,
		            dataType : "json",  
		            type : "POST",
		            success: function(result){
		                    try{
		                        result = eval('('+result+')');
		                    }catch(e){
		                    }
		                    if (result.success) {
		                    	$.ligerDialog.alert(result.msg);
		                    //	g.loadData();
		                        dialog.close();
		                    } else {
		                    	top.$.ligerDialog.alert(result.msg);
		                    }
		                 }
		           });
			}
		}, {
			text : '取消',
			onclick : function(item, dialog) {
				dialog.close();
			}
		} ]

	});
	
}




function cr_edit(){
	var g = $("#messagelist_maingrid").ligerGetGridManager();
	var r = g.getSelectedRow();
	if (r == undefined)	{
		$.ligerDialog.alert('请选择一条记录进行修改!');
		return;
	}
	
	$.ajax({
        url: "messageList/addPageviews",
        data: {must_read_id:r.must_read_id},
        dataType : "json",  
        type : "POST",
        success: function(res){}
         });
	
	var s = parent.$.ligerDialog.open({
		url : "page/sjld/headmasterreading/readcase_update.jsp",
		width : 900,
		height :600,
		data: {
            content:r
        },
		buttons : [ {
			text : '确定',
			onclick : function(item, dialog) {
				var data = dialog.frame.liger.get("readcase_updateWindow_form").getData();
				if (data.must_read_title == null || data.must_read_title == "") {
					top.$.ligerDialog.alert("标题不能为空");
					return;
				}else if (data.case_content == null || data.case_content == "") {
					top.$.ligerDialog.alert("内容不能为空");
					return;
				}
				else if (data.event_type == null || data.event_type == "") {
					top.$.ligerDialog.alert("类型不能为空");
					return;
				}
				
				$.ajax({
		            url: "messageList/updateCaseRead",
		            data: data,
		            dataType : "json",  
		            type : "POST",
		            success: function(result){
		                    try{
		                        result = eval('('+result+')');
		                    }catch(e){
		                    }
		                    if (result.success) {
		                    	$.ligerDialog.alert(result.msg);
		                    	g.loadData();
		                        dialog.close();
		                    } else {
		                    	top.$.ligerDialog.alert(result.msg);
		                    }
		                 }
		           });
			}
		}, {
			text : '取消',
			onclick : function(item, dialog) {
				dialog.close();
			}
		} ]

	});
	
}
function cr_delRow(){
	var g = $("#messagelist_maingrid").ligerGetGridManager();
	var r = g.getSelectedRow();
	
	if (r == undefined)	{
		$.ligerDialog.alert('请选择一条记录进行删除!');
		return;
	}
	$.ligerDialog.confirm('确定要删除此记录吗', function (param) {
		if (param) {
			$.ajax({
		        //type: "get",
		        url: 'messageList/deleteCaseRead',
		        data: {must_read_id: r.must_read_id},
		        cache: false,
		        async: false,
		        dataType : "json",  
		        type : "POST",
		        success: function (result) {
		        	try{
		                result = eval('('+result+')');
		            }catch(e){
		            }
		          if (result.success) {
		        	  $.ligerDialog.alert('删除成功!');
		        	  g.loadData();
		          } else {
		        	  if (result.msg != null) {
		        		  $.ligerDialog.alert(result.msg);
		        	  } else {
		        		  $.ligerDialog.alert('删除失败!');
		        	  }
		          }
		        }
		      });
		}
	})
}
function viewFile(){
	var g = $("#messagelist_maingrid").ligerGetGridManager();
	var r = g.getSelectedRow();
	if (r == undefined)	{
		$.ligerDialog.alert('请选择有附件记录的行进行查看!');
		return;
	}
		$.ajax({
        url: "messageList/addPageviews",
        data: {must_read_id:r.must_read_id},
        dataType : "json",  
        type : "POST",
        success: function(res){}
         });
	
	parent.$.ligerDialog.open({
		url : "page/sjld/headmasterreading/readcase_file.jsp",
		width : 600,
		height : 460,
		data: {
            content:r
		},
		buttons : [ {
			text : '取消',
			onclick : function(item, dialog) {
				dialog.close();
			}
		} ]
	});
	
}

function previewFile(){

	var g = $("#readcaseFile_maingrid").ligerGetGridManager();
	var r = g.getSelectedRow();
	if (r == undefined)	{
		$.ligerDialog.alert('请选择一条记录进行预览!');
		return;
	}
	var openwin = document.getElementById("zxhpopwindow");
 	openwin.href = "upload/readcase/"+r.file_xname;
	openwin.click();
}


