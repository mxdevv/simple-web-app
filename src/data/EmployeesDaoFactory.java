package data;                                                                             
                                                                                          
import data.EmployeesDaoImpl;                                                           
                                                                                          
public abstract class EmployeesDaoFactory {                                             
  static EmployeesDaoImpl employeesDaoImpl = null;                   
  static public EmployeesDaoImpl instance() {              
    if (employeesDaoImpl == null)                   
      employeesDaoImpl = new EmployeesDaoImpl();    
    return employeesDaoImpl;                        
  }                                                 
}                                                   
