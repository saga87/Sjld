package com.wkrj.headmasterreading.bean;
/**
 * 必读案例
 * 数据库表：wkrj_readcase
 * @author wlp
 * @Description:
 * @date 2018年5月30日 上午11:27:17
 */
public class ReadCase {
	private String must_read_id;//必读信息id
	private String must_read_title;//必读信息标题
	private String case_content;//案例内容
	private String event_type;//案例类型
	private String file_yname;
	private String file_xname;
	private String addtime;
	
	
	
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
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
	public String getMust_read_id() {
		return must_read_id;
	}
	public void setMust_read_id(String must_read_id) {
		this.must_read_id = must_read_id;
	}
	public String getMust_read_title() {
		return must_read_title;
	}
	public void setMust_read_title(String must_read_title) {
		this.must_read_title = must_read_title;
	}
	public String getCase_content() {
		return case_content;
	}
	public void setCase_content(String case_content) {
		this.case_content = case_content;
	}
	public String getEvent_type() {
		return event_type;
	}
	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}
	
	
}
