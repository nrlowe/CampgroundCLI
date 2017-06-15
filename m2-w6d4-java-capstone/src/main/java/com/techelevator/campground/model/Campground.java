package com.techelevator.campground.model;

import java.time.LocalDate;

public class Campground {

	private Long campgroundId;
	private Long parkId;
	private String campgroundName;
	private LocalDate openFrom;
	private LocalDate openTo;
	private String dailyFee;

	public Long getCampgroundId() {
		return campgroundId;
	}

	public void setCampgroundId(Long campgroundId) {
		this.campgroundId = campgroundId;
	}

	public Long getParkId() {
		return parkId;
	}

	public void setParkId(Long parkId) {
		this.parkId = parkId;
	}

	public String getCampground() {
		return campgroundName;
	}

	public void setCampgroundName(String campgroundName) {
		this.campgroundName = campgroundName;
	}

	public LocalDate getOpenFrom() {
		return openFrom;
	}

	public void setOpenFrom(LocalDate openFrom) {
		this.openFrom = openFrom;
	}

	public LocalDate getOpenTo() {
		return openTo;
	}

	public void setOpenTo(LocalDate openTo) {
		this.openTo = openTo;
	}

	public String getDailyFee() {
		return dailyFee;
	}

	public void setDailyFee(String dailyFee) {
		this.dailyFee = dailyFee;
	}
}
