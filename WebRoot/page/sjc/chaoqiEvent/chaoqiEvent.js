
var url="";
var lay;
var manager;
var perm;
$(function(){

    lay=$("#overtimeEvent_layout").ligerLayout({rightWidth:560,isRightCollapse: true});
    
    manager = $("#overtimeEvent_maingrid").ligerGrid({
        url:'eventWf/WkrjEventWf/getOvertimeEventWfList',
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
        onDblClickRow: function (data, rowindex, rowobj) {
        	var s = parent.$.ligerDialog.open({
                //target: $("#eventReport_updateWindow_form"),
                url : "page/sjc/chaoqiEvent/chaoqiDetails.jsp",
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
