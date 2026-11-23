package railway.main;

import railway.dao.SeatDao;
import railway.model.Seat;

import java.util.ArrayList;

public class TestSeatDao {

    public static void main(String[] args) {

        SeatDao dao = new SeatDao();

        int scheduleId = 1;

        ArrayList<Seat> seats =
                dao.getAvailableSeats(scheduleId);

        if (seats.isEmpty()) {

            System.out.println("No seats available!");

        } else {

            System.out.println("Available Seats:\n");

            for (Seat seat : seats) {
                System.out.println(seat);
            }

            System.out.println("\nTotal available seats: "
                    + seats.size());
        }
    }
}