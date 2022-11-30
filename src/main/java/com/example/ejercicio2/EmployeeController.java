package com.example.ejercicio2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	
	@GetMapping("/employees")
	public ResponseEntity<Iterable<Employee>> getEmployees() {
		return new ResponseEntity<Iterable<Employee>>(employeeRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Integer id) {
		Employee employee = employeeRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "No encontrado")
		);

		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Integer> deleteEmployee(@PathVariable("id") Integer id) {
		try {
			employeeRepository.deleteById(id);
			return new ResponseEntity<Integer>(HttpStatus.NO_CONTENT);
		} catch (EmptyResultDataAccessException e) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existe");
		}
	}
	

	@PostMapping("/employees")
	public ResponseEntity<Employee> createEmployee(EmployeePostRequest employeePostRequest) {
		return new ResponseEntity<Employee>(HttpStatus.CREATED);
	}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Integer id, EmployeePostRequest employeePostRequest) {
		return new ResponseEntity<Employee>(HttpStatus.OK);
	}
	
	
}