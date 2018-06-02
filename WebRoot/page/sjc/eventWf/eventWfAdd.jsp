<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
<!doctype html>
<html lang="en">
 <head>
  	<meta charset="UTF-8">
  	<script src="plug-in/jquery/jquery.autocompleter.js"></script>
	<link rel="stylesheet" href="plug-in/jquery/main.css">
 </head>
 <body>
 
<style type="text/css">
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
    margin-left:100px;
    width:244px;
}
</style>

    <script type="text/javascript"> var user_id = "<%=user_id%>";</script>
    <script type="text/javascript" src="page/sjc/eventWf/eventWf.js"></script>
    <script type="text/javascript" src="system/js/commonStatus.js"></script>
    <!-- <script type="text/javascript" src="plug-in/jquery/ajaxfileupload.js"></script> -->
   
<script type="text/javascript">
var lay;
var dialog;
var addr_combobox;
var as_combobox;
var ct_combobox;
var cn_combobox;
var dl_combobox;
var cu_combobox;
var cd_combobox;
var dt_combobox;
       $(function (){
            dialog = frameElement.tab;
            lay=$("#eventWf_report_layout").ligerLayout({rightWidth:560,isRightCollapse: true});
            lay.setRightCollapse(false);
            
            var parentID = "59d063c2-e978-435d-b5e7-94ae23e46f43";
            as_combobox = $("#accept_source").ligerComboBox({
                url: 'eventWf/WkrjEventWf/getDataDictionary?parentID='+parentID,
                valueField: 'id',
                textField: 'name',
                width: 240,height: 30,selectBoxWidth: 240,selectBoxHeight: 100
            });
            parentID = "6c3d5d3b-17c4-42ac-b465-370b1ff11b2e";
//             ct_combobox = $("#content_type").ligerComboBox({
//                 url: 'eventWf/WkrjEventWf/getDataDictionary?parentID='+parentID,
//                 valueField: 'id', keySupport: true,
//                 textField: 'name',
//                 width: 240,height: 30,selectBoxWidth: 240,selectBoxHeight: 200
//             });
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
            parentID = "02";
            cn_combobox = $("#business_type").ligerComboBox({
                url: 'eventWf/WkrjEventWf/getDataDictionary?parentID='+parentID,
                valueField: 'id',
                textField: 'name',
                width: 240,height: 30,selectBoxWidth: 240,selectBoxHeight: 100
            });
            parentID = "deal_days";
            $("#deal_days").ligerComboBox({
                url: 'eventWf/WkrjEventWf/getDataDictionary?parentID='+parentID,
                valueField: 'id',
                textField: 'name',
                width: 240,height: 30,selectBoxWidth: 240,selectBoxHeight: 100
            });
            cd_combobox = $("#chengban_dept").ligerComboBox({
//                                 url: 'eventWf/WkrjEventWf/getDeptTree',
//                                 valueField: 'dept_id',isMultiSelect : true,isShowCheckBox : true,
//                                 textField: 'dept_name',
                                width: 240,height: 30,selectBoxWidth: 240,selectBoxHeight: 100, treeLeafOnly: false, isMultiSelect: false,
                                tree: { checkbox: false, url: 'eventWf/WkrjEventWf/getDeptTree', ajaxType: 'post', idField: 'id', idFieldName: 'text' }
                             });
            
            //$dialog = top.$("iframe[src='page/sjc/eventReport/message_add.jsp']")[0].contentWindow;//用于关闭dialog
            //var messagea_ue=UE.getEditor("event_content");
            
       });
       function split( val ) {
           return val.split( /,\s*/ );
       }
     
       function ajaxFileUpload() {
    	   $(".tjj")[0].hidden = true;
           $(".file")[0].hidden = true;
           var str=$("#file").val();
           var arr=str.split('\\');//注split可以用字符或字符串分割 
           var my=arr[arr.length-1];//这就是要取得的图片名称 
           var end=my.substr(my.lastIndexOf('.')+1,my.length-my.lastIndexOf('.')+1);
               if(end.toLowerCase() != "doc" && end.toLowerCase() != "docx"){
               alert("只允许选择word文件！");
               return false;
           }
           $("#cbd_loading3").show();
           $.ajaxFileUpload
           (
               {
                   url: 'eventWf/WkrjEventWf/uploadCbd', //用于文件上传的服务器端请求地址
                   secureuri: false, //是否需要安全协议，一般设置为false
                   fileElementId: 'file', //文件上传域的ID
                   dataType: 'json', //返回值类型 一般设置为json
                   success: function (data, status)  //服务器成功响应处理函数
                   {
                       if(data.success){
                    	   $(".tjj")[0].hidden = false;
                    	   $(".file")[0].hidden = false;
                           var attributes = data.attributes;
                           var name = attributes.name;
                           var tel = attributes.tel;
                           var address = attributes.address;
                           var handleDay = attributes.handleDay;
                           var leadOption = attributes.leadOption;
                           var ldcontent = attributes.ldcontent;
                           var handleOption = attributes.handleOption;
                           var handleResult = attributes.handleResult;
                           var zbzx = attributes.zbzx;
                           var no = attributes.no;
                           var time = attributes.time;
                           var fileurl = attributes.fileurl;
                           var cbd_filename = attributes.filename;
                           
                           $("#caller_username").val(name);
                           $("#caller_tel").val(tel);
                           $("#address2").val(address);
                           $("#deal_days").val(handleDay);
                           $("#deal_days_val").val(handleDay);
                           //dd_combobox.setValue(handleDay);
                           $("#event_content").val(ldcontent);
                           $("#caller_username").val(name);
                           //$("#deal_opinion").val(handleOption);
                           $("#zhuanban_instruction").val(handleOption);
                           $("#cbd_leaderpishi").val(leadOption);
                           $("#cbd_dealresult").val(handleResult);
                           $("#cbd_zbzx").val(zbzx);
                           $("#cbd_no").val(no);
                           $("#event_no").val(no);
                           $("#cbd_time").val(time);
                           $("#fileurls").val(fileurl);
                           $("#cbd_filename").val(cbd_filename);

                       }else{
                           alert(data.msg);
                       }
                       
                       $("#cbd_loading3").hide();
                       
                   },
                   error: function (data, status, e)//服务器响应失败处理函数
                   {
                       //alert(e);
                       
                   }
               }
           );
           return false;
       } 

</script>
<style>
.Co input{width:30%}
</style>
<div id="eventWf_report_layout" style="width:100%;">    
<!--添加来电受理开始-->
<form id="eventWf_addWindow_form" method="post" class="liger-form" action="eventWf/WkrjEventWf/addEventWf">
    <div style="overflow:hidden;">
        <!-- <div class="co">
          <label style="text-align:right;">上传承办单:</label> 
          <div class="r_div" style="position:relative;">
            <input type="button" value="添加承办单" class="tjj"><input class="file" onchange="ajaxFileUpload()"  type="file" name="file" id="file"  accept="doc,docx" />          
            <input type="hidden" id="fileurls" name="fileUrl" value="">
          </div>
        </div> -->
        <div class="Co">
            <label><span class="text_red" >*</span>&nbsp;&nbsp;受理渠道:</label>
            <input type="text" name="accept_source" id="accept_source" disabled/>
            <label><span class="text_red" >*</span>&nbsp;&nbsp;处理时限:</label>
            <input type="text" id="deal_days" name="deal_days" />
        </div>
        <div class="Co">
            <label><span class="text_red" >*</span>&nbsp;&nbsp;受理类别:</label>
            <input type="text" name="content_type" id="content_type" data-options="method:'post'"/>
            <input type="hidden" id="content_type_hidden">
            <label><span class="text_red" >*</span>&nbsp;&nbsp;业务类型:</label>
            <input type="text" id="business_type" name="business_type" />
        </div>
        <div class="Co">
            <label><span class="text_red" >*</span>&nbsp;&nbsp;投诉标题:</label>
            <input type="text" id="event_title" name="event_title" style="width:700px;"/>
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;&nbsp;&nbsp;投诉内容:</label>
            <div class="r_div">
                <textarea id="event_content" name="event_content" style="height:100px;width:700px;"></textarea>
            </div>
        </div>
        <div class="Co" style="" id="zhuanban_div">
            <label><span class="text_red" >*</span>&nbsp;&nbsp;受理单位:</label>
            <input type="text" name="chengban_dept" id="chengban_dept" />
            <input type="hidden" name="chengban_dept_hidden" id="chengban_dept_hidden" />
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;&nbsp;&nbsp;事件备注:</label>
            <div class="r_div">
                <textarea name="event_other" id="event_other" style="height:100px;width:700px;"></textarea>
            </div>
        </div>
    </div>
</form>

    <!-- 加载中提示 begin -->
    <div class="loading_con" id="cbd_loading3" style="display: none;">
    <!-- <img alt="" src="page/mspage/imgs/loading_3.gif" style="position: fixed;top: 45%;left: 38%"> -->
    </div>
    <!-- 加载中提示end -->

<!--添加来电受理结束-->
</div>
 </body>
 </html>