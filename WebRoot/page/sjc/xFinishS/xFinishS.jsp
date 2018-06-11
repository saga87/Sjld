<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<%@include file="/systemdev/myContext.jsp" %>
 <head>
<script type="text/javascript" src="page/sjc/xFinishS/xFinishS.js"></script>
<script type="text/javascript" src="system/js/commonStatus.js"></script>
<script type="text/javascript">
 $(function (){
     $("#start_date").ligerDateEditor({ showTime: false, label: '', labelWidth: 100, labelAlign: 'left' });
     $("#end_date").ligerDateEditor({ showTime: false, label: '', labelWidth: 100, labelAlign: 'left' });
//      var type = [{ id: 1, text: '寿光' },{ id: 2, text: '潍坊' },{ id: 3, text: '全部' }];
//             $("#type").ligerComboBox({ width: 150,height: 30,selectBoxWidth: 150, selectBoxHeight : 100, 
//             data: type, isMultiSelect: false, valueField: 'id' });
        var parentID = "6c3d5d3b-17c4-42ac-b465-370b1ff11b2e";
            ct_combobox = $("#content_type").ligerComboBox({
                url: 'eventReport/WkrjEventReport/getDataDictionary?parentID='+parentID,
                valueField: 'id',
                textField: 'name',
                width: 150,height: 30,selectBoxWidth: 150,selectBoxHeight: 200
            });
 });
 </script>
</head>
<body style="overflow:hidden;">
<div id="onceFinishS_layout" style="width:100%;">
    <div position="center">
        <div style="height:20px; overflow:hidden;padding:5px 0">
            <div class="fl_s">
                <select id="isshizhi">
                    <option value="1">市直</option>
                    <option value="0">镇街</option>
                    <option value="" selected>全部</option>
                </select>
            </div>
            <div class="fl_s fl_s2" style="width:306px">
                <input type="text" id="end_date" /><span style="float:right">~</span><input
                    style="float：right" type="text" id="start_date" />时间:
            </div>
            <a href="javascript:void(0)" iconCls="icon-search" plain="true"
                onclick="searchE()">查询</a> <a href="javascript:void(0)"
                iconCls="icon-search" plain="true" onclick="showAllE()">显示全部</a> <a
                href="javascript:void(0)" iconCls="icon-search" plain="true"
                onclick="exportExcel()">导出Excel</a>
        </div>
        <div id="onceFinishS_maingrid"></div>
    </div>
</div>
</body>
</html>
