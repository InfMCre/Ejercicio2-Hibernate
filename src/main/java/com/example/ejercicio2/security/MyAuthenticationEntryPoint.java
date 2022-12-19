package com.example.ejercicio2.security;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.AuthenticationException authException)
			throws IOException, ServletException {

		if (response.getStatus() == HttpServletResponse.SC_FORBIDDEN) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Authentication Failed");
		} else {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
		}
	}

}