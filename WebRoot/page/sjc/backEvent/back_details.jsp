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
            <label>&nbsp;&nbsp;编号:</label>
            <input type="text" name="event_no" disabled/>
            <label>&nbsp;&nbsp;状态:</label>
            <input type="text" name="event_status" disabled/>
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;用户姓名:</label>
            <input type="text" name="caller_username" disabled/>
            <label>&nbsp;&nbsp;用户电话:</label>
            <input type="text" name="caller_tel" disabled/>
        </div>
        <!-- <div class="Co">
            <label>&nbsp;&nbsp;用户地址:</label>
            <input type="text" name="address2" disabled/>
            <input type="hidden" name="address1"/>
        </div> -->
        <div class="Co">
            <label>&nbsp;&nbsp;受理工号:</label>
            <input type="text" name="accept_workno" disabled/>
            <label>&nbsp;&nbsp;受理时间:</label>
            <input type="text" name="event_inputtime" disabled/>
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;内容分类:</label>
            <input type="text" name="content_type" disabled/>
            <label>&nbsp;&nbsp;来电性质:</label>
            <input type="text" name="nature" disabled/>
            <input type="hidden" name="caller_nature"/>
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;&nbsp;&nbsp;具体要求:</label>
            <div class="r_div">
                <textarea name="event_content" style="height:100px;width:700px;background:#D0D0D0" disabled></textarea>
            </div>
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;部门:</label>
            <input type="text" name="dept_name" disabled/><input type="hidden" name="chengban_dept" id="chengban_dept_old" />
            <label><span class="text_red" >*</span>&nbsp;&nbsp;退回时间:</label>
            <input type="text" name="reply_time" disabled/>
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;&nbsp;&nbsp;退回理由:</label>
            <div class="r_div" id="reply_content_div" style="width:670px;">
                <textarea name="reply_content" id="reply_content" style="height:100px;width:700px;" disabled></textarea>
            </div>
        </div>
        <div class="Co">
            <label><span class="text_red" >*</span>审核结果:</label>
            <input type="radio" name="shenhe_status" value="1" /><span style="">通过</span>
            <input type="radio" name="shenhe_status" value="2" checked="checked"/><span style="">未通过</span>
        </div>
        <div class="Co" style="display:none" id="zhuanban_div">
            <label><span class="text_red" >*</span>&nbsp;&nbsp;承办单位:</label>
            <input type="text" name="" id="chengban_dept" />
            <label><span class="text_red" >*</span>处理时限:</label>
            <input type="text" name="deal_days" id="deal_days" />
            <label><span class="text_red" >*</span>内容分类:</label>
            <input type="text" name="content_type" id="content_type" style="width:195px;"/>
            <input type="hidden" id="content_type_hidden" name="content_type_hidden"/>
        </div>
        <div class="Co" style="display:none" id="zhuanban_instruction_div">
            <label>&nbsp;&nbsp;&nbsp;&nbsp;转办说明:</label>
            <div class="r_div">
                <textarea name="zhuanban_instruction" id="zhuanban_instruction" style="height:100px;width:700px;"></textarea>
            </div>
        </div>
        <!-- <div class="Co" style="display:none" id="center_opinion_div">
            <label>&nbsp;&nbsp;&nbsp;&nbsp;中心意见:</label>
            <div class="r_div">
                <textarea name="center_opinion" id="center_opinion" style="height:100px;width:700px;"></textarea>
            </div>
        </div> -->
        <input type="hidden" name="event_id" id="event_id">
        <input type="hidden" name="event_no" id="event_no">
        <input type="hidden" name="accept_workno" id="accept_workno">
        <input type="hidden" name="event_content" id="event_content">
        <input type="hidden" name="caller_username" id="caller_username">
        <input type="hidden" name="caller_tel" id="caller_tel">
        <input type="hidden" name="address2" id="address2">
        <input type="hidden" name="event_other" id="event_other">
        <input type="hidden" name="chengban_dept" id="chengban_dept_old">
        <input type="hidden" name="cbd_fileurl" id="cbd_fileurl">
        <input type="hidden" name="cbdtime" id="cbdtime">
        <input type="hidden" name="cbd_leaderpishi" id="cbd_leaderpishi">
        <input type="hidden" name="cbd_dealresult" id="cbd_dealresult">
        <input type="hidden" name="cbd_zbzx" id="cbd_zbzx">
        <input type="hidden" name="cbd_no" id="cbd_no">
        <input type="hidden" name="zhuanbanornot" id="zhuanbanornot">
    </div>
</form>
</body>
</html>