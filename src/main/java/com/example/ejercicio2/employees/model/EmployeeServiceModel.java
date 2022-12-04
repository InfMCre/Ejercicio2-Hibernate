package com.example.ejercicio2.employees.model;

import com.example.ejercicio2.departments.model.DepartmentServiceModel;
import com.fasterxml.jackson.annotation.JsonInclude;

public class EmployeeServiceModel {
	private Integer id;
	private String name;
	private String position;
	private Long salary;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private EmployeeServiceModel boss;
	private Integer bossId;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private DepartmentServiceModel department;
	private Integer departmentId;
	
	public EmployeeServiceModel() {}
	
	public EmployeeServiceModel(Integer id, String name, String position, Long salary, EmployeeServiceModel boss, Integer bossId,
			DepartmentServiceModel department, Integer departmentId) {
		super();
		this.id = id;
		this.name = name;
		this.position = position;
		this.salary = salary;
		this.boss = boss;
		this.bossId = bossId;
		this.department = department;
		this.departmentId = departmentId;
	}
	
	public EmployeeServiceModel(String name, String position, Long salary, EmployeeServiceModel boss, Integer bossId,
			DepartmentServiceModel department, Integer departmentId) {
		super();
		this.name = name;
		this.position = position;
		this.salary = salary;
		this.boss = boss;
		this.bossId = bossId;
		this.department = department;
		this.departmentId = departmentId;
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

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Long getSalary() {
		return salary;
	}

	public void setSalary(Long salary) {
		this.salary = salary;
	}

	public EmployeeServiceModel getBoss() {
		return boss;
	}

	public void setBoss(EmployeeServiceModel boss) {
		this.boss = boss;
	}

	public Integer getBossId() {
		return bossId;
	}

	public void setBossId(Integer bossId) {
		this.bossId = bossId;
	}

	public DepartmentServiceModel getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentServiceModel department) {
		this.department = department;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	@Override
	public String toString() {
		return "EmployeeService [id=" + id + ", name=" + name + ", position=" + position + ", salary=" + salary
				+ ", boss=" + boss + ", bossId=" + bossId + ", department=" + department + ", departmentId="
				+ departmentId + "]";
	}
	
	
	
}
