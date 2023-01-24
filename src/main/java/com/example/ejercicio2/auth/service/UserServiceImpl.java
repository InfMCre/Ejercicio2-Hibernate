package com.example.ejercicio2.auth.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ejercicio2.auth.model.RoleEnum;
import com.example.ejercicio2.auth.persistence.Role;
import com.example.ejercicio2.auth.persistence.User;
import com.example.ejercicio2.auth.repository.RoleRepository;
import com.example.ejercicio2.auth.repository.UserRepository;
import com.example.ejercicio2.security.CustomPasswordEncoder;

@Service("userDetailsService")
public class UserServiceImpl implements UserDetailsService, UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("pf");
        return userRepository.findByEmail(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User " + username + " not found"));
	}
	
	@Override
	public User signUp(User user) {
		// Nuestro CustomPassowrdEncoder
		CustomPasswordEncoder passwordEncoder = new CustomPasswordEncoder();
		String password = passwordEncoder.encode(user.getPassword());
		user.setPassword(password);

		// vamos a asegurar
		user.setEnabled(true);
		
		Role userRole = roleRepository.findByName(RoleEnum.USER.name()).get();
		List<Role> roles = new ArrayList<Role>();
		roles.add(userRole);
		user.setRoles(roles);
		return userRepository.save(user);
	}
	
	// hariamos otra funcion para la creacion del admin. cuyo endpoint de entrada no pueda ser usado por cualquiera

}
