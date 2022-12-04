package com.example.ejercicio2.departments.model;

import java.util.List;

import com.example.ejercicio2.employees.model.EmployeeServiceModel;
import com.fasterxml.jackson.annotation.JsonInclude;

public class DepartmentServiceModel {
	private Integer id;
	private String name;
	private String city;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<EmployeeServiceModel> employees;

	public DepartmentServiceModel(Integer id, String name, String city, List<EmployeeServiceModel> employees) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.employees = employees;
	}
	
	public DepartmentServiceModel(String name, String city, List<EmployeeServiceModel> employees) {
		super();
		this.name = name;
		this.city = city;
		this.employees = employees;
	}
	
	public DepartmentServiceModel(Integer id, String name, String city) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
	}
	public DepartmentServiceModel(String name, String city) {
		super();
		this.name = name;
		this.city = city;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<EmployeeServiceModel> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeServiceModel> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "DepartmentServiceModel [id=" + id + ", name=" + name + ", city=" + city + ", employees=" + employees
				+ "]";
	}
	
	
}
