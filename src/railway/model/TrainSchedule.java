package railway.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class TrainSchedule {

    private int scheduleId;
    private int trainId;
    private String trainNumber;
    private String trainName;
    private String source;
    private String destination;
    private LocalDate journeyDate;
    private LocalTime departureTime;
    private LocalTime arrivalTime;

    public TrainSchedule(int scheduleId, int trainId, String trainNumber, String trainName,  String source, String destination,
            LocalDate journeyDate, LocalTime departureTime, LocalTime arrivalTime) {

        this.scheduleId = scheduleId;
        this.trainId = trainId;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
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

    public String getTrainNumber() {
        return trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
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
        return trainNumber + " | " + trainName
                + " | " + source + " -> " + destination
                + " | " + journeyDate
                + " | Departure: " + departureTime
                + " | Arrival: " + arrivalTime;
    }
}