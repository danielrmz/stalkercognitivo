import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import org.jgraph.*;
import org.jgraph.event.GraphSelectionEvent;
import org.jgraph.event.GraphSelectionListener;

public class AgregaPersonaFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 9057251280536386344L;
	private JLabel nombreL,statusBar;
	private JTextField nombre;
	private JButton guardar,limpiar,cancelar;
	private JComboBox[] atributos;
	private JLabel[] atributosL;
	private JTextField[] pesos;
	private String[] attNames;
	//private Agente ai;
	
	public AgregaPersonaFrame(){
		//this.ai=ai;
		
		//-- Propiedades de la Interfaz
		this.setTitle("Administrador de Atributos");
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(true);
		
		nombreL = new JLabel("Nombre");
		nombreL.setBounds(10, 10, 50, 20);
		nombre = new JTextField();
		nombre.setBounds(70, 10, 300, 20);
		
		int x1 = 50;
		
		this.add(nombreL);
		this.add(nombre);
		
		atributos = new JComboBox[Atributo.atributos.size()];
		atributosL = new JLabel[Atributo.atributos.size()];
		pesos = new JTextField[Atributo.atributos.size()];
		attNames = new String[Atributo.atributos.size()+1];
		
		attNames[0]="";
		for(int j=0; j<Atributo.atributos.size(); j++){
			attNames[j+1] = Atributo.atributos.get(j).toString();
		}
		
		for(int i=0; i<Atributo.atributos.size();i++){
			atributosL[i] = new JLabel("Atributo "+(i+1));
			atributosL[i].setBounds(10, x1, 70, 20);
			atributos[i] = new JComboBox(attNames);
			//atributos[i].setBackground(Color.white);
			atributos[i].setBounds(80, x1, 200, 20);
			atributos[i].setEditable(false);
			atributos[i].addActionListener(this);
			pesos[i]= new JTextField();
			pesos[i].setBounds(290, x1, 60, 20);
			this.add(atributosL[i]);
			this.add(atributos[i]);
			this.add(pesos[i]);
			x1 +=30;
		}
		
		
		guardar = new JButton("Guardar");
		guardar.addActionListener(this);
		
		limpiar = new JButton("Limpiar");
		limpiar.addActionListener(this);
		
		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(this);
		
		JPanel botones = new JPanel(new GridLayout(1,3,10,10));
		botones.add(limpiar);
		botones.add(guardar);
		botones.add(cancelar);
		botones.setBounds(40, x1, 300, 20);
		
		x1+=30; 
		
		statusBar = new JLabel();
		statusBar.setForeground(Color.red);
		statusBar.setBounds(10, x1, 300, 20);
		
		this.add(botones);
		this.add(statusBar);
		
		this.setSize(400, x1+60);
	}
	
	private boolean validar(String name,String[] att, String[] attval){
		boolean sw=true;
		if(name.equals("")){
			statusBar.setText("Favor de completar el nombre");
			return false;
		}
		for(int i=0; i<Atributo.atributos.size();i++){
			if(!att[i].equals("")){
				sw=false;
				try{
					Float.parseFloat(attval[i]);
				}catch(Exception e){
					statusBar.setText("Los valores de los pesos deben ser númericos");
					return false;
				}
			}
		}
		if(sw){
			statusBar.setText("Al menos, dar de alta un atributo");
			return false;
		}
		statusBar.setText("");
		return true;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(guardar)){
			String name = nombre.getText();
			String [] att = new String[Atributo.atributos.size()];
			String [] attval = new String[Atributo.atributos.size()];
			for(int i=0; i<Atributo.atributos.size();i++){
				att[i]= (String)atributos[i].getSelectedItem();
				attval[i]= pesos[i].getText();
			}
			if(validar(name,att,attval)){
				Persona p = new Persona(name);
				if(Persona.personas.indexOf(p)!=-1){
					for(int i=0; i<Atributo.atributos.size();i++){
						if(!att[i].equals("")){
							p.setAttribute(att[i], Float.parseFloat(attval[i]));
						}
					}
					if(Persona.personas.size() > 1){
						Main.getAgente().agregarPersona(p);
					}
					Main.getInterfaz().repaint();
					this.dispose();
				}else{
					statusBar.setText("Intente otro nombre, pues ese ya está registrado");
				}
			}
		}else if(e.getSource().equals(limpiar)){
			nombre.setText("");
			statusBar.setText("");
			for(int i=0; i<Atributo.atributos.size();i++){
				atributos[i].setSelectedIndex(0);
				pesos[i].setText("");
			}	
			
		}else if(e.getSource().equals(cancelar)){
			this.dispose();
		}
		
	}

}
