package serveur;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


public class Serveur {

	private final static int port = 8090;
	

	public static void main(String[] argv) {
		
		HotelDesVentes hdv= null;
		affichageDeBienvenue();
		
		//Mise en réseau
		try {	
			hdv=new HotelDesVentes();
			LocateRegistry.createRegistry(port);
			Naming.bind("//localhost:"+port+"/hoteldesventes", hdv);
		} catch(RemoteException |  MalformedURLException e){
			//TODO
			e.printStackTrace();
		}catch(AlreadyBoundException e)	{
			//Exception ignorée
		}
	}


	private static void affichageDeBienvenue() {
		// TODO Auto-generated method stub
		try {
			System.out.println("@ IP : " + InetAddress.getLocalHost());
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
		
	}
	
}
	
