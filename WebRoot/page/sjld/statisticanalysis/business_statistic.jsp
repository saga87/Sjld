<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="en">
<%@include file="/systemdev/myContext.jsp" %>

    <head>
        <meta charset="utf-8">
        <style>
            /* css 代码  */
        </style>
        <script src="plug-in/echarts/echarts.min.js"></script>
        <script src="page/sjld/statisticanalysis/js/business_statistic.js"></script>
        
    </head>
    <body>
    	<div id="caseTypeZz" style="width:600px;height:400px;"></div>
    	<div style="height:20px; margin:10px 0 0 20px;padding:5px 0">
		<div id = "counties">
			<label>&nbsp;&nbsp;县市区:</label>
            <input type="text" name="chengban_dept" id="chengban_dept"/>
		</div>
		<div>
			<label >&nbsp;&nbsp;录入时间:</label>
			<br/>
            <input type="text" name="startTime" id="startTime"/>
            <span>~</span>
            <input type="text" name="endTime" id="endTime"/>
            
		</div>
		<div style ="margin-top:10px">
			<a href="javascript:void(0)"
                        iconCls="icon-search" plain="true"
                        onclick="statistic()">&nbsp;&nbsp;查看统计图</a>
		</div>
        </div>
        
        <div id="caseTypeBT" style="margin-left:360px; margin-top:-100px;margin-bottom:100px; width:400px;height:400px;"></div>
    </body>
</html>