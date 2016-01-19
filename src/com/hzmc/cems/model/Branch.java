package com.hzmc.cems.model;


public class Branch {

	private int		braId;	// 分支id
	private String	braName;// 分支名
	private String	braCom;	// 分支描述
	private int		proId;	// 项目ID
	private String	braStatus; //分支状态

	
	public String getBraStatus() {
		return braStatus;
	}

	
	public void setBraStatus(String braStatus) {
		this.braStatus = braStatus;
	}

	public int getBraId() {
		return braId;
	}

	public void setBraId(int braId) {
		this.braId = braId;
	}

	public String getBraName() {
		return braName;
	}

	public void setBraName(String braName) {
		this.braName = braName;
	}

	public String getBraCom() {
		return braCom;
	}

	public void setBraCom(String braCom) {
		this.braCom = braCom;
	}

	public int getProId() {
		return proId;
	}

	public void setProId(int proId) {
		this.proId = proId;
	}
}
