package com.techelevator.projects.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Department;
import com.techelevator.projects.model.DepartmentDAO;

public class JDBCDepartmentDAO implements DepartmentDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCDepartmentDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Department> getAllDepartments() {
		List<Department> allDepartments = new ArrayList<Department>();
		String sqlGetAllDepartments = "SELECT department_id, name " +
									"FROM department"; 
		SqlRowSet departmentResults = jdbcTemplate.queryForRowSet(sqlGetAllDepartments);
		while(departmentResults.next()) {
			Department theDepartment = mapRowToDepartment(departmentResults);
			allDepartments.add(theDepartment);
		}
		return allDepartments;
		
	}
 
	@Override
	public List<Department> searchDepartmentsByName(String nameSearch) {
		List<Department> departmentList = new ArrayList<Department>();
		String sqlFindDepartmentByName = "SELECT department_id, name " + "FROM department " + "WHERE name = ?";
		SqlRowSet departmentResult = jdbcTemplate.queryForRowSet(sqlFindDepartmentByName, nameSearch);
		while(departmentResult.next()) {
			Department newDepartment = mapRowToDepartment(departmentResult);
			departmentList.add(newDepartment);
		}
		return departmentList;
	}

	@Override
	public void updateDepartmentName(Long departmentId, String departmentName) {
		String sqlUpdateDepartmentName = "UPDATE department " + "SET name = ? " + "WHERE department_id = ?";
		jdbcTemplate.update(sqlUpdateDepartmentName, departmentName, departmentId);
	}

	@Override
	public Department createDepartment(String departmentName) {
		String sqlCreateDepartment = "INSERT INTO department(department_id, name) " + "VALUES( ?, ? ) ";
		Department newDepartment = new Department();
		newDepartment.setId(getNextDepartmentId());
		newDepartment.setName(departmentName);
		jdbcTemplate.update(sqlCreateDepartment, newDepartment.getId(), newDepartment.getName());
		return newDepartment;
	}

	@Override
	public Department getDepartmentById(Long id) {
		Department departmentById = new Department();
		String sqlGetDepartmentById = "SELECT name, department_id " + "From department " + "WHERE department_id = ?";
		SqlRowSet departmentResult = jdbcTemplate.queryForRowSet(sqlGetDepartmentById, id);
		while(departmentResult.next()) {
			departmentById = mapRowToDepartment(departmentResult);
		}
		return departmentById;
	}
	
	private Department mapRowToDepartment(SqlRowSet results) {
		Department theDepartment;
		theDepartment = new Department();
		theDepartment.setName(results.getString("name"));
		theDepartment.setId(results.getLong("department_id"));
		return theDepartment;
	}
	
	private long getNextDepartmentId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('seq_department_id')");
		if(nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong while getting the Department ID");
		}
	}

}
