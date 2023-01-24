package com.example.ejercicio2.employees.model;

import org.springframework.web.multipart.MultipartFile;

public class TestPostRequest {

	private String name;
	private String position;
	private Long salary;
	private Integer bossId;
	private Integer departmentId;
	private String file;
	
	public TestPostRequest() {}

	public TestPostRequest(String name, String position, Long salary, Integer bossId, Integer departmentId) {
		super();
		this.name = name;
		this.position = position;
		this.salary = salary;
		this.bossId = bossId;
		this.departmentId = departmentId;
	}
	
	public TestPostRequest(String name, String position, Long salary, Integer bossId, Integer departmentId, String file) {
		super();
		this.name = name;
		this.position = position;
		this.salary = salary;
		this.bossId = bossId;
		this.departmentId = departmentId;
		this.file = file;
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

	public Integer getBossId() {
		return bossId;
	}

	public void setBossId(Integer bossId) {
		this.bossId = bossId;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	
	

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "EmployeePostRequest [name=" + name + ", position=" + position + ", salary=" + salary + ", bossId="
				+ bossId + ", departmentId=" + departmentId + "]";
	}
	
	
}
