package com.wkrj.newsManage.bean;

import java.util.List;

import wkrjsystem.menu.bean.WkrjMenu;

/**
 * 通知公告表
 * 对应数据库表：wkrj_newsmanage
 * @author sjc
 *
 */
public class WkrjNewsManage {

	private String news_id;
	private String news_title;// 新闻标题
	private String news_content;// 新闻内容
	private String news_type;// 新闻类型（1.信息公告2.视频案例等）
	private String news_other;// 备注
	private String news_inputtime;// 录入时间
	private String news_inputuser;// 录入人
	private String news_inputuserdept;// 录入人所在部门
	private String news_lookarea;// 查看范围
	private String file_yname;
	private String file_xname;
	private List<WkrjMenu> menu;//菜单
	
	public List<WkrjMenu> getMenu() {
		return menu;
	}

	public void setMenu(List<WkrjMenu> menu) {
		this.menu = menu;
	}

	public String getNews_type() {
		return news_type;
	}

	public void setNews_type(String news_type) {
		this.news_type = news_type;
	}

	public String getFile_yname() {
		return file_yname;
	}

	public void setFile_yname(String file_yname) {
		this.file_yname = file_yname;
	}

	public String getFile_xname() {
		return file_xname;
	}

	public void setFile_xname(String file_xname) {
		this.file_xname = file_xname;
	}

	public String getNews_id() {
		return news_id;
	}

	public void setNews_id(String news_id) {
		this.news_id = news_id;
	}

	public String getNews_title() {
		return news_title;
	}

	public void setNews_title(String news_title) {
		this.news_title = news_title;
	}

	public String getNews_content() {
		return news_content;
	}

	public void setNews_content(String news_content) {
		this.news_content = news_content;
	}

	public String getNews_other() {
		return news_other;
	}

	public void setNews_other(String news_other) {
		this.news_other = news_other;
	}

	public String getNews_inputtime() {
		return news_inputtime;
	}

	public void setNews_inputtime(String news_inputtime) {
		this.news_inputtime = news_inputtime;
	}

	public String getNews_inputuser() {
		return news_inputuser;
	}

	public void setNews_inputuser(String news_inputuser) {
		this.news_inputuser = news_inputuser;
	}

	public String getNews_inputuserdept() {
		return news_inputuserdept;
	}

	public void setNews_inputuserdept(String news_inputuserdept) {
		this.news_inputuserdept = news_inputuserdept;
	}

	public String getNews_lookarea() {
		return news_lookarea;
	}

	public void setNews_lookarea(String news_lookarea) {
		this.news_lookarea = news_lookarea;
	}

}
