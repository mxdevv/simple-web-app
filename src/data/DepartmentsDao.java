package data;

import java.util.Collection;

import data.Department;

public interface DepartmentsDao {
	public int insert(Department department);
	public boolean delete(Department department);
	public boolean update(Department department);
	public boolean saveOrUpdate(Department department);
	public Department findById(int id);
	public Collection<Department> findByName(String name);
}
