package project;


public class Operation implements Posable{
	Numerique num1;
	Numerique num2;
	Numerique resultat;
	int opInt= -1;
	String [] ope = {"=PLUS", "=MOINS", "=FOIS", "=DIV"};
	/**
	 * creation de l'objet operation qui prnd deux objets
	 * de type Numrique et un string qui designe l'opration
	 * et genere un resultat  
	 * @param n1
	 * @param n2
	 * @param Op
	 */
	Operation(Numerique n1,Numerique n2, String Op) throws ErreurFormule{
		//on defini les numeriques de l'opération
		this.num1 = n1;
		this.num2 = n2;
		
		//transforme string en uppercase et en int operation
		Op = Op.toUpperCase();
		numOperation(Op); // throws ErreurFormule si opInt n'est pas un des quatre cas

		//trouver le resultat d cette operation et creation d'un Numerique resultat
		double result = resultatOperation();
		this.resultat = new Numerique(result);	
	}
	
	/**
	 * transformation de l'objet operation en String (pour la cellule)
	 */
	public String toString (){
		//String s = "="+ope[opInt]+"("+num1.getValeur()+";"+num2.getValeur()+")";
		String s = ""+resultat;
		return s;
	}
	
	
	public String afficher (){
		return (""+resultat);
	}
	
	/**
	 * retourne le resultat de l'objet operation
	 */
	public double getValeur (){
		return resultat.getValeur();
	}
	
	/**
	 * setter du resultat de l'operation avec deux operandes de type Numeriquue
	 * @param n1
	 * @param n2
	 */
	public void setValeur(Numerique n1, Numerique n2){
		this.num1 = n1;
		this.num2 = n2;
		double result = resultatOperation();
		resultat.setValeur(result, 0);
	}
	/**
	 * setter du resultat de l'operation avec deux operandes de type double
	 * @param d1
	 * @param d2
	 */
	public void setValeur(double d1, double d2){
		num1.setValeur(d1, 0);
		num2.setValeur(d2, 0);
		double result = resultatOperation();
		resultat.setValeur(result, 0);
	}
	

	/**
	 * prend le String de l'operation et reanvoie un 
	 * numéro qui deffini cette operation.
	 * Si l'operation n'existe pas renvoie 0
	 * @param Op
	 * @return
	 */
	public void numOperation(String Op){
		for(int i = 0; i<ope.length; i++){
			/*
			 * 0 = plus
			 * 1 = moins
			 * 2 = fois
			 * 3 = div
			 */
			if (Op.equals(ope[i])){
				this.opInt=i;
				return;
			}
			
		}
		throw new ErreurFormule ("pas une operation");
	}
	
	/**
	 * retourne le resultat des deux objets numerique 
	 * dans l'opration, avec le num du type d'operation
	 * @param op
	 * @return
	 */
	public double resultatOperation(){
		double premier = num1.getValeur();
		double deuxieme = num2.getValeur();
		double result;
		/*
		 * 0 = plus
		 * 1 = moins
		 * 2 = fois
		 * 3 = div
		 */
		if (this.opInt == 0){
			result = premier + deuxieme;
		} else if (this.opInt== 1){
			result = premier - deuxieme;
		} else if (this.opInt== 2){
			result = premier * deuxieme;
		} else if (this.opInt== 3){
			result = premier / deuxieme;
		} else {
			throw new ErreurFormule("pas une operation");
		}
		
		return result;
	}

	

}
