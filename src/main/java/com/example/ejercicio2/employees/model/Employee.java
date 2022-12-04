package com.example.ejercicio2.employees.model;

import com.example.ejercicio2.departments.model.Department;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.ForeignKey;

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


	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "boss_id", foreignKey=@ForeignKey(name = "Fk_boss_id" ))
	@JsonManagedReference
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	private Employee boss;
	
	// vamos a setear la id del empleado, debemos decir que es una columna de la BBDD
	// pero no es ni insertable ni actualizable, ya que lo gestiona desde la propiedadad "boss"
	// de esta forma obtendremos el valor en el modelo, si no, omitirá la id del jefe.
	@Column(name="boss_id", insertable=false, updatable=false)
	private Integer bossId;
	
	
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id", foreignKey=@ForeignKey(name = "Fk_department_id"))
	@JsonManagedReference
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	private Department department;
	
	@Column(name="department_id", insertable=false, updatable=false)
	private Integer departmentId;
	
	
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
	

	public Employee(Integer id, String name, String position, Long salary, Employee boss, Integer bossId,
			Department department) {
		super();
		this.id = id;
		this.name = name;
		this.position = position;
		this.salary = salary;
		this.boss = boss;
		this.bossId = bossId;
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

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", position=" + position + ", salary=" + salary + ", boss="
				+ boss + ", bossId=" + bossId + ", department=" + department + "]";
	}

	
	
	
}
