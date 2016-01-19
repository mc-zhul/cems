package com.hzmc.cems.model;


import java.util.List;


public class Project {

	private int				proId;		// id
	private String			proName;	// 项目名
	private String			proCom;		// 项目描述
	private String			isFat;		// 是否有子项目
	private String			proStatus;	// 项目状态0常态 1删除
	private List<Branch>	branchList;	// 分支列表

	public String getProStatus() {
		return proStatus;
	}

	public void setProStatus(String proStatus) {
		this.proStatus = proStatus;
	}

	public String getIsFat() {
		return isFat;
	}

	public void setIsFat(String isFat) {
		this.isFat = isFat;
	}

	public List<Branch> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<Branch> branchList) {
		this.branchList = branchList;
	}

	public int getProId() {
		return proId;
	}

	public void setProId(int proId) {
		this.proId = proId;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProCom() {
		return proCom;
	}

	public void setProCom(String proCom) {
		this.proCom = proCom;
	}
}
