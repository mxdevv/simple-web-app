package data;

import java.lang.StringBuilder;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

	public int insert(Employee employee) {
		Connection connection = null;                                                         
    PreparedStatement statement = null;                                                   
		int result;
		try {
			Connection connection = dataSource.getConnection();
			statement = connection.prepareStatement(
				"INSERT INTO employees ( " +
				"    id, first_name, second_name, birth_date, hire_date, salary, " +
				"    jobtitles_id, departments_id ) " +
				"  VALUES ( " +
				"    NULL, ?, ?, ?, ?, ?, ?, ? " +
				"  );"
			);
			statement.setString(1, employee.getFirstName());
			statement.setString(2, employee.getSecondName());
			statement.setDate(3, Date.valueOf(employee.getBirthDate()));
			statement.setDate(4, Date.valueOf(employee.getHireDate()));
			statement.setDouble(5, employee.getSalary());
			statement.setInt(6, findJobtitleId(employee.getJobtitle()));
			statement.setInt(7, employee.getDepartment().getId());

			result = statement.executeUpdate();
		} catch (Exception e) {                                                               
      e.printStackTrace();                                                                
    } finally {                                                                           
      try { connection.close(); } catch (Exception e) {}                                  
    }   
		return result; /* return int? */
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
			statement.setDate(3, Date.valueOf(employee.getBirthDate()));
			statement.setDate(4, Date.valueOf(employee.getHireDate()));
			statement.setDouble(5, employee.getSalary());
			statement.setInt(6, findJobtitleId(employee.getJobtitle()));
			statement.setInt(7, employee.getDepartment().getId());

			result = statement.executeUpdate();
		} catch (Exception e) {                                                               
      e.printStackTrace();                                                                
    } finally {                                                                           
      try { connection.close(); } catch (Exception e) {}                                  
    }
		if (result == 1)
			return true;
		return false;
	}

	public Employee findByID(int id) {
		;
	}
}
