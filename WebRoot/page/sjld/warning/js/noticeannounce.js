
var url="";
var lay;
var manager;
var perm;
$(function(){

	lay=$("#newsManage_layout").ligerLayout({rightWidth:560,isRightCollapse: true});
	
	manager = $("#newsManage_maingrid").ligerGrid({
		url:'noticeAnnounce/getNoticeAnnounceList',
        columns: [
        { display: '公告标题', name: 'na_title', id: 'na_title', width: '12%', align: 'center' },
        { display: '公告内容', name: 'na_content', id: 'na_content', width: '20%', align: 'center' },
        { display: '备注', name: 'na_other', id: 'na_other', width: '10%', align: 'center' },
        { display: '录入时间', name: 'na_inputtime', id: 'na_inputtime', width: '10%', align: 'center' },
        { display: '录入人账号', name: 'na_inputuser', id: 'na_inputuser', width: '10%', align: 'center' },
        { display: '录入人所在部门', name: 'na_inputuserdept', id: 'na_inputuserdept', width: '10%', align: 'center' },
        { display: '可视用户', name: 'accept_user', id: 'accept_user', width: '15%', align: 'center' },
        { display: '重要程度', width: '7%', align: 'center',name: 'importance', id: 'importance'}
        ], height: '100%',
        width:'99.9%',
        usePager :true,
		rownumbers : true,
        alternatingRow: true,
        //tree:{columnId:'newsManage_name',idField:'newsManage_id',parentIDField:'newsManage_parent_id'},
		toolbar : {
			items : [ {
				text : '增加',
				click : na_add,
				icon : 'add',
				id:'noticeAnnounce/addNoticeAnnounce'
			}, {
				line : true
			}, {
				text : '修改',
				click : na_edit,
				icon : 'modify',
				id:'wkrjsystem/newsManage/updateNews'
			}, {
				line : true
			}, {
				text : '删除',
				click : na_delRow,
				icon : 'delete',
				id:'noticeAnnounce/deleteNoticeAnnounce'
			}, {
				text : '查看附件',
				click : checkFileList,
				icon : 'search',
				id:'noticeAnnounce/getNoticeAnnounceFileList'
			}]
		}
    });
    
	
});

/*
 * 查看附件列表
 */
function checkFileList(row){
	var g = $("#newsManage_maingrid").ligerGetGridManager();
	var r = g.getSelectedRow();
	if (r == undefined)	{
		$.ligerDialog.alert('请选择一条记录查看附件!');
		return;
	}
	
	parent.$.ligerDialog.open({
		url : "page/sjld/warning/na_File.jsp",
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
/*
 * 删除附件
 */
function delnewsManageFile(row){
	var g = $("#newsManageFile_maingrid").ligerGetGridManager();
	var r = g.getSelectedRow();
	if (r == undefined)	{
		$.ligerDialog.alert('请选择一条记录进行删除!');
		return;
	}
	$.ligerDialog.confirm('确定要删除此记录吗', function (param) {
		if (param) {
			$.ajax({
		        //type: "get",
		        url: 'wkrjsystem/newsManage/delnewsManageFile',
		        data: { id: r.file_id, xFileName: r.file_xname },
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
		        	  $.ligerDialog.alert('删除失败!');
		          }
		        }
		      });
		}
	})
}

/**
 * 下载附件
 */
function down_load() {
	
	var g = $("#newsManageFile_maingrid").ligerGetGridManager();
	var r = g.getSelectedRow();
	if (r == undefined)	{
		$.ligerDialog.alert('请选择一条记录进行下载!');
		return;
	}
	var openwin = document.getElementById('sjcpopwindow');
 	openwin.href = 'wkrjsystem/newsManage/downloadFile?fileName='+r.file_xname+"&realName="+r.file_yname;
	openwin.click();
}

function previewFile(){

	var g = $("#newsManageFile_maingrid").ligerGetGridManager();
	var r = g.getSelectedRow();
	if (r == undefined)	{
		$.ligerDialog.alert('请选择一条记录进行预览!');
		return;
	}
	var openwin = document.getElementById("zxhpopwindow");
 	openwin.href = "upload/newsManage/"+r.file_xname;
	openwin.click();
}
//根据条件检索(查询)
function news() {
	//var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
	var g = $("#newsManage_maingrid").ligerGetGridManager();
	var news_title = $('#news_title').val();
	var news_inputtime = $('#news_inputtime').val();
	var end_date = $('#end_date').val();
	var notice_inputtime = $("#notice_inputtime").val();
	g.set({url:'wkrjsystem/newsManage/getNewsList?news_title='+encodeURI(news_title)+'&news_inputtime='+news_inputtime+'&end_date='+end_date});    
	g.reload();
}

/*
 * 显示全部
 */
function allNews() {
	var g = $("#newsManage_maingrid").ligerGetGridManager();
	g.set({url:'wkrjsystem/newsManage/getNewsList'});
    g.reload();
}

function newsManage_sign(row){
	var g = $("#newsManage_maingrid").ligerGetGridManager();
	var r = g.getSelectedRow();
	if (r == undefined)	{
		$.ligerDialog.alert('请选择一条记录进行签收!');
		return;
	}
	$.ligerDialog.confirm('确定要签收此记录吗', function (param) {
		if (param) {
			$.ajax({
		        url: 'wkrjsystem/newsManage/signNews',
		        data: { news_id: r.news_id },
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
		        	  $.ligerDialog.alert('签收成功!');
		        	  g.loadData();
		          } else {
		        	  if (result.msg != null) {
		        		  $.ligerDialog.alert(result.msg);
		        	  } else {
		        		  $.ligerDialog.alert('签收失败!');
		        	  }
		          }
		        }
		      });
		}
	})
}

function newsManage_check(row){
	var g = $("#newsManage_maingrid").ligerGetGridManager();
	var r = g.getSelectedRow();
	if (r == undefined)	{
		$.ligerDialog.alert('请选择一条记录进行查看!');
		return;
	}
	//$.ligerDialog.confirm('确定要查看此记录吗', function (param) {
		//if (param) {
		$.ajax({
	        url: 'wkrjsystem/newsManage/checkNews',
	        data: { news_id: r.news_id },
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
	        	  g.loadData();
	        	  parent.$.ligerDialog.open({
	        			url : "page/sjc/newsManage/news_check.jsp",
	        			width : 800,
	        			height : 600,
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
	          } else {
	        	  if (result.msg != null) {
	        		  $.ligerDialog.alert(result.msg);
	        	  } else {
	        		  //$.ligerDialog.alert('查看失败!');
	        	  }
	          }
	        }
	      });
		//}
	//})
}

function na_delRow(row){
	var g = $("#newsManage_maingrid").ligerGetGridManager();
	var r = g.getSelectedRow();
//	if (r.news_inputuser != user_id) {
//		top.$.ligerDialog.alert("只能本人修改");
//		return;
//	}
	if (r == undefined)	{
		$.ligerDialog.alert('请选择一条记录进行删除!');
		return;
	}
	$.ligerDialog.confirm('确定要删除此记录吗', function (param) {
		if (param) {
			$.ajax({
		        //type: "get",
		        url: 'noticeAnnounce/deleteNoticeAnnounce',
		        data: { id: r.na_id },
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
		              //LG.showSuccess('删除成功');
		        	  $.ligerDialog.alert('删除成功!');
		        	  g.loadData();
		          } else {
		              //LG.showError('删除失败！');
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


/**
 * 选择人员
 */
function f_selectContact(){
	var data = {"str_id":$("#news_lookarea").val(),
				"str_name":$("#txtContactName").val()}
	top.$.ligerDialog.open({ 
		title: '选择查看人员',
		data:{
			content:data
		},
		name:'winselector',width: 800, height: 600, 
		url: 'page/sjc/newsManage/selectPerson.jsp', 
		buttons: [{ text: '确定', onclick: f_selectContactOK },
		          { text: '取消', onclick: f_selectContactCancel }
		]
	});
    return false;
}

function f_selectContactOK(item, dialog) {
	var m = dialog.frame;
	var uid = m.getCheckedUser_id();
	var uname = m.getCheckedUser_realname();

	var str = "";
	$.each(uid,function(i,v){
		if(v!=null&&v!=""&&v!=undefined&&v!="undefined"){
			str += v + ";";
		}
	});
	if(str.length>0){
		str = str.substring(0,str.length-1);
	}
	var str_name = "";
	$.each(uname,function(i,v){
		if(v!=null&&v!=""&&v!=undefined&&v!="undefined"){
			str_name += v + ";";
		}
	});
	if(str_name.length>0){
		str_name = str_name.substring(0,str_name.length-1);
	}
	$("#news_lookarea").val(str);
	$("#txtContactName").val(str_name);
	// $("#hidCustomerID").val(data.CustomerID);
	dialog.close();
}

function f_selectContactCancel(item, dialog) {
	dialog.close();
}

/**
 * 添加通知公告
 */
function na_add(){
	//$("#form1").show();
	var g = $("#newsManage_maingrid").ligerGetGridManager();
	parent.$.ligerDialog.open({
		//target: $("#newsManage_addWindow"),
		url : "page/sjld/warning/na_add.jsp",
		width : 900,
		height : 600,
		id:'zzz',
		buttons : [ {
			text : '确定',
			onclick : function(item, dialog) {
				var m = dialog.frame;
				var len = 0;
				//len = m.$("#uploadify").data('uploadify').queueData.queueLength;
				var data = dialog.frame.liger.get("newsManage_addWindow_form").getData();//alert()
				if (data.na_title == null || data.na_title == "") {
					top.$.ligerDialog.alert("公告标题不能为空");
					return;
				}else if (data.na_content == null || data.na_content == "") {
					top.$.ligerDialog.alert("公告内容不能为空");
					return;
				}
//				else if (data.accept_user == null || data.accept_user == "") {
//					top.$.ligerDialog.alert("请选择查看范围");
//					return;
//				}
				var users = data.accept_user;
//				if(dialog.frame.liger.get("txtContactName")){
//					users = dialog.frame.liger.get("txtContactName").getValue();
//				}
				var _user = "";
				var _users = users.split(";");
				for (var i = 0; i < _users.length; i++) {
					_user += _users[i];
		            if (i < _users.length-1) {
		            	_user +=",";
		            }
				}
				data.accept_user = _user;
				
				
				
				var name = dialog.frame.$("input[name='file_name']");
				var path = dialog.frame.$("input[name='file_path']");
				var type = dialog.frame.$("input[name='file_type']");
				var file_name = new Array();
				var file_url = new Array();
				for(var i=0;i<path.length;i++){
					var nm = $(name[i]).attr("value");    //获取value
				    var url = $(path[i]).attr("value");    //获取value
				    var lb = $(type[i]).attr("value");
				    file_url.push(url);
				    file_name.push(nm+"."+lb);
				}
				data.file_name = file_name.join();
			    data.file_path = file_url.join();
				
				
				realAddNews(data,g,dialog);
			}
		}, {
			text : '取消',
			onclick : function(item, dialog) {
				dialog.close();
			}
		} ]
	});
}

/**
 * 真正保存
 * @param {} filename
 * @param {} isupdate
 */
function realAddNews(data,g,dialog){
	 $.ajax({
        url: "noticeAnnounce/addNoticeAnnounce",
        dataType : "json",  
        type : "POST",
        data: data,
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

/**
 * 通知公告信息修改
 */
function na_edit(){
	var g = $("#newsManage_maingrid").ligerGetGridManager();
	var r = g.getSelectedRow();
	if (r == undefined)	{
		$.ligerDialog.alert('请选择一条记录进行修改!');
		return;
	}
	if (r.news_inputuser != user_id) {
		top.$.ligerDialog.alert("只能本人修改");
		return;
	}
	var s = parent.$.ligerDialog.open({
		//target: $("#newsManage_updateWindow_form"),
		url : "page/sjc/newsManage/news_update.jsp",
		//data : JSON.stringify(r),
		width : 900,
		height :600,
		data: {
            content:r
        },
		buttons : [ {
			text : '确定',
			onclick : function(item, dialog) {
				console.dir(dialog);
				
				var data = dialog.frame.liger.get("newsManage_updateWindow_form").getData();
				if (data.news_title == null || data.news_title == "") {
					top.$.ligerDialog.alert("公告标题不能为空");
					return;
				}else if (data.news_content == null || data.news_content == "") {
					top.$.ligerDialog.alert("公告内容不能为空");
					return;
				}else if (data.news_lookarea == null || data.news_lookarea == "") {
					top.$.ligerDialog.alert("请选择查看范围");
					return;
				}
				//data['user[0].user_id'] = dialog.frame.liger.get("news_lookarea").getValue();
//				if(dialog.frame.liger.get("news_lookarea")){
//					var news_lookarea = dialog.frame.liger.get("news_lookarea").getValue();
//					data.news_lookarea = news_lookarea;
//				}
				var users = data.news_lookarea;
				var _user = "";
				var _users = users.split(";");
				for (var i = 0; i < _users.length; i++) {
					_user += _users[i];
		            if (i < _users.length-1) {
		            	_user +=",";
		            }
				}
				data.news_lookarea = _user;
				//data.news_type = dialog.frame.liger.get("news_type").getValue();
				$.ajax({
		            url: "wkrjsystem/newsManage/updateNews",
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

function getBmName(rowdata, rowindex, value){
	
	var deptname="";
	
	$.ajax({  
        url : "wkrjsystem/newsManage/getDeptNameById",  
        async : false, // 注意此处需要同步，因为返回完数据后，下面才能让结果的第一条selected  
        data:{deptId:rowdata.news_inputuserdept},
        dataType : "json",  
        type : "POST",
        success : function(res) {  
        	if(res.obj){
           		deptname = res.obj.dept_name;
           	}
        }  
    });
    
     return deptname;
}

function getUserName(rowdata, rowindex, value){
	
	var username="";
	
	$.ajax({
        url : "wkrjsystem/newsManage/getUserNameById",
        async : false, // 注意此处需要同步，因为返回完数据后，下面才能让结果的第一条selected
        type : "POST",
        data:{userId:rowdata.news_lookarea},
        dataType : "json",
        success : function(res) {
        	if(res.obj){
           		username = res.obj;
           	}
        }
    });
    
    return username;
}

function transferType(rowdata, rowindex, value) {
	if (rowdata.news_type == 1) {
		return '信息公告';
	} else if (rowdata.news_type == 2) {
		return '视频案例';
	} else {
		return '其他';
	}
}
function getTempTransfer(rowdata, rowindex, value) {
	var temp = "";
	$.ajax({
	    url: 'wkrjsystem/newsManage/ifCheckNews',
	    data: { news_id:rowdata.news_id,op_lookuser:rowindex.op_lookuser },
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
	    	  temp = "是";
	      } else {
	    	  temp = '<p style="color:#fff;background-color:red;">否</p>';
	      } 
	    }
	  });
	return temp;
}