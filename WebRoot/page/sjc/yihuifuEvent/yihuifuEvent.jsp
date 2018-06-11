<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<%@include file="/systemdev/myContext.jsp" %>
 <head>
<script type="text/javascript" src="page/sjc/yihuifuEvent/yihuifuEvent.js"></script>
<script type="text/javascript" src="system/js/commonStatus.js"></script>
<script type="text/javascript">
 $(function (){
     $("#start_date").ligerDateEditor({ showTime: false, label: '', labelWidth: 100, labelAlign: 'left' });
     $("#end_date").ligerDateEditor({ showTime: false, label: '', labelWidth: 100, labelAlign: 'left' });
 });
 </script>
</head>
<body style="overflow:hidden;">
<div id="repliedEventWf_layout" style="width:100%;">   
    <div position="center">
        <!-- <div class="search_div">
            <form action="" id="search_form">
                <div class="clear">
                    <div style="float:left;"><span class="search_name">事件编号：</span><input class="inputtext" type="text" name="event_no" id="event_no" style="width: 146px;height: 23px;">
                    </div>
                    <div style="float:left;"><span class="search_name">关键字：</span><input class="inputtext" type="text" name="event_content" id="event_content" style="width: 146px;height: 23px;">
                    </div>
                    <div style="float: left;">
                        <input type="button" class="button_c" value="高级搜索" onclick="goSearch()">
                    </div>
                    <div style="float:left;margin-left: -1px;">
                        <input type="button" class="button_c" onclick="goReset()" value="重置">
                    </div>
                </div>
            </form> 
        </div> -->
        <div id="repliedEventWf_maingrid"></div>
    </div>
</div>
</body>
</html>
