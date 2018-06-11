<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="en">
<%@include file="/systemdev/myContext.jsp" %>

    <head>
        <meta charset="utf-8">
        <script src="plug-in/jquery/jquery.autocompleter.js"></script>
		<link rel="stylesheet" href="plug-in/jquery/main.css">
        <style>
            input.tjj{
    background:#585555;
    height:34px;
    width:100px;
    line-height:34px;
    color:#fff;
    font-family:"微软雅黑";
    border:0;
    margin:0 0 0 0px;
    float:left;
    cursor: pointer;
}
input.file{
     width:100px;height:34px;position:absolute;top:0px;left:0px;opacity: 0; filter:Alpha(opacity=1);z-index:999;
}
.picdiv{
    width: 166px;
    float: left;        
}
.picdiv img{
    width: 125px;
    cursor: pointer;
}
.picdiv a{
    color: red;
    margin-top: 30px;
    display: block;
    float: right;
}
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
	margin-top:-8px;
    margin-left:70px;
    width:144px;
}
        </style>
        <script src="plug-in/echarts/echarts.min.js"></script>
        <script src="page/sjld/statisticanalysis/js/ratio.js"></script>
    <script  type="text/javascript">  
           
            var last_year_month = function() {  
                var d = new Date();  
                var result = [];  
                for(var i = 0; i < 12; i++) {  
                    d.setMonth(d.getMonth() - 1);  
                    var m = d.getMonth() + 1;  
                    m = m < 10 ? "0" + m : m;  
                    //在这里可以自定义输出的日期格式  
                    result.push(d.getFullYear() + "-" + m);  
                    //result.push(d.getFullYear() + "年" + m + '月');  
                }  
                return result;  
            }  
            $(document).ready(function() {  
                //生成前12个月日期下拉框  
                for(var allinfo = last_year_month(), i = 0; i < allinfo.length; i++) {  
                    $("#dateinfo").append("<option value='" + allinfo[i] + "'>" + allinfo[i] + "</option>");  
                }  
            });  
           
        </script>  
    
        
    </head>
    <body>
    		<div>
			<label>&nbsp;&nbsp;类别事项:</label>
            <input type="text" name="content_type" id="content_type"/>
            <select id="dateinfo" name="dateinfo" id="dateinfo">  
                <option>===请选择当前月份之前的月份===</option>  
            </select>  
			</div>
    
    	
			
			<div style ="margin-top:10px">
			<a href="javascript:void(0)"
                        iconCls="icon-search" plain="true"
                        onclick="ratio()">&nbsp;&nbsp;查看环比同比</a>
			</div>
			
    	<div id="ratioZz" style="width:400px;height:400px;"></div>
    </body>
</html>    