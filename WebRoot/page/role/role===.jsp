<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">


</style>


<div style="width:100%;height:100%;">
	<div class="easyui-layout" data-options="fit:true" id="role_layout">
		<div data-options="region:'west',split:true,collapsed:true" title="部门列表"
			style="width:500px;">
			
			<div id="setMenuPermission"
				style="background:#eee;">
				<a href="javascript:void(0)" class="easyui-linkbutton" 
					ekper="wkrjsystem/wkrjRole/getRoleList"
					iconCls="icon-reload" plain="true" onclick="showAllRole()">显示全部角色</a>
			</div>
			
			<ul id="deptTree_role" class="easyui-tree" url="wkrjsystem/wkrjDept/getDeptTree"
				ekper="wkrjsystem/wkrjDept/getDeptTree"
				data-options="method:'get',animate:true">
			</ul>
		</div>
		<div data-options="region:'east',split:true,collapsed:true" title="功能菜单"
			style="width:500px;">
			<div id="setMenuPermission"
				style="background:#157FCB;">
				
				<a href="javascript:void(0)" class="easyui-linkbutton"
					ekper=""
					iconCls="icon-add" plain="true" onclick="treeChecked('menuTree')">全选</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					ekper="wkrjsystem/wkrjRole/setMenuPermission"
					iconCls="icon-add" plain="true" onclick="setMenuPermission()">保存设置</a>
				
			</div>
			<ul id="menuTree" class="easyui-tree" url=""
				data-options="method:'get',animate:true,cascadeCheck:false">
			</ul>
		</div>
		<div data-options="region:'center'">
			<div id="roletoolbar" style="background:#eee;">
				<a href="javascript:void(0)" class="easyui-linkbutton"
				    ekper="wkrjsystem/wkrjRole/addRole"
					iconCls="icon-add" plain="true" onclick="addRole()">添加</a> <a
					href="javascript:void(0)" class="easyui-linkbutton" 
					ekper="wkrjsystem/wkrjRole/updateRole"
					iconCls="icon-edit" plain="true" onclick="editRole()">修改</a> <a
					href="javascript:void(0)" class="easyui-linkbutton"
					ekper="wkrjsystem/wkrjRole/delRole"
					iconCls="icon-remove" plain="true" onclick="delRole()">删除</a>  <a
					href="javascript:void(0)" class="easyui-linkbutton"
					ekper="wkrjsystem/wkrjRole/copyRole"
					iconCls="icon-edit" plain="true" onclick="copyRole()">复制</a>
			</div>
			<table id="roledatagrid" class="easyui-datagrid"
				data-options="url:'wkrjsystem/wkrjRole/getRoleList',method:'get',singleSelect:true,toolbar:'#roletoolbar',fit:true,fitColumns:true,pagination:true,pageSize:20">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<!--<th data-options="field:'role_id',align:'center'" width="80">角色
							ID</th>-->
						<th data-options="field:'role_name',align:'center'" width="100">角色名称</th>
						<th data-options="field:'role_type',align:'center'" width="80" formatter="role_type"
							>角色类型</th>
						<th data-options="field:'role_other',align:'center'" width="200">备注</th>
						<th data-options="field:'op',align:'center'" width="200" formatter="op">操作</th>
					</tr>
				</thead>
			</table>
		</div>
		<!-- 添加用户窗口开始 -->
		<div id="roleWindow" class="easyui-dialog" modal="true"
			style="width: 400px; height: 200px; padding: 10px 20px" closed="true"
			buttons="#role-buttons">
			<form id="roleForm" method="post" novalidate>
				<div class="fitem" style="display: none;">
					<label style="text-align:right;">角色id:</label> <input
						name="role_id" id="role_ids" class="easyui-validatebox">
				</div>
				<div class="fitem">
					<label style="text-align:right;">角色名称:</label> <input
						name="role_name" style="width: 200px;" class="easyui-validatebox">
				</div>
				<div class="fitem">
					<label style="text-align:right;">角色部门:</label> 
					
					<input class="easyui-combotree" name="role_dept" id="role_dept"
							data-options="method:'get'" url="wkrjsystem/wkrjDept/getDeptTree"
							style="width:200px;" />
				</div>
				<div class="fitem">
					<label style="text-align:right;">角色备注:</label>
					<textarea style="width:200px;" name="role_other"></textarea>
				</div>
			</form>
		</div>
		<div id="role-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton"
			    ekper="wkrjsystem/wkrjRole/addRole"
				iconCls="icon-ok" onclick="saveRole()">保存</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel"
				ekper=""
				onclick="javascript:$('#roleWindow').dialog('close')">关闭</a>
		</div>
		<!-- 添加用户窗口结束 -->
	</div>
</div>

<script type="text/javascript" src="system/js/role/role.js"></script>