package railway.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Schedule {

    private int scheduleId;
    private int trainId;
    private LocalDate journeyDate;
    private LocalTime departureTime;
    private LocalTime arrivalTime;

    
    public Schedule(int scheduleId, int trainId,LocalDate journeyDate,LocalTime departureTime,LocalTime arrivalTime) {

        this.scheduleId = scheduleId;
        this.trainId = trainId;
        this.journeyDate = journeyDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    
    public int getScheduleId() {
        return scheduleId;
    }

    public int getTrainId() {
        return trainId;
    }

    public LocalDate getJourneyDate() {
        return journeyDate;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }
    
    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    
    @Override
    public String toString() {
        return "Schedule ID: " + scheduleId + " | Train ID: " + trainId + " | Date: " + journeyDate + " | Departure: " + departureTime
                + " | Arrival: " + arrivalTime;
    }
}