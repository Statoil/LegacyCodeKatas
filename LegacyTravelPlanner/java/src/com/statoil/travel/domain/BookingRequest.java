package com.statoil.travel.domain;

public class BookingRequest {
	public static final String AIR = "air";
	
	private String from;
	private String to;
	private String modeOfTransport;
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getModeOfTransport() {
		return modeOfTransport;
	}
	
	public void setModeOfTransport(String modeOfTransport) {
		this.modeOfTransport = modeOfTransport;
	}
	
	public static BookingRequest from(String from) {
		BookingRequest request = new BookingRequest();
		request.setFrom(from);
		return request;
	}

	public BookingRequest to(String to) {
		this.setTo(to);
		return this;
	}
	
	public BookingRequest by(String modeOfTransport) {
		this.setModeOfTransport(modeOfTransport);
		return this;
	}

}
