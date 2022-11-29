package com.example.ejercicio2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="employees")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(length = 60)
	private String name;
	@Column(length = 60)
	private String position;
	private Long salary;


	@ManyToOne()
	@JoinColumn(name = "boss_id", foreignKey=@ForeignKey(name = "Fk_boss_id"))
	private Employee boss;

	@ManyToOne()
	@JoinColumn(name = "department_id", foreignKey=@ForeignKey(name = "Fk_department_id"))
	private Department department;

	// anteriormente teniamos
	// private Long bossId;
	// private Long departmentId;
	
	public Employee() {}

	public Employee(Integer id, String name, String position, Long salary, Employee boss, Department department) {
		super();
		this.id = id;
		this.name = name;
		this.position = position;
		this.salary = salary;
		this.boss = boss;
		this.department = department;
	}
	
	public Employee(String name, String position, Long salary, Employee boss, Department department) {
		super();
		this.name = name;
		this.position = position;
		this.salary = salary;
		this.boss = boss;
		this.department = department;
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

	public Employee getBoss() {
		return boss;
	}

	public void setBoss(Employee boss) {
		this.boss = boss;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", position=" + position + ", salary=" + salary + ", boss="
				+ boss + ", department=" + department + "]";
	}
	
	
	
	
}
