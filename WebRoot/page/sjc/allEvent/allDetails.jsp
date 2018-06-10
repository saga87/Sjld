<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<!-- <script language="javascript" src="plug-in/lodop/LodopFuncs.js"></script> -->
<script type="text/javascript" src="system/js/commonStatus.js"></script>
<script type="text/javascript">
	var cbd_fileurl;
	$(function() {
		var dialog = frameElement.dialog;
		var dialogData = dialog.get('data');//获取data参数
		$("#eventInfoWfWindow_form").ligerForm().setData(dialogData.content);
		cbd_fileurl = dialogData.content.cbd_fileurl;

		//var messagea_ue=UE.getEditor("reply_content");
		$("#reply_content_div").html(dialogData.content.reply_content);
		if (dialogData.content.reply_time != null
				&& dialogData.content.reply_time != "") {
			$("#dept_reply_div").show();
			if (dialogData.content.huifang_record != null
					&& dialogData.content.huifang_record != "") {
				$("#huifang_record_div").show();
			}
		}
		
		if (dialogData.content.deal_opinion != null
                && dialogData.content.deal_opinion != "") {
            $("#deal_opinion_div").show();
        }
		
		if (dialogData.content.duban_opinion != null
                && dialogData.content.duban_opinion != "") {
            $("#duban_opinion_div").show();
        }
		
// 		$.ajax({
//             type : 'POST',
//             url : 'eventWf/WkrjEventWf/getReplyFileInfoWf?event_id='
//                     + dialogData.content.event_id,
//             dataType : 'json',
//             success : function(result) {
//                 if (result.success) {
//                     $("#replyFileDiv").show();
//                     var d=eval(result);//解析
//                     $(d.obj).each(function(index,obj){  
//                     	$("#file_show_div").append($('<span style="margin-left:10px"><a href="javascipt:void(0);" onclick="previewOF(\''+obj.file_xname+'\',\''+obj.file_yname+'\')">'+obj.file_yname+'</a></span>'));//后台数据加到下拉框
//                     });
//                 } else {
//                 }
//             }
//         });

		perm = $('#eventInfoWf_grid')
				.ligerGrid(
						{
							url : 'eventWf/WkrjEventWf/getReplyInfoWfList?event_id='
									+ dialogData.content.event_id
									+ '&dept_name='
									+ dialogData.content.dept_name
									+ '&input_time='
									+ dialogData.content.event_inputtime
									+ '&event_no='
									+ dialogData.content.event_no
									+ '&qianshou_status='
									+ dialogData.content.qianshou_status
									+ '&qianshou_time='
									+ dialogData.content.qianshoutime
									+ '&event_status='
									+ dialogData.content.event_status
									+ '&delay_time='
                                    + dialogData.content.delay_time
                                    + '&opt_time='
                                    + dialogData.content.opttime,
							//                 parms :[{name:"event_id",value:dialogData.content.event_id,name:"dept_name",value:dialogData.content.dept_name,name:"accept_workno",value:dialogData.content.accept_workno,
							//                 name:"input_time",value:dialogData.content.event_inputtime,name:"cbd_no",value:dialogData.content.cbd_no,name:"qianshou_status",value:dialogData.content.qianshou_status,
							//                 name:"qianshou_time",value:dialogData.content.qianshou_time}],
							columns : [ {
								display : '操作结果',
								name : 'opt_type',
								id : 'opt_type',
								width : '20%',
								align : 'center', render : typeTrans
							}, {
								display : '操作人',
								name : 'user_realname',
								id : 'user_realname',
								width : '20%',
								align : 'center'
							}, {
								display : '内容',
								name : 'opt_content',
								id : 'opt_content',
								width : '25%',
								align : 'center'
							}, {
								display : '操作时间',
								name : 'opttime',
								id : 'opttime',
								width : '20%',
								align : 'center'
							}, {
								display : '编号',
								name : 'event_no',
								id : 'event_no',
								width : '15%',
								align : 'center'
							}
							//{ display: '操作建议', name: 'opt_suggestion', id: 'opt_suggestion', width: '25%', align: 'center' }
							],
							usePager : true,
							alternatingRow : true,
							rownumbers : true,
							height : '40%',
							width : 868
						});
	});
	/*
	 * 预览
	 */
	function preview() {
		var event_id = $("#event_id").val();
		print(event_id);
	}
	/*
	 * 预览
	 */
	function previewOF(file_xname,file_yname) {
	    var openwin = document.getElementById("sjcpopwindow");
	    //var exty = file_yname.split(".")[1];
	    var index = file_yname.lastIndexOf(".");
	    var exty = file_yname.substring(index+1);
	    var extx = file_xname.split(".")[1];
	    if (file_xname != null && file_xname != "") {
	        if (exty == extx) {//如果扩展名相同说明附件是图片，则直接预览
	            openwin.href = basePath_szrx_dept + "upload/deptOpinion/" + file_xname;
	        } else {//如果不同说明附件是文档，则下载
	            openwin.href = basePath_szrx_dept + "upload/deptOpinion/" + file_xname.split(".")[0] + "." + exty;
	        }
	        openwin.click();
	    }
	}
</script>

</head>
<body>
<form id="eventInfoWfWindow_form" method="get" class="liger-form">
    <div style="overflow:hidden;">
                <div class="Co">
                    <label><span class="text_red" >*</span>&nbsp;&nbsp;受理渠道:</label>
                    <input type="text" name="source" id="source" disabled/>
                    <input type="hidden" id="accept_source" name="accept_source">
                    <label><span class="text_red" >*</span>&nbsp;&nbsp;处理时限:</label>
                    <input type="text" id="deal_days" name="deal_days" disabled/>
                </div>
                <div class="Co">
                    <label><span class="text_red" >*</span>&nbsp;&nbsp;受理类别:</label>
                    <input type="text" name="content_type" id="content_type" data-options="method:'post'" disabled/>
                    <input type="hidden" id="content_type_hidden">
                    <label><span class="text_red" >*</span>&nbsp;&nbsp;业务类型:</label>
                    <input type="text" id="nature" name="nature" disabled/>
                    <input type="hidden" id="business_type" name="business_type">
                </div>
                <div class="Co">
                    <label><span class="text_red" >*</span>&nbsp;&nbsp;投诉标题:</label>
                    <input type="text" id="event_title" name="event_title" style="width:700px;" disabled/>
                </div>
                <div class="Co">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;投诉内容:</label>
                    <div class="r_div">
                        <textarea id="event_content" name="event_content" style="height:100px;width:700px;" disabled></textarea>
                    </div>
                </div>
                <div class="Co">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;事件备注:</label>
                    <div class="r_div">
                        <textarea name="event_other" id="event_other" style="height:100px;width:700px;" disabled></textarea>
                    </div>
                </div>
                <div class="Co" style="" id="zhuanban_div">
                    <label><span class="text_red" >*</span>&nbsp;&nbsp;受理单位:</label>
                    <input type="text" name="dept_name" id="chengban_dept" disabled/>
                    <input type="hidden" name="chengban_dept_hidden" id="chengban_dept_hidden" />
                </div>
        <input type="hidden" name="event_id" id="event_id">
    </div>
</form>
    <div position="center">
        <div id="eventInfoWf_grid"></div>
    </div>
</body>
</html>