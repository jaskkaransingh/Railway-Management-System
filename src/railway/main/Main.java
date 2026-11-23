package railway.main;

import java.util.Scanner;
import railway.dao.TrainScheduleDao;
import railway.model.TrainSchedule;

import java.sql.SQLException;
import java.time.LocalDate;
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


public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static TrainScheduleDao trainScheduleDao = new TrainScheduleDao();
    private static SeatDao seatDao = new SeatDao();
    private static BookingService bookingService = new BookingService();
    private static PaymentService paymentService = new PaymentService();
    private static UserService userService = new UserService();
    private static User currentUser = null;

    public static void main(String[] args) {

        boolean running = true;

        while (running) {
        	if (currentUser != null) {
        	    System.out.println("Logged in as: " + currentUser.getName());
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

                            for (TrainSchedule schedule : schedules) {
                                System.out.println(schedule);
                            }

                            System.out.print("\nEnter Schedule ID: ");
                            int selectedScheduleId = sc.nextInt();
                            sc.nextLine();

                            System.out.println("\n===== AVAILABLE SEATS =====");

                            List<Seat> availableSeats =
                                    seatDao.getAvailableSeats(selectedScheduleId);

                            if (availableSeats.isEmpty()) {

                                System.out.println("No seats available for this schedule!");

                            } else {

                                for (Seat seat : availableSeats) {
                                    System.out.println(seat);
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

                                        System.out.print("Enter Seat ID: ");
                                        int seatId = sc.nextInt();
                                        sc.nextLine();

                                        boolean seatFound = false;

                                        for (Seat seat : availableSeats) {

                                            if (seat.getSeatId() == seatId) {

                                                seatFound = true;
                                                break;
                                            }
                                        }

                                        if (!seatFound || seatIds.contains(seatId)) {

                                            System.out.println(
                                                    "Invalid or already selected seat!");

                                            validSelection = false;
                                            break;
                                        }

                                        Passenger passenger =
                                                new Passenger(passengerName, age, gender);

                                        passengers.add(passenger);
                                        seatIds.add(seatId);
                                    }

                                    if (!validSelection) {

                                        System.out.println(
                                                "Booking cancelled due to invalid seat selection!");

                                    } else {

                                        double totalAmount = 0;

                                        for (int seatId : seatIds) {

                                            for (Seat seat : availableSeats) {

                                                if (seat.getSeatId() == seatId) {

                                                    if (seat.getSeatClass()
                                                            .equalsIgnoreCase("AC")) {

                                                        totalAmount += 1500;

                                                    } else if (seat.getSeatClass()
                                                            .equalsIgnoreCase("SLEEPER")) {

                                                        totalAmount += 1000;
                                                    }

                                                    break;
                                                }
                                            }
                                        }

                                        System.out.println("\n===== FARE DETAILS =====");
                                        System.out.println(
                                                "Number of passengers: " + passengerCount);

                                        System.out.println(
                                                "Total Amount: ₹" + totalAmount);

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
                                                    "\n===== BOOKING SUCCESSFUL =====");

                                            System.out.println(
                                                    "Your ticket has been booked successfully!");

                                            System.out.println("\n" + ticket);

                                        } else {

                                            System.out.println(
                                                    "\n===== BOOKING FAILED =====");

                                            System.out.println(
                                                    "Unable to book ticket.");

                                            System.out.println(
                                                    "Please check seat availability and try again.");
                                        }
                                    }
                                }
                            }
                        }
                    }

                    break;
                }

                case 6:

                    System.out.println("\n===== VIEW TICKET =====");

                    System.out.print("Enter PNR: ");
                    String pnr = sc.nextLine();

                    Ticket ticket = bookingService.getTicketByPNR(pnr);

                    if (ticket != null) {
                        System.out.println("\n" + ticket);
                    } else {
                        System.out.println("Ticket not found!");
                    }

                    break;

                case 7:

                    System.out.println("\n===== CANCEL TICKET =====");

                    System.out.print("Enter PNR: ");
                    String cancelPnr = sc.nextLine();

                    boolean cancelled = bookingService.cancelTicket(cancelPnr);

                    if (cancelled) {
                        System.out.println("Your ticket has been cancelled.");
                    } else {
                        System.out.println("Cancellation failed!");
                    }

                    break;
                case 8:

                    System.out.println("\n===== MAKE PAYMENT =====");

                    System.out.print("Enter PNR: ");
                    String paymentPnr = sc.nextLine();

                    Payment payment = paymentService.processPayment(
                            paymentPnr,
                            true
                    );

                    if (payment != null) {
                        System.out.println("\n===== PAYMENT RECEIPT =====");
                        System.out.println(payment);
                    }

                    break;

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
}