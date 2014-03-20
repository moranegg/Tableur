package project;


public class Label implements Posable {
	String labe;
	
	Label (String s){
		labe = s;
	}
	

	
	void setValeur(String s){
		labe = s;
	}
	
	public String toString(){
		return labe;
	}
	
	public String afficher(){
		return labe;
	}



	@Override
	public double getValeur() {
		
		return (Double) null;
	}



	@Override
	public void setValeur(double d1, double d2) {
		// pas une methode executable en Label
		
	}

}
