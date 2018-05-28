package com.wkrj.eventReportWf.bean;

/**
 * 流程记录表
 * 对应数据库表：wkrj_procedurerecord
 * @author sjc
 *
 */
public class WkrjProcedureRecord {

    private String pr_id;
    private String event_id;// 事件ID
    private String opt_type;// 操作类型
    private String opt_time;// 操作时间
    private String opt_content;// 操作内容
    private String opt_user;// 操作人
    private String next_optuser;// 下一操作人
    private String next_optdept;// 下一操作部门
    private String event_no;// 事件编号
    public String getPr_id() {
        return pr_id;
    }
    public void setPr_id(String pr_id) {
        this.pr_id = pr_id;
    }
    public String getEvent_id() {
        return event_id;
    }
    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }
    public String getOpt_type() {
        return opt_type;
    }
    public void setOpt_type(String opt_type) {
        this.opt_type = opt_type;
    }
    public String getOpt_time() {
        return opt_time;
    }
    public void setOpt_time(String opt_time) {
        this.opt_time = opt_time;
    }
    public String getOpt_content() {
        return opt_content;
    }
    public void setOpt_content(String opt_content) {
        this.opt_content = opt_content;
    }
    public String getOpt_user() {
        return opt_user;
    }
    public void setOpt_user(String opt_user) {
        this.opt_user = opt_user;
    }
    public String getNext_optuser() {
        return next_optuser;
    }
    public void setNext_optuser(String next_optuser) {
        this.next_optuser = next_optuser;
    }
    public String getNext_optdept() {
        return next_optdept;
    }
    public void setNext_optdept(String next_optdept) {
        this.next_optdept = next_optdept;
    }
    public String getEvent_no() {
        return event_no;
    }
    public void setEvent_no(String event_no) {
        this.event_no = event_no;
    }

}
