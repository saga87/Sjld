
var url="";
var lay;
var manager;
var perm;
$(function(){

    lay=$("#notDealEventWf_layout").ligerLayout({rightWidth:560,isRightCollapse: true});
    
    manager = $("#notDealEventWf_maingrid").ligerGrid({
        url:'eventWf/WkrjEventWf/getWfNotDealList',
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
                url : "page/sjc/dealingEvent/dealingDetails.jsp",
                //data : JSON.stringify(r),
                width : 900,
                height :650,
                data: {
                    content:data
                },
                buttons : [ /*{
                    text : '撤销',
                    onclick : function(item, dialog) {
                    	top.$.ligerDialog.confirm("确定要撤销吗？",function(flag){
                            if (flag) {
		                        $.ajax({
		                            url: "eventWf/WkrjEventWf/chexiaoEventWf",
		                            data: { event_id: data.event_id },
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
                        });
                    }
                },*/ {
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
//  var start_date=$("#start_date").val();
//  var end_date=$("#end_date").val();
  var event_no=$("#event_no").val();
  var event_content=encodeURIComponent($("#event_content").val());
  //var qianshou_status=$("#qianshou_status").val();
  //var caller_nature=$("#caller_nature").ligerComboBox('getValue');

  manager.set({
      url:'eventWf/WkrjEventWf/getWfNotDealList?event_no='+event_no+'&event_content='+event_content,
      //pageSize:10,
      page:1
  });
  manager.reload(1);
}

function goReset(){
//  $("#start_date").val("");
//  $("#end_date").val("");
//  $("#deal_status").val("");
  $("#event_no").val("");
  $("#event_content").val("");
//  $("#caller_nature").ligerComboBox('setText','');
//  $("#caller_nature_val").val('');
//  $("#content_type_val").val('');    
}