package railway.model;

public class Passenger {

    private int passengerId;
    private String name;
    private int age;
    private String gender;

    public Passenger(int passengerId, String name, int age, String gender) {
        this.passengerId = passengerId;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public Passenger(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
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

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    @Override
    public String toString() {
        return "Passenger ID: " + passengerId + " | Name: " + name + " | Age: " + age + " | Gender: " + gender;
    }
}