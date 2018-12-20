package data;

import java.lang.StringBuilder;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Collection;

import javax.sql.DataSource;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;

import data.storage.DataSourceFactory;

import data.Employee;
import data.Department;
import data.EmployeesDao;

public class EmployeesDaoImpl implements EmployeesDao {
	private DataSource dataSource;
	
	public EmployeesDaoImpl() {
		try {
			dataSource = DataSourceFactory.createDataSource();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean check(Employee employee) {
		Connection connection = null;
    PreparedStatement statement = null;
		ResultSet resultSet = null;
		boolean result = false;
		try {
			Connection connection = dataSource.getConnection();
			statement = connection.prepareStatement(
				"SELECT ( " +
				"    first_name, second_name, birth_date, hire_date, salary, " +
				"    jobtitles_id, departments_id ) " +
				"  FROM employees " +
				"  WHERE (" +
				"    first_name = ? AND " +
				"    second_name = ? AND " +
				"    birth_date = ? AND " +
				"    hire_date = ? AND " +
				"    salary = ? AND " +
				"    jobtitles_id = ? AND " +
				"    departments_id = ? " +
				"  );"
			);
			statement.setString(1, employee.getFirstName());
			statement.setString(2, employee.getSecondName());
			statement.setDate  (3, Date.valueOf(employee.getBirthDate()));
			statement.setDate  (4, Date.valueOf(employee.getHireDate()));
			statement.setDouble(5, employee.getSalary());
			statement.setInt   (6, findJobtitleId(employee.getJobtitle()));
			statement.setInt   (7, employee.getDepartment().getId());
			resultSet = statement.executeQuery();
			result = resultSet.next();
		} catch (Exception e) {
      e.printStackTrace();
    } finally {
      try { connection.close(); } catch (Exception e) {}
    }   
		return result;
	}

	public int getJobTitleId(String jobTitle) {                                           
    Connection connection = null;                                                         
    PreparedStatement statement = null;                                                   
    ResultSet resultSet = null;                                                           
    int id = -1;                                                                          
    try {                                                                                 
      connection = dataSource.getConnection();                                            
      statement = connection.prepareStatement(                                            
        "SELECT id FROM jobtitles WHERE name = ?;");                                      
      statement.setString(1, jobTitle);                                            
      resultSet = statement.executeQuery();                                               
      resultSet.next();                                                                   
      id = resultSet.getInt(1);                                                           
    } catch (Exception e) {                                                               
      e.printStackTrace();                                                                
    } finally {                                                                           
      try { connection.close(); } catch (Exception e) {}                                  
    }                                                                                     
    return id;                                                                            
  }   

	public int insert(Employee employee) {
		Connection connection = null;
    PreparedStatement statement = null;
		int result;
		try {
			Connection connection = dataSource.getConnection();
			statement = connection.prepareStatement(
				"INSERT INTO employees ( " +
				"    first_name, second_name, birth_date, hire_date, salary, " +
				"    jobtitles_id, departments_id ) " +
				"  VALUES ( " +
				"    ?, ?, ?, ?, ?, ?, ? " +
				"  );"
			);
			statement.setString(1, employee.getFirstName());
			statement.setString(2, employee.getSecondName());
			statement.setDate  (3, Date.valueOf(employee.getBirthDate()));
			statement.setDate  (4, Date.valueOf(employee.getHireDate()));
			statement.setDouble(5, employee.getSalary());
			statement.setInt   (6, findJobtitleId(employee.getJobtitle()));
			statement.setInt   (7, employee.getDepartment().getId());

			result = statement.executeUpdate();
		} catch (Exception e) {
      e.printStackTrace();
    } finally {
      try { connection.close(); } catch (Exception e) {}
    }   
		return result;
	}

	public boolean delete(Employee employee) {
		Connection connection = null;
    PreparedStatement statement = null;
		int result;
		try {
			Connection connection = dataSource.getConnection();
			statement = connection.prepareStatement(
				"DELETE FROM employees WHERE ( " +
				"  first_name = ? AND " +
				"  second_name = ? AND " +
				"  birth_date = ? AND " +
				"  hire_date = ? AND " +
				"  salary = ? AND " +
				"  jobtitles_id = ? AND " +
				"  departments_id = ? " +
				");"
			);
			statement.setString(1, employee.getFirstName());
			statement.setString(2, employee.getSecondName());
			statement.setDate  (3, Date.valueOf(employee.getBirthDate()));
			statement.setDate  (4, Date.valueOf(employee.getHireDate()));
			statement.setDouble(5, employee.getSalary());
			statement.setInt   (6, findJobtitleId(employee.getJobtitle()));
			statement.setInt   (7, employee.getDepartment().getId());

			result = statement.executeUpdate();
		} catch (Exception e) {
      e.printStackTrace();
    } finally {
      try { connection.close(); } catch (Exception e) {}
    }
		if (result == 1) return true;
		return false;
	}

	public boolean update(Employee employee) {
		Connection connection = null;
    PreparedStatement statement = null;
		Employee employee = null;
		int result;
		try {
			Connection connection = dataSource.getConnection();
			statement = connection.prepareStatement(
				"UPDATE employees SET " +
				"  first_name = ?, " +
				"  birth_date = ?, " +
				"  hire_date = ?, " +
				"  salary = ?, " +
				"  jobtitles_id = ?, " +
				"  departments_id = ?;"
			);
			statement.setString(1, employee.getFirstName());
			statement.setString(2, employee.getSecondName());
			statement.setDate  (3, Date.valueOf(employee.getBirthDate()));
			statement.setDate  (4, Date.valueOf(employee.getHireDate()));
			statement.setDouble(5, employee.getSalary());
			statement.setInt   (6, employee.getJobtitle());
			statement.setInt   (7, employee.getDepartment().getId());

			result = statement.executeUpdate();
		} catch (Exception e) {
      e.printStackTrace();
    } finally {
      try { connection.close(); } catch (Exception e) {}
    }
		if (result == 1) return true;
		return false;
	}

	public boolean saveOrUpdate(Employee employee) {
		if (check(employee))
			update(employee);
		else
			insert(employee);
	}

	public Employee findByID(int id) {
		Connection connection = null;
    PreparedStatement statement = null;
		ResultSet resultSet = null;
		Employee employee = null;
		try {
			Connection connection = dataSource.getConnection();
			statement = connection.prepareStatement(
				"SELECT * FROM employees " +
				"  WHERE id = ?;"
			);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			resultSet.next();
			employee = new Employee(
				                 /* 1 *//* id */
				statement.getString(2), /* firstName */
				statement.getString(3), /* secondName */
				statement.getDate  (4), /* birthDate */
				statement.getDate  (5), /* hireDate */
				statement.getInt   (6), /* salary */
				statement.getString(7), /* jobtitle */
				statement.getInt   (8)  /* departmenstId */
			);
		} catch (Exception e) {
      e.printStackTrace();
    } finally {
      try { connection.close(); } catch (Exception e) {}
    }
		return employee;
	}

	public Collection<Employee> findByName(String firstName, String secondName) {
		Connection connection = null;
    PreparedStatement statement = null;
		ResultSet resultSet = null;
		ArrayList<Employee> employees = new ArrayList<>();
		try {
			Connection connection = dataSource.getConnection();
			statement = connection.prepareStatement(
				"SELECT * FROM employees " +
				"  WHERE ( " + 
				"    first_name = ? AND " +
				"    second_name = ? );"
			);
			statement.setString(1, firstName);
			statement.setString(2, secondName);
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				employees.add(new Employee(
				                 /* 1 *//* id */
				statement.getString(2), /* firstName */
				statement.getString(3), /* secondName */
				statement.getDate  (4), /* birthDate */
				statement.getDate  (5), /* hireDate */
				statement.getInt   (6), /* salary */
				statement.getString(7), /* jobtitle */
				statement.getInt   (8)  /* departmenstId */
				));
			}
		} catch (Exception e) {
      e.printStackTrace();
    } finally {
      try { connection.close(); } catch (Exception e) {}
    }
		return employees;
	}

	public Collection<Employee> findByJobtitle(String jobtitle) {
		Connection connection = null;
    PreparedStatement statement = null;
		ResultSet resultSet = null;
		ArrayList<Employee> employees = new ArrayList<>();
		try {
			Connection connection = dataSource.getConnection();
			statement = connection.prepareStatement(
				"SELECT * FROM employees " +
				"  WHERE ( " + 
				"    jobtitles_id = ? );"
			);
			statement.setString(1, getJobTitleId(jobtitle));
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				employees.add(new Employee(
				                 /* 1 *//* id */
				statement.getString(2), /* firstName */
				statement.getString(3), /* secondName */
				statement.getDate  (4), /* birthDate */
				statement.getDate  (5), /* hireDate */
				statement.getInt   (6), /* salary */
				statement.getString(7), /* jobtitle */
				statement.getInt   (8)  /* departmenstId */
				));
			}
		} catch (Exception e) {
      e.printStackTrace();
    } finally {
      try { connection.close(); } catch (Exception e) {}
    }
		return employees;
	}

	public Collection<Employee> findByDepartmentId(int id) {
		Connection connection = null;
    PreparedStatement statement = null;
		ResultSet resultSet = null;
		ArrayList<Employee> employees = new ArrayList<>();
		try {
			Connection connection = dataSource.getConnection();
			statement = connection.prepareStatement(
				"SELECT * FROM employees " +
				"  WHERE ( " + 
				"    departments_id = ? );"
			);
			statement.setString(1, getJobTitleId(jobtitle));
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				employees.add(new Employee(
				                 /* 1 *//* id */
				statement.getString(2), /* firstName */
				statement.getString(3), /* secondName */
				statement.getDate  (4), /* birthDate */
				statement.getDate  (5), /* hireDate */
				statement.getInt   (6), /* salary */
				statement.getString(7), /* jobtitle */
				statement.getInt   (8)  /* departmenstId */
				));
			}
		} catch (Exception e) {
      e.printStackTrace();
    } finally {
      try { connection.close(); } catch (Exception e) {}
    }
		return employees;
	}
}
