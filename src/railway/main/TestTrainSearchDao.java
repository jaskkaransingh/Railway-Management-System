package railway.main;

import railway.dao.TrainScheduleDao;
import railway.model.TrainSchedule;

import java.time.LocalDate;
import java.util.ArrayList;

public class TestTrainSearchDao {

    public static void main(String[] args) {

        TrainScheduleDao dao = new TrainScheduleDao();

        ArrayList<TrainSchedule> trains =
                dao.searchTrains(
                        "New Delhi",
                        "Mumbai",
                        LocalDate.of(2026, 12, 1)
                );

        if (trains.isEmpty()) {
            System.out.println("No trains available!");
        } else {

            System.out.println("Available Trains:\n");

            for (TrainSchedule train : trains) {
                System.out.println(train);
            }
        }
    }
}