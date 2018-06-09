
var url="";
var lay;
var manager;
var perm;
$(function(){

    lay=$("#repliedBackedEventWf_layout").ligerLayout({rightWidth:560,isRightCollapse: true});
    
    manager = $("#repliedBackedEventWf_maingrid").ligerGrid({
        url:'eventWf/WkrjEventWf/getRepliedBackEventWfList',
        columns: [
        { display: '编号', name: 'event_no', id: 'event_no', width: '10%', align: 'center' },
        { display: '投诉标题', name: 'event_title', id: 'event_title', width: '20%', align: 'center' },
        { display: '受理类别', name: 'content_type', id: 'content_type', width: '10%', align: 'center' },
        { display: '处理时限', name: 'deal_days', id: 'deal_days', width: '10%', align: 'center' },
        { display: '业务类型', name: 'nature', id: 'nature', width: '10%', align: 'center' },
        { display: '受理时间', name: 'event_inputtime', id: 'event_inputtime', width: '10%', align: 'center' },
        { display: '受理渠道', name: 'source', id: 'source', width: '10%', align: 'center' },
        { display: '备注', name: 'event_other', id: 'event_other', width: '19%', align: 'center' }
        //{ display: '状态', name: 'event_status', id: 'event_status', width: '10%', align: 'center', render: statusTrans }
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
//                id:'eventWf/WkrjEventWf/zhuanbanEvent_back'
//            } ]
//        },
        onDblClickRow: function (data, rowindex, rowobj) {
        	if (data.shenhe_status == 1 || data.shenhe_status == 2) {
            	top.$.ligerDialog.alert("已审核");
                return;
            }
            var s = parent.$.ligerDialog.open({
                //target: $("#eventReport_updateWindow_form"),
                url : "page/sjc/repliedBackEvent/repliedBack_details.jsp",
                //data : JSON.stringify(r),
                width : 900,
                height :750,
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

function event_zhuanban(row){
    var g = $("#repliedBackedEventWf_maingrid").ligerGetGridManager();
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
        url : "page/sjc/repliedBackEvent/repliedBack_details.jsp",
        //data : JSON.stringify(r),
        width : 900,
        height :650,
        data: {
            content:r
        },
        buttons : [ {
            text : '确定',
            onclick : function(item, dialog) {
                var data = dialog.frame.liger.get("eventInfo_repliedBackedWfWindow_form").getData();
                data.chengban_dept_old = data.chengban_dept;
                data.chengban_dept = dialog.frame.liger.get("chengban_dept").getValue();
                if (data.chengban_dept == null || data.chengban_dept == "") {
                    top.$.ligerDialog.alert("承办单位不能为空");
                    return;
                }
                data.deal_type = "转办";
                $.ajax({
                    url: "eventWf/WkrjEventWf/zhuanbanEvent_repliedBack",
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
