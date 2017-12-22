package commun;

import java.io.Serializable;
//import java.util.List;
import java.util.UUID;

//TODO: On doit redéfinir equals, pour qu'un listeClients.remove(ClientInfo)
//fonctionne, entre autres.

public class ClientInfo  implements Serializable {

	private static final long serialVersionUID = 4979345072204388593L;
	UUID id;
	String nom;
	//Donner une durée de vie à un client avec un Timestamp
	//List<UUID> currentRooms;

	public ClientInfo(UUID id2, String nomUtilisateur) {
		id=id2;
		nom=nomUtilisateur;
	}

	public UUID getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}
}
