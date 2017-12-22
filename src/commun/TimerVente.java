package commun;

import java.util.Timer;
import java.util.TimerTask;

public class TimerVente {
	private Date dateDeFin;
	
	public TimerVente(Date d) {
		dateDeFin=d;
	}
	
	public static void main(final String[] args) {
	    TimerTask timerTask = new TaskObjetSuivant();
	    Timer timer = new Timer(true);
	    timer.schedule(timerTask,dateDeFin);
	    System.out.println("Lancement execution");

	    try {
	      Thread.sleep(20000);
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }
	    timer.cancel();

	    System.out.println("Abandon execution");
	    try {
	      Thread.sleep(20000);
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }
	  }
}

  
