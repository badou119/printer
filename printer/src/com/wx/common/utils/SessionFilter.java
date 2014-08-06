package com.wx.common.utils;

import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 靳利华
 * Session过滤
 * 检查用户是否登陆，如没有登陆则跳转到登录页
 * 创建日期：2013-04-12
 */
public class SessionFilter implements Filter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SessionFilter.class);

	private String loginPage;
	private String[] excludePages;

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) {
		if (logger.isDebugEnabled()) {
			logger.debug("doFilter(ServletRequest, ServletResponse, FilterChain) - start"); //$NON-NLS-1$
		}

		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			String url = req.getServletPath();

			//是否不需要检查session
			boolean excluded = false;
			for (int i = 0; i < excludePages.length; i++) {
				if (url.contains(excludePages[i])){
					excluded = true;
					break;
				}
			}
			if (!excluded) {
				if (url.contains(".jsp") || url.contains(".action")) {
					if (!url.contains(loginPage)) {
						HttpSession session = req.getSession();
						// 从session从取出user,如果为空说明没有登录,将其转到登录页面.
						Object obj = session.getAttribute("loginUser");
						if (obj == null) {
							// 跳转到登陆页面
							res.sendRedirect(loginPage);

							if (logger.isDebugEnabled()) {
								logger.debug("doFilter(ServletRequest, ServletResponse, FilterChain) - end"); //$NON-NLS-1$
							}
							return;
						}
					}
				}
			}
			// 如果不需要检查则跳出过滤器继续执行
			chain.doFilter(request, response);
		} catch (Exception e) {
			logger.error("doFilter(ServletRequest, ServletResponse, FilterChain)", e); //$NON-NLS-1$

			e.printStackTrace();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("doFilter(ServletRequest, ServletResponse, FilterChain) - end"); //$NON-NLS-1$
		}
	}

	public void init(FilterConfig config) throws ServletException {
		if (logger.isDebugEnabled()) {
			logger.debug("init(FilterConfig) - start"); //$NON-NLS-1$
		}

		loginPage = config.getInitParameter("loginPage");
		String excludePage = config.getInitParameter("excludePage");
		excludePages = excludePage.split(",");
		
		if (logger.isDebugEnabled()) {
			logger.debug("init(FilterConfig) - end"); //$NON-NLS-1$
		}
	}

}
