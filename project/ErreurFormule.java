package project;

 public class ErreurFormule extends RuntimeException{
		String ex;
	    ErreurFormule(String string){
		ex = string;
	    }
	}

 class ErreurBoucleInf extends Error{

 }
