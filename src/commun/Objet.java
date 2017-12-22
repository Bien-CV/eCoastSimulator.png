package commun;

import java.io.Serializable;
import java.util.Date;

public class Objet implements Serializable{

	private static final long serialVersionUID = 1L;
	private String nom;
	private String description;
	private float prixBase;
	private float prixCourant;
	private String nomGagnant;
	private String nomProprietaire;
	
	//Initialisé par la salle de vente lors de la mise en vente de l'objet
	private Date dateDeMiseEnVente = null;
	private Date dateDeFinDeVente = null;
	
	
	public Objet(String nom, String description, int prixBase, String nomDuProprietaire) {
		super();
		this.nom = nom;
		this.description = description;
		this.prixBase = prixBase;
		this.prixCourant = prixBase;
		this.nomGagnant = "";
		this.nomProprietaire = nomDuProprietaire;
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
	
	public void miseEnVente () {
		dateDeMiseEnVente = new Date();
		dateDeFinDeVente = ajouterSeconde(dateDeMiseEnVente, 30);
	}
	
	public static Date ajouterSeconde(Date date, int nbSeconde) {
		Calendar cal = Calendar.getlnstance();
		cal.setTime(date.getTime());
		cal.add(Calendar.SECOND, nbSeconde);
		return cal.getTime();
	}
}
