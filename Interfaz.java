import javax.swing.*;

import org.jgraph.*;
import org.jgraph.event.GraphSelectionEvent;
import org.jgraph.event.GraphSelectionListener;

import java.awt.event.*;
import java.awt.*;
import java.io.File;

public class Interfaz extends JFrame implements ActionListener,GraphSelectionListener {

	private static final long serialVersionUID = 1039877813653006074L;

	private JMenuBar menuPrincipal;
	private JMenu archivo, simulacion, ayuda;
	private JMenuItem archivoAbrir, archivoGuardar, archivoCerrar, simulacionAgregar,
					  simulacionBorrar, simulacionBuscar, simulacionAtributos,
					  ayudaInfo;
	private JList amigos;
	private JPanel pnlGrafo,pnlBusqueda, panelAttribs;
	private JSplitPane panelSub, panelCentral,panelDer;
	private JButton search;
	private JTextField compatibilidad,niveles;
	private JLabel lblStatusBar;
	
	private DefaultListModel dlm = new DefaultListModel();
	
	private Persona seleccionada = null;
	
	public Interfaz(){
		//-- Propiedades de la Interfaz
		this.setSize(950,600);
		this.setTitle("Simulador de Redes Sociales");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		
		//-- Menu Principal
		menuPrincipal = new JMenuBar();
		
		archivo = new JMenu("Archivo");
		archivoAbrir = new JMenuItem ("Abrir Red");
		archivoGuardar = new JMenuItem ("Guardar Red");
		archivoCerrar = new JMenuItem ("Cerrar Simulador");
		archivoAbrir.addActionListener(this);
		archivoGuardar.addActionListener(this);
		archivoCerrar.addActionListener(this);
		
		archivo.add(archivoAbrir);
		archivo.add(archivoGuardar);
		archivo.add(archivoCerrar);
		
		simulacion = new JMenu("Simulación");
		simulacionAgregar = new JMenuItem ("Agregar Persona");
		simulacionBorrar = new JMenuItem ("Borrar Persona");
		simulacionBuscar = new JMenuItem ("Buscar Amigos");
		simulacionAtributos = new JMenuItem ("Administrar Atributos");
		
		simulacion.add(simulacionAgregar);
		simulacion.add(simulacionBorrar);
		simulacion.add(simulacionBuscar);
		simulacion.add(simulacionAtributos);
		
		
		
		ayuda = new JMenu("Ayuda");
		ayudaInfo = new JMenuItem("Acerca De");
		ayuda.add(ayudaInfo);
		
		menuPrincipal.add(archivo);
		menuPrincipal.add(simulacion);
		menuPrincipal.add(ayuda);
		
		//-- Paneles Izquierdo (Lista de integrantes) y Derecho (Grafo y propiedades de la busqueda)
		panelCentral = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		panelDer 	 = new JSplitPane( JSplitPane.VERTICAL_SPLIT );
		panelSub 	 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		
		//-- Establecer las propiedades lista de amigos.
		amigos = new JList(dlm);
		
		amigos.setPreferredSize(new Dimension(190, 600));
		amigos.setMinimumSize(new Dimension(190, 600));
		amigos.setVisibleRowCount(30);
		amigos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//-- Paneles Principales
		panelAttribs = new JPanel();
		panelAttribs.setPreferredSize(new Dimension(150, 400));
		panelAttribs.setMinimumSize(new Dimension(150,400));
		
		pnlGrafo = new JPanel();
		pnlGrafo.setPreferredSize(new Dimension(550, 400));
		pnlGrafo.setMinimumSize(new Dimension(550,400));
		
		pnlBusqueda = new JPanel(new GridLayout(4,2));
		pnlBusqueda.setPreferredSize(new Dimension(550, 200));
		pnlBusqueda.setMinimumSize(new Dimension(550, 200));
		
		panelDer.setLeftComponent(pnlGrafo);
		panelDer.setRightComponent(pnlBusqueda);
		
		//-- Agregar al splitpane vertical
		panelCentral.setLeftComponent(new JScrollPane(amigos));
		panelCentral.setRightComponent(panelDer);
		
		//-- Agregar al canvas principal
		this.setJMenuBar(menuPrincipal);
		panelSub.setLeftComponent(panelCentral);
		panelSub.setRightComponent(panelAttribs);
		this.add(panelSub,BorderLayout.CENTER);
		this.buildSearchPanel();
		
		//-- Statusbar
		lblStatusBar = new JLabel(" ");
		this.add(lblStatusBar,BorderLayout.SOUTH);
	}
	
	public void addElementToList(Persona p){
		this.dlm.addElement(p);
	}
	
	public void removeElement(Persona p){
		this.dlm.removeElement(p);
	}
	
	public void setGraphDisplay(JGraph g){
		this.panelDer.setLeftComponent(g);
		g.setPreferredSize(new Dimension(550, 400));
		g.setMinimumSize(new Dimension(550,400));
		g.addGraphSelectionListener(this);
		g.setAutoResizeGraph(true);
		g.setEditable(false);
	}
	
	public void buildSearchPanel(){
		search = new JButton("Buscar");
		search.addActionListener(this);
		compatibilidad = new JTextField();
		niveles = new JTextField();
		
		this.pnlBusqueda.add(new JLabel("Buscar Amigos"));
		this.pnlBusqueda.add(new JLabel());
		this.pnlBusqueda.add(new JLabel("Porcentaje de Compatibilidad (x/100):"));
		this.pnlBusqueda.add(compatibilidad);
		this.pnlBusqueda.add(new JLabel("Niveles de Búsqueda:"));
		this.pnlBusqueda.add(niveles);
		this.pnlBusqueda.add(new JLabel());
		this.pnlBusqueda.add(search);
		
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(archivoAbrir)){
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
		       File file = fc.getSelectedFile();
		       String path = file.getAbsolutePath();
		       //-- Mandar a abrir archivo del agente
			} 
		} else if(e.getSource().equals(archivoGuardar)) { 
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(this);
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            String path = file.getAbsolutePath();
	            //-- Mandar a guardar archivo del agente
	        } 
		} else if(e.getSource().equals(archivoCerrar)){
			System.exit(0);
		} else if(e.getSource().equals(search)){
			if(this.seleccionada == null){
				this.lblStatusBar.setText("Seleccione una persona sobre la cual realizar la busqueda");
				this.lblStatusBar.setForeground(Color.red);
			} else {
				this.lblStatusBar.setForeground(Color.black);
				this.lblStatusBar.setText("Buscando amigos...");
				//-- Buscar amigos con el agente
			}
			
		}
	}

	@Override
	public void valueChanged(GraphSelectionEvent arg0) {
		//-- Cambiar seleccion
		//System.out.println(arg0.getCell().toString());
	}
}
