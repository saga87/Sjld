<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<script language="javascript" src="plug-in/lodop/LodopFuncs.js"></script>
<script type="text/javascript" src="system/js/commonStatus.js"></script>
<script type="text/javascript">
	var cbd_fileurl;
	$(function() {
		var dialog = frameElement.dialog;
		var dialogData = dialog.get('data');//获取data参数
		$("#event_EditWfWindow_form").ligerForm().setData(dialogData.content);
		cbd_fileurl = dialogData.content.cbd_fileurl;
		$("#reply_content_div").html(dialogData.content.reply_content);
		//var messagea_ue=UE.getEditor("reply_content");
		if (dialogData.content.reply_time != null
				&& dialogData.content.reply_time != "") {
			$("#dept_reply_div").show();
			//$("#deal_opinion_div").hide();
		}
		if (dialogData.content.duban_opinion != null
				&& dialogData.content.duban_opinion != "") {
			$("#duban_opinion_div").show();
		}
	});
	/*
	 * 预览
	 */
	function preview() {
		var event_id = $("#event_id").val();
		print(event_id);
	}
</script>


</head>
<body>
<form id="event_EditWfWindow_form" method="get" class="liger-form">
        <div class="Co">
            <label><span class="text_red" >*</span>&nbsp;&nbsp;投诉标题:</label>
            <input type="text" id="event_title" name="event_title" />
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;&nbsp;&nbsp;投诉内容:</label>
            <div class="r_div">
                <textarea id="event_content" name="event_content" style="height:100px;width:700px;"></textarea>
            </div>
        </div>
        <!-- <div class="Co" style="" id="zhuanban_div">
            <label><span class="text_red" >*</span>&nbsp;&nbsp;承办单位:</label>
            <input type="text" name="chengban_dept" id="chengban_dept" />
            <input type="hidden" name="chengban_dept_hidden" id="chengban_dept_hidden" />
        </div> -->
        <div class="Co">
            <label>&nbsp;&nbsp;&nbsp;&nbsp;事件备注:</label>
            <div class="r_div">
                <textarea name="event_other" id="event_other" style="height:100px;width:700px;"></textarea>
            </div>
        </div>
        <input type="hidden" name="event_id" id="event_id">
    </div>
</form>
</body>
</html>