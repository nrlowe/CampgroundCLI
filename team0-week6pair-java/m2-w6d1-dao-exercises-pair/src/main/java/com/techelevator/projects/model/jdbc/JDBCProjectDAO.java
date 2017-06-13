package com.techelevator.projects.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Project;
import com.techelevator.projects.model.ProjectDAO;

public class JDBCProjectDAO implements ProjectDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCProjectDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Project> getAllActiveProjects() {
		List<Project> activeProjectList = new ArrayList<Project>();
		
		String sqlGetAllActiveProjects = "SELECT project_id, name, from_date, to_date " + "FROM project " + "WHERE from_date IS NOT NULL AND (to_date IS NULL OR to_date > ?) ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllActiveProjects, LocalDate.now());
		while(results.next()) {
			Project resultsProject = mapRowToProject(results);
			activeProjectList.add(resultsProject);
			
		}
		return activeProjectList;
	}

	@Override
	public void removeEmployeeFromProject(Long projectId, Long employeeId) {
		String sqlRemoveEmployeeFromProject = "DELETE FROM project_employee " + "WHERE project_id = ? AND employee_id = ? ";
		jdbcTemplate.update(sqlRemoveEmployeeFromProject, projectId, employeeId);
	}

	@Override
	public void addEmployeeToProject(Long projectId, Long employeeId) {
		String sqlAddEmployeeToProject = "INSERT INTO project_employee(project_id, employee_id) " + "VALUES (?, ?) ";
		jdbcTemplate.update(sqlAddEmployeeToProject, projectId, employeeId);
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
