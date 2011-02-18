package com.statoil.travel;

import com.statoil.travel.domain.BookingRequest;
import com.statoil.travel.domain.Calendar;
import com.statoil.travel.domain.ExpenseReport;
import com.statoil.travel.domain.Itinerary;
import com.statoil.travel.domain.Payment;
import com.statoil.travel.domain.User;
import com.statoil.travel.service.CalendarService;
import com.statoil.travel.service.LoginService;
import com.statoil.travel.service.TravelBookingService;
import com.statoil.travel.service.TravelReimbursmentService;

public class TravelPlanner {
	private User user;
	private Calendar calendar;
	private LoginService loginService;
	private TravelBookingService travelBookingService;
	private CalendarService calendarService;
	private TravelReimbursmentService travelReimbursementService;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		this.calendar = calendarService.getCalendarFor(user);
	}

	public boolean login() {
		boolean success = loginService.login(user);
		return success;
	}
	
	public Itinerary bookTravel(BookingRequest request) {
		return travelBookingService.requestBooking(request);
	}
	
	public void updateCalendar(Itinerary itinerary) {
		calendarService.updateCalendar(itinerary);
	}
	
	public Payment reimburseExpenses(ExpenseReport expenses) {
		travelReimbursementService.reimburseExpenses(expenses);
		return new Payment();
	}
	
	public TravelPlanner() {
		initializeServices();
	}
	
	private void initializeServices() {
		loginService = new LoginService();
		travelBookingService = new TravelBookingService();
		calendarService = new CalendarService();
		travelReimbursementService = new TravelReimbursmentService();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TravelPlanner travelPlanner = new TravelPlanner();
		User user = User.withName("Karl");
		travelPlanner.setUser(user);
		boolean success = travelPlanner.login();
		if (success) {
			BookingRequest request = BookingRequest.from("Trondheim").to("Stavanger").by(BookingRequest.AIR);
			Itinerary itinerary = travelPlanner.bookTravel(request);
			if (itinerary.booked()) {
				travelPlanner.updateCalendar(itinerary);
				ExpenseReport expenses = ExpenseReport.from(itinerary);
				Payment amount = travelPlanner.reimburseExpenses(expenses);
			}
		}
	}
}
