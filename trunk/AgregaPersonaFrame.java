import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.LinkedList;

import org.jgraph.*;
import org.jgraph.event.GraphSelectionEvent;
import org.jgraph.event.GraphSelectionListener;
/**
 * Frame para dar de alta una nueva persona en el sistema. Contiene el campode  nombre
 * y de losa tributos a incluir en el
 * Opcionalmente sirve para editar una persona, dependiendo del constructor usado
 * 
 * @author Equipo 6 Cynthia Treviño, Ricardo Magallanes, Daniel Ramirez 
 */
public class AgregaPersonaFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 9057251280536386344L;
	private JLabel nombreL,statusBar;
	private JTextField nombre;
	private JButton guardar,limpiar,cancelar;
	private JComboBox[] atributos;
	private JLabel[] atributosL;
	private JTextField[] pesos;
	private String[] attNames;
	private Persona base;//-- Persona base, si se va a editar.
	
	//-- Constructor para editar
	public AgregaPersonaFrame(Persona p){
		this(); //Cargar elementos principales
		this.nombre.setText(p.getNombre());
		this.base = p;
		this.setTitle("Administrar atributos de la persona: "+p.getNombre());
		LinkedList<Persona.PersonaAtributo> atribs = p.getAtributos();
		for(int i = 0; i < this.pesos.length; i++ ){
			try {
			if(atribs.get(i)!=null){
				this.pesos[i].setText(atribs.get(i).getWeight()+"");
				this.atributos[i].setSelectedItem(atribs.get(i).getName());
			}
			} catch(Exception exe){}
		}
	}
	
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
			if(att[i]!=null && !att[i].equals("")){
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
			LinkedList<String> already = new LinkedList<String>();
			for(int i=0, j=0; i<Atributo.atributos.size();i++){
				String item = (String)atributos[i].getSelectedItem();
				if(!already.contains(item)){
					att[j]= item;
					attval[j]= pesos[i].getText();
					already.add(item);
					j++;
				}
			}
			if(validar(name,att,attval)){
				if(this.base == null){
					for(Persona pexist : Persona.getPersonas()){
						if(pexist.getNombre().equals(name)){
							//-- Error
							statusBar.setText("Esa persona ya existe.");
							return;
						}
					}
					Persona p = new Persona(name);
					if(Persona.personas.indexOf(p)!=-1 && this.base == null){ //-- nuevo
						for(int i=0; i<Atributo.atributos.size();i++){
							if(att[i]!=null && !att[i].equals("")){
								p.setAttribute(att[i], Float.parseFloat(attval[i]));
							}
						}
						if(Persona.personas.size() > 1){
							Main.getAgente().agregarPersona(p);
						}
						Main.getInterfaz().repaint();
						this.dispose();
					} else{
						statusBar.setText("Intente otro nombre, pues ese ya está registrado");
					}
				} else { //-- Edicion.
					
					Persona persona_registrada = Persona.getPersona(this.base.getNombre());
					persona_registrada.setNombre(name);
					int i = 0;
					for(String atributo : att){
						this.base.getAtributo(atributo).setWeight(Float.parseFloat(attval[i]));
						i++;
					}
					Main.getInterfaz().updatePropertiesPanel();
					Main.getInterfaz().repaint();
					this.dispose();
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
