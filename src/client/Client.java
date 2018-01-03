// TODO : les todos

package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import commun.ClientInfo;
import commun.DebugTools;
import commun.DejaConnecteException;
import commun.IClient;
import commun.IHotelDesVentes;
import commun.Objet;
import commun.PasCreateurException;
import commun.PseudoDejaUtiliseException;
import commun.SalleDeVenteInfo;
import commun.ServeurInfo;
import commun.Message;

public class Client extends UnicastRemoteObject implements IClient {

	private static final long serialVersionUID = 1L;
	public ServeurInfo serveur;
	
	public String getAdresseClient() {
		return myClientInfos.getAdresseClient();
	}

	private IHotelDesVentes hdv=null;
	private HashMap<UUID, Objet> ventesSuivies;
	private HashMap<UUID, SalleDeVenteInfo> mapInfosSalles;
	// liste des messages postés dans les différentes salles de ventes suivies
	private HashMap<UUID, List<Message>> listesMessages=new HashMap<UUID, List<Message>>();

	public ClientInfo myClientInfos;
	
	private UUID idSalleObservee;
	private ECoastSimulatorGUI interfaceClient;

	public void setIdSalleObservee(UUID idSalleObservee) {
		this.idSalleObservee = idSalleObservee;
	}


	public Client(ECoastSimulatorGUI gui, String nom,String ipServeurSaisi, String portServeurSaisi) throws RemoteException {
		super();
		//TODO: Récupérer la vraie IP du client
		this.myClientInfos = new ClientInfo(UUID.randomUUID(),nom, "127.0.0.1", "8092");
		
		interfaceClient=gui;
		serveur= new ServeurInfo(ipServeurSaisi,portServeurSaisi);
		DebugTools.d(serveur.getAdresseServeur());
		connexionServeur();
		this.ventesSuivies = new HashMap<UUID, Objet>();
		
		
	}

	public void connexionServeur() {
		DebugTools.d("Tentative d'initialisation de hdv à l'adresse:"+serveur.getAdresseServeur());
		int nombreDeConnexions = 0;
		while(hdv==null && nombreDeConnexions < 20) {
			try {
				hdv = (IHotelDesVentes) Naming.lookup(serveur.getAdresseServeur());
				System.out.println("Connexion au serveur " + serveur.getAdresseServeur() + " reussi.");
			} catch (Exception e) {
				System.out.println("Connexion au serveur " + serveur.getAdresseServeur() + " impossible.");
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			nombreDeConnexions++;
		}
	}
	
	// notification au serveur de l'arrivée d'un nouveau client
	public void connexion () throws PseudoDejaUtiliseException, DejaConnecteException {
		try {
			// login + récupération de la liste des salles existantes.
			mapInfosSalles = hdv.login(this.myClientInfos);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (PseudoDejaUtiliseException pdue) {
			System.out.println("Pseudo déjà utilisé !");
			deconnexion();
			throw pdue;
		} catch (DejaConnecteException dce) {
			System.out.println("Deja connecté au serveur !");
			deconnexion();
			throw dce;
		}
	}
	
	// notification au serveur du départ d'un client
	public void deconnexion () {
		try {
			hdv.logout(myClientInfos);
			idSalleObservee = null;
			if(ventesSuivies != null) ventesSuivies.clear();
			if(mapInfosSalles != null) mapInfosSalles.clear();
			if(listesMessages != null) listesMessages.clear();
			hdv = null;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		// TODO : fermeture de l'application ?
	}
	
	// notification au serveur de l'ajout d'un nouvel objet a vendre dans une salle donnée.
	public void nouvelleSoumission(String nom, String description, float prix) throws RemoteException {
		Objet nouveau = new Objet(nom, description, prix,myClientInfos.getNom());
		//ajout de l'objet par le hdv
		try {
			hdv.ajouterObjet(nouveau, getIdSalleObservee(), getId());
		} catch (PasCreateurException e) {
			// TODO affichage utilisateur en cas d'ajout dans une salle qu'il a pas créé ?
			e.printStackTrace();
		}
		//print des informations sur l'ajout
	}
	// notification au serveur de l'ajout d'un nouvel objet a vendre dans une salle donnée.
	public void nouvelleSalle(String nom, String description, float f) throws RemoteException {
		Objet nouveau = new Objet(nom, description, f,myClientInfos.getNom());
		System.out.println(""+myClientInfos.getAdresseClient()+" "+myClientInfos.getNom()+" "+getId()+" "+nouveau+" "+myClientInfos.getNom()+" \n");
		hdv.creerSalle(myClientInfos, nouveau, "Salle de "+myClientInfos.getNom());
	}
	// notification au serveur de l'ajout d'un nouvel objet a vendre dans une salle donnée.
	public void pingServeur() throws RemoteException {
		if ( hdv==null) System.out.print("Hdv null : connexion pas effectué\n");
		hdv.ping();
	}

	public static void main(String[] argv) {
		try {
			//start IHM
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public IHotelDesVentes getServeur() {
		return hdv;
	}

	public void setServeur(IHotelDesVentes serveur) {
		this.hdv = serveur;
	}
	
	// notification au serveur de la fermeture d'une salle par le client
	public void fermetureSalle(UUID idSDV) {
		try {
			hdv.fermerSalle(idSDV, getId());
		} catch (PasCreateurException e) {
			// impossible de fermer la salle si on en est pas le créateur
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	// notification au serveur de la volonté de rejoindre unje nouvelle salle de vente.
	// ajout du nouvel objet suivit en cas de réussite.
	public void rejoindreSalle(UUID idSalle) {
		try {
			Objet obj = hdv.rejoindreSalle(idSalle, this.myClientInfos);
			ventesSuivies.put(idSalle, obj);
		} catch (RemoteException e) {
			e.printStackTrace();
			// TODO : affichage d'un message d'erreur par l'IHM
		}
		try {
			listesMessages.put(idSalle, hdv.getMessagesSalle(idSalle));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public HashMap<UUID, Objet> getVentesSuivies() {
		return ventesSuivies;
	}

	@Override
	public void nouveauMessage(UUID idSalle, Message message) {
		listesMessages.get(idSalle).add(message);
		interfaceClient.updateChat();
		// TODO : éventuellement supprimer les plus anciens messages au dela d'un certain nombre.
	}

	@Override
	public void notifModifObjet(UUID idSalle, Objet objet) {
		ventesSuivies.put(idSalle, objet);
		mapInfosSalles.get(idSalle).setObjCourrant(objet);
		interfaceClient.updateObjetSalleCourante();
	}

	@Override
	public void notifFermetureSalle(UUID idSalle) {
		ventesSuivies.remove(idSalle);
		mapInfosSalles.remove(idSalle);
		//TODO: devrait être travaillé
		if (idSalle.equals(idSalleObservee)) {
			idSalleObservee = null;
		}
		interfaceClient.actualiserInterface();
	}

	@Override
	public void notifNouvelleSalle(UUID idsdv, SalleDeVenteInfo sdvi) {
		mapInfosSalles.put(idsdv, sdvi);
		System.out.println("Une nouvelle salle a été crée");
		interfaceClient.updateListeDesSallesServeur();
	}

	public UUID getIdSalleObservee() {
		return this.idSalleObservee;
	}

	public HashMap<UUID, SalleDeVenteInfo> getMapInfosSalles() {
		return mapInfosSalles;
	}

	public void setMapInfosSalles(HashMap<UUID, SalleDeVenteInfo> mapInfosSalles) {
		this.mapInfosSalles = mapInfosSalles;
	}
	
	public SalleDeVenteInfo[] getTabInfosSalles() {
		if (mapInfosSalles != null) {
			Collection<SalleDeVenteInfo> vals = mapInfosSalles.values();
			SalleDeVenteInfo[] tab = new SalleDeVenteInfo[vals.size()];
			int i = 0;
			for (SalleDeVenteInfo sdvi : vals) {
				tab[i] = sdvi;
				++i;
			}
			return tab;
		}
		else return new SalleDeVenteInfo[0];
	}
	
	public SalleDeVenteInfo[] getTabVentesSuivies() {
		if (ventesSuivies != null) {
			Set<UUID> keys = ventesSuivies.keySet();
			SalleDeVenteInfo[] tab = new SalleDeVenteInfo[keys.size()];
			int i = 0;
			for (UUID idSalle : keys) {
				tab[i] = mapInfosSalles.get(idSalle);
				++i;
			}
			return tab;
		}
		else return new SalleDeVenteInfo[0];
	}

	public void quitterSalle(UUID idSalleAQuitter) {
		try {
			hdv.quitterSalle( getId(),idSalleAQuitter);
			ventesSuivies.remove(idSalleAQuitter);
			idSalleObservee = null;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}


	public UUID getId() {
		 
		return myClientInfos.getId();
	}


	public String getPortClient() {
		return myClientInfos.getPort();
	}


	public void setPortClient(String portClient) {
		myClientInfos.setPort(portClient);
		
	}


	public void bindClient() throws PseudoDejaUtiliseException, DejaConnecteException{
		Boolean flagRegistreOkay=false;
		int portClient=Integer.parseInt( getPortClient() );
		while(!flagRegistreOkay) {
			try {
				LocateRegistry.createRegistry(portClient);
				DebugTools.d("Registre créé au port "+portClient);
				flagRegistreOkay=true;
				setPortClient(Integer.toString(portClient));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (RemoteException e1) {
				
				
				DebugTools.d("Port "+portClient+" non disponible.");
			}
			portClient++;
		}
		DebugTools.d("Tentative de bind à "+getAdresseClient());
		
		try {
			try {
				Naming.rebind(getAdresseClient(), this);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DebugTools.d("bind à "+getAdresseClient()+" effectué.");
		} catch (RemoteException e1) {
			DebugTools.d("bind à "+getAdresseClient()+" échoué.");			
			e1.printStackTrace();
		}
		try {
			connexion();
		} catch (PseudoDejaUtiliseException pdue) {
			throw pdue;
		} catch (DejaConnecteException dce) {
			throw dce;
		}
	}


	public HashMap<UUID, List<Message>> getListesMessages() {
		return listesMessages;
	}


	public void setListesMessages(HashMap<UUID, List<Message>> listesMessages) {
		this.listesMessages = listesMessages;
	}


}
