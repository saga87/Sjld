
var url="";
var lay;
var manager;
var perm;
$(function(){

    lay=$("#eventSearchWf_layout").ligerLayout({rightWidth:560,isRightCollapse: true});
    
    manager = $("#eventSearchWf_maingrid").ligerGrid({
        url:'eventWf/WkrjEventWf/searchEventWfList',
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
        ], height: '100%',
        width:'99.9%',
        usePager :true,
        rownumbers : true,
        alternatingRow: true,
        //tree:{columnId:'eventReportWf_name',idField:'eventReportWf_id',parentIDField:'eventReportWf_parent_id'},
        /*toolbar : {
            items : [ {
                text : '督办',
                click : eventWf_duban,
                icon : 'modify',
                id:'eventWf/WkrjEventWf/dubanEventWf'
            }, {
                line : true
            } ]
        },*/
        onDblClickRow: function (data, rowindex, rowobj) {
            var s = parent.$.ligerDialog.open({
                //target: $("#eventReport_updateWindow_form"),
                url : "page/sjc/allEvent/allDetails.jsp",
                //data : JSON.stringify(r),
                width : 900,
                height :650,
                data: {
                    content:data
                },
                buttons : [ {
                    text : '取消',
                    onclick : function(item, dialog) {
                        dialog.close();
                    }
                } ]

            });
        }
    });
});

/**
 * 事件督办
 */
function eventWf_duban(row){
    var g = $("#eventSearchWf_maingrid").ligerGetGridManager();
    var r = g.getSelectedRow();
    if (r == undefined) {
        $.ligerDialog.alert('请选择一条记录进行督办!');
        return;
    }
    if (r.event_status == "直接完成" || r.event_status == "完成" || r.event_status == "回访完成") {
        $.ligerDialog.alert('此事件已完成!');
        return;
    }
    if (r.duban_status == "1") {
        $.ligerDialog.alert('已督办过!');
        return;
    }
    var s = parent.$.ligerDialog.open({
        //target: $("#eventReport_updateWindow_form"),
        url : "page/sjc/eventReportWf/dubanDetailsWf.jsp",
        //data : JSON.stringify(r),
        width : 900,
        height :350,
        data: {
            content:r
        },
        buttons : [ {
            text : '确定',
            onclick : function(item, dialog) {
                var data = dialog.frame.liger.get("event_dubanWfWindow_form").getData();
                $.ajax({
                    url: 'eventWf/WkrjEventWf/dubanEventWf',
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
 * 事件质检
 */
function qualityCheck(row){
    var g = $("#eventSearchWf_maingrid").ligerGetGridManager();
    var r = g.getSelectedRow();
    if (r == undefined) {
        $.ligerDialog.alert('请选择一条记录进行质检!');
        return;
    }
//    if (r.event_status == "直接完成" || r.event_status == "完成" || r.event_status == "回访完成") {
//        $.ligerDialog.alert('此事件已完成!');
//        return;
//    }
    if (r.zhijian_status == "1") {
        $.ligerDialog.alert('已质检过!');
        return;
    }
    var s = parent.$.ligerDialog.open({
        //target: $("#eventReport_updateWindow_form"),
        url : "page/sjc/wfEventSearch/zhijianDetailsWf.jsp",
        //data : JSON.stringify(r),
        width : 900,
        height :350,
        data: {
            content:r
        },
        buttons : [ {
            text : '确定',
            onclick : function(item, dialog) {
                var data = dialog.frame.liger.get("event_zhijianWfWindow_form").getData();
                $.ajax({
                    url: 'eventWf/WkrjEventWf/qualityCheck',
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

function goSearch(){
    var start_date=$("#start_date").val();
    var end_date=$("#end_date").val();
    var deal_status=$("#deal_status").val();
    var event_no=$("#event_no").val();
    var caller_tel=$("#caller_tel").val();
    var event_content=encodeURIComponent($("#event_content").val());
    //var qianshou_status=$("#qianshou_status").val();
    var business_type=$("#business_type").ligerComboBox('getValue');
    //var content_type=$("#content_type").ligerComboBox('getValue');
    var content_type = $('#content_type').val();
    //var chengban_user = $('#deal_zuoxi').val();

    manager.set({
        url:'eventWf/WkrjEventWf/searchEventWfList?start_date='+start_date+'&end_date='+end_date+'&deal_status='+deal_status+'&event_no='+event_no
        +'&business_type='+business_type+'&content_type='+content_type+'&event_content='+event_content+'&caller_tel='+caller_tel,
        //pageSize:10,
        page:1
    });
    manager.reload(1);
}

function goReset(){
    $("#start_date").val("");
    $("#end_date").val("");
    $("#deal_status").val("");
    $("#event_no").val("");
    $("#caller_tel").val("");
    $("#event_content").val("");
    $("#business_type").ligerComboBox('setText','');
    $("#business_type_val").val('');
    $("#content_type").ligerComboBox('setText','');
    $("#content_type_val").val('');    
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

function zhijianTrans(rowdata, rowindex, value) {
    if (rowdata.zhijian_status == 1) {
        return "是";
    } else if (rowdata.zhijian_status == 0) {
        return "否";
    } else {
        return "";
    }
}