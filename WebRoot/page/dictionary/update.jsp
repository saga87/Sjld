<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<script type="text/javascript">
   $(function (){
	    var dialog = frameElement.dialog;
	    var dialogData = dialog.get('data').content;//获取data参数
	    $("#update_form").ligerForm().setData(dialogData);

	    var box=$("#typeparentid").ligerComboBox({
            width :300,height: 30,selectBoxWidth:300,selectBoxHeight: 100, 
            valueField: 'id',treeLeafOnly: false, isMultiSelect: true,
            tree: { checkbox: false, url: 'wkrjsystem/wkrjDataDictionary/getDataDictionaryTree', ajaxType: 'post', idField: 'id' }
	   });
	   if(dialogData.typeparentid=="-1"){
		   box.setText(""); 		   		   
	   }else{
		   box.selectValue(dialogData.typeparentid); 		   
	   }
   });
</script>
</head>
<body>
<form id="update_form" method="post" class="liger-form">
	<div class="co">
	    <label style="text-align:right;">名称:</label>
	    <input type="text" name="typename" id="typename">
	</div>
	<div class="co">
	    <label style="text-align:right;">编码:</label>
	    <input type="text" name="typecode" id="typecode">
	</div>
	<div class="co">
	    <label style="text-align:right;">上级:</label>
	    <input type="text" name="typeparentid" id="typeparentid">
	</div>
    <div class="co">
        <label style="text-align:right;">备注:<br/></label>
        <div class="r_div">
        	<textarea rows="4" cols="70" name="notes" id="notes"></textarea>
        </div>
    </div>
</form>
 </body>
 </html>