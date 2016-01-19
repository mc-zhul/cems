package com.hzmc.cems.model;

public class User {

	private int				userId;
	private String			username;		// 用户名
	private String			password;		// 密码
	private String			name;			// 姓名
	private int				roleId;			// 角色id
	private Role			role;			// 角色
	private String	authority;	// 用户权限

	

	
	public String getAuthority() {
		return authority;
	}

	
	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
