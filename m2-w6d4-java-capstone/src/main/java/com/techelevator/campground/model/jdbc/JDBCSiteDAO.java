package com.techelevator.campground.model.jdbc;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.campground.model.Site;
import com.techelevator.campground.model.SiteDAO;

public class JDBCSiteDAO implements SiteDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Site> findSite() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Site> findSiteByCampground() {
		// TODO Auto-generated method stub
		return null;
	}

}
