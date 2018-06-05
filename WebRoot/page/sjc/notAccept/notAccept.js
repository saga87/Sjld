
var url="";
var lay;
var manager;
var perm;
$(function(){

    lay=$("#wfNotAccept_layout").ligerLayout({rightWidth:560,isRightCollapse: true});
    
    manager = $("#wfNotAccept_maingrid").ligerGrid({
        url:'eventWf/WkrjEventWf/getWfNotAcceptList_dept',
        columns: [
        { display: '编号', name: 'event_no', id: 'event_no', width: '10%', align: 'center' },
        { display: '投诉标题', name: 'event_title', id: 'event_title', width: '20%', align: 'center' },
        { display: '受理类别', name: 'content_type', id: 'content_type', width: '10%', align: 'center' },
        { display: '处理时限', name: 'deal_days', id: 'deal_days', width: '5%', align: 'center' },
        { display: '限制期限', name: 'remain_hours', id: 'remain_hours', width: '15%', align: 'center', render: showPic },
        { display: '业务类型', name: 'nature', id: 'nature', width: '10%', align: 'center' },
        { display: '受理时间', name: 'event_inputtime', id: 'event_inputtime', width: '10%', align: 'center' },
        { display: '受理渠道', name: 'source', id: 'source', width: '10%', align: 'center' },
        //{ display: '备注', name: 'event_other', id: 'event_other', width: '9%', align: 'center' },
        { display: '状态', name: 'event_status', id: 'event_status', width: '10%', align: 'center', render: statusTrans }
        //{ display: '接收部门', name: 'dept_name', id: 'dept_name', width: '19%', align: 'center' }
        ], height: '100%',
        width:'99.9%',
        usePager :true,
        rownumbers : true,
        alternatingRow: true,
        toolbar : {
            items : [ {
                text : '接收',
                click : event_sign,
                icon : 'modify',
                id:'eventWf/WkrjEventWf/signEventWf'
            }/*, {
                line : true
            }, {
                text : '退回',
                click : event_sendBack,
                icon : 'delete',
                id:'eventWf/WkrjEventWf/sendBackEventWf'
            }*/ ]
        },
        onDblClickRow: function (data, rowindex, rowobj) {
            var s = parent.$.ligerDialog.open({
                //target: $("#eventReport_updateWindow_form"),
                url : "page/sjc/notAccept/notAccept_details.jsp",
                //data : JSON.stringify(r),
                width : 900,
                height :550,
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

function event_sign(row){
    var g = $("#wfNotAccept_maingrid").ligerGetGridManager();
    var r = g.getSelectedRow();
    if (r == undefined) {
        $.ligerDialog.alert('请选择一条记录进行签收!');
        return;
    }
    $.ligerDialog.confirm('确定要签收此记录吗', function (param) {
        if (param) {
            $.ajax({
                url: 'eventWf/WkrjEventWf/signEventWf',
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
                      $.ligerDialog.alert('签收成功!');
                      g.loadData();
                  } else {
                      if (result.msg != null) {
                          $.ligerDialog.alert(result.msg);
                      } else {
                          $.ligerDialog.alert('签收失败!');
                      }
                  }
                }
              });
        }
    })
}

/*
 * date参数是要进行加减的日期，days参数是要加减的天数，如果往前算就传入负数，往后算就传入正数，如果是要进行月份的加减，就调用setMonth()和getMonth()就可以了，需要注意的是返回的月份是从0开始计算的
 */
function addDate(date,days){ 
    var d=new Date(date); 
    d.setDate(d.getDate()+days); 
    var m=d.getMonth()+1; 
    return d.getFullYear()+'-'+m+'-'+d.getDate()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds(); 
} 

function event_sendBack(row){
    var g = $("#wfNotAccept_maingrid").ligerGetGridManager();
    var r = g.getSelectedRow();
    if (r == undefined) {
        $.ligerDialog.alert('请选择一条记录进行退回!');
        return;
    }
    
    //把字符串格式转换为日期类
    var temp_date = addDate(Date.parse(r.event_inputtime),1);
    var curTime = new Date();
    var temp_time = new Date(temp_date);
    if (temp_time <= curTime) {//进行比较
    	$.ligerDialog.alert('受理时间已超过24小时不能退回!');
        return;
    }
    
    var s = parent.$.ligerDialog.open({
        //target: $("#eventReport_updateWindow_form"),
        url : "page/sjc/wfNotAccept/notAcceptDetailsWf.jsp",
        //data : JSON.stringify(r),
        width : 900,
        height :650,
        data: {
            content:r
        },
        buttons : [ {
            text : '确定',
            onclick : function(item, dialog) {
                var data = dialog.frame.liger.get("eventInfo_notAcceptWindow_form").getData();
                if (data.reply_content == null || data.reply_content == "") {
                    top.$.ligerDialog.alert("退回理由不能为空");
                    return;
                }
                //data.importance = dialog.frame.liger.get("importance").getValue();
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