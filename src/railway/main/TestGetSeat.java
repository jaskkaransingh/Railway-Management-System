package railway.main;

import railway.dao.SeatDao;
import railway.model.Seat;

public class TestGetSeat {

    public static void main(String[] args) {

        SeatDao dao = new SeatDao();

        Seat seat = dao.getSeatById(1);

        if (seat != null) {

            System.out.println("Seat found!");
            System.out.println(seat);

        } else {

            System.out.println("Seat not found!");
        }
    }
}