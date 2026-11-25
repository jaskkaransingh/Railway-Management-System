package railway.strategy;

public class SleeperFareStrategy implements FareStrategy {

    @Override
    public double calculateFare(int passengerCount) {
        return 1000 * passengerCount;
    }
}