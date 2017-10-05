package serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//import java.util.Stack;

import client.Acheteur;

public class salleDeVente extends UnicastRemoteObject implements ISalleDeVente{
	
	private static final long serialVersionUID = 1L;
	

	protected salleDeVente() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int rencherir(int nouveauPrix, Acheteur acheteur)
			throws RemoteException, Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void ajouterObjet(Objet objet) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Objet getObjet() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Objet> getListeObjets() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Acheteur> getListeAcheteurs() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


}
