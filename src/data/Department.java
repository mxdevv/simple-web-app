package data;

import java.util.Set;

import data.Employee;

public class Department {
	private int id;
	private String name;
	private String description;
	private Set<Employee> employees;

	public Department() {
		;
	}

	public Department(String name, String description, Set<Employee> employees) {
		this.name = name;
		this.description = description;
		this.employees = employees;
	}

	// protected
	public Department(String name, String description, Set<Employee> employees, int id) {
		this(name, description, employees);
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void addEmployee(Employee employee) {
		employees.add(employee);
	}

	public void removeEmployee(Employee employee) {
		employees.remove(employee);
	}

	protected void setId(int id) {
		this.id = id;
	}

	protected void setId() {
		;
	}

	public int getId() {
		return id;
	}
}
