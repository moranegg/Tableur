package project;


public interface Application {

	void setContenu(char col, int lig, String text) throws ErreurFormule;

	String getContenu(char col, int lig);

}
