<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="wkrjsystem.user.bean.WkrjUser" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
WkrjUser sessionObj = (WkrjUser) request.getSession()
            .getAttribute("user");
    String user_id = "", user_name = "", roler_type = "", permission = "", user_realname = "", deptId = "", deptName = "",roler_id="";
    if (sessionObj != null) {
        //roler_type = sessionObj.getUser_role();
        user_id = sessionObj.getUser_id();
        user_name = sessionObj.getUser_name();
        user_realname = sessionObj.getUser_realname();
        //permission = sessionObj.getPermission();
        deptId = sessionObj.getDept_id();
        //deptName = sessionObj.getDepartment_name();
        //roler_id = sessionObj.getRoler_id();
    }
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String d = sdf.format(date);
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="width=100%, initial-scale=1, maximum-scale=1, user-scalable=no">
    <base href="<%=basePath%>">
    
    <title>巡查情况-添加</title>
    <!-- <link rel="stylesheet" type="text/css" href="css/new_mobile/new_style.css"> -->
    <link rel="stylesheet" type="text/css" href="css/new_mobile/news_style.css?v=11" />
    <script type="text/javascript" src="plug-in/login/js/jquery.js"></script>
    <script type="text/javascript" src="plug-in/login/new_js/page.js"></script>
    <script src="plug-in/login/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="plug-in/jquery/jquery.form.min.js"></script>
    <script src="plug-in/login/js/jquery.combo.select.js"></script>
    <link rel="stylesheet" href="system/css/combo.select.css">
    <style>
         /* .UpClue { width: 400px; margin: 100px auto;} */
    </style>
    <script>
    $(function() {
        $('select').comboSelect();
        $('.UploadPicSub').change(function () {
            $('.UploadPicAdd').val($(this).val());
        });
    });
    </script>
    <script type="text/javascript">
    
        $(function(){
            $("#check_time").attr("value","<%=d%>");
            $("#check_schooldept").attr("value","<%=deptId%>");
            $("#check_person").attr("value","<%=user_realname%>");
            getSchloolByDept("<%=deptId%>");
        });
        /**
         * 保存通知公告
         */
        function up_report() {
            //document.getElementById("shouwen_save").disabled = true;
            var check_schooldept = $("#check_schooldept").val();
            var check_school = $("#check_school").val();
            var check_problem = $("#check_problem").val();
            var check_projectscope = $("#check_projectscope").val();
            //pers = pers.substring(0,pers.length-1);
            if (check_school == null || check_school == "") {
                alert("请选择所属学校");
                return;
            }
            if (check_problem == null || check_problem == "") {
                alert("请输入安全隐患情况");
                return;
            }
            if (check_projectscope == null || check_projectscope == "") {
                alert("请选择所属项目范围");
                return;
            }
            $.post('wkrjsystem/upReportInfo/addUpReportInfo', {
                    check_schooldept : check_schooldept, check_school : check_school, check_problem : check_problem,
                    check_projectscope : check_projectscope
                }, function(result) {
                    var result = eval('(' + result + ')');
                    if (result.success) {
                        alert('添加成功');
                        window.location = "wkrjsystem/upReportInfo/getUpReportInfoList?currentPaperNo=1";
                    } else {
                        alert('添加失败');
                    }
                });
        }
        function getSchloolByDept(dept_id) {
            var url="wkrjsystem/upReportInfo/getSchloolByDept?dept_id="+dept_id;//请求后台的url
            $("#check_school").empty();//先置空
            $("#check_school").append($('<option value="">-请选择-</option>'));//加上--请选择--选项
            if ($(dept_id).val()=="") {
                return;//无值，返回
            }
            $.ajax({
                url:url,  
                type:'POST',//POST方式
                dataType:'text',//返回text类型
                beforeSend:function(xmlHttpRequest,status){  
                    
                },  
                success:function(data,status){ 
                    var d=eval(data);//解析
                    $(d).each(function(index,entity){  
                    $("#check_school").append($('<option value="'+entity['school_id']+'">'+entity['school_name']+'</option>'));//后台数据加到下拉框
                    $("#check_school").comboSelect();
                    });  
                }
                
            });  
          
        }
        function saveReports() {
//             var oldName = $('#oldName1').val();
//             var fileUrl = $('#fileUrl2').val();
//             var fileType = $('#fileType3').val();
            var check_schooldept = $("#check_schooldept").val();
            var check_school = $("#check_school").val();
            var check_time = $("#check_time").val();
//             var check_content1 = $("#check_content1").val();
//             var check_content2 = $("#check_content2").val();
//             var check_content3 = $("#check_content3").val();
//             var check_projectscope1 = $("#check_projectscope1").val();
//             var check_projectscope2 = $("#check_projectscope2").val();
//             var check_projectscope3 = $("#check_projectscope3").val();
            var check_person = "";//检查人员
            //var check_time = "";//检查时间
            var check_content = "";//检查内容
            var dangerType = "";//安全隐患类别
            var isHaveNull = "0";//是否有空值
            var oldName = "";
            var fileUrl = "";
            var fileType = "";
            /* $(function(){
             $("input[class='report check_person_input']").each(function(index,item){
                if($(this).val()==""){
                    isHaveNull="1";
                }else{
                    check_person+=$(this).val();
                    check_person+=",";
                }
             });
            });
            $(function(){
             $("input[class='report check_time_input']").each(function(index,item){
                if($(this).val()==""){
                    isHaveNull="1";
                }else{
                    check_time+=$(this).val();
                    check_time+=",";
                }
             });
            }); */
            //pers = pers.substring(0,pers.length-1);
            if (check_school == null || check_school == "") {
                alert("请选择学校名称");
                return;
            }
            if (check_time == null || check_time == "") {
                alert("请输入检查时间");
                return;
            }
//             if (check_projectscope == null || check_projectscope == "") {
//                 alert("请选择安全隐患类别");
//                 return;
//             }
            document.getElementById("up_report_form").action = "wkrjsystem/upReportInfo/addUpReportInfo";
            document.getElementById("up_report_form").submit();
            <%-- $.post('wkrjsystem/upReportInfo/addUpReportInfo', {
                    check_schooldept : check_schooldept, check_school : check_school, check_person : check_person, check_time : check_time,
                    check_content1 : check_content1, check_content2 : check_content2, check_content3 : check_content3 
                }, function(result) {
                    var result = eval('(' + result + ')');
                    result = eval('(' + result + ')');
                    if (result.success) {
                        window.location = "<%=basePath%>/system/success.jsp";
                    } else {
                        window.location = "<%=basePath%>/system/error.jsp";
                    }
                }); --%>
        }
    </script>
</head>
<%
List<Map<String, Object>> deptList = (List<Map<String,Object>>)request.getAttribute("deptList");
List<Map<String, Object>> schoolList = (List<Map<String,Object>>)request.getAttribute("schoolList");
List<Map<String, Object>> projectList = (List<Map<String,Object>>)request.getAttribute("projectList");
%>
<body style="background:#f7f7ef;">
                <div class="Gform AdhisArt" id="addto1" style="display:none;">
                    <div class="UpClue" style="margin-top:20px;padding-top:20px;border-top:1px dashed #6cf;">
                        <!-- <div class="FormItem">
                            <span>请选择县市区</span><select name="" id="">
                                <option value=""></option>
                            </select>
                        </div>
                        <div class="FormItem">
                            <span>请选择学校</span><select name="" id="">
                                <option value=""></option>
                            </select>
                        </div>
                        <div class="FormItem">
                            <span>检查时间</span><input type="text" class="report check_time_input">
                        </div>
                        <div class="FormItem">
                            <span>检查人员</span><input type="text" class="report check_person_input">
                        </div> -->
                        <div class="FormItem">
                            <span>检查内容</span><textarea class="textarea" name="message"></textarea>
                        </div>
                        <div class="FormItem">
                            <span>安全隐患类别</span>
                            <select name="check_projectscope" >
                             <option value="">请选择安全隐患类别</option>
                            <%
                            if (projectList != null) {
                            for(int i = 0; i < projectList.size(); i++) { %>
                            <option value="<%=projectList.get(i).get("project_id") %>"><%=projectList.get(i).get("project_name") %></option>
                            <%}
                            } %>
                            </select>
                        </div>
                    </div>
                    <div class="FormUpFile">
                        <style>
                        .imgssc{
                            width:100%;
                            overflow:hidden;
                            position: relative;
                        }
                        .images label,.swfspsc label,.textarea label,.swfspsc label{
                            float: left;
                            width: 35%;
                            padding: 25px 15px;
                            font-family: "Helvetica Neue", Helvetica, sans-serif;
                            line-height: 1.1;
                            font-weight: bold;
                            text-align: right;
                            color: #333;
                        }
                        .imgssc img{
                            width:70px;
                            height:70px;
                            float: left;
                        }
                        #files{position:absolute;width:70px;height:70px;z-index: 12;right:0;top: 0;opacity:0}
                        .imgssc b{position:relative;-width:70px;display: inline-block;overflow:hidden;padding:0 0;}
                        .c_k{float:left;position:relative; margin:10px 10px;}
                        .c_k .close{position:absolute;z-index:2;right:-10px;top:-10px;width:20px;height:20px;}
                        .c_k .close img{width:100%;height:100%;}
                        </style>
                        <div class="imgssc">
                            <b>
                                <span>实证材料</span>
                                <img style="float:left;margin:10px 10px;" src="system/imgs/addImg.png">
                                <form enctype="multipart/form-data" method="POST" id="">  
                                    <input id="files" onclick="showActionSheet()" name="file">  
                                </form>
                            </b>
                        </div>
                    </div>
                </div>
    <div class="d3header">
        <p>潍坊市中小学校园安全管理平台</p>
    </div>
        <style>
         .imgssc{
             width:100%;
             overflow:hidden;
             position: relative;
         }
         .images label,.swfspsc label,.textarea label,.swfspsc label{
             float: left;
             width: 35%;
             padding: 25px 15px;
             font-family: "Helvetica Neue", Helvetica, sans-serif;
             line-height: 1.1;
             font-weight: bold;
             text-align: right;
             color: #333;
         }
         .imgssc img{
             width:70px;
             height:70px;
             float: left;
         }
         .c_k,.c_k span{border:none !important;margin:0 !important;}
         #files{position:absolute;width:70px;height:70px;z-index: 12;right:0;top: 0;opacity:0}
         .imgssc b{position:relative;-width:70px;display: inline-block;overflow:hidden;padding:0 0;}
         .c_k{float:left;position:relative; margin:10px !important;}
         .c_k .close{position:absolute;z-index:2;right:-10px;top:-10px;width:20px;height:20px;}
         .c_k .close img{width:100%;height:100%;}
        </style>
    <div class="height40"  style="height:3rem"></div>
    <div class="con3">
            <div class="PageTit W640">上报校园安全巡查</div>
            <form action="?" method="POST" id="up_report_form">
                <div class="Gform AdhisArt">
                    <input  type="hidden"  name="oldName1" id="oldName1" class="oldNameId">
                    <input  type="hidden"  name="fileUrl1" id="fileUrl1" class="fileUrlId">
                    <input  type="hidden"  name="fileType1" id="fileType1" class="fileTypeId">
                    <div class="UpClue">
                        <div class="FormItem">
                            <span>县市区名称</span><!-- <input type="text" name="check_schooldept" id="check_schooldept"> -->
                            <select name="check_schooldept" id="check_schooldept" onchange="getSchloolByDept(this.value)" >
                                <!-- <option>请选择县市区</option> -->
                                <%
                                if (deptList != null) {
                                for(int i = 0; i < deptList.size(); i++) { %>
                                <option value="<%=deptList.get(i).get("dept_id") %>"><%=deptList.get(i).get("dept_name") %></option>
                                <%}
                                } %>
                            </select>
                        </div>
                        <div class="FormItem">
                            <span>学校名称</span>
                            <select name="check_school" id="check_school">
                            </select>
                        </div>
                        <div class="FormItem">
                            <span>检查时间</span><input type="date" class="report check_time_input" name="check_time" id="check_time">
                        </div>
                        <div class="FormItem">
                            <span>检查人员</span><input type="text" class="report check_person_input" name="check_person" id="check_person">
                        </div>
                        <div class="FormItem">
                            <input type="hidden" id="check_projectscope1" name="check_projectscope1" value="14">
                            <span>[安全管理制度]检查内容</span><textarea class="textarea" id="check_content1" name="message1"></textarea>
                        </div>
                        <%-- <div class="FormItem">
                            <span>安全隐患类别</span>
                            <select name="check_projectscope" id="check_projectscope1">
                            <option value="">请选择安全隐患类别</option>
                            <%
                            if (projectList != null) {
                            for(int i = 0; i < projectList.size(); i++) { %>
                            <option value="<%=projectList.get(i).get("project_id") %>"><%=projectList.get(i).get("project_name") %></option>
                            <%}
                            } %>
                            </select>
                        </div> --%>
                    </div>
                    <div class="FormUpFile">
                        <div class="imgssc">
                            <b>
                                <span>[安全管理制度]实证材料</span>
                                <!-- <span class="c_k"><a class="img"><img width="70" height="70" src='system/imgs/Pic2.jpg' /></a><span class="close"><img src="system/imgs/close_w.png" /></span></span> -->
                                <img data="1" class="files"  style="float:left;margin:10px 10px;" src="system/imgs/addImg.png">
                            </b>
                        </div>
                    </div>
                        <div class="FormItem">
                            <span>[安全管理制度]整改天数</span>
                            <select name="reform_days1" id="reform_days1">
                                <option value=""></option>
                                <option value="1">1</option>
                                <option value="3">3</option>
                                <option value="5">5</option>
                                <option value="7">7</option>
                                <option value="10">10</option>
                            </select>
                        </div>
                </div>
                <hr>
                <!-- 安全教育start -->
                <div class="Gform AdhisArt">
                    <input  type="hidden"  name="oldName2" id="oldName2" class="oldNameId">
                    <input  type="hidden"  name="fileUrl2" id="fileUrl2" class="fileUrlId">
                    <input  type="hidden"  name="fileType2" id="fileType2" class="fileTypeId">
                        <div class="FormItem">
                            <input type="hidden" id="check_projectscope2" name="check_projectscope2" value="15">
                            <span>[安全教育]检查内容</span><textarea class="textarea" id="check_content2" name="message2"></textarea>
                        </div>
                    <div class="FormUpFile">
                        <div class="imgssc">
                            <b>
                                <span>[安全教育]实证材料</span>
                                <!-- <ul class="clearfix">
                                    <li><img src="system/imgs/Pic2.jpg" alt=""><i><img src="system/imgs/exitPicIco.png" alt=""></i></li>
                                    <li><b><img src="system/imgs/addImg.png" alt=""><input type="file"></b></li>
                                </ul> -->
                                <img data="2" class="files"  style="float:left;margin:10px 10px;" src="system/imgs/addImg.png">
                            </b>
                        </div>
                    </div>
                        <div class="FormItem">
                            <span>[安全教育]整改天数</span>
                            <select name="reform_days2" id="reform_days2">
                                <option value=""></option>
                                <option value="1">1</option>
                                <option value="3">3</option>
                                <option value="5">5</option>
                                <option value="7">7</option>
                                <option value="10">10</option>
                            </select>
                        </div>
                </div>
                <!-- 安全教育end -->
                <hr>
                <!-- 校舍、场地安全start -->
                <div class="Gform AdhisArt">
                    <input  type="hidden"  name="oldName3" id="oldName3" class="oldNameId">
                    <input  type="hidden"  name="fileUrl3" id="fileUrl3" class="fileUrlId">
                    <input  type="hidden"  name="fileType3" id="fileType3" class="fileTypeId">
                        <div class="FormItem">
                            <input type="hidden" id="check_projectscope3" name="check_projectscope3" value="16">
                            <span>[校舍、场地安全]检查内容</span><textarea class="textarea" id="check_content3" name="message3"></textarea>
                        </div>
                    <div class="FormUpFile">
                        <div class="imgssc">
                            <b>
                                <span>[校舍、场地安全]实证材料</span>
                                <!-- <ul class="clearfix">
                                    <li><img src="system/imgs/Pic2.jpg" alt=""><i><img src="system/imgs/exitPicIco.png" alt=""></i></li>
                                    <li><b><img src="system/imgs/addImg.png" alt=""><input type="file"></b></li>
                                </ul> -->
                                <img data="3" class="files"  style="float:left;margin:10px 10px;" src="system/imgs/addImg.png">
                            </b>
                        </div>
                    </div>
                        <div class="FormItem">
                            <span>[校舍、场地安全]整改天数</span>
                            <select name="reform_days3" id="reform_days3">
                                <option value=""></option>
                                <option value="1">1</option>
                                <option value="3">3</option>
                                <option value="5">5</option>
                                <option value="7">7</option>
                                <option value="10">10</option>
                            </select>
                        </div>
                </div>
                <!-- 校舍、场地安全end -->
                <hr>
                <!-- 消防安全start -->
                <div class="Gform AdhisArt">
                    <input  type="hidden"  name="oldName4" id="oldName4" class="oldNameId">
                    <input  type="hidden"  name="fileUrl4" id="fileUrl4" class="fileUrlId">
                    <input  type="hidden"  name="fileType4" id="fileType4" class="fileTypeId">
                        <div class="FormItem">
                            <input type="hidden" id="check_projectscope4" name="check_projectscope4" value="17">
                            <span>[消防安全]检查内容</span><textarea class="textarea" id="check_content4" name="message4"></textarea>
                        </div>
                    <div class="FormUpFile">
                        <div class="imgssc">
                            <b>
                                <span>[消防安全]实证材料</span>
                                <img data="4" class="files"  style="float:left;margin:10px 10px;" src="system/imgs/addImg.png">
                            </b>
                        </div>
                    </div>
                        <div class="FormItem">
                            <span>[消防安全]整改天数</span>
                            <select name="reform_days4" id="reform_days4">
                                <option value=""></option>
                                <option value="1">1</option>
                                <option value="3">3</option>
                                <option value="5">5</option>
                                <option value="7">7</option>
                                <option value="10">10</option>
                            </select>
                        </div>
                </div>
                <!-- 消防安全end -->
                <hr>
                <!-- 设施设备安全start -->
                <div class="Gform AdhisArt">
                    <input  type="hidden"  name="oldName5" id="oldName5" class="oldNameId">
                    <input  type="hidden"  name="fileUrl5" id="fileUrl5" class="fileUrlId">
                    <input  type="hidden"  name="fileType5" id="fileType5" class="fileTypeId">
                        <div class="FormItem">
                            <input type="hidden" id="check_projectscope5" name="check_projectscope5" value="7">
                            <span>[设施设备安全]检查内容</span><textarea class="textarea" id="check_content5" name="message5"></textarea>
                        </div>
                    <div class="FormUpFile">
                        <div class="imgssc">
                            <b>
                                <span>[设施设备安全]实证材料</span>
                                <img data="5" class="files"  style="float:left;margin:10px 10px;" src="system/imgs/addImg.png">
                            </b>
                        </div>
                    </div>
                        <div class="FormItem">
                            <span>[设施设备安全]整改天数</span>
                            <select name="reform_days5" id="reform_days5">
                                <option value=""></option>
                                <option value="1">1</option>
                                <option value="3">3</option>
                                <option value="5">5</option>
                                <option value="7">7</option>
                                <option value="10">10</option>
                            </select>
                        </div>
                </div>
                <!-- 设施设备安全end -->
                <hr>
                <!-- 饮食、饮水安全start -->
                <div class="Gform AdhisArt">
                    <input  type="hidden"  name="oldName6" id="oldName6" class="oldNameId">
                    <input  type="hidden"  name="fileUrl6" id="fileUrl6" class="fileUrlId">
                    <input  type="hidden"  name="fileType6" id="fileType6" class="fileTypeId">
                        <div class="FormItem">
                            <input type="hidden" id="check_projectscope6" name="check_projectscope6" value="18">
                            <span>[饮食、饮水安全]检查内容</span><textarea class="textarea" id="check_content6" name="message6"></textarea>
                        </div>
                    <div class="FormUpFile">
                        <div class="imgssc">
                            <b>
                                <span>[饮食、饮水安全]实证材料</span>
                                <img data="6" class="files"  style="float:left;margin:10px 10px;" src="system/imgs/addImg.png">
                            </b>
                        </div>
                    </div>
                        <div class="FormItem">
                            <span>[饮食、饮水安全]整改天数</span>
                            <select name="reform_days6" id="reform_days6">
                                <option value=""></option>
                                <option value="1">1</option>
                                <option value="3">3</option>
                                <option value="5">5</option>
                                <option value="7">7</option>
                                <option value="10">10</option>
                            </select>
                        </div>
                </div>
                <!-- 饮食、饮水安全end -->
                <hr>
                <!-- 交通安全start -->
                <div class="Gform AdhisArt">
                    <input  type="hidden"  name="oldName7" id="oldName7" class="oldNameId">
                    <input  type="hidden"  name="fileUrl7" id="fileUrl7" class="fileUrlId">
                    <input  type="hidden"  name="fileType7" id="fileType7" class="fileTypeId">
                        <div class="FormItem">
                            <input type="hidden" id="check_projectscope7" name="check_projectscope7" value="11">
                            <span>[交通安全]检查内容</span><textarea class="textarea" id="check_content7" name="message7"></textarea>
                        </div>
                    <div class="FormUpFile">
                        <div class="imgssc">
                            <b>
                                <span>[交通安全]实证材料</span>
                                <img data="7" class="files"  style="float:left;margin:10px 10px;" src="system/imgs/addImg.png">
                            </b>
                        </div>
                    </div>
                        <div class="FormItem">
                            <span>[交通安全]整改天数</span>
                            <select name="reform_days7" id="reform_days7">
                                <option value=""></option>
                                <option value="1">1</option>
                                <option value="3">3</option>
                                <option value="5">5</option>
                                <option value="7">7</option>
                                <option value="10">10</option>
                            </select>
                        </div>
                </div>
                <!-- 交通安全end -->
                <hr>
                <!-- 安保安全start -->
                <div class="Gform AdhisArt">
                    <input  type="hidden"  name="oldName8" id="oldName8" class="oldNameId">
                    <input  type="hidden"  name="fileUrl8" id="fileUrl8" class="fileUrlId">
                    <input  type="hidden"  name="fileType8" id="fileType8" class="fileTypeId">
                        <div class="FormItem">
                            <input type="hidden" id="check_projectscope8" name="check_projectscope8" value="12">
                            <span>[安保安全]检查内容</span><textarea class="textarea" id="check_content8" name="message8"></textarea>
                        </div>
                    <div class="FormUpFile">
                        <div class="imgssc">
                            <b>
                                <span>[安保安全]实证材料</span>
                                <img data="8" class="files"  style="float:left;margin:10px 10px;" src="system/imgs/addImg.png">
                            </b>
                        </div>
                    </div>
                        <div class="FormItem">
                            <span>[安保安全]整改天数</span>
                            <select name="reform_days8" id="reform_days8">
                                <option value=""></option>
                                <option value="1">1</option>
                                <option value="3">3</option>
                                <option value="5">5</option>
                                <option value="7">7</option>
                                <option value="10">10</option>
                            </select>
                        </div>
                </div>
                <!-- 安保安全end -->
                <hr>
                <!-- 防溺水安全start -->
                <div class="Gform AdhisArt">
                    <input  type="hidden"  name="oldName9" id="oldName9" class="oldNameId">
                    <input  type="hidden"  name="fileUrl9" id="fileUrl9" class="fileUrlId">
                    <input  type="hidden"  name="fileType9" id="fileType9" class="fileTypeId">
                        <div class="FormItem">
                            <input type="hidden" id="check_projectscope9" name="check_projectscope9" value="19">
                            <span>[防溺水安全]检查内容</span><textarea class="textarea" id="check_content9" name="message9"></textarea>
                        </div>
                    <div class="FormUpFile">
                        <div class="imgssc">
                            <b>
                                <span>[防溺水安全]实证材料</span>
                                <img data="9" class="files"  style="float:left;margin:10px 10px;" src="system/imgs/addImg.png">
                            </b>
                        </div>
                    </div>
                        <div class="FormItem">
                            <span>[防溺水安全]整改天数</span>
                            <select name="reform_days9" id="reform_days9">
                                <option value=""></option>
                                <option value="1">1</option>
                                <option value="3">3</option>
                                <option value="5">5</option>
                                <option value="7">7</option>
                                <option value="10">10</option>
                            </select>
                        </div>
                </div>
                <!-- 防溺水安全end -->
                <hr>
                <!-- 校园周边安全管理start -->
                <div class="Gform AdhisArt">
                    <input  type="hidden"  name="oldName10" id="oldName10" class="oldNameId">
                    <input  type="hidden"  name="fileUrl10" id="fileUrl10" class="fileUrlId">
                    <input  type="hidden"  name="fileType10" id="fileType10" class="fileTypeId">
                        <div class="FormItem">
                            <input type="hidden" id="check_projectscope10" name="check_projectscope10" value="20">
                            <span>[校园周边安全管理]检查内容</span><textarea class="textarea" id="check_content10" name="message10"></textarea>
                        </div>
                    <div class="FormUpFile">
                        <div class="imgssc">
                            <b>
                                <span>[校园周边安全管理]实证材料</span>
                                <img data="10" class="files"  style="float:left;margin:10px 10px;" src="system/imgs/addImg.png">
                            </b>
                        </div>
                    </div>
                        <div class="FormItem">
                            <span>[校园周边安全管理]整改天数</span>
                            <select name="reform_days10" id="reform_days10">
                                <option value=""></option>
                                <option value="1">1</option>
                                <option value="3">3</option>
                                <option value="5">5</option>
                                <option value="7">7</option>
                                <option value="10">10</option>
                            </select>
                        </div>
                </div>
                <!-- 校园周边安全管理end -->
                <hr>
                <!-- 其他安全start -->
                <div class="Gform AdhisArt">
                    <input  type="hidden"  name="oldName11" id="oldName11" class="oldNameId">
                    <input  type="hidden"  name="fileUrl11" id="fileUrl11" class="fileUrlId">
                    <input  type="hidden"  name="fileType11" id="fileType11" class="fileTypeId">
                        <div class="FormItem">
                            <input type="hidden" id="check_projectscope11" name="check_projectscope11" value="13">
                            <span>[其他安全]检查内容</span><textarea class="textarea" id="check_content11" name="message11"></textarea>
                        </div>
                    <div class="FormUpFile">
                        <div class="imgssc">
                            <b>
                                <span>[其他安全]实证材料</span>
                                <img data="11" class="files"  style="float:left;margin:10px 10px;" src="system/imgs/addImg.png">
                            </b>
                        </div>
                    </div>
                        <div class="FormItem">
                            <span>[其他安全]整改天数</span>
                            <select name="reform_days11" id="reform_days11">
                                <option value=""></option>
                                <option value="1">1</option>
                                <option value="3">3</option>
                                <option value="5">5</option>
                                <option value="7">7</option>
                                <option value="10">10</option>
                            </select>
                        </div>
                </div>
                <!-- 其他安全end -->
            </form>
        </div>
        <!-- <div class="Addto">
            <a href="javascript:void(0);"> &oplus;添加新隐患</a>
        </div> -->
        <div style="height:51px;"></div>
        <div class="FormSub" style="position:fixed;bottom:0;left:0; background:#fff;">
            <!-- <input class="tijiao" type="submit" value="提交">
            <input class="fanhui"  type="submit" value="返回"> -->
            <input class="tijiao" type="button" value="提交" onclick="saveReports()">
            <input class="fanhui" type="button" value="返回" onclick="history.go(-1)">
        </div>
                
    <script type="text/javascript">
    var oldName1='';
    var fileUrl1='';
    var fileType1='';
    var oldName2='';
    var fileUrl2='';
    var fileType2='';
    var oldName3='';
    var fileUrl3='';
    var fileType3='';
    var oldName4='';
    var fileUrl4='';
    var fileType4='';
    var oldName5='';
    var fileUrl5='';
    var fileType5='';
    var oldName6='';
    var fileUrl6='';
    var fileType6='';
    var oldName7='';
    var fileUrl7='';
    var fileType7='';
    var oldName8='';
    var fileUrl8='';
    var fileType8='';
    var oldName9='';
    var fileUrl9='';
    var fileType9='';
    var oldName10='';
    var fileUrl10='';
    var fileType10='';
    var oldName11='';
    var fileUrl11='';
    var fileType11='';
//     $("#isTel").change(function(){
//         if($("#isTel").prop("checked")){
//             document.getElementById("telHide").style.display="";//显示
//         }else{
//             document.getElementById("telHide").style.display="none";//隐藏
//         }
//     });
    var obj1;
    var obj2;
    var datanum;
    $(".files").click(function(){
        datanum=parseInt($(this).attr("data"));
        obj1=$(this).parents(".AdhisArt");
        uexWindow.cbActionSheet=function (opId, dataType, data) {
            if(data == 0) {
                paiopen();
            } else if(data == 1) {
                fileopen();
            }
        };
        var value = "拍照上传;本地上传";
        var mycars=value.split(";");
        uexWindow.actionSheet("","取消",mycars);
    })
    var imgurl = "E:\aaa.jpg";//默认图片url

    //显示log
    function setLog(msg){
       // document.getElementById("msgid").innerHTML = msg;
    }

    /* function showActionSheet(){
    } */
    function paiopen(){
        uexCamera.open();
    }
    function fileopen(){
        var datas = {
            min:1,
            max:1,
            quality:0.8,
            detailedInfo:true
        };
        var json = JSON.stringify(datas);
        uexImage.openPicker(json);
    } 
    //上传图片
    var uploadHttp = '<%=basePath%>'+'wkrjsystem/upReportInfo/uploadfile';
    var uopCode = 2;
    function uploadpic(){
        uexUploaderMgr.closeUploader(uopCode);
        if (imgurl.indexOf("http://") < 0) {
            uexUploaderMgr.createUploader(uopCode, uploadHttp);
        } else {
            uexWindow.toast(0, 5, "非本地图片", 1000);
        }
    }
    window.uexOnload = function(){
        uexLocation.openLocation();
        uexLocation.onChange = function(inLatitude, inLongitude){
             uexLocation.getAddress(inLatitude,inLongitude,'1');
             uexLocation.closeLocation();
        };
//         uexLocation.cbGetAddress = function(opCode, dataType, data){
//             var data=eval("("+data+")");
//             var location=data.location.lng+","+data.location.lat;
//             $("#infolocation").val(location);
//             $("#infoaddress").val(data.formatted_address);             
//         };
        //本地上传
        uexImage.onPickerClosed = function(info){
            var infoData=eval("("+info+")");
            if(infoData.isCancelled){
                return;
            }else{
                imgurl = infoData.data;
                obj1.find(".files").val(infoData.data);
                uploadpic();
            }
        };
        //相机上传
        uexCamera.cbOpen = function(opId,dataType,data){
            if(dataType==0) {
                imgurl = data;
                obj1.find(".files").val(imgurl);
                uploadpic();
            }
        };
        //****************上传回调***************
        uexUploaderMgr.cbCreateUploader = function(opId,dataType,data){
            uexUploaderMgr.uploadFile(uopCode,imgurl,"filename",1);
            setLog("开始上传图片");
        };
        uexUploaderMgr.onStatus = function(opId,fileSize,percent,serverPath,status){
            if(status==0){
                uexWindow.toast('1', '5', '上传进度：' + percent + '%','0');
                setLog("上传进度："+percent+"%");
            }
            if(status == 1){
                uexWindow.closeToast();
                var ser=eval("("+serverPath+")");
                <%-- $(".images").find("b").before('<img src='+'<%=basePath%>'+ser[0].fileurl+' />'); --%>
                obj1.find(".imgssc").find("b").find(".files").before('<span class="c_k"><a class="img"><img width="70" height="70" src='+'<%=basePath%>'+ser[0].fileurl+' /></a><span class="close"><img src="system/imgs/close_w.png" /></span></span>');
                uexUploaderMgr.closeUploader(uopCode);
                //obj1.find(".oldNameId").val('');
                //obj1.find(".fileUrlId").val('');
                //obj1.find(".fileTypeId").val('');
                switch(datanum){
                    case 1:
                    oldName1+=ser[0].filename+"|";
	                fileUrl1+=ser[0].fileurl+"|";
	                fileType1+=ser[0].fileextend+"|";
	                obj1.find(".oldNameId").val(oldName1);
	                obj1.find(".fileUrlId").val(fileUrl1);
	                obj1.find(".fileTypeId").val(fileType1);
	                break;
	                case 2:
                    oldName2+=ser[0].filename+"|";
                    fileUrl2+=ser[0].fileurl+"|";
                    fileType2+=ser[0].fileextend+"|";
                    obj1.find(".oldNameId").val(oldName2);
                    obj1.find(".fileUrlId").val(fileUrl2);
                    obj1.find(".fileTypeId").val(fileType2);
	                break;
	                case 3:
                    oldName3+=ser[0].filename+"|";
                    fileUrl3+=ser[0].fileurl+"|";
                    fileType3+=ser[0].fileextend+"|";
                    obj1.find(".oldNameId").val(oldName3);
                    obj1.find(".fileUrlId").val(fileUrl3);
                    obj1.find(".fileTypeId").val(fileType3);
                    break;
                    case 4:
                    oldName4+=ser[0].filename+"|";
                    fileUrl4+=ser[0].fileurl+"|";
                    fileType4+=ser[0].fileextend+"|";
                    obj1.find(".oldNameId").val(oldName4);
                    obj1.find(".fileUrlId").val(fileUrl4);
                    obj1.find(".fileTypeId").val(fileType4);
                    break;
                    case 5:
                    oldName5+=ser[0].filename+"|";
                    fileUrl5+=ser[0].fileurl+"|";
                    fileType5+=ser[0].fileextend+"|";
                    obj1.find(".oldNameId").val(oldName5);
                    obj1.find(".fileUrlId").val(fileUrl5);
                    obj1.find(".fileTypeId").val(fileType5);
                    break;
                    case 6:
                    oldName6+=ser[0].filename+"|";
                    fileUrl6+=ser[0].fileurl+"|";
                    fileType6+=ser[0].fileextend+"|";
                    obj1.find(".oldNameId").val(oldName6);
                    obj1.find(".fileUrlId").val(fileUrl6);
                    obj1.find(".fileTypeId").val(fileType6);
                    break;
                    case 7:
                    oldName7+=ser[0].filename+"|";
                    fileUrl7+=ser[0].fileurl+"|";
                    fileType7+=ser[0].fileextend+"|";
                    obj1.find(".oldNameId").val(oldName7);
                    obj1.find(".fileUrlId").val(fileUrl7);
                    obj1.find(".fileTypeId").val(fileType7);
                    break;
                    case 8:
                    oldName8+=ser[0].filename+"|";
                    fileUrl8+=ser[0].fileurl+"|";
                    fileType8+=ser[0].fileextend+"|";
                    obj1.find(".oldNameId").val(oldName8);
                    obj1.find(".fileUrlId").val(fileUrl8);
                    obj1.find(".fileTypeId").val(fileType8);
                    break;
                    case 9:
                    oldName9+=ser[0].filename+"|";
                    fileUrl9+=ser[0].fileurl+"|";
                    fileType9+=ser[0].fileextend+"|";
                    obj1.find(".oldNameId").val(oldName9);
                    obj1.find(".fileUrlId").val(fileUrl9);
                    obj1.find(".fileTypeId").val(fileType9);
                    break;
                    case 10:
                    oldName10+=ser[0].filename+"|";
                    fileUrl10+=ser[0].fileurl+"|";
                    fileType10+=ser[0].fileextend+"|";
                    obj1.find(".oldNameId").val(oldName10);
                    obj1.find(".fileUrlId").val(fileUrl10);
                    obj1.find(".fileTypeId").val(fileType10);
                    break;
                    case 11:
                    oldName11+=ser[0].filename+"|";
                    fileUrl11+=ser[0].fileurl+"|";
                    fileType11+=ser[0].fileextend+"|";
                    obj1.find(".oldNameId").val(oldName11);
                    obj1.find(".fileUrlId").val(fileUrl11);
                    obj1.find(".fileTypeId").val(fileType11);
                    break;
                }
            }
            if(status == 2){
                alert("上传失败");
            }
        };
        //******************************************
    };
    $(function(){
           //删除
                  $(".imgssc").on("click",".close",function(){
                    obj2=$(this).parents(".AdhisArt");
                      var aa=$(this).parent(".c_k").find(".img").find("img").attr("src");
                      //var index=$(this).parent(".c_k").index();
                      var this_=$(this).parent(".c_k");
                      var index=this_.index();
                      if (confirm("确定删除该图片？")) {
                        $.ajax({
                                //url:"http://www.wkedu.cn:5555/sfjbpt/ReportInfoController/delfile",
                                url:'<%=basePath%>'+"wkrjsystem/upReportInfo/delFile",
                                type:"GET",
                                data:{realPath:aa},
                                dataType:"json",
                                timeout:30000,
                                success:function(result) {
                                        if(result.success){
                                            var oldName = obj2.find(".oldNameId").val();
                                            var fileUrl = obj2.find(".fileUrlId").val();
                                            var fileTypeId = obj2.find(".fileTypeId").val();
                                            var old = oldName.split("|");
                                            var newName = fileUrl.split("|");
                                            var fileType = fileTypeId.split("|");
                                             obj2.find(".fileUrlId").val(fileUrl.replace(newName[index]+"|",""));
                                             obj2.find(".oldNameId").val(oldName.replace(old[index]+"|",""));
                                             obj2.find(".fileTypeId").val(fileTypeId.replace(fileType[index]+"|",""));
                                            
                                            this_.remove();
                                        }
                                }, error:function(xhr,erroType,error,msg) {
                                    
                                }
                            });
                      }
                     });
        });
    </script>
</body>

</html>