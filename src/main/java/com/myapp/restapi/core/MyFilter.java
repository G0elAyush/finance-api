package com.myapp.restapi.core;

import java.io.IOException;
import java.util.Random;

import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MyFilter implements Filter{

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		if((servletRequest instanceof HttpServletRequest) && (servletResponse instanceof HttpServletResponse)) {
			
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			HttpRequestWrapper original_request = (HttpRequestWrapper) request;
			ContentCachingResponseWrapper responseToCache = new ContentCachingResponseWrapper(response);
			Random random = new Random();
			String correlationId = String.valueOf(Math.abs(random.nextInt()));
			request.setAttribute("correlationId", correlationId);
			
			
		}
		else {
			chain.doFilter(servletRequest, servletResponse);
		}
		
	}
	
	

}
