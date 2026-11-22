package railway.main;

import railway.dao.ScheduleDao;
import railway.model.Schedule;

import java.util.ArrayList;

public class TestScheduleDao {

    public static void main(String[] args) {

        ScheduleDao dao = new ScheduleDao();

        ArrayList<Schedule> schedules = dao.getAllSchedules();

        for (Schedule schedule : schedules) {
            System.out.println(schedule);
        }
    }
}