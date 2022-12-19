package com.example.ejercicio2.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc) throws IOException {
    	System.out.println("pf");
    	
    	
    	response.sendError(HttpServletResponse.SC_FORBIDDEN, "Authentication Failed");
    	// response.sendError(HttpServletResponse.SC_FORBIDDEN, "Authorization Failed");
    }
}