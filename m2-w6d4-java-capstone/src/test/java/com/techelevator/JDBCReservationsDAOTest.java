package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.campground.model.jdbc.JDBCReservationDAO;

public class JDBCReservationsDAOTest {
	private static SingleConnectionDataSource dataSource;
	private JDBCReservationDAO dao;
	private LocalDate inputFromDate = LocalDate.parse("1740-10-10");
	private LocalDate inputToDate = LocalDate.parse("1740-10-12");
	private LocalDate setDate = LocalDate.parse("2017-01-01");
	
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}
	
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}
	
	@Before
	public void setup() {
		String sqlInsertReservation = "INSERT INTO reservation(reservation_id, site_id, name, from_date, to_date, create_date) VALUES( ?, ?, ?, ?, ?, ? )";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sqlInsertReservation, 54, 10, "Captain Morgan", inputFromDate, inputToDate, setDate);
		dao = new JDBCReservationDAO(dataSource);
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void create_and_return_new_reservation() {
		List<String> results = dao.confirmReservation("Captain Morgan");
		assertEquals(1, results.size()); 
	}
	@Test
	public void create_reserveration_and_call_it() {
		dao.createNewReservation(24L, "George Washington", inputFromDate, inputToDate);
		List<String> results = dao.confirmReservation("George Washington");
		assertEquals(1, results.size());
	}

}
