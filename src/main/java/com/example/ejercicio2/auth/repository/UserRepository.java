package com.example.ejercicio2.auth.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.ejercicio2.auth.persistence.User;


public interface UserRepository extends CrudRepository<User, Integer> {
	Optional<User>findByEmail(String email);
}
