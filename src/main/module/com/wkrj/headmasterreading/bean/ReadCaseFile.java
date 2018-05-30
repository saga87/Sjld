package com.wkrj.headmasterreading.bean;
/**
 * 新加必读案例的附件
 * 数据库表:wkrj_readcasefile
 * @author wlp
 * @Description:
 * @date 2018年5月30日 下午2:43:55
 */
public class ReadCaseFile {
	private String file_id;//附件id
	private String must_read_id;//新添必读案例id
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
	public String getMust_read_id() {
		return must_read_id;
	}
	public void setMust_read_id(String must_read_id) {
		this.must_read_id = must_read_id;
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
