package data;

import java.time.LocalDate;

import data.Department;

public class Employee {
	private int id;
	private String firstName;
	private String secondName;
	private LocalDate birthDate;
	private LocalDate hireDate;
	private String jobtitle;;
	private double salary;
	private Department department;

	public Employee() {
		;
	}

	public Employee(String firstName, String secondName, LocalDate birthDate,
			LocalDate hireDate, String jobtitle, double salary, Department department) {
		this.firstName = firstName;
		this.secondName = secondName;
		this.birthDate = birthDate;
		this.hireDate = hireDate;
		this.jobtitle = jobtitle;
		this.salary = salary;
		this.department = department;
	}

	// protected
	public Employee(String firstName, String secondName, LocalDate birthDate,
			LocalDate hireDate, String jobtitle, double salary, Department department, int id) {
		this(firstName, secondName, birthDate, hireDate, jobtitle, salary, department);
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

	public LocalDate getHireDate() {
		return hireDate;	
	}

	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}

	public String getJobtitle() {
		return jobtitle;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public double getSalary() {
		return salary;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Department getDepartment() {
		return department;
	}

	protected void setId(int id) {
		this.id = id;
	}

	protected void setId() {
	}

	public int getId() {
		return id;
	}
}
