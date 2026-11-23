package railway.main;

import railway.dao.SeatDao;

public class TestBookSeat {

    public static void main(String[] args) {

        SeatDao dao = new SeatDao();

        int seatId = 1;

        boolean booked = dao.bookSeat(seatId);

        if (booked) {
            System.out.println("Seat booked successfully!");
        } else {
            System.out.println("Seat booking failed!");
        }
    }
}