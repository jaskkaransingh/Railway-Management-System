package railway.service;

import railway.dao.BookingDao;
import railway.dao.BookingPassengerDao;
import railway.model.Booking;
import railway.model.Ticket;
import railway.model.TicketPassenger;

import java.util.ArrayList;
import java.security.SecureRandom;
import railway.dao.PassengerDao;
import railway.dao.SeatDao;
import railway.dao.ScheduleDao;

import railway.database.DBConnection;

import railway.model.Passenger;
import railway.model.Schedule;
import railway.model.BookingPassenger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
public class BookingService {

    private BookingDao bookingDao;
    private BookingPassengerDao bookingPassengerDao;
    
    private PassengerDao passengerDao;
    private SeatDao seatDao;
    private ScheduleDao scheduleDao;

    public BookingService() {

        bookingDao = new BookingDao();
        bookingPassengerDao = new BookingPassengerDao();
        passengerDao = new PassengerDao();
        seatDao = new SeatDao();
        scheduleDao = new ScheduleDao();

    }
    
    public Ticket bookTicket(int userId, int scheduleId, List<Passenger> passengers, List<Integer> seatIds, double totalAmount) {

    	// Step 1: Validate passengers
    	if (passengers == null || passengers.isEmpty()) {
    	    System.out.println("Booking failed: No passengers provided.");
    	    return null;
    	}

    	// Step 2: Validate seats
    	if (seatIds == null || seatIds.isEmpty()) {
    	    System.out.println("Booking failed: No seats selected.");
    	    return null;
    	}

    	// Step 3: Passenger count must equal seat count
    	if (passengers.size() != seatIds.size()) {
    	    System.out.println("Booking failed: Passenger count and seat count must match.");
    	    return null;
    	}

    	// Step 4: Validate fare
    	if (totalAmount <= 0) {
    	    System.out.println("Booking failed: Invalid total amount.");
    	    return null;
    	}

    	// Step 5: Check duplicate seat IDs
    	if (seatIds.size() != seatIds.stream().distinct().count()) {
    	    System.out.println("Booking failed: Duplicate seats selected.");
    	    return null;
    	}

    	// Step 6: Validate schedule
    	Schedule schedule = scheduleDao.getScheduleById(scheduleId);

    	if (schedule == null) {
    	    System.out.println("Booking failed: Invalid schedule.");
    	    return null;
    	}
    	Connection con = null;

    	try {

    	    con = DBConnection.getConnection();
    	    con.setAutoCommit(false);
    	    System.out.println("Transaction started.");
    	 // Step 9: Generate PNR
    	    String pnr = generatePNR();

    	    // Step 10: Create Booking object
    	    Booking booking = new Booking(
    	            userId,
    	            scheduleId,
    	            pnr,
    	            "CONFIRMED",
    	            totalAmount
    	    );

    	    // Step 11: Insert booking into database
    	    int bookingId = bookingDao.addBooking(con, booking);

    	    if (bookingId == -1) {
    	        throw new SQLException("Failed to create booking.");
    	    }

    	    System.out.println("Booking created successfully!");
    	    System.out.println("Booking ID: " + bookingId);
    	    System.out.println("PNR: " + pnr);
    	    
    	 // Step 12: Insert passengers
    	    ArrayList<Integer> passengerIds = new ArrayList<>();

    	    for (Passenger passenger : passengers) {

    	        int passengerId = passengerDao.addPassenger(con, passenger);

    	        if (passengerId == -1) {
    	            throw new SQLException("Failed to insert passenger.");
    	        }

    	        passengerIds.add(passengerId);

    	        System.out.println("Passenger added: " + passenger.getName());
    	    }

    	 // Step 13: Reserve selected seats
    	    for (int seatId : seatIds) {

    	        boolean seatBooked = seatDao.bookSeat(
    	                con,
    	                seatId,
    	                scheduleId
    	        );

    	        if (!seatBooked) {
    	            throw new SQLException(
    	                    "Seat " + seatId + " is unavailable or invalid."
    	            );
    	        }

    	        System.out.println("Seat booked: " + seatId);
    	    }
    	    
    	 // Step 14: Link passengers with booking and seats
    	    for (int i = 0; i < passengers.size(); i++) {

    	        BookingPassenger bookingPassenger = new BookingPassenger(
    	                bookingId,
    	                passengerIds.get(i),
    	                seatIds.get(i)
    	        );

    	        boolean linked = bookingPassengerDao.addBookingPassenger(
    	                con,
    	                bookingPassenger
    	        );

    	        if (!linked) {
    	            throw new SQLException(
    	                    "Failed to link passenger with booking."
    	            );
    	        }

    	        System.out.println(
    	                passengers.get(i).getName()
    	                + " assigned to seat "
    	                + seatIds.get(i)
    	        );
    	    }

    	    con.commit();
    	    System.out.println("Booking completed successfully!");

	    	 // Step 16: Retrieve complete ticket
	    	Ticket ticket = getTicketByPNR(pnr);
	
	    	return ticket;

    	} catch (SQLException e) {

    	    if (con != null) {
    	        try {
    	            con.rollback();
    	            System.out.println("Transaction rolled back.");
    	        } catch (SQLException rollbackError) {
    	            rollbackError.printStackTrace();
    	        }
    	    }

    	    e.printStackTrace();
    	    return null;

    	} finally {

    	    if (con != null) {
    	        try {
    	            con.close();
    	        } catch (SQLException e) {
    	            e.printStackTrace();
    	        }
    	    }

    	}
    	
    }

    public Ticket getTicketByPNR(String pnr) {

        // Step 1: Find booking using PNR
        Booking booking = bookingDao.getBookingByPNR(pnr);

        if (booking == null) {
            return null;
        }

        // Step 2: Find passengers belonging to this booking
        ArrayList<TicketPassenger> passengers =
                bookingPassengerDao.getTicketPassengers(
                        booking.getBookingId()
                );

        // Step 3: Combine everything into a Ticket
        Ticket ticket = new Ticket(booking, passengers);

        return ticket;
    }
    public String generatePNR() {

        SecureRandom random = new SecureRandom();

        while (true) {

            long pnrNumber = 1000000000L
                    + (long) (random.nextDouble() * 9000000000L);

            String pnr = String.valueOf(pnrNumber);

            
            Booking existingBooking = bookingDao.getBookingByPNR(pnr);

            if (existingBooking == null) {
                return pnr;
            }
        }
    }
    public boolean cancelTicket(String pnr) {

        // 1. Validate PNR
        if (pnr == null || pnr.trim().isEmpty()) {
            System.out.println("Invalid PNR!");
            return false;
        }

        // 2. Find booking
        Booking booking = bookingDao.getBookingByPNR(pnr);

        if (booking == null) {
            System.out.println("Booking not found!");
            return false;
        }

        // 3. Check booking status
        if (!booking.getBookingStatus().equals("CONFIRMED")) {
            System.out.println("Booking cannot be cancelled!");
            return false;
        }

        Connection con = null;

        try {
            // 4. Start transaction
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            // 5. Cancel booking
            boolean cancelled = bookingDao.cancelBooking(con, pnr);

            if (!cancelled) {
                throw new SQLException("Failed to cancel booking!");
            }

            // 6. Release seats
            boolean seatsReleased = seatDao.releaseSeats(con, pnr);

            if (!seatsReleased) {
                throw new SQLException("Failed to release seats!");
            }

            // 7. Commit transaction
            con.commit();

            System.out.println("Ticket cancelled successfully!");
            return true;

        } catch (SQLException e) {

            // 8. Rollback if anything fails
            if (con != null) {
                try {
                    con.rollback();
                    System.out.println("Cancellation rolled back!");
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }

            e.printStackTrace();
            return false;

        } finally {

            // 9. Close connection
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}