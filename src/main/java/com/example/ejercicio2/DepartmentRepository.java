package com.example.ejercicio2;

import org.springframework.data.repository.CrudRepository;

//esto se implementará automáticamente por SPRING en un Bean llamado employeeRepository
//CRUD significa Create, Read, Update, Delete
public interface DepartmentRepository extends CrudRepository<Department, Integer>  {

}
