<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">

<script type="text/javascript">
   $(function (){
	   $("#dept_parent_id").ligerComboBox({
            width :300,height: 30,selectBoxWidth:300,selectBoxHeight: 200, 
            valueField: 'id', treeLeafOnly: false, isMultiSelect: true,
            tree: { checkbox: false, url: 'wkrjsystem/wkrjDept/getDeptTree', ajaxType: 'post', idField: 'id' }
	   });
            
       
   });
</script>
</head>
<body>
<!--添加部门开始-->
<form id="dept_addWindow_form" method="post" class="liger-form">
	<div class="formdiv" style="display: none;">
	   <label style="text-align:right;">组织结构id:</label>
	    <input name="dept_id" id="dept_id" class="ui-textbox">
	</div>
	<div class="formdiv">
	    <label style="text-align:right;">部门名称:</label>
	    <input data-options="required:true" name="dept_name">
	</div>
	<div class="formdiv">
	    <label style="text-align:right;">上级部门:</label>
	    <div class="r_div">
	    	<input name="dept_parent_id" id="dept_parent_id"
	            data-options="method:'post'"/>
	    </div>
	</div>
	<div class="formdiv">
	    <label style="text-align:right;">显示顺序:</label>
	    <input data-options="required:true"
	        name="dept_order" />
	</div>
	<div class="formdiv">
	    <label style="text-align:right;">备　　注:</label>
	    <input name="dept_other"  />
	</div>
</form>
</body>
</html>