package com.hzmc.cems.model;


import java.util.List;


public class Accredit {

	private int			acId;		// ID
	private int			auId;		// 权限ID
	private int			roleId;		// 角色ID
	private List<Role>	roles;		// 所有角色
	private Authority	authoritys;	// 权限

	public Authority getAuthoritys() {
		return authoritys;
	}

	public void setAuthoritys(Authority authoritys) {
		this.authoritys = authoritys;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public int getAcId() {
		return acId;
	}

	public void setAcId(int acId) {
		this.acId = acId;
	}

	public int getAuId() {
		return auId;
	}

	public void setAuId(int auId) {
		this.auId = auId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
}
