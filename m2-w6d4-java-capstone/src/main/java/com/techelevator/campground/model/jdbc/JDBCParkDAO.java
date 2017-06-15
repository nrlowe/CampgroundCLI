package com.techelevator.campground.model.jdbc;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.ParkDAO;

public class JDBCParkDAO implements ParkDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	@Override
	public List<Park> getAllParksByName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Park> getAllParkInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
