package project;

import java.util.HashSet;

public class Placement {
	private int ligne;
	private char colonne; 
	private MyApplication appliTableur;
	private String texteEntrez;
	private boolean [] type = new boolean[4];
	private Posable contenu;
	
	HashSet <Placement> ref;// les Placament auquels le placement actuel fait refference
	/**
	 * constructeur
	 * creation de mon objet placement seulement dans une application donnée
	 * état initiale est de placement "vide"
	 * @param a
	 */
	public Placement(MyApplication a){
		appliTableur = a;
		contenu = new Vide();
		setBoolean(3);// met le tableau type à false, et apres change la case 3(pour placement vide à true)
		ref = new HashSet <Placement>();
		this.texteEntrez = "";
	}
	
	/**
	 * prend string et transforme en contenu de la cellule(placement)
	 * @param s
	 * @throws ErreurFormule
	 */
	
	public void setContenu(String text) {
		String s = text;
		s = s.trim();
		
		//cas ou l'utilisateur n'a rien tapé
		if (s.equals("")){
			contenu = new Vide();
			setBoolean(3);
			setLesRef();
			this.texteEntrez = s;
			return;
			
		//cas ou l'utilisateur a commencé de taper une operation
			
		} else if (this.trueOperation(s)){
					 
					//si l'operation est testée comme bonne opération
						Posable actuel = concatenerOperation(text);
						// si la methode concatenerOperation(text) n'a pas echouee
						if (actuel == null){
							contenu = new Label("formule impossible!");
							setBoolean(2);
							
							this.texteEntrez = s;
							return;
						} else {
						this.contenu = actuel;
						setBoolean(1);

						setLesRef();
						this.texteEntrez = s;
						return;
						}
					
			
			
		//cas ou l'utilisateur a tapé une valeur numerique
		} else if (trueNumerique(s)) {
			//cas avec virgule
			if(s.contains(",")){
				String [] numero =s.split(",");
				double partieEnt = Double.parseDouble(numero[0]);
				double partieFlot = Double.parseDouble(numero[1])* 0.1;
				double tout = partieEnt+ partieFlot;
				contenu = new Numerique( tout);//creation d'objet Numerique
				setBoolean(0);
				setLesRef();
				this.texteEntrez = s;
				return;
				
				
			} else {
			double d =Double.parseDouble(s);
			contenu = new Numerique( d);//creation d'objet Numerique
			setBoolean(0);
			setLesRef();
			this.texteEntrez = s;
			return;
			}
		// tout autre cas est un label
		} else {
			contenu = new Label(s); //creation d'objet Label
			setBoolean(2);
			setLesRef();// si on change une case qui etait numerique en label, il faut
						//changer aussi ces case filles qui ont eu des operation avec elle
			this.texteEntrez = s;
		}
		
	}
	/**
	 * renvoie le texte entrez en haut de la feuille (pas dans la cellule)
	 * @return
	 */
	public String getContenu(){
		return this.texteEntrez;
	}
	/**
	 * renvoie la valeur numerique de la cellule
	 * si la cellule est un Numerique - renvoie double
	 * si la cellule est vide - renvoie 0
	 * si la cellule est Label - renvoie null
	 * si la cellule est operation - renvoie la valeur du Numerique resultat de l'operation
	 * @return
	 */
	public double getVal(){
		return contenu.getValeur();
	}
	
	/**
	 * texte affiche' dans la cellule
	 * renvoie le contenu de la cellule en String fait par l'interface posable
	 * qui est fait pour le contenu de la cellule
	 * @return
	 */
	public String getAffichage(){
		setContenu(texteEntrez);
		return contenu.toString();
	}
	/////////////////////////////////////////test du text entrez/////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * methode qui test si ce text  est en format d'une operation
	 * appel au methodes trueNumerique - test si numerique 
	 * et  bonPlacement - test si est une cellule du tableau
	 * @param STring traduction
	 * @return boolean
	 */
	public boolean trueOperation(String traduction){
		String s = traduction.toUpperCase().trim();
		if (! (s.startsWith("="))){
			return false;
		}
		//verification de partie operation (avant '(')
		if(!(s.contains("("))){
			return false;
		}
		String  [] operatiopn  = s.split("\\(");
		String oper =operatiopn[0];
		if( !(oper.equals("=PLUS")|| oper.equals("=MOINS")
				|| oper.equals("=FOIS")|| oper.equals("=DIV"))){
			return false;
		}
		// verification de premier numerique dans l'operation
		if(!(operatiopn[1].contains(";"))){
			return false;
		} 
		String [] premier = operatiopn[1].split(";");
		
		if (!(trueNumerique( premier[0]) || bonPlacement( premier[0]) )){
			return false;
		}
		
		//verification de deuxieme numerique dans l'operation
		if(!(premier[1].contains(")"))){
			return false;
		} 
		String [] deuxieme =  premier[1].split("\\)");
		if (!(trueNumerique(deuxieme[0]) || bonPlacement(deuxieme[0]) )){
			return false;
		}
		
		// si aucun return a été effectué trueOperation est valide
		return true;
	}
	/**
	 * methode qui test si ce text peux etre un numerique
	 *  (en étant seulement un double, pas de ref)
	 * @param s
	 * @return boolean
	 */
	public boolean trueNumerique(String s){
		s = s.trim();
		char [] t= s.toCharArray();
		boolean num= false;
		int cptVIrgulePOint = 0;
		if(t[0] =='.'|| t[0]==','){
			return false;
		}
		for(int i =0; i<t.length;i++){
			if((t[i]>='0' && t[i]<='9')|| t[i]=='.'|| t[i]==','){
				num=true;
				if (t[i]=='.'|| t[i]==','){
					cptVIrgulePOint ++;
				}
			} else {
				return false;
			}
		}
		if(cptVIrgulePOint>= 2 ){
			return false;
		} 
		
		return num;
	}
	/**
	 * qui test si un bout de texte est vraiment une référence à une cellule
	 * @param cellule
	 * @return boolean
	 */
	public boolean bonPlacement(String cellule){
		String s= cellule.trim();
		char [] t = s.toCharArray();
		if(t[0]>='A'&& t[0]<='J'){
			if(t[1]>='0' && t[1]<='9')
				return true;
				
			else
				return false;
		
		
		} else {
			return false;
		}
			
		
	}
	
	
	
	////////////////////////////////////////////fin tests/////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * methode qui prend un texte intiale et qui crée une opération
	 * renvoie le text a la methode traduction 
	 * catch les erreurs
	 * @param texte
	 * @return Operation
	 */
	public Operation concatenerOperation(String texte){
		Operation op = null;
		try {
			op =traduction(texte);
			
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("la cellule de ref n'est pas dans le tableau");
		}catch(ErreurFormule e){
			System.out.println("erreur formule");
		} catch (ErreurBoucleInf e){
			System.out.println("problem!boucle  inf");
		}
		return op;


		
	}
	public Operation traduction(String s) throws ArrayIndexOutOfBoundsException, ErreurFormule, ErreurBoucleInf {
		String op = s.toUpperCase().trim();
		String [] t = op.split("\\(");
		String oper = trouverOper(t[0]); //trouver operation
		
		String [] premier =t[1].split(";");
		Numerique premierNum = trouverNumeriquePremier(premier[0]);
		
		String [] deuxieme =  premier[1].split("\\)");
		
		Numerique deuxiemeNum = trouverNumeriqueDeuxieme(deuxieme[0]);
		


		Operation Op = new Operation(premierNum, deuxiemeNum, oper);
		return Op;
	}
	
	/**
	 * @param s
	 * @return String (l'operateur)
	 */
	public String trouverOper(String s) throws ErreurFormule{
		String op = s.toUpperCase().trim();
		if( (op.equals("=PLUS")|| op.equals("=MOINS")
				|| op.equals("=FOIS")|| op.equals("=DIV"))){
			return op;
		}else{
			throw new ErreurFormule("pas une operation");
		}
		
	}
	/**
	 * @param s
	 * @return Numerique
	 */
	public Numerique trouverNumeriquePremier(String s) throws ErreurFormule, ArrayIndexOutOfBoundsException,ErreurBoucleInf{
		Numerique num1 = traductionNumerique( s.toUpperCase().trim());
		return num1;
		
	}
	/**
	 * methode qui prend le texte initiale et renvoie 
	 * la partie qui represente le deuxieme numerique seulement
	 * @param s
	 * @return string
	 */
	public Numerique trouverNumeriqueDeuxieme(String s)throws ErreurFormule, ArrayIndexOutOfBoundsException,ErreurBoucleInf{
		Numerique num2 = traductionNumerique( s.toUpperCase().trim());
		return num2;
		
	}
	/**
	 * methode qui prend une partie du string initiale et qui la transforme en numerique
	 * @param s
	 * @return Numerique
	 * @throws ErreurFormule
	 */
	public Numerique traductionNumerique(String s) throws  ArrayIndexOutOfBoundsException,ErreurBoucleInf{
		Numerique num;
	
		if (trueNumerique( s)){		
			if(s.contains(",")){
				String [] numero =s.split(",");
				double partieEnt = Double.parseDouble(numero[0]);
				double partieFlot = Double.parseDouble(numero[1])* 0.1;
				double tout = partieEnt+ partieFlot;
				
				num = new Numerique(tout);
				return num;
				
			}
			double d =Double.parseDouble(s);
			num = new Numerique(d);
			return num;
			
		} else if (bonPlacement( s)){ 
			//chercher valeur numerique de placement
			Placement temp = trouverPlacement(s);
			try {
				setRef(s);// methode qui "try" d'ajouter le placement actuel dans les ref du placement s 
				Numerique nunu = TraductionPlacementref(temp);
				return nunu;
			} catch (ArrayIndexOutOfBoundsException e){
				throw new ArrayIndexOutOfBoundsException();
			} catch(ErreurBoucleInf e){
				throw new ErreurBoucleInf();
			}
			

		/*} else if ( trueOperation(s)){
			Operation imbrique = concatenerOperation(s);
			double brique = imbrique.getValeur();
			Numerique bribri = new Numerique(brique);
			return bribri;
		*/
		 	
		 
			
		} else {
			throw new ErreurFormule(" pas de valeur numerique");
		}
		
	}
	
	/**
	 * prend un string qui est une ref (A0, B6...) et retourne l'objet placement de cette ref
	 * @param ref
	 * @return Numerique
	 */
	public Numerique TraductionPlacementref(Placement ref){
		Numerique num;
		
		if (ref.getBoolean() == 2){
			throw new ErreurFormule(
					"une operation avec un label n'est pas possible");
			
		}
		double d = ref.getVal();
		num = new Numerique(d);
		return num;	
	}
	

	/**
	 * setter de ligne (est effectué seulement à la création de l'application)
	 * @param i
	 */
	public void setLigne(int i){
		this.ligne =i;
	}
	/**
	 * setter de colonne (est effectué seulement à la création de l'application)
	 * @param j
	 */
	public void setColonne(int j){
		char [] tab = {'A','B','C','D','E','F','G','H','I','J'};
		char c = tab[j];
		this.colonne = c;
	}
	/**
	 * getter de ligne
	 * @return int
	 */
	public int getLigne(){
		return this.ligne;
	}
	/**
	 * getter de colonne
	 * @return char
	 */
	public char getColonne(){
		return this.colonne;
	}
	/**
	 * getter du numero de la case dans le tableau des booleans
	 * @return int
	 */
	public int getBoolean(){
		for(int i=0; i<type.length;i++){
			if(type[i]== true){
				return i;
			}
		}
		return -1;
	}
	/**
	 * setter pour ecrire dans la case du tableau type, quel type est ce Placment actuel
	 * 	type[0] =numerique
		type[1]= operation
		type[2] = label
		type[3] = vide
	 */
	public void setBoolean(int i){
		
		for(int j=0; j<type.length;j++){
			type[j] = false;
		}
		
		type[i]=true;
	}
	
	/**
	 * prend le texte qui designe une cellule et renvoie le placement dans l'application
	 * qui est concerné
	 * @param cellule
	 * @return Placement
	 * @throws ErreurFormule
	 */
	public Placement trouverPlacement(String cellule) throws ErreurFormule {
		String s= cellule.trim();
		char [] t = s.toCharArray();
		char colonne;
		int ligne;
		if(t[0]>='A'&& t[0]<='J'){
			colonne = t[0];
		} else {
		    throw new ArrayIndexOutOfBoundsException();
		    
		}
		  
		if(t[1]>='0' && t[1]<='9'){
			ligne = Character.getNumericValue(t[1]);
			
		} else {
			throw new ArrayIndexOutOfBoundsException();
		} if (t.length>2){
			throw new ArrayIndexOutOfBoundsException();
		}
		
		return this.appliTableur.getPlacement(colonne, ligne);
		
	}
	/**
	 * methode qui ajoute l'objet courant dans l'objet Placement qui est refference par lui
	 * ajouter la fille dans la mere
	 * si B2 : =PLUS(A0;A1); b2 est la fille
	 * A0 et A1 contienne B2 dans leurs ref, a0 et a1 sont les meres
	 * comme ça on pourrais savoir qui il faut changer en les changent
	 */
	public void  setRef(String cellule ) throws ErreurBoucleInf{

			Placement mere =  trouverPlacement(cellule);
			mere.ajoutRef(this); //this= fille
		
		
	}
	/**
	 * prend un placement et le rajoute dans l'ArrayList de ref du placement en cours
	 * p1.ajoutRef(p2)
	 * j'ajoute a p1 le placement p2 dans ses refs
	 * donc je dois verifier si p1 existe deja dans p2
	 * @param placement
	 */
	private void ajoutRef(Placement fille) throws ErreurBoucleInf {
		Placement mere= this;
		if(fille.verifRef(mere)){ //si dans la fille il n'y a pas deja la mere (ou dans ses refs)
			throw new ErreurBoucleInf();
		}
		mere.getRef().add(fille);
	}
	/**
	 * par reccurence
	 * verificatiion des references de la cellule actuelle dans l'arrayList des refs de
	 * cette cellule
	 * @param Placement recuP
	 * @return boolean false or error
	 */
	public boolean verifRef(Placement mere){
		Placement fille = this;
		if(fille.getRef().isEmpty()){// cas terminal, la liste des ref est vide donc ne contient pas this
			return false;
		} 
		
		if(fille.getRef().contains(mere)){
			return true;
		} 
		 
		for(Placement p : fille.getRef()){
			 if(p.verifRef(mere)){
				 return true;
			}
		}
		return false;
		
	}
		/*for(Placement p : recuP.getref){
			boolean b = (actuel.ref.contains(recuP))|| verifRef(p);
			if (b==true)
				throw new ErreurBoucleInf();
		}
		return false;
	}*/
	/**
	 * getter des refs
	 * @return
	 */
	public HashSet<Placement> getRef(){
		return this.ref;
	}
	
	/**
	 * methode qui modifie toute les refs 
	 * @throws ErreurFormule
	 */
	public void setLesRef() throws ErreurFormule{
		try {
			if(ref.isEmpty()){
				return;
			} else {
				
				int bol = this.getBoolean();
				
				for(Placement p: ref){
					while(!(verifRef(p))){
						if (bol==2){
							p.setContenu("ref modif");
						} else {
							p.setContenu(p.texteEntrez);
						}
					}
				}
			}
		} catch(ErreurBoucleInf e){
			
		}
	}
}
