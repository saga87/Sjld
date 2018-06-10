<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<%@include file="/systemdev/myContext.jsp" %>
 <head>
<script type="text/javascript" src="page/sjc/allEvent/allEvent.js"></script>
<script type="text/javascript" src="system/js/commonStatus.js"></script>
<script src="plug-in/jquery/jquery.autocompleter.js"></script>
    <link rel="stylesheet" href="plug-in/jquery/main.css">
<script type="text/javascript">
 $(function (){
//      $("#start_date").ligerDateEditor({ showTime: false, label: '', labelWidth: 100, labelAlign: 'left' });
//      $("#end_date").ligerDateEditor({ showTime: false, label: '', labelWidth: 100, labelAlign: 'left' });
            var parentID = "02";
            cn_combobox = $("#business_type").ligerComboBox({
                url: 'eventWf/WkrjEventWf/getDataDictionary?parentID='+parentID,
                valueField: 'id',
                textField: 'name',
                width: 150,height: 20,selectBoxWidth: 150,selectBoxHeight: 100
            });
            parentID = "6c3d5d3b-17c4-42ac-b465-370b1ff11b2e";
            $.ajax({
                type:'POST',
                url:'eventWf/WkrjEventWf/getDataDictionary?parentID='+parentID,
                dataType:'json',
                success:function(datas){
                     $('#content_type').autocompleter({                
                         minChars:0,
                         // marker for autocomplete matches
                         highlightMatches: true,
                         // object to local or url to remote search
                         source: datas,
                         // custom template
                         template: '{{ label }}',
                         // show hint
                         hint: false,
                         // abort source if empty field
                         empty: true,
                         // max results
                         callback: function (value, index, selected) {
                             if (selected) {
                                 $("#content_type_hidden").val(selected.label);
                             }
                         }
                     });
                    
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
    margin-left: 7px;
    /* overflow: scroll; */
    width: 120px;
    /* height: 600px; */
    margin-top: -4px
}
</style>
</head>
<body style="overflow:hidden;">
<div id="eventSearchWf_layout" style="width:100%;">    
    <div position="center">
        <div class="search_div">
            <form action="" id="search_form">
            <div class="clear">
	            <!-- <div style="float:left;"><span class="search_name">处理状态：</span>
		            <select name="deal_status" id="deal_status">
			            <option value="">--请选择--</option>
			            <option value="未回复">未回复</option>
			            <option value="已回复">已回复</option>
			            <option value="已完成">已完成</option>
			            <option value="已撤销">已撤销</option>
			            <option value="延时">延时</option>
		            </select>
	            </div> -->
	            <div style="float:left;"><span class="search_name">事件编号：</span><input class="inputtext" type="text" name="event_no" id="event_no" style="width: 146px;height: 16px;">
	            </div>
	            <div style="float:left;"><span class="search_name">关键字：</span><input class="inputtext" type="text" name="event_content" id="event_content" style="width: 146px;height: 16px;">
	            </div>
	            <div style="float:left;"><span class="search_name">业务类型：</span>
	                <div style="width: 150px;display: inline-block;margin-top: 5px;height: 16px;">
	                 <input name="business_type" id="business_type"/>
	                </div>
	            </div>
	            <div style="float:left;"><span class="search_name">受理类别：</span>
	                <div style="width: 150px;display: inline-block;margin-top: 1px;height: 16px;">
	                 <input name="content_type" id="content_type"/>
	                 <input type="hidden" id="content_type_hidden">
	                </div>
	            </div>
	            <div style="float:left;"><span class="search_name">受理时间：</span><input class="Wdate" style="width: 87px;" name="start_date" id="start_date" type="text"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">到<input class="Wdate" style="width: 87px;" name="end_date" id="end_date"   type="text"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	            </div>
	            <div style="float: left;">
	            <input type="button" class="button_c" value="高级搜索" onclick="goSearch()">
	            </div>
	            <div style="float:left;margin-left: -1px;">
	              <input type="button" class="button_c" onclick="goReset()" value="重置">
	            </div>
            </div>
            </form> 
        </div>
        <div id="eventSearchWf_maingrid"></div>
    </div>
</div>
</body>
</html>
