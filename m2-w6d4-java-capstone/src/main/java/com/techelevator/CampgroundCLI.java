package com.techelevator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.CampgroundDAO;
import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.ParkDAO;
import com.techelevator.campground.model.ReservationDAO;
import com.techelevator.campground.model.SiteDAO;
import com.techelevator.campground.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.campground.model.jdbc.JDBCParkDAO;
import com.techelevator.campground.model.jdbc.JDBCReservationDAO;
import com.techelevator.campground.model.jdbc.JDBCSiteDAO;
import com.techelevator.campground.view.Menu;

public class CampgroundCLI {

	
	private static final String MENU_OPTION_RETURN_TO_PREVIOUS_SCREEN = "Return to previous screen";
	
	private static final String PARK_INFO_VIEW_CAMPGROUNDS = "View Campgrounds";
	private static final String PARK_INFO_SEARCH_FOR_RESERVATION = "Search For Reservation";
	private static final String[] PARK_INFO_MENU = new String[] { PARK_INFO_VIEW_CAMPGROUNDS,
																  PARK_INFO_SEARCH_FOR_RESERVATION,
																  MENU_OPTION_RETURN_TO_PREVIOUS_SCREEN };
	
	private static final String CAMPGROUND_INFO_SEARCH_FOR_AVAILABLE_RESERVATION = "Search for Available Reservation";
	private static final String[] CAMPGROUND_INFO = new String[] { CAMPGROUND_INFO_SEARCH_FOR_AVAILABLE_RESERVATION,
																   MENU_OPTION_RETURN_TO_PREVIOUS_SCREEN };
	
	
	private Menu menu;
	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private ReservationDAO reservationDAO;
	private SiteDAO siteDAO;
	
	
	public static void main(String[] args) {
		CampgroundCLI application = new CampgroundCLI();
		application.run();
	}
	
	public CampgroundCLI() {
		this.menu = new Menu(System.in, System.out);
		
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		
		parkDAO = new JDBCParkDAO(dataSource);
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
		reservationDAO = new JDBCReservationDAO(dataSource);
		siteDAO = new JDBCSiteDAO(dataSource);
	}

	private void run() {
		List<String> parkNames = parkDAO.getAllParkNames();
		parkNames.add("Quit");
		String[] parkMenu = parkNames.toArray(new String[0]);
		while(true) {
			printHeading("Select a park for further details");
			String choice = (String)menu.getChoiceFromOptions(parkMenu);
			if(choice.equals("Quit")) {
				System.exit(0);
			} else {
				handleParkSelection(choice);
			}
		}
	}
	
	private void handleParkSelection(String parkName) {
		Park selectedPark = parkDAO.getAllParkInfo(parkName);
		if(selectedPark != null) {
				System.out.println(selectedPark.getParkName() + " National Park");
				System.out.println(String.format("%-20s", "Location: ") + selectedPark.getLocation());
				System.out.println(String.format("%-20s","Established: ") + selectedPark.getEstablishDate());
				System.out.println(String.format("%-20s","Area: ") + selectedPark.getArea());
				System.out.println(String.format("%-20s","Annual Visitors ") + selectedPark.getVisitors());
				System.out.println();
				System.out.println(selectedPark.getDescription());
		} else {
			System.out.println("There are no results.");
		}
		printHeading("Select Command");
		String choice = (String)menu.getChoiceFromOptions(PARK_INFO_MENU);
		if(choice.equals(PARK_INFO_VIEW_CAMPGROUNDS)) {
			handleViewCampgrounds(parkName);
		} else if (choice.equals(PARK_INFO_SEARCH_FOR_RESERVATION)){
			handleSearchForReservation();
		}
	}
	
	
	private void handleViewCampgrounds(String parkName) {
		List<Campground> campgroundsInPark = campgroundDAO.getCampgroundsByParkName(parkName);
		if(campgroundsInPark != null) {
			System.out.println("Name			Open		Close		Daily Fee");
			
		}
		
	}
	
	private void handleSearchForReservation() {
		
	}
	
	private void printHeading(String headingText) {
		System.out.println("\n"+headingText);
		for(int i = 0; i < headingText.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
	}
	
	private String getUserInput(String prompt) {
		System.out.print(prompt + " >>> ");
		return new Scanner(System.in).nextLine();
	}
	
}









