package com.example.ejercicio2.employees.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.ejercicio2.employees.model.Employee;


// esto se implementará automáticamente por SPRING en un Bean llamado employeeRepository
// CRUD significa Create, Read, Update, Delete
public interface EmployeeRepository extends CrudRepository<Employee, Integer>  {

}
