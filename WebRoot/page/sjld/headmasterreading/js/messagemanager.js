
var url="";
var lay;
var manager;
var perm;
$(function(){

	lay=$("#messageManage_layout").ligerLayout({rightWidth:560,isRightCollapse: true});
	
	manager = $("#messageManage_maingrid").ligerGrid({
		url:'messageManage/getList',
        columns: [
        { display: '留言者', name: 'commenter', id: 'commenter', width: '8%', align: 'center' },
        { display: '学校', name: 'comment_school', id: 'comment_school', width: '10%', align: 'center' },
        { display: '留言案例标题', name: 'must_read_title', id: 'must_read_title', width: '18%', align: 'center' },
        { display: '留言时间', name: 'comment_time', id: 'comment_time', width: '10%', align: 'center' },
        { display: '留言内容', name: 'comment_content', id: 'comment_content', width: '48%', align: 'center' },
        { display: '点赞量', name: 'likenum', id: 'likenum', width: '5%', align: 'center' },
        ], height: '100%',
        width:'99.9%',
        usePager :true,
		rownumbers : true,
        alternatingRow: true,
		toolbar : {
			items : [ {
				text : '删除',
				click : message_delRow,
				icon : 'delete',
				id:'messageManage/deleteMessage'
			}, {
				text : '点赞',
				click : message_likeRow,
				icon : 'like',
				id:'messageManage/likeMessage'
			}]
		}
    });
    
	
});

function message_likeRow(row){
	var g = $("#messageManage_maingrid").ligerGetGridManager();
	var r = g.getSelectedRow();
	if (r == undefined)	{
		$.ligerDialog.alert('请选择一条记录进行操作!');
		return;
	}
	$.ajax({
        url: 'messageManage/likeMessage',
        data: {must_read_id: r.must_read_id},
        cache: false,
        async: false,
        dataType : "json",  
        type : "POST",
        success: function (result) {
        	$.ligerDialog.alert(result.msg);
      	    g.loadData();
        }
      });
}


function message_delRow(row){
	var g = $("#messageManage_maingrid").ligerGetGridManager();
	var r = g.getSelectedRow();
	if (r == undefined)	{
		$.ligerDialog.alert('请选择一条记录进行删除!');
		return;
	}
	if (r.commenter != session_user_name) {
		top.$.ligerDialog.alert("您不能删除他人留言");
		return;
	}
	
	$.ligerDialog.confirm('确定要删除此记录吗', function (param) {
		if (param) {
			$.ajax({
		        //type: "get",
		        url: 'messageManage/deleteMessage',
		        data: {must_read_id: r.must_read_id},
		        cache: false,
		        async: false,
		        dataType : "json",  
		        type : "POST",
		        success: function (result) {
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

//根据条件检索(查询)
function messages() {
	var g = $("#messageManage_maingrid").ligerGetGridManager();
	var comment_content = $('#comment_content').val();
	var start_date = $('#start_date').val();
	var end_date = $('#end_date').val();
	var must_read_title = $('#must_read_title').val();
	g.set({url:'messageManage/getList?must_read_title='+must_read_title
		+'&comment_content='+comment_content
		+'&start_date='+start_date+'&end_date='+end_date});    
	g.reload();
}

/*
 * 显示全部
 */
function allMessages() {
	var g = $("#messageManage_maingrid").ligerGetGridManager();
	g.set({url:'messageManage/getList'});
    g.reload();
}







