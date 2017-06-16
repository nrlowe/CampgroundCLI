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

import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.jdbc.JDBCParkDAO;

public class JDBCParkDAOTest {
	private static SingleConnectionDataSource dataSource;
	private JDBCParkDAO dao;
	private LocalDate create = LocalDate.parse("1812-10-10");
	
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
		String sqlInsertPark = "INSERT INTO park (park_id, name, location, establish_date, area, visitors, description ) VALUES (?, ?, ?, ?, ?, ?, ? ) ";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sqlInsertPark, 4, "Empty Park", "Mars", create, 10, 4, "Some empty park on Mars made by the Great Viking Sir Joe.");
		dao = new JDBCParkDAO(dataSource);
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void return_all_parks() {
		List<Park> results = dao.getAllParksByName();
		assertEquals(4, results.size());
	}
	
	@Test
	public void return_all_park_info() {
		List<Park> results = dao.getAllParkInfo(4L);
		assertEquals(1, results.size());
	}

}
