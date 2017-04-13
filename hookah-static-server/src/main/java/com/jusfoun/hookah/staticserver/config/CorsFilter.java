package com.jusfoun.hookah.staticserver.config;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 解决浏览器CORS（跨域访问）问题
 * @author liudeakira
 *
 */
@WebFilter(filterName = "corsFilter",urlPatterns = "/*")
public class CorsFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 指定允许其他域名访问
		response.addHeader("Access-Control-Allow-Origin","*");
		if (request.getMethod() == "OPTIONS"){ // 加点逻辑
			response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
			response.addHeader("Access-Control-Allow-Headers", "content-type, authorization, accept,x-requested-with");
        }
        // 响应头设置
		//response.setHeader("Access-Control-Allow-Headers","x-requested-with,content-type");
		filterChain.doFilter(request, response);

	}

}
