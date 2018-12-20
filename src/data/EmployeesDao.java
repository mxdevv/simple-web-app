package data;

import java.util.Collection;

import data.Employee;

public interface EmployeesDao {
	public int insert(Employee employee);
	public boolean delete(Employee employee);
	public boolean update(Employee employee);
	public boolean saveOrUpdate(Employee employee);
	public Employee findByID(int id);
	public Collection<Employee> findByName(String firstName, String secondName);
	public Collection<Employee> findByJobtitle(String jobtitle);
}
