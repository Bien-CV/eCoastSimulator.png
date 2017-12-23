// TODO :
// Eventuellement ajouter un attribut date pour pouvoir supprimer les plus vieux messages
// 		(et potentiellemtn afficher la date dans le chat)

package commun;

public class Message {
	private String auteur;
	private String contenu;
	
	public Message(String auteur, String contenu) {
		super();
		this.auteur = auteur;
		this.contenu = contenu;
	}
	
	public String getAuteur() {
		return auteur;
	}
	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
}
