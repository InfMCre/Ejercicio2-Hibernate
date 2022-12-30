package com.example.ejercicio2.employees.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.ejercicio2.employees.model.Employee;


// esto se implementará automáticamente por SPRING en un Bean llamado employeeRepository
// CRUD significa Create, Read, Update, Delete
public interface EmployeeRepository extends CrudRepository<Employee, Integer>  {

	// lista de empleados
	Iterable<Employee> findAllByNameAndPosition(String name, String position);
	Iterable<Employee> findAllByNameAndDepartmentId(String name, Integer departmentId);
	
	
	Iterable<Employee> findAllByNameOrDepartmentId(String name, Integer departmentId); // OR
	
	// en este caso el nombre de la funcion no importa, pero para que se vea la diferencia.
	@Query("select e from #{#entityName} e where e.name = ?1 OR e.departmentId = ?2")
	Iterable<Employee> findAllByNameOrDepartmentIdCustom(String name, Integer departmentId);
	
	// lista de ids de departamentos
	// haria en SQL department_id IN ()
	Iterable<Employee> findAllByDepartmentIdIn(Collection<Integer> departmentIds); 
	//	ejemplo para crear la coleccion...
	//	Collection<Integer> collection = new ArrayList<Integer>();
	//	collection.add(1);
	//	collection.add(2);
	// haria en SQL department_id IN (1,2)
	
	
	// CUIDADO por que si hay multiples resultados va a petar, ya que espera un unico resultado. al haber puesto el return "Optional<Employee>"
	Optional<Employee> findByName(String name);
	Optional<Employee> findByNameAndPosition(String name, String position);
	Optional<Employee> findByNameAndBossId(String name, Integer bossId);
	Optional<Employee> findByNameAndDepartmentId(String name, Integer departmentId);
	
}
