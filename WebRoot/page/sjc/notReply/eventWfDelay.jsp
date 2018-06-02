<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
<script type="text/javascript">
var cbd_fileurl;
$(function (){
    var dialog = frameElement.dialog;
    var dialogData = dialog.get('data');//获取data参数
    $("#wfNotReply_delayWindow_form").ligerForm().setData(dialogData.content);
    cbd_fileurl = dialogData.content.cbd_fileurl;
});
/*
 * 预览
 */
function preview(){
    var openwin = document.getElementById("sjcpopwindow");
    if (cbd_fileurl != null && cbd_fileurl != "") {
        openwin.href = cbd_fileurl.replace(".doc",".pdf");
        openwin.click();
    } else {
        top.$.ligerDialog.alert("此事件无承办单");
    }
}
</script>


</head>
<body>
<form id="wfNotReply_delayWindow_form" method="get" class="liger-form">
    <!-- <div id="accept_user"></div> -->
    <div style="overflow:hidden;">
        <div class="Co">
            <label>&nbsp;&nbsp;来电姓名:</label>
            <input type="text" name="caller_username" disabled/>
            <label>&nbsp;&nbsp;来电号码:</label>
            <input type="text" name="caller_tel" disabled/>
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;&nbsp;&nbsp;电话内容:</label>
            <div class="r_div">
                <textarea name="event_content" style="height:100px;width:700px;background:#D0D0D0" disabled></textarea>
            </div>
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;内容分类:</label>
            <input type="text" name="content_type" disabled/>
            <label>&nbsp;&nbsp;来电性质:</label>
            <input type="text" name="nature" disabled/>
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;受理时间:</label>
            <input type="text" name="event_inputtime" disabled/>
            <label>&nbsp;&nbsp;处理时限:</label>
            <input type="text" name="deal_days" disabled/><!-- <a href="javascipt:void(0);" onclick="preview()">查看承办单</a> -->
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;&nbsp;&nbsp;备注:</label>
            <div class="r_div">
                <textarea name="event_other" style="height:100px;width:700px;background:#D0D0D0" disabled></textarea>
            </div>
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;&nbsp;&nbsp;延时理由:</label>
            <div class="r_div">
                <textarea name="delay_reason" style="height:100px;width:700px;" ></textarea>
            </div>
        </div>
        <input type="hidden" name="event_id" id="event_id">
        <input type="hidden" name="event_no" id="event_no">
    </div>
</form>
</body>
</html>