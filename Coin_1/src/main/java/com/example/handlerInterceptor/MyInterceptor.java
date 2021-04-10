package com.example.handlerInterceptor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.user.model.GraphicsCardAll;
import com.example.user.service.GraphicsService;
import com.example.user.service.UserService;
import com.sun.istack.Nullable;

@Component
public class MyInterceptor implements HandlerInterceptor {
	Logger logger = LoggerFactory.getLogger(MyInterceptor.class);

	@Autowired
	private GraphicsService graphicsService;
	private final String signin = "http://localhost:10084/coin/user/gotosignin";
	// 首頁讀資料
	private final String index = "http://localhost:10084/coin/user/gotomyindex";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//        logger.info("拦截器1 在控制器执行之前执行");

		String uri = request.getRequestURI();
		String contextPath = request.getContextPath(); // 當前路徑

		String path = uri.substring(uri.lastIndexOf("/") + 1);
		String url = request.getRequestURL().toString();
//		
//		System.out.println("當前路徑:   " + path);

		/**
		 * 個人中心 必須登入才能進入
		 */
		if (url.contains("/gotomyusers")) {
			if (request.getSession().getAttribute("email") == null) {
				response.sendRedirect(index);
				return false;
			}
		}

		/**
		 * 已經登入成功不應該再進入登入畫面
		 */
		if (url.contains("/gotosignin")) {
			if (request.getSession().getAttribute("email") != null) {
				response.sendRedirect(index);
				return false;
			}
		}

		/**
		 * 開始挖礦必須為登入狀態
		 */
		if (url.contains("/gotomydevice")) {
			if (request.getSession().getAttribute("email") == null) {
				response.sendRedirect(signin);
				return false;
			}
		}

		/**
		 * 儲值頁面 必需存在session 方能判斷當前的儲值 使用者是誰
		 */
		if (url.contains("/storedvalue")) {
			if (request.getSession().getAttribute("email") == null) {
				response.sendRedirect(index);
				return false;
			}
		}

		return true;
	}

	/**
	 * 控制器之后，跳转前
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
//        logger.info("拦截器1 在控制器执行之后执行");
	}

	/**
	 * 跳转之后执行
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
//        logger.info("拦截器1 最后执行");
	}

}