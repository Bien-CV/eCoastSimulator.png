package client;

import java.rmi.Remote;
import java.util.UUID;
import commun.*;

public interface IClient extends Remote {
	
	void fermetureSalle (UUID idSDV);
	
	void notifyNouvelleEnchere (float nouveauPrix, String gagnant, UUID idObjet);
	
}
