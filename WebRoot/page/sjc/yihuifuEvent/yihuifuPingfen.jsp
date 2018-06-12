<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<!-- <link type="text/css" rel="stylesheet" href="page/sjc/eventReport/mp/css/base.css" />
<link href="page/sjc/eventReport/mp/css/index.css" rel="stylesheet" type="text/css" /> -->
<!-- <script language="javascript" src="plug-in/lodop/LodopFuncs.js"></script> -->
<script type="text/javascript" src="system/js/commonStatus.js"></script>
    <script src="plug-in/jquery/jquery.autocompleter.js"></script>
    <link rel="stylesheet" href="plug-in/jquery/main.css">
<script type="text/javascript">
	var cbd_fileurl;
	var lay;
	$(function() {
		var dialog = frameElement.dialog;
		var dialogData = dialog.get('data');//获取data参数
		$("#eventInfo_repliedWfWindow_form").ligerForm().setData(
				dialogData.content);
		cbd_fileurl = dialogData.content.cbd_fileurl;
		$("#reply_content_div").html(dialogData.content.reply_content);


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
<style type="text/css">
.l-form li {
    float: none;
    overflow: auto;
    text-align: inherit;
    line-height: auto;
    padding: 0;
    padding-top: 2px;
    padding-bottom: 2px;
    position: auto;
}
#autocompleter-1,#autocompleter-2{
    margin-left:604px;
    width:195px;
}
</style>
</head>
<body>
<div id="replied_eventWf_layout" style="width:100%;">
    <div position="left" style="height:100%;overflow: scroll;">
        <form id="eventInfo_repliedWfWindow_form" method="get" class="liger-form">
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
		        <!-- <div class="Co">
		            <label>&nbsp;&nbsp;&nbsp;&nbsp;事件备注:</label>
		            <div class="r_div">
		                <textarea name="event_other" id="event_other" style="height:100px;width:700px;" disabled></textarea>
		            </div>
		        </div> -->
		        <div class="Co" style="" id="zhuanban_div">
		            <label><span class="text_red" >*</span>&nbsp;&nbsp;受理单位:</label>
		            <input type="text" name="dept_name" id="chengban_dept" disabled/>
		            <input type="hidden" name="chengban_dept_hidden" id="chengban_dept_hidden" />
		        </div>
		        <div class="Co" style="" id="">
		            <label><span class="text_red" >*</span>回复内容:</label>
		            <div class="r_div">
		                <textarea name="reply_content" id="" style="height:100px;width:700px;" disabled></textarea>
		            </div>
		        </div>
		        <div class="Co" style="" id="satisfy_div">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;满意度:</label>
                    <input type="radio" name="satisfy_status" value="1"/>满意
                    <input type="radio" name="satisfy_status" value="2"/>基本满意
                    <input type="radio" name="satisfy_status" value="3"/>不满意
                </div>
                <div class="Co" style="display:none" id="back_div">
		            <label>&nbsp;&nbsp;&nbsp;&nbsp;退回理由:</label>
		            <div class="r_div">
		                <textarea name="opt_content" id="back_reason" style="height:100px;width:700px;" ></textarea>
		            </div>
		        </div>
		        <input type="hidden" name="event_id" id="event_id">
		        <input type="hidden" name="event_no" id="event_no">
		        <input type="hidden" name="zhuanbanornot" id="zhuanbanornot">
		        <input type="hidden" name="chengban_dept">
		        <input type="hidden" name="qianshou_status">
		        <input type="hidden" name="event_inputtime">
            </div>
        </form>
    </div>
</div>
</body>
</html>