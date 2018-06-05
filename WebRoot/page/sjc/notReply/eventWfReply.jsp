<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<!-- <script language="javascript" src="plug-in/lodop/LodopFuncs.js"></script> -->
<script type="text/javascript" src="system/js/commonStatus.js"></script>
<style type="text/css">
#reply_content_div span {
	background: #FFFFFF url(../images/ui/input.gif) repeat-x top;
}
</style>
<script type="text/javascript">
	var cbd_fileurl;
	$(function() {
		var dialog = frameElement.dialog;
		var dialogData = dialog.get('data');//获取data参数
		$("#wfNotReply_replyWindow_form").ligerForm().setData(
				dialogData.content);
		cbd_fileurl = dialogData.content.cbd_fileurl;
		//判断公开与隐藏
		if (dialogData.content.caller_username == "隐藏") {
			//$("#reply_content_div").html("<span type='text' id='item1' contenteditable='true' style='display:inline-block;float:none;width:230px;border:none;font-size:14px;outline:none;border-bottom:1px solid #ccc;'></span>（部门）接<span type='text' id='item2' contenteditable='true' style='display:inline-block;float:none;width:100px;border:none;font-size:14px;outline:none;border-bottom:1px solid #ccc;'></span>号承办单后，<div><span contenteditable='true' type='text' id='item3' style='display:inline-block;float:none;width:100%;border:none;font-size:14px;outline:none;border-bottom:1px solid #ccc;'></span></div> （'立即责成<span contenteditable='true' type='text' id='item4' style='display:inline-block;float:none;width:200px;border:none;font-size:14px;outline:none;border-bottom:1px solid #ccc;'></span>进行调查处理'或'直接答复'或'进行现场处理'等）。<br>一、调查处理结果：<span contenteditable='true' type='text' id='item5' style='display:inline-block;float:none;width:100%;min-height:100px;border:none;font-size:14px;outline:none;border-bottom:1px solid #ccc;vertical-align:top'></span><br>（此处填写调查处理意见）。<br>二、经办单位：<span contenteditable='true'  type='text' id='item12' style='display:inline-block;float:none;width:130px;border:none;font-size:14px;outline:none;border-bottom:1px solid #ccc;'></span> (具体负责处理科室）  联系人：<span contenteditable='true' type='text' id='item13' style='display:inline-block;float:none;width:50px;border:none;font-size:14px;outline:none;border-bottom:1px solid #ccc;'></span>（姓名） 职务：<span contenteditable='true' type='text' id='item14' style='display:inline-block;float:none;width:80px;border:none;font-size:14px;outline:none;border-bottom:1px solid #ccc;'></span>    联系电话：<span contenteditable='true' type='text' id='item15' style='display:inline-block;float:none;width:120px;border:none;font-size:14px;outline:none;border-bottom:1px solid #ccc;'></span>(来电人后期如有问题可拨打该电话进行反映）。<br>");
			$("#reply_content_divHidden").show();
			$("#reply_content_divPublic").remove();
		} else {
			//$("#reply_content_div").html("<span type='text' id='item1' contenteditable='true' style='display:inline-block;float:none;width:230px;border:none;font-size:14px;outline:none;border-bottom:1px solid #ccc;'></span>（部门）接<span type='text' id='item2' contenteditable='true' style='display:inline-block;float:none;width:100px;border:none;font-size:14px;outline:none;border-bottom:1px solid #ccc;'></span>号承办单后，<div><span contenteditable='true' type='text' id='item3' style='display:inline-block;float:none;width:100%;border:none;font-size:14px;outline:none;border-bottom:1px solid #ccc;'></span></div> （'立即责成<span contenteditable='true' type='text' id='item4' style='display:inline-block;float:none;width:200px;border:none;font-size:14px;outline:none;border-bottom:1px solid #ccc;'></span>进行调查处理'或'直接答复'或'进行现场处理'等）。<br>一、调查处理结果：<span contenteditable='true' type='text' id='item5' style='display:inline-block;float:none;width:100%;min-height:100px;border:none;font-size:14px;outline:none;border-bottom:1px solid #ccc;vertical-align:top'></span><br>（此处填写调查处理意见）。<br>二、回访情况：<span contenteditable='true' type='text' id='item6' style='text-align:center;display:inline-block;float:none;width:50px;border:none;font-size:14px;outline:none;border-bottom:1px solid #ccc;'></span>年<span contenteditable='true' type='text' id='item7' style='text-align:center;display:inline-block;float:none;width:50px;border:none;font-size:14px;outline:none;border-bottom:1px solid #ccc;'></span>月<span contenteditable='true' type='text' id='item8' style='text-align:center;display:inline-block;float:none;width:50px;border:none;font-size:14px;outline:none;border-bottom:1px solid #ccc;'></span>日<span contenteditable='true' type='text' id='item9' style='display:inline-block;float:none;width:50px;border:none;font-size:14px;outline:none;border-bottom:1px solid #ccc;'></span>（工作人员姓名）工作人员用<span contenteditable='true' type='text' id='item10' style='display:inline-block;float:none;width:120px;border:none;font-size:14px;outline:none;border-bottom:1px solid #ccc;'></span>（电话号码）电话联系来电人（如是直接答复或现场处理的，写明办理完成后进行了现场回访）。<br>三、来电人是否满意：来电人表示<input type='radio' name='satisfyDu' value='1'>满意<input type='radio' name='satisfyDu' value='0'>基本满意<input type='radio' name='satisfyDu' value='2'>不满意<div id='notSatisfy_reason' style='display:none'><span contenteditable='true' type='text' id='item11' style='display:inline-block;float:none;width:520px;border:none;font-size:14px;outline:none;border-bottom:1px solid #ccc;'></span>（如不满意请详细说明原因）。</div><br>四、经办单位：<span contenteditable='true'  type='text' id='item12' style='display:inline-block;float:none;width:130px;border:none;font-size:14px;outline:none;border-bottom:1px solid #ccc;'></span> (具体负责处理科室）  联系人：<span contenteditable='true' type='text' id='item13' style='display:inline-block;float:none;width:50px;border:none;font-size:14px;outline:none;border-bottom:1px solid #ccc;'></span>（姓名） 职务：<span contenteditable='true' type='text' id='item14' style='display:inline-block;float:none;width:80px;border:none;font-size:14px;outline:none;border-bottom:1px solid #ccc;'></span>    联系电话：<span contenteditable='true' type='text' id='item15' style='display:inline-block;float:none;width:120px;border:none;font-size:14px;outline:none;border-bottom:1px solid #ccc;'></span>(来电人后期如有问题可拨打该电话进行反映）。<br>");
			$("#reply_content_divPublic").show();
			$("#reply_content_divHidden").remove();
			//默认上当前日期
			var myDate = new Date();
			$("#item6").html(myDate.getFullYear()); //获取完整的年份(4位,1970-????)
			//         $("#item7").html(myDate.getMonth()+1);       //获取当前月份(0-11,0代表1月)
			//         $("#item8").html(myDate.getDate());        //获取当前日(1-31)
		}
		$("#item1").html(dialogData.content.dept_name);
		$("#item2").html(dialogData.content.event_no);
		//根据radio值设置可见不可见
		$(":radio").click(function() {
			if ($(this).val() == 1) {
				$("#reply_div").show();
				$("#zhuanban_div").hide();
				$("#back_div").hide();
			} else if ($(this).val() == 2) {
				$("#reply_div").hide();
                $("#zhuanban_div").show();
                var cd_combobox = $("#chengban_dept").ligerComboBox({
	                 width: 240,height: 30,selectBoxWidth: 240,selectBoxHeight: 100, treeLeafOnly: false, isMultiSelect: false,
	                 tree: { checkbox: false, url: 'eventWf/WkrjEventWf/getDeptTree', ajaxType: 'post', idField: 'id', idFieldName: 'text' }
	              });
                $("#back_div").hide();
			} else if ($(this).val() == 3) {
                $("#reply_div").hide();
                $("#zhuanban_div").hide();
                $("#back_div").show();
            }
		});
		
		/* var reply_ue=UE.getEditor("reply_content");
		reply_ue.ready(function() {
		        //清空编辑器的内容
		        reply_ue.setContent('');           
		        $.ajax({
		            type:"POST",
		            url:"eventReportWf/WkrjEventReportWf/getDeptReplyFormat",
		            dataType:"json",
		            success:function(data){
		                if(data.success){
		                    reply_ue.setContent(data.obj);                     
		                }
		            }
		            
		        });
		    }); */

		$("#uploadify").uploadify({
			'buttonText' : '请选择',
			'height' : 30,
			'swf' : 'systemdev/js/uploadfy/uploadify.swf',
			'uploader' : '../../../eventReport/WkrjEventReport/uploadPic',
			'width' : 120,
			'auto' : true,
			'multi' : true,//多选
			'fileTypeExts' : '*.jpg;*.jpeg;*.png;*.xls;*.xlsx;*.doc;*.docx',
			'formData' : {
				file_index : 0
			},
			'fileObjName' : 'deptOpinion_file',
			//         'onSelect':function(){
			//             $("#isupload").val(0);
			//         },
			'onUploadSuccess' : function(file, data, response) {
				var d = eval('(' + data + ')');
				d = eval('(' + d + ')');
				var filename = d.filename;
				var yFileName = d.yFileName;
				var file_type = d.fileType;
				//xfilename += filename+",";
				//yfilename += yFileName+",";
				//filetype += file_type+",";
				$("#file_xname").val(filename + "," + $("#file_xname").val());
				$("#file_yname").val(yFileName + "," + $("#file_yname").val());
				$("#file_type").val(file_type + "," + $("#file_type").val());
				top.$(".l-window-mask").hide();
				//$("#isupload").val(1);
			},
			'removeCompleted' : false
		});
	});
	/*
	 * 预览
	 */
	function preview() {
		var event_id = $("#event_id").val();
		print(event_id);
	}
</script>


</head>
<body>
<link href="systemdev/js/uploadfy/uploadify.css"
     rel="stylesheet" type="text/css" />
 <script type="text/javascript"
   src="systemdev/js/uploadfy/jquery.uploadify.min.js"></script>
 <script type="text/javascript"
   src="systemdev/js/uploadfy/jquery.uploadify.js"></script>
<form id="wfNotReply_replyWindow_form" method="get" class="liger-form">
    <!-- <div id="accept_user"></div> -->
    <div style="overflow:hidden;">
        <div class="Co">
            <label><span class="text_red" >*</span>&nbsp;&nbsp;受理渠道:</label>
            <input type="text" name="source" id="source" disabled/>
            <input type="hidden" id="accept_source" name="accept_source">
            <label><span class="text_red" >*</span>&nbsp;&nbsp;处理时限:</label>
            <input type="text" id="deal_days" name="deal_days" disabled/>
        </div>
        <div class="Co">
            <label><span class="text_red" >*</span>&nbsp;&nbsp;受理类别:</label>
            <input type="text" name="content_type" id="content_type" data-options="method:'post'" disabled/>
            <input type="hidden" id="content_type_hidden">
            <label><span class="text_red" >*</span>&nbsp;&nbsp;业务类型:</label>
            <input type="text" id="nature" name="nature" disabled/>
            <input type="hidden" id="business_type" name="business_type">
        </div>
        <div class="Co">
            <label><span class="text_red" >*</span>&nbsp;&nbsp;投诉标题:</label>
            <input type="text" id="event_title" name="event_title" style="width:700px;" disabled/>
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;&nbsp;&nbsp;投诉内容:</label>
            <div class="r_div">
                <textarea id="event_content" name="event_content" style="height:100px;width:700px;" disabled></textarea>
            </div>
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;&nbsp;&nbsp;事件备注:</label>
            <div class="r_div">
                <textarea name="event_other" id="event_other" style="height:100px;width:700px;" disabled></textarea>
            </div>
        </div>
        <div class="Co">
            <label>&nbsp;&nbsp;&nbsp;&nbsp;处理结果:</label>
            <input type="radio" name="deal_result" value="1"/>回复
            <input type="radio" name="deal_result" value="2"/>转办
            <input type="radio" name="deal_result" value="3"/>退回
        </div>
        <div class="Co" style="display:none" id="reply_div">
            <label>&nbsp;&nbsp;&nbsp;&nbsp;回复内容:</label>
            <div class="r_div">
                <textarea name="opt_content" id="reply_content" style="height:100px;width:700px;" ></textarea>
            </div>
        </div>
        <div class="Co" style="display:none" id="zhuanban_div">
            <label><span class="text_red" >*</span>&nbsp;&nbsp;受理单位:</label>
            <input type="text" name="" id="chengban_dept" />
            <input type="hidden" name="chengban_dept_hidden" id="chengban_dept_hidden" />
        </div>
        <div class="Co" style="display:none" id="back_div">
            <label>&nbsp;&nbsp;&nbsp;&nbsp;退回理由:</label>
            <div class="r_div">
                <textarea name="opt_content" id="back_reason" style="height:100px;width:700px;" ></textarea>
            </div>
        </div>
        <!-- <div class="Co">
            <label>&nbsp;&nbsp;附件:</label>
            <div class="r_div">
                <input type="file" name="deptOpinion_file" id="uploadify" />
            </div>
                            <input type="hidden" name="file_yname" id="file_yname" />
                            <input type="hidden" name="file_xname" id="file_xname" />
                            <input type="hidden" name="file_type" id="file_type" />
        </div> -->
        <input type="hidden" name="event_id" id="event_id">
        <input type="hidden" name="event_no" id="event_no">
    </div>
</form>
</body>
</html>