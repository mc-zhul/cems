package com.hzmc.cems.model;


public class Authority {

	private int		auId;		// 权限ID
	private String	authority;	// 权限
	private String	status;		// 权限状态

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getAuId() {
		return auId;
	}

	public void setAuId(int auId) {
		this.auId = auId;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
}
