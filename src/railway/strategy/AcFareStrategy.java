package railway.strategy;

public class AcFareStrategy implements FareStrategy {

    @Override
    public double calculateFare(int passengerCount) {
        return 1500 * passengerCount;
    }
}