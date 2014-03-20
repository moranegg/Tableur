package project;

public class main {

	/**
	 * @param args
	 * @throws ErreurFormule 
	 */
	public static void main(String[] args) throws ErreurFormule {
		MyApplication test = new MyApplication();
		IGTableur tableur = new IGTableur(test);
		test.setInterface(tableur);

		Terminal.lireString();
		tableur.fermer();
		
		
	}
}
