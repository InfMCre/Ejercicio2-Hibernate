package com.example.ejercicio2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
@RestController
@RequestMapping("api")
public class DepartmentController {

	@Autowired
	private DepartmentRepository departmentRepository;

	
	@GetMapping("/departments")
	public ResponseEntity<Iterable<Department>> getDepartments() {
		return new ResponseEntity<Iterable<Department>>(departmentRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/departments/{id}")
	public ResponseEntity<Department> getDepartmentById(@PathVariable("id") Integer id) {
		Department department = departmentRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "No encontrado")
		);
		department.getEmployees();
		return new ResponseEntity<Department>(department, HttpStatus.OK);
	}
}