import javax.swing.*;

import org.jgraph.*;
import org.jgraph.event.GraphSelectionEvent;
import org.jgraph.event.GraphSelectionListener;

import java.awt.event.*;
import java.awt.*;
import java.io.File;

public class manejadorAtributos extends JFrame implements ActionListener,GraphSelectionListener {

	private static final long serialVersionUID = 1039877813653006074L;
  
	
	
	
	public manejadorAtributos(){
		
		this.setSize(200,200);
		this.setTitle("Administrar Atributos");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		
		
	}
	
	
	
	public void actionPerformed(ActionEvent e){
	
	
		
	}
	
	public void valueChanged(GraphSelectionEvent arg0) {
		//-- Cambiar seleccion
		//System.out.println(arg0.getCell().toString());
	}

}
