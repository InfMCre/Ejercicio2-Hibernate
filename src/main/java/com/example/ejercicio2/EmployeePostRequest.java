package com.example.ejercicio2;




public class EmployeePostRequest {

	private String name;
	private String position;
	private Long salary;
	private Long bossId;
	private Long departmentId;
	
	public EmployeePostRequest() {}

	public EmployeePostRequest(String name, String position, Long salary, Long bossId, Long departmentId) {
		super();
		this.name = name;
		this.position = position;
		this.salary = salary;
		this.bossId = bossId;
		this.departmentId = departmentId;
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

	public Long getBossId() {
		return bossId;
	}

	public void setBossId(Long bossId) {
		this.bossId = bossId;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	@Override
	public String toString() {
		return "EmployeePostRequest [name=" + name + ", position=" + position + ", salary=" + salary + ", bossId="
				+ bossId + ", departmentId=" + departmentId + "]";
	}
	
	

	
}
