package com.example.ejercicio2.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.ejercicio2.auth.persistence.Role;
import com.example.ejercicio2.auth.persistence.User;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Component
public class JwtTokenFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtTokenUtil jwtUtil;

	// comprueba que tiene el jwt en la petición y que es valido
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		if (!hasAuthorizationBearer(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = getAccessToken(request);

		if (!jwtUtil.validateAccessToken(token)) {
			filterChain.doFilter(request, response);
			return;
		}

		System.out.println("FUNCIONANDO");
		setAuthenticationContext(token, request);
		filterChain.doFilter(request, response);
	}
	
	// comprueba si tiene el jwt en el header de la petición
	private boolean hasAuthorizationBearer(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
			return false;
		}

		return true;
	}

	// obtiene el jwt de la cabecera
	private String getAccessToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		String token = header.split(" ")[1].trim();
		return token;
	}


	private void setAuthenticationContext(String token, HttpServletRequest request) {
//		UserDetails userDetails = getUserDetails(token);
//		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, null);
//		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		UserDetails userDetails = getUserDetails(token);
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	// genera los detalles del usuario a partir del jwt
	private UserDetails getUserDetails(String token) {
		
		User userDetails = new User();
		userDetails.setId(jwtUtil.getUserId(token));
		userDetails.setEmail(jwtUtil.getSubject(token));
		//userDetails.setRoles(jwtUtil.getUserRoles(token));
//		List<Role> roles = new ArrayList<Role>();
//		roles.add(new Role(1,"ROLE_USER"));
		// roles.add(new Role(2,"ROLE_USER"));
//		userDetails.setRoles(roles);
		
		
		List<Role> userRoles = jwtUtil.getUserRoles(token);
		
		
		userDetails.setRoles(userRoles);
		System.out.println("--userdetails--");
		
		userRoles.get(0).getName();
		
		// userDetails.getAuthorities();
		//System.out.println(userDetails.toString());
		//System.out.println(userDetails.getRoles().size());
		// System.out.println(userDetails.getRoles().stream().findFirst());
		// System.out.println(userDetails.getRoles().stream().findFirst().toString());

		// System.out.println(userDetails.getAuthorities().stream().findFirst().toString());
		// System.out.println(userDetails.getAuthorities().size());
		
		
//		for (Role role : userDetails.getRoles()) {
//			System.out.println(role);
//		}
//		Collection<SimpleGrantedAuthority> pruebas = (Collection<SimpleGrantedAuthority>) userDetails.getAuthorities();
//		pruebas.stream().forEach(rol -> {
//			System.out.println(rol.getAuthority());
//		});
//		
//		
		
		
		
		return userDetails;
	}

}
