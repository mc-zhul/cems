package com.hzmc.cems.model;


import java.util.Date;


public class Version {

	private int		verId;			// 版本ID
	private String	verName;		// 版本名称
	private String	verNum;			// 版本号
	private int		userId;			// 用户Id
	private Date	updateTime;		// 创建时间
	private String	verContent;		// 版本类容
	private String	size;			// 版本大小
	private String	verAddress;		// 版本地址
	private String	comments;		// 备注
	private int		braId;			// 分支ID
	private String	status	= "0";	// 状态，0为普通。1为删除
	private User	user;			// 用户

	public int getVerId() {
		return verId;
	}

	public void setVerId(int verId) {
		this.verId = verId;
	}

	public String getVerName() {
		return verName;
	}

	public void setVerName(String verName) {
		this.verName = verName;
	}

	public String getVerNum() {
		return verNum;
	}

	public void setVerNum(String verNum) {
		this.verNum = verNum;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getVerContent() {
		return verContent;
	}

	public void setVerContent(String verContent) {
		this.verContent = verContent;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getVerAddress() {
		return verAddress;
	}

	public void setVerAddress(String verAddress) {
		this.verAddress = verAddress;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getBraId() {
		return braId;
	}

	public void setBraId(int braId) {
		this.braId = braId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
