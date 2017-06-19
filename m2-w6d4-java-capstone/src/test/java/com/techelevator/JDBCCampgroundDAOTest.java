package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
//import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
//import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.jdbc.JDBCCampgroundDAO;

public class JDBCCampgroundDAOTest {
		private static SingleConnectionDataSource dataSource;
		private JDBCCampgroundDAO dao;
//		private LocalDate inputFromDate = LocalDate.parse("0440-10-10");
//		private LocalDate inputToDate = LocalDate.parse("2000-12-01");
//		private LocalDate setDate = LocalDate.parse("0440-08-01");
		
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
//			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			dao = new JDBCCampgroundDAO(dataSource);
		}
		
		@After
		public void rollback() throws SQLException {
			dataSource.getConnection().rollback();
		}
		
		@Test
		public void return_camgrounds_by_parkname() {
		List<Campground> results = dao.getCampgroundsByParkName("Cuyahoga Valley");
		assertEquals(1, results.size());
		}
		
		@Test
		public void return_campgroundinfo_by_campgroundid() {
			List<Campground> results = dao.getCamproundInfo(1L);
			assertEquals(1, results.size());
		}
		
		

}
