package project;


public class Numerique implements Posable{
	double num;
	
	Numerique(double d){
		this.num = d;
	}
	public double getValeur(){
		return num;
	}
	
	public void setValeur(double d1, double zero){
		this.num = d1;
	}
	
	public String toString(){
		double d= this.num;
		String s=(""+d);
		return s;
		
	}
	
	public String afficher(){
		return (""+num);
	}

}
