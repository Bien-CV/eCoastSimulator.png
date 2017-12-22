package commun;

import java.util.Date;
import java.util.TimerTask;

public class TaskObjetSuivant extends TimerTask {
	@Override
	  public void run() {
	    
	    try {
	      Thread.sleep(5000);
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }
	    System.out.println(new Date()+": Il est temps de passer à la vente suivante" );
	  }
}


public class MaTache  {
  
}