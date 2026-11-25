package railway.main;

import java.util.Scanner;
import railway.dao.TrainScheduleDao;
import railway.model.TrainSchedule;
import railway.filehandling.BookingCsvHandler;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import railway.dao.SeatDao;
import railway.model.Seat;
import railway.service.BookingService;
import railway.model.Ticket;
import railway.service.PaymentService;
import railway.model.Payment;
import railway.service.UserService;
import railway.model.User;
import railway.model.Passenger;
import java.util.ArrayList;
import railway.dao.TrainDao;
import railway.model.Train;
import railway.dao.ScheduleDao;
import railway.model.Schedule;
import railway.dao.BookingDao;
import railway.model.Booking;
import railway.dao.UserDao;
import railway.filehandling.TicketPdfHandler;
import railway.payment.PaymentMethod;
import railway.payment.PaymentMethodFactory;

import railway.strategy.FareStrategy;
import railway.strategy.AcFareStrategy;
import railway.strategy.SleeperFareStrategy;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static TrainScheduleDao trainScheduleDao = new TrainScheduleDao();
    private static SeatDao seatDao = new SeatDao();
    private static BookingService bookingService = new BookingService();
    private static PaymentService paymentService = new PaymentService();
    private static UserService userService = new UserService();
    private static User currentUser = null;
    private static TrainDao trainDao = new TrainDao();
    private static ScheduleDao scheduleDao = new ScheduleDao();
    private static BookingDao bookingDao = new BookingDao();
    private static UserDao userDao = new UserDao();
    private static BookingCsvHandler csvHandler = new BookingCsvHandler();
    private static TicketPdfHandler ticketPdfHandler = new TicketPdfHandler();

    public static void main(String[] args) {

        boolean running = true;

        while (running) {
        	if (currentUser != null &&
        	        currentUser.getRole().equals("ADMIN")) {

        	    showAdminMenu();

        	    int adminChoice = sc.nextInt();
        	    sc.nextLine();

        	    switch (adminChoice) {

        	    case 1:

        	        System.out.println("\n===== ALL TRAINS =====");

        	        List<Train> trains = trainDao.getAllTrains();

        	        if (trains.isEmpty()) {
        	            System.out.println("No trains found!");
        	        } else {
        	            for (Train train : trains) {
        	                System.out.println(train);
        	            }
        	        }

        	        break;
        	        
        	    case 2: {

        	        System.out.println("\n===== ADD NEW TRAIN =====");

        	        System.out.print("Enter Train Number: ");
        	        String trainNumber = sc.nextLine();

        	        System.out.print("Enter Train Name: ");
        	        String trainName = sc.nextLine();

        	        System.out.print("Enter Source Station: ");
        	        String source = sc.nextLine();

        	        System.out.print("Enter Destination Station: ");
        	        String destination = sc.nextLine();

        	        Train train = new Train(
        	                0,
        	                trainNumber,
        	                trainName,
        	                source,
        	                destination
        	        );

        	        boolean added = trainDao.addTrain(train);

        	        if (added) {
        	            System.out.println("\nTrain added successfully!");
        	        } else {
        	            System.out.println("\nFailed to add train!");
        	            System.out.println("Check whether the train number already exists.");
        	        }

        	        break;
        	    }
        	    
        	    case 3: {

        	        System.out.println("\n===== UPDATE TRAIN =====");

        	        System.out.print("Enter Train ID to update: ");
        	        int trainId = sc.nextInt();
        	        sc.nextLine();

        	        System.out.print("Enter new Train Number: ");
        	        String trainNumber = sc.nextLine();

        	        System.out.print("Enter new Train Name: ");
        	        String trainName = sc.nextLine();

        	        System.out.print("Enter new Source Station: ");
        	        String source = sc.nextLine();

        	        System.out.print("Enter new Destination Station: ");
        	        String destination = sc.nextLine();

        	        Train train = new Train(
        	                trainId,
        	                trainNumber,
        	                trainName,
        	                source,
        	                destination
        	        );

        	        boolean updated = trainDao.updateTrain(train);

        	        if (updated) {
        	            System.out.println("\nTrain updated successfully!");
        	        } else {
        	            System.out.println("\nTrain update failed!");
        	            System.out.println("Check Train ID or duplicate Train Number.");
        	        }

        	        break;
        	    }
        	    
        	    case 5:

        	        boolean scheduleMenuRunning = true;

        	        while (scheduleMenuRunning) {

        	            showScheduleMenu();

        	            int scheduleChoice = sc.nextInt();
        	            sc.nextLine();

        	            switch (scheduleChoice) {

        	            case 1:

        	                ArrayList<TrainSchedule> allSchedules =
        	                        trainScheduleDao.getAllTrainSchedules();

        	                System.out.println("\n========== ALL SCHEDULES ==========");

        	                if (allSchedules.isEmpty()) {
        	                    System.out.println("No schedules found.");
        	                } else {
        	                    for (TrainSchedule ts : allSchedules) {
        	                        System.out.println("----------------------------------");
        	                        System.out.println("Schedule ID: " + ts.getScheduleId());
        	                        System.out.println("Train: " + ts.getTrainNumber()
        	                                + " - " + ts.getTrainName());
        	                        System.out.println("Route: " + ts.getSource()
        	                                + " to " + ts.getDestination());
        	                        System.out.println("Date: " + ts.getJourneyDate());
        	                        System.out.println("Departure: " + ts.getDepartureTime());
        	                        System.out.println("Arrival: " + ts.getArrivalTime());
        	                    }
        	                }

        	                break;

	        	           case 2:

	        	        	    System.out.println("\n========== ADD NEW SCHEDULE ==========");

	        	        	    // Display available trains
	        	        	    ArrayList<Train> allTrains = trainDao.getAllTrains();

	        	        	    for (Train train : allTrains) {
	        	        	        System.out.println(train);
	        	        	    }

	        	        	    System.out.print("\nEnter Train ID: ");
	        	        	    int trainId = sc.nextInt();
	        	        	    sc.nextLine();

	        	        	    System.out.print("Enter Journey Date (YYYY-MM-DD): ");
	        	        	    LocalDate journeyDate = LocalDate.parse(sc.nextLine());

	        	        	    System.out.print("Enter Departure Time (HH:MM): ");
	        	        	    LocalTime departureTime = LocalTime.parse(sc.nextLine());

	        	        	    System.out.print("Enter Arrival Time (HH:MM): ");
	        	        	    LocalTime arrivalTime = LocalTime.parse(sc.nextLine());

	        	        	    Schedule schedule = new Schedule(
	        	        	        0,
	        	        	        trainId,
	        	        	        journeyDate,
	        	        	        departureTime,
	        	        	        arrivalTime
	        	        	    );

	        	        	    boolean added = scheduleDao.addSchedule(schedule);

	        	        	    if (added) {

	        	        	        System.out.println("Schedule added successfully!");

	        	        	        // Find the newly created Schedule ID
	        	        	        ArrayList<Schedule> updatedSchedules = scheduleDao.getAllSchedules();

	        	        	        int newScheduleId = -1;

	        	        	        for (Schedule s : updatedSchedules) {

	        	        	            if (s.getTrainId() == trainId &&
	        	        	                    s.getJourneyDate().equals(journeyDate)) {

	        	        	                newScheduleId = s.getScheduleId();
	        	        	                break;
	        	        	            }
	        	        	        }

	        	        	        // Generate seats automatically
	        	        	        if (newScheduleId != -1) {

	        	        	            boolean seatsGenerated =
	        	        	                    seatDao.generateSeats(newScheduleId);

	        	        	            if (seatsGenerated) {
	        	        	                System.out.println("Seats are ready for booking!");
	        	        	            } else {
	        	        	                System.out.println("Schedule created, but seat generation failed.");
	        	        	            }

	        	        	        } else {
	        	        	            System.out.println("Could not find the new Schedule ID.");
	        	        	        }

	        	        	    } else {
	        	        	        System.out.println("Failed to add schedule.");
	        	        	    }

	        	        	    break;

        	                case 3:
        	                    scheduleMenuRunning = false;
        	                    break;

        	                default:
        	                    System.out.println("Invalid choice!");
        	            }
        	        }

        	        break;
        	    
        	    case 6:

        	        ArrayList<Booking> allBookings = bookingDao.getAllBookings();

        	        System.out.println("\n========== ALL BOOKINGS ==========");

        	        if (allBookings.isEmpty()) {
        	            System.out.println("No bookings found.");
        	        } else {

        	            for (Booking booking : allBookings) {

        	                System.out.println("----------------------------------");
        	                System.out.println("Booking ID: " + booking.getBookingId());
        	                System.out.println("User ID: " + booking.getUserId());
        	                System.out.println("Schedule ID: " + booking.getScheduleId());
        	                System.out.println("PNR: " + booking.getPnr());
        	                System.out.println("Status: " + booking.getBookingStatus());
        	                System.out.println("Booking Date: " + booking.getBookingDate());
        	                System.out.println("Amount: Rs. " + booking.getTotalAmount());
        	            }
        	        }

        	        break;
        	    
        	    case 7:

        	        ArrayList<User> allUsers = userDao.getAllUsers();

        	        System.out.println("\n========== ALL REGISTERED USERS ==========");

        	        if (allUsers.isEmpty()) {
        	            System.out.println("No users found.");
        	        } else {

        	            for (User user : allUsers) {

        	                System.out.println("----------------------------------");
        	                System.out.println("User ID: " + user.getUserId());
        	                System.out.println("Name: " + user.getName());
        	                System.out.println("Email: " + user.getEmail());
        	                System.out.println("Role: " + user.getRole());
        	            }
        	        }

        	        break;
        	       
        	    case 8:

        	        ArrayList<Booking> bookingsToExport = bookingDao.getAllBookings();

        	        csvHandler.exportBookings(bookingsToExport);

        	        break;

        	    case 9:

        	        currentUser = null;
        	        System.out.println("Admin logged out successfully!");
        	        break;

        	    default:

        	        System.out.println("Admin option coming soon!");
        	}

        	    continue;
        	}
        	if (currentUser != null) {

        	    System.out.println("Logged in as: " + currentUser.getName());
        	    System.out.println("Role: " + currentUser.getRole());

        	    if (currentUser.getRole().equals("ADMIN")) {
        	        System.out.println("Access Level: Administrator");
        	    } else {
        	        System.out.println("Access Level: Standard User");
        	    }

        	} else {

        	    System.out.println("Status: Guest");
        	}

            System.out.println("\n====================================");
            System.out.println("   RAILWAY RESERVATION SYSTEM");
            System.out.println("====================================");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Search Trains");
            System.out.println("4. View Available Seats");
            System.out.println("5. Book Ticket");
            System.out.println("6. View Ticket using PNR");
            System.out.println("7. Cancel Ticket");
            System.out.println("8. Make Payment");
            System.out.println("9. View Payment Details");
            System.out.println("10. Logout");
            System.out.println("11. Exit");
            
            System.out.println("------------------------------------");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

            case 1:

                System.out.println("\n===== USER REGISTRATION =====");

                System.out.print("Enter your name: ");
                String name = sc.nextLine();

                System.out.print("Enter your email: ");
                String email = sc.nextLine();

                System.out.print("Enter your password: ");
                String password = sc.nextLine();

                try {

                    boolean registered = userService.registerUser(
                            name,
                            email,
                            password
                    );

                    if (registered) {
                        System.out.println("Registration successful!");
                    } else {
                        System.out.println("Registration failed!");
                    }

                } catch (SQLException e) {

                    System.out.println("Database error during registration!");
                    e.printStackTrace();
                }

                break;

            case 2:

                System.out.println("\n===== USER LOGIN =====");

                System.out.print("Enter email: ");
                String loginEmail = sc.nextLine();

                System.out.print("Enter password: ");
                String loginPassword = sc.nextLine();

                try {

                    User loggedInUser = userService.loginUser(
                            loginEmail,
                            loginPassword
                    );

                    if (loggedInUser != null) {

                        currentUser = loggedInUser;

                        System.out.println("\nLogin successful!");
                        System.out.println("Welcome, " + currentUser.getName());

                        System.out.println("User ID: " + currentUser.getUserId());
                        System.out.println("Role: " + currentUser.getRole());

                    } else {

                        System.out.println("Login failed!");
                    }

                } catch (SQLException e) {

                    System.out.println("Database error during login!");
                    e.printStackTrace();
                }

                break;

                case 3:

                    System.out.println("\n===== SEARCH TRAINS =====");

                    System.out.print("Enter source station: ");
                    String source = sc.nextLine();

                    System.out.print("Enter destination station: ");
                    String destination = sc.nextLine();

                    System.out.print("Enter journey date (YYYY-MM-DD): ");
                    String dateInput = sc.nextLine();

                    try {

                        LocalDate journeyDate = LocalDate.parse(dateInput);

                        List<TrainSchedule> trains =
                                trainScheduleDao.searchTrains(
                                        source,
                                        destination,
                                        journeyDate
                                );

                        if (trains.isEmpty()) {

                            System.out.println("No trains found!");

                        } else {

                            System.out.println("\n===== AVAILABLE TRAINS =====");

                            for (TrainSchedule train : trains) {
                                System.out.println(train);
                            }
                        }

                    } catch (Exception e) {

                        System.out.println("Invalid date format!");
                        System.out.println("Please use YYYY-MM-DD");

                    }

                    break;

                case 4: {

                    System.out.println("\n===== AVAILABLE SEATS =====");

                    System.out.print("Enter Schedule ID: ");
                    int scheduleId = sc.nextInt();
                    sc.nextLine();

                    List<Seat> availableSeats =
                            seatDao.getAvailableSeats(scheduleId);

                    if (availableSeats.isEmpty()) {

                        System.out.println("No seats available!");

                    } else {

                        System.out.println("\nAvailable Seats:");

                        for (Seat seat : availableSeats) {
                            System.out.println(seat);
                        }
                    }

                    break;
                }

                case 5: {

                    System.out.println("\n===== BOOK TICKET =====");

                    if (currentUser == null) {

                        System.out.println("Please login first to book a ticket!");

                    } else {

                        System.out.println("Welcome, " + currentUser.getName());
                        System.out.println("User ID: " + currentUser.getUserId());

                        System.out.println("\nAvailable Train Schedules:");

                        List<TrainSchedule> schedules =
                                trainScheduleDao.getAllTrainSchedules();

                        if (schedules.isEmpty()) {

                            System.out.println("No train schedules available!");

                        } else {

                        	System.out.println("\n========== AVAILABLE TRAINS ==========");

                        	for (int i = 0; i < schedules.size(); i++) {

                        	    TrainSchedule schedule = schedules.get(i);

                        	    System.out.println("\n" + (i + 1) + ". "
                        	            + schedule.getTrainNumber() + " - "
                        	            + schedule.getTrainName());

                        	    System.out.println("   Route: "
                        	            + schedule.getSource() + " to "
                        	            + schedule.getDestination());

                        	    System.out.println("   Date: "
                        	            + schedule.getJourneyDate());

                        	    System.out.println("   Departure: "
                        	            + schedule.getDepartureTime());

                        	    System.out.println("   Arrival: "
                        	            + schedule.getArrivalTime());
                        	}

                        	System.out.print("\nSelect train by serial number: ");
                        	int trainChoice = sc.nextInt();
                        	sc.nextLine();

                        	if (trainChoice < 1 || trainChoice > schedules.size()) {

                        	    System.out.println("Invalid train selection!");
                        	    break;
                        	}

                        	int selectedScheduleId =
                        	        schedules.get(trainChoice-1).getScheduleId();

                        	System.out.println("Selected train successfully!");
                        	

                            System.out.println("\n===== AVAILABLE SEATS =====");

                            List<Seat> availableSeats =
                                    seatDao.getAvailableSeats(selectedScheduleId);

                            if (availableSeats.isEmpty()) {

                                System.out.println("No seats available for this schedule!");

                            } else {

                                for (Seat seat : availableSeats) {
                                    System.out.println(
                                            seat.getSeatNumber() + " | Class: "
                                            + seat.getSeatClass() + " | Available"
                                    );
                                }

                                System.out.print("\nEnter number of passengers: ");
                                int passengerCount = sc.nextInt();
                                sc.nextLine();

                                if (passengerCount <= 0 ||
                                        passengerCount > availableSeats.size()) {

                                    System.out.println("Invalid number of passengers!");

                                } else {

                                    ArrayList<Passenger> passengers =
                                            new ArrayList<>();

                                    ArrayList<Integer> seatIds =
                                            new ArrayList<>();

                                    boolean validSelection = true;

                                    for (int i = 1; i <= passengerCount; i++) {

                                        System.out.println("\n===== PASSENGER " + i + " =====");

                                        System.out.print("Enter passenger name: ");
                                        String passengerName = sc.nextLine();

                                        System.out.print("Enter passenger age: ");
                                        int age = sc.nextInt();
                                        sc.nextLine();

                                        System.out.print("Enter gender (MALE/FEMALE/OTHER): ");
                                        String gender = sc.nextLine().toUpperCase();

                                        System.out.print("Enter Seat Number (e.g. A1, B4): ");
                                        String seatNumber = sc.nextLine().trim();

                                        boolean seatFound = false;
                                        int selectedSeatId = -1;

                                        for (Seat seat : availableSeats) {

                                            if (seat.getSeatNumber()
                                                    .equalsIgnoreCase(seatNumber)) {

                                                seatFound = true;
                                                selectedSeatId = seat.getSeatId();
                                                break;
                                            }
                                        }

                                        if (!seatFound || seatIds.contains(selectedSeatId)) {

                                            System.out.println(
                                                    "Invalid or already selected seat number!"
                                            );

                                            validSelection = false;
                                            break;
                                        }

                                        Passenger passenger =
                                                new Passenger(passengerName, age, gender);

                                        passengers.add(passenger);

                                        // Store database Seat ID internally
                                        seatIds.add(selectedSeatId);
                                    }

                                    if (!validSelection) {

                                        System.out.println(
                                                "Booking cancelled due to invalid seat selection!"
                                        );

                                    } else {


											double totalAmount = 0;
											
											for (int seatId : seatIds) {
											
											    for (Seat seat : availableSeats) {
											
											        if (seat.getSeatId() == seatId) {
											
											            FareStrategy fareStrategy = null;
											
											            if (seat.getSeatClass().equalsIgnoreCase("AC")) {
											                fareStrategy = new AcFareStrategy();
											
											            } else if (seat.getSeatClass().equalsIgnoreCase("SLEEPER")) {
											                fareStrategy = new SleeperFareStrategy();
											            }
											
											            if (fareStrategy != null) {
											                totalAmount += fareStrategy.calculateFare(1);
											            }
											
											            break;
											        }
											    }
											}


                                        System.out.println("\n===== FARE DETAILS =====");

                                        System.out.println(
                                                "Number of passengers: " + passengerCount
                                        );

                                        System.out.println(
                                                "Total Amount: ₹" + totalAmount
                                        );

                                        System.out.println("\nProcessing booking...");

                                        Ticket ticket = bookingService.bookTicket(
                                                currentUser.getUserId(),
                                                selectedScheduleId,
                                                passengers,
                                                seatIds,
                                                totalAmount
                                        );

                                        if (ticket != null) {

                                            System.out.println(
                                                    "\n===== BOOKING SUCCESSFUL ====="
                                            );

                                            System.out.println(
                                                    "Your ticket has been booked successfully!"
                                            );

                                            System.out.println("\n" + ticket);

                                        } else {

                                            System.out.println(
                                                    "\n===== BOOKING FAILED ====="
                                            );

                                            System.out.println(
                                                    "Unable to book ticket."
                                            );

                                            System.out.println(
                                                    "Please check seat availability and try again."
                                            );
                                        }
                                    }
                                }
                            }
                        }
                    }

                    break;
                }

                case 6: {

                    System.out.println("\n===== VIEW TICKET =====");

                    if (currentUser == null) {

                        System.out.println("Please login first!");

                    } else {

                        System.out.print("Enter PNR: ");
                        String pnr = sc.nextLine();

                        Ticket ticket = bookingService.getTicketByPNR(pnr);

                        if (ticket != null) {

                            if (ticket.getBooking().getUserId()
                                    == currentUser.getUserId()) {

                                System.out.println("\n" + ticket);

                            } else {

                                System.out.println(
                                        "Access denied! This ticket does not belong to you.");
                            }

                        } else {

                            System.out.println("Ticket not found!");
                        }
                    }

                    break;
                }

                case 7: {

                    System.out.println("\n===== CANCEL TICKET =====");

                    if (currentUser == null) {

                        System.out.println("Please login first!");

                    } else {

                        System.out.print("Enter PNR: ");
                        String cancelPnr = sc.nextLine();

                        Ticket ticket = bookingService.getTicketByPNR(cancelPnr);

                        if (ticket == null) {

                            System.out.println("Ticket not found!");

                        } else if (ticket.getBooking().getUserId()
                                != currentUser.getUserId()) {

                            System.out.println(
                                    "Access denied! This ticket does not belong to you.");

                        } else {

                            boolean cancelled =
                                    bookingService.cancelTicket(cancelPnr);

                            if (cancelled) {

                                System.out.println("Your ticket has been cancelled.");

                            } else {

                                System.out.println("Cancellation failed!");
                            }
                        }
                    }

                    break;
                }
                

				case 8: {
				
				    System.out.println("\n===== MAKE PAYMENT =====");
				
				    if (currentUser == null) {
				
				        System.out.println("Please login first!");
				
				    } else {
				
				        System.out.print("Enter PNR: ");
				        String paymentPnr = sc.nextLine();
				
				        Ticket ticket = bookingService.getTicketByPNR(paymentPnr);
				
				        if (ticket == null) {
				
				            System.out.println("Booking not found!");
				
				        } else if (ticket.getBooking().getUserId()
				                != currentUser.getUserId()) {
				
				            System.out.println(
				                "Access denied! This booking does not belong to you."
				            );
				
				        } else {
				
				            System.out.println("\nSelect Payment Method:");
				            System.out.println("1. UPI");
				            System.out.println("2. Debit/Credit Card");
				            System.out.println("3. Net Banking");
				            System.out.print("Enter choice: ");
				
				            int paymentChoice = sc.nextInt();
				            sc.nextLine();
				
				            PaymentMethod paymentMethod;
				
				            try {
				
				                paymentMethod =
				                    PaymentMethodFactory.getPaymentMethod(paymentChoice);
				
				            } catch (IllegalArgumentException e) {
				
				                System.out.println("Invalid payment method!");
				                break;
				            }
				
				            Payment payment = paymentService.processPayment(
				                paymentPnr,
				                paymentMethod
				            );
				
				            if (payment != null) {
				
				                System.out.println("\n===== PAYMENT RECEIPT =====");
				                System.out.println(payment);
				
				                // Generate PDF only after successful payment
				                if (payment.getPaymentStatus().equals("SUCCESS")) {
				
				                    TrainSchedule schedule =
				                        trainScheduleDao.getTrainScheduleById(
				                            ticket.getBooking().getScheduleId()
				                        );
				
				                    if (schedule != null) {
				
				                        Ticket confirmedTicket =
				                            bookingService.getTicketByPNR(paymentPnr);
				
				                        String pdfPath =
				                            ticketPdfHandler.generateTicketPDF(
				                                confirmedTicket,
				                                schedule
				                            );
				
				                        if (pdfPath != null) {
				
				                            System.out.println(
				                                "Your PDF ticket is ready: " + pdfPath
				                            );
				                        }
				
				                    } else {
				
				                        System.out.println(
				                            "Could not retrieve train schedule for PDF."
				                        );
				                    }
				                }
				            }
				        }
				    }
				
				    break;
				}



                case 9:

                    System.out.println("\n===== PAYMENT DETAILS =====");

                    System.out.print("Enter Booking ID: ");
                    int bookingId = sc.nextInt();
                    sc.nextLine();

                    Payment paymentDetails =
                            paymentService.getPaymentByBookingId(bookingId);

                    if (paymentDetails != null) {

                        System.out.println("\n===== PAYMENT RECEIPT =====");
                        System.out.println(paymentDetails);

                    } else {

                        System.out.println("No payment record found!");
                    }

                    break;

                case 10:
                	if (currentUser == null) {

                        System.out.println("No user is currently logged in!");

                    } else {

                        System.out.println("Goodbye, " + currentUser.getName());

                        currentUser = null;

                        System.out.println("Logout successful!");
                    }

                    break;
                    
                case 11:

                    running = false;

                    System.out.println("Thank you for using Railway Reservation System!");

                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }

        sc.close();
    }
    
    public static void showAdminMenu() {

        System.out.println("\n====================================");
        System.out.println("          ADMIN DASHBOARD");
        System.out.println("====================================");
        System.out.println("1. View All Trains");
        System.out.println("2. Add Train");
        System.out.println("3. Update Train");
        System.out.println("4. Reserved");
        System.out.println("5. Manage Schedules");
        System.out.println("6. View All Bookings");
        System.out.println("7. View All Users");
        System.out.println("8. Export Bookings to CSV");
        System.out.println("9. Logout");
        System.out.println("------------------------------------");
        System.out.print("Enter your choice: ");
    }
    public static void showScheduleMenu() {

        System.out.println("\n====================================");
        System.out.println("         MANAGE SCHEDULES");
        System.out.println("====================================");
        System.out.println("1. View All Schedules");
        System.out.println("2. Add New Schedule");
        System.out.println("3. Back to Admin Dashboard");
        System.out.println("------------------------------------");
        System.out.print("Enter your choice: ");
    }
}