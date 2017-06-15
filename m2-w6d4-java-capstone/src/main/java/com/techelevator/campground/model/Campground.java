package com.techelevator.campground.model;

import java.time.LocalDate;

public class Campground {

	private Long campgroundId;
	private Long parkId;
	private String parkName;
	private LocalDate openFrom;
	private LocalDate openTo;
	private DollarAmount dailyFee;

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

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
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

	public DollarAmount getDailyFee() {
		return dailyFee;
	}

	public void setDollarAmount(DollarAmount dailyFee) {
		this.dailyFee = dailyFee;
	}
}
