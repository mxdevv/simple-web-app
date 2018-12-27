import java.util.ArrayList;

import java.time.LocalDate;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

import data.*;
import json.*;

public class Servlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setContentType("text/html");
		PrintWriter printWriter = resp.getWriter();

		String id              = req.getParameter("id");
		String firstName       = req.getParameter("firstName");
		String secondName      = req.getParameter("secondName");
		String hireDateMinimum = req.getParameter("hireDateMinimum");
		String hireDateMaximum = req.getParameter("hireDateMaximum");
		String jobtitle        = req.getParameter("jobtitle");
		
		printWriter.println("<h1>Welcome, you request:</h1>");
		printWriter.println("id = "              + id              + "<br>");
		printWriter.println("firstName = "       + firstName       + "<br>");
		printWriter.println("secondName = "      + secondName      + "<br>");
		printWriter.println("hireDateMinimum = " + hireDateMinimum + "<br>");
		printWriter.println("hireDateMaximum = " + hireDateMaximum + "<br>");
		printWriter.println("jobtitle = "        + jobtitle        + "<br>");

		DepartmentsDaoImpl departmentsDaoImpl = DepartmentsDaoFactory.instance();
		ArrayList<Department> departments =
				(ArrayList<Department>)departmentsDaoImpl.getAll();

		JsonGroup jsonEmployees = new JsonGroup();
		JsonGroup jsonEmployee = null;
		JsonGroup jsonDepartment = null;
		for(Department depar : departments) {
			for(Employee empl : depar.getEmployees()) {
		
				/* ======== start filter ========= */
				LocalDate localDate = null;

				if (id != null) {
					if ( ! id.equals(String.valueOf(empl.getId()))) continue;
				} else {
					if ( (firstName != null) 
							&& ( ! firstName.equals(empl.getFirstName())))
						continue;
					if ( (secondName != null)
							&& ( ! secondName.equals(empl.getSecondName())))
						continue;
					if ( (jobtitle != null)
							&& ( ! jobtitle.equals(empl.getJobtitle())))
						continue;
					if (hireDateMinimum != null) {
						localDate = LocalDate.parse(hireDateMinimum);
						if (localDate.isAfter(empl.getHireDate()))
							continue;
					}
					if (hireDateMaximum != null) {
						localDate = LocalDate.parse(hireDateMaximum);
						if (localDate.isBefore(empl.getHireDate()))
							continue;
					}
				}
				/* -------- end filter --------- */

				jsonEmployee = new JsonGroup();

				jsonEmployee.addElement(
					new JsonElement("id", String.valueOf(empl.getId())));
				jsonEmployee.addElement(
					new JsonElement("firstName", empl.getFirstName()));
				jsonEmployee.addElement(
					new JsonElement("secondName", empl.getSecondName()));
				jsonEmployee.addElement(
					new JsonElement("birthDate", empl.getBirthDate().toString()));
				jsonEmployee.addElement(
					new JsonElement("hireDate", empl.getHireDate().toString()));
				jsonEmployee.addElement(
					new JsonElement("jobTitle", empl.getJobtitle()));
				jsonEmployee.addElement(
					new JsonElement("salary", String.valueOf(empl.getSalary())));
				
				jsonDepartment = new JsonGroup("department");
				jsonDepartment.addElement(
					new JsonElement("id", String.valueOf(depar.getId())));
				jsonDepartment.addElement(
					new JsonElement("name", depar.getName()));
				jsonDepartment.addElement(
					new JsonElement("description", depar.getDescription()));

				jsonEmployee.addGroup(jsonDepartment);
				jsonEmployees.addGroup(jsonEmployee);
			}
		}
		
		printWriter.println("<h1>You answer:</h1><br>");
		printWriter.println("<textarea>");
		printWriter.println(jsonEmployees.getJsonString());
		printWriter.println("</textarea>");
	}
}
