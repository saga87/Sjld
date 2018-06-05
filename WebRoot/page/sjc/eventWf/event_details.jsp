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
<form id="eventInfoWfWindow_form" method="get" class="liger-form">
    <!-- <div id="accept_user"></div> -->
    <div style="overflow:hidden;">
        <div class="Co">
            <label>&nbsp;&nbsp;内容分类:</label>
            <input type="text" name="content_type" id="content_type" disabled/>
            <label>&nbsp;&nbsp;来电性质:</label>
            <input type="text" name="nature" id="nature" disabled/>
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;处理时限:</label>
            <input type="text" name="deal_days" id="deal_days" disabled/><a href="javascipt:void(0);" onclick="preview()">查看承办单</a>
        </div>
        <!-- <div class="Co">
            <label>&nbsp;&nbsp;时间:</label>
            <input type="text" name="event_inputtime" disabled/>
        </div> -->
        <div class="Co">
            <label>&nbsp;&nbsp;&nbsp;&nbsp;事件内容:</label>
            <div class="r_div">
                <textarea name="event_content" style="height:100px;width:700px;background:#D0D0D0" disabled></textarea>
            </div>
        </div>
        <div id="dept_reply_div" style="display:none">
            <div class="Co">
                <label>&nbsp;&nbsp;部门:</label>
                <input type="text" name="dept_name" disabled/><input type="hidden" name="chengban_dept" id="chengban_dept_old"/>
                <label>&nbsp;&nbsp;回复时间:</label>
                <input type="text" name="reply_time" disabled/>
            </div>
            <div class="Co">
                <label>部门领导批示:</label>
                <div class="r_div">
                    <textarea name="leader_pishi" style="height:100px;width:700px;background:#D0D0D0" disabled></textarea>
                </div>
            </div>
            <div class="Co">
                <label>&nbsp;&nbsp;&nbsp;&nbsp;处理结果:</label>
                <div class="r_div" id="reply_content_div" style="width:670px;">
                    <!-- <textarea name="reply_content" id="reply_content" style="height:200px;width:690px;" disabled></textarea> -->
                </div>
            </div>
        </div>
            <div class="Co" id="duban_opinion_div" style="display:none">
                <label>督办意见:</label>
                <div class="r_div">
                    <textarea name="duban_opinion" style="height:100px;width:700px;background:#D0D0D0" disabled></textarea>
                </div>
            </div>
        <input type="hidden" name="event_id" id="event_id">
    </div>
</form>
</body>
</html>