
var url="";
var lay;
var manager;
var perm;
$(function(){

    lay=$("#eventWf_layout").ligerLayout({rightWidth:560,isRightCollapse: true});
    
    manager = $("#eventWf_maingrid").ligerGrid({
        url:'eventWf/WkrjEventWf/getWfNotAcceptList_dept',
        columns: [
        { display: '编号', name: 'event_no', id: 'event_no', width: '10%', align: 'center' },
        { display: '投诉标题', name: 'event_title', id: 'event_title', width: '20%', align: 'center' },
        { display: '受理类别', name: 'content_type', id: 'content_type', width: '10%', align: 'center' },
        { display: '处理时限', name: 'deal_days', id: 'deal_days', width: '10%', align: 'center' },
        { display: '业务类型', name: 'nature', id: 'nature', width: '10%', align: 'center' },
        { display: '受理时间', name: 'event_inputtime', id: 'event_inputtime', width: '10%', align: 'center' },
        { display: '受理渠道', name: 'source', id: 'source', width: '10%', align: 'center' },
        { display: '备注', name: 'event_other', id: 'event_other', width: '9%', align: 'center' },
        { display: '状态', name: 'event_status', id: 'event_status', width: '10%', align: 'center', render: statusTrans }
//        { display: '操作', isSort: false, width: '9%', render: function (rowdata, rowindex, value){
//            var h = "<a style='text-decoration:none;' onclick='print(\""+rowindex+"\")' href='javascript:void(0)'>[打印]</a>";
//            return h;
//            }
//        }
        ], height: '100%',
        width:'99.9%',
        usePager :true,
        rownumbers : true,
        alternatingRow: true,
        //tree:{columnId:'eventWf_name',idField:'eventWf_id',parentIDField:'eventWf_parent_id'},
        toolbar : {
            items : [ {
                text : '增加',
                click : eventWf_add,
                icon : 'add',
                id:'eventWf/WkrjEventWf/addEventWf'
            }, {
                line : true
            }, {
                text : '修改',
                click : eventWf_edit,
                icon : 'modify',
                id:'eventWf/WkrjEventWf/editEventWf'
            }, {
                line : true
            }, {
                text : '删除',
                click : eventWf_del,
                icon : 'delete',
                id:'eventWf/WkrjEventWf/delEventWf'
            } ]
        },
        onDblClickRow: function (data, rowindex, rowobj) {
//            var s = parent.$.ligerDialog.open({
//                url : "page/sjc/eventWf/event_details.jsp",
//                width : 900,
//                height :650,
//                data: {
//                    content:data
//                },
//                buttons : [ {
//                    text : '取消',
//                    onclick : function(item, dialog) {
//                        dialog.close();
//                    }
//                } ]
//
//            });
        }
    });
});

/**
 * 事件修改
 */
function eventWf_edit(row){
    var g = $("#eventWf_maingrid").ligerGetGridManager();
    var r = g.getSelectedRow();
    if (r == undefined) {
        $.ligerDialog.alert('请选择一条记录进行修改!');
        return;
    }
    if (r.qianshou_status != "0") {
        $.ligerDialog.alert('此事件处理中!');
        return;
    }
    var s = parent.$.ligerDialog.open({
        //target: $("#eventReport_updateWindow_form"),
        url : "page/sjc/eventWf/eventWfEdit.jsp",
        //data : JSON.stringify(r),
        width : 900,
        height :650,
        data: {
            content:r
        },
        buttons : [ {
            text : '确定',
            onclick : function(item, dialog) {
                var data = dialog.frame.liger.get("event_EditWfWindow_form").getData();
                $.ajax({
                    url: 'eventWf/WkrjEventWf/editEventWf',
                    data: data,
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
                            $.ligerDialog.alert(result.msg);
                            manager.loadData();
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
 * 事件删除
 */
function eventWf_del(row){
    var g = $("#eventWf_maingrid").ligerGetGridManager();
    var r = g.getSelectedRow();
    if (r == undefined) {
        $.ligerDialog.alert('请选择一条记录进行删除!');
        return;
    }
    if (r.qianshou_status != "0") {
        $.ligerDialog.alert('此事件处理中!');
        return;
    }
    $.ligerDialog.confirm('确定要删除此记录吗', function (param) {
        if (param) {
            $.ajax({
                url: 'eventWf/WkrjEventWf/delEventWf',
                data: { event_id: r.event_id },
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

function goSearch(){
    var start_date=$("#start_date").val();
    var end_date=$("#end_date").val();
    var event_status=$("#event_status").val();
    var event_no=$("#event_no").val();
    var event_content=encodeURIComponent($("#event_content").val());
    var qianshou_status=$("#qianshou_status").val();
    var caller_username=$("#caller_username").val();
    var caller_tel=$("#caller_tel").val();
    var content_type=$("#content_type").val();
    var business_type=$("#business_type").ligerComboBox('getValue');
    //var content_type=$("#content_type").ligerComboBox('getValue');

    manager.set({
        url:'eventWf/WkrjEventWf/searchEventWfList?start_date='+start_date+'&end_date='+end_date+'&event_status='+event_status+'&event_no='+event_no+'&event_content='+event_content
        +'&qianshou_status='+qianshou_status+'&caller_username='+caller_username+'&caller_tel='+caller_tel+'&business_type='+business_type+'&content_type='+content_type,
        //pageSize:10,
        page:1
    });
    manager.reload(1);
}

function goReset(){
    $("#start_date").val("");
    $("#end_date").val("");
    $("#event_status").val("");
    $("#event_content").val("");
    $("#qianshou_status").val("");
    $("#caller_username").val("");
    $("#caller_tel").val("");
    $("#business_type").ligerComboBox('setText','');
    $("#business_type_val").val('');
    $("#cotent_type").ligerComboBox('setText','');
    $("#cotent_type_val").val('');    
}

/**
 * 添加事件
 */
function eventWf_add(){
    //$("#form1").show();
    var g = $("#eventWf_maingrid").ligerGetGridManager();
    parent.$.ligerDialog.open({
        //target: $("#eventWf_addWindow"),
        url : "page/sjc/eventWf/eventWfAdd.jsp",
        width : 900,
        height : 630,
        id:'sss',
        buttons : [ {
            text : '确定',
            onclick : function(item, dialog) {
                var m = dialog.frame;
                var len = 0;
                //len = m.$("#uploadify").data('uploadify').queueData.queueLength;
                var data = dialog.frame.liger.get("eventWf_addWindow_form").getData();
                data.chengban_dept = dialog.frame.liger.get("chengban_dept").getValue();
                //data.chengban_dept = dialog.frame.$("#chengban_dept_hidden").val();
                data.content_type = dialog.frame.$("#content_type_hidden").val();
                data.deal_days = dialog.frame.liger.get("deal_days").getValue();
                //data.deal_level = dialog.frame.liger.get("deal_level").getValue();
                data.business_type = dialog.frame.liger.get("business_type").getValue();
                //data.caller_gender = dialog.frame.liger.get("caller_gender").getValue();
                data.accept_source = dialog.frame.liger.get("accept_source").getValue();
                //data.content_type = dialog.frame.liger.get("content_type").getValue();
                if (data.event_content == null || data.event_content == "") {
                    top.$.ligerDialog.alert("投诉内容不能为空");
                    return;
                } else if (data.deal_days == null || data.deal_days == "") {
                    top.$.ligerDialog.alert("处理时限不能为空");
                    return;
                } else if (data.content_type == null || data.content_type == "") {
                    top.$.ligerDialog.alert("受理类别不能为空");
                    return;
                } else if (data.business_type == null || data.business_type == "") {
                    top.$.ligerDialog.alert("业务类型不能为空");
                    return;
                } else if (data.chengban_dept == null || data.chengban_dept == "") {
                    top.$.ligerDialog.alert("承办单位不能为空");
                    return;
                }
                realAddEventReport(data,g,dialog);
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
function realAddEventReport(data,g,dialog){
     $.ajax({
        url: "eventWf/WkrjEventWf/addEventWf",
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

function dubanTrans(rowdata, rowindex, value) {
    if (rowdata.duban_status == 1) {
        return "是";
    } else if (rowdata.duban_status == 0) {
        return "否";
    } else {
        return "";
    }
}