package com.example.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;



public class LogProcessTimeFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

	
			long startTime=System.currentTimeMillis();
			filterChain.doFilter(request, response);
			long processTime =System.currentTimeMillis()-startTime;
			
			request.setCharacterEncoding("utf-8");
			
			System.out.println(processTime);
		
	}
	
	

	
	
}