package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.campground.model.Site;
import com.techelevator.campground.model.jdbc.JDBCSiteDAO;

public class JDBCSiteDAOTest {
	private static SingleConnectionDataSource dataSource;
	private JDBCSiteDAO dao;
	private LocalDate fromDate = LocalDate.parse("2017-06-01");
	private LocalDate toDate = LocalDate.parse("2017-06-30");
//	private LocalDate arrivalDate = LocalDate.parse("2017-07-09");
//	private LocalDate departureDate = LocalDate.parse("2017-04-08");
//	
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
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		dao = new JDBCSiteDAO(dataSource);
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test 
	public void return_current_reservations_by_site() {
		List<Site> results = dao.getCurrentReservationsBySite(1L, fromDate, toDate);
		assertEquals(5, results.size());
 	}
	
	
	
}
