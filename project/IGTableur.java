package project;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;


import java.awt.event.*;
import java.text.DecimalFormat;

class MyMouse extends MouseInputAdapter{
    private IGTableur igt;
    private int x, y;
    MyMouse(IGTableur oi, int xx, int yy){
	igt = oi;
	x = xx;
	y = yy;
    }
    public void mouseClicked(MouseEvent e){
	igt.recordClick(x,y);
    }
}

class ButtonListener implements ActionListener{
    private IGTableur igt;
    ButtonListener(IGTableur o){
	igt=o;
    }
    public void actionPerformed(ActionEvent e) {
	if (e.getActionCommand().equals("OK"))
	    igt.clickOK();
	else
	    igt.clickCancel();
    }
}

public class IGTableur extends JFrame {
    private JTextField[][] tab = new JTextField[10][10];
    private JLabel nomcase = new JLabel("");
    private JTextField input = new JTextField(25);
    private JPanel haut = new JPanel();
    private JPanel feuille = new JPanel();
    private JLabel errmsg = new JLabel();
    private char col;
    private int lig;
    private Application appli;
    private DecimalFormat df = new DecimalFormat("#######.##"); 
    /**
     * 
     * @param a
     */
    IGTableur(Application a){
		super("Tableur");
		appli=a;
		JLabel[] horlab = new JLabel[10];
		JLabel[] verlab = new JLabel[10];
		JButton ok=new JButton("OK");
		JButton cancel=new JButton("annuler");
		haut.setLayout(new FlowLayout());
		haut.add(nomcase);
		input.setEditable(true);
		haut.add(input);
		haut.add(ok);
		haut.add(cancel);
		ok.addActionListener(new ButtonListener(this));
		cancel.addActionListener(new ButtonListener(this));
		feuille.setLayout(new GridLayout(11,11));
		for (int i=0; i<10; i++){
		    for (int j=0; j<10; j++){
			tab[i][j]= new JTextField(10);
			tab[i][j].setEditable(false);
			tab[i][j].addMouseListener(new MyMouse(this,i,j));
			tab[i][j].setHorizontalAlignment(JTextField.LEFT);
		    }
		}
		feuille.add(new JLabel(""));
		char c = 'A';
		for (int i=0; i<10; i++,c++){
		    verlab[i]=new JLabel(""+c,JLabel.CENTER);
		    feuille.add(verlab[i]);
		}
		for (int i=0; i<10; i++){
		    horlab[i]=new JLabel("" + i+ "    ",JLabel.RIGHT);
		    feuille.add(horlab[i]);
		    for (int j=0; j<10; j++){
			feuille.add(tab[i][j]);
		    }
		}
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel rehaut=new JPanel();
		rehaut.setLayout(new GridLayout(2,1));
		rehaut.add(haut);
		errmsg.setForeground(Color.RED);
		errmsg.setHorizontalAlignment(JLabel.CENTER);
		rehaut.add(errmsg);
		haut.setVisible(false);
		JPanel tout = new JPanel();
		tout.setLayout(new BorderLayout());
		tout.add(rehaut,BorderLayout.NORTH);
		tout.add(feuille,BorderLayout.CENTER);
	        setContentPane(tout);
	        pack();
	        setVisible(true);
    }
    /**
     * 
     * @param x
     * @param y
     */
    void recordClick(int x,int y){
    	if (!haut.isVisible()){
		    col = ((char) ('A'+y));
		    lig = x;
		    haut.setVisible(true);
		    nomcase.setText("case "+col+x);
		    input.setText(appli.getContenu(col,lig));
		    errmsg.setVisible(false);
		    input.requestFocus();
		}
    }
    /**
     * 
     */
    void clickOK(){
		try{
		    appli.setContenu(col,lig,input.getText());
		    haut.setVisible(false);
		}catch(ErreurFormule e){
		    errmsg.setText("Erreur de formule "+e.getMessage());
		    errmsg.setVisible(true);
		}
    }
    /**
     * 
     */
    void clickCancel(){
	haut.setVisible(false);
	errmsg.setVisible(false);
    }
    /**
     * 
     * @param c
     * @param l
     * @param v
     */
    public void modifieCellule(char c, int l, String v){
		int co = c-'A';
		String aff;
		try{
		    double d=Double.parseDouble(v);
		    aff=df.format(d) + ' ';
		    tab[l][co].setHorizontalAlignment(JTextField.RIGHT);
		}catch(NumberFormatException e){
		    tab[l][co].setHorizontalAlignment(JTextField.LEFT);
			aff = ' ' + v;
		}
		tab[l][co].setText(aff);
	 
    }
    /**
     * 
     */
	 public void fermer(){
	        this.dispose();
	    }
}



