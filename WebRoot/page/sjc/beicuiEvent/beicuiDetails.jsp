<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
<script type="text/javascript">
$(function (){
    var dialog = frameElement.dialog;
    var dialogData = dialog.get('data');//获取data参数
    $("#eventInfo_overtimeWindow_form").ligerForm().setData(dialogData.content);
    $("#reply_content_div").html(dialogData.content.reply_content);
    //var messagea_ue=UE.getEditor("reply_content");
 if (dialogData.content.reply_time != null && dialogData.content.reply_time != "") {
     $("#dept_reply_div").show();
     $("#deal_opinion_div").hide();
 }
 
});
</script>


</head>
<body>
<form id="eventInfo_overtimeWindow_form" method="get" class="liger-form">
    <div style="overflow:hidden;">
        <div class="Co">
            <label><span class="text_red" >*</span>&nbsp;&nbsp;受理渠道:</label>
            <input type="text" name="source" id="source" disabled/>
            <label><span class="text_red" >*</span>&nbsp;&nbsp;处理时限:</label>
            <input type="text" id="deal_days" name="deal_days" disabled/>
        </div>
        <div class="Co">
            <label><span class="text_red" >*</span>&nbsp;&nbsp;受理类别:</label>
            <input type="text" name="content_type" id="content_type" data-options="method:'post'" disabled/>
            <input type="hidden" id="content_type_hidden">
            <label><span class="text_red" >*</span>&nbsp;&nbsp;业务类型:</label>
            <input type="text" id="nature" name="nature" disabled/>
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
        <div class="Co" style="" id="zhuanban_div">
            <label><span class="text_red" >*</span>&nbsp;&nbsp;受理单位:</label>
            <input type="text" name="dept_name" id="chengban_dept" disabled/>
            <input type="hidden" name="chengban_dept_hidden" id="chengban_dept_hidden" />
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;&nbsp;&nbsp;事件备注:</label>
            <div class="r_div">
                <textarea name="event_other" id="event_other" style="height:100px;width:700px;" disabled></textarea>
            </div>
        </div>
        <input type="hidden" name="event_id" id="event_id">
    </div>
</form>
</body>
</html>