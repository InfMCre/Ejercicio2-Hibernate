package com.example.ejercicio2.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.ejercicio2.auth.model.RoleEnum;

import jakarta.servlet.http.HttpServletResponse;



@Configuration
//@EnableGlobalMethodSecurity(
//	prePostEnabled = true
//)
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired 
	private JwtTokenFilter jwtTokenFilter;
	
	//@Autowired 
	//private MyAuthenticationEntryPoint myAuthenticationEntryPoint;
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	// utilizado para encriptar las contraseñas en la DB
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
//    }
	
	
	// aqui definimos principalmente cuales son las urls van a poder ser accesibles sin identificarse
	// y cuales seran obligatorias
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
        http.authorizeHttpRequests(
        	(authz) -> 
        				authz
        					//.requestMatchers("/api/employees/**").hasAnyAuthority("USER", "ROLE_USER")
        					.requestMatchers("/api/employees/**").hasAnyAuthority("USER")
        					//.requestMatchers("/api/departments/**").hasAnyRole("ADMIN", "ROLE_ADMIN")
        					.requestMatchers("/api/auth/**").permitAll()
        					// .requestMatchers("/api/departments/**").hasAuthority(RoleEnum.USER.name())
        					.anyRequest().authenticated()
        					
        					// .and().exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint)
        );
        
        
        http.exceptionHandling().authenticationEntryPoint(new MyAuthenticationEntryPoint());
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());
        
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
}