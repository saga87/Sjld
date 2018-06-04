function onSelect(note){
    var g = $("#user_maingrid").ligerGetGridManager();
    g.set({url:'wkrjsystem/wkrjUser/getUserList?deptId='+note.data.id});
    g.reload();
}

//根据条件检索
function searchPrincipal() {
	var g = $("#user_maingrid").ligerGetGridManager();
	var userName = $('#userName').val();
	var counties = $('#counties').val();
	var school_name = $('#school_name').val();
	g.set({url:'wkrjsystem/wkrjUser/getPrincipleList?userName='+userName+'&counties='+counties
		+'&school_name='+school_name});
    g.reload();
}


function showAllUser() {
	var g = $("#user_maingrid").ligerGetGridManager();
	g.set({url:'wkrjsystem/wkrjUser/getPrincipleList'});
    g.reload();
}

var url="";
var lay;
var manager;
var perm;
$(function(){
	
	
	lay=$("#user_layout").ligerLayout({leftWidth:220,isRightCollapse: true});

	
	
	manager = $("#user_maingrid").ligerGrid({
		url:'wkrjsystem/wkrjUser/getPrincipleList',
        columns : [
        { display: '校长', name: 'user_realname', id:'user_realname',width: '8%', align: 'center' },
        { display: '学校', name: 'school_name', id:'school_name',width: '25%', align: 'center' },
        { display: '县市区', name: 'counties', id:'counties',width: '8%', align: 'center' },
        { display: '办公电话', name: 'telephone', id:'telephone',width: '9%', align: 'center' },
        { display: '手机', name: 'user_tel', id:'user_tel',width: '9%', align: 'center' },
        { display: '邮箱', name: 'email', id: 'email', width: '15%', align: 'center'}
        ], height : '100%',
        width:'99.9%',
        usePager : true,
		rownumbers : true,
        alternatingRow : true,
		toolbar : {
			items : [ {
				text : '增加',
				click : addPrinciple,
				icon : 'add',
				id:'wkrjsystem/wkrjUser/addUser'
			}, {
				line : true
			}, {
				text : '修改',
				click : editPrincipal,
				icon : 'modify',
				id:'wkrjsystem/wkrjUser/updateUser'
			}, {
				line : true
			}, {
				text : '删除',
				click : delUser,
				icon : 'delete',
				id:'wkrjsystem/wkrjUser/delUser'
			}, {
				text : '显示全部',
				click : showAllUser,
				icon : 'search',
				id:'wkrjsystem/wkrjUser/getPrincipleList'
			}, {
				text : '重置密码',
				click : resetPw,
				icon : 'lock',
				id:'wkrjsystem/wkrjUser/repeatPw'
			}]
		}
    });
	$("#pageloading").hide();
});

//
function downloadTemplete(){
	/*alert("sdfsd");
	this.location.href="page/user/school.xls";*/
	
	var comment = document.getElementById('school_a_id');
	if (document.all) {
		// For IE
		comment.click();
	} else if (document.createEvent) {
		// FOR DOM2
		var ev = document.createEvent('HTMLEvents');
		ev.initEvent('click', false, true);
		comment.dispatchEvent(ev);
	}
}

// 导入学校信息
function importSchool(){
	var g = $("#user_maingrid").ligerGetGridManager();
	parent.$.ligerDialog.open({
		//target: $("#user_addWindow"),
		url : "page/user/import_school.jsp",
		width : 500,
		height : 300,
		buttons : [{
			text : '关闭',
			onclick : function(item, dialog) {
				dialog.close();
			}
		}]
	});
}

// 删除用户
function delUser(row){
	var g = $("#user_maingrid").ligerGetGridManager();
	var r = g.getSelectedRow();
	if (r == undefined)	{
		$.ligerDialog.alert('请选择一条记录进行删除!');
		return;
	}
	$.ligerDialog.confirm('确定要删除此记录吗', function (param) {
		if (param) {
			$.ajax({
		        //type: "get",
		        url: 'wkrjsystem/wkrjUser/delUser',
		        data: { id: r.user_id },
		        cache: false,
		        async: false,
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

//禁用用户
function disableUser(row){
	var g = $("#user_maingrid").ligerGetGridManager();
	var r = g.getSelectedRow();
	if (r == undefined)	{
		$.ligerDialog.alert('请选择要禁用的用户!');
		return;
	}
	$.ligerDialog.confirm('确定要禁用此用户吗', function (param) {
		if (param) {
			$.ajax({
		        //type: "get",
		        url: 'wkrjsystem/wkrjUser/forbiddenUser',
		        data: { id: r.user_id, flag: '1' },
		        cache: false,
		        async: false,
		        success: function (result) {
		        	try{
		                result = eval('('+result+')');
		            }catch(e){
		            }
		          if (result.success) {
		        	  $.ligerDialog.alert('禁用成功!');
		        	  g.loadData();
		          } else {
		        	  $.ligerDialog.alert('禁用失败!');
		          }
		        }
		      });
		}
	})
}

//启用用户
function enableUser(row){
	var g = $("#user_maingrid").ligerGetGridManager();
	var r = g.getSelectedRow();
	if (r == undefined)	{
		$.ligerDialog.alert('请选择要启用的用户!');
		return;
	}
	$.ligerDialog.confirm('确定要启用此用户吗', function (param) {
		if (param) {
			$.ajax({
		        //type: "get",
		        url: 'wkrjsystem/wkrjUser/forbiddenUser',
		        data: { id: r.user_id, flag: '0' },
		        cache: false,
		        async: false,
		        success: function (result) {
		        	try{
		                result = eval('('+result+')');
		            }catch(e){
		            }
		          if (result.success) {
		        	  $.ligerDialog.alert('启用成功!');
		        	  g.loadData();
		          } else {
		        	  $.ligerDialog.alert('启用失败!');
		          }
		        }
		      });
		}
	})
}

//重置密码成123
function resetPw(){
	
	var g = $("#user_maingrid").ligerGetGridManager();
	var r = g.getSelectedRow();
	if (r == undefined)	{
		$.ligerDialog.alert('请选择要重置的用户!');
		return;
	}
	$.ligerDialog.confirm('你确定要重置密码吗,重置后密码变为123', function (param) {
		if (param) {
			$.ajax({
		        //type: "get",
		        url: 'wkrjsystem/wkrjUser/repeatPw',
		        data: { id: r.user_id, flag: '0' },
		        cache: false,
		        async: false,
		        success: function (result) {
		        	try{
		                result = eval('('+result+')');
		            }catch(e){
		            }
		          if (result.success) {
		        	  $.ligerDialog.alert('重置成功!');
		        	  g.loadData();
		          } else {
		        	  $.ligerDialog.alert('重置失败!');
		          }
		        }
		      });
		}
	})
}

/**
 * 添加校长
 */
function addPrinciple(){
	var g = $("#user_maingrid").ligerGetGridManager();
	parent.$.ligerDialog.open({
		//target: $("#user_addWindow"),
		url : "system/user/principle_add.jsp",
		width : 500,
		height : 560,
		buttons : [ {
			text : '确定',
			onclick : function(item, dialog) {
				var data = dialog.frame.liger.get("user_addWindow_form").getData();
				if (data.user_name == "" || data.user_name == null) {
					top.$.ligerDialog.alert("请输入账号名称");
					return;
				}
				
				if (data.school_name == "" || data.school_name == null) {
					top.$.ligerDialog.alert("请输入学校名称");
					return;
				}
				
				if (data.user_realname == "" || data.user_realname == null) {
					top.$.ligerDialog.alert("请输入真实姓名");
					return;
				}
				if (data.counties == "" || data.counties == null) {
					top.$.ligerDialog.alert("请输入县市区");
					return;
				}
				if (data.user_tel == "" || data.user_tel == null) {
					top.$.ligerDialog.alert("手机号不能为空");
					return;
				}
				//校长角色
				data['user_role[0].role_id'] = 'a1f646f6-7e71-4e1a-b544-f190785b044c';
				data.user_is_enable = '0';
				$.ajax({
		            url: "wkrjsystem/wkrjUser/addUser",
		            data: data,
		            type: 'post',
		            dataType: 'json',
		            success: function(result){
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

/**
 * 模块修改
 */
function editPrincipal(){
	var g = $("#user_maingrid").ligerGetGridManager();
	var r = g.getSelectedRow();
	if (r == undefined)	{
		$.ligerDialog.alert('请选择一条记录进行修改!');
		return;
	}

	var s = parent.$.ligerDialog.open({
		url : "system/user/principle_update.jsp",
		width : 500,
		height : 520,
		data: {
            content:r
        },
		buttons : [ {
			text : '确定',
			onclick : function(item, dialog) {
				var data = dialog.frame.liger.get("user_updateWindow_form").getData();
				if (data.user_name == "" || data.user_name == null) {
					top.$.ligerDialog.alert("请输入账号名称");
					return;
				}
				if (data.user_realname == "" || data.user_realname == null) {
					top.$.ligerDialog.alert("请输入真实姓名");
					return;
				}
				
				if (data.school_name == "" || data.school_name == null) {
					top.$.ligerDialog.alert("请输入学校名称");
					return;
				}
				
				if (data.counties == "" || data.counties == null) {
					top.$.ligerDialog.alert("请输入县市区");
					return;
				}
				
				if (data.user_tel == "" || data.user_tel == null) {
					top.$.ligerDialog.alert("手机号不能为空");
					return;
				}
				
				//校长角色
				data['user_role[0].role_id'] = 'a1f646f6-7e71-4e1a-b544-f190785b044c';
				data.user_is_enable = '0';
				
				$.ajax({
		            url: "wkrjsystem/wkrjUser/updateUser",
		            data: data,
		            type: 'post',
		            dataType: 'json',
		            success: function(result){
		                    /*try{
		                        result = eval('('+result+')');
		                    }catch(e){
		                    }*/
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

function checkSfz() {
	
	var sfz = $('#sjc_userview_sfz').val();
	var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
	if (sfz!="") {
	    if (reg.test(sfz) === false) {  
	    	$.ligerDialog.alert('身份证输入不合法');
	        $('#sjc_userview_sfz').val('');
	        return  false;  
	    }  
    }
}

function checkTel(){
	
	var tel = $('#sjc_userview_tel').val();
	if(tel!=""){
		if(!(/^0?1[3|4|5|8][0-9]\d{8}$/.test(tel))){
			$.ligerDialog.alert('请输入正确的手机号');
			$('#sjc_userview_tel').val('');
			return;
		}
		
	}
	
}

function getBmName(rowdata, rowindex, value){
	
	var deptname="";
	
	$.ajax({  
        url : "wkrjsystem/wkrjUser/getDeptNameById",  
        async : false, // 注意此处需要同步，因为返回完数据后，下面才能让结果的第一条selected  
        type : "POST",
        data:{deptId:rowdata.dept_id},
        dataType : "json",  
        success : function(res) {  
        	if(res.obj){
           		deptname = res.obj.dept_name;
           	}
        }  
    });
    
     return deptname;
}

function getStationName(rowdata, rowindex, value){
	
	var stationName="";
	
	$.ajax({  
        url : "wkrjsystem/wkrjUser/getStationById",  
        async : false, // 注意此处需要同步，因为返回完数据后，下面才能让结果的第一条selected  
        type : "POST",
        data:{stationId:rowdata.station_id},
        dataType : "json",  
        success : function(res) {
        	if(res.obj){
           		stationName = res.obj.station_name;
           	}
        }  
    });
	
	return stationName;
}

function user_is_enable(rowdata, rowindex, value) {
	if (rowdata.user_is_enable == 0) {
		return '否';
	} else if(rowdata.user_is_enable == 1){
		return '是';
	}else{
		return '有问题';
	}
}

function showRoleName(rowdata, rowindex, value){
	var roleName="";
	if(rowdata.user_role.length>0){
		
		for(var i=0;i<rowdata.user_role.length;i++){
			roleName += rowdata.user_role[i].role_name;
			
			if(i<rowdata.user_role.length-1){
				roleName +=",";
			}
		}
	}
	return roleName;
}