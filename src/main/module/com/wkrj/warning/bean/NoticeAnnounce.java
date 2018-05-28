package com.wkrj.warning.bean;
/**
 * 预警提醒-通知公告
 * 数据库:wkrj_noticeannounce
 * @author wlp
 * @Description:
 * @date 2018年5月26日 上午9:56:45
 */
public class NoticeAnnounce {
	private String na_id;//id
	private String na_title;//标题
	private String na_content;//内容
	private String na_other;//备注
	private String na_inputuser;//录入人
	private String na_inputuserdept;//录入人部门
	private String na_inputtime;//录入时间
	private int importance;//重要程度
	private String accept_user;//可视用户
	
	public String getNa_id() {
		return na_id;
	}
	public void setNa_id(String na_id) {
		this.na_id = na_id;
	}
	public String getNa_title() {
		return na_title;
	}
	public void setNa_title(String na_title) {
		this.na_title = na_title;
	}
	public String getNa_content() {
		return na_content;
	}
	public void setNa_content(String na_content) {
		this.na_content = na_content;
	}
	public String getNa_other() {
		return na_other;
	}
	public void setNa_other(String na_other) {
		this.na_other = na_other;
	}
	public String getNa_inputuser() {
		return na_inputuser;
	}
	public void setNa_inputuser(String na_inputuser) {
		this.na_inputuser = na_inputuser;
	}
	public String getNa_inputuserdept() {
		return na_inputuserdept;
	}
	public void setNa_inputuserdept(String na_inputuserdept) {
		this.na_inputuserdept = na_inputuserdept;
	}
	public String getNa_inputtime() {
		return na_inputtime;
	}
	public void setNa_inputtime(String na_inputtime) {
		this.na_inputtime = na_inputtime;
	}
	public int getImportance() {
		return importance;
	}
	public void setImportance(int importance) {
		this.importance = importance;
	}
	public String getAccept_user() {
		return accept_user;
	}
	public void setAccept_user(String accept_user) {
		this.accept_user = accept_user;
	}
	
	
}
