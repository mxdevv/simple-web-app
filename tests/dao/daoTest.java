import java.util.ArrayList;

import java.time.LocalDate;
import java.time.Month;

import data.Employee;
import data.Department;

import data.EmployeesDaoImpl;
//import data.DepartmentsDaoImpl;

public class daoTest {
  public static void main(String args[]) {
    EmployeesDaoImpl employeesDaoImpl = new EmployeesDaoImpl();
    
    Department department1 = new Department("ndepar1",
                                            "ndescr1",
                                            null,
                                            1);
    
    Employee employee1 = new Employee("nfirst1",
                                      "nsecond1",
                                      LocalDate.of(2015, Month.DECEMBER, 20),
                                      LocalDate.of(2015, Month.DECEMBER, 21),
                                      "head",
                                      123_321,
                                      department1);
    
    Employee employee2 = new Employee("nfirst2",
                                      "nsecond2",
                                      LocalDate.of(2016, Month.DECEMBER, 20),
                                      LocalDate.of(2016, Month.DECEMBER, 21),
                                      "assistant",
                                      80_800,
                                      department1,
                                      3);
    
    Employee employee3 = new Employee("nfirst3",
                                      "nsecond3",
                                      LocalDate.of(2017, Month.DECEMBER, 20),
                                      LocalDate.of(2017, Month.DECEMBER, 21),
                                      "engineer",
                                      90_900,
                                      department1);
   

    /* OK */
  System.out.println("\temployeesDaoImpl.delete(employee1);");
    employeesDaoImpl.delete(employee1);
  
    /* OK */
  System.out.println("\temployeesDaoImpl.insert(employee1);");
    employeesDaoImpl.insert(employee1);
 
    /* OK */
  System.out.println("\temployeesDaoImpl.update(employee2);");
    employeesDaoImpl.update(employee2); 

    /* OK */
  System.out.println("\temployeesDaoImpl.saveOrUpdate(employee3);");
    employeesDaoImpl.saveOrUpdate(employee3);

    /* OK */
  System.out.println("\t... = employeesDaoImpl.findByID(2);");
    Employee employee4 = employeesDaoImpl.findByID(2);
    System.out.println("employee4 {\n" +
                       "  firstName = "  + employee4.getFirstName()  + '\n' +
                       "  secondName = " + employee4.getSecondName() + '\n' +
                       "  birthDate = "  + employee4.getBirthDate()  + '\n' +
                       "  hireDate = "   + employee4.getHireDate()   + '\n' +
                       "  jobtitle = "   + employee4.getJobtitle()   + '\n' +
                       "  salary = "     + employee4.getSalary()     + '\n' +
                       "  department = " + employee4.getDepartment() + '\n' +
                       "}\n");

    /* OK */
  System.out.println("\t... = employeesDaoImpl.findByName(\"first1\", \"second1\");");
    ArrayList<Employee> findByNames =
        (ArrayList<Employee>)employeesDaoImpl.findByName("first1", "second1");
    for(Employee el : findByNames) {
      System.out.println("{\n" +
                         "  firstName = "  + el.getFirstName()  + '\n' +
                         "  secondName = " + el.getSecondName() + '\n' +
                         "  birthDate = "  + el.getBirthDate()  + '\n' +
                         "  hireDate = "   + el.getHireDate()   + '\n' +
                         "  jobtitle = "   + el.getJobtitle()   + '\n' +
                         "  salary = "     + el.getSalary()     + '\n' +
                         "  department = " + el.getDepartment() + '\n' +
                         "}");
    }
    System.out.println();

    /* OK */
  System.out.println("\t... = employeesDaoImpl.findByJobtitle(\"assistant\")");
    ArrayList<Employee> findByJobtitles =
        (ArrayList<Employee>)employeesDaoImpl.findByJobtitle("assistant");
    for(Employee el : findByJobtitles) {
      System.out.println("{\n" +
                         "  firstName = "  + el.getFirstName()  + '\n' +
                         "  secondName = " + el.getSecondName() + '\n' +
                         "  birthDate = "  + el.getBirthDate()  + '\n' +
                         "  hireDate = "   + el.getHireDate()   + '\n' +
                         "  jobtitle = "   + el.getJobtitle()   + '\n' +
                         "  salary = "     + el.getSalary()     + '\n' +
                         "  department = " + el.getDepartment() + '\n' +
                         "}");
    }
    System.out.println();

    /* OK */
  System.out.println("\t... = employeesDaoImpl.findByDepartmentId(2)");
    ArrayList<Employee> findByDepartmentIds =
        (ArrayList<Employee>)employeesDaoImpl.findByDepartmentId(2);
    for(Employee el : findByDepartmentIds) {
      System.out.println("{\n" +
                         "  firstName = "  + el.getFirstName()  + '\n' +
                         "  secondName = " + el.getSecondName() + '\n' +
                         "  birthDate = "  + el.getBirthDate()  + '\n' +
                         "  hireDate = "   + el.getHireDate()   + '\n' +
                         "  jobtitle = "   + el.getJobtitle()   + '\n' +
                         "  salary = "     + el.getSalary()     + '\n' +
                         "  department = " + el.getDepartment() + '\n' +
                         "}");
    }
    System.out.println();
  }
}
