package project;


public class Vide implements Posable {
	String vide = "";
	
	public double getValeur(){
		return 0;
	}
	
	void setValeur(){
		vide = "";
	}
	
	public String toString(){
		return vide;
	}
	
	public String afficher(){
		return vide;
	}
	
	@Override
	public void setValeur(double d1, double d2) {
		// pas une methode executable en Vide
		
	}


}
