package com.wkrj.warning.bean;
/**
 * 通知公告附件
 * 数据库:wkrj_noticeannouncefile
 * @author wlp
 * @Description:
 * @date 2018年5月26日 上午10:05:11
 */
public class NoticeAnnounceFile {
	private String file_id;//附件id
	private String na_id;//外键 通知公告id
	private String file_yname;// 附件原名称
	private String file_xname;// 附件现名称
	private String file_inputtime;// 附件上传时间
	private String file_inputuser;// 录入人
	
	
	
	public String getFile_id() {
		return file_id;
	}
	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}
	public String getNa_id() {
		return na_id;
	}
	public void setNa_id(String na_id) {
		this.na_id = na_id;
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
