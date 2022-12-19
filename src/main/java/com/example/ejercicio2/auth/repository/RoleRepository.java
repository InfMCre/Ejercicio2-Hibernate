package com.example.ejercicio2.auth.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.ejercicio2.auth.model.RoleEnum;
import com.example.ejercicio2.auth.persistence.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
		Optional<Role> findByName(String role);
}
