// TODO :
// Eventuellement ajouter un attribut date pour pouvoir supprimer les plus vieux messages
// 		(et potentiellemtn afficher la date dans le chat)

package commun;

import java.io.Serializable;

public class Message implements Serializable{
	private static final long serialVersionUID = -7045948667272167832L;
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
