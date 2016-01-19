package com.hzmc.cems.model;

public class SessionUser {

	@SuppressWarnings({ "rawtypes" })
	static ThreadLocal sessionUser = new ThreadLocal();

	@SuppressWarnings("unchecked")
	public static void setSessionUser(User user) {
		sessionUser.set(user);
	}

	public static User getSessionUser() {
		return (User) sessionUser.get();
	}

	public static String getSessionUserName() {
		return getSessionUser().getName();
	}
}
