<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/systemdev/myContext.jsp" %>
<!doctype html>
<html lang="en">
 <head>
 <meta charset="UTF-8">
<script type="text/javascript">
var dialog = frameElement.dialog;
var dialogData = dialog.get('data');//获取data参数
        $(function (){
            //$("#eventInfoWindow_form").ligerForm().setData(dialogData.content);
            //var messagea_ue=UE.getEditor("reply_content");
            //lay=$("#chengbanEvent_layout").ligerLayout({rightWidth:560,isRightCollapse: true});

            perm=$('#eventInfo_grid').ligerGrid({
                        url:'onceFinishS/WkrjOnceFinishS/getEventInfoList',
                        parms :[{name:"dept_id",value:dialogData.content.dept_id},{name:"type",value:dialogData.content.type},{name:"flag",value:dialogData.content.flag},
                                {name:"start_date",value:dialogData.content.start_date},{name:"end_date",value:dialogData.content.end_date}],
                        columns :[
                            { display: '编号', name: 'event_no', id: 'event_no', width: '15%', align: 'center' },
                            { display: '来电内容', name: 'event_content', id: 'event_content', width: '30%', align: 'left' },
                            { display: '时间', name: 'event_inputtime', id: 'event_inputtime', width: '15%', align: 'center' },
                            { display: '内容分类', name: 'content_type', id: 'content_type', width: '20%', align: 'center' },
                            { display: '来电性质', name: 'nature', id: 'nature', width: '20%', align: 'center' }
                        ],
                        usePager : true,
                        alternatingRow : true,
                        rownumbers : true,
                        height : 559,
                        width : 885,
                        toolbar : {
                            items : [ {
                                text : '导出',
                                click : exportExcel,
                                icon : 'excel',
                                id:'onceFinishS/WkrjOnceFinishS/export_banjieEvent'
                            } ]
                        },
                        onDblClickRow: function (data, rowindex, rowobj) {
                        	if (true) {
                            //if (data.event_no.indexOf("W") >= 0) {
                                var s = parent.$.ligerDialog.open({
                                    //target: $("#eventReport_updateWindow_form"),
                                    url : "page/sjc/wfEventSearch/wfEventDetails.jsp",
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
                            } else {
                                var s = parent.$.ligerDialog.open({
                                    //target: $("#eventReport_updateWindow_form"),
                                    url : "page/sjc/eventReport/eventOpRecord.jsp",
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
                        }
                    });
        });
        function exportExcel(){
        //  var business_type = $('#business_type').ligerGetComboBoxManager().getValue();
          var dept_id = dialogData.content.dept_id;
          var flag = dialogData.content.flag;
          var start_date = dialogData.content.start_date;
          var end_date = dialogData.content.end_date;

          $.post('onceFinishS/WkrjOnceFinishS/export_banjieEvent', {
        	  dept_id : dept_id,
        	  flag : flag,
              start_date : start_date,
              end_date : end_date
          }, function(result) {
              var result = eval('(' + result + ')');
              result = eval('(' + result + ')');
              if (result.success) {
                  window.location.href = "upload/" + result.file_name;
              } else {
                  $.ligerDialog.alert('导出失败');
              }
          });
        }
</script>
</head>
<body>
<div id="chengbanEvent_layout" style="width:900px;">
    <div position="center">
        <div id="eventInfo_grid"></div>
    </div>
</div>
</body>
</html>