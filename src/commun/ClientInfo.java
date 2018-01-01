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
	String ip;
	String port;
	
	public String getAdresseClient() {
		return "//"+ip+":"+port+"/"+id;
	}
	//Donner une durée de vie à un client avec un Timestamp
	//List<UUID> currentRooms;

	public ClientInfo(UUID id2, String nomUtilisateur,String ipClient,String portClient) {
		id=id2;
		nom=nomUtilisateur;
		ip=ipClient;
		port=portClient;
	}

	public UUID getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
}
