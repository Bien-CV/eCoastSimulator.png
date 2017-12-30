package commun;

public class SalleDeVenteInfo {
	
	private String nom;
	private UUID id;
	private Objet objCourrant;
	
	public SalleDeVenteInfo(String nom, Objet objCourrant) {
		super();
		this.nom = nom;
		this.id = id;
		this.objCourrant = objCourrant;
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
		return objCourrant;
	}
	
	public void setObjCourrant(Objet objCourrant) {
		this.objCourrant = objCourrant;
	}

}
