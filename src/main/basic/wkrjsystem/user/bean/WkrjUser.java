﻿package wkrjsystem.user.bean;

import java.util.List;

import wkrjsystem.role.bean.WkrjRole;

/**
 * 岗位模块表 对应数据库表：wkrj_sys_user
 * 
 * @author zhaoxiaohua
 * 
 */
public class WkrjUser {

	private String user_id;
	private String station_id;// 岗位名称
	private String dept_id;// 部门名称
	private String dept_name;// 部门名称
	private String user_name;// 用户名
	private String user_password;// 密码
//	private List<WkrjUserRole> user_role;// 角色
	private List<WkrjRole> user_role;// 角色
	private String user_realname;// 真实姓名
	private String user_card;// 身份证
	private String user_is_enable;// 是否禁用0不禁用1禁用
	private String user_inputtime;// 录入时间
	private String user_last_ip;// 最后登录ip
	private String user_no;// 工号--仅用于学院OA
	private String user_last_time;// 最后登录时间
	private String user_tel;// 电话
	private int user_accounttype;// 用户类型0是普通用户1是开发管理员用户
	private String inputuserdept;// 录入人所在部门
	private String unit_name;// 单位名称
	
	/*以下给校长用户属性*/
	private String school_name;//学校
	private String counties;//县市区
	private String telephone;//办公电话
	private String email;//邮箱
	
	
	
	public String getSchool_name() {
		return school_name;
	}

	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}

	public String getCounties() {
		return counties;
	}

	public void setCounties(String counties) {
		this.counties = counties;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUnit_name() {
		return unit_name;
	}

	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}

	public String getInputuserdept() {
		return inputuserdept;
	}

	public void setInputuserdept(String inputuserdept) {
		this.inputuserdept = inputuserdept;
	}


	public int getUser_accounttype() {
		return user_accounttype;
	}

	public void setUser_accounttype(int user_accounttype) {
		this.user_accounttype = user_accounttype;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getStation_id() {
		return station_id;
	}

	public String getUser_inputtime() {
		return user_inputtime;
	}

	public void setUser_inputtime(String user_inputtime) {
		this.user_inputtime = user_inputtime;
	}

	public void setStation_id(String station_id) {
		this.station_id = station_id;
	}

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	

//	public List<WkrjUserRole> getUser_role() {
//		return user_role;
//	}
//
//	public void setUser_role(List<WkrjUserRole> user_role) {
//		this.user_role = user_role;
//	}

	public List<WkrjRole> getUser_role() {
		return user_role;
	}

	public void setUser_role(List<WkrjRole> user_role) {
		this.user_role = user_role;
	}

	public String getUser_realname() {
		return user_realname;
	}

	public void setUser_realname(String user_realname) {
		this.user_realname = user_realname;
	}

	public String getUser_card() {
		return user_card;
	}

	public void setUser_card(String user_card) {
		this.user_card = user_card;
	}

	public String getUser_is_enable() {
		return user_is_enable;
	}

	public void setUser_is_enable(String user_is_enable) {
		this.user_is_enable = user_is_enable;
	}

	public String getUser_last_ip() {
		return user_last_ip;
	}

	public void setUser_last_ip(String user_last_ip) {
		this.user_last_ip = user_last_ip;
	}

	public String getUser_no() {
		return user_no;
	}

	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}

	public String getUser_last_time() {
		return user_last_time;
	}

	public void setUser_last_time(String user_last_time) {
		this.user_last_time = user_last_time;
	}

	public String getUser_tel() {
		return user_tel;
	}

	public void setUser_tel(String user_tel) {
		this.user_tel = user_tel;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

}
