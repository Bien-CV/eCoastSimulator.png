// TODO : les todos

package client;

import java.awt.event.MouseAdapter;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
	private HashMap<UUID, List<Message>> listesMessages;

	public ClientInfo myClientInfos;
	
	private UUID idSalleObservee;
	private String nomSalleObservee;
	private UUID idObjetObserve;
	private String nomObjetObserve;
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			nombreDeConnexions++;
		}
	}
	
	// notification au serveur de l'arrivée d'un nouveau client
	public void connexion () {
		try {
			// login + récupération de la liste des salles existantes.
			// TODO : mettre a jour l'IHM avec la liste susmentionnée.
			mapInfosSalles = hdv.login(this.myClientInfos);
			
		} catch (RemoteException | PseudoDejaUtiliseException | DejaConnecteException e) {
			e.printStackTrace();
		}
	}
	
	// notification au serveur du départ d'un client
	public void deconnexion () {
		try {
			hdv.logout(myClientInfos);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		// TODO : fermeture de l'application ?
	}
	
	// notification au serveur de l'ajout d'un nouvel objet a vendre dans une salle donnée.
	public void nouvelleSoumission(String nom, String description, int prix, UUID idSdv) throws RemoteException {
		Objet nouveau = new Objet(nom, description, prix,myClientInfos.getNom());
		//ajout de l'objet par le hdv
		// TODO : peut etre autoriser l'ajout seulement pour le créateur de la salle
		try {
			hdv.ajouterObjet(nouveau, idSdv, getId());
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
			// TODO : refresh l'IHM pour prendre en compte les modifs
		} catch (RemoteException e) {
			e.printStackTrace();
			// TODO : affichage d'un message d'erreur par l'IHM
		}
	}
	
	public HashMap<UUID, Objet> getVentesSuivies() {
		return ventesSuivies;
	}

	@Override
	public void nouveauMessage(UUID idSalle, Message message) {
		getListesMessages().get(idSalle).add(message);
		interfaceClient.actualiserInterface();
		// TODO : éventuellement supprimer les plus anciens messages au dela d'un certain nombre.
	}

	@Override
	public void notifModifObjet(UUID idSalle, Objet objet) {
		ventesSuivies.put(idSalle, objet);
		mapInfosSalles.get(idSalle).setObjCourrant(objet);
		interfaceClient.actualiserInterface();
	}

	@Override
	public void notifFermetureSalle(UUID idSalle) {
		ventesSuivies.remove(idSalle);
		mapInfosSalles.remove(idSalle);
		interfaceClient.actualiserInterface();
	}

	@Override
	public void notifNouvelleSalle(UUID idsdv, SalleDeVenteInfo sdvi) {
		mapInfosSalles.put(idsdv, sdvi);
		System.out.println("Une nouvelle salle a été crée");
		interfaceClient.actualiserInterface();
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
		// TODO : lever une exception ?
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
		// TODO : lever une exception ?
		else return new SalleDeVenteInfo[0];
	}

	public void quitterSalle(UUID idSalleAQuitter) {
		try {
			hdv.quitterSalle( getId(),idSalleAQuitter);
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


	public void bindClient() {
		Boolean flagRegistreOkay=false;
		Registry r=null;
		while(!flagRegistreOkay) {
			try {
				//XXX: Get un registry fait bugger !
				//r = LocateRegistry.getRegistry(Integer.parseInt(myClientInfos.getPort()));
				
				if (r==null) {
					
					r=LocateRegistry.createRegistry(Integer.parseInt(myClientInfos.getPort()));
					DebugTools.d("Registre créé au port "+Integer.parseInt(myClientInfos.getPort()));
				}else {
					DebugTools.d("Registre trouvé au port "+Integer.parseInt(myClientInfos.getPort()));
				}
				flagRegistreOkay=true;
				
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			setPortClient( Integer.toString(Integer.parseInt(getPortClient())+1) );
		}
		DebugTools.d("Tentative de bind à "+getAdresseClient());
		
		try {
			r.rebind(getAdresseClient(), this);
			DebugTools.d("bind à "+getAdresseClient()+" effectué.");
		} catch (RemoteException e1) {
			DebugTools.d("bind à "+getAdresseClient()+" échoué.");			
			e1.printStackTrace();
		}
		
		
	}


	public HashMap<UUID, List<Message>> getListesMessages() {
		return listesMessages;
	}


	public void setListesMessages(HashMap<UUID, List<Message>> listesMessages) {
		this.listesMessages = listesMessages;
	}


}
