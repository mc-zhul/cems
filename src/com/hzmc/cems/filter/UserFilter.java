package com.hzmc.cems.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hzmc.cems.model.SessionUser;
import com.hzmc.cems.model.User;
public class UserFilter implements Filter {
	public void destroy() {
		System.out.println("用户过滤器已销毁...");
	}
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc)
			throws IOException, ServletException {
		// 得到request
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		// 得到session
		User user = (User) request.getSession().getAttribute("loginUser");
		if (user == null) {
			response.sendRedirect(request.getContextPath());
		} else {
			SessionUser.setSessionUser(user);
			fc.doFilter(req, res);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("用户过滤器已加载...");
	}

}
