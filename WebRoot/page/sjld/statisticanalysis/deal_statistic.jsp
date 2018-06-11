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
        <script src="page/sjld/statisticanalysis/js/deal.js"></script>
        
    </head>
    <body>
    	<div id="caseSXTypeZz" style="width:800px;height:400px;"></div>
    	<div id="qxrank">
    	<div style="margin-left:20px">
			<input type="radio" name="radio"  value="1">受理
			<input type="radio" name="radio"  value="2">处理
			<input type="radio" name="radio"  value="3">办结
			
			<div style ="margin-top:10px">
			<a href="javascript:void(0)"
                        iconCls="icon-search" plain="true"
                        onclick="rank()">&nbsp;&nbsp;查看排行</a>
			</div>
			
    	</div>
    	
    	<div id="rank_layout" style="width:100%;margin-top:10px">	
			<div position="center">
				<div id="rank_maingrid"></div>
			</div>
   		</div>
    	</div>
    </body>
</html>    