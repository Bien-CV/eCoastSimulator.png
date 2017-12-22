package commun;

public class DejaConnecteException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DejaConnecteException() {
		System.out.println("Deja connecté !");
	}
}
