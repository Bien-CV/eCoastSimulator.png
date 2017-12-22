package commun;

public class PseudoDejaUtiliseException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public PseudoDejaUtiliseException() {
		System.out.println("Pseudo non disponible !");
	}
}
