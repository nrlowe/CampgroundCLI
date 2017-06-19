package com.techelevator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.CampgroundDAO;
import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.ParkDAO;
import com.techelevator.campground.model.ReservationDAO;
import com.techelevator.campground.model.Site;
import com.techelevator.campground.model.SiteDAO;
import com.techelevator.campground.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.campground.model.jdbc.JDBCParkDAO;
import com.techelevator.campground.model.jdbc.JDBCReservationDAO;
import com.techelevator.campground.model.jdbc.JDBCSiteDAO;
import com.techelevator.campground.view.Menu;

public class CampgroundCLI {

	private static final String MENU_OPTION_RETURN_TO_PREVIOUS_SCREEN = "Return to previous screen";

	private static final String PARK_INFO_VIEW_CAMPGROUNDS = "View Campgrounds";
	// private static final String PARK_INFO_SEARCH_FOR_RESERVATION = "Search
	// For Reservation";
	private static final String[] PARK_INFO_MENU = new String[] { PARK_INFO_VIEW_CAMPGROUNDS,
			MENU_OPTION_RETURN_TO_PREVIOUS_SCREEN };
	// PARK_INFO_SEARCH_FOR_RESERVATION, MENU_OPTION_RETURN_TO_PREVIOUS_SCREEN
	// };

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
		while (true) {
			printHeading("Select a park for further details");
			String choice = (String) menu.getChoiceFromOptions(parkMenu);
			if (choice.equals("Quit")) {
				System.exit(0);
			} else {
				handleParkSelection(choice);
			}
		}
	}

	private void handleParkSelection(String parkName) {
		Park selectedPark = parkDAO.getAllParkInfo(parkName);
		System.out.println();
		if (selectedPark != null) {
			System.out.println(selectedPark.getParkName() + " National Park");
			System.out.println(String.format("%-20s", "Location: ") + selectedPark.getLocation());
			System.out.println(String.format("%-20s", "Established: ") + selectedPark.getEstablishDate());
			System.out.println(String.format("%-20s", "Area: ") + selectedPark.getArea());
			System.out.println(String.format("%-20s", "Annual Visitors ") + selectedPark.getVisitors());
			System.out.println();
			String formatParkDescription = selectedPark.getDescription();
			StringBuilder parkDescription = new StringBuilder(formatParkDescription);
			int i = 0;
			while (i + 20 < parkDescription.length() && (i = parkDescription.lastIndexOf(" ", i + 65)) != -1) {
				parkDescription.replace(i, i + 1, "\n");
			}
			System.out.println(parkDescription);
		} else {
			System.out.println("There are no results.");
		}
		printHeading("Select Command");
		String choice = (String) menu.getChoiceFromOptions(PARK_INFO_MENU);
		if (choice.equals(PARK_INFO_VIEW_CAMPGROUNDS)) {
			handleViewCampgrounds(selectedPark.getParkName());
			// } else if (choice.equals(PARK_INFO_SEARCH_FOR_RESERVATION)) {
			// handleSearchForReservation(selectedPark.getParkName());
		}
	}

	private void handleViewCampgrounds(String parkName) {
		List<Campground> campgroundsInPark = campgroundDAO.getCampgroundsByParkName(parkName);
		if (campgroundsInPark != null) {
			System.out.println();
			System.out.println("CAMPGROUND LIST");
			System.out.println(String.format("%-20s", "Campground ID") + String.format("%-35s", "Name")
					+ String.format("%-15s", "Open") + String.format("%-17s", "Close") + "Daily Fee");
			System.out.println(
					"------------------------------------------------------------------------------------------------");
			for (Campground campground : campgroundsInPark) {
				Long campgroundId = campground.getCampgroundId();
				String campgroundName = campground.getCampgroundName();
				String formatOpenFrom = campground.getOpenFrom();
				String openFrom = convertNumToMonth(formatOpenFrom);
				String formatOpenTo = campground.getOpenTo();
				String openTo = convertNumToMonth(formatOpenTo);
				double dailyFee = campground.getDailyFee();
				System.out.println(String.format("%-5s", "") + String.format("%-15s", campgroundId)
						+ String.format("%-35s", campgroundName) + String.format("%-15s", openFrom)
						+ String.format("%-17s", openTo) + "$" + dailyFee + "0");
			}
			String choiceTwo = (String) menu.getChoiceFromOptions(CAMPGROUND_INFO);
			if (choiceTwo.equals(CAMPGROUND_INFO_SEARCH_FOR_AVAILABLE_RESERVATION)) {
				handleSearchForReservation(parkName);
			} else {
			}
		}
	}

	private void handleSearchForReservation(String parkName) {
		List<Campground> campgroundsInPark = campgroundDAO.getCampgroundsByParkName(parkName);
		if (campgroundsInPark != null) {
			System.out.println();
			System.out.println("CAMPGROUND LIST");
			System.out.println(String.format("%-20s", "Campground ID") + String.format("%-35s", "Name")
					+ String.format("%-15s", "Open") + String.format("%-17s", "Close") + "Daily Fee");
			System.out.println(
					"------------------------------------------------------------------------------------------------");
			for (Campground campground : campgroundsInPark) {
				Long campgroundId = campground.getCampgroundId();
				String campgroundName = campground.getCampgroundName();
				String formatOpenFrom = campground.getOpenFrom();
				String openFrom = convertNumToMonth(formatOpenFrom);
				String formatOpenTo = campground.getOpenTo();
				String openTo = convertNumToMonth(formatOpenTo);
				double dailyFee = campground.getDailyFee();
				System.out.println(String.format("%-5s", "") + String.format("%-15s", campgroundId)
						+ String.format("%-35s", campgroundName) + String.format("%-15s", openFrom)
						+ String.format("%-17s", openTo) + "$" + dailyFee + "0");
			}
			String campgroundId = menu.getChoiceFromOptions("Which campground (enter 0 to cancel)?");
			Long id = Long.parseLong(campgroundId);
			Long obj1 = campgroundsInPark.get(0).getCampgroundId();
			Long obj2 = campgroundsInPark.get(campgroundsInPark.size() - 1).getCampgroundId();
			if (id == 0) {
				handleViewCampgrounds(parkName);
			} else if (id < obj1 || id > obj2) {
				System.out.println();
				System.out.println("Please select a valid option");
				handleViewCampgrounds(parkName);
			} else {
				String arrivalDate = menu.getChoiceFromOptions("What is the arrival date? yyyy-mm-dd:");
				// if date is not in correct format?
				// or try loop?
//					try {
						LocalDate fromDate = convertStringToDate(arrivalDate);
//					} catch (ParseException e) {
//						System.out.println("Please enter in provided format");
//					}
					// formatter.setLenient(false);
					String departureDate = menu.getChoiceFromOptions("What is the departure date? yyyy-mm-dd: ");
//					try {
						LocalDate toDate = convertStringToDate(departureDate);
//					} catch (ParseException e) {
//						System.out.println("Please enter in provided format");
//					}
					validateDateRange(toDate, fromDate, parkName);
					handleMakeReservation(id, fromDate, toDate);
			}
		}
	}

	private void handleMakeReservation(Long id, LocalDate fromDate, LocalDate toDate) {
		List<Site> availableSites = siteDAO.getCurrentReservationsBySite(id, fromDate, toDate);
		if (availableSites != null) {
			System.out.println();
			System.out.println("AVAILABLE CAMPSITES");
			System.out.println(String.format("%-20s", "Site No.") + String.format("%-20s", "Max Occup.")
					+ String.format("%-20s", "Accessible") + String.format("%-20s", "Max RV Length")
					+ String.format("%-30s", "Utilities Available") + "Cost");
			System.out.println(
					"------------------------------------------------------------------------------------------------------------------");
			for (Site openSite : availableSites) {
				Long siteId = openSite.getSiteId();
				Long maxOccupancy = openSite.getMaxOccupancy();
				boolean accessible = openSite.getAccessible();
				Long maxRVLength = openSite.getMaxRVLength();
				boolean utilities = openSite.getUtilities();
				System.out.println(String.format("%-5s", " ") + String.format("%-20s", siteId)
						+ String.format("%-18s", maxOccupancy) + String.format("%-22s", accessible)
						+ String.format("%-23s", maxRVLength) + String.format("%-20s", utilities) + "$"
						+ openSite.getDailyFee() * getNumberOfDaysForCost(toDate, fromDate) + "0");
			}
			String chosenSite = menu.getChoiceFromOptions("Which site should be reserved (Enter 0 to cancel)?");
			Long siteId = Long.parseLong(chosenSite);
			if (siteId == 0) {
				handleMakeReservation(id, fromDate, toDate);
			}
			String name = menu.getChoiceFromOptions("What name should the reservation be made under?");
			reservationDAO.createNewReservation(siteId, name, fromDate, toDate);
			System.out.println("The reservation has been made and the Confirmation ID is  "
					+ reservationDAO.confirmReservation(name));
			System.exit(0);
		} else {
			System.out.println("There are no available reservations.");
		}
	}

	private int getNumberOfDaysForCost(LocalDate endDate, LocalDate startDate) {
		int totalDays = endDate.compareTo(startDate);
		return totalDays;
	}

	private int validateDateRange(LocalDate endDate, LocalDate startDate, String parkName) {
		// What if they pick a month the campground isnt open??
		int totalDays = endDate.compareTo(startDate);
		if (totalDays < 0) {
			System.out.println("Departure date must be AFTER arrival date!");
			handleSearchForReservation(parkName);
		} else if (totalDays == 0) {
			System.out.println("Reservation must be for at least 1 night.");
			handleSearchForReservation(parkName);
		} else {
		}
		return totalDays;
	}

	private LocalDate convertStringToDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate newDate = LocalDate.parse(date, formatter);
		return newDate;
	}

	private void printHeading(String headingText) {
		System.out.println("\n" + headingText);
		for (int i = 0; i < headingText.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
	}

	private String convertNumToMonth(String num) {
		if (num.equals("01")) {
			return "January";
		} else if (num.equals("02")) {
			return "Feburary";
		} else if (num.equals("03")) {
			return "March";
		} else if (num.equals("04")) {
			return "April";
		} else if (num.equals("05")) {
			return "May";
		} else if (num.equals("06")) {
			return "June";
		} else if (num.equals("07")) {
			return "July";
		} else if (num.equals("08")) {
			return "August";
		} else if (num.equals("09")) {
			return "September";
		} else if (num.equals("10")) {
			return "October";
		} else if (num.equals("11")) {
			return "Novemeber";
		} else if (num.equals("12")) {
			return "Decemeber";
		} else {

		}
		return null;
	}

}
