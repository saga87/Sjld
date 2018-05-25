package com.wkrj.newsManage.bean;

import java.util.List;

import wkrjsystem.menu.bean.WkrjMenu;

/**
 * 通知公告附件表
 * 对应数据库表：wkrj_newsfile
 * @author sjc
 *
 */
public class WkrjNewsFile {   

	private String file_id;
	private String news_id;// 信息ID
	private String file_yname;// 附件原名称
	private String file_xname;// 附件现名称
	private String file_inputtime;// 附件上传时间
	private String file_inputuser;// 录入人
	private List<WkrjMenu> menu;//菜单
	
	public List<WkrjMenu> getMenu() {
		return menu;
	}

	public void setMenu(List<WkrjMenu> menu) {
		this.menu = menu;
	}

	public String getNews_id() {
		return news_id;
	}

	public void setNews_id(String news_id) {
		this.news_id = news_id;
	}

	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
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

	public String getFile_inputtime() {
		return file_inputtime;
	}

	public void setFile_inputtime(String file_inputtime) {
		this.file_inputtime = file_inputtime;
	}

	public String getFile_inputuser() {
		return file_inputuser;
	}

	public void setFile_inputuser(String file_inputuser) {
		this.file_inputuser = file_inputuser;
	}

}
