package railway.main;

import railway.dao.TrainScheduleDao;
import railway.model.TrainSchedule;

import java.util.ArrayList;

public class TestTrainScheduleDao {

    public static void main(String[] args) {

        TrainScheduleDao dao = new TrainScheduleDao();

        ArrayList<TrainSchedule> trainSchedules =
                dao.getAllTrainSchedules();

        for (TrainSchedule ts : trainSchedules) {
            System.out.println(ts);
        }
    }
}