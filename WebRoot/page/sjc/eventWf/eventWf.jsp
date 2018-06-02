<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<%@include file="/systemdev/myContext.jsp" %>
 <head>
<script type="text/javascript" src="page/sjc/eventWf/eventWf.js"></script>
<script type="text/javascript" src="system/js/commonStatus.js"></script>
<script type="text/javascript">
 $(function (){
//      $("#start_date").ligerDateEditor({ showTime: false, label: '', labelWidth: 100, labelAlign: 'left' });
//      $("#end_date").ligerDateEditor({ showTime: false, label: '', labelWidth: 100, labelAlign: 'left' });
            var parentID = "02";
            cn_combobox = $("#caller_nature").ligerComboBox({
                url: 'eventReport/WkrjEventReport/getDataDictionary?parentID='+parentID,
                valueField: 'id',
                textField: 'name',
                width: 150,height: 30,selectBoxWidth: 150,selectBoxHeight: 100
            });
            parentID = "6c3d5d3b-17c4-42ac-b465-370b1ff11b2e";
            ct_combobox = $("#content_type").ligerComboBox({
                url: 'eventReport/WkrjEventReport/getDataDictionary?parentID='+parentID,
                valueField: 'id', keySupport: true, autocomplete: true,
                textField: 'name',
                width: 150,height: 30,selectBoxWidth: 150,selectBoxHeight: 200
            });
 });
 </script>
</head>
<body style="overflow:hidden;">
<div id="eventWf_layout" style="width:100%;">    
    <div position="center">
        <div id="eventWf_maingrid"></div>
    </div>
</div>
</body>
</html>
