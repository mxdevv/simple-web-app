package data;

import java.lang.StringBuilder;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashSet;

import javax.sql.DataSource;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;

import data.storage.DataSourceFactory;

import data.Employee;
import data.EmployeesDaoImpl;
import data.EmployeesDaoFactory;
import data.Department;
import data.DepartmentsDao;

public class DepartmentsDaoImpl implements DepartmentsDao {
  private DataSource dataSource;
	private EmployeesDaoImpl employeesDaoImpl;

  public DepartmentsDaoImpl() {
    try {
      dataSource = DataSourceFactory.createDataSource();
			employeesDaoImpl = EmployeesDaoFactory.instance();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

	private boolean check(Department department) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		boolean result = false;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(
				"SELECT " +
				"    name, Description " +
				"  FROM departments " +
				"  WHERE (" +
				"    name = ? AND " +
				"    Description = ? " +
				"  );"
			);
			statement.setString(1, department.getName());
			statement.setString(2, department.getDescription());
			resultSet = statement.executeQuery();
			result = resultSet.next();
			connection.close();

			for(Employee employee : department.getEmployees()) {
				if ( ! employeesDaoImpl.check(employee))
					result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { connection.close(); } catch (Exception e) {}
		}
		return result;
	}

	public int insert(Department department) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int result = -1;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(
				"INSERT INTO departments ( " +
				"    name, Description ) " +
				"  VALUES ( " +
				"    ?, ? " +
				"  );"
			);
			statement.setString(1, department.getName());
			statement.setString(2, department.getDescription());
			result = statement.executeUpdate();
			
			statement = connection.prepareStatement(
				"SELECT last_insert_id() FROM departments;"
			);
			resultSet = statement.executeQuery();     
      resultSet.next();                         
      int id = resultSet.getInt(1);  
			department.setId(id);
			
			connection.close();

			for(Employee employee : department.getEmployees()) {
				if (employeesDaoImpl.insert(employee) == 0)
					result = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { connection.close(); } catch (Exception e) {}
		}
		return result;
	}

	public boolean delete(Department department) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int result = -1;
		try {
			connection = dataSource.getConnection();
			
			statement = connection.prepareStatement(
				"SELECT id FROM departments " + 
				"    WHERE ( " +
				"  name = ? AND " +
				"  Description = ? );"
			);
			statement.setString(1, department.getName());
			statement.setString(2, department.getDescription());
			resultSet = statement.executeQuery();     
      resultSet.next();                         
      int id = resultSet.getInt(1);  
			department.setId(id);
			
			connection.close();
			
			for(Employee employee : department.getEmployees())
				if (employeesDaoImpl.delete(employee) == false)
					result = 0;
			
			connection = dataSource.getConnection();
			
			statement = connection.prepareStatement(
				"DELETE FROM departments WHERE ( " +
				"  id = ? " +
				");"
			);
			statement.setInt(1, id);
			result = statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { connection.close(); } catch (Exception e) {}
		}
		if (result == 1) return true;
		return false;
	}

	public boolean update(Department department) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int result = -1;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(
				"UPDATE departments SET " +
				"  name = ?, " +
				"  description = ?;"
			);
			statement.setString(1, department.getName());
			statement.setString(2, department.getDescription());
			result = statement.executeUpdate();
			
			statement = connection.prepareStatement(
				"SELECT id FROM departments " + 
				"    WHERE ( " +
				"  name = ? AND " +
				"  Description = ? );"
			);
			statement.setString(1, department.getName());
			statement.setString(2, department.getDescription());
			resultSet = statement.executeQuery();     
      resultSet.next();                         
      int id = resultSet.getInt(1);  
			department.setId(id);
			
			connection.close();

			for(Employee employee : department.getEmployees()) {
				if (employeesDaoImpl.update(employee) == false)
					result = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { connection.close(); } catch (Exception e) {}
		}
		if (result == 1) return true;
		return false;
	}

	public boolean saveOrUpdate(Department department) {
  	if (check(department)) {
      update(department);
			return true;
		}
    else {
      insert(department);
			return false;
		}
	}

	public Department findById(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Department department = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(
				"SELECT * FROM departments " +
				"  WHERE id = ?;"
			);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			resultSet.next();
			department = new Department(
				                 /* 1 */ /* id */
				resultSet.getString(2),  /* name */
				resultSet.getString(3),  /* description */
				new HashSet<Employee>()  /* employees */
			);
			connection.close();
	
			ArrayList<Employee> employees =
					(ArrayList<Employee>)employeesDaoImpl.findByDepartmentId(id);
			for(Employee employee : employees) {
				department.addEmployee(employee);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { connection.close(); } catch (Exception e) {}
		}
		return department;
	}

	public Collection<Department> findByName(String name) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Department department = null;
		ArrayList<Department> departments = new ArrayList<Department>();
		int id = -1;
		try {
			connection = dataSource.getConnection();
			
			statement = connection.prepareStatement(
				"SELECT * FROM departments " +
				"  WHERE name = ?;"
			);
			statement.setString(1, name);
			resultSet = statement.executeQuery();
		
			while(resultSet.next()) {
				connection = dataSource.getConnection();
			
				department = new Department(
													 /* 1 */ /* id */
					resultSet.getString(2),  /* name */
					resultSet.getString(3),  /* description */
					new HashSet<Employee>()  /* employees */
				);
			
				id = resultSet.getInt(1);
			
				connection.close();
		
				ArrayList<Employee> employees =
						(ArrayList<Employee>)employeesDaoImpl.findByDepartmentId(id);
				for(Employee employee : employees) {
					department.addEmployee(employee);
				}
				departments.add(department);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { connection.close(); } catch (Exception e) {}
		}
		return departments;
	}

	public Collection<Department> getAll() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Department department = null;
		ArrayList<Department> departments = new ArrayList<Department>();
		int id = -1;
		try {
			connection = dataSource.getConnection();
			
			statement = connection.prepareStatement(
				"SELECT * FROM departments;"
			);
			resultSet = statement.executeQuery();
		
			while(resultSet.next()) {
				connection = dataSource.getConnection();
			
				id = resultSet.getInt(1);
				
				department = new Department(
					resultSet.getString(2),  /* name */
					resultSet.getString(3),  /* description */
					new HashSet<Employee>(), /* employees */
					id                       /* id */
				);
			
				connection.close();
		
				ArrayList<Employee> employees =
						(ArrayList<Employee>)employeesDaoImpl.findByDepartmentId(id);
				for(Employee employee : employees) {
					department.addEmployee(employee);
				}
				departments.add(department);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { connection.close(); } catch (Exception e) {}
		}
		return departments;
	}
}
