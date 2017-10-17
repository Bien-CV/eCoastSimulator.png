package client;

import java.rmi.Remote;
import java.util.UUID;
import commun.*;

public interface IClient extends Remote {
	
	void fermetureSalle (UUID idSDV);
	
	void objetVendu (Objet objetSuivant);
	
	void surenchere (float nouveauPrix, String gagnant);
	
}
