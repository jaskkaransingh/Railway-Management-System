package railway.main;

import railway.dao.PassengerDao;
import railway.model.Passenger;

public class TestGetPassenger {

    public static void main(String[] args) {

        PassengerDao dao = new PassengerDao();

        Passenger passenger = dao.getPassengerById(1);

        if (passenger != null) {
            System.out.println("Passenger found!");
            System.out.println(passenger);
        } else {
            System.out.println("Passenger not found!");
        }
    }
}