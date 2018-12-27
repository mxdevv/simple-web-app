package data;

import data.DepartmentsDaoImpl;

public abstract class DepartmentsDaoFactory {
	static DepartmentsDaoImpl departmentsDaoImpl = null;
	static public DepartmentsDaoImpl instance() {
		if (departmentsDaoImpl == null)
			departmentsDaoImpl = new DepartmentsDaoImpl();
		return departmentsDaoImpl;
	}
}
