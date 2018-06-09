<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<%@include file="/systemdev/myContext.jsp" %>
 <head>
<script type="text/javascript" src="page/sjc/beicuiEvent/beicuiEvent.js"></script>
<script type="text/javascript" src="system/js/commonStatus.js"></script>
<script type="text/javascript">
 $(function (){
     $("#start_date").ligerDateEditor({ showTime: false, label: '', labelWidth: 100, labelAlign: 'left' });
     $("#end_date").ligerDateEditor({ showTime: false, label: '', labelWidth: 100, labelAlign: 'left' });
 });
 </script>
</head>
<body style="overflow:hidden;">
<div id="beicuiEvent_layout" style="width:100%;">    
    <div position="center">
        <div id="beicuiEvent_maingrid"></div>
    </div>
</div>
</body>
</html>
