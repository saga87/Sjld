<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<%@include file="/systemdev/myContext.jsp" %>
 <head>
<script type="text/javascript">
var url="";
var lay;
var manager;
var perm;
$(function(){
	lay=$("#module_layout").ligerLayout({});	
    manager = $("#module_maingrid").ligerGrid({
    	url:'wkrjsystem/wkrjDataDictionary/getDataDictionary',
        columns: [
        { display: '字典名称', name: 'typename',id:'id1',  align: 'left' },
        { display: '字典编码', name: 'typecode', type: 'int', align: 'left' },
        { display: '备注', name: 'notes', align: 'left' }
        ], width: '100%', pageSizeOptions: [5, 10, 15, 20], height: '100%',checkbox:false,
        alternatingRow: false, tree: { columnId: 'id1' },
		toolbar : {
			items : [ {
				text : '增加',
				click : add,
				icon : 'add',
				id:'wkrjsystem/wkrjDataDictionary/add'
			}, {
				line : true
			}, {
				text : '修改',
				click : update,
				icon : 'modify',
				id:'wkrjsystem/wkrjDataDictionary/update'
			}, {
				line : true
			}, {
				text : '删除',
				click : del,
				icon : 'delete',
				id:'wkrjsystem/wkrjDataDictionary/del'
			}]
                 }        
    });
});

function add(){
	var parentid="-1";
	var row = manager.getSelectedRow();
	if(row!=null){
//		console.dir(rows[0]);
		parentid=row.id;
	}
	
	$.ligerDialog.open({
		url : "page/dictionary/add.jsp",
		width : 500,
		height : 400,
		data: {
			parentid:parentid
        },		
		buttons : [ {
			text : '确定',
			onclick : function(item, dialog) {
				var m = dialog.frame;
				var data = dialog.frame.liger.get("add_form").getData();//alert()
				var typeparentid = dialog.frame.liger.get("typeparentid").getValue();
				data.typeparentid = typeparentid;
				
				if(data.typename==""){
                	$.ligerDialog.alert("请填写名称！");					
                	return;
				}
				if(data.typecode==""){
                	$.ligerDialog.alert("请填写编码！");					
                	return;
				}
				
				
				//保存
				$.ajax({
			        url: "wkrjsystem/wkrjDataDictionary/add",
			        dataType : "json",  
			        type : "POST",
			        data: data,
			        success: function(result){			        	
			                if (result.success) {
			                	$.ligerDialog.alert(result.msg);
			                	manager.loadData();
			                    dialog.close();
			                } else {
			                	$.ligerDialog.alert(result.msg);
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
function update(){
	var row = manager.getSelectedRow();
	if(row==null){
		$.ligerDialog.alert("请选择要操作的数据！");
		return;
	}
	
	$.ligerDialog.open({
		url : "page/dictionary/update.jsp",
		width : 500,
		height : 400,
		data: {
			content:row
        },		
		buttons : [ {
			text : '确定',
			onclick : function(item, dialog) {
				var data = dialog.frame.liger.get("update_form").getData();//alert()
				var typeparentid = dialog.frame.liger.get("typeparentid").getValue();
				data.typeparentid = typeparentid;		
				data.id=row.id;
				if(data.typename==""){
                	$.ligerDialog.alert("请填写名称！");					
                	return;
				}
				if(data.typecode==""){
                	$.ligerDialog.alert("请填写编码！");					
                	return;
				}
				
				
				//保存
				$.ajax({
			        url: "wkrjsystem/wkrjDataDictionary/update",
			        dataType : "json",  
			        type : "POST",
			        data: data,
			        success: function(result){			        	
			                if (result.success) {
			                	$.ligerDialog.alert(result.msg);
			                	manager.loadData();
			                    dialog.close();
			                } else {
			                	$.ligerDialog.alert(result.msg);
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
function del(){
	var row = manager.getSelectedRow();
	if(row==null){
		$.ligerDialog.alert("请选择要操作的数据！");
		return;
	}
	$.ligerDialog.confirm('确定要删除此记录吗', function (param) {
		if (param) {
			$.ajax({
		        type: "POST",
		        url: 'wkrjsystem/wkrjDataDictionary/del',
		        data: { id: row.id },
		        dataType:"json",
		        cache: false,
		        async: false,
		        success: function (result) {
		          if (result.success) {
		        	  $.ligerDialog.alert(result.msg);
		        	  manager.reload();
		          } else {
		        	  $.ligerDialog.alert(result.msg);
		          }
		        }
		      });
		}
	})	
}
 </script>
</head>
<body style="overflow:hidden;">
<div id="module_layout" style="width:100%;">	
	<div position="center">
		<div id="module_maingrid"></div>
	</div>
</div>
</body>
</html>
