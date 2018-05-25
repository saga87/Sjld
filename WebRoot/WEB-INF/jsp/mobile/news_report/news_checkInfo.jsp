<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.wkrj.upReportInfo.bean.WkrjUpReportInfo" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta charset="UTF-8">
	<title>WebAPP-07</title>
	<base href="<%=basePath%>">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<%-- <meta name="viewport" content="width=100%, initial-scale=1, maximum-scale=1, user-scalable=no">--%>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=8">
	<title>潍坊市校园安全管理平台</title>
	<link rel="stylesheet" type="text/css" href="css/new_mobile/news_style.css" />
	<link rel="stylesheet" type="text/css" href="css/new_mobile/Login.css" />
	<script type="text/javascript" src="plug-in/login/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="plug-in/login/new_js/page.js"></script>
	<script type="text/javascript" src="plug-in/login/new_js/Login.js"></script>
	<script type="text/javascript">
	  $(function () {
		  /* $('.Ghislist6 ul li p input').attr("checked","checked"); */

		  $('.Ghislist6 ul').on("click","li",function  () {
	    	  objj=$(this).find("p input");
	    	  if (objj.is(":checked")) {
	    		  objj.prop("checked", false);
	          } else {
	        	  objj.prop("checked", true);
	          }
	      });
	       
	        $('.Ghislist6 ul').on("click","li input",function  (event) {
	            event.stopPropagation();
	        });
	      
	    });
	    
	    
	    //修改上报信息
	    function editReportInfo() {
	        var check_id = $('#temp_check_id').val();
	        if (check_id == null || check_id == "") {
	            alert("请选择一条记录");
	            return;
	        }
	        window.location="wkrjsystem/upReportInfo/getReportInfoById?id="+check_id;
	    }
	    //增加上报信息
	    function add(){
	    	window.location="wkrjsystem/upReportInfo/to_add";
	    }
	    /**
	     * 选中行时设置临时值
	     */
	    function setVal(id) {
	        $("#meeting_temp_id").val(id);
	    }
	    /**
	     * 下达通知
	     */
	    function giveNotice(id){
	        var check_id = $('#temp_check_id').val();
	        if (check_id == null || check_id == "") {
	            alert("请选择一条记录");
	            return;
	        }
	        if (confirm('您确定要审阅吗?审阅后不能再修改')) {
	                $.post('wkrjsystem/upReportInfo/giveNotice', {check_id:check_id},
	                    function(result){
	                    var result = eval('(' + result + ')');
	                    if (result.success) {
	                        alert('审阅成功');
	                        window.location = "wkrjsystem/upReportInfo/getUpReportInfoList?currentPaperNo=1";
	                    } else {
	                        if (result.msg != null) {
	                            alert('已经审阅过了，请不要重复审阅');
	                        } else {
	                            alert('审阅失败');
	                        }
	                    }
	                });
	            }
	    }
	    function setVal(id) {
	        $('#temp_check_id').val('');
	        $('#temp_check_id').val(id);
	        //$('#temp_check_id').style = "background:red";
	    }
	    /*
	    *查询
	    */
	    function inquiry(){
	    	var check_problem = $("#check_problem").val();
	    	var check_school = $("#check_school").val();
	    	window.location.href="wkrjsystem/upReportInfo/getUpReportInfoList?check_problem="+check_problem+"&check_school="+check_school;
	    	/* $.ajax({
		        type: "post",
		        url: 'wkrjsystem/upReportInfo/getUpReportInfoList',
		        data: { check_problem:check_problem,check_school:check_school},
		        dataType:'text',
	    		cache: false,
	        	async: false,
	        	success: function (result) {
	        	try{
	                result = eval('('+result+')');alert('sdsd');
	            }catch(e){
	            }	
	    }
	    	}); */
	    }
	    
	</script>
</head>
<%
  List<Map<String, Object>> reportInfos = (List<Map<String, Object>>)request.getAttribute("reportInfos"); 
%>
<body class="f7" style="background:#f7f7ef;">
	<div class="d3header">
		<p>潍坊市中小学校园安全管理平台</p>
	</div>
	<div class="height40"  style="height:40px"></div>
	<div class="PageTit W640">巡查信息查询</div>
	<div class="Gform AdhisArt">
		<form action="?" method="POST">
			<div class="UpClue">
				<div class="FormItem">
					
					巡查情况：<input type="text" id="check_problem">
				</div>
				<div class="FormItem">
					巡查学校：<input type="text" id="check_school">
				</div>
			</div>
			<div class="FormSub">
				<input class="tijiao4" type="button" value="查询" onclick="inquiry()">
			</div>
			<div class="Ghis AdhisArt">
				<div class="Ghislist6">
					<ul>
						<li>
							<p class="xuanze1"><b>选择</b></p>
							<p><b>学校名称</b></p>
							<p><b>安全隐患情况</b></p>
							<p><b>上报状态</b></p>
						</li>
					</ul>
				</div>
				
				<div class="Ghislist6" style="">
            <input type="hidden" id="temp_check_id" name="temp_check_id" />
                <ul>
                <%
                if (reportInfos != null) {
                for (int i = 0; i < reportInfos.size(); i++) { %>
                    <li id="<%=reportInfos.get(i).get("check_id")%>" onclick="setVal('<%=reportInfos.get(i).get("check_id")%>')">
                        <p class="xuanze1"><input type="checkbox" style="height:1.2rem;width:1.2rem;border:0px solid #ccc;"></p>
                        <p><%=reportInfos.get(i).get("school_name")%></p>
                        <p><%=reportInfos.get(i).get("check_problem")%></p>
                        <p><%=("1".equals(reportInfos.get(i).get("check_flag"))?"已审阅":"未审阅")%></p> 
                    </li>
                <%}
                } %>
                </ul>
            </div>
				
            <!--分页 begin-->
	        <%
	        int paperNums=Integer.parseInt(request.getAttribute("paper_nums").toString());//总页数
	        int current_paperNum=Integer.parseInt(request.getAttribute("currentPaperNo").toString());//当前页 
	        int next_paperNum=current_paperNum;//下一页
	        int prev_paperNum=current_paperNum;//上一页
	        if(current_paperNum < paperNums){
	            next_paperNum++;
	        }
	        if(current_paperNum>1){
	            prev_paperNum--;
	        }  
	        double paperI =Math.ceil((double)paperNums/10);
	        int paper = (int)paperI;
	        %>
	        <!-- 分页 end -->
				<div class="paginter">
					<ul>
						<li><a href="wkrjsystem/upReportInfo/getUpReportInfoList?currentPaperNo=1">首页</a></li>
						<li><a href="wkrjsystem/upReportInfo/getUpReportInfoList?currentPaperNo=<%=prev_paperNum%>">上一页</a></li>
						<li><a href="javascript:void(0)"><%=current_paperNum %> / <%=paperNums %></a></li>
						<li><a href="wkrjsystem/upReportInfo/getUpReportInfoList?currentPaperNo=<%=next_paperNum%>">下一页</a></li>
						<li><a href="wkrjsystem/upReportInfo/getUpReportInfoList?currentPaperNo=${paper_nums==0?1:paper_nums }">末页</a></li>
					</ul>
				</div>
				<div class="Heit60"></div>
        <footer>
			
			<div class="FormSub">
				
				<input class="zengjia" type="button" onclick="add()" value="增加"/>
				<input class="xiugai" type="button" onclick="editReportInfo()"value="修改">
				<input class="xiada"  type="button" onclick="giveNotice()" value="审阅">
			</div>
			
			</footer>
		</form>
	</div>
	

</body>
</html>