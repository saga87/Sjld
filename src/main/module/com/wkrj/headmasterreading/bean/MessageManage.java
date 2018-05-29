package com.wkrj.headmasterreading.bean;
/**
 * 留言管理实体
 * 数据库表：wkrj_messagemanager
 * @author wlp
 * @Description:
 * @date 2018年5月29日 上午11:23:06
 */
public class MessageManage {
	private String must_read_id;//必读信息id
	private String commenter;//留言者
	private String comment_school;//学校
	private String must_read_title;//必读信息标题
	private String comment_time;//留言时间
	private String comment_content;//留言内容
	private int likenum;//点赞数
	public String getMust_read_id() {
		return must_read_id;
	}
	public void setMust_read_id(String must_read_id) {
		this.must_read_id = must_read_id;
	}
	public String getCommenter() {
		return commenter;
	}
	public void setCommenter(String commenter) {
		this.commenter = commenter;
	}
	public String getComment_school() {
		return comment_school;
	}
	public void setComment_school(String comment_school) {
		this.comment_school = comment_school;
	}
	public String getMust_read_title() {
		return must_read_title;
	}
	public void setMust_read_title(String must_read_title) {
		this.must_read_title = must_read_title;
	}
	public String getComment_time() {
		return comment_time;
	}
	public void setComment_time(String comment_time) {
		this.comment_time = comment_time;
	}
	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}
	public int getLikenum() {
		return likenum;
	}
	public void setLikenum(int likenum) {
		this.likenum = likenum;
	}
	
}
