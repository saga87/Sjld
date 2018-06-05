
var url="";
var lay;
var manager;
var perm;
$(function(){

    lay=$("#wfNotReply_layout").ligerLayout({rightWidth:560,isRightCollapse: true});
    
    manager = $("#wfNotReply_maingrid").ligerGrid({
        url:'eventWf/WkrjEventWf/getWfNotReplyList',
        columns: [
        { display: '编号', name: 'event_no', id: 'event_no', width: '10%', align: 'center' },
        { display: '投诉标题', name: 'event_title', id: 'event_title', width: '20%', align: 'center' },
        { display: '受理类别', name: 'content_type', id: 'content_type', width: '10%', align: 'center' },
        { display: '处理时限', name: 'deal_days', id: 'deal_days', width: '5%', align: 'center' },
        { display: '限制期限', name: 'remain_hours', id: 'remain_hours', width: '15%', align: 'center', render: showPic },
        { display: '业务类型', name: 'nature', id: 'nature', width: '5%', align: 'center' },
        { display: '受理时间', name: 'event_inputtime', id: 'event_inputtime', width: '10%', align: 'center' },
        { display: '受理渠道', name: 'source', id: 'source', width: '6%', align: 'center' },
        { display: '是否转办', name: 'zhuanbanornot', id: 'zhuanbanornot', width: '9%', align: 'center', render: zhuanbanTrans },
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
        toolbar : {
            items : [/* {
                text : '打印',
                click : print2,
                icon : 'print',
                id:'eventWf/WkrjEventWf/getPrintPage'
            }, {
                line : true
            },*/ {
                text : '延时',
                click : eventReport_delay,
                icon : 'modify',
                id:'eventWf/WkrjEventWf/delayEventWf'
            }/*, {
                line : true
            }, {
                text : '回复',
                click : eventReport_reply,
                icon : 'modify',
                id:'eventWf/WkrjEventWf/replyEventWf'
            }*/ ]
        },
        onDblClickRow: function (data, rowindex, rowobj) {
        	if (data.zhuanbanornot == 1) {
            	top.$.ligerDialog.alert("已转办");
                return;
            }
            var s = parent.$.ligerDialog.open({
                //target: $("#wfNotReply_updateWindow_form"),
                url : "page/sjc/notReply/eventWfReply.jsp",
                //data : JSON.stringify(r),
                width : 900,
                height :650,
                data: {
                    content:data
                },
                buttons : [ {
                    text : '确定',
                    onclick : function(item, dialog) {
                        var data = dialog.frame.liger.get("wfNotReply_replyWindow_form").getData();
                        //data.opt_content = dialog.frame.$("#reply_content_div").html();
                        //data.importance = dialog.frame.liger.get("importance").getValue();
                        data.deal_result = dialog.frame.$('input:radio:checked').val();
                        if (data.deal_result == undefined) {
                        	top.$.ligerDialog.alert("请选择处理结果");
                            return;
                        }
                        if (data.deal_result == 1) {
                        	data.opt_content = dialog.frame.$("#reply_content").val();
                        	$.ajax({
                                url: "eventWf/WkrjEventWf/replyEventWf",
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
                        if (data.deal_result == 2) {
                        	data.chengban_dept = dialog.frame.liger.get("chengban_dept").getValue();
                        	$.ajax({
                                url: "eventWf/WkrjEventWf/zhuanbanEvent",
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
                        if (data.deal_result == 3) {
                        	data.opt_content = dialog.frame.dialog.frame.$("#back_reason").val();
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

function eventReport_delay(row){
    var g = $("#wfNotReply_maingrid").ligerGetGridManager();
    var r = g.getSelectedRow();
    if (r == undefined) {
        $.ligerDialog.alert('请选择一条记录进行延时!');
        return;
    }
    if (r.zhuanbanornot == 1) {
    	top.$.ligerDialog.alert("已转办");
        return;
    }
    var s = parent.$.ligerDialog.open({
        //target: $("#eventReport_updateWindow_form"),
        url : "page/sjc/notReply/eventWfDelay.jsp",
        //data : JSON.stringify(r),
        width : 900,
        height :650,
        data: {
            content:r
        },
        buttons : [ {
            text : '确定',
            onclick : function(item, dialog) {
                var data = dialog.frame.liger.get("wfNotReply_delayWindow_form").getData();
                if (data.delay_reason == null || data.delay_reason == "") {
                    top.$.ligerDialog.alert("延时理由不能为空");
                    return;
                }
                //data.importance = dialog.frame.liger.get("importance").getValue();
                $.ajax({
                    url: "eventWf/WkrjEventWf/delayEventWf",
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

function eventReport_reply(row){
    var g = $("#wfNotReply_maingrid").ligerGetGridManager();
    var r = g.getSelectedRow();
    if (r == undefined) {
        $.ligerDialog.alert('请选择一条记录进行回复!');
        return;
    }
    var s = parent.$.ligerDialog.open({
        //target: $("#eventReport_updateWindow_form"),
        url : "page/sjc/wfNotReply/eventWf_reply.jsp",
        //data : JSON.stringify(r),
        width : 900,
        height :650,
        data: {
            content:r
        },
        buttons : [ {
            text : '确定',
            onclick : function(item, dialog) {
                var data = dialog.frame.liger.get("wfNotReply_replyWindow_form").getData();
//                data.reply_content = reply_ue.getContent();
//                data.reply_content_text = reply_ue.getPlainTxt();
                var item1 = dialog.frame.$("#item1").html();
                var item2 = dialog.frame.$("#item2").html();
                //var item3 = dialog.frame.$("#item3").html();
                var item4 = dialog.frame.$("#item4").html();
                var item5 = dialog.frame.$("#item5").html();
                var item6 = dialog.frame.$("#item6").html();
                var item7 = dialog.frame.$("#item7").html();
                var item8 = dialog.frame.$("#item8").html();
                var item9 = dialog.frame.$("#item9").html();
                var item10 = dialog.frame.$("#item10").html();
                var item11 = dialog.frame.$("#item11").html();
                var item12 = dialog.frame.$("#item12").html();
                var item13 = dialog.frame.$("#item13").html();
                var item14 = dialog.frame.$("#item14").html();
                var item15 = dialog.frame.$("#item15").html();
//                var item16 = dialog.frame.$("#item16").html();
//                var item17 = dialog.frame.$("#item17").html();
//                var item18 = dialog.frame.$("#item18").html();
                var item19 = dialog.frame.$("#item19").html();
                var item20 = dialog.frame.$("#item20").html();
                if (data.caller_username == "隐藏") {
                    if (item1 == "" || item2 == "" || item4 == "" || item5 == "" ||
                            item12 == "" || item13 == "" || item14 == "" || item15 == "") {
                        top.$.ligerDialog.alert("回复格式中不能有空项");
                        return;
                    }
                    data.reply_content = dialog.frame.$("#reply_content_divHidden").html();
                } else {
                    var satisfyDu = dialog.frame.$("input[name='satisfyDu']:checked").val();
                    if (item1 == "" || item2 == "" || item4 == "" || item5 == "" || item6 == "" || item7 == "" || item8 == "" || item9 == "" || item10 == "" || (satisfyDu == "2" && item11 == "") ||
                            item12 == "" || item13 == "" || item14 == "" || item15 == "" || satisfyDu == undefined || item19 == "" || item20 == "") {
                        top.$.ligerDialog.alert("回复格式中不能有空项");
                        return;
                    }
                    data.reply_content = dialog.frame.$("#reply_content_divPublic").html();
                }
                //data.reply_content = dialog.frame.$("#reply_content_div").html();
                //data.importance = dialog.frame.liger.get("importance").getValue();
                data.file_yname = dialog.frame.$("#file_yname").val();
                data.file_xname = dialog.frame.$("#file_xname").val();
                $.ajax({
                    url: "eventWf/WkrjEventWf/replyEventWf",
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

function showPic(rowdata, rowindex, value) {
    var temp = Math.abs(parseInt(rowdata.remain_hours))/24;
    temp = Math.round(temp*100)/100;
    if (rowdata.remain_hours < -1) {
        return "<img src='page/imgs/notover.png'/>"+rowdata.remain_hours+"小时("+temp+"天)";
    } else if (rowdata.remain_hours > 0) {
        return "<img src='page/imgs/over.gif'/>"+rowdata.remain_hours+"小时("+temp+"天)";
    } else {
        return "<img src='page/imgs/tover.gif'/>"+rowdata.remain_hours+"小时("+temp+"天)";
    }
}

function print2(){
//    var rows = manager.getSelectedRows();
//    if(rows==null || rows.length!=1){
//        $.ligerDialog.alert("请选择一条要打印的数据！");
//        return;
//    }    
//    var row=rows[0];
    var g = $("#wfNotReply_maingrid").ligerGetGridManager();
    var r = g.getSelectedRow();
    if (r == undefined) {
        $.ligerDialog.alert('请选择一条记录进行打印!');
        return;
    }
    var LODOP = getLodop();
    if(LODOP!=null){
            var first="";
               $.ajax({
                   type:'POST',
                   url:'eventWf/WkrjEventWf/getPrintPage?event_id='+r.event_id,
                   data: {},
                   async:false,
                   success:function(data){
                       first=data;                                  
                   }  
               });
               LODOP.PRINT_INIT("打印");                       
            LODOP.ADD_PRINT_HTM(50, 60, "90%", "90%", first);
            LODOP.PREVIEW();                         
    }
}    

function goSearch(){
//    var start_date=$("#start_date").val();
//    var end_date=$("#end_date").val();
    var event_no=$("#event_no").val();
    var event_content=encodeURIComponent($("#event_content").val());
    //var qianshou_status=$("#qianshou_status").val();
    //var caller_nature=$("#caller_nature").ligerComboBox('getValue');

    manager.set({
        url:'eventWf/WkrjEventWf/getWfNotReplyList?event_no='+event_no+'&event_content='+event_content,
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
//    $("#caller_nature").ligerComboBox('setText','');
//    $("#caller_nature_val").val('');
}