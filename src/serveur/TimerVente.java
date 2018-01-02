package serveur;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

// Lancement automatique d'une nouvelle vente lorsque la précédente est arrivée à expiration.
public class TimerVente {
	private static Date dateDeFin;
	private static UUID idSdv;
	private static HotelDesVentes parent;

	public TimerVente(Date d,HotelDesVentes hdv, UUID sdv) {
		dateDeFin=d;
		parent=hdv;
		idSdv=sdv;
	}

	public static void main(final String[] args) {
		TimerTask timerTask = new TaskObjetSuivantClient(parent, idSdv);
		Timer timer = new Timer(true);
		timer.schedule(timerTask,dateDeFin);
	}
}
