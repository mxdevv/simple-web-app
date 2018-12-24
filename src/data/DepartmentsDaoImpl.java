package data;

import java.lang.StringBuilder;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Collection;
import java.util.ArrayList;

import javax.sql.DataSource;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;

import data.storage.DataSourceFactory;

import data.Employee;
import data.EmployeesDaoImpl;
import data.Department;
import data.DepartmentsDao;

public class DepartmentsDaoImpl implements DepartmentsDao {
  private DataSource dataSource;
	private EmployeesDaoImpl employeesDaoImpl;

  public DepartmentsDaoImpl() {
    try {
      dataSource = DataSourceFactory.createDataSource();
			employeesDaoImpl = new EmployeesDaoImpl();
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
				"SELECT ( " +
				"    name, description ) " +
				"  FROM departments " +
				"  WHERE (" +
				"    name = ? AND " +
				"    description = ? " +
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
		int result = -1;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(
				"INSERT INTO departments ( " +
				"    name, description ) " +
				"  VALUES ( " +
				"    ?, ? " +
				"  );"
			);
			statement.setString(1, department.getName());
			result = statement.executeUpdate();
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
		int result = -1;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(
				"DELETE FROM employees WHERE ( " +
				"  name = ? AND " +
				"  description = ? " +
				");"
			);
			statement.setString(1, department.getName());
			statement.setString(2, department.getDescription());
			result = statement.executeUpdate();
			connection.close();

			for(Employee employee : department.getEmployees()) {
				if (employeesDaoImpl.delete(employee) == false)
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

	public boolean update(Department department) {
		Connection connection = null;
		PreparedStatement statement = null;
		int result = -1;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(
				"UPDATE employees SET " +
				"  name, " +
				"  description = ?;"
			);
			statement.setString(1, department.getName());
			statement.setString(2, department.getDescription());
			result = statement.executeUpdate();
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
				null                     /* employees */
			);
			connection.close();
	
			ArrayList<Employee> employees =
					(ArrayList<Employee>)employeesDaoImpl.findByDepartmentId(id);
			for(Employee employee : employees) {
				department.getEmployees().add(employee);
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
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(
				"SELECT * FROM departments " +
				"  WHERE name = ?;"
			);
			statement.setString(1, name);
			resultSet = statement.executeQuery();
			connection.close();
			
			while(resultSet.next()) {
				connection = dataSource.getConnection();
				department = new Department(
													 /* 1 */ /* id */
					resultSet.getString(2),  /* name */
					resultSet.getString(3),  /* description */
					null                     /* employees */
				);
				connection.close();
		
				ArrayList<Employee> employees =
						(ArrayList<Employee>)employeesDaoImpl.findByDepartmentId(-1);
				for(Employee employee : employees) {
					department.getEmployees().add(employee);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { connection.close(); } catch (Exception e) {}
		}
		//return department;
		return null;
	}
}
