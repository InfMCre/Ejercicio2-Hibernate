package com.example.ejercicio2.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ejercicio2.auth.model.AuthRequest;
import com.example.ejercicio2.auth.model.AuthResponse;
import com.example.ejercicio2.auth.persistence.User;
import com.example.ejercicio2.auth.service.UserService;
import com.example.ejercicio2.security.JwtTokenUtil;

@RestController
@RequestMapping("api/auth")
public class AuthController {
	
	@Autowired 
	AuthenticationManager authManager;
	@Autowired 
	JwtTokenUtil jwtUtil;
	@Autowired
	UserService userService;
	
	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody AuthRequest request) {
		try {
			Authentication authentication = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
			);
			User user = (User) authentication.getPrincipal();
			System.out.println(user.getEmail());
			String accessToken = jwtUtil.generateAccessToken(user);
			AuthResponse response = new AuthResponse(user.getId(), user.getEmail(), accessToken);
			jwtUtil.getUserId(accessToken);
			return ResponseEntity.ok().body(response);
			
		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody AuthRequest request) {
		// TODO solo esta creado en el caso de que funcione. Si no es posible que de 500
		User user = new User(request.getEmail(), request.getPassword());
		userService.signUp(user);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	
	@GetMapping("/auth/me")
	public ResponseEntity<?> getUserInfo(Authentication authentication) {
		// aqui podemos castearlo a UserDetails o User. El UserDetails es una interfaz, 
		// si lo casteamos a la interfaz no podremos sacar campos como la ID
		User userDetails = (User) authentication.getPrincipal();
		
		// aqui podriamos devolver datos del usuario. quizá no sea lo que queremos devolver o no lo querramos devolver
		// es un ejemplo por que con userDetails.getId() tendríamos la ID del usuario sin que la pase por parametro
		// necesario en algunos servicios: si quiero devolver una lista o elemento privado del usuario, no voy a querer
		// que el usuario mande su ID por parametro. Ya que es trampeable.
		// de ahi que sea "/me" en el ejemplo
		
		return ResponseEntity.ok().body(userDetails);
	}
	
}
