
$(function(){
	
	$("#main_layout").ligerLayout({
			leftWidth: 240,
			topHeight: 85,
	        space: 4
	});
		
	tab = $("#mainTabs").ligerTab({height:'100%'});
		
	$("#leftMenuTree").ligerTree({
		url:'wkrjsystem/wkrjMenu/getLeftMenu',
		checkbox:false,
		nodeWidth: 160,
		onClick: function(node) {
			
			if (null!=node.data.attributes.menu_url && "null"!=node.data.attributes.menu_url && node.data.attributes.menu_url != "") {
				var url_ = permissionsCheck(node.data.attributes.menu_url);
				if(""==url_){
					$.ligerDialog.alert('没有此权限');
					return false;
				}else{
					openTab(url_, node.data.text, node.data.id, node.iconCls);
				}
			}
		},
		onSuccess:function(node){
			if(""==node||node==null){
				alert("没有任何权限");
				window.location.href="systemdev/developer.jsp";
			}
			/*if(false==node.data.success){
				window.location.href="systemdev/developer.jsp";
			}*/
		}
	});
	

//	$.post('wkrjsystem/statistic/getExpiredInfoList', {page:1,rows:20}, function(result) {
//	    var result = eval('(' + result + ')');
//        if (result.cnt > 0 || result.expired_count > 0 || 
//        		result.isExpiring_count > 0 || result.notChecked_count > 0) {
//            var title = "超期提醒";
//            //var plugin = "reportInfoCheck/reportInfoCheck";
//            //$.ligerDialog.tip(0, '<a onclick="open1(\''+plugin+'\',\''+title+'\',\'\',\'\');"  href="javascript:void(0);" style="text-decoration:none;color:red;font-size:14px;">您有['+result.total+']条待办事项已到期，点击可以打开待办事项</a>');
//            var html_content = '校园安全巡查未查看：<a style="text-decoration:none;color:red;font-size:14px;">'+
//            						'['+result.cnt+']</a>条<br>'+
//            					'校园安全巡查已到期：<a style="text-decoration:none;color:red;font-size:14px;">'+
//            						'['+result.expired_count+']</a>条<br>'+
//            					'校园安全巡查快到期：<a style="text-decoration:none;color:red;font-size:14px;">'+
//            						'['+result.isExpiring_count+']</a>条<br>'+
//            					'通知公告未查看：<a href="javascript:void(0);" onclick="newsNotChecked_click()" style="text-decoration:underline;color:red;font-size:14px;">'+
//            						'['+result.notChecked_count+']</a>条<br>'+
//            					'灭火器使用寿命到期：<a href="javascript:void(0);" onclick="mhqsm_click()" style="text-decoration:underline;color:red;font-size:14px;">'+
//            						'['+result.mhqExpiredCnt+']</a>个<br>'+
//            					'灭火器年检时间到期：<a href="javascript:void(0);" onclick="mhqnj_click()" style="text-decoration:underline;color:red;font-size:14px;">'+
//            						'['+result.mhqYearCheckExpiredCnt+']</a>个';
//            var ddd = $.ligerDialog.tip({
//            	width: 250,
//            	height: 200,
//            	title: '提示信息',
//            	content: html_content
//            });
//            /*setTimeout(function () {
//    			ddd.close(); //5秒延迟后关闭tip
//    		}, 5000);*/
//        }
//    });
});

function newsNotChecked_click(){
	openTab("sjc/newsManage/newsManage", "通知公告管理", "e66baf6b-6524-4e60-9b8f-5b575cb6a59b");
}

function mhqsm_click(){
	openTab("sjc/fireSecurity/fireSecurity", "消防安全", "d97d161f-c803-417a-8480-fe89436e377b");
}

function mhqnj_click(){
	openTab("sjc/fireSecurity/fireSecurity", "消防安全", "d97d161f-c803-417a-8480-fe89436e377b");
}

// 创建tab
function openTab(plugin, title, id, iconCls) {
	tab = $("#mainTabs").ligerTab({height:'100%'});
	if(tab.isTabItemExist(id)){
		tab.selectTabItem(id);
	}else{
		if(plugin.indexOf(".wk")>0){
			tab.addTabItem({
                tabid: id,
                text: title,
                url: plugin
            });
		}else{
			tab.addTabItem({
                tabid: id,
                text: title,
                url: "page/"+plugin+".jsp"
            });
		}
	}
}

function permissionsCheck(url) {
			
	var resultStr = '';
	
	$.ajax({
		url : 'wkrjsystem/wkrjlogin/permissionsCheck',
		type : "POST",
		data:{
			"url":url
		},
		async : false,
		success : function(objJson) {
			if(objJson.success == true) {
				resultStr = url;
			}
		},
		dataType : "json"
	});
	
	return resultStr;
}
	
// 退出登录
function loginout() {
	//window.location.href = "wkrjsystem/wkrjlogin/loginout";
}

// 修改密码窗口
function password() {
	$.ligerDialog.open({ 
		url: 'system/updatepassword.jsp',
		height: 200,
		width: null, 
		buttons: [{ 
			text: '确定', 
			onclick: function (item, dialog) {
				
				var data = dialog.frame.liger.get("passwordForm").getData();
				$.ajax({
		            url: "wkrjsystem/wkrjlogin/updatepassword",
		            data: data,
		            dataType:'json',
		            type:'POST',
		            success: function(result){
	                    //result = eval('('+result+')');
	                    if (result.success) {
	                    	$.ligerDialog.alert(result.msg);
	                        dialog.close();
	                    } else {
	                    	$.ligerDialog.alert(result.msg);
	                    }
		            }
		          });
				
				
			}}, 
			{ text: '取消', onclick: function (item, dialog) { 
			dialog.close(); 
			} 
		}] 
	});
	//$('#passwordWindow_dev').dialog('open').dialog('setTitle', '修改密码');
}

// 修改密码
function updatePassword() {
	url = 'login/updatepassword';
	$('#passwordForm_dev').form('submit', {
		url : url,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			var result = eval('(' + result + ')');
			if (result.success) {
				$.messager.alert('操作提示', '修改成功');
				$('#passwordWindow').dialog('close');
			} else {
				$.messager.alert('操作提示', result.msg);
			}
		}
	});
}


