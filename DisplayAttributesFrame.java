import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.LinkedList;

public class DisplayAttributesFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton cerrar = new JButton("Cerrar Ventana");
	public Persona p = null;
	public static LinkedList<DisplayAttributesFrame> ventanas = new LinkedList<DisplayAttributesFrame>();
	private AttributesTable at;
	public static DisplayAttributesFrame getAttributesForm(Persona p){
		for(DisplayAttributesFrame ventana : DisplayAttributesFrame.ventanas){
			if(ventana.p.equals(p)){
				return null;
			}
		}
		DisplayAttributesFrame x = new DisplayAttributesFrame(p);
		ventanas.add(x);
		x.setVisible(true);
		return x;
	}
	
	public static void refreshWindows(){
		for(DisplayAttributesFrame ventana : DisplayAttributesFrame.ventanas){
			ventana.refreshPerson();
		}
	}
	
	public DisplayAttributesFrame(Persona p){
		super("Atributos de: "+p.getNombre());
		this.setSize(new Dimension(200,150));
		this.setLayout(new BorderLayout());
		this.p = p;
		this.setAlwaysOnTop(true);
		cerrar.addActionListener(this);
		at = new AttributesTable();
		at.setData(p.getAtributosTable());
		
		this.add(cerrar,BorderLayout.SOUTH);
		this.add(at,BorderLayout.CENTER);
		
	}

	public void refreshPerson(){
		at.setData(p.getAtributosTable());
		at.repaint();
		this.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(cerrar)){
			this.dispose();
		}
	}
}
