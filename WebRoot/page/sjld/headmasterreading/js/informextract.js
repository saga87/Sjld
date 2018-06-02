
var url="";
var lay;
var manager;
var perm;



$(function(){

	lay=$("#informExtract_layout").ligerLayout({rightWidth:560,isRightCollapse: true});
	
	manager = $("#informExtract_maingrid").ligerGrid({
		url:'informExtract/getFinishedEvent',
        columns: [
        { display: '编号', name: 'event_no', id: 'event_no', width: '10%', align: 'center' },
        { display: '标题', name: 'event_title', id: 'event_title', width: '20%', align: 'center' },
        { display: '受理渠道', name: 'source', id: 'source', width: '10%', align: 'center' },
        { display: '业务类型', name: 'nature', id: 'nature', width: '5%', align: 'center' },
        { display: '受理时间', name: 'qianshou_time', id: 'qianshou_time', width: '10%', align: 'center' },
        { display: '受理类别', name: 'content_type', id: 'content_type', width: '10%', align: 'center' },
        { display: '受理单位', name: 'chengban', id: 'chengban', width: '15%', align: 'center' },
        { display: '状态', name: 'event_status', id: 'event_status', width: '10%', align: 'center' },
        { display: '满意度', name: 'satisfy_status', id: 'satisfy_status', width: '10%', align: 'center' },
        ], height: '100%',
        width:'99.9%',
        usePager :true,
		rownumbers : true,
        alternatingRow: true,
        toolbar : {
			items : [ {
				text : '上传案例',
				click : upload_case,
				icon : 'add',
				id:'informExtract/uploadCase'
			}, {
				line : true
			}, {
				text : '提取案例',
				click : extract_case,
				icon : 'add',
				id:'informExtract/uploadCase'
			
			}]
		}
    });
    
	
});


function extract_case(){
	var g = $("#informExtract_maingrid").ligerGetGridManager();
	var r = g.getSelectedRow();
	if (r == undefined)	{
		$.ligerDialog.alert('请选择一条记录进行提取!');
		return;
	}
	parent.$.ligerDialog.open({
		url : "page/sjld/headmasterreading/readcase_extract.jsp",
		width : 600,
		height : 460,
		data: {
            content:r
		},
		buttons : [ {
			text : '确定',
			onclick : function(item, dialog) {
				var m = dialog.frame;
				var len = 0;
				var data = dialog.frame.liger.get("readcase_extractWindow_form").getData();//alert()
				
				if (data.event_title == null || data.event_title == "") {
					top.$.ligerDialog.alert("标题不能为空");
					return;
				}else if (data.event_content == null || data.event_content == "") {
					top.$.ligerDialog.alert("内容不能为空");
					return;
				}
				else if (data.content_type == null || data.content_type == "") {
					top.$.ligerDialog.alert("类型不能为空");
					return;
				}
				
				data.must_read_id = data.event_no;
				data.must_read_title = data.event_title;
				data.case_content = data.event_content;
				data.event_type = data.content_type
				realAddCase(data,g,dialog);
			}
		},{
			text : '取消',
			onclick : function(item, dialog) {
				dialog.close();
			}
		} ]
	});
	
	
}


function upload_case(){
	var g = $("#informExtract_maingrid").ligerGetGridManager();
	parent.$.ligerDialog.open({
		url : "page/sjld/headmasterreading/readcase_add.jsp",
		width : 900,
		height : 600,
		id:'zzz',
		buttons : [ {
			text : '确定',
			onclick : function(item, dialog) {
				var m = dialog.frame;
				var len = 0;
				var data = dialog.frame.liger.get("readcase_addWindow_form").getData();//alert()
				
				
				
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
				
				
				
				realAddCase(data,g,dialog);
			}
		}, {
			text : '取消',
			onclick : function(item, dialog) {
				dialog.close();
			}
		} ]
	});
}

function realAddCase(data,g,dialog){
	 $.ajax({
       url: "informExtract/uploadCase",
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


//根据条件检索(查询)
function messages() {
	var g = $("#informExtract_maingrid").ligerGetGridManager();
	var event_no = $('#event_no').val();
	var event_title = $('#event_title').val();
//	var business_type = $('#business_type').val();
	var event_content = $('#event_content').val();
	g.set({url:'informExtract/getFinishedEvent?event_no='+event_no
		+'&event_title='+event_title
		+'&event_content='+event_content
//		+'&business_type='+business_type
		});    
	g.reload();
}








