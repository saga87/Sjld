<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
   
   <!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
    <script type="text/javascript"
   src="system/js/user/user.js"></script>

<script type="text/javascript">
       $(function (){
           var dialog = frameElement.dialog;
           var dialogData = dialog.get('data');//获取data参数
           $("#user_updateWindow_form").ligerForm().setData(dialogData.content);
           $dialog = top.$("iframe[src='system/user/user_update.jsp']")[0].contentWindow;//用于关闭dialog    
       });
     
</script>
 </head>
 <body>
<!--添加菜单开始-->
<form id="user_updateWindow_form" method="post" class="liger-form">
	<div style="overflow:hidden;">
            <div class="formdiv">
                <label style="text-align:right;"><font color="red">*</font>账号名称:</label>
                <input id="sjc_user_userName"
                    name="user_name" data-options="required:true" />
            </div>
            <div class="formdiv" id="sjc_user_password" style="display: none;">
                <label style="text-align:right;"><font color="red">*</font>账户密码:</label>
                <input name="user_password" 
                    type="password" class="ui-textbox" data-options="required:true" />
            </div>
            <div class="formdiv">
                <label style="text-align:right;"><font color="red">*</font>真实姓名:</label>
                <input data-options="required:true" name="user_realname" />
            </div>
            
            <div class="formdiv">
                <label style="text-align:right;"><font color="red">*</font>所属学校:</label>
                <div class="r_div">
                	<input id="school_name" name="school_name"  />
                </div>
            </div>
            
            <div class="formdiv">
                <label style="text-align:right;"><font color="red">*</font>县市区:</label>
                <div class="r_div">
                	<input id="counties" name="counties"  />
                </div>
            </div>
            
            <div class="formdiv">
                <label style="text-align:right;">办公电话:</label>
                <input  id="sjc_userview_tel" name="telephone" />
            </div>
            
            <div class="formdiv">
                <label style="text-align:right;">手机:</label>
                <input onblur="checkTel()" id="sjc_userview_tel"
                    name="user_tel" />
            </div>
            
            <div class="formdiv">
                <label style="text-align:right;">邮箱:</label>
                <input  id="email" name="email" />
            </div>
		
		<input type="hidden" style="display:none;" name="user_id"  />
	</div>
</form>
 </body>
 </html>