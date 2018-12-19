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

	public Employee(String firstName, String secondName, LocalDate birthdate,
			LocalDate hireDate, String jobtitle, double salary, Department department) {
		;
	}

	protected Employee(String firstName, String secondName, LocalDate birthdate,
			LocalDate hireDate, String jobtitle, double salary, Department department, int id) {
		;
	}

	public void setFirstName(String firstName) {
		;
	}

	public String getFirstName() {
		
		return null;
	}

	public void setSecondName(String secondName) {
		;
	}

	public String getSecondName() {
		
		return null;
	}

	public void setBirthDate(LocalDate birthDate) {
		;
	}

	public LocalDate getBirthDate() {
		
		return null;
	}

	public void setHireDate(LocalDate hireDate) {
		;
	}

	public LocalDate getHireDate() {
		
		return null;
	}

	public void setJobtitle(String jobtitle) {
		;
	}

	public String getJobtitle() {
		
		return null;
	}

	public void setSalary(double salary) {
		;
	}

	public double getSalary() {
		
		return -1;
	}

	public void setDepartment(Department department) {
		;
	}

	public Department getDepartment() {
		
		return null;
	}

	protected void setId(int id) {
		;
	}

	protected void setId() {
		;
	}

	public int getId() {
		
		return -1;
	}
}
