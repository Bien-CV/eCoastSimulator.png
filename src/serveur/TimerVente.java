package commun;

import java.util.Timer;
import java.util.TimerTask;

public class TimerVente {
	private Date dateDeFin;
	private UUID idSdv;
	
	public TimerVente(Date d) {
		dateDeFin=d;
	}
	
	public static void main(final String[] args) {
	    TimerTask timerTask = new TaskObjetSuivant();
	    Timer timer = new Timer(true);
	    timer.schedule(timerTask(idSdv),dateDeFin);
	  }
}
