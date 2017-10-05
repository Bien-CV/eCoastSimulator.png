package serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public interface IHotelDesVentes extends Remote {

	public List<SalleDeVente> getListeSalles() throws RemoteException;
	
	public SalleDeVente rejoindreSalle(UUID id) throws RemoteException;
	
	public SalleDeVente creerSalle(UUID id,Objet o) throws RemoteException;
	
	public void login(String nomUtilisateur);
	
	public void logout();
}
