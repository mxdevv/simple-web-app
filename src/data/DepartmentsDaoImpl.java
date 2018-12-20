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
import data.DepartmentsDao;

public class DepartmentsDaoImpl implements DepartmentsDao {
  private DataSource dataSource;

  public DepartmentsDaoImpl() {
    try {
      dataSource = DataSourceFactory.createDataSource();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

	private boolean check(Department departments) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		boolean result = false;
		try {
			Connection connection = dataSource.getConnection();
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

			for(Employee employee : department.employees) {
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
		int result;
		try {
			Connection connection = dataSource.getConnection();
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

			for(Employee employee : department.employees) {
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
		int result;
		try {
			Connection connection = dataSource.getConnection();
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

			for(Employee employee : department.employees) {
				if (employeesDaoImpl.delete(employee) == 0)
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
		Employee employee = null;
		int result;
		try {
			Connection connection = dataSource.getConnection();
			statement = connection.prepareStatement(
				"UPDATE employees SET " +
				"  name, " +
				"  description = ?;"
			);
			statement.setString(1, department.getName());
			statement.setString(2, department.getDescription());
			result = statement.executeUpdate();
			connection.close();

			for(Employee employee : department.employees) {
				if (employeesDaoImpl.update(employee) == 0)
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
  	if (check(department))
      update(department);
    else
      insert(department);
	}

	public Department findById(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Department department = null;
		try {
			Connection connection = dataSource.getConnection();
			statement = connection.prepareStatement(
				"SELECT * FROM departments " +
				"  WHERE id = ?;"
			);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			resultSet.next();
			department = new Department(
				                 /* 1 *//* id */
				statement.getString(2), /* name */
				statement.getString(3), /* description */
			);
			connection.close();
	
			ArrayList<Employee> employees = EmployeesDaoImpl.getByDepartmentsId();
			for(Employee employee : employees) {
				department.employees.add(employee);
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
			Connection connection = dataSource.getConnection();
			statement = connection.prepareStatement(
				"SELECT * FROM departments " +
				"  WHERE name = ?;"
			);
			statement.setString(1, name);
			resultSet = statement.executeQuery();
			connection.close();
			
			while(resultSet.next()) {
				Connection connection = dataSource.getConnection();
				department = new Department(
													 /* 1 *//* id */
					statement.getString(2), /* name */
					statement.getString(3), /* description */
				);
				connection.close();
		
				ArrayList<Employee> employees = EmployeesDaoImpl.getByDepartmentsId();
				for(Employee employee : employees) {
					department.employees.add(employee);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { connection.close(); } catch (Exception e) {}
		}
		return department;
	}
}
