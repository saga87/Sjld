<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/systemdev/myContext.jsp" %>
 <!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
<script type="text/javascript"
   src="system/js/user/principle.js"></script>

 </head>
 <body>
<!--添加用户开始-->
<form id="user_addWindow_form" method="post" class="liger-form">
	<div style="overflow:hidden;">
		    <div class="formdiv" style="display: none;">
                <label style="text-align:right;">用户id:</label>
                <input name="user_id" id="user_ids" class="ui-textbox">
            </div>
            <div class="formdiv">
                <label style="text-align:right;"><font color="red">*</font>账号名称:</label>
                <input id="sjc_user_userName"
                    name="user_name" data-options="required:true">
            </div>
            
            <div class="formdiv" id="sjc_user_password" >
                <label style="text-align:right;"><font color="red">*</font>账户密码:</label>
                <input name="user_password" type="password"
                	data-options="required:true" />
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
                <label style="text-align:right;"><font color="red">*</font>手机:</label>
                <input onblur="checkTel()" id="sjc_userview_tel"
                    name="user_tel" />
            </div>
            
            <div class="formdiv">
                <label style="text-align:right;">邮箱:</label>
                <input  id="email" name="email" />
            </div>
            
</form>
 </body>
 </html>