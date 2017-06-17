package com.techelevator.campground.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.CampgroundDAO;

public class JDBCCampgroundDAO implements CampgroundDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Campground> getCampgroundsByParkName(String name) {
		List<Campground> results = new ArrayList<Campground>();
		String sqlReturnCampgrounds = "SELECT campground_id, campground.park_id , campground.name, open_from_mm, open_to_mm, daily_fee " +
				"FROM campground " + 
				"JOIN park ON park.park_id = campground.park_id " +
				"WHERE park.name = ? ";
		SqlRowSet campgroundInfo = jdbcTemplate.queryForRowSet(sqlReturnCampgrounds, name);
		while(campgroundInfo.next()) {
			Campground campgroundResults = mapRowToPark(campgroundInfo);
			results.add(campgroundResults);
		}
 		return results;
	}


	@Override
	public List<Campground> getCamproundInfo(Long id) {
		List<Campground> results = new ArrayList<Campground>();
		String sqlReturnsCampgroundById = "SELECT campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee " + "FROM campground " + "WHERE campground_id = ? ";
		SqlRowSet campgroundInfo = jdbcTemplate.queryForRowSet(sqlReturnsCampgroundById, id);
		while(campgroundInfo.next()) {
			Campground campgroundResults = mapRowToPark(campgroundInfo);
			results.add(campgroundResults);
		}
		return results;
	}
	
	private Campground mapRowToPark(SqlRowSet results) {
		Campground theCampground;
		theCampground = new Campground();
		theCampground.setCampgroundId(results.getLong("campground_id"));
		theCampground.setParkId(results.getLong("park_id"));
		theCampground.setCampgroundName(results.getString("name"));
		theCampground.setOpenFrom(results.getString("open_from_mm"));
		theCampground.setOpenTo(results.getString("open_to_mm"));
		theCampground.setDailyFee(results.getDouble("daily_fee"));
		return theCampground;
	}

}
