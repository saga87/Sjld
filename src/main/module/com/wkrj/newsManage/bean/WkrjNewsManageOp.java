package com.wkrj.newsManage.bean;

import java.util.List;

import wkrjsystem.menu.bean.WkrjMenu;

/**
 * 通知公告操作表
 * 对应数据库表：wkrj_newsmanage_op
 * @author sjc
 *
 */
public class WkrjNewsManageOp {

	private String op_id;
	private String news_id;// 新闻ID
	private String op_signtime;// 签收时间
	private String op_looktime;// 查看时间
	private String op_signuser;// 签收人
	private String op_lookuser;// 查看人
	private List<WkrjMenu> menu;//菜单
	
	public List<WkrjMenu> getMenu() {
		return menu;
	}

	public String getOp_id() {
		return op_id;
	}

	public void setOp_id(String op_id) {
		this.op_id = op_id;
	}

	public String getNews_id() {
		return news_id;
	}

	public void setNews_id(String news_id) {
		this.news_id = news_id;
	}

	public String getOp_signtime() {
		return op_signtime;
	}

	public void setOp_signtime(String op_signtime) {
		this.op_signtime = op_signtime;
	}

	public String getOp_looktime() {
		return op_looktime;
	}

	public void setOp_looktime(String op_looktime) {
		this.op_looktime = op_looktime;
	}

	public String getOp_signuser() {
		return op_signuser;
	}

	public void setOp_signuser(String op_signuser) {
		this.op_signuser = op_signuser;
	}

	public String getOp_lookuser() {
		return op_lookuser;
	}

	public void setOp_lookuser(String op_lookuser) {
		this.op_lookuser = op_lookuser;
	}

	public void setMenu(List<WkrjMenu> menu) {
		this.menu = menu;
	}

}
