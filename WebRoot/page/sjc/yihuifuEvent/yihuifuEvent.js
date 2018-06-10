
var url="";
var lay;
var manager;
var perm;
$(function(){

    lay=$("#repliedEventWf_layout").ligerLayout({rightWidth:560,isRightCollapse: true});
    
    manager = $("#repliedEventWf_maingrid").ligerGrid({
        url:'eventWf/WkrjEventWf/getRepliedEventWfList',
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
        onDblClickRow: function (data, rowindex, rowobj) {
        	if (data.satisfy_status != null && data.satisfy_status != "") {
                top.$.ligerDialog.alert("已评价");
                return;
            }
            var s = parent.$.ligerDialog.open({
                //target: $("#eventReport_updateWindow_form"),
                url : "page/sjc/yihuifuEvent/yihuifuDetails.jsp",
                //data : JSON.stringify(r),
                width : 900,
                height :650,
                data: {
                    content:data
                },
                buttons : [ {
                    text : '保存',
                    onclick : function(item, dialog) {
                        var data = dialog.frame.liger.get("eventInfo_repliedWfWindow_form").getData();
                        if (data.deal_type == null || data.deal_type == "") {
                            top.$.ligerDialog.alert("请选择如何处理");
                            return;
                        }
                        if (data.deal_type == "1") {
                        	data.satisfy_status = dialog.frame.$('input[name="satisfy_status"]:checked').val();
                            if (data.satisfy_status == undefined) {
                                top.$.ligerDialog.alert('请选择满意度');
                                return;
                            }
                            $.ajax({
                                url: "eventWf/WkrjEventWf/pingjiaEventWf",
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
                        if (data.deal_type == "2") {
                        	data.opt_content = dialog.frame.$('#back_reason').val();
                            if (data.opt_content == null || data.opt_content == "") {
                                top.$.ligerDialog.alert("退回理由不能为空");
                                return;
                            }
                            $.ajax({
                                url: "eventWf/WkrjEventWf/sendBackEventWf",
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

function goSearch(){
//    var start_date=$("#start_date").val();
//    var end_date=$("#end_date").val();
    var event_no=$("#event_no").val();
    var event_content=encodeURIComponent($("#event_content").val());
    //var qianshou_status=$("#qianshou_status").val();
    //var business_type=$("#business_type").ligerComboBox('getValue');
    //var content_type=$("#content_type").ligerComboBox('getValue');

    manager.set({
        url:'eventWf/WkrjEventWf/getRepliedEventWfList?event_no='+event_no+'&event_content='+event_content,
        //pageSize:10,
        page:1
    });
    manager.reload(1);
}

function goReset(){
//    $("#start_date").val("");
//    $("#end_date").val("");
//    $("#deal_status").val("");
    $("#event_no").val("");
    $("#event_content").val("");
//    $("#business_type").ligerComboBox('setText','');
//    $("#business_type_val").val('');
//    $("#content_type").ligerComboBox('setText','');
//    $("#content_type_val").val('');    
}