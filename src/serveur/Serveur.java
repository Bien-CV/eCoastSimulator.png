package serveur;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;




public class Serveur{

	private final static int port = 8090;
	private static Donnees bdd = new Donnees();


	public static void main(String[] argv) {
		bdd.initObjets();
		VenteImpl vente = null;
		try {
			vente = new VenteImpl(bdd.getListeObjets());
			System.out.println("@ IP : " + InetAddress.getLocalHost());
		} catch (UnknownHostException | RemoteException e1) {
			e1.printStackTrace();
		}
		
		try {	
			LocateRegistry.createRegistry(port);
			Naming.bind("//localhost:"+port+"/enchere", vente);
		} catch(RemoteException |  MalformedURLException e){
			e.printStackTrace();
		} catch(AlreadyBoundException e) {
			
		}
		
		while(true){
			//On recrée une nouvelle vente
			if(vente.getEtatVente() == EtatVente.TERMINE){
				bdd.initObjets();
				try {
					vente = new VenteImpl(bdd.getListeObjets());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
	}	
}
	
