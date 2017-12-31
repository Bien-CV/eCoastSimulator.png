//OK
package serveur;


import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;

public class Serveur {

	private final static int port = 8090;
	

	public static void main(String[] argv) {
		
		HotelDesVentes hdv= null;
		affichageDeBienvenue();
		
		//Mise en réseau
		try {	
			//hdv est l'objet à rendre disponible au client
			hdv=new HotelDesVentes();
			Registry r = LocateRegistry.getRegistry(port);
			if (r==null) {
				r=LocateRegistry.createRegistry(port);
			}
			System.out.println("Serveur: Registre créé au port "+port+"\n");
			Naming.rebind("//127.0.0.1:"+port+"/hoteldesventes", hdv);
		} catch(RemoteException |  MalformedURLException e){
			e.printStackTrace();
		}
	}


	private static void affichageDeBienvenue() {
		try {
			System.out.println("@ IP : " + InetAddress.getLocalHost());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
	}
	
}
	
