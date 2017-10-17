package commun;

import java.io.Serializable;

public class Objet implements Serializable{

	private static final long serialVersionUID = 1L;
	private String nom;
	private String description;
	private float prixBase;
	private float prixCourant;
	private boolean disponible;
	private String nomGagnant;
	
	
	public Objet(String nom, String description, int prixBase) {
		super();
		this.nom = nom;
		this.description = description;
		this.prixBase = prixBase;
		this.prixCourant = prixBase;
		this.disponible = true;
		this.nomGagnant = "";
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getPrixBase() {
		return prixBase;
	}
	public void setPrixBase(int prixBase) {
		this.prixBase = prixBase;
	}
	public boolean isDisponible() {
		return disponible;
	}
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
	public float getPrixCourant() {
		return prixCourant;
	}
	public void setPrixCourant(int prixCourant) {
		this.prixCourant = prixCourant;
	}

	public String getNomGagnant() {
		return nomGagnant;
	}

	public void setNomGagnant(String nomGagnant) {
		this.nomGagnant = nomGagnant;
	}
	
	
	
}