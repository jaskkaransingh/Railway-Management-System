package railway.main;

import railway.dao.PassengerDao;
import railway.model.Passenger;

public class TestPassengerDao {

    public static void main(String[] args) {

        PassengerDao dao = new PassengerDao();

        Passenger passenger = new Passenger(
                "Jaskaran Singh",
                20,
                "MALE"
        );

        int passengerId = dao.addPassenger(passenger);

        if (passengerId != -1) {
            System.out.println("Passenger added successfully!");
            System.out.println("Passenger ID: " + passengerId);
        } else {
            System.out.println("Failed to add passenger.");
        }
    }
}