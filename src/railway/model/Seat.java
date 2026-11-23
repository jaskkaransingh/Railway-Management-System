package railway.model;

public class Seat {

    private int seatId;
    private int scheduleId;
    private String seatNumber;
    private String seatClass;
    private String status;

    public Seat(int seatId, int scheduleId, String seatNumber, String seatClass, String status) {

        this.seatId = seatId;
        this.scheduleId = scheduleId;
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.status = status;
    }

    
    public int getSeatId() {
        return seatId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Seat: " + seatNumber + " | Class: " + seatClass + " | Status: " + status;
    }
}