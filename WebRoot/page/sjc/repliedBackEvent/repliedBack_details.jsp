<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
  <script src="plug-in/jquery/jquery.autocompleter.js"></script>
  <link rel="stylesheet" href="plug-in/jquery/main.css">
<script type="text/javascript">
$(function (){
    var dialog = frameElement.dialog;
    var dialogData = dialog.get('data');//获取data参数
    $("#eventInfo_backedWfWindow_form").ligerForm().setData(dialogData.content);
    //$("#reply_content_div").html(dialogData.content.reply_content);
    //var messagea_ue=UE.getEditor("reply_content");
    $("#zhuanban_instruction").val('');
    $("#center_opinion").val('');
    
                             var user_secret = [{ id: 1, text: '显示用户信息' },{ id: 0, text: '隐藏用户信息' }];
                             $("#user_secret").ligerComboBox({ width : 300, height : 30, selectBoxWidth : 300, selectBoxHeight : 100, 
                                data: user_secret, isMultiSelect: false, valueField: 'id' });
		
		$(":radio").click(function(){
		    if ($(this).val() == 1) {
		    	$("#zhuanban_div").show();
		    	var cd_combobox = $("#chengban_dept").ligerComboBox({
                    url: 'eventReport/WkrjEventReport/getDeptTree',
                    valueField: 'dept_id',
                    textField: 'dept_name',
                    width: 150,height: 30,selectBoxWidth: 150,selectBoxHeight: 200
                 });
		    	cd_combobox.setValue(dialogData.content.chengban_dept);
		    	
		    	var parentID = "deal_days";
		        var deal_days_combobox = $("#deal_days")
		                .ligerComboBox(
		                        {
		                            url : 'eventReport/WkrjEventReport/getDataDictionary?parentID='
		                                    + parentID,
		                            valueField : 'id',
		                            textField : 'name',
		                            width : 150,
		                            height : 30,
		                            selectBoxWidth : 150,
		                            selectBoxHeight : 100
		                        });
		        deal_days_combobox.setValue(dialogData.content.deal_days);

		        parentID = "6c3d5d3b-17c4-42ac-b465-370b1ff11b2e";
		        $.ajax({
		            type : 'POST',
		            url : 'eventReport/WkrjEventReport/getDataDictionary?parentID='
		                    + parentID,
		            dataType : 'json',
		            success : function(datas) {
		                $('#content_type').autocompleter({
		                    minChars : 0,
		                    // marker for autocomplete matches
		                    highlightMatches : true,
		                    // object to local or url to remote search
		                    source : datas,
		                    // custom template
		                    template : '{{ label }}',
		                    // show hint
		                    hint : false,
		                    // abort source if empty field
		                    empty : true,
		                    // max results
		                    callback : function(value, index, selected) {
		                        if (selected) {
		                            $("#content_type_hidden").val(selected.label);
		                        }
		                    }
		                });

		            }
		        });
		    	$("#zhuanban_instruction_div").show();
		    } else {
		    	$("#zhuanban_div").hide();
                $("#zhuanban_instruction_div").hide();
		    }
		});
	});
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
<form id="eventInfo_backedWfWindow_form" method="get" class="liger-form">
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
                <div class="Co" style="" id="">
                    <label><span class="text_red" >*</span>回复内容:</label>
                    <div class="r_div">
                        <textarea name="reply_content" id="" style="height:100px;width:700px;" disabled></textarea>
                    </div>
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
</body>
</html>