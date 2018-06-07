<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>

<script type="text/javascript"
   src="page/sjld/warning/js/noticeannounce.js"></script>
<script type="text/javascript">

var checkedUser_id = [];
var checkedUser_realname = [];
function getCheckedUser_id(){
	return checkedUser_id;
}
function getCheckedUser_realname(){
	return checkedUser_realname;
}

$(function(){
	
    lay=$("#select_user_layout").ligerLayout({leftWidth:200,isRightCollapse: true});
    
	var data = frameElement.dialog.get('data').content;//获取data参数
	try {
		data = eval('('+data+')');
	} catch (e) {}
	
    tree = $("#select_user_dept_tree").ligerTree({
    	checkbox: false,
    	url : 'wkrjsystem/newsManage/getDeptTree',
    	idFieldName : 'id',
    	slide : false,
    	onSelect: onSelect
    });
	
    manager = $("#select_user_maingrid").ligerGrid({
        url:'wkrjsystem/newsManage/getUserList',
        columns : [
        { display: '真实姓名', name: 'user_realname', id:'user_realname',width: '100%', align: 'left' }
       
        ], height : '100%',
        width:'100%',
        checkbox: true,
        usePager : true,
        //rownumbers : true,
        alternatingRow : true,
        isChecked: f_isChecked, 
        onCheckRow: f_onCheckRow, 
        onCheckAllRow: f_onCheckAllRow
    });
    $("#pageloading").hide();
});

//左侧树，点击
function onSelect(note){
	var g = $("#select_user_maingrid").ligerGetGridManager();
	g.set({url:'wkrjsystem/wkrjUser/getUserLowerList?deptId='+note.data.id});
	g.reload();
}

//grid选中，init
function f_isChecked(rowdata){
	if (findcheckedUser_id(rowdata.user_id) == -1){
        return false;
	}
    return true;
}
//全选按钮
function f_onCheckAllRow(checked){
    for (var rowid in this.records){
        if(checked){
            addcheckedUser_id(this.records[rowid]['user_id'],this.records[rowid]['user_realname']);
        }else{
            removecheckedUser_id(this.records[rowid]['user_id'],this.records[rowid]['user_realname']);
        }
    }
}
//选中按钮
function f_onCheckRow(checked, data){
    if(checked){
    	addcheckedUser_id(data.user_id,data.user_realname);
    }else{
    	removecheckedUser_id(data.user_id,data.user_realname);
    } 
}


//选中事件
function addcheckedUser_id(user_id,user_realname){
    if(findcheckedUser_id(user_id) == -1){
    	checkedUser_id.push(user_id);
    	checkedUser_realname.push(user_realname);
    }
}
//取消选中事件
function removecheckedUser_id(user_id,user_realname){
    var i = findcheckedUser_id(user_id);
    if(i==-1){
    	return;
    }
    checkedUser_id.splice(i,1);
    checkedUser_realname.splice(i,1);
}

//查询对应user_id的行号
function findcheckedUser_id(user_id){
    for(var i =0;i<checkedUser_id.length;i++){
        if(checkedUser_id[i] == user_id){
        	return i;
        }
    }
    return -1;
}
 
</script>
<div class="l-loading" style="display:block" id="pageloading"></div>
    <div id="select_user_layout" style="width:100%;">
	    <div position="center">
	        <div id="select_user_maingrid"></div>
	    </div>
        <div position="left" left="150px" >
            <ul id="select_user_dept_tree"></ul> 
        </div> 
    </div>
