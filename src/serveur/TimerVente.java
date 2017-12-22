package serveur;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class TimerVente {
	private Date dateDeFin;
	private UUID idSdv;
	
	public TimerVente(Date d) {
		dateDeFin=d;
	}
	
	public static void main(final String[] args) {
	    TimerTask timerTask = new TaskObjetSuivantClient();
	    Timer timer = new Timer(true);
	    timer.schedule(timerTask(idSdv),dateDeFin);
	  }
}
