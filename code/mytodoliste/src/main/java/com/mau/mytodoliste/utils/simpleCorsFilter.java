package com.mau.mytodoliste.utils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

public class simpleCorsFilter extends GenericFilterBean {

	public simpleCorsFilter() {


	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletResponse resp = (HttpServletResponse) response;
		
		resp.setHeader("Access-control-Allow-Origin", "http://localhost:4200");
		resp.setHeader("Access-control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
		chain.doFilter(request, resp);
	}

}
