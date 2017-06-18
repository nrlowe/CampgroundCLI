package com.techelevator.campground.model;



public class Campground {

	private Long campgroundId;
	private Long parkId;
	private String campgroundName;
	private String openFrom;
	private String openTo;
	private double dailyFee;

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

	public String getCampgroundName() {
		return campgroundName;
	}

	public void setCampgroundName(String campgroundName) {
		this.campgroundName = campgroundName;
	}

	public String getOpenFrom() {
		return openFrom;
	}

	public void setOpenFrom(String openFrom) {
		this.openFrom = openFrom;
	}

	public String getOpenTo() {
		return openTo;
	}

	public void setOpenTo(String openTo) {
		this.openTo = openTo;
	}

	public double getDailyFee() {
		return dailyFee;
	}

	public void setDailyFee(double dailyFee) {
		this.dailyFee = dailyFee;
	}
}
