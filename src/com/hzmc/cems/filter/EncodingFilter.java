package com.hzmc.cems.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 字符過濾器
 *    
 * @author 杨超  
 * @time   
 *      2015-7-15 上午10:04:25
 */

public class EncodingFilter implements Filter{
	private FilterConfig config = null;
	private String encoding = null;
	//销毁过滤器
	public void destroy() {
		System.out.println("字符编码过滤器已销毁...");
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc) throws IOException, ServletException {
		if (encoding == null) {
			encoding = config.getInitParameter("encoding");
		}
		res.setContentType("text/html;charset=UTF-8");   
		req.setCharacterEncoding(encoding);
		res.setCharacterEncoding(encoding);
		fc.doFilter(req, res);
	}

	//初始化编码过滤器
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("编码初始化");
		this.config = arg0;
	}

}
