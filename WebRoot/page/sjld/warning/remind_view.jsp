<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="page/sjld/warning/js/remind.js"></script>
<script type="text/javascript">
	$(function() {
		var dialog = frameElement.dialog;
		var dialogData = dialog.get('data');//获取data参数
		$("#viewremind_form").ligerForm().setData(dialogData.content);
	});
</script>


</head>
<body>
<form id="viewremind_form" method="get" class="liger-form">
		
		<div class="Co">
            <label>&nbsp;&nbsp;编号:</label>
            <input type="text" id="event_no" name="event_no" disabled="disabled"/>
        </div>

        <div class="Co">
            <label>&nbsp;&nbsp;投诉标题:</label>
            <input type="text" id="event_title" name="event_title" disabled="disabled"/>
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;&nbsp;&nbsp;投诉内容:</label>
            <div class="r_div">
                <textarea id="event_content" disabled="disabled" name="event_content" style="height:150px;width:700px;"></textarea>
            </div>
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;&nbsp;&nbsp;事件备注:</label>
            <div class="r_div">
                <textarea name="event_other" disabled="disabled" id="event_other" style="height:150px;width:700px;"></textarea>
            </div>
        </div>
    </div>
</form>
</body>
</html>