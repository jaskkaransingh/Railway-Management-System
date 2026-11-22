package railway.main;

import railway.dao.TrainDao;
import railway.model.Train;
import java.util.ArrayList;
public class TrainTestDao {

	public static void main(String[] args) {
		
		TrainDao dao = new TrainDao();
		
		ArrayList<Train> trains = dao.getAllTrains();
		
		for(Train train : trains) {
			System.out.println(train);
		}

	}

}
