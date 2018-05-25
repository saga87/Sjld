<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.wkrj.upReportInfo.bean.WkrjUpReportInfoFile" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
	<meta name="viewport" content="width=100%, initial-scale=1, maximum-scale=1, user-scalable=no">
    <base href="<%=basePath%>">
    
    <title>巡查情况-修改</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/mobile/style.css">
    <link rel="stylesheet" type="text/css" href="system/css/style.css">
    <script type="text/javascript" src="plug-in/login/js/jquery.js"></script>
    <!-- <script type="text/javascript" src="js/new/jquery.js"></script>
    <script type="text/javascript" src="js/new/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="js/new/js.js"></script> -->
    <script type="text/javascript" src="plug-in/login/js/form.js"></script>
    <script src="plug-in/login/js/jquery.combo.select.js"></script>
    <link rel="stylesheet" href="system/css/combo.select.css">
    <script type="text/javascript">
    //修改
    function update() {
        var check_problem = document.getElementById("check_problem").value;
        var check_projectscope = document.getElementById("check_projectscope").value;
        var check_flag = document.getElementById("check_flag").value;
        var check_id = document.getElementById("check_id").value;
        var oldName = $('#oldNameId').val();
        var fileUrl = $('#fileUrlId').val();
        var fileType = $('#fileTypeId').val();
        if (check_flag == 1) {
            alert("已下达通知，不允许修改");
            return;
        }
        $.ajax({
            url: "wkrjsystem/upReportInfo/updateReport",
            data: {check_problem:check_problem, check_projectscope:check_projectscope, id:check_id, oldName:oldName, fileUrl:fileUrl, fileType:fileType},
            success: function(result){
                    try{
                        result = eval('('+result+')');
                    }catch(e){
                    }
                    if (result.success) {
                        alert(result.msg);
                        window.location = "wkrjsystem/upReportInfo/getUpReportInfoList?currentPaperNo=1";
                    } else {
                        alert(result.msg);
                    }
                 }
           }); 
    }
    /**
     * 删除附件
     */
    function delReportFile(fileid,filename,id) {
	    if (confirm("确定删除此附件吗")) {
	        $.ajax({
	            url: "wkrjsystem/upReportInfo/delReportFile",
	            data: {fileid:fileid, filename:filename},
	            success: function(result){
	                    try{
	                        result = eval('('+result+')');
	                        result = eval('('+result+')');
	                    }catch(e){
	                    }
	                    if (result.success) {
	                        alert(result.msg);
	                        window.location = "wkrjsystem/upReportInfo/getReportInfoById?id="+id;
	                    } else {
	                        alert(result.msg);
	                    }
	                 }
	           }); 
        }
    }
    //上传
    function uploadReport(id){
        if ($("#reportfj").val() == null || $("#reportfj").val() == "") {
            alert("请选择附件");
            return;
        }
        document.getElementById("sjc_uploadReportForm").action = "wkrjsystem/upReportInfo/uploadReportFile?id="+id;
        document.getElementById("sjc_uploadReportForm").submit();
    }
    /**
	 * 下载附件
	 */
	function downloadReportFile(file_yname, file_xname) {
	    var openwin = document.getElementById('sjcpopwindow_upReportInfo');
	    openwin.href = 'wkrjsystem/upReportInfo/downloadFile?fileName='+file_xname+"&realName="+file_yname;
	    openwin.click();
	}
    </script>
  </head>
  <%
  List<Map<String, Object>> reportDetail = (List<Map<String, Object>>)request.getAttribute("reportDetail");//System.out.println(reportDetail.get(0).get("reform_days"));
  List<WkrjUpReportInfoFile> reportFile = (List<WkrjUpReportInfoFile>)request.getAttribute("reportFile");
  List<Map<String, Object>> projectList = (List<Map<String,Object>>)request.getAttribute("projectList");//System.out.println("10".equals(reportDetail.get(0).get("reform_days").toString()));
  %>
  <script type="text/javascript">
    $(function(){
        $("#check_projectscope").val("<%=reportDetail.get(0).get("check_projectscope")%>");
        $("#reform_days").val("<%=reportDetail.get(0).get("reform_days")%>");
    });
  </script>
  <body>
    <div class="headerBar">
        <div class="header W640">
            <%@include file="top.jsp"%>
        </div>
        <div class="headerSmall W640 Bsh">
            <h1>修改</h1>
        </div>
    </div>
    <div style="height:80px"></div>
    <div class="W640">
        <div class="LeaveForm InfoUp">
        <%if (reportDetail != null && reportDetail.size() > 0) { %>
            <form id="sjc_AddReportForm" method="post" novalidate enctype="multipart/form-data">
                <a id="sjcpopwindow_upReportInfo" href="#" style="display:none;" target="_blank"></a>
                <input type="hidden" id="check_id" value="<%=reportDetail.get(0).get("check_id")%>">
                <input type="hidden" id="check_flag" value="<%=reportDetail.get(0).get("check_flag")%>">
                <div class="InfoUpInp">
                    <span>学校：</span>
                    <input name="title" id="school_name" value="<%=reportDetail.get(0).get("school_name")%>" readonly/>
                </div>
                        <div class="InfoUpInp FormItem">
                            <span>&nbsp;&nbsp;安全隐患类别：</span>
                            <select name="check_projectscope" id="check_projectscope">
                            <%
                            if (projectList != null) {
                            for(int i = 0; i < projectList.size(); i++) { 
                                String checked = "";
	                            if (projectList.get(i).get("project_id").equals(reportDetail.get(0).get("check_projectscope"))) {
	                                checked = "checked";
                                }
                            %>
                            <option value="<%=projectList.get(i).get("project_id") %>" <%=checked %>><%=projectList.get(i).get("project_name") %></option>
                            <%}
                            } %>
                            </select>
                        </div>
                        <div class="InfoUpInp FormItem">
                            <span>&nbsp;&nbsp;整改天数：</span>
                            <select name="reform_days" id="reform_days">
                                <option value=""></option>
                                <option value="1" <%="1".equals(reportDetail.get(0).get("reform_days").toString())? "checked":"" %>>1</option>
                                <option value="3" <%="3".equals(reportDetail.get(0).get("reform_days").toString())? "checked":"" %>>3</option>
                                <option value="5" <%="5".equals(reportDetail.get(0).get("reform_days").toString())? "checked":"" %>>5</option>
                                <option value="7" <%="7".equals(reportDetail.get(0).get("reform_days").toString())? "checked":"" %>>7</option>
                                <option value="10" <%="10".equals(reportDetail.get(0).get("reform_days").toString())? "checked":"" %>>10</option>
                            </select>
                        </div>
                <div class="InfoUpInp">
                    <span>安全隐患情况：</span><b><textarea rows="3" cols="20" name="check_problem" id="check_problem" ><%=reportDetail.get(0).get("check_problem")%></textarea></b>
                </div>
                <div class="InfoUpInp">
                    <span>附件：</span>
                </div>
                <div class="UpdateFile">
                <%if (reportFile != null) { %>
                <%for (int i = 0; i < reportFile.size(); i++) { 
                    String fileoriginalname = reportFile.get(i).getFile_yname();
                    String filename = reportFile.get(i).getFile_xname();
                    String id = reportFile.get(i).getCheck_id();
                    String fileid = reportFile.get(i).getFile_id();
                %>
                    <div class="InfoUpInp">
                        <a href="upload/reportInfo/<%=filename %>" target="_blank">打开</a><a href="javascript:void(0);" onclick="delReportFile('<%=fileid%>','<%=filename%>','<%=id%>')">删除</a><%-- <a href="javascript:void(0);" onclick="downloadReportFile('<%=fileoriginalname%>','<%=filename%>','<%=id%>')">下载</a> --%><i><%=fileoriginalname %></i>
                    </div>
                <%} %>
                <%} %>
                </div>
                <input  type="hidden"  name="oldName"  id="oldNameId">
                <input  type="hidden"  name="fileUrl"  id="fileUrlId">
                <input  type="hidden"  name="fileType"  id="fileTypeId">
            </form>
                <div class="InfoUpInp">
                    <%-- <form id="sjc_uploadReportForm" method="post" novalidate enctype="multipart/form-data">
                        <p class="File">
                            <input type="text" placeholder="附件名称"><b>浏览</b><input type="file" name="fj" id="reportfj">
                            <input type="button" class="" onclick="uploadReport('<%=reportDetail.get(0).get("check_id")%>')" style="border:0; text-indent:0" value="上传附件">
                        </p>
                    </form> --%>
                    <!-- <div class="images fl" style="width:70%">
                        <b>
                        <img src="system/imgs/addImg.png" alt="">
                        <form enctype="multipart/form-data" method="POST" id="photoform">
                            <input onclick="showActionSheet()" id="files" name="file">
                        </form>
                        </b>
                    </div> -->
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
		                    <span class="c_k"></span>
		                    <img style="float:left;margin:10px 10px;" src="system/imgs/addImg.png">
		                    <form enctype="multipart/form-data" method="POST" id="photoform">  
		                        <input id="files" onclick="showActionSheet()" name="file">  
		                    </form>
		                </b>
		            </div>
                </div>
                <p class="CommonInpsub clear"><input class="sub1 fl" type="submit" value="保存" onclick="update()" /><input class="sub2 fr" type="button" value="返回" onclick="history.go(-1)"></p>
        <%} %>
        </div>
    </div>
    <script type="text/javascript">
	    var oldName='';
	    var fileUrl='';
	    var fileType='';

	    var imgurl = "E:\aaa.jpg";//默认图片url
	
	    //显示log
	    function setLog(msg){
	        document.getElementById("msgid").innerHTML = msg;
	    }
	
	    function showActionSheet(){
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
	    }
	    
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

	        //本地上传
	        uexImage.onPickerClosed = function(info){
	            var infoData=eval("("+info+")");
	            if(infoData.isCancelled){
	                return;
	            }else{
	                imgurl = infoData.data;
	                $("#files").val(infoData.data);
	                uploadpic();
	            }
	        };
	        //相机上传
	        uexCamera.cbOpen = function(opId,dataType,data){
	            if(dataType==0) {
	                imgurl = data;
	                $("#files").val(imgurl);
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
	                $(".imgssc").find("b").before('<span class="c_k"><i class="img"><img width="70" height="70" src='+'<%=basePath%>'+ser[0].fileurl+' /></i><span class="close"><img src="system/imgs/close_w.png" /></span></span>');
	                uexUploaderMgr.closeUploader(uopCode);
	                $("#oldNameId").val('');
	                $("#fileUrlId").val('');
	                $("#fileTypeId").val('');
	                oldName+=ser[0].filename+"|";
	                fileUrl+=ser[0].fileurl+"|";
	                fileType+=ser[0].fileextend+"|";
	                
	                $("#oldNameId").val(oldName);
	                $("#fileUrlId").val(fileUrl);
	                $("#fileTypeId").val(fileType);
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
                      var aa=$(this).parent(".c_k").find(".img").find("img").attr("src");
                      //var index=$(this).parent(".c_k").index();
                      var this_=$(this).parent(".c_k");
                      var index=this_.index();
//                       appcan.ready(function() {
                          
//                       })  
						if (confirm("确定删除该图片？")) {
                        $.ajax({
                                //url:"http://www.wkedu.cn:5555/sfjbpt/ReportInfoController/delfile",
                                url:'<%=basePath%>'+"wkrjsystem/upReportInfo/delFile",
                                type:"GET",
                                data:{realPath:aa}, 
                                dataType:"json",
                                timeout:30000,
                                success:function(result) {
                                    //result = eval('(' + result + ')');
                                        if(result.success){
                                            var oldName = $('#oldNameId').val();
                                            var fileUrl = $('#fileUrlId').val();
                                            var fileTypeId = $("#fileTypeId").val();
                                            var old = oldName.split("|");
                                            var newName = fileUrl.split("|");
                                            var fileType = fileTypeId.split("|");
                                             $("#fileUrlId").val(fileUrl.replace(newName[index]+"|",""));
                                             $("#oldNameId").val(oldName.replace(old[index]+"|",""));
                                             $("#fileTypeId").val(fileTypeId.replace(fileType[index]+"|",""));
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
