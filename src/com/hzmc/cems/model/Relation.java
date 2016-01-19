package com.hzmc.cems.model;

public class Relation {

	private int		relaId;		// 关系id
	private int		childId;	// 子版本id
	private int		fatherId;	// 父版本ID
	private Version	version;	// 版本
	private User	user;		// 用户

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public int getRelaId() {
		return relaId;
	}

	public void setRelaId(int relaId) {
		this.relaId = relaId;
	}

	public int getChildId() {
		return childId;
	}

	public void setChildId(int childId) {
		this.childId = childId;
	}

	public int getFatherId() {
		return fatherId;
	}

	public void setFatherId(int fatherId) {
		this.fatherId = fatherId;
	}
}
