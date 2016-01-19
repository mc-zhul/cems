package com.hzmc.cems.model;


import java.util.Date;


public class OpLog {

	private Date	logDate;	// 操作时间
	private String	name;		// Trust用户名
	private String	firModule;	// 模块名
	private String	secModule;
	private String	oper;		// 1:add 2:mod 3:del 4:list
	private String	desc;		// 描述
	private String	rowDetail;

	public String getFirModule() {
		return firModule;
	}

	public void setFirModule(String firModule) {
		this.firModule = firModule;
	}

	public String getSecModule() {
		return secModule;
	}

	public void setSecModule(String secModule) {
		this.secModule = secModule;
	}

	public String getRowDetail() {
		return rowDetail;
	}

	public void setRowDetail(String rowDetail) {
		this.rowDetail = rowDetail;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
