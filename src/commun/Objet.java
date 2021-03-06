package commun;

import java.io.Serializable;
import java.util.Calendar;
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
	
	
	public Objet(String nom, String description, float f, String nomDuProprietaire) {
		super();
		this.nom = nom;
		this.description = description;
		this.prixBase = f;
		this.prixCourant = f;
		this.nomGagnant = nomDuProprietaire;
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
	public void setPrixBase(float prixBase) {
		this.prixBase = prixBase;
	}
	public float getPrixCourant() {
		return prixCourant;
	}
	public void setPrixCourant(float prixCourant) {
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
		setDateDeFinDeVente(ajouterSeconde(dateDeMiseEnVente, 30));
	}
	
	public static Date ajouterSeconde(Date date, int nbSeconde) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, nbSeconde);
		return cal.getTime();
	}

	public Date getDateDeFinDeVente() {
		return dateDeFinDeVente;
	}

	public void setDateDeFinDeVente(Date dateDeFinDeVente) {
		this.dateDeFinDeVente = dateDeFinDeVente;
	}

	public String getNomProprietaire() {
		return nomProprietaire;
	}

	public void setNomProprietaire(String nomProprietaire) {
		this.nomProprietaire = nomProprietaire;
	}


	public Date getDateDeMiseEnVente() {
		return dateDeMiseEnVente;
	}

	public void setDateDeMiseEnVente(Date dateDeMiseEnVente) {
		this.dateDeMiseEnVente = dateDeMiseEnVente;
	}
	
	public String tempsRestant() {
		Calendar cal = Calendar.getInstance();
		long present = cal.getTimeInMillis();
		long restant = dateDeFinDeVente.getTime() - present;
		return (restant%86400000)/3600000+":"+((restant%86400000)%3600000)/60000+":"+(((restant%86400000)%3600000)%60000)/1000;
	}
}
