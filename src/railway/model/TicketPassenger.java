package railway.model;

public class TicketPassenger {

    private int passengerId;
    private String name;
    private int age;
    private String gender;
    private String seatNumber;
    private String seatClass;

    public TicketPassenger(int passengerId, String name, int age, String gender, String seatNumber, String seatClass) {

        this.passengerId = passengerId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public String getSeatClass() {
        return seatClass;
    }

    @Override
    public String toString() {
        return "Passenger: " + name + " | Age: " + age + " | Gender: " + gender + " | Seat: " + seatNumber + " | Class: " + seatClass;
    }
}