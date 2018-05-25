<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">

<script type="text/javascript">
   $(function (){
	   $("#dept_parent_id").ligerComboBox({
            width :300,height: 30,selectBoxWidth:300,selectBoxHeight: 100, 
            valueField: 'id', treeLeafOnly: false, isMultiSelect: true,
            tree: { checkbox: false, url: 'wkrjsystem/wkrjDept/getDeptTree', ajaxType: 'post', idField: 'id' }
	   });
            
       $("#city_or_county").ligerComboBox({
    	   width:300,
           height: 30,
           //url:'wkrjsystemdev/wkrjModule/getGridInfo.wk', isMultiSelect: false,
           isMultiSelect: false,
           onSelected: function (newValue) {
               if(1==newValue){
                   $("#juzhang_div").show();
                   $("#fenguan_div").show();
                   $("#anquankezhang_div").show();
                   //$("#fenguanlingdao_div").hide();
                   //$("#fenguanlingdao_div").remove();
                   $("#fenguan_label").html("分管局长");
                   $("#juzhang").val('');
                   $("#fenguan").val('');
                   $("#anquankezhang").val('');
               }else{
                   $("#juzhang_div").hide();
                   //$("#fenguanjuzhang_div").hide();
                   //$("#fenguanjuzhang_div").remove();
                   $("#fenguan_div").show();
                   $("#anquankezhang_div").show();
                   $("#fenguan_label").html("分管领导");
                   $("#juzhang").val('');
                   $("#fenguan").val('');
                   $("#anquankezhang").val('');
               }
           }
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
                    <label>是否县级市:</label>
                    <div class="r_div">
	                    <select id="city_or_county" name="city_or_county">
	                        <option></option>
	                        <option value="1">是</option>
	                        <option value="0">否</option>
	                    </select>  
                    </div>
                </div>
                <div id="juzhang_div" style="display:none" class="Co">
                    <label>局长:</label>
                    <input id="juzhang" name="juzhang"/>
                </div>
                <div id="fenguan_div" style="display:none" class="Co">
                    <label id="fenguan_label">分管领导:</label>
                    <input id="fenguan" name="fenguan" />
                </div>
                <div id="anquankezhang_div" style="display:none" class="Co">
                    <label>安全科长(多人的话逗号分隔):</label>
                    <input id="anquankezhang" name="anquankezhang"/>
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