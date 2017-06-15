package com.techelevator.campground.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.ParkDAO;


public class JDBCParkDAO implements ParkDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	@Override
	public List<Park> getAllParksByName() {
		List<Park> results = new ArrayList<Park>();
		String sqlParksByName = "SELECT park_id, name, location, establish_date, area, visitors, description " + "FROM park " + "ORDER BY name ASC "; 
		SqlRowSet parkResults = jdbcTemplate.queryForRowSet(sqlParksByName);
		while(parkResults.next()) {
			Park mapNametoList = mapRowToPark(parkResults);
			results.add(mapNametoList);
		}
		return results;
	}

	@Override
	public List<Park> getAllParkInfo(Long id) {
		List<Park> results = new ArrayList<Park>();
		String sqlParksById = "SELECT park_id, name, location, establish_date, area, visitors, description " + "FROM park " + "WHERE park_id = ? ";
		SqlRowSet parkInfo = jdbcTemplate.queryForRowSet(sqlParksById, id);
		while(parkInfo.next()) {
			Park mapParkInfo = mapRowToPark(parkInfo);
			results.add(mapParkInfo);
		}
		
		return results;
	}
	
	
	private Park mapRowToPark(SqlRowSet results) {
		Park thePark;
		thePark = new Park();
		thePark.setParkId(results.getLong("park_id"));
		thePark.setParkName(results.getString("name"));
		thePark.setLocation(results.getString("location"));
		thePark.setEstablishDate(results.getDate("establish_date").toLocalDate());
		thePark.setArea(results.getLong("area"));
		thePark.setVisitors(results.getLong("visitors"));
		thePark.setDescription(results.getString("description"));
		return thePark;
	}

}
