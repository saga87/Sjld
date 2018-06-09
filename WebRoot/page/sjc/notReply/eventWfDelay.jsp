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
        <div class="Co">
            <label>&nbsp;&nbsp;&nbsp;&nbsp;延时理由:</label>
            <div class="r_div">
                <textarea name="opt_content" id="delay_reason" style="height:100px;width:700px;" ></textarea>
            </div>
        </div>
        <input type="hidden" name="event_id" id="event_id">
        <input type="hidden" name="event_no" id="event_no">
    </div>
</form>
</body>
</html>