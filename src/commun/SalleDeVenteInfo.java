package commun;

import java.io.Serializable;
import java.util.UUID;

public class SalleDeVenteInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5766736704258333158L;
	private String nom;
	private UUID id;
	private Objet objCourant;
	
	public SalleDeVenteInfo(String nom, UUID id, Objet objCourrant) {
		super();
		this.nom = nom;
		this.id = id;
		this.objCourant = objCourrant;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public Objet getObjCourant() {
		return objCourant;
	}
	
	public void setObjCourrant(Objet objCourrant) {
		this.objCourant = objCourrant;
	}

	public String toString(){
		return nom+":"+objCourant.getNom();
		
	}
}
