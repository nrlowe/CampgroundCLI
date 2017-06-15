package com.techelevator.campground.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Site;
import com.techelevator.campground.model.SiteDAO;

public class JDBCSiteDAO implements SiteDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Site> getCurrentReservationsBySite(Long id, LocalDate fromDate, LocalDate toDate) {
		List<Site> results = new ArrayList<Site>();
		String sqlReturnReservationsByCampId = "SELECT campground.name, site.site_id " +
"FROM campground " +
"JOIN site ON site.campground_id = campground.campground_id " +
"JOIN reservation ON site.site_id = reservation.site_id " +
"WHERE campground.campground_id = ? " +
"EXCEPT" +
"SELECT campground.name, site.site_id " +
"FROM campground " +
"JOIN site ON site.campground_id = campground.campground_id " +
"JOIN reservation ON site.site_id = reservation.site_id " +
"WHERE from_date >= ? AND from_date <= ? AND to_date >= ? AND to_date <= ? " +
"ORDER BY site_id ASC ";
		SqlRowSet rowResult = jdbcTemplate.queryForRowSet(sqlReturnReservationsByCampId, id, fromDate, toDate, fromDate, toDate);
		while(rowResult.next()) {
			Site siteResults = mapRowToSite(rowResult);
			results.add(siteResults);
		}
			
		
		return null;
	}
	private Site mapRowToSite(SqlRowSet results) {
		Site theSite;
		theSite = new Site();
		theSite.setSiteId(results.getLong("site_id"));
		theSite.setCampgroundId(results.getLong("campgroundId"));
		theSite.setSiteNumber(results.getLong("site_number"));
		theSite.setMaxOccupancy(results.getLong("max_occupancy"));
		theSite.setAccessible(results.getBoolean("accessible"));
		theSite.setMaxRVLength(results.getLong("max_rv_length"));
		theSite.setUtilities(results.getBoolean("utilities"));
		return theSite;
	}

}
