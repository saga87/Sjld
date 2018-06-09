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
		$("#eventInfo_delayedWfWindow_form").ligerForm().setData(
				dialogData.content);
		cbd_fileurl = dialogData.content.cbd_fileurl;
		//$("#zhuanban_instruction").val('');

// 		var cd_combobox = $("#chengban_dept").ligerComboBox({
// 			url : 'eventWf/WkrjEventWf/getDeptTree',
// 			valueField : 'dept_id',
// 			textField : 'dept_name',
// 			width : 300,
// 			height : 30,
// 			selectBoxWidth : 300,
// 			selectBoxHeight : 200
// 		});
// 		cd_combobox.setValue(dialogData.content.chengban_dept);
		//                              var user_secret = [{ id: 1, text: '显示用户信息' },{ id: 0, text: '隐藏用户信息' }];
		//                              $("#user_secret").ligerComboBox({ width : 300, height : 30, selectBoxWidth : 300, selectBoxHeight : 100, 
		//                                 data: user_secret, isMultiSelect: false, valueField: 'id' });
		var parentID = "deal_days";
        var deal_days_combobox = $("#deal_days").ligerComboBox({
            url: 'eventWf/WkrjEventWf/getDataDictionary?parentID='+parentID,
            valueField: 'id',
            textField: 'name',
            width: 240,height: 30,selectBoxWidth: 240,selectBoxHeight: 100
        });
        deal_days_combobox
        .setValue(dialogData.content.deal_days);
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
<form id="eventInfo_delayedWfWindow_form" method="get" class="liger-form">
    <div style="overflow:hidden;">
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
                    <label><span class="text_red" >*</span>&nbsp;&nbsp;受理渠道:</label>
                    <input type="text" name="source" id="source" disabled/>
                    <input type="hidden" id="accept_source" name="accept_source">
                </div>
                <div class="Co" style="" id="">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;延时理由:</label>
                    <div class="r_div">
                        <textarea name="opt_content" id="delay_reason" style="height:100px;width:700px;" disabled></textarea>
                    </div>
                </div>
                <div class="Co">
                    <label><span class="text_red" >*</span>&nbsp;&nbsp;处理时限:</label>
                    <input type="text" id="deal_days" name="" />
                    <input type="hidden" id="deal_days_old" name="deal_days" />
                </div>
        <input type="hidden" name="event_id" id="event_id">
        <input type="hidden" name="event_no" id="event_no">
    </div>
</form>
</body>
</html>