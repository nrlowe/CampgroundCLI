package com.techelevator.projects.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Employee;
import com.techelevator.projects.model.EmployeeDAO;

public class JDBCEmployeeDAO implements EmployeeDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCEmployeeDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> allEmployees = new ArrayList<Employee>();
		String sqlGetAllEmployees = "SELECT employee_id, department_id, first_name, last_name, birth_date, gender, hire_date " + "FROM employee ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllEmployees);
		while(results.next()) {
		Employee employeeId = mapRowToEmployee(results);
		allEmployees.add(employeeId);
		}
		return allEmployees;
	}

	@Override
	public List<Employee> searchEmployeesByName(String firstNameSearch, String lastNameSearch) {
		List<Employee> employeesByName = new ArrayList<Employee>();
		String sqlSearchEmployeesByName = "SELECT employee_id, department_id, first_name, last_name, birth_date, gender, hire_date " + 
										  "FROM employee " + "WHERE first_name = ? AND last_name = ? ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchEmployeesByName, firstNameSearch, lastNameSearch);
		while(results.next()) {
			Employee employeeName = mapRowToEmployee(results);
			employeesByName.add(employeeName);
		}
		return employeesByName;
	}

	@Override
	public List<Employee> getEmployeesByDepartmentId(long id) {
		List<Employee> employeesByDepartment = new ArrayList<Employee>();
		String sqlGetEmployeesByDepartmentId = "SELECT employee_id, department_id, first_name, last_name, birth_date, gender, hire_date " +
											   "FROM employee " + "WHERE department_id = ? ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetEmployeesByDepartmentId, id);
		while(results.next()) {
			Employee employeeDeptId = mapRowToEmployee(results);
			employeesByDepartment.add(employeeDeptId);
		}
		return employeesByDepartment;
	}

	@Override
	public List<Employee> getEmployeesWithoutProjects() {
		List<Employee> employeesWithoutProjects = new ArrayList<Employee>();
		String sqlGetEmployeesWithoutProjects = "SELECT e.employee_id, e.department_id, first_name, last_name, birth_date, gender, hire_date , pe.project_id  " 
				+ "FROM employee e " + "LEFT JOIN project_employee pe ON pe.employee_id = e.employee_id " + "WHERE pe.project_id IS NULL";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetEmployeesWithoutProjects);
		while(results.next()) {
			Employee benchEmployee = mapRowToEmployee(results);
			employeesWithoutProjects.add(benchEmployee);
		}
		return employeesWithoutProjects;
	}

	@Override
	public List<Employee> getEmployeesByProjectId(Long projectId) {
		List<Employee> employeeByProject = new ArrayList<Employee>();
		String sqlFindEmployeesByProjectId = "SELECT e.employee_id, e.department_id, first_name, last_name, birth_date, gender, hire_date , pe.project_id  " 
		+ "FROM employee e " + "JOIN project_employee pe ON pe.employee_id = e.employee_id " + "WHERE project_id = ? ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindEmployeesByProjectId, projectId);
		while(results.next()) {
		Employee employeeProjectId = mapRowToEmployee(results);
		employeeByProject.add(employeeProjectId);
		}
		return employeeByProject;
	}

	@Override
	public void changeEmployeeDepartment(Long employeeId, Long departmentId) {
		
	}
	
	private Employee mapRowToEmployee(SqlRowSet results) {
		Employee theEmployee;
		theEmployee = new Employee();
		theEmployee.setId(results.getLong("employee_id"));
		theEmployee.setDepartmentId(results.getLong("department_id"));
		theEmployee.setFirstName(results.getString("first_name"));
		theEmployee.setLastName(results.getString("last_name"));
		theEmployee.setBirthDay(results.getDate("birth_date").toLocalDate());
		theEmployee.setGender(results.getString("gender").charAt(0));
		theEmployee.setHireDate(results.getDate("hire_date").toLocalDate());
		return theEmployee;
	}
}










