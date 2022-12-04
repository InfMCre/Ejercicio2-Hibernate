package com.example.ejercicio2.departments.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.ejercicio2.departments.model.Department;

//esto se implementará automáticamente por SPRING en un Bean llamado employeeRepository
//CRUD significa Create, Read, Update, Delete
public interface DepartmentRepository extends CrudRepository<Department, Integer>  {

}
