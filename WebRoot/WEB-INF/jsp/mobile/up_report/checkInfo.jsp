<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.wkrj.upReportInfo.bean.WkrjUpReportInfo" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <meta charset="UTF-8">
    <base href="<%=basePath%>">
    <meta name="viewport" content="width=100%, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=8">
    <title>巡查情况-列表</title>
    <link rel="stylesheet" type="text/css" href="system/css/style.css">
    <script type="text/javascript" src="plug-in/login/js/jquery.js"></script>
    <!-- <script type="text/javascript" src="js/new/jquery.js"></script>
    <script type="text/javascript" src="js/new/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="js/new/js.js"></script>
    <script type="text/javascript" src="js/library/login/form.js"></script> -->
    <script type="text/javascript">
    $(function () {
       $('.Ghislist ul li').click(function  () {
        $('.Ghislist ul li').removeAttr('style');
        $(this).css('background','#C4E1FF');
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
        if (confirm('您确定要下达通知吗?下达后不能再次修改')) {
                $.post('wkrjsystem/upReportInfo/giveNotice', {check_id:check_id},
                    function(result){
                    var result = eval('(' + result + ')');
                    if (result.success) {
                        alert('下达成功');
                        window.location = "wkrjsystem/upReportInfo/getUpReportInfoList?currentPaperNo=1";
                    } else {
                        if (result.msg != null) {
                            alert('已经下达过了，请不要重复下达');
                        } else {
                            alert('下达失败');
                        }
                    }
                });
            }
    }
    function setVal(id) {
        $('#temp_check_id').val('');
        $('#temp_check_id').val(id);
        $('#temp_check_id').style = "background:red";
    }
    </script>
</head>
<%
  List<Map<String, Object>> reportInfos = (List<Map<String, Object>>)request.getAttribute("reportInfos"); 
%>
<body class="f7">
    <div class="W640">
        <div class="PageTit W640">巡查情况</div>
        <div class="Ghis AdhisArt">
            <div class="GhisTit">
                <p><b>学校</b></p>
                <p><b>安全隐患情况</b></p>
            </div>
            <div class="Ghislist" style="">
            <input type="hidden" id="temp_check_id" name="temp_check_id" />
                <ul>
                <%
                if (reportInfos != null) {
                for (int i = 0; i < reportInfos.size(); i++) { %>
                    <li id="<%=reportInfos.get(i).get("check_id")%>" onclick="setVal('<%=reportInfos.get(i).get("check_id")%>')">
                        <p><%=reportInfos.get(i).get("school_name") %></p>
                        <p><%=reportInfos.get(i).get("check_problem") %></p>
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
        <div class="W640 ListBtm">
            <a href="wkrjsystem/upReportInfo/to_add">添加</a>
            <a href="javascript:void(0)" onclick="editReportInfo()">修改</a>
            <a href="javascript:void(0);" onclick="giveNotice()">下达</a>
        </div>
    </footer>
        </div>
    </div>
</body>
</html>