//OK
package serveur;


import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;

import commun.DebugTools;

public class Serveur {
	private static HotelDesVentes hdv= null;
	private static int port = 8090;
	private static Registry r = null;
	private static String adresseServeur="rmi://localhost:"+port+"/hoteldesventes";
	
	public static void main(String[] argv) {
		System.setProperty("java.rmi.server.hostname", adresseServeur);
		initHdv();
		initRegistry();
		bindServeur();
	}

	private static void bindServeur() {
		try {
			
			DebugTools.d("Adresse serveur= "+adresseServeur);
			Naming.rebind(adresseServeur, hdv);
		} catch (AccessException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}

	private static void initRegistry() {
		//XXX: Get un registry fait bugger !
		//getRegistry();
		createRegistryIfNull();
		
	}


	private static void getRegistry() {
		try {
			
			r = LocateRegistry.getRegistry(port);
			
			if (r!=null) {
				DebugTools.d("Registry existant trouvé :");
				DebugTools.d(r.toString());
			}else {
				DebugTools.d("Pas de registry pré-existant.");
			}
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}

	private static void createRegistryIfNull() {
		try {	
			if (r==null) {
				r=LocateRegistry.createRegistry(port);
				System.out.println("Serveur: Registre créé au port "+port+" :");
				DebugTools.d(r.toString());
			}
		} catch( ExportException e )	{
			//Exception ignorée
		} catch(RemoteException  e){
			e.printStackTrace();
		}
		
	}

	private static void initHdv() {
		try {
			hdv=new HotelDesVentes();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
}
	
