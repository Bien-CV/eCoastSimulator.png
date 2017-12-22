package client;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class HandleClient {
	private final static int port = 8091;
	

	public static void main(String[] argv) {
		
		Client client= null;
		affichageDeBienvenue();
		
		//TODO:lancement interface
		
		//Attendre saisie du pseudo par le client
		//TODO: Utiliser un nom de client saisi via le GUI
		String pseudo="nomDuClient";
		
		
		
		//Mise en réseau
		try {	
			
			client=new Client(pseudo);
			
			//TODO:Connect serveur
			client.connexionServeur();
			
			LocateRegistry.createRegistry(port);
			Naming.bind(client.getAdresseClient(), client);
		} catch(RemoteException |  MalformedURLException e){
			e.printStackTrace();
		} catch(AlreadyBoundException e)	{
			//Exception ignorée
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
	
