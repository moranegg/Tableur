package project;





public class MyApplication implements Application{
	
	Placement [][] tab;
	private IGTableur igt;
	
	MyApplication() throws ErreurFormule{
		
		tab = new Placement [10][10];
		for(int i=0; i<tab.length; i++){
			for(int j=0; j<tab[i].length; j++){
				// definir placement par deffault- cellule vide et ligne et colonne
				tab[i][j]= new Placement(this);
				tab[i][j].setLigne(i);
				tab[i][j].setColonne(j);
				System.out.println("la cellule "+tab[i][j].getColonne()+tab[i][j].getLigne()+" a ete creer");
				
				
				
			}
		}
	}
	public void setInterface(IGTableur i){ igt =i; }
	
	/**
	 * setter du contenu dans le tableau des Placement
	 */
	@Override
	public void setContenu(char col, int lig, String text) throws ErreurFormule {
		int ligne = lig;
		int colonne =  col - 'A';
		
		tab[ligne][colonne].setContenu(text);
		igt.modifieCellule(col, lig, tab[ligne][colonne].getAffichage());
		loopAffichage();
		
		
	}
	/**
	 * getter du contenu du tableau de placement
	 */
	@Override
	public String getContenu(char col, int lig) {
		int ligne = lig;
		int colonne = col - 'A';

		return tab[ligne][colonne].getContenu();
	}

	
	public Placement getPlacement(char col, int lig) {
		int ligne = lig;
		int colonne = col - 'A';

		return tab[ligne][colonne];
	}
	
	public void loopAffichage(){
		for(int i=0; i<tab.length; i++){
			for(int j=0; j<tab[i].length; j++){
				char c = toColonne(j);
				igt.modifieCellule(c, i, tab[i][j].getAffichage());
			}
		}
	}
	
	public char toColonne(int col){
		char [] tab = {'A', 'B','C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
		return tab[col];
	}

}
