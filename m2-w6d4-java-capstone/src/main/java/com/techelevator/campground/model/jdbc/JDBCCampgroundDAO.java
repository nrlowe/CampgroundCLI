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
	public List<Campground> getCampgroundsByParkID(Long id) {
		List<Campground> results = new ArrayList<Campground>();
		String sqlReturnCampgrounds = "SELECT campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee " + "FROM campground " + "WHERE park_id = ? ";
		SqlRowSet campgroundInfo = jdbcTemplate.queryForRowSet(sqlReturnCampgrounds, id);
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
		theCampground.setOpenFrom(results.getDate("open_from_mm").toLocalDate());
		theCampground.setOpenTo(results.getDate("open_to_mm").toLocalDate());
		theCampground.setDailyFee(results.getString("daily_fee"));
		return theCampground;
	}

}
