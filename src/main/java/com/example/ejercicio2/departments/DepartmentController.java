package com.example.ejercicio2.departments;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.ejercicio2.departments.model.Department;
import com.example.ejercicio2.departments.model.DepartmentExpands;
import com.example.ejercicio2.departments.model.DepartmentServiceModel;
import com.example.ejercicio2.departments.repository.DepartmentRepository;
import com.example.ejercicio2.employees.model.Employee;
import com.example.ejercicio2.employees.model.EmployeeServiceModel;
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
	public ResponseEntity<DepartmentServiceModel> getDepartmentById(
		@PathVariable("id") Integer id, 
		@RequestParam(required = false) List<DepartmentExpands> expand
	) {
		
		Department department = departmentRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "No encontrado")
		);
		
		// TODO esto no debería estar en el controlador pero nos hemos comido la capa del servicio..
		List<EmployeeServiceModel> employees = null;
		
		if (expand != null && expand.indexOf(DepartmentExpands.EMPLOYEES) != -1) {
			employees = new ArrayList<EmployeeServiceModel>();
			for (Employee currentEmployee : department.getEmployees()) {
				EmployeeServiceModel bossResponse = null;
				if (expand.indexOf(DepartmentExpands.EMPLOYEES_BOSS) != -1) {
					// solo si tambien ha decidido hacer expand de empleados...
					Employee boss = currentEmployee.getBoss();
					bossResponse = new EmployeeServiceModel(
							boss.getId(),
							boss.getName(),
							boss.getPosition(),
							boss.getSalary(),
							null,
							boss.getBossId(),
							null,
							boss.getDepartmentId()
					);
				}
				employees.add(
					new EmployeeServiceModel(
						currentEmployee.getId(),
						currentEmployee.getName(),
						currentEmployee.getPosition(),
						currentEmployee.getSalary(),
						bossResponse,
						currentEmployee.getBossId(),
						null,
						currentEmployee.getDepartmentId()
					)
				);
			}
		}
		
		DepartmentServiceModel response = new DepartmentServiceModel(department.getId(), department.getName(), department.getCity(), employees);
		return new ResponseEntity<DepartmentServiceModel>(response, HttpStatus.OK);
	}
	
}