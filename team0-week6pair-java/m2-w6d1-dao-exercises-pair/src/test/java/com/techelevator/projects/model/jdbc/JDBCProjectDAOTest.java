package com.techelevator.projects.model.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.time.LocalDate;
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
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.Date;

import com.techelevator.projects.model.Project;


public class JDBCProjectDAOTest {
	private static final String TEST_PROJECT = "PressButton";
	private static SingleConnectionDataSource dataSource;
	private JDBCProjectDAO dao;
	private static final LocalDate ourStartDate = LocalDate.parse("1719-10-10");
	
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
	
	@Before public void setup() {
		String sqlInsertProject = "INSERT INTO project(project_id, name, from_date, to_date) VALUES (?, ?, ?, ?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sqlInsertProject, 7, TEST_PROJECT, ourStartDate, null);
		dao = new JDBCProjectDAO(dataSource); 
	}
	
	@After 
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void return_all_active_projects() {
		List<Project> results = dao.getAllActiveProjects();
			assertEquals(3, results.size());
	}
	
	@Test
	public void add_employee_to_the_project() {
		dao.addEmployeeToProject(7L, 5L);
		List<Project> results = ourEmployee();
		assertEquals(4, results.size());
		
	}
	
	@Test
	public void remove_employee_from_project() {
		dao.addEmployeeToProject(7L, 5L);
		dao.removeEmployeeFromProject(7L, 5L);
		List<Project> results = ourEmployee();
		assertEquals(3, results.size());
	}
	
	private List<Project> ourEmployee() {
		List<Project> numberOfEmployeesOnProject = new ArrayList<Project>();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//		String sqlFindEmployeeOnProject = "SELECT employee_id " + "FROM project_employee " + "WHERE project_id = ? ";
//		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindEmployeeOnProject, 7L);
		String sqlFindEmployeeOnProject = "SELECT project.project_id, name, from_date, to_date " + "FROM project " + "JOIN project_employee ON project_employee.project_id = project.project_id " + "WHERE employee_id = ? ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindEmployeeOnProject, 5L);
		while(results.next()) {
			Project resultsProject = mapRowToProject(results);
			numberOfEmployeesOnProject.add(resultsProject);
		}
		
		return numberOfEmployeesOnProject;
	}
	private Project mapRowToProject(SqlRowSet results) {
		Project theProject;
		theProject = new Project();
		theProject.setId(results.getLong("project_id"));
		theProject.setName(results.getString("name"));
		if(results.getDate("from_date") != null){
			theProject.setStartDate(results.getDate("from_date").toLocalDate());
		}
		if(results.getDate("to_date") != null){
		theProject.setEndDate(results.getDate("to_date").toLocalDate());
		}
		return theProject;
	}
}


