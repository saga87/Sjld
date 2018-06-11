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
        <script src="page/sjld/statisticanalysis/js/deal.js"></script>
    
    <script type="text/javascript">
    	var parentID = "6c3d5d3b-17c4-42ac-b465-370b1ff11b2e";
$(function (){
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
        
    </head>
    <body>
    	<div id="caseSXTypeZz" style="width:800px;height:400px;"></div>
    	<div id="SatisfactionBT" style="width:600px;height:400px;"></div>
    	
    	
    	<div id="qxrank">
    	<div style="margin-left:20px">
			<input type="radio" name="radio"  value="1" checked="checked">受理
			<input type="radio" name="radio"  value="2">处理
			<input type="radio" name="radio"  value="3">办结
			
			<div>
			<label>&nbsp;&nbsp;类别事项:</label>
            <input type="text" name="content_type" id="content_type"/>
            <input type="hidden" id="content_type_hidden">
			</div>
			
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