package com.techelevator.projects.model.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.projects.model.Department;

public class JDBCDepartmentDAOTest {
	
	private static final String TEST_DEPARTMENT = "GOGO";
	
	private static SingleConnectionDataSource dataSource;
	private JDBCDepartmentDAO dao;
	
	@BeforeClass
	public static void setupDataSource() {
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
		String sqlInsertDepartment = "INSERT INTO department (department_id, name) VALUES (5, ?) ";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sqlInsertDepartment, TEST_DEPARTMENT);
		dao = new JDBCDepartmentDAO(dataSource);
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test 
	public void get_list_of_all_departments() {
		List<Department> allDepartments = dao.getAllDepartments();	
		assertEquals(5, allDepartments.size());
	}
	
	@Test
	public void search_departments_by_name() {
		List<Department> results = dao.searchDepartmentsByName(TEST_DEPARTMENT);
		assertNotNull(results);
		assertEquals(1, results.size());
	}
	
	@Test
	public void department_name_can_be_updated() throws SQLException {
		dao.updateDepartmentName((long) 5, "HR");
		List<Department> results = dao.searchDepartmentsByName("HR");
		assertNotNull(results);
		assertEquals(1, results.size());
	}
	
	@Test
	public void can_new_department_be_created()  {
		dao.createDepartment("HR");
		List<Department> results = dao.searchDepartmentsByName("HR");
		assertEquals(1, results.size());
	}
	
	@Test
	public void can_search_by_department_id() {
		Department results = dao.getDepartmentById((long) 5);
		assertEquals(TEST_DEPARTMENT, results.toString());
	}
	
//	private Department getDepartment(long id, String name) throws SQLException {
//		Department theDepartment = new Department();
//		theDepartment.setId(id);
//		theDepartment.setName(name);
//		return theDepartment;
//	}
	
}


















