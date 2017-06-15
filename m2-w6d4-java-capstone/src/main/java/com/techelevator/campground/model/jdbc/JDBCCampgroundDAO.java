package com.techelevator.campground.model.jdbc;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.CampgroundDAO;

public class JDBCCampgroundDAO implements CampgroundDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Campground> getCampgroundsByParkID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Campground> getCamproundInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
