package serveur;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class TimerVente {
	private static Date dateDeFin;
	private static UUID idSdv;
	private HotelDesVentes parent;
	
	public TimerVente(Date d,HotelDesVentes hdv) {
		dateDeFin=d;
		parent=hdv;
	}
	
	public static void main(final String[] args) {
	    TimerTask timerTask = new TaskObjetSuivantClient(null, idSdv);
	    Timer timer = new Timer(true);
	    timer.schedule(timerTask,dateDeFin);
	  }
}
