package serveur;

import java.util.Date;
import java.util.TimerTask;
import java.util.UUID;

public class TaskObjetSuivantClient extends TimerTask {
	UUID idSdv;
	HotelDesVentes parent;
	
	public TaskObjetSuivantClient(HotelDesVentes hdv, UUID sdv) {
		idSdv=sdv;
		parent=hdv;
	}
	
	@Override
	  public void run() {
	    
		parent.getSalleById(idSdv).venteSuivante();
		
	  }
}