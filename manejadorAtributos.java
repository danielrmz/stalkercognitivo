import javax.swing.*;

import java.awt.event.*;
import java.awt.*;


public class manejadorAtributos extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1039877813653006074L;
  
	private JLabel a,b,c,d;
	private JButton salir,agregar;
	private TextField one;
	private JList atr;
	private DefaultListModel dlm = new DefaultListModel();
	
	
	
	public manejadorAtributos(){
		
		this.setSize(700,300);
		this.setTitle("Administrar Atributos");
		this.setLayout(null);
		this.setResizable(true);
		
		this.setResizable(true);
		JList atr=new JList(dlm);
		a=new JLabel("A continuación se muestran los atributos disponibles:\n");
		b=new JLabel("Nombre del atributo:");
		c=new JLabel("Agregar Atributo-");
		d=new JLabel("El atributo se ha agregado exitosamente.");
    	this.add(a);
		Insets inset=this.getInsets();
		Dimension x =a.getPreferredSize();
		a.setBounds(0+inset.left,0+inset.top,x.width,x.height);
		
		one=new TextField();
		atr.setPreferredSize(new Dimension(190,240));
        one.setPreferredSize(new Dimension(207,20));
	    this.add(atr);
	    Dimension x2=atr.getPreferredSize();
	    atr.setBounds(0+inset.left,30+inset.top,x2.width,x2.height);
        
        
	    this.add(b);
	    Dimension x4=b.getPreferredSize();
	    b.setBounds(250+inset.left,60+inset.top,x4.width,x4.height);
	    
	    this.add(c);
	  	Dimension x5=c.getPreferredSize();
	    c.setBounds(200+inset.left,30+inset.top,x5.width,x5.height);
	    
	    this.add(d);
	  	Dimension x8=d.getPreferredSize();
	    d.setBounds(250+inset.left,140+inset.top,x8.width,x8.height);
	    d.setVisible(false);
	    
        this.add(one);
        Dimension x3=one.getPreferredSize();
        one.setBounds(384+inset.left,60+inset.top,x3.width,x3.height);
        
        salir=new JButton("Salir");
        salir.addActionListener(this);
        this.add(salir);
        Dimension x6=salir.getPreferredSize();
        salir.setBounds(384+inset.left,100+inset.top,x6.width,x6.height);
        
        agregar=new JButton("Agregar Atributo");
        agregar.addActionListener(this);
        this.add(agregar);
        Dimension x7=agregar.getPreferredSize();
        agregar.setBounds(450+inset.left,100+inset.top,x7.width,x7.height);
        
        for(int i=0; i<Persona.PersonaAtributo.atributos.size();i++){
        
        this.dlm.addElement(Persona.PersonaAtributo.atributos.get(i).getName());
        
        }
		
		
		
	}
	
	public void actionPerformed(ActionEvent e){
	 
	  if(e.getSource().equals(salir)){
		  this.setVisible(false);
	  }
	  else{
		 Atributo atr=new Atributo(one.getText());
		 d.setVisible(true);
		 int z= Persona.PersonaAtributo.atributos.size();
		 this.dlm.addElement(Persona.PersonaAtributo.atributos.get(z-1));
	  }
	
		
	}
	


}
