
var url="";
var lay;
var manager;
var perm;
$(function(){

    lay=$("#backedEventWf_layout").ligerLayout({rightWidth:560,isRightCollapse: true});
    
    manager = $("#backedEventWf_maingrid").ligerGrid({
        url:'eventReportWf/WkrjEventReportWf/getBackedEventWfList',
        columns: [
        { display: '编号', name: 'event_no', id: 'event_no', width: '10%', align: 'center' },
        { display: '内容', name: 'event_content', id: 'event_content', width: '30%', align: 'center' },
        { display: '姓名', name: 'caller_username', id: 'caller_username', width: '10%', align: 'center' },
        { display: '来电号码', name: 'caller_tel', id: 'caller_tel', width: '9%', align: 'center' },
        { display: '处理时限', name: 'deal_days', id: 'deal_days', width: '10%', align: 'center' },
        { display: '是否已审核', name: 'shenhe_status', id: 'shenhe_status', width: '10%', align: 'center', render: shenheTrans },
        //{ display: '处理级别', name: 'level', id: 'level', width: '10%', align: 'center' },
        { display: '来电性质', name: 'nature', id: 'nature', width: '10%', align: 'center' },
        { display: '发布时间', name: 'event_inputtime', id: 'event_inputtime', width: '10%', align: 'center' }
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
//        toolbar : {
//            items : [ {
//                text : '转办',
//                click : event_zhuanban,
//                icon : 'modify',
//                id:'eventReportWf/WkrjEventReportWf/zhuanbanEvent_back'
//            } ]
//        },
        onDblClickRow: function (data, rowindex, rowobj) {
        	if (data.shenhe_status == 1 || data.shenhe_status == 2) {
            	top.$.ligerDialog.alert("已审核");
                return;
            }
            var s = parent.$.ligerDialog.open({
                //target: $("#eventReport_updateWindow_form"),
                url : "page/sjc/sendBackEventWf/backDetailsWf.jsp",
                //data : JSON.stringify(r),
                width : 900,
                height :750,
                data: {
                    content:data
                },
                buttons : [ {
                    text : '确定',
                    onclick : function(item, dialog) {
                        var data = dialog.frame.liger.get("eventInfo_backedWfWindow_form").getData();
                        if (data.shenhe_status == null || data.shenhe_status == "") {
                        	top.$.ligerDialog.alert("请选择审核结果");
                            return;
                        }
                        if (data.shenhe_status == 1) {//审核通过直接转办
                        	data.chengban_dept_old = data.chengban_dept;
                            data.chengban_dept = dialog.frame.liger.get("chengban_dept").getValue();
                            if (data.chengban_dept == null || data.chengban_dept == "") {
                                top.$.ligerDialog.alert("承办单位不能为空");
                                return;
                            }
                            if (data.deal_days == null || data.deal_days == "") {
                                top.$.ligerDialog.alert("时限不能为空");
                                return;
                            }
                            data.deal_type = "转办";
                        }
                        $.ajax({
                            url: "eventReportWf/WkrjEventReportWf/shenheBackReason",
                            data: data,
                            dataType : "json",  
                            type : "POST",
                            success: function(result){
                                    try{
                                        result = eval('('+result+')');
                                    }catch(e){
                                    }
                                    if (result.success) {
                                    	top.$.ligerDialog.alert(result.msg);
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
    });
});

function event_zhuanban(row){
    var g = $("#backedEventWf_maingrid").ligerGetGridManager();
    var r = g.getSelectedRow();
    if (r == undefined) {
        $.ligerDialog.alert('请选择一条记录进行转办!');
        return;
    }
    if (r.zhuanbanornot == 1) {
        $.ligerDialog.alert('该记录已转办!');
        return;
    }
    var s = parent.$.ligerDialog.open({
        //target: $("#eventReport_updateWindow_form"),
        url : "page/sjc/sendBackEventWf/backDetailsWf.jsp",
        //data : JSON.stringify(r),
        width : 900,
        height :650,
        data: {
            content:r
        },
        buttons : [ {
            text : '确定',
            onclick : function(item, dialog) {
                var data = dialog.frame.liger.get("eventInfo_backedWfWindow_form").getData();
                data.chengban_dept_old = data.chengban_dept;
                data.chengban_dept = dialog.frame.liger.get("chengban_dept").getValue();
                if (data.chengban_dept == null || data.chengban_dept == "") {
                    top.$.ligerDialog.alert("承办单位不能为空");
                    return;
                }
//                if (data.user_secret == null || data.user_secret == "") {
//                    top.$.ligerDialog.alert("用户保密不能为空");
//                    return;
//                }
                data.deal_type = "转办";
                $.ajax({
                    url: "eventReportWf/WkrjEventReportWf/zhuanbanEvent_back",
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
