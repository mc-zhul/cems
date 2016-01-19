package com.hzmc.cems.model;

import java.util.List;

public class Role {
	private int roleId;
	private String roleType;// 角色类型
	private String roleCom;// 角色描述
	private List<User> users; // 用户
	private List<Accredit> accreditList;//授权

	



	public List<Accredit> getAccreditList() {
		return accreditList;
	}

	public void setAccreditList(List<Accredit> accreditList) {
		this.accreditList = accreditList;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getRoleCom() {
		return roleCom;
	}

	public void setRoleCom(String roleCom) {
		this.roleCom = roleCom;
	}

}
