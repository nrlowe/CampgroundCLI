package com.techelevator.projects.model.jdbc;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Employee;
import com.techelevator.projects.model.Project;

public class JDBCEmployeeDAOTest {
	private static final LocalDate ourEmployeeBD = LocalDate.parse("1316-10-12");
	private static final LocalDate hireDate = LocalDate.parse("1456-05-10");
	private static SingleConnectionDataSource dataSource;
	private JDBCEmployeeDAO dao;
	
	@BeforeClass
	public static void setupDateSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/projects");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}
	
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}
	
	@Before 
	public void setup() {
		// If you dont have access to the database you could create a project and/or a department to test if the created
		// employee would be listed as a result.
		String sqlInsertEmployee = "INSERT INTO Employee (employee_id, department_id, first_name, last_name, birth_date, gender, hire_date ) "
									+ "VALUES (?, ?, ?, ?, ?, ?, ? ) ";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sqlInsertEmployee, 13, 1, "Bill", "Smith", ourEmployeeBD, "F", hireDate );
		dao = new JDBCEmployeeDAO(dataSource);	
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void return_all_employees() {
		List<Employee> results = dao.getAllEmployees();
		assertEquals(13, results.size());
	}
	
	@Test
	public void return_employee_by_name() {
		List<Employee> results = dao.searchEmployeesByName("Bill", "Smith");
		assertEquals(1, results.size());
	}
	
	@Test
	public void change_department_then_return_employee_by_department() {
		dao.changeEmployeeDepartment(13L, null);
		List<Employee> results = dao.getEmployeesByDepartmentId(1L);
		assertEquals(1, results.size());
	}
	
	@Test
	public void return_employees_without_project() {
		List<Employee> results = dao.getEmployeesWithoutProjects();
		assertEquals(3, results.size());
	}
	
	@Test
	public void return_employees_by_project_number() {
		List<Employee> results = dao.getEmployeesByProjectId(3L);
		assertEquals(3, results.size());
	}
	

}
