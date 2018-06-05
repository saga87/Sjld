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
		$("#eventInfo_notAcceptWindow_form").ligerForm().setData(
				dialogData.content);
		cbd_fileurl = dialogData.content.cbd_fileurl;
	});
	/*
	 * 预览
	 */
	function preview() {
		//     var openwin = document.getElementById("sjcpopwindow");
		//     if (cbd_fileurl != null && cbd_fileurl != "") {
		//         openwin.href = basePath_szrx+cbd_fileurl.replace(".doc",".pdf");
		//         openwin.click();
		//     } else {
		//         top.$.ligerDialog.alert("此事件无承办单");
		//     }
		var event_id = $("#event_id").val();
		print(event_id);
	}
</script>


</head>
<body>
<form id="eventInfo_notAcceptWindow_form" method="get" class="liger-form">
    <div style="overflow:hidden;">
        <div class="Co">
            <label>&nbsp;&nbsp;来电姓名:</label>
            <input type="text" name="caller_username" id="caller_username" disabled/>
            <label>&nbsp;&nbsp;来电号码:</label>
            <input type="text" name="caller_tel" id="caller_tel" disabled/>
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;受理时间:</label>
            <input type="text" name="event_inputtime" disabled/><a href="javascipt:void(0);" onclick="preview()">查看承办单</a>
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;内容分类:</label>
            <input type="text" name="content_type" disabled/>
            <label>&nbsp;&nbsp;来电性质:</label>
            <input type="text" name="nature" disabled/>
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;&nbsp;&nbsp;电话内容:</label>
            <div class="r_div">
                <textarea name="event_content" style="height:100px;width:700px;background:#D0D0D0" disabled></textarea>
            </div>
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;&nbsp;&nbsp;转办说明:</label>
            <div class="r_div">
                <textarea name="zhuanban_instruction" style="height:100px;width:700px;background:#D0D0D0" disabled></textarea>
            </div>
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;&nbsp;&nbsp;退回理由:</label>
            <div class="r_div">
                <textarea name="reply_content" style="height:100px;width:700px;" ></textarea>
            </div>
        </div>
        <input type="hidden" name="event_id" id="event_id">
    </div>
</form>
</body>
</html>