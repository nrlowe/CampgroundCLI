package com.techelevator.campground.model.jdbc;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.campground.model.Reservation;
import com.techelevator.campground.model.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Reservation> getAllReservations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reservation> searchReservationsByName() {
		// TODO Auto-generated method stub
		return null;
	}

}
