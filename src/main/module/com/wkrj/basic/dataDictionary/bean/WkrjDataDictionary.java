package com.wkrj.basic.dataDictionary.bean;

/**
 * 数据字典表 对应数据库表：wkrj_sys_data_dictionary
 * 
 * @author sjc
 * 
 */
public class WkrjDataDictionary {

	private String id;
	private String typecode;
	private String typename;
	private String typeid;
	private String typeparentid;
	private String value;
	private String notes;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public String getTypeparentid() {
		return typeparentid;
	}

	public void setTypeparentid(String typeparentid) {
		this.typeparentid = typeparentid;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
