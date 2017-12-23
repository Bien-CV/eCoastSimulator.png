package commun;

public class PlusDeVenteException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public PlusDeVenteException() {
		System.out.println("Toutes les ventes de la salle on été effectuées, fermeture.");
	}

}
