package com.wkrj.headmasterreading.bean;
/**
 * 点赞表实体
 * 数据库表：wkrj_like
 * @author wlp
 * @Description:
 * @date 2018年5月29日 下午2:12:48
 */
public class LikeBean {
	private String must_read_id; //被点赞的必读案例id
	private String user_id;//校长用户id
	public String getMust_read_id() {
		return must_read_id;
	}
	public void setMust_read_id(String must_read_id) {
		this.must_read_id = must_read_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
}
