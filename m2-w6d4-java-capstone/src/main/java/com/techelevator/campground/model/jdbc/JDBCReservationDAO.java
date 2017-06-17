package com.techelevator.campground.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Reservation;
import com.techelevator.campground.model.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO {
	private JdbcTemplate jdbcTemplate;
	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void createNewReservation(Long siteId, String name, LocalDate fromDate, LocalDate toDate) {
		LocalDate create = LocalDate.now();
		String sqlReservationCreation = "INSERT INTO reservation(site_id, name, from_date, to_date, create_date) VALUES(?, ?, ?, ?, ?)";
		jdbcTemplate.update(sqlReservationCreation, siteId, name, fromDate, toDate, create);
	}
	
	@Override
	public List<String> confirmReservation(String name) {
		List<String> reservationId = new ArrayList<String>();
		String sqlReturnReservationName = "SELECT reservation_id " + "FROM reservation " + "WHERE name = ? ";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlReturnReservationName, name);
		while(result.next()) {
			reservationId.add(result.getString("reservation_id"));
		
		}
		return reservationId;
	}
	
	private Reservation mapToReservationRow(SqlRowSet result) {
		Reservation newReservation;
		newReservation = new Reservation();
		newReservation.setReservationId(result.getLong("reservation_id"));
		newReservation.setSiteId(result.getLong("site_id"));
		newReservation.setName(result.getString("name"));
		newReservation.setFromDate(result.getDate("from_date").toLocalDate());
		newReservation.setToDate(result.getDate("to_date").toLocalDate());
		newReservation.setReservationCreatedDate(result.getDate("create_date").toLocalDate());
		return newReservation;
	}
}

	
