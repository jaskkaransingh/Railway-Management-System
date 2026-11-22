package railway.model;

public class Train {
	
	private int trainId;
	private String trainNumber;
	private String trainName;
	private String source;
	private String destination;
	
	public Train(int trainId, String trainNumber, String trainName, String source, String destination) {
		this.trainId = trainId;
		this.trainName = trainName;
		this.trainNumber = trainNumber;
		this.destination = destination;
		this.source = source;
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
	@Override
	public String toString() {
		return trainNumber + " | " + trainName + " | from " + source + " to " + destination;
	}
}
